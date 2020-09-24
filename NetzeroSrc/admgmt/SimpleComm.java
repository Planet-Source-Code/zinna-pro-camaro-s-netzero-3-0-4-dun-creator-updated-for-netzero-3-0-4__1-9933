// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleComm.java

package admgmt;

import java.io.*;
import java.net.Socket;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

// Referenced classes of package admgmt:
//            SCTransCar

public class SimpleComm
{

    public SimpleComm()
    {
        m_socket = null;
    }

    public void closeSocket()
    {
        try
        {
            if(m_socket != null)
            {
                m_socket.close();
                m_socket = null;
            }
        }
        catch(Exception exception)
        {
            System.err.println(exception);
        }
    }

    public Socket getSocket()
    {
        return m_socket;
    }

    protected Object readSocket()
        throws Exception
    {
        java.io.InputStream inputstream = getSocket().getInputStream();
        BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        int i = bufferedinputstream.read();
        bytearrayoutputstream.write(i);
        for(int k = 0; k < 10; k++)
        {
            while(bufferedinputstream.available() > 0) 
            {
                int j = bufferedinputstream.read();
                bytearrayoutputstream.write(j);
            }
            try
            {
                ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());
                InflaterInputStream inflaterinputstream = new InflaterInputStream(bytearrayinputstream);
                ObjectInputStream objectinputstream = new ObjectInputStream(inflaterinputstream);
                return objectinputstream.readObject();
            }
            catch(Exception _ex)
            {
                Thread.sleep((int)(m_fWaitForMoreSec * 1000F));
            }
        }

        return null;
    }

    private void sendInChunks(OutputStream outputstream, byte abyte0[])
    {
        try
        {
            if(abyte0.length < 10000)
            {
                outputstream.write(abyte0);
            } else
            {
                int i = abyte0.length / 10000;
                for(int j = 0; j < i; j++)
                {
                    int k = j * 10000;
                    outputstream.write(abyte0, k, 10000);
                }

                int l = i * 10000;
                outputstream.write(abyte0, l, abyte0.length - l);
            }
        }
        catch(Exception _ex) { }
    }

    protected void sendSocket(String s, Object obj)
        throws Exception
    {
        OutputStream outputstream = getSocket().getOutputStream();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(deflateroutputstream);
        SCTransCar sctranscar = new SCTransCar();
        sctranscar.setTransCode(s);
        sctranscar.setContents(obj);
        objectoutputstream.writeObject(sctranscar);
        objectoutputstream.close();
        sendInChunks(outputstream, bytearrayoutputstream.toByteArray());
        outputstream.flush();
    }

    public void setSocket(Socket socket)
    {
        m_socket = socket;
    }

    public static void setWaitForMoreSeconds(float f)
    {
        m_fWaitForMoreSec = f;
    }

    protected Socket m_socket;
    private static float m_fWaitForMoreSec = 1.0F;

}
