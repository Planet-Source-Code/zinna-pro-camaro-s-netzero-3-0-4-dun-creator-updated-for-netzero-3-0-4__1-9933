// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Formatter.java

package util;

import java.util.*;
import servers.UbberServer;

public class Formatter
{

    private static String char20String()
    {
        return "\024";
    }

    private static String char22String()
    {
        return "\026";
    }

    public static Vector conRadiusToVector(String str)
    {
        Vector returnVector = new Vector();
        String radiusString = null;
        try
        {
            if(str == null || str.length() == 0)
                return null;
            if(str.indexOf(":") > 0)
                radiusString = str.substring(str.indexOf(":"), str.length());
            else
                radiusString = str.substring(str.indexOf(" "), str.length());
            StringTokenizer anSt = new StringTokenizer(radiusString, " ");
            int count = anSt.countTokens();
            for(int i = 0; i < count; i++)
                returnVector.addElement(anSt.nextToken());

        }
        catch(Exception e)
        {
            UbberServer.logException(e, str);
            returnVector = null;
        }
        return returnVector;
    }

    public static Vector formatCSVRecords(String aString)
    {
        StringTokenizer anSt = new StringTokenizer(aString, ",", true);
        int anInt = anSt.countTokens();
        boolean missedToken = false;
        Vector aVector = new Vector();
        for(int i = 0; i < anInt; i++)
        {
            String token = anSt.nextToken().trim();
            if(token.equals(","))
            {
                if(missedToken)
                    aVector.addElement(null);
                else
                    missedToken = true;
            } else
            {
                aVector.addElement(token);
                missedToken = false;
            }
        }

        return aVector;
    }

    public static String formatDateString(Date aDate)
    {
        String newString = null;
        String aString = null;
        int reTryCount = 0;
        do
            try
            {
                aString = aDate + "";
            }
            catch(Exception _ex)
            {
                UbberServer.logException(new Exception("DateToStringError"), "formateDateString()");
                reTryCount++;
            }
        while(aString == null && reTryCount < 5);
        newString = aString.substring(4, 10) + " " + aString.substring(24, aString.length()) + " " + aString.substring(11, 19);
        return newString;
    }

    public static Vector formatRecords(String aString)
    {
        StringTokenizer anSt = new StringTokenizer(aString, char22String());
        int anInt = anSt.countTokens();
        Vector aVector = new Vector();
        for(int i = 0; i < anInt; i++)
            aVector.addElement(anSt.nextToken());

        return aVector;
    }

    public static Vector formatRecords(Vector aVector)
    {
        Vector keyVector = new Vector();
        Vector valueVector = new Vector();
        Vector returnVector = new Vector();
        returnVector.addElement(keyVector);
        returnVector.addElement(valueVector);
        String memberString = (String)aVector.elementAt(0);
        StringTokenizer anSt = new StringTokenizer(memberString, char20String());
        int anInt = anSt.countTokens();
        for(int i = 0; i < anInt; i++)
        {
            String profileElementString = anSt.nextToken();
            int anIndex = profileElementString.indexOf(char22String());
            String memberInfoString = profileElementString.substring(0, anIndex);
            String dataString = profileElementString.substring(memberInfoString.length() + 1, profileElementString.length());
            keyVector.addElement(memberInfoString);
            valueVector.addElement(dataString);
        }

        return returnVector;
    }

    public static int getRawVersionNumber(String aString)
    {
        String str = new String();
        String versionString = aString.toUpperCase();
        for(int i = 0; i < versionString.length(); i++)
        {
            char ch = versionString.charAt(i);
            if(ch >= '0' && ch <= '9')
                str = str + ch;
        }

        return Integer.parseInt(str);
    }

    public static int getVersionNumber(String aString)
    {
        String str = new String();
        int versionMulitplier = 0;
        String versionString = aString.toUpperCase();
        for(int i = 0; i < versionString.length(); i++)
        {
            char ch = versionString.charAt(i);
            if(ch >= '0' && ch <= '9')
                str = str + ch;
            else
            if(ch == 'Q')
                versionMulitplier = 1;
            else
            if(ch == 'X')
                versionMulitplier = 2;
        }

        return Integer.parseInt(str) + 1000 * versionMulitplier;
    }

    public static boolean isNullObject(Object obj)
    {
        return obj == null;
    }

    public static boolean newVersionNumber(String aString)
    {
        String str = new String();
        for(int i = 0; i < aString.length(); i++)
        {
            char ch = aString.charAt(i);
            if(ch >= '0' && ch <= '9')
                str = str + ch;
        }

        return Integer.parseInt(str) > 161;
    }

    public static Vector stringToVector(String str)
    {
        Vector returnVector = new Vector();
        if(str == null || str.length() == 0)
            return returnVector;
        String memberString = str;
        StringTokenizer anSt = new StringTokenizer(memberString, "\024");
        int count = anSt.countTokens();
        for(int i = 0; i < count; i++)
            returnVector.addElement(anSt.nextToken());

        return returnVector;
    }

    public static String vectorToString(Vector vect)
    {
        String strReturn = new String("");
        if(vect == null)
            return strReturn;
        for(Enumeration e = vect.elements(); e.hasMoreElements();)
        {
            Integer i = (Integer)e.nextElement();
            strReturn = strReturn + i.toString() + '\024';
        }

        return strReturn;
    }

    public Formatter()
    {
    }
}
