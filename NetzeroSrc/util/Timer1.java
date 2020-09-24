// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Timer1.java

package util;


public abstract class Timer1 extends Thread
{

    public Timer1()
    {
        this("Timer-" + TimerCount++);
    }

    public Timer1(String s)
    {
        super(s);
        timeToDie_ = false;
        m_secs = 1;
    }

    public void start(int i)
    {
        m_secs = i;
        super.start();
    }

    public void run()
    {
        while(!timeToDie_) 
            try
            {
                Thread.sleep(m_secs * 1000);
                timesUp();
            }
            catch(Exception _ex)
            {
                timeToDie_ = true;
            }
    }

    protected abstract void timesUp();

    private static int TimerCount = 0;
    protected boolean timeToDie_;
    private int m_secs;

}
