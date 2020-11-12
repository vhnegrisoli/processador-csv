package com.processadorcsv.modulos.personagem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EMesPrimeiraAparicao {

    JANEIRO("January", 1),
    FEVEREIRO("February", 2),
    MARCO("March", 3),
    ABRIL("April", 4),
    MAIO("May", 5),
    JUNHO("June", 6),
    JULHO("July", 7),
    AGOSTO("August", 8),
    SETEMBRO("September", 9),
    OUTUBRO("October", 10),
    NOVEMBRO("November", 11),
    DEZEMBRO("December", 12);

    @Getter
    private String mesValor;
    @Getter
    private Integer mes;
}
