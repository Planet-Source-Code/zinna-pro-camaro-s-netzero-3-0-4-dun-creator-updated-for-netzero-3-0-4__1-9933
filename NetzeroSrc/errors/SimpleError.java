// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleError.java

package errors;

import java.io.PrintStream;
import nzcom.ZCast;

// Referenced classes of package errors:
//            Error

public abstract class SimpleError
    implements Error
{

    void dump(PrintStream printstream)
    {
        if(ZCast.m_nzDebugMode.equals("on"))
            printstream.println(m_strUserId);
    }

    public String getUserId()
    {
        return m_strUserId;
    }

    public SimpleError()
    {
        m_strUserId = null;
    }

    public abstract int getErrorType();

    String m_strUserId;
}
