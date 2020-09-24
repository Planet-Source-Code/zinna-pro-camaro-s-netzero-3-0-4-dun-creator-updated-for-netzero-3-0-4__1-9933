// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayList.java

package admgmt;

import java.util.Enumeration;
import java.util.Vector;
import nzcom.*;
import tcpBinary.*;

// Referenced classes of package admgmt:
//            PlayAd, RichMediaAd, VideoAd, ImageAd

public abstract class PlayList
{

    public Enumeration elements()
    {
        return m_playAds.elements();
    }

    public void sendStats()
    {
        NZUserLog nzuserlog = NZUserLog.getDefaultUserLog();
        ZCast.displayDebug("PlayList: sending update stats to ulog=" + nzuserlog);
        if(nzuserlog != null)
            nzuserlog.sendSessionUpdateStats(this);
    }

    public String substituteUrlParam(String s, String s1, String s2)
    {
        String s3 = s;
        int i = s.indexOf(s1 + "=");
        if(i >= 0)
        {
            int j = s.indexOf("&", i);
            s3 = s.substring(0, i) + s1 + "=" + s2;
            if(j >= 0)
                s3 = s3 + s.substring(j, s.length());
            ZCast.displayDebug("GM", "-->substituted. New string: " + s3);
        }
        return s3;
    }

    protected PlayList()
    {
        m_playAds = new Vector();
    }

    protected abstract Vector getPlayListData();

    protected abstract void addToPlayList(PlayAd playad);

    protected boolean populatePlayList()
    {
        generatePlayAds(getPlayListData());
        return m_playAds.size() > 0;
    }

    protected void generatePlayAds(Vector vector)
    {
        if(vector != null)
        {
            int i = Integer.parseInt(ServerParms.getParm("ADDisplayTime", "30"));
            int j = Integer.parseInt(ServerParms.getParm("MinADDisplayTime", "5"));
            int k = Integer.parseInt(ServerParms.getParm("MaxADDisplayTime", "300"));
            if(System.getProperty("nz.displayIntv") != null)
            {
                i = Integer.parseInt(System.getProperty("nz.displayIntv"));
                ZCast.displayDebug("setting default display interval to " + i + " seconds");
            }
            for(int l = 0; l < vector.size(); l++)
            {
                String s = (String)vector.elementAt(l);
                PlayAd playad = parseRawAd(s, ';', i, j, k);
                if(playad != null)
                    addToPlayList(playad);
                else
                    ZCast.displayDebug("invalid raw ad " + s);
            }

            ZCast.displayDebug("Playlist created with " + vector.size() + " ads");
        }
    }

    protected PlayAd parseRawAd(String s, char c, int i, int j, int k)
    {
        Object obj = null;
        int l = 0;
        int i1 = 0;
        int j1 = i;
        Vector vector = new Vector(7);
        int k1 = 0;
        int l1;
        for(int i2 = 0; i2 < 5 && (l1 = s.indexOf(c, k1)) != -1; i2++)
            try
            {
                if(i2 < 3)
                {
                    if(i2 == 2)
                    {
                        String s1 = s.substring(k1, l1);
                        String s2 = ServerParms.getParm("GMtag", null);
                        if(s2 != null)
                        {
                            Object obj1 = null;
                            Object obj2 = null;
                            int k2 = s2.indexOf("=");
                            if(k2 >= 0)
                            {
                                String s3 = s2.substring(0, k2);
                                String s4 = s2.substring(k2 + 1, s2.length());
                                s1 = substituteUrlParam(s1, s3, s4);
                            } else
                            {
                                ZCast.displayDebug("GM", "-->improperly formatted parm: " + s2);
                            }
                        } else
                        {
                            ZCast.displayDebug("GM", "-->did not get a gmtag.");
                        }
                        vector.addElement(s1);
                    } else
                    {
                        vector.addElement(s.substring(k1, l1));
                    }
                } else
                if(i2 == 3)
                {
                    l = Integer.parseInt(s.substring(k1, l1));
                    i1 = l % 10;
                    l /= 10;
                } else
                if(i2 == 4)
                {
                    int j2 = Integer.parseInt(s.substring(k1, l1));
                    if(j2 >= j && j2 <= k)
                        j1 = j2;
                }
                k1 = l1 + 1;
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }

        if(ZCast.m_demoMode)
            vector.addElement(vector.elementAt(1));
        else
            vector.addElement(null);
        vector.addElement(null);
        vector.addElement(new Integer(j1));
        vector.addElement(new Integer(l));
        switch(i1)
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
        return ((PlayAd) (obj));
    }

    protected Vector getDataFromServer(String s, Object obj, String s1, String s2)
    {
        Vector vector = null;
        Transaction transaction = new Transaction(false);
        try
        {
            transaction.setServerLocation(ServerParms.getParm(s1, transaction.getServerLocation()));
            TransactionRequest transactionrequest = new TransactionRequest();
            transactionrequest.setClientVersion(ZCast.getZcastVersion());
            transactionrequest.setUserNumber(Initializer.m_userNumber);
            transactionrequest.setSessionId(Initializer.m_sessionId);
            transactionrequest.setDataObject(obj);
            transactionrequest.setUserId(s2);
            TransactionResult transactionresult = transaction.execute(transactionrequest, s);
            ZCast.displayDebug("PlayList: (" + s + ") success flag set to: " + transactionresult.getSuccessFlag());
            if(transactionresult.getSuccessFlag())
            {
                Object obj1 = transactionresult.getDataObject();
                if(obj1 instanceof TransactionResponse)
                {
                    TransactionResponse transactionresponse = (TransactionResponse)obj1;
                    ZCast.displayDebug("PlayList: (" + s + ") return code = " + transactionresponse.getReturnCode());
                    if(transactionresponse.getReturnCode() == 0)
                    {
                        Object obj2 = transactionresponse.getReturnObject();
                        if(obj2 instanceof Vector)
                            vector = (Vector)obj2;
                        else
                            ZCast.displayDebug("PlayList: " + s + " failed to return a valid object.");
                    }
                } else
                {
                    ZCast.displayDebug("PlayList: " + s + " failed to return a TransactionResponse object.");
                }
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return vector;
    }

    protected void writeOutPlayList()
    {
        if(ZCast.m_nzDebugMode.indexOf("playlist") != -1 && m_playAds != null)
        {
            ZCast.displayDebug("T--------------- Begin New PlayList ----------------");
            for(Enumeration enumeration = m_playAds.elements(); enumeration.hasMoreElements(); ZCast.displayDebug("playlist", ""))
            {
                PlayAd playad = (PlayAd)enumeration.nextElement();
                ZCast.displayDebug("playlist", "ID: " + playad.getAdId());
                ZCast.displayDebug("playlist", "Type: " + playad.getAdType());
                ZCast.displayDebug("playlist", "ImageURL: " + playad.getAdImageString());
                ZCast.displayDebug("playlist", "ClickThruURL: " + playad.getAdUrlString());
                ZCast.displayDebug("playlist", "TargetString: " + playad.getTargetString());
            }

        }
    }

    protected Vector m_playAds;
    public static final String DISP_TIME = "30";
    public static final String MIN_DISP_TIME = "5";
    public static final String MAX_DISP_TIME = "300";
}
