// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonitorThread.java

package nzcom;

import gui.NZDialogBox;
import java.awt.*;
import java.util.ResourceBundle;

// Referenced classes of package nzcom:
//            OSDetectNative, InactivityDialog, NZWindow, ZCast, 
//            ServerParms

public class MonitorThread extends Thread
{

    public MonitorThread(NZWindow nzwindow, boolean flag)
    {
        super("User Active Monitor");
        DefSegTime = Integer.parseInt(ServerParms.getParm("InactiveSegmentTime", "5"));
        segmentTime = DefSegTime;
        f = nzwindow;
        initialInterval = flag;
        resetInterval();
    }

    public static int getMaxWaitMin()
    {
        int i;
        if(ZCast.m_connectionType == 0)
            i = Integer.parseInt(ServerParms.getParm("InactiveMinutesRAS", "30"));
        else
            i = Integer.parseInt(ServerParms.getParm("InactiveMinutesLAN", "90"));
        return i;
    }

    public void resetInterval()
    {
        maxWaitMin = getMaxWaitMin();
        ZCast.displayDebug("monitor started: interval = " + maxWaitMin + "\n\n\n\n");
        if(maxWaitMin < segmentTime)
        {
            segmentTime = maxWaitMin;
            ZCast.displayDebug("\tsegmentTime is less than maxWaitMin, so reset");
        }
        maxWait = segmentTime * 60000;
        ZCast.displayDebug("maxWait == " + maxWait);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void run()
    {
        boolean flag = true;
        InactivityDialog inactivitydialog = null;
        accumTime = maxWaitMin;
        Frame frame = null;
        while(flag) 
            try
            {
                if(accumTime < segmentTime)
                    segmentTime = accumTime;
                Thread.sleep(segmentTime * 60000);
                ZCast.displayDebug("\n########## - WOKE UP!\n");
                accumTime -= segmentTime;
                ZCast.displayDebug("########## - accumTime==" + accumTime);
                ZCast.displayDebug("########## - segmentTime==" + segmentTime);
                ZCast.displayDebug("########## - f.monSwitch==" + f.monSwitch);
                if(!f.monSwitch && accumTime <= 0)
                {
                    ZCast.displayDebug("########## - out of time and no activity!");
                    if(inactivitydialog == null)
                    {
                        frame = new Frame("nztest");
                        inactivitydialog = new InactivityDialog(frame);
                        inactivitydialog.setLocation((screenSize.width - inactivitydialog.getSize().width) / 2, (screenSize.height - inactivitydialog.getSize().height) / 2);
                    }
                    ZCast.m_osDetectNative.floatWindow(inactivitydialog.getTitle());
                    inactivitydialog.setVisible(true);
                    frame.addNotify();
                    if(!NZWindow.isPopupShowing)
                    {
                        NZWindow.getOsDetect();
                        OSDetectNative.setTopMost(frame.getTitle());
                    }
                    int i = inactivitydialog.getRemainingSeconds();
                    inactivitydialog.setResumeStatus(false);
                    for(; i > 0 && !inactivitydialog.getResumeStatus(); i--)
                    {
                        inactivitydialog.setRemainingSeconds(i);
                        if(i <= 10 || i % 10 == 0)
                            Toolkit.getDefaultToolkit().beep();
                        Thread.sleep(1000L);
                        if(!NZWindow.isPopupShowing)
                        {
                            NZWindow.getOsDetect();
                            OSDetectNative.setTopMost(frame.getTitle());
                        }
                    }

                    if(!inactivitydialog.getResumeStatus())
                    {
                        inactivitydialog.setVisible(false);
                        ZCast.terminateProgram(0, null);
                        stop();
                    } else
                    {
                        accumTime = maxWaitMin;
                        segmentTime = DefSegTime;
                        f.monSwitch = false;
                    }
                } else
                {
                    int j = Integer.parseInt(ServerParms.getParm("InactiveWarningMinutes", "5"));
                    int k = Integer.parseInt(ServerParms.getParm("InactiveWarningDisplaySeconds", "25"));
                    if(!f.monSwitch && accumTime > 0 && accumTime <= j)
                    {
                        String as[] = {
                            "ok"
                        };
                        if(f.IsZeroPortDocked())
                            NZDialogBox.showMessageDialog("NetZero Tip", "Did you know you can click on ads in the ZeroPort to\nget more information?  Ad's are the graphics that\nappear on the top left side of the ZeroPort.", 0, as, k);
                        else
                            NZDialogBox.showMessageDialog("NetZero Tip", "Did you know you can click on ads in the ZeroPort to\nget more information?  Ad's are the graphics that\nappear on the top left side of the ZeroPort.", 0, as, k);
                    } else
                    if(f.monSwitch)
                    {
                        ZCast.displayDebug("########## - f.monSwitch shows activity");
                        f.monSwitch = false;
                        accumTime = maxWaitMin;
                        segmentTime = DefSegTime;
                    }
                }
            }
            catch(InterruptedException _ex) { }
    }

    public void setSuppress(boolean flag)
    {
        bSuppress = flag;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private NZWindow f;
    private int maxWait;
    private int maxWaitMin;
    private int accumTime;
    private int localTime;
    private boolean bSuppress;
    private boolean initialInterval;
    private Dimension screenSize;
    private int DefSegTime;
    private int segmentTime;

}
