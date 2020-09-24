// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarqueePool.java

package pool;

import admgmt.*;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import nzcom.ZCast;
import spe.SPEngine;
import spe.SPItemListener;

// Referenced classes of package pool:
//            HashPool, UserList, AdDisplayTypes, PoolTypes, 
//            PoolAd

public class MarqueePool extends HashPool
    implements Serializable, SPItemListener
{

    protected MarqueePool()
    {
        m_dteLastPlayed = null;
        m_iListType = 4;
        m_adcapacity = 3;
        m_sFilename = ".\\pool\\" + POOL_FILE;
        load();
        checkClientCaps();
    }

    public PoolAd getDefaultAd()
    {
        PoolAd poolad = super.getDefaultAd();
        if(poolad != null)
        {
            File file = new File(poolad.getCacheFile());
            if(!file.exists() && !SPEngine.getInstance().exists(poolad))
            {
                SPEngine.getInstance().add(poolad.m_sImageUrl, poolad.m_sCacheFile, 6, 4, this, poolad);
                ZCast.displayDebug("Pool", "<><><> POOL: Requesting default ad: " + poolad.getAdId() + " hash " + poolad);
            }
            return poolad;
        }
        String s = PoolTypes.getDefault(m_iListType);
        StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
        poolad = parseRawAd(stringtokenizer, false);
        poolad.m_sCacheFile = ".\\pool\\AD" + poolad.getAdId() + ".GIF";
        poolad.setAdId("DEFAD");
        File file1 = new File(poolad.getCacheFile());
        if(!file1.exists())
        {
            poolad.isDownloading = true;
            if(!SPEngine.getInstance().exists(poolad))
            {
                SPEngine.getInstance().add(poolad.m_sImageUrl, poolad.m_sCacheFile, 6, 4, this, poolad);
                ZCast.displayDebug("Pool", "<><><> POOL: Requesting default ad (first time?): " + poolad.getAdId() + " hash " + poolad);
            }
            save();
        }
        return poolad;
    }

    public static synchronized MarqueePool getInstance()
    {
        if(m_Instance == null)
            m_Instance = new MarqueePool();
        return m_Instance;
    }

    public Vector getPlaylist()
    {
        Vector vector = new Vector(3);
        Object obj = null;
        Object obj1 = null;
        resetPlayable();
        for(int i = 0; i < 3; i++)
        {
            PoolAd poolad;
            if(getPlayableAdCount() == 0)
                poolad = null;
            else
                poolad = getNextPlayableAd(false);
            Vector vector1;
            if(poolad != null)
            {
                vector1 = poolad.toVector();
            } else
            {
                poolad = getDefaultAd();
                if(poolad == null)
                    return vector;
                vector1 = poolad.toVector();
            }
            switch(poolad.m_iDisplayType)
            {
            case 2: // '\002'
                vector.addElement(new VideoAd(vector1));
                break;

            case 6: // '\006'
                vector.addElement(new RichMediaAd(vector1));
                break;

            case 4: // '\004'
                vector.addElement(new RichMediaAd(vector1));
                break;

            case 3: // '\003'
            case 5: // '\005'
            default:
                vector.addElement(new ImageAd(vector1));
                break;
            }
        }

        save();
        return vector;
    }

    public synchronized void load()
    {
        super.load();
    }

    public synchronized void processTrans(String s)
    {
        Object obj = null;
        Object obj1 = null;
        try
        {
            if(s == null || s.equals("NONE"))
                return;
            StringTokenizer stringtokenizer = new StringTokenizer(s, "\n");
            for(int i = 0; stringtokenizer.hasMoreTokens() && i < 3; i++)
            {
                String s1 = stringtokenizer.nextToken();
                StringTokenizer stringtokenizer1 = new StringTokenizer(s1, ";");
                PoolAd poolad = parseRawAd(stringtokenizer1, false);
                if(poolad != null)
                    if(poolad.m_iAdType == m_iListType && isCurrentAd(poolad))
                    {
                        File file = new File(poolad.getCacheFile());
                        if(file.exists())
                            poolad.setDownloading(false);
                        else
                            poolad.setDownloading(true);
                        addElement(poolad);
                        addUserAd(m_sCurUser, poolad.getAdId(), i);
                        m_bDirty = true;
                    } else
                    {
                        ZCast.displayDebug("Pool", " Marquee Pool - Invalid ad: " + poolad.m_sAdIdString);
                        if(!isCurrentAd(poolad))
                            ZCast.displayDebug("Pool", " Date is not valid!!\n");
                    }
            }

            if(stringtokenizer.hasMoreTokens())
            {
                String s2 = stringtokenizer.nextToken();
                StringTokenizer stringtokenizer2 = new StringTokenizer(s2, ";");
                PoolAd poolad1 = parseRawAd(stringtokenizer2, false);
                if(poolad1 != null)
                {
                    poolad1.m_sCacheFile = ".\\pool\\AD" + poolad1.getAdId() + "." + AdDisplayTypes.getExtention(poolad1.m_iDisplayType);
                    poolad1.setAdId("DEFAD");
                    File file1 = new File(poolad1.getCacheFile());
                    if(file1.exists())
                        poolad1.setDownloading(false);
                    else
                        poolad1.setDownloading(true);
                    poolad1.isDefault = true;
                    m_adlist.put(poolad1.getAdId(), poolad1);
                    m_bDirty = true;
                }
            }
        }
        catch(Exception _ex)
        {
            return;
        }
        updateSPE();
        removeUnusedAds();
        save();
    }

    public synchronized void save()
    {
        super.save();
    }

    protected void addElement(PoolAd poolad)
    {
        boolean flag = false;
        if(m_adlist != null)
        {
            if(m_adlist.containsKey(poolad.getAdId()))
            {
                PoolAd poolad1 = (PoolAd)m_adlist.get(poolad.getAdId());
                poolad1.copy(poolad);
            } else
            {
                m_adlist.put(poolad.getAdId(), poolad);
            }
        } else
        {
            createDefault();
            m_adlist.put(poolad.getAdId(), poolad);
        }
        m_bDirty = true;
        save();
    }

    protected void addUserAd(String s, String s1, int i)
    {
        if(m_userads == null)
        {
            UserList userlist = new UserList(s);
            userlist.addAdId(s1, i);
            m_userads.put(s, userlist);
            return;
        }
        if(m_userads.containsKey(s))
        {
            UserList userlist1 = (UserList)m_userads.get(s);
            userlist1.addAdId(s1, i);
        } else
        {
            UserList userlist2 = new UserList(s);
            userlist2.addAdId(s1, i);
            m_userads.put(s, userlist2);
        }
    }

    private static transient MarqueePool m_Instance = null;
    private transient Date m_dteLastPlayed;
    private static String POOL_FILE = "marquee.nzp";

}
