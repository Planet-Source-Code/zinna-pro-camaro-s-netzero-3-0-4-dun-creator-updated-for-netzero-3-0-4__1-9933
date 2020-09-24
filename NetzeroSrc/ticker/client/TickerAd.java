// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerAd.java

package ticker.client;

import java.awt.*;
import ticker.ClientTickerToken;
import ticker.TickerToken;

public class TickerAd extends ClientTickerToken
{

    public TickerAd(String s, String s1, String s2)
    {
        adId = null;
        adText = null;
        adType = null;
        wasHidden = true;
        adId = s;
        adText = s1;
        setUrl(s2);
    }

    public int getAdClickThruCount()
    {
        return adClickThruCount;
    }

    public int getAdDisplayCount()
    {
        return adDisplayCount;
    }

    public String getAdId()
    {
        return adId;
    }

    public String getAdText()
    {
        return adText;
    }

    public String getAdType()
    {
        return adType;
    }

    public int getCategory()
    {
        return category;
    }

    public int getTopicId()
    {
        return topicId;
    }

    public int getWidth()
    {
        if(width == -1)
            width = metrics.stringWidth(myAdText()) + cx;
        return width;
    }

    public boolean hasStatsChanged()
    {
        return statsChanged;
    }

    public void hidden()
    {
        wasHidden = true;
    }

    public void incAdClickThruCount()
    {
        adClickThruCount++;
        statsChanged = true;
    }

    public void incAdDisplayCount()
    {
        adDisplayCount++;
        statsChanged = true;
    }

    private void initialize()
    {
        resetStats();
    }

    public void isHidden()
    {
        wasHidden = true;
    }

    private String myAdText()
    {
        return "----" + getAdText() + "-----";
    }

    public int render(Graphics g, int i)
    {
        int j = i;
        g.setFont(font);
        g.setColor(color);
        g.drawString(myAdText(), j, y);
        j += metrics.stringWidth(myAdText());
        setBoundingRect(i, 0, j - i, g.getClipBounds().height);
        j += cx;
        return j;
    }

    public void resetStats()
    {
        adDisplayCount = 0;
        adClickThruCount = 0;
        statsChanged = false;
    }

    public void setAdType(String s)
    {
        adType = s;
    }

    public void setCategory(int i)
    {
        category = i;
    }

    public static void setFonts(Graphics g)
    {
        font = new Font(g.getFont().getName(), 1, 12);
        metrics = g.getFontMetrics(font);
    }

    public void setTopicId(int i)
    {
        topicId = i;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(getAdId() + ";");
        stringbuffer.append(getAdText() + ";");
        stringbuffer.append(getUrl() + ";");
        stringbuffer.append(getAdDisplayCount() + ";");
        stringbuffer.append(getAdClickThruCount());
        return stringbuffer.toString();
    }

    public void updateDisplayCount(int i)
    {
        if(wasHidden)
        {
            if(getBoundingRect().x + getBoundingRect().width > 0 && i - getBoundingRect().x >= 5)
            {
                incAdDisplayCount();
                wasHidden = false;
            }
        } else
        if(getBoundingRect().x + getBoundingRect().width <= 0)
            wasHidden = true;
    }

    private String adId;
    private String adText;
    private String adType;
    private int adDisplayCount;
    private int adClickThruCount;
    private boolean statsChanged;
    private int category;
    private int topicId;
    private static Color color;
    private static Font font = null;
    private static FontMetrics metrics = null;
    private static int y = 15;
    private static int cx = 10;
    private boolean wasHidden;
    static final String tickerAdSep = ";";
    private static final int delta = 5;

    static 
    {
        color = Color.pink;
    }
}
