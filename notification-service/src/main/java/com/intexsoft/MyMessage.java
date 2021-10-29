package com.intexsoft;

import java.io.Serializable;

public class MyMessage implements Serializable {
    private String address;

    private String message;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
