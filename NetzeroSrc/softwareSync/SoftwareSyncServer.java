// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SoftwareSyncServer.java

package softwareSync;

import java.util.Date;
import nzcom.ZCast;

// Referenced classes of package softwareSync:
//            DistributionTask, DirectoryList, FileStats

public class SoftwareSyncServer
{

    public SoftwareSyncServer()
    {
        localFiles = null;
        codePath = "currentSoftware";
        theDistTask = null;
        badFileSize = "";
        DistributionTask distributiontask = new DistributionTask();
        distributiontask.setFileName("nzDist.zip");
        distributiontask.setMetaData(".\\nzDist.zip");
        distributiontask.setTask("update");
        theDistTask = distributiontask;
    }

    private int findMatchingFile(int i, FileStats afilestats[])
    {
        String s = localFiles[i].getName();
        s = s.substring(s.lastIndexOf("/"), s.length());
        for(int j = 0; j < afilestats.length; j++)
            if(afilestats[j].getName().indexOf(s) > 0)
                return j;

        return -1;
    }

    private boolean hasFileChanged(FileStats filestats, FileStats filestats1)
    {
        if((filestats.getSize() == filestats1.getSize()) | (filestats.getSize() == 0L))
            return false;
        if(filestats.getName().indexOf("zcast1_6.zip") > 0)
            badFileSize = String.valueOf(filestats.getSize());
        return true;
    }

    public DistributionTask[] processFileList(FileStats afilestats[])
    {
        DistributionTask adistributiontask[] = new DistributionTask[1];
        boolean flag = false;
        boolean flag1 = false;
        localFiles = (new DirectoryList(codePath)).getFileList();
        for(int j = 0; j < localFiles.length; j++)
        {
            int i = findMatchingFile(j, afilestats);
            if(i >= 0)
            {
                if(hasFileChanged(afilestats[i], localFiles[j]))
                    flag1 = true;
            } else
            {
                flag1 = true;
            }
            if(flag1)
            {
                j = localFiles.length;
                adistributiontask[0] = theDistTask;
            }
        }

        if(adistributiontask[0] != null)
        {
            double d = Math.random();
            if(d > 1.1000000000000001D)
            {
                adistributiontask = new DistributionTask[1];
                ZCast.displayDebug("Software out of date !!! NO LUCK " + badFileSize + " " + new Date());
            } else
            {
                ZCast.displayDebug("Software out of date !!! " + badFileSize + " " + new Date());
            }
        }
        return adistributiontask;
    }

    public void setClientBasePath(FileStats afilestats[])
    {
        String s = afilestats[0].getName();
    }

    private FileStats localFiles[];
    private String codePath;
    private DistributionTask theDistTask;
    private String badFileSize;
}
