package com.example.WebDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository - 貯藏處
 */
@Component
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    private final HashMap<Long, Person> database = new HashMap<>();

    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public synchronized Person save(Person person) {
        Long id = idGenerator.getAndIncrement();
        person.setId(id);
        database.put(id, person);

        return person;
    }

    @Override
    public synchronized Optional<Person> get(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public synchronized List<Person> getAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public synchronized Optional<Person> delete(Long id) {
        return Optional.ofNullable(database.remove(id));
    }
}
