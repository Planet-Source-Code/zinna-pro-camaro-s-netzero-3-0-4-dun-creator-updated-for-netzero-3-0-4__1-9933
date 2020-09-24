// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cookie.java

package util;

import java.util.*;
import nzcom.ZCast;

public class Cookie
{

    public Cookie()
    {
        this(null);
    }

    public Cookie(String s)
    {
        path = "/";
        secure = false;
        expires = null;
        domain = null;
        names = null;
        names = new Hashtable();
        if(s != null)
            parseCookie(s);
    }

    public void addNameValue(String s, String s1)
    {
        names.put(s, s1);
    }

    public String getDomain()
    {
        return domain;
    }

    public String getExpires()
    {
        return expires;
    }

    public String getFirstName()
    {
        Enumeration enumeration = names.keys();
        return (String)enumeration.nextElement();
    }

    public Hashtable getNames()
    {
        return names;
    }

    public String getNameValue(String s)
    {
        return (String)names.get(s);
    }

    public Enumeration getNameValues()
    {
        return names.keys();
    }

    public String getPath()
    {
        return path;
    }

    public boolean isSecure()
    {
        return secure;
    }

    private void parseCookie(String s)
    {
        String s1 = " ;=";
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, s1); stringtokenizer.hasMoreTokens();)
        {
            String s2 = stringtokenizer.nextToken();
            if(s2.equals("path"))
                setPath(stringtokenizer.nextToken());
            else
            if(s2.equals("secure"))
                setSecure(true);
            else
            if(s2.equals("domain"))
                setDomain(stringtokenizer.nextToken());
            else
            if(s2.equals("expires"))
                try
                {
                    setExpires(stringtokenizer.nextToken());
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug(exception);
                }
            else
                addNameValue(s2, stringtokenizer.nextToken());
        }

        Object obj = null;
    }

    public void setDomain(String s)
    {
        domain = s;
    }

    public void setExpires(String s)
    {
        expires = s;
    }

    public void setExpires(Calendar calendar)
    {
        String s = "";
        int i = calendar.get(7);
        switch(i)
        {
        case 2: // '\002'
            s = "Mon";
            break;

        case 3: // '\003'
            s = "Tue";
            break;

        case 4: // '\004'
            s = "Wed";
            break;

        case 5: // '\005'
            s = "Thu";
            break;

        case 6: // '\006'
            s = "Fri";
            break;

        case 7: // '\007'
            s = "Sat";
            break;

        case 1: // '\001'
            s = "Sun";
            break;
        }
        String s1 = s + ", " + calendar.get(5) + "-" + calendar.get(2) + "-" + (calendar.get(1) + 1) + " " + calendar.get(11) + ":" + calendar.get(12) + ":" + calendar.get(13) + " " + calendar.getTimeZone().getID();
        ZCast.displayDebug("Setting expire date to: " + s1);
        setExpires(s1);
    }

    public void setNames(Hashtable hashtable)
    {
        names = hashtable;
    }

    public void setPath(String s)
    {
        path = s;
    }

    public void setSecure(boolean flag)
    {
        secure = flag;
    }

    public String toString()
    {
        return "nzcom.Cookie [" + toURLHeaderString() + "]";
    }

    public String toURLHeaderString()
    {
        String s = "";
        if(names.size() > 0)
        {
            for(Enumeration enumeration = names.keys(); enumeration.hasMoreElements();)
            {
                String s1 = (String)enumeration.nextElement();
                String s2 = (String)names.get(s1);
                s = s + s1 + "=" + s2;
                if(enumeration.hasMoreElements())
                    s = s + "; ";
            }

        }
        if(isSecure())
            s = s + "secure";
        if(getPath() != null)
            s = s + "; path=" + getPath();
        if(getDomain() != null)
            s = s + "; domain=" + getDomain();
        if(getExpires() != null)
            s = s + "; expires=" + getExpires();
        return s;
    }

    private String path;
    private boolean secure;
    private String expires;
    private String domain;
    private Hashtable names;
}
