// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPList.java

package spe;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package spe:
//            SPListItem, SPItemListener

public class SPList
    implements FilenameFilter
{

    public boolean accept(File file, String s)
    {
        return tempFileInList(String.valueOf(file) + "\\" + s) ^ true;
    }

    public synchronized void add(String s, String s1, String s2, int i, int j, SPItemListener spitemlistener, Object obj)
    {
        m_ListItems.addElement(new SPListItem(s, s1, s2, i, j, spitemlistener, obj));
    }

    public SPListItem getAt(int i)
    {
        return (SPListItem)m_ListItems.elementAt(i);
    }

    public int getCount()
    {
        return m_ListItems.size();
    }

    public synchronized SPListItem getFirstHighestPriority()
    {
        SPListItem splistitem = null;
        for(int i = 0; i < getCount(); i++)
        {
            SPListItem splistitem1 = getAt(i);
            if(splistitem1 != null && (splistitem == null || splistitem1.getPriority() > splistitem.getPriority()))
                splistitem = splistitem1;
        }

        return splistitem;
    }

    public boolean contains(Object obj)
    {
        for(Enumeration enumeration = m_ListItems.elements(); enumeration.hasMoreElements();)
        {
            SPListItem splistitem = (SPListItem)enumeration.nextElement();
            if(splistitem.getIdentifier() == obj)
                return true;
        }

        return false;
    }

    public synchronized boolean removeByIdentifier(Object obj, boolean flag)
    {
        Object obj1 = null;
        for(int i = 0; i < getCount(); i++)
        {
            SPListItem splistitem = getAt(i);
            if(splistitem != null && splistitem.getIdentifier() == obj)
                return removeByItem(splistitem, flag);
        }

        return false;
    }

    public synchronized boolean removeByItem(SPListItem splistitem, boolean flag)
    {
        splistitem.close();
        if(flag)
            splistitem.deleteTempFile();
        return m_ListItems.removeElement(splistitem);
    }

    public synchronized boolean tempFileInList(String s)
    {
        Object obj = null;
        for(int i = 0; i < getCount(); i++)
        {
            SPListItem splistitem = getAt(i);
            if(splistitem != null && splistitem.getTempFileName().equalsIgnoreCase(s))
                return true;
        }

        return false;
    }

    public synchronized Enumeration getEnumeration()
    {
        return m_ListItems.elements();
    }

    public synchronized void setAllModes(int i)
    {
        for(Enumeration enumeration = getEnumeration(); enumeration.hasMoreElements(); ((SPListItem)enumeration.nextElement()).setMode(i));
    }

    public SPList()
    {
        m_ListItems = new Vector();
    }

    private Vector m_ListItems;
}
