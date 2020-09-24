// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComponentInstaller.java

package softwareSync;

import gui.NZDialogBox;
import java.io.*;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package softwareSync:
//            FileInstaller

public class ComponentInstaller
    implements FileInstaller
{

    public ComponentInstaller()
    {
        m_Prompt = null;
        m_SystemDir = null;
        m_InstallExeFileName = "regsvr32 /s";
        m_UninstallExeFileName = "regsvr32 /u /s";
    }

    public ComponentInstaller(String s)
    {
        m_Prompt = null;
        m_SystemDir = null;
        m_InstallExeFileName = "regsvr32 /s";
        m_UninstallExeFileName = "regsvr32 /u /s";
        m_Prompt = s;
    }

    public boolean promptInstall()
    {
        if(m_Prompt != null)
        {
            String as[] = {
                "Yes", "No"
            };
            return NZDialogBox.showMessageDialog("Question", m_Prompt, 1, as) == 0;
        } else
        {
            return true;
        }
    }

    public boolean install(String s, String s1, String s2, String s3)
    {
        File file = new File(s3, s2);
        if(file.exists())
        {
            if(!exec(getSystemDir() + File.separator + m_UninstallExeFileName + " \"" + s3 + File.separator + s2 + "\""))
                return false;
            if(!file.delete())
                ZCast.displayDebug("Failed to del old file " + s3 + File.separator + s2);
        }
        if(!(new File(s1, s)).renameTo(file))
            ZCast.displayDebug("Failed to move file " + s1 + File.separator + s + ", to " + s3 + File.separator + s2);
        return exec(getSystemDir() + File.separator + m_InstallExeFileName + " \"" + s3 + File.separator + s2 + "\"");
    }

    protected boolean exec(String s)
    {
        boolean flag = false;
        try
        {
            if(!s.equals(""))
            {
                Process process = Runtime.getRuntime().exec(s);
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s1;
                while((s1 = bufferedreader.readLine()) != null) 
                    ZCast.displayDebug("SYSERR: " + s1);
                bufferedreader.close();
                flag = true;
            }
        }
        catch(Exception _ex) { }
        return flag;
    }

    protected String getSystemDir()
    {
        if(m_SystemDir == null)
            m_SystemDir = OSDetectNative.getSystemRoot();
        return m_SystemDir;
    }

    protected String m_Prompt;
    protected String m_SystemDir;
    private String m_InstallExeFileName;
    private String m_UninstallExeFileName;
}
