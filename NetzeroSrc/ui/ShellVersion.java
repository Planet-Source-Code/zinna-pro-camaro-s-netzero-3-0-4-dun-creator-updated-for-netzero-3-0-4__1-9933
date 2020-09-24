// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShellVersion.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            UbberUI, ShellUI

public class ShellVersion extends UbberUI
    implements Serializable
{

    public ShellVersion(Vector vector)
    {
        setNzVersion(vector.elementAt(0));
        setStatus(vector.elementAt(1));
        setID(vector.elementAt(2));
    }

    public Long getID()
    {
        return shellID;
    }

    public String getNzVersion()
    {
        return nzVersion;
    }

    public String getStatus()
    {
        return status;
    }

    public boolean isActive()
    {
        return getStatus().equals(ShellUI.statusCodeActive());
    }

    public void setID(Object obj)
    {
        if(obj == null)
            shellID = null;
        else
        if(obj instanceof String)
            shellID = new Long((String)obj);
        else
        if(obj instanceof Long)
            shellID = (Long)obj;
    }

    public void setNzVersion(Object obj)
    {
        if(obj == null)
            nzVersion = null;
        else
            nzVersion = (String)obj;
    }

    public void setStatus(Object obj)
    {
        if(obj == null)
            status = null;
        else
            status = (String)obj;
    }

    public String toString()
    {
        return "Software Version: " + getNzVersion() + " Status: " + getStatus();
    }

    public Vector updateVector()
    {
        Vector vector = new Vector();
        vector.addElement(getNzVersion());
        vector.addElement(getStatus());
        vector.addElement(getID());
        return vector;
    }

    private String nzVersion;
    private String status;
    private Long shellID;
}
