package com.ottugi.curry.config;

import com.ottugi.curry.service.recipe.RecipeCsvReader;
import com.ottugi.curry.service.recipe.RecipeCsvWriter;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RecipeCsvReader recipeCsvReader;
    private final RecipeCsvWriter recipeCsvWriter;

    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileItemReaderJob() throws Exception {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() throws Exception {
        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<RecipeSaveRequestDto, RecipeSaveRequestDto>chunk(chunkSize)
                .reader(recipeCsvReader.csvFileItemReader())
                .writer(recipeCsvWriter)
                .allowStartIfComplete(true)
                .build();
    }
}