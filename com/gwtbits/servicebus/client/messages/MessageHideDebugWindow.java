package com.gwtbits.servicebus.client.messages;

import com.gwtbits.servicebus.client.framework.Message;

/**
 */
public class MessageHideDebugWindow extends Message {
    public final static MessageHideDebugWindow ONE = new MessageHideDebugWindow();

    @Override
    public String getMessageId() {
        return "hideDebugWindow";
    }

    @Override
    public Message createNewInstance() {
        return new MessageHideDebugWindow();
    }
}
