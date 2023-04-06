package fr.cenotelie.training.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableFeignClients
public class MoviesApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

}
