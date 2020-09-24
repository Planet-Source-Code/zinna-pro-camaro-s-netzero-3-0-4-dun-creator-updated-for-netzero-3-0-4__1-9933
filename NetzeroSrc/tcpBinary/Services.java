// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Services.java

package tcpBinary;

import java.io.*;
import java.net.Socket;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import nzcom.ZCast;

// Referenced classes of package tcpBinary:
//            TransactionProgress, TransactionResult, ServiceInterface, ServerInterface

public class Services
{

    public static int getMaxSendSize()
    {
        return maxSendSize;
    }

    public ServerInterface getMyInterface()
    {
        return myInterface;
    }

    public boolean getRecvCompressedData()
    {
        return recvCompressedData;
    }

    public boolean getSendCompressedData()
    {
        return sendCompressedData;
    }

    public void messageInfo(String s)
    {
        if(myInterface != null)
            myInterface.messageInfo(s);
        else
            ZCast.displayDebug(s);
    }

    protected TransactionResult recv(Socket socket)
        throws Exception
    {
        TransactionResult transactionresult = new TransactionResult();
        String s = null;
        Object obj = null;
        int i = 0;
        Object obj1 = null;
        byte abyte2[] = null;
        Object obj2 = null;
        Object obj3 = null;
        abyte2 = recvWithLength(socket, 10);
        s = new String(abyte2);
        try
        {
            i = Integer.parseInt(s.trim());
        }
        catch(Exception _ex)
        {
            ZCast.displayDebug("protocol Error");
            transactionresult.setDataObject("error");
            transactionresult.setSuccessFlag(false);
            return transactionresult;
        }
        abyte2 = recvWithLength(socket, 4);
        transactionresult.setRequestType(new String(abyte2));
        if(!transactionresult.getRequestType().equals("CFNC"))
        {
            byte abyte0[] = recvWithLengthCompressed(socket, i);
            try
            {
                ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
                ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
                transactionresult.setDataObject(objectinputstream.readObject());
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
        } else
        {
            byte abyte1[] = recvWithLengthCompressed(socket, i);
            String s1 = new String(abyte1);
            transactionresult.setDataObject(s1);
        }
        return transactionresult;
    }

    private byte[] recvWithLength(Socket socket, int i)
        throws Exception
    {
        InputStream inputstream = null;
        int j = 0;
        int k = 0;
        char c = '\u2710';
        int l = i;
        byte abyte0[] = new byte[i];
        Object obj = null;
        inputstream = socket.getInputStream();
        do
        {
            if(l < c)
                j = inputstream.read(abyte0, k, l);
            else
                j = inputstream.read(abyte0, k, c);
            l -= j;
            k += j;
        } while((l != 0) & (j >= 0));
        return abyte0;
    }

    private byte[] recvWithLengthCompressed(Socket socket, int i)
        throws Exception
    {
        InputStream inputstream = null;
        int j = 0;
        int k = 0;
        char c = '\u2710';
        int l = i;
        byte abyte0[] = new byte[i];
        Object obj = null;
        inputstream = socket.getInputStream();
        BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
        bufferedinputstream.mark(10);
        try
        {
            InflaterInputStream inflaterinputstream = new InflaterInputStream(bufferedinputstream);
            abyte0[0] = (byte)inflaterinputstream.read();
            l--;
            k++;
            setSendCompressedData(true);
            bufferedinputstream = new BufferedInputStream(inflaterinputstream);
        }
        catch(Exception _ex)
        {
            bufferedinputstream.reset();
        }
        do
        {
            if(l < c)
                j = bufferedinputstream.read(abyte0, k, l);
            else
                j = bufferedinputstream.read(abyte0, k, c);
            l -= j;
            k += j;
            report((k * 100) / i);
        } while((l != 0) & (j >= 0));
        return abyte0;
    }

    private void report(int i)
    {
        if(getTpInterface() != null)
            getTpInterface().reportProgress(getTransCode(), i);
    }

    protected void setTransCode(String s)
    {
        m_sTransCode = s;
    }

    private String getTransCode()
    {
        return m_sTransCode;
    }

    private TransactionProgress getTpInterface()
    {
        return m_interfaceTransProg;
    }

    public void setTpInterface(TransactionProgress transactionprogress)
    {
        m_interfaceTransProg = transactionprogress;
    }

    protected void send(Socket socket, Object obj, String s, boolean flag)
        throws Exception
    {
        PrintStream printstream = null;
        String s1 = null;
        String s2 = s;
        byte abyte0[] = null;
        Object obj1 = null;
        Object obj2 = null;
        setTransCode(s);
        if(!flag)
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
            abyte0 = bytearrayoutputstream.toByteArray();
            s1 = String.valueOf(abyte0.length);
        } else
        {
            s1 = String.valueOf(((String)obj).length());
        }
        do
            s1 = " " + s1;
        while(s1.length() < 10);
        if(s == null)
        {
            s2 = "NONE";
        } else
        {
            if(s.length() > 4)
                s2 = s.substring(0, 3);
            if(s.length() < 4)
                do
                    s = new String(s + " ");
                while(s.length() < 4);
        }
        printstream = new PrintStream(socket.getOutputStream());
        printstream.flush();
        printstream.print(s1);
        printstream.print(s2);
        if(!flag)
            printstream.write(abyte0);
        else
            printstream.print(obj);
        printstream.flush();
    }

    protected void sendCompressed(Socket socket, Object obj, String s, boolean flag)
        throws Exception
    {
        PrintStream printstream = null;
        String s1 = null;
        String s2 = s;
        byte abyte0[] = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        if(!flag)
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
            abyte0 = bytearrayoutputstream.toByteArray();
            s1 = String.valueOf(abyte0.length);
            bytearrayoutputstream = new ByteArrayOutputStream();
            DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
            objectoutputstream = new ObjectOutputStream(deflateroutputstream);
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
            abyte0 = bytearrayoutputstream.toByteArray();
        } else
        {
            s1 = String.valueOf(((String)obj).length());
        }
        do
            s1 = " " + s1;
        while(s1.length() < 10);
        if(s == null)
        {
            s2 = "NONE";
        } else
        {
            if(s.length() > 4)
                s2 = s.substring(0, 3);
            if(s.length() < 4)
                do
                    s = new String(s + " ");
                while(s.length() < 4);
        }
        printstream = new PrintStream(socket.getOutputStream());
        printstream.flush();
        printstream.print(s1);
        printstream.print(s2);
        if(!flag)
        {
            if(abyte0.length > 10000)
            {
                int i = abyte0.length / 10000;
                for(int j = 0; j < i; j++)
                {
                    int k = j * 10000;
                    printstream.write(abyte0, k, 10000);
                }

                int l = i * 10000;
                printstream.write(abyte0, l, abyte0.length - l);
            } else
            {
                printstream.write(abyte0);
            }
        } else
        {
            printstream.print(obj);
        }
        printstream.flush();
    }

    public void setMyInterface(ServerInterface serverinterface)
    {
        myInterface = serverinterface;
    }

    public static void setRecvCompressedData(boolean flag)
    {
        recvCompressedData = flag;
    }

    public static void setSendCompressedData(boolean flag)
    {
        sendCompressedData = flag;
    }

    public Services()
    {
        myInterface = null;
        debugMode = false;
        m_interfaceTransProg = null;
        m_sTransCode = null;
    }

    private ServerInterface myInterface;
    protected boolean debugMode;
    private static boolean recvCompressedData = false;
    private static boolean sendCompressedData = false;
    private static int maxSendSize = 3500;
    private TransactionProgress m_interfaceTransProg;
    private String m_sTransCode;

}
