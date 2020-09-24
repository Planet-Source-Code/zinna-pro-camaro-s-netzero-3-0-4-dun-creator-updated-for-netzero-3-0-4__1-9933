// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Image.java

package ui;

import java.io.Serializable;

public class Image
    implements Serializable
{

    public Image(Object obj, Object obj1)
    {
        setImageID(obj);
        setComponentState(obj1);
    }

    public Long getComponentState()
    {
        return stateID;
    }

    public Long getImageID()
    {
        return imageID;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setComponentState(Object obj)
    {
        if(obj == null)
            stateID = null;
        else
        if(obj instanceof String)
            stateID = new Long(((String)obj).trim());
        else
        if(obj instanceof Long)
            stateID = (Long)obj;
    }

    public void setImageID(Object obj)
    {
        if(obj == null)
            imageID = null;
        else
        if(obj instanceof String)
            imageID = new Long(((String)obj).trim());
        else
        if(obj instanceof Long)
            imageID = (Long)obj;
    }

    public void setImageName(String s)
    {
        imageName = s;
    }

    public String toString()
    {
        return String.valueOf(getComponentState()) + "     " + getImageName();
    }

    private Long imageID;
    private Long stateID;
    private String imageName;
}
