package org.kimtaehoondev.jpcollector.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jpcollector.jobposting.dto.response.JobPostingResponseDto;
import org.kimtaehoondev.jpcollector.jobposting.service.JobPostingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final JobPostingServiceImpl jobPostingService;
    @GetMapping("/")
    String index(Model model) {
        List<JobPostingResponseDto> postings = jobPostingService.findAll();
        model.addAttribute("postings", postings);
        return "view/index";
    }
}
