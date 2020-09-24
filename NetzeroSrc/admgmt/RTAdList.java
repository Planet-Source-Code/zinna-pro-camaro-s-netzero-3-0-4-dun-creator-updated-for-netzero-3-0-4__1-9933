// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTAdList.java

package admgmt;

import java.io.*;
import java.util.*;

// Referenced classes of package admgmt:
//            RTServices, SCTransCar, RTTransaction

public class RTAdList extends RTServices
{

    public RTAdList(String s, int i)
    {
        m_htUserUrls = null;
        m_htUserKeywords = null;
        m_htAds = null;
        m_sRTIP = null;
        m_iRTPort = 5000;
        m_iDisplayType = 1;
        m_sAreaCode = null;
        m_fosUserUrls = null;
        m_fosIDs = null;
        m_lastServerCall = 0L;
        init(s, i, 1, "0");
    }

    public RTAdList(String s, int i, int j)
    {
        m_htUserUrls = null;
        m_htUserKeywords = null;
        m_htAds = null;
        m_sRTIP = null;
        m_iRTPort = 5000;
        m_iDisplayType = 1;
        m_sAreaCode = null;
        m_fosUserUrls = null;
        m_fosIDs = null;
        m_lastServerCall = 0L;
        init(s, i, j, "0");
    }

    public RTAdList(String s, int i, int j, String s1)
    {
        m_htUserUrls = null;
        m_htUserKeywords = null;
        m_htAds = null;
        m_sRTIP = null;
        m_iRTPort = 5000;
        m_iDisplayType = 1;
        m_sAreaCode = null;
        m_fosUserUrls = null;
        m_fosIDs = null;
        m_lastServerCall = 0L;
        init(s, i, j, s1);
    }

    private Object addTarget(String s, boolean flag, boolean flag1)
    {
        Object obj = null;
        String as[] = null;
        for(int i = 0; i != -1;)
        {
            String s1 = null;
            i = s.indexOf(RTServices.ADListDelim);
            if(i == -1)
            {
                s1 = s;
            } else
            {
                s1 = s.substring(0, i);
                s = s.substring(i + 1);
            }
            try
            {
                if(flag)
                {
                    as = RTServices.urlPlaylistArr(s1);
                    Object obj1 = RTServices.putAd(getHtUserUrls(), getHtUrlsByKeyword(), getHtAds(), as[RTServices.URL_Index], as[RTServices.PLAYLIST_ID_Index], as[RTServices.AD_Index]);
                    if(obj == null && obj1 != null)
                        obj = obj1;
                }
                if(flag1)
                {
                    getFosUserUrls(true).write((s1 + '\n').getBytes());
                    getFosUserUrls(true).flush();
                    if(!as[RTServices.URL_ID_Index].startsWith("-"))
                    {
                        if(as == null)
                            as = RTServices.urlPlaylistArr(s1);
                        getFosIDs(true).write((as[RTServices.URL_ID_Index] + RTServices.InputDelim + as[RTServices.PLAYLIST_ID_Index] + '\n').getBytes());
                        getFosIDs(true).flush();
                    }
                }
            }
            catch(Exception exception)
            {
                System.out.println(String.valueOf(exception) + " cannot add target:" + s1);
            }
        }

        return obj;
    }

    private void cacheAdditions(Vector vector)
    {
        Object obj = null;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            try
            {
                String s = (String)enumeration.nextElement();
                if(s != null && s.length() > 0)
                    addTarget(s, true, RTServices.CACHEToDisk);
            }
            catch(Exception _ex) { }

    }

    private void cacheLoad()
    {
        try
        {
            File file = new File(getCoreFileName());
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            for(String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
                try
                {
                    if(s.length() > 0)
                        addTarget(s, true, false);
                }
                catch(Exception exception1)
                {
                    exception1.printStackTrace();
                }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private Object callRTServer(String s)
    {
        long l = System.currentTimeMillis();
        if(l - m_lastServerCall < (long)(RTServices.MINServerCallLaps * 1000))
            return null;
        m_lastServerCall = l;
        try
        {
            SCTransCar sctranscar = (SCTransCar)RTServices.m_rTt.execute("RTGT", getAdDisplayType() + RTServices.InputDelim + getAreaCode() + RTServices.InputDelim + s);
            String s1 = (String)sctranscar.getContents();
            if(s1 != null)
            {
                return addTarget(s1, true, RTServices.CACHEToDisk);
            } else
            {
                String s2 = new Long(-l) + RTServices.InputDelim + s + RTServices.InputDelim + RTServices.NULLPlaylistID + RTServices.InputDelim + RTServices.URLNOTTargeted;
                addTarget(s2, true, RTServices.CACHEToDisk);
                return null;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    public void close()
    {
        try
        {
            if(m_fosUserUrls != null)
                m_fosUserUrls.close();
            if(m_fosIDs == null)
                m_fosIDs.close();
        }
        catch(Exception _ex) { }
    }

    private void defaultLoad(Vector vector)
    {
        Object obj = null;
        try
        {
            getFosUserUrls(false);
            getFosIDs(false);
            for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            {
                String s = (String)enumeration.nextElement();
                try
                {
                    if(s != null && s.length() != 0)
                        if(s.startsWith(RTServices.SettingsFollow))
                        {
                            RTServices.applySettings(s);
                            s = (new StringBuffer(s)).reverse().toString();
                            getFosIDs(true).write((s + '\n').getBytes());
                        } else
                        {
                            addTarget(s, true, RTServices.CACHEToDisk);
                        }
                }
                catch(Exception exception1)
                {
                    exception1.printStackTrace();
                }
            }

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public Vector findAdList(String s)
    {
        try
        {
            if(invalidUrl(s))
                return null;
            Object obj = searchHashtable(s);
            if(obj == null)
                obj = callRTServer(s);
            else
            if(obj instanceof String)
                if(((String)obj).equals(RTServices.URLISTargeted))
                    obj = callRTServer(s);
                else
                    return null;
            if(obj != null && (obj instanceof Vector))
                return resolveAds(obj);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private int getAdDisplayType()
    {
        return m_iDisplayType;
    }

    private String getAreaCode()
    {
        return m_sAreaCode;
    }

    private String getCoreFileName()
    {
        return RTServices.COREFilename;
    }

    private FileOutputStream getFosIDs(boolean flag)
    {
        if(m_fosIDs == null)
            try
            {
                m_fosIDs = new FileOutputStream(getIdsFileName(), flag);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        return m_fosIDs;
    }

    private FileOutputStream getFosUserUrls(boolean flag)
    {
        if(m_fosUserUrls == null)
            try
            {
                m_fosUserUrls = new FileOutputStream(getCoreFileName(), flag);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        return m_fosUserUrls;
    }

    private Hashtable getHtAds()
    {
        if(m_htAds == null)
            m_htAds = new Hashtable(50);
        return m_htAds;
    }

    private Hashtable getHtUrlsByKeyword()
    {
        if(m_htUserKeywords == null)
            m_htUserKeywords = new Hashtable(25);
        return m_htUserKeywords;
    }

    private Hashtable getHtUserUrls()
    {
        if(m_htUserUrls == null)
            m_htUserUrls = new Hashtable(100);
        return m_htUserUrls;
    }

    private String getIdsFileName()
    {
        return RTServices.IDSFilename;
    }

    private long getMissOKMillis()
    {
        return RTServices.MissOKForSecs * 1000L;
    }

    private long getVerifyMillis()
    {
        return RTServices.SECSBetweenVerify * 1000L;
    }

    private void init(String s, int i, int j, String s1)
    {
        m_sRTIP = s;
        if(i != 0)
            m_iRTPort = i;
        m_iDisplayType = j;
        m_sAreaCode = s1;
        RTServices.m_rTt = new RTTransaction(s, i);
        RTServices.TMPFilename = "." + File.separator + RTServices.CACHEDirectory + File.separator + RTServices.TMPFilename;
        RTServices.COREFilename = "." + File.separator + RTServices.CACHEDirectory + File.separator + RTServices.COREFilename;
        RTServices.IDSFilename = "." + File.separator + RTServices.CACHEDirectory + File.separator + RTServices.IDSFilename;
        loadHashtable();
        getFosUserUrls(true);
        getFosIDs(true);
    }

    private boolean invalidUrl(String s)
    {
        if(s == null || s.length() == 0)
            return true;
        for(int i = 0; i < RTServices.INValidSearchWords.length; i++)
            if(s.indexOf(RTServices.INValidSearchWords[i]) != -1)
                return true;

        return false;
    }

    private void loadHashtable()
    {
        Object obj = null;
        try
        {
            SCTransCar sctranscar = (SCTransCar)startup();
            if(RTServices.CACHEToDisk && (sctranscar == null || sctranscar.getContents() == null))
            {
                System.out.println("loading local cache...");
                cacheLoad();
            } else
            if(sctranscar != null && (sctranscar.getContents() instanceof Hashtable))
            {
                System.out.println("validating local cache...");
                validateLoad((Hashtable)sctranscar.getContents());
            } else
            if(sctranscar != null && (sctranscar.getContents() instanceof Vector))
            {
                System.out.println("loading default cache from server...");
                defaultLoad((Vector)sctranscar.getContents());
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private Vector randomizeAds(Vector vector)
    {
        Enumeration enumeration = vector.elements();
        String as[] = new String[vector.size()];
        as[0] = (String)enumeration.nextElement();
        Vector vector1 = new Vector();
        Vector vector2 = new Vector();
        for(int i = 1; i < vector.size(); i++)
            try
            {
                String s = (String)vector.elementAt(i);
                int k = Integer.parseInt(s.substring(0, s.indexOf(RTServices.ADID_ORDER_Delim)));
                if(k == 0)
                {
                    vector2.addElement(new Integer(i));
                } else
                {
                    if(s.indexOf(RTServices.FLAGPlayListText) == -1)
                        s = (String)getHtAds().get(vector.elementAt(k));
                    else
                        s = s.substring(s.indexOf(RTServices.FLAGPlayListText) + RTServices.FLAGPlayListText.length());
                    if(s != null)
                        as[k] = s;
                }
            }
            catch(Exception exception)
            {
                System.out.println(String.valueOf(exception) + " randomizeAds:" + vector);
            }

        int j = (int)(System.currentTimeMillis() % 100L);
        while(vector2.size() > 0) 
        {
            Integer integer = (Integer)vector2.elementAt(0);
            int i1 = integer.intValue();
            try
            {
                if(vector2.size() == 1)
                {
                    as[i1] = (String)getHtAds().get(vector.elementAt(i1));
                } else
                {
                    int j1 = j % vector2.size();
                    Integer integer1 = (Integer)vector2.elementAt(j1);
                    int k1 = integer1.intValue();
                    as[k1] = (String)getHtAds().get(vector.elementAt(i1));
                    if(i1 != k1)
                    {
                        as[i1] = (String)getHtAds().get(vector.elementAt(k1));
                        vector2.removeElementAt(j1);
                    }
                }
                vector2.removeElementAt(0);
            }
            catch(Exception exception1)
            {
                System.out.println(String.valueOf(exception1) + " randomizeAds" + vector);
            }
        }
        for(int l = 0; l < as.length; l++)
            if(as[l] != null)
                vector1.addElement(as[l]);

        return vector1;
    }

    private Vector resolveAds(Object obj)
    {
        Vector vector = (Vector)obj;
        if(vector.size() > 2)
        {
            Object obj1 = null;
            Vector vector2 = randomizeAds(vector);
            return vector2;
        }
        Vector vector1 = new Vector();
        vector1.addElement(vector.elementAt(0));
        String s = (String)getHtAds().get(vector.elementAt(1));
        if(s == null)
        {
            return null;
        } else
        {
            vector1.addElement(s);
            return vector1;
        }
    }

    private Object searchHashtable(String s)
    {
        Object obj = null;
        try
        {
            obj = RTServices.findAd(getHtUserUrls(), getHtUrlsByKeyword(), s);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return obj;
    }

    private Object startup()
    {
        FileReader filereader = null;
        String s = getAdDisplayType() + RTServices.InputDelim + getAreaCode();
        try
        {
            File file = new File(getCoreFileName());
            if(file.exists() && file.length() > 0L)
            {
                File file1 = new File(getIdsFileName());
                if(file1.exists() && file1.length() > 0L)
                {
                    filereader = new FileReader(file1);
                    BufferedReader bufferedreader = new BufferedReader(filereader);
                    String s1 = bufferedreader.readLine();
                    if(s1.startsWith(RTServices.SettingsFollow))
                        RTServices.applySettings((new StringBuffer(s1)).reverse().toString());
                    if(System.currentTimeMillis() - file1.lastModified() < getVerifyMillis())
                    {
                        bufferedreader.close();
                        filereader = null;
                        return null;
                    }
                    if(!RTServices.CACHEToDisk)
                        return RTServices.m_rTt.execute("RTGO", s);
                    Vector vector = new Vector();
                    vector.addElement(s);
                    for(String s2 = bufferedreader.readLine(); s2 != null; s2 = bufferedreader.readLine())
                        vector.addElement(s2);

                    bufferedreader.close();
                    filereader = null;
                    if(vector.size() <= RTServices.MAXCacheEntries)
                        return RTServices.m_rTt.execute("RTVR", vector);
                }
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            try
            {
                filereader.close();
                filereader = null;
            }
            catch(Exception _ex) { }
        }
        return RTServices.m_rTt.execute("RTGO", s);
    }

    private void validateLoad(Hashtable hashtable)
        throws Exception
    {
        long l = 0L;
        File file = new File(getCoreFileName());
        BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        l = System.currentTimeMillis();
        FileOutputStream fileoutputstream = new FileOutputStream(RTServices.TMPFilename, false);
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
        getFosIDs(false);
        String s = (String)hashtable.get(RTServices.SettingsFollow);
        if(s != null)
        {
            RTServices.applySettings(s);
            s = (new StringBuffer(s)).reverse().toString();
            getFosIDs(true).write((s + '\n').getBytes());
            hashtable.remove(RTServices.SettingsFollow);
        }
        Vector vector = (Vector)hashtable.get(RTServices.CACHEAdditions);
        if(vector != null)
            hashtable.remove(RTServices.CACHEAdditions);
        for(String s1 = bufferedreader.readLine(); s1 != null; s1 = bufferedreader.readLine())
            verify(s1, hashtable, bufferedoutputstream, l);

        bufferedreader.close();
        bufferedreader = null;
        getFosIDs(true).flush();
        file.delete();
        bufferedoutputstream.flush();
        bufferedoutputstream.close();
        File file1 = new File(RTServices.TMPFilename);
        file1.renameTo(file);
        if(vector != null)
            cacheAdditions(vector);
    }

    private void verify(String s, Hashtable hashtable, OutputStream outputstream, long l)
    {
        boolean flag = false;
        try
        {
            String as[] = RTServices.urlPlaylistArr(s);
            if(as[RTServices.URL_ID_Index].startsWith("-"))
            {
                flag = true;
                if(l - (new Long(as[RTServices.URL_ID_Index].substring(1))).longValue() > getMissOKMillis())
                    return;
            }
            if(!hashtable.isEmpty() && hashtable.containsKey(as[RTServices.URL_ID_Index]))
            {
                hashtable.remove(as[RTServices.URL_ID_Index]);
                return;
            }
            RTServices.putAd(getHtUserUrls(), getHtUrlsByKeyword(), getHtAds(), as[RTServices.URL_Index], as[RTServices.PLAYLIST_ID_Index], as[RTServices.AD_Index]);
            String s1 = as[RTServices.URL_ID_Index] + RTServices.InputDelim + as[RTServices.PLAYLIST_ID_Index] + '\n';
            if(!flag)
                getFosIDs(true).write(s1.getBytes());
            outputstream.write((s + '\n').getBytes());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private Hashtable m_htUserUrls;
    private Hashtable m_htUserKeywords;
    private Hashtable m_htAds;
    private String m_sRTIP;
    private int m_iRTPort;
    private int m_iDisplayType;
    private String m_sAreaCode;
    private FileOutputStream m_fosUserUrls;
    private FileOutputStream m_fosIDs;
    private long m_lastServerCall;
}
