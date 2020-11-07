package com.processadorcsv.modulos.personagem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "personagem_hq")
public class PersonagemHQ {

    @Id
    private String id;
    @Field(name = "nome", type = FieldType.Text, fielddata = true)
    private String nome;
    @Field(name = "identidade", type = FieldType.Text, fielddata = true)
    private String identidade;
    @Field(name = "alinhamento", type = FieldType.Text, fielddata = true)
    private String alinhamento;
    @Field(name = "corOlho", type = FieldType.Text, fielddata = true)
    private String corOlho;
    @Field(name = "corCabelo", type = FieldType.Text, fielddata = true)
    private String corCabelo;
    @Field(name = "sexo", type = FieldType.Text, fielddata = true)
    private String sexo;
    @Field(name = "situacaoVida", type = FieldType.Text, fielddata = true)
    private String situacaoVida;
    @Field(name = "totalAparicoes", type = FieldType.Long)
    private Long totalAparicoes;
    @Field(name = "primeiraAparicao", type = FieldType.Text, fielddata = true)
    private String primeiraAparicao;
    @Field(name = "dataPrimeiraAparicao", type = FieldType.Date, format = DateFormat.year_month, fielddata = true)
    private String dataPrimeiraAparicao;
    @Field(name = "anoPrimeiraAparicao", type = FieldType.Long)
    private String anoPrimeiraAparicao;
    @Field(name = "mesPrimeiraAparicao", type = FieldType.Long)
    private String mesPrimeiraAparicao;
}
