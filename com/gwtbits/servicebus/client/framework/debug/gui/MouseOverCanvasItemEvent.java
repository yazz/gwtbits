package com.gwtbits.servicebus.client.framework.debug.gui;

import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasItemBaseClass;

/**
 */
public class MouseOverCanvasItemEvent {
    GwtBitsCanvasItemBaseClass canvasItem = null;
    public void setCanvasItem(GwtBitsCanvasItemBaseClass item) {
        this.canvasItem = item;
    }

    public GwtBitsCanvasItemBaseClass getCanvasItem() {
        return canvasItem;
    }
}
