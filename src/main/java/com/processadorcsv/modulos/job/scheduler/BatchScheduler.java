package com.processadorcsv.modulos.job.scheduler;

import com.processadorcsv.modulos.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchScheduler {

    @Autowired
    private JobService jobService;

    @Scheduled(cron = "${app-config.job.scheduler.every-hour}")
    public void schedule() {
        log.info("Iniciando o scheduler de job...");
        jobService.triggerJob();
        log.info("Finalizando o scheduler de job...");
    }
}
