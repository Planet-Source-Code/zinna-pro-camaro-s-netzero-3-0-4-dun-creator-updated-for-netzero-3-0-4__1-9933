// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailValue.java

package mail;


public class EmailValue
{

    public EmailValue()
    {
    }

    public EmailValue(String s, String s1)
    {
        valueName = s;
        valueData = s1;
    }

    public String getValueData()
    {
        return valueData;
    }

    public String getValueName()
    {
        return valueName;
    }

    public void setValueData(String s)
    {
        valueData = s;
    }

    public void setValueName(String s)
    {
        valueName = s;
    }

    private String valueName;
    private String valueData;
}
