// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerResponseImpl.java

package ticker;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ticker:
//            TickerResponse

public class TickerResponseImpl
    implements Serializable, TickerResponse
{

    public Vector getSponsorMessages()
    {
        return m_vAds;
    }

    public Vector getTickerMessages()
    {
        return m_vMessages;
    }

    public void setSponsorMessages(Vector vector)
    {
        m_vAds = vector;
    }

    public void setTickerMessages(Vector vector)
    {
        m_vMessages = vector;
    }

    public TickerResponseImpl()
    {
        m_vAds = null;
        m_vMessages = null;
    }

    private Vector m_vAds;
    private Vector m_vMessages;
}
