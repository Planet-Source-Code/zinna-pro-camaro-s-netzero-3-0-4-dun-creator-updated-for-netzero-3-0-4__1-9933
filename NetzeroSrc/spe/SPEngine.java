// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPEngine.java

package spe;

import java.io.File;
import nzcom.ZCast;

// Referenced classes of package spe:
//            SPNative, SPListItem, SPEngineListener, BooleanLock, 
//            SPList, SPItemListener

public class SPEngine extends Thread
{

    protected SPEngine()
    {
        super("Smart Pull Engine");
        m_sTempDir = ".\\basket";
        m_fMaxKbps = 5F;
        m_List = new SPList();
        m_CurrentItem = null;
        m_bLanMode = false;
        m_nBeginMillis = 0L;
        m_nBeginIdleTimeoutMillis = 0L;
        m_bIdle = false;
        m_bTimeToExit = false;
        m_ProcessLock = new BooleanLock(false);
        m_Callback = null;
    }

    public void add(String s, String s1, int i, int j, SPItemListener spitemlistener, Object obj)
    {
        String s2 = getTempDir() + "\\" + parseAndGetLastDirName(s1) + parseAndGetFileName(s1);
        m_List.add(s, s1, s2, i, m_bLanMode ? 4 : j, spitemlistener, obj);
        notifyTimeToProcess();
    }

    public static synchronized void begin(String s, SPEngineListener spenginelistener)
    {
        SPEngine spengine = getInstance();
        if(!spengine.isAlive())
        {
            spengine.setTempDir(s);
            spengine.setListener(spenginelistener);
            spengine.start();
        }
    }

    public static synchronized void begin(String s, SPEngineListener spenginelistener, float f)
    {
        SPEngine spengine = getInstance();
        if(!spengine.isAlive())
        {
            spengine.setTempDir(s);
            spengine.setListener(spenginelistener);
            spengine.setMaxKbps(f);
            spengine.start();
        }
    }

    private void cleanUpOrphans()
    {
        deleteFiles(getTempDir(), (new File(getTempDir())).list(m_List));
    }

    protected void deleteFiles(String s, String as[])
    {
        try
        {
            for(int i = 0; i < as.length; i++)
                if(!(new File(s + "\\" + as[i])).delete())
                    error("Nonfatal error in SPEngine.deleteFiles. Failed to delete file " + as[i]);

        }
        catch(ArrayIndexOutOfBoundsException _ex) { }
        catch(Exception exception1)
        {
            error("Nonfatal error in SPEngine.deleteFiles. Error while deleting file. Exception: " + exception1.toString());
        }
    }

    public static synchronized void end()
        throws InterruptedException
    {
        if(m_Instance != null)
        {
            SPEngine spengine = getInstance();
            spengine.notifyTimeToExit();
            spengine.join(m_nJoinTimeout);
        }
    }

    protected void error(String s)
    {
        if(m_Callback != null)
            try
            {
                m_Callback.onEngineError(s);
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug(exception1);
            }
    }

    protected void warning(String s)
    {
        if(m_Callback != null)
            try
            {
                m_Callback.onEngineWarning(s);
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug(exception1);
            }
    }

    protected void exception(Exception exception1)
    {
        if(m_Callback != null)
            try
            {
                m_Callback.onEngineFatalException(exception1);
            }
            catch(Exception exception2)
            {
                ZCast.displayDebug(exception2);
            }
    }

    public static synchronized SPEngine getInstance()
    {
        if(m_Instance == null)
            m_Instance = new SPEngine();
        return m_Instance;
    }

    protected static synchronized void cleanUpInstance()
    {
        if(m_Instance != null)
            m_Instance = null;
    }

    protected SPListItem getNextItem()
    {
        SPListItem splistitem = m_List.getFirstHighestPriority();
        if(splistitem != null && m_CurrentItem != null && splistitem != m_CurrentItem && !m_CurrentItem.canInterrupt())
            splistitem = m_CurrentItem;
        return splistitem;
    }

    public String getTempDir()
    {
        return m_sTempDir;
    }

    private void markThreadStartTime()
    {
        m_nBeginMillis = System.currentTimeMillis();
    }

    private void notifyTimeToExit()
    {
        m_bTimeToExit = true;
        notifyTimeToProcess();
    }

    private void notifyTimeToProcess()
    {
        m_ProcessLock.setValue(true);
    }

    protected String parseAndGetFileName(String s)
    {
        String s1 = s;
        int i = s.lastIndexOf(92);
        if(i <= 0)
            i = s.lastIndexOf(47);
        if(i > 0)
            s1 = s.substring(i + 1);
        return s1;
    }

    protected String parseAndGetLastDirName(String s)
    {
        String s1 = "";
        int i = s.lastIndexOf(92);
        if(i <= 0)
            i = s.lastIndexOf(47);
        if(i > 0)
        {
            int j = s.lastIndexOf("\\", i - 1);
            if(j <= 0)
                j = s.lastIndexOf(47, i - 1);
            if(j + 1 < i)
                s1 = s.substring(j + 1, i);
            if(s1.equals(".") || s1.equals(".."))
                s1 = "";
        }
        return s1;
    }

    protected void process()
    {
        try
        {
            boolean flag = true;
            long l = m_nDefaultPollTime;
            m_bIdle = false;
            SPListItem splistitem = getNextItem();
            if(m_CurrentItem != splistitem)
            {
                m_CurrentItem = splistitem;
                if(m_CurrentItem != null)
                    m_CurrentItem.open();
            } else
            if(m_CurrentItem != null)
            {
                if(SPNative.errorOccurred())
                {
                    if(SPNative.isFinished())
                    {
                        m_CurrentItem.finished();
                        removeCurrentItem(false);
                    } else
                    {
                        m_CurrentItem.error(SPNative.getErrorString());
                        removeCurrentItem(true);
                    }
                    flag = false;
                } else
                if(!SPNative.isOpen())
                {
                    m_CurrentItem = splistitem;
                    if(m_CurrentItem != null)
                        m_CurrentItem.open();
                } else
                {
                    long l1 = SPNative.getTimeLeft() / 4;
                    l = l1 <= l ? l : l1;
                    l = l <= m_nMaxPollTime ? l : m_nMaxPollTime;
                    if(SPNative.warningOccurred())
                        handleWarning();
                }
            } else
            {
                m_bIdle = true;
            }
            if(checkIdleTimeOut())
            {
                warning("Idle timeout has been reached, spe shutting down.");
                m_bTimeToExit = true;
                return;
            }
            if(flag)
                m_ProcessLock.waitToSetFalse(l);
        }
        catch(InterruptedException interruptedexception)
        {
            exception(interruptedexception);
        }
    }

    public boolean exists(Object obj)
    {
        return m_List.contains(obj);
    }

    public boolean remove(Object obj)
    {
        return remove(obj, true);
    }

    public boolean remove(Object obj, boolean flag)
    {
        boolean flag1 = m_List.removeByIdentifier(obj, flag);
        notifyTimeToProcess();
        return flag1;
    }

    protected boolean removeCurrentItem(boolean flag)
    {
        boolean flag1 = false;
        if(m_CurrentItem != null && (flag1 = m_List.removeByItem(m_CurrentItem, flag)))
            m_CurrentItem = null;
        return flag1;
    }

    public void run()
    {
        try
        {
            markThreadStartTime();
            if(!SPNative.init())
            {
                error("Fatal error in SPEngine.run. SPNative.init failed.");
                return;
            }
            Thread.sleep(m_nSPNativeWarmUpTime);
            if(m_fMaxKbps > 0.0F && !SPNative.setMaxKbps(m_fMaxKbps))
                error("Nonfatal error in SPEngine.run. SPNative.setMaxKpbs failed.");
            if(!SPNative.setPreferredSettings())
                error("Nonfatal error in SPEngine.run. SPNative.setPreferredSettings failed.");
            if(!SPNative.setSensitivities(m_nTransmitSensitivity, m_nReceiveSensitivity, m_nTransmitPatternSensitivity, m_nReceivePatternSensitivity))
                error("Nonfatal error in SPEngine.run. SPNative.setSensitivities failed.");
            (new File(m_sTempDir)).mkdir();
            while(!m_bTimeToExit) 
                process();
            shutdownNative();
            testCleanUpOrphans();
            cleanUpInstance();
        }
        catch(ThreadDeath threaddeath)
        {
            error("Fatal error in SPEngine.run. ThreadDeath exception: " + threaddeath.toString());
            shutdownNative();
            throw threaddeath;
        }
        catch(Exception exception1)
        {
            exception(exception1);
            shutdownNative();
        }
    }

    public void setLanMode(boolean flag)
    {
        m_bLanMode = flag;
        if(m_bLanMode)
            m_List.setAllModes(4);
    }

    private void setListener(SPEngineListener spenginelistener)
    {
        m_Callback = spenginelistener;
    }

    private void setMaxKbps(float f)
    {
        m_fMaxKbps = f;
    }

    private void setTempDir(String s)
    {
        m_sTempDir = s;
    }

    private void shutdownNative()
    {
        if(!SPNative.shutdown())
            error("Nonfatal error in SPEngine.shutdownNative. SPNative.shutdown failed.");
    }

    private void testCleanUpOrphans()
    {
        if(System.currentTimeMillis() > m_nBeginMillis + m_nAliveTimeBeforeOrphanCleanUp)
            cleanUpOrphans();
    }

    private void handleWarning()
    {
        if(m_Callback != null)
        {
            warning(SPNative.getWarningString());
            if(SPNative.getWarningType() == 1 && SPNative.getWarningDetail() == 1)
                m_Callback.onFailedDUNCapabilities();
        }
    }

    private boolean checkIdleTimeOut()
    {
        if(m_bIdle)
        {
            if(m_nBeginIdleTimeoutMillis == 0L)
                m_nBeginIdleTimeoutMillis = System.currentTimeMillis();
            else
                return System.currentTimeMillis() > m_nBeginIdleTimeoutMillis + (long)m_nIdleTimeout;
        } else
        {
            m_nBeginIdleTimeoutMillis = 0L;
        }
        return false;
    }

    private static long m_nDefaultPollTime = 15000L;
    private static long m_nMaxPollTime = 0x1d4c0L;
    private static long m_nSPNativeWarmUpTime = 2000L;
    private static long m_nAliveTimeBeforeOrphanCleanUp = 0x493e0L;
    private static long m_nJoinTimeout = 6000L;
    private static int m_nTransmitSensitivity = 5;
    private static int m_nReceiveSensitivity = 700;
    private static int m_nTransmitPatternSensitivity = 100;
    private static int m_nReceivePatternSensitivity = 2000;
    private static int m_nIdleTimeout = 0x493e0;
    protected String m_sTempDir;
    protected float m_fMaxKbps;
    protected SPList m_List;
    protected SPListItem m_CurrentItem;
    protected boolean m_bLanMode;
    protected long m_nBeginMillis;
    protected long m_nBeginIdleTimeoutMillis;
    protected boolean m_bIdle;
    private volatile boolean m_bTimeToExit;
    private BooleanLock m_ProcessLock;
    private SPEngineListener m_Callback;
    private static SPEngine m_Instance = null;

}
