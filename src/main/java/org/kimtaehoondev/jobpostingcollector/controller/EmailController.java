package org.kimtaehoondev.jobpostingcollector.controller;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRequestDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.SendAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.VerifyAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("emailDto", new EmailRequestDto());
        return "email/register-form";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("emailDto") EmailRegisterDto dto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/register-form";
        }
        emailService.register(dto);
        return "redirect:/";
    }

    @PostMapping("/auth/send")
    public String sendAuthCode(@Validated @ModelAttribute("emailDto") SendAuthCodeDto dto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/register-form";
        }
        emailService.sendAuthCode(dto.getEmail());
        return "redirect:/email/register";
    }

    @PostMapping("/auth/verify")
    public String verifyAuthCode(@Validated @ModelAttribute("emailDto") VerifyAuthCodeDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/register-form";
        }
        emailService.verifyAuthCode(dto.getEmail(), dto.getCode());
        return "redirect:/email/register";
    }

    @GetMapping("/delete")
    public String showDeleteForm(Model model) {
        model.addAttribute("emailDeleteDto", new EmailDeleteDto());
        return "email/delete-form";
    }

    @PostMapping("/delete")
    public String delete(@Validated @ModelAttribute("emailDeleteDto") EmailDeleteDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/delete-form";
        }
        emailService.delete(dto);
        return "redirect:/";
    }

}
