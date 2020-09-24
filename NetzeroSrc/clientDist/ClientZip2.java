// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientZip2.java

package clientDist;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClientZip2
{

    public ClientZip2()
    {
        cName = null;
        cFile = null;
        ctlNames = null;
        bChkReplace = false;
        logName = "swdist.log";
        logFile = null;
        pwLog = null;
        bLogging = false;
        statusDialog = null;
        fileCntr = 0;
    }

    public ClientZip2(String s)
    {
        cName = null;
        cFile = null;
        ctlNames = null;
        bChkReplace = false;
        logName = "swdist.log";
        logFile = null;
        pwLog = null;
        bLogging = false;
        statusDialog = null;
        fileCntr = 0;
        cName = s;
    }

    public static void main(String args[])
    {
        if(args.length > 0)
            defName = args[0];
        System.out.println("Main return: " + runUpdate());
    }

    public static boolean runUpdate()
    {
        ClientZip2 clientzip2 = new ClientZip2(defName);
        return clientzip2.performUpdate();
    }

    public static void checkDist(String s, String s1, String s2)
    {
        ClientZip2 clientzip2 = new ClientZip2();
        clientzip2.setChkReplace(true);
        clientzip2.addMessage("Distribution check for " + s2);
        try
        {
            ZipFile zipfile = new ZipFile(s);
            clientzip2.cmdExtract(zipfile, s1, s2);
        }
        catch(Exception exception)
        {
            clientzip2.addMessage("...Error: Distribution check failed for " + s1 + " to " + s2);
            clientzip2.addMessage(exception.toString());
        }
    }

    private boolean performUpdate()
    {
        statusDialog = new Dialog(new Frame("NetZero Software Update"), "NetZero Software Update");
        statusDialog.setLayout(new FlowLayout(1));
        statusDialog.setResizable(false);
        statusDialog.setSize(300, 100);
        centerComponent(statusDialog);
        statusDialog.add(new Label("Software update in progress.  Please wait."));
        percentLabel = new Label("                                                      ");
        statusDialog.add(percentLabel);
        statusDialog.setVisible(true);
        openLogFile();
        if(loadControlFile(cName) > 0)
            processControlFiles();
        if(globalCt == 0)
            if(cFile.delete())
                addMessage("Control file deleted");
            else
                addMessage("Error: Control file could not be deleted");
        if(globalCt == 0)
            addMessage("Distribution processing completed with no errors.");
        else
            addMessage("Distribution processing completed with errors.");
        if(bLogging)
            pwLog.close();
        statusDialog.dispose();
        return globalCt <= 0;
    }

    public void processControlFiles()
    {
        for(int i = 0; i < ctlNames.length; i++)
        {
            fileCntr = i + 1;
            String s = ctlNames[i];
            addMessage("Examining " + s);
            processOneControl(s);
        }

    }

    public void processOneControl(String s)
    {
        File file = new File(s);
        if(!file.exists())
        {
            addMessage(s + " not found");
            return;
        }
        try
        {
            ZipFile zipfile = new ZipFile(s);
            ZipEntry zipentry = zipfile.getEntry("sd.ins");
            if(zipentry != null)
            {
                errCt = 0;
                processEntry(zipfile, zipentry);
                if(errCt == 0 && file.delete())
                    addMessage("Distribution file " + s + " deleted");
            } else
            {
                addMessage("Error: Instruction file not found");
            }
        }
        catch(Exception exception)
        {
            addMessage(exception.toString());
        }
    }

    public void processEntry(ZipFile zipfile, ZipEntry zipentry)
    {
        Vector vector = new Vector(10);
        curZip = zipfile;
        errCt = 0;
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(zipfile.getInputStream(zipentry)));
            String s;
            while((s = bufferedreader.readLine()) != null) 
                vector.addElement(s);
            bufferedreader.close();
            Enumeration enumeration = vector.elements();
            int i = 0;
            String s1;
            for(; enumeration.hasMoreElements(); processCommand(s1))
            {
                percentLabel.setText("File " + fileCntr + " of " + ctlNames.length + ": " + (++i * 100) / vector.size() + "% Complete");
                statusDialog.toFront();
                s1 = (String)enumeration.nextElement();
            }

            zipfile.close();
            curZip = null;
        }
        catch(Exception exception)
        {
            addMessage(exception.toString());
        }
    }

    public void processCommand(String s)
    {
        String s1 = null;
        String s2 = null;
        String s3 = null;
        addMessage("Processing " + s);
        if(s.startsWith("*"))
            return;
        StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
        int i = stringtokenizer.countTokens();
        if(i < 2)
            return;
        s1 = stringtokenizer.nextToken();
        s2 = stringtokenizer.nextToken();
        if(i >= 3)
            s3 = stringtokenizer.nextToken();
        if(s1.equals("extract"))
        {
            if(cmdExtract(curZip, s2, s3))
                processError(s);
            return;
        }
        if(s1.equals("mkdir"))
        {
            if(cmdMkDir(s2))
                processError(s);
            return;
        }
        if(s1.equals("delete"))
        {
            if(cmdDelete(s2))
                processError(s);
            return;
        }
        if(s1.equals("copy"))
        {
            if(cmdCopy(s2, s3))
                processError(s);
            return;
        }
        if(s1.equals("rename") && cmdRename(s2, s3))
            processError(s);
    }

    public boolean cmdCopy(String s, String s1)
    {
        File file = new File(s);
        if(!file.exists())
        {
            addMessage("...Error: " + s + " could not be copied to " + s1 + ", from file not found.");
            return true;
        }
        File file1 = new File(s1);
        try
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            byte abyte0[] = new byte[4096];
            int i;
            while((i = fileinputstream.read(abyte0, 0, 4096)) > -1) 
                fileoutputstream.write(abyte0, 0, i);
            fileinputstream.close();
            fileoutputstream.close();
            addMessage("...Success: " + s + " copied to " + s1);
        }
        catch(Exception exception)
        {
            addMessage("...Error " + s + " could not be copied to " + s1);
            addMessage(exception.toString());
            return true;
        }
        return false;
    }

    public boolean cmdDelete(String s)
    {
        File file = new File(s);
        if(!file.exists())
        {
            addMessage("...No Action: " + s + " could not be deleted, file not found");
            return false;
        }
        if(!file.delete())
        {
            addMessage("...Error: " + s + " could not be deleted");
            return true;
        } else
        {
            addMessage("...Success: " + s + " deleted");
            return false;
        }
    }

    public boolean cmdExtract(ZipFile zipfile, String s, String s1)
    {
        if(s1 == null)
            s1 = s;
        try
        {
            ZipEntry zipentry = zipfile.getEntry(s);
            if(zipentry != null)
            {
                File file = new File(s1);
                if(checkFileReplace(zipentry, file))
                {
                    addMessage("...No Action: File already correct version.");
                    return false;
                }
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                InputStream inputstream = zipfile.getInputStream(zipentry);
                byte abyte0[] = new byte[4096];
                int i;
                while((i = inputstream.read(abyte0, 0, 4096)) > -1) 
                    fileoutputstream.write(abyte0, 0, i);
                inputstream.close();
                fileoutputstream.close();
                addMessage("...Success: " + s + " extracted to " + s1);
            } else
            {
                addMessage("...No Action: " + s + " not found in zip file");
            }
        }
        catch(Exception exception)
        {
            addMessage("...Error: " + s + " could not be extracted to " + s1);
            addMessage(exception.toString());
            return true;
        }
        return false;
    }

    public boolean cmdMkDir(String s)
    {
        File file = new File(s);
        if(file.exists())
        {
            addMessage("...No Action: Directory " + s + " not created, it already exists.");
            return false;
        }
        if(!file.mkdirs())
        {
            addMessage("...Error: Directory" + s + " could not be created");
            return true;
        } else
        {
            addMessage("...Success: Directory " + s + " created");
            return false;
        }
    }

    public boolean cmdRename(String s, String s1)
    {
        File file = new File(s);
        File file1 = new File(s1);
        if(!file.renameTo(file1))
        {
            addMessage("...Error:" + s + " could not be renamed to " + s1);
            return true;
        } else
        {
            addMessage("...Success:" + s + " renamed to " + s1);
            return false;
        }
    }

    public static void centerComponent(Component component)
    {
        if(component != null)
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Point point = new Point((dimension.width - component.getSize().width) / 2, (dimension.height - component.getSize().height) / 2);
            component.setLocation(point.x, point.y);
        }
    }

    public int loadControlFile(String s)
    {
        int i = 0;
        cFile = new File(s);
        if(!cFile.exists())
        {
            addMessage("Error: " + s + " file not found");
            return 0;
        }
        Vector vector = new Vector(8);
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(cFile)));
            String s1;
            while((s1 = bufferedreader.readLine()) != null) 
                if(!s1.equals(" ") && s1.length() > 1)
                {
                    vector.addElement(s1);
                    i++;
                }
            bufferedreader.close();
            ctlNames = new String[i];
            int j = 0;
            for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
                ctlNames[j++] = (String)enumeration.nextElement();

            addMessage(i + " names added to processing");
        }
        catch(Exception exception)
        {
            addMessage(exception.toString());
        }
        return i;
    }

    public void processError(String s)
    {
        if(s.indexOf("-nr") != -1)
        {
            return;
        } else
        {
            errCt++;
            globalCt++;
            return;
        }
    }

    public void setChkReplace(boolean flag)
    {
        bChkReplace = flag;
    }

    public boolean checkFileReplace(ZipEntry zipentry, File file)
    {
        if(!bChkReplace)
            return false;
        if(!file.exists())
            return false;
        else
            return zipentry.getSize() == file.length();
    }

    public void openLogFile()
    {
        logFile = new File(logName);
        try
        {
            pwLog = new PrintWriter(new FileWriter(logName, true));
            bLogging = true;
            pwLog.println("");
            pwLog.println(new Date());
        }
        catch(Exception exception)
        {
            addMessage(exception.toString());
        }
    }

    public void addMessage(String s)
    {
        if(bLogging)
            pwLog.println(s);
        else
            System.out.println(s);
    }

    private static String defName = "sdctrl";
    private String cName;
    private File cFile;
    private String ctlNames[];
    private ZipFile curZip;
    private ZipEntry curEntry;
    private boolean bChkReplace;
    private String logName;
    private File logFile;
    private PrintWriter pwLog;
    private boolean bLogging;
    private static int errCt = 0;
    private static int globalCt = 0;
    private Dialog statusDialog;
    private Label percentLabel;
    private int fileCntr;

}
