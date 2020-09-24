// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NetworkErrorImpl.java

package errors;

import java.net.InetAddress;
import java.util.Date;

// Referenced classes of package errors:
//            SimpleError, NetworkError

public class NetworkErrorImpl extends SimpleError
    implements NetworkError
{

    public NetworkErrorImpl(String s, Date date)
    {
        m_strUserId = s;
        m_date = date;
    }

    public Date getDate()
    {
        return m_date;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public Integer getErrorNumber()
    {
        return errorNumber;
    }

    public int getErrorType()
    {
        return 101;
    }

    public String getFileName()
    {
        return fileName;
    }

    public int getPort()
    {
        return port;
    }

    public int getProtocol()
    {
        return protocol;
    }

    public Date getReportDate()
    {
        return reportDate;
    }

    public Integer getReturnCode()
    {
        return returnCode;
    }

    public InetAddress getServerIpAddress()
    {
        return serverIpAddress;
    }

    public Long getSessionId()
    {
        return sessionId;
    }

    public Date getSysDate()
    {
        return transSysDate;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setErrorMessage(String s)
    {
        errorMessage = s;
    }

    public void setErrorNumber(Integer integer)
    {
        errorNumber = integer;
    }

    public void setFileName(String s)
    {
        fileName = s;
    }

    public void setPort(int i)
    {
        port = i;
    }

    public void setProtocol(int i)
    {
        protocol = i;
    }

    public void setReportDate(Date date)
    {
        reportDate = date;
    }

    public void setReturnCode(Integer integer)
    {
        returnCode = integer;
    }

    public void setServerIpAddress(InetAddress inetaddress)
    {
        serverIpAddress = inetaddress;
    }

    public void setSessionId(Long long1)
    {
        sessionId = long1;
    }

    public void setSysDate(Date date)
    {
        transSysDate = date;
    }

    public void setTransactionType(String s)
    {
        transactionType = s;
    }

    private Long sessionId;
    private Date reportDate;
    private Date transSysDate;
    private Integer errorNumber;
    private InetAddress serverIpAddress;
    private String fileName;
    private String transactionType;
    private int protocol;
    private int port;
    private Integer returnCode;
    private Date m_date;
    private String errorMessage;
}
