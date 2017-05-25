package com.example.WebDemo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by tl on 5/23/17.
 */
public interface PersonRepository {

    Person save(Person person);

    Optional<Person> get(Long id);

    Optional<Person>  delete(Long id);

    List<Person> getAll();

}
