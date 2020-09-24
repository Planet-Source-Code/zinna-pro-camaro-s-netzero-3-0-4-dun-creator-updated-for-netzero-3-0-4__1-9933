// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerTokenInterface.java

package ticker;

import java.awt.Graphics;

public interface TickerTokenInterface
{

    public abstract int getWidth();

    public abstract int render(Graphics g, int i);
}
