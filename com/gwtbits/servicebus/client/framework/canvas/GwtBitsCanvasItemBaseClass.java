package com.gwtbits.servicebus.client.framework.canvas;

import com.google.gwt.canvas.client.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 03/01/13
 * Time: 07:23
 * To change this template use File | Settings | File Templates.
 */
abstract public class GwtBitsCanvasItemBaseClass {
    public int x=10;
    public int y=10;
    int width=10;
    int height=10;
    int xtolerance = 75;
    int ytolerance = 15;

    public abstract void render(Canvas canvas);

    public String getDebugString() {
         return "Nothing set";
    }

    public boolean isSelected(int cursorX, int cursorY) {
        if (cursorX < (x + xtolerance))
            if (cursorX > (x - 10)) if (cursorY < (y + ytolerance)) if (cursorY > (y - 10)) return true;
        return false;

    }

    public void onMouseOver() {

    }

    public void onMouseOut() {

    }

    public void onMouseClick() {

    }

}
