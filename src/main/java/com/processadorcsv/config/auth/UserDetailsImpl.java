package com.processadorcsv.config.auth;

import com.processadorcsv.modulos.usuario.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsImpl extends User {

    @Getter
    private Integer id;
    @Getter
    private String nome;
    @Getter
    private String email;

    public UserDetailsImpl(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this(usuario,
            true,
            true,
            true,
            true,
            authorities);
    }

    public UserDetailsImpl(Usuario usuario,
                           boolean enabled,
                           boolean accountNonExpired,
                           boolean credentialsNonExpired,
                           boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(),
            usuario.getSenha(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities);
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }
}
