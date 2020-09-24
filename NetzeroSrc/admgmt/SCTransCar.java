// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SCTransCar.java

package admgmt;

import java.io.Serializable;

public class SCTransCar
    implements Serializable
{

    public SCTransCar()
    {
        m_sTransCode = null;
        m_oContents = null;
    }

    public Object getContents()
    {
        return m_oContents;
    }

    public String getTransCode()
    {
        return m_sTransCode;
    }

    public void setContents(Object obj)
    {
        m_oContents = obj;
    }

    public void setTransCode(String s)
    {
        m_sTransCode = s;
    }

    private String m_sTransCode;
    private Object m_oContents;
}
