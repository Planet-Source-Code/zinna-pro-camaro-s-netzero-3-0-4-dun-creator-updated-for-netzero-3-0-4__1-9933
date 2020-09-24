// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpShell.java

package zpc;

import java.awt.*;
import java.io.*;
import java.util.*;
import nzcom.*;
import ui.*;

// Referenced classes of package zpc:
//            ZpComponent, ZpContainer

public class ZpShell
{

    public ZpShell()
    {
        unrenderedSet = false;
        mySize = new Dimension(0, 0);
        testMode = System.getProperty("nz.uiTest") != null;
    }

    public void fetchResourceCache(String s)
    {
        File file = new File(s);
        if(file.exists())
            try
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(file));
                aResourceCache = (Hashtable)objectinputstream.readObject();
                objectinputstream.close();
                if(!ZCast.m_demoMode)
                    try
                    {
                        File file1 = new File(String.valueOf(Initializer.m_userNumber) + ".urlcache.obj");
                        ObjectInputStream objectinputstream1 = new ObjectInputStream(new FileInputStream(file1));
                        Hashtable hashtable = (Hashtable)objectinputstream1.readObject();
                        objectinputstream1.close();
                        Object obj;
                        for(Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements(); aResourceCache.put(obj, hashtable.get(obj)))
                            obj = enumeration.nextElement();

                    }
                    catch(Exception exception1)
                    {
                        ZCast.displayDebug("Failed to load dynamic resource cache\r\n");
                        ZCast.displayDebug(exception1);
                    }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception.toString());
            }
        else
            ZCast.displayDebug("serialized object not found: " + s);
    }

    public void fetchUI(String s)
    {
        File file = new File(s);
        if(file.exists())
            try
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(file));
                aWrapper = (ShellUIWrapper)objectinputstream.readObject();
                objectinputstream.close();
                aShellUI = (ShellUI)aWrapper.getShellUI();
                uiLayouts = aWrapper.getAllShellLayout();
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception.toString());
            }
        else
            ZCast.displayDebug("serialized object not found: " + s);
    }

    public ZpComponent findComponentByID(Long long1)
    {
        for(int i = 0; i < uiContainers.length; i++)
        {
            ZpComponent azpcomponent[] = uiContainers[i].getZpComponents();
            for(int j = 0; j < azpcomponent.length; j++)
                if(azpcomponent[j].getIComponent().getComponentID().longValue() == long1.longValue())
                    return azpcomponent[j];

        }

        return null;
    }

    public ZpComponent getComponentByAction(int i)
    {
        for(int j = 0; j < uiContainers.length; j++)
            if(uiContainers[j].getLayoutStatus())
            {
                ZpComponent azpcomponent[] = uiContainers[j].getZpComponents();
                for(int k = 0; k < azpcomponent.length; k++)
                    if(azpcomponent[k].getActionType() == i)
                        return azpcomponent[k];

            }

        return null;
    }

    public ZpComponent getComponentByDesc(String s)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getLayoutStatus())
            {
                ZpComponent azpcomponent[] = uiContainers[i].getZpComponents();
                for(int j = 0; j < azpcomponent.length; j++)
                    if(azpcomponent[j].getDescription().equals(s))
                        return azpcomponent[j];

            }

        return null;
    }

    public ZpComponent getComponentByID(Long long1)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getLayoutStatus())
            {
                ZpComponent azpcomponent[] = uiContainers[i].getZpComponents();
                for(int j = 0; j < azpcomponent.length; j++)
                    if(azpcomponent[j].getIComponent().getComponentID().longValue() == long1.longValue())
                        return azpcomponent[j];

            }

        return null;
    }

    public ZpContainer getContainerByDesc(String s)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getIContainer().getContainerDesc().equals(s))
                return uiContainers[i];

        return null;
    }

    public ZpContainer getContainerById(Long long1)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getIContainer().getContainerID().longValue() == long1.longValue())
                return uiContainers[i];

        return null;
    }

    public long getCurrentLayoutID()
    {
        if(currentLayoutID == null)
            return 0L;
        else
            return currentLayoutID.longValue();
    }

    public long getLayoutIdByDesc(String s)
    {
        for(Enumeration enumeration = uiLayouts.elements(); enumeration.hasMoreElements();)
        {
            ContainerLayout containerlayout = (ContainerLayout)enumeration.nextElement();
            if(containerlayout.getLayoutDesc().equals(s))
                return containerlayout.getLayoutID().longValue();
        }

        return 0L;
    }

    public Hashtable getResourceCache()
    {
        return aResourceCache;
    }

    public Dimension getShellSize()
    {
        mySize.width = 0;
        mySize.height = 0;
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].isRenderable && uiContainers[i].getLayoutStatus())
            {
                Dimension dimension = uiContainers[i].getSize();
                Point point = uiContainers[i].getLocation();
                if(point.x + dimension.width > mySize.width)
                    mySize.width = point.x + dimension.width;
                if(point.y + dimension.height > mySize.height)
                    mySize.height = point.y + dimension.height;
            }

        return mySize;
    }

    public IShellUI getShellUi()
    {
        return aShellUI;
    }

    public ShellUIWrapper getShellWrapper()
    {
        return aWrapper;
    }

    public ZpContainer[] getUiContainers()
    {
        return uiContainers;
    }

    public ZpComponent getZpComponentForPoint(Point point)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getLayoutStatus())
            {
                ZpComponent zpcomponent = uiContainers[i].containsPoint(point);
                if(zpcomponent != null)
                    return zpcomponent;
            }

        return null;
    }

    public void initShell()
    {
        uiContainers = new ZpContainer[aShellUI.countContainer().intValue()];
        Vector vector = aShellUI.listContainer();
        for(int i = 0; i < uiContainers.length; i++)
            uiContainers[i] = new ZpContainer(aShellUI, aResourceCache, (IContainer)vector.elementAt(i));

    }

    public void paint(Graphics g)
    {
        for(int i = 0; i < uiContainers.length; i++)
            if(uiContainers[i].getLayoutStatus())
                uiContainers[i].paint(g);

    }

    public void resetLayoutStatus()
    {
        for(int i = 0; i < uiContainers.length; i++)
            uiContainers[i].setLayoutStatus(false);

    }

    public void setHidden(boolean flag, Integer integer)
    {
        for(int i = 0; i < uiContainers.length; i++)
            uiContainers[i].setHidden(flag, integer);

    }

    public void setLocations(Long long1)
    {
        ZCast.displayDebug("setting to layout " + long1);
        resetLayoutStatus();
        Enumeration enumeration;
        if(long1 == null)
            enumeration = aWrapper.getShellLayout().elements();
        else
            enumeration = aWrapper.getShellLayout(long1).elements();
        ZpContainer zpcontainer;
        for(; enumeration.hasMoreElements(); zpcontainer.setLayoutStatus(true))
        {
            ContainerLayout containerlayout = (ContainerLayout)enumeration.nextElement();
            currentLayoutID = containerlayout.getLayoutID();
            zpcontainer = getContainerById(containerlayout.getContainerID());
            if(containerlayout.getPosition().intValue() == 0)
            {
                zpcontainer.setOrigin(0, 0);
            } else
            {
                ZpContainer zpcontainer1 = getContainerById(containerlayout.getRelativeTo());
                zpcontainer.setOrigin(zpcontainer1, containerlayout.getPosition().intValue());
            }
        }

        if(!unrenderedSet)
        {
            for(int i = 0; i < uiContainers.length; i++)
                if(!uiContainers[i].isRenderable)
                    uiContainers[i].setOrigin(0, 0);

            unrenderedSet = true;
        }
    }

    protected ShellUIWrapper aWrapper;
    protected IShellUI aShellUI;
    protected Hashtable aResourceCache;
    protected ZpContainer uiContainers[];
    protected Vector uiLayouts;
    protected Dimension mySize;
    private NZWindow parent;
    private boolean testMode;
    private boolean unrenderedSet;
    private Long currentLayoutID;
}
