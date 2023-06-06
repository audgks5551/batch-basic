package io.mhan.springbatchtest.batch.config;

import io.mhan.springbatchtest.batch.listener.JobCompletionNotificationListener;
import io.mhan.springbatchtest.batch.processor.PersonItemProcessor;
import io.mhan.springbatchtest.person.entity.Person;
import io.mhan.springbatchtest.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final PersonRepository personRepository;

    @Bean
    public RepositoryItemReader<Person> reader() {
        RepositoryItemReader<Person> reader = new RepositoryItemReader<>();
        reader.setRepository(personRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(10);
        reader.setSort(Map.of("id", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<Person> writer(PersonRepository personRepository) {
        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
        writer.setRepository(personRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      RepositoryItemWriter<Person> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
