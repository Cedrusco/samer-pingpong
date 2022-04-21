package com.cedrus.samercsaleh.pingpong.stream;

import com.cedrus.samercsaleh.pingpong.config.AppConfig;
import com.cedrus.samercsaleh.pingpong.config.TopicConfig;
import com.cedrus.samercsaleh.pingpong.model.PingPongBall;
import com.cedrus.samercsaleh.pingpong.model.PingPongTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class TopologyProvider {
    private final TopicConfig topicConfig;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public TopologyProvider (TopicConfig topicConfig, AppConfig appConfig, ObjectMapper objectMapper){
        this.topicConfig = topicConfig;
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }
    public Topology getPingPongTopology (PingPongTarget pingPongTarget){
        final StreamsBuilder builder = new StreamsBuilder();
        log.info("Stream builder initialized");
        log.info(pingPongTarget.toString());

        final KStream<String, String> initialStream = builder.stream(topicConfig.getTopicName(), Consumed.with(Serdes.String(),Serdes.String()));
        final KStream<String, String>[] branches = initialStream.branch(getTargetFilterPredicate(pingPongTarget));
        final KStream<String, String> filteredStream = branches[0];
        final KStream<String, String> loggedAndDelayedStream = filteredStream.transformValues(getLogAndDelayVts());

        loggedAndDelayedStream.to(topicConfig.getTopicName(),Produced.with(Serdes.String(),Serdes.String()));
        return builder.build();
    }

    private Predicate<String, String> getTargetFilterPredicate(PingPongTarget pingPongTarget){
        return (key, value) -> {
            PingPongBall pingPongBall = deserializeBall(value);
            return pingPongBall.getPingPongTarget().equals(pingPongTarget);
        };
    }

    private PingPongBall deserializeBall(String pingPongBallAsString) {
        try {
            return objectMapper.readValue(pingPongBallAsString, PingPongBall.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String serializeBall(PingPongBall pingPongBall) {
        try {
            return objectMapper.writeValueAsString(pingPongBall);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ValueTransformerSupplier<String, String> getLogAndDelayVts(){
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) {

            }

            @Override
            public String transform(String value) {
                log.info("Transforming ball -  Value is: {}", value);
                log.debug("Received ball.");
                final int minDelaySec = appConfig.getMinDelaySeconds();
                final int maxDelaySec = appConfig.getMaxDelaySeconds();
                final int deltaDelaySec = maxDelaySec - minDelaySec;
                final Random random = new Random();
                final int sleepSecs = random.nextInt(deltaDelaySec) + minDelaySec;
                log.debug("Will Sleep for {} seconds.", sleepSecs);
                try {
                    Thread.sleep(sleepSecs * 1000L);
                    return value;
                } catch (InterruptedException e) {
                    log.error("Interrupted during Sleep.", e);
                }
                final PingPongBall pingPongBall = deserializeBall(value);
                pingPongBall.switchTarget();
                return serializeBall(pingPongBall);
            }

            @Override
            public void close() {

            }
        };
    }
}
