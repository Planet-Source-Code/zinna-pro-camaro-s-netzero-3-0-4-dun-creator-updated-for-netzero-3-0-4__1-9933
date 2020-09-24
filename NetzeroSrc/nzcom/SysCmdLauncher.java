// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysCmdLauncher.java

package nzcom;

import serviceThread.ScheduleInterface;
import tcpBinary.SysMessage;

// Referenced classes of package nzcom:
//            MsgProcObj

public class SysCmdLauncher
    implements ScheduleInterface
{

    public SysCmdLauncher(SysMessage sysmessage)
    {
        SysMsg = null;
        setSysMsg(sysmessage);
    }

    public void activate()
    {
        MsgProcObj.processSysCmd(SysMsg);
    }

    public SysMessage getSysMsg()
    {
        return SysMsg;
    }

    public void setSysMsg(SysMessage sysmessage)
    {
        SysMsg = sysmessage;
    }

    private SysMessage SysMsg;
}
