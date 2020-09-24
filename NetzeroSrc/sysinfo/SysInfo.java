// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysInfo.java

package sysinfo;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import nzcom.OSDetectNative;
import nzcom.ZCast;

public class SysInfo
    implements Serializable
{

    public SysInfo()
    {
        items = null;
        loginsToWait = 0;
        adDisplayType = 0;
        items = new Hashtable();
    }

    public boolean containsAll(SysInfo sysinfo)
    {
        if(sysinfo == null)
            return false;
        for(Enumeration enumeration = items.keys(); enumeration.hasMoreElements();)
        {
            String s = (String)enumeration.nextElement();
            String s1 = (String)get(s);
            String s2 = (String)sysinfo.get(s);
            if(s.equals("procSpeed"))
            {
                long l = (new Long(s1)).longValue();
                long l2 = (new Long(s2)).longValue();
                if(Math.abs(l - l2) > 50L)
                    return false;
            } else
            if(s.equals("totPhysMem"))
            {
                long l1 = (new Long(s1)).longValue();
                long l3 = (new Long(s2)).longValue();
                if(Math.abs(l1 - l3) > 0x4c4b40L)
                    return false;
            } else
            if(!s1.equals(s2))
                return false;
        }

        return true;
    }

    public Enumeration elements()
    {
        return items.elements();
    }

    public Object get(String s)
    {
        return items.get(s);
    }

    public int getAdDisplayType()
    {
        return adDisplayType;
    }

    public int getLoginsToWait()
    {
        return loginsToWait;
    }

    public boolean isOkToUpdate()
    {
        if(loginsToWait > 0)
        {
            loginsToWait--;
            return false;
        } else
        {
            return true;
        }
    }

    public Enumeration keys()
    {
        return items.keys();
    }

    public void put(String s, String s1)
    {
        if(s != null && s1 != null)
            items.put(s, s1);
    }

    public void remove(String s)
    {
        items.remove(s);
    }

    public void setAdDisplayType(int i)
    {
        adDisplayType = i;
    }

    public void setLoginsToWait(int i)
    {
        loginsToWait = i;
    }

    public String toString()
    {
        String s = "";
        Enumeration enumeration = items.keys();
        if(enumeration != null)
            while(enumeration.hasMoreElements()) 
            {
                String s1 = (String)enumeration.nextElement();
                s = s + s1 + "=" + items.get(s1);
                if(enumeration.hasMoreElements())
                    s = s + "|";
            }
        return s;
    }

    public boolean hasVideoHardware()
    {
        try
        {
            String s = (String)get("xVersion");
            if(s == null || s.trim().equals(""))
                return false;
            s = (String)get("procSpeed");
            if(s != null && !s.trim().equals(""))
            {
                int i = Integer.parseInt(s.trim());
                if(i < 166)
                    return false;
            }
            s = (String)get("procHasMMX");
            if(s != null && !s.trim().equals("") && s.equalsIgnoreCase("false"))
                return false;
            s = (String)get("totPhysMem");
            if(s != null && !s.trim().equals(""))
            {
                int j = Integer.parseInt(s.trim());
                if(j < 32)
                    return false;
            }
            boolean flag = OSDetectNative.isRegKeyPresent("HKEY_LOCAL_MACHINE", "SOFTWARE\\Microsoft\\MediaPlayer");
            if(!flag)
            {
                ZCast.displayDebug("\nSysInfo: No Media Player - no video!!!");
                return false;
            } else
            {
                return true;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return false;
    }

    public boolean hasIE4()
    {
        try
        {
            String s = (String)get("ieBrowserVersion");
            if(s == null || s.trim().equals(""))
                return false;
            int i = Integer.parseInt(s.trim().substring(0, 1));
            return i >= 4;
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return false;
    }

    static final long serialVersionUID = 0xc052582d41e88fa3L;
    private Hashtable items;
    private int loginsToWait;
    private int adDisplayType;
}
