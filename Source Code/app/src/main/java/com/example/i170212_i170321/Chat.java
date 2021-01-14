package com.example.i170212_i170321;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private Contact receiver;
    private List<Message> messages;

    public Chat()
    {
        this.receiver = new Contact();
        this.messages = new ArrayList<Message>();
    }
    public Chat(Contact receiver, List<Message> messages) {

        this.receiver = receiver;
        this.messages = messages;
    }


    public Contact getReceiver() {
        return receiver;
    }

    public void setReceiver(Contact receiver) {
        this.receiver = receiver;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
