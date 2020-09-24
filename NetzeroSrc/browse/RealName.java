// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RealName.java

package browse;

import nzcom.ZCast;

public class RealName
{

    public RealName()
    {
    }

    public void dump()
    {
        ZCast.displayDebug("\tnm: " + getName());
        ZCast.displayDebug("\tlc: " + getLocale());
        ZCast.displayDebug("\tisrn: " + isRN());
        ZCast.displayDebug("\tty: " + getType());
    }

    public String getLocale()
    {
        return lc;
    }

    public String getName()
    {
        return nm;
    }

    public String getRN()
    {
        return isrn;
    }

    public String getType()
    {
        return ty;
    }

    public boolean isRN()
    {
        try
        {
            return getRN().equals("yes");
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    public void setLocale(String s)
    {
        lc = s;
    }

    public void setName(String s)
    {
        nm = s;
    }

    public void setRN(String s)
    {
        isrn = s;
    }

    public void setType(String s)
    {
        ty = s;
    }

    private String nm;
    private String lc;
    private String isrn;
    private String ty;
}
