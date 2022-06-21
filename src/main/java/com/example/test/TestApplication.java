package com.example.test;

import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.test.service.SchedulerService;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	
	@Autowired
	private SchedulerService schedulerService;
	
	@PostConstruct
	private void init() {
		try {
			schedulerService.createJob();
			schedulerService.executeManually();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
