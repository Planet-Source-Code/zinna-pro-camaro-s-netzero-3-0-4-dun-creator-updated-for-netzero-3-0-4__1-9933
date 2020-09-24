// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailClient.java

package mail;

import java.awt.Component;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import nzcom.*;
import serviceThread.ScheduleInterface;
import zpc.ZpComponent;

public class MailClient
    implements ScheduleInterface
{

    public MailClient(String s, String s1, NZWindow nzwindow)
    {
        socket = null;
        in = null;
        out = null;
        response = null;
        userId = null;
        userPassword = null;
        userId = s;
        userPassword = s1;
        nzWindow = nzwindow;
        serverName = ServerParms.getParm("msip", serverName);
    }

    public void activate()
    {
        int i = getMailCountOnServer();
        String s;
        if(i <= 0)
            s = "no";
        else
            s = String.valueOf(i);
        nzWindow.getMailComponent().setHoverText(resNZResource.getString("You_have_") + s + resNZResource.getString("_new_email_message(s)_at_N"));
        if(i > 0)
            nzWindow.getMailComponent().setState(1);
        else
            nzWindow.getMailComponent().setState(0);
        nzWindow.repaint();
    }

    private boolean authorized()
        throws IOException
    {
        out.println("user " + userId);
        response = in.readLine();
        ZCast.displayDebug("(user)" + response);
        if(response.startsWith("+OK"))
        {
            out.println("pass " + userPassword);
            response = in.readLine();
            ZCast.displayDebug("(pass)" + response);
            if(response.startsWith("+OK"))
                return true;
        }
        return false;
    }

    public int getMailCountOnServer()
    {
        InetAddress inetaddress = null;
        try
        {
            inetaddress = InetAddress.getByName(serverName);
            ZCast.displayDebug(inetaddress.toString());
        }
        catch(UnknownHostException unknownhostexception)
        {
            ZCast.displayDebug(unknownhostexception);
            return -1;
        }
        int i = -1;
        try
        {
            socket = new Socket(inetaddress, 110);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            response = in.readLine();
            ZCast.displayDebug(response);
            if(authorized())
            {
                i = retrieveMailCount();
                quit();
            }
        }
        catch(IOException ioexception)
        {
            ZCast.displayDebug(ioexception);
        }
        finally
        {
            try
            {
                if(socket != null)
                    socket.close();
            }
            catch(IOException _ex) { }
        }
        return i;
    }

    public static void main(String args[])
    {
        MailClient mailclient = new MailClient("", "", null);
        int i = mailclient.getMailCountOnServer();
        ZCast.displayDebug("you have " + i + " mail(s)...");
    }

    private void quit()
        throws IOException
    {
        out.println("quit");
        response = in.readLine();
        ZCast.displayDebug(response);
    }

    private int retrieveMailCount()
        throws IOException
    {
        out.println(statusCommand);
        response = in.readLine();
        ZCast.displayDebug("(" + statusCommand + ")" + response);
        if(response.startsWith("+OK"))
            try
            {
                int i = response.indexOf(32, 4);
                return Integer.parseInt(response.substring(4));
            }
            catch(Exception _ex) { }
        return -1;
    }

    public void setStatusCommand(String s)
    {
        statusCommand = s;
    }

    public static void startEmail(NZWindow nzwindow, String s)
    {
        OSDetectNative osdetectnative = NZWindow.getOsDetect();
        String s1 = null;
        s1 = OSDetectNative.getRegValue("SOFTWARE\\NetZero, Inc.\\NetZero\\Users\\" + s, "EMAIL", false);
        ZCast.displayDebug("Email default is " + s1);
        ZCast.displayDebug("Email default length is " + s1.length());
        if(s1.trim() == null || s1.length() == 0)
        {
            s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail", "", false);
            ZCast.displayDebug("after getting the default email ");
        }
        ZCast.displayDebug("Email default is " + s1);
        if(s1 != null)
            if(s1.equals("Netscape Messenger"))
            {
                String s2 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\" + s1 + "\\Protocols\\mailto\\shell\\open\\command", "", false);
                if(s2 != null)
                {
                    s2 = osdetectnative.expandEnvironmentVariables(s2);
                    int i = s2.lastIndexOf(32);
                    if(i > -1)
                        s2 = s2.substring(0, i) + " -mail";
                    ZCast.displayDebug("start command for " + s1 + " is " + s2);
                    osdetectnative.startProcess(s2);
                }
            } else
            {
                String s3 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail\\" + s1 + "\\shell\\open\\command", "", false);
                if(s3 != null)
                {
                    s3 = osdetectnative.expandEnvironmentVariables(s3);
                    ZCast.displayDebug("start command for " + s1 + " is " + s3);
                    osdetectnative.startProcess(s3);
                }
            }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private static String statusCommand = "new";
    private static String serverName = "zm.netzero.net";
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String response;
    private String userId;
    private String userPassword;
    private static NZWindow nzWindow;

}
