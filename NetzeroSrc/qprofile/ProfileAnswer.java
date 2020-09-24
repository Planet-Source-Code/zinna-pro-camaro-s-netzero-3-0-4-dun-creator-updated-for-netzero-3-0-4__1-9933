// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileAnswer.java

package qprofile;

import java.io.Serializable;

public class ProfileAnswer
    implements Serializable
{

    public ProfileAnswer()
    {
    }

    public Character getCategory()
    {
        return category;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }

    public Integer getDisplayCode()
    {
        return displayCode;
    }

    public Boolean isSelected()
    {
        return isSelected;
    }

    public void setCategory(Character character)
    {
        category = character;
    }

    public void setCode(Integer integer)
    {
        code = integer;
    }

    public void setDesc(String s)
    {
        desc = s;
    }

    public void setDisplayCode(Integer integer)
    {
        displayCode = integer;
    }

    public void setIsSelected(Boolean boolean1)
    {
        isSelected = boolean1;
    }

    protected Integer code;
    protected Character category;
    protected Boolean isSelected;
    protected String desc;
    protected Integer displayCode;
}
