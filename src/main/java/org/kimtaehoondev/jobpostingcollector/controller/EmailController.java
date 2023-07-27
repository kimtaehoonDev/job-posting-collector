package org.kimtaehoondev.jobpostingcollector.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.SendAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.VerifyAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public String register(@Valid @ModelAttribute EmailRegisterDto dto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/form";
        }

        emailService.register(dto);
        return "redirect:/";
    }
    @DeleteMapping
    public String delete(@Valid @ModelAttribute EmailDeleteDto dto,
                         BindingResult bindingResult) {
        emailService.delete(dto);
        return "redirect:/";
    }

    @PostMapping("/auth/send")
    public String sendAuthCode(@Valid @ModelAttribute SendAuthCodeDto dto,
                               BindingResult bindingResult) {
        emailService.sendAuthCode(dto.getEmail());
        return "redirect:/email";
    }

    @PostMapping("/auth/verify")
    public String verifyAuthCode(@Valid @ModelAttribute VerifyAuthCodeDto dto,
                                 BindingResult bindingResult) {
        emailService.verifyAuthCode(dto.getEmail(), dto.getCode());
        return "redirect:/email";
    }
}
