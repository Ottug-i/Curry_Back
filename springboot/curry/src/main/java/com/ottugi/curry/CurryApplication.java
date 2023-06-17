package com.ottugi.curry;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class CurryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurryApplication.class, args);
	}

}
