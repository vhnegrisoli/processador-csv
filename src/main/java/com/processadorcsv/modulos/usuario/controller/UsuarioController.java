package com.processadorcsv.modulos.usuario.controller;

import com.processadorcsv.modulos.usuario.dto.UsuarioRequest;
import com.processadorcsv.modulos.usuario.dto.UsuarioResponse;
import com.processadorcsv.modulos.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("public")
    public UsuarioResponse salvarUsuario(@RequestBody UsuarioRequest request) {
        return usuarioService.salvarUsuario(request);
    }

    @PutMapping("{id}")
    public UsuarioResponse atualizarUsuario(@PathVariable Integer id,
                                         @RequestBody UsuarioRequest request) {
        return usuarioService.atualizarUsuario(request, id);
    }

    @GetMapping("autenticado")
    public UsuarioResponse buscarUsuarioAutenticado() {
        return usuarioService.buscarUsuarioAutenticado();
    }
}
