package com.omoi.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.omoi.entity.LoginUser;
import com.omoi.exception.ServiceException;
import com.omoi.service.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        // 如果没有token直接放行
        if (CharSequenceUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果有token则根据token获取id
        JWT jwt = JWTUtil.parseToken(token);
        Long id = (Long) jwt.getPayload("id");

        if (ObjectUtil.isNull(id)){
            throw new ServiceException("token错误");
        }

        // 从缓存中获取用户信息
        LoginUser loginUser = UserServiceImpl.holder.getUserMap().get(id);
        if (ObjectUtil.isNull(loginUser)){
            throw new ServiceException("该用户未登录");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
