package com.processadorcsv.config.batch;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BatchScheduler {

    private JobLauncher jobLauncher;
    private Job importarPersonagemJob;

    @Scheduled(cron = "0 * * * * *")
    public void schedule() {
        log.info("Iniciando o job importarPersonagemJob...");
        try {
            jobLauncher.run(importarPersonagemJob, new JobParametersBuilder().toJobParameters());
            log.info("Finalizando o job importarPersonagemJob com sucesso!");
        } catch (Exception ex) {
            log.error("Erro ao executar o job importarPersonagemJob. Erro: ", ex);
        }
    }
}
