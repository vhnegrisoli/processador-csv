package com.processadorcsv.modulos.processamento.model;

import com.processadorcsv.modulos.processamento.enums.EStatusProcessamento;
import com.processadorcsv.modulos.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARQUIVO_PROCESSAMENTO")
public class ArquivoProcessamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NOME_ARQUIVO", length = 50, nullable = false)
    private String nomeArquivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_PROCESSAMENTO", length = 8, nullable = false)
    private EStatusProcessamento statusProcessamento;

    @Column(name = "FALHA_DESCRICAO", length = 2000)
    private String falhaDescricao;

    @ManyToOne
    @JoinColumn(name = "FK_USUARIO", nullable = false)
    private Usuario usuario;
}
