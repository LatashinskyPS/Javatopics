package com.intexsoft.notificationssevice.models;

import java.io.Serializable;

public class MessageFromBank implements Serializable {
    private String address;

    private String theme;

    private String message;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
