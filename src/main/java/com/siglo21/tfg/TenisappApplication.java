package com.siglo21.tfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TenisappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenisappApplication.class, args);
	}

}
