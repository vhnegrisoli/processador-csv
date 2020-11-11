package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.PersonagemElastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonagemElasticRepositoryImpl implements PersonagemElasticRepositoryCustom {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private ElasticsearchOperations elasticsearchOperations;

    public List<PersonagemElastic> findByNome(NativeSearchQuery query) {
        return elasticsearchRestTemplate
            .search(query, PersonagemElastic.class)
            .getSearchHits()
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }
}
