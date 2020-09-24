// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dll4Mail.java

package mail;

import java.util.StringTokenizer;

public class Dll4Mail
{

    private static native char[] _getPrivateProfileString(String s, String s1, String s2);

    public static native boolean createRegKey(String s, String s1);

    public static native boolean createRegKey(String s, String s1, String s2);

    public static native boolean deleteRegKey(String s, String s1);

    public static String[] getPrivateProfileString(String s, String s1, String s2)
    {
        char ac[] = _getPrivateProfileString(s, s1, s2);
        return parseArrayOfChars(ac, "\0");
    }

    public static native String[] getRegSubkeys(String s, String s1);

    public static native String getRegValueData(String s, String s1, String s2);

    public static native int getSubkeysCount(String s, String s1);

    public static native int isExistingKey(String s, String s1);

    public static native boolean isPrivateProfileString(String s, String s1, String s2);

    static String[] parseArrayOfChars(char ac[], String s)
    {
        String as[] = null;
        try
        {
            StringTokenizer stringtokenizer = new StringTokenizer(new String(ac), s);
            int i = stringtokenizer.countTokens();
            as = new String[i];
            for(int j = 0; j < i; j++)
                as[j] = stringtokenizer.nextToken();

        }
        catch(Exception _ex)
        {
            as = new String[0];
        }
        finally
        {
            return as;
        }
    }

    public static native boolean removeRegBinaryValueData(String s, String s1, String s2, int i, boolean flag);

    public static native boolean setRegBinaryValueData(String s, String s1, String s2, byte abyte0[]);

    public static native boolean setRegDwordValueData(String s, String s1, String s2, int i);

    public static native boolean setRegValueData(String s, String s1, String s2, String s3);

    public static native boolean setupOutlookAccount(String s);

    public static native boolean setupOutlookExpressAccount(String s);

    public static native boolean updateRegBinaryValueData(String s, String s1, String s2, byte abyte0[], boolean flag);

    public static native boolean writePrivateProfileString(String s, String s1, String s2, String s3);

    public Dll4Mail()
    {
    }
}
