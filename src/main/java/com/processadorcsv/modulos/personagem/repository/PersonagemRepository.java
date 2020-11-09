package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {

    List<Personagem> findByNome(String nome);
}
