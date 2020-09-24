// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SystemUpdate.java

package softwareSync;

import gui.NZDialogBox;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
import nzcom.OSDetectNative;
import nzcom.ZCast;
import spe.DefaultEngineListener;
import spe.SPEngine;
import sysinfo.SysInfo;
import sysinfo.SysInfoDriver;

// Referenced classes of package softwareSync:
//            OCXINFInstaller, ComponentInstaller, FileVersion, UpdateItem

public class SystemUpdate extends Thread
{

    public SystemUpdate()
    {
        super("System Update");
        updates = null;
    }

    protected void buildUpdateVector()
    {
        String s = OSDetectNative.getSystemRoot();
        String s1 = System.getProperty("user.dir") + File.separator + "update";
        SysInfo sysinfo = SysInfoDriver.getOldSystemInfo();
        (new File(s1)).mkdir();
        updates = new Vector();
        if(sysinfo != null)
        {
            if(sysinfo.hasVideoHardware())
            {
                ZCast.displayDebug("-Has video hardware + DirectX, check codec versions");
                updates.addElement(new UpdateItem("mpg4ds32.ax", s1, "mpg4ds32.ax", s, "http://imgs.netzero.net/webads/Frank/mpg4ds32.ax", new FileVersion("4.00.0.3844"), new ComponentInstaller()));
                updates.addElement(new UpdateItem("l3codecx.ax", s1, "l3codecx.ax", s, "http://imgs.netzero.net/webads/Frank/l3codecx.ax", new FileVersion("1,5,0,50"), new ComponentInstaller()));
            } else
            {
                ZCast.displayDebug("-Does not have video hardware, skipping");
            }
            if(sysinfo.hasIE4())
            {
                ZCast.displayDebug("-Has IE 4, check Flash version");
                updates.addElement(new UpdateItem("swflash.inf", s1, "swflash.ocx", s + File.separator + "macromed" + File.separator + "Flash", "http://imgs.netzero.net/webads/Frank/SWFLASH.INF", new FileVersion("4.0"), null));
                updates.addElement(new UpdateItem("swflash.ocx", s1, "swflash.ocx", s + File.separator + "macromed" + File.separator + "Flash", "http://imgs.netzero.net/webads/Frank/SWFLASH.OCX", new FileVersion("4.0"), new OCXINFInstaller("You need to install/upgrade Flash. Install it now?")));
            } else
            {
                ZCast.displayDebug("-Does not have IE 4, skipping Flash");
            }
        }
    }

    public void run()
    {
        try
        {
            ZCast.displayDebug("\n\n**************** System Update **********************");
            buildUpdateVector();
            processFiles();
            ZCast.displayDebug("**************** System Update **********************\n");
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    private void processFiles()
    {
        for(Enumeration enumeration = updates.elements(); enumeration.hasMoreElements(); ((UpdateItem)enumeration.nextElement()).checkVersionAndUpdate());
    }

    public static void main(String args[])
    {
        ZCast.m_nzDebugMode = "on";
        SPEngine.getInstance().setLanMode(true);
        SPEngine.begin(".\\basket", new DefaultEngineListener());
        SystemUpdate systemupdate = new SystemUpdate();
        systemupdate.start();
        String args1[] = {
            "Continue"
        };
        NZDialogBox.showMessageDialog("Pause", "Click Continue when you want to exit.", 1, args1);
        try
        {
            SPEngine.end();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        System.gc();
    }

    private Vector updates;
}
