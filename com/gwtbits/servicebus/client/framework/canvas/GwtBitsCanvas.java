package com.gwtbits.servicebus.client.framework.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.gwtbits.servicebus.client.framework.debug.gui.Color;
import com.gwtbits.servicebus.client.framework.debug.gui.MouseOverCanvasItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class GwtBitsCanvas extends Composite {
    Canvas backCanvas = Canvas.createIfSupported();
    Canvas canvas = Canvas.createIfSupported();

    public int height = 600;
    public int width = Window.getClientWidth() - 100;
    MouseOverCanvasItemHandler mouseOverAgentHandler = null;

    List<GwtBitsCanvasItemBaseClass> itemsOnCanvas = new ArrayList<GwtBitsCanvasItemBaseClass>();

    public GwtBitsCanvas() {
        init();

        initWidget(canvas);

        addDrawTimer();
        addItemMouseoverHandler();
    }

    private void addItemMouseoverHandler() {
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (mouseOverAgentHandler != null) {
                    for (GwtBitsCanvasItemBaseClass item: itemsOnCanvas) {
                        if (item != null) {
                            if (item.isSelected(event.getX(),event.getY())) {
                                mouseOverAgentHandler.mouseOverCanvasItem(item);
                                item.onMouseOver();
                                return;
                            }

                        };

                    }
                }
            }

        });
    }

    private void addDrawTimer() {
        Timer t = new Timer() {
            @Override
            public void run() {
                draw();
            }
        };
        t.scheduleRepeating(500);
    }

    private void init() {
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        backCanvas.setCoordinateSpaceWidth(width);
        backCanvas.setCoordinateSpaceHeight(height);
    }

    public void remove(GwtBitsCanvasItemBaseClass item) {
        itemsOnCanvas.remove(item);
    }

    public void add(GwtBitsCanvasItemBaseClass item) {
        itemsOnCanvas.add(item);
    }

    public void draw() {
        backCanvas.getContext2d().setFillStyle(Color.BACKGROUND_COLOR);
        backCanvas.getContext2d().fillRect(0, 0, width, height);

        for (GwtBitsCanvasItemBaseClass item: itemsOnCanvas) {
            item.render(backCanvas);
        }
        canvas.getContext2d().drawImage(backCanvas.getContext2d().getCanvas(),0,0);
    }

    public void addMouseMoveHandler(MouseMoveHandler handler) {
        canvas.addMouseMoveHandler(handler);
    }

    public void addMouseOverAgentHandler(MouseOverCanvasItemHandler handler) {
        mouseOverAgentHandler = handler;
    }
}
