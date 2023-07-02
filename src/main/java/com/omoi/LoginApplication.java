package com.omoi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author omoi
 * @date 2023/7/2
 */
@SpringBootApplication
@MapperScan("com.omoi.mapper")
public class LoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
        System.out.println("启动成功！");
    }
}
