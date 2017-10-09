package hubomoa.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class HubomoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubomoaApplication.class, args);
	}
}
