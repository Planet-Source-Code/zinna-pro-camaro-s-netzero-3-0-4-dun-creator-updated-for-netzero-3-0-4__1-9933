// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerThreadInterface.java

package tcpBinary;


public interface ServerThreadInterface
{

    public abstract void decreaseThreadCount();

    public abstract void increaseThreadCount();

    public abstract void writeTimesLog(String s);
}
