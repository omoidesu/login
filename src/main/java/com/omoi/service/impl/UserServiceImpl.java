package com.omoi.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omoi.entity.Certificate;
import com.omoi.entity.LoginUser;
import com.omoi.entity.User;
import com.omoi.exception.ServiceException;
import com.omoi.request.UserRequest;
import com.omoi.service.IUserService;
import com.omoi.mapper.UserMapper;
import com.omoi.util.RsaUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author omoi
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-07-02 05:39:35
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService {
    private final AuthenticationManager authenticationManager;

    private static final byte[] SECRET = "secret".getBytes();
    public static final UserHolder holder = new UserHolder();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean register(String id, User user) {
        if (!Validator.isEmail(user.getEmail())){
            throw new ServiceException("邮箱格式不正确");
        }

        if (!Validator.isMobile(user.getTel())){
            throw new ServiceException("手机号格式不正确");
        }

        String passwordEncrypt = user.getPassword();
        String password = RsaUtil.decrypt(id, passwordEncrypt);
        String encodePassword = encoder.encode(password);
        user.setPassword(encodePassword);

        return save(user);
    }

    @Override
    public LoginUser login(String id, User user) {
        String passwordEncrypt = user.getPassword();
        String password = RsaUtil.decrypt(id, passwordEncrypt);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (ObjectUtil.isNull(authenticate)){
            throw new ServiceException("用户名或密码错误");
        }

        return (LoginUser) authenticate.getPrincipal();
    }

    @Override
    public Map<String, String> getJwtData(LoginUser loginUser) {
        User user = loginUser.getUser();

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("expire", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);

        String token = JWTUtil.createToken(payload, SECRET);

        holder.getUserMap().put(user.getId(), loginUser);

        HashMap<String, String> result = new HashMap<>();
        result.put("code", token);
        return result;
    }

    @Override
    public Certificate generateCertificate() {
        String id = RandomUtil.randomString(16);
        String salt = RandomUtil.randomString(16);
        String publicKey = RsaUtil.getPublicKey(id, salt);

        return new Certificate(id, publicKey, salt);
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        holder.getUserMap().remove(principal.getUser().getId());
    }

    /**
     * 用于存储用户信息
     * 实际场景可以换用redis
     */
    @Data
    public static class UserHolder{
        private Map<Long, LoginUser> userMap = new HashMap<>();
    }
}




