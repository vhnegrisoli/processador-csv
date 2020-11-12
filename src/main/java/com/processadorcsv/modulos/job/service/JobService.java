package com.processadorcsv.modulos.job.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JobService {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job importarPersonagemJob;

    public void triggerJob() {
        log.info("Iniciando o job importarPersonagemJob...");
        try {
            jobLauncher.run(importarPersonagemJob, new JobParametersBuilder().toJobParameters());
            log.info("Finalizando o job importarPersonagemJob com sucesso!");
        } catch (Exception ex) {
            log.error("Erro ao executar o job importarPersonagemJob. Erro: ", ex);
        }
    }
}
