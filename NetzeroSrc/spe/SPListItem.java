// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPListItem.java

package spe;

import java.io.File;
import nzcom.ZCast;

// Referenced classes of package spe:
//            SPItemListener, SPPriority, SPNative

public class SPListItem
{

    public SPListItem(String s, String s1, String s2, int i, int j, SPItemListener spitemlistener, Object obj)
    {
        m_sRemoteFileName = null;
        m_sLocalFileName = null;
        m_sTempFileName = null;
        m_nPriority = 3;
        m_nMode = 0;
        m_CallBack = null;
        m_Identifier = null;
        m_bDownloading = false;
        m_sRemoteFileName = s;
        m_sLocalFileName = s1;
        m_sTempFileName = s2;
        m_nPriority = i;
        m_nMode = j;
        m_CallBack = spitemlistener;
        m_Identifier = obj;
    }

    public boolean canInterrupt()
    {
        if(m_bDownloading)
        {
            int i = SPNative.getFileTotal();
            double d = i <= 0 ? 0 : SPNative.getTransferLeft() / i;
            return d < SPPriority.getPercent(getPriority());
        } else
        {
            return true;
        }
    }

    public void clear()
    {
        m_sRemoteFileName = "";
        m_sLocalFileName = "";
        m_sTempFileName = "";
        m_nPriority = 3;
        m_nMode = 0;
        m_CallBack = null;
        m_Identifier = null;
        m_bDownloading = false;
    }

    protected void deleteFile(String s)
    {
        try
        {
            if(!(new File(s)).delete())
                error("Nonfatal error in SPListItem.deleteFile. Failed to delete file " + s);
        }
        catch(Exception exception)
        {
            error("Nonfatal error in SPListItem.deleteFile. Error deleting file " + s + ". Exception: " + exception.toString());
        }
    }

    public void deleteTempFile()
    {
        deleteFile(m_sTempFileName);
    }

    public void error(String s)
    {
        if(m_CallBack != null)
            try
            {
                m_CallBack.onItemError(s, getIdentifier());
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
    }

    public void finished()
    {
        m_bDownloading = false;
        if(moveFile(m_sTempFileName, m_sLocalFileName, true) && m_CallBack != null)
            try
            {
                m_CallBack.onItemFinished(m_Identifier);
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
    }

    public Object getIdentifier()
    {
        return m_Identifier;
    }

    public int getPriority()
    {
        return m_nPriority;
    }

    public String getTempFileName()
    {
        return m_sTempFileName;
    }

    protected boolean moveFile(String s, String s1, boolean flag)
    {
        boolean flag1 = false;
        try
        {
            (new File(parseAndGetDirName(s1))).mkdir();
            File file = new File(s1);
            if(flag && file.exists() && !file.delete())
                error("Nonfatal error in SPListItem.moveFile. Failed to delete file " + s);
            if(!(flag1 = (new File(s)).renameTo(file)))
                error("Nonfatal error in SPListItem.moveFile. Failed to move file " + s + " to " + s1);
        }
        catch(Exception exception)
        {
            error("Nonfatal error in SPListItem.moveFile. Error moving file " + s + " to " + s1 + ". Exception: " + exception.toString());
        }
        return flag1;
    }

    public boolean open()
    {
        if(SPNative.open(m_sRemoteFileName, m_sTempFileName, m_nMode))
        {
            m_bDownloading = true;
            return true;
        } else
        {
            m_bDownloading = false;
            return false;
        }
    }

    protected String parseAndGetDirName(String s)
    {
        String s1 = "";
        int i = s.lastIndexOf(92);
        if(i == 0)
            i = s.lastIndexOf(47);
        if(i > 0)
            s1 = s.substring(0, i);
        return s1;
    }

    public void setMode(int i)
    {
        m_nMode = i;
    }

    public void close()
    {
        if(m_bDownloading)
        {
            SPNative.cancel();
            m_bDownloading = false;
        }
    }

    private String m_sRemoteFileName;
    private String m_sLocalFileName;
    private String m_sTempFileName;
    private int m_nPriority;
    private int m_nMode;
    private SPItemListener m_CallBack;
    private Object m_Identifier;
    private boolean m_bDownloading;
}
