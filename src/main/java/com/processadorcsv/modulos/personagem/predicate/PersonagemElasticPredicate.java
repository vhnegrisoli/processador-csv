package com.processadorcsv.modulos.personagem.predicate;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

public class PersonagemElasticPredicate {

    private static final String WILDCARD = "*";

    private BoolQueryBuilder builder;

    public PersonagemElasticPredicate() {
        this.builder = new BoolQueryBuilder();
    }

    public PersonagemElasticPredicate comNome(String nome) {
        builder.filter(wildcardQuery(getKeywordField("nome"), WILDCARD.concat(nome).concat(WILDCARD)));
        return this;
    }

    public NativeSearchQuery build() {
        return new NativeSearchQuery(this.builder);
    }

    private String getKeywordField(String field) {
        return field.concat(".keyword");
    }
}
