// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BooleanLock.java

package spe;


public class BooleanLock
{

    public BooleanLock()
    {
        this(false);
    }

    public BooleanLock(boolean flag)
    {
        m_bValue = flag;
    }

    public synchronized boolean isFalse()
    {
        return m_bValue ^ true;
    }

    public synchronized boolean isTrue()
    {
        return m_bValue;
    }

    public synchronized void setValue(boolean flag)
    {
        if(m_bValue != flag)
        {
            m_bValue = flag;
            notifyAll();
        }
    }

    public synchronized boolean waitToSetFalse(long l)
        throws InterruptedException
    {
        boolean flag = waitUntilTrue(l);
        if(flag)
            setValue(false);
        return flag;
    }

    public synchronized boolean waitToSetTrue(long l)
        throws InterruptedException
    {
        boolean flag = waitUntilFalse(l);
        if(flag)
            setValue(true);
        return flag;
    }

    public synchronized boolean waitUntilFalse(long l)
        throws InterruptedException
    {
        return waitUntilStateIs(false, l);
    }

    public synchronized boolean waitUntilStateIs(boolean flag, long l)
        throws InterruptedException
    {
        if(l == 0L)
        {
            while(m_bValue != flag) 
                wait();
            return true;
        }
        long l1 = System.currentTimeMillis() + l;
        for(long l2 = l; m_bValue != flag && l2 > 0L; l2 = l1 - System.currentTimeMillis())
            wait(l2);

        return m_bValue == flag;
    }

    public synchronized boolean waitUntilTrue(long l)
        throws InterruptedException
    {
        return waitUntilStateIs(true, l);
    }

    private boolean m_bValue;
}
