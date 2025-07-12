package ma.enset.aigen.controllers;

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
public class AiAgentController {

    private ChatClient chatClient;


    public AiAgentController(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    //Zéro shot prompt
    @GetMapping("/chat")
    public String askLLM(String query) {

        List<Message> examples = List.of(
                new UserMessage("6+4"),
                new AssistantMessage("Le résultat est : 10")
        );

        return chatClient.prompt()
                .system("Répond tjr les reponses en majuscules")
                .messages(examples)
                .user(query)
                .call()
                .content();
    }
}
