// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VideoRenderer.java

package video;

import java.awt.Rectangle;

public interface VideoRenderer
{

    public abstract boolean init();

    public abstract boolean play(String s);

    public abstract boolean stop();

    public abstract boolean setVolume(int i, int j);

    public abstract boolean setTarget(String s, Rectangle rectangle);

    public abstract boolean isPlaying();

    public abstract void cleanup();
}
