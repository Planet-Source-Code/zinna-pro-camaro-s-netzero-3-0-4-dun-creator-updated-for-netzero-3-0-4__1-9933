// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OutlookX.java

package mail;

import java.util.ResourceBundle;

// Referenced classes of package mail:
//            MicrosoftClient, Dll4Mail, EmailCfg, EmailConfigException

public class OutlookX extends MicrosoftClient
{

    public OutlookX(String s)
        throws EmailConfigException
    {
        super(s);
    }

    public String getAppName()
    {
        return "Outlook Express";
    }

    public boolean hasExistingAccounts()
    {
        return false;
    }

    public boolean isAccountAlreadyDefined()
    {
        return false;
    }

    public void setupAccount()
        throws EmailConfigException
    {
        if(!Dll4Mail.setupOutlookExpressAccount(loginName))
            throw new EmailConfigException(resNZResource.getString("Unable_to_configure_") + getAppName() + ".");
        else
            return;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");

}
