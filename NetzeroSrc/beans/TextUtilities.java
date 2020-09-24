// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextUtilities.java

package beans;

import java.util.ResourceBundle;

public class TextUtilities
{

    public TextUtilities()
    {
    }

    public static int classifyChar(char c, String s)
    {
        if(c >= '0' && c <= '9')
            return 1;
        if(c >= 'A' && c <= 'Z')
            return 2;
        if(c >= 'a' && c <= 'z')
            return 2;
        if(s != null && s.indexOf(c) > -1)
            return 8;
        return printable.indexOf(c) <= -1 ? 0 : 4;
    }

    private static ResourceBundle resNZResource;
    private static final String printable;

    static 
    {
        resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
        printable = resNZResource.getString(",.<>?;_'[]{}|-__+/)(*&^%$#");
    }
}
