// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IShellVersion.java

package ui;


// Referenced classes of package ui:
//            IUbberUI

public interface IShellVersion
    extends IUbberUI
{

    public abstract Long getID();

    public abstract String getNzVersion();

    public abstract String getStatus();

    public abstract void setID(Object obj);

    public abstract void setNzVersion(Object obj);

    public abstract void setStatus(Object obj);
}
