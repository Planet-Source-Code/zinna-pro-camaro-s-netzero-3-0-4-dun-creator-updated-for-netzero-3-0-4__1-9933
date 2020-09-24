// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HashPool.java

package pool;

import admgmt.*;
import gui.MemberRecs;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import nzcom.ZCast;
import spe.SPEngine;
import spe.SPItemListener;
import sysinfo.SysInfo;
import sysinfo.SysInfoDriver;

// Referenced classes of package pool:
//            PoolSaver, AdDisplayTypes, UserAd, PoolTypes, 
//            UserList, PoolAd

public abstract class HashPool
    implements Serializable, SPItemListener
{

    protected HashPool()
    {
        m_iListType = 0;
        m_sFilename = null;
        m_bCompress = false;
        m_adlist = null;
        m_userads = null;
        m_dteLastPlayed = null;
        m_sCurUser = null;
        m_bDirty = false;
        MediaType = 1;
        isVideoOk = false;
        m_dteSys = null;
        m_dteDelta = null;
        rGenerator = new Random();
        m_adcapacity = 3;
        m_sCurUser = MemberRecs.getCurrentMemberID();
        if(m_sCurUser == null)
            m_sCurUser = "DEFAULT";
    }

    protected void addElement(PoolAd poolad)
    {
        boolean flag = false;
        if(m_adlist.size() > m_adcapacity)
            prune();
        if(m_adlist.size() == m_adcapacity)
            flag = true;
        if(m_adlist != null)
        {
            if(m_adlist.containsKey(poolad.getAdId()))
            {
                PoolAd poolad1 = (PoolAd)m_adlist.get(poolad.getAdId());
                poolad1.copy(poolad);
                if(m_userads.containsKey(m_sCurUser))
                {
                    for(Enumeration enumeration = m_userads.elements(); enumeration.hasMoreElements();)
                    {
                        UserList userlist = (UserList)enumeration.nextElement();
                        if(userlist.getUserId().equals(m_sCurUser) && userlist.hasAd(poolad.getAdId()))
                            return;
                    }

                    addUserAd(m_sCurUser, poolad.getAdId());
                } else
                {
                    m_adlist.put(poolad.getAdId(), poolad);
                    addUserAd(m_sCurUser, poolad.getAdId());
                }
            } else
            {
                if(flag)
                    prune();
                m_adlist.put(poolad.getAdId(), poolad);
                addUserAd(m_sCurUser, poolad.getAdId());
            }
        } else
        {
            createDefault();
            m_adlist.put(poolad.getAdId(), poolad);
            addUserAd(m_sCurUser, poolad.getAdId());
        }
        m_bDirty = true;
        save();
    }

    protected void addUserAd(String s, String s1)
    {
        if(m_userads == null)
        {
            UserList userlist = new UserList(s);
            userlist.addAdId(s1);
            m_userads.put(s, userlist);
            return;
        }
        if(m_userads.containsKey(s))
        {
            UserList userlist1 = (UserList)m_userads.get(s);
            if(userlist1.hasAd(s1))
                return;
            userlist1.addAdId(s1);
        } else
        {
            UserList userlist2 = new UserList(s);
            userlist2.addAdId(s1);
            m_userads.put(s, userlist2);
        }
    }

    protected boolean appendPlaylist(PoolAd poolad, Vector vector)
    {
        if(poolad == null)
            return false;
        if(vector == null)
            vector = new Vector(1);
        Vector vector1 = poolad.toVector();
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
        return true;
    }

    protected boolean checkClientCaps()
    {
        try
        {
            SysInfoDriver sysinfodriver = new SysInfoDriver();
            SysInfo sysinfo = SysInfoDriver.getOldSystemInfo();
            MediaType = sysinfo.getAdDisplayType();
            isVideoOk = sysinfo.hasVideoHardware();
            if(isVideoOk)
            {
                if(MediaType < 2)
                    MediaType = 2;
                m_bDirty = true;
            }
        }
        catch(Exception _ex)
        {
            MediaType = 1;
            isVideoOk = false;
            return false;
        }
        return isVideoOk;
    }

    protected void copy(HashPool hashpool)
    {
        m_userads = hashpool.m_userads;
        m_adlist = hashpool.m_adlist;
        m_adcapacity = hashpool.m_adcapacity;
        m_dteSys = hashpool.m_dteSys;
        m_dteLastPlayed = hashpool.m_dteLastPlayed;
        m_dteDelta = hashpool.m_dteDelta;
        m_bDirty = hashpool.m_bDirty;
        m_bCompress = hashpool.m_bCompress;
    }

    protected void createDefault()
    {
        if(m_sCurUser == null)
            m_sCurUser = "DEFAULT";
        File file = new File(".\\pool");
        if(!file.exists())
            file.mkdir();
        file = new File(".\\pool\\" + POOL_FILE);
        if(file.exists())
        {
            load();
            return;
        }
        if(m_adlist == null)
        {
            m_adlist = new Hashtable(10);
            if(m_userads == null)
                m_userads = new Hashtable(5);
            String s = PoolTypes.getDefault(m_iListType);
            StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
            PoolAd poolad = parseRawAd(stringtokenizer, false);
            if(m_iListType == 4)
                poolad.m_sCacheFile = ".\\pool\\AD" + poolad.getAdId() + ".GIF";
            else
                poolad.m_sCacheFile = ".\\pool\\AD" + PoolTypes.getDefaultId(m_iListType) + ".GIF";
            poolad.setAdId("DEFAD");
            File file1 = new File(poolad.getCacheFile());
            if(file1.exists())
                poolad.setDownloading(false);
            else
                poolad.setDownloading(true);
            poolad.isDefault = true;
            m_adlist.put(poolad.getAdId(), poolad);
            m_bDirty = true;
        }
        UserList userlist = new UserList("DEFAULT");
        userlist.addAdId("DEFAD");
        m_userads.put("DEFAULT", userlist);
        m_bDirty = true;
        if(m_bDirty)
            save();
    }

    protected synchronized boolean export(String s)
    {
        Object obj = null;
        try
        {
            if(s == null)
                throw new Exception("Invalid Filename!");
            FileOutputStream fileoutputstream = new FileOutputStream(s);
            ObjectOutputStream objectoutputstream;
            if(m_bCompress)
            {
                GZIPOutputStream gzipoutputstream = new GZIPOutputStream(fileoutputstream);
                objectoutputstream = new ObjectOutputStream(gzipoutputstream);
            } else
            {
                objectoutputstream = new ObjectOutputStream(fileoutputstream);
            }
            objectoutputstream.writeObject(m_adlist);
            objectoutputstream.flush();
            objectoutputstream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    protected void finalize()
        throws Throwable
    {
        if(m_bDirty)
            save();
    }

    protected Date getAdjustedDate()
    {
        Date date = new Date();
        if(m_dteDelta == null)
        {
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            return date;
        } else
        {
            date = new Date(date.getTime() - m_dteDelta.getTime());
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            return date;
        }
    }

    public PoolAd getDefaultAd()
    {
        PoolAd poolad = (PoolAd)m_adlist.get("DEFAD");
        if(poolad != null)
        {
            File file = new File(poolad.getCacheFile());
            if(file.exists())
                return poolad;
            if(!SPEngine.getInstance().exists(poolad))
            {
                SPEngine.getInstance().add(poolad.m_sImageUrl, poolad.m_sCacheFile, 6, 4, this, poolad);
                ZCast.displayDebug("<><><> POOL: Requesting default ad: " + poolad.getAdId() + " hash " + poolad);
            }
        }
        return poolad;
    }

    public UserAd getDefaultUserAd()
    {
        UserList userlist = (UserList)m_userads.get("DEFAULT");
        Enumeration enumeration = userlist.elements();
        UserAd userad = (UserAd)enumeration.nextElement();
        return userad;
    }

    public PoolAd getNextPlayableAd(boolean flag)
    {
        UserAd userad = null;
        PoolAd poolad = null;
        UserList userlist = (UserList)m_userads.get(m_sCurUser);
        do
        {
            if(userlist != null)
                userad = userlist.getNextPlayableAd(flag);
            if(userad == null)
                if(resetPlayable())
                {
                    userad = userlist.getNextPlayableAd(flag);
                    m_bDirty = true;
                } else
                {
                    userad = getDefaultUserAd();
                }
            poolad = (PoolAd)m_adlist.get(userad.getAdId());
            if(poolad.getAdId().equals("DEFAD"))
                break;
            if(poolad.m_iDisplayType > MediaType)
            {
                poolad = null;
            } else
            {
                File file = new File(poolad.m_sCacheFile);
                if(!file.exists())
                {
                    poolad.isDownloading = true;
                    userlist.setPlayable(poolad.getAdId(), false);
                    updateSPE();
                    save();
                    poolad = null;
                }
            }
        } while(poolad == null);
        return poolad;
    }

    protected int getAdCount()
    {
        return getAdCount(m_sCurUser);
    }

    protected int getAdCount(String s)
    {
        if(s == null)
            return 0;
        UserList userlist = (UserList)m_userads.get(s);
        if(userlist == null)
            return 0;
        else
            return userlist.getCount();
    }

    protected int getPlayableAdCount()
    {
        return getPlayableAdCount(m_sCurUser);
    }

    protected int getPlayableAdCount(String s)
    {
        if(s == null)
            return 0;
        UserList userlist = (UserList)m_userads.get(s);
        if(userlist == null)
            return 0;
        int i = 0;
        Object obj = null;
        for(Enumeration enumeration = userlist.elements(); enumeration.hasMoreElements();)
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad != null && userad.isPlayable)
            {
                PoolAd poolad = (PoolAd)m_adlist.get(userad.getAdId());
                if(isCurrentAd(poolad))
                    i++;
            }
        }

        return i;
    }

    public Vector getPlaylist()
    {
        Vector vector = new Vector(3);
        Object obj = null;
        for(int i = 0; i < 2; i++)
        {
            PoolAd poolad = getNextPlayableAd(false);
            Vector vector1 = poolad.toVector();
            if(vector1 != null)
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
            if(poolad.getAdId().equals(DEFAULT_ADID))
                break;
        }

        save();
        return vector;
    }

    public String getUserId()
    {
        return m_sCurUser;
    }

    public synchronized boolean importHash(String s)
    {
        Object obj = null;
        File file = new File(s);
        if(!file.exists())
            return false;
        try
        {
            if(s == null)
                throw new Exception("Invalid Filename!");
            FileInputStream fileinputstream = new FileInputStream(s);
            ObjectInputStream objectinputstream;
            if(m_bCompress)
            {
                GZIPInputStream gzipinputstream = new GZIPInputStream(fileinputstream);
                objectinputstream = new ObjectInputStream(gzipinputstream);
            } else
            {
                objectinputstream = new ObjectInputStream(fileinputstream);
            }
            Hashtable hashtable = (Hashtable)objectinputstream.readObject();
            objectinputstream.close();
            createDefault();
            m_adlist = hashtable;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        m_bDirty = false;
        return true;
    }

    protected boolean isCurrentAd(PoolAd poolad)
    {
        Date date = getAdjustedDate();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(2);
        Date date1 = new Date(poolad.getStartDate());
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(1);
        Date date2 = new Date(poolad.getEndDate());
        date2.setHours(23);
        date2.setMinutes(59);
        date2.setSeconds(59);
        return date.after(date1) && date.before(date2);
    }

    protected synchronized void load()
    {
        Object obj = null;
        String s = m_sFilename;
        if(s == null)
            s = ".\\pool\\" + POOL_FILE;
        File file = new File(s);
        if(!file.exists())
        {
            createDefault();
            return;
        }
        try
        {
            if(s == null)
                throw new Exception("Invalid Filename!");
            FileInputStream fileinputstream = new FileInputStream(s);
            ObjectInputStream objectinputstream;
            if(m_bCompress)
            {
                GZIPInputStream gzipinputstream = new GZIPInputStream(fileinputstream);
                objectinputstream = new ObjectInputStream(gzipinputstream);
            } else
            {
                objectinputstream = new ObjectInputStream(fileinputstream);
            }
            HashPool hashpool = (HashPool)objectinputstream.readObject();
            objectinputstream.close();
            copy(hashpool);
        }
        catch(InvalidClassException _ex)
        {
            ZCast.displayDebug("Updating pool file to new format...");
            createDefault();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            ZCast.displayDebug(" invalid .nzp file, re-creating  ");
            createDefault();
        }
        m_bDirty = false;
    }

    public synchronized void onItemError(String s, Object obj)
    {
        ZCast.displayDebug("Pool", " ====> pool " + m_iListType + " received error message ");
        ZCast.displayDebug("Pool", " ====>  " + s);
        PoolAd poolad = (PoolAd)obj;
        if(poolad != null)
        {
            poolad.addError();
            m_bDirty = true;
            save();
        }
    }

    public synchronized void onItemFinished(Object obj)
    {
        PoolAd poolad = (PoolAd)obj;
        ZCast.displayDebug("Pool", "<><> Pool: received " + poolad.getAdId() + " from SPE (" + poolad + ")");
        setDownloaded(poolad);
        save();
    }

    protected PoolAd parseRawAd(StringTokenizer stringtokenizer)
    {
        return parseRawAd(stringtokenizer, true);
    }

    protected PoolAd parseRawAd(StringTokenizer stringtokenizer, boolean flag)
    {
        PoolAd poolad = null;
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        if(stringtokenizer == null)
        {
            ZCast.displayDebug("  Pools:  RECEIVED NULL TOKEN!!");
            return null;
        }
        try
        {
            if(stringtokenizer.countTokens() >= 5)
            {
                String s3 = stringtokenizer.nextToken();
                String s7 = stringtokenizer.nextToken();
                String s1 = stringtokenizer.nextToken();
                String s2 = stringtokenizer.nextToken();
                String s4 = stringtokenizer.nextToken();
                String s5 = stringtokenizer.nextToken();
                String s6 = stringtokenizer.nextToken();
                int i = s7.indexOf("&");
                String s = s7.substring(0, i);
                poolad = new PoolAd(s, s1);
                poolad.m_sAdIdString = s7;
                poolad.setAdType(s3);
                poolad.m_iAdType = Integer.parseInt(s3.substring(0, 1));
                poolad.m_iDisplayType = Integer.parseInt(s3.substring(1, 2));
                poolad.m_iLength = Integer.parseInt(s4);
                poolad.setAdUrlString(s2);
                if(s5 != null)
                    poolad.setStartDate(s5);
                if(s6 != null)
                    poolad.setEndDate(s6);
                poolad.m_sCacheFile = ".\\pool\\AD" + s + "." + AdDisplayTypes.getExtention(poolad.m_iDisplayType);
                if(flag)
                    addElement(poolad);
            }
        }
        catch(Exception _ex)
        {
            ZCast.displayDebug("Pool", "<><>Pool: Invalid entry in PLYI!");
            return null;
        }
        return poolad;
    }

    public void processTrans(String s)
    {
        if(s == null || s.equals("NONE"))
            return;
        Object obj = null;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, "\n"); stringtokenizer.hasMoreTokens();)
        {
            StringTokenizer stringtokenizer1 = new StringTokenizer(stringtokenizer.nextToken(), ";");
            PoolAd poolad = parseRawAd(stringtokenizer1, false);
            if(poolad != null && poolad.m_iAdType <= MediaType && (poolad.m_iDisplayType != 2 || isVideoOk))
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

        updateSPE();
        save();
    }

    protected synchronized void prune()
    {
        removeExpiredAds();
        for(int i = 0; getAdCount() > m_adcapacity && i < 10; i++)
            removeOldestAd();

    }

    protected void removeAd(String s)
    {
        PoolAd poolad = (PoolAd)m_adlist.get(s);
        if(poolad == null)
            return;
        m_adlist.remove(poolad.getAdId());
        removeAdFromUsers(s);
        File file = new File(poolad.m_sCacheFile);
        if(file.exists())
            file.delete();
        save();
    }

    protected void removeAdFromUser(String s)
    {
        if(s == null)
            return;
        for(Enumeration enumeration = m_userads.elements(); enumeration.hasMoreElements();)
        {
            UserList userlist = (UserList)enumeration.nextElement();
            if(userlist.getUserId().equals(m_sCurUser) && userlist.hasAd(s))
            {
                userlist.removeAdId(s);
                m_bDirty = true;
            }
        }

    }

    protected synchronized void removeAdFromUsers(String s)
    {
        if(s == null)
            return;
        for(Enumeration enumeration = m_userads.elements(); enumeration.hasMoreElements();)
        {
            UserList userlist = (UserList)enumeration.nextElement();
            if(userlist.getUserId() != "DEFAULT")
            {
                userlist.removeAdId(s);
                m_bDirty = true;
            }
        }

    }

    protected synchronized void removeExpiredAds()
    {
        Date date = null;
        date = getAdjustedDate();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        for(Enumeration enumeration = m_adlist.elements(); enumeration.hasMoreElements();)
        {
            PoolAd poolad = (PoolAd)enumeration.nextElement();
            if(poolad == null)
                break;
            if(!poolad.isDefault && date.after(new Date(poolad.getEndDate())))
            {
                m_adlist.remove(poolad.getAdId());
                removeAdFromUser(poolad.getAdId());
            }
        }

    }

    protected synchronized void removeOldestAd()
    {
        UserList userlist = (UserList)m_userads.get(m_sCurUser);
        Enumeration enumeration = userlist.elements();
        Object obj = null;
        PoolAd poolad = null;
        PoolAd poolad1 = null;
        Date date = new Date();
        while(enumeration.hasMoreElements()) 
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            poolad = (PoolAd)m_adlist.get(userad.getAdId());
            if(!poolad.isDefault)
                if(poolad1 == null)
                    poolad1 = poolad;
                else
                if(poolad.getEndDate() - date.getTime() <= poolad1.getEndDate() - date.getTime())
                    poolad1 = poolad;
        }
        if(poolad1 != null)
        {
            ZCast.displayDebug("Pool", "&&& POOL: removing OLDEST Ad" + poolad1.getAdId());
            removeAd(poolad1.getAdId());
        } else
        {
            ZCast.displayDebug("Pool", "&&& POOL: removing FIRST  pad " + poolad.getAdId());
            removeAd(poolad.getAdId());
        }
        save();
    }

    protected synchronized void removeUnusedAds()
    {
        boolean flag = false;
        for(Enumeration enumeration = m_adlist.elements(); enumeration.hasMoreElements();)
        {
            PoolAd poolad = (PoolAd)enumeration.nextElement();
            if(poolad != null && !poolad.isDefault)
            {
                Enumeration enumeration1 = m_userads.elements();
                while(enumeration1.hasMoreElements()) 
                {
                    UserList userlist = (UserList)enumeration1.nextElement();
                    if(userlist.getUserId() == "DEFAULT")
                        continue;
                    Thread.yield();
                    if(!userlist.hasAd(poolad.getAdId()))
                        continue;
                    flag = true;
                    break;
                }
                if(!flag)
                    m_adlist.remove(poolad.getAdId());
            }
        }

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
        for(Enumeration enumeration = userlist.elements(); enumeration.hasMoreElements();)
        {
            UserAd userad = (UserAd)enumeration.nextElement();
            if(userad == null)
                break;
            PoolAd poolad = (PoolAd)m_adlist.get(userad.getAdId());
            if(poolad != null && !poolad.isDownloading && isCurrentAd(poolad) && poolad.m_iDisplayType <= MediaType)
            {
                userad.setPlayable(true);
                i++;
            }
        }

        return i > 0;
    }

    public synchronized void save()
    {
        PoolSaver poolsaver = new PoolSaver(this);
        poolsaver.run();
        m_bDirty = false;
    }

    public void setCapacity(int i)
    {
        if(i != m_adcapacity)
        {
            m_adcapacity = i;
            prune();
        }
        m_bDirty = true;
    }

    protected void setDeltaTime(boolean flag)
    {
        Date date = new Date();
        if(flag)
            m_dteDelta = new Date(m_dteSys.getTime() - date.getTime());
        else
        if(m_dteDelta != null && !date.equals(m_dteDelta))
            m_dteDelta = new Date(m_dteSys.getTime() - date.getTime());
    }

    protected void setDownloaded(String s)
    {
        PoolAd poolad = (PoolAd)m_adlist.get(s);
        if(poolad == null)
        {
            ZCast.displayDebug("Pool", "POOL.setDownloaded - received NULL AdId - " + s);
            return;
        }
        poolad.setDownloading(false);
        if(isCurrentAd(poolad) && poolad.m_iDisplayType <= MediaType)
        {
            for(Enumeration enumeration = m_userads.elements(); enumeration.hasMoreElements();)
            {
                UserList userlist = (UserList)enumeration.nextElement();
                if(!userlist.getUserId().equals("DEFAULT") && userlist.hasAd(s))
                    userlist.setPlayable(s, true);
            }

        }
    }

    protected void setDownloaded(PoolAd poolad)
    {
        setDownloaded(poolad.getAdId());
    }

    public void setServerDate(Date date)
    {
        if(date.getTime() == 0L)
        {
            return;
        } else
        {
            m_dteSys = date;
            setDeltaTime(true);
            return;
        }
    }

    public void setServerDate(long l)
    {
        if(l == 0L)
        {
            return;
        } else
        {
            m_dteSys = new Date(l);
            setDeltaTime(true);
            return;
        }
    }

    public void setUserId(String s)
    {
        m_sCurUser = s;
        UserList userlist;
        try
        {
            userlist = (UserList)m_userads.get(s);
        }
        catch(NullPointerException _ex)
        {
            createDefault();
            UserList userlist1 = new UserList(s);
            m_userads.put(s, userlist1);
        }
    }

    public void showContents()
    {
        System.out.println(">> Pool: " + m_sFilename);
        ZCast.displayDebug("List of Ad Id(s): ");
        PoolAd poolad;
        Date date;
        for(Enumeration enumeration = m_adlist.elements(); enumeration.hasMoreElements(); System.out.println("  ID: " + poolad.getAdId() + ", " + date.toString() + "  length: " + poolad.m_iLength))
        {
            poolad = (PoolAd)enumeration.nextElement();
            date = new Date(poolad.getEndDate());
        }

        ZCast.displayDebug("List of Ads by user: ");
        Object obj = null;
        Object obj1 = null;
        String s;
        for(Enumeration enumeration1 = m_userads.elements(); enumeration1.hasMoreElements(); System.out.println(s))
        {
            s = "  ADS: ";
            UserList userlist = (UserList)enumeration1.nextElement();
            System.out.println("USER: " + userlist.getUserId());
            for(Enumeration enumeration2 = userlist.elements(); enumeration2.hasMoreElements();)
            {
                UserAd userad = (UserAd)enumeration2.nextElement();
                if(userad == null)
                    s = s + "NULL AD!, ";
                else
                    s = s + userad.getAdId() + ", ";
            }

        }

    }

    protected void updateSPE()
    {
        Vector vector = new Vector(4);
        for(Enumeration enumeration = m_adlist.elements(); enumeration.hasMoreElements();)
        {
            PoolAd poolad = (PoolAd)enumeration.nextElement();
            if(poolad.isDownloading)
            {
                PoolAd poolad2 = poolad;
                if(poolad.m_iDisplayType == 2)
                {
                    if(isVideoOk)
                        vector.addElement(poolad.getAdId());
                } else
                if(SPEngine.getInstance().exists(poolad2))
                {
                    ZCast.displayDebug("Pool", "<><> Pool: already requested " + poolad2.getAdId());
                } else
                {
                    if(poolad.m_iDisplayType <= MediaType)
                        SPEngine.getInstance().add(poolad.m_sImageUrl, poolad.m_sCacheFile, 5, 4, this, poolad2);
                    ZCast.displayDebug("Pool", "<><> Pool: sending " + poolad2 + " with id: " + poolad2.getAdId() + " to SPE,  mode: " + 4 + " Pty: " + 5);
                }
            }
        }

        if(vector.size() > 0)
        {
            for(int i = 0; i < vector.size(); i++)
            {
                for(int j = 0; j < vector.size(); j++)
                {
                    PoolAd poolad3 = (PoolAd)m_adlist.get((String)vector.elementAt(i));
                    PoolAd poolad4 = (PoolAd)m_adlist.get((String)vector.elementAt(j));
                    if(poolad3.getStartDate() < poolad4.getStartDate())
                    {
                        String s = new String((String)vector.elementAt(i));
                        vector.setElementAt(vector.elementAt(j), i);
                        vector.setElementAt(s, j);
                    } else
                    if(poolad3.getStartDate() == poolad4.getStartDate() && poolad3.toInt() < poolad4.toInt())
                    {
                        String s1 = new String((String)vector.elementAt(i));
                        vector.setElementAt(vector.elementAt(j), i);
                        vector.setElementAt(s1, j);
                    }
                }

            }

            PoolAd poolad1;
            for(Enumeration enumeration1 = vector.elements(); enumeration1.hasMoreElements(); ZCast.displayDebug("Pool", "<><> Pool: sending tvi " + poolad1 + " with id: " + poolad1.getAdId() + " to SPE. mode: " + 1 + " Pty: " + 2))
            {
                poolad1 = (PoolAd)m_adlist.get((String)enumeration1.nextElement());
                if(SPEngine.getInstance().exists(poolad1))
                {
                    SPEngine.getInstance().remove(poolad1, false);
                    ZCast.displayDebug("Pool", "<><> Pool: already requested " + poolad1.getAdId() + " removing to change order.");
                }
                SPEngine.getInstance().add(poolad1.m_sImageUrl, poolad1.m_sCacheFile, 2, 1, this, poolad1);
            }

        }
    }

    protected int m_iListType;
    static final long serialVersionUID = 0x27023fc20a7f268aL;
    protected String m_sFilename;
    protected boolean m_bCompress;
    protected Hashtable m_adlist;
    protected Hashtable m_userads;
    private transient Date m_dteLastPlayed;
    protected transient String m_sCurUser;
    protected transient boolean m_bDirty;
    public int MediaType;
    public boolean isVideoOk;
    private Date m_dteSys;
    private Date m_dteDelta;
    private transient Random rGenerator;
    protected int m_adcapacity;
    private static String DEFAULT_AD = "DEFAULT.GIF";
    private static String DEFAULT_ADID = "DEFAULT_AD";
    private static String POOL_FILE = "pool.nzp";

}
