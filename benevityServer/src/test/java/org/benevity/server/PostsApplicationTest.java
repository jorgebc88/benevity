package org.benevity.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("test")
public class PostsApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(PostsApplicationTest.class, args);
    }
}
