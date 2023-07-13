package com.example.module;

import com.example.module.config.JpaConfig;
import com.example.module.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

//@SpringBootApplication(scanBasePackages = {"com.example.module", "com.example.module"})
//@EntityScan(basePackages = {"com.example.core.entity"})
//@EnableJpaRepositories(basePackages = {"com.example.module.repository"})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

    }

}
