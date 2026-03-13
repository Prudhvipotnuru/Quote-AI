package com.prudhvi.open_ai.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prudhvi.open_ai.service.LlmClient;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ScriptController {
	@Autowired
	@Qualifier("sarvamClient")
	private LlmClient llmClient;

	@GetMapping(value="/generateScript", produces= "text/plain")
	public String generate(@RequestParam String mood) {
		return llmClient.generate(mood);
	}
	
	// Add for Thymeleaf
	@GetMapping("/")
	public void index(HttpServletResponse response) {
	    try {
			response.sendRedirect("/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
