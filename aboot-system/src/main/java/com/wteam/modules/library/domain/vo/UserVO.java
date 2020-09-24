package com.wteam.modules.library.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: Charles
 * @Date: 2020/9/23 21:35
 */

@Data
public class UserVO {

    private Long id;


    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;


    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮箱
     */
    @Pattern(regexp = "([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}",message = "格式错误")
    private String email;

    /**
     * 电话
     */
    @NotNull
    private String phone;

    /**
     * 学号
     */
    @NotNull
    private String studentId;
}
