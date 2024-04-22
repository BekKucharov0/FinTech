package com.example.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinTechApplication.class, args);
    }

}
