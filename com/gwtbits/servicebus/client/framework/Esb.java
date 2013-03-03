package com.gwtbits.servicebus.client.framework;

import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtbits.servicebus.client.framework.debug.DebugAgentToAgentMessageTotals;
import com.gwtbits.servicebus.client.framework.debug.gui.DebugWindow;
import com.gwtbits.servicebus.client.framework.debug.gui.RelativePoint;
import com.gwtbits.servicebus.client.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ESB represents the GUI Event Driven system
 *
 *
 */
public class Esb implements IAgent {
    private Map<IAgent,List<String>>    agentsAndMessageTypes   = new HashMap<IAgent,List<String>>();
    private Map<String,List<IAgent>>    messageTypesAndAgents   = new HashMap<String,List<IAgent>>();
    private Map<String,Message>         messageTypesAndDetails  = new HashMap<String,Message>();
    private List<EsbHistoryItem>        history                 = new ArrayList<EsbHistoryItem>();
    private Map<IAgent,RelativePoint>   defaultAgentScreenPositions= new HashMap<IAgent, RelativePoint>();

    public static DebugWindow   w = new DebugWindow();
    public static PopupPanel    p = null;



    /**
     * Create a singleton of the ESB
     */
    private Esb() {
    }
    private static Esb esb = getInstance();
    private static Esb getInstance() {
        if (esb == null) {
            esb = new Esb();
            register(esb, new RelativePoint(0.1, 0.5));
        }
        return esb;
    }








    static public void showDebugWindow() {

        if (p == null) {
            p = new PopupPanel();
            p.add(w);
            p.setAutoHideEnabled(true);
        }
        MessageRefreshDebugWindow mr = new MessageRefreshDebugWindow();
        sendFromAgent(getInstance(),mr);
        p.show();
    }



















    public static void registerScreenPosition(IAgent agent,double x, double y) {
        getInstance().instanceRegisterScreenPosition(agent,new RelativePoint(x,y));
    }
    public static void registerEsbScreenPosition(RelativePoint rp) {
        getInstance().instanceRegisterScreenPosition(getInstance(),rp);
    }
    public static void registerScreenPosition(IAgent agent,RelativePoint rp) {
        getInstance().instanceRegisterScreenPosition(agent,rp);
    }
    public void instanceRegisterScreenPosition(IAgent agent,RelativePoint rp) {
        defaultAgentScreenPositions.put(agent,rp);
    }

    public static RelativePoint getScreenPosition(IAgent agent) {
        return getInstance().instanceGetScreenPosition(agent);
    }
    public RelativePoint instanceGetScreenPosition(IAgent agent) {
        RelativePoint rp = defaultAgentScreenPositions.get(agent);
        return rp;
    }







    /**
     * Small test code for the ESB
     */
    static public void test() {
        IAgent agent = new IAgent() {

            @Override
            public String getAgentName() {
                return "Username/password agent(test)";
            }

            @Override
            public String getAgentDescription() {
                return "No desc";
            }

            @Override
            public void receive(Message message) {
                if (message.checkMessage(MessageSendPassword.ONE)) {
                    MessageSendPassword pm = (MessageSendPassword)message;
                    pm.getValue();

                }
                else if (message.checkMessage(MessageSendUsername.ONE)) {
                    MessageSendUsername pm = (MessageSendUsername)message;
                    pm.getValue();
                }
            }
        };
        IAgent agent2 = new IAgent() {

            @Override
            public String getAgentName() {
                return "Dummy agent(test)";
            }

            @Override
            public String getAgentDescription() {
                return "No desc";
            }

            @Override
            public void receive(Message message) {
                if (message.checkMessage(MessageDummy.ONE)) {
                    MessageDummy dm = (MessageDummy)message;
                    dm.getValue();
                }
                else if (message.checkMessage(MessageSendUsername.ONE)) {
                    MessageSendUsername pm = (MessageSendUsername)message;
                    pm.getValue();
                }
            }
        };
        MessageSendPassword passwordMessage = new MessageSendPassword();
        passwordMessage.username = "Zubair";
        passwordMessage.password = "password";

        MessageSendUsername um = new MessageSendUsername();
        um.username = "some@";

        Esb.register(agent);
        Esb.registerScreenPosition(agent,new RelativePoint(0.4,0.1));

        Esb.register(agent2);
        Esb.registerScreenPosition(agent2, new RelativePoint(0.6, 0.1));

        Esb.sendFromAgent(agent, passwordMessage);
        Esb.sendFromAgent(agent, um);
        Esb.sendFromAgent(agent, um);
        Esb.sendFromAgent(agent, new MessageDummy());
        Esb.sendFromAgent(agent2, new MessageDummy());
        Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>> fromTotals = Esb.getAllMessageTotalsFromAgent(agent);
        fromTotals = null;
    }




    public static List<EsbHistoryItem> getHistory() {
        return getInstance().instanceGetHistory();
    }
    public List<EsbHistoryItem> instanceGetHistory() {
        return history;
    }


    public static List<IAgent> getAgents() {
        return getInstance().instanceGetAgents();
    }
    public List<IAgent> instanceGetAgents() {
        List<IAgent> listOfAgents = new ArrayList<IAgent>();
        for (Object ag : agentsAndMessageTypes.keySet().toArray()) {
            listOfAgents.add((IAgent)ag);
        }
        return  listOfAgents;
    }


    public static List<String> getMessageTypes() {
        return getInstance().instanceGetMessageTypes();
    }
    public List<String> instanceGetMessageTypes() {
        List<String> listOfMessageTypes = new ArrayList<String>();
        for (Object ag : messageTypesAndAgents.keySet().toArray()) {
            listOfMessageTypes.add((String)ag);
        }
        return  listOfMessageTypes;
    }



    public static List<String> getMessageTypes(IAgent agent) {
        return getInstance().instanceGetMessageTypes(agent);
    }
    public List<String> instanceGetMessageTypes(IAgent agent) {
        List<String> messageTypes = agentsAndMessageTypes.get(agent);
        if (messageTypes == null) {
            messageTypes = new ArrayList<String>();
            agentsAndMessageTypes.put(agent, messageTypes);
        }

        return messageTypes;
    }


    public static List<String> register(IAgent agent, double x, double y) {
        List<String> ls = getInstance().instanceRegister(agent);
        registerScreenPosition(agent,new RelativePoint(x,y));
        return ls;
    }
    public static List<String> register(IAgent agent, RelativePoint rp) {
        List<String> ls = getInstance().instanceRegister(agent);
        registerScreenPosition(agent,rp);
        return ls;
    }
    public static List<String> register(IAgent agent) {
        return getInstance().instanceRegister(agent);
    }
    public List<String> instanceRegister(IAgent agent) {
        List<String> messageTypes = agentsAndMessageTypes.get(agent);
        if (messageTypes == null) {
            messageTypes = new ArrayList<String>();
            agentsAndMessageTypes.put(agent, messageTypes);
        }

        RegisterMessage rm = new RegisterMessage();
        rm.setAgent(agent);
        agent.receive(rm);


        return messageTypes;
    }




















    private static List<IAgent> register(String messageName) {
        return getInstance().instanceRegister(messageName);
    }
    private List<IAgent> instanceRegister(String messageName) {
        List<IAgent> agents = messageTypesAndAgents.get(messageName);
        if (agents == null) {
            agents = new ArrayList<IAgent>();
            messageTypesAndAgents.put(messageName, agents);
        }
        return agents;
    }





    public static Message getMessageDetails(String messageId) {
        return getInstance().instanceGetMessageDetails(messageId);
    }
    public Message instanceGetMessageDetails(String messageId) {
        Message m = messageTypesAndDetails.get(messageId);
        return m;
    }


        public static void sendFromAgent(IAgent fromAgent, Message message) {
        getInstance().instanceSendFromAgent(fromAgent, message);
    }
    public void instanceSendFromAgent(IAgent fromAgent, Message message) {
        String messageName = message.getMessageId();
        List<IAgent> agents = messageTypesAndAgents.get(messageName);
        if (message.isMessageLoggable()) {
            EsbHistoryItem historyItem = new EsbHistoryItem();
            historyItem.message = message;
            historyItem.senderAgent = fromAgent;
            historyItem.receiverAgents = agents;
            history.add(historyItem);

        }
        if (agents != null) {
            for (IAgent toAgent : agents) {
                toAgent.receive(message);


                Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>>  mapOfMessageTotalsFromThisAgent = fromMessageTotals.get(fromAgent);
                if (mapOfMessageTotalsFromThisAgent == null) {
                    fromMessageTotals.put(fromAgent, new HashMap<IAgent,Map<String,DebugAgentToAgentMessageTotals>>());
                    mapOfMessageTotalsFromThisAgent = fromMessageTotals.get(fromAgent);
                }



                Map<String,DebugAgentToAgentMessageTotals>  mapOfMessageTotalsToThisAgent = mapOfMessageTotalsFromThisAgent.get(toAgent);
                if (mapOfMessageTotalsToThisAgent == null) {
                    mapOfMessageTotalsFromThisAgent.put(toAgent, new HashMap<String,DebugAgentToAgentMessageTotals>());
                    mapOfMessageTotalsToThisAgent = mapOfMessageTotalsFromThisAgent.get(toAgent);
                }





                DebugAgentToAgentMessageTotals messageTotals = mapOfMessageTotalsToThisAgent.get(messageName);
                if (messageTotals == null) {
                    DebugAgentToAgentMessageTotals mt = new DebugAgentToAgentMessageTotals();
                    mt.messageType = messageName;
                    mapOfMessageTotalsToThisAgent.put(messageName, mt);
                    mt.count = 1;
                } else {
                    messageTotals.count ++;
                }



                List<IAgent> listSendingTo = listOfAgentsMessagesSentTo.get(fromAgent);
                if (listSendingTo == null) {
                    listOfAgentsMessagesSentTo.put(fromAgent, new ArrayList<IAgent>());
                    listSendingTo = listOfAgentsMessagesSentTo.get(fromAgent);
                }
                boolean hasAgent = listSendingTo.contains(toAgent);
                if (!hasAgent)
                    listSendingTo.add(toAgent);

            }
        }
    }




    public static void listen(IAgent agent, Message message) {
        getInstance().instanceListen(agent, message);
    }
    public void instanceListen(IAgent agent, Message message) {
        List<String> messageTypes = getMessageTypes(agent);
        if (!messageTypes.contains(message.getMessageId())) {
            messageTypes.add(message.getMessageId());
            messageTypesAndDetails.put(message.getMessageId(),message);
        }

        List<IAgent> agents = register(message.getMessageId());
        if (!agents.contains(agent)){
            agents.add(agent);
        }
    }




    @Override
    public String getAgentName() {
        return "ESB";
    }

    @Override
    public String getAgentDescription() {
        return "The enterprise servicie bus";
    }

    @Override
    public void receive(Message message) {
        if (message.checkMessage(MessageHideDebugWindow.ONE)) {
            p.hide();
        }
    }








    public static List<String> unregister(IAgent agent) {
        return getInstance().instanceUnregister(agent);
    }
    public List<String> instanceUnregister(IAgent agent) {
        List<String> messageTypes = agentsAndMessageTypes.get(agent);
        if (messageTypes != null) {
            UnregisterMessage rm = new UnregisterMessage();
            rm.setAgent(agent);
            agent.receive(rm);

            agentsAndMessageTypes.remove(agent);
        }

        return messageTypes;
    }




    public static void unlisten(IAgent agent, Message message) {
        getInstance().instanceUnlisten(agent, message);
    }
    public void instanceUnlisten(IAgent agent, Message message) {
        List<String> messageTypes = getMessageTypes(agent);

        List<IAgent> agents = register(message.getMessageId());
        if (agents.contains(agent)){
            agents.remove(agent);
        }
    }

    public Map<IAgent,Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>>> fromMessageTotals = new HashMap<IAgent,Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>>>();
    public static Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>> getAllMessageTotalsFromAgent(IAgent fromAgent) {
        return getInstance().instanceGetAllMessageTotalsFromAgent(fromAgent);
    }
    public Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>> instanceGetAllMessageTotalsFromAgent(IAgent fromAgent) {
        Map<IAgent,Map<String,DebugAgentToAgentMessageTotals>>  list = fromMessageTotals.get(fromAgent);
        return list;
    }




    private Map<IAgent, List<IAgent>> listOfAgentsMessagesSentTo = new HashMap<IAgent, List<IAgent>>();
    public static List<IAgent> getListOfAgentsMessagesSentTo(IAgent fromAgent) {
        return getInstance().instanceGetListOfAgentsMessagesSentTo(fromAgent);
    }
    public List<IAgent> instanceGetListOfAgentsMessagesSentTo(IAgent fromAgent) {
        List<IAgent> list = listOfAgentsMessagesSentTo.get(fromAgent);
        return list;
    }
}
