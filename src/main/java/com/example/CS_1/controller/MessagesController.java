package com.example.CS_1.controller;

import com.example.CS_1.entity.Message;
import com.example.CS_1.entity.User;
import com.example.CS_1.service.MessageService;
import com.example.CS_1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MessagesController {

    private final MessageService messageService;
    private final UserService userService;

    public MessagesController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;

    }

    @GetMapping("/messages")
    public String showMessages(Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "messages";
    }

    @PostMapping("/messages/add")
    public String addMessage(@RequestParam String text, Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        System.out.println("user: " + user);
        if (user != null) {
            Message message = new Message();
            message.setText(text);
            message.setUser(user);
            messageService.save(message);
        }

        return "redirect:/messages";
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        Message message = messageService.findById(id);
        if (message != null && user.getUsername().equals(message.getUser().getUsername())) {
            messageService.delete(message);
        }
        return "redirect:/messages";
    }


    // Wyświetlanie formularza z uprawnieniami
    @GetMapping("/messages/permission/{id}")
    public String getMessagePermissions(@PathVariable Long id, Model model) {
        Message message = messageService.findById(id);
        System.out.println("message: " + message);
        if (message != null) {
            model.addAttribute("message", message);
            model.addAttribute("allUsers", userService.findAllUsers());
            return "message-permissions";
        }
        return "redirect:/messages";
    }

    // Dodawanie uprawnienia edycji
    @PostMapping("/messages/addPermission/{id}")
    public String addPermission(@PathVariable Long id, @RequestParam Long userId, Principal principal) {
        String username = principal.getName();
        User userPrincipal = userService.findUserByUsername(username);
        Message message = messageService.findById(id);
        User user = userService.findUserById(userId);
        System.out.println("user.getUsername(): " + user.getUsername() + " message.getUser().getUsername(): " + message.getUser().getUsername());
        if (message != null && user != null && userPrincipal.getUsername().equals(message.getUser().getUsername())) {
            message.add(user);
            messageService.save(message);
        }
        return "redirect:/messages/permission/{id}";
    }

    // Usuwanie uprawnienia edycji
    @PostMapping("/messages/removePermission/{id}")
    public String removePermission(@PathVariable Long id, @RequestParam Long userId, Principal principal) {
        String username = principal.getName();
        User userPrincipal = userService.findUserByUsername(username);
        Message message = messageService.findById(id);
        User user = userService.findUserById(userId);
        if (message != null && user != null && userPrincipal.getUsername().equals(message.getUser().getUsername())) {
            message.getAllowedUsers().remove(user);
            messageService.save(message);
        }
        return "redirect:/messages/permission/{id}";
    }
}

