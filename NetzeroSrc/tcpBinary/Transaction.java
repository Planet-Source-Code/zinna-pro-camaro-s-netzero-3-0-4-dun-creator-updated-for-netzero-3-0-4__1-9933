// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transaction.java

package tcpBinary;

import errors.NetworkErrorImpl;
import io.Local;
import java.io.File;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import nzcom.*;
import ticker.FetchRequestImpl;
import ticker.TickerRequestImpl;
import transaction.client.TransactionThread;
import transaction.client.UlogThread;

// Referenced classes of package tcpBinary:
//            Client, TransactionResponse, TransactionRequest, Services, 
//            TransactionResult, TransactionProgress

public class Transaction extends Client
{

    public Transaction()
    {
        myResult = new TransactionResult();
        throwErrors = true;
        displayUserMessge = true;
        serverLocation = "app1.netzero.net";
        serverPort = 6789;
        soTimeout = 0;
    }

    public Transaction(boolean flag)
    {
        myResult = new TransactionResult();
        throwErrors = true;
        displayUserMessge = true;
        serverLocation = "app1.netzero.net";
        serverPort = 6789;
        soTimeout = 0;
        displayUserMessge = flag;
    }

    public Transaction(TransactionProgress transactionprogress)
    {
        myResult = new TransactionResult();
        throwErrors = true;
        displayUserMessge = true;
        serverLocation = "app1.netzero.net";
        serverPort = 6789;
        soTimeout = 0;
        setTpInterface(transactionprogress);
    }

    public TransactionResult execute(Object obj, String s)
    {
        ZCast.displayDebug("<--------------- executing " + s + " transaction");
        if(ZCast.m_demoMode)
        {
            if(s.equals("TICK"))
                myResult = (TransactionResult)Local.deserialize("demo/" + s + ((TickerRequestImpl)((TransactionRequest)obj).getDataObject()).getTopicId() + ".demo", false);
            else
            if(s.equals("FTCK"))
                myResult = (TransactionResult)Local.deserialize("demo/" + s + ((FetchRequestImpl)((TransactionRequest)obj).getDataObject()).getItemId() + ".demo", false);
            else
                myResult = (TransactionResult)Local.deserialize("demo/" + s + ".demo", false);
            if(myResult == null)
                myResult = (TransactionResult)Local.deserialize("demo/" + s + ".demo", false);
        } else
        {
            if(getServerLocation().equals("null"))
            {
                ZCast.displayDebug(s + " sent to null");
                return new TransactionResult();
            }
            setTransCode(s);
            String s1 = null;
            try
            {
                connectToSocket(getServerLocation(), getServerPort());
                modifySocket(getSocket());
                ZCast.displayDebug("connect to: " + getSocket().getInetAddress() + " port=" + getServerPort());
                if(getSendCompressedData())
                    sendCompressed(getSocket(), obj, s, false);
                else
                    send(getSocket(), obj, s, false);
                myResult = recv(getSocket());
                close();
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("exception in Transaction.execute(): " + exception);
                myResult.setSuccessFlag(false);
                s1 = exception.toString();
            }
            ZCast.displayDebug(">--------------- end executing " + s + " transaction");
            if(myResult.getSuccessFlag() && (myResult.getDataObject() instanceof TransactionResponse))
            {
                TransactionResponse transactionresponse = (TransactionResponse)myResult.getDataObject();
                ZCast.displayDebug("Process the messages!!!!!");
                MsgProcObj.processMsgs(transactionresponse.getUserMessages());
            } else
            {
                NetworkErrorImpl networkerrorimpl = new NetworkErrorImpl(Initializer.m_zwindow.getNzUserid(), new Date());
                networkerrorimpl.setSessionId(Initializer.m_sessionId);
                if(getSocket() != null)
                {
                    networkerrorimpl.setServerIpAddress(getSocket().getInetAddress());
                    networkerrorimpl.setPort(getSocket().getPort());
                }
                networkerrorimpl.setTransactionType(s);
                networkerrorimpl.setProtocol(0);
                networkerrorimpl.setErrorMessage(s1);
                if(Initializer.m_ulogThread != null)
                    Initializer.m_ulogThread.putRequest(networkerrorimpl, "NDAT");
                else
                    UlogThread.addTransactionToFile(networkerrorimpl, "NDAT");
            }
        }
        if(System.getProperty("nz.capture") != null)
        {
            ZCast.displayDebug("-->CAPTURING Transaction");
            String s2 = null;
            if(s.equals("TICK"))
                s2 = "demo/" + s + ((TickerRequestImpl)((TransactionRequest)obj).getDataObject()).getTopicId() + ".demo";
            else
            if(s.equals("FTCK"))
                s2 = "demo/" + s + ((FetchRequestImpl)((TransactionRequest)obj).getDataObject()).getItemId() + ".demo";
            else
                s2 = "demo/" + s + ".demo";
            File file = new File(s2);
            if(file.exists())
                file.delete();
            Local.serialize(s2, myResult);
            ZCast.displayDebug("-->CAPTURE File = " + s2);
        }
        return myResult;
    }

    public TransactionResult executeForProxy(Object obj, String s, String s1, int i)
    {
        try
        {
            connectToSocket(s1, i);
            send(getSocket(), obj, s, false);
            myResult = recv(getSocket());
            close();
        }
        catch(Exception _ex)
        {
            myResult.setSuccessFlag(false);
        }
        return myResult;
    }

    public String getServerLocation()
    {
        return serverLocation;
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public int getSOTimeout()
    {
        return soTimeout;
    }

    public boolean getThrowErrors()
    {
        return throwErrors;
    }

    public static void main(String args[])
    {
        Transaction transaction = new Transaction();
        TransactionResult transactionresult = null;
        transactionresult = transaction.execute("susanb", "TARG");
        ZCast.displayDebug(transactionresult.getDataObject());
    }

    public void modifySocket(Socket socket)
    {
        try
        {
            socket.setSoTimeout(soTimeout);
        }
        catch(SocketException socketexception)
        {
            ZCast.displayDebug(socketexception);
        }
    }

    public void setServerLocation(String s)
    {
        int i = s.lastIndexOf(58);
        if(i < 0)
        {
            serverLocation = s;
        } else
        {
            serverLocation = s.substring(0, i);
            setServerPort(Integer.parseInt(s.substring(i + 1)));
        }
    }

    public void setServerPort(int i)
    {
        serverPort = i;
    }

    public void setSoTimeout(int i)
    {
        soTimeout = i;
    }

    public void setThrowErrors(boolean flag)
    {
        throwErrors = flag;
    }

    private void setUserMessage(String s)
    {
    }

    private TransactionResult myResult;
    private boolean throwErrors;
    private boolean displayUserMessge;
    protected String serverLocation;
    protected int serverPort;
    private int soTimeout;
}
