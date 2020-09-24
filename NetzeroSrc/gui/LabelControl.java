// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LabelControl.java

package gui;

import java.awt.*;

public class LabelControl extends Label
{

    public LabelControl()
    {
        setCursor(Cursor.getPredefinedCursor(12));
    }

    public void setActionUrl(String s)
    {
        actionUrl = s;
    }

    public String getActionUrl()
    {
        String s = actionUrl;
        if(token != null)
        {
            for(int i = actionUrl.indexOf("[CODE]"); i != -1; i = s.indexOf("[CODE]"))
                s = s.substring(0, i) + token + s.substring(i + 6);

        }
        return s;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        FontMetrics fontmetrics = g.getFontMetrics(getFont());
        int i = fontmetrics.getDescent();
        int j = fontmetrics.getHeight();
        Dimension dimension = getPreferredSize();
        g.drawLine(0, j - 1, dimension.width - 12, j - 1);
    }

    public void setSubstituteToken(String s)
    {
        token = s;
    }

    private String actionUrl;
    private String token;
}
