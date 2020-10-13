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
import com.wteam.modules.library.domain.CreditScoreLog;
import com.wteam.modules.library.service.CreditScoreLogService;
import com.wteam.modules.library.domain.criteria.CreditScoreLogQueryCriteria;
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
* 信用分记录 控制层.
* @author charles
* @since 2020-10-03
*/
@SuppressWarnings({"rawtypes"})
@Api(value="信用分记录Controller",tags={"新增：信用分记录操作"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/creditScoreLog")
@PermissionGroup(value = "CREDITSCORELOG",aliasPrefix = "信用分记录")
public class CreditScoreLogController {

    private final CreditScoreLogService creditScoreLogService;

    //@Log("查询信用分记录分页")
    @ApiOperation(value = "查询信用分记录分页")
    @GetMapping(value = "/page")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:list')")
    public R getCreditScoreLogs(CreditScoreLogQueryCriteria criteria, Pageable pageable){
        return R.ok(creditScoreLogService.queryAll(criteria,pageable));
    }

    //@Log("查询信用分记录详情")
    @ApiOperation(value = "查询信用分记录详情")
    @GetMapping(value = "/get/{id}")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:list')")
    public R get(@PathVariable Long id){
        return R.ok(creditScoreLogService.findDTOById(id));
    }

    //@Log("新增信用分记录")
    @ApiOperation(value = "新增信用分记录")
    @PostMapping(value = "/add")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:add')")
    public R create(@Validated @RequestBody CreditScoreLog resources){
        return R.ok(creditScoreLogService.create(resources));
    }

    //@Log("修改信用分记录")
    @ApiOperation(value = "修改信用分记录")
    @PostMapping(value = "/edit")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:edit')")
    public R update(@Validated(CreditScoreLog.Update.class) @RequestBody CreditScoreLog resources){
        creditScoreLogService.update(resources);
        return R.ok();
    }

    //@Log("删除信用分记录")
    @ApiOperation(value = "删除信用分记录")
    @PostMapping(value = "/del")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:del')")
    public R delete(@RequestBody Set<Long> ids){
        creditScoreLogService.deleteAll(ids);
        return R.ok();
    }

    //@Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('CREDITSCORELOG:all','CREDITSCORELOG:list')")
    public void download(HttpServletResponse response, CreditScoreLogQueryCriteria criteria) throws IOException {
        creditScoreLogService.download(creditScoreLogService.queryAll(criteria), response);
    }

}