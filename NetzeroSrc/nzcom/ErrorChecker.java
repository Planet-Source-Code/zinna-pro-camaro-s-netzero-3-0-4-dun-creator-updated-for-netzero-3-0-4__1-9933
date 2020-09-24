// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ErrorChecker.java

package nzcom;

import beans.BB;
import gui.NZDialogBox;
import java.util.ResourceBundle;

// Referenced classes of package nzcom:
//            RASWinNative, ZCast

public class ErrorChecker extends Thread
{

    public static ErrorChecker getErrorChecker()
    {
        if(m_errorChecker == null)
            m_errorChecker = new ErrorChecker();
        return m_errorChecker;
    }

    private ErrorChecker()
    {
        super("Error (RAS) Checker");
    }

    public void start()
    {
        ZCast.displayDebug("   ****** ErrorChecker is ALIVE=" + isAlive());
        if(!isAlive())
        {
            timeToDie = false;
            super.start();
        }
    }

    public void terminate()
    {
        timeToDie = true;
        interrupt();
        ZCast.displayDebug("Leaving ErrorChecker terminate()");
    }

    public void run()
    {
        try
        {
            (new BB()).start();
            ZCast.displayDebug("Nutti monitor started");
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        while(!timeToDie) 
            try
            {
                if(ZCast.m_connectionType == 0 && ZCast.m_rasWinNative.getStatus() != 0)
                {
                    String as[] = {
                        "ok"
                    };
                    NZDialogBox.showMessageDialog(ZCast.m_resNZResource.getString("Connection_Lost"), ZCast.m_resNZResource.getString("NetZero_application_cannot"), 2, as, 5, ZCast.m_resNZResource.getString("seconds_until_ZCast_ends"));
                    ZCast.displayDebug("ERROR CHECKER!! entering into ZCast.terminateProgram");
                    ZCast.terminateProgram(ERRORCHECKCODE, null);
                }
                Thread.sleep(1000L);
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug("ERROR CHECKER!! TIME TO DIE = " + timeToDie);
                ZCast.displayDebug(exception1);
            }
        ZCast.displayDebug("end of run ERROR CHECKER");
    }

    private static final int SLEEP_MILL = 1000;
    public static int ERRORCHECKCODE = -742;
    private static ErrorChecker m_errorChecker = null;
    private boolean timeToDie;

}
