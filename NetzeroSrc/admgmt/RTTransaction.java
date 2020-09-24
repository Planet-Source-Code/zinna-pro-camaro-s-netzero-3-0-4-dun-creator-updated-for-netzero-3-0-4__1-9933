// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTTransaction.java

package admgmt;

import java.net.Socket;
import nzcom.ZCast;

// Referenced classes of package admgmt:
//            SimpleComm

public class RTTransaction extends SimpleComm
{

    public RTTransaction(String s, int i)
    {
        m_sIP = null;
        m_iPort = 0;
        m_sIP = s;
        m_iPort = i;
    }

    public Object execute(String s, Object obj)
    {
        try
        {
            setSocket(new Socket(m_sIP, m_iPort));
            sendSocket(s, obj);
            Object obj1 = readSocket();
            closeSocket();
            return obj1;
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(String.valueOf(exception) + " RTTransaction::execute");
        }
        return null;
    }

    private String m_sIP;
    private int m_iPort;
}
