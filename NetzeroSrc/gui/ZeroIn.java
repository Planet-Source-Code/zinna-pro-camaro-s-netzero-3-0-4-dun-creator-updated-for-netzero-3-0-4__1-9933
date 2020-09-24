// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZeroIn.java

package gui;

import admgmt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import nzcom.Initializer;
import nzcom.ZCast;
import pool.*;
import util.Timer1;
import util.UiUtils;
import video.TviPlayer;

// Referenced classes of package gui:
//            NZButton, Startup, AgComponent, VolumeScale, 
//            NZDialogBox

public class ZeroIn extends Dialog
    implements Runnable, PlayListConsumer, PlayAdListener
{

    public ZeroIn(Frame frame, String s, boolean flag, Startup startup)
    {
        super(frame, s, flag);
        stup = null;
        playList_ = null;
        displayThread_ = null;
        lock_ = new Object();
        keepOnScreenTimer_ = new Timer1("Zero In Screen Guard") {

            public void timesUp()
            {
                synchronized(lock_)
                {
                    UiUtils.keepWindowFromGoingOffScreen(ZeroIn.this);
                }
            }

        };
        currentAd = null;
        ad_Clicked = null;
        cancelClicked_ = false;
        hasConnection_ = false;
        countAds = 0;
        save_time = 0L;
        zeroInAd_Clicked = false;
        labelStatus = null;
        nZTVCancel = null;
        volscale = null;
        needsEmergencyExit = false;
        displayScreen_ = new Canvas() {

            public void paint(Graphics g)
            {
                if(currentAd != null)
                    currentAd.renderAd(this);
            }

            public void update(Graphics g)
            {
                paint(g);
            }

        };
        stup = startup;
        initialize(s);
        me = this;
    }

    private void adClicked(long l)
    {
        save_time = l;
        ad_Clicked = currentAd;
        zeroInAd_Clicked = true;
        if(ad_Clicked != null && !ad_Clicked.getAdId().equals(ZeroInPool.getInstance().getDefaultAd().getAdIdString()) && !ad_Clicked.getAdId().equals(ZeroInPool.getInstance().getBumper().getAdIdString()))
        {
            Initializer.setZeroInAd(ad_Clicked, l);
            if(okToShowDialog)
            {
                okToShowDialog = false;
                NZDialogBox.showMessageDialog(resNZResource.getString("Information"), resNZResource.getString("Once_the_connection_has_been_made"), 0, new String[] {
                    "Ok"
                }, 5, getBounds());
                okToShowDialog = true;
            }
        }
    }

    public void adDisplayControlDisclosure(Object obj)
    {
        if((obj instanceof PlayAdVolumeControl) && volscale != null)
        {
            volscale.setVolumeControl((PlayAdVolumeControl)obj);
            volscale.repaint();
            repaint();
        }
    }

    public void adDisplayOccured()
    {
    }

    private void cleanupOnScreenTimerThread()
    {
        ZCast.displayDebug("ZeroIn.cleanupOnScreenTimerThread()");
        synchronized(lock_)
        {
            if(keepOnScreenTimer_ != null)
            {
                keepOnScreenTimer_.stop();
                keepOnScreenTimer_ = null;
            }
        }
    }

    private synchronized void cleanupThread()
    {
        cleanupOnScreenTimerThread();
        ZCast.displayDebug("ZeroIn.cleanupThread()");
        if(displayThread_ != null)
        {
            displayThread_.stop();
            displayThread_ = null;
        }
    }

    public void connectNotification()
    {
        hasConnection_ = true;
        try
        {
            if(needsEmergencyExit)
            {
                doFinish();
                ZCast.displayDebug("**************************************");
                ZCast.displayDebug("        In Emergency Exit ()");
                ZCast.displayDebug("**************************************");
                setVisible(false);
                dispose();
                cleanupThread();
                me = null;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            setVisible(false);
        }
    }

    private AgComponent createNZTVBackground()
    {
        AgComponent agcomponent = new AgComponent();
        agcomponent.initialize(null, "images/nztv_bg.gif");
        agcomponent.setLocation(3, 22);
        return agcomponent;
    }

    private void doCancel()
    {
        ZCast.displayDebug("ZeroIn.doCancel()");
        if(currentAd != null)
        {
            currentAd.stopRenderAd();
            currentAd.cleanUp();
            currentAd = null;
        }
        stup.zeroInCancelNotification();
    }

    public synchronized void doCancelClicked()
    {
        if(!cancelClicked_ && displayThread_ != null)
        {
            cancelClicked_ = true;
            displayThread_.interrupt();
        }
    }

    private void doFinish()
    {
        ZCast.displayDebug("ZeroIn.doFinish()");
        if(currentAd != null)
        {
            currentAd.cleanUp();
            currentAd = null;
        }
        stup.zeroInFinishNotification();
    }

    private NZButton getButtonNZTVCancel()
    {
        if(nZTVCancel == null)
            try
            {
                nZTVCancel = new NZButton("cancelnztv");
                nZTVCancel.setBounds(149, 282, 57, 19);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return nZTVCancel;
    }

    private Label getLabelStatus()
    {
        if(labelStatus == null)
            try
            {
                labelStatus = new Label();
                labelStatus.setName("Label2");
                labelStatus.setFont(new Font("arial", 0, 10));
                labelStatus.setAlignment(1);
                labelStatus.setText("Connecting to NetZero...");
                labelStatus.setBackground(Color.black);
                labelStatus.setForeground(Color.white);
                labelStatus.setBounds(29, 249, 293, 16);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return labelStatus;
    }

    private VolumeScale getVolumeScale()
    {
        volscale = new VolumeScale();
        volscale.initialize("images/volume.gif");
        volscale.addMouseListener(volscale);
        volscale.addMouseMotionListener(volscale);
        volscale.setLocation(324, 36);
        return volscale;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initialize(String s)
    {
        try
        {
            setName(s);
            setLayout(null);
            setSize(350, 315);
            setResizable(false);
            displayScreen_.setBounds(63, 47, 224, 152);
            displayScreen_.setBackground(Color.black);
            add(displayScreen_);
            add(getLabelStatus(), getLabelStatus().getName());
            add(getButtonNZTVCancel(), getButtonNZTVCancel().getName());
            add(getVolumeScale());
            add(createNZTVBackground());
            displayScreen_.setSize(new Dimension(224, 167));
            displayScreen_.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent mouseevent)
                {
                    adClicked(mouseevent.getWhen());
                }

            });
            getButtonNZTVCancel().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    doCancelClicked();
                }

            });
            addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent windowevent)
                {
                    doCancelClicked();
                }

            });
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            needsEmergencyExit = true;
        }
    }

    public void newPlayListNotification(boolean flag)
    {
    }

    public void run()
    {
        try
        {
            ZCast.displayDebug("**************************************");
            ZCast.displayDebug("       Starting Zero In run()");
            ZCast.displayDebug("**************************************");
            playList_ = ZeroInPlayList.getZeroInPlayList();
            playList_.addConsumer(this);
            playList_.setDrivingConsumer(this);
            boolean flag = true;
            for(boolean flag2 = false; !flag2;)
            {
                if(cancelClicked_)
                {
                    doCancel();
                    flag2 = true;
                }
                if(currentAd != null)
                    currentAd.cleanUp();
                currentAd = playList_.getNextPlayAd(this);
                if(currentAd == null)
                    break;
                currentAd.addPlayAdListener(this);
                boolean flag1;
                if(currentAd.prepareForDisplay(displayScreen_))
                {
                    repaint();
                    displayScreen_.repaint();
                    flag1 = true;
                } else
                {
                    ZCast.displayDebug("current Ad does not display correctly");
                    flag1 = false;
                }
                if(currentAd instanceof VideoAd)
                {
                    String s = TviPlayer.getInstance().getVideoFileCaption();
                    setTitle(s);
                } else
                {
                    setTitle(DEFAULT_TITLE);
                }
                ZCast.displayDebug("ZEROIN! currentAd: " + currentAd + ",  Ad Count: " + countAds);
                if(flag1 && countAds < 2)
                {
                    Thread.sleep(currentAd.getAdDisplayTime() * 1000);
                    waitForVideo();
                } else
                {
                    if(!flag1)
                        Thread.sleep(50L);
                    if(countAds >= 2)
                        waitForConnection();
                }
                if(cancelClicked_)
                {
                    doCancel();
                    flag2 = true;
                } else
                if(videoIsDone() && hasConnection_)
                {
                    doFinish();
                    flag2 = true;
                }
                countAds++;
            }

        }
        catch(Exception exception1)
        {
            ZCast.displayDebug(exception1);
            doCancel();
        }
        finally
        {
            ZCast.displayDebug("**************************************");
            ZCast.displayDebug("        Leaving Zero In run()");
            ZCast.displayDebug("**************************************");
            setVisible(false);
            dispose();
            cleanupThread();
            me = null;
        }
    }

    public void setStatusText(String s)
    {
        labelStatus.setText(s);
    }

    public void start()
    {
        try
        {
            if(displayThread_ == null)
            {
                displayThread_ = new Thread(this, "ZeroIn");
                displayThread_.start();
            }
            if(keepOnScreenTimer_ != null)
                keepOnScreenTimer_.start(1);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
            needsEmergencyExit = true;
        }
    }

    public boolean videoIsDone()
    {
        return countAds > 1;
    }

    public boolean waitForConnection()
    {
        int i = 0;
        ZCast.displayDebug("\n********** in waitForConnection() **** **");
        while(!hasConnection_ && !cancelClicked_) 
            try
            {
                Thread.sleep(500L);
                if(++i <= 600)
                    continue;
                ZCast.displayDebug("\n********************************");
                ZCast.displayDebug(" time out waiting for connection!!");
                ZCast.displayDebug("\n********************************");
                break;
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
        return true;
    }

    public boolean waitForVideo()
    {
        int i = 0;
        if((currentAd instanceof VideoAd) && ((VideoAd)currentAd).isPlaying())
            while(((VideoAd)currentAd).isPlaying() && !cancelClicked_) 
                try
                {
                    Thread.sleep(500L);
                    if(++i > 90)
                        break;
                }
                catch(Exception _ex) { }
        return true;
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private Startup stup;
    private ZeroInPlayList playList_;
    private Thread displayThread_;
    private Object lock_;
    private Timer1 keepOnScreenTimer_;
    private PlayAd currentAd;
    private PlayAd ad_Clicked;
    private boolean cancelClicked_;
    private boolean hasConnection_;
    private int countAds;
    private long save_time;
    private boolean zeroInAd_Clicked;
    private Label labelStatus;
    private NZButton nZTVCancel;
    private VolumeScale volscale;
    private static String DEFAULT_TITLE = "NZTV";
    private static boolean okToShowDialog = true;
    private static ZeroIn me = null;
    private boolean needsEmergencyExit;
    private Canvas displayScreen_;




}
