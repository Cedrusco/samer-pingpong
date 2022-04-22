package com.cedrus.samercsaleh.pingpong;

import com.cedrus.samercsaleh.pingpong.config.AppConfig;
import com.cedrus.samercsaleh.pingpong.config.KafkaConfig;
import com.cedrus.samercsaleh.pingpong.config.TopicConfig;
import com.cedrus.samercsaleh.pingpong.stream.PingService;
import com.cedrus.samercsaleh.pingpong.stream.PongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class, KafkaConfig.class, TopicConfig.class})
public class PingPongApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingPongApplication.class, args);
	}

	@Bean
	public CommandLineRunner pingRunner (ApplicationContext context){
		return args -> {
			((PingService) context.getBean("pingService")).startPingStream();
		};
	}

	@Bean
	public CommandLineRunner pongRunner (ApplicationContext context){
		return args -> {
			((PongService) context.getBean("pongService")).startPongStream();
		};
	}
}
