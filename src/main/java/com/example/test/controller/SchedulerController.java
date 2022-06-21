package com.example.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.service.SchedulerService;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

	@Autowired
	private SchedulerService schedulerService;
	
	@GetMapping("/execute")
	public Map<String, Object> executeManually() {
		try {
			schedulerService.executeManually();
			return toResposne("ok", "job execute manually successful");
		} catch (SchedulerException e) {
			e.printStackTrace();
			return toResposne("error", e.getMessage());
		}
	}
	
	@PostMapping("/reschedule")
	public Map<String, Object> rescheduleJob(@RequestParam String cron) {
		try {
			schedulerService.rescheduleJob(cron);
			return toResposne("ok", "reschedule job with cron successful");
		} catch (SchedulerException e) {
			e.printStackTrace();
			return toResposne("error", e.getMessage());
		}
	}
	
	private Map<String, Object> toResposne(String status, String message){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		return response;
	}
}
