// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientUI.java

package ui;

import java.io.*;
import nzcom.ZCast;

public class ClientUI
{

    public ClientUI()
    {
    }

    public static Object deserializeUi(String s)
    {
        File file = new File(s);
        if(file.exists())
            try
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(file));
                Object obj = objectinputstream.readObject();
                objectinputstream.close();
                return obj;
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception.toString());
            }
        else
            ZCast.displayDebug("serialized object not found: " + s);
        return null;
    }

    public static void serializeObject(Object obj, String s)
    {
        if(obj == null)
        {
            ZCast.displayDebug("No object to serialize");
            return;
        }
        ZCast.displayDebug("Serializing " + obj.getClass().getName() + " to " + s);
        File file = new File(s);
        try
        {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(file));
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("serialization failed: " + exception.toString());
        }
    }
}
