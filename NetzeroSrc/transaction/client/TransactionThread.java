// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionThread.java

package transaction.client;

import java.util.Date;
import java.util.Vector;
import nzcom.*;
import tcpBinary.*;
import transaction.NewQuery;
import transaction.SimpleQuery;

public abstract class TransactionThread extends Thread
{

    public TransactionThread(String s)
    {
        super(s);
        queries = null;
        stop = false;
        programTerminating = false;
        threadTimeout = 30000L;
        initialize();
        start();
    }

    protected int getPort()
    {
        return port;
    }

    protected String getServerLocation()
    {
        return serverLocation;
    }

    private long getThreadTimeout()
    {
        return threadTimeout;
    }

    protected abstract String getTransactionCode();

    private void initialize()
    {
        queries = new Vector();
        onInitialize();
    }

    public synchronized boolean isProgramTerminating()
    {
        return programTerminating;
    }

    protected abstract boolean isTransactionSuccessfull(TransactionResponse transactionresponse);

    protected abstract void onInitialize();

    protected abstract void onTerminate();

    private void proceedQueue()
    {
        boolean flag = isProgramTerminating();
        if(!flag || !ZCast.m_emergencyExit)
        {
            int i;
            synchronized(this)
            {
                i = queries.size();
            }
            if(i > 0)
            {
                Vector vector = new Vector(i);
                for(int j = 0; j < i; j++)
                    vector.addElement(queries.elementAt(j));

                TransactionRequest transactionrequest = new TransactionRequest();
                transactionrequest.setDataObject(vector);
                transactionrequest.setClientVersion(ZCast.getZcastVersion());
                transactionrequest.setUserNumber(Initializer.m_userNumber);
                transactionrequest.setSessionId(Initializer.m_sessionId);
                transactionrequest.setUserId(ConfigParams.getVers());
                Vector vector1 = new Vector();
                vector1.addElement(new Date());
                vector.insertElementAt(vector1, 0);
                Transaction transaction = new Transaction(false);
                transaction.setServerLocation(ServerParms.getParm(getServerLocation(), transaction.getServerLocation()));
                transaction.setSoTimeout(0x1d4c0);
                TransactionResult transactionresult = transaction.execute(transactionrequest, getTransactionCode());
                if(transactionresult.getSuccessFlag() && (transactionresult.getDataObject() instanceof TransactionResponse) && isTransactionSuccessfull((TransactionResponse)transactionresult.getDataObject()))
                {
                    ZCast.displayDebug("transaction " + getTransactionCode() + " processed.");
                    ZCast.displayDebug("\t(" + i + " transaction objects sent" + ")" + "\n");
                    for(int k = 0; k < i; k++)
                        queries.removeElementAt(0);

                } else
                {
                    ZCast.displayDebug("transaction " + getTransactionCode() + " failed.");
                }
            }
        }
        if(flag)
            terminate();
    }

    public void programIsTerminating()
    {
        ZCast.displayDebug("entering in transaction.TransactionThreadImpl.programIsTerminating()");
        synchronized(this)
        {
            programTerminating = true;
            notifyAll();
        }
        try
        {
            ZCast.displayDebug("-->>TransactionThreadImpl waiting to die.");
            synchronized(this)
            {
                wait();
            }
        }
        catch(InterruptedException interruptedexception)
        {
            ZCast.displayDebug(interruptedexception);
        }
        ZCast.displayDebug("leaving transaction.TransactionThreadImpl.programIsTerminating()");
    }

    public synchronized void putRequest(Object obj, String s)
    {
        ZCast.displayDebug("\tadding " + s + " request.");
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setUserId(Initializer.m_zwindow.getNzUserid());
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setClientVersion(ConfigParams.getVers());
        transactionrequest.setDataObject(obj);
        transactionrequest.setTransactionCode(null);
        SimpleQuery simplequery = new SimpleQuery(transactionrequest, s);
        queries.addElement(simplequery);
    }

    public synchronized void putRequest(TransactionRequest transactionrequest, String s)
    {
        ZCast.displayDebug("\tadding " + s + " request.");
        SimpleQuery simplequery = new SimpleQuery(transactionrequest, s);
        queries.addElement(simplequery);
    }

    public synchronized void putRequest(NewQuery newquery)
    {
        ZCast.displayDebug("\tadding " + newquery.getTransactionCode() + " request.");
        queries.addElement(newquery);
    }

    public void run()
    {
        while(!stop) 
        {
            if(!stop && !isProgramTerminating())
                synchronized(this)
                {
                    try
                    {
                        wait(getThreadTimeout());
                    }
                    catch(InterruptedException _ex) { }
                }
            proceedQueue();
        }
    }

    public void setPort(int i)
    {
        port = i;
    }

    public void setServerLocation(String s)
    {
        serverLocation = s;
    }

    public void setThreadTimeout(long l)
    {
        threadTimeout = l;
    }

    protected final void terminate()
    {
        ZCast.displayDebug("entering in TransactionThreadImpl.terminate()");
        onTerminate();
        synchronized(this)
        {
            notifyAll();
        }
        stop = true;
    }

    protected Vector queries;
    protected String serverLocation;
    protected int port;
    private boolean stop;
    private boolean programTerminating;
    private long threadTimeout;
}
