package com.processadorcsv.modulos.personagem.model;

import com.processadorcsv.modulos.personagem.dto.PersonagemCsvDTO;
import com.processadorcsv.modulos.processamento.enums.EMesPrimeiraAparicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.YearMonth;
import java.util.stream.Stream;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "personagem")
public class PersonagemElastic {

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
    private YearMonth dataPrimeiraAparicao;
    @Field(name = "anoPrimeiraAparicao", type = FieldType.Integer)
    private Integer anoPrimeiraAparicao;
    @Field(name = "mesPrimeiraAparicao", type = FieldType.Integer)
    private Integer mesPrimeiraAparicao;

    public static PersonagemElastic converterDe(Personagem personagem) {
        var personagemElastic = new PersonagemElastic();
        BeanUtils.copyProperties(personagem, personagemElastic);
        return personagemElastic;
    }
}