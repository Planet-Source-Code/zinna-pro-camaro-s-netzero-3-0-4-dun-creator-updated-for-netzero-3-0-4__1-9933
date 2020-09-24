// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContainerLayout.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            UbberUI, ShellUI

public class ContainerLayout extends UbberUI
    implements Serializable, Cloneable
{

    public ContainerLayout(Vector vector)
    {
        setLayoutID(vector.elementAt(0));
        setContainerOrder(vector.elementAt(1));
        setContainerID(vector.elementAt(2));
        setRelativeTo(vector.elementAt(3));
        setPosition(vector.elementAt(4));
        setShellID(vector.elementAt(5));
        setStatus(vector.elementAt(6));
        setLayoutDesc(vector.elementAt(7));
    }

    public ContainerLayout copy()
    {
        try
        {
            ContainerLayout containerlayout = (ContainerLayout)clone();
            return containerlayout;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public Long getContainerID()
    {
        return containerID;
    }

    public Integer getContainerOrder()
    {
        return containerOrder;
    }

    public String getLayoutDesc()
    {
        return layoutDesc;
    }

    public Long getLayoutID()
    {
        return layoutID;
    }

    public Integer getPosition()
    {
        return position;
    }

    public Long getRelativeTo()
    {
        return relativeTo;
    }

    public Long getShellID()
    {
        return shellID;
    }

    public String getStatus()
    {
        return status;
    }

    public void setContainerID(Object obj)
    {
        if(obj == null)
            containerID = null;
        else
        if(obj instanceof String)
            containerID = new Long((String)obj);
        else
        if(obj instanceof Long)
            containerID = (Long)obj;
    }

    public void setContainerOrder(Object obj)
    {
        if(obj == null)
            containerOrder = null;
        else
        if(obj instanceof String)
            containerOrder = new Integer((String)obj);
        else
        if(obj instanceof Integer)
            containerOrder = (Integer)obj;
    }

    public void setInactive()
    {
        setStatus(ShellUI.statusCodeInactive());
        setObjState(UbberUI.deleteStateCode());
    }

    public void setLayoutDesc(Object obj)
    {
        if(obj == null)
            layoutDesc = null;
        else
        if(obj instanceof String)
            layoutDesc = (String)obj;
    }

    public void setLayoutID(Object obj)
    {
        if(obj == null)
            layoutID = null;
        else
        if(obj instanceof String)
            layoutID = new Long((String)obj);
        else
        if(obj instanceof Long)
            layoutID = (Long)obj;
    }

    public void setPosition(Object obj)
    {
        if(obj == null)
            position = null;
        else
        if(obj instanceof String)
            position = new Integer((String)obj);
        else
        if(obj instanceof Integer)
            position = (Integer)obj;
    }

    public void setRelativeTo(Object obj)
    {
        if(obj == null)
            relativeTo = null;
        else
        if(obj instanceof String)
            relativeTo = new Long((String)obj);
        else
        if(obj instanceof Long)
            relativeTo = (Long)obj;
    }

    public void setShellID(Object obj)
    {
        if(obj == null)
            shellID = null;
        else
        if(obj instanceof String)
            shellID = new Long((String)obj);
        else
        if(obj instanceof Long)
            shellID = (Long)obj;
    }

    public void setStatus(Object obj)
    {
        if(obj == null)
            status = null;
        else
        if(obj instanceof String)
            status = (String)obj;
    }

    public Vector updateVector()
    {
        Vector vector = new Vector();
        vector.addElement(getLayoutID());
        vector.addElement(getContainerOrder());
        vector.addElement(getContainerID());
        vector.addElement(getRelativeTo());
        vector.addElement(getPosition());
        vector.addElement(getShellID());
        vector.addElement(getStatus());
        vector.addElement(getLayoutDesc());
        return vector;
    }

    private Integer containerOrder;
    private Long containerID;
    private Long relativeTo;
    private Integer position;
    private Long shellID;
    private Long layoutID;
    private String status;
    private String layoutDesc;
}
