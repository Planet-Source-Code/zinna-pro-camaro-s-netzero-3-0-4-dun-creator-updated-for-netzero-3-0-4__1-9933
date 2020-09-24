// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoPlayList.java

package admgmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import nzcom.ServerParms;
import nzcom.ZCast;

// Referenced classes of package admgmt:
//            SequentialPlayList, PlayAd, PlayList

public class DemoPlayList extends SequentialPlayList
{

    public void sendStats()
    {
        ZCast.displayDebug("DemoPlayList: not sending stats.");
    }

    private DemoPlayList()
    {
        super(-1, 0);
    }

    public static DemoPlayList getDemoPlayList()
    {
        if(g_demoPlayList == null)
        {
            g_demoPlayList = new DemoPlayList();
            if(!g_demoPlayList.populatePlayList())
            {
                ZCast.displayDebug("--> Error populating DemoPlayList");
                g_demoPlayList = null;
            } else
            {
                ZCast.displayDebug("--> DemoPlayList created.");
            }
        }
        return g_demoPlayList;
    }

    public boolean populatePlayList()
    {
        int i = Integer.parseInt(ServerParms.getParm("ADDisplayTime", "30"));
        int j = Integer.parseInt(ServerParms.getParm("MinADDisplayTime", "5"));
        int k = Integer.parseInt(ServerParms.getParm("MaxADDisplayTime", "300"));
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(playListFile));
            String s;
            while((s = bufferedreader.readLine()) != null) 
                if(!s.startsWith("#"))
                {
                    PlayAd playad = parseRawAd(s, ';', i, j, k);
                    if(playad != null)
                    {
                        ZCast.displayDebug(playad.toString());
                        addToPlayList(playad);
                    } else
                    {
                        ZCast.displayDebug("DemoPlayList: Invalid play ad: " + s);
                    }
                }
            bufferedreader.close();
            return true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return false;
    }

    private static DemoPlayList g_demoPlayList = null;
    private static String playListFile = "demo/playlist.txt";
    private static Vector g_playAds = null;

}
