// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TickerPanel.java

package ticker.client;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import nzcom.ZCast;
import ticker.*;

// Referenced classes of package ticker.client:
//            TickerAd, ImageGenerator

public class TickerPanel extends Panel
    implements Runnable
{
    class MouseEventHandler extends MouseAdapter
    {

        public void mousePressed(MouseEvent mouseevent)
        {
            gotoURL(mouseevent.getPoint());
        }

        MouseEventHandler()
        {
        }
    }


    public TickerPanel(ImageGenerator imagegenerator)
    {
        aGenerator = imagegenerator;
        initialize();
    }

    private int getPixelAdjustement()
    {
        return pixelAdjust;
    }

    public long getRefreshInterval()
    {
        return refreshIntv;
    }

    private void gotoURL(Point point)
    {
        aGenerator.gotoUrl(point);
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        addMouseListener(new MouseEventHandler());
    }

    private void initialize()
    {
        setName("TickerPanel");
        setLayout(null);
        setBackground(Color.black);
        setCursor(new Cursor(12));
        pixelAdjust = 2;
        refreshIntv = 100L;
        maxIntv = 140L;
        minIntv = 50L;
        increment = 10L;
        init = true;
        displayCount = -1;
        suspended = false;
        initConnections();
    }

    public void paint(Graphics g)
    {
        if(init)
        {
            StockToken.setFonts(g);
            NewsToken.setFonts(g);
            SportToken.setFonts(g);
            TickerAd.setFonts(g);
            LeadingMessage.setFonts(g);
            TrailingMessage.setFonts(g);
            init = false;
        }
        image = aGenerator.getImage(g, this);
        if(image == null)
            return;
        width = image.getWidth(this);
        int i = aGenerator.getState();
        if(i == 1 || i == 0)
        {
            displayCount++;
            if(displayCount % 3 != 0)
                return;
            x = (g.getClipBounds().width - image.getWidth(this)) / 2;
        } else
        if(aGenerator.getFirstDisplay())
        {
            x = getSize().width - 1;
            aGenerator.setFirstDisplay(false);
            displayCount = -1;
        }
        g.drawImage(image, x, 0, this);
        int j = x;
        int k = 0;
        do
        {
            g.drawImage(image, j, 0, this);
            aGenerator.updateBounds(x, g.getClipBounds().width - 1);
            k += image.getWidth(this);
            image = aGenerator.getNextImage();
            j = x + k;
        } while(image != null && j < g.getClipBounds().width);
        if(image == null && j <= 0)
            x = g.getClipBounds().width + 1;
    }

    public synchronized void resume()
    {
        suspended = false;
        notify();
    }

    public void run()
    {
        x = getSize().width;
        while(running) 
        {
            synchronized(this)
            {
                while(suspended) 
                    try
                    {
                        wait(1000L);
                    }
                    catch(InterruptedException _ex) { }
            }
            try
            {
                repaint();
                if(x > -width)
                    x -= getPixelAdjustement();
                else
                    x = getSize().width;
                Thread.sleep(refreshIntv);
            }
            catch(InterruptedException interruptedexception)
            {
                ZCast.displayDebug(interruptedexception);
            }
        }
    }

    public void setDefaultIntv(long l, long l1, long l2, int i)
    {
        if(l1 < l)
        {
            long l3 = l;
            l = l1;
            l1 = l3;
        }
        if(l2 < l || l2 > l1)
            l2 = (l + l1) / 2L;
        refreshIntv = l2;
        maxIntv = l1;
        minIntv = l;
        increment = i;
    }

    public synchronized void setPixelAdjustment(int i)
    {
        pixelAdjust = i;
    }

    public synchronized void setRefreshInterval(long l)
    {
        refreshIntv = l;
    }

    public void setTickerOff()
    {
        suspend();
        aGenerator.suspendRetriever();
    }

    public void setTickerOn()
    {
        if(suspended)
        {
            resume();
            aGenerator.resumeRetriever();
        } else
        {
            start();
        }
    }

    public void speedDown()
    {
        if(refreshIntv + increment <= maxIntv)
            setRefreshInterval(refreshIntv + increment);
    }

    public void speedUp()
    {
        if(refreshIntv - increment >= minIntv)
            setRefreshInterval(refreshIntv - increment);
    }

    public void start()
    {
        running = true;
        if(selfThread == null)
            selfThread = new Thread(this, "Ticker Panel");
        if(!selfThread.isAlive())
            selfThread.start();
    }

    public void suspend()
    {
        suspended = true;
    }

    public void terminate()
    {
        running = false;
    }

    private Thread selfThread;
    private boolean running;
    private boolean suspended;
    private ImageGenerator aGenerator;
    private long refreshIntv;
    private long maxIntv;
    private long minIntv;
    private long increment;
    private int pixelAdjust;
    private int x;
    private boolean init;
    private int displayCount;
    private int width;
    private Image image;

}
