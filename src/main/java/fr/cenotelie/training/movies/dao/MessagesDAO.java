package fr.cenotelie.training.movies.dao;

import fr.cenotelie.training.movies.dto.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessagesDAO {

    private List<Message> messages;

    @PostConstruct
    public void init() {
        messages = new ArrayList<>();
        messages.add(Message.builder().title("message 1").content("my first message").build());
        messages.add(Message.builder().title("message 2").content("my second message").build());
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message addMessage(Message message) {
        messages.add(message);
        return message;
    }

}
