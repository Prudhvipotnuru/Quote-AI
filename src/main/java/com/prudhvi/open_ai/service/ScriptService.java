package com.prudhvi.open_ai.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

@Service
public class ScriptService {

	Logger logger;

	private final ChatModel chatmodel;
	
	@Value("${app.quote.prompt-template}")
	private String promptTemplate;
	
	@Value("${app.model}")
	private String model;
	
	@Value("${app.max-tokens}")
	private Integer maxTokens;

	public ScriptService(ChatModel chatmodel) {
		this.chatmodel = chatmodel;
	}

	public String generate(String mood) {
		
		String prompt=promptTemplate+mood;

		ChatResponse chatResponse = chatmodel
				.call(new Prompt(prompt, OpenAiChatOptions.builder()
													      .model(model)
														  .maxTokens(maxTokens).build()));

		return chatResponse.getResult().getOutput().getText();
	}

}
