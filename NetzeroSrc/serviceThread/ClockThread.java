// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClockThread.java

package serviceThread;


// Referenced classes of package serviceThread:
//            ServiceThread

class ClockThread extends Thread
{

    ClockThread(int i, ServiceThread servicethread)
    {
        super("Clock Thread " + ++threadCount_);
        count = 30;
        count = i;
        serviceThread = servicethread;
        serviceThread.start();
        start();
    }

    public void run()
    {
        do
            try
            {
                serviceThread.decrementTime();
                serviceThread.resume();
                Thread.sleep(count * 1000);
            }
            catch(InterruptedException _ex) { }
        while(true);
    }

    static int threadCount_ = 0;
    int count;
    ServiceThread serviceThread;

}
