package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.criteria.UserRoleQueryCriteria;
import com.wteam.modules.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Charles
 * @Date: 2020/9/20 14:42
 */

@SuppressWarnings({"rawtypes"})
@Api(value="管理员Controller",tags={"系统：管理员操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin")
@PermissionGroup(value = "ADMIN", aliasPrefix = "管理员")
public class AdminController {

    private static final Long ADMIN = 1L;

    private final UserService userService;

    @ApiOperation(value = "查询管理员列表")
    @Log("查询管理员")
    @GetMapping("/page")
    @PreAuthorize("@R.check('USER:all','USER:list')")
    public R getAdmins(UserRoleQueryCriteria criteria, Pageable pageable){
        criteria.setRoleId(ADMIN);
        return R.ok(userService.queryAll(criteria,pageable));
    }

    @ApiOperation(value = "新增管理员")
    @Log("新增管理员")
    @GetMapping("/add")
    @PreAuthorize("@R.check('ADMIN:all','ADMIN:add')")
    public R add(){
        return R.ok();
    }

    @ApiOperation(value = "编辑管理员列表")
    @Log("编辑管理员")
    @GetMapping("/edit")
    @PreAuthorize("@R.check('ADMIN:all','ADMIN:delete')")
    public R edit(){
        return R.ok();
    }

    @ApiOperation(value = "批量删除管理员列表")
    @Log("批量删除管理员")
    @GetMapping("/delete")
    @PreAuthorize("@R.check('ADMIN:all','ADMIN:del')")
    public R delete(){
        return R.ok();
    }

}
