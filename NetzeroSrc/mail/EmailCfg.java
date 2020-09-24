// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailCfg.java

package mail;

import nzcom.*;
import tcpBinary.*;

// Referenced classes of package mail:
//            Dll4Mail, EmailConfigException

public abstract class EmailCfg
{

    public EmailCfg(String s)
        throws EmailConfigException
    {
        loginName = null;
        loginName = s;
    }

    public abstract String getAppName();

    public String getLoginName()
    {
        return loginName;
    }

    public abstract boolean isAccountAlreadyDefined();

    public boolean sendEMCFxAction()
    {
        updateNZRORegistry();
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("plip1", transaction.getServerLocation()));
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setDataObject(null);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        TransactionResult transactionresult = transaction.execute(transactionrequest, "EMCF");
        if(!(transactionresult.getDataObject() instanceof TransactionResponse))
        {
            ZCast.displayDebug("transaction EMCF failed to return a TransactionResponse object");
            return false;
        }
        TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
        return transactionresponse.toString().trim().equalsIgnoreCase("1");
    }

    public abstract void setupAccount()
        throws EmailConfigException;

    public void updateNZRORegistry()
    {
        try
        {
            String s = "SOFTWARE\\NETZERO, Inc.\\NetZero\\USERS\\" + getLoginName();
            if(!Dll4Mail.createRegKey("HKEY_LOCAL_MACHINE", s))
            {
                return;
            } else
            {
                Dll4Mail.setRegValueData("HKEY_LOCAL_MACHINE", s, "EMAIL", getAppName());
                return;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("Exception caught while trying to create registry for NZRO: " + exception.toString());
        }
    }

    protected String loginName;
}
