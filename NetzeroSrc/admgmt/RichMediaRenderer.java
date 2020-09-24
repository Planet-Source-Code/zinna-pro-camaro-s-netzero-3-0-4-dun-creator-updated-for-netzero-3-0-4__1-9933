// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RichMediaRenderer.java

package admgmt;

import java.awt.Rectangle;

// Referenced classes of package admgmt:
//            RichMediaListener

public interface RichMediaRenderer
{

    public abstract boolean play(String s);

    public abstract boolean playFlash(String s, int i, int j, String s1);

    public abstract boolean setTarget(String s, Rectangle rectangle);

    public abstract void releaseTarget();

    public abstract void addRichMediaListener(RichMediaListener richmedialistener);

    public abstract void removeRichMediaListener(RichMediaListener richmedialistener);
}
