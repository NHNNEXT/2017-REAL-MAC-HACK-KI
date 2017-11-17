package com.amigotrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MachackkiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachackkiApplication.class, args);
	}
}
