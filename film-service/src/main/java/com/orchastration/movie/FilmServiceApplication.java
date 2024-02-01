package com.orchastration.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class FilmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmServiceApplication.class, args);
	}

}
