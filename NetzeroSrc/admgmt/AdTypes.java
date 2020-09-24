// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdTypes.java

package admgmt;


public final class AdTypes
{

    public static final String getExtention(int i)
    {
        return EXT[i];
    }

    public AdTypes()
    {
    }

    public static final int REGULAR = 0;
    public static final int TARGETED = 1;
    public static final int ZERO_IN = 2;
    public static final int ZERO_OUT = 3;
    public static final int MARQUEE = 4;
    public static final int GIF = 0;
    public static final int GIFA = 1;
    public static final int VIDEO = 2;
    public static final int HTML = 4;
    public static final int FLASH = 6;
    public static final String EXT[] = {
        "GIF", "GIF", "TVI", "", "HTML", "", "SWF", "", "", ""
    };

}
