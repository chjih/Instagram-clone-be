package com.example.InstagramCloneCoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InstagramCloneCodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramCloneCodingApplication.class, args);
	}

}
