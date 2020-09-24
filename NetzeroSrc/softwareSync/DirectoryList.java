// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DirectoryList.java

package softwareSync;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package softwareSync:
//            FileStats

public class DirectoryList
    implements Serializable
{

    public DirectoryList(String s)
    {
        fileStatVector = new Vector();
        fileList = null;
        getAllFiles(s);
    }

    private FileStats[] getAllFiles(String s)
    {
        File file = new File(s);
        String as[] = new String[70];
        String as1[] = null;
        boolean flag = false;
        as1 = file.list();
        for(int i = 0; i < as1.length; i++)
        {
            String s1 = s + "/" + as1[i];
            File file1 = new File(s1);
            if(file1.isDirectory())
            {
                FileStats filestats = new FileStats(file1);
                filestats.setIsDirectory(true);
                fileStatVector.addElement(filestats);
                getAllFiles(s1);
            } else
            {
                FileStats filestats1 = new FileStats(file1);
                fileStatVector.addElement(filestats1);
            }
        }

        fileList = new FileStats[fileStatVector.size()];
        for(int j = 0; j < fileList.length; j++)
        {
            fileList[j] = (FileStats)fileStatVector.elementAt(j);
            fileList[j].setBasePath(s);
        }

        return fileList;
    }

    public FileStats[] getFileList()
    {
        return fileList;
    }

    private Vector fileStatVector;
    private FileStats fileList[];
}
