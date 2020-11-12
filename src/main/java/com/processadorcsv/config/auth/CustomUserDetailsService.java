package com.processadorcsv.config.auth;

import com.processadorcsv.config.exception.ValidacaoException;
import com.processadorcsv.modulos.usuario.model.Usuario;
import com.processadorcsv.modulos.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws ValidacaoException {
        return usuarioRepository.findByEmailIgnoreCase(email)
            .map(usuario -> new UserDetailsImpl(
                usuario, getPermissoes(usuario)) {
            })
            .orElseThrow(() -> new ValidacaoException("Usuário ou senha inválidos."));
    }

    private List<SimpleGrantedAuthority> getPermissoes(Usuario usuario) {
        return usuario
            .getPermissoes()
            .stream()
            .map(permissao -> "ROLE_" + permissao.getRole())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
