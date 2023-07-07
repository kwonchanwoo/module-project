package com.example.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = {"com.example.module", "com.example.module"})
//@EntityScan(basePackages = {"com.example.core.entity"})
//@EnableJpaRepositories(basePackages = {"com.example.module.repository"})
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

    }

}
