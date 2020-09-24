// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayListConsumer.java

package admgmt;


public interface PlayListConsumer
{

    public abstract String toString();

    public abstract void newPlayListNotification(boolean flag);
}
