package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.IAgent;

import java.util.List;

/**
 */
public class DebugAgentsWindow extends Composite  {
    HorizontalPanel mainAgentsPanel = new HorizontalPanel();
    VerticalPanel propertyPanel = new VerticalPanel();
    VerticalPanel agentsPanel = new VerticalPanel();

    public DebugAgentsWindow() {
        mainAgentsPanel.add(agentsPanel);
        mainAgentsPanel.add(propertyPanel);

        initWidget(mainAgentsPanel);

        update();
    }


    public void update() {
            List<IAgent> agents = Esb.getAgents();
            agentsPanel.clear();
            for (IAgent a: agents) {
                Button b = new Button();
                b.setText(a.getAgentName());
                agentsPanel.add(b);
                b.addClickHandler(new ClickHandler() {
                    IAgent currentAgent;
                    @Override
                    public void onClick(ClickEvent event) {
                        propertyPanel.clear();
                        propertyPanel.add(new Label(currentAgent.getAgentName() ));
                        propertyPanel.add(new Label(currentAgent.getAgentDescription() ));
                        propertyPanel.add(new Label("Messages for " + currentAgent.getAgentName() +":"));
                        List<String> eventsCaptured = Esb.getMessageTypes(currentAgent);
                        for (String s: eventsCaptured) {
                            propertyPanel.add(new Label(s));
                        }
                    }
                    public ClickHandler setCurrentAgent(IAgent ia) {
                        currentAgent = ia;
                        return this;
                    }
                }.setCurrentAgent(a));
        }
    }
}
