package com.omoi.service;

import com.omoi.entity.Certificate;
import com.omoi.entity.LoginUser;
import com.omoi.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author omoi
* @description 针对表【user】的数据库操作Service
* @createDate 2023-07-02 05:39:35
*/
public interface IUserService extends IService<User> {

    boolean register(String id, User user);

    LoginUser login(String id, User user);

    Map<String, String> getJwtData(LoginUser user);

    Certificate generateCertificate();

    void logout();
}
