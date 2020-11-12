package com.processadorcsv.modulos.usuario.service;

import com.processadorcsv.config.auth.UserDetailsImpl;
import com.processadorcsv.config.exception.ValidacaoException;
import com.processadorcsv.modulos.usuario.dto.UsuarioRequest;
import com.processadorcsv.modulos.usuario.dto.UsuarioResponse;
import com.processadorcsv.modulos.usuario.model.Usuario;
import com.processadorcsv.modulos.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PermissaoService permissaoService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UsuarioResponse salvarUsuario(UsuarioRequest request) {
        validarDadosUsuario(request, null);
        var usuario = Usuario.converterDe(request, recuperarSenhaCriptografada(request.getSenha()));
        usuarioRepository.save(usuario);
        return UsuarioResponse.converterDe(buscarPorId(usuario.getId()));
    }

    public UsuarioResponse atualizarUsuario(UsuarioRequest request, Integer id) {
        validarDadosUsuario(request, id);
        var usuario = Usuario.converterDe(request, recuperarSenhaCriptografada(request.getSenha()));
        usuario.setId(id);
        usuarioRepository.save(usuario);
        return UsuarioResponse.converterDe(buscarPorId(usuario.getId()));
    }

    private String recuperarSenhaCriptografada(String senha) {
        return bCryptPasswordEncoder.encode(senha);
    }

    private void validarDadosUsuario(UsuarioRequest request, Integer id) {
        validarDadosVazios(request);
        validarIdExistente(id);
        validarEmailExistente(request, id);
    }

    private void validarDadosVazios(UsuarioRequest request) {
        if (isEmpty(request.getNome())
            || isEmpty(request.getEmail())
            || isEmpty(request.getSenha())) {
            throw new ValidacaoException("Todos os dados são obrigatórios.");
        }
    }

    private void validarIdExistente(Integer id) {
        if (!isEmpty(id) && !usuarioRepository.existsById(id)) {
            throw new ValidacaoException("Não existe um usuário para este ID.");
        }
    }

    private void validarEmailExistente(UsuarioRequest request, Integer id) {
        usuarioRepository.findByEmailIgnoreCase(request.getEmail())
            .ifPresent(usuario -> {
                if (isEmpty(id) || !usuario.getId().equals(id)) {
                    throw new ValidacaoException("O e-mail já existe.");
                }
            });
    }

    public UsuarioResponse buscarUsuarioAutenticado() {
        UserDetailsImpl usuarioAutenticado = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            if (principal instanceof UserDetailsImpl) {
                usuarioAutenticado = (UserDetailsImpl) principal;
            }
        } catch (Exception ex) {
            log.error("Erro ao recuperar usuário autenticado.", ex);
            throw new ValidacaoException("Usuário não autenticado.");
        }
        if (isEmpty(usuarioAutenticado)) {
            throw new ValidacaoException("Usuário não encontrado.");
        } else {
            return UsuarioResponse.converterDe(usuarioAutenticado);
        }
    }

    public Usuario buscarPorId(Integer id) {
        var usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ValidacaoException("Usuário não encontrado."));
        usuario.setPermissoes(permissaoService.buscarPermissoesPorId(usuario.getPermissoes()));
        return usuario;
    }
}
