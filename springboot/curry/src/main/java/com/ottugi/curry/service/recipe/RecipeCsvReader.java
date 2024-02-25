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
    private final String filePath;

    public RecipeCsvReader(GlobalConfig config) {
        this.filePath = config.getFile_Path();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<RecipeSaveRequestDto> csvFileItemReader() {
        FlatFileItemReader<RecipeSaveRequestDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(filePath));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8");

        DefaultLineMapper<RecipeSaveRequestDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer("@");
        delimitedLineTokenizer.setNames(
                "recipeId", "name", "composition", "ingredients", "servings",
                "difficulty", "thumbnail", "time", "orders", "photo", "genre");
        delimitedLineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<RecipeSaveRequestDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(RecipeSaveRequestDto.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
