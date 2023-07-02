package com.omoi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.omoi.entity.AjaxResult;
import com.omoi.entity.Certificate;
import com.omoi.entity.LoginUser;
import com.omoi.entity.User;
import com.omoi.request.UserRequest;
import com.omoi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author omoi
 * @date 2023/7/2
 */
@RestController
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // 注册
    @PostMapping("/user/register")
    public AjaxResult userRegister(@RequestBody UserRequest request){
        User user = BeanUtil.copyProperties(request, User.class);
        boolean register = userService.register(request.getPassword(), user);

        return register ? AjaxResult.success() : AjaxResult.error();
    }

    // 登录
    @PostMapping("/user/login")
    public AjaxResult userLogin(@RequestBody UserRequest request){
        User user = BeanUtil.copyProperties(request, User.class);
        LoginUser loginUser = userService.login(request.getId(), user);
        Map<String, String> data = userService.getJwtData(loginUser);

        return AjaxResult.success(data);
    }

    // 获取RSA公钥
    @GetMapping("/user/certificate")
    public AjaxResult getCertificate(){
        Certificate certificate = userService.generateCertificate();

        return AjaxResult.success(certificate);
    }

    // 注销
    @PostMapping("/user/logout")
    public AjaxResult userLogout(){
        userService.logout();

        return AjaxResult.success();
    }
}
