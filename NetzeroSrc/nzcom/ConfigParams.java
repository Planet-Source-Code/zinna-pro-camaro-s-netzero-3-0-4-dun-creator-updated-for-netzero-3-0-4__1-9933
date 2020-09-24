// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigParams.java

package nzcom;

import java.io.*;
import java.util.StringTokenizer;

// Referenced classes of package nzcom:
//            ZCast, OSDetectNative

public class ConfigParams
{

    private static boolean checkAuth(long l)
    {
        return l == 0x147ebabL;
    }

    public static void destroy()
    {
        cities = null;
        num1 = null;
        ranks = null;
        System.gc();
    }

    public static String getAdUrlHost(long l)
    {
        if(checkAuth(l))
            return "imgs.netzero.net";
        else
            return null;
    }

    public static String[] getAllCities()
    {
        return cities;
    }

    public static String[] getAllNumbers()
    {
        return num1;
    }

    public static String getCity(int i)
    {
        if(i >= 0 && i < cities.length)
            return cities[i];
        else
            return "";
    }

    public static String getDialEntry(long l)
    {
        if(checkAuth(l))
            return "NetZero";
        else
            return null;
    }

    public static String[] getNumAndRankByCity(String s)
    {
        int ai[] = new int[20];
        int i = 0;
        ZCast.displayDebug("find matches for " + s);
        for(int j = 0; j < cities.length; j++)
            if(s.equals(cities[j].substring(2) + ", " + cities[j].substring(0, 2)))
            {
                ZCast.displayDebug("YES!");
                ai[i] = j;
                i++;
            }

        ZCast.displayDebug("NumMatches=" + i);
        if(i > 0)
        {
            String as[] = new String[i];
            for(int k = 0; k < i; k++)
            {
                as[k] = num1[ai[k]];
                as[k] = as[k].concat("|");
                ZCast.displayDebug("append rank " + ranks[ai[k]]);
                as[k] = as[k].concat(ranks[ai[k]]);
                ZCast.displayDebug("nums[i] is " + as[k]);
            }

            return as;
        } else
        {
            return null;
        }
    }

    public static String getNumber(int i)
    {
        if(i >= 0 && i < num1.length)
            return num1[i];
        else
            return "";
    }

    public static String getNumberRank(int i)
    {
        if(i >= 0 && i < ranks.length)
            return ranks[i];
        else
            return "";
    }

    public static String[] getNumbers(String s)
    {
        int ai[] = new int[12];
        int k = 0;
        for(int i = 0; i < num1.length; i++)
            if(s.equals(num1[1].substring(0, 2)))
                ai[k++] = i;

        if(k > 0)
        {
            String as[] = new String[k];
            for(int j = 0; j < k; j++)
                as[j] = num1[ai[j]].substring(3);

            return as;
        } else
        {
            return null;
        }
    }

    public static String getPassword(long l)
    {
        if(checkAuth(l))
            return OSDetectNative.unmangle("QLaQ4LWfQgggQfgQ");
        else
            return null;
    }

    public static String getPhoneVers()
    {
        return "1.4.1";
    }

    public static String getUserid(long l)
    {
        if(checkAuth(l))
            return OSDetectNative.unmangle("yLyCTQy1yfa1y7fL7MCyy7fgTggTQMQMQgfggTTgTgMggT");
        else
            return null;
    }

    public static String getVers()
    {
        if(vsource.equals("Z") || vsource == null)
            return vers;
        else
            return vers + vsource;
    }

    public static String getVSource()
    {
        return vsource;
    }

    public static boolean initalize(File file)
    {
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
            if(bufferedreader != null)
            {
                String s = bufferedreader.readLine();
                String s1 = bufferedreader.readLine();
                String s2 = bufferedreader.readLine();
                if(s != null)
                    parseCities(s);
                if(s1 != null)
                    parseNumbers(s1);
                if(s2 != null)
                    parseRanks(s2);
                bufferedreader.close();
                return true;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return false;
    }

    public static void parseCities(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "|");
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        int j = 0;
        while(stringtokenizer.hasMoreTokens()) 
            as[j++] = stringtokenizer.nextToken();
        cities = as;
    }

    public static void parseNumbers(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "|");
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        int j = 0;
        while(stringtokenizer.hasMoreTokens()) 
            as[j++] = stringtokenizer.nextToken();
        num1 = as;
    }

    public static void parseRanks(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "|");
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        int j = 0;
        while(stringtokenizer.hasMoreTokens()) 
            as[j++] = stringtokenizer.nextToken();
        ranks = as;
    }

    public static void setAllNumbers(String as[])
    {
        num1 = as;
    }

    public ConfigParams()
    {
    }

    public static final String isp[] = {
        "GTE", "AGIS"
    };
    private static String cities[] = null;
    private static String num1[] = null;
    private static String ranks[] = null;
    private static final String spwd = "QLaQ4LWfQgggQfgQ";
    private static final String suid = "yLyCTQy1yfa1y7fL7MCyy7fgTggTQMQMQgfggTTgTgMggT";
    private static String vers = "3.0.4";
    private static final String phoneVers = "1.4.1";
    private static String vsource = "Z";

}
