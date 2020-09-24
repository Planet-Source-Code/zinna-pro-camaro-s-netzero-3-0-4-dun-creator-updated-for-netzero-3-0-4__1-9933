// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BrowserDisplay.java

package nzcom;

import admgmt.RichMediaListener;
import admgmt.RichMediaRenderer;
import java.awt.*;
import java.io.File;

// Referenced classes of package nzcom:
//            NZWindow, Initializer, ZCast, OSDetectNative

public class BrowserDisplay
    implements RichMediaRenderer
{

    private BrowserDisplay(int i)
    {
        error = false;
        m_ShowURLs = true;
        windowId = i;
    }

    public boolean isActive()
    {
        return windowId != -1;
    }

    public static BrowserDisplay getBrowser(String s)
    {
        return getBrowser(s, 150, 70);
    }

    public static BrowserDisplay getBrowser(String s, int i, int j)
    {
        if(init != 1)
            return null;
        int k = addBrowserAt(s, i, j);
        if(k != -1)
            return new BrowserDisplay(k);
        else
            return null;
    }

    public static BrowserDisplay getBrowser(boolean flag)
    {
        if(init != 1)
            return null;
        if(singletonBrowser != null)
        {
            if(!flag)
            {
                setDisplayClicksInNewWindow(flag, singletonBrowser.windowId);
                singletonBrowser.m_ShowURLs = flag;
            }
            return singletonBrowser;
        }
        int i = addBrowserAt("NzJavaWindow", 0, 0);
        if(i != -1)
            singletonBrowser = new BrowserDisplay(i);
        if(!flag)
        {
            setDisplayClicksInNewWindow(flag, singletonBrowser.windowId);
            singletonBrowser.m_ShowURLs = flag;
        }
        singletonBrowser.showUrl("about:<HTML><BODY bgcolor=Black></BODY></HTML>");
        return singletonBrowser;
    }

    public static BrowserDisplay getZeroPortBrowser()
    {
        if(init != 1)
            return null;
        if(zeroPortBrowser != null)
            return zeroPortBrowser;
        int i = addBrowserAt("NzJavaWindow", 0, 0);
        if(i != -1)
            zeroPortBrowser = new BrowserDisplay(i);
        zeroPortBrowser.showUrl("about:<HTML><BODY bgcolor=Black></BODY></HTML>");
        return zeroPortBrowser;
    }

    public boolean setTarget(String s, Rectangle rectangle)
    {
        boolean flag = false;
        if(s == null)
        {
            if(init == 1 && !error)
            {
                FindZeroPortAdPanel();
                if(m_ZeroPortAdPanelHandle != 0)
                {
                    reparentToWindow(m_ZeroPortAdPanelHandle, windowId);
                    flag = true;
                }
            }
        } else
        if(init == 1 && !error)
        {
            reparentByTitle(s, windowId, rectangle.x, rectangle.y);
            flag = true;
        }
        return flag;
    }

    public void releaseTarget()
    {
        if(!m_ShowURLs)
        {
            m_ShowURLs = true;
            setDisplayClicksInNewWindow(m_ShowURLs);
        }
        if(m_ZeroPortFrameHandle == 0)
            m_ZeroPortFrameHandle = OSDetectNative.getWindowHandle("NzJavaWindow");
        reparentToWindow(m_ZeroPortFrameHandle, windowId);
        showUrl("about:<HTML><BODY bgcolor=Black></BODY></HTML>");
    }

    public void reparentBrowser(String s, int i, int j)
    {
        if(init != 1)
            return;
        if(!error)
            reparentByTitle(s, windowId, i, j);
    }

    public void addRichMediaListener(RichMediaListener richmedialistener)
    {
        staticListener = richmedialistener;
    }

    public void removeRichMediaListener(RichMediaListener richmedialistener)
    {
        if(richmedialistener == staticListener)
            staticListener = null;
    }

    public void setDisplayClicksInNewWindow(boolean flag)
    {
        setDisplayClicksInNewWindow(flag, windowId);
    }

    public static boolean initBrowser()
    {
        if(init == 0)
        {
            boolean flag = init();
            if(flag)
                init = 1;
            else
                init = -1;
        }
        return init == 1;
    }

    public boolean play(String s)
    {
        return showUrl("about:<FRAMESET COLS=\"100%\"> <FRAME SCROLLING=NO SRC=\"" + s + "\" frameborder=0 framespacing = 0 border=0 marginheight=0 marginwidth=0> </FRAMESET>");
    }

    public boolean playFlash(String s, int i, int j, String s1)
    {
        if(s.toLowerCase().endsWith("swf"))
        {
            String s2 = null;
            File file = new File(s1);
            try
            {
                s2 = "file:///" + file.getCanonicalPath();
            }
            catch(Exception _ex) { }
            String s3 = "about: <html><body bgColor=#000000 marginheight=0 marginwidth=0 leftMargin=0 rightMargin=0 topMargin=0 bottomMargin=0><font color=White face=arial >";
            s3 = s3 + "<OBJECT classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
            s3 = s3 + " WIDTH = " + j + " HEIGHT = " + i;
            s3 = s3 + " ID=nzlogo ";
            s3 = s3 + "  altHTML=<a href=http://www.macromedia.com/shockwave/download/ ><img src=\"" + s2 + "\" border=0> </a> ";
            s3 = s3 + "  <PARAM NAME=MOVIE VALUE= \"";
            String s4 = " \" > ";
            s4 = s4 + " <PARAM NAME=quality VALUE=high> ";
            s4 = s4 + " <PARAM NAME=menu VALUE=false> ";
            s4 = s4 + " <PARAM NAME=SCALE VALUE=noborder> ";
            s4 = s4 + " </object> </body> </html> ";
            ZCast.displayDebug(" URL:\n " + s3 + s + s4);
            return showUrl(s3 + s + s4);
        } else
        {
            return showUrl("about:<FRAMESET COLS=\"100%\"> <FRAME SCROLLING=NO SRC=\"" + s + "\" frameborder=0 framespacing = 0 border=0 marginheight=0 marginwidth=0> </FRAMESET>");
        }
    }

    public boolean showUrl(String s)
    {
        if(init != 1)
            return false;
        else
            return navigate(s, windowId);
    }

    public static void navigatingTo(String s)
    {
        try
        {
            if(staticListener != null)
                staticListener.navigatingTo(s);
            ZCast.displayDebug("IE navigating to url " + s + "\n");
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public static void downloadComplete(String s)
    {
        try
        {
            if(staticListener != null)
                staticListener.downloadComplete(s);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public static void interacting()
    {
        try
        {
            if(staticListener != null)
                staticListener.interacting();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public boolean cleanup()
    {
        error = true;
        return releaseBrowser();
    }

    public boolean isBusy()
    {
        if(init == 1 || error)
            return true;
        else
            return isBusy(windowId);
    }

    public static boolean releaseBrowser()
    {
        if(init == 1)
        {
            boolean flag = shutdown();
            if(flag)
                init = -1;
        }
        return init == -1;
    }

    private void FindZeroPortAdPanel()
    {
        if(m_ZeroPortAdPanelHandle == 0)
        {
            int i = OSDetectNative.getZeroPortWindowHandle();
            if(i != 0)
            {
                m_ZeroPortAdPanelHandle = getChildWindowHandleAt(i, 150, 40);
                Component component = Initializer.m_zwindow.getAdBanner();
                if(!doesSizeMatch(m_ZeroPortAdPanelHandle, 5, component.getSize().width, component.getSize().height))
                {
                    ZCast.displayDebug("< < < Failed to match ZeroPort panel size!!!");
                    m_ZeroPortAdPanelHandle = 0;
                }
            }
        }
    }

    private static native boolean init();

    private static native boolean shutdown();

    private static native int addBrowserAt(String s, int i, int j);

    private static native int reparentByTitle(String s, int i, int j, int k);

    private static native int reparentByHandle(int i, int j, int k, int l);

    private static native int reparentToWindow(int i, int j);

    private static native boolean cycleBrowser(int i);

    private static native boolean navigate(String s, int i);

    private static native boolean navigateFrame(String s, int i, String s1);

    private static native void setDisplayClicksInNewWindow(boolean flag, int i);

    private static native boolean isBusy(int i);

    private static native int getChildWindowHandleAt(int i, int j, int k);

    private static native int getChildOfClass(int i, int j, String s);

    private static native boolean doesSizeMatch(int i, int j, int k, int l);

    private static int init = 0;
    private static RichMediaListener staticListener = null;
    private static BrowserDisplay zeroPortBrowser = null;
    private static BrowserDisplay singletonBrowser = null;
    int windowId;
    boolean error;
    boolean m_ShowURLs;
    private static final String wrapper1 = "about:<FRAMESET COLS=\"100%\"> <FRAME SCROLLING=NO SRC=\"";
    private static final String wrapper2 = "\" frameborder=0 framespacing = 0 border=0 marginheight=0 marginwidth=0> </FRAMESET>";
    private static final String blackBack = "about:<HTML><BODY bgcolor=Black></BODY></HTML>";
    private static int m_ZeroPortAdPanelHandle = 0;
    private static int m_ZeroPortFrameHandle = 0;

}
