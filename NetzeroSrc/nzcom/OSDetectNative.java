// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OSDetectNative.java

package nzcom;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.*;
import sysinfo.SysInfo;
import util.Cookie;

// Referenced classes of package nzcom:
//            Initializer, ZCast, NZWindow

public class OSDetectNative
{

    public static String localizeURL(String s)
    {
        String s1 = "";
        String s2 = "";
        String s3 = System.getProperty("user.dir");
        s3 = s3.replace(':', '|');
        int i = s.indexOf("file:");
        if(i != -1)
            s2 = s.substring(5, s.length());
        else
            s2 = s;
        s1 = "file://" + s3 + File.separator + s2;
        return s1;
    }

    public static native int osw32CheckResolution();

    public static native boolean os32dock(int i, int j, int k);

    private static native void os32PushWindow(int i, int j, int k);

    private static native Rectangle osw32GetWorkspaceSize();

    public static native void os32undock();

    public static void setNZwindow(NZWindow nzwindow)
    {
        nzWindowObj = nzwindow;
    }

    public static Rectangle getWorkspaceSize()
    {
        return osw32GetWorkspaceSize();
    }

    public static int checkResolution()
    {
        return osw32CheckResolution();
    }

    public static boolean dock(int i, int j, int k)
    {
        return os32dock(i, j, k);
    }

    public static void pushWindow(int i, int j, int k)
    {
        os32PushWindow(i, j, k);
    }

    public static void undock()
    {
        os32undock();
    }

    public static void undocksWindow()
    {
        nzWindowObj.undockWindow("down");
    }

    public static void setSearchPanelLocations()
    {
        nzWindowObj.setSearchPanelLocations();
    }

    public static native boolean createRegKey(String s, String s1);

    public static native void osw32catchCtrlC();

    public static native boolean createRegKey(String s, String s1, String s2);

    public static native String[] getRegSubkeys(String s, String s1);

    public static native boolean isRegKeyPresent(String s, String s1);

    private static native String osw32getreg(String s, String s1, boolean flag);

    public static native String getRegValueData(String s, String s1, String s2);

    public static native int getSubkeysCount(String s, String s1);

    public static native boolean setRegValueData(String s, String s1, String s2, String s3);

    public static String getRegValue(String s, String s1, boolean flag)
    {
        return osw32getreg(s, s1, flag);
    }

    public boolean setRegValue(String s, String s1, int i)
    {
        return osw32setregInt(s, s1, i);
    }

    public boolean setRegValue(String s, String s1, String s2)
    {
        return osw32setregString(s, s1, s2);
    }

    public static void catchingUnexpExcep()
    {
        osw32catchCtrlC();
    }

    public static native void cleanUpCookieResources();

    private static native boolean osw32setIECookie(String s, String s1);

    private native boolean osw32setregInt(String s, String s1, int i);

    private native boolean osw32setregString(String s, String s1, String s2);

    public static boolean setIECookie(String s, String s1, String s2, String s3, String s4, boolean flag)
    {
        String s5 = s2 + "=" + s3 + ";path=" + s1 + ";expires=" + s4;
        ZCast.displayDebug("GM", "Setting ie cookie: " + s5);
        return osw32setIECookie("http://" + s, s5);
    }

    public static boolean setNetscapeCookie(String s, String s1, String s2, String s3, String s4, boolean flag)
    {
        boolean flag1 = false;
        return flag1;
    }

    public static String getSpecificCookie(String s)
    {
        if(netscapeIsDefaultBrowser())
            return getSpecificNetscapeCookie(s);
        if(ieIsDefaultBrowser())
            return getSpecificIECookie("http://" + s);
        else
            return null;
    }

    public static native String getSpecificIECookie(String s);

    public static String getSpecificNetscapeCookie(String s)
    {
        String s1 = null;
        return s1;
    }

    public static boolean setCookie(Cookie cookie)
    {
        if(netscapeIsDefaultBrowser())
            return setNetscapeCookie(cookie.getDomain(), cookie.getPath(), cookie.getFirstName(), cookie.getNameValue(cookie.getFirstName()), cookie.getExpires(), cookie.isSecure());
        if(ieIsDefaultBrowser())
            return setIECookie(cookie.getDomain(), cookie.getPath(), cookie.getFirstName(), cookie.getNameValue(cookie.getFirstName()), cookie.getExpires(), cookie.isSecure());
        else
            return false;
    }

    public static boolean netscapeIsDefaultBrowser()
    {
        String s = getRegValue("SOFTWARE\\Classes\\http\\shell\\open\\command", "", false);
        return s.indexOf("netscape.exe") > 0;
    }

    public static boolean ieIsDefaultBrowser()
    {
        String s = getRegValue("SOFTWARE\\Classes\\http\\shell\\open\\command", "", false);
        return s.indexOf("iexplore.exe") > 0;
    }

    public String expandEnvironmentVariables(String s)
    {
        return osw32expandEnvVars(s);
    }

    public void floatWindow(int i)
    {
        osw32floath(i);
    }

    public boolean floatWindow(String s)
    {
        return osw32float(s);
    }

    public int getHandle(String s)
    {
        return osw32geth(s);
    }

    public String getOsCommand()
    {
        return osw32command();
    }

    public int startProcess(String s)
    {
        return osw32start(s);
    }

    public static native SysInfo getSystemInfo();

    private native int osw32start(String s);

    private native String osw32command();

    private native String osw32expandEnvVars(String s);

    private native boolean osw32float(String s);

    private native void osw32floath(int i);

    private native int osw32geth(String s);

    public static native int getWindowHandle(String s);

    public static native int getZeroPortWindowHandle();

    public static native boolean setTopMost(int i);

    public static boolean setTopMost(String s)
    {
        int i = getWindowHandle(s);
        if(i != 0)
            return setTopMost(i);
        else
            return false;
    }

    public static native String getSystemRoot();

    public static native String getFileVersion(String s);

    private native String osw32mangle(String s);

    private native String osw32unmangle(String s);

    public static String unmangle(String s)
    {
        int k = s.length() / 2;
        char ac[] = new char[k];
        for(int l = 0; l < k; l++)
        {
            int i = "1aMQf7gT94LWe6yC".indexOf(s.charAt(l));
            int j = "1aMQf7gT94LWe6yC".indexOf(s.charAt(l + k));
            ac[l] = (char)(j * 16 + i);
        }

        String s1 = new String(ac);
        return s1;
    }

    public static String mangle(String s)
    {
        int k = s.length();
        char ac[] = new char[2 * k];
        for(int l = 0; l < k; l++)
        {
            char c = s.charAt(l);
            int i = c / 16;
            int j = c % 16;
            ac[l] = "1aMQf7gT94LWe6yC".charAt(j);
            ac[l + k] = "1aMQf7gT94LWe6yC".charAt(i);
        }

        String s1 = new String(ac);
        return s1;
    }

    public static String mangle2(String s)
    {
        return s;
    }

    public native boolean osw32initVolume();

    public native void osw32mute(boolean flag);

    public static void setVolumeLevel(int i, int j)
    {
        osw32setVolumeLevel(i, j);
    }

    public static native void osw32setVolumeLevel(int i, int j);

    public static native void osw32uninitVolume();

    public static native boolean osw32stopPaletteMessages(String s);

    public static native boolean osw32resumePaletteMessages(String s);

    private static native void osw32http(String s);

    private static native void osw32http2(String s, boolean flag);

    private native int osw32status();

    public static synchronized void showUrl(String s)
    {
        if(ZCast.m_demoMode)
            s = localizeURL(s);
        boolean flag = Initializer.m_zwindow.getNewBrowserState();
        osw32http2(s, flag);
    }

    public static synchronized void showUrl(String s, boolean flag)
    {
        if(ZCast.m_demoMode)
            s = localizeURL(s);
        osw32http2(s, flag);
    }

    public boolean testNetwork()
    {
        try
        {
            InetAddress.getByName(networkName);
            haveNetwork = true;
            ZCast.displayDebug("found existing network connection");
        }
        catch(UnknownHostException _ex)
        {
            haveNetwork = false;
            ZCast.displayDebug("found no existing network connection");
        }
        return haveNetwork;
    }

    public boolean testRas()
    {
        int i = osw32status();
        haveRas = i == 1;
        if(haveRas)
            ZCast.displayDebug("found RAS support");
        else
            ZCast.displayDebug("found no RAS support");
        return haveRas;
    }

    public boolean testUrlAccess()
    {
        ZCast.displayDebug("Checking for network access ... ");
        try
        {
            URL url = new URL(urlTest);
            try
            {
                url.getContent();
                haveNetwork = true;
                ZCast.displayDebug("found active HTTP access");
            }
            catch(IOException _ex)
            {
                haveNetwork = false;
                ZCast.displayDebug("found no active HTTP access");
            }
        }
        catch(Exception exception)
        {
            haveNetwork = false;
            ZCast.displayDebug(exception);
        }
        return haveNetwork;
    }

    public static native void unloadProcessIcon();

    public static native boolean updateProcessIcons();

    public static native boolean loadProcessIcon(String s);

    public static native boolean removeSystemMenuItem(String s, int i);

    public static native boolean removeSystemMenuItemByHandle(int i, int j);

    public boolean removeAllSystemMenuItems(String s)
    {
        return removeAllSystemMenuItems(getWindowHandle(s));
    }

    public static boolean removeAllSystemMenuItems(int i)
    {
        boolean flag = true;
        flag &= removeSystemMenuItemByHandle(i, 6);
        flag &= removeSystemMenuItemByHandle(i, 5);
        flag &= removeSystemMenuItemByHandle(i, 4);
        flag &= removeSystemMenuItemByHandle(i, 3);
        flag &= removeSystemMenuItemByHandle(i, 2);
        flag &= removeSystemMenuItemByHandle(i, 1);
        flag &= removeSystemMenuItemByHandle(i, 0);
        return flag;
    }

    public static native boolean enableScreenSaver(boolean flag);

    public static native boolean writePrivateProfileString(String s, String s1, String s2, String s3);

    public static native boolean isPrivateProfileString(String s, String s1, String s2);

    public static native Point getMousePos();

    public static native void moveWindowWithMouse(int i);

    public static void moveZeroPortWithMouse()
    {
        moveWindowWithMouse(getZeroPortWindowHandle());
    }

    public static native void moveResizeWindowWithMouseAtPoint(int i, int j, int k, int l, int i1);

    public static void moveResizeZeroPortWithMouseAtPoint(int i, int j, int k, int l)
    {
        moveResizeWindowWithMouseAtPoint(getZeroPortWindowHandle(), i, j, k, l);
    }

    public OSDetectNative()
    {
    }

    public static final int DOCK_TOP = 0;
    public static final int DOCK_BOTTOM = 1;
    public static NZWindow nzWindowObj = null;
    protected boolean haveRas;
    protected boolean haveNetwork;
    protected static String networkName = "NetZero.net";
    protected static String urlTest = "http://www.netzero.net/";
    private static final String m1 = "1aMQf7gT94LWe6yC";
    public static final int RESTORE_MENU_INDEX = 0;
    public static final int MOVE_MENU_INDEX = 1;
    public static final int SIZE_MENU_INDEX = 2;
    public static final int MINIMIZE_MENU_INDEX = 3;
    public static final int MAXAMIZE_MENU_INDEX = 4;
    public static final int SEPARATOR_MENU_INDEX = 5;
    public static final int CLOSE_MENU_INDEX = 6;

}
