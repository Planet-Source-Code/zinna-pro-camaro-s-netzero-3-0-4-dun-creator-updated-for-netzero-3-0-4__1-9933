// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OCXINFInstaller.java

package softwareSync;

import java.io.File;

// Referenced classes of package softwareSync:
//            ComponentInstaller

public class OCXINFInstaller extends ComponentInstaller
{

    public OCXINFInstaller()
    {
        m_InfInstallExe = "rundll32 setupapi,InstallHinfSection DefaultInstall 132 ";
    }

    public OCXINFInstaller(String s)
    {
        super(s);
        m_InfInstallExe = "rundll32 setupapi,InstallHinfSection DefaultInstall 132 ";
    }

    public boolean install(String s, String s1, String s2, String s3)
    {
        int i = s.lastIndexOf(".");
        String s4 = s;
        if(i >= 0)
            s4 = s.substring(0, i) + ".inf";
        if(!exec(getSystemDir() + File.separator + m_InfInstallExe + s1 + File.separator + s4))
        {
            String s5 = getSystemDir().substring(0, getSystemDir().lastIndexOf(File.separator));
            if(!exec(s5 + File.separator + m_InfInstallExe + s1 + File.separator + s4) && !exec(m_InfInstallExe + s1 + File.separator + s4))
                return false;
        }
        (new File(s1, s)).delete();
        (new File(s1, s4)).delete();
        return true;
    }

    private String m_InfInstallExe;
}
