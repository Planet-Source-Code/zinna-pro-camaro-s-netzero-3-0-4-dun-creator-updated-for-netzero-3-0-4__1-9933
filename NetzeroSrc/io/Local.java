// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Local.java

package io;

import java.io.*;
import nzcom.ZCast;

public class Local
{

    public static Object deserialize(String s, boolean flag)
    {
        Object obj = null;
        File file = new File(s);
        if(file.exists())
        {
            ObjectInputStream objectinputstream = null;
            try
            {
                objectinputstream = new ObjectInputStream(new FileInputStream(file));
                obj = objectinputstream.readObject();
                objectinputstream.close();
                objectinputstream = null;
            }
            catch(Exception exception1)
            {
                ZCast.displayDebug(exception1);
            }
            finally
            {
                try
                {
                    if(objectinputstream != null)
                        objectinputstream.close();
                    if(flag)
                        file.delete();
                }
                catch(IOException ioexception)
                {
                    ZCast.displayDebug(ioexception);
                }
            }
        }
        return obj;
    }

    public static void serialize(String s, Object obj)
    {
        ObjectOutputStream objectoutputstream = null;
        try
        {
            objectoutputstream = new ObjectOutputStream(new FileOutputStream(s));
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
            objectoutputstream = null;
        }
        catch(Exception exception1)
        {
            ZCast.displayDebug(exception1);
        }
        finally
        {
            try
            {
                if(objectoutputstream != null)
                    objectoutputstream.close();
            }
            catch(IOException ioexception)
            {
                ZCast.displayDebug(ioexception);
            }
        }
    }

    public Local()
    {
    }
}
