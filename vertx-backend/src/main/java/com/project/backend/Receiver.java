package com.project.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;

public class Receiver extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

//        eb.consumer("messagesBus", message -> {
//            String customMessage = (String) message.body();
//            
//            
//            System.out.println("Receiver ->>>>>>>> " + customMessage.toString());
//            
//           message.reply(customMessage);
//
//        });
        eb.consumer("messagesBus", message -> {
            MessageDTO customMessage = (MessageDTO) message.body();

            System.out.println("Receiver ->>>>>>>> " + customMessage.toString());

            message.reply(customMessage);

        });

        // System.out.println("Ready!");
    }

}
