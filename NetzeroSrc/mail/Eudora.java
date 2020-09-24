// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Eudora.java

package mail;

import java.util.*;

// Referenced classes of package mail:
//            MessNEudora, EmailCfg, Dll4Mail, EmailConfigException

public class Eudora extends MessNEudora
{

    public Eudora(String s)
        throws EmailConfigException
    {
        super(s);
        personalities = null;
    }

    private Hashtable _getPersonalities()
        throws EmailConfigException
    {
        if(personalities == null)
        {
            personalities = new Hashtable();
            String as[] = Dll4Mail.getPrivateProfileString("Personalities", "", getFileName());
            for(int i = 0; i < as.length; i++)
            {
                String as1[] = Dll4Mail.getPrivateProfileString("Personalities", as[i], getFileName());
                if(as1.length >= 1)
                    personalities.put(as[i], as1[0]);
            }

        }
        return personalities;
    }

    public boolean canCreateNewProf()
    {
        try
        {
            String as[] = Dll4Mail.getRegSubkeys("HKEY_LOCAL_MACHINE", "software\\qualcomm incorporated\\eudora pro email");
            if(as[0].startsWith("4"))
                return isAccountDefined("Settings");
        }
        catch(Exception _ex) { }
        return false;
    }

    protected void createNewProfile()
        throws EmailConfigException
    {
        Enumeration enumeration = _getPersonalities().keys();
        int i = 0;
        try
        {
            while(enumeration.hasMoreElements()) 
            {
                String s = ((String)enumeration.nextElement()).substring(7);
                int j = Integer.parseInt(s);
                if(j > i)
                    i = j;
            }
        }
        catch(Exception _ex)
        {
            throw new EmailConfigException(resNZResource.getString("Cannot_setup_the_entry_nam"));
        }
        String s1 = "Persona" + i;
        String s2 = "Persona-" + loginName;
        if(!Dll4Mail.writePrivateProfileString("Personalities", s1, s2, getFileName()))
        {
            throw new EmailConfigException(resNZResource.getString("Unable_to_create_'") + s1 + resNZResource.getString("'_section_in_the_initializ"));
        } else
        {
            updateSection(s2);
            return;
        }
    }

    public String getAppName()
    {
        return "Eudora";
    }

    public String[] getProfiles()
    {
        Vector vector = new Vector();
        try
        {
            for(Enumeration enumeration = _getPersonalities().elements(); enumeration.hasMoreElements();)
                try
                {
                    String s = (String)enumeration.nextElement();
                    vector.addElement(s.substring(8));
                }
                catch(Exception _ex) { }

        }
        catch(Exception _ex) { }
        finally
        {
            vector.insertElementAt("Dominant", 0);
            String as[] = new String[vector.size()];
            vector.copyInto(as);
            return as;
        }
    }

    public void initFileDir()
        throws EmailConfigException
    {
        try
        {
            fileDir = Dll4Mail.getRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail\\Eudora\\shell\\open\\command", "");
            int i = fileDir.lastIndexOf("\\");
            fileDir = fileDir.substring(0, i);
        }
        catch(Exception _ex)
        {
            throw new EmailConfigException(resNZResource.getString("Unable_the_retrieve_Eudora"));
        }
    }

    public void initFileName()
        throws EmailConfigException
    {
        try
        {
            fileName = getFileDir() + "\\eudora.ini";
        }
        catch(Exception _ex)
        {
            throw new EmailConfigException(resNZResource.getString("Unable_the_setup_Eudora_in"));
        }
    }

    public boolean isAccountAlreadyDefined()
    {
        String s = getProfileName().equals("Dominant") ? "Settings" : "Persona-" + getProfileName();
        return isAccountDefined(s);
    }

    private boolean isAccountDefined(String s)
    {
        try
        {
            return Dll4Mail.isPrivateProfileString(s, "POPAccount", getFileName()) && Dll4Mail.isPrivateProfileString(s, "ReturnAddress", getFileName()) && Dll4Mail.isPrivateProfileString(s, "SMTPServer", getFileName()) && Dll4Mail.isPrivateProfileString(s, "POPServer", getFileName()) && Dll4Mail.isPrivateProfileString(s, "LoginName", getFileName());
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    protected void updateExistingAccount()
        throws EmailConfigException
    {
        if(getProfileName().equals("Dominant"))
            updateSection("Settings");
        else
            updateSection("Persona-" + getProfileName());
    }

    private void updateSection(String s)
        throws EmailConfigException
    {
        String s1 = "POPAccount";
        if(!Dll4Mail.writePrivateProfileString(s, s1, loginName + "@pop.netzero.net", getFileName()))
            throw new EmailConfigException("(" + s + resNZResource.getString(")_Cannot_setup_'") + s1 + resNZResource.getString("'of_the_initialization_fil"));
        String s2 = "ReturnAddress";
        if(!Dll4Mail.writePrivateProfileString(s, s2, loginName + "@netzero.net", getFileName()))
            throw new EmailConfigException("(" + s + resNZResource.getString(")_Cannot_setup_'") + s2 + resNZResource.getString("'of_the_initialization_fil"));
        String s3 = "SMTPServer";
        if(!Dll4Mail.writePrivateProfileString(s, s3, "smtp.netzero.net", getFileName()))
            throw new EmailConfigException("(" + s + resNZResource.getString(")_Cannot_setup_'") + s3 + resNZResource.getString("'of_the_initialization_fil"));
        String s4 = "POPServer";
        if(!Dll4Mail.writePrivateProfileString(s, s4, "pop.netzero.net", getFileName()))
            throw new EmailConfigException("(" + s + resNZResource.getString(")_Cannot_setup_'") + s4 + resNZResource.getString("'of_the_initialization_fil"));
        String s5 = "LoginName";
        if(!Dll4Mail.writePrivateProfileString(s, s5, loginName, getFileName()))
            throw new EmailConfigException("(" + s + resNZResource.getString(")_Cannot_setup_'") + s5 + resNZResource.getString("'of_the_initialization_fil"));
        else
            return;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    Hashtable personalities;

}
