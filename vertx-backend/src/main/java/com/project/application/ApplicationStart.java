package com.project.application;

import com.project.resource.ChatResource;
import com.project.dto.CustomMessageCodec;
import com.project.dto.MessageDTO;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;

public class ApplicationStart {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        vertx.deployVerticle(new ChatResource());
        EventBus eventBus = vertx.eventBus();
        eventBus.registerDefaultCodec(MessageDTO.class, new CustomMessageCodec());
    }

}
