/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.web;

import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.UserOrder;
import com.wteam.modules.library.service.UserOrderService;
import com.wteam.modules.library.domain.criteria.UserOrderQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* 用户预约信息 控制层.
* @author charles
* @since 2020-10-02
*/
@SuppressWarnings({"rawtypes"})
@Api(value="用户预约信息Controller",tags={"新增：用户预约信息操作"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/userOrder")
@PermissionGroup(value = "USERORDER",aliasPrefix = "用户预约信息")
public class UserOrderController {

    private final UserOrderService userOrderService;

    //@Log("查询用户预约信息分页")
    @ApiOperation(value = "查询用户预约信息分页")
    @GetMapping(value = "/page")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:list')")
    public R getUserOrders(UserOrderQueryCriteria criteria, Pageable pageable){
        return R.ok(userOrderService.queryAll(criteria,pageable));
    }

    //@Log("查询用户预约信息详情")
    @ApiOperation(value = "查询用户预约信息详情")
    @GetMapping(value = "/get/{id}")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:list')")
    public R get(@PathVariable Long id){
        return R.ok(userOrderService.findDTOById(id));
    }

    //@Log("新增用户预约信息")
    @ApiOperation(value = "新增用户预约信息")
    @PostMapping(value = "/add")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:add')")
    public R create(@Validated @RequestBody UserOrder resources){
        return R.ok(userOrderService.create(resources));
    }

    //@Log("修改用户预约信息")
    @ApiOperation(value = "修改用户预约信息")
    @PostMapping(value = "/edit")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:edit')")
    public R update(@Validated(UserOrder.Update.class) @RequestBody UserOrder resources){
        userOrderService.update(resources);
        return R.ok();
    }

    //@Log("删除用户预约信息")
    @ApiOperation(value = "删除用户预约信息")
    @PostMapping(value = "/del")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:del')")
    public R delete(@RequestBody Set<Long> ids){
        userOrderService.deleteAll(ids);
        return R.ok();
    }

    //@Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('USERORDER:all','USERORDER:list')")
    public void download(HttpServletResponse response, UserOrderQueryCriteria criteria) throws IOException {
        userOrderService.download(userOrderService.queryAll(criteria), response);
    }

}