package com.ottugi.curry.config;

import com.ottugi.curry.service.recipe.RecipeCsvReader;
import com.ottugi.curry.service.recipe.RecipeCsvWriter;
import com.ottugi.curry.web.dto.recipe.RecipeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    private static final int CHUNK_SIZE = 1000;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RecipeCsvReader recipeCsvReader;
    private final RecipeCsvWriter recipeCsvWriter;

    @Bean
    public Job csvFileItemReaderJob() {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() {
        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<RecipeSaveRequestDto, RecipeSaveRequestDto>chunk(CHUNK_SIZE)
                .reader(recipeCsvReader.csvFileItemReader())
                .writer(recipeCsvWriter)
                .allowStartIfComplete(true)
                .build();
    }
}