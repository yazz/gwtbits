package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.EsbHistoryItem;
import com.gwtbits.servicebus.client.framework.IAgent;
import com.gwtbits.servicebus.client.framework.Message;
import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvas;
import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasItemBaseClass;
import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasRectangle;
import com.gwtbits.servicebus.client.framework.debug.DebugAgentToAgentMessageTotals;
import com.gwtbits.servicebus.client.messages.MessageRefreshDebugWindow;
import java.util.*;
import java.util.List;






/**
 * This class is used for the messages and agents map
 */
public class DebugAgentsMessagesMapWindow extends Composite implements IAgent  {

    VerticalPanel                   mainAgentsPanel = new VerticalPanel();
    VerticalPanel                   propertyPanel = new VerticalPanel();
    HorizontalPanel                 statusBar = new HorizontalPanel();
    Label                           mousexLabel = new Label("Mouse x");
    Label                           mouseyLabel = new Label("Mouse y");
    int                             mouseX;
    int                             mouseY;
    IAgent                          currentAgent = null;
    GwtBitsCanvas                   gwtBitsCanvas = new GwtBitsCanvas();
    MessageInfoWindowOnCanvas       infoWindow;
    List<EsbHistoryItem>            messagesFromAgent = new ArrayList<EsbHistoryItem>();
    List<EsbHistoryItem>            messagesToAgent = new ArrayList<EsbHistoryItem>();

    Map<IAgent,Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>>> fromMessageTotals = null;


    /**
     *
     */
    public DebugAgentsMessagesMapWindow() {

        statusBar.add(mousexLabel);
        statusBar.add(mouseyLabel);
        mousexLabel.setWidth("100px");


        infoWindow = MessageInfoWindowOnCanvas.getInstance();
        infoWindow.setCanvas(gwtBitsCanvas);

        calculatePositions();
        mainAgentsPanel.add(gwtBitsCanvas);

        mainAgentsPanel.add(statusBar);
        mainAgentsPanel.add(propertyPanel);

        initWidget(mainAgentsPanel);

        gwtBitsCanvas.addMouseMoveHandler(new MouseMoveHandler() {
            public void onMouseMove(MouseMoveEvent event) {
                mouseX = event.getRelativeX(gwtBitsCanvas.getElement());
                mouseY = event.getRelativeY(gwtBitsCanvas.getElement());
                mousexLabel.setText(""+ mouseX);
                mouseyLabel.setText(""+ mouseY);
//
//                IAgent newagent =getSelectedAgent();
//                if (currentAgent != newagent){
//                    setNewAgentSelected(newagent);
//                }
            }
        });


        gwtBitsCanvas.addMouseOverAgentHandler(new MouseOverCanvasItemHandler() {
            @Override
            public void mouseOverCanvasItem(GwtBitsCanvasItemBaseClass canvasItem) {
                propertyPanel.clear();
                propertyPanel.add(new Label(canvasItem.getDebugString()));

            }
        });

        Esb.register(this);
        Esb.registerScreenPosition(this,0.9,0.9);
    }





    /**
     *
     */
    private void setNewAgentSelected(IAgent newagent) {
        currentAgent = newagent;
        propertyPanel.clear();
        propertyPanel.add(new Label(newagent.getAgentDescription()));

        messagesFromAgent = new ArrayList<EsbHistoryItem>();
        for (EsbHistoryItem currentHistoryItem: Esb.getHistory()) {
            if (currentHistoryItem.senderAgent == newagent) {
                messagesFromAgent.add(currentHistoryItem);
            }
        }


        messagesToAgent = new ArrayList<EsbHistoryItem>();
        for (EsbHistoryItem currentHistoryItem: Esb.getHistory()) {
            for (IAgent receiverAgent: currentHistoryItem.receiverAgents) {
                if (receiverAgent == newagent) {
                    messagesToAgent.add(currentHistoryItem);
                }
            }
        }
    }












    private Point convertRelativePositionToPoint(RelativePoint rp) {
        int x = (int)(Math.random() * (gwtBitsCanvas.width-150));
        int y = (int)(Math.random() * (gwtBitsCanvas.height-50));
        if (rp != null) {
            if (rp.x != -1)
                x = (int)(rp.x * (gwtBitsCanvas.width-150));
            if (rp.y != -1)
                y = (int)(rp.y * (gwtBitsCanvas.height-50));
        }
        return new Point(x, y);
    }


    Map<IAgent,Point> agentScreenPoints = new HashMap<IAgent, Point>();



    private void calculatePositions() {

        List<IAgent> agents = Esb.getAgents();
        for (IAgent a: agents) {
            RelativePoint rp = Esb.getScreenPosition(a);
            Point fromAgentScreenLocation = convertRelativePositionToPoint(rp);

            agentScreenPoints.put(a, fromAgentScreenLocation);

            GwtBitsCanvasRectangle item = new GwtBitsCanvasRectangle(fromAgentScreenLocation.x,fromAgentScreenLocation.y,5,5);
            item.text = a.getAgentName();
            gwtBitsCanvas.add(item);




            List<IAgent> agentsThisAgentTalksTo = Esb.getListOfAgentsMessagesSentTo(a);
            if (agentsThisAgentTalksTo != null) {
                for (IAgent toAgent: agentsThisAgentTalksTo) {
                    if (toAgent != null)
                    {
                        Point locationOfToAgent = agentScreenPoints.get(toAgent);
                        if (locationOfToAgent != null) {

                            GwtBitsSendMessageArrow newArrow = new GwtBitsSendMessageArrow(
                                                                    fromAgentScreenLocation.x,fromAgentScreenLocation.y,
                                                                    locationOfToAgent.x,locationOfToAgent.y,
                                                                    infoWindow
                            );


                            Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>> totals = Esb.getAllMessageTotalsFromAgent(a);
                            Map<String,DebugAgentToAgentMessageTotals> messTotalsToAgent = totals.get(toAgent);
                            if (messTotalsToAgent != null) {
                                List<DebugAgentToAgentMessageTotals> ttt = new ArrayList<DebugAgentToAgentMessageTotals>();
                                String text ="";
                                for (String stt:messTotalsToAgent.keySet()) {
                                    DebugAgentToAgentMessageTotals tt = messTotalsToAgent.get(stt);
                                    text = text + tt.messageType +  ",";
                                    ttt.add(tt);
                                }
                                newArrow.text = text;
                                newArrow.setMessageTotals(ttt);
                            }
                            gwtBitsCanvas.add(newArrow);
                        }
                    }
                }
            }
        }
        String s="jhkhj";
        Esb.getAgents();
    }

    @Override
    public String getAgentName() {
        return "DebugAgentsMessagesMapWindow";
    }

    @Override
    public String getAgentDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void receive(Message message) {
        if (message.checkMessage(MessageRefreshDebugWindow.ONE)) {
            calculatePositions();
        }
    }
}


