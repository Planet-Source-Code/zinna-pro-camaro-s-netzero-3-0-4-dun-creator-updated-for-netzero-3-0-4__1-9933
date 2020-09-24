// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileStats.java

package softwareSync;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class FileStats
    implements Serializable
{

    public FileStats(File file)
    {
        basePath = null;
        name = "";
        lastModified = 0L;
        size = 0L;
        isDirectory = false;
        setName(file.getPath());
        setLastModified(file.lastModified());
        setSize(file.length());
    }

    public String getBasePath()
    {
        return basePath;
    }

    public String getFileNameOnly()
    {
        String s = getName();
        s = s.substring(getBasePath().length() + 1, s.length());
        return s;
    }

    public boolean getIsDirectory()
    {
        return isDirectory;
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public String getName()
    {
        return name;
    }

    public long getSize()
    {
        return size;
    }

    public void setBasePath(String s)
    {
        basePath = s;
    }

    public void setIsDirectory(boolean flag)
    {
        isDirectory = flag;
    }

    public void setLastModified(long l)
    {
        lastModified = l;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setSize(long l)
    {
        size = l;
    }

    public String toString()
    {
        return getName() + " " + getSize() + " " + new Date(getLastModified());
    }

    private String basePath;
    private String name;
    private long lastModified;
    private long size;
    private boolean isDirectory;
}
