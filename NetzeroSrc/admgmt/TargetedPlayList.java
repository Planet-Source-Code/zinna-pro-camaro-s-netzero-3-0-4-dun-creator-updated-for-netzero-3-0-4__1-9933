// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TargetedPlayList.java

package admgmt;

import java.util.Vector;
import nzcom.*;

// Referenced classes of package admgmt:
//            PlayList, PlayAd, RTAdList, AdDisplayer, 
//            PlayAdListener

public class TargetedPlayList extends PlayList
{
    public class InsertionThread extends Thread
        implements PlayAdListener
    {

        public void adDisplayOccured()
        {
            if(isAlive())
            {
                ZCast.displayDebug("targ", "Targeted Ad displayed!! m_stop=" + m_stop);
                resume();
            }
        }

        public void adDisplayControlDisclosure(Object obj)
        {
        }

        public void run()
        {
            PlayAd playad = null;
            m_stop = false;
            AdDisplayer addisplayer = AdDisplayer.getAdDisplayer();
            boolean flag = true;
            if(addisplayer != null)
            {
                while(!m_stop && (playad = getNextPlayAd()) != null) 
                    if(playad.downloadBanner())
                    {
                        playad.addPlayAdListener(this);
                        if(!addisplayer.changeAdDisplay(playad, flag, flag))
                        {
                            goBackOneAd();
                            playad.clearPlayAdListeners();
                        } else
                        {
                            flag = false;
                        }
                        if(!m_stop)
                        {
                            ZCast.displayDebug("targ", "Target insertion thread suspending...");
                            suspend();
                            ZCast.displayDebug("targ", "Target insertion thread resuming!! m_stop=" + m_stop);
                        }
                    }
                if(playad == null)
                    m_currentPLId = null;
            }
            ZCast.displayDebug("targ", "Target insertion thread exiting!!");
        }

        private boolean m_stop;



        public InsertionThread()
        {
            super("Targeted-Play-List Insertion Thread");
        }
    }


    public TargetedPlayList()
    {
        m_order = null;
        m_rawAds = null;
        m_currentPLId = null;
        m_insertThread = null;
        m_currentAd = 0;
        m_loopAmt = 0;
        m_loopCount = 0;
        String s = ServerParms.getParm("lvip1", "lv.netzero.net");
        int i = 5000;
        int j = s.indexOf(58);
        if(j != -1)
        {
            i = Integer.parseInt(s.substring(j + 1));
            s = s.substring(0, j);
        }
        String s1 = ZCast.m_profile.getPhoneLocation();
        if(s1 != null && !s1.equals(""))
            m_trAdList = new RTAdList(s, i, 6, s1.substring(0, 3));
        m_currentTP = this;
    }

    public static TargetedPlayList getTargetedPlayList()
    {
        return m_currentTP;
    }

    public void targetThis(String s)
    {
        if(isTargeted(s))
        {
            ZCast.displayDebug("targ", "TargetedPlayList--> targeted url=" + s);
            stopTargetedAds();
            m_currentAd = 0;
            insertTargetedAds();
        } else
        if(m_loopAmt != 0)
        {
            ZCast.displayDebug("targ", "TargetedPlayList--> STOPPING targeted playlist loop!!");
            stopTargetedAds();
            m_currentAd = 0;
            m_loopAmt = 0;
            m_currentPLId = null;
        }
    }

    protected boolean isTargeted(String s)
    {
        boolean flag = false;
        if(isNewPlayList(m_trAdList.findAdList(s)))
            flag = populatePlayList();
        return flag;
    }

    protected boolean isNewPlayList(Vector vector)
    {
        boolean flag = false;
        ZCast.displayDebug("targ", "Targeted rawAds=" + vector);
        if(vector != null && vector.size() > 1)
            try
            {
                String s = (String)vector.firstElement();
                s.trim();
                ZCast.displayDebug("targ", "Targeted playlist id is " + s);
                if(m_currentPLId == null || !s.equals(m_currentPLId))
                {
                    m_currentPLId = s;
                    vector.removeElementAt(0);
                    m_rawAds = vector;
                    flag = true;
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
        return flag;
    }

    protected void addToPlayList(PlayAd playad)
    {
        if(playad != null)
        {
            ZCast.displayDebug("targ", "Pad.getAdId=" + playad.getAdId());
            if(playad.getAdId().startsWith("LOOP"))
            {
                m_loopCount = 0;
                m_loopAmt = -1;
                ZCast.displayDebug("targ", "We have a looping targeted playlist amt=" + m_loopAmt);
            } else
            {
                m_playAds.addElement(playad);
            }
        }
    }

    protected Vector getPlayListData()
    {
        m_playAds.removeAllElements();
        return m_rawAds;
    }

    protected void insertTargetedAds()
    {
        if(m_playAds != null && m_playAds.size() > 0)
        {
            m_insertThread = new InsertionThread();
            m_insertThread.start();
            ZCast.displayDebug("targ", "TargetedPlayList--> start a new insertion thread.");
        }
    }

    public void targetAgain()
    {
        if(m_insertThread != null && m_insertThread.isAlive())
            m_insertThread.resume();
        ZCast.displayDebug("targ", "TargetAgain!!..");
    }

    protected void stopTargetedAds()
    {
        if(m_insertThread != null && m_insertThread.isAlive())
        {
            m_insertThread.m_stop = true;
            m_insertThread.resume();
            ZCast.displayDebug("targ", "TargetedPlayList--> stopping the insertion thread.");
        }
    }

    protected PlayAd getNextPlayAd()
    {
        PlayAd playad = null;
        ZCast.displayDebug("targ", "Targeted getNextPlayAd index=" + m_currentAd + " size=" + m_playAds.size());
        if(m_playAds != null && m_currentAd >= 0 && m_currentAd < m_playAds.size())
        {
            playad = (PlayAd)m_playAds.elementAt(m_currentAd);
            m_currentAd++;
            ZCast.displayDebug("targ", "  Ad=" + playad.getAdUrlString());
            if(m_currentAd >= m_playAds.size() && (m_loopAmt == -1 || m_loopCount++ < m_loopAmt))
            {
                ZCast.displayDebug("targ", "Targeted Ad loops! resetting index to beginning.");
                m_currentAd = 0;
            }
        }
        return playad;
    }

    protected void goBackOneAd()
    {
        if(m_playAds != null && m_currentAd > 0)
            m_currentAd--;
        ZCast.displayDebug("targ", "Targeted goBackOneAd index=" + m_currentAd);
    }

    private static TargetedPlayList m_currentTP = null;
    private Vector m_rawAds;
    protected int m_currentAd;
    private RTAdList m_trAdList;
    private Vector m_order;
    protected String m_currentPLId;
    private InsertionThread m_insertThread;
    private int m_loopAmt;
    private int m_loopCount;
    private static final char DELIMITER = 59;
    private static final int DISPLAY_TYPE = 6;
    private static final int DEFAULT_PORT = 5000;
    private static final String DEFAULT_IP = "lv.netzero.net";
    private static final String LOOP_MODE = "LOOP";

}
