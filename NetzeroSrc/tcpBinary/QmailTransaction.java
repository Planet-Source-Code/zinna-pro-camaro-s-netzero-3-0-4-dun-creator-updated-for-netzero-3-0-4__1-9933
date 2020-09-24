// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QmailTransaction.java

package tcpBinary;


// Referenced classes of package tcpBinary:
//            Transaction

public class QmailTransaction extends Transaction
{

    public QmailTransaction()
    {
        setServerLocation("mail.netzero.net");
    }

    public QmailTransaction(boolean flag)
    {
        super(flag);
        setServerLocation("mail.netzero.net");
    }
}
