// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionResponse.java

package tcpBinary;

import java.io.Serializable;
import java.util.Properties;

public class TransactionResponse
    implements Serializable
{

    public TransactionResponse()
    {
    }

    public int getReturnCode()
    {
        return returnCode.intValue();
    }

    public String getReturnMessage()
    {
        return returnMessage;
    }

    public Object getReturnObject()
    {
        return returnObject;
    }

    public Properties getReturnProperties()
    {
        return returnProperties;
    }

    public Long getSessionId()
    {
        return sessionID;
    }

    public Integer getTransactionCode()
    {
        return transactionCode;
    }

    public Object[] getUserMessages()
    {
        return userMessages;
    }

    public Integer getUserNumber()
    {
        return userNumber;
    }

    public void setReturnCode(int i)
    {
        returnCode = new Integer(i);
    }

    public void setReturnMessage(String s)
    {
        returnMessage = s;
    }

    public void setReturnObject(Object obj)
    {
        returnObject = obj;
    }

    public void setReturnProperties(Properties properties)
    {
        returnProperties = properties;
    }

    public void setSessionId(Long long1)
    {
        sessionID = long1;
    }

    public void setTransactionCode(Integer integer)
    {
        transactionCode = integer;
    }

    public void setUserMessages(Object aobj[])
    {
        userMessages = aobj;
    }

    public void setUserNumber(Integer integer)
    {
        userNumber = integer;
    }

    protected Integer returnCode;
    protected String returnMessage;
    protected Object returnObject;
    protected Properties returnProperties;
    protected Object userMessages[];
    protected Integer userNumber;
    protected Long sessionID;
    protected Integer transactionCode;
}
