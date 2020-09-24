// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZButtonPanel.java

package gui;

import java.awt.*;
import java.io.PrintStream;
import jclass.bwt.JCContainer;
import jclass.bwt.JCGroupBox;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButton

public class NZButtonPanel extends Panel
{

    public NZButtonPanel()
    {
        NumButtons = 0;
        Layout = -1;
        Buttons = null;
        box = null;
        BoxWidth = 0;
        BoxHeight = 0;
        initialize();
    }

    public NZButtonPanel(NZButton anzbutton[], int i)
    {
        NumButtons = 0;
        Layout = -1;
        Buttons = null;
        box = null;
        BoxWidth = 0;
        BoxHeight = 0;
        Layout = i;
        NumButtons = anzbutton.length;
        Buttons = anzbutton;
        ZCast.displayDebug("NumButtons == " + NumButtons);
        if(NumButtons == 0)
        {
            ZCast.displayDebug("returning");
            return;
        } else
        {
            initialize();
            setButtons(anzbutton, i);
            return;
        }
    }

    protected JCGroupBox getBox()
    {
        return box;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initialize()
    {
        ZCast.displayDebug("inside initialize");
        byte byte0 = 8;
        BoxWidth = 459;
        if(Buttons != null)
            BoxHeight = Buttons[0].getSize().height + 16 + 4;
        else
            BoxHeight = 40;
        ZCast.displayDebug("setLayout");
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.setLayout(null);
        add(panel, "Center");
        ZCast.displayDebug("make box");
        box = new JCGroupBox();
        ZCast.displayDebug("Made box");
        box.setName("BottomBox");
        box.setLayout(null);
        box.setShadowThickness(2);
        panel.add(box);
        box.setLocation(0, 0);
        ZCast.displayDebug("call layoutButtons");
        layoutButtons();
        setSize(475, BoxHeight);
        int i = 355 - getSize().height - 5;
        ZCast.displayDebug("temp == " + i);
        setLocation(5, i);
        box.setSize(BoxWidth, BoxHeight);
    }

    private void layoutButtons()
    {
        ZCast.displayDebug("Layout == " + Layout);
        if(Layout == -1)
            return;
        switch(Layout)
        {
        case 0: // '\0'
            box.setLayout(new FlowLayout());
            for(int i = 0; i < Buttons.length; i++)
                box.add(Buttons[i]);

            break;

        case 4: // '\004'
            break;

        case 1: // '\001'
            box.setLayout(new FlowLayout(2));
            for(int j = 0; j < Buttons.length; j++)
                box.add(Buttons[j]);

            break;

        case 2: // '\002'
            box.setLayout(new FlowLayout(0));
            for(int k = 0; k < Buttons.length; k++)
                box.add(Buttons[k]);

            break;

        case 3: // '\003'
            layoutWideRight();
            break;

        default:
            return;
        }
    }

    private void layoutWideRight()
    {
    }

    public static void main(String args[])
    {
        try
        {
            Frame frame;
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.TestFrame");
                frame = (Frame)class1.newInstance();
            }
            catch(Throwable _ex)
            {
                frame = new Frame();
            }
            String args1[] = {
                "Back", "Next", "Cancel"
            };
            NZButtonPanel nzbuttonpanel = new NZButtonPanel();
            frame.add("Center", nzbuttonpanel);
            frame.setSize(nzbuttonpanel.getSize());
            frame.setVisible(true);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Panel");
            throwable.printStackTrace(System.out);
        }
    }

    public void setButtons(NZButton anzbutton[], int i)
    {
        Buttons = anzbutton;
        Layout = i;
        NumButtons = anzbutton.length;
        char c = '\u01CC';
        int j = Buttons[0].getSize().height + 16;
        box.setSize(c, j);
        setSize(c, j);
        layoutButtons();
        repaint();
    }

    public void setSize(int i, int j)
    {
        super.setSize(i, j);
        box.setSize(i, j);
    }

    public void setWidth(int i)
    {
        super.setSize(i, BoxHeight);
        box.setSize(i, BoxHeight);
    }

    public static final int CENTER = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int WIDE_RIGHT = 3;
    public static final int WIDE_LEFT = 4;
    private int NumButtons;
    private int Layout;
    NZButton Buttons[];
    private JCGroupBox box;
    private int BoxWidth;
    private int BoxHeight;
}
