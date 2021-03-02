package com.signore.hater.Controller;

import com.signore.hater.Entity.Message;
import com.signore.hater.Entity.User;
import com.signore.hater.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    private final MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String greeting() {
        return "/greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        List<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageService.findByTag(filter);
        } else {
            messages = messageService.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);

        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                message.setFilename(resultFilename);
            }
            model.addAttribute("message", null);

            messageService.save(message);
        }

        List<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }
}
