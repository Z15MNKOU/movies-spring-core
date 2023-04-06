package fr.cenotelie.training.movies.web;

import fr.cenotelie.training.movies.dto.Message;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultModelAttributeCtrl {

    @ModelAttribute("newMessage")
    public Message getDefaultMessage() {
        return new Message();
    }
}
