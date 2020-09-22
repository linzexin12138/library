package com.wteam.modules.library.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: Charles
 * @Date: 2020/9/22 12:48
 */

@Data
public class LibUser{

    public final static String ENTITY_NAME ="学生账号";

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
    private String phone;

    /**
     * 学号
     */
    private String studentId;


}
