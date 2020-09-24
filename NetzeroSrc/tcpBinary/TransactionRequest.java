// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionRequest.java

package tcpBinary;

import java.io.Serializable;

public class TransactionRequest
    implements Serializable
{

    public TransactionRequest()
    {
    }

    public TransactionRequest(String s, String s1, Object obj)
    {
        userid = s;
        clientVersion = s1;
        dataObject = obj;
    }

    public String getClientVersion()
    {
        return clientVersion;
    }

    public Object getDataObject()
    {
        return dataObject;
    }

    public String getPassword()
    {
        return password;
    }

    public Long getSessionId()
    {
        return sessionID;
    }

    public Integer getTransactionCode()
    {
        return transactionCode;
    }

    public String getUserid()
    {
        return userid;
    }

    public Integer getUserNumber()
    {
        return userNumber;
    }

    public void setClientVersion(String s)
    {
        clientVersion = s;
    }

    public void setDataObject(Object obj)
    {
        dataObject = obj;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setSessionId(Long long1)
    {
        sessionID = long1;
    }

    public void setTransactionCode(Integer integer)
    {
        transactionCode = integer;
    }

    public void setUserId(String s)
    {
        userid = s;
    }

    public void setUserNumber(Integer integer)
    {
        userNumber = integer;
    }

    protected String userid;
    protected Integer userNumber;
    protected Long sessionID;
    protected String password;
    protected String clientVersion;
    protected Object dataObject;
    protected Integer transactionCode;
}
