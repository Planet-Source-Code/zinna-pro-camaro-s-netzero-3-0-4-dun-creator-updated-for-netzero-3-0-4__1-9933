// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserList.java

package pool;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package pool:
//            UserAd

public class UserList
    implements Serializable
{

    public UserList()
    {
        m_sUserId = null;
        m_AdIds = null;
    }

    public UserList(String s)
    {
        m_sUserId = null;
        m_AdIds = null;
        m_sUserId = s;
        m_AdIds = new Vector();
    }

    public void addAdId(String s)
    {
        for(Enumeration enumeration = m_AdIds.elements(); enumeration.hasMoreElements();)
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad.getAdId() == s)
                return;
        }

        UserAd userad1 = new UserAd(m_sUserId, s);
        m_AdIds.addElement(userad1);
    }

    public void addAdId(String s, int i)
    {
        UserAd userad = new UserAd(m_sUserId, s);
        if(m_AdIds.size() > i && m_AdIds.size() != 0)
        {
            m_AdIds.setElementAt(userad, i);
        } else
        {
            for(int j = m_AdIds.size(); j < i; j++)
                m_AdIds.addElement(null);

            m_AdIds.addElement(userad);
        }
    }

    public Enumeration elements()
    {
        return m_AdIds.elements();
    }

    public int getCount()
    {
        return m_AdIds.size();
    }

    public UserAd getNextPlayableAd(boolean flag)
    {
        UserAd userad = null;
        if(m_AdIds.size() == 0)
            return null;
        if(flag)
        {
            Vector vector = (Vector)m_AdIds.clone();
            Random random = new Random();
            do
            {
                int i = (int)(random.nextFloat() * (float)vector.size());
                userad = (UserAd)vector.elementAt(i);
                if(userad != null && userad.isPlayable)
                    break;
                if(vector.size() != 1)
                {
                    vector.removeElementAt(i);
                    continue;
                }
                if(!userad.isPlayable)
                    return null;
                break;
            } while(vector.size() != 0 && !userad.isPlayable);
            userad.isPlayable = false;
            return userad;
        }
        for(Enumeration enumeration = m_AdIds.elements(); enumeration.hasMoreElements();)
        {
            userad = (UserAd)enumeration.nextElement();
            if(userad != null && userad.isPlayable)
                break;
        }

        if(userad == null)
            return null;
        if(!userad.isPlayable)
        {
            return null;
        } else
        {
            userad.isPlayable = false;
            return userad;
        }
    }

    public String getUserId()
    {
        return m_sUserId;
    }

    public boolean hasAd(String s)
    {
        if(m_AdIds == null)
            return false;
        Enumeration enumeration = m_AdIds.elements();
        Object obj = null;
        while(enumeration.hasMoreElements()) 
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad != null && userad.getAdId().equals(s))
                return true;
        }
        return false;
    }

    public void removeAdId(String s)
    {
        if(hasAd(s))
        {
            for(int i = 0; i < m_AdIds.size(); i++)
            {
                UserAd userad = (UserAd)m_AdIds.elementAt(i);
                if(userad != null && s.equals(userad.getAdId()))
                    m_AdIds.removeElementAt(i);
            }

        }
    }

    public void resetPlayable()
    {
        for(Enumeration enumeration = elements(); enumeration.hasMoreElements();)
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad != null)
                userad.setPlayable(true);
        }

    }

    public void setPlayable(String s)
    {
        if(s == null)
            return;
        Enumeration enumeration = m_AdIds.elements();
        Object obj = null;
        while(enumeration.hasMoreElements()) 
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad != null && userad.getAdId() == s)
                userad.setPlayable(true);
        }
    }

    public void setPlayable(String s, boolean flag)
    {
        if(s == null)
            return;
        Enumeration enumeration = m_AdIds.elements();
        Object obj = null;
        while(enumeration.hasMoreElements()) 
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad != null && userad.getAdId() == s)
                userad.setPlayable(flag);
        }
    }

    private String m_sUserId;
    private Vector m_AdIds;
}
