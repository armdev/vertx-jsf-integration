package com.project.resource;

import com.project.dto.MessageDTO;
import com.project.entities.MessageEntity;
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
import org.javalite.activejdbc.Base;

public class ChatResource extends AbstractVerticle {

    private final EventBus eventBus = null;
    private  List<MessageDTO> mainList = new ArrayList<>();
   /// private final Collection<MessageDTO> mainList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("middleBus"));
        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("0. A socket was created");
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
        router.route("/*").handler(StaticHandler.create());

        //     EventBus eventBusLocal = vertx.eventBus();
//        eventBusLocal.consumer("messagesBus", (Message<Object> message) -> {
//            //get from message bus and save to list
//            MessageDTO customMessage = (MessageDTO) message.body();
//            System.out.println("1. MessagesBus Receiver: " + customMessage.toString());
//            mainList.add(customMessage);
//            message.reply(customMessage);
//        });
        vertx.createHttpServer().requestHandler(router::accept).listen(9999);
        System.out.println("Service running at 0.0.0.0:9999");
    }

    private void publishToEventBus(RoutingContext routingContext) {
        EventBus eventBusLocal = vertx.eventBus();
        final MessageDTO message = Json.decodeValue(routingContext.getBodyAsString(),
                MessageDTO.class);
        System.out.println("2. Publishing message to bus ");
        System.out.println("3. Current Message==> " + message);
        HttpServerResponse response = routingContext.response();
        response.setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(message));
        eventBusLocal.publish("messagesBus", message);
        Date currentDate = new Date();

        System.out.println("!!!.Store message in database");
        boolean check = Base.hasConnection();
        if (!check) {
            Base.open();
        }
        MessageEntity userMessage = new MessageEntity(message.getUsername(), message.getMessage(), currentDate);
        userMessage.saveIt();
        Base.close();
        System.out.println("4. Publishing logs to middleBus!!!! ");

        eventBusLocal.publish("middleBus", "Message from @" + message.getUsername() + " received at " + currentDate.toString());

    }

    private void getMessagesFromBus(RoutingContext routingContext) {
        //get from database
        boolean check = Base.hasConnection();
        if (!check) {
            Base.open();
        }
        List<MessageEntity> messages = MessageEntity.findAll().orderBy("received_date DESC");
        messages.stream().map((msgs) -> {
            MessageDTO obj = new MessageDTO();
            obj.setMessage(msgs.getMessage());
            obj.setUsername(msgs.getUsername());
            return obj;
        }).forEachOrdered((obj) -> {
            mainList.add(0, obj);
        });
        Base.close();
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(mainList));

    }

}
