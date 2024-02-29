package com.ottugi.curry.service.recipe;

import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class RecipeCsvReader {
    private final String delimiter;
    private final String[] fieldNames;
    private final String filePath;

    public RecipeCsvReader(GlobalConfig config) {
        this.filePath = config.getFile_Path();
        this.delimiter = config.getFile_delimiter();
        this.fieldNames = config.getField_Names();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<RecipeSaveRequestDto> csvFileItemReader() {
        FlatFileItemReader<RecipeSaveRequestDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(filePath));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8");

        DefaultLineMapper<RecipeSaveRequestDto> defaultLineMapper = createLineMapper();
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }

    private DefaultLineMapper<RecipeSaveRequestDto> createLineMapper() {
        DelimitedLineTokenizer tokenizer = createTokenizer();
        BeanWrapperFieldSetMapper<RecipeSaveRequestDto> fieldSetMapper = createFieldSetMapper();

        DefaultLineMapper<RecipeSaveRequestDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    private DelimitedLineTokenizer createTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(delimiter);
        tokenizer.setNames(fieldNames);
        tokenizer.setStrict(false);
        return tokenizer;
    }

    private BeanWrapperFieldSetMapper<RecipeSaveRequestDto> createFieldSetMapper() {
        BeanWrapperFieldSetMapper<RecipeSaveRequestDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RecipeSaveRequestDto.class);
        return fieldSetMapper;
    }
}
