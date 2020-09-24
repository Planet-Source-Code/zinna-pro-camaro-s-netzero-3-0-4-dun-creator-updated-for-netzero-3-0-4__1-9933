// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPPriority.java

package spe;


public final class SPPriority
{

    public static final double getPercent(int i)
    {
        return PERCENT[i];
    }

    public SPPriority()
    {
    }

    public static final int LOWEST = 0;
    public static final int LOW = 1;
    public static final int BELOW_NORMAL = 2;
    public static final int NORMAL = 3;
    public static final int ABOVE_NORMAL = 4;
    public static final int HIGH = 5;
    public static final int HIGHEST = 6;
    public static final double PERCENT[] = {
        0.90000000000000002D, 0.90000000000000002D, 0.90000000000000002D, 0.90000000000000002D, 0.80000000000000004D, 0.59999999999999998D, 0
    };

}
