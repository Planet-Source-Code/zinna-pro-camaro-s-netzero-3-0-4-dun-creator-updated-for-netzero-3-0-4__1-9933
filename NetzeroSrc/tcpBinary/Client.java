// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Client.java

package tcpBinary;

import java.net.Socket;

// Referenced classes of package tcpBinary:
//            Services

public class Client extends Services
{

    public void close()
    {
        try
        {
            mySocket.close();
        }
        catch(Exception _ex) { }
    }

    public Socket connectToSocket(String s, int i)
        throws Exception
    {
        Socket socket = null;
        socket = new Socket(s, i);
        setSocket(socket);
        return socket;
    }

    public Socket getSocket()
    {
        return mySocket;
    }

    public void setSocket(Socket socket)
    {
        mySocket = socket;
    }

    public Client()
    {
        mySocket = null;
    }

    public Socket mySocket;
}
