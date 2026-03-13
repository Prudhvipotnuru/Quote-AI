package com.prudhvi.open_ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SarvamClient implements LlmClient{
	
	@Value("${app.sarvam.ai.token}")
    private String token;
	
	@Value("${app.quote.prompt-template}")
	private String promptTemplate;
	
	@Value("${app.sarvam.model}")
	private String model;
	
	@Value("${app.max-tokens}")
	private Integer maxTokens;
	
	private final WebClient webClient;
	
	public SarvamClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.sarvam.ai").build();
    }

	@Override
	public String generate(String mood) {
		String jsonBody = """
		{
		  "messages":[{"role":"user","content":"%s"}],
		  "model":"%s",
		  "max_tokens":"%s"
		}
		""".formatted(promptTemplate+mood,model,maxTokens);
		
		String responseBody = webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
		ObjectMapper mapper = new ObjectMapper();

		JsonNode root = null;
		try {
			root = mapper.readTree(responseBody);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   // responseBody = HTTP response as String
		
		String reply = root
		        .path("choices")
		        .get(0)
		        .path("message")
		        .path("content")
		        .asText();
		return reply;

	}

}
