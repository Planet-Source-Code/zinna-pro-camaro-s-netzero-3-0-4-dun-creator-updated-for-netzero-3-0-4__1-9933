// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerAdList.java

package ticker.client;

import java.util.*;
import nzcom.ZCast;
import ticker.ClientTickerToken;

// Referenced classes of package ticker.client:
//            TickerAd

public class TickerAdList
{

    public TickerAdList()
    {
        tickerAds = null;
        refreshList = true;
        nextIndex = 0;
        currIndex = -1;
    }

    public void clearPositions()
    {
        if(tickerAds == null)
            return;
        for(int i = 0; i < tickerAds.size(); i++)
        {
            TickerAd tickerad = (TickerAd)tickerAds.elementAt(i);
            tickerad.setPositionInImage(-1);
            tickerad.hidden();
        }

    }

    public TickerAd current()
    {
        TickerAd tickerad = null;
        try
        {
            tickerad = (TickerAd)tickerAds.elementAt(currIndex);
        }
        catch(Exception _ex) { }
        return tickerad;
    }

    public void fillList(Vector vector)
    {
        Vector vector1 = parseList(vector);
        if(vector1 != null && vector1.size() > 0)
        {
            tickerAds = vector1;
            nextIndex = 0;
        }
        refreshList = false;
    }

    public Vector getAds()
    {
        return tickerAds;
    }

    public boolean hasElements()
    {
        return tickerAds != null && tickerAds.size() > 0;
    }

    public TickerAd next()
    {
        if(!hasElements())
            return null;
        TickerAd tickerad = (TickerAd)tickerAds.elementAt(nextIndex);
        currIndex = nextIndex;
        nextIndex++;
        if(nextIndex >= tickerAds.size())
            nextIndex = 0;
        return tickerad;
    }

    private static Vector parseList(Vector vector)
    {
        if(vector == null)
            return null;
        Vector vector1 = new Vector();
        String s4 = null;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            try
            {
                s4 = (String)enumeration.nextElement();
                StringTokenizer stringtokenizer = new StringTokenizer(s4, ";");
                if(stringtokenizer.countTokens() >= 4)
                {
                    String s = stringtokenizer.nextToken();
                    String s1 = stringtokenizer.nextToken();
                    String s2 = stringtokenizer.nextToken();
                    String s3 = stringtokenizer.nextToken();
                    TickerAd tickerad = new TickerAd(s, s1, s2);
                    tickerad.setAdType(s3);
                    vector1.addElement(tickerad);
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("Invalid raw ad: " + s4 + "(" + exception + ")");
            }

        return vector1;
    }

    public boolean refreshList()
    {
        return refreshList;
    }

    private Vector tickerAds;
    private int nextIndex;
    private int currIndex;
    private boolean refreshList;
}
