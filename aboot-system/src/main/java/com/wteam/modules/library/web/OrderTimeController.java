package com.wteam.modules.library.web;

import cn.hutool.core.lang.Assert;
import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.annotation.rest.AnonymousGetMapping;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.OrderTime;
import com.wteam.modules.library.domain.criteria.OrderTimeQueryCriteria;
import com.wteam.modules.library.service.OrderTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Charles
 * @since 2020/9/22 20:35
 */

@Api(value="预约时间Controller",tags={"图书馆：预约时间管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orderTime")
@PermissionGroup(value = "ORDERTIME", aliasPrefix = "预约时间")
public class OrderTimeController {
    
    private final OrderTimeService orderTimeService;

    @ApiOperation(value = "查询预约时间")
    @Log("查询预约时间")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:list')")
    public R getOrderTimes(){
        return R.ok(orderTimeService.queryAll());
    }


    @ApiOperation(value = "获取某天的预约时间段")
    @Log("获取某天的预约时间段")
    @GetMapping(value = "/getTodayOrderTime")
    @AnonymousGetMapping
    public R getSomedayOrderTime(@RequestParam LocalDate localDate)
    {
        return R.ok(orderTimeService.getSomedayOrderTime(localDate));
    }

    @ApiOperation(value = "新增预约时间")
    @Log("新增预约时间")
    @PostMapping("add")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:add')")
    public R create(@Validated @RequestBody OrderTime resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(orderTimeService.create(resources));
    }

    @ApiOperation(value = "修改预约时间")
    @Log("修改预约时间")
    @PostMapping("edit")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:edit')")
    public R edit(@Validated(OrderTime.Update.class) @RequestBody OrderTime resources){
        orderTimeService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除预约时间")
    @Log("删除预约时间")
    @PostMapping("del")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:del')")
    public R delete(@RequestBody Set<Long> ids){
        orderTimeService.deleteAll(ids);
        return R.ok();
    }

//    @Override
//    public Map<String,Object> queryAll(OrderRecordQueryCriteria criteria, Pageable pageable){
//        Page<OrderRecord> page = orderTimeService.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
//        return PageUtil.toPage(page.map(orderRecordMapper::toDto));
//    }
//
//    @Override
//    public List<OrderRecordDTO> queryAll(OrderRecordQueryCriteria criteria){
//        return orderRecordMapper.toDto(orderRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
//    }
    
}
