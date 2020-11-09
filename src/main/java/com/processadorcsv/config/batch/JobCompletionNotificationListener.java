package com.processadorcsv.config.batch;

import com.processadorcsv.modulos.personagem.model.Personagem;
import com.processadorcsv.modulos.personagem.model.PersonagemElastic;
import com.processadorcsv.modulos.personagem.repository.PersonagemElasticRepository;
import com.processadorcsv.modulos.personagem.repository.PersonagemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final PersonagemRepository personagemRepository;
    private final PersonagemElasticRepository personagemElasticRepository;

    @Autowired
    public JobCompletionNotificationListener(PersonagemRepository personagemRepository,
                                             PersonagemElasticRepository personagemElasticRepository) {
        this.personagemRepository = personagemRepository;
        this.personagemElasticRepository = personagemElasticRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            removerPadrao();
            log.info("O processamento foi finalizado com sucesso."
                .concat("\nForma inseridos ")
                .concat(String.valueOf(personagemRepository.count()))
                .concat(" itens.")
            );
            inserirDadosNoElasticSearch();
        } else {
            log.info("Erro ao processar job.");
        }
    }

    private void inserirDadosNoElasticSearch() {
        log.info("Inserindo dados no ElasticSearch.");
        var dados = personagemRepository.findAll()
            .stream()
            .map(PersonagemElastic::converterDe)
            .collect(Collectors.toList());
        if (!isEmpty(dados)) {
            personagemElasticRepository.saveAll(dados);
            log.info("Dados inseridos com sucesso!");
        } else {
            log.info("Não foram encontrados dados para inserir no ElasticSearch.");
        }
    }

    @Transactional
    private void removerPadrao() {
        personagemRepository.findByNome("Padrão")
            .forEach(personagemRepository::delete);
    }
}