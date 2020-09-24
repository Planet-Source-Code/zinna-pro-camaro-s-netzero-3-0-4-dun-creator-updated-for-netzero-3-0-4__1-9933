// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IComponent.java

package ui;

import java.awt.Dimension;
import java.util.Vector;

// Referenced classes of package ui:
//            IUbberUI

public interface IComponent
    extends IUbberUI
{

    public abstract Long getActionObjID();

    public abstract Integer getActionType();

    public abstract String getComponentDesc();

    public abstract Long getComponentID();

    public abstract Integer getComponentOrder();

    public abstract Integer getComponentState();

    public abstract String getComponentStatus();

    public abstract String getComponentType();

    public abstract String getComponentVersion();

    public abstract Long getContainerID();

    public abstract Dimension getDimension();

    public abstract Integer getEventID();

    public abstract Long getFeedID();

    public abstract Integer getGroupID();

    public abstract Integer getHeight();

    public abstract Integer getHideSource();

    public abstract Boolean getHideState();

    public abstract Integer getHideTarget();

    public abstract Long getHover();

    public abstract Vector getImages();

    public abstract Integer getMouseCursor();

    public abstract Long getResourceID();

    public abstract Long getSponsorID();

    public abstract Integer getWidth();

    public abstract Integer getXAxis();

    public abstract Integer getYAxis();

    public abstract void setActionObjID(Object obj);

    public abstract void setActionType(Object obj);

    public abstract void setComponentDesc(Object obj);

    public abstract void setComponentID(Object obj);

    public abstract void setComponentOrder(Object obj);

    public abstract void setComponentState(Object obj);

    public abstract void setComponentStatus(Object obj);

    public abstract void setComponentType(Object obj);

    public abstract void setComponentVersion(Object obj);

    public abstract void setContainerID(Object obj);

    public abstract void setDimension(Object obj);

    public abstract void setEventID(Object obj);

    public abstract void setFeedID(Object obj);

    public abstract void setHeight(Object obj);

    public abstract void setHideSource(Object obj);

    public abstract void setHideState(Object obj);

    public abstract void setHideTarget(Object obj);

    public abstract void setHover(Object obj);

    public abstract void setImages(Object obj);

    public abstract void setMouseCursor(Object obj);

    public abstract void setResourceID(Object obj);

    public abstract void setSponsorID(Object obj);

    public abstract void setWidth(Object obj);

    public abstract void setXAxis(Object obj);

    public abstract void setYAxis(Object obj);
}
