// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UIContainer.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            UbberUI

public abstract class UIContainer extends UbberUI
    implements Serializable
{

    public UIContainer()
    {
        containerID = null;
        containerVersion = null;
        containerDesc = null;
        containerOrder = null;
        containerStatus = null;
        shellID = null;
    }

    public UIContainer(Vector vector)
    {
        this();
        setContainerID(vector.elementAt(0));
        setContainerVersion(vector.elementAt(1));
        setContainerDesc(vector.elementAt(2));
        setContainerStatus(vector.elementAt(3));
        setContainerOrder(vector.elementAt(4));
        setShellID(vector.elementAt(5));
    }

    public String getContainerDesc()
    {
        return containerDesc;
    }

    public Long getContainerID()
    {
        return containerID;
    }

    public Integer getContainerOrder()
    {
        return containerOrder;
    }

    public String getContainerStatus()
    {
        return containerStatus;
    }

    public String getContainerVersion()
    {
        return containerVersion;
    }

    public Long getShellID()
    {
        return shellID;
    }

    public void setContainerDesc(Object obj)
    {
        containerDesc = (String)obj;
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

    public void setContainerStatus(Object obj)
    {
        containerStatus = (String)obj;
    }

    public void setContainerVersion(Object obj)
    {
        containerVersion = (String)obj;
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

    private Long containerID;
    private String containerVersion;
    private String containerDesc;
    private Integer containerOrder;
    private String containerStatus;
    private Long shellID;
}
