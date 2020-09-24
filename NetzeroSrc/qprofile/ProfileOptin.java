// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileOptin.java

package qprofile;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package qprofile:
//            RelatedOptin

public class ProfileOptin
    implements Serializable
{

    public ProfileOptin()
    {
    }

    public void addRelatedOptin(RelatedOptin relatedoptin)
    {
        if(relatedOptins == null)
            relatedOptins = new Vector(8);
        relatedOptins.addElement(relatedoptin);
    }

    public Integer getCategory()
    {
        return category;
    }

    public Integer getCode()
    {
        return code;
    }

    public Character getDataType()
    {
        return dataType;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getDisplayId()
    {
        return displayId;
    }

    public Integer getLength()
    {
        return length;
    }

    public Integer getPartnerCode()
    {
        return partnerCode;
    }

    public Boolean getProfileFlag()
    {
        return profileFlag;
    }

    public Boolean getRelatedFlag()
    {
        return relatedFlag;
    }

    public Vector getRelatedOptins()
    {
        return relatedOptins;
    }

    public Enumeration getRelatedOptinsEnumeration()
    {
        return relatedOptins.elements();
    }

    public Character getStatus()
    {
        return status;
    }

    public Boolean getTargetFlag()
    {
        return targetFlag;
    }

    public String getText()
    {
        return text;
    }

    public void removeAllRelatedOptins()
    {
        if(relatedOptins != null)
            relatedOptins.removeAllElements();
    }

    public void setCategory(Integer integer)
    {
        category = integer;
    }

    public void setCode(Integer integer)
    {
        code = integer;
    }

    public void setDataType(Character character)
    {
        dataType = character;
    }

    public void setDesc(String s)
    {
        desc = s;
    }

    public void setDisplayId(String s)
    {
        displayId = s;
    }

    public void setLength(Integer integer)
    {
        length = integer;
    }

    public void setPartnerCode(Integer integer)
    {
        partnerCode = integer;
    }

    public void setProfileFlag(Boolean boolean1)
    {
        profileFlag = boolean1;
    }

    public void setRelatedFlag(Boolean boolean1)
    {
        relatedFlag = boolean1;
    }

    public void setRelatedOptins(Vector vector)
    {
        relatedOptins = vector;
    }

    public void setStatus(Character character)
    {
        status = character;
    }

    public void setTargetFlag(Boolean boolean1)
    {
        targetFlag = boolean1;
    }

    public void setText(String s)
    {
        text = s;
    }

    protected Integer code;
    protected Integer partnerCode;
    protected String desc;
    protected String text;
    protected Integer length;
    protected Character dataType;
    protected Character status;
    protected Integer category;
    protected Boolean relatedFlag;
    protected Boolean profileFlag;
    protected Boolean targetFlag;
    protected String displayId;
    protected Vector relatedOptins;
}
