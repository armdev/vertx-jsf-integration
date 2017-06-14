package com.web.chat.beans;

import com.sun.faces.component.visit.FullVisitContext;
import com.web.chat.client.RESTClientBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
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
        // System.out.println("I am a listener");

    }

    public void addMessage1() {
        //     System.out.println("I am a listener second");

    }

    public void sendMessage() {
        try {
            restClient.sendMessage(message);
            message.setMessage(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //not used
    public String getNewMessage() {
        Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            // System.out.println(entry.getKey() + "/" + entry.getValue());
        }

        String strFlash = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("super");

        UIComponent component = findComponent("status");
        if (component != null) {

        }
        return newMessage;
    }

    //not used
    public UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), (VisitContext context1, UIComponent component) -> {
            if (component.getId().equals(id)) {
                found[0] = component;
                return VisitResult.COMPLETE;
            }
            return VisitResult.ACCEPT;
        });

        return found[0];

    }

    public List<Message> getMessageList() {
        return restClient.getMessageList();
    }

}
