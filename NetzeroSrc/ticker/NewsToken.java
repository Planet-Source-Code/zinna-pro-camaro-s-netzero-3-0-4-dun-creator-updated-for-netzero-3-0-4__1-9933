// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsToken.java

package ticker;

import java.awt.*;

// Referenced classes of package ticker:
//            ClientTickerToken, TickerToken

public class NewsToken extends ClientTickerToken
{

    public NewsToken()
    {
    }

    public int getWidth()
    {
        if(width == -1)
        {
            width = getKeyName() == null ? 6 : (keyMetrics.stringWidth(getKeyName()) + cx2) - cx1;
            width += cx1;
            width += textMetrics.stringWidth((String)getDataObject());
            width += cx1;
        }
        return width;
    }

    public int render(Graphics g, int i)
    {
        if(getKeyName() != null)
            i += cx2 - cx1;
        int j = i;
        if(getKeyName() != null)
        {
            g.setFont(keyFont);
            g.setColor(keyColor);
            g.drawString(getKeyName(), j, y1);
            j += keyMetrics.stringWidth(getKeyName()) + cx1;
            g.setFont(textFont);
            g.setColor(textColor);
        } else
        {
            g.setFont(textFont);
            g.setColor(textColor);
            g.fillOval(j, y3, 6, 6);
            j += 6 + cx1;
        }
        String s = (String)getDataObject();
        g.drawString(s, j, y2);
        j += textMetrics.stringWidth(s);
        getBoundingRect().setSize(j - i, g.getClipBounds().height);
        j += cx1;
        return j;
    }

    public static void setFonts(Graphics g)
    {
        keyFont = new Font(g.getFont().getName(), 0, 10);
        keyMetrics = g.getFontMetrics(keyFont);
        textFont = new Font(g.getFont().getName(), 0, 12);
        textMetrics = g.getFontMetrics(textFont);
    }

    private static Color keyColor;
    private static Font keyFont = null;
    private static FontMetrics keyMetrics = null;
    private static Color textColor;
    private static Font textFont = null;
    private static FontMetrics textMetrics = null;
    private static int y1 = 11;
    private static int y2 = 12;
    private static int y3 = 4;
    private static int cx1 = 5;
    private static int cx2 = 20;
    public static String delimiter = "|";

    static 
    {
        keyColor = Color.white;
        textColor = Color.orange;
    }
}
