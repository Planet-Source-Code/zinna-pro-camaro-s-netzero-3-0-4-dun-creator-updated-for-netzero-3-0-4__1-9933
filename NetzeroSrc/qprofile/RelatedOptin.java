// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelatedOptin.java

package qprofile;

import java.io.Serializable;

public class RelatedOptin
    implements Serializable
{

    public RelatedOptin()
    {
    }

    public Integer getCode()
    {
        return code;
    }

    public String getRelCode()
    {
        return relCode;
    }

    public Integer getRelOptinCode()
    {
        return relOptinCode;
    }

    public void setCode(Integer integer)
    {
        code = integer;
    }

    public void setRelCode(String s)
    {
        relCode = s;
    }

    public void setRelOptinCode(Integer integer)
    {
        relOptinCode = integer;
    }

    protected Integer code;
    protected Integer relOptinCode;
    protected String relCode;
}
