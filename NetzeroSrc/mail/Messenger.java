// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Messenger.java

package mail;

import java.io.*;
import java.util.ResourceBundle;

// Referenced classes of package mail:
//            MessNEudora, Dll4Mail, EmailValue, Nsdll, 
//            EmailCfg, EmailConfigException

public class Messenger extends MessNEudora
{

    public Messenger(String s)
        throws EmailConfigException
    {
        super(s);
    }

    public boolean canCreateNewProf()
    {
        return true;
    }

    private void createBlankEntry(int i)
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int j = 0; j < i; j++)
            stringbuffer.append(" ");

        String s = stringbuffer.toString();
        raf.writeBytes(s);
    }

    private int createEntry(String s, String s1)
        throws Exception
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("user_pref(\"");
        stringbuffer.append(s);
        stringbuffer.append("\", \"");
        stringbuffer.append(s1);
        stringbuffer.append("\");\r\n");
        String s2 = stringbuffer.toString();
        raf.writeBytes(s2);
        return s2.length();
    }

    protected void createNewProfile()
        throws EmailConfigException
    {
        if(!Nsdll.createProfile(loginName, getFileDir()))
            throw new EmailConfigException(resNZResource.getString("Unable_to_create_ns_regist"));
        File file = new File(getFileName());
        if(file.exists())
        {
            updateExistingAccount();
            return;
        }
        String s = null;
        file = new File(fileDir);
        if(file.exists() || file.mkdirs())
        {
            fileDir += "\\Mail";
            File file1 = new File(fileDir);
            if(file1.exists() || file1.mkdir())
            {
                File file2 = new File(getFileName());
                try
                {
                    raf = new RandomAccessFile(file2, "rw");
                    EmailValue aemailvalue[] = getParams();
                    for(int i = 0; i < aemailvalue.length; i++)
                        createEntry(aemailvalue[i].getValueName(), aemailvalue[i].getValueData());

                    raf.close();
                    raf = null;
                }
                catch(Exception exception1)
                {
                    s = exception1.toString();
                }
                finally
                {
                    try
                    {
                        if(raf != null)
                            raf.close();
                    }
                    catch(IOException _ex) { }
                    if(s != null)
                        throw new EmailConfigException(s);
                }
            } else
            {
                throw new EmailConfigException(resNZResource.getString("Unable_to_create_'") + fileDir + resNZResource.getString("'_directory"));
            }
        } else
        {
            throw new EmailConfigException("Unable to create '" + fileDir + resNZResource.getString("'_directory"));
        }
    }

    public String getAppName()
    {
        return "Netscape Messenger";
    }

    private EmailValue[] getParams()
    {
        EmailValue aemailvalue[] = new EmailValue[4];
        aemailvalue[0] = new EmailValue("mail.identity.useremail", loginName + "@netzero.net");
        aemailvalue[1] = new EmailValue("mail.pop_name", loginName);
        aemailvalue[2] = new EmailValue("network.hosts.pop_server", "pop.netzero.net");
        aemailvalue[3] = new EmailValue("network.hosts.smtp_server", "smtp.netzero.net");
        return aemailvalue;
    }

    public String[] getProfiles()
    {
        return Nsdll.getProfileList();
    }

    protected void initFileDir()
        throws EmailConfigException
    {
        try
        {
            if(isNewProfile())
            {
                fileDir = Dll4Mail.getRegValueData("HKEY_CURRENT_USER", "Software\\Netscape\\Netscape Navigator\\Main", "Install Directory");
                if(fileDir.trim().length() == 0)
                    fileDir = Dll4Mail.getRegValueData("HKEY_LOCAL_MACHINE", "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths", "Netscape.EXE");
                int i = fileDir.toLowerCase().indexOf("netscape");
                i = fileDir.indexOf("\\", i);
                fileDir = fileDir.substring(0, i);
                fileDir = fileDir + "\\users\\" + loginName;
            } else
            {
                fileDir = Nsdll.getProfileDir(getProfileName());
            }
        }
        catch(Exception _ex)
        {
            throw new EmailConfigException(resNZResource.getString("Unable_to_retrieve_Netscap"));
        }
    }

    protected void initFileName()
        throws EmailConfigException
    {
        try
        {
            fileName = getFileDir() + "\\prefs.js";
        }
        catch(Exception _ex)
        {
            throw new EmailConfigException(resNZResource.getString("Unable_to_initialize_Netsc"));
        }
    }

    public boolean isAccountAlreadyDefined()
    {
        return false;
    }

    public boolean isVersionAbleToConfigure()
    {
        try
        {
            String s = Dll4Mail.getRegValueData("HKEY_LOCAL_MACHINE", "Software\\Netscape\\Netscape Navigator", "CurrentVersion");
            if(s == null)
                return false;
            return s.trim().substring(0, 3).compareTo(new String("4.6")) >= 0;
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    private boolean seek(String s)
        throws IOException
    {
        raf.seek(0L);
        for(long l = raf.getFilePointer(); l != len; l = raf.getFilePointer())
        {
            String s1 = raf.readLine();
            if(s1.indexOf(s) != -1)
                return true;
        }

        return false;
    }

    private void seek(String s, String s1)
        throws Exception
    {
        raf.seek(0L);
        for(long l = raf.getFilePointer(); l != len; l = raf.getFilePointer())
        {
            String s2 = raf.readLine();
            if(s2.indexOf(s) == -1)
                continue;
            raf.seek(l);
            createBlankEntry(s2.length());
            raf.seek(len);
            break;
        }

        createEntry(s, s1);
        len = raf.getFilePointer();
    }

    protected void updateExistingAccount()
        throws EmailConfigException
    {
        try
        {
            File file = new File(getFileName());
            updateFile(file);
        }
        catch(Exception exception)
        {
            throw new EmailConfigException(exception.toString());
        }
    }

    private void updateFile(File file)
        throws EmailConfigException
    {
        String s = null;
        try
        {
            raf = new RandomAccessFile(file, "rw");
            len = raf.length();
            EmailValue aemailvalue[] = getParams();
            for(int i = 0; i < aemailvalue.length; i++)
                seek(aemailvalue[i].getValueName(), aemailvalue[i].getValueData());

            raf.close();
            raf = null;
        }
        catch(Exception exception1)
        {
            s = exception1.toString();
        }
        finally
        {
            try
            {
                if(raf != null)
                    raf.close();
            }
            catch(IOException _ex) { }
            if(s != null)
                throw new EmailConfigException(s);
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private RandomAccessFile raf;
    private long len;

}
