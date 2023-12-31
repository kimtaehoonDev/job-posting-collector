package org.kimtaehoondev.jpcollector.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.email.auth.generator.AuthCodeGenerator;
import org.kimtaehoondev.jpcollector.exception.impl.EmailNotFoundException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailPasswordInvalidException;
import org.kimtaehoondev.jpcollector.exception.impl.EmailUnAuthorizedException;
import org.kimtaehoondev.jpcollector.web.dto.request.EmailDeleteDto;
import org.kimtaehoondev.jpcollector.web.dto.request.EmailRegisterDto;
import org.kimtaehoondev.jpcollector.web.dto.request.SendAuthCodeDto;
import org.kimtaehoondev.jpcollector.web.dto.request.VerifyAuthCodeDto;
import org.kimtaehoondev.jpcollector.email.service.EmailManagementService;
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
    private final EmailManagementService emailManagementService;
    private final AuthCodeGenerator authCodeGenerator;


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("emailDto", new EmailRegisterDto());
        return "view/email/register-form";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("emailDto") EmailRegisterDto dto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view/email/register-form";
        }
        try {
            emailManagementService.register(dto);
            return "redirect:/";
        } catch (EmailUnAuthorizedException e) {
            bindingResult.addError(new FieldError("emailDto","pwd", e.getMessage()));
            return "view/email/register-form";
        }
    }

    @GetMapping("/delete")
    public String showDeleteForm(Model model) {
        model.addAttribute("emailDto", new EmailDeleteDto());
        return "view/email/delete-form";
    }

    @PostMapping("/delete")
    public String delete(@Validated @ModelAttribute("emailDto") EmailDeleteDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view/email/delete-form";
        }
        try {
            emailManagementService.delete(dto);
            return "redirect:/";
        } catch (EmailPasswordInvalidException e) {
            bindingResult.addError(new FieldError("emailDto","pwd", e.getMessage()));
            return "view/email/delete-form";
        } catch (EmailNotFoundException e) {
            bindingResult.addError(new FieldError("emailDto","email", e.getMessage()));
            return "view/email/delete-form";
        }
    }


    @PostMapping("/auth/send")
    @ResponseBody
    public ResponseEntity<List<String>> sendAuthCode(@Validated @RequestBody SendAuthCodeDto dto,
                                                     BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        if (!errors.isEmpty()) {
            List<String> errorMessages = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        emailManagementService.sendAuthCode(dto.getEmail(), authCodeGenerator.generate());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/verify")
    @ResponseBody
    public ResponseEntity<List<String>> verifyAuthCode(@Validated @RequestBody VerifyAuthCodeDto dto,
                                                       BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            emailManagementService.verifyAuthCode(dto.getEmail(), dto.getCode());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        }

    }
}
