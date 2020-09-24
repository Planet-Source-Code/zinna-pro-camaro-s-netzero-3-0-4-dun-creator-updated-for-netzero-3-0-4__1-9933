// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZpHover.java

package zpc;

import java.awt.*;

public class ZpHover extends Window
    implements Runnable
{

    public ZpHover(Frame frame)
    {
        super(frame);
        text = "default";
        showTime = 4000;
        hideTime = 1000;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = 60;
        height = 20;
        fm = Toolkit.getDefaultToolkit().getFontMetrics(new Font("SansSerif", 0, 12));
        thread = new Thread(this, "ZpHover - tool tip");
        thread.start();
    }

    public String getText()
    {
        return text;
    }

    public void interrupt()
    {
        thread.interrupt();
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        g.drawString(text, 3, 15);
    }

    public void run()
    {
        do
            try
            {
                Thread.interrupted();
                setVisible(false);
                Thread.sleep(hideTime);
                setVisible(true);
                Thread.sleep(showTime);
            }
            catch(InterruptedException _ex) { }
            finally
            {
                setVisible(false);
                thread.suspend();
            }
        while(true);
    }

    public void setHideTime(int i)
    {
        hideTime = i;
    }

    public void setLocation()
    {
        int i = mousePos.x + 15;
        int j = mousePos.y + 15;
        if(i + width > screenSize.getSize().width)
            i = screenSize.getSize().width - width - 2;
        if(j + height > screenSize.getSize().height)
            j = screenSize.getSize().height - height - 2;
        setLocation(i, j);
    }

    public void setShowTime(int i)
    {
        showTime = i;
    }

    public void setText(String s)
    {
        text = s;
        width = fm.stringWidth(s) + 6;
        setSize(width, height);
    }

    public void show(Point point, String s)
    {
        if(s != null)
        {
            mousePos = point;
            setText(s);
            setLocation();
            super.show();
            thread.resume();
        }
    }

    private String text;
    private int showTime;
    private int hideTime;
    private Dimension screenSize;
    private int width;
    private int height;
    private FontMetrics fm;
    private Point mousePos;
    private Thread thread;
}
