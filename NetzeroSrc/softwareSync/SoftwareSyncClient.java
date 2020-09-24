// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SoftwareSyncClient.java

package softwareSync;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import nzcom.*;
import spe.DefaultItemListener;
import spe.SPEngine;

// Referenced classes of package softwareSync:
//            DirectoryList, DistributionTask, SyncFile

public class SoftwareSyncClient
{

    public SoftwareSyncClient()
    {
    }

    private static void addSPEngine(SyncFile syncfile)
    {
        SPEngine.getInstance().add(syncfile.srcFile, ".\\" + syncfile.destFile.replace('/', '\\'), SPEpriority, SPEmode, new DefaultItemListener(), syncfile.destFile);
    }

    private static void addTask(DistributionTask distributiontask)
    {
        removeFile(distributiontask);
        String s = "http://" + ServerParms.getParm("sdip", "sd.netzero.net").trim() + "/compaq/sd/" + distributiontask.getFileName();
        SyncFile syncfile = new SyncFile(s, distributiontask.getFileName(), getFileSize(s));
        addSPEngine(syncfile);
        syncFiles.addElement(syncfile);
    }

    private static boolean allFilesExist(Vector vector)
    {
        for(int i = 0; i < vector.size(); i++)
        {
            SyncFile syncfile = (SyncFile)vector.elementAt(i);
            File file = new File(syncfile.destFile);
            if(!file.exists())
                return false;
        }

        return true;
    }

    private static void createCTLFile(Vector vector)
    {
        removeFile(CtlFileName);
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(CtlFileName));
            for(int i = 0; i < vector.size(); i++)
            {
                SyncFile syncfile = (SyncFile)vector.elementAt(i);
                printwriter.println(syncfile.destFile);
            }

            printwriter.close();
        }
        catch(Exception _ex) { }
    }

    private static boolean createDir(DistributionTask distributiontask)
    {
        File file = new File(distributiontask.getFileName());
        return file.mkdir();
    }

    public static DirectoryList createDirectoryList(String s)
    {
        return new DirectoryList(s);
    }

    public static void doUpdate()
    {
        File file = new File(CtlFileName);
        if(!file.exists())
        {
            Vector vector = loadSyncFiles();
            if(vector == null)
                return;
            if(!allFilesExist(vector))
                return;
            createCTLFile(vector);
            removeFile(SyncFileName);
        }
        ZCast.displayDebug("Software Update - Do the Update");
        ZCast.m_osDetectNative.startProcess("nzDistw");
        System.exit(8);
    }

    private static int getFileSize(String s)
    {
        int i = 0;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            i = urlconnection.getContentLength();
        }
        catch(Exception _ex) { }
        return i;
    }

    private static Vector loadSyncFiles()
    {
        File file = new File(SyncFileName);
        if(!file.exists())
            return null;
        try
        {
            FileInputStream fileinputstream = new FileInputStream(SyncFileName);
            ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
            Vector vector = (Vector)objectinputstream.readObject();
            objectinputstream.close();
            return vector;
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    private static boolean removeFile(String s)
    {
        File file = new File(s);
        return file.delete();
    }

    private static boolean removeFile(DistributionTask distributiontask)
    {
        return removeFile(distributiontask.getFileName());
    }

    private static boolean removeFile(SyncFile syncfile)
    {
        return removeFile(syncfile.destFile);
    }

    private static void removeSPEngine(SyncFile syncfile)
    {
        SPEngine.getInstance().add(syncfile.srcFile, syncfile.destFile, SPEpriority, SPEmode, null, syncfile.destFile);
        SPEngine.getInstance().remove(syncfile.destFile);
    }

    public void run(DistributionTask adistributiontask[])
    {
        syncFiles = loadSyncFiles();
        boolean flag = false;
        if(syncFiles != null)
            flag = true;
        boolean flag1 = false;
        if(flag)
        {
            boolean flag2 = false;
            Vector vector = new Vector();
            for(int i = 0; i < adistributiontask.length; i++)
            {
                boolean flag3 = false;
                for(int j1 = 0; j1 < syncFiles.size(); j1++)
                {
                    SyncFile syncfile3 = (SyncFile)syncFiles.elementAt(j1);
                    if(!adistributiontask[i].getFileName().equals(syncfile3.destFile))
                        continue;
                    flag3 = true;
                    break;
                }

                if(flag3)
                    continue;
                flag2 = true;
                break;
            }

            if(!flag2)
            {
                for(int j = 0; j < syncFiles.size(); j++)
                {
                    SyncFile syncfile2 = (SyncFile)syncFiles.elementAt(j);
                    File file1 = new File(syncfile2.destFile);
                    if(syncfile2.fileSize == getFileSize(syncfile2.srcFile))
                        continue;
                    flag1 = true;
                    break;
                }

            } else
            {
                flag1 = true;
            }
        }
        if(flag1)
        {
            for(int k = 0; k < syncFiles.size(); k++)
            {
                SyncFile syncfile = (SyncFile)syncFiles.elementAt(k);
                removeSPEngine(syncfile);
                removeFile(syncfile.destFile);
            }

        }
        if(flag1 || !flag)
        {
            ZCast.displayDebug("Software Update - Start Over");
            syncFiles = new Vector();
            for(int l = 0; l < adistributiontask.length; l++)
                if(adistributiontask[l].getTask().equals("delete"))
                    removeFile(adistributiontask[l].getFileName());
                else
                if(adistributiontask[l].getTask().equals("update"))
                    addTask(adistributiontask[l]);

            saveSyncFiles(syncFiles);
        } else
        {
            ZCast.displayDebug("Software Update - Resume");
            for(int i1 = 0; i1 < syncFiles.size(); i1++)
            {
                SyncFile syncfile1 = (SyncFile)syncFiles.elementAt(i1);
                File file = new File(syncfile1.destFile);
                if(!file.exists())
                    addSPEngine(syncfile1);
            }

        }
    }

    private static void saveSyncFiles(Vector vector)
    {
        removeFile(SyncFileName);
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(SyncFileName);
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(vector);
            objectoutputstream.flush();
            fileoutputstream.close();
        }
        catch(IOException _ex)
        {
            ZCast.displayDebug("SoftwareSyncClient - saveSyncFiles, ERROR");
        }
    }

    protected static Vector syncFiles = null;
    protected static String SyncFileName = "SyncFile.dat";
    protected static String CtlFileName = "sdctrl";
    protected static int SPEpriority = 4;
    protected static int SPEmode = 2;

}
