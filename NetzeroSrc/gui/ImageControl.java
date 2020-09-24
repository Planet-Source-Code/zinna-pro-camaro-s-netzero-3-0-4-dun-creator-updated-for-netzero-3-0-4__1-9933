// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageControl.java

package gui;

import java.awt.*;
import java.io.PrintStream;
import java.net.URL;

public class ImageControl extends Canvas
{

    public ImageControl()
    {
    }

    public void setImageURL(URL url1)
    {
        url = url1;
        setImage(getToolkit().getImage(url1));
    }

    public URL getImageURL()
    {
        return url;
    }

    public Image getImage()
    {
        return image;
    }

    public synchronized void setImage(Image image1)
    {
        try
        {
            image = image1;
            if(image1 == null)
            {
                System.err.println("setImage - image is null");
                return;
            }
            MediaTracker mediatracker = new MediaTracker(this);
            mediatracker.addImage(image1, 0);
            mediatracker.waitForAll();
        }
        catch(Exception _ex) { }
        setSize(minimumSize());
    }

    public void setActionUrl(String s)
    {
        actionUrl = s;
    }

    public String getActionUrl()
    {
        return actionUrl;
    }

    public void paint(Graphics g)
    {
        if(image != null)
        {
            g.drawImage(image, 0, 0, this);
        } else
        {
            super.paint(g);
            FontMetrics fontmetrics = g.getFontMetrics(getFont());
            int i = fontmetrics.getDescent();
            int j = fontmetrics.getHeight();
            Dimension dimension = getPreferredSize();
            g.drawLine(0, j - i, dimension.width, j - i);
        }
    }

    public Dimension minimumSize()
    {
        if(image != null && image.getWidth(this) > 0)
            return new Dimension(image.getWidth(this), image.getHeight(this));
        else
            return super.minimumSize();
    }

    Image image;
    URL url;
    private String actionUrl;
}
