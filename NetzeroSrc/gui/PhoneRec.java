// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhoneRec.java

package gui;

import java.io.Serializable;
import nzcom.ZCast;

public class PhoneRec
    implements Cloneable, Serializable
{

    public PhoneRec(String s, String s1)
    {
        CityString = null;
        StateString = null;
        AreaCode = null;
        First3 = null;
        Last4 = null;
        LDFlag = false;
        UseAreaCode = false;
        rank = "-9999.9999";
        inactive = false;
        TempInactive = false;
        RetryCount = 3;
        rejected = false;
        IgnoreLDWarning = false;
        int i = s.indexOf(", ");
        CityString = s.substring(0, i);
        StateString = s.substring(i + 2);
        parseNumRank(s1);
    }

    public PhoneRec(String s, String s1, String s2, String s3)
    {
        CityString = null;
        StateString = null;
        AreaCode = null;
        First3 = null;
        Last4 = null;
        LDFlag = false;
        UseAreaCode = false;
        rank = "-9999.9999";
        inactive = false;
        TempInactive = false;
        RetryCount = 3;
        rejected = false;
        IgnoreLDWarning = false;
        CityString = s;
        AreaCode = s1;
        First3 = s2;
        Last4 = s3;
    }

    public PhoneRec(String s, String s1, String s2, String s3, String s4)
    {
        CityString = null;
        StateString = null;
        AreaCode = null;
        First3 = null;
        Last4 = null;
        LDFlag = false;
        UseAreaCode = false;
        rank = "-9999.9999";
        inactive = false;
        TempInactive = false;
        RetryCount = 3;
        rejected = false;
        IgnoreLDWarning = false;
        CityString = s;
        AreaCode = s1;
        First3 = s2;
        Last4 = s3;
        rank = s4;
    }

    public boolean active()
    {
        return inactive ^ true;
    }

    public Object clone()
    {
        try
        {
            return (PhoneRec)super.clone();
        }
        catch(CloneNotSupportedException _ex)
        {
            return null;
        }
    }

    public String getAreaCode()
    {
        return AreaCode;
    }

    public String getCityString()
    {
        return CityString;
    }

    public String getDialString()
    {
        if(LDFlag && UseAreaCode)
            return "1-" + AreaCode + "-" + getPhoneNumber();
        if(UseAreaCode)
            return AreaCode + "-" + getPhoneNumber();
        else
            return getPhoneNumber();
    }

    public String getFirst3()
    {
        return First3;
    }

    public boolean getIgnoreLDWarning()
    {
        return IgnoreLDWarning;
    }

    public String getLast4()
    {
        return Last4;
    }

    public boolean getLDFlag()
    {
        return LDFlag;
    }

    public String getPhoneNumber()
    {
        return First3 + "-" + Last4;
    }

    public String getRank()
    {
        return rank;
    }

    public boolean getRejected()
    {
        return rejected;
    }

    public int getRetryCount()
    {
        return RetryCount;
    }

    public String getStateString()
    {
        return StateString;
    }

    public boolean getUseAreaCode()
    {
        return UseAreaCode;
    }

    public boolean numberIsBad(String s)
    {
        return !getAreaCode().equals(s) && !getUseAreaCode();
    }

    private void parseNumRank(String s)
    {
        ZCast.displayDebug("numrank is " + s);
        AreaCode = s.substring(0, 3);
        First3 = s.substring(3, 6);
        Last4 = s.substring(7, 11);
        int i = s.indexOf("|");
        rank = s.substring(i + 1);
    }

    public void setIgnoreLDWarning(boolean flag)
    {
        IgnoreLDWarning = flag;
    }

    public void setInactive(boolean flag)
    {
        inactive = flag;
    }

    public void setLDFlag(boolean flag)
    {
        LDFlag = flag;
    }

    public void setRank(String s)
    {
        rank = s;
    }

    public void setRejected(boolean flag)
    {
        rejected = flag;
    }

    public void setRetryCount(int i)
    {
        RetryCount = i;
    }

    public void setStateString(String s)
    {
        StateString = s;
    }

    public void setTempInactive(boolean flag)
    {
        TempInactive = flag;
    }

    public void setUseAreaCode(boolean flag)
    {
        UseAreaCode = flag;
    }

    public boolean TempInactive()
    {
        return TempInactive;
    }

    public String toBadNumberString()
    {
        String s = toListString();
        s = s + " is in area code (" + getAreaCode() + ")";
        return s;
    }

    public String toListString()
    {
        Object obj = null;
        String s = "";
        if(StateString != null)
            s = ", " + StateString;
        if(LDFlag && UseAreaCode)
            return "1-" + AreaCode + "-" + getPhoneNumber() + ", " + CityString + s;
        if(UseAreaCode)
            return AreaCode + "-" + getPhoneNumber() + ", " + CityString + s;
        else
            return getPhoneNumber() + ", " + CityString + s;
    }

    static final long serialVersionUID = 0x334c672d67c37be1L;
    static final String NO_RANK = "-9999.9999";
    private String CityString;
    private String StateString;
    private String AreaCode;
    private String First3;
    private String Last4;
    private boolean LDFlag;
    private boolean UseAreaCode;
    private String rank;
    private boolean inactive;
    private boolean TempInactive;
    private int RetryCount;
    private boolean rejected;
    private boolean IgnoreLDWarning;
}
