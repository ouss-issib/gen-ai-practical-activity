package ma.enset.aigen.controllers;

import ma.enset.aigen.outputs.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class AiGenStructuredController {
    private ChatClient chatClient;


    public AiGenStructuredController(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    //Zéro shot prompt
    @GetMapping("/askAgent")
    public MovieList askLLM(String query) {
        String systemMessage = """
                Vous êtes un spécialiste dans le domaine du cinéma
                répond à la question de l'utilisateur à ce propos
                """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(query)
                .call()
                .entity(MovieList.class);

    }
}
