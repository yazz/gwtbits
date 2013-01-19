package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageRefreshDebugWindow extends Message {
    public final static MessageRefreshDebugWindow ONE = new MessageRefreshDebugWindow();

    @Override
    public String getMessageId() {
        return "refreshDebugWindow";
    }

    @Override
    public Message createNewInstance() {
        return new MessageRefreshDebugWindow();
    }
}
