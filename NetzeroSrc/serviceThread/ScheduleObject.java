// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScheduleObject.java

package serviceThread;


// Referenced classes of package serviceThread:
//            ScheduleInterface

public class ScheduleObject
{

    public ScheduleObject(ScheduleInterface scheduleinterface, int i, int j, int k, int l, boolean flag)
    {
        firstInterval = 0;
        interval = 0;
        frequency = 0;
        priority = 5;
        timeLeft = 0;
        freqMode = false;
        setFirstInterval(i);
        setInterval(j);
        setFrequency(k);
        setPriority(l);
        setTimeLeft(i);
        setInfiniteFreq(flag);
        setScheduleInterface(scheduleinterface);
    }

    public void activate()
    {
        obj.activate();
    }

    public int getFirstInterval()
    {
        return firstInterval;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public int getInterval()
    {
        return interval;
    }

    public int getPriority()
    {
        return priority;
    }

    public ScheduleInterface getScheduleInterface()
    {
        return obj;
    }

    public int getTimeLeft()
    {
        return timeLeft;
    }

    public boolean isInfiniteFreq()
    {
        return freqMode;
    }

    public void setFirstInterval(int i)
    {
        firstInterval = interval;
    }

    public void setFrequency(int i)
    {
        frequency = i;
    }

    public void setInfiniteFreq(boolean flag)
    {
        freqMode = flag;
    }

    public void setInterval(int i)
    {
        interval = i;
    }

    public void setPriority(int i)
    {
        if(i < 0 || i > 5)
            i = 5;
        priority = i;
    }

    public void setScheduleInterface(ScheduleInterface scheduleinterface)
    {
        obj = scheduleinterface;
    }

    public void setTimeLeft(int i)
    {
        timeLeft = i;
    }

    int firstInterval;
    int interval;
    int frequency;
    int priority;
    int timeLeft;
    boolean freqMode;
    ScheduleInterface obj;
}
