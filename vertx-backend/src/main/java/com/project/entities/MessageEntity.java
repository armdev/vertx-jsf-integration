package com.project.entities;

import java.util.Date;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("message")
public class MessageEntity extends Model {

    private static final long serialVersionUID = 1L;

    public MessageEntity() {
    }

    public MessageEntity(String username, String message, Date receivedDate) {
        set("username", username, "message", message, "received_date", receivedDate);
    }

    public String getUsername() {
        return getString("username");
    }

    public String getMessage() {
        return getString("message");
    }

    public Date getReceivedDate() {
        return getDate("received_date");
    }
}
