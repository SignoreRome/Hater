package com.signore.hater.Controller;

import com.signore.hater.Entity.Message;
import com.signore.hater.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String add(@RequestParam String text,
                      @RequestParam String tag){
        Message message = new Message(text, tag);
        messageService.save(message);
        return "redirect:/";
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
