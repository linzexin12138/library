package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.criteria.FloorQueryCriteria;
import com.wteam.modules.library.service.FloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 楼层 控制层
 * @Author: Charles
 * @Date: 2020/9/21 10:13
 */

@Api(value="楼层Controller",tags={"图书馆：楼层操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/floor")
@PermissionGroup(value = "FLOOR", aliasPrefix = "楼层")
public class FloorController {

    private final FloorService floorService;

    @ApiOperation(value = "查询楼层")
    @Log("查询楼层")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','FLOOR:all','FLOOR:list')")
    public R getFloors(FloorQueryCriteria criteria){
       return R.ok(floorService.queryAll(criteria));
    }


    @ApiOperation(value = "新增楼层")
    @Log("新增楼层")
    @PostMapping("add")
    @PreAuthorize("@R.check('FLOOR:all','FLOOR:add')")
    public R create(@Validated @RequestBody Floor resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(floorService.create(resources));
    }

    @ApiOperation(value = "修改楼层")
    @Log("修改楼层")
    @PostMapping("edit")
    @PreAuthorize("@R.check('FLOOR:all','FLOOR:edit')")
    public R edit(@Validated(Floor.Update.class) @RequestBody Floor resources){
        floorService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除楼层")
    @Log("删除楼层")
    @PostMapping("del")
    @PreAuthorize("@R.check('FLOOR:all','FLOOR:del')")
    public R delete(@RequestBody Set<Long> ids){
        floorService.delete(ids);
        return R.ok();
    }

    /**
     * 导出数据
     * @param criteria /
     * @return response
     */
    @ApiOperation(value = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('FLOOR:all','FLOOR:list','USER:all','USER:list')")
    public void download(FloorQueryCriteria criteria, HttpServletResponse response) throws IOException {
        floorService.download(floorService.queryAll(criteria),response);
    }
}
