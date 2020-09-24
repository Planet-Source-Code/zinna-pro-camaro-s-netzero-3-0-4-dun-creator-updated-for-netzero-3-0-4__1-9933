// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPEngineListener.java

package spe;


public interface SPEngineListener
{

    public abstract void onEngineError(String s);

    public abstract void onEngineWarning(String s);

    public abstract void onFailedDUNCapabilities();

    public abstract void onEngineFatalException(Exception exception);
}
