// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShellUIWrapper.java

package ui;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package ui:
//            ContainerLayout, IShellUI

public class ShellUIWrapper
    implements Serializable
{

    public ShellUIWrapper(IShellUI ishellui, Vector vector)
    {
        setShellUI(ishellui);
        setAllShellLayout(vector);
    }

    public Vector getAllShellLayout()
    {
        return shellLayout;
    }

    public Vector getShellLayout()
    {
        Vector vector = null;
        if(getShellUI() != null)
        {
            Long long1 = getShellUI().getLayoutType();
            vector = getShellLayout(long1);
        }
        return vector;
    }

    public Vector getShellLayout(Long long1)
    {
        Vector vector = new Vector();
        if(getShellUI() != null)
        {
            for(int i = 0; i < getAllShellLayout().size(); i++)
            {
                ContainerLayout containerlayout = (ContainerLayout)getAllShellLayout().elementAt(i);
                if(long1.equals(containerlayout.getLayoutID()))
                    vector.addElement(containerlayout);
            }

        }
        return vector;
    }

    public IShellUI getShellUI()
    {
        return shellUI;
    }

    public void setAllShellLayout(Vector vector)
    {
        shellLayout = vector;
    }

    public void setInactive()
    {
        getShellUI().setInactive();
        for(int i = 0; i < getAllShellLayout().size(); i++)
            ((ContainerLayout)getAllShellLayout().elementAt(i)).setInactive();

    }

    public void setShellUI(IShellUI ishellui)
    {
        shellUI = ishellui;
    }

    public String toString()
    {
        return getShellUI().toString();
    }

    private Vector shellLayout;
    private IShellUI shellUI;
}
