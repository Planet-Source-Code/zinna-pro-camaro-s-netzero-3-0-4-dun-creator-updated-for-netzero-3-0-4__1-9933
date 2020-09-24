// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageAd.java

package admgmt;

import java.awt.*;
import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package admgmt:
//            PlayAd

public class ImageAd extends PlayAd
{

    public ImageAd(Vector vector)
    {
        m_adId = (String)vector.elementAt(0);
        if(vector.elementAt(1) != null)
        {
            m_adImageString = (String)vector.elementAt(1);
            m_adUrlString = (String)vector.elementAt(2);
            m_adCacheFile = (String)vector.elementAt(3);
            m_targetString = (String)vector.elementAt(4);
            m_adDisplayTime = ((Integer)vector.elementAt(5)).intValue();
            m_adType = ((Integer)vector.elementAt(6)).intValue();
            timeStampAdUrls();
        }
        m_adDisplayType = 1;
    }

    public boolean prepareForDisplay(Component component)
    {
        boolean flag = false;
        notifyDisplayOccured();
        clearPlayAdListeners();
        if(component != null)
        {
            MediaTracker mediatracker = new MediaTracker(component);
            try
            {
                m_banner = component.getToolkit().getImage(m_adCacheFile);
                mediatracker.addImage(m_banner, 1);
                mediatracker.waitForID(1);
                if(!mediatracker.isErrorID(1))
                {
                    incrAdDisplayCount(1);
                    setDisplayStartTime();
                    component.setSize(m_banner.getWidth(component), m_banner.getHeight(component));
                    flag = true;
                } else
                {
                    ZCast.displayDebug("Render error occurred in Ad " + getShortAdId());
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
            mediatracker.removeImage(m_banner, 1);
        }
        return flag;
    }

    public void renderAd(Component component)
    {
        if(component != null && m_banner != null)
        {
            Dimension dimension = component.getSize();
            int i = m_banner.getHeight(component);
            Graphics g = component.getGraphics();
            g.drawImage(m_banner, 0, (dimension.height - i) / 2, component.getBackground(), component);
            g.dispose();
        }
    }

    public void stopRenderAd()
    {
    }

    public void cleanUp()
    {
        if(m_banner != null)
        {
            m_banner.flush();
            m_banner = null;
        }
    }

    protected String getFileExtension()
    {
        return ".gif";
    }

    private Image m_banner;
    private final String DEFAULT_FILENAME_EXT = ".gif";
}
