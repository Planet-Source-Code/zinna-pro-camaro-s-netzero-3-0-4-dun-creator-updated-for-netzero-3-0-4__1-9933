// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerEnumerator.java

package ticker.client;

import java.util.*;
import ticker.ClientTickerToken;

final class TickerEnumerator
    implements Enumeration
{

    TickerEnumerator(Vector vector, Vector vector1)
    {
        tokens = vector;
        ads = vector1;
        index = 0;
        count = 0;
        if(vector != null)
        {
            for(int i = 0; i < vector.size(); i++)
                if(((ClientTickerToken)vector.elementAt(i)).getPositionInImage() != -1)
                    count++;

        }
        if(vector1 != null)
        {
            for(int j = 0; j < vector1.size(); j++)
                if(((ClientTickerToken)vector1.elementAt(j)).getPositionInImage() != -1)
                    count++;

        }
    }

    public boolean hasMoreElements()
    {
        return index < count;
    }

    public Object nextElement()
    {
        if(tokens != null)
            synchronized(tokens)
            {
                for(int i = 0; i < tokens.size(); i++)
                    if(((ClientTickerToken)tokens.elementAt(i)).getPositionInImage() == index)
                    {
                        index++;
                        Object obj = tokens.elementAt(i);
                        return obj;
                    }

            }
        if(ads != null)
            synchronized(ads)
            {
                for(int j = 0; j < ads.size(); j++)
                    if(((ClientTickerToken)ads.elementAt(j)).getPositionInImage() == index)
                    {
                        index++;
                        Object obj1 = ads.elementAt(j);
                        return obj1;
                    }

            }
        throw new NoSuchElementException("TickerEnumerator");
    }

    private Vector tokens;
    private Vector ads;
    private int count;
    private int index;
}
