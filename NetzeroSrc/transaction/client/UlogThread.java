// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UlogThread.java

package transaction.client;

import errors.NZErrors;
import java.io.File;
import java.util.Vector;
import nzcom.*;
import tcpBinary.TransactionRequest;
import tcpBinary.TransactionResponse;
import transaction.NewQuery;
import transaction.SimpleQuery;

// Referenced classes of package transaction.client:
//            TransactionThread

public class UlogThread extends TransactionThread
{

    public UlogThread()
    {
        super("ULog");
        ZCast.displayDebug("=========== UlogThread created ================");
    }

    public static void addTransactionToFile(Object obj, String s)
    {
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setUserId(Initializer.m_zwindow.getNzUserid());
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setClientVersion(ConfigParams.getVers());
        transactionrequest.setDataObject(obj);
        transactionrequest.setTransactionCode(null);
        addTransactionToFile(transactionrequest, s);
    }

    public static void addTransactionToFile(TransactionRequest transactionrequest, String s)
    {
        Vector vector = null;
        Object obj = NZErrors.deserialize(SERTRANS, false);
        if(obj != null && (obj instanceof Vector))
        {
            vector = (Vector)obj;
            if(vector.size() > 0 && !(vector.elementAt(0) instanceof NewQuery))
            {
                TransactionRequest transactionrequest1 = new TransactionRequest();
                transactionrequest1.setClientVersion(ConfigParams.getVers());
                transactionrequest1.setDataObject(vector);
                transactionrequest1.setUserNumber(Initializer.m_userNumber);
                transactionrequest1.setSessionId(Initializer.m_sessionId);
                transactionrequest1.setUserId(Initializer.m_zwindow.getNzUserid());
                SimpleQuery simplequery1 = new SimpleQuery(transactionrequest1, s);
                Vector vector1 = new Vector();
                vector1.addElement(simplequery1);
                vector = vector1;
            }
        } else
        {
            vector = new Vector();
        }
        SimpleQuery simplequery = new SimpleQuery(transactionrequest, s);
        vector.addElement(transactionrequest);
        NZErrors.serialize(SERTRANS, vector);
    }

    public static boolean deleteFile(String s)
    {
        try
        {
            File file = new File(s);
            file.delete();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            return false;
        }
        return true;
    }

    protected String getTransactionCode()
    {
        return "VOTR";
    }

    protected boolean isTransactionSuccessfull(TransactionResponse transactionresponse)
    {
        return transactionresponse.getReturnCode() == 0;
    }

    protected void onInitialize()
    {
        setServerLocation("trip1");
    }

    protected void onTerminate()
    {
        int i = queries.size();
        if(i <= 0)
        {
            deleteFile(SERTRANS);
            return;
        }
        for(int j = 0; j < i; j++)
            try
            {
                SimpleQuery simplequery = (SimpleQuery)queries.elementAt(j);
                if(simplequery.getTransactionCode().equals("ULOG"))
                    simplequery.setTransactionCode("CREC");
            }
            catch(Exception _ex) { }

        NZErrors.serialize(SERTRANS, queries);
        ZCast.displayDebug(i + " transactions serialized.");
    }

    public static String SERTRANS = "obj.save";

}
