// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoteException.java

package softwareSync;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import nzcom.ZCast;

// Referenced classes of package softwareSync:
//            RemoteExceptionServer

public class RemoteException
    implements Runnable
{

    public RemoteException(String s, Exception exception)
    {
        outData = null;
        myThread = null;
        myException = null;
        myUserid = null;
        myException = exception;
        myUserid = s;
        start();
    }

    private void persistException()
    {
        Object obj = null;
        String s1 = null;
        try
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            myException.printStackTrace(new PrintStream(bytearrayoutputstream));
            String s = bytearrayoutputstream.toString();
            s1 = new String(myUserid + " " + new Date() + "\r\n" + s);
            if(printToScreen)
                ZCast.displayDebug(s1);
        }
        catch(Exception _ex) { }
        String as[] = new String[2];
        as[0] = "tp1wsb";
        as[1] = s1;
        RemoteExceptionServer.writeToFile(as);
    }

    public void run()
    {
        persistException();
    }

    private void start()
    {
        if(myThread == null)
        {
            myThread = new Thread(this);
            myThread.setPriority(5);
            myThread.start();
        }
    }

    protected String outData[];
    protected Thread myThread;
    protected static boolean printToScreen = false;
    private Exception myException;
    private String myUserid;

}
