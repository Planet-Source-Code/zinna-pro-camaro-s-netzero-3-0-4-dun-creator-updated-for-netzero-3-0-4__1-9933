// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UbberUI.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            IUbberUI

public abstract class UbberUI
    implements Serializable, IUbberUI
{

    public UbberUI()
    {
    }

    public static String createStateCode()
    {
        return "insert";
    }

    public static String deleteStateCode()
    {
        return "delete";
    }

    public Boolean getchangedFlag()
    {
        return changedFlag;
    }

    public String getObjState()
    {
        return objState;
    }

    public void setchangedFlag(Boolean boolean1)
    {
        changedFlag = boolean1;
    }

    public void setObjState(String s)
    {
        objState = s;
    }

    public String updateString()
    {
        String s = "";
        Vector vector = updateVector();
        for(int i = 0; i < vector.size(); i++)
        {
            String s1 = updateValueOf(vector.elementAt(i));
            s = s + s1 + ",";
        }

        return s;
    }

    protected String updateValueOf(Object obj)
    {
        if(obj == null)
            return "";
        if(obj instanceof String)
            return (String)obj;
        if(obj instanceof Boolean)
            if(((Boolean)obj).booleanValue())
                return "1";
            else
                return "0";
        if(obj instanceof Integer)
            return ((Integer)obj).toString();
        if(obj instanceof Long)
            return ((Long)obj).toString();
        else
            return null;
    }

    public abstract Vector updateVector();

    private Boolean changedFlag;
    private String objState;
}
