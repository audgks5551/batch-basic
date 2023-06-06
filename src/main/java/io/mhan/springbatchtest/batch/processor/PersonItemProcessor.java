package io.mhan.springbatchtest.batch.processor;

import io.mhan.springbatchtest.person.entity.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(final Person person) throws Exception {
        final Long id = person.getId();
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return transformedPerson;
    }

}
