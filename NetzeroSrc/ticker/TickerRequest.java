// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerRequest.java

package ticker;


public interface TickerRequest
{

    public abstract Integer getTopicId();

    public abstract boolean sendBackAds();

    public static final int STOCKS_TOKEN = 0;
    public static final int NEWS_TOKEN = 1;
    public static final int SPORTS_TOKEN = 2;
    public static final int TOPIC_COUNT = 4;
}
