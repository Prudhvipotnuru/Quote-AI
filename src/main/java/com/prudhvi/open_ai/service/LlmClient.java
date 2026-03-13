package com.prudhvi.open_ai.service;

import org.springframework.stereotype.Component;

@Component
public interface LlmClient {
	public String generate(String mood);
}
