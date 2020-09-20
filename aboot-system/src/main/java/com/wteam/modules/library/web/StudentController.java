package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.criteria.UserRoleQueryCriteria;
import com.wteam.modules.system.config.LoginType;
import com.wteam.modules.system.domain.User;
import com.wteam.modules.system.domain.mapper.RoleMapper;
import com.wteam.modules.system.service.RoleService;
import com.wteam.modules.system.service.UserService;
import com.wteam.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Charles
 * @Date: 2020/9/20 16:13
 */

@SuppressWarnings({"rawtypes"})
@Api(value="学生Controller",tags={"系统：学生操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/student")
@PermissionGroup(value = "STUDENT", aliasPrefix = "学生")
public class StudentController {

    private static final Long STUDENT = 4L;

    private final UserService userService;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "查询学生列表")
    @Log("查询学生")
    @GetMapping("/page")
    @PreAuthorize("@R.check('USER:all','USER:list')")
    public R getStudents(UserRoleQueryCriteria criteria, Pageable pageable){
        criteria.setRoleId(STUDENT);
        return R.ok(userService.queryAll(criteria,pageable));
    }


    @ApiOperation(value = "新增学生")
    @Log("新增学生")
    @PostMapping("/add")
    @PreAuthorize("@R.check('STUDENT:all','STUDENT:add')")
    public R add(@Validated @RequestBody User resources){
        Assert.isNull(resources.getId(),"实体ID应为空");

        //设置密码
        if (StringUtils.isNotBlank(resources.getPassword())){
            resources.setPassword(passwordEncoder.encode(resources.getPassword()));
        }else {
            resources.setPassword(passwordEncoder.encode("123456"));
        }

        //设置登录类型
        resources.setLoginType(LoginType.LOGIN_NORMAL);
        return R.ok(userService.createAdmin(resources,STUDENT));
    }

    //编辑管理员的功能通过调用UserController里的edit方法，路径为/api/user/edit

    //批量删除管理者的功能调用UserController里的delete方法，路径为/api/user/del
}
