package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.domain.vo.R;
import com.wteam.exception.BadRequestException;
import com.wteam.modules.library.domain.*;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import com.wteam.modules.library.service.*;
import com.wteam.modules.library.utils.TimeUtil;
import com.wteam.utils.DateUtil;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

/**
 * @author Charles
 * @since 2020/9/28 15:50
 */

@Api(value="图书馆预约Controller",tags={"图书馆：预约操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/order")
public class OrderController {

    private final UserExtraService userExtraService;
    private final OrderRecordService orderRecordService;
    private final UserOrderService userOrderService;
    private final CreditScoreLogService creditScoreService;
    private final RedisUtils redisUtils;

    private static final int ORDER_NO_SIGN_IN = 0;
    private static final int ORDER_SIGN_IN = 1;
    private static final int ORDER_SIGN_OUT = 2;
    private static final int ORDER_TIME_OUT = 3;
    private static final int ORDER_CANCEL = 4;

    @ApiOperation(value = "预约座位")
    @Log("预约座位")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("order")
    public R order(@Validated @RequestBody OrderRecordVO orderRecordVO){

        Long userId = SecurityUtils.getId();
        UserExtra userExtra = userExtraService.findByUserId(userId);

        if (userExtra.getCreditScore() < 80){
            throw new BadRequestException("信用分低于80分不可以预约座位");
        }

        if (orderRecordVO.getSeatIdList().size() != orderRecordVO.getOrderTimeIdList().size()){
            throw new BadRequestException("参数错误");
        }

        LocalDate date = orderRecordVO.getDate();

        if (!TimeUtil.isToday(date) && !TimeUtil.isTomorrow(date)){
            return R.ok("只能预约今天或者明天的座位");
        }

        if (userOrderService.checkRepeat(userId, orderRecordVO.getDate(),orderRecordVO.getOrderTimeIdList())){
            throw new BadRequestException("一个时间段只能预定一个座位");
        }

        List<OrderRecord> orderRecordList;
        orderRecordVO.setUserId(userId);
        orderRecordList = orderRecordService.create(orderRecordVO);
        userOrderService.create(orderRecordList);

        for (OrderRecord orderRecord : orderRecordList){
            LocalDateTime dateTime = LocalDateTime.of(orderRecord.getDate(), orderRecord.getOrderTime().getEndTime());
            Long orderTimeStamp = DateUtil.getTimeStamp(dateTime);
            Long nowTimeStamp = DateUtil.getTimeStamp(LocalDateTime.now());
            Long expireTime = orderTimeStamp - nowTimeStamp;
            redisUtils.set("orderId:"+orderRecord.getId(),orderRecord.getStatus(),expireTime);
        }
        return R.ok("预约成功");
    }

    @SuppressWarnings("Duplicates")
    @ApiOperation(value = "签到")
    @Log("签到")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("signIn")
    public R signIn(@RequestParam Long orderRecordId){

        OrderRecord orderRecord = orderRecordService.findByIdAndUserId(orderRecordId, SecurityUtils.getId());

        if (orderRecord == null){
            throw new BadRequestException("没有该预约记录");
        }
        //只有预约订单的状态为未签到才可以进行签到
        if (orderRecord.getStatus() != 0){
            throw new BadRequestException("该预约已经不可以签到了");
        }

        LocalTime orderStarTime = orderRecord.getOrderTime().getStarTime();
        LocalTime orderEndTime = orderRecord.getOrderTime().getEndTime();
        LocalTime checkStartTime = orderStarTime.minusMinutes(10L);

        LocalTime orderCreateTime = orderRecord.getCreatedAt().toLocalDateTime().toLocalTime();
        LocalTime nowTime = LocalTime.now();

        Long userId = SecurityUtils.getId();
        UserExtra userExtra = userExtraService.findByUserId(userId);
        CreditScoreLog creditScoreLog = null;

        if (!TimeUtil.isToday(orderRecord.getDate())){
            throw new BadRequestException("还没到预约当天，不可以签到");
        }else if(nowTime.isAfter(orderEndTime)){
            throw new BadRequestException("签到时间已经过");
        }else if(orderStarTime.isBefore(orderCreateTime) || orderStarTime.equals(orderCreateTime)){
            //当用户预约座位时正好在预约时间段内，则在预约后十分钟内进行签到不扣分
            if (nowTime.isAfter(orderCreateTime.plusMinutes(10L))){
                int score = userExtra.getCreditScore() - 1;
                userExtra.setCreditScore(score);

                creditScoreLog = new CreditScoreLog();
                creditScoreLog.setUserId(userId);
                creditScoreLog.setReason("迟到十分钟，扣除1分信用分");
                creditScoreLog.setCreditScore(score);
            }
        }else if(nowTime.isBefore(checkStartTime)){
            throw new BadRequestException("预约时间的前十分钟才开始签到");
        }else if(nowTime.isAfter(orderStarTime.plusMinutes(10L))){
            //在预约开始时间后十分钟进行签到则要扣除1分信用分
            int score = userExtra.getCreditScore() - 1;
            userExtra.setCreditScore(score);

            creditScoreLog = new CreditScoreLog();
            creditScoreLog.setUserId(userId);
            creditScoreLog.setReason("迟到十分钟，扣除1分信用分");
            creditScoreLog.setCreditScore(score);
        }

        orderRecord.setId(orderRecordId);
        orderRecord.setStatus(ORDER_SIGN_IN);
        orderRecordService.updateStatus(orderRecord);
        userExtraService.updateCreditScore(userExtra);

        if (creditScoreLog != null){
            creditScoreService.create(creditScoreLog);
        }
        return R.ok("签到成功");
    }

    @ApiOperation(value = "提前离开")
    @Log("提前离开")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("signOut")
    public R signOut(@RequestParam Long orderRecordId){
        OrderRecord orderRecord = orderRecordService.findByIdAndUserId(orderRecordId,SecurityUtils.getId());
        if (orderRecord == null){
            throw new BadRequestException("没有该预约记录");
        }

        //只有签到后才可以提前离开
        if (orderRecord.getStatus() != 1){
            throw new BadRequestException("只有签到后才可以提前离开");
        }

        LocalTime endTime = orderRecord.getOrderTime().getEndTime();
        LocalTime nowTime = LocalTime.now();

        if (!TimeUtil.isToday(orderRecord.getDate())){
            throw new BadRequestException("还没到预约当天");
        }

        if (nowTime.isAfter(endTime)){
            throw new BadRequestException("预约时间已结束");
        }

        orderRecord.setStatus(ORDER_SIGN_OUT);
        orderRecordService.update(orderRecord);
        userOrderService.deleteByOrderId(orderRecord.getId());
        return R.ok("成功");
    }

    @ApiOperation(value = "取消预约")
    @Log("取消预约")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("cancel")
    public R cancel(Long orderRecordId){

        OrderRecord orderRecord = orderRecordService.findByIdAndUserId(orderRecordId,SecurityUtils.getId());
        if (orderRecord == null){
            throw new BadRequestException("没有该预约记录");
        }

        if (orderRecord.getStatus() != 0){
            throw new BadRequestException("该预约不可取消");
        }

        LocalTime starTime = orderRecord.getOrderTime().getStarTime();
        LocalTime endTime = orderRecord.getOrderTime().getEndTime();
        LocalTime nowTime = LocalTime.now();

        UserExtra userExtra = userExtraService.findByUserId(SecurityUtils.getId());
        CreditScoreLog creditScoreLog = null;

        //预约时间在今天以前的为过期
        if (TimeUtil.isHistory(orderRecord.getDate())){
            throw new BadRequestException("该预约已过期");
        }else if (TimeUtil.isToday(orderRecord.getDate())){
            //如果取消时预约已经开始了，则扣除信用分
            if (nowTime.isAfter(starTime)){
                int score = userExtra.getCreditScore() - 3;
                userExtra.setCreditScore(score);

                creditScoreLog = new CreditScoreLog();
                creditScoreLog.setUserId(SecurityUtils.getId());
                creditScoreLog.setReason("取消预约时间过晚，扣除3分信用分");
                creditScoreLog.setCreditScore(score);
            }
            //预约时间已经过期
            if (nowTime.isAfter(endTime)){
                throw new BadRequestException("该预约已过期");
            }
        }

        orderRecord.setStatus(ORDER_CANCEL);
        orderRecordService.updateStatus(orderRecord);
        userOrderService.deleteByOrderId(orderRecord.getId());

        if (creditScoreLog != null){
            userExtraService.updateStatus(userExtra);
            creditScoreService.create(creditScoreLog);
        }

        return R.ok("取消成功");
    }


    @ApiOperation(value = "我的预约")
    @Log("我的预约")
    @GetMapping("myOrder")
    public R myOrder(@RequestParam Long userId){
        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();
        criteria.setUserId(userId);
        return R.ok(orderRecordService.queryAll(criteria));
    }

    @ApiOperation(value = "预约情况")
    @Log("预约情况")
    @GetMapping("orderInfo")
    public R orderInfo(@RequestParam LocalDate date){
        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();
        criteria.setDate(date);
        return R.ok(orderRecordService.queryAll(criteria));
    }

    @ApiOperation(value = "某馆号的座位预约情况")
    @Log("某馆号的座位预约情况")
    @GetMapping("seatInfo")
    public R seatInfo(@RequestParam LocalDate date, @RequestParam Long roomId, Pageable pageable){

//        List<Long> seatId = seatService.getIdListByRoomId(roomId);


        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();
        criteria.setDate(date);
        return R.ok(orderRecordService.queryAll(criteria));
    }
}
