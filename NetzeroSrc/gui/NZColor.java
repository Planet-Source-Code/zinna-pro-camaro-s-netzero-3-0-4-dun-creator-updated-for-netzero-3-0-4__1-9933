// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZColor.java

package gui;

import java.awt.Color;
import java.awt.SystemColor;

public class NZColor
{

    public NZColor()
    {
    }

    public static final Color BACKGROUND;
    public static final Color TEXT;
    public static final Color TEXT_BACKGROUND;

    static 
    {
        BACKGROUND = SystemColor.control;
        TEXT = SystemColor.controlText;
        TEXT_BACKGROUND = Color.white;
    }
}
