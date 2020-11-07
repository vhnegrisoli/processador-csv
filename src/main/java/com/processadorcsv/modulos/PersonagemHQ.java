package com.processadorcsv.modulos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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
}
