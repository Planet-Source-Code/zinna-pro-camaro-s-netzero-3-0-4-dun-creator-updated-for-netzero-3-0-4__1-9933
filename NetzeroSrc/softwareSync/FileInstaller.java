// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileInstaller.java

package softwareSync;


public interface FileInstaller
{

    public abstract boolean promptInstall();

    public abstract boolean install(String s, String s1, String s2, String s3);
}
