// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroOutPlayList.java

package admgmt;

import java.util.Enumeration;
import java.util.Vector;
import nzcom.ZCast;
import pool.ZeroOutPool;
import util.Link;

// Referenced classes of package admgmt:
//            SequentialPlayList

public final class ZeroOutPlayList extends SequentialPlayList
{

    private ZeroOutPlayList(int i, int j)
    {
        super(i, j);
    }

    public static final ZeroOutPlayList getZeroOutPlayList()
    {
        m_meDefault = new ZeroOutPlayList(-1, 0);
        if(!m_meDefault.populatePlayList())
            m_meDefault = null;
        ZCast.displayDebug("ZeroOutPlayList creation returned " + m_meDefault);
        return m_meDefault;
    }

    protected boolean populatePlayList()
    {
        Vector vector = ZeroOutPool.getInstance().getPlaylist();
        Object obj;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); append(obj))
            obj = enumeration.nextElement();

        Link link = m_head.getNext();
        return link != null && link != m_head;
    }

    private static ZeroOutPlayList m_meDefault = null;

}
