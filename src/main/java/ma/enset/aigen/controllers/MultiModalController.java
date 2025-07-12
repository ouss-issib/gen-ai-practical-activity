package ma.enset.aigen.controllers;

import ma.enset.aigen.outputs.CniModal;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiModalController {
    private ChatClient chatClient;

    @Value("classpath:/images/cni.jpg")
    private Resource image;
    public MultiModalController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/describe")
    public CniModal describeImage() {
        return chatClient.prompt()
                .system("Donne moi une description de l'image fournie")
                .user(u->
                        u.text("Décrire cette image")
                                .media(MediaType.IMAGE_JPEG,image))
                .call()
                .entity(CniModal.class);

    }

}
