package com.omoi.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.omoi.entity.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 401
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        AjaxResult errorResult = AjaxResult.error("请先登录");
        response.getWriter().write(JSONUtil.toJsonStr(errorResult));
    }
}
