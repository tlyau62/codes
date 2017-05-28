package com.example.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Created by tl on 5/26/17.
 * note
 * 1. using Tomcat server will cause IOException pipe broken if client terminates connection (using jetter instread)
 * 2. https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html
 * ref
 * 1. https://www.youtube.com/watch?v=5PXo3T3VTjA&t=4252s
 * 2. http://javadeveloperzone.com/spring-mvc/spring-mvc-download-file-example/
 * 3. https://stackoverflow.com/questions/37831100/upload-large-file-in-spring-using-filecopyutils-copy
 * 4. http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/StreamingResponseBody.html
 * 5. http://www.journaldev.com/2651/spring-mvc-exception-handling-controlleradvice-exceptionhandler-handlerexceptionresolver
 * 6. http://www.voidcn.com/blog/do_bset_yourself/article/p-5994564.html
 * 7, http://shazsterblog.blogspot.hk/2016/02/asynchronous-streaming-request.html
 */
@RestController
@RequestMapping("/api")
public class StreamController {

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    @GetMapping("/stream")
    public StreamingResponseBody stream() {

        // run by Servlet container thread: qtp89509666-14
        log.info("current thread: {}", Thread.currentThread());

        // return value(function) run by async thread: simp-async1
        return outputStream -> {
            for (int i = 0; i < 10; i++) {
                log.info("{} -> wrote value: {}", Thread.currentThread(), i);
                String line = String.valueOf(i) + "\n";
                outputStream.write(line.getBytes());
                outputStream.flush();
                sleep(1);
            }
        };

        // notes
        // - 1 "Servlet container" thread per request
        // - IOException throws if client stop request (can't be caught)
        // - StreamingResponseBody supports ResponseEntity
        // - all threads are managed by tomcat/jetty server
    }

    @GetMapping("/stream2")
    public void stream2(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        // run by Servlet container thread: qtp89509666-14 (non-async)
        for (int i = 0; i < 10; i++) {
            log.info("{} -> wrote value: {}", Thread.currentThread(), i);
            String line = String.valueOf(i) + "\n";
            outputStream.write(line.getBytes());
            outputStream.flush();
            sleep(1);
        }

    }

    @GetMapping("/stream3")
    @Async
    public CompletableFuture stream3(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        for (int i = 0; i < 10; i++) {
            log.info("{} -> wrote value: {}", Thread.currentThread(), i);
            String line = String.valueOf(i) + "\n";
            outputStream.write(line.getBytes());
            outputStream.flush();
            sleep(1);
        }

        return CompletableFuture.completedFuture(null);
    }

    @GetMapping("/download")
    public StreamingResponseBody download(HttpServletResponse response) {
        return outputStream -> {
            ClassPathResource resource = new ClassPathResource("test.pdf");

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"test.pdf\"");
            response.setContentLengthLong(resource.contentLength());

            // copy block size 4096B; file no needs to be fully loaded into memory
            StreamUtils.copy(resource.getInputStream(), outputStream);
            // ~ FileCopyUtils.copy(new ClassPathResource("test.pdf").getInputStream(), outputStream);

            log.info("download complete");
        };
    }

    @ExceptionHandler(IOException.class)
    public void handleStreamException(HttpServletRequest request, IOException ex) {
        if (request.getRequestURI().equals("/api/stream"))
            log.info("client aborts connection");
        else
            log.info("{} throws exception: {}", request.getRequestURI(), ex.getMessage());
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
