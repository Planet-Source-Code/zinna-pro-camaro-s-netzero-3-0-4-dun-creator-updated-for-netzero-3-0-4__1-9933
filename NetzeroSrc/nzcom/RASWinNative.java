// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RASWinNative.java

package nzcom;

import java.util.StringTokenizer;

// Referenced classes of package nzcom:
//            ZCast

public class RASWinNative
{

    public void cancelConnection()
    {
        connectStatus = nw32cancel();
        if(connectStatus == 0)
            currentEntry = null;
    }

    public int createDunEntry(String s, String s1, String s2)
    {
        return nw32crtdun(s, s1, s2);
    }

    public void deleteDunEntry(String s)
    {
        nw32deldun(s);
    }

    public int dialEntry(String s)
    {
        if(!nw32cleanentry(s))
            ZCast.displayDebug("Failed to clean DUN entry before dialing.");
        ZCast.displayDebug("user name   = " + username);
        ZCast.displayDebug("password    = " + password);
        ZCast.displayDebug("server      = " + server);
        ZCast.displayDebug("phoneNumber = " + phoneNumber);
        ZCast.displayDebug("**********************      dialEntry End        **************************");
        nw32setauth(useGlobal ^ true, username, password, server, phoneNumber);
        connectStatus = nw32dialentry(s);
        if(connectStatus == 0)
            currentEntry = s;
        return connectStatus;
    }

    public int dialSynchEntry(String s)
    {
        nw32setauth(useGlobal ^ true, username, password, server, phoneNumber);
        connectStatus = nw32synchdial(s);
        if(connectStatus == 0)
            currentEntry = s;
        return connectStatus;
    }

    public boolean doesEntryExist(String s)
    {
        return nw32entryexists(s);
    }

    public boolean setModemMute(String s, boolean flag)
    {
        return nw32setmodemvolume(s, flag ? 0 : 4);
    }

    public int getActiveCount()
    {
        return nw32getactive();
    }

    public String getConnStatus()
    {
        return nw32connstatus();
    }

    public String getErrorString(int i)
    {
        return nw32geterror(i);
    }

    public int getPhoneBookCount()
    {
        if(phoneBookEntries != null)
            return phoneBookEntries.length;
        else
            return 0;
    }

    public String[] getPhoneBookEntries()
    {
        if(phoneBookEntries == null)
        {
            String s = nw32getentries();
            if(s != null && s.length() > 1)
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
                int i = stringtokenizer.countTokens();
                phoneBookEntries = new String[i];
                for(int j = 0; stringtokenizer.hasMoreTokens(); j++)
                    phoneBookEntries[j] = stringtokenizer.nextToken();

            }
        }
        return phoneBookEntries;
    }

    public int getStatus()
    {
        connectStatus = nw32status();
        return connectStatus;
    }

    public void hangUpConnection()
    {
        connectStatus = nw32hangup();
    }

    public boolean isEntryActive(String s)
    {
        return nw32testentry(s) == 1;
    }

    private native int nw32cancel();

    private native String nw32connstatus();

    private native int nw32crtdun(String s, String s1, String s2);

    private native void nw32deldun(String s);

    private native boolean nw32cleanentry(String s);

    private native int nw32dialentry(String s);

    private native boolean nw32entryexists(String s);

    private native int nw32getactive();

    private native String nw32getentries();

    private native String nw32geterror(int i);

    private native int nw32hangup();

    private native void nw32setauth(boolean flag, String s, String s1, String s2, String s3);

    private native void nw32setdebug(boolean flag);

    private native void nw32setReceiver(Object obj);

    private native int nw32status();

    private native int nw32synchdial(String s);

    private native int nw32testentry(String s);

    private native boolean nw32setmodemvolume(String s, int i);

    public void setNativeDebug(boolean flag)
    {
        nw32setdebug(flag);
    }

    public void setStatusReceiver(Object obj)
    {
        nw32setReceiver(obj);
    }

    public void useDefault()
    {
        useGlobal = false;
    }

    public void useGlobal(String s, String s1, String s2, String s3)
    {
        useGlobal = true;
        username = s;
        password = s1;
        server = s2;
        phoneNumber = s3;
    }

    public RASWinNative()
    {
        connectStatus = -1;
        useGlobal = false;
    }

    protected String phoneBookEntries[];
    protected String currentEntry;
    protected int connectStatus;
    protected static boolean osHasRas;
    public static final int CONNECTED = 0;
    public static final int DISCONNECTED = 3;
    private boolean useGlobal;
    private String username;
    private String password;
    private String server;
    private String phoneNumber;

    static 
    {
        try
        {
            System.loadLibrary("NzRasWin32");
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }
}
