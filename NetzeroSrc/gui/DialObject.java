// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialObject.java

package gui;

import errors.NZErrors;
import exception.NZException;
import java.util.ResourceBundle;
import java.util.Vector;
import nzcom.*;

// Referenced classes of package gui:
//            DialGroups, Startup

public class DialObject
{

    public DialObject(String s, String s1, int i, String s2)
    {
        location = null;
        PhoneNumber = null;
        redialCount = 0;
        location = s;
        PhoneNumber = s1;
        redialCount = i;
        rank = s2;
    }

    public boolean dialNumbers(Profile profile, Startup startup, RASWinNative raswinnative, int i, int j)
        throws NZException
    {
        int k = 0;
        boolean flag = true;
        ZCast.displayDebug("dialobj", "\n\n\n@@@@@@@@@@@@@@@@@@@@ DIAL INFORMATION STARTS @@@@@@@@@@@@@@@@@@@\n\n");
        ZCast.displayDebug("dialobj", "****** PHONE NUMBER WHICH IS DIALING : " + profile.getPhoneLocation());
        ZCast.displayDebug("dialobj", "****** Format of dialing this number : " + profile.getPhoneNumber());
        ZCast.displayDebug("dialobj", "****** Total redialCount             : " + redialCount);
        ZCast.displayDebug("dialobj", "****** Total numbers in the list     : " + j);
        ZCast.displayDebug("dialobj", "************************************************************\n");
        startup.setStatusLabel(profile.getPhoneLocation());
        boolean flag1 = true;
        while(flag1) 
        {
            ZCast.displayDebug("dialobj", "****** Redialing for                 :  (" + k + ")  time");
            ZCast.displayDebug("dialobj", "****** Rank of this number           :  (" + rank + ")  ");
            startup.setStatusLabel("Dialing " + profile.getPhoneNumber() + " , attempt " + (k + 1) + " of " + (redialCount + 1));
            RASFrame rasframe = new RASFrame(raswinnative, ConfigParams.getDialEntry(ZCast.m_authCode), profile, startup);
            rasframe.initialize();
            try
            {
                int l;
                while((l = rasframe.getConnectMode()) == -1) 
                {
                    Thread.sleep(1000L);
                    ZCast.displayDebug("dialobj", "DialObject!! connectMode=" + l);
                }
                k++;
                ZCast.displayDebug("dialobj", "DialObject!! dial loop exited, connectMode=" + l);
                if(rasframe.getConnectMode() == 0)
                {
                    flag1 = false;
                    flag = false;
                } else
                if(rasframe.getConnectMode() == -2)
                {
                    if(k > redialCount)
                    {
                        flag1 = false;
                        if(++i == j)
                        {
                            ZCast.displayDebug("dialobj", "\n\n-------------------------------------------------------------------------------------------");
                            ZCast.displayDebug("dialobj", "@@@@  Throw NZException because no number left to dial ");
                            ZCast.displayDebug("dialobj", "-------------------------------------------------------------------------------------------\n\n");
                            flag = false;
                            NZErrors.getInstance().serializeErrors();
                            throw new NZException(300, resNZResource.getString("Unable_to_connect_with_any"));
                        }
                    } else
                    {
                        Thread.sleep(5000L);
                    }
                } else
                {
                    throw new NZException(300, "Dialing Error");
                }
            }
            catch(InterruptedException _ex)
            {
                ZCast.displayDebug("dialobj", "DialObject interrupted! ");
                throw new NZException(300, "User Cancel");
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("dialobj", "dialToConnect: " + exception);
                ZCast.displayDebug("dialobj", exception);
                if(exception instanceof NZException)
                    throw (NZException)exception;
                else
                    throw new NZException(300, resNZResource.getString("Unable_to_connect"));
            }
        }
        return flag;
    }

    public static void dialToConnect(Startup startup)
        throws NZException
    {
        m_currentDialIndex = -1;
        boolean flag = true;
        Vector vector = DialGroups.getNumbersToDial();
        while(flag) 
        {
            m_currentDialIndex++;
            if((vector != null) & (vector.size() > m_currentDialIndex))
            {
                savePhoneInfo();
                if(dObj != null)
                    flag = dObj.dialNumbers(ZCast.m_profile, startup, ZCast.m_rasWinNative, m_currentDialIndex, vector.size());
            }
        }
        ZCast.displayDebug("dialobj", "********** Hurray!!!!!!!  CONNECTION SUCCESSFUL !  ***************");
    }

    public static void doConnect(int i, Startup startup)
        throws NZException
    {
        ZCast.m_authenticationMode = i;
        if(ZCast.m_connectionType == 1 || ZCast.m_connectionType == 2)
        {
            ZCast.displayDebug("CONNECTED: TYPE IS LAN OR DEMO");
            return;
        }
        if(ZCast.m_rasWinNative.getActiveCount() > 0)
        {
            throw new NZException(2, resNZResource.getString("NetZero_dialup_already_active"));
        } else
        {
            dialToConnect(startup);
            return;
        }
    }

    public static void doDisconnect()
    {
        try
        {
            if(ZCast.m_rasWinNative != null && ZCast.m_connectionType == 0 && ZCast.m_rasWinNative.getStatus() != 3)
            {
                ZCast.m_rasWinNative.hangUpConnection();
                for(; ZCast.m_rasWinNative.isEntryActive(ConfigParams.getDialEntry(ZCast.m_authCode)); Thread.sleep(5000L))
                    ZCast.displayDebug("dialobj", "Disconnecting: Waiting for modem to hangup...");

            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("dialobj", exception);
        }
    }

    public String getLocation()
    {
        return location;
    }

    public String getPhoneNumber()
    {
        return PhoneNumber;
    }

    public String getRank()
    {
        return rank;
    }

    public int getRedialCount()
    {
        return redialCount;
    }

    public static void savePhoneInfo()
    {
        Vector vector = DialGroups.getNumbersToDial();
        int i = vector.size();
        if(vector != null && vector.size() > m_currentDialIndex)
        {
            dObj = (DialObject)vector.elementAt(m_currentDialIndex);
            if(dObj != null && ZCast.m_profile != null)
            {
                ZCast.m_profile.setPhoneNumber(dObj.getPhoneNumber());
                ZCast.m_profile.setPhoneLocation(dObj.getLocation());
                ZCast.m_profile.saveProperties();
            }
        }
    }

    public void setLocation(String s)
    {
        location = s;
    }

    public void setPhoneNumber(String s)
    {
        PhoneNumber = s;
    }

    public void setRank(String s)
    {
        rank = s;
    }

    public void setRedialCount(int i)
    {
        redialCount = i;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private String location;
    private String PhoneNumber;
    private int redialCount;
    private String rank;
    private static DialObject dObj = null;
    private static int m_currentDialIndex = 0;

}
