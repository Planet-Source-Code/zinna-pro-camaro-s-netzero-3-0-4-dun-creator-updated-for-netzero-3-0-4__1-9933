// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueryResults.java

package browse;

import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package browse:
//            RealName

public class QueryResults
{

    public QueryResults()
    {
    }

    public void addRealName(RealName realname)
    {
        if(realnames == null)
            realnames = new Vector();
        realnames.addElement(realname);
    }

    public void dump()
    {
        ZCast.displayDebug("--- QueryResult object ---");
        ZCast.displayDebug("query: " + getQuery());
        ZCast.displayDebug("len: " + getLen());
        ZCast.displayDebug("rns: " + getRns());
        ZCast.displayDebug("ans: " + getAns());
        ZCast.displayDebug("type: " + getType());
        if(realnames != null)
        {
            for(int i = 0; i < realnames.size(); i++)
                ((RealName)realnames.elementAt(i)).dump();

        }
    }

    public int getAns()
    {
        return ans;
    }

    public int getLen()
    {
        return len;
    }

    public String getQuery()
    {
        return query;
    }

    public RealName getRealName(int i)
    {
        try
        {
            return (RealName)realnames.elementAt(i);
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public int getRns()
    {
        return rns;
    }

    public String getType()
    {
        return ty;
    }

    public boolean isExactMatch()
    {
        try
        {
            return getType().equals("perfect");
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    public void setAns(int i)
    {
        ans = i;
    }

    public void setLen(int i)
    {
        len = i;
    }

    public void setQuery(String s)
    {
        query = s;
    }

    public void setRns(int i)
    {
        rns = i;
    }

    public void setType(String s)
    {
        ty = s;
    }

    private String query;
    private int len;
    private int rns;
    private int ans;
    private String ty;
    Vector realnames;
}
