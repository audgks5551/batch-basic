package io.mhan.springbatchtest.batch.listener;

import io.mhan.springbatchtest.person.entity.Person;
import io.mhan.springbatchtest.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final PersonRepository personRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB 종료");

            List<Person> people = personRepository.findAll();

            people.forEach(person -> {
                log.info("{}", person);
            });
        }
    }
}
