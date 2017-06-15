package com.web.chat.client;

import com.web.chat.models.Message;
import com.web.chat.utils.ParamUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

/**
 *
 * @author armenar
 */
@Named
@ApplicationScoped
public class RESTClientBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public RESTClientBean() {

    }

    @PostConstruct
    public void init() {

    }

    public Integer sendMessage(Message message) {
        Integer userId = 0;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:9999/api/message");
            JSONObject json = new JSONObject();
            json.put("username", message.getUsername());
            json.put("message", message.getMessage());
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "utf8");
            request.setEntity(params);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                ObjectMapper mapper = new ObjectMapper();
                userId = ParamUtil.integerValue(EntityUtils.toString(httpResponse.getEntity()));
            }

        } catch (IOException e) {

        }
        return userId;
    }

    public List<Message> getMessageList() {
        List<Message> list = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:9999/api/messagelist");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                HttpEntity entity = httpResponse.getEntity();
                ObjectMapper mapper = new ObjectMapper();
                if (entity != null) {
                    list = mapper.readValue(EntityUtils.toString(entity), List.class);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

}
