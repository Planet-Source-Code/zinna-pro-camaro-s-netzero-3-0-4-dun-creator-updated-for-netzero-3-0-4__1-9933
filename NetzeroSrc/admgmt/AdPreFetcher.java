// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdPreFetcher.java

package admgmt;

import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package admgmt:
//            PlayAd, SequentialPlayList, MarqueePlayList, PlayListConsumer, 
//            PlayAdListener

public class AdPreFetcher extends Thread
    implements PlayAdListener, PlayListConsumer
{

    public AdPreFetcher(int i, Vector vector, boolean flag, String s)
    {
        super(s);
        m_playList = MarqueePlayList.getMarqueePlayList();
        m_fetchLimit = i;
        m_adServers = vector;
        m_amtPrefetched = 0;
        m_name = s;
        m_logic = flag;
    }

    public void adDisplayOccured()
    {
        if(m_amtPrefetched > 0)
        {
            m_amtPrefetched--;
            interrupt();
        }
    }

    public void adDisplayControlDisclosure(Object obj)
    {
    }

    public void newPlayListNotification(boolean flag)
    {
        ZCast.displayDebug("adprefetch", "\nAdPreFetcher " + toString() + " resuming!\n");
        if(flag)
        {
            ZCast.displayDebug("adprefetch", "AdPreFetcher " + toString() + " reseting prefetch quota!\n");
            m_amtPrefetched = 0;
        }
        interrupt();
        resume();
    }

    public void run()
    {
        if(m_playList != null)
        {
            m_playList.addConsumer(this);
            do
                try
                {
                    PlayAd playad;
                    while((playad = m_playList.getNextPlayAd(this)) != null) 
                    {
                        if(playad.getAdType() == 0 && playad.fromAdServers(m_adServers) == m_logic && playad.downloadBanner())
                        {
                            playad.addPlayAdListener(this);
                            for(m_amtPrefetched++; m_amtPrefetched >= m_fetchLimit;)
                                try
                                {
                                    ZCast.displayDebug("adprefetch", "\nAdPreFetcher " + toString() + " going to sleep...\n");
                                    Thread.sleep(60000L);
                                }
                                catch(InterruptedException _ex) { }
                                catch(Exception exception)
                                {
                                    ZCast.displayDebug("adprefetch", exception);
                                }

                        }
                        Thread.yield();
                    }
                    ZCast.displayDebug("adprefetch", "\nAdPreFetcher " + toString() + " suspending...\n");
                    suspend();
                }
                catch(Exception exception1)
                {
                    ZCast.displayDebug("adprefetch", exception1);
                }
            while(true);
        } else
        {
            return;
        }
    }

    private SequentialPlayList m_playList;
    private int m_fetchLimit;
    private Vector m_adServers;
    private String m_name;
    private int m_amtPrefetched;
    private boolean m_logic;
    private static final int MAX_ZZZZ = 60000;
    private static final int SKIP_LIMIT = 40;
}
