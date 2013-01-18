package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageSendPassword extends Message {
    public final static MessageSendPassword ONE = new MessageSendPassword();

    public String username;
    public String password;

    @Override
    public String getMessageId() {
        return "sendpassword";
    }

    @Override
    public Message createNewInstance() {
        return new MessageSendPassword();
    }
}
