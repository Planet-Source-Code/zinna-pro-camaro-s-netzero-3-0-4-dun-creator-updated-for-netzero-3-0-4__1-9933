// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StockToken.java

package ticker;

import java.awt.*;
import java.util.StringTokenizer;
import nzcom.ZCast;

// Referenced classes of package ticker:
//            ClientTickerToken, TickerToken

public class StockToken extends ClientTickerToken
{

    public StockToken()
    {
        value = null;
        change = null;
        realName = null;
        family = null;
        businessName = null;
    }

    public StockToken(String s, String s1)
    {
        value = null;
        change = null;
        realName = null;
        family = null;
        businessName = null;
        setDataObject(s1);
        setKeyName(s);
    }

    public String getBusinessName()
    {
        return businessName;
    }

    protected String getChange()
    {
        return change;
    }

    public String getFamily()
    {
        return family;
    }

    protected String getValue()
    {
        if(value == null)
            try
            {
                String s = (String)getDataObject();
                StringTokenizer stringtokenizer = new StringTokenizer(s, delimiter);
                int i = stringtokenizer.countTokens();
                if(i >= 2)
                {
                    if(i >= 3)
                        realName = stringtokenizer.nextToken();
                    value = stringtokenizer.nextToken();
                    change = stringtokenizer.nextToken();
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
        return value;
    }

    public int getWidth()
    {
        if(width == -1)
        {
            width = 0;
            width += symbolMetrics.stringWidth(getKeyName()) + cx1;
            width += symbolMetrics.stringWidth(getValue()) + cx1;
            width += symbolMetrics.stringWidth(getChange()) + cx2;
        }
        return width;
    }

    protected boolean isNegativeChange()
    {
        try
        {
            return getChange().charAt(0) == '-';
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    public int render(Graphics g, int i)
    {
        int j = i;
        g.setFont(symbolFont);
        g.setColor(symbolColor);
        g.drawString(getKeyName(), j, y1);
        j += symbolMetrics.stringWidth(getKeyName()) + cx1;
        g.setFont(valueFont);
        g.setColor(valueColor);
        g.drawString(getValue(), j, y2);
        j += symbolMetrics.stringWidth(getValue()) + cx1;
        g.setFont(valueFont);
        if(isNegativeChange())
            g.setColor(negativeChangeColor);
        else
            g.setColor(valueColor);
        g.drawString(getChange(), j, y2);
        j += symbolMetrics.stringWidth(getChange());
        getBoundingRect().setSize(j - i, g.getClipBounds().height);
        j += cx2;
        return j;
    }

    public void setBusinessName(String s)
    {
        businessName = s;
    }

    public void setfamily(String s)
    {
        family = s;
    }

    public static void setFonts(Graphics g)
    {
        symbolFont = new Font(g.getFont().getName(), 1, 12);
        valueFont = new Font(g.getFont().getName(), 0, 11);
        symbolMetrics = g.getFontMetrics(symbolFont);
        valueMetrics = g.getFontMetrics(symbolFont);
    }

    private String value;
    private String change;
    private String realName;
    public static String delimiter = "|";
    private String family;
    private String businessName;
    private static Color symbolColor;
    private static Color valueColor;
    private static Color negativeChangeColor;
    private static Font symbolFont = null;
    private static Font valueFont = null;
    private static FontMetrics symbolMetrics = null;
    private static FontMetrics valueMetrics = null;
    private static int y1 = 11;
    private static int y2 = 12;
    private static int cx1 = 5;
    private static int cx2 = 20;

    static 
    {
        symbolColor = Color.white;
        valueColor = Color.green;
        negativeChangeColor = Color.red;
    }
}
