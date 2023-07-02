package com.omoi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.omoi.entity.LoginUser;
import com.omoi.entity.User;
import com.omoi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(wrapper);

        if (ObjectUtil.isNull(user)){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return new LoginUser(user);
    }
}
