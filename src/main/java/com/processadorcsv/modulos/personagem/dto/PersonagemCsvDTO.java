package com.processadorcsv.modulos.personagem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonagemCsvDTO {

    private String page_id;
    private String name;
    private String urlslug;
    private String id;
    private String align;
    private String eye;
    private String hair;
    private String sex;
    private String gsm;
    private String alive;
    private String appearences;
    private String firstAppearence;
    private String year;
}
