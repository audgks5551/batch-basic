package io.mhan.springbatchtest.person.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
}
