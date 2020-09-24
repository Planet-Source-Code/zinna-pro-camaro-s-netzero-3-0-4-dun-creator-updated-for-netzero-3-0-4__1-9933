// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayAd.java

package admgmt;

import java.awt.Component;
import java.awt.Point;
import java.io.*;
import java.net.*;
import java.util.*;
import nzcom.*;
import util.Cookie;

// Referenced classes of package admgmt:
//            PlayAdListener

public abstract class PlayAd
{

    public PlayAd()
    {
        m_extendedTime = 0;
        m_displayListenerList = null;
        played = false;
        m_adIdShort = null;
        m_adDisplayCount = 0;
        m_adClickthruCount = 0;
        m_adPrevCount = 0;
        m_adNextCount = 0;
        m_adErrors = 0;
        m_fetchError = false;
        m_fetching = false;
        m_adCacheFile = null;
        m_statsChanged = false;
        m_adDisplayTime = 30;
        m_avgClickthruTime = 0L;
        m_totalDisplays = 0;
        m_totalClickThrus = 0;
        m_totalPrev = 0;
        m_totalNext = 0;
        m_totalError = 0;
        m_totalTarget = 0;
        m_clicksPerDisplay = 0;
        m_maxClicksPerDisplay = 3;
        m_clickLagTime = 5000L;
        m_displayListenerList = new Vector(1);
        m_targetCount = 0;
        m_targetString = null;
        m_displayStartTime = 0L;
    }

    public int getExtendedTime()
    {
        int i = m_extendedTime;
        m_extendedTime = 0;
        return i;
    }

    public abstract boolean prepareForDisplay(Component component);

    public abstract void renderAd(Component component);

    public abstract void stopRenderAd();

    public abstract void cleanUp();

    public void setPlayed(boolean flag)
    {
        played = flag;
    }

    public boolean isInterruptable()
    {
        return m_adType != 4 || played;
    }

    public String toString()
    {
        return "PlayAd [m_adId=" + m_adId + ";m_adImageString=" + m_adImageString + ";m_adUrlString=" + m_adUrlString + ";m_adType=" + m_adType + ";m_adDisplayTime=" + m_adDisplayTime + ";m_targetString=" + m_targetString + "]";
    }

    public void cleanUpCache()
    {
        if(m_adCacheFile != null && m_adCacheFile.startsWith(CACHE_FILE_PATH))
            try
            {
                File file = new File(m_adCacheFile);
                if(file.isDirectory())
                {
                    String as[] = file.list();
                    if(as != null)
                    {
                        for(int i = 0; i < as.length; i++)
                            (new File(m_adCacheFile + File.separator + as[i])).delete();

                    }
                }
                boolean flag = file.delete();
                ZCast.displayDebug("newlist", "PlayAd: " + m_adIdShort + " deleting cached file " + m_adCacheFile + ", result=" + flag);
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
    }

    public static void cleanUpCacheDir()
    {
        try
        {
            File file = new File("cache");
            String as[] = file.list(new FilenameFilter() {

                public boolean accept(File file2, String s1)
                {
                    return s1.indexOf("Ad") != -1;
                }

            });
            if(as != null)
            {
                for(int i = 0; i < as.length; i++)
                {
                    String s = CACHE_FILE_PATH + as[i];
                    File file1 = new File(s);
                    if(file1.isDirectory())
                    {
                        String as1[] = file1.list();
                        if(as1 != null)
                        {
                            for(int j = 0; j < as1.length; j++)
                                (new File(s + File.separator + as1[j])).delete();

                        }
                    }
                    boolean flag = file1.delete();
                    ZCast.displayDebug("playad", "Deleting cache file=" + s + " result=" + flag);
                    Thread.yield();
                }

            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public void addPlayAdListener(PlayAdListener playadlistener)
    {
        if(m_displayListenerList != null)
            m_displayListenerList.addElement(playadlistener);
    }

    public void clearPlayAdListeners()
    {
        if(m_displayListenerList != null)
            m_displayListenerList.removeAllElements();
    }

    public void notifyDisplayOccured()
    {
        if(m_displayListenerList != null)
        {
            for(int i = 0; i < m_displayListenerList.size(); i++)
                try
                {
                    PlayAdListener playadlistener = (PlayAdListener)m_displayListenerList.elementAt(i);
                    playadlistener.adDisplayOccured();
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug(exception);
                }

        }
    }

    public boolean clickThru(long l)
    {
        boolean flag;
        if(flag = setClickThruTime(l))
        {
            String s = getAdUrlString();
            if(m_adType == 2)
            {
                if(ZCast.m_demoMode)
                {
                    OSDetectNative.showUrl(s, true);
                    return flag;
                }
                ZCast.displayDebug("Right before showURL in clickThru new browser " + s);
                try
                {
                    ZCast.displayDebug("The URL is " + s);
                    ZCast.displayDebug("the browser to use is " + ZCast.getBrowserToUse());
                    String s1 = "";
                    int i = ZCast.getBrowserToUse().indexOf(" \"");
                    if(i != -1)
                        s1 = ZCast.getBrowserToUse().substring(0, i);
                    else
                        s1 = ZCast.getBrowserToUse();
                    ZCast.displayDebug("the browser to use is " + s1);
                    if(s1.indexOf("Netscape") >= 0)
                        Thread.sleep(1000L);
                    OSDetectNative.showUrl(s, true);
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug("excpetion in getRuntime()");
                    ZCast.displayDebug(exception);
                }
            } else
            {
                ZCast.displayDebug("Right before showURL in clickThru same browser " + s);
                OSDetectNative.showUrl(s);
            }
            incrAdClickthruCount(1);
        }
        return flag;
    }

    public int getState()
    {
        byte byte0 = 1;
        if(m_fetchError)
            byte0 = -1;
        else
        if(m_fetching || m_adCacheFile == null)
            byte0 = 0;
        return byte0;
    }

    public boolean downloadBanner()
    {
        byte abyte0[] = new byte[4096];
        boolean flag = false;
        m_fetchError = false;
        m_fetching = true;
        if(m_adDisplayType != 4)
        {
            m_adCacheFile = CACHE_FILE_PATH + "Ad" + getShortAdId() + getFileExtension();
            ZCast.displayDebug("playad", "Downloading PlayAd " + getShortAdId() + " type=" + m_adType + " Dsply=" + m_adDisplayType + " into=" + m_adCacheFile);
            try
            {
                String s = getFinalURL(m_adImageString, true);
                URL url = new URL(s);
                URLConnection urlconnection = url.openConnection();
                urlconnection.setUseCaches(false);
                urlconnection.setAllowUserInteraction(false);
                urlconnection.connect();
                BufferedInputStream bufferedinputstream = new BufferedInputStream(urlconnection.getInputStream());
                FileOutputStream fileoutputstream = new FileOutputStream(m_adCacheFile);
                int i;
                while((i = bufferedinputstream.read(abyte0)) != -1) 
                    fileoutputstream.write(abyte0, 0, i);
                bufferedinputstream.close();
                fileoutputstream.close();
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
                m_fetchError = true;
                incrAdErrors(1);
            }
            ZCast.displayDebug("playad", "downloading PlayAd " + getShortAdId() + " result=" + (m_fetchError ^ true));
        } else
        {
            ZCast.displayDebug("playad", "Downloading PlayAd " + getShortAdId() + " type=" + m_adType + " Dsply=" + m_adDisplayType + " into=" + m_adCacheFile);
            ZCast.displayDebug("playad", "Bypass downloading PlayAd " + getShortAdId() + " HTML Ad.");
            m_adCacheFile = "html";
        }
        m_fetching = false;
        return m_fetchError ^ true;
    }

    public boolean fromAdServers(Vector vector)
    {
        boolean flag = false;
        if(vector != null && !vector.isEmpty())
        {
            for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            {
                String s = (String)enumeration.nextElement();
                if(m_adImageString != null && m_adImageString.startsWith(s))
                {
                    flag = true;
                    break;
                }
            }

        } else
        {
            flag = true;
        }
        return flag;
    }

    public int getAdDisplayTime()
    {
        return m_adDisplayTime;
    }

    public int getAdType()
    {
        return m_adType;
    }

    public int getAdDisplayType()
    {
        return m_adDisplayType;
    }

    public String getTargetString()
    {
        return m_targetString;
    }

    public String getCacheFile()
    {
        return m_adCacheFile;
    }

    public String getAdId()
    {
        return m_adId;
    }

    public String getShortAdId()
    {
        if(m_adIdShort == null)
        {
            int i = m_adId.indexOf(38);
            if(i > 0)
                m_adIdShort = m_adId.substring(0, i);
            else
                m_adIdShort = m_adId;
        }
        return m_adIdShort;
    }

    public String getAdImageString()
    {
        return m_adImageString;
    }

    public String getAdUrlString()
    {
        return m_adUrlString;
    }

    public int getAverageClickThruIntv()
    {
        int i = 0;
        if(m_adClickthruCount > 0)
            i = (int)m_avgClickthruTime / (1000 * m_adClickthruCount);
        return i;
    }

    public boolean isStatsChanged()
    {
        return m_statsChanged;
    }

    public String getStatsToString(boolean flag)
    {
        String s = m_adId + "," + m_totalDisplays + "," + m_totalClickThrus + "," + m_totalPrev + "," + m_totalNext + "," + m_totalError + "," + getAverageClickThruIntv() + "," + m_adDisplayCount + "," + m_adClickthruCount + "," + m_adPrevCount + "," + m_adNextCount + "," + m_adErrors;
        if(flag)
        {
            m_statsChanged = false;
            m_adDisplayCount = 0;
            m_adClickthruCount = 0;
            m_adPrevCount = 0;
            m_adNextCount = 0;
            m_adErrors = 0;
        }
        return s;
    }

    protected void incrAdClickthruCount(int i)
    {
        if(!m_fetchError)
        {
            m_adClickthruCount += i;
            m_totalClickThrus += i;
            m_statsChanged = true;
        }
    }

    protected void incrAdDisplayCount(int i)
    {
        if(!m_fetchError)
        {
            m_adDisplayCount += i;
            m_totalDisplays += i;
            m_statsChanged = true;
        }
    }

    protected void incrAdErrors(int i)
    {
        m_adErrors += i;
        m_totalError += i;
        m_statsChanged = true;
    }

    protected void incrAdNextCount(int i)
    {
        if(!m_fetchError)
        {
            m_adNextCount += i;
            m_totalNext += i;
            m_statsChanged = true;
        }
    }

    protected void incrAdPrevCount(int i)
    {
        if(!m_fetchError)
        {
            m_adPrevCount += i;
            m_totalPrev += i;
            m_statsChanged = true;
        }
    }

    protected void incrTargetCount(int i)
    {
        m_targetCount += i;
        m_totalTarget += i;
    }

    protected void setDisplayStartTime()
    {
        Date date = new Date();
        m_displayStartTime = date.getTime();
        m_clicksPerDisplay = 0;
        ZCast.displayDebug("PlayAd: Displaying " + m_adId + " " + date.toString());
    }

    protected Component findHighestParent(Component component, Class class1, Point point)
    {
        Object obj = null;
        if(component != null && !class1.isInstance(component))
        {
            Point point1 = component.getLocation();
            point.setLocation(point1.x, point1.y);
            ZCast.displayDebug(">>>->child name=" + component.getName() + " orig=" + point);
            for(java.awt.Container container = component.getParent(); container != null; container = container.getParent())
            {
                obj = container;
                ZCast.displayDebug(">>>->parent name=" + ((Component) (obj)).getName() + " orig=" + point);
                if(class1.isInstance(obj))
                    break;
                Point point2 = ((Component) (obj)).getLocation();
                point.translate(point2.x, point2.y);
            }

        }
        return ((Component) (obj));
    }

    protected boolean setClickThruTime(long l)
    {
        boolean flag = false;
        if(!m_fetchError && m_displayStartTime > 0L)
            if(m_clicksPerDisplay < m_maxClicksPerDisplay)
            {
                if(m_clicksPerDisplay == 0 || m_clicksPerDisplay > 0 && l - m_clickthruTime >= m_clickLagTime)
                {
                    m_clicksPerDisplay++;
                    m_clickthruTime = l;
                    m_avgClickthruTime += m_clickthruTime - m_displayStartTime;
                    flag = true;
                } else
                {
                    ZCast.displayDebug("ignoring clickthru due to timing");
                }
            } else
            {
                ZCast.displayDebug("ignoring clickthru due to count");
            }
        return flag;
    }

    protected void timeStampAdUrls()
    {
        String s = ServerParms.getParm("Nzts", null);
        if(s != null)
        {
            Calendar calendar = Calendar.getInstance();
            String s1 = calendar.get(1) + "." + (calendar.get(2) + 1) + "." + calendar.get(5) + "." + calendar.get(10) + "." + calendar.get(12) + "." + calendar.get(13);
            m_adImageString = urlTimeStamp(m_adImageString, s1, s);
            m_adUrlString = urlTimeStamp(m_adUrlString, s1, s);
        }
    }

    protected abstract String getFileExtension();

    private void consumeCookie(String s)
    {
        Hashtable hashtable = parseCookie(s);
        if(!hashtable.isEmpty())
        {
            String s1 = (String)hashtable.get("domain");
            String s2 = (String)hashtable.get("path");
            String s3 = (String)hashtable.get("name");
            String s4 = (String)hashtable.get("value");
            String s5 = (String)hashtable.get("expires");
            String s6 = (String)hashtable.get("secure");
            ZCast.displayDebug("cookie", ">>parsed cookie domain: " + s1);
            ZCast.displayDebug("cookie", ">>parsed cookie path: " + s2);
            ZCast.displayDebug("cookie", ">>parsed cookie name: " + s3);
            ZCast.displayDebug("cookie", ">>parsed cookie value: " + s4);
            ZCast.displayDebug("cookie", ">>parsed cookie expires: " + s5);
            ZCast.displayDebug("cookie", ">>parsed cookie secure: " + s6);
            if(OSDetectNative.ieIsDefaultBrowser())
            {
                boolean flag = OSDetectNative.setIECookie(s1, s2, s3, s4, s5, false);
                ZCast.displayDebug("GM", ">> setIEcookie ret: " + flag);
            }
        }
    }

    private Hashtable parseCookie(String s)
    {
        String s1 = "name";
        String s2 = "= ;";
        String s3 = ";";
        String s4 = s2;
        Hashtable hashtable = new Hashtable();
        StringTokenizer stringtokenizer = new StringTokenizer(s, s2, true);
        try
        {
            while(stringtokenizer.hasMoreTokens()) 
            {
                String s5 = stringtokenizer.nextToken(s4);
                if(s5.equalsIgnoreCase("domain"))
                    s1 = "domain";
                else
                if(s5.equalsIgnoreCase("path"))
                    s1 = "path";
                else
                if(s5.equalsIgnoreCase("expires"))
                    s1 = "expires";
                else
                if(s5.equalsIgnoreCase("secure"))
                {
                    s1 = "secure";
                    hashtable.put(s1, "true");
                } else
                if(s5.equalsIgnoreCase("="))
                    s4 = s3;
                else
                if(s4 == s3)
                {
                    hashtable.put(s1, s5);
                    s4 = s2;
                } else
                if(!s5.equals("=") && !s5.equals(";") && !s5.equals(" "))
                {
                    hashtable.put("name", s5);
                    s1 = "value";
                }
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("cookie", "\n>>Exception parsing COOKIE: " + s);
            ZCast.displayDebug(exception);
        }
        return hashtable;
    }

    private Cookie getGmCookieInfo()
    {
        return getCookieInfo(Initializer.g_gmCookieDomain);
    }

    private Cookie getCookieInfo(String s)
    {
        Cookie cookie = null;
        if(s != null)
        {
            String s1 = OSDetectNative.getSpecificCookie(s);
            if(s1 == null)
            {
                ZCast.displayDebug("cookie", "-->no cookie found in browser for " + s);
            } else
            {
                cookie = new Cookie(s1);
                cookie.setExpires("");
                ZCast.displayDebug("cookie", "--> Ad " + getShortAdId() + " Cookie = " + cookie.toString());
            }
        }
        return cookie;
    }

    private String getFinalURL(String s, boolean flag)
    {
        ZCast.displayDebug("cookie", "\nURL to connect to " + s);
        try
        {
            URL url = new URL(s);
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            HttpURLConnection.setFollowRedirects(false);
            httpurlconnection.setUseCaches(false);
            httpurlconnection.setAllowUserInteraction(false);
            if(flag)
                setImageCookies(httpurlconnection);
            int i = httpurlconnection.getResponseCode();
            if(ZCast.m_nzDebugMode == "on")
            {
                ZCast.displayDebug("cookie", "    URL: " + s);
                ZCast.displayDebug("cookie", "Message: " + httpurlconnection.getResponseMessage());
                ZCast.displayDebug("cookie", "   Code: " + i);
                ZCast.displayDebug("cookie", "   ---(headers)-------------------------");
                for(int j = 0; j < 10; j++)
                {
                    if(httpurlconnection.getHeaderField(j) == null)
                        break;
                    ZCast.displayDebug("cookie", "      Header (" + httpurlconnection.getHeaderFieldKey(j) + "): " + httpurlconnection.getHeaderField(j));
                }

            }
            if(i == 301 || i == 302 || i == 303 || i == 302)
            {
                String s1 = httpurlconnection.getHeaderField("Location");
                ZCast.displayDebug("cookie", "\n..... Location header=" + s1);
                if(!s1.startsWith("http://"))
                {
                    int k = s.indexOf(47, 7);
                    if(k != -1)
                        s = s.substring(0, k) + s1;
                } else
                {
                    s = s1;
                }
                ZCast.displayDebug("cookie", "\n..... following redirect to " + s);
                return getFinalURL(s, flag);
            }
        }
        catch(Exception _ex)
        {
            return s;
        }
        return s;
    }

    private void setImageCookies(URLConnection urlconnection)
    {
        Object obj = null;
        URL url = urlconnection.getURL();
        if(Initializer.g_gmAdDomain == null)
        {
            ZCast.displayDebug("cookie", "Cookie: Ignoring cookies.");
            return;
        }
        ZCast.displayDebug("cookie", "GM Domain = " + Initializer.g_gmAdDomain);
        if(obj == null)
        {
            Cookie cookie = getCookieInfo(url.getHost());
            ZCast.displayDebug("PlayAd", "<><>--> Searching for " + url.getHost() + " from " + url.toString());
            if(cookie != null)
            {
                urlconnection.setRequestProperty("Cookie", cookie.toURLHeaderString());
                ZCast.displayDebug("PlayAd", "<><>--> Set Ad" + url.toString() + " cookie = " + cookie.toString());
            } else
            {
                ZCast.displayDebug("PlayAd", "<><>--> Cookie for " + url.toString() + " was null... wait for it to be downloaded");
            }
        }
        String s = urlconnection.getHeaderField("Set-Cookie");
        if(s != null)
        {
            ZCast.displayDebug("cookie", "--> Setting Cookie for Ad " + getShortAdId() + ", URL " + m_adImageString);
            ZCast.displayDebug("cookie", "--> cookie is " + s);
            consumeCookie(s);
        }
    }

    private String urlTimeStamp(String s, String s1, String s2)
    {
        String s3 = s;
        int i = s.indexOf(s2 + "=");
        if(i >= 0)
        {
            int j = s.indexOf("&", i);
            s3 = s.substring(0, i) + s2 + "=" + s1;
            if(j >= 0)
                s3 = s3 + s.substring(j);
        }
        return s3;
    }

    protected String m_adId;
    protected String m_adIdShort;
    protected String m_adImageString;
    protected String m_adUrlString;
    protected int m_adType;
    protected int m_adDisplayType;
    protected int m_adDisplayCount;
    protected int m_adPrevCount;
    protected int m_adNextCount;
    protected int m_adErrors;
    protected String m_adCacheFile;
    protected boolean m_fetchError;
    protected boolean m_fetching;
    protected boolean m_statsChanged;
    protected int m_adDisplayTime;
    protected String m_targetString;
    protected int m_targetCount;
    protected long m_displayStartTime;
    protected int m_extendedTime;
    protected long m_clickthruTime;
    protected int m_adClickthruCount;
    protected long m_avgClickthruTime;
    protected int m_clicksPerDisplay;
    protected int m_maxClicksPerDisplay;
    protected long m_clickLagTime;
    protected int m_totalClickThrus;
    protected int m_totalDisplays;
    protected int m_totalPrev;
    protected int m_totalNext;
    protected int m_totalError;
    protected int m_totalTarget;
    protected Vector m_displayListenerList;
    public static final int ERROR = -1;
    public static final int NOT_READY = 0;
    public static final int READY = 1;
    private static final String CACHE_DIRECTORY_NAME = "cache";
    private static final String CACHE_FILENAME_PREFIX = "Ad";
    private static final String CACHE_FILE_PATH;
    private boolean played;
    protected static int TIME_AFTER_INTERACTION = Integer.parseInt(ServerParms.getParm("Time_After_Interaction", "8"));
    private static final char SEP_CHAR = 38;

    static 
    {
        CACHE_FILE_PATH = "cache" + File.separator;
    }
}
