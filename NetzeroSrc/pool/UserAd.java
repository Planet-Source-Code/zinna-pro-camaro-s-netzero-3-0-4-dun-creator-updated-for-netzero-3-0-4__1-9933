// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserAd.java

package pool;

import java.io.Serializable;

public class UserAd
    implements Serializable
{

    protected UserAd()
    {
        isPlayable = false;
    }

    public UserAd(String s, String s1)
    {
        isPlayable = false;
        m_sUserId = s;
        m_sAdId = s1;
        isPlayable = false;
    }

    public String getAdId()
    {
        return m_sAdId;
    }

    public boolean getPlayable()
    {
        return isPlayable;
    }

    public String getUserId()
    {
        return m_sUserId;
    }

    protected void setPlayable(boolean flag)
    {
        isPlayable = flag;
    }

    protected String m_sUserId;
    protected String m_sAdId;
    protected boolean isPlayable;
}
