// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZException.java

package exception;

import nzcom.ZCast;

public class NZException extends Exception
{

    public NZException()
    {
        severity = 100;
    }

    public NZException(int i, String s)
    {
        super(s);
        severity = 100;
        severity = i;
    }

    public NZException(String s)
    {
        super(s);
        severity = 100;
    }

    public String getSevereString()
    {
        switch(getSeverity())
        {
        case 300: 
            return "FATAL";

        case 200: 
            return "WARNING";
        }
        return "UNKNOWN";
    }

    public int getSeverity()
    {
        return severity;
    }

    public void log()
    {
        log(((Exception) (this)));
    }

    public void log(Exception exception)
    {
        ZCast.displayDebug("=======Exception has occured");
        ZCast.displayDebug("=======" + exception);
        ZCast.displayDebug(exception);
    }

    void newMethod()
    {
    }

    public void printStackTrace()
    {
        ZCast.displayDebug("==NZException");
        ZCast.displayDebug("==SEVERITY:" + getSevereString());
        super.printStackTrace();
    }

    public static final int FATAL = 300;
    public static final int WARNING = 200;
    public static final int UNKNOWN = 100;
    private int severity;
}
