package com.processadorcsv.modulos.usuario.repository;

import com.processadorcsv.modulos.usuario.model.Permissao;
import com.processadorcsv.modulos.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {

    List<Permissao> findByIdIn(List<Integer> ids);
}
