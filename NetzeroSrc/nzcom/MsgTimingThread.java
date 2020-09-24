// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgTimingThread.java

package nzcom;


// Referenced classes of package nzcom:
//            ZCast, MessageDialog

public class MsgTimingThread extends Thread
{

    public MsgTimingThread(MessageDialog messagedialog, int i)
    {
        Timer = 0;
        Timer = i;
        MsgDlg = messagedialog;
    }

    public void run()
    {
        try
        {
            Thread.sleep(Timer * 1000);
            MsgDlg.disposeNoSave();
        }
        catch(Exception _ex)
        {
            ZCast.displayDebug("Catch exception");
        }
    }

    private int Timer;
    private MessageDialog MsgDlg;
}
