// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImagePanel.java

package gui;

import java.awt.*;

public class ImagePanel extends Panel
{

    public ImagePanel(Image image1)
    {
        image = null;
        insets = new Insets(5, 5, 5, 5);
        if(image1 != null)
            image = image1;
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(image.getWidth(null) + insets.left + insets.right, image.getHeight(null) + insets.top + insets.bottom);
    }

    public Dimension minimumSize()
    {
        return getMinimumSize();
    }

    public void paint(Graphics g)
    {
        if(image != null)
            g.drawImage(image, insets.top, insets.left, this);
    }

    public Dimension preferredSize()
    {
        return getMinimumSize();
    }

    private Image image;
    private Insets insets;
}
