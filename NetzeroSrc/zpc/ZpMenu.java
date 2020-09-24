// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpMenu.java

package zpc;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import nzcom.NZWindow;
import nzcom.ZCast;

// Referenced classes of package zpc:
//            ZpComponent, ZpContainer

public class ZpMenu extends Window
    implements MouseListener, Runnable
{

    public ZpMenu(NZWindow nzwindow)
    {
        super(nzwindow.parentFrame);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        clickedComponent = null;
        inMenu = false;
        nzWindow = nzwindow;
        addMouseListener(this);
    }

    public boolean getInMenuStatus()
    {
        return inMenu;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        inMenu = true;
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        inMenu = false;
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        clickedComponent = null;
        ZpComponent zpcomponent = zpContainer.containsPoint(mouseevent.getPoint());
        if(zpcomponent != null)
        {
            zpcomponent.setState(1);
            clickedComponent = zpcomponent;
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        ZpComponent zpcomponent = zpContainer.containsPoint(mouseevent.getPoint());
        if(zpcomponent != null)
        {
            if(clickedComponent != zpcomponent && clickedComponent != null)
            {
                clickedComponent.setState(0);
                clickedComponent = null;
                repaint();
                return;
            }
            Long long1 = new Long(zpcomponent.getComponentId());
            Integer integer = zpcomponent.getEventId();
            if(integer != null && integer.intValue() == 0)
            {
                zpcomponent.mouseClickCounter();
                nzWindow.getComponentTable().put(long1, zpcomponent);
                ZCast.displayDebug("Click Test SubComponent ID= " + long1.longValue() + " counter=" + zpcomponent.getMouseClickedCount() + "\n");
            }
            zpcomponent.setState(1);
            repaint();
            setVisible(false);
            nzWindow.showAdUrl(zpcomponent.getActionString());
            nzWindow.logEvent(zpcomponent);
        }
        setVisible(false);
    }

    public void paint(Graphics g)
    {
        ZpComponent azpcomponent[] = zpContainer.getZpComponents();
        for(int i = 0; i < azpcomponent.length; i++)
            azpcomponent[i].paint(g);

    }

    public void show(ZpContainer zpcontainer, int i, int j)
    {
        ZCast.displayDebug("in menu window " + zpcontainer.getSize().width + " " + zpcontainer.getSize().height);
        zpContainer = zpcontainer;
        setSize(zpcontainer.getSize());
        setLocation(i, j);
        setVisible(true);
        (new Thread(this)).start();
    }

    public void setVisible(boolean flag)
    {
        if(flag & (isVisible() ^ true))
            inMenu = false;
        super.setVisible(flag);
    }

    public void run()
    {
        try
        {
            Thread.sleep(250L);
        }
        catch(Exception _ex) { }
        do
            try
            {
                Thread.sleep(500L);
            }
            catch(Exception _ex) { }
        while(inMenu);
        setVisible(false);
    }

    NZWindow nzWindow;
    ZpContainer zpContainer;
    Dimension screenSize;
    ZpComponent clickedComponent;
    boolean inMenu;
}
