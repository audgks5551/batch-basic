package io.mhan.springbatchtest.base.init;

import io.mhan.springbatchtest.person.entity.Person;
import io.mhan.springbatchtest.person.repository.PersonRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class InitData {

    @Bean
    @Order(1)
    public ApplicationRunner devInitData(PersonRepository personRepository) {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                Person person = Person.builder()
                        .firstName("firstName" + i)
                        .lastName("lastName" + i)
                        .build();
                personRepository.save(person);
            }
        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner testBatch(JobLauncher jobLauncher, Job importUserJob) {
        return args -> {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(importUserJob, jobParameters);
        };
    }
}
