// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoolSaver.java

package pool;

import java.io.*;
import java.util.zip.GZIPOutputStream;
import nzcom.ZCast;

// Referenced classes of package pool:
//            HashPool

public class PoolSaver
    implements Serializable, Runnable
{

    public PoolSaver(HashPool hashpool)
    {
        m_objPool = null;
        m_objPool = hashpool;
    }

    public void run()
    {
        Object obj = null;
        try
        {
            if(m_objPool.m_sFilename == null)
                throw new Exception("Invalid Filename!");
            FileOutputStream fileoutputstream = new FileOutputStream(m_objPool.m_sFilename);
            ObjectOutputStream objectoutputstream;
            if(m_objPool.m_bCompress)
            {
                GZIPOutputStream gzipoutputstream = new GZIPOutputStream(fileoutputstream);
                objectoutputstream = new ObjectOutputStream(gzipoutputstream);
            } else
            {
                objectoutputstream = new ObjectOutputStream(fileoutputstream);
            }
            objectoutputstream.writeObject(m_objPool);
            objectoutputstream.flush();
            objectoutputstream.close();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("Pool: " + m_objPool.m_sFilename + " - writing file");
            ZCast.displayDebug(exception);
        }
    }

    HashPool m_objPool;
}
