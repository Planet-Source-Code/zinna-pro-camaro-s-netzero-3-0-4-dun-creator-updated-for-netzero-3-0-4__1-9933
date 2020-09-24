// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleQuery.java

package transaction;

import tcpBinary.TransactionRequest;

// Referenced classes of package transaction:
//            NewQuery

public class SimpleQuery
    implements NewQuery
{

    public SimpleQuery(TransactionRequest transactionrequest, String s)
    {
        request = null;
        transCode = null;
        request = transactionrequest;
        transCode = s;
    }

    public String getTransactionCode()
    {
        return transCode;
    }

    public TransactionRequest getTransactionRequest()
    {
        return request;
    }

    public void setTransactionCode(String s)
    {
        transCode = s;
    }

    private TransactionRequest request;
    private String transCode;
}
