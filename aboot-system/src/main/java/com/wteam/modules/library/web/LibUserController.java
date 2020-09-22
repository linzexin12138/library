package com.wteam.modules.library.web;

import com.google.common.collect.Sets;
import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.annotation.rest.AnonymousPostMapping;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.LibUser;
import com.wteam.modules.library.service.CategoryService;
import com.wteam.modules.library.service.FloorService;
import com.wteam.modules.library.service.RoomService;
import com.wteam.modules.library.service.SeatService;
import com.wteam.modules.system.config.LoginType;
import com.wteam.modules.system.domain.Role;
import com.wteam.modules.system.domain.User;
import com.wteam.modules.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author: Charles
 * @Date: 2020/9/22 11:25
 */

@Api(value="图书馆用户Controller",tags={"图书馆：用户操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/libUser")
public class LibUserController {

    private final UserService userService;
    private final FloorService floorService;
    private final CategoryService categoryService;
    private final RoomService roomService;
    private final SeatService seatService;
    private final PasswordEncoder passwordEncoder;

    private static final Long STUDENT = 4L;

    @ApiOperation(value = "注册用户")
    @Log("注册用户")
    @AnonymousPostMapping("register")
    public R register(@Validated @RequestBody LibUser resources){
        Assert.isNull(resources.getId(),"实体ID应为空");

        User user = new User();
        user.setUsername(resources.getUsername());
        user.setNickname("用户12138");
        user.setPassword(passwordEncoder.encode(resources.getPassword()));
        user.setPhone(resources.getPhone());
        user.setEmail(resources.getEmail());
        user.setEnabled(true);
        user.setLoginType(LoginType.LOGIN_NORMAL);
        user.setLastLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        user.setRoles(Sets.newHashSet(new Role(STUDENT)));
        return R.ok(userService.create(user));
    }

}
