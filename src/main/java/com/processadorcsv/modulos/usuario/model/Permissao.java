package com.processadorcsv.modulos.usuario.model;

import com.processadorcsv.modulos.usuario.enums.EPermissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERMISSAO")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRICAO", nullable = false, unique = true, length = 120)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, unique = true, length = 120)
    private EPermissao role;

    public Permissao(Integer id) {
        this.id = id;
    }
}
