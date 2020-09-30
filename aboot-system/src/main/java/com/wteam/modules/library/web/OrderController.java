package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.domain.vo.R;
import com.wteam.exception.BadRequestException;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.OrderTime;
import com.wteam.modules.library.domain.UserExtra;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import com.wteam.modules.library.service.OrderTimeService;
import com.wteam.modules.library.service.OrderRecordService;
import com.wteam.modules.library.service.SeatService;
import com.wteam.modules.library.service.UserExtraService;
import com.wteam.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Author: Charles
 * @Date: 2020/9/28 15:50
 */

@Api(value="图书馆预约Controller",tags={"图书馆：预约操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/order")
public class OrderController {

    private final UserExtraService userExtraService;
    private final OrderRecordService orderRecordService;
    private final OrderTimeService orderTimeService;
    private final SeatService seatService;
    private final PasswordEncoder passwordEncoder;

    private static final int ORDER_NOTHING = 0;
    private static final int ORDER_TODAY = 1;
    private static final int ORDER_TOMORROW = 2;
    private static final int ORDER_TODAY_AND_TOMORROW = 3;

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

        UserExtra userExtra = userExtraService.findById(userId);

        Timestamp date = orderRecordVO.getDate();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime orderTime = date.toLocalDateTime();

        Integer orderStatus = userExtra.getOrderStatus();
        int compare = localTime.compareTo(orderTime);

        //用户一天只能预约一次，之后要签退才可以接着预约
        if ((orderStatus == ORDER_TODAY || orderStatus == ORDER_TODAY_AND_TOMORROW) &&  compare == 0){
            return R.ok("您已预约了今天的座位，请使用完后再预约");
        }else if ((orderStatus == ORDER_TOMORROW || orderStatus == ORDER_TODAY_AND_TOMORROW) && compare == -1){
            return R.ok("您已预约了明天的座位，请使用完后再预约");
        } else {
            try{
                orderRecordService.create(orderRecordVO);
            }catch (Exception e){
                throw new BadRequestException("已被其他人预约了");
            }

            //设置用户预约状态
            if (compare == 0){
                if (orderStatus == ORDER_NOTHING){
                    userExtra.setOrderStatus(ORDER_TODAY);
                }else {
                    userExtra.setOrderStatus(ORDER_TODAY_AND_TOMORROW);
                }
            }else{
                if (orderStatus == ORDER_NOTHING){
                    userExtra.setOrderStatus(ORDER_TOMORROW);
                }else {
                    userExtra.setOrderStatus(ORDER_TODAY_AND_TOMORROW);
                }
            }
            userExtraService.update(userExtra);
            return R.ok("预约成功");
        }
    }

    @ApiOperation(value = "签到")
    @Log("签到")
    @PostMapping("signIn")
    public R signIn(@RequestParam Long orderRecordId){

        OrderRecord orderRecord = orderRecordService.findById(orderRecordId);
        OrderTime orderTime = orderTimeService.findById(orderRecord.getOrderTimeId());
        orderRecord.setId(orderRecordId);
        orderRecord.setStatus(ORDER_SIGN_IN);
        orderRecordService.update(orderRecord);
        return R.ok();
    }

    @ApiOperation(value = "签退")
    @Log("签退")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("signOut")
    public R signOut(@RequestParam Long orderRecordId){
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(orderRecordId);
        orderRecord.setStatus(ORDER_SIGN_OUT);
        orderRecordService.update(orderRecord);
        return R.ok();
    }

    @ApiOperation(value = "取消预约")
    @Log("取消预约")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("cancel")
    public R cancel(@Validated(UserExtra.Update.class) @RequestBody OrderRecord resource){
        //如果当前时间比预约时间晚的话，则扣除一定的信用分
        //取消预约后更新用户的预约状态
//        if ()
//
//        OrderTime orderTime = orderTimeService.findById(resource.getOrderTimeId());
        UserExtra userExtra = userExtraService.findById(resource.getUserId());
        userExtra.setOrderStatus(0);
        orderRecordService.cancelOrder(resource);
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
    public R orderInfo(@RequestParam Timestamp date){
        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();
        criteria.setDate(date);
        return R.ok(orderRecordService.queryAll(criteria));
    }

    @ApiOperation(value = "某馆号的座位预约情况")
    @Log("某馆号的座位预约情况")
    @GetMapping("seatInfo")
    public R seatInfo(@RequestParam Timestamp date, @RequestParam Long roomId, Pageable pageable){

//        List<Long> seatId = seatService.getIdListByRoomId(roomId);


        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();
        criteria.setDate(date);
        return R.ok(orderRecordService.queryAll(criteria));
    }
}
