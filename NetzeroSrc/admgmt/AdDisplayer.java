// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdDisplayer.java

package admgmt;

import java.awt.*;
import nzcom.ServerParms;
import nzcom.ZCast;
import zpc.ZpComponent;

// Referenced classes of package admgmt:
//            TargetedPlayList, PlayList, PlayAd, SequentialPlayList, 
//            MarqueePlayList, DemoPlayList, PlayListConsumer

public class AdDisplayer extends Canvas
    implements Runnable, PlayListConsumer
{

    protected AdDisplayer(ZpComponent zpcomponent, ZpComponent zpcomponent1, MenuItem menuitem, MenuItem menuitem1)
    {
        m_currentAd = null;
        m_InterruptFlag = 0;
        interruptable = false;
        tempInt = 0;
        needsRetargetting = false;
        m_sessUpd = 0;
        m_prevButton = zpcomponent;
        m_nextButton = zpcomponent1;
        m_back = menuitem;
        m_next = menuitem1;
        if(ZCast.m_demoMode)
            m_playList = DemoPlayList.getDemoPlayList();
        else
            m_playList = MarqueePlayList.getMarqueePlayList();
        m_playList.addConsumer(this);
        m_playList.setDrivingConsumer(this);
        m_sessUpdMax = Integer.parseInt(ServerParms.getParm("SendStatsInterval", "10"));
        m_maxWaitTime = Integer.parseInt(ServerParms.getParm("Times_After_Waiting", "5"));
    }

    public static AdDisplayer getAdDisplayer(ZpComponent zpcomponent, ZpComponent zpcomponent1, MenuItem menuitem, MenuItem menuitem1)
    {
        if(m_theAdDisplayer == null)
        {
            m_theAdDisplayer = new AdDisplayer(zpcomponent, zpcomponent1, menuitem, menuitem1);
            ZCast.displayDebug("addisplayer", "AdDisplayer-->> creation returned " + m_theAdDisplayer.toString());
        }
        return m_theAdDisplayer;
    }

    public static AdDisplayer getAdDisplayer()
    {
        ZCast.displayDebug("addisplayer", "AdDisplayer-->> retrieving object instance=" + m_theAdDisplayer);
        return m_theAdDisplayer;
    }

    public void adClicked(long l)
    {
        if(m_currentAd != null)
        {
            if(m_currentAd.clickThru(l))
            {
                if(!ZCast.m_demoMode)
                    m_playList.sendStats();
                m_sessUpd = 0;
            }
        } else
        {
            ZCast.displayDebug("addisplayer", "***** m_currentAd is null");
        }
    }

    private void displayAd(PlayAd playad)
    {
        if(m_currentAd != null)
            m_currentAd.cleanUp();
        ZCast.displayDebug("addisplayer", "AdDisplayer displaying Ad= " + playad.getShortAdId() + " Dsply=" + playad.getAdDisplayType());
        m_currentAd = playad;
        if(m_currentAd.prepareForDisplay(this))
        {
            repaint();
        } else
        {
            ZCast.displayDebug("addisplayer", "current Ad does not display correctly");
            if(m_displayThread != null)
                m_displayThread.interrupt();
        }
    }

    public String toString()
    {
        return "AdDisplayer";
    }

    public void newPlayListNotification(boolean flag)
    {
    }

    public void paint(Graphics g)
    {
        if(m_currentAd != null)
        {
            m_currentAd.renderAd(this);
        } else
        {
            ZCast.displayDebug("addisplayer", "current Ad does not display correctly");
            if(m_displayThread != null)
                m_displayThread.interrupt();
        }
    }

    private void handleException(Exception exception)
    {
        ZCast.displayDebug("addisplayer", "AdDisplayer Interrupted! type=" + m_InterruptFlag);
        if(exception instanceof InterruptedException)
        {
            try
            {
                if(m_InterruptFlag == 1)
                {
                    PlayAd playad = m_playList.getPrevBackWindowPlayAd(this);
                    if(playad == null)
                        setPrevStatus(false);
                    else
                    if(playad.getState() == 1)
                    {
                        setNextStatus(true);
                        m_currentAd.incrAdPrevCount(1);
                        displayAd(playad);
                    }
                    Thread.sleep(m_currentAd.getAdDisplayTime() * 1000);
                    while((tempInt = m_currentAd.getExtendedTime()) != 0) 
                    {
                        ZCast.displayDebug("addisplayer", "going to sleep more");
                        Thread.sleep(tempInt * 1000);
                    }
                    updateSession();
                } else
                if(m_InterruptFlag == 2)
                {
                    PlayAd playad1 = m_playList.getNextBackWindowPlayAd(this);
                    if(playad1 == null)
                        setNextStatus(false);
                    else
                    if(playad1.getState() == 1)
                    {
                        setPrevStatus(true);
                        m_currentAd.incrAdNextCount(1);
                        displayAd(playad1);
                    }
                    Thread.sleep(m_currentAd.getAdDisplayTime() * 1000);
                    while((tempInt = m_currentAd.getExtendedTime()) != 0) 
                    {
                        ZCast.displayDebug("addisplayer", "going to sleep more");
                        Thread.sleep(tempInt * 1000);
                    }
                    updateSession();
                }
            }
            catch(Exception exception1)
            {
                handleException(exception1);
            }
            m_InterruptFlag = 0;
        } else
        {
            ZCast.displayDebug("addisplayer", exception);
        }
    }

    public void run()
    {
        boolean flag = false;
        PlayAd playad;
        while((playad = m_playList.getNextPlayAd(this)) != null) 
        {
            try
            {
                if(needsRetargetting && playad.isInterruptable())
                {
                    needsRetargetting = false;
                    TargetedPlayList.getTargetedPlayList().targetAgain();
                    setInterruptable(playad.isInterruptable());
                    Thread.sleep(playad.getAdDisplayTime() * 1000);
                } else
                {
                    for(int i = 0; playad.getState() == 0 && i < m_maxWaitTime; i++)
                    {
                        ZCast.displayDebug("addisplayer", "AdDisplayer sleeps to wait for Ad=" + playad.getShortAdId());
                        Thread.sleep(1000L);
                    }

                    if(playad.getState() == 0 && playad.isInterruptable())
                    {
                        ZCast.displayDebug("addisplayer", " >>>>>>>>>>>>>>>>>>>> Waiting too long, going to prev ad <<<<<<<<<<<<<<<<<<<<<<<");
                        m_InterruptFlag = 1;
                        throw new InterruptedException();
                    }
                    if(playad.getState() != 1)
                        continue;
                    if(flag)
                        setPrevStatus(true);
                    setInterruptable(playad.isInterruptable());
                    displayAd(playad);
                    playad.setPlayed(true);
                    setNextStatus(false);
                    flag = true;
                    ZCast.displayDebug("addisplayer", "AdDisplayer sleeps for " + m_currentAd.getAdDisplayTime() + " secs");
                    Thread.sleep(m_currentAd.getAdDisplayTime() * 1000);
                    while((tempInt = m_currentAd.getExtendedTime()) != 0) 
                    {
                        ZCast.displayDebug("addisplayer", "going to sleep more");
                        Thread.sleep(tempInt * 1000);
                    }
                    ZCast.displayDebug("addisplayer", "AdDisplayer awakes!\n");
                    updateSession();
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("addisplayer", " Intended exception caught in ad displayer ");
                handleException(exception);
                m_InterruptFlag = 0;
            }
            System.gc();
        }
    }

    public synchronized void setInterrupt(int i)
    {
        if(m_displayThread != null && m_displayThread.isAlive())
        {
            m_InterruptFlag = i;
            m_displayThread.interrupt();
        }
    }

    private void setNextStatus(boolean flag)
    {
        if(m_nextButton != null)
        {
            m_next.setEnabled(flag);
            m_nextButton.setEnabled(flag);
        }
    }

    private void setPrevStatus(boolean flag)
    {
        if(m_prevButton != null)
        {
            m_prevButton.setEnabled(flag);
            m_back.setEnabled(flag);
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void start()
    {
        if(m_displayThread == null)
        {
            m_displayThread = new Thread(this, toString());
            m_displayThread.start();
        }
    }

    private void updateSession()
    {
        m_sessUpd++;
        if(m_sessUpd >= m_sessUpdMax)
        {
            m_sessUpd = 0;
            if(!ZCast.m_demoMode && m_playList != null)
                m_playList.sendStats();
        }
    }

    public boolean changeAdDisplay(PlayAd playad, boolean flag, boolean flag1)
    {
        if(m_playList != null && interruptable)
        {
            if(flag)
                m_playList.changeDCToBefore(this, playad);
            else
                m_playList.changeDCToAfter(this, playad);
            if(flag1)
                m_displayThread.interrupt();
            return true;
        }
        if(m_playList != null)
            needsRetargetting = true;
        return false;
    }

    private void setInterruptable(boolean flag)
    {
        interruptable = flag;
    }

    public static final int PREV_BUTTON = 1;
    public static final int NEXT_BUTTON = 2;
    private int m_sessUpd;
    private int m_sessUpdMax;
    private SequentialPlayList m_playList;
    private PlayAd m_currentAd;
    private int m_InterruptFlag;
    private ZpComponent m_prevButton;
    private ZpComponent m_nextButton;
    private MenuItem m_back;
    private MenuItem m_next;
    private Thread m_displayThread;
    private static AdDisplayer m_theAdDisplayer = null;
    private boolean interruptable;
    private int tempInt;
    private int m_maxWaitTime;
    private boolean needsRetargetting;

}
