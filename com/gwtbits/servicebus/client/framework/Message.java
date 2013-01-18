package com.gwtbits.servicebus.client.framework;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 */
abstract public class Message {

    abstract public String getMessageId();
    abstract public Message createNewInstance();

    public String getValue() {
        return "getValue not defined for " + this.getMessageId();
    }

    public Widget setValue() {
        return new Label("setValue not defined for " + this.getMessageId());
    }

    public boolean isMessageLoggable() {
        return true;
    }

    boolean inDefn = false;
    boolean inUndefn = false;

    private IAgent agent=null;
    IAgent getAgent() {
        return agent;
    }
    void setAgent(IAgent a) {
        agent = a;
    }

    public boolean checkMessage(Message message) {
        if (inDefn) {
            Esb.listen(agent, message);
        }
        else if (inUndefn) {
            Esb.unlisten(agent, message);
        }
        else {
            if (getMessageId().equals(message.getMessageId()))
                return true;
        }
        return false;
    }

}
