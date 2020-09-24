// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataRetriever.java

package ticker.client;

import java.util.Date;
import nzcom.ZCast;
import ticker.TickerRequestImpl;

// Referenced classes of package ticker.client:
//            TickerTransactions, ImageGenerator

public class DataRetriever
    implements Runnable
{

    public DataRetriever(ImageGenerator imagegenerator)
    {
        aGenerator = imagegenerator;
        initialize();
    }

    public void fetchData()
    {
        if(aGenerator == null)
            return;
        if(aGenerator.getState() == 0 && !aGenerator.refreshNeeded())
        {
            ZCast.displayDebug("data in cache");
            aGenerator.setFirstDisplay(true);
            aGenerator.setRegenerate(true);
            aGenerator.setState(2);
            return;
        } else
        {
            ZCast.displayDebug("fetching new data");
            boolean flag = aGenerator.adsNeeded();
            TickerRequestImpl tickerrequestimpl = new TickerRequestImpl(aGenerator.getTopic());
            tickerrequestimpl.setSendAds(flag);
            ticker.TickerResponse tickerresponse = TickerTransactions.fetchTickerData(tickerrequestimpl);
            aGenerator.setData(tickerresponse, flag);
            return;
        }
    }

    private void initialize()
    {
        stop = true;
        selfThread = null;
        suspended = false;
        restart = false;
    }

    public boolean isRunning()
    {
        return !stop && !suspended;
    }

    public synchronized void resume()
    {
        restart = true;
        notify();
    }

    public void run()
    {
        while(!stop) 
        {
            if(suspended)
            {
                if(!restart)
                    continue;
                suspended = false;
                restart = false;
            }
            fetchData();
            long l = 0L;
            long l1 = (new Date()).getTime();
            for(; l < refreshTime; l = (new Date()).getTime() - l1)
            {
                if(stop || suspended)
                    break;
                synchronized(this)
                {
                    try
                    {
                        wait(1000L);
                    }
                    catch(InterruptedException _ex) { }
                }
            }

        }
    }

    public void setRefreshTime(int i)
    {
        refreshTime = (long)i * 60L * 1000L;
    }

    public void start()
    {
        if(refreshTime <= 0L)
            return;
        if(selfThread == null)
            selfThread = new Thread(this);
        if(!selfThread.isAlive())
        {
            stop = false;
            selfThread.start();
        }
    }

    public void suspend()
    {
        suspended = true;
    }

    public void terminate()
    {
        stop = true;
    }

    private boolean stop;
    private long refreshTime;
    private Thread selfThread;
    private boolean suspended;
    private boolean restart;
    private ImageGenerator aGenerator;
}
