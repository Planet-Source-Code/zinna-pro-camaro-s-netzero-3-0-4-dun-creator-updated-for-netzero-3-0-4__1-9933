// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageGenerator.java

package ticker.client;

import java.awt.*;
import java.util.*;
import nzcom.*;
import ticker.*;

// Referenced classes of package ticker.client:
//            DataRetriever, TickerAd, TickerAdList, TopicWrapper

public class ImageGenerator
{

    public ImageGenerator()
    {
        wrappers = null;
        aRetriever = null;
        bRegenerate = false;
        state = 65535;
        temporary = null;
        images = new Vector();
        currentImage = 0;
        maxSize = 10000;
        currTopic = -1;
        wrappers = new TopicWrapper[4];
    }

    public boolean adsNeeded()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null)
            return false;
        else
            return topicwrapper.getAdList().refreshList();
    }

    public void clearPositions()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper != null)
            topicwrapper.clearPositions();
    }

    public void dumpStats()
    {
        if(wrappers == null)
            return;
        for(int i = 0; i < wrappers.length; i++)
        {
            Vector vector = wrappers[i].getAdList().getAds();
            if(vector != null)
            {
                for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); ZCast.displayDebug(enumeration.nextElement()));
            }
        }

    }

    public TickerAd getCurrentAd()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null)
            return null;
        else
            return topicwrapper.getCurrentAd();
    }

    private Image getCurrentImage()
    {
        try
        {
            Image image = (Image)images.elementAt(currentImage);
            nextImage = currentImage + 1;
            return image;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    private TopicWrapper getCurrentWrapper()
    {
        if(currTopic < 0 || currTopic >= wrappers.length)
        {
            ZCast.displayDebug("ImageGenerator: current topic has an invalid value");
            return null;
        } else
        {
            return wrappers[currTopic];
        }
    }

    public synchronized boolean getFirstDisplay()
    {
        return firstDisplay;
    }

    public synchronized Image getImage(Graphics g, Component component)
    {
        if(getState() == 0 || getState() == 1)
            return getTemporaryImage(g, component);
        if(getRegenerate())
        {
            images.removeAllElements();
            int i = 0;
            for(Enumeration enumeration = getCurrentWrapper().tokens(); enumeration.hasMoreElements();)
            {
                ClientTickerToken clienttickertoken = (ClientTickerToken)enumeration.nextElement();
                i += clienttickertoken.getWidth();
            }

            if(i > 0)
            {
                int j = 0;
                int k = i / maxSize;
                Enumeration enumeration1 = getCurrentWrapper().tokens();
                ClientTickerToken clienttickertoken1 = (ClientTickerToken)enumeration1.nextElement();
                clienttickertoken1.getBoundingRect().setLocation(j, 0);
                for(int l = 0; l < k; l++)
                {
                    Image image = component.createImage(maxSize, g.getClipBounds().height);
                    Graphics g1 = image.getGraphics();
                    g1.setClip(0, 0, maxSize, g.getClipBounds().height);
                    do
                    {
                        j = clienttickertoken1.render(g1, j);
                        if(j < maxSize)
                        {
                            clienttickertoken1 = (ClientTickerToken)enumeration1.nextElement();
                            clienttickertoken1.getBoundingRect().setLocation(j, 0);
                        }
                    } while(j < maxSize);
                    g1.dispose();
                    j = j - clienttickertoken1.getWidth() - maxSize;
                    images.addElement(image);
                    i -= maxSize;
                }

                if(i > 0)
                {
                    Image image1 = component.createImage(i, g.getClipBounds().height);
                    Graphics g2 = image1.getGraphics();
                    g2.setClip(0, 0, i, g.getClipBounds().height);
                    do
                    {
                        j = clienttickertoken1.render(g2, j);
                        if(!enumeration1.hasMoreElements())
                            break;
                        clienttickertoken1 = (ClientTickerToken)enumeration1.nextElement();
                        clienttickertoken1.getBoundingRect().setLocation(j, 0);
                    } while(true);
                    g2.dispose();
                    images.addElement(image1);
                }
            }
            setRegenerate(false);
            currentImage = 0;
        }
        return getCurrentImage();
    }

    public TickerAd getNextAd()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null)
            return null;
        else
            return topicwrapper.getNextAd();
    }

    public Image getNextImage()
    {
        if(getState() == 0 || getState() == 1)
            return null;
        try
        {
            Image image = (Image)images.elementAt(nextImage);
            nextImage++;
            return image;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public int getRefreshTime()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null)
            return -1;
        else
            return topicwrapper.getRefreshTime();
    }

    private synchronized boolean getRegenerate()
    {
        return bRegenerate;
    }

    public synchronized int getState()
    {
        return state;
    }

    private Image getTemporaryImage(Graphics g, Component component)
    {
        if(temporary == null)
        {
            String s = resNZResource.getString("Please_wait_while_fetching");
            Font font = new Font(g.getFont().getName(), 1, 12);
            FontMetrics fontmetrics = g.getFontMetrics(font);
            int i = fontmetrics.stringWidth(s);
            int j = g.getClipBounds().height;
            temporary = component.createImage(i, j);
            Graphics g1 = temporary.getGraphics();
            g1.setClip(0, 0, i, j);
            g1.setFont(font);
            g1.setColor(Color.yellow);
            g1.drawString(s, 0, 11);
            g1.dispose();
            setFirstDisplay(true);
        }
        return temporary;
    }

    private ClientTickerToken getToken(int i)
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null || !topicwrapper.hasData())
            return null;
        else
            return topicwrapper.getToken(i);
    }

    public synchronized int getTopic()
    {
        return currTopic;
    }

    private synchronized String getUrlBase()
    {
        return urlBase;
    }

    public synchronized void gotoUrl(Point point)
    {
        String s = null;
        String s1 = getUrlBase();
        boolean flag = false;
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper != null)
        {
            for(Enumeration enumeration = topicwrapper.tokens(); enumeration.hasMoreElements();)
            {
                ClientTickerToken clienttickertoken = (ClientTickerToken)enumeration.nextElement();
                if(clienttickertoken.getBoundingRect().contains(point))
                {
                    ZCast.displayDebug("click thru: " + clienttickertoken.getKeyName() + "; ");
                    if(clienttickertoken instanceof TickerAd)
                        ((TickerAd)clienttickertoken).incAdClickThruCount();
                    flag = clienttickertoken instanceof StockToken;
                    s = clienttickertoken.getUrl();
                    break;
                }
            }

        }
        if(flag)
            if(s != null && s1 != null)
                s = s1 + s;
            else
            if(s1 != null)
                s = s1;
        if(ZCast.m_demoMode)
            s = s + "/index.htm";
        ZCast.displayDebug("\n\n***Token URL = " + s);
        if(s != null)
            Initializer.m_zwindow.showAdUrl(s);
    }

    public synchronized void refreshDataAfterUserSelection()
    {
        aRetriever.suspend();
        setState(1);
        aRetriever.resume();
    }

    public boolean refreshNeeded()
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper == null)
            return false;
        else
            return topicwrapper.refreshNeeded();
    }

    public void resumeRetriever()
    {
        if(aRetriever != null)
            aRetriever.resume();
    }

    public void selChanged(int i, boolean flag)
    {
        if(i < 0 || currTopic >= wrappers.length)
            return;
        if(wrappers[i] == null)
        {
            return;
        } else
        {
            wrappers[i].setSelChanged(flag);
            return;
        }
    }

    public void setData(TickerResponse tickerresponse, boolean flag)
    {
        if(tickerresponse != null)
        {
            Vector vector = tickerresponse.getTickerMessages();
            if(vector != null)
            {
                for(int i = 0; i < vector.size();)
                    if(!(vector.elementAt(i) instanceof TickerToken))
                        vector.removeElementAt(i);
                    else
                        i++;

            }
            TopicWrapper topicwrapper = getCurrentWrapper();
            if(topicwrapper != null)
                synchronized(this)
                {
                    topicwrapper.setData(vector);
                    if(flag)
                        topicwrapper.getAdList().fillList(tickerresponse.getSponsorMessages());
                }
        }
        if(getState() != 2)
        {
            setState(2);
            setFirstDisplay(true);
        }
        setRegenerate(true);
    }

    public synchronized void setFirstDisplay(boolean flag)
    {
        firstDisplay = flag;
    }

    public synchronized void setRegenerate(boolean flag)
    {
        bRegenerate = flag;
    }

    public void setRetriever(DataRetriever dataretriever)
    {
        aRetriever = dataretriever;
    }

    public synchronized void setState(int i)
    {
        state = i;
    }

    public synchronized void setTopic(int i)
    {
        if(currTopic == i)
            return;
        if(i < 0 || i >= 4)
            return;
        currTopic = i;
        if(aRetriever != null)
        {
            boolean flag = aRetriever.isRunning();
            if(flag)
                aRetriever.suspend();
            aRetriever.setRefreshTime(getRefreshTime());
            setState(0);
            if(flag)
                aRetriever.resume();
            else
                aRetriever.start();
        }
    }

    public synchronized void setUrlBase(String s)
    {
        urlBase = s;
    }

    public synchronized void setWrapper(int i, TopicWrapper topicwrapper)
    {
        if(i >= 0 && i < wrappers.length)
            wrappers[i] = topicwrapper;
    }

    public void suspendRetriever()
    {
        if(aRetriever != null)
            aRetriever.suspend();
    }

    public synchronized void updateBounds(int i, int j)
    {
        TopicWrapper topicwrapper = getCurrentWrapper();
        if(topicwrapper != null)
        {
            Enumeration enumeration = topicwrapper.tokens();
            if(enumeration.hasMoreElements())
            {
                ClientTickerToken clienttickertoken = (ClientTickerToken)enumeration.nextElement();
                int k = i - clienttickertoken.getBoundingRect().x;
                clienttickertoken.translate(k);
                if(clienttickertoken instanceof TickerAd)
                    ((TickerAd)clienttickertoken).updateDisplayCount(j);
                while(enumeration.hasMoreElements()) 
                {
                    ClientTickerToken clienttickertoken1 = (ClientTickerToken)enumeration.nextElement();
                    clienttickertoken1.translate(k);
                    if(clienttickertoken1 instanceof TickerAd)
                        ((TickerAd)clienttickertoken1).updateDisplayCount(j);
                }
            }
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private TopicWrapper wrappers[];
    private int currTopic;
    private DataRetriever aRetriever;
    boolean bRegenerate;
    boolean firstDisplay;
    public static final int NEWTOPIC = 0;
    public static final int SELCHANGE = 1;
    public static final int NORMAL = 2;
    public static final int UNKNOWN = 65535;
    private int state;
    private Image temporary;
    private Vector images;
    private int currentImage;
    private int nextImage;
    private int maxSize;
    private String urlBase;

}
