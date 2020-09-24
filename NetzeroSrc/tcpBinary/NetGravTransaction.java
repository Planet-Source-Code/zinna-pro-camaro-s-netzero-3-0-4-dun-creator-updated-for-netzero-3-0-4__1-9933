// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NetGravTransaction.java

package tcpBinary;

import java.util.Vector;

// Referenced classes of package tcpBinary:
//            Transaction, TransactionResult

public class NetGravTransaction extends Transaction
{

    public NetGravTransaction()
    {
        myServerPort = 7789;
        setServerLocation("app1.netzero.net");
        serverPort = 7789;
    }

    public NetGravTransaction(boolean flag)
    {
        super(flag);
        myServerPort = 7789;
        setServerLocation("app1.netzero.net");
        serverPort = 7789;
    }

    public TransactionResult execute(Object obj, String s)
    {
        if(obj instanceof Vector)
        {
            Vector vector = (Vector)obj;
            setPort((String)vector.elementAt(0));
        } else
        {
            setPort((String)obj);
        }
        return super.execute(obj, s);
    }

    public int getServerPort()
    {
        return myServerPort;
    }

    public void setPort(String s)
    {
        char ac[] = s.toLowerCase().toCharArray();
        if(ac[0] < 'h')
            myServerPort = 7790;
        else
        if(ac[0] < 'p')
            myServerPort = 7789;
        else
            myServerPort = 7791;
    }

    private int myServerPort;
}
