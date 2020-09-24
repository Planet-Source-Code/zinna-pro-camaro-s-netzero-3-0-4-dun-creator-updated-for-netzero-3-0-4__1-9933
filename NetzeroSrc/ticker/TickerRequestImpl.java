// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerRequestImpl.java

package ticker;

import java.io.Serializable;

// Referenced classes of package ticker:
//            TickerRequest

public class TickerRequestImpl
    implements Serializable, TickerRequest
{

    public TickerRequestImpl(int i)
    {
        m_bAds = false;
        m_nTopicId = null;
        m_nTopicId = new Integer(i);
    }

    public Integer getTopicId()
    {
        return m_nTopicId;
    }

    public boolean sendBackAds()
    {
        return m_bAds;
    }

    public void setSendAds(boolean flag)
    {
        m_bAds = flag;
    }

    private boolean m_bAds;
    private Integer m_nTopicId;
}
