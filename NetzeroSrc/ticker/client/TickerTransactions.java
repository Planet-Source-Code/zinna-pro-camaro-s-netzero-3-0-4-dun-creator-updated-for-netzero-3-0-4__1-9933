// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerTransactions.java

package ticker.client;

import errors.NZErrors;
import java.util.Vector;
import nzcom.*;
import tcpBinary.*;
import ticker.*;
import ticker.miscellaneous.Fake;

public final class TickerTransactions
    implements TransactionCode
{

    private TickerTransactions()
    {
    }

    public static TransactionResponse executeTransaction(Object obj, String s)
    {
        TransactionRequest transactionrequest = new TransactionRequest();
        transactionrequest.setUserId(Initializer.m_zwindow.getNzUserid());
        transactionrequest.setPassword(null);
        transactionrequest.setUserNumber(Initializer.m_userNumber);
        transactionrequest.setSessionId(Initializer.m_sessionId);
        transactionrequest.setClientVersion(ConfigParams.getVers());
        transactionrequest.setDataObject(obj);
        transactionrequest.setTransactionCode(null);
        Transaction transaction = new Transaction(false);
        transaction.setServerLocation(ServerParms.getParm("tkip1", transaction.getServerLocation()));
        TransactionResult transactionresult = transaction.execute(transactionrequest, s);
        if(!transactionresult.getSuccessFlag())
        {
            ZCast.displayDebug("(" + s + ") success flag set to: " + transactionresult.getSuccessFlag());
            return null;
        }
        if(!(transactionresult.getDataObject() instanceof TransactionResponse))
        {
            ZCast.displayDebug("(" + s + ") failed to return TransactionResponse object");
            return null;
        }
        TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
        if(transactionresponse.getReturnCode() != 0)
        {
            ZCast.displayDebug("(" + s + ") return code = " + transactionresponse.getReturnCode());
            return null;
        } else
        {
            return transactionresponse;
        }
    }

    public static TickerResponse fetchTickerData(TickerRequest tickerrequest)
    {
        if(nzFake)
        {
            int i = tickerrequest.getTopicId().intValue();
            boolean flag = tickerrequest.sendBackAds();
            TickerResponseImpl tickerresponseimpl = new TickerResponseImpl();
            switch(i)
            {
            default:
                break;

            case 0: // '\0'
                tickerresponseimpl.setTickerMessages(Fake.getStocksSamples());
                if(flag)
                    tickerresponseimpl.setSponsorMessages(Fake.getStocksAds());
                break;

            case 1: // '\001'
                tickerresponseimpl.setTickerMessages(Fake.getNewsSamples());
                if(flag)
                    tickerresponseimpl.setSponsorMessages(Fake.getNewsAds());
                break;

            case 2: // '\002'
                tickerresponseimpl.setTickerMessages(Fake.getSportsSamples());
                if(flag)
                    tickerresponseimpl.setSponsorMessages(Fake.getSportsAds());
                break;
            }
            return tickerresponseimpl;
        }
        TransactionResponse transactionresponse = executeTransaction(tickerrequest, "TICK");
        if(transactionresponse == null)
            return null;
        if(!(transactionresponse.getReturnObject() instanceof TickerResponse))
        {
            ZCast.displayDebug("(TICK) failed to return a TickerResponse object");
            return null;
        } else
        {
            return (TickerResponse)transactionresponse.getReturnObject();
        }
    }

    public static Vector fetchTickerInformation(FetchRequest fetchrequest)
    {
        if(nzFake)
            return Fake.fakeFetchTickerInformation(fetchrequest);
        TransactionResponse transactionresponse = executeTransaction(fetchrequest, "FTCK");
        ZCast.displayDebug("fetch ticker information avec id = " + fetchrequest.getItemId());
        if(transactionresponse == null)
            return null;
        if(!(transactionresponse.getReturnObject() instanceof Vector))
        {
            ZCast.displayDebug("(FTCK) failed to return a java.util.Vector object");
            return null;
        } else
        {
            return (Vector)transactionresponse.getReturnObject();
        }
    }

    public static boolean saveTickerInformation(Vector vector)
    {
        if(vector == null || vector.size() == 0)
            return false;
        if(nzFake)
            NZErrors.serialize(Fake.getInformationFileName(), vector);
        else
            return executeTransaction(vector, "STCK") != null;
        return true;
    }

    public static boolean nzFake = false;

}
