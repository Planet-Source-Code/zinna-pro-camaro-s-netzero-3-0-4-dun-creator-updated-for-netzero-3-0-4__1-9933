// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoolTypes.java

package pool;


public final class PoolTypes
{

    protected static String getBumper(int i)
    {
        String s = BUMPER[i];
        return s;
    }

    protected static String getDefault(int i)
    {
        String s = DEFAULT[i];
        return s;
    }

    protected static String getDefaultId(int i)
    {
        String s = DEFAULTID[i];
        return s;
    }

    public PoolTypes()
    {
    }

    public static final String DEFAULT_USER = "DEFAULT";
    public static final String DEFAULT_ADID = "DEFAD";
    public static final String POOL_PATH = ".\\pool";
    public static final String DEFAULT[] = {
        "NONE;\n", "NONE;\n", "21;13828&1141&2405&958&1*21.13828;http://imgs.netzero.net/webads/netzeroin/nztvprogress5loop.gif;http://www.myzstart.com/;30;03/01/2000;03/01/2010;\n", "30;9610&1141&2405&958&1*30.9610;http://imgs.netzero.net/webads/default/nztv_bumper_still.gif;http://www.myzstart.com/;30;03/01/2000;03/01/2010;\n", "40;9612&1141&2405&958&1*40.9612;http://imgs.netzero.net/webads/clubzeropromo/new_club.gif;http://www.myzstart.com/;30;03/01/2000;03/01/2000;\n"
    };
    public static final String BUMPER[] = {
        "NONE;\n", "NONE;\n", "21;9611&1141&2405&958&1*21.9611;http://imgs.netzero.net/webads/default/nztv_bumper_anima2.gif;http://www.myzstart.com/;5;03/01/2000;03/01/2010;\n", "NONE;\n", "NONE;\n"
    };
    public static final String DEFAULTID[] = {
        "NONE;\n", "NONE;\n", "ZERIN", "DEFAD", "DEFAD"
    };

}
