package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by tl on 5/28/17.
 * - Sse (server-sent events)
 */

@RestController
@RequestMapping("/api")
public class EmitterController {

    static final Logger log = LoggerFactory.getLogger(EmitterController.class);

    final Set<SseEmitter> emitters = new CopyOnWriteArraySet<>();

    @GetMapping("/message")
    public ResponseEntity<SseEmitter> getMessage() {
        SseEmitter emitter = new SseEmitter(); // each emitter corresponds to one client
        emitter.onCompletion(() -> {
            log.info(emitter + "async complete");
            emitters.remove(emitter);
        });
        emitters.add(emitter);
        return ResponseEntity.ok().body(emitter);
    }

    @PostMapping("/message")
    public ResponseEntity postMessage(@RequestParam("msg") String msg) {
        log.info("broadcast message: " + msg);

        // broadcast msg
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(msg, MediaType.TEXT_PLAIN);
                emitter.complete(); // stop the emitter
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }

        return ResponseEntity.ok().body("message added");
    }

}
