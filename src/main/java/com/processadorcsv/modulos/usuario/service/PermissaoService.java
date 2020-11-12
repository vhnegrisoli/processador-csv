package com.processadorcsv.modulos.usuario.service;

import com.processadorcsv.modulos.usuario.model.Permissao;
import com.processadorcsv.modulos.usuario.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> buscarPermissoesPorId(List<Permissao> permissoes) {
        return permissaoRepository.findByIdIn(permissoes
            .stream()
            .map(Permissao::getId)
            .collect(Collectors.toList()));
    }
}
