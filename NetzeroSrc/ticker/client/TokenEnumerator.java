// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TokenEnumerator.java

package ticker.client;

import java.util.*;

final class TokenEnumerator
    implements Enumeration
{

    TokenEnumerator(Vector vector, Vector vector1)
    {
        tokens = vector;
        ads = vector1;
        indexA = 0;
        indexT = 0;
    }

    public boolean hasMoreElements()
    {
        return ads != null && ads.size() > 0 && indexA == 0 || tokens != null && indexT < tokens.size();
    }

    public Object nextElement()
    {
        if(indexA == 0 && ads != null && ads.size() > 0)
            return ads.elementAt(indexA++);
        if(tokens != null)
            synchronized(tokens)
            {
                Object obj = tokens.elementAt(indexT++);
                return obj;
            }
        else
            throw new NoSuchElementException("TickerEnumerator");
    }

    private Vector tokens;
    private Vector ads;
    private int indexA;
    private int indexT;
}
