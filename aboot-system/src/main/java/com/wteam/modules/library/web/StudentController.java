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

    @ApiOperation(value = "查询学生列表")
    @Log("查询学生")
    @GetMapping("/page")
    @PreAuthorize("@R.check('USER:all','USER:list')")
    public R getStudents(UserRoleQueryCriteria criteria, Pageable pageable){
        criteria.setRoleId(STUDENT);
        return R.ok(userService.queryAll(criteria,pageable));
    }
}
