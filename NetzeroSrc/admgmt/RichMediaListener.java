// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RichMediaListener.java

package admgmt;


public interface RichMediaListener
{

    public abstract void navigatingTo(String s);

    public abstract void downloadComplete(String s);

    public abstract void interacting();
}
