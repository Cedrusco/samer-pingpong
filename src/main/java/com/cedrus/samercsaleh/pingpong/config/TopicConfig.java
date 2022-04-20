package com.cedrus.samercsaleh.pingpong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter // Lombok annotation for getters
@Setter //Lombok annotation for setters
@Component // necassary for spring to autowire
@ConfigurationProperties( // tells spring to bring in any configs with w given prefix
        prefix = "app.topic"
)

public class TopicConfig {
    private String topicName;
}
