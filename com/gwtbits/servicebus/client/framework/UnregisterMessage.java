package com.gwtbits.servicebus.client.framework;

/**
 */
public class UnregisterMessage extends Message {
    public final static UnregisterMessage ONE = new UnregisterMessage();

    public UnregisterMessage() {
        inUndefn = true;
    }

    @Override
    public String getMessageId() {
        return "system_unregister";
    }

    @Override
    public Message createNewInstance() {
        return new UnregisterMessage();
    }
}
