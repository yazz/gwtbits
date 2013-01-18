package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageSendUsername extends Message {
    public final static MessageSendUsername ONE = new MessageSendUsername();

    public String username;

    @Override
    public String getMessageId() {
        return "sendusername";
    }

    @Override
    public Message createNewInstance() {
        return new MessageSendUsername();
    }
}
