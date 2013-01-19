package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.IAgent;
import com.gwtbits.servicebus.client.framework.Message;

import java.util.List;

/**
 */
public class DebugMessagesWindow extends Composite implements  IAgent  {
    HorizontalPanel mainMessagesPanel   = new HorizontalPanel();
    VerticalPanel   propertyPanel       = new VerticalPanel();
    VerticalPanel   agentsPanel         = new VerticalPanel();

    public DebugMessagesWindow() {
        mainMessagesPanel.add(agentsPanel);
        mainMessagesPanel.add(propertyPanel);

        initWidget(mainMessagesPanel);
        update();
    }


    public void update() {
            List<String> messages = Esb.getMessageTypes();
            agentsPanel.clear();
            for (String a: messages) {
                Message m = Esb.getMessageDetails(a);
                Button b = new Button();
                b.setText(a);
                agentsPanel.add(b);
                b.addClickHandler(new ClickHandler() {
                    Message currentMessage;

                    @Override
                    public void onClick(ClickEvent event) {
                        propertyPanel.clear();
                        propertyPanel.add(new Label(currentMessage.getMessageId()));
                        propertyPanel.add(new Label(currentMessage.getValue()));

                        final Message newMessage = currentMessage.createNewInstance();
                        propertyPanel.add(newMessage.setValue());
                        Button sb = new Button();
                        propertyPanel.add(sb);
                        sb.setText("Send");
                        sb.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                Esb.sendFromAgent(DebugMessagesWindow.this,newMessage);
                            }
                        });
                    }

                    public ClickHandler setCurrentMessage(Message ma) {
                        currentMessage = ma;
                        return this;
                    }
                }.setCurrentMessage(m));

        }
    }

    @Override
    public String getAgentName() {
        return "DebugMessagesWindow";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAgentDescription() {
        return "DebugMessagesWindow desc";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void receive(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
