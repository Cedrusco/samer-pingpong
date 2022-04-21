package com.cedrus.samercsaleh.pingpong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter // Lombok annotation for getters
@Setter //Lombok annotation for setters
@Component // necassary for spring to autowire
@ConfigurationProperties( // tells spring to bring in any configs with given prefix
        prefix = "app.topic"
)

public class TopicConfig {
    private String topicName;
}

/* note on config packages:
This application currently gets its configuration from the resources directory, as application.yaml, but there is a huge hierarchy of levels that the configuration can come from.  Normally, in a real production environment, higher-priority levels will override this.
 */