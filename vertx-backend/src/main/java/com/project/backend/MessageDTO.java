package com.project.backend;

import java.util.Objects;

/**
 *
 * @author armenar
 */
public class MessageDTO {

    private String username;
    private String message;

    public MessageDTO() {
    }

    public MessageDTO(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MessageDTO other = (MessageDTO) obj;
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return "Message{" + "username=" + username + ", message=" + message + '}';
    }

  

    
    
}
