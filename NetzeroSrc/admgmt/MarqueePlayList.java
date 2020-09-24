// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarqueePlayList.java

package admgmt;

import java.util.Enumeration;
import java.util.Vector;
import nzcom.ServerParms;
import nzcom.ZCast;
import pool.MarqueePool;
import util.Link;

// Referenced classes of package admgmt:
//            SequentialPlayList

public final class MarqueePlayList extends SequentialPlayList
{

    private MarqueePlayList(int i, int j)
    {
        super(i, j);
    }

    public static final MarqueePlayList getMarqueePlayList()
    {
        if(m_meDefault == null)
        {
            int i = Integer.parseInt(ServerParms.getParm("StartMultiPlayListFetch", "5"));
            int j = Integer.parseInt(ServerParms.getParm("ClickBackMax", "5"));
            m_meDefault = new MarqueePlayList(i, j);
            if(!m_meDefault.populatePlayList())
                m_meDefault = null;
            ZCast.displayDebug("MarqueePlayList creation returned " + m_meDefault);
        }
        ZCast.displayDebug("Getting Marquee playlist " + m_meDefault);
        return m_meDefault;
    }

    protected boolean populatePlayList()
    {
        Vector vector = MarqueePool.getInstance().getPlaylist();
        Object obj;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); append(obj))
            obj = enumeration.nextElement();

        m_multiplePlayListTrigger = m_head.getNext();
        Link link = m_head.getNext();
        return link != null && link != m_head;
    }

    private static MarqueePlayList m_meDefault = null;

}
