// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MicrosoftClient.java

package mail;


// Referenced classes of package mail:
//            EmailCfg, EmailConfigException

public abstract class MicrosoftClient extends EmailCfg
{

    public MicrosoftClient(String s)
        throws EmailConfigException
    {
        super(s);
    }

    public abstract boolean hasExistingAccounts();
}
