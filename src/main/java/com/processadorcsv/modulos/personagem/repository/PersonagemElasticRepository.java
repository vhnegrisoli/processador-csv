package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.PersonagemElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonagemElasticRepository extends ElasticsearchRepository<PersonagemElastic, String> {

    List<PersonagemElastic> findAll();
}
