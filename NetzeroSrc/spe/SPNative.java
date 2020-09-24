// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPNative.java

package spe;


public class SPNative
{

    public static native boolean cancel();

    public static native boolean errorOccurred();

    public static native boolean getActive();

    public static native float getAveKbps();

    public static native int getErrorDetail();

    public static native String getErrorString();

    public static native int getErrorType();

    public static native int getFileTotal();

    public static native int getNetMTU();

    public static native int getNetWindowSize();

    public static native int getStartTime();

    public static native boolean getStreaming();

    public static native int getTimeLeft();

    public static native int getTotalTime();

    public static native int getTransferLeft();

    public static native int getTransferred();

    public static native int getTransferTotal();

    public static native boolean init();

    public static native boolean isFinished();

    public static native boolean isOpen();

    public static native boolean open(String s, String s1, int i);

    public static native boolean setMaxKbps(float f);

    public static native boolean setNetSettings(int i, int j);

    public static native boolean setPreferredSettings();

    public static native boolean setSensitivities(int i, int j, int k, int l);

    public static native boolean shutdown();

    public static native boolean warningOccurred();

    public static native int getWarningDetail();

    public static native String getWarningString();

    public static native int getWarningType();

    public SPNative()
    {
    }

    public static final int TypeNoError = 0;
    public static final int TypeErrorMonitor = 1;
    public static final int TypeErrorIO = 2;
    public static final int TypeErrorFinished = 3;
    public static final int RasNoError = 0;
    public static final int RasFailedEnable = 1;
    public static final int RasFailedDisable = 2;
    public static final int RasFailedCollect = 3;
    public static final int RasFailedGet = 4;
    public static final int StepNoError = 0;
    public static final int StepBadParameter = 1;
    public static final int StepTransferComplete = 2;
    public static final int StepLocalFileError = 3;
    public static final int StepRemoteFileError = 4;
    public static final int StepRemoteFileClosed = 5;
}
