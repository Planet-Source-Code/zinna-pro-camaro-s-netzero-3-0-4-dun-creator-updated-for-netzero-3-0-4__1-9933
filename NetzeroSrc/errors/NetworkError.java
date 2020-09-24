// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NetworkError.java

package errors;

import java.net.InetAddress;
import java.util.Date;

// Referenced classes of package errors:
//            Error

public interface NetworkError
    extends Error
{

    public abstract Date getDate();

    public abstract String getErrorMessage();

    public abstract Integer getErrorNumber();

    public abstract String getFileName();

    public abstract int getPort();

    public abstract int getProtocol();

    public abstract Date getReportDate();

    public abstract Integer getReturnCode();

    public abstract InetAddress getServerIpAddress();

    public abstract Long getSessionId();

    public abstract Date getSysDate();

    public abstract String getTransactionType();

    public static final int PROTOCOL_TCPIP = 0;
    public static final int PROTOCOL_HTTP = 1;
    public static final int PROTOCOL_FTP = 2;
}
