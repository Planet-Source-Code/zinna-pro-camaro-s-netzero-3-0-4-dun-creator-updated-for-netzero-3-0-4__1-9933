// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoolCleaner.java

package pool;

import java.io.File;
import java.util.*;
import nzcom.ZCast;

// Referenced classes of package pool:
//            PoolTypes, PoolAd, HashPool, ZeroOutPool, 
//            ZeroInPool, MarqueePool

public class PoolCleaner
{

    public PoolCleaner()
    {
        mqpool = MarqueePool.getInstance();
        zipool = ZeroInPool.getInstance();
        zopool = ZeroOutPool.getInstance();
    }

    public void addPoolFiles()
    {
        m_poollist = new Vector();
        m_poollist.addElement(((HashPool) (mqpool)).m_sFilename.toLowerCase());
        m_poollist.addElement(((HashPool) (zipool)).m_sFilename.toLowerCase());
        m_poollist.addElement(((HashPool) (zopool)).m_sFilename.toLowerCase());
        PoolAd poolad = zipool.getBumper();
        m_poollist.addElement(poolad.m_sCacheFile.toLowerCase());
        String s = PoolTypes.getDefault(4);
        StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
        PoolAd poolad1 = mqpool.parseRawAd(stringtokenizer, false);
        m_poollist.addElement(poolad1.m_sCacheFile.toLowerCase());
        for(Enumeration enumeration = ((HashPool) (mqpool)).m_adlist.elements(); enumeration.hasMoreElements(); m_poollist.addElement(((PoolAd)enumeration.nextElement()).m_sCacheFile.toLowerCase()));
        for(Enumeration enumeration1 = ((HashPool) (zipool)).m_adlist.elements(); enumeration1.hasMoreElements(); m_poollist.addElement(((PoolAd)enumeration1.nextElement()).m_sCacheFile.toLowerCase()));
        for(Enumeration enumeration2 = ((HashPool) (zopool)).m_adlist.elements(); enumeration2.hasMoreElements(); m_poollist.addElement(((PoolAd)enumeration2.nextElement()).m_sCacheFile.toLowerCase()));
    }

    public boolean removeOrphans()
    {
        if(m_poollist == null)
            addPoolFiles();
        boolean flag = false;
        File file = new File(".\\pool");
        Vector vector = copyToVector(file.list());
        for(int i = 0; i < vector.size(); i++)
        {
            boolean flag1 = true;
            File file1 = null;
            for(Enumeration enumeration = m_poollist.elements(); enumeration.hasMoreElements() && flag1;)
            {
                file1 = new File((String)enumeration.nextElement());
                if(file1.getName().toLowerCase().equals(vector.elementAt(i)))
                {
                    flag1 = false;
                    break;
                }
            }

            if(flag1 && file1 != null)
            {
                File file2 = new File(".\\pool/" + vector.elementAt(i));
                try
                {
                    if(file2 != null && file2.isFile())
                    {
                        ZCast.displayDebug(" Cleaning Pool: >>>> Deleting " + vector.elementAt(i));
                        file2.delete();
                        Thread.yield();
                    }
                }
                catch(Exception _ex)
                {
                    ZCast.displayDebug(" POOL CLEANER: Cannot delete file:" + file2.getName());
                    flag = true;
                }
            }
        }

        return flag;
    }

    public Vector copyToVector(String as[])
    {
        Vector vector = new Vector();
        for(int i = 0; i < as.length; i++)
            vector.addElement(as[i].toLowerCase());

        return vector;
    }

    protected MarqueePool mqpool;
    protected ZeroInPool zipool;
    protected ZeroOutPool zopool;
    protected Vector m_poollist;
}
