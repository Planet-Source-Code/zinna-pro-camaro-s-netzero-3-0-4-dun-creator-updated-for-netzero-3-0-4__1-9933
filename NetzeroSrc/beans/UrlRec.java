// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UrlRec.java

package beans;

import java.util.Date;
import java.util.Vector;
import nzcom.ZCast;
import util.DateFormatter;

public class UrlRec
{

    public UrlRec(int i, long l, Date date, long l1, int j, 
            String s, String s1)
    {
        PrevUrlID = i;
        duration = l;
        DateVisited = date;
        BrowserID = l1;
        UrlID = j;
        resource = s;
        UrlString = s1;
    }

    private String buildDbString()
    {
        String s = new String();
        s = s.concat("|");
        if(PrevUrlID == 0)
            s = s.concat("|BEGIN");
        else
            s = s.concat("|" + PrevUrlID);
        s = s.concat("|" + duration);
        s = s.concat("|" + DateFormatter.formatDate(DateVisited));
        s = s.concat("|" + BrowserID);
        s = s.concat("|" + UrlID);
        s = s.concat("|" + resource);
        s = s.concat("|" + UrlString);
        return s;
    }

    public long getBrowserID()
    {
        return BrowserID;
    }

    public Date getDateVisited()
    {
        return DateVisited;
    }

    public long getDuration()
    {
        return duration;
    }

    public int getPrevUrlID()
    {
        return PrevUrlID;
    }

    public String getResource()
    {
        return resource;
    }

    public int getUrlID()
    {
        return UrlID;
    }

    public String getUrlString()
    {
        return UrlString;
    }

    public void ignore()
    {
        PrevUrlID = -1;
    }

    public boolean ignoreUrl()
    {
        return PrevUrlID == -1;
    }

    public boolean isClosed()
    {
        return getResource().equals("CLOSE");
    }

    public void printUrl()
    {
        ZCast.displayDebug("\t--------URL--------");
        ZCast.displayDebug("\tPrevUrlID == " + PrevUrlID);
        ZCast.displayDebug("\tduration == " + duration);
        ZCast.displayDebug("\tDateVisited == " + DateVisited);
        ZCast.displayDebug("\tBrowserID == " + BrowserID);
        ZCast.displayDebug("\tUrlID == " + UrlID);
        ZCast.displayDebug("\tresource == " + resource);
        ZCast.displayDebug("\tUrlString == " + UrlString);
    }

    public void processUrl(Vector vector)
    {
        String s = buildDbString();
        ZCast.displayDebug(s);
        vector.addElement(s);
    }

    public static final int NULL_URL_ID = 0;
    public static final int IGNORE_URL = -1;
    public static final String CLOSE = "CLOSE";
    public static final String END = "END";
    private int PrevUrlID;
    private long duration;
    private Date DateVisited;
    private long BrowserID;
    private int UrlID;
    private String resource;
    private String UrlString;
}
