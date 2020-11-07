package com.processadorcsv.modulos.usuario.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario {

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
    private Set<Permissao> permissoes;
}
