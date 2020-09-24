// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FetchRequest.java

package ticker;

import java.io.Serializable;

public interface FetchRequest
    extends Serializable
{

    public abstract Integer getItemId();

    public abstract int getStatus();

    public abstract String getWanted();

    public static final int FIRSTFETCH = 0;
    public static final int GETITEMS = 1;
    public static final int LOOKUP = 2;
}
