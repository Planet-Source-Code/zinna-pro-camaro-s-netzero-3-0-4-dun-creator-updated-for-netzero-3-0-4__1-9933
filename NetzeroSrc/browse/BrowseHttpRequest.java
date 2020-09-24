// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BrowseHttpRequest.java

package browse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import nzcom.*;

// Referenced classes of package browse:
//            QueryResults, RealName

public class BrowseHttpRequest
{

    public BrowseHttpRequest()
    {
        rnresponder = "http://responder.us.realnames.com/resolver.dll?action=query&realname=***&xmlschemavers=2.0&locale=en-US";
        rnresolver = "http://\"navigation.us.realnames.com/resolver.dll?action=navigation&realname=***&providerid=114\"";
        customsearch = "http://\"netzero.looksmart.com/r_search?key=***&isp=jl&comefrom=ijl-browse\"";
    }

    public BrowseHttpRequest(Vector vector)
    {
        rnresponder = "http://responder.us.realnames.com/resolver.dll?action=query&realname=***&xmlschemavers=2.0&locale=en-US";
        rnresolver = "http://\"navigation.us.realnames.com/resolver.dll?action=navigation&realname=***&providerid=114\"";
        customsearch = "http://\"netzero.looksmart.com/r_search?key=***&isp=jl&comefrom=ijl-browse\"";
        setUrls(vector);
    }

    private static String buildGetUrl(String s, String s1)
    {
        if(s != null && s1 != null)
        {
            int i = s.indexOf("***");
            if(i != -1)
            {
                StringBuffer stringbuffer = new StringBuffer();
                stringbuffer.append(s.substring(0, i));
                stringbuffer.append(URLEncoder.encode(s1));
                if(i + 3 < s.length())
                    stringbuffer.append(s.substring(i + 3));
                return stringbuffer.toString();
            }
        }
        return null;
    }

    private static String buildGetUrl(String s, Hashtable hashtable)
    {
        StringBuffer stringbuffer = new StringBuffer(s);
        Enumeration enumeration = hashtable.keys();
        if(enumeration.hasMoreElements())
        {
            stringbuffer.append("?");
            String s1 = (String)enumeration.nextElement();
            String s3 = (String)hashtable.get(s1);
            stringbuffer.append(URLEncoder.encode(s1));
            stringbuffer.append("=");
            stringbuffer.append(URLEncoder.encode(s3));
            String s4;
            for(; enumeration.hasMoreElements(); stringbuffer.append(URLEncoder.encode(s4)))
            {
                String s2 = (String)enumeration.nextElement();
                s4 = (String)hashtable.get(s2);
                stringbuffer.append("&");
                stringbuffer.append(URLEncoder.encode(s2));
                stringbuffer.append("=");
            }

        }
        return stringbuffer.toString();
    }

    private String getProperty(String s, String s1)
        throws StringIndexOutOfBoundsException
    {
        int i = s.indexOf(s1);
        int j = s.indexOf(34, i);
        int k = s.indexOf(34, j + 1);
        return s.substring(j + 1, k);
    }

    public String getResolverUrl()
    {
        return rnresolver;
    }

    public String getResponderUrl()
    {
        return rnresponder;
    }

    public QueryResults getResult()
    {
        return aResult;
    }

    public String getSearchUrl()
    {
        return customsearch;
    }

    public void gotoRealName(String s)
    {
        Vector vector = httpRequest(getResponderUrl(), s);
        parseXML(vector);
        showRealName(s);
    }

    public static Vector httpRequest(String s)
    {
        Vector vector = new Vector();
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setAllowUserInteraction(false);
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
            do
            {
                String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                ZCast.displayDebug(s1);
                vector.addElement(s1.trim());
            } while(true);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return vector;
    }

    public static Vector httpRequest(String s, String s1)
    {
        String s2 = buildGetUrl(s, s1);
        return httpRequest(s2);
    }

    public static Vector httpRequest(String s, Hashtable hashtable)
    {
        String s1 = buildGetUrl(s, hashtable);
        return httpRequest(s1);
    }

    public static void main(String args[])
    {
        BrowseHttpRequest browsehttprequest = new BrowseHttpRequest();
        ZCast.displayDebug(buildGetUrl(browsehttprequest.getResponderUrl(), "ford explorer"));
        ZCast.displayDebug(buildGetUrl(browsehttprequest.getResolverUrl(), "ford explorer"));
        ZCast.displayDebug(buildGetUrl(browsehttprequest.getSearchUrl(), "ford explorer"));
    }

    private int nextRecord(Vector vector, int i, QueryResults queryresults)
    {
        RealName realname = new RealName();
        try
        {
            String s = (String)vector.elementAt(i++);
            if(s.equalsIgnoreCase("<record>"))
            {
                String s1 = (String)vector.elementAt(i++);
                if(s1.startsWith("<realname"))
                    try
                    {
                        realname.setName(getProperty(s1, "nm"));
                        realname.setLocale(getProperty(s1, "lc"));
                        realname.setRN(getProperty(s1, "isrn"));
                        realname.setType(getProperty(s1, "ty"));
                        queryresults.addRealName(realname);
                    }
                    catch(Exception _ex) { }
                do
                    s1 = (String)vector.elementAt(i++);
                while(!s1.equalsIgnoreCase("</record>"));
                return i;
            }
        }
        catch(Exception _ex) { }
        return -1;
    }

    public void parseXML(Vector vector)
    {
        QueryResults queryresults = null;
        int i = 0;
        try
        {
            while(i < vector.size()) 
            {
                String s = (String)vector.elementAt(i++);
                if(s.startsWith("<queryresults"))
                {
                    String s2 = getProperty(s, "query");
                    String s3 = getProperty(s, "ty");
                    int j = Integer.parseInt(getProperty(s, "len"));
                    int k = Integer.parseInt(getProperty(s, "rns"));
                    queryresults = new QueryResults();
                    queryresults.setQuery(s2);
                    queryresults.setType(s3);
                    queryresults.setLen(j);
                    queryresults.setRns(k);
                    break;
                }
            }
            if(queryresults != null)
                do
                {
                    String s1 = (String)vector.elementAt(i);
                    if(s1.equals("</queryresults>"))
                        break;
                    i = nextRecord(vector, i, queryresults);
                } while(i != -1 && i < vector.size());
        }
        catch(Exception exception1)
        {
            ZCast.displayDebug(exception1);
        }
        finally
        {
            aResult = queryresults;
        }
    }

    public void setUrls(Vector vector)
    {
        if(vector != null && vector.size() > 6)
        {
            Object obj = vector.elementAt(6);
            if(obj != null && (obj instanceof String))
                rnresponder = (String)obj;
            if(vector.size() > 7)
            {
                Object obj1 = vector.elementAt(7);
                if(obj1 != null && (obj1 instanceof String))
                    rnresolver = (String)obj1;
            }
            if(vector.size() > 8)
            {
                Object obj2 = vector.elementAt(8);
                if(obj2 != null && (obj2 instanceof String))
                    customsearch = (String)obj2;
            }
        }
    }

    private void showRealName(String s)
    {
        if(aResult != null && aResult.isExactMatch())
        {
            RealName realname = aResult.getRealName(0);
            if(realname != null && realname.getName() != null && realname.isRN())
            {
                String s1 = buildGetUrl(getResolverUrl(), realname.getName());
                Initializer.m_zwindow.showAdUrl(s1);
                return;
            }
        }
        showSearch(s);
    }

    public void showSearch(String s)
    {
        String s1 = buildGetUrl(getSearchUrl(), s);
        Initializer.m_zwindow.showAdUrl(s1);
    }

    private QueryResults aResult;
    private String rnresponder;
    private String rnresolver;
    private String customsearch;
}
