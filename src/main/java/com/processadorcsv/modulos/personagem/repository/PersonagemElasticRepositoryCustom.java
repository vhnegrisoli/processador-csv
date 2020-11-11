package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.PersonagemElastic;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.List;

public interface PersonagemElasticRepositoryCustom {

    List<PersonagemElastic> findByNome(NativeSearchQuery nome);
}
