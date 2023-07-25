package org.kimtaehoondev.jobpostingcollector.controller;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRequestDto;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @GetMapping
    public String showManagementForm() {
        return "email/form";
    }

    @PostMapping
    public String register(@ModelAttribute EmailRequestDto dto) {
        emailService.register(dto);
        return "redirect:/";
    }
    @DeleteMapping
    public String delete(@ModelAttribute EmailRequestDto dto) {
        emailService.delete(dto);
        return "redirect:/";
    }
}
