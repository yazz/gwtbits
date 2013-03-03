package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.IAgent;
import com.gwtbits.servicebus.client.framework.Message;
import com.gwtbits.servicebus.client.messages.MessageHideDebugWindow;




/**
 * This is the main debug window that is shown. It contains the four main views
 */
public class DebugWindow extends Composite  implements IAgent{

    VerticalPanel                   mainDebugPanel                  = new VerticalPanel();
    TabPanel                        tabPanel                        = new TabPanel();
    DebugMessagesWindow             debugMessagesWindow             = new DebugMessagesWindow();
    DebugMessagesHistoryWindow      debugMessagesHistoryWindow      = new DebugMessagesHistoryWindow();
    DebugAgentsWindow               debugAgentsWindow               = new DebugAgentsWindow();
    DebugAgentsMessagesMapWindow    debugAgentsMessagesMapWindow    = new DebugAgentsMessagesMapWindow();



    public DebugWindow() {
        init();
        addDebugWindows();
        addCloseButton();
    }





    private void init() {
        initWidget(mainDebugPanel);

        Esb.register(this, new RelativePoint(0.5, 0.3));
    }





    private void addCloseButton() {
        Button closeDebug = new Button();
        closeDebug.setText("Close");
        closeDebug.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            Esb.sendFromAgent(DebugWindow.this, new MessageHideDebugWindow());
            }
        });
        mainDebugPanel.add(closeDebug);
    }





    private void addDebugWindows() {
        mainDebugPanel.add(tabPanel);
        tabPanel.add(debugAgentsWindow,"Agents");
        tabPanel.add(debugMessagesWindow, "Messages");
        tabPanel.add(debugMessagesHistoryWindow, "History");
        tabPanel.add(debugAgentsMessagesMapWindow, "Map");
        tabPanel.selectTab(0);
        tabPanel.setAnimationEnabled(true);
    }





    @Override
    public String getAgentName() {
        return "DebugWindow";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAgentDescription() {
        return "The debug window shows all the debug tabs";
    }

    @Override
    public void receive(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
