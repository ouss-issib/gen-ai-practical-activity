package ma.enset.aigen.controllers;

import ma.enset.aigen.outputs.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.print.attribute.standard.Media;
import java.awt.*;

@RestController
public class StreamingController {

    private ChatClient chatClient;


    public StreamingController(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(String query) {
        return chatClient.prompt()
                .user(query)
                .stream()
                .content();

    }
    @GetMapping("/nostream")
    public String nostream(String query) {

        return chatClient.prompt()
                .user(query)
                .call()
                .content();

    }
}
