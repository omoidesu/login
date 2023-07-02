package com.omoi.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.omoi.entity.AjaxResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 403
        response.setStatus(HttpStatus.HTTP_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        AjaxResult errorResult = AjaxResult.error("权限不足");
        response.getWriter().write(JSONUtil.toJsonStr(errorResult));
    }
}
