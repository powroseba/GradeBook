package com.application.batch;

import com.application.batch.readers.MonthAvgReader;
import com.application.batch.writers.MonthAvgWriter;
import com.domain.batch.MonthStudentAvg;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Configuration
@EnableScheduling
public class BatchConfig {

    @PersistenceContext
    private EntityManager entityManager;

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private JobLauncher jobLauncher;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobLauncher = jobLauncher;
    }

    @Bean
    public Job averageOfAllUsersInEveryMonth() {
        return jobBuilderFactory.get("averageOfAllUsersInEveryMonth")
                .incrementer(new RunIdIncrementer())
                .start(averageOfAllUsersInEveryMonthStep())
                .build();
    }

    @Bean
    public Step averageOfAllUsersInEveryMonthStep() {
        return stepBuilderFactory.get("averageOfAllUsersInEveryMonthStep")
                .<List<Object>,List<MonthStudentAvg>>chunk(1)
                .reader(new MonthAvgReader())
                .writer(new MonthAvgWriter())
                .build();
    }
}
