// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileVersion.java

package softwareSync;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;
import nzcom.OSDetectNative;
import nzcom.ZCast;

public class FileVersion
{

    public FileVersion(File file)
    {
        m_Version = new Vector();
        fromString(OSDetectNative.getFileVersion(file.getAbsolutePath()));
    }

    public FileVersion(String s)
    {
        m_Version = new Vector();
        fromString(s);
    }

    public void fromString(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, ",.");
        m_Version.removeAllElements();
        try
        {
            for(; stringtokenizer.hasMoreTokens(); m_Version.addElement(new Integer(stringtokenizer.nextToken().trim())));
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public boolean greaterThan(FileVersion fileversion)
    {
        return greaterThan(fileversion.getVersionVector(), 0);
    }

    protected Vector getVersionVector()
    {
        return m_Version;
    }

    protected boolean greaterThan(Vector vector, int i)
    {
        if(vector.size() <= i || m_Version.size() <= i)
            return vector.size() < m_Version.size();
        Integer integer = (Integer)vector.elementAt(i);
        Integer integer1 = (Integer)m_Version.elementAt(i);
        if(integer1.intValue() > integer.intValue())
            return true;
        if(integer1.intValue() == integer.intValue())
            return greaterThan(vector, i + 1);
        else
            return false;
    }

    private Vector m_Version;
}
