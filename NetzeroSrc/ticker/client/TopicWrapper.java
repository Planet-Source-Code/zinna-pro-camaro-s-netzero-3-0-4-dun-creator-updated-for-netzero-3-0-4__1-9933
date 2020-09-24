// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TopicWrapper.java

package ticker.client;

import java.util.*;
import ticker.ClientTickerToken;

// Referenced classes of package ticker.client:
//            TokenEnumerator, TickerEnumerator, TickerAdList, TickerAd

public class TopicWrapper
{

    public TopicWrapper(int i, long l)
    {
        data = null;
        selChanged = false;
        timestamp = l;
        refreshTime = i;
        adList = new TickerAdList();
    }

    public void clearPositions()
    {
        getAdList().clearPositions();
    }

    public final synchronized Enumeration elements()
    {
        return new TickerEnumerator(data, adList.getAds());
    }

    public int eltCount()
    {
        return data != null ? data.size() : 0;
    }

    public TickerAdList getAdList()
    {
        return adList;
    }

    public TickerAd getCurrentAd()
    {
        return getAdList().current();
    }

    public static TopicWrapper getDefaultWrapper()
    {
        TopicWrapper topicwrapper = new TopicWrapper(1, 30000L);
        return topicwrapper;
    }

    public TickerAd getNextAd()
    {
        return getAdList().next();
    }

    public int getRefreshTime()
    {
        return refreshTime;
    }

    public ClientTickerToken getToken(int i)
    {
        ClientTickerToken clienttickertoken = null;
        try
        {
            i %= data.size();
            clienttickertoken = (ClientTickerToken)data.elementAt(i);
        }
        catch(Exception _ex) { }
        return clienttickertoken;
    }

    public boolean hasData()
    {
        return data != null && data.size() > 0;
    }

    public boolean refreshNeeded()
    {
        return !hasData() || timeElapsed() || selChanged;
    }

    public void setData(Vector vector)
    {
        data = vector;
        fetchTime = (new Date()).getTime();
        selChanged = false;
    }

    public void setSelChanged(boolean flag)
    {
        selChanged = flag;
    }

    private boolean timeElapsed()
    {
        return (new Date()).getTime() - fetchTime >= timestamp;
    }

    public final synchronized Enumeration tokens()
    {
        return new TokenEnumerator(data, adList.getAds());
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("fetchTime: " + fetchTime);
        stringbuffer.append("; timestamp: " + timestamp);
        stringbuffer.append("; data: " + data);
        stringbuffer.append("; refreshTime: " + refreshTime + "\n");
        return stringbuffer.toString();
    }

    private long fetchTime;
    private long timestamp;
    private Vector data;
    private int refreshTime;
    private TickerAdList adList;
    private boolean selChanged;
}
