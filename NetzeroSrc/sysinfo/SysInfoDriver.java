// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysInfoDriver.java

package sysinfo;

import gui.MemberRecs;
import java.io.*;
import java.util.Enumeration;
import nzcom.*;
import tcpBinary.*;

// Referenced classes of package sysinfo:
//            SysInfo

public class SysInfoDriver
{

    public static SysInfo getOldSystemInfo()
    {
        SysInfo sysinfo = null;
        try
        {
            File file = new File(sysInfoFile);
            if(file.exists())
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(sysInfoFile));
                Object obj = objectinputstream.readObject();
                if(obj instanceof SysInfo)
                    sysinfo = (SysInfo)obj;
                else
                    ZCast.displayDebug("Wrong object type in file.");
                objectinputstream.close();
            } else
            {
                ZCast.displayDebug("File does not exist. Skipping.");
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return sysinfo;
    }

    public static void setAdDisplayType(int i)
    {
        SysInfo sysinfo = getOldSystemInfo();
        if(sysinfo == null)
        {
            return;
        } else
        {
            sysinfo.setAdDisplayType(i);
            sysinfo.setLoginsToWait(0);
            writeSysInfo(sysinfo);
            return;
        }
    }

    public static void setLoginsToWait(int i)
    {
        SysInfo sysinfo = getOldSystemInfo();
        if(sysinfo == null)
            sysinfo = new SysInfo();
        sysinfo.setLoginsToWait(i);
        writeSysInfo(sysinfo);
    }

    public static void updateHardwareProfile()
    {
        ZCast.displayDebug("updateHardwareProfile called");
        Long long1 = ZCast.getMachineIDReg();
        if(long1 == null || long1.intValue() == 0)
        {
            ZCast.displayDebug("updateHardwareProfile: Skipped - Machine Id not defined");
            return;
        }
        SysInfo sysinfo = getOldSystemInfo();
        if(sysinfo != null && !sysinfo.isOkToUpdate())
        {
            writeSysInfo(sysinfo);
            ZCast.displayDebug("updateHardwareProfile: Skipped - Session count down");
            return;
        }
        SysInfo sysinfo1 = OSDetectNative.getSystemInfo();
        if(sysinfo1 == null)
        {
            ZCast.displayDebug("updateHardwareProfile: Skipped - could not get system info");
            return;
        }
        sysinfo1.put("machineId", long1.toString());
        if(sysinfo != null && sysinfo1.containsAll(sysinfo) && sysinfo.containsAll(sysinfo1))
        {
            ZCast.displayDebug("updateHardwareProfile: Skipped - hardware unchanged");
            return;
        }
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setClientVersion(ConfigParams.getVers());
        transactionrequest.setUserId(MemberRecs.getCurrentMemberID());
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setDataObject(sysinfo1.toString());
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
        TransactionResult transactionresult = transaction.execute(transactionrequest, "HARD");
        ZCast.displayDebug("(HARD) success flag set to: " + transactionresult.getSuccessFlag());
        if(!(transactionresult.getDataObject() instanceof TransactionResponse))
        {
            ZCast.displayDebug("updateHardwareProfile: Transaction HARD failed to return TransactionResponse object");
            return;
        }
        TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
        Initializer.compareTransResponse(transactionresponse, "HARD");
        ZCast.displayDebug("(HARD) return code = " + transactionresponse.getReturnCode());
        if(transactionresponse.getReturnCode() != 0)
        {
            if(transactionresponse.getReturnObject() instanceof Integer)
            {
                Integer integer = (Integer)transactionresponse.getReturnObject();
                setLoginsToWait(integer.intValue());
            }
            ZCast.displayDebug("updateHardwareProfile: Transaction return code not zero");
            return;
        }
        if(transactionresponse.getReturnObject() instanceof Integer)
        {
            writeSysInfo(sysinfo1);
            Integer integer1 = (Integer)transactionresponse.getReturnObject();
            setAdDisplayType(integer1.intValue());
        }
    }

    public static void writeSysInfo(SysInfo sysinfo)
    {
        try
        {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(sysInfoFile));
            objectoutputstream.writeObject(sysinfo);
            objectoutputstream.close();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public static void main(String args[])
    {
        SysInfo sysinfo = null;
        System.out.println("Hardware Profile");
        System.loadLibrary("OsUtil");
        System.loadLibrary("NzAdv");
        sysinfo = OSDetectNative.getSystemInfo();
        if(sysinfo != null)
        {
            String s;
            String s1;
            for(Enumeration enumeration = sysinfo.keys(); enumeration.hasMoreElements(); System.out.println(s + " = " + s1))
            {
                s = (String)enumeration.nextElement();
                s1 = (String)sysinfo.get(s);
            }

        } else
        {
            System.out.println("Error Obtaining Hardware Profile");
        }
    }

    public SysInfoDriver()
    {
    }

    private static String sysInfoFile = "sysinfo.obj";

}
