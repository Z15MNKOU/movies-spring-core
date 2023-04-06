package fr.cenotelie.training.movies.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("movie")
@Data
public class MessagesConfig {

    private String message;

    private String messageH2;

}
