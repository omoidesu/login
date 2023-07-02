package com.omoi.handler;

import com.omoi.entity.AjaxResult;
import com.omoi.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author omoi
 * @date 2023/7/2
 */
@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public AjaxResult handler(ServiceException e){
        return AjaxResult.error(e.getMessage());
    }
}
