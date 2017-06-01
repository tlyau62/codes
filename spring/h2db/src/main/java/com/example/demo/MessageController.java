package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tl on 5/31/17.
 * https://stackoverflow.com/questions/3715981/what-s-the-best-restful-method-to-return-total-number-of-items-in-an-object
 */
@RestController
@RequestMapping("/api")
public class MessageController {


    private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        log.info("info: getMessages");
        return ResponseEntity.ok(messageRepository.findAll());
    }

    @PostMapping("/messages")
    public ResponseEntity postMessages(@RequestBody Message message) {
        log.info("info: postMessages");
        if (message.getId() != null) {
            return ResponseEntity.badRequest().body("id cannot be set");
        }
        Message msg = messageRepository.save(message);
        return ResponseEntity.ok().body("message id: " + msg.getId() + " has created");
    }

}
