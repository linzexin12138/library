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
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.service.OrderRecordService;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.utils.SecurityUtils;
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
* 预约记录 控制层.
* @author charles
* @since 2020-09-23
*/
@SuppressWarnings({"rawtypes"})
@Api(value="预约记录Controller",tags={"图书馆：预约记录操作"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/orderRecord")
@PermissionGroup(value = "ORDERRECORD",aliasPrefix = "预约记录")
public class OrderRecordController {

    private final OrderRecordService orderRecordService;

    //@Log("查询预约记录分页")
    @ApiOperation(value = "查询预约记录分页")
    @GetMapping(value = "/page")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:list')")
    public R getOrderRecords(OrderRecordQueryCriteria criteria, Pageable pageable){
        return R.ok(orderRecordService.queryAll(criteria,pageable));
    }

    //@Log("查询预约记录详情")
    @ApiOperation(value = "查询预约记录详情")
    @GetMapping(value = "/get/{id}")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:list')")
    public R get(@PathVariable Long id){
        return R.ok(orderRecordService.findDTOById(id));
    }

    //@Log("新增预约记录")
    @ApiOperation(value = "新增预约记录")
    @PostMapping(value = "/add")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:add')")
    public R create(@Validated @RequestBody OrderRecord resources){
        return R.ok(orderRecordService.create(resources));
    }

    //@Log("修改预约记录")
    @ApiOperation(value = "修改预约记录")
    @PostMapping(value = "/edit")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:edit')")
    public R update(@Validated(OrderRecord.Update.class) @RequestBody OrderRecord resources){
        orderRecordService.update(resources);
        return R.ok();
    }

    //@Log("删除预约记录")
    @ApiOperation(value = "删除预约记录")
    @PostMapping(value = "/del")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:del')")
    public R delete(@RequestBody Set<Long> ids){
        orderRecordService.deleteAll(ids);
        return R.ok();
    }

    //@Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('ORDERRECORD:all','ORDERRECORD:list')")
    public void download(HttpServletResponse response, OrderRecordQueryCriteria criteria) throws IOException {
        orderRecordService.download(orderRecordService.queryAll(criteria), response);
    }

}