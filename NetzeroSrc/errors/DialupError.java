// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialupError.java

package errors;

import java.util.Date;

// Referenced classes of package errors:
//            Error

public interface DialupError
    extends Error
{

    public abstract Date getDate();

    public abstract String getErrorCode();

    public abstract String getLocationNumber();

    public abstract String getPhoneNumber();

    public abstract String getTimestamp();
}
