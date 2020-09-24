// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZDialogBox.java

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.StringTokenizer;
import jclass.bwt.JCComponent;
import jclass.bwt.JCLabel;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButton, NZLabel, BulletinLayout, NZColor, 
//            NZFont, AgComponent

public class NZDialogBox
    implements Runnable
{
    private class MyItemListener
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            System.out.println("e is " + itemevent);
            NZDialogBox.NoShowState = NZDialogBox.NoShowCB.getState();
        }

        MyItemListener()
        {
        }
    }


    private static AgComponent addPic(Panel panel, int i)
    {
        AgComponent agcomponent = null;
        switch(i)
        {
        case 0: // '\0'
            agcomponent = new AgComponent();
            agcomponent.initialize(null, "images/dialog/Information.gif");
            agcomponent.setLocation(5, 5);
            panel.add(agcomponent);
            break;

        case 2: // '\002'
            agcomponent = new AgComponent();
            agcomponent.initialize(null, "images/dialog/Error.gif");
            agcomponent.setLocation(5, 5);
            panel.add(agcomponent);
            break;

        case 1: // '\001'
            agcomponent = new AgComponent();
            agcomponent.initialize(null, "images/dialog/Question.gif");
            agcomponent.setLocation(5, 5);
            panel.add(agcomponent);
            break;

        case 3: // '\003'
            agcomponent = new AgComponent();
            agcomponent.initialize(null, "images/dialog/Warn.gif");
            agcomponent.setLocation(5, 5);
            panel.add(agcomponent);
            break;
        }
        return agcomponent;
    }

    private static Dialog createDialog(String s, String s1, int i)
    {
        Dialog dialog1 = null;
        ZCast.displayDebug("              Creating window");
        if(ZCast.topFrame != null)
        {
            ZCast.displayDebug("topFrame not null");
            dialog1 = new Dialog(ZCast.topFrame, s, true);
        } else
        {
            dialog1 = new Dialog(new Frame(), s, true);
        }
        dialog1.setFont(new NZFont());
        dialog1.setBackground(NZColor.BACKGROUND);
        dialog1.setLayout(new BorderLayout());
        dialog1.setResizable(false);
        Panel panel = new Panel();
        panel.setLayout(new BulletinLayout());
        dialog1.add("Center", panel);
        Panel panel1 = getBottom(ButtonStrings);
        panel.add(panel1);
        dialog1.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                NZDialogBox.setSelectedValue(-1);
                NZDialogBox.dialog.dispose();
                NZDialogBox.dialog = null;
            }

            public void windowOpened(WindowEvent windowevent)
            {
                OSDetectNative.updateProcessIcons();
            }

            public void windowActivated(WindowEvent windowevent)
            {
                if(NZDialogBox.b.length > 0)
                    NZDialogBox.b[0].requestFocus();
            }

        });
        AgComponent agcomponent = addPic(panel, i);
        StringTokenizer stringtokenizer = new StringTokenizer(s1, "\n");
        int j = stringtokenizer.countTokens();
        NZLabel nzlabel = new NZLabel(s1);
        FontMetrics fontmetrics = nzlabel.getFontMetrics(new NZFont());
        int k = getMaxWidth(fontmetrics, s1);
        int l = fontmetrics.getHeight() + 4;
        nzlabel.setSize(k + 10, l * j);
        nzlabel.setLocation(40, 5);
        panel.add(nzlabel);
        Panel panel2 = new Panel();
        if(CountDownLabel != null)
        {
            panel.add(CountDownLabel);
            panel.add(DisplayLabel);
            CountDownLabel.setSize(15, 18);
        }
        int i1;
        int j1;
        if(agcomponent != null)
        {
            i1 = k + agcomponent.fetchSize().width + 30;
            j1 = j * l + 70;
        } else
        {
            i1 = k + 30;
            j1 = j * l + 50;
        }
        if(CountDownLabel != null)
        {
            j1 += 25;
            CountDownLabel.setLocation(40, l * j + 10);
            DisplayLabel.setLocation(65, l * j + 10);
        }
        dialog1.setSize(i1, j1);
        if(DisplayString != null)
            panel1.setLocation(i1 / 2 - panel1.getSize().width / 2, l * j + 35);
        else
            panel1.setLocation(i1 / 2 - panel1.getSize().width / 2, l * j + 15);
        return dialog1;
    }

    private static Dialog createDialogNoShow(String s, String s1, int i, boolean flag)
    {
        byte byte0 = 0;
        Dialog dialog1 = null;
        ZCast.displayDebug("              Creating window");
        if(ZCast.topFrame != null)
        {
            ZCast.displayDebug("topFrame not null");
            dialog1 = new Dialog(ZCast.topFrame, s, true);
        } else
        {
            dialog1 = new Dialog(new Frame(), s, true);
        }
        dialog1.setFont(new NZFont());
        dialog1.setBackground(NZColor.BACKGROUND);
        dialog1.setLayout(new BorderLayout());
        dialog1.setResizable(false);
        Panel panel = new Panel();
        panel.setLayout(new BulletinLayout());
        dialog1.add("Center", panel);
        Panel panel1 = getBottom(ButtonStrings);
        panel.add(panel1);
        dialog1.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                NZDialogBox.setSelectedValue(-1);
                NZDialogBox.dialog.dispose();
                NZDialogBox.dialog = null;
            }

            public void windowOpened(WindowEvent windowevent)
            {
                OSDetectNative.updateProcessIcons();
            }

            public void windowActivated(WindowEvent windowevent)
            {
                if(NZDialogBox.b.length > 0)
                    NZDialogBox.b[0].requestFocus();
            }

        });
        AgComponent agcomponent = addPic(panel, i);
        StringTokenizer stringtokenizer = new StringTokenizer(s1, "\n");
        int j = stringtokenizer.countTokens();
        NZLabel nzlabel = new NZLabel(s1);
        FontMetrics fontmetrics = nzlabel.getFontMetrics(new NZFont());
        int k = getMaxWidth(fontmetrics, s1);
        int l = fontmetrics.getHeight() + 4;
        nzlabel.setSize(k + 10, l * j);
        nzlabel.setLocation(40, 5);
        panel.add(nzlabel);
        if(UrlString != null)
            byte0 = 30;
        else
            byte0 = 5;
        int i1;
        int j1;
        if(agcomponent != null)
        {
            i1 = k + agcomponent.fetchSize().width + 30;
            j1 = j * l + 70 + byte0;
        } else
        {
            i1 = k + 30;
            j1 = j * l + 50 + byte0;
        }
        if(UrlString != null)
        {
            Panel panel2 = getUrlPanel();
            panel2.setLocation(i1 / 2 - panel2.getSize().width / 2, l * j + 2);
            panel.add(panel2);
        }
        dialog1.setSize(i1, j1);
        NoShowCB = new Checkbox("Don't Show Me This Again");
        NoShowCB.setLocation(40, l * j + byte0);
        NoShowCB.addItemListener(new MyItemListener());
        panel.add(NoShowCB);
        panel1.setLocation(i1 / 2 - panel1.getSize().width / 2, l * j + 20 + byte0);
        return dialog1;
    }

    private static Panel getBottom(String as[])
    {
        Panel panel = new Panel(new FlowLayout());
        b = new NZButton[as.length];
        for(int i = 0; i < as.length; i++)
        {
            b[i] = new NZButton(as[i]);
            b[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    int j = NZDialogBox.getButtonIndex(actionevent);
                    NZDialogBox.setSelectedValue(j);
                    NZDialogBox.dialog.dispose();
                    NZDialogBox.dialog = null;
                }

            });
            panel.add(b[i]);
        }

        panel.setSize(panel.getPreferredSize());
        return panel;
    }

    private static int getButtonIndex(ActionEvent actionevent)
    {
        int i = -1;
        for(int j = 0; j < ButtonStrings.length; j++)
        {
            if(!ButtonStrings[j].equals(actionevent.getActionCommand()))
                continue;
            i = j;
            break;
        }

        return i;
    }

    private static String getLongestLine(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\n");
        int i = 0;
        int j = 0;
        for(int k = 1; stringtokenizer.hasMoreTokens(); k++)
        {
            String s1 = stringtokenizer.nextToken();
            if(s1.length() > i)
            {
                i = s1.length();
                j = k;
            }
        }

        stringtokenizer = new StringTokenizer(s, "\n");
        String s2 = null;
        for(int l = 0; l < j; l++)
            s2 = stringtokenizer.nextToken();

        return s2;
    }

    private static int getMaxWidth(FontMetrics fontmetrics, String s)
    {
        String s1 = getLongestLine(s);
        int i = fontmetrics.stringWidth(s1);
        if(DisplayString != null)
        {
            int j = fontmetrics.stringWidth(DisplayString) + 25;
            if(j > i)
                i = j;
        }
        return i;
    }

    private static int getSelectedValue()
    {
        return selectedValue;
    }

    private static Panel getUrlPanel()
    {
        Panel panel = new Panel(new FlowLayout());
        NZButton nzbutton = new NZButton(UrlString);
        nzbutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                NZDialogBox.setSelectedValue(100);
                NZDialogBox.dialog.dispose();
                NZDialogBox.dialog = null;
            }

        });
        panel.add(nzbutton);
        panel.setSize(panel.getPreferredSize());
        return panel;
    }

    public static Image loadImage(String s)
    {
        Image image = null;
        try
        {
            image = Toolkit.getDefaultToolkit().getImage(s);
            if(image != null)
            {
                MediaTracker mediatracker = new MediaTracker(dialog);
                mediatracker.addImage(image, 0);
                mediatracker.waitForID(0);
            }
        }
        catch(Exception _ex) { }
        return image;
    }

    public static void main(String args[])
    {
        String args1[] = new String[2];
        args1[0] = "Ok";
        args1[1] = "Cancel";
        int i = showMessageDialog("Information", "I am going to restart your computer.\n ", 0, args1, 10, "Count down");
        ZCast.displayDebug("return value was: " + i);
        System.exit(0);
    }

    public static boolean NoShowState()
    {
        return NoShowState;
    }

    public void run()
    {
        try
        {
            dialog.repaint();
            while(DisplaySecs > 0) 
            {
                Thread.sleep(1000L);
                DisplaySecs--;
                if(CountDownLabel != null)
                    CountDownLabel.setText(String.valueOf(DisplaySecs));
                ZCast.displayDebug("DisplaySecs = " + DisplaySecs);
            }
            if(dialog != null)
            {
                dialog.dispose();
                dialog = null;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("run exception is " + exception);
        }
    }

    private static void setSelectedValue(int i)
    {
        selectedValue = i;
    }

    public static int showMessageDialog(String s, String s1, int i, String as[])
    {
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        dialog = createDialog(s, s1, i);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        if(dialog != null)
            dialog.repaint();
        int j = getSelectedValue();
        return j;
    }

    public static int showMessageDialog(String s, String s1, int i, String as[], int j)
    {
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        if(dialog == null)
            dialog = createDialog(s, s1, i);
        DisplaySecs = j;
        NZDialogBox nzdialogbox = new NZDialogBox();
        Thread thread = new Thread(nzdialogbox);
        ZCast.displayDebug("starting the thread here, no label");
        thread.start();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        thread.stop();
        int k = getSelectedValue();
        return k;
    }

    public static int showMessageDialog(String s, String s1, int i, String as[], int j, Rectangle rectangle)
    {
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        if(dialog == null)
            dialog = createDialog(s, s1, i);
        DisplaySecs = j;
        NZDialogBox nzdialogbox = new NZDialogBox();
        Thread thread = new Thread(nzdialogbox);
        ZCast.displayDebug("starting the thread here, no label");
        thread.start();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int k;
        int l;
        if(rectangle == null)
        {
            l = dimension.height / 2 - dialog.getSize().height / 2;
            k = dimension.width / 2 - dialog.getSize().width / 2;
        } else
        {
            if(rectangle.y >= dimension.height - (rectangle.y + rectangle.height))
                l = rectangle.y - dialog.getSize().height - 20;
            else
                l = rectangle.y + rectangle.height + 20;
            k = (rectangle.x + rectangle.width / 2) - dialog.getSize().width / 2;
        }
        dialog.setLocation(k, l);
        dialog.show();
        if(dialog != null)
            dialog.repaint();
        thread.stop();
        int i1 = getSelectedValue();
        return i1;
    }

    public static int showMessageDialog(String s, String s1, int i, String as[], int j, String s2)
    {
        ButtonStrings = as;
        DisplayString = s2;
        DisplayLabel = new NZLabel(s2);
        CountDownLabel = new NZLabel(String.valueOf(j));
        CountDownLabel.setForeground(Color.red);
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        if(dialog == null)
            dialog = createDialog(s, s1, i);
        DisplaySecs = j;
        NZDialogBox nzdialogbox = new NZDialogBox();
        Thread thread = new Thread(nzdialogbox);
        ZCast.displayDebug("***** Starting the thread!");
        thread.start();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        if(dialog != null)
            dialog.repaint();
        DisplayString = null;
        DisplayLabel = null;
        CountDownLabel = null;
        thread.stop();
        int k = getSelectedValue();
        return k;
    }

    public static int showMessageDialogNoShow(String s, String s1, int i, String as[], boolean flag)
    {
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        NoShowState = false;
        dialog = createDialogNoShow(s, s1, i, flag);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        if(dialog != null)
            dialog.repaint();
        int j = getSelectedValue();
        return j;
    }

    public static int showMessageDialogNoShow(String s, String s1, int i, String s2, String as[], boolean flag)
    {
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        UrlString = s2;
        NoShowState = false;
        dialog = createDialogNoShow(s, s1, i, flag);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        if(dialog != null)
            dialog.repaint();
        int j = getSelectedValue();
        return j;
    }

    public static boolean visitUrl()
    {
        return getSelectedValue() == 100;
    }

    public NZDialogBox()
    {
    }

    public static final int INFORMATION_MESSAGE = 0;
    public static final int QUESTION_MESSAGE = 1;
    public static final int ERROR_MESSAGE = 2;
    public static final int WARNING_MESSAGE = 3;
    public static final int VISIT_URL = 100;
    private static int selectedValue = -1;
    private static Dialog dialog = null;
    private static String ButtonStrings[] = null;
    private static String UrlString = null;
    private static int DisplaySecs = 15;
    private static String DisplayString = null;
    private static NZLabel DisplayLabel = null;
    private static NZLabel CountDownLabel = null;
    private static int handle = -1;
    private static boolean NoShowState = false;
    private static Checkbox NoShowCB = null;
    private static NZButton b[] = null;









}
