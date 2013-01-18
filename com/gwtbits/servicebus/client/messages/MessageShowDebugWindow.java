package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageShowDebugWindow extends Message {
    public final static MessageShowDebugWindow ONE = new MessageShowDebugWindow();

    @Override
    public String getMessageId() {
        return "showDebugWindow";
    }

    @Override
    public Message createNewInstance() {
        return new MessageShowDebugWindow();
    }
}
