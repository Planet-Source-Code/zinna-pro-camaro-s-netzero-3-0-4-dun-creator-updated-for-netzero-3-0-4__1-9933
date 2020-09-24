// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientTickerToken.java

package ticker;

import java.awt.Graphics;
import java.awt.Rectangle;

// Referenced classes of package ticker:
//            TickerToken, TickerTokenInterface

public abstract class ClientTickerToken extends TickerToken
    implements TickerTokenInterface
{

    public ClientTickerToken()
    {
        rect = null;
        width = -1;
        positionInImage = -1;
        rect = new Rectangle(0, 0, 0, 0);
    }

    public Rectangle getBoundingRect()
    {
        return rect;
    }

    public int getPositionInImage()
    {
        return positionInImage;
    }

    protected void setBoundingRect(int i, int j, int k, int l)
    {
        rect = new Rectangle(i, j, k, l);
    }

    public void setPositionInImage(int i)
    {
        positionInImage = i;
    }

    public void translate(int i)
    {
        rect.translate(i, 0);
    }

    public abstract int getWidth();

    public abstract int render(Graphics g, int i);

    private Rectangle rect;
    protected int width;
    protected int positionInImage;
}
