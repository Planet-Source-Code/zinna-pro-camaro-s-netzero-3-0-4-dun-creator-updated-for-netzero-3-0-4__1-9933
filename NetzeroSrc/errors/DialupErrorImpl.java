// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialupErrorImpl.java

package errors;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;
import nzcom.ZCast;

// Referenced classes of package errors:
//            SimpleError, DialupError

public class DialupErrorImpl extends SimpleError
    implements DialupError
{

    public DialupErrorImpl(String s, int i, Date date, String s1, String s2)
    {
        m_strUserId = s;
        m_nErrorCode = new Integer(i);
        m_date = date;
        m_strPhoneNumber = s1;
        m_strLocationNumber = s2;
    }

    public DialupErrorImpl(String s, Integer integer, Date date, String s1, String s2)
    {
        m_strUserId = s;
        m_nErrorCode = integer;
        m_date = date;
        m_strPhoneNumber = s1;
        m_strLocationNumber = s2;
    }

    void dump(PrintStream printstream)
    {
        super.dump(printstream);
        if(ZCast.m_nzDebugMode.equals("on"))
        {
            printstream.println("Timestamp: " + getTimestamp());
            printstream.println("Error code: " + getErrorCode());
            printstream.println("Phone #: " + getPhoneNumber());
            printstream.println("Location #: " + getLocationNumber());
        }
    }

    public Date getDate()
    {
        return m_date;
    }

    public String getErrorCode()
    {
        return m_nErrorCode.toString();
    }

    public int getErrorType()
    {
        return 100;
    }

    public String getLocationNumber()
    {
        return m_strLocationNumber;
    }

    public String getPhoneNumber()
    {
        return m_strPhoneNumber;
    }

    public String getTimestamp()
    {
        return (new Timestamp(m_date.getTime())).toString();
    }

    private Integer m_nErrorCode;
    private Date m_date;
    private String m_strPhoneNumber;
    private String m_strLocationNumber;
}
