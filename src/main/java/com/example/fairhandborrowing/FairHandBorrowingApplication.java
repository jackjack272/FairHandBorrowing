package com.example.fairhandborrowing;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FairHandBorrowingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FairHandBorrowingApplication.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
