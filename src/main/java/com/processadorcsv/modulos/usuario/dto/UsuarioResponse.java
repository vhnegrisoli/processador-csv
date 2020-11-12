package com.processadorcsv.modulos.usuario.dto;

import com.processadorcsv.config.auth.UserDetailsImpl;
import com.processadorcsv.modulos.usuario.enums.EPermissao;
import com.processadorcsv.modulos.usuario.model.Permissao;
import com.processadorcsv.modulos.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Integer id;
    private String nome;
    private String email;
    private List<String> permissoes;

    public static UsuarioResponse converterDe(Usuario usuario) {
        return UsuarioResponse
            .builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nome(usuario.getNome())
            .permissoes(usuario.getPermissoes()
                .stream()
                .map(Permissao::getRole)
                .map(EPermissao::name)
                .collect(Collectors.toList())
            )
            .build();
    }

    public static UsuarioResponse converterDe(UserDetailsImpl usuario) {
        return UsuarioResponse
            .builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nome(usuario.getNome())
            .permissoes(usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
            )
            .build();
    }
}
