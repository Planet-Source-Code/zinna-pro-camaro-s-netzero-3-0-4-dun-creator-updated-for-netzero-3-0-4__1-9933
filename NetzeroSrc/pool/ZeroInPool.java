// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroInPool.java

package pool;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import nzcom.ZCast;
import spe.SPEngine;
import spe.SPItemListener;

// Referenced classes of package pool:
//            HashPool, UserAd, UserList, PoolAd, 
//            PoolTypes

public class ZeroInPool extends HashPool
    implements Serializable, SPItemListener
{

    protected ZeroInPool()
    {
        m_dteLastPlayed = null;
        m_sFilename = ".\\pool\\" + POOL_FILE;
        m_iListType = 2;
        m_adcapacity = 10;
        load();
        checkClientCaps();
    }

    protected synchronized void createDefault()
    {
        m_sFilename = ".\\pool\\" + POOL_FILE;
        ZCast.displayDebug("Pool", "<><> Creating " + m_sFilename);
        super.createDefault();
        StringTokenizer stringtokenizer = new StringTokenizer(PoolTypes.getBumper(m_iListType), ";");
        PoolAd poolad = parseRawAd(stringtokenizer, false);
        poolad.m_sCacheFile = ".\\pool/AD" + poolad.getAdId() + ".GIF";
        poolad.setDownloading(false);
        poolad.isDefault = true;
        m_adlist.put(poolad.getAdId(), poolad);
        m_bDirty = true;
        save();
    }

    public PoolAd getBumper()
    {
        StringTokenizer stringtokenizer = new StringTokenizer(PoolTypes.getBumper(m_iListType), ";");
        PoolAd poolad = parseRawAd(stringtokenizer, false);
        PoolAd poolad1 = (PoolAd)m_adlist.get(poolad.getAdId());
        if(poolad1 == null)
            poolad1 = poolad;
        File file = new File(poolad.getCacheFile());
        if(!file.exists())
        {
            ZCast.displayDebug(" NO BUMPER ON DISK!");
            if(!SPEngine.getInstance().exists(poolad1))
            {
                SPEngine.getInstance().add(poolad.m_sImageUrl, poolad.m_sCacheFile, 6, 4, this, poolad1);
                ZCast.displayDebug("<><> Pool: Bumper not found - requesting - " + poolad1.getAdId() + " from SPE.");
            }
        }
        return poolad;
    }

    public static synchronized ZeroInPool getInstance()
    {
        if(m_Instance == null)
            m_Instance = new ZeroInPool();
        return m_Instance;
    }

    public Vector getPlaylist()
    {
        Vector vector = new Vector(3);
        PoolAd poolad = null;
        Object obj = null;
        poolad = getBumper();
        if(poolad != null)
            appendPlaylist(poolad, vector);
        if(m_dteLastPlayed != null && (new Date()).getTime() < m_dteLastPlayed.getTime() + 60L)
        {
            ZCast.displayDebug("Pool", " ZEROIN: Played in 1 min Ad drop!!");
            ZCast.displayDebug("Pool", " ZEROIN: ... showing default Ad only");
        } else
        {
            if(getPlayableAdCount() == 0)
                resetPlayable();
            poolad = getNextPlayableAd(true);
            if(poolad != null)
            {
                appendPlaylist(poolad, vector);
            } else
            {
                poolad = getDefaultAd();
                appendPlaylist(poolad, vector);
            }
        }
        poolad = getDefaultAd();
        PoolAd poolad1 = new PoolAd();
        poolad1.copy(poolad);
        poolad1.m_iLength = 300;
        appendPlaylist(poolad1, vector);
        m_dteLastPlayed = new Date();
        updateSPE();
        save();
        return vector;
    }

    public synchronized void processTrans(String s)
    {
        if(s == null || s.equals("NONE"))
            return;
        if(MediaType < 2)
            checkClientCaps();
        StringTokenizer stringtokenizer = null;
        try
        {
            stringtokenizer = new StringTokenizer(s, "\n");
        }
        catch(Exception _ex)
        {
            return;
        }
        String s1 = getBumper().getAdId();
        String s2 = getDefaultAd().getAdIdString();
        while(stringtokenizer.hasMoreTokens()) 
        {
            StringTokenizer stringtokenizer1 = null;
            try
            {
                stringtokenizer1 = new StringTokenizer(stringtokenizer.nextToken(), ";");
            }
            catch(Exception _ex)
            {
                break;
            }
            PoolAd poolad = parseRawAd(stringtokenizer1, false);
            if(poolad != null && poolad.m_iAdType <= MediaType && !s1.equals(poolad.getAdId()) && !s2.equals(poolad.getAdIdString()) && (poolad.m_iDisplayType != 2 || isVideoOk))
            {
                File file = new File(poolad.getCacheFile());
                if(file.exists())
                    poolad.setDownloading(false);
                else
                    poolad.setDownloading(true);
                addElement(poolad);
                m_bDirty = true;
            }
        }
        prune();
        updateSPE();
        save();
    }

    protected boolean resetPlayable()
    {
        int i = 0;
        Date date = getAdjustedDate();
        date.setHours(1);
        date.setMinutes(0);
        date.setSeconds(0);
        UserList userlist = (UserList)m_userads.get(m_sCurUser);
        if(userlist == null)
            return false;
        if(userlist.getCount() < 4)
        {
            ZCast.displayDebug("  ZeroIn: < 4 Ads: USING HashPool resetPlayable() ");
            return super.resetPlayable();
        }
        Object obj = null;
        if(MediaType >= 2 && isVideoOk)
        {
            for(Enumeration enumeration = userlist.elements(); enumeration.hasMoreElements() && i < 4;)
            {
                UserAd userad = (UserAd)enumeration.nextElement();
                PoolAd poolad = (PoolAd)m_adlist.get(userad.getAdId());
                if(!poolad.isDownloading && poolad.m_iDisplayType == 2 && date.after(new Date(poolad.getStartDate())))
                {
                    userad.setPlayable(true);
                    i++;
                }
            }

        }
        if(MediaType >= 6)
        {
            for(Enumeration enumeration1 = userlist.elements(); enumeration1.hasMoreElements() && i < 4;)
            {
                UserAd userad1 = (UserAd)enumeration1.nextElement();
                PoolAd poolad1 = (PoolAd)m_adlist.get(userad1.getAdId());
                if(!poolad1.isDownloading && poolad1.m_iDisplayType == 6 && date.after(new Date(poolad1.getStartDate())))
                {
                    userad1.setPlayable(true);
                    i++;
                }
            }

        }
        for(Enumeration enumeration2 = userlist.elements(); enumeration2.hasMoreElements() && i < 4;)
        {
            UserAd userad2 = (UserAd)enumeration2.nextElement();
            PoolAd poolad2 = (PoolAd)m_adlist.get(userad2.getAdId());
            if(!poolad2.isDownloading && !userad2.isPlayable && date.after(new Date(poolad2.getStartDate())) && poolad2.m_iDisplayType < 2)
            {
                userad2.setPlayable(true);
                i++;
            }
        }

        return i > 0;
    }

    protected void setDownloaded(PoolAd poolad)
    {
        PoolAd poolad1 = poolad;
        if(poolad1 == null)
            ZCast.displayDebug("  POOL: Cannot ad " + poolad.getAdId() + " from SPE ");
        else
            poolad1.setDownloading(false);
        if(poolad1.m_iDisplayType == 2)
        {
            UserList userlist = (UserList)m_userads.get(m_sCurUser);
            Object obj = null;
            Enumeration enumeration = userlist.elements();
            while(enumeration.hasMoreElements()) 
            {
                UserAd userad = (UserAd)enumeration.nextElement();
                if(userad == null || !userad.isPlayable)
                    continue;
                PoolAd poolad2 = (PoolAd)m_adlist.get(userad.getAdId());
                if(poolad2.m_iDisplayType == 2)
                    continue;
                userad.isPlayable = false;
                userlist.setPlayable(poolad1.getAdId(), true);
                ZCast.displayDebug("<><><>\nPOOL: replacing playable Ad " + userad.getAdId() + " with " + poolad1.getAdId());
                save();
                break;
            }
        }
    }

    private static transient ZeroInPool m_Instance = null;
    private transient Date m_dteLastPlayed;
    private static String POOL_FILE = "zeroin.nzp";

}
