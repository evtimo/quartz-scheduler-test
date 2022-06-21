package com.example.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.test.bean.ExchangeFilter;
import com.example.test.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.service.RateService;

@RestController
@RequestMapping("/test")
public class ExchangeController {

	@Autowired
	private RateService gradeService;
	
	@GetMapping("/exchange")
	public Rate findRate(ExchangeFilter filter){
		return gradeService.findByFiltered(filter); 
	}
	
	private Map<String, Object> toResposne(String status, String message){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		return response;
	}
}
