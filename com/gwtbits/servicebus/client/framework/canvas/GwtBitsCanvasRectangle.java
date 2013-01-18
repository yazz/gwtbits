package com.gwtbits.servicebus.client.framework.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.gwtbits.servicebus.client.framework.debug.gui.Color;

/**
 */
public class GwtBitsCanvasRectangle extends GwtBitsCanvasItemBaseClass {

    public GwtBitsCanvasRectangle() {

    }

    public GwtBitsCanvasRectangle(int x,int y ,int w,int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getContext2d().setFillStyle(Color.BLACK);
        canvas.getContext2d().fillRect(x, y, width, height);

        if (text != null) {
            canvas.getContext2d().setLineWidth(1);
            canvas.getContext2d().fillText(text,x+width+3,y+height+1);
        }
    }

    public String text=null;

    @Override
    public String getDebugString() {
        if (text != null)
            return text;
        return "Nothing set";
    }

}
