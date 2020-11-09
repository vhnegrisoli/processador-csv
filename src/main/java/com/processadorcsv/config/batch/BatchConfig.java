package com.processadorcsv.config.batch;

import com.processadorcsv.modulos.personagem.dto.PersonagemCsvDTO;
import com.processadorcsv.modulos.personagem.model.Personagem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @SneakyThrows
    public FlatFileItemReader<PersonagemCsvDTO> reader() {
        log.info("Iniciando Reader");
        return new FlatFileItemReaderBuilder<PersonagemCsvDTO>()
            .name("personagemItemReader")
            .resource(new PathResource("process/data.csv"))
            .delimited()
            .names(getCsvHeaders())
            .fieldSetMapper(new BeanWrapperFieldSetMapper<PersonagemCsvDTO>() {{
                setTargetType(PersonagemCsvDTO.class);
            }})
            .build();
    }

    @Bean
    public PersonagemItemProcessor processor() {
        return new PersonagemItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Personagem> writer(DataSource dataSource) {
        log.info("Iniciando Writer");
        return new JdbcBatchItemWriterBuilder<Personagem>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql(generateInsert())
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importarPersonagemJob(JobCompletionNotificationListener listener, Step step1) {
        log.info("Iniciando Job: importarPersonagemJob");
        return jobBuilderFactory.get("importarPersonagemJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Personagem> writer) {
        log.info("Iniciando Step: step1");
        return stepBuilderFactory.get("step1")
            .<PersonagemCsvDTO, Personagem> chunk(100)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

    private String[] getCsvHeaders() {
        return new String[]{
            "page_id",
            "name",
            "urlslug",
            "ID",
            "ALIGN",
            "EYE",
            "HAIR",
            "SEX",
            "GSM",
            "ALIVE",
            "APPEARANCES",
            "FIRST APPEARANCE",
            "YEAR"
        };
    }

    private String generateInsert() {
        return "INSERT INTO personagem "
            .concat("(id, nome, identidade, alinhamento, cor_olho, cor_cabelo, sexo, situacao_vida, total_aparicoes,")
            .concat("primeira_aparicao, data_primeira_aparicao, ano_primeira_aparicao, mes_primeira_aparicao) ")
            .concat("VALUES ")
            .concat("(nextval('hibernate_sequence'), :nome, :identidade, :alinhamento, :corOlho, :corCabelo, :sexo, :situacaoVida, :totalAparicoes,")
            .concat(":primeiraAparicao, :dataPrimeiraAparicao, :anoPrimeiraAparicao, :mesPrimeiraAparicao)");
    }
}
