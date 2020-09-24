// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AgComponent.java

package gui;

import java.awt.*;
import nzcom.ZCast;

public class AgComponent extends Component
{

    public AgComponent()
    {
        haveImage = false;
    }

    public Dimension fetchSize()
    {
        if(haveImage)
            return getSize();
        else
            return new Dimension(100, 100);
    }

    public Image getImage()
    {
        return showImage;
    }

    public void initialize(Window window, String s)
    {
        try
        {
            showImage = getToolkit().getImage(s);
            mt = new MediaTracker(this);
            mt.addImage(showImage, 1);
            mt.waitForID(1);
            haveImage = true;
            setSize(showImage.getWidth(this), showImage.getHeight(this));
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
        }
    }

    public void paint(Graphics g)
    {
        if(haveImage)
            g.drawImage(showImage, 0, 0, this);
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    private Image showImage;
    private MediaTracker mt;
    private boolean haveImage;
}
