// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64.java

package util;


public class Base64
{

    public Base64()
    {
    }

    public static long fromBase64(String s)
    {
        long l = 0L;
        s = s.trim();
        for(int i = 0; i < s.length(); i++)
            l = (l << 6) + (long)digits.indexOf(s.substring(i, i + 1));

        return l;
    }

    public static String toBase64(long l)
    {
        String s = "";
        for(long l1 = 0L; l1 < 11L; l1++)
        {
            long l2 = l >> (int)(l1 * 6L);
            if(l2 == 0L && l1 > 0L)
                break;
            int i = (int)(l2 & 63L);
            s = digits.substring(i, i + 1) + s;
        }

        return s;
    }

    static String digits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ()";

}
