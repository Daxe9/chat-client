package com.example;

import java.util.ArrayList;

public class SharedResource {
    private ArrayList<String> messages;
    private int accessTime;

    public SharedResource() {
        messages = new ArrayList<>();
        accessTime = 0;
    }

    public void setMessage(String message) {
        messages.add(message);
    }

    public String getMessage() {
        String result = messages.get(accessTime);
        accessTime++;
        return result;
    }
}
