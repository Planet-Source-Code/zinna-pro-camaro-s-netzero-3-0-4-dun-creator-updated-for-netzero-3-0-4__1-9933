// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UiUtils.java

package util;

import java.awt.*;

public class UiUtils
{

    public static void centerWindow(Window window)
    {
        if(window != null)
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int i = (dimension.width - window.getSize().width) / 2;
            int j = (dimension.height - window.getSize().height) / 2;
            if(i < 0)
                i = 0;
            if(j < 0)
                j = 0;
            window.setLocation(i, j);
        }
    }

    public static void keepWindowFromGoingOffScreen(Window window)
    {
        if(window != null && window.isVisible())
        {
            boolean flag = false;
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Point point = window.getLocation();
            int i = point.x;
            int j = point.y;
            int k = window.getSize().width;
            int l = window.getSize().height;
            int i1 = (i + k) - dimension.width;
            if(i1 > 0)
            {
                i -= i1;
                flag = true;
            }
            int j1 = (j + l) - dimension.height;
            if(j1 > 0)
            {
                j -= j1;
                flag = true;
            }
            if(i < 0)
            {
                i = 0;
                flag = true;
            }
            if(j < 0)
            {
                j = 0;
                flag = true;
            }
            if(flag)
            {
                window.setVisible(false);
                window.setLocation(i, j);
                window.setVisible(true);
            }
        }
    }

    public static Label createAwtLabel(String s, String s1, Rectangle rectangle, Font font)
    {
        Label label = new Label();
        label.setName(s);
        label.setFont(font);
        label.setText(s1);
        if(rectangle != null)
            label.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        return label;
    }

    public static Label createAwtLabel(String s, String s1, Rectangle rectangle)
    {
        return createAwtLabel(s, s1, rectangle, REGULAR_LABEL_FONT);
    }

    public UiUtils()
    {
    }

    public static Font REGULAR_LABEL_FONT = new Font("dialog", 0, 12);
    public static Font REGULAR_BOLD_LABEL_FONT = new Font("dialog", 1, 12);

}
