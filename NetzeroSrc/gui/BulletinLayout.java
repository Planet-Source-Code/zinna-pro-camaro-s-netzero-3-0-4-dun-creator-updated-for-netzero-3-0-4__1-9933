// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BulletinLayout.java

package gui;

import java.awt.*;
import java.util.Hashtable;

public class BulletinLayout
    implements LayoutManager
{

    private void addComponentToHashtable(Component component)
    {
        Insets insets = component.getParent().getInsets();
        Point point = component.getLocation();
        component.setLocation(point.x + insets.left, point.y + insets.top);
        hash.put(component, component.getLocation());
    }

    public void addLayoutComponent(String s, Component component)
    {
    }

    private Point getComponentLocation(Component component)
    {
        Insets insets = component.getParent().getInsets();
        Point point = component.getLocation();
        if(!hash.containsKey(component))
        {
            addComponentToHashtable(component);
        } else
        {
            Point point1 = (Point)hash.get(component);
            if(point1.x != point.x || point1.y != point.y)
                addComponentToHashtable(component);
        }
        return component.getLocation();
    }

    public void layoutContainer(Container container)
    {
        Insets insets = container.getInsets();
        int i = container.getComponentCount();
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            if(component.isVisible())
            {
                Dimension dimension = component.getSize();
                Dimension dimension1 = component.getPreferredSize();
                Point point = getComponentLocation(component);
                if(dimension.width < dimension1.width || dimension.height < dimension1.height)
                    dimension = dimension1;
                component.setBounds(point.x, point.y, dimension.width, dimension.height);
            }
        }

    }

    public Dimension minimumLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        Dimension dimension = new Dimension(0, 0);
        int i = container.getComponentCount();
        Rectangle rectangle = new Rectangle(0, 0);
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getMinimumSize();
                Rectangle rectangle1 = new Rectangle(component.getLocation());
                rectangle1.setSize(dimension1.width, dimension1.height);
                rectangle = rectangle.union(rectangle1);
            }
        }

        dimension.width += rectangle.width + insets.right;
        dimension.height += rectangle.height + insets.bottom;
        return dimension;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        Dimension dimension = new Dimension(0, 0);
        int i = container.getComponentCount();
        Rectangle rectangle = new Rectangle(0, 0);
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getSize();
                Rectangle rectangle1 = new Rectangle(component.getLocation());
                rectangle1.width = dimension1.width;
                rectangle1.height = dimension1.height;
                rectangle = rectangle.union(rectangle1);
            }
        }

        dimension.width += rectangle.width + insets.right;
        dimension.height += rectangle.height + insets.bottom;
        return dimension;
    }

    public void removeLayoutComponent(Component component)
    {
    }

    public BulletinLayout()
    {
        hash = new Hashtable();
    }

    private Hashtable hash;
}
