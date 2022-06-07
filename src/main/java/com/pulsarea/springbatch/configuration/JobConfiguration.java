package com.pulsarea.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    public JobConfiguration(StepBuilderFactory stepBuilderFactory,
                            JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Hello, World!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job job() {
        Step step = this.stepBuilderFactory
                .get("firstStep")
                .tasklet(tasklet())
                .build();
        return this.jobBuilderFactory
                .get("job")
                .start(step)
                .build();
    }
}
