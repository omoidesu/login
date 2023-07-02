package com.omoi.controller;

import com.omoi.entity.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author omoi
 * @date 2023/7/2
 */
@RestController
public class TestController {
    // 测试JWT使用的接口
    @PostMapping("/test")
    public AjaxResult test(){
        return AjaxResult.success("hello world");
    }
}
