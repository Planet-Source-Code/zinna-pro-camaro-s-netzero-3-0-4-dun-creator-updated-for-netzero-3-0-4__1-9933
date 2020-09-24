// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProcessZip.java

package nzcom;

import gui.DialGroups;
import gui.NZDialogBox;
import java.io.*;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import tcpIp.FtpService;

// Referenced classes of package nzcom:
//            ConfigParams, ServerParms, Profile, ZCast, 
//            OSDetectNative

public class ProcessZip
{

    public static void delayedProcess(OSDetectNative osdetectnative, String s)
    {
        try
        {
            File file = new File("sdstat");
            if(file.exists())
            {
                String as[] = {
                    "ok"
                };
                NZDialogBox.showMessageDialog(resNZResource.getString("Upgrade_Completed"), resNZResource.getString("Click_OK_to_restart_NetZer"), 0, as, 5, resNZResource.getString("seconds_for_auto_restart"));
                file.delete();
                osdetectnative.startProcess(s);
                System.exit(8);
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public static void main(String args[])
    {
        ZCast.displayDebug("invoking process zip");
        processDownload("nzDist.zip", null, null);
    }

    public static void postProcess()
    {
        try
        {
            File file = new File("xfer.bat");
            if(!file.exists())
            {
                ZCast.displayDebug("post processing not needed");
                return;
            }
            ZipFile zipfile = new ZipFile("lib/zcast1_6.zip");
            Enumeration enumeration = zipfile.entries();
            ZCast.displayDebug("post processing started");
            while(enumeration.hasMoreElements()) 
            {
                ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                String s = zipentry.getName();
                if(s.endsWith(".gif") || s.endsWith(".jpg") || s.endsWith(".bmp"))
                    try
                    {
                        InputStream inputstream = zipfile.getInputStream(zipentry);
                        FileOutputStream fileoutputstream = new FileOutputStream(s);
                        byte abyte0[] = new byte[4096];
                        ZCast.displayDebug("extracting " + s);
                        int i;
                        while((i = inputstream.read(abyte0, 0, 4096)) > 0) 
                            fileoutputstream.write(abyte0, 0, i);
                        inputstream.close();
                        fileoutputstream.close();
                    }
                    catch(Exception exception1)
                    {
                        ZCast.displayDebug(exception1);
                    }
            }
            zipfile.close();
            file.delete();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        ZCast.displayDebug("postprocessing ended");
    }

    public static void processDownload(String s, OSDetectNative osdetectnative, String s1)
    {
        String s2 = "lib/zcast1_6.zip";
        String s3 = "zcastsd.zip";
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        try
        {
            File file = new File(s);
            if(file.exists())
                flag1 = true;
            File file2 = new File(s3);
            if(file2.exists())
                flag2 = true;
            if(!flag1 && !flag2)
            {
                ZCast.displayDebug("distribution file(s) not found, no processing...");
                return;
            }
            File file3 = new File("xfer.bat");
            PrintWriter printwriter1 = new PrintWriter(new FileWriter(file3));
            File file4 = new File("cleanup.bat");
            PrintWriter printwriter2 = new PrintWriter(new FileWriter(file4));
            File file5 = new File("dist/");
            if(file5.exists())
            {
                file5.delete();
                ZCast.displayDebug("distribution directory deleted");
            }
            file5.mkdir();
            ZCast.displayDebug("distribution directory re-created");
            if(flag2)
            {
                File file6 = new File("dist/zcastsd.zix");
                file2.renameTo(file6);
                printwriter1.println("copy dist\\zcastsd.zix lib\\zcastsd.zip");
                printwriter2.println("del dist\\zcastsd.zix");
                flag = true;
            }
            if(flag1)
            {
                File file7 = new File("dist/zcast.zix");
                file.renameTo(file7);
                flag = true;
                printwriter1.println("copy dist\\zcast.zix lib\\zcast.zip");
                printwriter2.println("del dist\\zcast.zix");
                ZipFile zipfile = new ZipFile(file7);
                for(Enumeration enumeration = zipfile.entries(); enumeration.hasMoreElements();)
                {
                    ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                    String s4 = zipentry.getName();
                    if(s4.endsWith(".exe") || s4.endsWith(".dll"))
                        try
                        {
                            InputStream inputstream = zipfile.getInputStream(zipentry);
                            FileOutputStream fileoutputstream = new FileOutputStream("dist/" + s4);
                            byte abyte0[] = new byte[4096];
                            ZCast.displayDebug("extracting " + s4);
                            int i;
                            while((i = inputstream.read(abyte0, 0, 4096)) > 0) 
                                fileoutputstream.write(abyte0, 0, i);
                            inputstream.close();
                            fileoutputstream.close();
                            printwriter1.println("copy dist\\" + s4 + " " + s4);
                            printwriter2.println("del dist\\" + s4);
                        }
                        catch(Exception exception2)
                        {
                            ZCast.displayDebug(exception2);
                        }
                }

            }
            printwriter1.println("nzStart");
            printwriter1.close();
            printwriter2.close();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        if(flag)
            try
            {
                File file1 = new File("sdstat");
                PrintWriter printwriter = new PrintWriter(new FileWriter(file1));
                printwriter.println("SD downloaded; install needed");
                printwriter.close();
                if(s1 != null)
                {
                    String as[] = {
                        "ok"
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Software_Update"), resNZResource.getString("Click_OK_to_complete_the_u"), 0, as, 60, resNZResource.getString("seconds_until_auto_restart"));
                    file1.delete();
                    osdetectnative.startProcess(s1);
                    System.exit(7);
                }
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug(exception1);
            }
    }

    public static void processDownload(OSDetectNative osdetectnative, String s)
    {
        String as[] = {
            "ok"
        };
        NZDialogBox.showMessageDialog(resNZResource.getString("Software_Update"), resNZResource.getString("Click_OK_to_complete_the_u"), 0, as, 60, resNZResource.getString("seconds_until_auto_restart"));
        osdetectnative.startProcess(s);
        System.exit(7);
    }

    public static boolean processPhoneList()
    {
        boolean flag = false;
        String s = "phn.dat";
        String s1 = "phn.tmp";
        String s2 = ZCast.m_profile.getPhoneListVersion();
        String s3 = ServerParms.getParm("PhoneList", "");
        String s4 = s3;
        ZCast.displayDebug("@@@@@@@@@@@@@@@@@ processPhoneList profVer =" + s2 + " serverVer = " + s3);
        if(s3.equals(""))
            return false;
        if(s3.equals(ConfigParams.getPhoneVers()))
            return false;
        File file = new File(s);
        if(file.exists())
        {
            s4 = s4 + " " + file.lastModified() + " " + file.length();
            ZCast.displayDebug("@@@@@@@@@@@@@@@@@ PHONE LIST!!! compString=" + s4);
            ZCast.displayDebug("@@@@@@@@@@@@@@@@@ PHONE LIST!!! profVer=" + s2);
            flag = s2.equals(s4) ^ true;
        } else
        {
            flag = true;
        }
        ZCast.displayDebug("@@@@@@@@@@@@@@@@@ PHONE LIST!!! Do we need to download phn.dat? " + flag);
        if(flag)
            try
            {
                ZCast.displayDebug("****>>PHONE LIST!!! attempting to download phonelist file");
                FtpService ftpservice = new FtpService(ServerParms.getParm("phip", ""), "ftp", "install@netzero.net");
                ftpservice.receiveFile(s, s1);
                File file1 = new File(s1);
                ZCast.displayDebug("****>>PHONE LIST!!! downloaded phonelist file size=" + file1.length());
                if(file1 != null && file1.length() > 0L)
                {
                    if(file.exists())
                        file.delete();
                    file1.renameTo(file);
                }
                ZCast.displayDebug("****>>PHONE LIST!!!  Downloaded file: " + file.getAbsolutePath());
                ZCast.displayDebug("****>>PHONE LIST!!!  size=" + file.length() + " Last modified=" + file.lastModified());
                ZCast.m_profile.setPhoneListVersion(s3 + " " + file.lastModified() + " " + file.length());
                ZCast.m_profile.saveProperties();
                ConfigParams.initalize(file);
                ZCast.displayDebug("@@@@@@@@@@@@@@@@@ verifyAllPhoneList ");
                DialGroups.verifyAllPhoneList();
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("****>>PHONE LIST!!! phonelist download FAILED!!");
                ZCast.displayDebug(exception);
            }
        return flag;
    }

    public ProcessZip()
    {
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private static final String targetName = "lib/zcast1_6.zip";
    private static final String fname = "xfer.bat";
    private static final String fname2 = "cleanup.bat";
    private static final String fname3 = "sdstat";
    private static final String blddir = "dist/";

}
