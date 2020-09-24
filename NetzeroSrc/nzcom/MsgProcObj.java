// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgProcObj.java

package nzcom;

import java.awt.Frame;
import java.util.ResourceBundle;
import java.util.Vector;
import serviceThread.ServiceThread;
import tcpBinary.*;

// Referenced classes of package nzcom:
//            MessageDialog, MsgDialogLauncher, Initializer, SysCmdLauncher, 
//            ZCast

public class MsgProcObj
{

    public MsgProcObj()
    {
        MsgVector = new Vector();
    }

    private static void addMsg(NZMsg nzmsg)
    {
        if(msgInVector(nzmsg))
            return;
        if(nzmsg.getProcess() == 2)
            MsgVector.insertElementAt(nzmsg, 0);
        else
            MsgVector.addElement(nzmsg);
    }

    private static Vector getMsgVector()
    {
        return MsgVector;
    }

    private static void logOffUser()
    {
        ZCast.terminateProgram(0, resNZResource.getString("Server_Terminating_Client_"));
    }

    public static void main(String args[])
    {
        MsgProcObj msgprocobj = new MsgProcObj();
        UserMessage ausermessage[] = new UserMessage[10];
        ausermessage[0] = new UserMessage(0, "this is a test string 0");
        ausermessage[1] = new UserMessage(1, "this is a test string 1");
        ausermessage[2] = new UserMessage(2, "this is a test string 2");
        processMsgs(ausermessage);
        ausermessage[0] = null;
        ausermessage[1] = null;
        ausermessage[2] = null;
        ausermessage[0] = new UserMessage(2, "this is a test string 0");
        processMsgs(ausermessage);
        for(int i = 0; i < MsgVector.size(); i++)
        {
            UserMessage usermessage = (UserMessage)MsgVector.elementAt(i);
        }

        MsgVector.removeElementAt(1);
        processMsgs(ausermessage);
        for(int j = 0; j < MsgVector.size(); j++)
        {
            UserMessage usermessage1 = (UserMessage)MsgVector.elementAt(j);
        }

    }

    private static boolean msgInVector(NZMsg nzmsg)
    {
        Vector vector = getMsgVector();
        for(int i = 0; i < vector.size(); i++)
        {
            NZMsg nzmsg1 = (NZMsg)vector.elementAt(i);
            if(nzmsg.getID() == nzmsg1.getID())
                return true;
        }

        return false;
    }

    public static void printMsgVector()
    {
        Vector vector = getMsgVector();
        for(int i = 0; i < vector.size(); i++)
        {
            NZMsg nzmsg = (NZMsg)vector.elementAt(i);
            ZCast.displayDebug("MsgVector[" + i + "].ID == " + nzmsg.getID());
        }

    }

    private static void processCmd(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            logOffUser();
            break;

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        default:
            ZCast.displayDebug("System Message not recognized-------");
            break;
        }
    }

    public static void processMsgs(Object aobj[])
    {
        if(aobj == null)
        {
            ZCast.displayDebug("No messages to process");
            return;
        }
        for(int i = 0; i < aobj.length; i++)
        {
            if(aobj[i] == null)
                break;
            if(aobj[i] instanceof NZMsg)
                processNZMsg((NZMsg)aobj[i]);
        }

    }

    private static void processNZMsg(NZMsg nzmsg)
    {
        if(nzmsg.isUserMsg())
            processUserMsg((UserMessage)nzmsg);
        else
            processSysMsg((SysMessage)nzmsg);
    }

    public static void processSysCmd(SysMessage sysmessage)
    {
        switch(sysmessage.getCmd())
        {
        case 1: // '\001'
            logOffUser();
            break;

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        default:
            ZCast.displayDebug("System Message not recognized-------");
            break;
        }
    }

    public static void processSysMsg(SysMessage sysmessage)
    {
        if(!msgInVector(sysmessage))
        {
            addMsg(sysmessage);
            if(sysmessage.isUserMsg())
                processUserCmd((UserMessage)sysmessage);
            else
                processSysCmd(sysmessage);
        }
    }

    public static void processUserCmd(UserMessage usermessage)
    {
        addMsg(usermessage);
        SysCmdLauncher syscmdlauncher = new SysCmdLauncher(usermessage);
        Initializer.m_serviceThread.register(syscmdlauncher, usermessage.getDisplayTime(), 0, 1, usermessage.getPriority());
    }

    private static void processUserMsg(UserMessage usermessage)
    {
        switch(usermessage.getProcess())
        {
        case 1: // '\001'
            if(!msgInVector(usermessage))
            {
                showMsg(usermessage);
                if(usermessage.isSysMsg())
                    processUserCmd(usermessage);
            }
            break;

        case 2: // '\002'
            if(msgInVector(usermessage))
                break;
            showIntervalMsg(usermessage);
            if(usermessage.isSysMsg())
                processUserCmd(usermessage);
            break;

        case 3: // '\003'
            showMsg(usermessage);
            if(usermessage.isSysMsg())
                processUserCmd(usermessage);
            break;

        default:
            ZCast.displayDebug("default error in processUserMsg");
            break;
        }
    }

    public static void saveMsg(NZMsg nzmsg)
    {
        addMsg(nzmsg);
    }

    private static void showIntervalMsg(UserMessage usermessage)
    {
        if(!msgInVector(usermessage))
        {
            MsgDialogLauncher msgdialoglauncher = new MsgDialogLauncher(usermessage);
            Initializer.m_serviceThread.register(msgdialoglauncher, 0, usermessage.getInterval(), usermessage.getFreq(), usermessage.getPriority());
            saveMsg(usermessage);
        }
    }

    private static void showMsg(UserMessage usermessage)
    {
        MessageDialog messagedialog;
        MessageDialog messagedialog1;
        try
        {
            if(usermessage.getTitle() != null)
                messagedialog = new MessageDialog(new Frame(), usermessage.getTitle(), usermessage);
            else
                messagedialog1 = new MessageDialog(new Frame(), usermessage);
        }
        catch(Throwable throwable)
        {
            ZCast.displayDebug("Exception occurred creating MessageDialog");
            ZCast.displayDebug(throwable);
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private static Vector MsgVector;

}
