// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultEngineListener.java

package spe;

import gui.DialGroups;
import gui.NZDialogBox;
import nzcom.*;

// Referenced classes of package spe:
//            SPEngineListener

public class DefaultEngineListener
    implements SPEngineListener
{

    public DefaultEngineListener()
    {
    }

    public void onEngineError(String s)
    {
        ZCast.displayDebug("<> SPE onEngineError: " + s);
    }

    public void onEngineFatalException(Exception exception)
    {
        ZCast.displayDebug(exception);
    }

    public void onEngineWarning(String s)
    {
        ZCast.displayDebug("<> SPE onEngineWarning: " + s);
    }

    public void onFailedDUNCapabilities()
    {
        ZCast.displayDebug("<> SPE failed DUN capabilities.");
        boolean flag = DialGroups.getDUN_noshow();
        if(!flag)
        {
            String as[] = {
                "yes", "no"
            };
            int i = NZDialogBox.showMessageDialogNoShow("Outdated Dial-Up Networking", "We have detected an outdated version of dial up networking.\nWould you like to go to the vendor's website to download an\nupdated version?", 3, as, flag);
            DialGroups.setDUN_noshow(NZDialogBox.NoShowState());
            DialGroups.writeGroups();
            if(i == 0)
            {
                String s = ServerParms.getParm("UpdateDunUrl", "http://microsoft.com/ntserver/nts/downloads/recommended/dun13win95/default.asp");
                OSDetectNative.showUrl(s);
            }
            flag = NZDialogBox.NoShowState();
            DialGroups.setDUN_noshow(flag);
            DialGroups.writeGroups();
        }
    }
}
