package org.springframework.samples.petclinic.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
class ChatConfig {

	@Value("classpath:/prompts/system.st")
	private Resource systemResource;

	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
		// @formatter:off
		return chatClientBuilder
			.defaultAdvisors(
				new SimpleLoggerAdvisor(),
				new PromptChatMemoryAdvisor(chatMemory)
			)
			.defaultSystem(systemResource)
			.build();
		// @formatter:on
	}

	@Bean
	ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

}
