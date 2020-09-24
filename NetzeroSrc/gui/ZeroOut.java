// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroOut.java

package gui;

import admgmt.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.Date;
import java.util.ResourceBundle;
import jclass.bwt.*;
import jclass.util.JCString;
import nzcom.OSDetectNative;
import nzcom.ZCast;
import util.Timer1;

// Referenced classes of package gui:
//            NZButton

public class ZeroOut extends Dialog
    implements PlayListConsumer
{

    private static ZeroOut getZeroOutDialog(Frame frame, boolean flag)
    {
        if(TheZeroOut == null)
            TheZeroOut = new ZeroOut(frame, flag);
        if(!firstTime)
            TheZeroOut.setupNextAd();
        return TheZeroOut;
    }

    public static void popup(Frame frame)
    {
        if(TheZeroOut != null && TheZeroOut.isVisible())
        {
            Toolkit.getDefaultToolkit().beep();
            TheZeroOut.toFront();
            return;
        } else
        {
            ZeroOut zeroout = getZeroOutDialog(frame, false);
            ZCast.centerComponent(zeroout);
            zeroout.setVisible(true);
            return;
        }
    }

    private static void debugPrint(String s)
    {
        ZCast.displayDebug(s);
    }

    private void prepareSize()
    {
        imageComp_.setSize(224, 152);
        doLayout0();
        invalidate();
    }

    protected ZeroOut(Frame frame, boolean flag)
    {
        super(frame, flag);
        sleepTime_ = 0;
        viewAdMouseListener_ = new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseevent)
            {
                doViewAd();
            }

        };
        viewAdKeyListener = new KeyAdapter() {

            public void keyPressed(KeyEvent keyevent)
            {
                int i = keyevent.getKeyCode();
                if(i == 10)
                    doViewAd();
                else
                if(i == 27)
                    doCancel();
            }

        };
        timeElapse_ = false;
        dismiss_ = false;
        curImage_ = null;
        imageComp_ = new Canvas() {

            public void paint(Graphics g)
            {
                if(playAd_ != null)
                    playAd_.renderAd(imageComp_);
            }

        };
        adViewButton_ = null;
        exitButton_ = null;
        cancelButton_ = null;
        adImageLabel_ = null;
        adGroupBox_ = null;
        buttonGroupBox_ = null;
        initialize();
    }

    private synchronized void removeTimer(boolean flag)
    {
        if(timer_ != null)
        {
            if(flag)
                timer_.interrupt();
            timer_ = null;
        }
    }

    private void cleanUp(boolean flag)
    {
        removeTimer(true);
        if(!ZCast.m_demoMode)
            zopl_.sendStats();
        if(playAd_ != null)
        {
            playAd_.cleanUp();
            playAd_ = null;
        }
        if(flag)
        {
            dispose();
            TheZeroOut = null;
        } else
        {
            setVisible(false);
        }
    }

    private void doViewAd()
    {
        try
        {
            debugPrint("\tdoViewAd()");
            setTimeElapse(true);
            if(playAd_ != null)
                playAd_.clickThru((new Date()).getTime());
            doCancel();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void doExit()
    {
        try
        {
            debugPrint("\tdoExit()");
            setTimeElapse(true);
            setDismissStatus(true);
            cleanUp(true);
            ZCast.terminateProgram(0, null);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void doCancel()
    {
        try
        {
            debugPrint("\tdoCancel()");
            setTimeElapse(true);
            setDismissStatus(false);
            cleanUp(false);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public boolean getDismissStatus()
    {
        return dismiss_;
    }

    private void setDismissStatus(boolean flag)
    {
        dismiss_ = flag;
    }

    private NZButton createButton(String s, String s1)
    {
        try
        {
            NZButton nzbutton = new NZButton(s);
            nzbutton.setName(s1);
            nzbutton.setFont(new Font("dialog", 0, 12));
            nzbutton.setBackground(SystemColor.control);
            nzbutton.setVisible(true);
            return nzbutton;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
        return null;
    }

    private JCGroupBox createGroupBox(String s, String s1)
    {
        try
        {
            JCGroupBox jcgroupbox = new JCGroupBox();
            jcgroupbox.setName(s);
            jcgroupbox.setFont(new Font("dialog", 0, 12));
            jcgroupbox.setBackground(SystemColor.control);
            jcgroupbox.setShadowThickness(GROUP_BOX_SHADOW_THICKNESS);
            if(s1 != null)
            {
                JCString jcstring = JCString.parse(this, s1);
                jcgroupbox.setTitle(jcstring, 0);
            }
            jcgroupbox.setVisible(true);
            return jcgroupbox;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
        return null;
    }

    private JCLabel getAdLabel()
    {
        if(adImageLabel_ == null)
            try
            {
                adImageLabel_ = new JCLabel();
                adImageLabel_.setName("adImageLabel");
                adImageLabel_.setFont(new Font("dialog", 0, 12));
                adImageLabel_.setBackground(SystemColor.control);
                adImageLabel_.setLabel(curImage_);
                adImageLabel_.addMouseListener(viewAdMouseListener_);
                adImageLabel_.addKeyListener(viewAdKeyListener);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return adImageLabel_;
    }

    private boolean getTimeElapse()
    {
        return timeElapse_;
    }

    private void setTimeElapse(boolean flag)
    {
        timeElapse_ = flag;
    }

    private void handleException(Throwable throwable)
    {
    }

    private int computeButtonBoxMinWidth()
    {
        int i = adViewButton_.getPreferredSize().width;
        int j = exitButton_.getPreferredSize().width;
        int k = cancelButton_.getPreferredSize().width;
        return i + j + k + 2 * GROUP_BOX_H_MARGIN + GROUP_BOX_SHADOW_THICKNESS + 2 * GAP;
    }

    private int computeMinBoxWidth(int i)
    {
        int j = computeButtonBoxMinWidth();
        int k = i + 2 * GROUP_BOX_H_MARGIN + GROUP_BOX_SHADOW_THICKNESS;
        return j >= k ? j : k;
    }

    private void doLayout0()
    {
        int i = 0;
        int j = 0;
        Dimension dimension = imageComp_.getSize();
        debugPrint("In doLayout0(), image component bounds: " + imageComp_.getBounds());
        i = dimension.width;
        j = dimension.height;
        int k = computeMinBoxWidth(i);
        int l = WINDOW_MARGIN + GROUP_BOX_SHADOW_THICKNESS;
        int i1 = WINDOW_MARGIN + GROUP_BOX_SHADOW_THICKNESS + BOX_LABEL_EXTRA;
        int j1 = j + 2 * GROUP_BOX_V_MARGIN;
        adGroupBox_.setBounds(l, i1, k, j1);
        adGroupBox_.setLayout(new FlowLayout());
        int k1 = l;
        int l1 = i1 + j1 + GAP;
        int i2 = k;
        int j2 = adViewButton_.getPreferredSize().height + 2 * GROUP_BOX_V_MARGIN;
        buttonGroupBox_.setBounds(k1, l1, k, j2);
        buttonGroupBox_.setLayout(null);
        int k2 = GROUP_BOX_V_MARGIN;
        Dimension dimension1 = adViewButton_.getPreferredSize();
        adViewButton_.setBounds(GROUP_BOX_H_MARGIN, k2, dimension1.width, dimension1.height);
        dimension1 = cancelButton_.getPreferredSize();
        int l2 = k - (GROUP_BOX_H_MARGIN + GROUP_BOX_SHADOW_THICKNESS + dimension1.width);
        cancelButton_.setBounds(l2, k2, dimension1.width, dimension1.height);
        dimension1 = exitButton_.getPreferredSize();
        int i3 = l2 - (GAP + dimension1.width);
        exitButton_.setBounds(i3, k2, dimension1.width, dimension1.height);
        int j3 = k + 2 * WINDOW_MARGIN + GROUP_BOX_SHADOW_THICKNESS;
        int k3 = l1 + j2 + WINDOW_MARGIN;
        Dimension dimension2 = Toolkit.getDefaultToolkit().getScreenSize();
        if(j3 > dimension2.width)
            j3 = dimension2.width;
        if(k3 > dimension2.height)
            k3 = dimension2.height;
        setSize(j3, k3);
    }

    private void setupNextAd()
    {
        zopl_ = ZeroOutPlayList.getZeroOutPlayList();
        zopl_.addConsumer(this);
        playAd_ = zopl_.getNextPlayAd(this);
        if(playAd_ != null)
        {
            debugPrint("Zero Out Play List returns AD: " + playAd_);
            prepareSize();
            playAd_.prepareForDisplay(imageComp_);
        }
        sleepTime_ = playAd_ == null ? DEFAULT_DISPLAY_TIME : playAd_.getAdDisplayTime();
        timer_ = new Timer1("ZeroOut Timer") {

            public void start(int i)
            {
                if(imageComp_ != null)
                    imageComp_.repaint();
                super.start(i);
            }

            public void timesUp()
            {
                ZeroOut.debugPrint("timesUp()");
                if(!getTimeElapse())
                {
                    ZeroOut.debugPrint("\tbefore doCancel()");
                    doCancel();
                }
                removeTimer(true);
            }

        };
        setTimeElapse(false);
    }

    private void initialize()
    {
        setName(resNZResource.getString("NetZero_Logoff_Dialog"));
        setResizable(false);
        setLayout(null);
        setTitle(resNZResource.getString("NetZero_Logoff_Dialog"));
        setBackground(SystemColor.control);
        setTimeElapse(false);
        imageComp_.addMouseListener(viewAdMouseListener_);
        imageComp_.addKeyListener(viewAdKeyListener);
        adGroupBox_ = createGroupBox("AdGroupBox", "[IMG=images/session.gif]");
        add(adGroupBox_, adGroupBox_.getName());
        adGroupBox_.add(imageComp_, imageComp_.getName());
        adViewButton_ = createButton("visit_sponsor", "adViewButton");
        adViewButton_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                doViewAd();
            }

        });
        exitButton_ = createButton("log_off", "exitButton");
        exitButton_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                doExit();
            }

        });
        cancelButton_ = createButton("cancel", "cancelButton");
        cancelButton_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                doCancel();
            }

        });
        buttonGroupBox_ = createGroupBox("buttonGroupBox", null);
        add(buttonGroupBox_, buttonGroupBox_.getName());
        buttonGroupBox_.add(adViewButton_, adViewButton_.getName());
        buttonGroupBox_.add(exitButton_, exitButton_.getName());
        buttonGroupBox_.add(cancelButton_, cancelButton_.getName());
        prepareSize();
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                doCancel();
            }

            public void windowOpened(WindowEvent windowevent)
            {
                OSDetectNative.updateProcessIcons();
                if(ZeroOut.firstTime)
                {
                    setupNextAd();
                    if(timer_ != null)
                    {
                        ZeroOut.debugPrint("\t====> starting timer, sleep time: " + sleepTime_);
                        timer_.start(sleepTime_);
                    }
                    ZeroOut.firstTime = false;
                }
            }

            public void windowActivated(WindowEvent windowevent)
            {
            }

        });
    }

    public void setVisible(boolean flag)
    {
        debugPrint("**************<setVisible>********************\n\n\n\n");
        debugPrint("in setVisible(), visible " + flag);
        if(flag && timer_ != null)
        {
            debugPrint("\t====> starting timer, sleep time: " + sleepTime_);
            timer_.start(sleepTime_);
        }
        debugPrint("\n\n\n\n***************************************************");
        super.setVisible(flag);
    }

    public String toString()
    {
        return "ZeroOut Dialog";
    }

    public void newPlayListNotification(boolean flag)
    {
    }

    public static void main(String args[])
    {
        try
        {
            ZeroOut zeroout = new ZeroOut(new Frame(), true);
            ZCast.displayDebug("Starting gui.ZeroOut unit test");
            zeroout.setVisible(true);
            if(zeroout.getDismissStatus())
                ZCast.displayDebug("\tUser clicked Log-Off button");
            else
                ZCast.displayDebug("\tUser didn't click Log-Off button");
            ZCast.displayDebug("End gui.ZeroOut unit test");
            System.exit(0);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private static final int ZEROOUT_DISPLAY_WIDTH = 224;
    private static final int ZEROOUT_DISPLAY_HEIGHT = 152;
    private static int DEFAULT_DISPLAY_TIME = 30;
    private static int GAP = 10;
    private static int GROUP_BOX_H_MARGIN = 10;
    private static int GROUP_BOX_V_MARGIN = 20;
    private static int GROUP_BOX_SHADOW_THICKNESS = 4;
    private static int WINDOW_MARGIN = 10;
    private static int BOX_LABEL_EXTRA = 15;
    private Timer1 timer_;
    private int sleepTime_;
    protected MouseAdapter viewAdMouseListener_;
    protected KeyAdapter viewAdKeyListener;
    private PlayAd playAd_;
    private ZeroOutPlayList zopl_;
    private boolean timeElapse_;
    private boolean dismiss_;
    private Image curImage_;
    private Canvas imageComp_;
    private NZButton adViewButton_;
    private NZButton exitButton_;
    private NZButton cancelButton_;
    private JCLabel adImageLabel_;
    private JCGroupBox adGroupBox_;
    private JCGroupBox buttonGroupBox_;
    private static boolean firstTime = true;
    private static ZeroOut TheZeroOut = null;














}
