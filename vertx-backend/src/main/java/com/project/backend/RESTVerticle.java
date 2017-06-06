package com.project.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RESTVerticle extends AbstractVerticle {

    private final EventBus eventBus = null;
    private final List<MessageDTO> mainList = new ArrayList<>();

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("middleBus"));
        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("A socket was created");
            }
            event.complete(true);
        }));

        router.route().handler(BodyHandler.create());
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedHeader("Content-Type"));

        router.post("/api/message").handler(this::publishToEventBus);
        router.get("/api/messagelist").handler(this::getMessagesFromBus);

        router.get("/api/health").handler(ctx -> {
            ctx.response().end("I'm ok");
        });

        // Hysrix Stream Endpoint
       // router.get(EventMetricsStreamHandler.DEFAULT_HYSTRIX_PREFIX);

        router.route("/*").handler(StaticHandler.create());
        EventBus eventBusLocal = vertx.eventBus();
        eventBusLocal.consumer("messagesBus", message -> {
            MessageDTO customMessage = (MessageDTO) message.body();
            System.out.println("Receiver MAIN " + customMessage.toString());
            mainList.add(0, customMessage);
            message.reply(customMessage);
        });
        //eventBus.publish("middleBus", "Here we go!!");

        vertx.createHttpServer().requestHandler(router::accept).listen(9999);
        //  vertx.setPeriodic(4000, t -> vertx.eventBus().publish("middleBus", "Hello From Server"));
        System.out.println("Service running at 0.0.0.0:9999");

    }

    private void publishToEventBus(RoutingContext routingContext) {
        EventBus eventBusLocal = vertx.eventBus();
        // System.out.println("routingContext.getBodyAsString() " + routingContext.getBodyAsString());
        final MessageDTO message = Json.decodeValue(routingContext.getBodyAsString(),
                MessageDTO.class);
        System.out.println("message#### " + message);

        HttpServerResponse response = routingContext.response();
        response.setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(message));

        eventBusLocal.publish("messagesBus", message);
        Date currentDate = new Date();

        System.out.println("@@@@@@@Publishing log @@@@@@");
        eventBusLocal.publish("middleBus", "Message from @" + message.getUsername() + " received at " + currentDate.toString());

    }

    private void getMessagesFromBus(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(mainList));

    }

}
