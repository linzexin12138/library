package com.wteam.modules.library.web;

import cn.hutool.core.lang.Assert;
import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.LibOrderTime;
import com.wteam.modules.library.service.LibOrderTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:35
 */

@Api(value="预约时间Controller",tags={"图书馆：预约时间管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/libOrderTime")
@PermissionGroup(value = "ORDERTIME", aliasPrefix = "预约时间")
public class LibOrderTimeController {
    
    private final LibOrderTimeService libOrderTimeService;

    @ApiOperation(value = "查询预约时间")
    @Log("查询预约时间")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:list')")
    public R getOrderTimes(){
        return R.ok(libOrderTimeService.queryAll());
    }


    @ApiOperation(value = "新增预约时间")
    @Log("新增预约时间")
    @PostMapping("add")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:add')")
    public R create(@Validated @RequestBody LibOrderTime resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(libOrderTimeService.create(resources));
    }

    @ApiOperation(value = "修改预约时间")
    @Log("修改预约时间")
    @PostMapping("edit")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:edit')")
    public R edit(@Validated(LibOrderTime.Update.class) @RequestBody LibOrderTime resources){
        libOrderTimeService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除预约时间")
    @Log("删除预约时间")
    @PostMapping("del")
    @PreAuthorize("@R.check('ORDERTIME:all','ORDERTIME:del')")
    public R delete(@RequestBody Set<Long> ids){
        libOrderTimeService.delete(ids);
        return R.ok();
    }
    
    
}
