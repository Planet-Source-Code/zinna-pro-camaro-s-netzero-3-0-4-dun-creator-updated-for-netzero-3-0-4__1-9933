// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TopicItem.java

package ticker;

import java.util.Vector;

public interface TopicItem
{

    public abstract Integer getItemId();

    public abstract String getItemName();

    public abstract Vector getSubItems();

    public abstract boolean hasChildren();

    public abstract void removeChildren();

    public static final char delimiter = 124;
    public static final char index_codes[] = {
        'A', 'C', 'D', 'I', 'Q', 'S', 'Y'
    };
    public static final String index_names[] = {
        "AMEX", "CANADA", "DOW JONES", "INDEX", "NASDAQ", "NASDAQ Sma", "NYSE"
    };

}
