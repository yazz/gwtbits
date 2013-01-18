package com.gwtbits.servicebus.client.framework;

/**
 */
public class RegisterMessage extends Message {
    public final static RegisterMessage ONE = new RegisterMessage();

    public RegisterMessage() {
        inDefn = true;
    }

    @Override
    public String getMessageId() {
        return "system_register";
    }

    @Override
    public Message createNewInstance() {
        return new RegisterMessage();
    }
}
