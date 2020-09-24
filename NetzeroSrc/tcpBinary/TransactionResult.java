// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionResult.java

package tcpBinary;

import java.io.Serializable;

public class TransactionResult
    implements Serializable
{

    public Object getDataObject()
    {
        return dataObject;
    }

    public String getRequestType()
    {
        return requestType;
    }

    public boolean getSuccessFlag()
    {
        return successFlag;
    }

    public void setDataObject(Object obj)
    {
        dataObject = obj;
    }

    public void setRequestType(String s)
    {
        requestType = s;
    }

    public void setSuccessFlag(boolean flag)
    {
        successFlag = flag;
    }

    public String toString()
    {
        return getRequestType() + ":::" + getDataObject();
    }

    public TransactionResult()
    {
        dataObject = null;
        requestType = null;
        successFlag = true;
    }

    private Object dataObject;
    private String requestType;
    private boolean successFlag;
}
