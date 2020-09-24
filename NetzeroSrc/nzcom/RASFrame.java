// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RASFrame.java

package nzcom;

import errors.NZErrors;
import gui.*;
import java.awt.Dialog;
import java.util.*;
import util.Base64;

// Referenced classes of package nzcom:
//            ConfigParams, RASWinNative, Initializer, ZCast, 
//            Profile

public class RASFrame
{

    public RASFrame()
    {
        connectMode = -1;
        w32RAS = null;
        dialEntry = null;
        authCode = 0x147ebabL;
        nzRealm = "@netzero.net";
    }

    public RASFrame(RASWinNative raswinnative, String s, Profile profile, Startup startup)
    {
        connectMode = -1;
        w32RAS = null;
        dialEntry = null;
        authCode = 0x147ebabL;
        nzRealm = "@netzero.net";
        dialEntry = s;
        prof = profile;
        aLogon = startup;
        phoneNumber = profile.getPhoneNumber();
        setRasWinNative(raswinnative);
    }

    private void connectComplete()
    {
        ZCast.markConnectionTime();
    }

    private void connectTerminated()
    {
    }

    public int getConnectMode()
    {
        return connectMode;
    }

    public static String getStatusText(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            return resNZResource.getString("Initializing_modem");

        case 2: // '\002'
            return resNZResource.getString("Connecting_to_NetZero");

        case 3: // '\003'
            return resNZResource.getString("Connection_established_to_");

        case 5: // '\005'
            return resNZResource.getString("Verifying_userid_and_passw");

        case 14: // '\016'
            return resNZResource.getString("Userid_and_password_accept");

        case 19: // '\023'
            return resNZResource.getString("Start_authentication");

        case 21: // '\025'
            return resNZResource.getString("Performing_network_logon");

        case 8192: 
            return resNZResource.getString("Connection_Completed");

        case 8193: 
            return resNZResource.getString("Disconnected");
        }
        return null;
    }

    private void initConnection(String s)
    {
        phoneEntry = s;
        String s1 = System.getProperty("nz.realm");
        if(s1 != null)
            nzRealm = s1;
        ZCast.displayDebug("dialdialog", "********** authmode    :  " + ZCast.m_authenticationMode);
        ZCast.generateSessionId();
        Long long1 = Initializer.m_sessionId;
        String s2 = "0";
        if(long1 != null)
            s2 = Base64.toBase64(long1.longValue());
        if(ZCast.m_authenticationMode == 1)
            w32RAS.useGlobal(s2 + ":" + ZCast.getZcastVersion() + ":" + MemberRecs.getCurrentMemberID() + nzRealm, MemberRecs.getCurrentPassword(), "*", phoneNumber);
        else
        if(ZCast.m_authenticationMode == 2)
        {
            String s3 = MemberRecs.getCurrentPassword();
            if(s3.length() > 14)
                s3 = s3.substring(0, 14);
            ZCast.displayDebug("dialdialog", "********** Mangled password    :  " + s3);
            w32RAS.useGlobal(s2 + ":" + ZCast.getZcastVersion() + ":" + MemberRecs.getCurrentMemberID() + nzRealm, "0" + s3 + "1", "*", phoneNumber);
        } else
        {
            w32RAS.useGlobal(s2 + ":" + ZCast.getZcastVersion() + ":" + ConfigParams.getUserid(authCode), ConfigParams.getPassword(authCode), "*", phoneNumber);
        }
        if(phoneNumber.trim().length() > 0)
        {
            String s4 = prof.getPhoneLocation();
            StringTokenizer stringtokenizer = new StringTokenizer(s4, " ");
            stringtokenizer.nextToken();
            for(s4 = new String(); stringtokenizer.hasMoreTokens(); s4 = s4 + "  ")
                s4 = s4 + stringtokenizer.nextToken().trim();

            aLogon.setTitle(resNZResource.getString("Dialing_") + phoneNumber + " (  " + s4 + "  )");
        }
        int i = w32RAS.dialEntry(phoneEntry);
        ZCast.displayDebug("dialdialog", "RasFrame :  dStatus=" + i);
    }

    public void initialize()
    {
        if(w32RAS != null)
        {
            w32RAS.setStatusReceiver(this);
            rcvFrame = this;
            if(dialEntry != null)
            {
                initConnection(dialEntry);
                return;
            }
        }
    }

    private void setConnectMode(int i)
    {
        connectMode = i;
    }

    public void setDialStatus(int i, int j)
    {
        ZCast.displayDebug("Native Dialer call back: dError=" + j);
        if(j > 0)
        {
            w32RAS.hangUpConnection();
            NZErrors.getInstance().addDialupError(MemberRecs.getCurrentMemberID(), new Integer(j), new Date(), phoneNumber, prof.getPhoneLocation());
            String s = w32RAS.getErrorString(j);
            if(j == 718 || j == 680 || j == 676 || j == 678)
            {
                setConnectMode(-2);
            } else
            {
                if(j == 691 || j == 5)
                    s = resNZResource.getString("member_id_/_password_inval");
                String as[] = new String[1];
                as[0] = "ok";
                String s2 = resNZResource.getString("Connection_problem_(") + j + ")";
                String s3 = s;
                NZDialogBox.showMessageDialog(s2, s3, 2, as);
                NZErrors.getInstance().serializeErrors();
                ZCast.displayDebug("Native Dialer call back: setting connectMmode to -1");
                setConnectMode(-3);
            }
            return;
        }
        if(i > 2)
        {
            String s1 = getStatusText(i);
            if(s1 != null)
                aLogon.setStatusLabel(s1 + " ...");
            if(i == 8192)
            {
                connectComplete();
                setConnectMode(0);
            }
        }
    }

    public void setRasWinNative(RASWinNative raswinnative)
    {
        w32RAS = raswinnative;
    }

    public static int showWinStatus(int i, int j)
    {
        rcvFrame.setDialStatus(i, j);
        if(i == 8192)
            return 1;
        else
            return j;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private int connectMode;
    private String phoneEntry;
    private String phoneNumber;
    private Profile prof;
    private Startup aLogon;
    private static RASFrame rcvFrame;
    private RASWinNative w32RAS;
    private String dialEntry;
    private long authCode;
    private String nzRealm;

}
