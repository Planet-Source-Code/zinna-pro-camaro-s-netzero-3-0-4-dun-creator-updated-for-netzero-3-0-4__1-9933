// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPItemListener.java

package spe;


public interface SPItemListener
{

    public abstract void onItemError(String s, Object obj);

    public abstract void onItemFinished(Object obj);
}
