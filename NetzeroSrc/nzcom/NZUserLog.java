// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZUserLog.java

package nzcom;

import admgmt.*;
import beans.UrlListMgr;
import errors.NZErrors;
import gui.MemberRecs;
import java.io.File;
import java.text.DateFormat;
import java.util.*;
import tcpBinary.*;
import transaction.NewQuery;
import transaction.SimpleQuery;
import transaction.client.TransactionThread;
import transaction.client.UlogThread;
import util.DateFormatter;

// Referenced classes of package nzcom:
//            ServerParms, ZCast, NZWindow, Initializer, 
//            ConfigParams

public class NZUserLog
{

    public static final NZUserLog getDefaultUserLog()
    {
        if(m_meDefault == null)
            m_meDefault = new NZUserLog(MemberRecs.getCurrentMemberID());
        return m_meDefault;
    }

    private NZUserLog(String s)
    {
        persistData = null;
        ComponentTrackingData = null;
        userid = s;
        createDateTime = new Date();
    }

    public static void deserializeStats()
    {
        File file = new File(UlogThread.SERTRANS);
        if(!file.exists())
            return;
        Object obj = NZErrors.deserialize(UlogThread.SERTRANS, false);
        if(obj == null || !(obj instanceof Vector))
        {
            file.delete();
            return;
        }
        Vector vector = (Vector)obj;
        if(vector.size() <= 0)
            return;
        if(!(vector.elementAt(0) instanceof NewQuery))
        {
            TransactionRequest transactionrequest = new TransactionRequest();
            transactionrequest.setClientVersion(ConfigParams.getVers());
            transactionrequest.setDataObject(vector);
            transactionrequest.setUserNumber(Initializer.m_userNumber);
            transactionrequest.setSessionId(Initializer.m_sessionId);
            transactionrequest.setUserId(Initializer.m_zwindow.getNzUserid());
            SimpleQuery simplequery = new SimpleQuery(transactionrequest, "CREC");
            Vector vector1 = new Vector();
            vector1.addElement(simplequery);
            vector = vector1;
        }
        if(Initializer.m_ulogThread != null)
        {
            for(int i = 0; i < vector.size(); i++)
                try
                {
                    SimpleQuery simplequery1 = (SimpleQuery)vector.elementAt(i);
                    Initializer.m_ulogThread.putRequest(simplequery1);
                    ZCast.displayDebug("ulog", simplequery1.getTransactionCode() + " request deserialized.");
                }
                catch(ClassCastException _ex) { }

        } else
        {
            TransactionRequest transactionrequest1 = new TransactionRequest();
            transactionrequest1.setDataObject(vector);
            transactionrequest1.setClientVersion(ConfigParams.getVers());
            transactionrequest1.setUserNumber(Initializer.m_userNumber);
            transactionrequest1.setSessionId(Initializer.m_sessionId);
            transactionrequest1.setUserId(Initializer.m_zwindow.getNzUserid());
            Transaction transaction = new Transaction(false);
            transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
            transaction.setSoTimeout(0x1d4c0);
            TransactionResult transactionresult = transaction.execute(transactionrequest1, "VOTR");
            if(transactionresult.getSuccessFlag() && (transactionresult.getDataObject() instanceof TransactionResponse) && ((TransactionResponse)transactionresult.getDataObject()).getReturnCode() == 0)
            {
                ZCast.displayDebug("ulog", "VOTR submitted (" + vector.size() + " objects sent)");
                file.delete();
            } else
            {
                ZCast.displayDebug("ulog", "transaction VOTR failed.");
            }
        }
    }

    public Date getCreateDateTime()
    {
        return createDateTime;
    }

    public void sendSessionEndStats()
    {
        if(ZCast.m_demoMode)
        {
            ZCast.displayDebug("ulog", "DemoMode: Skipping sendSessionEndStats function.");
            return;
        }
        Vector vector = new Vector();
        Object obj = MarqueePlayList.getMarqueePlayList();
        processAds(vector, ((PlayList) (obj)));
        obj = TargetedPlayList.getTargetedPlayList();
        if(obj != null)
            processAds(vector, ((PlayList) (obj)));
        vector.addElement("!!!");
        UrlListMgr.endProcessing(vector);
        sendStats("session end", vector);
    }

    public void sendSessionUpdateStats(PlayList playlist)
    {
        if(ZCast.m_demoMode)
        {
            ZCast.displayDebug("ulog", "DemoMode: Skipping sendSessionUpdateStats function.");
            return;
        }
        if(playlist != null)
        {
            Vector vector = new Vector();
            processAds(vector, playlist);
            vector.addElement("!!!");
            UrlListMgr.processUrlLists(vector);
            sendStats("session update", vector);
        }
    }

    private void sendStats(String s, Vector vector)
    {
        sendDateTime = new Date();
        Vector vector1 = new Vector(10);
        vector1.addElement(userid);
        vector1.addElement(s);
        vector1.addElement(DateFormatter.formatDate(createDateTime));
        vector1.addElement(DateFormatter.formatDate(sendDateTime));
        try
        {
            for(int i = 0; i < vector.size(); i++)
                vector1.addElement(vector.elementAt(i));

        }
        catch(Exception _ex) { }
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setClientVersion(ConfigParams.getVers());
        if(persistData == null)
        {
            transactionrequest.setDataObject(vector1);
            ZCast.displayDebug("ulog", "********************");
            ZCast.displayDebug("ulog", "persist data is null");
            for(int j = 0; j < vector1.size(); j++)
                ZCast.displayDebug("ulog", vector1.elementAt(j));

            ZCast.displayDebug("ulog", "********************");
        } else
        {
            Vector avector[] = new Vector[3];
            avector[0] = vector1;
            avector[1] = persistData;
            avector[2] = ComponentTrackingData;
            persistData = null;
            transactionrequest.setDataObject(avector);
            ZCast.displayDebug("ulog", "********************");
            ZCast.displayDebug("ulog", "persist data is not null");
            for(int k = 0; k < avector.length; k++)
                try
                {
                    ZCast.displayDebug("ulog", avector[k]);
                }
                catch(Exception _ex) { }

            ZCast.displayDebug("ulog", "********************");
        }
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setUserId(userid);
        if(Initializer.m_ulogThread != null)
        {
            Initializer.m_ulogThread.putRequest(transactionrequest, "ULOG");
        } else
        {
            Transaction transaction = new Transaction(false);
            transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
            TransactionResult transactionresult = transaction.execute(transactionrequest, "ULOG");
            if(transactionresult.getSuccessFlag() && (transactionresult.getDataObject() instanceof TransactionResponse) && ((TransactionResponse)transactionresult.getDataObject()).getReturnCode() == 0)
            {
                ZCast.displayDebug("ulog", "event " + s + " submitted");
            } else
            {
                ZCast.displayDebug("ulog", "event " + s + " failed");
                UlogThread.addTransactionToFile(transactionrequest, "CREC");
            }
        }
    }

    private void processAds(Vector vector, PlayList playlist)
    {
        if(playlist != null)
        {
            for(Enumeration enumeration = playlist.elements(); enumeration.hasMoreElements();)
            {
                PlayAd playad = (PlayAd)enumeration.nextElement();
                if(playad.isStatsChanged())
                    vector.addElement(playad.getStatsToString(true));
            }

        }
    }

    private void serializeStats(TransactionRequest transactionrequest)
    {
        Vector vector = null;
        Object obj = NZErrors.deserialize("obj.save", true);
        if(obj != null && (obj instanceof Vector))
        {
            vector = (Vector)obj;
            if(vector.size() > 0 && !(vector.elementAt(0) instanceof NewQuery))
            {
                TransactionRequest transactionrequest1 = new TransactionRequest();
                transactionrequest1.setClientVersion(ConfigParams.getVers());
                transactionrequest1.setDataObject(vector);
                transactionrequest1.setUserNumber(Initializer.m_userNumber);
                transactionrequest1.setSessionId(Initializer.m_sessionId);
                transactionrequest1.setUserId(userid);
                SimpleQuery simplequery1 = new SimpleQuery(transactionrequest1, "CREC");
                Vector vector1 = new Vector();
                vector1.addElement(simplequery1);
                vector = vector1;
            }
        } else
        {
            vector = new Vector();
        }
        SimpleQuery simplequery = new SimpleQuery(transactionrequest, "CREC");
        vector.addElement(transactionrequest);
        NZErrors.serialize(UlogThread.SERTRANS, vector);
    }

    protected void setComponentTrackingData(Vector vector)
    {
        ComponentTrackingData = vector;
    }

    protected void setPersistData(Vector vector)
    {
        persistData = vector;
    }

    private String showDateTime(Date date)
    {
        DateFormat dateformat = DateFormat.getDateTimeInstance();
        return dateformat.format(date);
    }

    protected String userid;
    protected Date createDateTime;
    protected Date sendDateTime;
    protected Vector persistData;
    private int xMode;
    private int iDisplayCount;
    private int iPrevCount;
    private int iNextCount;
    private int iRetrievalCount;
    private int iPrintAdCount;
    private int iPrintCouponCount;
    private static final String sepString = "!!!";
    protected Vector ComponentTrackingData;
    private static NZUserLog m_meDefault = null;
    private static final String SESSION_END = "session end";
    private static final String SESSION_UPDATE = "session update";

}
