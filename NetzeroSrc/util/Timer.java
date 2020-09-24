// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Timer.java

package util;

import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package util:
//            TimerListener

public class Timer extends Thread
{

    public Timer()
    {
        m_listenerList = null;
        m_secs = 0;
    }

    public void start(int i)
    {
        m_secs = i;
        super.start();
    }

    public void run()
    {
        try
        {
            Thread.sleep(m_secs * 1000);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        timesUpNotifications();
        clearListeners();
    }

    public void addTimerListener(TimerListener timerlistener)
    {
        if(m_listenerList == null)
            m_listenerList = new Vector();
        m_listenerList.addElement(timerlistener);
    }

    private void timesUpNotifications()
    {
        if(m_listenerList != null)
        {
            for(int i = 0; i < m_listenerList.size(); i++)
                try
                {
                    TimerListener timerlistener = (TimerListener)m_listenerList.elementAt(i);
                    timerlistener.timesUp();
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug(exception);
                }

        }
    }

    private void clearListeners()
    {
        if(m_listenerList != null)
            m_listenerList.removeAllElements();
    }

    private Vector m_listenerList;
    private int m_secs;
}
