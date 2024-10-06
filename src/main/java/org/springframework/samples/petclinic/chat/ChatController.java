package org.springframework.samples.petclinic.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

	private ChatClient chatClient;

	ChatController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@PostMapping("/chat")
	public String chat(@RequestBody String message) {
		LOGGER.debug("Chat {}", message);
		try {
			return chatClient
				.prompt()
				.user(message)
				.call()
				.content();
		} catch (RuntimeException exception) {
			LOGGER.error("Technical error while calling chat client", exception);
			return "Sorry, I am not able to help you with that.";
		}
	}
}
