package org.kimtaehoondev.jobpostingcollector.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.EmailRequestDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.SendAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.dto.request.VerifyAuthCodeDto;
import org.kimtaehoondev.jobpostingcollector.email.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public ResponseEntity<List<String>> sendAuthCode(@Validated @RequestBody SendAuthCodeDto dto,
                                               BindingResult bindingResult) {
        List<FieldError> emailErrors = bindingResult.getFieldErrors("email");

        if (!emailErrors.isEmpty()) {
            List<String> errorMessages = emailErrors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        emailService.sendAuthCode(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/verify")
    @ResponseBody
    public ResponseEntity<List<String>> verifyAuthCode(@Validated @RequestBody VerifyAuthCodeDto dto,
                                 BindingResult bindingResult) {
        List<FieldError> emailErrors = bindingResult.getFieldErrors();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = emailErrors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            emailService.verifyAuthCode(dto.getEmail(), dto.getCode());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        }

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
