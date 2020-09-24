// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewQuery.java

package transaction;

import java.io.Serializable;
import tcpBinary.TransactionRequest;

public interface NewQuery
    extends Serializable
{

    public abstract String getTransactionCode();

    public abstract TransactionRequest getTransactionRequest();
}
