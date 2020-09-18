/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 *  Notice: Whale Cloud Inc copyrights this specification.
 *  No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 */
package com.wteam.web;

import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.annotation.rest.AnonymousPostMapping;
import com.wteam.domain.LocalStorage;
import com.wteam.domain.criteria.LocalStorageQueryCriteria;
import com.wteam.domain.vo.R;
import com.wteam.service.LocalStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
* 存储 控制层.
* @author mission
* @since 2019-11-03
*/
@SuppressWarnings({"rawtypes"})
@Api(value="存储Controller",tags={"工具：存储操作接口"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/localStorage")
@PermissionGroup(value = "LOCALSTORAGE", aliasPrefix = "存储")
public class LocalStorageController {

    private final LocalStorageService localStorageService;


    //@Log("查询存储")
    @ApiOperation(value = "查询存储")
    @GetMapping(value = "/page")
    @PreAuthorize("@R.check('LOCALSTORAGE:all','LOCALSTORAGE:list')")
    public R getLocalStorages(LocalStorageQueryCriteria criteria, Pageable pageable){
        return R.ok(localStorageService.queryAll(criteria,pageable));
    }

    //@Log("查询存储详情")
    @ApiOperation(value = "查询存储")
    @GetMapping(value = "/get/{id}")
    @PreAuthorize("@R.check('GENTEMPLATE:all','GENTEMPLATE:list')")
        public R get(@PathVariable Long id){
    return R.ok(localStorageService.findDTOById(id));
    }

    //@Log("新增存储")
    @ApiOperation(value = "新增存储")
    @AnonymousPostMapping(value = "/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "File", allowMultiple = true),
            @ApiImplicitParam(name = "name",value ="文件名")
    })
    public R create(@RequestParam(required = false) String name, @RequestParam("file") MultipartFile file){
        return R.ok(localStorageService.create(name,file));
    }

    //@Log("修改存储")
    @ApiOperation(value = "修改存储")
    @PostMapping(value = "/edit")
    @PreAuthorize("@R.check('LOCALSTORAGE:all','LOCALSTORAGE:edit')")
    public R update(@Validated @RequestBody LocalStorage resources){
        localStorageService.update(resources);
        return R.ok();
    }


    //@Log("多选删除")
    @PostMapping("/del")
    @ApiOperation("多选删除")
    @PreAuthorize("@R.check('LOCALSTORAGE:all','LOCALSTORAGE:del')")
    public R deleteAll(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return R.ok();
    }


    //@Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('LOCALSTORAGE:all','LOCALSTORAGE:list')")
    public void download(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

}