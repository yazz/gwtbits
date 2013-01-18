package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageDummy extends Message {
    public final static MessageDummy ONE = new MessageDummy();

    public String dummy=null;

    @Override
    public String getMessageId() {
        return "dummy";
    }

    @Override
    public Message createNewInstance() {
        return new MessageDummy();
    }

    @Override
    public String getValue() {
        if (dummy == null)
            return "Dummy is null";
        return "Dummy = " + dummy;
    }
}
