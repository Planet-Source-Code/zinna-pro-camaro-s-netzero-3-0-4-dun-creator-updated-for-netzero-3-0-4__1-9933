// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UrlListMgr.java

package beans;

import java.util.*;
import nzcom.ZCast;

// Referenced classes of package beans:
//            UrlListRec

public class UrlListMgr
{

    public UrlListMgr()
    {
        UrlLists = new Vector();
        uid = 0;
    }

    public static int assignNewUid()
    {
        return ++uid;
    }

    public static void closeBrowser(long l, Date date)
    {
        if(UrlLists == null)
        {
            UrlLists = new Vector();
            uid = 0;
            return;
        }
        UrlListRec urllistrec = getList(l);
        if(urllistrec == null)
        {
            return;
        } else
        {
            urllistrec.addCloseUrl(date);
            return;
        }
    }

    public static void endProcessing(Vector vector)
    {
        Date date = new Date();
        Object obj = null;
        if(UrlLists == null)
            return;
        UrlListRec urllistrec;
        for(Enumeration enumeration = UrlLists.elements(); enumeration.hasMoreElements(); urllistrec.processUrls(vector))
        {
            urllistrec = (UrlListRec)enumeration.nextElement();
            if(!urllistrec.isEmpty() && !urllistrec.isClosed())
                urllistrec.addEndUrl(date);
        }

    }

    private static UrlListRec getList(long l)
    {
        boolean flag = false;
        UrlListRec urllistrec = null;
        for(Enumeration enumeration = UrlLists.elements(); enumeration.hasMoreElements();)
        {
            urllistrec = (UrlListRec)enumeration.nextElement();
            if(urllistrec.getBid() == l)
            {
                flag = true;
                break;
            }
        }

        if(flag)
            return urllistrec;
        else
            return null;
    }

    private Vector getVector(long l)
    {
        return null;
    }

    private static UrlListRec makeNewList(long l)
    {
        UrlListRec urllistrec = new UrlListRec(l);
        UrlLists.addElement(urllistrec);
        return urllistrec;
    }

    public static void printAllLists()
    {
        Object obj = null;
        int i = 0;
        ZCast.displayDebug("-------- Number of lists == " + UrlLists.size());
        for(Enumeration enumeration = UrlLists.elements(); enumeration.hasMoreElements();)
        {
            UrlListRec urllistrec = (UrlListRec)enumeration.nextElement();
            urllistrec.printUrlList();
            ZCast.displayDebug("-------- Done printing list " + i + " ---------");
            i++;
        }

    }

    public static void processNewEntry(String s, String s1, long l, Date date)
    {
        if(UrlLists == null)
        {
            UrlLists = new Vector();
            uid = 0;
        }
        UrlListRec urllistrec = getList(l);
        if(urllistrec == null)
            urllistrec = makeNewList(l);
        urllistrec.addUrl(s, s1, date);
    }

    public static void processUrlLists(Vector vector)
    {
        Object obj = null;
        boolean flag = false;
        if(UrlLists == null)
            return;
        Enumeration enumeration = UrlLists.elements();
        ZCast.displayDebug("\nSending URL data to server:");
        UrlListRec urllistrec;
        for(; enumeration.hasMoreElements(); urllistrec.processUrls(vector))
            urllistrec = (UrlListRec)enumeration.nextElement();

        ZCast.displayDebug("End of URL data\n");
    }

    private static Vector UrlLists = null;
    private static int uid = 0;

}
