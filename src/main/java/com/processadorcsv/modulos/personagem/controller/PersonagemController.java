package com.processadorcsv.modulos.personagem.controller;

import com.processadorcsv.modulos.personagem.model.PersonagemElastic;
import com.processadorcsv.modulos.personagem.predicate.PersonagemElasticPredicate;
import com.processadorcsv.modulos.personagem.repository.PersonagemElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/personagem")
public class PersonagemController {

    @Autowired
    private PersonagemElasticRepository repository;

    @GetMapping("nome/{nome}")
    public List<PersonagemElastic> buscarPersonagens(@PathVariable String nome) {
        return repository.findByNome(
            new PersonagemElasticPredicate()
                .comNome(nome)
                .build()
        );
    }
}
