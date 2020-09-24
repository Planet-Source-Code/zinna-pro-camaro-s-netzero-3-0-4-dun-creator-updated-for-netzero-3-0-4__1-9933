// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceThread.java

package serviceThread;

import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package serviceThread:
//            Test, ScheduleObject, ClockThread, ScheduleInterface

public class ServiceThread extends Thread
{

    public ServiceThread(int i)
    {
        super("Service Thread");
        registeredObjects = new Vector();
        jobQueue = new Vector();
        count = 30;
        count = i;
        ClockThread clockthread = new ClockThread(i, this);
    }

    public void decrementTime()
    {
        for(Enumeration enumeration = registeredObjects.elements(); enumeration.hasMoreElements();)
        {
            ScheduleObject scheduleobject = (ScheduleObject)enumeration.nextElement();
            if(scheduleobject.getTimeLeft() == 0)
            {
                scheduleobject.setTimeLeft(scheduleobject.getInterval());
                int i = scheduleobject.getFrequency();
                if(i == 0)
                {
                    if(scheduleobject.isInfiniteFreq())
                        jobQueue.addElement(scheduleobject);
                    else
                        deRegister(scheduleobject);
                } else
                {
                    scheduleobject.setFrequency(--i);
                    boolean flag = false;
                    for(int k = 0; k < jobQueue.size(); k++)
                        if(scheduleobject.getPriority() <= ((ScheduleObject)jobQueue.elementAt(k)).getPriority())
                        {
                            jobQueue.insertElementAt(scheduleobject, k);
                            flag = true;
                            return;
                        }

                    if(!flag)
                        jobQueue.addElement(scheduleobject);
                }
            } else
            {
                int j = scheduleobject.getTimeLeft();
                scheduleobject.setTimeLeft(--j);
            }
        }

    }

    public void deRegister(ScheduleInterface scheduleinterface)
    {
        for(int i = 0; i < registeredObjects.size(); i++)
        {
            ScheduleObject scheduleobject = (ScheduleObject)registeredObjects.elementAt(i);
            if(scheduleinterface == scheduleobject.getScheduleInterface())
            {
                registeredObjects.removeElement(scheduleobject);
                return;
            }
        }

    }

    public void deRegister(ScheduleObject scheduleobject)
    {
        registeredObjects.removeElement(scheduleobject);
    }

    public static void main(String args[])
    {
        Test test = new Test("obj1");
        ServiceThread servicethread = new ServiceThread(20);
        servicethread.register(test, 0, 60, 5, 4);
    }

    public void register(ScheduleInterface scheduleinterface, int i, int j, int k, int l)
    {
        ScheduleObject scheduleobject = new ScheduleObject(scheduleinterface, i / count, j / count, k, l, false);
        registeredObjects.addElement(scheduleobject);
    }

    public void register(ScheduleInterface scheduleinterface, int i, int j, int k, int l, boolean flag)
    {
        ScheduleObject scheduleobject = new ScheduleObject(scheduleinterface, i / count, j / count, k, l, flag);
        registeredObjects.addElement(scheduleobject);
    }

    public void run()
    {
_L2:
        ((ScheduleObject)jobQueue.elementAt(0)).activate();
        jobQueue.removeElementAt(0);
        while(jobQueue.isEmpty()) 
            suspend();
        if(true) goto _L2; else goto _L1
_L1:
    }

    static final int PRIORITY_1 = 1;
    static final int PRIORITY_2 = 2;
    static final int PRIORITY_3 = 3;
    static final int PRIORITY_4 = 4;
    static final int PRIORITY_5 = 5;
    Vector registeredObjects;
    Vector jobQueue;
    ScheduleObject object;
    int count;
}
