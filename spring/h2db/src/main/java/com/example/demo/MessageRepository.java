package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tl on 5/31/17.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
