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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.processadorcsv.modulos.personagem.utils.PersonagemConstantes.PADRAO;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;
    private final PersonagemRepository personagemRepository;
    private final PersonagemElasticRepository personagemElasticRepository;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate JdbcTemplate,
                                             PersonagemRepository personagemRepository,
                                             PersonagemElasticRepository personagemElasticRepository) {
        this.jdbcTemplate = JdbcTemplate;
        this.personagemRepository = personagemRepository;
        this.personagemElasticRepository = personagemElasticRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("O processamento foi finalizado com sucesso."
                .concat("\nForma inseridos ")
                .concat(String.valueOf(personagemRepository.count()))
                .concat(" itens.")
            );
            inserirDadosNoElasticSearch();
            removerPadrao();
        } else {
            log.info("Erro ao processar job.");
        }
    }

    private void inserirDadosNoElasticSearch() {
        log.info("Inserindo dados no ElasticSearch.");
        var dados = personagemRepository.findAll()
            .stream()
            .filter(this::isDiferenteDePadrao)
            .map(PersonagemElastic::converterDe)
            .collect(Collectors.toList());
        if (!isEmpty(dados)) {
            personagemElasticRepository.saveAll(dados);
            log.info("Dados inseridos com sucesso!");
        } else {
            log.info("Não foram encontrados dados para inserir no ElasticSearch.");
        }
    }

    private boolean isDiferenteDePadrao(Personagem personagem) {
        return !isEmpty(personagem.getNome()) && !personagem.getNome().equals(PADRAO);
    }

    private void removerPadrao() {
        log.info("Foi encontrado um total de "
        .concat(String.valueOf(personagemRepository.countByNome(PADRAO)))
        .concat(" registros com valores PADRAO. Removendo..."));
        jdbcTemplate.execute("DELETE FROM personagem WHERE nome = '".concat(PADRAO).concat("'"));
        log.info("Dados do tipo PADRAO foram removidos com sucesso!");
    }
}