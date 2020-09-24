// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpContainer.java

package zpc;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import nzcom.ZCast;
import ui.IComponent;
import ui.IContainer;
import ui.IShellUI;
import ui.Image;
import ui.ShellUI;

// Referenced classes of package zpc:
//            ZpComponent, ZpShell

public class ZpContainer extends Container
{

    protected ZpContainer()
    {
        isRenderable = false;
        isHidden = false;
    }

    public ZpContainer(IShellUI ishellui, Hashtable hashtable, IContainer icontainer)
    {
        isRenderable = false;
        isHidden = false;
        setLayout(null);
        owner = ishellui;
        parent = icontainer;
        isRenderable = icontainer.getRendered().booleanValue();
        origin = new Point(0, 0);
        if(isRenderable)
        {
            fillx = icontainer.getXFills().booleanValue();
            filly = icontainer.getYFills().booleanValue();
            orientation = icontainer.getOrientation().intValue();
        } else
        if(icontainer.getOrientation() == null)
            orientation = 1;
        else
            orientation = icontainer.getOrientation().intValue();
        children = new ZpComponent[icontainer.countComponent().intValue()];
        setResourceCache(hashtable);
        int i = 0;
        for(Enumeration enumeration = parent.listComponentEnumeration(); enumeration.hasMoreElements(); add(children[i++]))
            children[i] = new ZpComponent(this, (IComponent)enumeration.nextElement());

        int k = 0;
        int l = 0;
        for(int j = 0; j < children.length; j++)
            if(orientation == 1)
            {
                k = children[j].getSize().width;
                l += children[j].getSize().height;
            } else
            {
                k += children[j].getSize().width;
                l = children[j].getSize().height;
            }

        setSize(k, l);
        visibleSize = new Dimension(k, l);
        hiddenSize = new Dimension(0, 0);
    }

    public ZpContainer(ShellUI shellui)
    {
        isRenderable = false;
        isHidden = false;
        owner = shellui;
    }

    public ZpComponent containsPoint(Point point)
    {
        for(int i = 0; i < children.length; i++)
            if(children[i].getBounds().contains(point))
                return children[i];

        return null;
    }

    protected java.awt.Image[] createImages(IComponent icomponent)
    {
        if(mt == null)
            mt = new MediaTracker(this);
        Vector vector = icomponent.getImages();
        if(vector != null)
        {
            if(vector.size() == 0)
                return null;
            java.awt.Image aimage[] = new java.awt.Image[vector.size()];
            for(int i = 0; i < aimage.length; i++)
            {
                Image image = (Image)vector.elementAt(i);
                byte abyte0[] = (byte[])resourceCache.get(image.getImageID());
                if(abyte0 != null)
                {
                    aimage[i] = Toolkit.getDefaultToolkit().createImage(abyte0);
                    mt.addImage(aimage[i], i + 1);
                    try
                    {
                        mt.waitForID(i + 1);
                    }
                    catch(Exception _ex) { }
                    mt.removeImage(aimage[i]);
                } else
                {
                    ZCast.displayDebug("ResourceCache image " + image.getImageID() + " not found; needed by " + icomponent.getComponentDesc());
                }
            }

            return aimage;
        } else
        {
            return null;
        }
    }

    public IContainer getIContainer()
    {
        return parent;
    }

    public boolean getLayoutStatus()
    {
        return inCurrentLayout;
    }

    protected int getOrientation()
    {
        return orientation;
    }

    public Point getOrigin()
    {
        return origin;
    }

    public String getResourceString(Object obj)
    {
        String s = (String)resourceCache.get((Long)obj);
        return s;
    }

    public String getResourceString(IComponent icomponent)
    {
        String s = (String)resourceCache.get(icomponent.getActionObjID());
        return s;
    }

    public String getResourceString(Long long1)
    {
        return (String)resourceCache.get(long1);
    }

    protected boolean getXFill()
    {
        return fillx;
    }

    protected boolean getYFill()
    {
        return filly;
    }

    public ZpComponent[] getZpComponents()
    {
        return children;
    }

    public ZpShell getZpShell()
    {
        return shell;
    }

    public void paint(Graphics g)
    {
        if(isRenderable)
        {
            for(int i = 0; i < children.length; i++)
                children[i].paint(g);

        }
    }

    public void setHidden(boolean flag)
    {
        isHidden = flag;
        if(isHidden)
            setSize(hiddenSize);
        else
            setSize(visibleSize);
    }

    public void setHidden(boolean flag, Integer integer)
    {
        boolean flag1 = false;
        int i = 0;
        int j = 0;
        for(int k = 0; k < children.length; k++)
        {
            if(children[k].isHideable && children[k].getIComponent().getHideTarget().intValue() == integer.intValue())
            {
                children[k].setHidden(flag, integer);
                flag1 = true;
            }
            if(orientation == 1)
            {
                j += children[k].getSize().height;
                i = Math.max(i, children[k].getSize().width);
            } else
            {
                i += children[k].getSize().width;
                j = Math.max(j, children[k].getSize().height);
            }
        }

        if(flag1)
            setSize(i, j);
    }

    public void setLayoutStatus(boolean flag)
    {
        inCurrentLayout = flag;
    }

    public void setOrigin(int i, int j)
    {
        origin.x = i;
        origin.y = j;
        setLocation(i, j);
        boolean flag = false;
        boolean flag1 = false;
        int k = i;
        int l = j;
        for(int i1 = 0; i1 < children.length; i1++)
        {
            children[i1].setLocation(k, l);
            if(orientation == 1)
                l += children[i1].getSize().height;
            else
                k += children[i1].getSize().width;
        }

    }

    public void setOrigin(ZpContainer zpcontainer, int i)
    {
        Point point = zpcontainer.getLocation();
        Dimension dimension = zpcontainer.getSize();
        switch(i)
        {
        case 1: // '\001'
            setOrigin(point.x, point.y + dimension.height);
            break;

        case 2: // '\002'
            setOrigin((point.x + dimension.width) - getSize().width, point.y + dimension.height);
            break;

        case 3: // '\003'
            setOrigin(point.x + dimension.width, point.y);
            break;

        case 4: // '\004'
            setOrigin(point.x + dimension.width, (point.y + dimension.height) - getSize().height);
            break;
        }
    }

    public static void setResourceCache(Hashtable hashtable)
    {
        resourceCache = hashtable;
    }

    protected IShellUI owner;
    protected IContainer parent;
    protected ZpShell shell;
    protected ZpComponent children[];
    protected boolean isRenderable;
    protected boolean fillx;
    protected boolean filly;
    protected int orientation;
    protected boolean externalContainer;
    protected Point origin;
    protected Dimension visibleSize;
    protected Dimension hiddenSize;
    protected boolean isHidden;
    protected static Hashtable resourceCache;
    protected static MediaTracker mt;
    protected boolean inCurrentLayout;
}
