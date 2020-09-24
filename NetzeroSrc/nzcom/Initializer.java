// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Initializer.java

package nzcom;

import admgmt.*;
import errors.NZErrors;
import exception.NZException;
import gui.*;
import java.awt.Component;
import java.io.File;
import java.util.*;
import pool.*;
import serviceThread.ServiceThread;
import softwareSync.*;
import spe.DefaultEngineListener;
import spe.SPEngine;
import sysinfo.SysInfo;
import sysinfo.SysInfoDriver;
import tcpBinary.*;
import transaction.client.UlogThread;
import ui.ClientUI;

// Referenced classes of package nzcom:
//            MonitorThread, ProcessZip, MsgProcObj, ErrorChecker, 
//            NZUserLog, Profile, NZWindow, ServerParms, 
//            OSDetectNative, RASWinNative, ZCast

public class Initializer extends Thread
    implements TransactionProgress
{

    public Initializer(Startup startup)
    {
        inStartup = startup;
        me = this;
    }

    public static void checkDisconnect(int i)
        throws NZException
    {
        if(ZCast.m_rasWinNative != null && ZCast.m_rasWinNative.getStatus() != 0)
            throw new NZException(300, m_resNZResource.getString("Drop_in_connection"));
        else
            return;
    }

    public static void compareTransResponse(TransactionResponse transactionresponse, String s)
    {
        ZCast.displayDebug("------------- transaction (" + s + ") related test -------------");
        ZCast.displayDebug("TransactionResponse.UserNumber     :" + transactionresponse.getUserNumber() + "(" + m_userNumber + ")");
        ZCast.displayDebug("TransactionResponse.sessionId      : " + transactionresponse.getSessionId() + "(" + m_sessionId + ")");
        ZCast.displayDebug("------------- end transaction related test -----------------\n");
    }

    public static void displayURL(String s)
    {
        if(s.trim().equals(""))
        {
            ZCast.displayDebug("no url");
            return;
        } else
        {
            OSDetectNative.showUrl(s);
            ZCast.displayDebug("URL DISPLAYED: " + s);
            return;
        }
    }

    private static void showHomePage()
    {
        if(ZCast.m_demoMode)
        {
            OSDetectNative.showUrl("file:demo/clubzero/index.htm", true);
            return;
        }
        String s = null;
        if(m_memberProfileStatus == 2 || m_memberProfileStatus == 3)
        {
            s = ServerParms.getParm("UpdateProfileURL", "https://gold.netzero.net/servlets/updateprofile?");
            int i = s.indexOf("https://");
            if(i == -1)
                i = s.indexOf("http://") + 7;
            else
                i += 8;
            s = s.substring(0, i) + "\"" + s.substring(i) + "&MemberID=" + MemberRecs.getCurrentMemberID() + "\"";
        } else
        if(m_homePage instanceof Vector)
        {
            String s1 = OSDetectNative.getRegValue("SOFTWARE\\Compaq\\Internet\\Netzero", "URL", true).trim();
            if(s1 != null && s1.length() > 5)
                m_homePage.setElementAt(s1, 0);
            m_zwindow.setHomePageUrl(m_homePage);
            s = (String)m_homePage.elementAt(0);
        } else
        {
            ZCast.displayDebug("INITIALIZER!! SOMETHING SERIOUSLY WRONG!!!... ");
        }
        if(s != null)
            OSDetectNative.showUrl(s, true);
        ZCast.displayDebug("INITIALIZER!! home page=" + s);
        if(m_homeFlags != null && (m_homeFlags.elementAt(0) instanceof String))
        {
            ZCast.displayDebug("m_homeFlags.elementAt(0)=" + m_homeFlags.elementAt(0));
            if(((String)m_homeFlags.elementAt(0)).trim().equalsIgnoreCase("true"))
                m_showMailConfig = true;
        }
    }

    public static boolean doLGN2()
    {
        if(ZCast.m_demoMode)
        {
            ZCast.displayDebug("In DEMO mode, skipping LGN2 transaction.");
            return true;
        }
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("lgip1", transaction.getServerLocation()));
        Vector vector = new Vector(14);
        String s = MemberRecs.getCurrentMemberID();
        vector.addElement(s);
        ZCast.displayDebug(" LoginID = " + s);
        String s1 = MemberRecs.getCurrentPassword();
        vector.addElement(s1);
        ZCast.displayDebug(" Password = " + s1);
        String s2 = ZCast.m_profile.getPhoneLocation();
        vector.addElement(s2);
        ZCast.displayDebug(" Phonelocation = " + s2);
        DirectoryList directorylist = SoftwareSyncClient.createDirectoryList(".");
        softwareSync.FileStats afilestats[] = directorylist.getFileList();
        vector.addElement(directorylist);
        vector.addElement("SD Version " + ZCast.getZcastVersion());
        vector.addElement(System.getProperty("os.name"));
        vector.addElement(System.getProperty("os.version"));
        vector.addElement(s2.substring(0, 3));
        vector.addElement(s2.substring(4, 7));
        vector.addElement(s2.substring(13, 15));
        vector.addElement(s2.substring(16));
        Object obj = ClientUI.deserializeUi("cache.obj");
        if(obj != null && (obj = ClientUI.deserializeUi("shellui.obj")) != null)
            vector.addElement(obj.toString());
        else
            vector.addElement(null);
        vector.addElement(getURLResDate(m_userNumber));
        String s3 = DialGroups.getConnectDialFrom();
        vector.addElement(s3);
        ZCast.displayDebug("NPANXX =" + s3);
        TransactionRequest transactionrequest = new TransactionRequest(s, ZCast.getZcastVersion(), vector);
        transactionrequest.setUserNumber(m_userNumber);
        transactionrequest.setSessionId(m_sessionId);
        transaction.setTpInterface(me);
        TransactionResult transactionresult = transaction.execute(transactionrequest, "LGN2");
        try
        {
            if(!(transactionresult.getDataObject() instanceof String))
            {
                if(transactionresult.getDataObject() instanceof TransactionResponse)
                {
                    TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
                    compareTransResponse(transactionresponse, "LGN2");
                    Object aobj[] = (Object[])transactionresponse.getReturnObject();
                    ZCast.displayDebug("Object fromServer = " + aobj);
                    if(aobj != null)
                        ZCast.displayDebug(aobj.length + " objects from server");
                    if(aobj != null)
                    {
                        if(aobj.length >= 4)
                        {
                            ClientUI.serializeObject(aobj[2], "shellui.obj");
                            ClientUI.serializeObject(aobj[3], "cache.obj");
                            ClientUI.serializeObject(aobj[4], "pdata.obj");
                        }
                        if(aobj.length > 4 && aobj[5] != null && (aobj[5] instanceof Hashtable))
                            ClientUI.serializeObject(aobj[5], String.valueOf(m_userNumber) + ".urlcache.obj");
                        if(aobj.length > 5 && aobj[6] != null && (aobj[6] instanceof Vector))
                        {
                            Vector vector1 = (Vector)aobj[6];
                            if(vector1 != null)
                            {
                                String s4 = (String)vector1.elementAt(0);
                                if(s4 != null)
                                {
                                    ZCast.displayDebug("p_server_list = " + s4);
                                    DialGroups.setServerNums(s4);
                                    DialGroups.writeGroups();
                                    String s5 = (String)vector1.elementAt(1);
                                    ZCast.displayDebug("npanxxOut = " + s5);
                                }
                            }
                        }
                    } else
                    {
                        return false;
                    }
                    m_memberProfileStatus = transactionresponse.getReturnCode();
                    ZCast.displayDebug("LGN2!! m_memberProfileStatus = " + m_memberProfileStatus);
                    if(!ZCast.m_suppressSD)
                    {
                        DistributionTask adistributiontask[] = (DistributionTask[])aobj[1];
                        if(adistributiontask != null)
                        {
                            boolean flag = false;
                            ZCast.displayDebug("server task count = " + adistributiontask.length);
                            for(int i = 0; i < adistributiontask.length; i++)
                                if(adistributiontask[i] == null)
                                {
                                    flag = true;
                                    ZCast.displayDebug("server task " + i + " was null");
                                } else
                                {
                                    ZCast.displayDebug("processing " + adistributiontask[i].getTask());
                                }

                            if(!flag)
                            {
                                SoftwareSyncClient softwaresyncclient = new SoftwareSyncClient();
                                softwaresyncclient.run(adistributiontask);
                            }
                        } else
                        {
                            ZCast.displayDebug("no software tasks returned");
                        }
                    } else
                    {
                        ZCast.displayDebug("Software distribution processing bypassed");
                    }
                } else
                {
                    return false;
                }
            } else
            {
                return false;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            return false;
        }
        return true;
    }

    public static void doPARM()
        throws Exception
    {
        m_userNumber = null;
        loadLocalServerParms();
        String s = ServerParms.getParm("UseCompression", "yes");
        if(s.equals("yes"))
        {
            Services.setRecvCompressedData(true);
            Services.setSendCompressedData(true);
            ZCast.displayDebug("compression active");
        }
        if(ZCast.m_connectionType == 0)
            checkDisconnect(39);
        m_mailIntervalString = ServerParms.getParm("CheckMail", m_mailIntervalString);
        m_mailInterval = Integer.parseInt(m_mailIntervalString);
    }

    public static Initializer getInitializer()
    {
        return me;
    }

    private static Long getURLResDate(Integer integer)
    {
        try
        {
            File file = new File(integer.toString() + ".urlcache.obj");
            return new Long(file.lastModified());
        }
        catch(Exception _ex)
        {
            return new Long(12345L);
        }
    }

    private static void initOnline()
        throws NZException
    {
        if(!doLGN2())
            throw new NZException(300, m_resNZResource.getString("Login_transaction_failed"));
        inStartup.connectNotification();
        NZUserLog nzuserlog = NZUserLog.getDefaultUserLog();
        ZCast.displayDebug("INITIALIZER!! UserLog created=" + nzuserlog);
        m_zwindow = new NZWindow(ZCast.topFrame);
        m_zwindow.setOsDetect(ZCast.m_osDetectNative);
        m_zwindow.setNzPassword(MemberRecs.getCurrentPassword());
        ErrorChecker.getErrorChecker().start();
        m_zwindow.setProfile(ZCast.m_profile);
        ZCast.displayDebug("INITIALIZER!! Startup state is " + Startup.startUpState);
        try
        {
            if(Startup.startUpState == 1)
            {
                ZCast.displayDebug("INITIALIZER!! going to sleep to wait for startup to finish...");
                Thread.sleep(Integer.parseInt(ServerParms.getParm("WaitTimeBeforeZeroPortStart", "40")) * 1000);
            }
        }
        catch(InterruptedException _ex)
        {
            if(Startup.startUpState == 0)
            {
                ZCast.displayDebug("INITIALIZER!! AWAKENS! and the world continues...");
            } else
            {
                ZCast.displayDebug("INITIALIZER!! AWAKENS! User is cancelling...exit this thread.");
                throw new NZException(300, "User Cancel");
            }
        }
        if(Startup.startUpState == 2)
        {
            ZCast.displayDebug("INITIALIZER!! User is cancelling...exit this thread.");
            throw new NZException(300, "User Cancel");
        }
        inStartup.cleanup();
        inStartup = null;
        ZCast.displayDebug("INITIALIZER!! STARTING SERVICE THREAD");
        MsgProcObj msgprocobj = new MsgProcObj();
        m_serviceThread = new ServiceThread(30);
        m_zwindow.initialize();
        m_zwindow.requestFocus();
        showHomePage();
        if(!ZCast.m_demoMode && m_showMailConfig)
            m_zwindow.setupEmailAccount();
        monitoringThread();
        showZeroInAdURL();
        ZeroInPlayList.getZeroInPlayList().sendStats();
    }

    private static void initOnlineLowPriority()
        throws NZException
    {
        if(ZCast.m_demoMode)
        {
            SysInfoDriver.updateHardwareProfile();
            return;
        }
        PlayAd.cleanUpCacheDir();
        startAdPreFetch();
        if(ServerParms.getParm("UlogThreadOff", "false").equals("false"))
            m_ulogThread = new UlogThread();
        NZUserLog.deserializeStats();
        NZErrors.getInstance().sendErrors();
        ProcessZip.processPhoneList();
        SPEngine.getInstance().setLanMode(ZCast.m_connectionType == 1);
        int i = Integer.parseInt(ServerParms.getParm("Max_Kbps", "-1"));
        ZCast.displayDebug(" <><> SPE: READ Max_Kbps = " + i + " from ServerParams\n\n");
        if(i < 1)
            i = 5;
        if(ZCast.m_connectionType != 2)
        {
            ZCast.displayDebug("<>SPE starting up...");
            SPEngine.begin(".\\basket", ZCast.m_nzDebugMode.equals("on") ? ((spe.SPEngineListener) (new DefaultEngineListener())) : null, i);
        }
        updateAdPools();
        (new SystemUpdate()).start();
    }

    private static void loadLocalServerParms()
    {
        try
        {
            ServerParms.setProgressListener(me);
            if(ZCast.m_cfIP.startsWith("local"))
            {
                ServerParms.loadPropertiesFromFile("serverparms");
                String s = ZCast.m_cfIP;
                ZCast.m_cfIP = ServerParms.getParm("cfip1", ZCast.m_cfIP);
                ZCast.displayDebug("-->cfIP: " + ZCast.m_cfIP);
                ServerParms.getFromServer(ZCast.m_cfIP);
                ZCast.m_cfIP = s;
                ServerParms.loadPropertiesFromFile("serverparms");
            } else
            {
                ServerParms.getFromServer(ZCast.m_cfIP);
            }
            ServerParms.dump("parms", System.out);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("parms", exception);
            ZCast.terminateProgram(31, ZCast.m_resNZResource.getString("Unable_to_contact_the_NetZ"));
        }
    }

    private static void monitoringThread()
    {
        if(ZCast.m_connectionType == 0)
        {
            ZCast.displayDebug("\n\n\n\n\n\tSTART THE MONITOR THREAD");
            m_zwindow.setMonitorThread(new MonitorThread(m_zwindow, MemberRecs.getIsNewUser()));
            m_zwindow.getMonitorThread().start();
        } else
        {
            m_zwindow.setMonitorThread(new MonitorThread(m_zwindow, MemberRecs.getIsNewUser()));
            m_zwindow.getMonitorThread().start();
        }
    }

    public void run()
    {
        try
        {
            try
            {
                ZCast.markConnectionTime();
                DialObject.doConnect(2, inStartup);
            }
            catch(NZException nzexception)
            {
                if(nzexception.getSeverity() >= 300)
                {
                    ZCast.displayDebug("CONNECTION FAILED, ABORTING:" + nzexception);
                    throw nzexception;
                }
            }
            try
            {
                doPARM();
                if(OSDetectNative.ieIsDefaultBrowser())
                    setGmInfo();
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
                throw exception;
            }
            try
            {
                initOnline();
            }
            catch(NZException nzexception1)
            {
                nzexception1.log();
                if(nzexception1.getSeverity() >= 300)
                {
                    ZCast.displayDebug("INIT ONLINE FAILED, ABORTING:" + nzexception1);
                    throw nzexception1;
                }
            }
            initOnlineLowPriority();
        }
        catch(Exception exception1)
        {
            ZCast.displayDebug(exception1);
            if(!(exception1 instanceof NZException) || !((NZException)exception1).getMessage().equals("User Cancel"))
                inStartup.cancelNotification(exception1.getMessage());
            if(ErrorChecker.getErrorChecker().isAlive())
                ErrorChecker.getErrorChecker().terminate();
            Startup.startUpState = 1;
        }
    }

    public static void setHomePageAndFlag(Vector vector, Vector vector1)
    {
        m_homePage = vector;
        m_homeFlags = vector1;
    }

    private static void setHomePageAndFlag(TransactionResponse transactionresponse)
    {
        Vector vector = (Vector)transactionresponse.getReturnObject();
        if(vector.elementAt(0) instanceof Vector)
            m_homePage = (Vector)vector.elementAt(0);
        else
            ZCast.displayDebug("Home Page Vector is null");
        if(vector.elementAt(1) instanceof Vector)
            m_homeFlags = (Vector)vector.elementAt(1);
        else
            ZCast.displayDebug("Home Flag Vector is null");
    }

    public static void setZeroInAd(PlayAd playad, long l)
    {
        if(zeroInAd == null && zeroInAdClickTime == null)
        {
            zeroInAd = new Vector();
            zeroInAdClickTime = new Vector();
        }
        if(!zeroInAd.isEmpty() && zeroInAd.indexOf(playad) >= 0)
            return;
        if(zeroInAd.isEmpty() || playad.getAdDisplayType() == 2)
        {
            zeroInAd.addElement(playad);
            zeroInAdClickTime.addElement(new Long(l));
            return;
        } else
        {
            return;
        }
    }

    private static void showZeroInAdURL()
    {
        try
        {
            ZCast.displayDebug("in showZeroINAd URL ");
            if(zeroInAd != null)
            {
                ZCast.displayDebug("in showZeroINAd URL, # of urls to show " + zeroInAd.size());
                for(int i = 0; i < zeroInAd.size(); i++)
                    ((PlayAd)zeroInAd.elementAt(i)).clickThru(((Long)zeroInAdClickTime.elementAt(i)).longValue());

            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    private static void startAdPreFetch()
    {
        Vector vector = new Vector();
        int i;
        String s;
        for(i = 0; (s = ServerParms.getParm("PreFetch" + i, null)) != null; i++)
        {
            Integer integer = null;
            Vector vector1 = new Vector();
            StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
            ZCast.displayDebug("test1", "AdPT" + i + "=" + s);
            while(stringtokenizer.hasMoreTokens()) 
                if(integer == null)
                {
                    integer = new Integer(stringtokenizer.nextToken());
                    ZCast.displayDebug("test1", "-->amt=" + integer);
                } else
                {
                    String s1 = stringtokenizer.nextToken();
                    ZCast.displayDebug("test1", "-->server=" + s1);
                    vector1.addElement(s1);
                    vector.addElement(s1);
                }
            AdPreFetcher adprefetcher;
            if(vector1.size() > 0)
                adprefetcher = new AdPreFetcher(integer.intValue(), vector1, true, "AdPT" + i);
            else
                adprefetcher = new AdPreFetcher(integer.intValue(), vector, false, "AdPT" + i);
            adprefetcher.start();
        }

        if(i == 0)
        {
            AdPreFetcher adprefetcher1 = new AdPreFetcher(3, null, true, "AdPT-Default!");
            adprefetcher1.start();
        }
    }

    private static void updateAdPools()
    {
        Object obj = null;
        Transaction transaction = new Transaction(false);
        try
        {
            String s = ZCast.m_profile.getPhoneLocation();
            Vector vector1 = new Vector(2);
            Vector vector2 = new Vector(6);
            vector2.addElement(MemberRecs.getCurrentMemberID());
            vector2.addElement(s.substring(0, 3));
            vector2.addElement(s.substring(4, 7));
            vector2.addElement(s.substring(13, 15));
            vector2.addElement(s.substring(16));
            vector2.addElement(NZUserLog.getDefaultUserLog().getCreateDateTime());
            vector1.addElement(vector2);
            int i = SysInfoDriver.getOldSystemInfo().getAdDisplayType();
            ZCast.displayDebug("About to perform PLYI: My machine's ad display type=" + i);
            vector1.addElement("2:" + i + "|" + 3 + ":" + i + "|" + 4 + ":" + i + "|");
            transaction.setServerLocation(ServerParms.getParm("plip1", transaction.getServerLocation()));
            TransactionRequest transactionrequest = new TransactionRequest();
            transactionrequest.setClientVersion(ZCast.getZcastVersion());
            transactionrequest.setUserNumber(m_userNumber);
            transactionrequest.setSessionId(m_sessionId);
            transactionrequest.setDataObject(vector1);
            transactionrequest.setUserId(MemberRecs.getCurrentMemberID());
            TransactionResult transactionresult = transaction.execute(transactionrequest, "PLYI");
            ZCast.displayDebug("PLYI: success flag set to: " + transactionresult.getSuccessFlag());
            if(transactionresult.getSuccessFlag())
            {
                long l = Long.parseLong(ServerParms.getParm("Server_Time", "0"));
                ZCast.displayDebug("\n<><> POOL: Received Server_Time: = " + (new Date(l)).toString());
                if(l != 0L)
                {
                    MarqueePool.getInstance().setServerDate(l);
                    ZeroInPool.getInstance().setServerDate(l);
                    ZeroOutPool.getInstance().setServerDate(l);
                }
                Object obj1 = transactionresult.getDataObject();
                if(obj1 instanceof TransactionResponse)
                {
                    TransactionResponse transactionresponse = (TransactionResponse)obj1;
                    ZCast.displayDebug("PLYI: return code = " + transactionresponse.getReturnCode());
                    if(transactionresponse.getReturnCode() == 0)
                    {
                        Object obj2 = transactionresponse.getReturnObject();
                        if(obj2 instanceof Vector)
                        {
                            Vector vector = (Vector)obj2;
                            for(int j = 0; j < 3; j++)
                            {
                                String s1 = (String)vector.elementAt(j);
                                switch(j)
                                {
                                case 0: // '\0'
                                    ZCast.displayDebug("Pool", "PLYI: Marquee Pool:\n" + s1);
                                    MarqueePool.getInstance().setUserId(MemberRecs.getCurrentMemberID());
                                    MarqueePool.getInstance().processTrans(s1);
                                    break;

                                case 1: // '\001'
                                    ZCast.displayDebug("Pool", "PLYI: ZeroIn Pool:\n" + s1);
                                    ZeroInPool.getInstance().setUserId(MemberRecs.getCurrentMemberID());
                                    ZeroInPool.getInstance().processTrans(s1);
                                    break;

                                case 2: // '\002'
                                    ZCast.displayDebug("Pool", "PLYI: ZeroOut Pool:\n" + s1);
                                    ZeroOutPool.getInstance().setUserId(MemberRecs.getCurrentMemberID());
                                    ZeroOutPool.getInstance().processTrans(s1);
                                    break;
                                }
                            }

                            PoolCleaner poolcleaner = new PoolCleaner();
                            poolcleaner.removeOrphans();
                        } else
                        {
                            ZCast.displayDebug("PLYI: failed to return a valid object.");
                        }
                    }
                } else
                {
                    ZCast.displayDebug("PLYI: failed to return a TransactionResponse object.");
                }
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public void reportProgress(String s, int i)
    {
        if(inStartup == null)
        {
            ZCast.displayDebug("\n\nCANNOT display transaction progress. Startup is null.\n");
            return;
        }
        String s1 = "";
        if(s.equals("LGN2"))
            s1 = "Updating user interface:";
        else
        if(s.equals("PRM2"))
            s1 = "Retrieving user preferences:";
        else
            ZCast.displayDebug("Initializer: Transaction code not caught: " + s);
        inStartup.setStatusLabel(s1 + " " + i + "% complete.");
    }

    public static void setGmInfo()
    {
        String s = ServerParms.getParm("GMDomain", "");
        if(s == null || s.equals(""))
            return;
        StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
        g_gmCookieDomain = stringtokenizer.nextToken();
        if(stringtokenizer.hasMoreTokens())
            g_gmAdDomain = stringtokenizer.nextToken();
        if(g_gmAdDomain == null)
            g_gmAdDomain = g_gmCookieDomain;
    }

    private static ResourceBundle m_resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private static boolean m_showMailConfig = false;
    private static int m_currentDialIndex = -1;
    public static Integer m_userNumber = null;
    public static Long m_sessionId = null;
    private static final int COMPLETE = 0;
    private static final int INCOMPLETE = 2;
    private static final int OUTDATED = 3;
    private static int m_memberProfileStatus = 0;
    private static Vector m_homePage = null;
    private static Vector m_homeFlags = null;
    public static ServiceThread m_serviceThread;
    public static UlogThread m_ulogThread;
    public static final String URL_CACHE_EXT = ".urlcache.obj";
    private static Startup inStartup = null;
    public static NZWindow m_zwindow;
    public static int m_mailInterval = 2;
    private static String m_mailIntervalString = "2";
    private static Vector zeroInAd = null;
    private static Vector zeroInAdClickTime = null;
    public static String g_gmCookieDomain = null;
    public static String g_gmAdDomain = null;
    private static Initializer me = null;

}
