package com.processadorcsv.modulos.usuario.model;

import com.processadorcsv.modulos.usuario.dto.UsuarioRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario {

    private static final Integer ID_PERMISSAO_VISITANTE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NOME", nullable = false, length = 120)
    private String nome;

    @Column(name = "EMAIL", nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "SENHA", nullable = false, length = 120)
    private String senha;

    @NotNull
    @JoinTable(name = "USUARIO_PERMISSAO", joinColumns = {
        @JoinColumn(name = "FK_USUARIO", foreignKey = @ForeignKey(name = "FK_USUARIO_PK"),
            referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PERMISSAO", foreignKey = @ForeignKey(name = "FK_PERMISSAO_PK"),
            referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permissao> permissoes;

    @PrePersist
    public void definirPermissaoVisitanteAntesDePersistir() {
        permissoes = List.of(new Permissao(ID_PERMISSAO_VISITANTE));
    }

    public static Usuario converterDe(UsuarioRequest request, String senhaCriptografada) {
        return Usuario
            .builder()
            .nome(request.getNome())
            .email(request.getEmail())
            .senha(senhaCriptografada)
            .build();
    }
}
