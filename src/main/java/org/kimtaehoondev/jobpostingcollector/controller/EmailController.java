package org.kimtaehoondev.jobpostingcollector.controller;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.SendAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.VerifyAuthCodeDto;
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
    public String register(@ModelAttribute EmailRegisterDto dto) {
        emailService.register(dto);
        return "redirect:/";
    }
    @DeleteMapping
    public String delete(@ModelAttribute EmailDeleteDto dto) {
        emailService.delete(dto);
        return "redirect:/";
    }

    @PostMapping("/auth/send")
    public String sendAuthCode(@ModelAttribute SendAuthCodeDto dto) {
        emailService.sendAuthCode(dto.getEmail());
        return "redirect:/email";
    }

    @PostMapping("/auth/verify")
    public String verifyAuthCode(@ModelAttribute VerifyAuthCodeDto dto) {
        emailService.verifyAuthCode(dto.getEmail(), dto.getCode());
        return "redirect:/email";
    }
}
