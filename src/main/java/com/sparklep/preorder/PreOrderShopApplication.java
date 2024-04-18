package com.sparklep.preorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PreOrderShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreOrderShopApplication.class, args);
    }

}
