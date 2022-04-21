package com.cedrus.samercsaleh.pingpong.stream;

import com.cedrus.samercsaleh.pingpong.config.KafkaConfig;
import com.cedrus.samercsaleh.pingpong.model.PingPongTarget;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service

public class PingService {

    private final KafkaConfig kafkaConfig;
    private final TopologyProvider topologyProvider;

    @Autowired
    public PingService(KafkaConfig kafkaConfig, TopologyProvider topologyProvider){
        this.kafkaConfig = kafkaConfig;
        this.topologyProvider = topologyProvider;
    }

    public void startPingStream() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PingPongTarget.PING);
        kafkaProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        kafkaProperties.put("AUTO_OFFSET_RESET_CONFIG",kafkaConfig.getAutoOffsetReset());

        final KafkaStreams pingStream = new KafkaStreams(topologyProvider.getPingPongTopology(PingPongTarget.PING),kafkaProperties);

        pingStream.start();
        log.info("Ping Stream Started");
        Runtime.getRuntime().addShutdownHook(new Thread(pingStream::close));
    }
}
