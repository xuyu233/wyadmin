package com.wy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wy.mapper")
public class WyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyApplication.class, args);
    }

}
