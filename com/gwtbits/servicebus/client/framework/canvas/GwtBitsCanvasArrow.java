package com.gwtbits.servicebus.client.framework.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.gwtbits.servicebus.client.framework.debug.gui.Color;
import com.gwtbits.servicebus.client.framework.debug.gui.Point;

/**
 */
public class GwtBitsCanvasArrow extends GwtBitsCanvasItemBaseClass {
    public int         endX = -1;
    public int         endY = -1;
    CssColor    lightGray = CssColor.make("rgba(150,150,150,0.6)");

    public GwtBitsCanvasArrow(int x, int y, int endX, int endY) {
        this.x = x;
        this.y = y;
        this.endX= endX;
        this.endY = endY;
    }

    @Override
    public void render(Canvas canvas) {
        drawArrowWithText(canvas,text,new Point(x,y) , new Point(endX,endY));
    }

    public String text=null;


    private void drawArrowWithText(Canvas backCanvas,String text, Point pointFrom, Point pointTo) {
        backCanvas.getContext2d().setFillStyle(lightGray);
        backCanvas.getContext2d().setLineWidth(1);
        backCanvas.getContext2d().beginPath();
        backCanvas.getContext2d().moveTo(pointFrom.x, pointFrom.y);
        backCanvas.getContext2d().lineTo(pointTo.x, pointTo.y);
        backCanvas.getContext2d().stroke();

        int lineLength=10;
        float angle = (float) Math.toDegrees(Math.atan2(  pointTo.x - pointFrom.x, pointTo.y-pointFrom.y));

        int incx = (int)(Math.sin(((angle + 135) * Math.PI) / 180) * lineLength);
        int incy = (int)(Math.cos(((angle + 135) * Math.PI) / 180) * lineLength);

        backCanvas.getContext2d().lineTo(pointTo.x + incx, pointTo.y + incy);
        backCanvas.getContext2d().stroke();


        int incx2 = (int)(Math.sin(((angle-135) * Math.PI) / 180) * lineLength);
        int incy2 = (int)(Math.cos(((angle-135) * Math.PI) / 180) * lineLength);
        backCanvas.getContext2d().moveTo(pointTo.x, pointTo.y);
        backCanvas.getContext2d().lineTo(pointTo.x + incx2, pointTo.y + incy2);
        backCanvas.getContext2d().stroke();

//        backCanvas.getContext2d().setFillStyle(selectedColor);
        if (text != null) {
            backCanvas.getContext2d().setFillStyle(Color.RED);
            int midPointX = (pointTo.x + pointFrom.x) / 2;
            int midPointY = (pointTo.y + pointFrom.y) / 2;
            backCanvas.getContext2d().fillText(text, midPointX, midPointY);
            backCanvas.getContext2d().setFillStyle(lightGray);
        }
    }

    @Override
    public String getDebugString() {
        if (text != null)
            return text;
        return "Nothing set on arrow";
    }

    @Override
    public boolean isSelected(int cursorX, int cursorY) {
        int midPointX = (x + endX) / 2;
        int midPointY = (y + endY) / 2;

        ytolerance = 35;

        if (
                (cursorX < (midPointX + xtolerance)) &&
                        (cursorX > (midPointX - 10)) &&
                        (cursorY < (midPointY + ytolerance)) &&
                        (cursorY > (midPointY - 30)) ) {
            return true;
        }

        return false;
    }
}
