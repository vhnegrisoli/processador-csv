package com.processadorcsv.modulos.personagem.repository;

import com.processadorcsv.modulos.personagem.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {

    Long countByNome(String nome);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM Personagem p WHERE p.nome = :nome")
    void deleteByName(String nome);
}
