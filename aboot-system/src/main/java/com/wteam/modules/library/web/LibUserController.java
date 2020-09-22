package com.wteam.modules.library.web;

import com.wteam.annotation.permission.PermissionGroup;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Charles
 * @Date: 2020/9/22 11:25
 */

@Api(value="图书馆用户Controller",tags={"系统：图书馆用户操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/libUser")
@PermissionGroup(value = "LIBUSER", aliasPrefix = "图书馆用户")
public class LibUserController {


}
