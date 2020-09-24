// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerToken.java

package ticker;

import java.io.Serializable;

public class TickerToken
    implements Serializable
{

    public TickerToken()
    {
        m_keyName = null;
        m_objData = null;
        m_url = null;
        m_iId = null;
    }

    public Object getDataObject()
    {
        return m_objData;
    }

    public String getKeyName()
    {
        return m_keyName;
    }

    public Integer getTokenId()
    {
        return m_iId;
    }

    public String getUrl()
    {
        return m_url;
    }

    public void setDataObject(Object obj)
    {
        m_objData = obj;
    }

    public void setKeyName(String s)
    {
        m_keyName = s;
    }

    public void setTokenId(int i)
    {
        m_iId = new Integer(i);
    }

    public void setUrl(String s)
    {
        m_url = s;
    }

    private String m_keyName;
    private Object m_objData;
    private String m_url;
    private Integer m_iId;
}
