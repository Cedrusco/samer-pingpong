package com.cedrus.samercsaleh.pingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // generates a constructor with 1 parameter for each field in your class

public class PingPongBall {
    private String id;
    private PingPongTarget pingPongTarget;
    private Color color;

    public void switchTarget() {
        this.pingPongTarget = pingPongTarget.equals(PingPongTarget.PING) ? PingPongTarget.PONG : PingPongTarget.PING;
    }
}
