package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.base.BaseEntity;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Room;
import com.wteam.modules.library.domain.criteria.RoomQueryCriteria;
import com.wteam.modules.library.domain.dto.FloorSmallDTO;
import com.wteam.modules.library.domain.dto.RoomDTO;
import com.wteam.modules.library.domain.dto.RoomSmallDTO;
import com.wteam.modules.library.service.RoomService;
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
 * 馆号 控制层
 * @Author: Charles
 * @Date: 2020/9/21 10:13
 */

@Api(value="馆号Controller",tags={"图书馆：馆号操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/room")
@PermissionGroup(value = "ROOM", aliasPrefix = "馆号")
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(value = "查询馆号")
    @Log("查询馆号")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','ROOM:all','ROOM:list')")
    public R getRooms(RoomQueryCriteria criteria){
       return R.ok(roomService.queryAll(criteria));
    }


    @ApiOperation(value = "新增馆号")
    @Log("新增馆号")
    @PostMapping("add")
    @PreAuthorize("@R.check('ROOM:all','ROOM:add')")
    public R create(@Validated @RequestBody Room resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(roomService.create(resources));
    }

    @ApiOperation(value = "修改馆号")
    @Log("修改馆号")
    @PostMapping("edit")
    @PreAuthorize("@R.check('ROOM:all','ROOM:edit')")
    public R edit(@Validated(Room.Update.class) @RequestBody Room resources){
        roomService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除馆号")
    @Log("删除馆号")
    @PostMapping("del")
    @PreAuthorize("@R.check('ROOM:all','ROOM:del')")
    public R delete(@RequestBody Set<Long> ids){
        roomService.delete(ids);
        return R.ok();
    }

    @ApiOperation(value = "管理馆号里的座位")
    @Log("管理馆号里的座位")
    @PostMapping("editSeat")
    @PreAuthorize("@R.check('FLOOR:all','FLOOR:edit')")
    public R editSeat(@Validated @RequestBody RoomSmallDTO room){
        if (room.getId()!= null){
            roomService.editSeat(room.getId(), room.getSeatIds());

        }
        return R.ok();
    }

    /**
     * 导出数据
     * @param criteria /
     * @return response
     */
    @ApiOperation(value = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('ROOM:all','ROOM:list','USER:all','USER:list')")
    public void download(RoomQueryCriteria criteria, HttpServletResponse response) throws IOException {
        roomService.download(roomService.queryAll(criteria),response);
    }
}
