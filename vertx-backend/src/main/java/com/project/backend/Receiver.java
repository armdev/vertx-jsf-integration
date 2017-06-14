package com.project.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Receiver extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();
        eb.consumer("messagesBus", message -> {
            MessageDTO customMessage = (MessageDTO) message.body();
            System.out.println("MessagesBus Receiver Class ->>>>>>>> " + customMessage.toString());
            message.reply(customMessage);
        });
    }

}
