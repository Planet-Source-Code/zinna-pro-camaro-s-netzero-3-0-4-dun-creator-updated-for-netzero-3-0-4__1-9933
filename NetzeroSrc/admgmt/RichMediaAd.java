// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RichMediaAd.java

package admgmt;

import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import nzcom.*;
import pool.*;
import util.Timer;
import util.TimerListener;

// Referenced classes of package admgmt:
//            PlayAd, PlayAdListener, RichMediaRenderer, PlayAdVolumeControl, 
//            RichMediaListener

public class RichMediaAd extends PlayAd
    implements RichMediaListener, PlayAdVolumeControl, TimerListener
{

    public RichMediaAd(Vector vector)
    {
        m_totalDisplayTime = 0;
        impressionCounter = 0;
        rect = new Rectangle();
        windowTitle = null;
        mediaLocation = null;
        m_SetTargetFailed = false;
        m_ReTryTimer = null;
        m_adId = (String)vector.elementAt(0);
        m_adImageString = (String)vector.elementAt(1);
        m_adUrlString = (String)vector.elementAt(2);
        m_adCacheFile = (String)vector.elementAt(3);
        m_targetString = (String)vector.elementAt(4);
        m_adDisplayTime = ((Integer)vector.elementAt(5)).intValue();
        m_adType = ((Integer)vector.elementAt(6)).intValue();
        m_adDisplayType = 4;
        timeStampAdUrls();
        if(m_adType == 2)
            m_richMediaRenderer = BrowserDisplay.getBrowser(false);
        else
        if(m_adType == 3)
            m_richMediaRenderer = BrowserDisplay.getBrowser(true);
        else
            m_richMediaRenderer = BrowserDisplay.getZeroPortBrowser();
    }

    public void cleanUp()
    {
        if(m_richMediaRenderer != null)
        {
            m_richMediaRenderer.releaseTarget();
            ZCast.displayDebug("richm", "\n>>>> RichMedia: cleanUp releasing the renderer=" + m_richMediaRenderer + "\n\n");
            m_richMediaRenderer.removeRichMediaListener(this);
        }
        m_totalDisplayTime = 0;
        impressionCounter = 0;
    }

    public void discloseVolumeControlToListeners()
    {
        if(m_displayListenerList != null)
        {
            for(int i = 0; i < m_displayListenerList.size(); i++)
                try
                {
                    PlayAdListener playadlistener = (PlayAdListener)m_displayListenerList.elementAt(i);
                    playadlistener.adDisplayControlDisclosure(this);
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug("richm", exception);
                }

        }
    }

    public void downloadComplete(String s)
    {
    }

    public void interacting()
    {
        long l = (new Date()).getTime();
        int i = 0;
        Initializer.m_zwindow.resetMonitorThread();
        if(m_totalDisplayTime == 0)
            m_totalDisplayTime = getAdDisplayTime();
        ZCast.displayDebug("richm", "in RichMediaAd interacting get displayTime is " + getAdDisplayTime());
        ZCast.displayDebug("richm", "in RichMediaAd interacting get Total displayTime is " + m_totalDisplayTime + "   " + (l - m_displayStartTime) + "    " + (long)(m_totalDisplayTime - PlayAd.TIME_AFTER_INTERACTION) * 1000L);
        if(l - m_displayStartTime > (long)(m_totalDisplayTime - PlayAd.TIME_AFTER_INTERACTION) * 1000L)
        {
            i = PlayAd.TIME_AFTER_INTERACTION - (int)((long)m_totalDisplayTime - (l - m_displayStartTime) / 1000L);
            ZCast.displayDebug("richm", " going to set tempExtension" + i);
        }
        if(i > 0)
        {
            ZCast.displayDebug("richm", " going to have extended time of " + i);
            m_extendedTime += i;
            m_totalDisplayTime = m_totalDisplayTime + i;
            if((m_totalDisplayTime - getAdDisplayTime()) / getAdDisplayTime() - impressionCounter == 0)
            {
                ZCast.displayDebug("richm", " Incrementing the AdImpression Counter by 1 ");
                incrAdDisplayCount(1);
                impressionCounter++;
            }
        }
    }

    public void navigatingTo(String s)
    {
        Date date = new Date();
        if(setClickThruTime(date.getTime()))
            incrAdClickthruCount(1);
    }

    private boolean playMedia()
    {
        boolean flag = false;
        if(mediaLocation.toLowerCase().endsWith("swf"))
        {
            String s = null;
            switch(m_adType)
            {
            case 2: // '\002'
                s = ZeroInPool.getInstance().getDefaultAd().getCacheFile();
                break;

            case 3: // '\003'
                s = ZeroOutPool.getInstance().getDefaultAd().getCacheFile();
                break;

            case 4: // '\004'
            default:
                s = MarqueePool.getInstance().getDefaultAd().getCacheFile();
                break;
            }
            flag = m_richMediaRenderer.playFlash(mediaLocation, rect.height, rect.width, s);
        } else
        {
            flag = m_richMediaRenderer.play(mediaLocation);
        }
        if(flag)
        {
            incrAdDisplayCount(1);
            setDisplayStartTime();
            return true;
        } else
        {
            ZCast.displayDebug("richm", "RichMedia Render error occurred in Ad " + getShortAdId());
            return false;
        }
    }

    public boolean prepareForDisplay(Component component)
    {
        boolean flag = false;
        notifyDisplayOccured();
        discloseVolumeControlToListeners();
        clearPlayAdListeners();
        if(component != null && m_richMediaRenderer != null)
        {
            m_richMediaRenderer.addRichMediaListener(this);
            try
            {
                if(m_adType == 2 || m_adType == 3)
                {
                    Point point = new Point();
                    Component component1 = findHighestParent(component, Class.forName("java.awt.Dialog"), point);
                    ZCast.displayDebug("richm", ">>RichMediaAd: parent returned=" + component1);
                    if(component1 == null)
                        component1 = component;
                    windowTitle = component1.getName();
                    rect.x = point.x;
                    rect.y = point.y;
                }
                rect.height = component.getSize().height;
                rect.width = component.getSize().width;
                ZCast.displayDebug("richm", ">>RichMediaAd: parent name=" + windowTitle + " rect=" + rect);
                m_SetTargetFailed = m_richMediaRenderer.setTarget(windowTitle, rect) ^ true;
                ZCast.displayDebug("richm", ">>RichMediaAd: m_SetTargetFailed=" + m_SetTargetFailed);
                if(m_adCacheFile.equals("html"))
                {
                    mediaLocation = m_adImageString;
                } else
                {
                    File file = new File(m_adCacheFile);
                    mediaLocation = "file:///" + file.getCanonicalPath();
                }
                ZCast.displayDebug("richm", ">>RichMediaAd: mediaLocation=" + mediaLocation);
                if(!m_SetTargetFailed)
                {
                    flag = playMedia();
                } else
                {
                    startReTryTimer();
                    flag = true;
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug("richm", exception);
            }
        }
        return flag;
    }

    public void renderAd(Component component)
    {
    }

    public void setVolumeLevel(int i, int j)
    {
        OSDetectNative.setVolumeLevel(i, j);
    }

    private void startReTryTimer()
    {
        m_ReTryTimer = new Timer();
        m_ReTryTimer.addTimerListener(this);
        m_ReTryTimer.start(1);
    }

    public void stopRenderAd()
    {
        if(m_richMediaRenderer != null)
        {
            m_richMediaRenderer.releaseTarget();
            ZCast.displayDebug("richm", "\n>>> RichMedia: stopRenderAd releasing the renderer=" + m_richMediaRenderer + "\n\n");
            m_richMediaRenderer.removeRichMediaListener(this);
        }
    }

    private void stopReTryTimer()
    {
        m_ReTryTimer = null;
    }

    public void timesUp()
    {
        try
        {
            if(m_SetTargetFailed)
            {
                m_SetTargetFailed = m_richMediaRenderer.setTarget(windowTitle, rect) ^ true;
                ZCast.displayDebug("richm", ">>RichMediaAd: 2nd try! m_SetTargetFailed=" + m_SetTargetFailed);
                if(!m_SetTargetFailed)
                {
                    playMedia();
                    stopReTryTimer();
                } else
                {
                    startReTryTimer();
                }
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    protected String getFileExtension()
    {
        return ".swf";
    }

    private RichMediaRenderer m_richMediaRenderer;
    private int m_totalDisplayTime;
    private int impressionCounter;
    private Rectangle rect;
    private String windowTitle;
    private String mediaLocation;
    private boolean m_SetTargetFailed;
    private Timer m_ReTryTimer;
    private final String DEFAULT_FILENAME_EXT = ".swf";
}
