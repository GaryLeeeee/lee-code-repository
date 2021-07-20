package com.garylee.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//自动为mapper文件夹下的接口类生成实现类(等同于直接@Mapper)
@MapperScan("com.garylee.repository.**.mapper")
public class LeeDbCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeeDbCodeApplication.class, args);
    }

}
