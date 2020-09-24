// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nsdll.java

package mail;

import nzcom.ZCast;

public class Nsdll
{

    public static native boolean createProfile(String s, String s1);

    public static native String getProfileDir(String s);

    public static native String[] getProfileList();

    public static void main(String args[])
    {
        String args1[] = getProfileList();
        for(int i = 0; i < args1.length; i++)
            ZCast.displayDebug(args1[i]);

    }

    public Nsdll()
    {
    }
}
