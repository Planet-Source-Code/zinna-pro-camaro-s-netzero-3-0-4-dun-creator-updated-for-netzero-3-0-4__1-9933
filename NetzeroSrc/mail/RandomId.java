// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomId.java

package mail;

import java.util.Date;
import java.util.Random;

public class RandomId
{

    private RandomId(byte abyte0[], String s)
    {
        strId = null;
        id = null;
        id = abyte0;
        strId = s;
    }

    public byte[] getId()
    {
        return id;
    }

    public static RandomId getRandomId()
    {
        byte abyte0[] = new byte[16];
        Random random = new Random((new Date()).getTime());
        random.nextBytes(abyte0);
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < 16; i++)
        {
            String s = Integer.toHexString(abyte0[i]);
            int j = s.length();
            if(j >= 2)
                stringbuffer.append(s.substring(s.length() - 2));
            else
            if(j == 1)
            {
                stringbuffer.append("0");
                stringbuffer.append(s);
            } else
            {
                stringbuffer.append("00");
            }
        }

        return new RandomId(abyte0, stringbuffer.toString());
    }

    public static RandomId getRandomId(long l)
    {
        byte abyte0[] = new byte[16];
        Random random = new Random(l);
        random.nextBytes(abyte0);
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < 16; i++)
        {
            String s = Integer.toHexString(abyte0[i]);
            int j = s.length();
            if(j >= 2)
                stringbuffer.append(s.substring(s.length() - 2));
            else
            if(j == 1)
            {
                stringbuffer.append("0");
                stringbuffer.append(s);
            } else
            {
                stringbuffer.append("00");
            }
        }

        return new RandomId(abyte0, stringbuffer.toString());
    }

    public static RandomId getRandomId(Random random)
    {
        byte abyte0[] = new byte[16];
        random.nextBytes(abyte0);
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < 16; i++)
        {
            String s = Integer.toHexString(abyte0[i]);
            int j = s.length();
            if(j >= 2)
                stringbuffer.append(s.substring(s.length() - 2));
            else
            if(j == 1)
            {
                stringbuffer.append("0");
                stringbuffer.append(s);
            } else
            {
                stringbuffer.append("00");
            }
        }

        return new RandomId(abyte0, stringbuffer.toString());
    }

    public String toString()
    {
        return strId;
    }

    private String strId;
    private byte id[];
}
