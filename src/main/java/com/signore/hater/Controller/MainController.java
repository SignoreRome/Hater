package com.signore.hater.Controller;

import com.signore.hater.Entity.Message;
import com.signore.hater.Entity.User;
import com.signore.hater.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MessageService messageService;

    @Autowired
    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String,Object> model){
        List<Message> messages = messageService.findAll();
        model.put("messages",messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String,Object> model){
        Message message = new Message(text, tag, user);
        messageService.save(message);
        List<Message> messages = messageService.findAll();
        model.put("messages", messages);
        return "/main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        List<Message> messages;
        if (filter!=null&&!filter.isEmpty()){
            messages = messageService.findByTag(filter);
        } else {
            messages = messageService.findAll();
        }

        model.put("messages",messages);
        return "main";
    }
}
