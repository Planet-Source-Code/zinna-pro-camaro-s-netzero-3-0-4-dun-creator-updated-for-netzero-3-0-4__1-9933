// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TviPlayer.java

package video;

import java.awt.Point;
import java.awt.Rectangle;

// Referenced classes of package video:
//            VideoRenderer

public class TviPlayer
    implements VideoRenderer
{

    protected TviPlayer()
    {
        m_bPlaying = false;
        m_bPassMouseMessages = true;
        m_bImpressed = false;
        m_bVideoClicked = false;
        m_bCCClicked = false;
    }

    public void cleanup()
    {
        if(m_bInitialized)
        {
            m_bInitialized = false;
            unInitializeNative();
        }
    }

    private static native boolean findWindowNative(String s);

    public String getErrorString()
    {
        return getVideoErrorStringNative();
    }

    public static synchronized TviPlayer getInstance()
    {
        if(m_Instance == null)
            m_Instance = new TviPlayer();
        return m_Instance;
    }

    public static VideoRenderer getInterface()
    {
        return getInstance();
    }

    private static native String getVideoCaptionNative();

    private static native String getVideoErrorStringNative();

    public String getVideoFileCaption()
    {
        String s = new String(getVideoCaptionNative());
        if(s.length() > 0)
            return "NZTV - " + s;
        else
            return "NZTV";
    }

    public int getVolume()
    {
        return m_iVolume;
    }

    public boolean init()
    {
        if(!m_bInitialized)
            return m_bInitialized = initializeNative();
        else
            return true;
    }

    public boolean init(String s)
    {
        return init() && setWindowTitle(s);
    }

    private static native boolean initializeNative();

    public boolean isPlaying()
    {
        return m_bPlaying = isPlayingNative();
    }

    private static native boolean isPlayingNative();

    public boolean play(String s)
    {
        if(!isPlaying())
        {
            init();
            if(!setFileNameNative(s))
                return false;
            if(!setPassMouseMessagesNative(m_bPassMouseMessages))
                return false;
            if(!playNative())
            {
                return false;
            } else
            {
                m_bPlaying = true;
                return true;
            }
        } else
        {
            return false;
        }
    }

    public boolean stop()
    {
        return reset();
    }

    private static native boolean playNative();

    public boolean reset()
    {
        m_bPlaying = false;
        return resetVideoNative();
    }

    private static native boolean resetVideoNative();

    private static native boolean setFileNameNative(String s);

    public void setPassMouseMessages(boolean flag)
    {
        m_bPassMouseMessages = flag;
    }

    private static native boolean setPassMouseMessagesNative(boolean flag);

    public boolean setPosition(int i, int j)
    {
        return setPosNative(i, j);
    }

    private static native boolean setPosNative(int i, int j);

    public boolean setTarget(String s, Rectangle rectangle)
    {
        boolean flag = setWindowTitle(s);
        flag &= setPosition(rectangle.getLocation().x, rectangle.getLocation().y);
        return flag;
    }

    public boolean setVolume(int i, int j)
    {
        if(i < j && setVolumeNative(i, j))
        {
            m_iVolume = i;
            return true;
        } else
        {
            return false;
        }
    }

    private static native boolean setVolumeNative(int i, int j);

    public boolean setWindowTitle(String s)
    {
        if(s != "")
            return findWindowNative(s);
        else
            return false;
    }

    private static native void unInitializeNative();

    private static boolean m_bInitialized = false;
    private static TviPlayer m_Instance = null;
    private boolean m_bPlaying;
    private boolean m_bPassMouseMessages;
    public boolean m_bImpressed;
    public boolean m_bVideoClicked;
    public boolean m_bCCClicked;
    public static int m_iVolume = 6;
    public static final String DEFAULT_TITLE = "NZTV";

}
