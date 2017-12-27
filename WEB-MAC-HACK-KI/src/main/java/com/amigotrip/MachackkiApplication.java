package com.amigotrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class},  // basePackageClasses에 지정
		basePackages = {"com.amigotrip.domain"})
public class MachackkiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MachackkiApplication.class, args);
	}
}
