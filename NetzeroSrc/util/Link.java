// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Link.java

package util;


public class Link
{

    public Link(Object obj)
    {
        m_prev = m_next = null;
        m_theThing = obj;
        m_id = -1;
    }

    public Link getNext()
    {
        return m_next;
    }

    public Link getPrev()
    {
        return m_prev;
    }

    public Object getObject()
    {
        return m_theThing;
    }

    public void setNext(Link link)
    {
        m_next = link;
    }

    public void setPrev(Link link)
    {
        m_prev = link;
    }

    public void cleanUp()
    {
        m_prev = null;
        m_next = null;
        m_theThing = null;
        m_id = -2;
    }

    public int getId()
    {
        return m_id;
    }

    public void setId(int i)
    {
        m_id = i;
    }

    private Link m_prev;
    private Link m_next;
    private Object m_theThing;
    private int m_id;
}
