// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateFormatter.java

package util;

import java.util.Date;

public class DateFormatter
{

    public static String formatDate(Date date)
    {
        int i = 0;
        String s = null;
        do
            try
            {
                s = date.toString();
            }
            catch(Exception _ex)
            {
                i++;
                s = null;
            }
        while(s == null && i < 5);
        String s1 = s.substring(4, 10) + " " + s.substring(24, s.length()) + " " + s.substring(11, 19);
        return s1;
    }

    public DateFormatter()
    {
    }
}
