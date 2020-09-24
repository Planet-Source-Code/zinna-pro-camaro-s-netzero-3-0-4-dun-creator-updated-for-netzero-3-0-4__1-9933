// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTServices.java

package admgmt;

import java.io.PrintStream;
import java.util.*;

// Referenced classes of package admgmt:
//            SimpleComm, RTTransaction

public abstract class RTServices
{

    public static void applySettings(String s)
    {
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, SettingsFollow); stringtokenizer.hasMoreTokens();)
        {
            String s1 = stringtokenizer.nextToken();
            int i = s1.indexOf(61);
            if(i >= 0)
            {
                String s2 = s1.substring(i + 1);
                s1 = s1.substring(0, i);
                if(s1.equals("ud"))
                    URLDelims = s2;
                else
                if(s1.equals("dsw"))
                    DomainSkipWords = valueToArray(s2, ",");
                else
                if(s1.equals("isw"))
                    INValidSearchWords = valueToArray(s2, ",");
                else
                if(s1.equals("kwi"))
                    KEYWordIndicater = s2;
                else
                if(s1.equals("kwd"))
                    KEYWordDelim = s2;
                else
                if(s1.equals("wcc"))
                    WILDCardChar = s2;
                else
                if(s1.equals("aid"))
                    ADID_Delim = s2.charAt(0);
                else
                if(s1.equals("unt"))
                    URLNOTTargeted = s2;
                else
                if(s1.equals("mscl"))
                    MINServerCallLaps = Integer.parseInt(s2);
                else
                if(s1.equals("mce"))
                    MAXCacheEntries = Integer.parseInt(s2);
                else
                if(s1.equals("mofs"))
                    MissOKForSecs = Long.parseLong(s2);
                else
                if(s1.equals("npi"))
                    NULLPlaylistID = s2;
                else
                if(s1.equals("sbv"))
                    SECSBetweenVerify = Long.parseLong(s2);
                else
                if(s1.equals("wfms"))
                    SimpleComm.setWaitForMoreSeconds(Float.valueOf(s2).floatValue());
                else
                if(s1.equals("uit"))
                    URLISTargeted = s2;
                else
                if(s1.equals("uidi"))
                    URL_ID_Index = Integer.parseInt(s2);
                else
                if(s1.equals("urli"))
                    URL_Index = Integer.parseInt(s2);
                else
                if(s1.equals("plidi"))
                    PLAYLIST_ID_Index = Integer.parseInt(s2);
                else
                if(s1.equals("adi"))
                    AD_Index = Integer.parseInt(s2);
                else
                if(s1.equals("tfn"))
                    TMPFilename = s2;
                else
                if(s1.equals("aiod"))
                    ADID_ORDER_Delim = s2;
                else
                if(s1.equals("pld"))
                    PLAYList_Delim = s2;
                else
                if(s1.equals("inpd"))
                    InputDelim = s2;
                else
                if(s1.equals("ctd"))
                    CACHEToDisk = s2.equals("false") ^ true;
                else
                if(s1.equals("msl"))
                    MAXSuffixLength = Integer.parseInt(s2);
            }
        }

    }

    public static Object checkDefaults(Hashtable hashtable, String s, StringTokenizer stringtokenizer, boolean flag)
    {
        Object obj = null;
        if(s != null)
            obj = findWild(hashtable, s);
        if(obj == null && !flag)
            obj = hashtable.get(DefaultUrlName);
        if(obj != null && (obj instanceof Hashtable) && stringtokenizer != null && stringtokenizer.hasMoreTokens())
            return findAd((Hashtable)obj, stringtokenizer.nextToken(), stringtokenizer, false);
        else
            return obj;
    }

    public static boolean contains(String s, String as[])
    {
        for(int i = 0; i < as.length; i++)
            if(s.indexOf(as[i]) != -1)
                return true;

        return false;
    }

    public static Object findAd(Hashtable hashtable, String s, StringTokenizer stringtokenizer, boolean flag)
    {
        if(hashtable.isEmpty())
            return null;
        Object obj = hashtable.get(s);
        if(obj != null)
        {
            if(!(obj instanceof Hashtable))
                return obj;
            if(stringtokenizer == null || !stringtokenizer.hasMoreTokens())
                obj = checkDefaults((Hashtable)obj, s, null, false);
            else
                obj = findAd((Hashtable)obj, stringtokenizer.nextToken(), stringtokenizer, false);
            if(obj != null)
                return obj;
        }
        return checkDefaults(hashtable, s, stringtokenizer, flag);
    }

    public static Object findAd(Hashtable hashtable, Hashtable hashtable1, String s)
    {
        s = s.toLowerCase();
        Object obj = null;
        int i = s.indexOf(KEYWordIndicater);
        if(i > 0)
        {
            Object obj1 = searchKeywords(hashtable1, s, i);
            if(obj1 != null)
                return obj1;
        }
        StringTokenizer stringtokenizer = tokenizeUrl(s);
        if(stringtokenizer == null)
            return null;
        String s1 = stringtokenizer.nextToken();
        if(DomainSkipWords.length > 0 && contains(s1, DomainSkipWords))
            return URLNOTTargeted;
        else
            return findAd(hashtable, stripWs(s1), stringtokenizer, true);
    }

    public static Object findWild(Hashtable hashtable, String s)
    {
        Hashtable hashtable1 = null;
        try
        {
            hashtable1 = (Hashtable)hashtable.get(WILDCardOnRight);
        }
        catch(Exception _ex) { }
        if(hashtable1 != null && !hashtable1.isEmpty())
        {
            for(Enumeration enumeration = hashtable1.keys(); enumeration.hasMoreElements();)
            {
                String s1 = (String)enumeration.nextElement();
                if(s.startsWith(s1))
                    return hashtable1.get(s1);
            }

        }
        try
        {
            hashtable1 = (Hashtable)hashtable.get(WILDCardOnLeft);
        }
        catch(Exception _ex) { }
        if(hashtable1 != null && !hashtable1.isEmpty())
        {
            for(Enumeration enumeration1 = hashtable1.keys(); enumeration1.hasMoreElements();)
            {
                String s2 = (String)enumeration1.nextElement();
                if(s.endsWith(s2))
                    return hashtable1.get(s2);
            }

        }
        return null;
    }

    public static Object putAd(Hashtable hashtable, Hashtable hashtable1, Hashtable hashtable2, String s, String s1, String s2)
    {
        if(s1 == null)
            return null;
        Object obj = storeAds(hashtable2, s1, s2);
        String s3 = s.toLowerCase();
        if(s3.startsWith(KEYWordIndicater))
        {
            putAdIds(hashtable1, s3, null, obj);
        } else
        {
            StringTokenizer stringtokenizer = tokenizeUrl(s3);
            if(stringtokenizer != null)
                putAdIds(hashtable, stripWs(stringtokenizer.nextToken()), stringtokenizer, obj);
        }
        return obj;
    }

    public static void putAdIds(Hashtable hashtable, String s, StringTokenizer stringtokenizer, Object obj)
    {
        String s1 = null;
        int i = 0;
        if((i = s.indexOf(WILDCardChar)) != -1)
        {
            if(i > 0)
            {
                s1 = s.substring(0, i);
                s = WILDCardOnRight;
            } else
            {
                s1 = s.substring(1);
                s = WILDCardOnLeft;
            }
        } else
        if(stringtokenizer != null && stringtokenizer.hasMoreTokens())
        {
            s1 = stringtokenizer.nextToken();
            if(s1.equals(WILDCardChar))
                s1 = null;
        }
        Object obj1 = null;
        if(!hashtable.isEmpty())
            obj1 = hashtable.get(s);
        if(s1 == null)
        {
            if(obj1 instanceof Hashtable)
                ((Hashtable)obj1).put(DefaultUrlName, obj);
            else
                hashtable.put(s, obj);
        } else
        {
            Hashtable hashtable1 = null;
            if(obj1 == null)
            {
                hashtable1 = new Hashtable(5);
                hashtable.put(s, hashtable1);
            } else
            if(obj1 instanceof Hashtable)
            {
                hashtable1 = (Hashtable)obj1;
            } else
            {
                hashtable1 = new Hashtable(5);
                hashtable1.put(DefaultUrlName, obj1);
                hashtable.put(s, hashtable1);
            }
            putAdIds(hashtable1, s1, stringtokenizer, obj);
        }
    }

    public static Object searchKeywords(Hashtable hashtable, String s, int i)
    {
        if(hashtable == null || hashtable.isEmpty())
            return null;
        for(; i != -1; i = s.indexOf(KEYWordIndicater))
        {
            Object obj = null;
            s = s.substring(i);
            i = s.indexOf(KEYWordDelim);
            try
            {
                if(i > 0)
                    obj = hashtable.get(s.substring(0, i));
                else
                    obj = hashtable.get(s);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            if(obj != null)
                return obj;
            if(i < 0 || i + 1 >= s.length())
                return null;
            s = s.substring(i + 1);
        }

        return null;
    }

    public static Object storeAds(Hashtable hashtable, String s, String s1)
    {
        if(hashtable == null || s.startsWith(NULLPlaylistID))
            return s1;
        Vector vector = new Vector();
        vector.addElement(s);
        try
        {
            int i = 0;
            Object obj = null;
            Object obj1 = null;
            while(i != -1) 
            {
                i = s1.indexOf(InputDelim);
                String s2;
                if(i == -1)
                {
                    s2 = s1;
                } else
                {
                    s2 = s1.substring(0, i);
                    if(i + 1 < s1.length())
                        s1 = s1.substring(i + 1);
                }
                if(s2.indexOf(FLAGPlayListText) != -1)
                {
                    vector.addElement(s2);
                } else
                {
                    int j = s2.indexOf(ADID_Delim);
                    if(j != -1)
                    {
                        String s3 = s2.substring(0, j);
                        vector.addElement(s3);
                        j = s2.indexOf(ADID_ORDER_Delim);
                        hashtable.put(s3, s2.substring(j + 1));
                    }
                }
            }
        }
        catch(Exception exception)
        {
            System.out.println(String.valueOf(exception) + " storeAds:" + s);
        }
        return vector;
    }

    public static String[] storeClanAds(Hashtable hashtable, String s)
    {
        Vector vector = new Vector();
        for(int i = 0; i != -1;)
            try
            {
                i = s.indexOf(InputDelim);
                String s1 = null;
                if(i > 0)
                {
                    s1 = s.substring(0, i);
                    s = s.substring(i + 1);
                } else
                {
                    s1 = s;
                }
                String s2 = s1.substring(0, s1.indexOf(ADID_Delim));
                hashtable.put(s2, s1);
                vector.addElement(s2);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }

        String as[] = new String[vector.size()];
        int j = 0;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            as[j++] = (String)enumeration.nextElement();

        return as;
    }

    public static String stripWs(String s)
    {
        int i = s.indexOf(46);
        if(i == -1 || i + 1 == s.length())
            return s;
        if(s.substring(0, i).indexOf("www") != -1)
            return s.substring(i + 1);
        else
            return s;
    }

    public static StringTokenizer tokenizeUrl(String s)
    {
        int i = 0;
        if((i = s.indexOf("://")) > 0)
            s = s.substring(i + 3);
        i = s.indexOf(47);
        if(i != -1 && s.length() - i > MAXSuffixLength)
            s = s.substring(0, i + MAXSuffixLength);
        return new StringTokenizer(s, URLDelims);
    }

    protected static String[] urlPlaylistArr(String s)
    {
        int i = AD_Index + 1;
        String as[] = new String[i];
        try
        {
            for(int j = 0; j < i; j++)
            {
                int k = s.indexOf(InputDelim);
                if(k < 0 || j == i - 1)
                {
                    as[j] = s;
                    break;
                }
                as[j] = s.substring(0, k);
                if(k + 1 == s.length())
                    break;
                s = s.substring(k + 1);
            }

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return as;
    }

    public static String[] valueToArray(String s, String s1)
    {
        String as[] = null;
        try
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
            as = new String[stringtokenizer.countTokens()];
            int i = 0;
            while(stringtokenizer.hasMoreTokens()) 
                as[i++] = stringtokenizer.nextToken();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(as != null)
            return as;
        else
            return new String[0];
    }

    public RTServices()
    {
    }

    public static String URLDelims = "/\\";
    public static String DomainSkipWords[] = {
        ""
    };
    public static String INValidSearchWords[] = {
        "xxx", "sex", "adult"
    };
    public static String DefaultUrlName = "[defaultkeysname]";
    public static String KEYWordIndicater = "=";
    public static String KEYWordDelim = "&";
    public static String WILDCardChar = "*";
    public static String WILDCardOnLeft = "[wildcardonleft]";
    public static String WILDCardOnRight = "[wildcardonright]";
    public static String URLNOTTargeted = "miss";
    public static String URLISTargeted = "gogetit";
    public static String SettingsFollow = "@";
    public static String CACHEAdditions = "cacheadds";
    public static int MINServerCallLaps = 5;
    public static int URL_ID_Index = 0;
    public static int URL_Index = 1;
    public static int PLAYLIST_ID_Index = 2;
    public static int AD_Index = 3;
    public static String CACHEDirectory = "cache";
    public static String TMPFilename = "inad.tmp";
    public static String COREFilename = "localv.nz";
    public static String IDSFilename = "idpairs.nz";
    public static int MAXCacheEntries = 1500;
    public static long MissOKForSecs = 0x15180L;
    public static String NULLPlaylistID = "-1";
    public static String ADID_ORDER_Delim = ":";
    public static long SECSBetweenVerify = 0x15180L;
    public static char ADID_Delim = '&';
    public static String PLAYList_Delim = ";";
    public static String InputDelim = "\024";
    public static String ADListDelim = "\n";
    protected static RTTransaction m_rTt = null;
    public static boolean CACHEToDisk = true;
    public static String FLAGPlayListText = "flag=";
    public static int MAXSuffixLength = 50;

}
