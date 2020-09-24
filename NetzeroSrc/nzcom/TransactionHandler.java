// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionHandler.java

package nzcom;

import tcpBinary.*;

// Referenced classes of package nzcom:
//            ZCast, Initializer, ConfigParams, ServerParms

public class TransactionHandler
{

    public static TransactionResponse getHomePage(String s)
    {
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("plip1", transaction.getServerLocation()));
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setClientVersion(ConfigParams.getVers());
        transactionrequest.setDataObject(null);
        transactionrequest.setUserId(s);
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        TransactionResult transactionresult = transaction.execute(transactionrequest, "HOME");
        ZCast.displayDebug("(HOME) success flag set to: " + transactionresult.getSuccessFlag());
        if(!(transactionresult.getDataObject() instanceof TransactionResponse))
        {
            ZCast.displayDebug("transaction HOME failed to return a TransactionResponse object");
            return null;
        } else
        {
            TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
            Initializer.compareTransResponse(transactionresponse, "HOME");
            return transactionresponse;
        }
    }

    public TransactionHandler()
    {
    }
}
