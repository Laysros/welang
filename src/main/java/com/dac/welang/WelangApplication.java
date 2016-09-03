package com.dac.welang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@SpringBootApplication
@EnableAsync
public class WelangApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelangApplication.class, args);
	}
}
