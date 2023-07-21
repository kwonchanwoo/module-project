package com.example.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = {"com.example.module", "com.example.module"})
//@EntityScan(basePackages = {"com.example.core.entity"})
//@EnableJpaRepositories(basePackages = {"com.example.module.repository"})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
//        System.out.println("sumTen(10) = " + sumTen(10));

    }

//    static int sumTen(int i) {
//        if (i <= 0) {
//            System.out.println("i = " + i);
//            return i;
//        } else {
//            System.out.println("ai = " + i);
//            val result = sumTen(i - 1) + i;
//            System.out.println("bi = " + i);
//            System.out.println("sumTen(i - 1) + i = " + result);
//            return result;
//        }
//
//    }
}
