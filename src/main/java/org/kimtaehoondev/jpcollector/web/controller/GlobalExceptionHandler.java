package org.kimtaehoondev.jpcollector.web.controller;

import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.config.AdminProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final AdminProperties properties;

    @ExceptionHandler(Exception.class)
    public String unexpected(Exception e, Model model) {
        model.addAttribute("adminEmail", properties.getEmail());
        return "view/error/500.html";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFound(Exception e) {
        return "view/error/404.html";
    }

}
