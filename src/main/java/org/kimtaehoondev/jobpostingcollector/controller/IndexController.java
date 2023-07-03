package org.kimtaehoondev.jobpostingcollector.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.service.JobPostingServiceImpl;
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
        return "index";
    }
}
