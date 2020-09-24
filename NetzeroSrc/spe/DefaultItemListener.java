// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultItemListener.java

package spe;

import nzcom.ZCast;

// Referenced classes of package spe:
//            SPItemListener

public class DefaultItemListener
    implements SPItemListener
{

    public void onItemError(String s, Object obj)
    {
        ZCast.displayDebug("<> SPE onItemError fired. Error message: " + s + "  - identifier: " + obj.toString());
    }

    public void onItemFinished(Object obj)
    {
        ZCast.displayDebug("<> SPE finshed downloading item: " + obj.toString());
    }

    public DefaultItemListener()
    {
    }
}
