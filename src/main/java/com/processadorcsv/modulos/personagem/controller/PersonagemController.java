package com.processadorcsv.modulos.personagem.controller;

import com.processadorcsv.modulos.personagem.model.Personagem;
import com.processadorcsv.modulos.personagem.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/teste")
public class PersonagemController {

    @Autowired
    private PersonagemRepository repository;

    @GetMapping("index")
    public List<Personagem> buscarPersonagens() {
        repository.save(Personagem.builder().nome("Superman").build());
        return repository.findAll();
    }
}
