// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TopicsInformationImpl.java

package ticker;

import java.io.Serializable;
import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package ticker:
//            TopicItemImpl, TopicsInformation, TopicItem

public class TopicsInformationImpl
    implements TopicsInformation, Serializable
{

    public TopicsInformationImpl(TopicItem topicitem, Vector vector)
    {
        header = null;
        m_selection = null;
        upperLimit = null;
        searchStatus = new Integer(-1);
        columnLabels = null;
        topicId = null;
        header = topicitem;
        m_selection = vector;
    }

    public TopicsInformationImpl(TopicItem topicitem, Vector vector, int i)
    {
        header = null;
        m_selection = null;
        upperLimit = null;
        searchStatus = new Integer(-1);
        columnLabels = null;
        topicId = null;
        header = topicitem;
        m_selection = vector;
        upperLimit = new Integer(i);
    }

    public void addElement(TopicItem topicitem)
    {
        if(m_selection == null)
            m_selection = new Vector();
        m_selection.addElement(topicitem);
    }

    public void dump()
    {
        ZCast.displayDebug("*** header ***");
        ((TopicItemImpl)getHeader()).dump(1);
        ZCast.displayDebug("*** selected items ***");
        Vector vector = getSelection();
        if(vector == null)
            return;
        for(int i = 0; i < vector.size(); i++)
            ((TopicItemImpl)vector.elementAt(i)).dump(1);

    }

    public String[] getColumnLabels()
    {
        return columnLabels;
    }

    public TopicItem getHeader()
    {
        return header;
    }

    public int getSearchStatus()
    {
        return searchStatus != null ? searchStatus.intValue() : -1;
    }

    public Vector getSelection()
    {
        return m_selection;
    }

    public int getTopicId()
    {
        try
        {
            return topicId.intValue();
        }
        catch(Exception _ex)
        {
            return -1;
        }
    }

    public Integer getUpperLimit()
    {
        return upperLimit;
    }

    public void setColumnLabels(String as[])
    {
        columnLabels = as;
    }

    public void setSearchStatus(int i)
    {
        searchStatus = new Integer(i);
    }

    public void setSelection2Null()
    {
        m_selection = null;
    }

    public void setTopicId(int i)
    {
        if(i < 0 || i >= 4)
        {
            ZCast.displayDebug("warning: invalid topic id");
            i = -1;
        }
        topicId = new Integer(i);
    }

    public void setUpperLimit(int i)
    {
        upperLimit = new Integer(i);
    }

    private TopicItem header;
    private Vector m_selection;
    private Integer upperLimit;
    private Integer searchStatus;
    private String columnLabels[];
    private Integer topicId;
}
