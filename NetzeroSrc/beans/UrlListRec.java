// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UrlListRec.java

package beans;

import java.util.*;
import nzcom.ZCast;

// Referenced classes of package beans:
//            UrlListMgr, UrlRec

public class UrlListRec
{

    public UrlListRec(long l)
    {
        bid = l;
        UrlVector = new Vector();
    }

    public void addCloseUrl(Date date)
    {
        String s = new String("CLOSE");
        String s1 = new String("CLOSE");
        addUrl(s, s1, date);
    }

    public void addEndUrl(Date date)
    {
        String s = new String("END");
        String s1 = new String("END");
        addUrl(s, s1, date);
    }

    public void addUrl(String s, String s1, Date date)
    {
        UrlRec urlrec;
        if(UrlVector.size() == 0)
        {
            urlrec = new UrlRec(0, 0L, date, bid, UrlListMgr.assignNewUid(), s, s1);
        } else
        {
            UrlRec urlrec1 = getLastUrl();
            urlrec = new UrlRec(urlrec1.getUrlID(), calcTime(urlrec1.getDateVisited(), date), date, bid, UrlListMgr.assignNewUid(), s, s1);
        }
        UrlVector.addElement(urlrec);
    }

    private long calcTime(Date date, Date date1)
    {
        if(date1.before(date))
        {
            ZCast.displayDebug("something wrong.  System time changed");
            return 0L;
        } else
        {
            long l = date.getTime();
            long l1 = date1.getTime();
            return (l1 - l) / 1000L;
        }
    }

    public long getBid()
    {
        return bid;
    }

    public UrlRec getLastUrl()
    {
        if(UrlVector.size() == 0)
            return null;
        else
            return (UrlRec)UrlVector.elementAt(UrlVector.size() - 1);
    }

    public boolean isClosed()
    {
        return getLastUrl().isClosed();
    }

    public boolean isEmpty()
    {
        return UrlVector.isEmpty();
    }

    public void printUrlList()
    {
        ZCast.displayDebug("########### URL List for Browser ID " + bid + " ############");
        UrlRec urlrec;
        for(Enumeration enumeration = UrlVector.elements(); enumeration.hasMoreElements(); urlrec.printUrl())
            urlrec = (UrlRec)enumeration.nextElement();

    }

    public void processUrls(Vector vector)
    {
        if(UrlVector.isEmpty())
            return;
        UrlRec urlrec = (UrlRec)UrlVector.elementAt(0);
        if(urlrec.ignoreUrl())
        {
            if(UrlVector.size() == 1)
                return;
            UrlVector.removeElementAt(0);
        }
        for(Enumeration enumeration = UrlVector.elements(); enumeration.hasMoreElements(); urlrec.processUrl(vector))
            urlrec = (UrlRec)enumeration.nextElement();

        urlrec = getLastUrl();
        UrlVector.removeAllElements();
        if(urlrec.isClosed())
        {
            bid = -1L;
        } else
        {
            urlrec.ignore();
            UrlVector.addElement(urlrec);
        }
    }

    public void removeUrls()
    {
    }

    private long bid;
    private Vector UrlVector;
}
