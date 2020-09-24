// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VolumeScale.java

package gui;

import admgmt.PlayAdVolumeControl;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import nzcom.ZCast;

public class VolumeScale extends Canvas
    implements MouseListener, MouseMotionListener
{

    protected VolumeScale()
    {
        haveImage = false;
        knobImages = null;
        maxPositionUp = 15;
        minPositionDown = 12;
        Up = false;
        Down = false;
        dUp = false;
        dDown = false;
        Dragging = false;
        resetUp = false;
        resetDown = false;
        offScreenBuffer = null;
        Volume = null;
        scale = 0;
        Clicked = false;
        currentPositionUp = 0;
        currentPositionDown = 0;
    }

    protected VolumeScale(PlayAdVolumeControl playadvolumecontrol)
    {
        haveImage = false;
        knobImages = null;
        maxPositionUp = 15;
        minPositionDown = 12;
        Up = false;
        Down = false;
        dUp = false;
        dDown = false;
        Dragging = false;
        resetUp = false;
        resetDown = false;
        offScreenBuffer = null;
        Volume = null;
        scale = 0;
        Clicked = false;
        currentPositionUp = 0;
        currentPositionDown = 0;
        Volume = playadvolumecontrol;
    }

    public void CheckVolumeVideoStatus(Graphics g)
    {
        if(Volume == null)
            g.drawImage(img, 0, 0, 14, 78, 0, 0, 14, 78, this);
        else
            g.drawImage(img, 0, 0, 14, 78, 168, 0, 182, 78, this);
    }

    public Dimension fetchSize()
    {
        if(haveImage)
            return getSize();
        else
            return new Dimension(100, 100);
    }

    public void initialize(String s)
    {
        try
        {
            MediaTracker mediatracker = new MediaTracker(this);
            img = getToolkit().getImage(s);
            mediatracker.addImage(img, 1);
            mediatracker.waitForID(1);
            haveImage = true;
            setSize(14, 78);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
        }
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
        double d = 0.0D;
        if(Volume != null)
        {
            Clicked = true;
            Dragging = false;
            if(mouseevent.getX() > 0 && mouseevent.getX() < 15 && mouseevent.getY() < 79 && mouseevent.getY() >= 0)
            {
                double d1 = (int)((double)(23 * (78 - mouseevent.getY())) * 0.0128205D);
                scale = (int)d1;
                repaint();
            }
        }
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
        double d = 0.0D;
        if(Volume != null)
        {
            Dragging = true;
            if(mouseevent.getY() >= 0 && mouseevent.getY() < 79 && mouseevent.getX() >= 0 && mouseevent.getX() < 15)
            {
                double d1 = (int)((double)(23 * (78 - mouseevent.getY())) * 0.0128205D);
                scale = (int)d1;
                repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        PressedY = mouseevent.getY();
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void paint(Graphics g)
    {
        if(img != null)
            if(Clicked || Dragging)
            {
                g.drawImage(img, 0, 0, 14, 78, 14 * scale, 0, 14 * (scale + 1), 78, this);
                Volume.setVolumeLevel(scale, 23);
            } else
            {
                CheckVolumeVideoStatus(g);
            }
    }

    protected void setVolumeControl(PlayAdVolumeControl playadvolumecontrol)
    {
        Volume = playadvolumecontrol;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    private boolean haveImage;
    private Vector knobImages;
    private int currentPositionUp;
    private int currentPositionDown;
    private int maxPositionUp;
    private int minPositionDown;
    private Image img;
    private boolean Up;
    private boolean Down;
    private boolean dUp;
    private boolean dDown;
    private boolean Dragging;
    private int PressedY;
    private int DraggedY;
    private boolean resetUp;
    private boolean resetDown;
    private int R;
    private int L;
    private int dR;
    private int dL;
    private Image offScreenBuffer;
    private PlayAdVolumeControl Volume;
    private int scale;
    private boolean Clicked;
}
