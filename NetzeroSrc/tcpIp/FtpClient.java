// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FtpClient.java

package tcpIp;

import errors.NetworkErrorImpl;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import nzcom.DialDialog;
import nzcom.Initializer;
import nzcom.NZWindow;
import nzcom.ServerParms;
import nzcom.ZCast;
import sun.net.TelnetInputStream;
import transaction.client.TransactionThread;
import transaction.client.UlogThread;

public class FtpClient
{

    public FtpClient(String s, String s1, String s2)
    {
        logFlag = false;
        debugFlag = false;
        mvsFlag = false;
        binaryFlag = true;
        ftpStartTime = 0L;
        echoSocket = null;
        outputStream = null;
        inputStream = null;
        receiveFileName = null;
        processLog = null;
        user = null;
        password = null;
        server = null;
        myAddress = null;
        ftpc = null;
        setServer(s);
        setUser(s1);
        setPassword(s2);
    }

    private void displayText(String s)
    {
        String s1 = null;
        s1 = String.valueOf(this) + ":" + s;
        if(getDebugFlag())
            ZCast.displayDebug(s1);
        if(getLogFlag())
            processLog = processLog + "\r\n" + s1;
    }

    private String doDataPort(String as[], boolean flag, DataInputStream datainputstream, PrintStream printstream)
        throws Exception
    {
        ServerSocket serversocket = null;
        String s = "";
        int k = 0;
        RandomAccessFile randomaccessfile = null;
        for(int i = 0; i < as.length; i++)
            displayText(resNZResource.getString("Command_is__") + as[i]);

        serversocket = new ServerSocket(0);
        port(serversocket, datainputstream, printstream);
        if(flag)
        {
            printstream.println(getTransferType());
            displayText(getTransferType());
            getReply(datainputstream);
        }
        if(restartpoint != 0L)
        {
            printstream.println("rest " + restartpoint);
            displayText(resNZResource.getString("rest_") + restartpoint);
            getReply(datainputstream);
        }
        for(int j = 0; j < as.length; j++)
        {
            printstream.println(as[j]);
            displayText(as[j]);
            int l = getReply(datainputstream);
            if(l == 1)
            {
                Socket socket = serversocket.accept();
                InputStream inputstream = socket.getInputStream();
                byte abyte0[] = new byte[recvBlockSize];
                int j1;
                if(flag)
                {
                    randomaccessfile = new RandomAccessFile(receiveFileName, "rw");
                    if(restartpoint != 0L)
                    {
                        displayText(resNZResource.getString("seeking_to_") + restartpoint);
                        randomaccessfile.seek(restartpoint);
                        restartpoint = 0L;
                    }
                    displayText(resNZResource.getString("Recieving_File__") + receiveFileName);
                    int i1;
                    while((i1 = inputstream.read(abyte0)) != -1) 
                    {
                        randomaccessfile.write(abyte0, 0, i1);
                        k += i1;
                        if(aDialog != null)
                            aDialog.setLabel2Text(receiveFileName + ": " + k + resNZResource.getString("_bytes_received"));
                    }
                    displayText("\n");
                    randomaccessfile.close();
                } else
                {
                    while((j1 = inputstream.read(abyte0)) != -1) 
                    {
                        if(getDebugFlag())
                            System.out.write(abyte0, 0, j1);
                        s = s + new String(abyte0, 0, 0, j1);
                    }
                }
                if(!mvsFlag)
                    getReply(datainputstream);
                inputstream.close();
                socket.close();
            } else
            {
                if(flag)
                    displayText(resNZResource.getString("Error_calling_for_download"));
                serversocket.close();
            }
        }

        if(randomaccessfile != null)
            randomaccessfile.close();
        return s;
    }

    private String doDataPort(String s, boolean flag, DataInputStream datainputstream, PrintStream printstream)
        throws Exception
    {
        String as[] = new String[1];
        as[0] = s;
        return doDataPort(as, flag, datainputstream, printstream);
    }

    public String ftpDeleteFile(String s)
        throws Exception
    {
        ftpLogon();
        String s1 = "dele " + s;
        String s2 = doDataPort(s1, false, inputStream, outputStream);
        ftpLogout();
        return s2;
    }

    public String ftpGetFileInformation(String s)
        throws Exception
    {
        ftpLogon();
        String s1 = "list " + s;
        String s2 = doDataPort(s1, false, inputStream, outputStream);
        ftpLogout();
        return s2;
    }

    private void ftpLogon()
        throws Exception
    {
        String s = null;
        boolean flag = false;
        ftpStartTime = System.currentTimeMillis();
        processLog = "";
        echoSocket = new Socket(getServer(), 21);
        if(myAddress == null)
            myAddress = echoSocket.getLocalAddress();
        outputStream = new PrintStream(echoSocket.getOutputStream());
        inputStream = new DataInputStream(echoSocket.getInputStream());
        getReply(inputStream);
        s = "user " + getUser();
        outputStream.println(s);
        if(getReply(inputStream) != 3)
            return;
        s = "pass " + getPassword();
        outputStream.println(s);
        if(getReply(inputStream) != 2)
            throw new Exception(resNZResource.getString("User_Id_and_password_not_M"));
        else
            return;
    }

    private void ftpLogout()
        throws Exception
    {
        outputStream.close();
        inputStream.close();
        echoSocket.close();
        logProcessDetails();
    }

    public void ftpReceiveFile(String s, String s1)
        throws Exception
    {
        httpReceiveFile(s, s1);
    }

    public void ftpRenameFile(String s, String s1)
        throws Exception
    {
        String as[] = new String[2];
        ftpLogon();
        as[0] = "rnfr " + s;
        as[1] = "rnto " + s1;
        doDataPort(as, false, inputStream, outputStream);
        ftpLogout();
    }

    public void ftpSendFile(String s, String s1)
        throws Exception
    {
        ftpLogon();
        String s2 = "stor " + s;
        upload(s2, inputStream, outputStream);
        ftpRenameFile(s, s1);
        ftpLogout();
    }

    public void ftpSubmitBatchJob(String s)
        throws Exception
    {
        ftpLogon();
        setMvsFlag(true);
        setBinaryFlag(false);
        String s1 = "site filetype=jes";
        doDataPort(s1, false, inputStream, outputStream);
        s1 = "retr " + s;
        doDataPort(s1, false, inputStream, outputStream);
        ftpLogout();
    }

    private boolean getBinaryFlag()
    {
        return binaryFlag;
    }

    private boolean getDebugFlag()
    {
        return debugFlag;
    }

    private boolean getLogFlag()
    {
        return logFlag;
    }

    private String getPassword()
    {
        return password;
    }

    public String getReceiveFileName()
    {
        return receiveFileName;
    }

    private int getReply(DataInputStream datainputstream)
        throws Exception
    {
        String s = null;
        do
        {
            s = datainputstream.readLine();
            displayText(s);
        } while(!Character.isDigit(s.charAt(0)) || !Character.isDigit(s.charAt(1)) || !Character.isDigit(s.charAt(2)) || s.charAt(3) != ' ');
        int i = Integer.parseInt(s.substring(0, 1));
        if(i == 5)
            throw new Exception(s);
        else
            return i;
    }

    private String getServer()
    {
        return server;
    }

    private String getTransferType()
    {
        if(getBinaryFlag())
            return "type i";
        else
            return "type a";
    }

    private String getUser()
    {
        return user;
    }

    public void httpReceiveFile(String s, String s1)
        throws Exception
    {
        String s2 = ServerParms.getParm("sdip", "sd.netzero.net");
        String s3 = "http://" + s2 + "/compaq/sd/";
        boolean flag = false;
        int j = 0;
        byte abyte0[] = new byte[4096];
        Object obj = null;
        Object obj1 = null;
        String s4 = s3 + s;
        String s5 = null;
        try
        {
            URL url = new URL(s4);
            ZCast.displayDebug("source file : " + s4);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setUseCaches(false);
            urlconnection.setAllowUserInteraction(false);
            urlconnection.connect();
            BufferedInputStream bufferedinputstream = new BufferedInputStream(urlconnection.getInputStream());
            FileOutputStream fileoutputstream = new FileOutputStream(s1);
            receiveFileName = resNZResource.getString("processing...");
            int i;
            while((i = bufferedinputstream.read(abyte0)) != -1) 
            {
                fileoutputstream.write(abyte0, 0, i);
                j += i;
                if(aDialog != null)
                    aDialog.setLabel2Text(receiveFileName + ": " + j + resNZResource.getString("_bytes_received"));
            }
            bufferedinputstream.close();
            fileoutputstream.close();
            return;
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(String.valueOf(exception) + ": file " + s4);
        }
        NetworkErrorImpl networkerrorimpl = new NetworkErrorImpl(Initializer.m_zwindow.getNzUserid(), new Date());
        networkerrorimpl.setSessionId(Initializer.m_sessionId);
        networkerrorimpl.setProtocol(1);
        networkerrorimpl.setFileName(s4);
        networkerrorimpl.setErrorMessage(s5);
        if(Initializer.m_ulogThread != null)
            Initializer.m_ulogThread.putRequest(networkerrorimpl, "NDAT");
        else
            UlogThread.addTransactionToFile(networkerrorimpl, "NDAT");
    }

    private void logProcessDetails()
    {
        double d = 0.0D;
        String s = null;
        d = System.currentTimeMillis() - ftpStartTime;
        d /= 1000D;
        s = "Responce Time: " + d + " seconds";
        displayText(s + "\n");
        if(getLogFlag())
            writeToLog(processLog);
    }

    private boolean port(ServerSocket serversocket, DataInputStream datainputstream, PrintStream printstream)
        throws Exception
    {
        int i = serversocket.getLocalPort();
        displayText(resNZResource.getString("Will_listen_on_port,_") + i);
        byte abyte0[] = myAddress.getAddress();
        short aword0[] = new short[4];
        for(int j = 0; j <= 3; j++)
        {
            aword0[j] = abyte0[j];
            if(aword0[j] < 0)
                aword0[j] += 256;
        }

        String s = "port " + aword0[0] + "," + aword0[1] + "," + aword0[2] + "," + aword0[3] + "," + ((i & 0xff00) >> 8) + "," + (i & 0xff);
        printstream.println(s);
        ZCast.displayDebug(s);
        displayText(s);
        int k = getReply(datainputstream);
        return k == 2;
    }

    private void rest(String s, DataInputStream datainputstream, PrintStream printstream)
        throws Exception
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s);
        stringtokenizer.nextToken();
        restartpoint = Integer.parseInt(stringtokenizer.nextToken());
        displayText("restart noted");
    }

    public void setBinaryFlag(boolean flag)
    {
        binaryFlag = flag;
    }

    private void setDebugFlag(boolean flag)
    {
        debugFlag = flag;
    }

    public void setDialDialog(DialDialog dialdialog)
    {
        aDialog = dialdialog;
    }

    private void setLogFlag(boolean flag)
    {
        logFlag = flag;
    }

    private void setMvsFlag(boolean flag)
    {
        mvsFlag = flag;
    }

    private void setPassword(String s)
    {
        password = s;
    }

    private String setReceiveFileName()
        throws Exception
    {
        String s = "ftpServiceOutput";
        Object obj = null;
        if(getReceiveFileName() != null)
            return getReceiveFileName();
        for(int i = 0; i < 1000; i++)
        {
            String s1 = s + i;
            if(!(new File(s1)).exists())
            {
                setReceiveFileName(s1);
                return s1;
            }
        }

        throw new Exception(resNZResource.getString("Ftp_output_directory_is_fu"));
    }

    public void setReceiveFileName(String s)
    {
        receiveFileName = s;
    }

    private void setServer(String s)
    {
        server = s;
    }

    private void setUser(String s)
    {
        user = s;
    }

    private void sunFtpLogon()
        throws Exception
    {
        ftpc = new sun.net.ftp.FtpClient();
        ZCast.displayDebug("sun create server connection to " + getServer());
        ftpc.openServer(getServer());
        ftpStartTime = System.currentTimeMillis();
        processLog = "";
        ZCast.displayDebug("sun login as " + getUser());
        ftpc.login(getUser(), getPassword());
    }

    private void sunFtpLogout()
        throws Exception
    {
        if(ftpc != null)
        {
            ftpc.closeServer();
            ftpc = null;
            logProcessDetails();
        }
    }

    public void sunFtpReceiveFile(String s, String s1)
        throws Exception
    {
        int i = 0;
        int j = 0;
        ZCast.displayDebug("using sun classes for ftp receive file " + s);
        sunFtpLogon();
        ftpc.binary();
        setReceiveFileName(s1);
        TelnetInputStream telnetinputstream = ftpc.get(s);
        File file = new File(s1);
        FileOutputStream fileoutputstream = new FileOutputStream(file);
        byte abyte0[] = new byte[4096];
        while((i = telnetinputstream.read(abyte0, 0, 4096)) > -1) 
        {
            fileoutputstream.write(abyte0, 0, i);
            j += i;
            if(aDialog != null)
                aDialog.setLabel2Text(receiveFileName + ": " + i + resNZResource.getString("_bytes_received"));
        }
        fileoutputstream.close();
        telnetinputstream.close();
        sunFtpLogout();
        ftpLogon();
    }

    private boolean upload(String s, DataInputStream datainputstream, PrintStream printstream)
        throws Exception
    {
        ServerSocket serversocket = null;
        try
        {
            serversocket = new ServerSocket(0);
        }
        catch(IOException ioexception)
        {
            displayText(resNZResource.getString("Could_not_get_port_for_lis") + serversocket.getLocalPort() + ", " + ioexception);
            return false;
        }
        port(serversocket, datainputstream, printstream);
        printstream.println(getTransferType());
        displayText(getTransferType());
        getReply(datainputstream);
        if(restartpoint != 0L)
        {
            printstream.println("rest " + restartpoint);
            displayText("rest " + restartpoint);
            getReply(datainputstream);
        }
        printstream.println(s);
        displayText(s);
        int i = getReply(datainputstream);
        if(i == 1)
        {
            Socket socket = null;
            try
            {
                socket = serversocket.accept();
            }
            catch(IOException ioexception1)
            {
                displayText(resNZResource.getString("Accept_failed__") + serversocket.getLocalPort() + ", " + ioexception1);
            }
            try
            {
                OutputStream outputstream = socket.getOutputStream();
                byte abyte0[] = new byte[recvBlockSize];
                StringTokenizer stringtokenizer = new StringTokenizer(s);
                stringtokenizer.nextToken();
                String s1 = stringtokenizer.nextToken();
                RandomAccessFile randomaccessfile = new RandomAccessFile(s1, "r");
                if(restartpoint != 0L)
                {
                    displayText(resNZResource.getString("seeking_to_") + restartpoint);
                    randomaccessfile.seek(restartpoint);
                    restartpoint = 0L;
                }
                displayText(resNZResource.getString("sending_file__") + s1);
                int j;
                while((j = randomaccessfile.read(abyte0)) > 0) 
                    outputstream.write(abyte0, 0, j);
                displayText("\n");
                randomaccessfile.close();
                outputstream.close();
                socket.close();
                serversocket.close();
                i = getReply(datainputstream);
            }
            catch(IOException ioexception2)
            {
                ioexception2.printStackTrace();
            }
            return i == 2;
        }
        displayText(resNZResource.getString("Error_calling_for_download"));
        try
        {
            serversocket.close();
        }
        catch(IOException _ex)
        {
            displayText(resNZResource.getString("Error_closing_server_socke"));
        }
        return false;
    }

    private static synchronized void writeToLog(String s)
    {
        Object obj = null;
        try
        {
            RandomAccessFile randomaccessfile = new RandomAccessFile(logFileName, "rw");
            randomaccessfile.seek(randomaccessfile.length());
            randomaccessfile.write(s.getBytes());
            randomaccessfile.close();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("Error writing to log : " + exception);
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    static long restartpoint = 0L;
    static final int PRELIM = 1;
    static final int COMPLETE = 2;
    static final int CONTINUE = 3;
    static final int TRANSIENT = 4;
    static final int ERROR = 5;
    private static int sendBlockSize = 10000;
    private static int recvBlockSize = 10000;
    private static String logFileName = "ftpClient.log";
    private boolean logFlag;
    private boolean debugFlag;
    private boolean mvsFlag;
    private boolean binaryFlag;
    private long ftpStartTime;
    private Socket echoSocket;
    private PrintStream outputStream;
    private DataInputStream inputStream;
    private String receiveFileName;
    private String processLog;
    private String user;
    private String password;
    private String server;
    private DialDialog aDialog;
    private InetAddress myAddress;
    private sun.net.ftp.FtpClient ftpc;

}
