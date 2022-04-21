package com.cedrus.samercsaleh.pingpong.controller;

import com.cedrus.samercsaleh.pingpong.model.*;
import com.cedrus.samercsaleh.pingpong.stream.PingPongBallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PingPongBallController {
    private final PingPongBallService pingPongBallService;

    @Autowired
    public PingPongBallController (PingPongBallService pingPongBallService){
        this.pingPongBallService = pingPongBallService;
    }
    @PostMapping(value = "/ball")
    public ResponseEntity <ServeBallResponse> serveBall(@RequestBody ServeBallRequest serveBallRequest){
        log.debug("ServeBall init, request={}", serveBallRequest);
            return addBall(serveBallRequest);
    }

    private ResponseEntity<ServeBallResponse> addBall(ServeBallRequest serveBallRequest) {
        try{
            final PingPongBall pingPongBall = new PingPongBall(serveBallRequest.getId(), PingPongTarget.PING, Color.valueOf(serveBallRequest.getColor()));
            pingPongBallService.serveBall(pingPongBall);
            ServeBallResponse serveBallResponse = new ServeBallResponse(true);
            return new ResponseEntity<>(serveBallResponse,HttpStatus.OK);
        }catch(RuntimeException e){
            log.error("RuntimeException caught when trying to get ball.", e);
            ServeBallResponse serveBallResponse = new ServeBallResponse(false, e.getMessage(),"INTERNAL_ERROR");
            return new ResponseEntity<>(serveBallResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
