package io.mhan.springbatchtest.person.repository;

import io.mhan.springbatchtest.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
