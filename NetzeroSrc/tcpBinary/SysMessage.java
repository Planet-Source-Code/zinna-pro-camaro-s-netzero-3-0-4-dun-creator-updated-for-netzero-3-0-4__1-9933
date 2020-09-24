// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysMessage.java

package tcpBinary;


// Referenced classes of package tcpBinary:
//            NZMsg

public class SysMessage extends NZMsg
{

    public SysMessage(int i)
    {
        super(i);
        cmd = 0;
    }

    public SysMessage(int i, int j)
    {
        super(i, 1);
        cmd = 0;
        setCmd(j);
    }

    public SysMessage(int i, int j, Object obj)
    {
        this(i, j);
        setObj(obj);
    }

    public int getCmd()
    {
        return cmd;
    }

    public void setCmd(int i)
    {
        cmd = i;
    }

    public static final int CMD_LOG_OFF = 1;
    public static final int CMD_MSG_DELETE = 2;
    public static final int CMD_MSG_UPDATE = 3;
    public static final int CMD_FILE_DELETE = 4;
    public static final int CMD_FILE_UPDATE = 5;
    protected int cmd;
}
