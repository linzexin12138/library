package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.base.BaseEntity;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Seat;
import com.wteam.modules.library.domain.criteria.SeatQueryCriteria;
import com.wteam.modules.library.service.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 座位 控制层
 * @Author: Charles
 * @Date: 2020/9/21 10:13
 */

@Api(value="座位Controller",tags={"图书馆：座位操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/seat")
@PermissionGroup(value = "SEAT", aliasPrefix = "座位")
public class SeatController {

    private final SeatService seatService;

    @ApiOperation(value = "查询座位")
    @Log("查询座位")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','SEAT:all','SEAT:list')")
    public R getSeats(SeatQueryCriteria criteria){
       return R.ok(seatService.queryAll(criteria));
    }


    @ApiOperation(value = "新增座位")
    @Log("新增座位")
    @PostMapping("add")
    @PreAuthorize("@R.check('SEAT:all','SEAT:add')")
    public R create(@Validated @RequestBody Seat resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(seatService.create(resources));
    }

    @ApiOperation(value = "修改座位")
    @Log("修改座位")
    @PostMapping("edit")
    @PreAuthorize("@R.check('SEAT:all','SEAT:edit')")
    public R edit(@Validated(Seat.Update.class) @RequestBody Seat resources){
        seatService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除座位")
    @Log("删除座位")
    @PostMapping("del")
    @PreAuthorize("@R.check('SEAT:all','SEAT:del')")
    public R delete(@RequestBody Set<Long> ids){
        seatService.delete(ids);
        return R.ok();
    }

    /**
     * 导出数据
     * @param criteria /
     * @return response
     */
    @ApiOperation(value = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('SEAT:all','SEAT:list','USER:all','USER:list')")
    public void download(SeatQueryCriteria criteria, HttpServletResponse response) throws IOException {
        seatService.download(seatService.queryAll(criteria),response);
    }
}
