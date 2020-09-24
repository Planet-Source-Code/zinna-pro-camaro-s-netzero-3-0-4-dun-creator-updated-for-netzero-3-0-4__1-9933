// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoTargetedPlayList.java

package admgmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;
import nzcom.ServerParms;
import nzcom.ZCast;

// Referenced classes of package admgmt:
//            TargetedPlayList, RichMediaAd, VideoAd, ImageAd, 
//            PlayAd, PlayList, AdDisplayer, PlayAdListener

public class DemoTargetedPlayList extends TargetedPlayList
{
    public class DemoInsertionThread extends Thread
        implements PlayAdListener
    {

        public void adDisplayOccured()
        {
            resume();
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
                    try
                    {
                        ZCast.displayDebug(" ====>>the play ad going to be inserted is " + playad.toString());
                        playad.addPlayAdListener(this);
                        addisplayer.changeAdDisplay(playad, flag, flag);
                        flag = false;
                        suspend();
                    }
                    catch(Exception exception)
                    {
                        exception.printStackTrace();
                    }
                if(playad == null)
                    m_currentPLId = null;
            }
        }

        public void setStop(boolean flag)
        {
            m_stop = flag;
        }

        private boolean m_stop;

        public DemoInsertionThread()
        {
            super("Targeted-Play-List Insertion Thread");
            ZCast.displayDebug("DemoInsertionThread");
        }
    }


    private DemoTargetedPlayList()
    {
        m_insertThread = null;
    }

    public DemoTargetedPlayList createDemoTargetedPlayList()
    {
        return getDemoTargetedPlayList();
    }

    public void sendStats()
    {
        ZCast.displayDebug("DemoPlayList: not sending stats.");
    }

    public void targetThis(String s)
    {
        ZCast.displayDebug("DemoTargetedPlayList: Looking for a match for " + s);
        m_playAds = new Vector();
        for(int i = 0; g_targetedPlayAds != null && i < g_targetedPlayAds.size(); i++)
        {
            PlayAd playad = (PlayAd)g_targetedPlayAds.elementAt(i);
            String s1 = playad.getTargetString();
            if(s1.startsWith("="))
            {
                s1 = s1.substring(1, s1.length());
                int j = s.indexOf("?");
                if(s.indexOf(s1) > -1)
                    m_playAds.addElement(playad);
            } else
            {
                if(s1.startsWith("*"))
                    s1 = s1.substring(1, s1.length());
                if(s1.endsWith("*"))
                    s1 = s1.substring(0, s1.length() - 1);
                if(s.indexOf(s1) >= 0)
                {
                    ZCast.displayDebug("\n\nMatch found! PlayAd = " + playad.getShortAdId());
                    ZCast.displayDebug("\tCache file = " + playad.getCacheFile());
                    m_playAds.addElement(playad);
                }
            }
        }

        ZCast.displayDebug("\n\nm_playAds.size() = " + m_playAds.size());
        if(m_playAds.size() > 0)
        {
            stopTargetedAds();
            m_currentAd = 0;
            insertTargetedAds();
        }
    }

    protected void insertTargetedAds()
    {
        if(m_playAds != null && m_playAds.size() > 0)
        {
            m_insertThread = new DemoInsertionThread();
            m_insertThread.start();
            ZCast.displayDebug("TargetedPlayList--> start a new insertion thread.");
        }
    }

    protected void stopTargetedAds()
    {
        if(m_insertThread != null && m_insertThread.isAlive())
        {
            m_insertThread.setStop(true);
            m_insertThread.resume();
            ZCast.displayDebug("TargetedPlayList--> stopping the insertion thread.");
            try
            {
                m_insertThread.join();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            ZCast.displayDebug("TargetedPlayList--> stopped the insertion thread.");
        }
    }

    public static DemoTargetedPlayList getDemoTargetedPlayList()
    {
        if(g_demoTargeted == null)
        {
            g_demoTargeted = new DemoTargetedPlayList();
            if(!g_demoTargeted.populatePlayList())
            {
                ZCast.displayDebug("FRANK", "--> Error populating DemoTargetedPlayList");
                g_demoTargeted = null;
            } else
            {
                ZCast.displayDebug("FRANK", "--> DemoTargetedPlayList created.");
            }
        }
        return g_demoTargeted;
    }

    public Vector getPlayListData()
    {
        return g_targetedPlayAds;
    }

    protected PlayAd parseRawAd(String s, char c, int i, int j, int k)
    {
        Object obj = null;
        try
        {
            Vector vector = new Vector();
            int l = 0;
            StringTokenizer stringtokenizer = new StringTokenizer(s, String.valueOf(c));
            String s1 = stringtokenizer.nextToken();
            vector.addElement(stringtokenizer.nextToken());
            vector.addElement(stringtokenizer.nextToken());
            vector.addElement(stringtokenizer.nextToken());
            vector.addElement(vector.elementAt(1));
            int i1 = Integer.parseInt(stringtokenizer.nextToken());
            l = i1 % 10;
            i1 /= 10;
            vector.addElement(s1);
            vector.addElement(new Integer(stringtokenizer.nextToken()));
            vector.addElement(new Integer(i1));
            switch(l)
            {
            case 0: // '\0'
            case 1: // '\001'
                obj = new ImageAd(vector);
                break;

            case 2: // '\002'
                obj = new VideoAd(vector);
                break;

            case 4: // '\004'
            case 6: // '\006'
                obj = new RichMediaAd(vector);
                break;

            case 3: // '\003'
            case 5: // '\005'
            default:
                obj = new ImageAd(vector);
                break;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return ((PlayAd) (obj));
    }

    public boolean populatePlayList()
    {
        g_targetedPlayAds = new Vector();
        int i = Integer.parseInt(ServerParms.getParm("ADDisplayTime", "30"));
        int j = Integer.parseInt(ServerParms.getParm("MinADDisplayTime", "5"));
        int k = Integer.parseInt(ServerParms.getParm("MaxADDisplayTime", "300"));
        try
        {
            String s = null;
            BufferedReader bufferedreader = new BufferedReader(new FileReader(targetPlayListFile));
            while((s = bufferedreader.readLine()) != null) 
                if(!s.startsWith("#"))
                {
                    PlayAd playad = parseRawAd(s, ';', i, j, k);
                    if(playad != null)
                        g_targetedPlayAds.addElement(playad);
                    ZCast.displayDebug(s);
                }
            bufferedreader.close();
            return true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return false;
    }

    private static DemoTargetedPlayList g_demoTargeted = null;
    private static Vector g_targetedPlayAds = null;
    private static String targetPlayListFile = "demo/targetplaylist.txt";
    private DemoInsertionThread m_insertThread;

}
