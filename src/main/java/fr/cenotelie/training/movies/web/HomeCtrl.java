package fr.cenotelie.training.movies.web;

import fr.cenotelie.training.movies.dao.MessagesDAO;
import fr.cenotelie.training.movies.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeCtrl {

    @Autowired
    private MessagesDAO dao;

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("messages", dao.getMessages());
        return "index";
    }

    @PostMapping
    public String createMessage(@ModelAttribute Message message) {
        log.info("adding new message with title " + message.getTitle());
        dao.addMessage(message);
        return "redirect:/";
    }
}
