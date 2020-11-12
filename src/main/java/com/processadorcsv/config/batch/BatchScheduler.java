package com.processadorcsv.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchScheduler {

    @Autowired
    private JobService jobService;

    @Scheduled(cron = "1 * * * * *")
    public void schedule() {
        log.info("Iniciando o scheduler de job...");
        jobService.triggerJob();
        log.info("Finalizando o scheduler de job...");
    }
}
