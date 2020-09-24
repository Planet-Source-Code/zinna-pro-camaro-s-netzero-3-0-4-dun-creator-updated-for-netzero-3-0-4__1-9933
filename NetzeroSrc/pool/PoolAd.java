// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoolAd.java

package pool;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import nzcom.ZCast;

public class PoolAd
    implements Serializable
{

    protected PoolAd()
    {
        isDownloading = true;
        m_err_count = 0;
        isDefault = false;
    }

    protected PoolAd(String s)
    {
        isDownloading = true;
        m_err_count = 0;
        isDefault = false;
        m_sImageUrl = s;
    }

    public PoolAd(String s, String s1)
    {
        isDownloading = true;
        m_err_count = 0;
        isDefault = false;
        m_sImageUrl = s1;
        m_sAdId = s;
    }

    public void addError()
    {
        m_err_count++;
    }

    protected void copy(PoolAd poolad)
    {
        m_sAdIdString = poolad.m_sAdIdString;
        m_sImageUrl = poolad.m_sImageUrl;
        m_sClickUrl = poolad.m_sClickUrl;
        m_sAdType = poolad.m_sAdType;
        m_sCacheFile = poolad.m_sCacheFile;
        m_iLength = poolad.m_iLength;
        m_iAdType = poolad.m_iAdType;
        m_iDisplayType = poolad.m_iDisplayType;
        m_dteStart = poolad.m_dteStart;
        m_dteEnd = poolad.m_dteEnd;
        isDownloading = poolad.isDownloading;
        isDefault = poolad.isDefault;
        m_err_count = 0;
    }

    public String getAdId()
    {
        return m_sAdId;
    }

    public String getAdIdString()
    {
        return m_sAdIdString;
    }

    public int getAdType()
    {
        return m_iAdType;
    }

    public String getCacheFile()
    {
        return m_sCacheFile;
    }

    public boolean getDownloading()
    {
        return isDownloading;
    }

    public long getEndDate()
    {
        return m_dteEnd;
    }

    public int getErrorCount()
    {
        return m_err_count;
    }

    public int getLength()
    {
        return m_iLength;
    }

    public long getStartDate()
    {
        return m_dteStart;
    }

    public void setAdId(String s)
    {
        m_sAdId = s;
    }

    public void setAdType(String s)
    {
        m_sAdType = s;
    }

    public void setAdUrlString(String s)
    {
        m_sClickUrl = s;
    }

    protected void setDownloading(boolean flag)
    {
        isDownloading = flag;
    }

    public void setEndDate(long l)
    {
        m_dteEnd = l;
    }

    public void setEndDate(String s)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try
        {
            date = simpledateformat.parse(s);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
            date = null;
        }
        if(date != null)
            m_dteEnd = date.getTime();
    }

    protected void setLength(int i)
    {
        m_iLength = i;
    }

    protected void setStartDate(long l)
    {
        m_dteStart = l;
    }

    protected void setStartDate(String s)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try
        {
            date = simpledateformat.parse(s);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
            date = null;
        }
        if(date != null)
            m_dteStart = date.getTime();
    }

    protected int toInt()
    {
        int i;
        try
        {
            i = Integer.parseInt(m_sAdId);
        }
        catch(Exception _ex)
        {
            i = 0;
        }
        return i;
    }

    protected Vector toVector()
    {
        Vector vector = new Vector(7);
        vector.addElement(m_sAdIdString);
        vector.addElement(m_sImageUrl);
        vector.addElement(m_sClickUrl);
        vector.addElement(m_sCacheFile);
        vector.addElement("");
        Integer integer = new Integer(m_iLength);
        vector.addElement(integer);
        Integer integer1 = new Integer(m_iAdType);
        vector.addElement(integer1);
        return vector;
    }

    private String m_sAdId;
    protected String m_sAdIdString;
    protected String m_sImageUrl;
    protected String m_sClickUrl;
    protected String m_sAdType;
    protected String m_sCacheFile;
    protected int m_iLength;
    protected int m_iAdType;
    protected int m_iDisplayType;
    protected long m_dteStart;
    protected long m_dteEnd;
    protected boolean isDownloading;
    private int m_err_count;
    protected boolean isDefault;
}
