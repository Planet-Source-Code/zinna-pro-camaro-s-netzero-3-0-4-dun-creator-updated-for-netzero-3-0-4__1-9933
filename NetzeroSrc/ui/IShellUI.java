// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IShellUI.java

package ui;

import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package ui:
//            IUbberUI, IContainer

public interface IShellUI
    extends IUbberUI
{

    public abstract void addContainer(IContainer icontainer);

    public abstract Integer countContainer();

    public abstract void deleteContainer(IContainer icontainer);

    public abstract Boolean getChanged();

    public abstract Long getID();

    public abstract Long getLayoutType();

    public abstract String getShellDesc();

    public abstract String getShellVersion();

    public abstract String getStatus();

    public abstract boolean isActive();

    public abstract Vector listContainer();

    public abstract Enumeration listContainerEnumeration();

    public abstract void setChanged(Boolean boolean1);

    public abstract void setID(Object obj);

    public abstract void setInactive();

    public abstract void setLayoutType(Object obj);

    public abstract void setShellDesc(Object obj);

    public abstract void setShellVersion(Object obj);

    public abstract void setStatus(Object obj);

    public static final String FLOATING_LAYOUT = "up";
    public static final String FLOATING_TICKER_LAYOUT = "down";
    public static final String DOCKED_LAYOUT = "up-adv";
    public static final String DOCKED_TICKER_LAYOUT = "down-adv";
    public static final String ICON_LAYOUT = "up-icon";
    public static final String ICON_TICKER_LAYOUT = "down-icon";
    public static final String LAYOUTS[] = {
        "up", "down", "up-adv", "down-adv", "up-icon", "down-icon"
    };

}
