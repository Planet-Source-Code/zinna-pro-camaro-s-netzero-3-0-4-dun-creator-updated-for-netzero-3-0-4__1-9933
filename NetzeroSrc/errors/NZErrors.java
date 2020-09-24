// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZErrors.java

package errors;

import java.io.*;
import java.util.*;
import nzcom.*;
import tcpBinary.*;

// Referenced classes of package errors:
//            DialupErrorImpl

public final class NZErrors
{

    private NZErrors()
    {
        m_vSessionErrors = null;
    }

    public void addDialupError(String s, int i, Date date, String s1, String s2)
    {
        ZCast.displayDebug("entered in errors.NZErrors.addDialupError() (userId = " + s + ")");
        addError(new DialupErrorImpl(s, i, date, s1, s2));
    }

    public void addDialupError(String s, Integer integer, Date date, String s1, String s2)
    {
        if(s == null || date == null || integer == null)
        {
            return;
        } else
        {
            ZCast.displayDebug("entered in errors.NZErrors.addDialupError() (userId = " + s + "; locationNumner = " + s2 + ")");
            addError(new DialupErrorImpl(s, integer, date, s1, s2));
            return;
        }
    }

    private void addError(Object obj)
    {
        if(m_vSessionErrors == null)
            m_vSessionErrors = new Vector();
        m_vSessionErrors.addElement(obj);
    }

    public static Object deserialize(String s, boolean flag)
    {
        Object obj = null;
        File file = new File(s);
        if(file.exists())
        {
            ObjectInputStream objectinputstream = null;
            try
            {
                objectinputstream = new ObjectInputStream(new FileInputStream(file));
                obj = objectinputstream.readObject();
                objectinputstream.close();
                objectinputstream = null;
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug(exception1);
            }
            finally
            {
                try
                {
                    if(objectinputstream != null)
                        objectinputstream.close();
                    if(flag)
                        file.delete();
                }
                catch(IOException _ex) { }
            }
        }
        return obj;
    }

    public static NZErrors getInstance()
    {
        return m_nzErrors;
    }

    public void sendErrors()
    {
        ZCast.displayDebug("entering in errors.NZErrors.sendErrors() method");
        Vector vector = null;
        Object obj = deserialize("dialup.err", true);
        if(obj != null && (obj instanceof Vector))
        {
            vector = (Vector)obj;
        } else
        {
            if(m_vSessionErrors == null)
                return;
            vector = new Vector();
        }
        vector.insertElementAt(new Date(), 0);
        if(m_vSessionErrors != null)
        {
            for(Enumeration enumeration = m_vSessionErrors.elements(); enumeration.hasMoreElements(); vector.addElement(enumeration.nextElement()));
        }
        TransactionRequest transactionrequest = new TransactionRequest(null, ConfigParams.getVers(), vector);
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
        TransactionResult transactionresult = transaction.execute(transactionrequest, "DDAT");
        ZCast.displayDebug("(DDAT) success flag set to: " + transactionresult.getSuccessFlag());
        if(transactionresult.getDataObject() instanceof TransactionResponse)
        {
            TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
            Initializer.compareTransResponse(transactionresponse, "DDAT");
            ZCast.displayDebug("(DDAT) success flag set to: " + transactionresult.getSuccessFlag());
            ZCast.displayDebug("(DDAT) return code = " + transactionresponse.getReturnCode());
            if(transactionresponse.getReturnCode() == 0)
                return;
        } else
        {
            ZCast.displayDebug("transaction DDAT failed to return TransactionResponse object");
        }
        vector.removeElementAt(0);
        serialize("dialup.err", vector);
    }

    public static void serialize(String s, Object obj)
    {
        ObjectOutputStream objectoutputstream = null;
        try
        {
            objectoutputstream = new ObjectOutputStream(new FileOutputStream(s));
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
            objectoutputstream = null;
        }
        catch(Exception exception1)
        {
            ZCast.displayDebug(exception1);
        }
        finally
        {
            try
            {
                if(objectoutputstream != null)
                    objectoutputstream.close();
            }
            catch(IOException _ex) { }
        }
    }

    public void serializeErrors()
    {
        Vector vector = null;
        Object obj = deserialize("dialup.err", true);
        if(obj != null && (obj instanceof Vector))
            vector = (Vector)obj;
        else
            vector = new Vector();
        for(Enumeration enumeration = m_vSessionErrors.elements(); enumeration.hasMoreElements(); vector.addElement(enumeration.nextElement()));
        serialize("dialup.err", vector);
    }

    private static final String DIALUPERROR_TRANSCODE = "DDAT";
    private static final String DIALUPERROR_FILENAME = "dialup.err";
    private static NZErrors m_nzErrors = new NZErrors();
    private Vector m_vSessionErrors;

}
