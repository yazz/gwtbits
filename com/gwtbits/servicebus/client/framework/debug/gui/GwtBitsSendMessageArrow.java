package com.gwtbits.servicebus.client.framework.debug.gui;

import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasArrow;
import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasRectangle;
import com.gwtbits.servicebus.client.framework.debug.DebugAgentToAgentMessageTotals;

import java.util.List;

/**
 */
public class GwtBitsSendMessageArrow extends GwtBitsCanvasArrow {
    private MessageInfoWindowOnCanvas               infoWindowOnCanvas;
    private int                                     yinc                =   0;
    private List<DebugAgentToAgentMessageTotals>    messageTotals       = null;



    public GwtBitsSendMessageArrow(int x, int y, int endX, int endY, MessageInfoWindowOnCanvas infoWindowOnCanvas) {
        super(x,y,endX,endY);
        this.infoWindowOnCanvas = infoWindowOnCanvas;
        infoWindowOnCanvas.setAttachedCanvasItem(this);
    }

    public void onMouseOver() {
        infoWindowOnCanvas.clearAllItems();
        yinc=0;

        if (messageTotals != null) {
            for (DebugAgentToAgentMessageTotals dt:messageTotals) {
                addMessage(dt);
            }
        }
    }

    private void addMessage(DebugAgentToAgentMessageTotals dt) {
        yinc += 20;

        GwtBitsCanvasRectangle messageTotal = new GwtBitsCanvasRectangle();
        messageTotal.x = ((x + endX)/2) + messageTotal.x;
        messageTotal.y = ((y + endY)/2) + messageTotal.y + yinc;
        messageTotal.text = dt.messageType;
        infoWindowOnCanvas.addItem(messageTotal);
    }

    public void onMouseOut() {

    }

    public void onMouseClick() {

    }

    public void setMessageTotals(List<DebugAgentToAgentMessageTotals> totals) {
        messageTotals = totals;
    }
}
