// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZMsg.java

package tcpBinary;

import java.io.Serializable;
import java.util.Date;

public class NZMsg
    implements Serializable
{

    public NZMsg(int i)
    {
        type = 0;
        ID = 0;
        process = 1;
        freq = 0;
        priority = 4;
        DateCreated = null;
        DateExpire = null;
        DateSent = null;
        DateReceived = null;
        DateProcessed = null;
        AckFlag = 0;
        interval = 0;
        obj = null;
        ID = i;
        DateCreated = new Date();
    }

    public NZMsg(int i, int j)
    {
        this(i);
        type = j;
    }

    public int getAckFlag()
    {
        return AckFlag;
    }

    public Date getDateCreated()
    {
        return DateCreated;
    }

    public Date getDateExpire()
    {
        return DateExpire;
    }

    public Date getDateProcessed()
    {
        return DateProcessed;
    }

    public Date getDateReceived()
    {
        return DateReceived;
    }

    public Date getDateSent()
    {
        return DateSent;
    }

    public int getFreq()
    {
        return freq;
    }

    public int getID()
    {
        return ID;
    }

    public int getInterval()
    {
        return interval;
    }

    public Object getObj()
    {
        return obj;
    }

    public int getPriority()
    {
        return priority;
    }

    public int getProcess()
    {
        return process;
    }

    public int getType()
    {
        return type;
    }

    public boolean isSysMsg()
    {
        return type == 1;
    }

    public boolean isUserMsg()
    {
        return type == 0;
    }

    public void setAckFlag(int i)
    {
        AckFlag = i;
    }

    public void setDateExpire(Date date)
    {
        DateExpire = date;
    }

    public void setDateProcessed(Date date)
    {
        DateProcessed = date;
    }

    public void setDateReceived(Date date)
    {
        DateReceived = date;
    }

    public void setDateSent(Date date)
    {
        DateSent = date;
    }

    public void setFreq(int i)
    {
        freq = i;
    }

    public void setID(int i)
    {
        ID = i;
    }

    public void setInterval(int i)
    {
        interval = i;
    }

    public void setObj(Object obj1)
    {
        obj = obj1;
    }

    public void setPriority(int i)
    {
        if(i < 1 || i > 5)
            priority = 5;
        else
            priority = i;
    }

    public void setProcess(int i)
    {
        process = i;
    }

    protected void setType(int i)
    {
    }

    static final int USR_MSG = 0;
    static final int SYS_MSG = 1;
    public static final int PROCESS_ONCE = 1;
    public static final int PROCESS_INTERVALS = 2;
    public static final int PROCESS_ALWAYS = 3;
    public static final int MIN_SEC = 60;
    public static final int HOUR_SEC = 3600;
    public static final int DAY_SEC = 0x15180;
    public static final int WEEK_SEC = 0x93a80;
    public static final int PRI_1 = 1;
    public static final int PRI_2 = 2;
    public static final int PRI_3 = 3;
    public static final int PRI_4 = 4;
    public static final int PRI_5 = 5;
    public static final int ACK_NO = 0;
    public static final int ACK_YES = 1;
    public static final int ACK_URGENT = 2;
    private int type;
    private int ID;
    private int process;
    private int freq;
    private int priority;
    private Date DateCreated;
    private Date DateExpire;
    private Date DateSent;
    private Date DateReceived;
    private Date DateProcessed;
    private int AckFlag;
    private int interval;
    private Object obj;
}
