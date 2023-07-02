package com.omoi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Data
public class UserRequest {
    @NotNull(message = "id不能为空")
    private String id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private String email;

    private String tel;
}
