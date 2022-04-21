package com.cedrus.samercsaleh.pingpong.stream;

import com.cedrus.samercsaleh.pingpong.kafka.PingPongBallProducer;
import com.cedrus.samercsaleh.pingpong.model.PingPongBall;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class PingPongBallService {
    private final PingPongBallProducer pingPongBallProducer;
    private final ObjectMapper objectMapper;

    public PingPongBallService (PingPongBallProducer pingPongBallProducer, ObjectMapper objectMapper){
        this.pingPongBallProducer = pingPongBallProducer;
        this.objectMapper = objectMapper;
    }

    public void serveBall(PingPongBall pingPongBall){
        try {
            String ballAsJSON = objectMapper.writeValueAsString(pingPongBall);
            pingPongBallProducer.sendMessage(ballAsJSON);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
