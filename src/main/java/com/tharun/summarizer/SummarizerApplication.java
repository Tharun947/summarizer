package com.tharun.summarizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SummarizerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SummarizerApplication.class, args);
		
	}

}


