package com.example.fitnessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.fitnessapp")
public class InasAnesLab1Application {

	public static void main(String[] args) {
		SpringApplication.run(InasAnesLab1Application.class, args);
	}

}
