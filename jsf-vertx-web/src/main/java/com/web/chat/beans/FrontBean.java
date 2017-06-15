package com.web.chat.beans;

import com.web.chat.models.Message;
import com.web.chat.client.RESTClientBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author armenar
 */
@Named
@RequestScoped
public class FrontBean implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @Inject
    private RESTClientBean restClient;

    @Setter
    @Getter
    private Message message;
    @Setter
    @Getter
    private String newMessage;

    public FrontBean() {
    }

    @PostConstruct
    public void init() {
        message = new Message();

    }

    public void addMessage() {      

    }

    public void addMessage1() {       

    }

    public void sendMessage() {
        try {
            restClient.sendMessage(message);
            message.setMessage(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public List<Message> getMessageList() {
        return restClient.getMessageList();
    }

}
