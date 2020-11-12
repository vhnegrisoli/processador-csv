package com.processadorcsv.modulos.job.controller;

import com.processadorcsv.modulos.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("personagem/iniciar")
    public void iniciarJobPersonagens() {
        jobService.triggerJob();
    }
}
