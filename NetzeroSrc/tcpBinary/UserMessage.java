// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserMessage.java

package tcpBinary;


// Referenced classes of package tcpBinary:
//            SysMessage, NZMsg

public class UserMessage extends SysMessage
{

    public UserMessage(int i, Object obj)
    {
        super(i);
        title = null;
        msg = null;
        NumShown = 0;
        DisplayTime = 15;
        setMsg(obj);
        setDisplayTime(15);
        setType(0);
    }

    public UserMessage(int i, Object obj, int j)
    {
        this(i, obj);
        setDisplayTime(j);
    }

    public UserMessage(int i, String s, Object obj)
    {
        this(i, obj);
        setTitle(s);
    }

    public UserMessage(int i, String s, Object obj, int j)
    {
        this(i, obj);
        setTitle(s);
        setDisplayTime(j);
    }

    public int getDisplayTime()
    {
        if(DisplayTime <= 0)
            return 15;
        else
            return DisplayTime;
    }

    public Object getMsg()
    {
        return msg;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isSysMsg()
    {
        return cmd >= 1 && cmd <= 5;
    }

    public void setDisplayTime(int i)
    {
        DisplayTime = i;
    }

    public void setMsg(Object obj)
    {
        msg = obj;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    private static final int DISPLAY_TIME = 15;
    private String title;
    private Object msg;
    private int NumShown;
    private int DisplayTime;
}
