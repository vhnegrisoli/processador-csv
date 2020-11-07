package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.PersonagemHQ;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonagemRepository extends ElasticsearchRepository<PersonagemHQ, String> {

    List<PersonagemHQ> findAll();
}
