// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateItem.java

package softwareSync;

import java.io.File;
import nzcom.ZCast;
import spe.DefaultItemListener;
import spe.SPEngine;

// Referenced classes of package softwareSync:
//            FileInstaller, FileVersion

public class UpdateItem
{

    public UpdateItem(String s, String s1, String s2, String s3, String s4, FileVersion fileversion, FileInstaller fileinstaller)
    {
        m_LocalFileName = null;
        m_LocalPath = null;
        m_OldFileName = null;
        m_OldPath = null;
        m_RemoteFileName = null;
        m_RequiredVersion = null;
        m_Installer = null;
        m_LocalFileName = s;
        m_LocalPath = s1;
        m_OldFileName = s2;
        m_OldPath = s3;
        m_RemoteFileName = s4;
        m_RequiredVersion = fileversion;
        m_Installer = fileinstaller;
    }

    public void checkVersionAndUpdate()
    {
        if(!meetsRequiredVersion())
        {
            File file = new File(m_LocalPath, m_LocalFileName);
            if(!file.exists())
            {
                ZCast.displayDebug("1 - Downloading file: " + m_RemoteFileName);
                downloadNewVersion();
            } else
            {
                ZCast.displayDebug("2 - Installing file: " + m_LocalPath + File.separator + m_LocalFileName);
                if(!install())
                    ZCast.displayDebug("# - FAILED to install file: " + m_LocalPath + File.separator + m_LocalFileName);
            }
        } else
        {
            ZCast.displayDebug("3 - Skipping file: " + m_OldPath + File.separator + m_LocalFileName);
        }
    }

    public boolean meetsRequiredVersion()
    {
        if(m_OldPath != null)
        {
            File file = new File(m_OldPath, m_OldFileName);
            if(file.exists())
                return m_RequiredVersion.greaterThan(new FileVersion(file)) ^ true;
        }
        return false;
    }

    private void downloadNewVersion()
    {
        SPEngine.getInstance().add(m_RemoteFileName, m_LocalPath + File.separator + m_LocalFileName, 4, 2, new DefaultItemListener(), m_LocalFileName);
    }

    private boolean install()
    {
        if(m_Installer != null)
        {
            if(m_Installer.promptInstall())
                return m_Installer.install(m_LocalFileName, m_LocalPath, m_OldFileName, m_OldPath);
            ZCast.displayDebug("# - User chose not to install file:" + m_LocalPath + File.separator + m_LocalFileName);
        }
        return true;
    }

    private String m_LocalFileName;
    private String m_LocalPath;
    private String m_OldFileName;
    private String m_OldPath;
    private String m_RemoteFileName;
    private FileVersion m_RequiredVersion;
    private FileInstaller m_Installer;
}
