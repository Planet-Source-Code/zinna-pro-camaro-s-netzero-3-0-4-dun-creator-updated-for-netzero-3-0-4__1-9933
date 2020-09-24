// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VideoAd.java

package admgmt;

import java.awt.*;
import java.util.Vector;
import nzcom.ZCast;
import video.TviPlayer;
import video.VideoRenderer;

// Referenced classes of package admgmt:
//            PlayAd, PlayAdListener, PlayAdVolumeControl

public class VideoAd extends PlayAd
    implements PlayAdVolumeControl
{

    public VideoAd(Vector vector)
    {
        m_adId = (String)vector.elementAt(0);
        m_adImageString = (String)vector.elementAt(1);
        m_adUrlString = (String)vector.elementAt(2);
        m_adCacheFile = (String)vector.elementAt(3);
        m_targetString = (String)vector.elementAt(4);
        m_adDisplayTime = ((Integer)vector.elementAt(5)).intValue();
        m_adType = ((Integer)vector.elementAt(6)).intValue();
        m_volumeLevel = 6;
        m_volumeScale = 10;
        m_adDisplayType = 2;
        timeStampAdUrls();
    }

    public boolean isPlaying()
    {
        if(m_videoRenderer == null)
            return false;
        else
            return m_videoRenderer.isPlaying();
    }

    public boolean prepareForDisplay(Component component)
    {
        boolean flag = false;
        notifyDisplayOccured();
        discloseVolumeControlToListeners();
        clearPlayAdListeners();
        if(component != null)
        {
            m_videoRenderer = TviPlayer.getInterface();
            try
            {
                Point point = new Point();
                Component component1 = findHighestParent(component, Class.forName("java.awt.Dialog"), point);
                if(component1 == null)
                    component1 = component;
                String s = component1.getName();
                Rectangle rectangle = component.getBounds();
                ZCast.displayDebug("Video>>>>>>>--->>>>Origin=" + point);
                ZCast.displayDebug("Video>>>>>>>--->>>>rect=" + rectangle);
                rectangle.x = point.x;
                rectangle.y = point.y;
                flag = m_videoRenderer.setTarget(s, rectangle);
                if(flag)
                {
                    incrAdDisplayCount(1);
                    setDisplayStartTime();
                    flag = m_videoRenderer.play(m_adCacheFile);
                    m_videoRenderer.setVolume(m_volumeLevel, m_volumeScale);
                } else
                {
                    ZCast.displayDebug("Video setup error occurred in Ad " + getShortAdId());
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
        }
        return flag;
    }

    public void renderAd(Component component)
    {
    }

    public void stopRenderAd()
    {
        if(m_videoRenderer != null)
            m_videoRenderer.stop();
    }

    public void cleanUp()
    {
        if(m_videoRenderer != null)
            m_videoRenderer = null;
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
                    ZCast.displayDebug(exception);
                }

        }
    }

    public void setVolumeLevel(int i, int j)
    {
        if(m_videoRenderer != null)
        {
            m_videoRenderer.setVolume(i, j);
            m_volumeLevel = i;
            m_volumeScale = j;
        }
    }

    protected String getFileExtension()
    {
        return ".tvi";
    }

    VideoRenderer m_videoRenderer;
    int m_volumeLevel;
    int m_volumeScale;
    private final String DEFAULT_FILENAME_EXT = ".tvi";
}
