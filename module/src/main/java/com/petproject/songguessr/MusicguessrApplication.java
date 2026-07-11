package com.petproject.songguessr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MusicguessrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicguessrApplication.class, args);
	}
}
