package com.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.demo.controller","com.demo.bean", "com.demo.database"})
public class Java3Assignment3Application {

	public static void main(String[] args) {
		SpringApplication.run(Java3Assignment3Application.class, args);
	}

}