// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BB.java

package beans;

import admgmt.DemoTargetedPlayList;
import admgmt.TargetedPlayList;
import java.util.Date;
import nzcom.ServerParms;
import nzcom.ZCast;

// Referenced classes of package beans:
//            UrlListMgr

public class BB
{

    private String parseBrowserID(String s)
    {
        String s1 = null;
        if(s.startsWith("CLOSE|"))
        {
            s1 = s.substring(6);
        } else
        {
            int i = s.indexOf(124);
            if(i >= 0)
                s1 = s.substring(0, i);
        }
        return s1;
    }

    private String parseResource(String s)
    {
        int i = s.indexOf("|");
        int j = s.indexOf("://");
        if(i == -1 || j == -1)
        {
            return null;
        } else
        {
            String s1 = s.substring(i + 1, j);
            return s1;
        }
    }

    private String parseUrlString(String s)
    {
        int i = s.indexOf("://");
        return i < 0 ? s : s.substring(i + 3);
    }

    public void reportUrl(String s)
    {
        ZCast.displayDebug("\n --------------- entering in reportURL ----------------");
        ZCast.displayDebug("\turl =" + s);
        try
        {
            Date date = new Date();
            long l = -1L;
            boolean flag = false;
            if(s == null || s.trim().length() == 0)
            {
                ZCast.displayDebug("Returning from reportURL");
                return;
            }
            if(s.startsWith("!DUN"))
            {
                ZCast.displayDebug("exiting from DUN disconnect");
                ZCast.m_emergencyExit = true;
                ZCast.terminateProgram(26, null);
                return;
            }
            if(s.startsWith("CLOSE|"))
                flag = true;
            String s1 = parseBrowserID(s);
            if(s1 == null)
                ZCast.displayDebug("bidstr is null!");
            else
                try
                {
                    l = Long.parseLong(s1);
                }
                catch(Exception _ex) { }
            if(flag && l != -1L)
            {
                UrlListMgr.closeBrowser(l, date);
                ZCast.displayDebug("Returning from reportURL 2");
                return;
            }
            s = s.toLowerCase();
            String s2 = parseResource(s);
            String s3 = parseUrlString(s);
            int i = 256;
            try
            {
                i = Integer.parseInt(ServerParms.getParm("URLMaxLength", "256"));
            }
            catch(NumberFormatException _ex) { }
            String s4 = s3.substring(0, Math.min(s3.length(), i));
            UrlListMgr.processNewEntry(s2, s4, l, date);
            if(ZCast.m_demoMode)
            {
                m_tarpl = DemoTargetedPlayList.getDemoTargetedPlayList();
                int j = s3.lastIndexOf(47);
                if(j >= 0)
                    s3 = s3.substring(j + 1, s3.length());
                m_tarpl.targetThis(s3);
            } else
            if(m_tarpl != null)
                m_tarpl.targetThis(s4);
            else
                ZCast.displayDebug("************* m_tarpl is null *************");
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("************************************************");
            ZCast.displayDebug("Exception caught in BB");
            ZCast.displayDebug(exception);
        }
    }

    public boolean start()
    {
        try
        {
            System.loadLibrary("hook");
            if(m_tarpl == null)
                if(ZCast.m_demoMode)
                    m_tarpl = DemoTargetedPlayList.getDemoTargetedPlayList();
                else
                    m_tarpl = new TargetedPlayList();
            startBB();
        }
        catch(UnsatisfiedLinkError _ex)
        {
            return false;
        }
        return true;
    }

    private native void startBB();

    public void stop()
    {
        stopBB();
    }

    private native void stopBB();

    public BB()
    {
        m_tarpl = null;
    }

    private TargetedPlayList m_tarpl;
}
