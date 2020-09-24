// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerParms.java

package nzcom;

import gui.MemberRecs;
import java.io.*;
import java.util.*;
import tcpBinary.*;

// Referenced classes of package nzcom:
//            ConfigParams, Initializer, ZCast

public class ServerParms
{

    public static void setProgressListener(TransactionProgress transactionprogress)
    {
        progressListener = transactionprogress;
    }

    public static void getFromServer(String s)
        throws Exception
    {
        TransactionRequest transactionrequest = new TransactionRequest();
        Date date = new Date();
        ZCast.displayDebug("********************>>>>>>>>>>>>>>>>>>Starting of PRM2 time is : " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
        transactionrequest.setUserId(MemberRecs.getCurrentMemberID());
        if(ZCast.m_connectionType == 1 || ZCast.m_connectionType == 2)
            ZCast.generateSessionId();
        transactionrequest.setSessionId(Initializer.m_sessionId);
        ZCast.displayDebug(">>> PRM2 - Session ID = " + transactionrequest.getSessionId());
        transactionrequest.setClientVersion(ConfigParams.getVers());
        Vector vector = new Vector();
        transactionrequest.setDataObject(vector);
        vector.addElement(ZCast.getConnectionTime().toString());
        Long long1 = ZCast.getMachineIDReg();
        if(long1 == null)
            long1 = new Long(0L);
        Long long2 = ZCast.getSessionCtrReg();
        if(long2 == null)
            long2 = new Long(0L);
        Integer integer = new Integer(0);
        if(long1.longValue() == 0L || long2.longValue() > 0xf4236L)
            integer = new Integer(1);
        vector.addElement(integer);
        vector.addElement(long1);
        vector.addElement(MemberRecs.getCurrentLanguage());
        Transaction transaction = new Transaction(false);
        transaction.setTpInterface(progressListener);
        transaction.setServerLocation(s);
        TransactionResult transactionresult = transaction.execute(transactionrequest, "PRM2");
        ZCast.displayDebug("(PRM2) success flag set to: " + transactionresult.getSuccessFlag());
        if(!(transactionresult.getDataObject() instanceof TransactionResponse))
        {
            ZCast.displayDebug("transaction PRM2 failed to return TransactionResponse object");
            throw new Exception(resNZResource.getString("PARM2__Unable_to_contact_th"));
        }
        TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
        Initializer.compareTransResponse(transactionresponse, "PRM2");
        ZCast.displayDebug("(PRM2) return code = " + transactionresponse.getReturnCode());
        if(transactionresponse.getReturnCode() != 0)
            throw new Exception(resNZResource.getString("PARM2__Unable_to_contact_th"));
        ZCast.displayDebug("user number is : " + transactionresponse.getUserNumber());
        Initializer.m_userNumber = transactionresponse.getUserNumber();
        Long long3 = transactionresponse.getSessionId();
        if(long3 != null && long3.longValue() != 0L)
        {
            ZCast.displayDebug("session ID is : " + long3);
            Initializer.m_sessionId = long3;
        }
        if(transactionresponse.getReturnObject() instanceof Vector)
        {
            Vector vector1 = (Vector)transactionresponse.getReturnObject();
            Vector vector2 = null;
            Vector vector3 = null;
            Vector vector4 = null;
            try
            {
                vector2 = (Vector)vector1.elementAt(0);
                vector3 = (Vector)vector1.elementAt(1);
                vector4 = (Vector)vector1.elementAt(2);
            }
            catch(Exception exception)
            {
                throw exception;
            }
            Initializer.setHomePageAndFlag(vector3, vector4);
            Object obj = vector2.elementAt(0);
            if(!(obj instanceof Properties))
                throw new Exception(resNZResource.getString("PARM2__Unable_to_contact_th"));
            m_serverParameters = (Properties)obj;
            if(integer.longValue() == 1L)
            {
                Object obj1 = vector2.elementAt(1);
                if(obj1 instanceof Long)
                {
                    ZCast.setMachineIDReg((Long)obj1);
                    ZCast.setSessionCtrReg(new Long(-1L));
                } else
                {
                    ZCast.displayDebug("Transaction PRM2 failed to return a valid Machine ID object");
                }
            }
        } else
        {
            ZCast.displayDebug("Transaction PRM2 failed to return a Vector as the Return Object");
        }
        Date date1 = new Date();
        ZCast.displayDebug("********************>>>>>>>>>>>>>>>>>>ENDing of PRM2 time is : " + date1.getHours() + ":" + date1.getMinutes() + ":" + date1.getSeconds());
    }

    public static String getParm(String s, String s1)
    {
        String s2 = s1;
        if(m_serverParameters != null)
            s2 = m_serverParameters.getProperty(s, s1);
        if(s2 != null)
            s2 = s2.trim();
        return s2;
    }

    public static void loadPropertiesFromFile(String s)
    {
        try
        {
            m_serverParameters = new Properties();
            FileInputStream fileinputstream = new FileInputStream(s);
            m_serverParameters.load(fileinputstream);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public static void savePropertiesToFile(String s)
    {
        if(m_serverParameters != null)
            try
            {
                FileOutputStream fileoutputstream = new FileOutputStream(s);
                m_serverParameters.save(fileoutputstream, "NZ Server Parameters");
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
    }

    public static void dump(String s, PrintStream printstream)
    {
        if(m_serverParameters != null)
        {
            if(ZCast.m_nzDebugMode.equals("on") || ZCast.m_nzDebugMode.indexOf(s) != -1)
                m_serverParameters.list(printstream);
        } else
        {
            ZCast.displayDebug("Server Parms property is null.");
        }
    }

    public ServerParms()
    {
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private static Properties m_serverParameters = null;
    private static TransactionProgress progressListener = null;

}
