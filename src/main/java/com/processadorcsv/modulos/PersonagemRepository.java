package com.processadorcsv.modulos;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonagemRepository extends ElasticsearchRepository<PersonagemHQ, String> {

    List<PersonagemHQ> findAll();
}
