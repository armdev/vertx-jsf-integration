package com.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author armenar
 */
@ManagedBean(name = "messageBean")
@SessionScoped
public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> logList = new ArrayList<>();

    public MessageBean() {

    }

    @PostConstruct
    public void init() {
    }
    
    public List<String> getLogList() {
        return logList;
    }

    public void setLogList(List<String> logList) {
        this.logList = logList;
    }
    
    

}
