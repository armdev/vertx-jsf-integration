package com.web.chat.client;

import com.web.chat.beans.Message;
import com.web.chat.utils.ParamUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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
        HttpClient httpClient = new DefaultHttpClient();
        Integer userId = 0;
        try {
            HttpPost request = new HttpPost("http://localhost:9999/api/message");
            JSONObject json = new JSONObject();
            json.put("username", message.getUsername());
            json.put("message", message.getMessage());
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "utf8");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
        } catch (IOException ex) {
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return userId;
    }

    public List<String> getMessageListddd() {
        List<String> list = new ArrayList<>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9999/api/messagelist");
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("entity " + EntityUtils.toString(entity));
            if (entity != null) {
                //String str  = mapper.readValue(EntityUtils.toString(entity), String.class);
                list.add(entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Message> getMessageList() {
        List<Message> list = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9999/api/messagelist");
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            if (entity != null) {
                list = mapper.readValue(EntityUtils.toString(entity), List.class);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

}
