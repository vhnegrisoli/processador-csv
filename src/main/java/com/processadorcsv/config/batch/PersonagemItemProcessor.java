package com.processadorcsv.config.batch;

import com.processadorcsv.modulos.personagem.dto.PersonagemCsvDTO;
import com.processadorcsv.modulos.personagem.model.Personagem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonagemItemProcessor implements ItemProcessor<PersonagemCsvDTO, Personagem> {

    @Override
    public Personagem process(final PersonagemCsvDTO personagemCsvDTO) {
        log.info("Iniciando Processor.");
        log.info("Iniciando processamento do CSV...");
        if (isLinhaValida(personagemCsvDTO)) {
            log.info("Processamento finalizado com sucesso.");
            return Personagem.converterDe(personagemCsvDTO);
        }
        log.info("Gerando arquivo vazio devido à existência de linha inválida.");
        return Personagem.gerarPadrao();
    }

    private boolean isLinhaValida(PersonagemCsvDTO personagemCsvDTO) {
        try {
            Integer.parseInt(personagemCsvDTO.getYear());
            return true;
        } catch (Exception ex) {
            log.error("Linha inválida para o campo ano: ".concat(personagemCsvDTO.getYear()));
            return false;
        }
    }
}
