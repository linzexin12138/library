package com.wteam.modules.library.web;

import com.google.common.collect.Sets;
import com.wteam.annotation.Log;
import com.wteam.annotation.rest.AnonymousPostMapping;
import com.wteam.domain.vo.R;
import com.wteam.exception.BadRequestException;
import com.wteam.modules.library.domain.LibUser;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.dto.OrderRecordDTO;
import com.wteam.modules.library.domain.vo.UserVO;
import com.wteam.modules.library.service.*;
import com.wteam.modules.system.config.LoginType;
import com.wteam.modules.system.domain.Role;
import com.wteam.modules.system.domain.User;
import com.wteam.modules.system.domain.dto.UserDTO;
import com.wteam.modules.system.service.UserService;
import com.wteam.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final LibUserService libUserService;
    private final FloorService floorService;
    private final CategoryService categoryService;
    private final RoomService roomService;
    private final SeatService seatService;
    private final OrderRecordService orderRecordService;
    private final PasswordEncoder passwordEncoder;

    private static final Long STUDENT = 4L;

    @ApiOperation(value = "注册用户")
    @Log("注册用户")
    @AnonymousPostMapping("register")
    public R register(@Validated @RequestBody UserVO resources){
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
        UserDTO userDTO = userService.create(user);

        LibUser libUser = new LibUser();
        libUser.setStudentId(resources.getStudentId());
        libUser.setUserId(userDTO.getId());
        libUserService.create(libUser);
        return R.ok(userDTO);
    }

    @ApiOperation(value = "预约座位")
    @Log("预约座位")
    @PostMapping("order")
    public R order(@Validated @RequestBody OrderRecord resource){
        Long userId = SecurityUtils.getId();
        resource.setUserId(userId);
        LibUser libUser = libUserService.findById(userId);

        if (libUser.getOrderStatus()){
            return R.ok("您已预约了一个座位，请使用完后再预约");
        }else {
            try{
                orderRecordService.create(resource);
            }catch (Exception e){
                throw new BadRequestException("已被其他人预约了");
            }

            //设置用户预约状态
            libUser.setOrderStatus(true);
            libUserService.update(libUser);
            return R.ok("预约成功");
        }
    }


    @ApiOperation(value = "取消预约")
    @Log("取消预约")
    @PostMapping("cancel")
    public R cancel(@Validated @RequestBody OrderRecord resource){
        return R.ok();
    }

    @ApiOperation(value = "我的预约")
    @Log("我的预约")
    @PostMapping("myOrder")
    public R myOrder(){
        OrderRecordQueryCriteria criteria = new OrderRecordQueryCriteria();

        orderRecordService.queryAll(criteria);
        return R.ok();
    }


}
