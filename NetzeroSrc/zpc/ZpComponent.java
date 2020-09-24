// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpComponent.java

package zpc;

import java.awt.*;
import ui.IComponent;

// Referenced classes of package zpc:
//            ZpContainer

public class ZpComponent extends Component
{

    protected ZpComponent()
    {
        numberStates = 0;
        isHidden = false;
        mouseClickcount = 0;
        mouseOvercount = 0;
    }

    public ZpComponent(ZpContainer zpcontainer)
    {
        numberStates = 0;
        isHidden = false;
        mouseClickcount = 0;
        mouseOvercount = 0;
        owner = zpcontainer;
    }

    public ZpComponent(ZpContainer zpcontainer, IComponent icomponent)
    {
        numberStates = 0;
        isHidden = false;
        mouseClickcount = 0;
        mouseOvercount = 0;
        owner = zpcontainer;
        parent = icomponent;
        if(icomponent.getActionType() != null)
            actionType = icomponent.getActionType().intValue();
        if(icomponent.getActionObjID() != null)
            actionObjectId = icomponent.getActionObjID().longValue();
        if(icomponent.getComponentType() != null)
            componentType = icomponent.getComponentType();
        else
            componentType = "unknown";
        if(icomponent.getGroupID() != null)
            groupId = icomponent.getGroupID().intValue();
        if(icomponent.getHover() != null)
            hoverText = owner.getResourceString(icomponent.getHover());
        if(icomponent.getComponentID() != null)
            componentId = icomponent.getComponentID().longValue();
        if(actionType == 1 || actionType == 4)
        {
            if(actionObjectId != 0L)
                actionString = owner.getResourceString(icomponent);
        } else
        if(actionType == 19)
        {
            Long long1 = icomponent.getResourceID();
            if(long1 != null)
                actionString = owner.getResourceString(long1);
        }
        currentState = 0;
        stateImages = owner.createImages(icomponent);
        if(stateImages != null && stateImages[0] != null)
        {
            setSize(stateImages[0].getWidth(zpcontainer), stateImages[0].getHeight(zpcontainer));
            numberStates = stateImages.length;
        }
        if(icomponent.getDimension() != null)
        {
            setSize(icomponent.getDimension());
            directDraw = true;
            fillColor = Color.black;
        }
        size = getSize();
        if(icomponent.getHideState() != null)
            isHideable = icomponent.getHideState().booleanValue();
    }

    public Object getActionObject()
    {
        return actionObject;
    }

    public long getActionObjectId()
    {
        return actionObjectId;
    }

    public String getActionString()
    {
        return actionString;
    }

    public int getActionType()
    {
        return actionType;
    }

    public long getComponentId()
    {
        return componentId;
    }

    public String getComponentType()
    {
        return componentType;
    }

    public String getDescription()
    {
        return parent.getComponentDesc();
    }

    public Integer getEventId()
    {
        return parent.getEventID();
    }

    public int getGroupId()
    {
        return groupId;
    }

    public String getHoverText()
    {
        return hoverText;
    }

    public IComponent getIComponent()
    {
        return parent;
    }

    public int getMouseClickedCount()
    {
        return mouseClickcount;
    }

    public int getMouseOverCount()
    {
        return mouseOvercount;
    }

    public int getState()
    {
        return currentState;
    }

    public int mouseClickCounter()
    {
        return mouseClickcount++;
    }

    public int mouseOverCounter()
    {
        return mouseOvercount++;
    }

    public void paint(Graphics g)
    {
        if(isHidden)
            return;
        if(!directDraw && numberStates > currentState && stateImages[currentState] != null)
        {
            g.drawImage(stateImages[currentState], getLocation().x, getLocation().y, this);
        } else
        {
            g.setColor(fillColor);
            g.fillRect(getLocation().x, getLocation().y, getSize().width, getSize().height);
        }
    }

    public void setHidden(boolean flag)
    {
        isHidden = flag;
        if(flag)
            setSize(0, 0);
        else
            setSize(size);
    }

    public void setHidden(boolean flag, Integer integer)
    {
        if(isHideable && integer.intValue() == parent.getHideTarget().intValue())
        {
            setHidden(flag);
            if(parent.getComponentDesc().equals("left_side") || parent.getComponentDesc().equals("right_side"))
            {
                if(flag)
                    setState(1);
                else
                    setState(0);
                setHidden(false);
            }
        }
    }

    public void setHoverText(String s)
    {
        hoverText = s;
    }

    public void setState(int i)
    {
        if(!isEnabled())
            return;
        if(i < numberStates)
        {
            currentState = i;
            if(i == 1 && componentType.equals("radio-button"))
            {
                for(int j = 0; j < owner.children.length; j++)
                    if(owner.children[j] != this && owner.children[j].getGroupId() == groupId)
                        owner.children[j].setState(0);

            }
        }
    }

    protected ZpContainer owner;
    protected IComponent parent;
    protected Rectangle loc;
    protected Dimension size;
    protected int currentState;
    protected int initialState;
    protected transient Image stateImages[];
    protected int numberStates;
    protected int actionType;
    protected long actionObjectId;
    protected Object actionObject;
    protected int objectType;
    protected String componentType;
    protected String hoverText;
    protected String actionString;
    protected boolean directDraw;
    protected Color fillColor;
    protected int groupId;
    protected boolean isHidden;
    protected boolean isHideable;
    protected static int cChoice = 0;
    protected long componentId;
    protected int mouseClickcount;
    protected int mouseOvercount;
    public static final int ACTION_SHOWURL = 1;
    public static final int ACTION_SHOWTICKER = 2;
    public static final int ACTION_HIDETICKER = 3;
    public static final int ACTION_CHANGETICKER = 4;
    public static final int ACTION_SPONSORMENU = 5;
    public static final int ACTION_EXIT = 6;
    public static final int ACTION_NEXTAD = 7;
    public static final int ACTION_PREVAD = 8;
    public static final int ACTION_DRAGWINDOW = 9;
    public static final int ACTION_MESSAGE = 10;
    public static final int ACTION_DIALOG = 11;
    public static final int ACTION_UNKNOWN = 12;
    public static final int ACTION_POPUPMENU = 13;
    public static final int ACTION_SLOWDOWNTICKER = 14;
    public static final int ACTION_SPEEDUPTICKER = 15;
    public static final int ACTION_LAUNCHPROGRAM = 16;
    public static final int ACTION_DOCKTOP = 17;
    public static final int ACTION_DOCKBOTTOM = 18;
    public static final int ACTION_URL_AND_POPUP = 19;

}
