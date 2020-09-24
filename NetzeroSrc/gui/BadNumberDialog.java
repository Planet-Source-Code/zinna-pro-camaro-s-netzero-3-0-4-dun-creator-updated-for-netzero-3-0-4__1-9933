// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BadNumberDialog.java

package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButton, NZLabel, BulletinLayout, NZColor, 
//            NZFont, AgComponent

public class BadNumberDialog
{

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

    private static Dialog createDialog(String s, String s1, String s2, int i)
    {
        thisparent = new Frame(s);
        Dialog dialog1 = new Dialog(thisparent, s, true);
        dialog1.setFont(new NZFont());
        dialog1.setBackground(NZColor.BACKGROUND);
        dialog1.setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.setLayout(new BulletinLayout());
        dialog1.add("Center", panel);
        Panel panel1 = getBottom(ButtonStrings);
        panel.add(panel1);
        dialog1.setResizable(false);
        dialog1.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                BadNumberDialog.setSelectedValue(-1);
                BadNumberDialog.dialog.dispose();
                BadNumberDialog.dialog = null;
            }

            public void windowOpened(WindowEvent windowevent)
            {
                OSDetectNative.updateProcessIcons();
            }

        });
        AgComponent agcomponent = addPic(panel, i);
        StringTokenizer stringtokenizer = new StringTokenizer(s1, "\n");
        int j = stringtokenizer.countTokens();
        NZLabel nzlabel = new NZLabel(s1);
        FontMetrics fontmetrics = nzlabel.getFontMetrics(new NZFont());
        int k = getMaxWidth(fontmetrics, s1);
        int l = fontmetrics.getHeight() + 2;
        nzlabel.setSize(k + 10, l * j);
        nzlabel.setLocation(40, 5);
        panel.add(nzlabel);
        cb = new Checkbox("Don't warn me about these numbers again");
        cb.setLocation(45, l * j + 25);
        panel.add(cb);
        cb.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent itemevent)
            {
                BadNumberDialog.ignore = BadNumberDialog.cb.getState();
            }

        });
        NZLabel nzlabel1 = new NZLabel(s2);
        StringTokenizer stringtokenizer1 = new StringTokenizer(s2, "\n");
        panel.add(nzlabel1);
        nzlabel1.setLocation(40, l * j + 55);
        j += stringtokenizer1.countTokens();
        int i1;
        int j1;
        if(agcomponent != null)
        {
            i1 = k + agcomponent.fetchSize().width + 30;
            j1 = j * l + 110;
        } else
        {
            i1 = k + 30;
            j1 = j * l + 90;
        }
        dialog1.setSize(i1, j1);
        thisparent.setSize(i1, j1);
        panel1.setLocation(i1 / 2 - panel1.getSize().width / 2, l * j + 60);
        return dialog1;
    }

    private static Panel getBottom(String as[])
    {
        Panel panel = new Panel(new FlowLayout());
        for(int i = 0; i < as.length; i++)
        {
            NZButton nzbutton = new NZButton(as[i]);
            nzbutton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    int j = BadNumberDialog.getButtonIndex(actionevent);
                    BadNumberDialog.setSelectedValue(j);
                    BadNumberDialog.dialog.dispose();
                    BadNumberDialog.dialog = null;
                }

            });
            panel.add(nzbutton);
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
        return i;
    }

    private static int getSelectedValue()
    {
        return selectedValue;
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
        String s = new String();
        s = s + "(Thousand Oaks, CA) 418-2000 should be 1-805-418-2000";
        s = s + "\n(Thousand Oaks, CA) 418-2020 should be 1-805-418-2020";
        boolean flag = showMessageDialog(s);
        ZCast.displayDebug("return value was: " + flag);
        ZCast.displayDebug("the checkbox value was " + ignore);
        System.exit(0);
    }

    private static void setSelectedValue(int i)
    {
        selectedValue = i;
    }

    public static final boolean showMessageDialog(String s)
    {
        ignore = false;
        String s1 = "Warning: Possible Dialing Errors";
        String as[] = {
            "yes", "no"
        };
        String s2 = "According to information you've provided, you are dialing from\narea code 818.  The following phone numbers in your phone list\nare in a different area code, but are not set up to dial '1' followed\nby the area code and number.\n\n" + s + "\n\nThis MIGHT cause potential problems such as dialing wrong\nnumbers when attempting to connect to NetZero.  You can\nchange the settings yourself or we can change them for you.";
        String s3 = "Do you want us to change these numbers to dial '1' followed\nby the area code and number when connecting to NetZero?";
        ButtonStrings = as;
        if(dialog != null)
        {
            dialog.dispose();
            dialog = null;
        }
        dialog = createDialog(s1, s2, s3, 3);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dimension.width / 2 - dialog.getSize().width / 2, dimension.height / 2 - dialog.getSize().height / 2);
        dialog.show();
        int i = getSelectedValue();
        return i == 0;
    }

    public BadNumberDialog()
    {
    }

    public static final int INFORMATION_MESSAGE = 0;
    public static final int QUESTION_MESSAGE = 1;
    public static final int ERROR_MESSAGE = 2;
    public static final int WARNING_MESSAGE = 3;
    private static int selectedValue = -1;
    private static Dialog dialog = null;
    private static String ButtonStrings[] = null;
    private static Frame thisparent = null;
    private static Checkbox cb = null;
    public static boolean ignore = false;






}
