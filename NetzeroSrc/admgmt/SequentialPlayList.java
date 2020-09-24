// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SequentialPlayList.java

package admgmt;

import gui.MemberRecs;
import java.util.*;
import nzcom.*;
import sysinfo.SysInfo;
import sysinfo.SysInfoDriver;
import util.Link;

// Referenced classes of package admgmt:
//            PlayList, PlayAd, PlayListConsumer

public class SequentialPlayList extends PlayList
    implements Enumeration
{
    private class MultiPlayListHandler extends Thread
    {

        public void run()
        {
            ZCast.displayDebug("newlist", "\n -->>> NEW Playlist is about to be downloaded \n");
            Link link = m_head.getPrev();
            if(populatePlayListXX())
            {
                writeOutPlayList();
                Link link1 = link.getNext();
                if(link1 != m_head)
                {
                    Link link2 = m_head.getNext();
                    ZCast.displayDebug("newlist", ">>>--> Start of old list=" + link2.getId() + " End of old list=" + link.getId());
                    ZCast.displayDebug("newlist", ">>>--> Start of new list=" + link1.getId() + " End of new list=" + m_head.getPrev().getId() + "\n");
                    ZCast.displayDebug("newlist", ">>>--> hasloop= " + m_hasLoop);
                    for(Enumeration enumeration = m_playListConsumerList.keys(); enumeration.hasMoreElements();)
                    {
                        Object obj = enumeration.nextElement();
                        Link link3 = (Link)m_playListConsumerList.get(obj);
                        if(link3 == link2 || link3 == m_head || m_hasLoop)
                        {
                            m_playListConsumerList.put(obj, link1);
                            ZCast.displayDebug("newlist", "Updating consumer:" + obj.toString());
                        }
                    }

                    ZCast.displayDebug("newlist", ">>>--> m_maxBackWindowSize= " + m_maxBackWindowSize);
                    ZCast.displayDebug("newlist", ">>>--> Old m_backLimitMarker= " + m_backLimitMarker.getId());
                    ZCast.displayDebug("newlist", ">>>--> Old m_backWindowSize= " + m_backWindowSize);
                    if(m_hasLoop)
                    {
                        m_backLimitMarker = link1;
                        int i;
                        for(i = 0; i < m_maxBackWindowSize && m_backLimitMarker != m_head; i++)
                            m_backLimitMarker = m_backLimitMarker.getPrev();

                        m_backWindowSize = i;
                    }
                    if(m_backLimitMarker != m_head)
                    {
                        while(link2 != m_backLimitMarker) 
                            if(link2.getObject() != null)
                            {
                                PlayAd playad = (PlayAd)link2.getObject();
                                playad.cleanUpCache();
                                link2 = link2.getNext();
                                link2.getPrev().cleanUp();
                            }
                        m_head.setNext(m_backLimitMarker);
                        m_backLimitMarker.setPrev(m_head);
                    }
                    ZCast.displayDebug("newlist", ">>>--> New m_head next= " + m_head.getNext().getId());
                    ZCast.displayDebug("newlist", ">>>--> New m_backLimitMarker= " + m_backLimitMarker.getId());
                    ZCast.displayDebug("newlist", ">>>--> New m_backWindowSize= " + m_backWindowSize);
                    ZCast.displayDebug("newlist", "\n -->>> New playlist arrived! signaling resume to all PlayListConsumers.\n");
                    newPlayListNotificationToConsumers(m_hasLoop);
                    m_hasLoop = false;
                }
            }
        }

        private MultiPlayListHandler()
        {
            super("Multi-Play-List Handler");
        }

        MultiPlayListHandler(_cls1 _pcls1)
        {
            this();
        }
    }

    static class _cls1
    {

        _cls1()
        {
        }
    }


    public static final SequentialPlayList getSequentialPlayList(int i, int j)
    {
        if(m_meDefault == null)
        {
            m_meDefault = new SequentialPlayList(i, j);
            if(!m_meDefault.populatePlayList())
                m_meDefault = null;
            ZCast.displayDebug("pl1", "SequentialPlayList creation returned " + m_meDefault);
        }
        return m_meDefault;
    }

    public void addConsumer(PlayListConsumer playlistconsumer)
    {
        try
        {
            if(m_playListConsumerList != null)
                m_playListConsumerList.put(playlistconsumer, m_head.getNext());
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("pl1", exception);
        }
        ZCast.displayDebug("pl1", "PlayList Added consumer: " + playlistconsumer.toString());
    }

    public void clearPlayListConsumers()
    {
        if(m_playListConsumerList != null)
            m_playListConsumerList.clear();
    }

    public void newPlayListNotificationToConsumers(boolean flag)
    {
        try
        {
            if(m_playListConsumerList != null)
            {
                PlayListConsumer playlistconsumer;
                for(Enumeration enumeration = m_playListConsumerList.keys(); enumeration.hasMoreElements(); playlistconsumer.newPlayListNotification(flag))
                {
                    Object obj = enumeration.nextElement();
                    ZCast.displayDebug("pl1", "Notifying consumer: " + obj.toString());
                    playlistconsumer = (PlayListConsumer)obj;
                }

            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("pl1", exception);
        }
    }

    public PlayListConsumer getDrivingConsumer()
    {
        return m_drivingConsumer;
    }

    public void setDrivingConsumer(PlayListConsumer playlistconsumer)
    {
        if(m_playListConsumerList != null && m_playListConsumerList.containsKey(playlistconsumer))
        {
            m_drivingConsumer = playlistconsumer;
            ZCast.displayDebug("pl1", "SequentialPlayList Driving consumer is: " + playlistconsumer);
        }
    }

    public PlayAd getNextPlayAd(PlayListConsumer playlistconsumer)
    {
        PlayAd playad = null;
        try
        {
            if(m_playListConsumerList != null)
            {
                Link link = (Link)m_playListConsumerList.get(playlistconsumer);
                if(playlistconsumer.equals(m_drivingConsumer) && link == m_multiplePlayListTrigger)
                {
                    MultiPlayListHandler multiplaylisthandler = new MultiPlayListHandler(null);
                    multiplaylisthandler.start();
                    m_multiplePlayListTrigger = null;
                }
                if(link == m_head)
                {
                    if(playlistconsumer.equals(m_drivingConsumer))
                    {
                        m_hasLoop = true;
                        link = link.getNext();
                        m_prevNextLoc = link;
                        m_currentDCLocation = link;
                        playad = (PlayAd)link.getObject();
                        if(m_backWindowSize < m_maxBackWindowSize)
                            m_backWindowSize = 0;
                    } else
                    {
                        playad = null;
                    }
                    m_playListConsumerList.put(playlistconsumer, link.getNext());
                } else
                {
                    playad = (PlayAd)link.getObject();
                    if(playlistconsumer.equals(m_drivingConsumer))
                    {
                        m_prevNextLoc = link;
                        m_currentDCLocation = link;
                        if(link.getNext() == m_head)
                        {
                            ZCast.displayDebug("pl1", "End-Zone reached! signaling resume to all PlayListConsumers.");
                            newPlayListNotificationToConsumers(false);
                        }
                        if(++m_backWindowSize > m_maxBackWindowSize)
                            updateBackLimitMarker();
                    }
                    m_playListConsumerList.put(playlistconsumer, link.getNext());
                }
                if(playad != null)
                    ZCast.displayDebug("pl1", "PlayList getNext lnk=" + link.getId() + " id=" + playad.getShortAdId() + " type=" + playad.getAdType() + " Dsply=" + playad.getAdDisplayType() + " consumer=" + playlistconsumer.toString());
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("pl1", exception);
        }
        return playad;
    }

    public PlayAd getNextBackWindowPlayAd(PlayListConsumer playlistconsumer)
    {
        PlayAd playad = null;
        if(playlistconsumer != null && playlistconsumer.equals(m_drivingConsumer))
        {
            if(m_prevNextLoc != m_currentDCLocation)
            {
                m_prevNextLoc = getLogicalNext(m_prevNextLoc);
                playad = (PlayAd)m_prevNextLoc.getObject();
            }
            ZCast.displayDebug("pl1", ">>>->>> Forward to: " + m_prevNextLoc.getId());
        }
        return playad;
    }

    public PlayAd getPrevBackWindowPlayAd(PlayListConsumer playlistconsumer)
    {
        PlayAd playad = null;
        if(playlistconsumer != null && playlistconsumer.equals(m_drivingConsumer))
        {
            if(m_prevNextLoc != m_backLimitMarker && (m_prevNextLoc.getPrev() != m_head || m_backLimitMarker != m_head))
            {
                if(m_prevNextLoc == m_currentDCLocation)
                {
                    m_playListConsumerList.put(m_drivingConsumer, m_prevNextLoc);
                    m_backWindowSize--;
                }
                m_prevNextLoc = getLogicalPrev(m_prevNextLoc);
                playad = (PlayAd)m_prevNextLoc.getObject();
            }
            ZCast.displayDebug("pl1", "<<<-<<< Backward to: " + m_prevNextLoc.getId());
        }
        return playad;
    }

    public void changeDCToBefore(PlayListConsumer playlistconsumer, PlayAd playad)
    {
        if(playad != null && playlistconsumer.equals(m_drivingConsumer))
        {
            Link link = insertBefore(playad, m_currentDCLocation);
            m_playListConsumerList.put(m_drivingConsumer, link);
            m_backWindowSize--;
        }
    }

    public void changeDCToAfter(PlayListConsumer playlistconsumer, PlayAd playad)
    {
        if(playad != null && playlistconsumer.equals(m_drivingConsumer))
        {
            Link link = insertAfter(playad, m_currentDCLocation);
            m_playListConsumerList.put(m_drivingConsumer, link);
        }
    }

    public Enumeration elements()
    {
        if(m_head != null && m_head.getNext() != m_head)
            m_enumNext = m_head.getNext();
        return this;
    }

    public boolean hasMoreElements()
    {
        return m_enumNext != m_head;
    }

    public Object nextElement()
    {
        Object obj = m_enumNext.getObject();
        m_enumNext = m_enumNext.getNext();
        return obj;
    }

    protected SequentialPlayList(int i, int j)
    {
        MAX_DISPLAY_TIME = 300;
        MIN_DISPLAY_TIME = 5;
        runningID = 0;
        m_head = new Link(null);
        m_head.setNext(m_head);
        m_head.setPrev(m_head);
        m_playListConsumerList = new Hashtable(10);
        m_drivingConsumer = null;
        m_multiplePlayListTrigger = null;
        m_startMultiPlayListFetch = i;
        m_backLimitMarker = m_head;
        m_backWindowSize = 0;
        m_maxBackWindowSize = j;
        m_currentDCLocation = null;
        m_hasLoop = false;
        m_newListArrived = false;
        m_enumNext = m_head;
    }

    protected boolean populatePlayList()
    {
        boolean flag = false;
        generatePlayAds(getPlayListData());
        Link link = m_head.getPrev();
        if(link != null && link != m_head)
        {
            if(m_startMultiPlayListFetch > -1)
            {
                m_multiplePlayListTrigger = link;
                for(int i = 0; i < m_startMultiPlayListFetch; i++)
                    m_multiplePlayListTrigger = m_multiplePlayListTrigger.getPrev();

            }
            flag = true;
        }
        return flag;
    }

    protected boolean populatePlayListXX()
    {
        boolean flag = false;
        generatePlayAds(getPlayListData());
        Link link = m_head.getPrev();
        if(link != null && link != m_head)
        {
            if(m_startMultiPlayListFetch > -1)
            {
                m_multiplePlayListTrigger = link;
                for(int i = 0; i < m_startMultiPlayListFetch; i++)
                    m_multiplePlayListTrigger = m_multiplePlayListTrigger.getPrev();

            }
            flag = true;
        }
        return flag;
    }

    protected void addToPlayList(PlayAd playad)
    {
        if(playad != null)
            append(playad);
    }

    protected Vector getPlayListData()
    {
        if(!m_hardwareUpdated)
        {
            SysInfoDriver.updateHardwareProfile();
            m_hardwareUpdated = true;
        }
        Vector vector = null;
        String s = ZCast.m_profile.getPhoneLocation();
        Vector vector1 = new Vector(2);
        Vector vector2 = new Vector(6);
        vector2.addElement(MemberRecs.getCurrentMemberID());
        vector2.addElement(s.substring(0, 3));
        vector2.addElement(s.substring(4, 7));
        vector2.addElement(s.substring(13, 15));
        vector2.addElement(s.substring(16));
        vector2.addElement(NZUserLog.getDefaultUserLog().getCreateDateTime());
        vector1.addElement(vector2);
        int i = SysInfoDriver.getOldSystemInfo().getAdDisplayType();
        ZCast.displayDebug("pl1", "\nAbout to perform PLAY: My machine's ad display type=" + i + "\n");
        vector1.addElement("0:" + i + "|");
        Vector vector3 = vector1;
        vector = getDataFromServer("PLAY", vector3, "plip1", MemberRecs.getCurrentMemberID());
        return vector;
    }

    protected void append(Object obj)
    {
        Link link = new Link(obj);
        link.setId(runningID++);
        link.setPrev(m_head.getPrev());
        link.setNext(m_head);
        m_head.getPrev().setNext(link);
        m_head.setPrev(link);
    }

    protected Link insertBefore(Object obj, Link link)
    {
        Link link1 = new Link(obj);
        link1.setPrev(link.getPrev());
        link1.setNext(link);
        link.getPrev().setNext(link1);
        link.setPrev(link1);
        return link1;
    }

    protected Link insertAfter(Object obj, Link link)
    {
        Link link1 = new Link(obj);
        link1.setPrev(link);
        link1.setNext(link.getNext());
        link.getNext().setPrev(link1);
        link.setNext(link1);
        return link1;
    }

    protected Link removeLink(Link link)
    {
        Link link1 = link;
        if(link != m_head && link != null)
        {
            link1 = link.getNext();
            link1.setPrev(link.getPrev());
            link.getPrev().setNext(link1);
            link.setPrev(null);
            link.setNext(null);
            if(link1 == m_head)
                link1 = link1.getNext();
        }
        return link1;
    }

    protected void writeOutPlayList()
    {
        if(ZCast.m_nzDebugMode.indexOf("playlist") != -1 && m_head != null)
        {
            ZCast.displayDebug("playlist", "S--------------- Begin New PlayList ----------------");
            for(Link link = m_head; (link = link.getNext()) != m_head;)
            {
                PlayAd playad = (PlayAd)link.getObject();
                ZCast.displayDebug("playlist", "#: " + link.getId());
                ZCast.displayDebug("playlist", "ID: " + playad.getAdId());
                ZCast.displayDebug("playlist", "Type: " + playad.getAdType());
                ZCast.displayDebug("playlist", "ImageURL: " + playad.getAdImageString());
                ZCast.displayDebug("playlist", "ClickThruURL: " + playad.getAdUrlString());
                ZCast.displayDebug("playlist", "TargetString: " + playad.getTargetString());
                ZCast.displayDebug("playlist", "");
            }

        }
    }

    private Link getLogicalNext(Link link)
    {
        Link link1 = link.getNext();
        return link1 != m_head ? link1 : link1.getNext();
    }

    private Link getLogicalPrev(Link link)
    {
        Link link1 = link.getPrev();
        return link1 != m_head ? link1 : link1.getPrev();
    }

    private void updateBackLimitMarker()
    {
        PlayAd playad = (PlayAd)m_backLimitMarker.getObject();
        if(m_backLimitMarker != m_head && playad.getAdType() == 1)
        {
            playad.cleanUpCache();
            m_backLimitMarker = removeLink(m_backLimitMarker);
        } else
        {
            m_backLimitMarker = getLogicalNext(m_backLimitMarker);
        }
        m_backWindowSize--;
        ZCast.displayDebug("pl1", "New BackMarker loc= " + m_backLimitMarker.getId() + " backWindowSize=" + m_backWindowSize);
    }

    protected int m_startMultiPlayListFetch;
    protected Link m_head;
    protected Link m_enumNext;
    protected Link m_multiplePlayListTrigger;
    private boolean m_newListArrived;
    private Link m_backLimitMarker;
    private int m_backWindowSize;
    private int m_maxBackWindowSize;
    private Link m_prevNextLoc;
    private boolean m_hasLoop;
    private Hashtable m_playListConsumerList;
    protected PlayListConsumer m_drivingConsumer;
    protected Link m_currentDCLocation;
    private int MAX_DISPLAY_TIME;
    private int MIN_DISPLAY_TIME;
    private static SequentialPlayList m_meDefault = null;
    private static boolean m_hardwareUpdated = false;
    private int runningID;









}
