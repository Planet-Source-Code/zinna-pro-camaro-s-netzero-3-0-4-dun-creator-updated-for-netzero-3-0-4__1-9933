// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncFile.java

package softwareSync;

import java.io.Serializable;

public class SyncFile
    implements Serializable
{

    public SyncFile()
    {
        srcFile = null;
        destFile = null;
        fileSize = 0;
    }

    public SyncFile(String s, String s1, int i)
    {
        srcFile = null;
        destFile = null;
        fileSize = 0;
        srcFile = s;
        destFile = s1;
        fileSize = i;
    }

    public String srcFile;
    public String destFile;
    public int fileSize;
}
