// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZLabel.java

package gui;

import jclass.bwt.JCLabel;

// Referenced classes of package gui:
//            NZFont

public class NZLabel extends JCLabel
{

    public NZLabel()
    {
        init();
    }

    public NZLabel(String s)
    {
        super(s);
        init();
    }

    private void init()
    {
        setFont(new NZFont());
        setAlignment(0);
    }
}
