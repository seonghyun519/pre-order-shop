package com.sparklep.preorder;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.TimeZone;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)// 스프링 시큐리티 제외 해제
@SpringBootApplication
public class PreOrderShopApplication {
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(PreOrderShopApplication.class, args);
    }

}
