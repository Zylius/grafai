package Atvaizdavimas.Controller;

import Atvaizdavimas.Drawers.OpenGL.OpenGL;

import java.awt.*;
import java.awt.event.*;

public class Mouse implements MouseWheelListener, MouseListener, MouseMotionListener  {
    OpenGL drawer;

    int oldMouseX, oldMouseY;

    public Mouse(OpenGL drawer)
    {
        this.drawer = drawer;

        drawer.addMouseWheelListener(this);
        drawer.addMouseListener(this);
        drawer.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (oldMouseY - y) / (float) size.height);
        oldMouseX = x;
        oldMouseY = y;
        this.drawer.setCameraPos(thetaX, thetaY);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.drawer.deltaZoom(e.getWheelRotation() * .5f);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
