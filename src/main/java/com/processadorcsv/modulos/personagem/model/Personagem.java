package com.processadorcsv.modulos.personagem.model;

import com.processadorcsv.modulos.personagem.dto.PersonagemCsvDTO;
import com.processadorcsv.modulos.processamento.enums.EMesPrimeiraAparicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERSONAGEM")
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "identidade")
    private String identidade;
    @Column(name = "alinhamento")
    private String alinhamento;
    @Column(name = "cor_olho")
    private String corOlho;
    @Column(name = "cor_cabelo")
    private String corCabelo;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "situacao_vida")
    private String situacaoVida;
    @Column(name = "total_aparicoes")
    private Long totalAparicoes;
    @Column(name = "primeira_aparicao")
    private String primeiraAparicao;
    @Column(name = "data_primeira_aparicao")
    private String dataPrimeiraAparicao;
    @Column(name = "ano_primeira_aparicao")
    private Integer anoPrimeiraAparicao;
    @Column(name = "mes_primeira_aparicao")
    private Integer mesPrimeiraAparicao;

    public static Personagem gerarPadrao() {
        return Personagem
            .builder()
            .nome("Padrão")
            .alinhamento("Padrão")
            .corOlho("Padrão")
            .corCabelo("Padrão")
            .sexo("Padrão")
            .situacaoVida("Padrão")
            .totalAparicoes(0L)
            .dataPrimeiraAparicao("2020-01")
            .anoPrimeiraAparicao(2020)
            .mesPrimeiraAparicao(1)
            .build();
    }

    public static Personagem converterDe(PersonagemCsvDTO dto) {
        var mes = recuperarMes(dto.getFirstAppearence());
        var ano = !isEmpty(dto.getYear()) ? Integer.parseInt(dto.getYear()) : null;
        var dataPrimeiraAparicao = recuperarDataPrimeiraAparicao(ano, mes);
        return Personagem
            .builder()
            .nome(dto.getName())
            .alinhamento(dto.getAlign())
            .corOlho(dto.getEye())
            .corCabelo(dto.getHair())
            .sexo(dto.getSex())
            .situacaoVida(dto.getAlive())
            .totalAparicoes(!isEmpty(dto.getAppearences())
                ? Long.parseLong(dto.getAppearences())
                : null)
            .dataPrimeiraAparicao(dataPrimeiraAparicao)
            .anoPrimeiraAparicao(ano)
            .mesPrimeiraAparicao(!isEmpty(mes) ? mes.getMes() : null)
            .build();
    }

    private static EMesPrimeiraAparicao recuperarMes(String dataPrimeiraAparicao) {
        return !isEmpty(dataPrimeiraAparicao)
            ? Stream.of(EMesPrimeiraAparicao.values())
            .filter(mes -> dataPrimeiraAparicao.contains(mes.getMesValor()))
            .findFirst()
            .orElse(null)
            : null;
    }

    private static String recuperarDataPrimeiraAparicao(Integer ano, EMesPrimeiraAparicao mes) {
        return !isEmpty(ano) && !isEmpty(mes)
            ? YearMonth.of(ano, mes.getMes()).format(DateTimeFormatter.ofPattern("yyyy-MM"))
            : null;
    }
}