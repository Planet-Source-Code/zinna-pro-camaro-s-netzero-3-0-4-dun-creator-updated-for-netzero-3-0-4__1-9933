// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DistributionTask.java

package softwareSync;

import java.io.Serializable;

public class DistributionTask
    implements Serializable
{

    public String getFileName()
    {
        return fileName;
    }

    public Object getMetaData()
    {
        return metaData;
    }

    public String getTask()
    {
        return task;
    }

    public void setFileName(String s)
    {
        fileName = s;
    }

    public void setMetaData(Object obj)
    {
        metaData = obj;
    }

    public void setTask(String s)
    {
        task = s;
    }

    public DistributionTask()
    {
        fileName = "";
        task = "";
        metaData = null;
    }

    private String fileName;
    private String task;
    private Object metaData;
}
