// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UIComponent.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            UbberUI

public abstract class UIComponent extends UbberUI
    implements Serializable
{

    public UIComponent()
    {
        componentID = null;
        componentVersion = null;
        containerID = null;
        componentDesc = null;
        componentState = null;
        componentOrder = null;
        componentType = null;
    }

    public UIComponent(Vector vector)
    {
        this();
        setComponentID(vector.elementAt(0));
        setContainerID(vector.elementAt(1));
        setComponentVersion(vector.elementAt(2));
        setComponentDesc(vector.elementAt(3));
        setComponentState(vector.elementAt(4));
        setComponentOrder(vector.elementAt(5));
        setComponentType(vector.elementAt(6));
    }

    public String getComponentDesc()
    {
        return componentDesc;
    }

    public Long getComponentID()
    {
        return componentID;
    }

    public Integer getComponentOrder()
    {
        return componentOrder;
    }

    public Integer getComponentState()
    {
        return componentState;
    }

    public String getComponentType()
    {
        return componentType;
    }

    public String getComponentVersion()
    {
        return componentVersion;
    }

    public Long getContainerID()
    {
        return containerID;
    }

    public void setComponentDesc(Object obj)
    {
        componentDesc = (String)obj;
    }

    public void setComponentID(Object obj)
    {
        componentID = (Long)obj;
    }

    public void setComponentOrder(Object obj)
    {
        componentOrder = (Integer)obj;
    }

    public void setComponentState(Object obj)
    {
        componentState = (Integer)obj;
    }

    public void setComponentType(Object obj)
    {
        componentType = (String)obj;
    }

    public void setComponentVersion(Object obj)
    {
        componentVersion = (String)obj;
    }

    public void setContainerID(Object obj)
    {
        containerID = (Long)obj;
    }

    public String toString()
    {
        return "Component: " + getComponentDesc();
    }

    private Long componentID;
    private String componentVersion;
    private Long containerID;
    private String componentDesc;
    private Integer componentState;
    private Integer componentOrder;
    private String componentType;
}
