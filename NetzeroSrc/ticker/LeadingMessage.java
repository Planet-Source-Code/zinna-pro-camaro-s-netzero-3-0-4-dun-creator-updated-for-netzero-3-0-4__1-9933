// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LeadingMessage.java

package ticker;

import java.awt.*;

// Referenced classes of package ticker:
//            ClientTickerToken, TickerToken

public class LeadingMessage extends ClientTickerToken
{

    public LeadingMessage()
    {
    }

    public int getWidth()
    {
        if(width == -1)
            width = textMetrics.stringWidth((String)getDataObject()) + cx;
        return width;
    }

    public int render(Graphics g, int i)
    {
        g.setFont(textFont);
        g.setColor(textColor);
        g.drawString((String)getDataObject(), i, y);
        getBoundingRect().setSize(getWidth() - cx, g.getClipBounds().height);
        return getWidth() + i;
    }

    public static void setFonts(Graphics g)
    {
        textFont = new Font(g.getFont().getName(), 1, 12);
        textMetrics = g.getFontMetrics(textFont);
    }

    private static Color textColor;
    private static Font textFont = null;
    private static FontMetrics textMetrics = null;
    private static int y = 11;
    private static int cx = 20;

    static 
    {
        textColor = Color.white;
    }
}
