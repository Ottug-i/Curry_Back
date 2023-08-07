package com.ottugi.curry;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@EnableCaching
@EnableBatchProcessing
@SpringBootApplication
public class CurryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurryApplication.class, args);
	}
}
