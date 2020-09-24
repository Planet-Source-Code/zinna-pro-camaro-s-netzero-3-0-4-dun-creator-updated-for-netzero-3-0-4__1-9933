// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberOptin.java

package qprofile;

import java.io.Serializable;

public class MemberOptin
    implements Serializable
{

    public MemberOptin()
    {
        status = new Integer(0);
        originalStatus = new Integer(0);
    }

    public Integer getCode()
    {
        return code;
    }

    public Integer getStatus()
    {
        return status;
    }

    public boolean isOptIn()
    {
        return status.intValue() == 1;
    }

    public boolean isOptinChanged()
    {
        return status.intValue() != originalStatus.intValue();
    }

    public void setCode(Integer integer)
    {
        code = integer;
    }

    public void setOptInStatus(boolean flag)
    {
        if(flag)
            status = new Integer(1);
        else
            status = new Integer(0);
    }

    public void setOriginalStatus(Integer integer)
    {
        originalStatus = integer;
    }

    public void setStatus(Integer integer)
    {
        status = integer;
    }

    protected Integer code;
    protected Integer status;
    protected Integer originalStatus;
}
