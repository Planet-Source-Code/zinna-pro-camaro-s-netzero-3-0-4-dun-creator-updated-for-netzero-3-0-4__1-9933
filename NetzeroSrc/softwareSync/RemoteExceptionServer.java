// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoteExceptionServer.java

package softwareSync;

import java.io.File;
import java.io.RandomAccessFile;

// Referenced classes of package softwareSync:
//            RemoteException

public class RemoteExceptionServer
{

    public static String getTempFile(String s)
    {
        Object obj = null;
        Object obj1 = null;
        for(int i = 0; i < 10000; i++)
        {
            String s1 = s + i + ".dmp";
            File file = new File(s1);
            if(!file.exists())
                return s1;
        }

        return null;
    }

    public static void main(String args[])
    {
        try
        {
            Object obj = null;
            obj.length();
        }
        catch(Exception exception)
        {
            new RemoteException("tp1wsb", exception);
        }
    }

    public static synchronized void writeToFile(String as[])
    {
        Object obj = null;
        String s = as[0];
        String s1 = as[1];
        try
        {
            RandomAccessFile randomaccessfile = new RandomAccessFile(getTempFile(s), "rw");
            randomaccessfile.seek(randomaccessfile.length());
            randomaccessfile.writeBytes(s1 + "\r\n");
            randomaccessfile.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public RemoteExceptionServer()
    {
    }
}
