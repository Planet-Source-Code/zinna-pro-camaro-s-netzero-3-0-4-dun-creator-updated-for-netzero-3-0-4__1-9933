// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShellUI.java

package ui;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package ui:
//            UbberUI, NzComponent, UIContainer, NzContainer, 
//            IShellUI, IContainer

public class ShellUI extends UbberUI
    implements IShellUI, Serializable, Cloneable
{

    public ShellUI()
    {
        id = null;
        version = null;
        status = null;
        shellDescription = null;
        containerVector = new Vector();
        changed = Boolean.FALSE;
    }

    public ShellUI(Vector vector)
    {
        this();
        setID(vector.elementAt(0));
        setShellVersion(vector.elementAt(1));
        setStatus(vector.elementAt(2));
        setShellDesc(vector.elementAt(3));
        setLayoutType(vector.elementAt(4));
    }

    public void addContainer(IContainer icontainer)
    {
        containerVector.addElement(icontainer);
    }

    public ShellUI copy()
    {
        try
        {
            ShellUI shellui = (ShellUI)clone();
            shellui.containerVector = new Vector();
            return shellui;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public Integer countContainer()
    {
        return new Integer(containerVector.size());
    }

    public ShellUI deepCopy()
    {
        try
        {
            ShellUI shellui = (ShellUI)clone();
            shellui.containerVector = new Vector();
            Vector vector = listContainer();
            for(int i = 0; i < vector.size(); i++)
            {
                NzContainer nzcontainer = (NzContainer)vector.elementAt(i);
                shellui.addContainer(nzcontainer.deepCopy());
            }

            return shellui;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public void deleteContainer(IContainer icontainer)
    {
        containerVector.removeElement(icontainer);
    }

    public Boolean getChanged()
    {
        return changed;
    }

    public Long getID()
    {
        return id;
    }

    public Long getLayoutType()
    {
        return layoutType;
    }

    public String getShellDesc()
    {
        return shellDescription;
    }

    public String getShellVersion()
    {
        return version;
    }

    public String getStatus()
    {
        return status;
    }

    public boolean isActive()
    {
        return getStatus().equals(statusCodeActive());
    }

    public Vector listContainer()
    {
        return containerVector;
    }

    public Enumeration listContainerEnumeration()
    {
        return containerVector.elements();
    }

    public void setActive()
    {
        setStatus(statusCodeActive());
        for(Enumeration enumeration = listContainerEnumeration(); enumeration.hasMoreElements();)
        {
            NzContainer nzcontainer = (NzContainer)enumeration.nextElement();
            nzcontainer.setContainerStatus(statusCodeActive());
            NzComponent nzcomponent;
            for(Enumeration enumeration1 = nzcontainer.listComponentEnumeration(); enumeration1.hasMoreElements(); nzcomponent.setComponentStatus(statusCodeActive()))
                nzcomponent = (NzComponent)enumeration1.nextElement();

        }

    }

    public void setChanged(Boolean boolean1)
    {
        changed = boolean1;
    }

    public void setID(Object obj)
    {
        if(obj == null)
            id = null;
        else
        if(obj instanceof String)
            id = new Long((String)obj);
        else
        if(obj instanceof Long)
            id = (Long)obj;
    }

    public void setInactive()
    {
        setStatus(statusCodeInactive());
        setObjState(UbberUI.deleteStateCode());
        for(Enumeration enumeration = listContainerEnumeration(); enumeration.hasMoreElements();)
        {
            NzContainer nzcontainer = (NzContainer)enumeration.nextElement();
            nzcontainer.setContainerStatus(statusCodeInactive());
            nzcontainer.setObjState(UbberUI.deleteStateCode());
            NzComponent nzcomponent;
            for(Enumeration enumeration1 = nzcontainer.listComponentEnumeration(); enumeration1.hasMoreElements(); nzcomponent.setObjState(UbberUI.deleteStateCode()))
            {
                nzcomponent = (NzComponent)enumeration1.nextElement();
                nzcomponent.setComponentStatus(statusCodeInactive());
            }

        }

    }

    public void setLayoutType(Object obj)
    {
        if(obj == null)
            layoutType = null;
        else
        if(obj instanceof String)
            layoutType = new Long((String)obj);
        else
        if(obj instanceof Long)
            layoutType = (Long)obj;
    }

    public void setShellDesc(Object obj)
    {
        shellDescription = (String)obj;
    }

    public void setShellVersion(Object obj)
    {
        version = (String)obj;
    }

    public void setStatus(Object obj)
    {
        status = (String)obj;
    }

    public static String statusCodeActive()
    {
        return "A";
    }

    public static String statusCodeInactive()
    {
        return "I";
    }

    public String toString()
    {
        return "ZeroPort: " + getShellDesc() + " " + getShellVersion() + getStatus();
    }

    public Vector updateVector()
    {
        Vector vector = new Vector();
        vector.addElement(getID());
        vector.addElement(getShellVersion());
        vector.addElement(getStatus());
        vector.addElement(getShellDesc());
        vector.addElement(getLayoutType());
        return vector;
    }

    private Long id;
    private String version;
    private String status;
    private Vector containerVector;
    private String shellDescription;
    private Boolean changed;
    private Long layoutType;
}
