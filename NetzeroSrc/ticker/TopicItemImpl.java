// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TopicItemImpl.java

package ticker;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package ticker:
//            TopicItem

public class TopicItemImpl
    implements TopicItem, Serializable
{

    public TopicItemImpl(Integer integer, String s, Vector vector, boolean flag)
    {
        m_nItemId = integer;
        m_strItemName = s;
        m_vSubItems = vector;
        m_bHasChildren = flag;
    }

    public TopicItemImpl(Integer integer, String s, boolean flag)
    {
        m_nItemId = integer;
        m_strItemName = s;
        m_vSubItems = null;
        m_bHasChildren = flag;
    }

    public void addSubItem(TopicItem topicitem)
    {
        if(m_vSubItems == null)
            m_vSubItems = new Vector();
        m_vSubItems.addElement(topicitem);
    }

    public void dump(int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int j = 0; j < i; j++)
            stringbuffer.append("\t");

        String s = stringbuffer.toString();
        ZCast.displayDebug(s + "name = " + getItemName());
        ZCast.displayDebug(s + "id = " + getItemId());
        if(m_vSubItems == null)
            return;
        for(Enumeration enumeration = m_vSubItems.elements(); enumeration.hasMoreElements();)
        {
            Object obj = enumeration.nextElement();
            try
            {
                TopicItemImpl topicitemimpl = (TopicItemImpl)obj;
                topicitemimpl.dump(i + 1);
            }
            catch(ClassCastException _ex) { }
        }

    }

    public static String getCodeForIndex(String s)
    {
        if(s == null || s.length() < 1)
            return null;
        for(int i = 0; i < TopicItem.index_names.length; i++)
            if(s.equals(TopicItem.index_names[i]))
                return new String() + TopicItem.index_codes[i];

        return null;
    }

    public static String getIndexForCode(String s)
    {
        if(s == null || s.length() < 1)
            return null;
        for(int i = 0; i < TopicItem.index_codes.length; i++)
            if(s.charAt(0) == TopicItem.index_codes[i])
                return TopicItem.index_names[i];

        return null;
    }

    public String getIndexForSymbol(String s)
    {
        return null;
    }

    public Integer getItemId()
    {
        return m_nItemId;
    }

    public String getItemName()
    {
        return m_strItemName;
    }

    public Vector getSubItems()
    {
        return m_vSubItems;
    }

    public boolean hasChildren()
    {
        return m_bHasChildren;
    }

    public void removeChildren()
    {
        m_vSubItems = null;
    }

    public void setKeyName(String s)
    {
        m_strItemName = s;
    }

    public void setSubItems(Vector vector)
    {
        m_vSubItems = vector;
    }

    private Integer m_nItemId;
    private String m_strItemName;
    private Vector m_vSubItems;
    private boolean m_bHasChildren;
}
