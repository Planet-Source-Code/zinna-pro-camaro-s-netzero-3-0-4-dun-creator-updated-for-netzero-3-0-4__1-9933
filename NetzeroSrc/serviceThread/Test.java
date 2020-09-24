// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Test.java

package serviceThread;

import nzcom.ZCast;

// Referenced classes of package serviceThread:
//            ScheduleInterface

public class Test
    implements ScheduleInterface
{

    public Test(String s)
    {
        text = "";
        text = s;
    }

    public void activate()
    {
        ZCast.displayDebug(text);
    }

    String text;
}
