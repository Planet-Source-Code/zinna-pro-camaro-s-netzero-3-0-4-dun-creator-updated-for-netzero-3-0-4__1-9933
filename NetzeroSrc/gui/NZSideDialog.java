// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZSideDialog.java

package gui;

import java.awt.*;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZDialog, AgComponent

public abstract class NZSideDialog extends NZDialog
{

    public NZSideDialog(Frame frame)
    {
        super(frame);
        SideImage = null;
        initialize();
    }

    public NZSideDialog(Frame frame, String as[])
    {
        super(frame, as);
        SideImage = null;
        initialize();
    }

    private void initConnections()
    {
        addWindowListener(this);
    }

    private void initialize()
    {
        setName("NZSideDialog");
        SideImage = new AgComponent();
        SideImage.initialize(null, "images/anima/left_still.gif");
        getContentsPane().add(SideImage);
        SideImage.setLocation(5, 5);
        ZCast.displayDebug("Side image size is " + SideImage.getSize().width + " X " + SideImage.getSize().height);
        initConnections();
    }

    protected abstract Panel makeMainPanel();

    protected AgComponent SideImage;
}
