package com.gwtbits.servicebus.client.framework;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
public class EsbHistoryItem {
    public IAgent senderAgent         = null;
    public  List<IAgent>        receiverAgents      = new ArrayList<IAgent>();
    public  Message             message             = null;
    public  Date                timestamp           = new Date();

    public String toString() {
        return "sender: " + senderAgent.getAgentName() + ", message: " + message.getMessageId();
    }
}
