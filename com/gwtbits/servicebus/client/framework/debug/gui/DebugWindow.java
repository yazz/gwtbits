package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.IAgent;
import com.gwtbits.servicebus.client.framework.Message;
import com.gwtbits.servicebus.client.messages.MessageHideDebugWindow;

/**
 */
public class DebugWindow extends Composite  implements IAgent{
    VerticalPanel                   mainDebugPanel                  = new VerticalPanel();
    TabPanel                        tabPanel                        = new TabPanel();
    DebugMessagesWindow             debugMessagesWindow             = new DebugMessagesWindow();
    DebugMessagesHistoryWindow      debugMessagesHistoryWindow      = new DebugMessagesHistoryWindow();
    DebugAgentsWindow               debugAgentsWindow               = new DebugAgentsWindow();
    DebugAgentsMessagesMapWindow    debugAgentsMessagesMapWindow    = new DebugAgentsMessagesMapWindow();

    public DebugWindow() {
        mainDebugPanel.add(tabPanel);
        tabPanel.add(debugAgentsWindow,"Agents");
        tabPanel.add(debugMessagesWindow, "Messages");
        tabPanel.add(debugMessagesHistoryWindow, "History");
        tabPanel.add(debugAgentsMessagesMapWindow, "Map");
        tabPanel.selectTab(0);
        tabPanel.setAnimationEnabled(true);

        Button closeDebug = new Button();
        closeDebug.setText("Close");
        closeDebug.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Esb.sendFromAgent(DebugWindow.this, new MessageHideDebugWindow());
            }
        });
        mainDebugPanel.add(closeDebug);

        initWidget(mainDebugPanel);

        Esb.register(this);
        Esb.registerScreenPosition(this,new RelativePoint(-1, 0.3));
    }

    public void unregisterFromEventBus() {
        Esb.unregister(debugMessagesWindow);
    }

    @Override
    public String getAgentName() {
        return "DebugWindow";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAgentDescription() {
        return "The debug window";
    }

    @Override
    public void receive(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
