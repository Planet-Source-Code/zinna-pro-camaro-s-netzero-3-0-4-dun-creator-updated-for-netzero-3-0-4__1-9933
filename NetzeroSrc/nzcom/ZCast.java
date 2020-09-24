// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZCast.java

package nzcom;

import clientDist.ClientZip2;
import exception.NZException;
import gui.*;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;
import java.util.*;
import softwareSync.SoftwareSyncClient;
import spe.SPEngine;
import transaction.client.TransactionThread;
import video.TviPlayer;
import video.VideoRenderer;

// Referenced classes of package nzcom:
//            BrowserDisplay, ErrorChecker, SoftwareExit, ProcessZip, 
//            ConfigParams, Profile, OSDetectNative, NZWindow, 
//            Initializer, ZCastConstants, RASWinNative

public class ZCast extends ThreadGroup
    implements Runnable, ZCastConstants
{

    protected ZCast(String as[])
    {
        super("ZeroPort");
        timeToDie_ = false;
        showStartupEnvironment(as);
    }

    protected synchronized void awaitTerminationCondition()
    {
        while(!timeToDie_) 
            try
            {
                wait(1000L);
                if(timeToDie_)
                    terminateProgram(exitStatusCode, exitMessage);
                keepTheMainFrameOnTop();
            }
            catch(InterruptedException interruptedexception)
            {
                displayDebug("***********************************************");
                displayDebug("  InterruptedException");
                displayDebug("-----------------------------------------------");
                displayDebug("Thread: " + Thread.currentThread());
                displayDebug("Exception: " + interruptedexception);
                displayDebug("***********************************************");
                if(Initializer.m_zwindow.IsZeroPortDocked())
                    OSDetectNative.undock();
                DialObject.doDisconnect();
                System.exit(1);
            }
    }

    public static void centerComponent(Component component)
    {
        if(component != null)
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Point point = new Point((dimension.width - component.getSize().width) / 2, (dimension.height - component.getSize().height) / 2);
            component.setLocation(point.x, point.y);
        }
    }

    private static void checkCompaqRegistration()
    {
        String s = OSDetectNative.getRegValue("SOFTWARE\\NetZero, Inc.\\Netzero\\CPQ", "Reg", false).trim();
        if(s != null && s.equals("incomplete"))
        {
            String as[] = {
                "ok"
            };
            NZDialogBox.showMessageDialog(m_resNZResource.getString("NetZero_not_registered"), m_resNZResource.getString("Please_activate_Compaq_reg"), 2, as);
            System.exit(3);
        }
    }

    public static boolean checkDirectory(String s)
    {
        File file = new File(s);
        try
        {
            if(file.exists() && file.isDirectory())
                return true;
            else
                return file.mkdirs();
        }
        catch(Exception exception)
        {
            displayDebug(exception);
        }
        return false;
    }

    public static boolean checkFile(String s)
    {
        File file = new File(s);
        try
        {
            if(file.exists())
                return true;
        }
        catch(Exception exception)
        {
            displayDebug(exception);
        }
        return false;
    }

    public static void checkZCast()
        throws NZException
    {
        if(m_osDetectNative.getHandle("NZJavaWindow") > 0 || m_osDetectNative.getHandle("NetZero v" + getZcastVersion() + " Logon") > 0)
            throw new NZException(300, m_resNZResource.getString("ZeroPort_is_already_running"));
        else
            return;
    }

    private static void createProfile()
    {
        m_profile = new Profile();
        String s = OSDetectNative.getRegValue("SOFTWARE\\NetZero, Inc.\\Netzero\\CPQ", "MemberID", true).trim();
        if(s != null && s.length() > 3)
        {
            m_profile.setSaveLoginState(true);
            m_profile.setProfUid(s);
            m_profile.setProfPwd("");
            m_profile.setFirstTimeFlag(false);
        }
    }

    protected void debugSetting()
    {
        String s = System.getProperty("nz.debugMsgs");
        if(s != null)
            m_nzDebugMode = s;
        displayDebug("debug", "DebugMode set to " + m_nzDebugMode);
        s = System.getProperty("nz.authMode");
        if(s != null)
            m_authenticationMode = Integer.parseInt(s);
        s = System.getProperty("nz.cfIP");
        if(s != null)
        {
            if(s.equals("dev"))
                m_cfIP = m_develIP;
            else
                m_cfIP = s;
            displayDebug("configuration processing routed to: " + m_cfIP);
        }
        String s1 = System.getProperty("nz.demo");
        if(s1 != null)
            m_demoMode = true;
    }

    public static synchronized void displayDebug(int i)
    {
        displayDebug("on", i);
    }

    public static synchronized void displayDebug(Exception exception)
    {
        displayDebug("on", exception);
    }

    public static synchronized void displayDebug(Object obj)
    {
        displayDebug("on", obj);
    }

    public static synchronized void displayDebug(String s)
    {
        displayDebug("on", s);
    }

    public static synchronized void displayDebug(String s, int i)
    {
        if(m_nzDebugMode.indexOf(s) != -1 || m_nzDebugMode.equals("on"))
            System.out.println(i);
    }

    public static synchronized void displayDebug(String s, Exception exception)
    {
        if(m_nzDebugMode.indexOf(s) != -1 || m_nzDebugMode.equals("on"))
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public static synchronized void displayDebug(String s, Object obj)
    {
        if(m_nzDebugMode.indexOf(s) != -1 || m_nzDebugMode.equals("on"))
            System.out.println(obj.toString());
    }

    public static synchronized void displayDebug(String s, String s1)
    {
        if(m_nzDebugMode.indexOf(s) != -1 || m_nzDebugMode.equals("on"))
            System.out.println(s1);
    }

    public static void generateSessionId()
    {
        Long long1 = getMachineIDReg();
        if(long1 == null)
        {
            Initializer.m_sessionId = null;
            return;
        }
        Long long2 = getSessionCtrReg();
        if(long2 == null)
            long2 = new Long(0L);
        else
            long2 = new Long(long2.longValue() + 1L);
        setSessionCtrReg(long2);
        if(long2.longValue() > 0xf423fL)
        {
            Initializer.m_sessionId = null;
            return;
        } else
        {
            Initializer.m_sessionId = new Long((long1.longValue() * 0xf4240L + long2.longValue()) * 10L + 9L);
            return;
        }
    }

    public static String getBrowserToUse()
    {
        return browserToUse;
    }

    public static Long getConnectionTime()
    {
        Date date = new Date();
        Long long1 = new Long(date.getTime() - m_connectionStart.getTime());
        return long1;
    }

    static boolean getHasEmailClient()
    {
        return hasEmailClient;
    }

    public static ZCast getInstance()
    {
        return TheApp;
    }

    public static Long getMachineIDReg()
    {
        String s = OSDetectNative.getRegValueData(regRoot, regKey, regMachineID);
        Long long1 = null;
        if(!s.equals(""))
            long1 = new Long(s);
        return long1;
    }

    public static Long getSessionCtrReg()
    {
        String s = OSDetectNative.getRegValueData(regRoot, regKey, regSessionCtr);
        Long long1 = null;
        if(!s.equals(""))
            long1 = new Long(s);
        return long1;
    }

    public static String getZcastVersion()
    {
        return ConfigParams.getVers();
    }

    private static boolean isBrowserSet(String s, boolean flag)
    {
        String s1 = null;
        if(flag)
            s1 = "http";
        else
            s1 = "https";
        String s2 = "SOFTWARE\\Classes\\" + s1 + "\\shell\\open\\";
        String s3 = s2 + "command";
        boolean flag1 = m_osDetectNative.setRegValue(s3, "", s);
        s3 = s2 + "ddeexec";
        flag1 &= m_osDetectNative.setRegValue(s2, "", "\"%1\",,-1,0,,,,");
        s3 = s2 + "ddeexec" + "\\Application";
        flag1 &= m_osDetectNative.setRegValue(s3, "", "IExplore");
        s3 = s2 + "ddeexec" + "\\Topic";
        flag1 &= m_osDetectNative.setRegValue(s3, "", "WWW_OpenURL");
        return flag1;
    }

    public void keepTheMainFrameOnTop()
    {
        if(topFrame == null)
            return;
        nzWin = Initializer.m_zwindow;
        if(nzWin == null)
        {
            m_osDetectNative.floatWindow(topFrame.getTitle());
            return;
        }
        onTop = true;
        synchronized(nzWin.getLockForFloat())
        {
            onTop = m_osDetectNative.floatWindow(topFrame.getTitle());
        }
        if(!onTop)
        {
            String as[] = {
                "ok"
            };
            NZDialogBox.showMessageDialog(m_resNZResource.getString("ERROR!"), m_resNZResource.getString("You_can_run_NetZero_but_yo"), 2, as, 7, m_resNZResource.getString("seconds_till_self-destruct"));
            m_emergencyExit = true;
            terminateProgram(0, null);
        }
    }

    public static void main(String args[])
    {
        try
        {
            System.loadLibrary("OsUtil");
        }
        catch(Exception _ex)
        {
            System.out.println("Failed to load library OsUtil.dll.");
        }
        try
        {
            System.loadLibrary("NzAdv");
        }
        catch(Exception _ex)
        {
            System.out.println("Failed to load library NzAdv.dll.");
        }
        TheApp = new ZCast(args);
        (new Thread(TheApp, TheApp, "ZeroPort Bootstrap")).start();
        displayDebug("***********************************************");
        displayDebug("  Exiting ZCast.main()");
        displayDebug("***********************************************");
    }

    public static void markConnectionTime()
    {
        m_connectionStart = new Date();
    }

    private static void preliminaryChecking()
        throws NZException
    {
        displayDebug("Catch Unexp Excep");
        OSDetectNative.catchingUnexpExcep();
        if(!checkDirectory("cache") || !checkFile("bin/net.dll"))
            throw new NZException(300, m_resNZResource.getString("NetZero_directory_structur"));
        int i = setBrowser();
        if(i != 0)
        {
            if(i == 1)
                throw new NZException(300, m_resNZResource.getString("Unable_to_set_default_brow"));
            if(i == 2)
                throw new NZException(300, m_resNZResource.getString("Unable_to_set_the_default_1"));
        }
        i = setEmailClient();
        if(i != 0)
            hasEmailClient = false;
        SoftwareSyncClient.doUpdate();
        ClientZip2.checkDist("lib/zcast1_6.zip", "newsd.zip", "lib/zcastsd.zip");
        checkCompaqRegistration();
        ProcessZip.delayedProcess(m_osDetectNative, "chkras SWDT");
        m_suppressSD = System.getProperty("nz.NOSoFt_DisTribute") != null;
        if(!m_suppressSD)
            ProcessZip.postProcess();
        checkZCast();
        createProfile();
    }

    public static void register(SoftwareExit softwareexit)
    {
        m_regclasses.addElement(softwareexit);
    }

    public void run()
    {
        OSDetectNative.loadProcessIcon("./images/nz_icon.ico");
        debugSetting();
        displayDebug("Program Material (C) Copyright 1998-2000 NetZero, Inc. - all rights reserved");
        displayDebug("Some portions (C) Copyright 1998 KL Group, Inc. - all rights reserved");
        displayDebug("");
        try
        {
            preliminaryChecking();
        }
        catch(NZException nzexception)
        {
            nzexception.printStackTrace();
            if(nzexception.getSeverity() >= 300)
            {
                System.err.println("PreliminaryChecking FAILED, ABORTING:" + nzexception);
                m_emergencyExit = true;
                terminateProgram(120, nzexception.getMessage());
            }
        }
        topFrame = new Frame("NzJavaWindow");
        Startup startup = new Startup(new Frame(), false, m_profile);
        startup.show();
        startup.toFront();
        int i = OSDetectNative.getWindowHandle(startup.getTitle());
        OSDetectNative.removeSystemMenuItemByHandle(i, 4);
        OSDetectNative.removeSystemMenuItemByHandle(i, 3);
        OSDetectNative.removeSystemMenuItemByHandle(i, 2);
        OSDetectNative.removeSystemMenuItemByHandle(i, 0);
        awaitTerminationCondition();
    }

    private static int setBrowser()
    {
        String s = OSDetectNative.getRegValue("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\IEXPLORE.EXE", "", false);
        if(s != null && s.length() > 0)
            s = s + " -nohome";
        browserToUse = s;
        String s1 = OSDetectNative.getRegValue("SOFTWARE\\Classes\\http\\shell\\open\\command", "", false);
        if(s1 == null || s1.indexOf("aol.exe") != -1 || s1.length() == 0)
        {
            if(s != null && s.length() > 0)
            {
                if(!isBrowserSet(s, true))
                    return 1;
            } else
            {
                return 1;
            }
        } else
        {
            browserToUse = s1;
        }
        s1 = OSDetectNative.getRegValue("SOFTWARE\\Classes\\https\\shell\\open\\command", "", false);
        if(s1 == null || s1.indexOf("aol.exe") != -1 || s1.length() == 0)
            if(s != null && s.length() > 0)
            {
                if(!isBrowserSet(s, false))
                    return 2;
            } else
            {
                return 2;
            }
        return 0;
    }

    private static int setEmailClient()
    {
        String s = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail", "", false);
        if(s == null || s.indexOf("AOL") != -1 || s.length() == 0)
        {
            String s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\Outlook Express", "", false);
            if(s1 == null || s1.length() == 0)
            {
                s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\Netscape Messenger", "", false);
                if(s1 == null || s1.length() == 0)
                {
                    s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\Eudora", "", false);
                    if(s1 == null || s1.length() == 0)
                    {
                        s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\Microsoft Outlook", "", false);
                        if(s1 != null && s1.length() > 0)
                            s1 = "Microsoft Outlook";
                    } else
                    {
                        s1 = "Eudora";
                    }
                } else
                {
                    s1 = "Netscape Messenger";
                }
            } else
            {
                s1 = "Outlook Express";
            }
            if(s1 != null && s1.length() > 0)
            {
                boolean flag = m_osDetectNative.setRegValue("SOFTWARE\\Clients\\Mail", "", s1);
                if(!flag)
                {
                    String as1[] = {
                        "ok"
                    };
                    NZDialogBox.showMessageDialog(m_resNZResource.getString("No_Default_Email_Client"), m_resNZResource.getString("Unable_to_set_the_default_"), 3, as1);
                    return 1;
                }
            } else
            {
                String as[] = {
                    "ok"
                };
                NZDialogBox.showMessageDialog(m_resNZResource.getString("No_Default_Email_Client"), m_resNZResource.getString("No_Email_Client_found._Ple"), 3, as);
                return 2;
            }
        }
        return 0;
    }

    public static void setMachineIDReg(Long long1)
    {
        OSDetectNative.createRegKey(regRoot, regKey);
        OSDetectNative.setRegValueData(regRoot, regKey, regMachineID, String.valueOf(long1));
    }

    public static void setSessionCtrReg(Long long1)
    {
        OSDetectNative.createRegKey(regRoot, regKey);
        OSDetectNative.setRegValueData(regRoot, regKey, regSessionCtr, String.valueOf(long1));
    }

    private static void showStartupEnvironment(String as[])
    {
        displayDebug("***********************************************");
        displayDebug("  ZCast.main()");
        displayDebug("-----------------------------------------------");
        for(int i = 0; i < as.length; i++)
            displayDebug("args[" + i + "]: " + as[i]);

        showThreads();
        displayDebug("***********************************************");
    }

    private static void showThreads()
    {
        Thread athread[] = new Thread[100];
        Object obj = null;
        ThreadGroup threadgroup;
        ThreadGroup threadgroup1;
        for(threadgroup1 = Thread.currentThread().getThreadGroup(); (threadgroup = threadgroup1.getParent()) != null; threadgroup1 = threadgroup);
        int i = threadgroup1.enumerate(athread);
        for(int j = 0; j < i; j++)
            displayDebug("thread[" + j + "]: " + athread[j]);

    }

    public static void terminateProgram(int i, String s)
    {
        displayDebug("@@ ZCast.terminateProgram ***********");
        exitStatusCode = i;
        exitMessage = s;
        try
        {
            if(m_regclasses != null)
            {
                for(int j = 0; j < m_regclasses.size(); j++)
                    ((SoftwareExit)m_regclasses.elementAt(j)).endPreProcessing();

            }
            OSDetectNative.undock();
            if(ErrorChecker.getErrorChecker() != null)
                if(i != ErrorChecker.ERRORCHECKCODE)
                    ErrorChecker.getErrorChecker().terminate();
                else
                    i = 0;
            if(Initializer.m_ulogThread != null)
                Initializer.m_ulogThread.programIsTerminating();
            DialObject.doDisconnect();
            if(m_regclasses != null)
            {
                for(int k = 0; k < m_regclasses.size(); k++)
                    ((SoftwareExit)m_regclasses.elementAt(k)).endPostProcessing();

            }
            TviPlayer.getInterface().cleanup();
            OSDetectNative.osw32uninitVolume();
            BrowserDisplay.releaseBrowser();
            if(s != null)
            {
                String as[] = {
                    "ok"
                };
                NZDialogBox.showMessageDialog(m_resNZResource.getString("The_ZeroPort_is_terminatin"), s, 3, as);
            }
            OSDetectNative.unloadProcessIcon();
            OSDetectNative.cleanUpCookieResources();
            displayDebug("<>SPE shutting down...");
            SPEngine.end();
            displayDebug("END OF TERMINATE PROGRAM ");
        }
        catch(Exception exception1)
        {
            displayDebug(exception1);
        }
        finally
        {
            displayDebug("in finally");
            System.exit(i);
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable)
    {
        displayDebug("***********************************************");
        displayDebug("  Uncaught Exception");
        displayDebug("-----------------------------------------------");
        displayDebug("Thread: " + thread);
        displayDebug(throwable);
        displayDebug("***********************************************");
    }

    private static final String m_cpqReg = "SOFTWARE\\NetZero, Inc.\\Netzero\\CPQ";
    public static ResourceBundle m_resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    public static String m_nzDebugMode = "off";
    public static boolean m_suppressSD = false;
    public static boolean m_demoMode = false;
    public static long m_authCode = 0x147ebabL;
    public static int m_authenticationMode = 2;
    public static boolean m_emergencyExit = false;
    public static String m_cfIP = "cf.netzero.net";
    public static String m_develIP = "216.100.161.20";
    public static Vector m_regclasses = new Vector();
    public static Profile m_profile;
    public static RASWinNative m_rasWinNative = null;
    public static OSDetectNative m_osDetectNative = new OSDetectNative();
    public static int m_connectionType = 2;
    public static Date m_connectionStart = null;
    private static String regRoot = "HKEY_LOCAL_MACHINE";
    private static String regKey = "SOFTWARE\\NetZero, Inc.\\NetZero";
    private static String regMachineID = "MachineID";
    private static String regSessionCtr = "SessionCtr";
    public static Frame topFrame = null;
    private static boolean hasEmailClient = true;
    private static String browserToUse = null;
    static ZCast TheApp = null;
    protected boolean timeToDie_;
    private static int exitStatusCode = 0;
    private static String exitMessage = null;
    private static NZWindow nzWin = null;
    private static boolean onTop = true;

}
