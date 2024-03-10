package com.ottugi.curry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CurryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurryApplication.class, args);
    }
}
