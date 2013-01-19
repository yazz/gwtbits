package com.gwtbits.servicebus.client.framework.debug.gui;

import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvas;
import com.gwtbits.servicebus.client.framework.canvas.GwtBitsCanvasItemBaseClass;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MessageInfoWindowOnCanvas {
    private GwtBitsCanvas canvas = null;

    static private MessageInfoWindowOnCanvas instance = null;
    private MessageInfoWindowOnCanvas() {

    };

    static public MessageInfoWindowOnCanvas getInstance() {
        if (instance == null)
            instance = new MessageInfoWindowOnCanvas();

        return instance;
    }

    private GwtBitsCanvasItemBaseClass attachedCanvasItem;
    public void setAttachedCanvasItem(GwtBitsCanvasItemBaseClass item) {
        this.attachedCanvasItem = item;
    }

    public void clearAllItems() {
        for  (GwtBitsCanvasItemBaseClass item :infoItems) {
            removeItem(item);
        }
    }

    public void removeItem(GwtBitsCanvasItemBaseClass deletedItem) {
        infoItems.remove(deletedItem);
        canvas.remove(deletedItem);
    }

    public void addItem(GwtBitsCanvasItemBaseClass newItem) {
        infoItems.add(newItem);
        canvas.add(newItem);
    }

    List<GwtBitsCanvasItemBaseClass> infoItems = new ArrayList<GwtBitsCanvasItemBaseClass>();
    public void setCanvas(GwtBitsCanvas canvas){
        this.canvas = canvas;
    }
}
