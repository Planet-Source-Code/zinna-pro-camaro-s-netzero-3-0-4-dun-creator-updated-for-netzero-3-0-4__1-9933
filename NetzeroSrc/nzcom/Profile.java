// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Profile.java

package nzcom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Properties;

// Referenced classes of package nzcom:
//            OSDetectNative, ZCast

public class Profile
{

    public Profile()
    {
        fileName = "jnetz.prop";
        wasDocked = false;
        try
        {
            nzProperties = new Properties();
            FileInputStream fileinputstream = new FileInputStream(fileName);
            nzProperties.load(fileinputstream);
        }
        catch(Exception _ex) { }
        last = getIntValue("LastAdIndex", 10);
        ndx = getIntValue("Channel", 1);
        xCoord = getIntValue("XCoordinate", 0);
        yCoord = getIntValue("YCoordinate", 0);
        titleState = getBooleanValue("ViewTitle", true);
        buttonState = getBooleanValue("ViewButtons", true);
        phoneNumber = getStringValue("PhoneNumber", "");
        phoneLocation = getStringValue("PhoneLocation", "");
        wasDocked = getBooleanValue("Docked", false);
    }

    private boolean getBooleanValue(String s, boolean flag)
    {
        String s1 = flag ? "true" : "false";
        s1 = getStringValue(s, s1);
        return s1.equals("true");
    }

    public boolean getButtonState()
    {
        return buttonState;
    }

    public String getFileDir()
    {
        return getStringValue("FileDir", "c:/Netzero/cache/");
    }

    public boolean getFirstTimeFlag()
    {
        return getBooleanValue("FirstTime", true);
    }

    private int getIntValue(String s, int i)
    {
        return Integer.parseInt(getStringValue(s, String.valueOf(i)));
    }

    public int getLastAdIndex()
    {
        return last;
    }

    public String getPhoneListVersion()
    {
        return getStringValue("PhoneList", "none");
    }

    public String getPhoneLocation()
    {
        if(!ZCast.m_demoMode && ZCast.m_connectionType == 1)
            return "999-999-9999 CA NetZero";
        else
            return phoneLocation;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getProfPwd()
    {
        return OSDetectNative.unmangle(getStringValue("ProfPWD", ""));
    }

    public String getProfUid()
    {
        return getStringValue("ProfUID", "");
    }

    public boolean getSaveLoginState()
    {
        return getBooleanValue("SaveLogin", false);
    }

    private String getStringValue(String s, String s1)
    {
        String s2 = nzProperties.getProperty(s);
        if(s2 == null)
        {
            s2 = s1;
            nzProperties.put(s, s2);
        }
        return s2;
    }

    public boolean getTitleState()
    {
        return titleState;
    }

    public int getX()
    {
        return xCoord;
    }

    public int getY()
    {
        return yCoord;
    }

    private void putBooleanValue(String s, boolean flag)
    {
        String s1 = flag ? "true" : "false";
        nzProperties.put(s, s1);
    }

    private void putIntValue(String s, int i)
    {
        nzProperties.put(s, String.valueOf(i));
    }

    private void putStringValue(String s, String s1)
    {
        nzProperties.put(s, s1);
    }

    public void saveProperties()
    {
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(fileName);
            nzProperties.save(fileoutputstream, "NetZero profile values");
        }
        catch(Exception _ex) { }
    }

    public void setButtonState(boolean flag)
    {
        if(flag != buttonState)
        {
            buttonState = flag;
            putBooleanValue("ViewButtons", buttonState);
        }
    }

    public void setDocked(boolean flag)
    {
        putBooleanValue("Docked", flag);
    }

    public void setFileDir(String s)
    {
        putStringValue("FileDir", s);
    }

    public void setFirstTimeFlag(boolean flag)
    {
        putBooleanValue("FirstTime", flag);
    }

    public void setLastAdIndex(int i)
    {
        if(i != last)
            putIntValue("LastAdIndex", i);
        last = i;
    }

    public void setPhoneListVersion(String s)
    {
        putStringValue("PhoneList", s);
    }

    public void setPhoneLocation(String s)
    {
        if(phoneLocation == null)
            phoneLocation = "";
        if(!phoneLocation.equals(s))
        {
            phoneLocation = s;
            putStringValue("PhoneLocation", phoneLocation);
        }
    }

    public void setPhoneNumber(String s)
    {
        if(phoneNumber == null)
            phoneNumber = "";
        if(!phoneNumber.equals(s))
        {
            phoneNumber = s;
            putStringValue("PhoneNumber", phoneNumber);
        }
    }

    public void setProfPwd(String s)
    {
        ZCast.displayDebug(" profPWD=" + s);
        putStringValue("ProfPWD", OSDetectNative.mangle(s));
    }

    public void setProfUid(String s)
    {
        putStringValue("ProfUID", s);
    }

    public void setSaveLoginState(boolean flag)
    {
        putBooleanValue("SaveLogin", flag);
    }

    public void setTestProfPwd(String s)
    {
        putStringValue("ProfPWD", s);
    }

    public void setTitleState(boolean flag)
    {
        if(flag != titleState)
        {
            titleState = flag;
            putBooleanValue("ViewTitle", titleState);
        }
    }

    public void setX(int i)
    {
        if(i != xCoord)
            putIntValue("XCoordinate", i);
        xCoord = i;
    }

    public void setY(int i)
    {
        if(i != yCoord)
            putIntValue("YCoordinate", i);
        yCoord = i;
    }

    public boolean wasDocked()
    {
        return getBooleanValue("Docked", false);
    }

    private String fileName;
    private Properties nzProperties;
    private int ndx;
    private int last;
    private int xCoord;
    private int yCoord;
    private boolean titleState;
    private boolean buttonState;
    private String phoneNumber;
    private String phoneLocation;
    private boolean wasDocked;
}
