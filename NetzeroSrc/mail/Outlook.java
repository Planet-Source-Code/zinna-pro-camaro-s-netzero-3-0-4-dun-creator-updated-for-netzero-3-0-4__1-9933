// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Outlook.java

package mail;

import java.util.*;
import nzcom.ZCast;

// Referenced classes of package mail:
//            MicrosoftClient, RandomId, EmailCfg, Dll4Mail, 
//            EmailConfigException

public class Outlook extends MicrosoftClient
{

    public Outlook(String s)
        throws EmailConfigException
    {
        super(s);
        keys = null;
        key1 = null;
        key2 = null;
    }

    private void createFirstKey()
        throws EmailConfigException
    {
        String s = getKey1() + "\\" + keys[0].toString();
        if(!Dll4Mail.createRegKey("HKEY_CURRENT_USER", s))
            throw new EmailConfigException("unable to create the registry key: " + s);
        byte abyte0[] = {
            2, 0, 0, 0, 12, 0, 0, 0, 22, 0, 
            0, 0, 77, 73, 78, 69, 84, 46, 68, 76, 
            76, 0, 73, 78, 69, 84, 65, 66, 46, 68, 
            76, 76, 0
        };
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3001", "Internet E-mail - " + loginName) || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3d09", "IMAIL") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3d0a", "MINET.DLL") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3d0b", "MailServiceEntry") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e661f", loginName) || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d01", keys[1].getId()) || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d02", keys[2].getId()) || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "101e3d0f", abyte0))
        {
            Dll4Mail.deleteRegKey("HKEY_CURRENT_USER", s);
            throw new EmailConfigException(resNZResource.getString("unable_to_set_a_registry_v") + s);
        } else
        {
            return;
        }
    }

    private void createFourthKey()
        throws EmailConfigException
    {
        String s = getKey2() + "\\" + loginName;
        if(!Dll4Mail.createRegKey("HKEY_CURRENT_USER", s))
        {
            throw new EmailConfigException(resNZResource.getString("unable_to_create_the_regis") + s);
        } else
        {
            setCommonValues(s, loginName);
            return;
        }
    }

    public void createSecondKey()
        throws EmailConfigException
    {
        String s = getKey1() + "\\" + keys[1].toString();
        if(!Dll4Mail.createRegKey("HKEY_CURRENT_USER", s))
            throw new EmailConfigException(resNZResource.getString("unable_to_create_the_regis") + s);
        byte abyte0[] = new byte[4];
        byte abyte1[] = {
            35, 0, 0, 0
        };
        if(!Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "00033009", abyte0) || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "00033009", abyte0) || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3001", "Internet Address Template") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3006", "Internet Address Template") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e300a", "INETAB.DLL") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3d09", "IMAIL") || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d0c", keys[0].getId()))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_a_registry_v") + s);
        else
            return;
    }

    public void createThirdKey()
        throws EmailConfigException
    {
        String s = getKey1() + "\\" + keys[2].toString();
        if(!Dll4Mail.createRegKey("HKEY_CURRENT_USER", s))
            throw new EmailConfigException(resNZResource.getString("unable_to_create_the_regis") + s);
        byte abyte0[] = new byte[4];
        byte abyte1[] = {
            36, 0, 0, 0
        };
        if(!Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "00033009", abyte0) || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "00033009", abyte0) || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3001", "Internet E-mail - " + loginName) || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3006", "Internet E-mail") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e300a", "MINET.DLL") || !Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, "001e3d09", "IMAIL") || !Dll4Mail.setRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d0c", keys[0].getId()))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_a_registry_v") + s);
        else
            return;
    }

    private void generateRdmKeys()
        throws EmailConfigException
    {
        keys = new RandomId[3];
        Random random = new Random((new Date()).getTime());
        for(int i = 0; i < 3; i++)
        {
            int j;
            for(j = 0; j < 10; j++)
            {
                keys[i] = RandomId.getRandomId(random);
                if(!isExistingKey(key1 + "\\" + keys[i], i))
                    break;
            }

            if(j >= 10)
                throw new EmailConfigException(resNZResource.getString("Unable_to_generate_random_"));
        }

    }

    public String getAppName()
    {
        return "Microsoft Outlook";
    }

    private String getKey1()
        throws EmailConfigException
    {
        if(key1 == null)
            setupMainKeys();
        return key1;
    }

    private String getKey2()
        throws EmailConfigException
    {
        if(key2 == null)
            setupMainKeys();
        return key2;
    }

    public boolean hasExistingAccounts()
    {
        return false;
    }

    public boolean isAccountAlreadyDefined()
    {
        try
        {
            return Dll4Mail.isExistingKey("HKEY_CURRENT_USER", getKey2() + "\\" + loginName) == 1;
        }
        catch(Exception _ex)
        {
            return true;
        }
    }

    private boolean isExistingKey(String s, int i)
    {
        for(int j = 0; j < i; j++)
            if(s.equals(keys[j]))
                return true;

        return Dll4Mail.isExistingKey("HKEY_CURRENT_USER", s) != 0;
    }

    static void setCommonValues(String s, String s1)
        throws EmailConfigException
    {
        if(Dll4Mail.isExistingKey("HKEY_CURRENT_USER", s) != 1)
            throw new EmailConfigException(resNZResource.getString("Unable_to_find_'") + s + resNZResource.getString("'_in_the_registry."));
        s = s + "\\" + s1;
        if(!Dll4Mail.createRegKey("HKEY_CURRENT_USER", s))
            throw new EmailConfigException(resNZResource.getString("unable_to_create_the_regis") + s);
        String s2 = "Account Name";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s2, s1))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s2);
        String s3 = "Connection Type";
        if(!Dll4Mail.setRegDwordValueData("HKEY_CURRENT_USER", s, s3, 1))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s3);
        String s4 = "POP3 Prompt for Password";
        if(!Dll4Mail.setRegDwordValueData("HKEY_CURRENT_USER", s, s4, 1))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s4);
        String s5 = "POP3 Server";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s5, "pop.netzero.net"))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s5);
        String s6 = "POP3 User Name";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s6, s1))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s6);
        String s7 = "SMTP Display Name";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s7, s1))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s7);
        String s8 = "SMTP Email Address";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s8, s1 + "@netzero.net"))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s8);
        String s9 = "SMTP Server";
        if(!Dll4Mail.setRegValueData("HKEY_CURRENT_USER", s, s9, "smtp.netzero.net"))
            throw new EmailConfigException(resNZResource.getString("unable_to_set_the_registry") + s9);
        else
            return;
    }

    public void setupAccount()
        throws EmailConfigException
    {
        boolean flag = true;
        String s = Dll4Mail.getRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail", "");
        ZCast.displayDebug("default App is " + s);
        if(s.indexOf("Microsoft Outlook") < 0)
        {
            flag = false;
            Dll4Mail.setRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail", "", "Microsoft Outlook");
        }
        if(!Dll4Mail.setupOutlookAccount(loginName))
        {
            if(!flag)
                Dll4Mail.setRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail", "", s);
            throw new EmailConfigException(resNZResource.getString("Unable_to_configure_") + getAppName() + ".");
        }
        if(!flag)
            Dll4Mail.setRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail", "", s);
    }

    private void setupMainKeys()
        throws EmailConfigException
    {
        if(System.getProperty("os.name").equals("Windows NT"))
        {
            key1 = "Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows Messaging Subsystem\\Profiles\\Preferred Customer";
            key2 = "Software\\Microsoft\\Office\\8.0\\Outlook\\OMI Account Manager\\Accounts";
        } else
        if(System.getProperty("os.name").equals("Windows 95") || System.getProperty("os.name").equals("Windows 98"))
        {
            key1 = "Software\\Microsoft\\Windows Messaging Subsystem\\Profiles\\Microsoft Outlook";
            key2 = "Software\\Microsoft\\Office\\8.0\\Outlook\\Internet Account Manager\\Accounts";
        } else
        {
            throw new EmailConfigException(resNZResource.getString("Unknown_operating_system."));
        }
    }

    private void updateFourthKey()
        throws EmailConfigException
    {
        String s = getKey1() + "\\9207f3e0a3b11019908b08002b2a56c2";
        if(!Dll4Mail.updateRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d0e", keys[0].getId(), false) || !Dll4Mail.updateRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d01", keys[1].getId(), false) || !Dll4Mail.updateRegBinaryValueData("HKEY_CURRENT_USER", s, "01023d02", keys[2].getId(), true))
            throw new EmailConfigException(resNZResource.getString("unable_to_update_") + s);
        else
            return;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private RandomId keys[];
    private String key1;
    private String key2;

}
