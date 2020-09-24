// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpSubMenu.java

package zpc;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import nzcom.NZWindow;
import nzcom.ZCast;

// Referenced classes of package zpc:
//            ZpComponent, ZpContainer

public class ZpSubMenu extends Panel
    implements MouseListener, FocusListener
{

    public ZpSubMenu(NZWindow nzwindow)
    {
        nzWindow = null;
        zpContainer = null;
        clickedComponent = null;
        speedIndicator = null;
        nzWindow = nzwindow;
        addMouseListener(this);
        addFocusListener(this);
    }

    public void focusGained(FocusEvent focusevent)
    {
    }

    public void focusLost(FocusEvent focusevent)
    {
        setVisible(false);
        getParent().remove(this);
    }

    public ZpContainer getContainer()
    {
        return zpContainer;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        setVisible(false);
        getParent().remove(this);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        clickedComponent = null;
        ZpComponent zpcomponent = zpContainer.containsPoint(mouseevent.getPoint());
        if(zpcomponent != null)
        {
            zpcomponent.setState(1);
            clickedComponent = zpcomponent;
            zpcomponent.repaint();
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        ZpComponent zpcomponent = zpContainer.containsPoint(mouseevent.getPoint());
        if(clickedComponent != zpcomponent && clickedComponent != null)
        {
            clickedComponent.setState(0);
            clickedComponent = null;
            repaint();
            return;
        }
        if(zpcomponent != null)
        {
            zpcomponent.setState(0);
            Integer integer = zpcomponent.getEventId();
            if(integer != null && integer.intValue() == 0)
            {
                zpcomponent.mouseClickCounter();
                Long long1 = new Long(zpcomponent.getComponentId());
                nzWindow.getComponentTable().put(long1, zpcomponent);
                ZCast.displayDebug("Click Test SubComponent ID= " + long1.longValue() + " counter=" + zpcomponent.getMouseClickedCount() + "\n");
            }
            int i = zpcomponent.getActionType();
            switch(i)
            {
            case 14: // '\016'
                nzWindow.slowDownTicker(speedIndicator);
                break;

            case 15: // '\017'
                nzWindow.speedUpTicker(speedIndicator);
                break;

            case 11: // '\013'
                nzWindow.showTickerCustomization();
                break;

            case 12: // '\f'
            case 13: // '\r'
            default:
                ZCast.displayDebug(zpcomponent.getDescription());
                break;
            }
        } else
        {
            setVisible(false);
        }
    }

    public void paint(Graphics g)
    {
        ZpComponent azpcomponent[] = zpContainer.getZpComponents();
        for(int i = 0; i < azpcomponent.length; i++)
        {
            azpcomponent[i].paint(g);
            if(i + 1 < azpcomponent.length)
            {
                int j = azpcomponent[i].getLocation().x + azpcomponent[i].getSize().width;
                int k = azpcomponent[i].getLocation().y;
                azpcomponent[i + 1].setLocation(j, k);
            }
        }

    }

    public void show(ZpContainer zpcontainer, Container container, int i, int j)
    {
        zpContainer = zpcontainer;
        ZpComponent azpcomponent[] = zpcontainer.getZpComponents();
        for(int k = 0; k < azpcomponent.length; k++)
        {
            ZpComponent zpcomponent = azpcomponent[k];
            if(!zpcomponent.getDescription().equals("speed indicator"))
                continue;
            speedIndicator = zpcomponent;
            break;
        }

        setSize(zpContainer.getSize());
        Point point = nzWindow.getLocation();
        container.add(this);
        setLocation(i, j);
        setVisible(true);
    }

    private NZWindow nzWindow;
    private ZpContainer zpContainer;
    private ZpComponent clickedComponent;
    private ZpComponent speedIndicator;
}
