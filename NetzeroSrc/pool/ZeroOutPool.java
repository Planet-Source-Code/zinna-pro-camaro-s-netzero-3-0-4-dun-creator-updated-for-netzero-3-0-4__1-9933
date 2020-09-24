// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroOutPool.java

package pool;

import java.io.Serializable;
import java.util.Vector;
import spe.SPItemListener;

// Referenced classes of package pool:
//            HashPool

public class ZeroOutPool extends HashPool
    implements Serializable, SPItemListener
{

    protected ZeroOutPool()
    {
        m_sFilename = ".\\pool\\zeroout.nzp";
        m_adcapacity = 6;
        m_iListType = 3;
        load();
        checkClientCaps();
    }

    public static synchronized ZeroOutPool getInstance()
    {
        if(m_Instance == null)
            m_Instance = new ZeroOutPool();
        return m_Instance;
    }

    public Vector getPlaylist()
    {
        Vector vector = new Vector(1);
        PoolAd poolad = null;
        Object obj = null;
        if(getPlayableAdCount() == 0)
            resetPlayable();
        poolad = getNextPlayableAd(true);
        appendPlaylist(poolad, vector);
        return vector;
    }

    private static transient ZeroOutPool m_Instance = null;
    private static final String POOL_FILE = "zeroout.nzp";

}
