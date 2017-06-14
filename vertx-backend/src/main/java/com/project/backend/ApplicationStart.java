package com.project.backend;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;

public class ApplicationStart {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        vertx.deployVerticle(new RESTVerticle());     
        EventBus eventBus = vertx.eventBus();
        eventBus.registerDefaultCodec(MessageDTO.class, new CustomMessageCodec());

    }

}
