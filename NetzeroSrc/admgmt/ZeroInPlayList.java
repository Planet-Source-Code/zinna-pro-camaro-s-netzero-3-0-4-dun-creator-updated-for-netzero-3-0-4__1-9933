// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroInPlayList.java

package admgmt;

import java.util.Enumeration;
import java.util.Vector;
import nzcom.ZCast;
import pool.ZeroInPool;
import util.Link;

// Referenced classes of package admgmt:
//            SequentialPlayList

public final class ZeroInPlayList extends SequentialPlayList
{

    public static final ZeroInPlayList getZeroInPlayList()
    {
        if(m_meDefault == null)
        {
            m_meDefault = new ZeroInPlayList(-1, 0);
            if(!m_meDefault.populatePlayList())
                m_meDefault = null;
            ZCast.displayDebug("ZeroInPlayList creation returned " + m_meDefault);
        }
        return m_meDefault;
    }

    public static final void clearZeroInPlayList()
    {
        m_meDefault = null;
    }

    protected boolean populatePlayList()
    {
        Vector vector = ZeroInPool.getInstance().getPlaylist();
        Object obj;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); append(obj))
            obj = enumeration.nextElement();

        Link link = m_head.getNext();
        return link != null && link != m_head;
    }

    private ZeroInPlayList(int i, int j)
    {
        super(i, j);
    }

    private static ZeroInPlayList m_meDefault = null;

}
