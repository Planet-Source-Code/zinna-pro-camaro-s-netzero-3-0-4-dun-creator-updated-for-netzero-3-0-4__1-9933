// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Request.java

package http;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import nzcom.ZCast;

public class Request
{

    public Request(String s)
    {
        serverAddress = null;
        program = null;
        programData = null;
        requestEnd = "& HTTP/1.0\r\nUser-Agent: Cis_Server\r\nAccept: image/gif, image/jpeg,  */*\r\n\r\n";
        serverAddress = s;
    }

    public String executeRequest()
    {
        Object obj = null;
        String s1 = "";
        try
        {
            String s2 = new String("http://" + serverAddress + getProgram() + "?" + getProgramData());
            s2 = new String("http://www.netzero.com");
            URL url = new URL(s2);
            URLConnection urlconnection = url.openConnection();
            BufferedInputStream bufferedinputstream = (BufferedInputStream)urlconnection.getContent();
            int i = bufferedinputstream.available();
            if(i > 0)
            {
                byte abyte0[] = new byte[i];
                bufferedinputstream.read(abyte0, 0, i - 1);
                s1 = new String(abyte0);
            } else
            {
                int j = 1;
                do
                {
                    String s = urlconnection.getHeaderField(j);
                    if(s == null)
                        break;
                    s1 = new String(s1 + "\n" + s);
                    j++;
                } while(true);
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return s1;
    }

    public String getProgram()
    {
        return program;
    }

    public String getProgramData()
    {
        return programData;
    }

    public byte[] gifFromCgi(String s)
    {
        Object obj = null;
        String s2 = "";
        byte abyte0[] = null;
        try
        {
            String s3 = new String("http://" + s);
            URL url = new URL(s3);
            URLConnection urlconnection = url.openConnection();
            InputStream inputstream = urlconnection.getInputStream();
            int i = inputstream.available();
            if(i > 0)
            {
                abyte0 = new byte[i];
                inputstream.read(abyte0, 0, i - 1);
                s2 = new String(abyte0);
            } else
            {
                int j = 1;
                do
                {
                    String s1 = urlconnection.getHeaderField(j);
                    if(s1 == null)
                        break;
                    s2 = new String(s2 + "\n" + s1);
                    j++;
                } while(true);
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return abyte0;
    }

    public void setProgram(String s)
    {
        program = s;
    }

    public void setProgramData(String s)
    {
        programData = s.replace(' ', '+');
    }

    public void setServerAddress(String s)
    {
        serverAddress = s;
    }

    private String serverAddress;
    private String program;
    private String programData;
    private String requestEnd;
}
