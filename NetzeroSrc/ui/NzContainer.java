// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NzContainer.java

package ui;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package ui:
//            UIContainer, NzComponent, IContainer, IComponent

public class NzContainer extends UIContainer
    implements IContainer, Serializable, Cloneable
{

    public NzContainer()
    {
        componentVector = new Vector();
        hideState = null;
        hideAll = null;
        orientation = null;
        x_fills = null;
        y_fills = null;
        rendered = null;
    }

    public NzContainer(Vector vector)
    {
        super(vector);
        componentVector = new Vector();
        setXFills(vector.elementAt(6));
        setHideState(vector.elementAt(7));
        setHideAll(vector.elementAt(8));
        setOrientation(vector.elementAt(9));
        setRendered(vector.elementAt(10));
        setYFills(vector.elementAt(11));
    }

    public void addComponent(IComponent icomponent)
    {
        componentVector.addElement(icomponent);
    }

    public NzContainer copy()
    {
        try
        {
            NzContainer nzcontainer = (NzContainer)clone();
            nzcontainer.componentVector = new Vector();
            return nzcontainer;
        }
        catch(CloneNotSupportedException _ex)
        {
            return null;
        }
    }

    public Integer countComponent()
    {
        return new Integer(componentVector.size());
    }

    public NzContainer deepCopy()
    {
        try
        {
            NzContainer nzcontainer = (NzContainer)clone();
            nzcontainer.componentVector = new Vector();
            Vector vector = listComponent();
            for(int i = 0; i < vector.size(); i++)
            {
                NzComponent nzcomponent = (NzComponent)vector.elementAt(i);
                nzcontainer.addComponent(nzcomponent.copy());
            }

            return nzcontainer;
        }
        catch(CloneNotSupportedException _ex)
        {
            return null;
        }
    }

    public void deleteComponent(IComponent icomponent)
    {
        componentVector.removeElement(icomponent);
    }

    public Boolean getHideAll()
    {
        return hideAll;
    }

    public Boolean getHideState()
    {
        return hideState;
    }

    public Integer getOrientation()
    {
        return orientation;
    }

    public Boolean getRendered()
    {
        return rendered;
    }

    public Boolean getXFills()
    {
        return x_fills;
    }

    public Boolean getYFills()
    {
        return y_fills;
    }

    public Vector listComponent()
    {
        return componentVector;
    }

    public Enumeration listComponentEnumeration()
    {
        return componentVector.elements();
    }

    public void setHideAll(Object obj)
    {
        if(obj == null)
            hideAll = null;
        else
        if(obj instanceof String)
            hideAll = new Boolean(((String)obj).equals("1"));
        else
        if(obj instanceof Boolean)
            hideAll = (Boolean)obj;
    }

    public void setHideState(Object obj)
    {
        if(obj == null)
            hideState = null;
        else
        if(obj instanceof String)
            hideState = new Boolean(((String)obj).equals("1"));
        else
        if(obj instanceof Boolean)
            hideState = (Boolean)obj;
    }

    public void setOrientation(Object obj)
    {
        if(obj == null)
            orientation = null;
        else
        if(obj instanceof String)
            orientation = new Integer((String)obj);
        else
        if(obj instanceof Integer)
            orientation = (Integer)obj;
    }

    public void setRendered(Object obj)
    {
        if(obj != null)
            if(obj instanceof String)
                rendered = new Boolean(((String)obj).equals("1"));
            else
            if(obj instanceof Boolean)
                rendered = (Boolean)obj;
    }

    public void setXFills(Object obj)
    {
        if(obj == null)
            x_fills = null;
        else
        if(obj instanceof String)
            x_fills = new Boolean(((String)obj).equals("1"));
        else
        if(obj instanceof Boolean)
            x_fills = (Boolean)obj;
    }

    public void setYFills(Object obj)
    {
        if(obj == null)
            y_fills = null;
        else
        if(obj instanceof String)
            y_fills = new Boolean(((String)obj).equals("1"));
        else
        if(obj instanceof Boolean)
            y_fills = (Boolean)obj;
    }

    public String toString()
    {
        if(getRendered().booleanValue())
            return "Container: " + getContainerDesc();
        else
            return "Sponsor Menu: " + getContainerDesc();
    }

    public Vector updateVector()
    {
        Vector vector = new Vector();
        vector.addElement(getContainerID());
        vector.addElement(getContainerVersion());
        vector.addElement(getContainerDesc());
        vector.addElement(getContainerStatus());
        vector.addElement(getContainerOrder());
        vector.addElement(getShellID());
        vector.addElement(getXFills());
        vector.addElement(getHideState());
        vector.addElement(getHideAll());
        vector.addElement(getOrientation());
        vector.addElement(getRendered());
        vector.addElement(getYFills());
        return vector;
    }

    private Boolean hideState;
    private Boolean hideAll;
    private Integer orientation;
    private Boolean x_fills;
    private Boolean y_fills;
    private Vector componentVector;
    private Boolean rendered;
}
