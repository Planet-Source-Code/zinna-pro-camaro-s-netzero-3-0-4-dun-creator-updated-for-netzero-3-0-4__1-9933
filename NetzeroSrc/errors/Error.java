// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Error.java

package errors;

import java.io.Serializable;

public interface Error
    extends Serializable
{

    public abstract int getErrorType();

    public abstract String getUserId();

    public static final int DIALUP_ERROR = 100;
    public static final int SERVER_ERROR = 101;
}
