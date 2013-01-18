package com.gwtbits.servicebus.client.framework;

/**
 */
public interface IAgent {
    public String getAgentName();
    public String getAgentDescription();
    public void receive(Message message);
}
