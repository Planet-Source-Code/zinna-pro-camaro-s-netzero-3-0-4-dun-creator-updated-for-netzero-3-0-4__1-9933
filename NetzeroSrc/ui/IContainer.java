// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IContainer.java

package ui;

import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package ui:
//            IUbberUI, IComponent

public interface IContainer
    extends IUbberUI
{

    public abstract void addComponent(IComponent icomponent);

    public abstract Integer countComponent();

    public abstract void deleteComponent(IComponent icomponent);

    public abstract String getContainerDesc();

    public abstract Long getContainerID();

    public abstract Integer getContainerOrder();

    public abstract String getContainerStatus();

    public abstract String getContainerVersion();

    public abstract Boolean getHideAll();

    public abstract Boolean getHideState();

    public abstract Integer getOrientation();

    public abstract Boolean getRendered();

    public abstract Long getShellID();

    public abstract Boolean getXFills();

    public abstract Boolean getYFills();

    public abstract Vector listComponent();

    public abstract Enumeration listComponentEnumeration();

    public abstract void setContainerDesc(Object obj);

    public abstract void setContainerID(Object obj);

    public abstract void setContainerOrder(Object obj);

    public abstract void setContainerStatus(Object obj);

    public abstract void setContainerVersion(Object obj);

    public abstract void setHideAll(Object obj);

    public abstract void setRendered(Object obj);

    public abstract void setShellID(Object obj);

    public abstract void setXFills(Object obj);

    public abstract void setYFills(Object obj);
}
