// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NzComponent.java

package ui;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Vector;
import util.Formatter;

// Referenced classes of package ui:
//            UIComponent, Image, IComponent

public class NzComponent extends UIComponent
    implements IComponent, Serializable, Cloneable
{

    public NzComponent()
    {
        images = new Vector();
        hover = null;
        actionType = null;
        eventID = null;
        actionObjectID = null;
        groupID = null;
        x_axis = null;
        y_axis = null;
        mouseCursor = null;
        hideSource = null;
        hideTarget = null;
        hideState = null;
        status = null;
    }

    public NzComponent(Vector vector)
    {
        super(vector);
        setActionType(vector.elementAt(7));
        setEventID(vector.elementAt(8));
        setGroupID(vector.elementAt(9));
        setHideSource(vector.elementAt(10));
        setHideState(vector.elementAt(11));
        setHideTarget(vector.elementAt(12));
        setHover(vector.elementAt(13));
        setMouseCursor(vector.elementAt(14));
        setImages(createImages(vector.elementAt(15), vector.elementAt(16)));
        setActionObjID(vector.elementAt(17));
        setXAxis(vector.elementAt(18));
        setYAxis(vector.elementAt(19));
        setComponentStatus(vector.elementAt(20));
        setResourceID(vector.elementAt(21));
        setFeedID(vector.elementAt(22));
        setSponsorID(vector.elementAt(23));
        setWidth(vector.elementAt(24));
        setHeight(vector.elementAt(25));
    }

    public NzComponent copy()
    {
        try
        {
            NzComponent nzcomponent = (NzComponent)clone();
            if(getImages() != null)
                nzcomponent.setImages(getImages().clone());
            return nzcomponent;
        }
        catch(CloneNotSupportedException _ex)
        {
            return null;
        }
    }

    private Vector createImages(Object obj, Object obj1)
    {
        Vector vector = new Vector();
        if(obj != null && obj1 != null)
        {
            Vector vector1 = Formatter.formatRecords((String)obj);
            Vector vector2 = Formatter.formatRecords((String)obj1);
            for(int i = 0; i < vector1.size(); i++)
                vector.addElement(new Image(vector1.elementAt(i), vector2.elementAt(i)));

        }
        return vector;
    }

    public Long getActionObjID()
    {
        return actionObjectID;
    }

    public Integer getActionType()
    {
        return actionType;
    }

    public String getComponentStatus()
    {
        return status;
    }

    public Dimension getDimension()
    {
        if(getWidth() != null && getHeight() != null)
            return new Dimension(getWidth().intValue(), getHeight().intValue());
        else
            return null;
    }

    public Integer getEventID()
    {
        return eventID;
    }

    public Long getFeedID()
    {
        return feedID;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public Integer getHeight()
    {
        return height;
    }

    public Integer getHideSource()
    {
        return hideSource;
    }

    public Boolean getHideState()
    {
        return hideState;
    }

    public Integer getHideTarget()
    {
        return hideTarget;
    }

    public Long getHover()
    {
        return hover;
    }

    public String getImageIDString()
    {
        String s = "";
        if(getImages() == null)
            return s;
        for(int i = 0; i < getImages().size(); i++)
        {
            Image image = (Image)getImages().elementAt(i);
            s = s + image.getImageID().toString() + ";";
        }

        return s;
    }

    public Vector getImages()
    {
        return images;
    }

    public String getImageStateString()
    {
        String s = "";
        if(getImages() == null)
            return s;
        for(int i = 0; i < getImages().size(); i++)
        {
            Image image = (Image)getImages().elementAt(i);
            s = s + image.getComponentState().toString() + ";";
        }

        return s;
    }

    public Integer getMouseCursor()
    {
        return mouseCursor;
    }

    public Long getResourceID()
    {
        return resourceID;
    }

    public Long getSponsorID()
    {
        return sponsorID;
    }

    public Integer getWidth()
    {
        return width;
    }

    public Integer getXAxis()
    {
        return x_axis;
    }

    public Integer getYAxis()
    {
        return y_axis;
    }

    public boolean hasMenu()
    {
        return getActionType().toString().equals("5") || getActionType().toString().equals("13");
    }

    public void setActionObjID(Object obj)
    {
        actionObjectID = (Long)obj;
    }

    public void setActionType(Object obj)
    {
        if(obj == null)
            actionType = new Integer("0");
        else
            actionType = (Integer)obj;
    }

    public void setComponentStatus(Object obj)
    {
        status = (String)obj;
    }

    public void setDimension(Object obj)
    {
        if(obj instanceof Dimension)
            dimension = (Dimension)obj;
    }

    public void setEventID(Object obj)
    {
        eventID = (Integer)obj;
    }

    public void setFeedID(Object obj)
    {
        feedID = (Long)obj;
    }

    public void setGroupID(Object obj)
    {
        groupID = (Integer)obj;
    }

    public void setHeight(Object obj)
    {
        height = (Integer)obj;
    }

    public void setHideSource(Object obj)
    {
        hideSource = (Integer)obj;
    }

    public void setHideState(Object obj)
    {
        hideState = (Boolean)obj;
    }

    public void setHideTarget(Object obj)
    {
        hideTarget = (Integer)obj;
    }

    public void setHover(Object obj)
    {
        hover = (Long)obj;
    }

    public void setImages(Object obj)
    {
        images = (Vector)obj;
    }

    public void setMouseCursor(Object obj)
    {
        mouseCursor = (Integer)obj;
    }

    public void setResourceID(Object obj)
    {
        resourceID = (Long)obj;
    }

    public void setSponsorID(Object obj)
    {
        sponsorID = (Long)obj;
    }

    public void setWidth(Object obj)
    {
        width = (Integer)obj;
    }

    public void setXAxis(Object obj)
    {
        x_axis = (Integer)obj;
    }

    public void setYAxis(Object obj)
    {
        y_axis = (Integer)obj;
    }

    public Vector updateVector()
    {
        Vector vector = new Vector();
        vector.addElement(getComponentID());
        vector.addElement(getContainerID());
        vector.addElement(getComponentVersion());
        vector.addElement(getComponentDesc());
        vector.addElement(getComponentState());
        vector.addElement(getComponentOrder());
        vector.addElement(getComponentType());
        vector.addElement(getActionType());
        vector.addElement(getEventID());
        vector.addElement(getGroupID());
        vector.addElement(getHideSource());
        vector.addElement(getHideState());
        vector.addElement(getHideTarget());
        vector.addElement(getHover());
        vector.addElement(getMouseCursor());
        vector.addElement(getImageIDString());
        vector.addElement(getImageStateString());
        vector.addElement(getActionObjID());
        vector.addElement(getXAxis());
        vector.addElement(getYAxis());
        vector.addElement(getComponentStatus());
        vector.addElement(getResourceID());
        vector.addElement(getFeedID());
        vector.addElement(getSponsorID());
        vector.addElement(getWidth());
        vector.addElement(getHeight());
        return vector;
    }

    private Vector images;
    private Long hover;
    private Integer actionType;
    private Integer eventID;
    private Long actionObjectID;
    private Integer groupID;
    private Integer x_axis;
    private Integer y_axis;
    private Integer mouseCursor;
    private Integer hideSource;
    private Integer hideTarget;
    private Boolean hideState;
    private String status;
    private Long sponsorID;
    private Long resourceID;
    private Long feedID;
    private Dimension dimension;
    private Integer width;
    private Integer height;
}
