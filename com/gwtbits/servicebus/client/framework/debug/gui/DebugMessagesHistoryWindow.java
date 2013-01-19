package com.gwtbits.servicebus.client.framework.debug.gui;

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.gwtbits.servicebus.client.framework.Esb;
import com.gwtbits.servicebus.client.framework.EsbHistoryItem;
import com.gwtbits.servicebus.client.framework.IAgent;

import java.util.List;

/**
 */
public class DebugMessagesHistoryWindow extends Composite  {
    HorizontalPanel         mainMessagesPanel   = new HorizontalPanel();
    final VerticalPanel     propertyPanel       = new VerticalPanel();
    VerticalPanel           agentsPanel         = new VerticalPanel();

    public DebugMessagesHistoryWindow() {
        mainMessagesPanel.add(agentsPanel);
        mainMessagesPanel.add(propertyPanel);

        initWidget(mainMessagesPanel);

        update();
    }

    public void update()  {

            List<EsbHistoryItem> history = Esb.getHistory();
            agentsPanel.clear();
            CellTable<EsbHistoryItem> table = new CellTable<EsbHistoryItem>();

            //
            // Timestamp column
            //
            TextColumn<EsbHistoryItem> timestampColumn = new TextColumn<EsbHistoryItem>() {
                @Override
                public String getValue(EsbHistoryItem historyItem) {
                    return historyItem.timestamp.toString();
                }
            };
            timestampColumn.setSortable(true);
            table.addColumn(timestampColumn);


            //
            // from agent column
            //
            TextColumn<EsbHistoryItem> fromAgentColumn = new TextColumn<EsbHistoryItem>() {
                @Override
                public String getValue(EsbHistoryItem historyItem) {
                    return historyItem.senderAgent.getAgentName();
                }
            };
            fromAgentColumn.setSortable(true);
            table.addColumn(fromAgentColumn);


            //
            // message column
            //
            TextColumn<EsbHistoryItem> messageColumn = new TextColumn<EsbHistoryItem>() {
                @Override
                public String getValue(EsbHistoryItem historyItem) {
                    return historyItem.message.getMessageId();
                }
            };
            messageColumn.setSortable(true);
            table.addColumn(messageColumn);




            //
            // to agents column
            //
            TextColumn<EsbHistoryItem> toAgentColumn = new TextColumn<EsbHistoryItem>() {
                @Override
                public String getValue(EsbHistoryItem historyItem) {
                    List<IAgent> lia = historyItem.receiverAgents;
                    if (lia != null) {
                        StringBuilder sb = new StringBuilder();
                        for (IAgent ia : lia) {
                            sb.append(ia.getAgentName());
                            sb.append(",");
                        }
                        return sb.toString();
                    }
                    return "No reciever agents";
                }
            };
            toAgentColumn.setSortable(true);
            table.addColumn(toAgentColumn);





            ListDataProvider<EsbHistoryItem> dataProvider = new ListDataProvider<EsbHistoryItem>();

            // Connect the table to the data provider.
            dataProvider.addDataDisplay(table);

            // Add the data to the data provider, which automatically pushes it to the
            // widget.
            List<EsbHistoryItem> list = dataProvider.getList();
            for (EsbHistoryItem historyItem : history) {
                list.add(historyItem);
            }

            agentsPanel.add(table);

            table.addCellPreviewHandler(new CellPreviewEvent.Handler() {

                @Override
                public void onCellPreview(CellPreviewEvent event) {

                    if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

                        EsbHistoryItem m = (EsbHistoryItem)event.getValue();
                        propertyPanel.clear();
                        propertyPanel.add(new Label(m.message.getValue()));
                    }
                }
            });


    }
}
