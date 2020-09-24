// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgDialogLauncher.java

package nzcom;

import java.awt.Frame;
import serviceThread.ScheduleInterface;
import tcpBinary.UserMessage;

// Referenced classes of package nzcom:
//            ZCast, MessageDialog

public class MsgDialogLauncher
    implements ScheduleInterface
{

    public MsgDialogLauncher(UserMessage usermessage)
    {
        UserMsg = null;
        UserMsg = usermessage;
    }

    public void activate()
    {
        MessageDialog messagedialog;
        MessageDialog messagedialog1;
        try
        {
            if(UserMsg.getTitle() != null)
                messagedialog = new MessageDialog(new Frame(), UserMsg.getTitle(), UserMsg);
            else
                messagedialog1 = new MessageDialog(new Frame(), UserMsg);
        }
        catch(Throwable throwable)
        {
            ZCast.displayDebug("Exception occurred creating MessageDialog");
            ZCast.displayDebug(throwable);
        }
    }

    public UserMessage getUserMsg()
    {
        return UserMsg;
    }

    public void setUserMsg(UserMessage usermessage)
    {
        UserMsg = usermessage;
    }

    private UserMessage UserMsg;
}
