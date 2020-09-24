// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FtpService.java

package tcpIp;

import nzcom.DialDialog;

// Referenced classes of package tcpIp:
//            FtpClient

public class FtpService
{

    public FtpService(String s, String s1, String s2)
        throws Exception
    {
        createFtpClient(s, s1, s2);
    }

    public FtpService(String s, String s1, String s2, DialDialog dialdialog)
        throws Exception
    {
        createFtpClient(s, s1, s2);
        ftpClient.setDialDialog(dialdialog);
    }

    private void createFtpClient(String s, String s1, String s2)
        throws Exception
    {
        ftpClient = new FtpClient(s, s1, s2);
    }

    public String deleteFile(String s)
        throws Exception
    {
        return ftpClient.ftpDeleteFile(s);
    }

    public String getFileInformation(String s)
        throws Exception
    {
        return ftpClient.ftpGetFileInformation(s);
    }

    public void receiveFile(String s, String s1)
        throws Exception
    {
        ftpClient.ftpReceiveFile(s, s1);
    }

    public void renameFile(String s, String s1)
        throws Exception
    {
        ftpClient.ftpRenameFile(s, s1);
    }

    public void sendFile(String s, String s1)
        throws Exception
    {
        ftpClient.ftpSendFile(s, s1);
    }

    public void setBinaryFlag(boolean flag)
    {
        ftpClient.setBinaryFlag(flag);
    }

    public void submitBatchJob(String s)
        throws Exception
    {
        ftpClient.ftpSubmitBatchJob(s);
    }

    private FtpClient ftpClient;
    private DialDialog aDialDialog;
}
