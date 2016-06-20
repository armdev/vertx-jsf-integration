package com.web.jsf;

import com.sun.faces.component.visit.FullVisitContext;
import com.web.jsf.rest.RESTClientBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

/**
 *
 * @author armenar
 */
@ManagedBean(name = "frontBean")
@RequestScoped
public class FrontBean implements Serializable {

    @ManagedProperty(value = "#{restClient}")
    private RESTClientBean restClient;

    private Message message;
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

        //String str = requestParams.get("status");
        //  System.out.println("Status string " + str);
        //  System.out.println("getNewMessage !!! " + newMessage);
        String strFlash = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("super");
        //System.out.println("strFlash " + strFlash);
        UIComponent component = findComponent("status");
        if (component != null) {
           // System.out.println("component " + component.toString());
        }
        return newMessage;
    }

    //not used
    public UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public List<Message> getMessageList() {
        return restClient.getMessageList();
    }

    public void setRestClient(RESTClientBean restClient) {
        this.restClient = restClient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
