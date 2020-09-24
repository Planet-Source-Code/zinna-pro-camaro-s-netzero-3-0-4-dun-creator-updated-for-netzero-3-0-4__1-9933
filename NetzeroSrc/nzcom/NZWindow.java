// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZWindow.java

package nzcom;

import admgmt.AdDisplayer;
import admgmt.SequentialPlayList;
import browse.BrowseDialog;
import browse.BrowseHttpRequest;
import exception.NZException;
import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import jclass.bwt.JCLabel;
import jclass.util.JCString;
import mail.*;
import serviceThread.ServiceThread;
import tcpBinary.*;
import ticker.FetchRequestImpl;
import ticker.client.*;
import ticker.miscellaneous.TickerSwd;
import ui.*;
import zpc.*;

// Referenced classes of package nzcom:
//            AboutDialog, Profile, SoftwareExit, ZCast, 
//            ServerParms, FeedbackForm, ConfigParams, Initializer, 
//            OSDetectNative, NZUserLog, RASWinNative, MonitorThread

public class NZWindow extends Window
    implements FocusListener, MouseListener, MouseMotionListener, ItemListener, ActionListener, WindowListener, SoftwareExit
{

    public NZWindow(Frame frame)
    {
        super(frame);
        alwaysTrue = true;
        useBookmarks = false;
        transMessages = false;
        frameWidth = 3;
        titleHeight = 20;
        onTopDelay = 1000;
        sizeMode = -1;
        hotSpot = new Rectangle(0, 0, 25, 25);
        rejectFocus = false;
        skipNextPressed = false;
        showDrag = true;
        showTitle = true;
        showButtons = true;
        suppressOnTop = false;
        titleHoverImage = null;
        lookSmartStart = null;
        lookSmartSearch = null;
        nzLog = null;
        launchUrl = "http://shop.netzero.net";
        homePageUrl = "http://shop.netzero.net";
        shopUrl = "http://shop.netzero.net";
        searchUrl = "http://netzero.looksmart.com/search";
        compaqUrl = "http://desktop.presario.net/scripts/redirectors/presario/deskredir.dll?s=nzeroqb&c=2c99&lc=0409";
        supportUrl = "http://www.netzero.net/support";
        monSwitch = false;
        eventLogging = 1;
        curEventCt = 0;
        showCompaq = true;
        popupMenu1 = null;
        pmmView = null;
        pmmBrowser = null;
        pmviNewBrowser = null;
        pmmPrint = null;
        pmpiAd = null;
        pmpiCoupon = null;
        pmmBookmarks = null;
        pmiBack = null;
        pmiNext = null;
        pmmSupport = null;
        pmisNet = null;
        pmisFeed = null;
        pmiProfile = null;
        pmiNetzero = null;
        pmmHelp = null;
        pmhiAbout = null;
        pmhiTutorial = null;
        pmiExit = null;
        pmmPosition = null;
        pmpiTopLeft = null;
        pmpiTopRight = null;
        pmpiBottomLeft = null;
        pmpiBottomRight = null;
        pmmTicker = null;
        pmtiOnOff = null;
        pmtiCustomize = null;
        pmmEmail = null;
        pmemCheck = null;
        pmemLaunch = null;
        pmemConfig = null;
        pmemCOEAK = null;
        pmtiDockTop = null;
        pmtiDockBottom = null;
        pmtiStandard = null;
        pmtiIcons = null;
        ivjContentsPane = null;
        currentContainer = null;
        currentComponent = null;
        knowWidth = false;
        knowHeight = false;
        setNext = false;
        useNext = false;
        nImages = 0;
        maxImages = 100;
        sizeKnown = false;
        imagesLoaded = false;
        currentTopic = -1;
        hoverComponent = null;
        iCy = 0;
        cy0 = "right_side (down)";
        cy1 = "br (down)";
        uiState = 0;
        defState = null;
        tickerState = false;
        iconState = false;
        customizeActive = false;
        tickerSpeed = 0;
        fromUpdate = false;
        mailDlg = null;
        hasPressed = false;
        subMenu = null;
        currentOrientation = -1;
        currentTickerSpeed = 0;
        isDocked = false;
        searchTextField = null;
        searchButton = null;
        searchPanel = null;
        enterSearch = null;
        lsLogo = null;
        parentFrame = frame;
        setNzUserid(MemberRecs.getCurrentMemberID());
        ZCast.register(this);
        table = new Hashtable();
        add(createSearchPanel());
        me = this;
    }

    public Hashtable getComponentTable()
    {
        return table;
    }

    public Object getLockForFloat()
    {
        return lockForFloat;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        isPopupShowing = false;
        ZCast.displayDebug("======================MENU ITEM CLICKED **********************************");
        if(actionevent.getSource() == pmiExit)
            conn100(actionevent);
        if(actionevent.getSource() == pmiProfile)
            conn102(actionevent);
        if(actionevent.getSource() == pmiBack)
            conn50(actionevent);
        if(actionevent.getSource() == pmiNext)
            conn60(actionevent);
        if(actionevent.getSource() == pmisNet)
            showAdUrl(supportUrl);
        if(actionevent.getSource() == pmisFeed)
            conn105(actionevent);
        if(actionevent.getSource() == pmtiCustomize)
        {
            showTickerCustomization();
            return;
        }
        if(actionevent.getSource() == pmemCheck)
        {
            checkMail();
            return;
        }
        if(actionevent.getSource() == pmemLaunch)
        {
            showMail();
            return;
        }
        if(actionevent.getSource() == pmemConfig)
        {
            setupEmailAccount();
            return;
        }
        if(actionevent.getSource() == pmemCOEAK)
        {
            showChangeOfEmailAddressKit();
            return;
        }
        if(actionevent.getSource() == pmtiIcons)
            undockWindow(tickerState ? "down-icon" : "up-icon");
        if(actionevent.getSource() == pmtiStandard)
            undockWindow(tickerState ? "down" : "up");
        if(actionevent.getSource() == pmtiDockBottom)
            dockWindow(1);
        if(actionevent.getSource() == pmtiDockTop)
            dockWindow(0);
        if(actionevent.getSource() == pmhiAbout)
        {
            showAbout();
            return;
        }
        if(actionevent.getSource() == pmhiTutorial)
        {
            if(ZCast.m_demoMode)
            {
                showAdUrl("file:demo/nzhelp/index.htm");
            } else
            {
                String s = ServerParms.getParm("tutorial", "http://www.netzero.net/support/the_zeroport.html").trim();
                showAdUrl(s);
            }
            return;
        }
        if(actionevent.getSource() == pmiNetzero)
        {
            if(ZCast.m_demoMode)
            {
                showAdUrl("file:demo/netzero/index.htm");
            } else
            {
                String s1 = ServerParms.getParm("netzero_home", "http://www.netzero.net").trim();
                showAdUrl(s1);
            }
            return;
        }
        if(actionevent.getSource() == pmpiTopLeft)
        {
            moveWindow(0, 0);
            pmpiTopLeft.setEnabled(false);
            pmpiTopRight.setEnabled(true);
            pmpiBottomLeft.setEnabled(true);
            pmpiBottomRight.setEnabled(true);
        }
        if(actionevent.getSource() == pmpiTopRight)
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            moveWindow(dimension.width - mySize.width, 0);
            pmpiTopLeft.setEnabled(true);
            pmpiTopRight.setEnabled(false);
            pmpiBottomLeft.setEnabled(true);
            pmpiBottomRight.setEnabled(true);
        }
        if(actionevent.getSource() == pmpiBottomLeft)
        {
            Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
            moveWindow(0, dimension1.height - mySize.height);
            pmpiTopLeft.setEnabled(true);
            pmpiTopRight.setEnabled(true);
            pmpiBottomLeft.setEnabled(false);
            pmpiBottomRight.setEnabled(true);
        }
        if(actionevent.getSource() == pmpiBottomRight)
        {
            Dimension dimension2 = Toolkit.getDefaultToolkit().getScreenSize();
            moveWindow(dimension2.width - mySize.width, dimension2.height - mySize.height);
            pmpiTopLeft.setEnabled(true);
            pmpiTopRight.setEnabled(true);
            pmpiBottomLeft.setEnabled(true);
            pmpiBottomRight.setEnabled(false);
        }
        if(actionevent.getSource() instanceof MenuItem)
        {
            ZCast.displayDebug(" in MenuItem Action Performed");
            MenuItem menuitem = (MenuItem)actionevent.getSource();
            if(menuitem.getParent() == pmmBookmarks)
            {
                ZCast.displayDebug("Bookmark " + menuitem.getLabel());
                showAdUrl(menuitem.getLabel());
            }
        }
    }

    private void adjustToScreen(Point point)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        if(point.x < 0)
            point.x = 0;
        if(point.x + mySize.width > dimension.width)
            point.x = dimension.width - mySize.width;
        if(point.y < 0)
            point.y = 0;
        if(point.y + mySize.height > dimension.height)
            point.y = dimension.height - mySize.height;
    }

    private void centerDialog(Dialog dialog)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = dialog.getSize();
        Point point = new Point((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);
        if(point.x < 0)
            point.x = 0;
        if(point.y < 0)
            point.y = 0;
        dialog.setLocation(point.x, point.y);
    }

    public int checkMail()
    {
        Thread thread = new Thread() {

            public void run()
            {
                if(mClient == null)
                    mClient = new MailClient(nzUserid, nzPassword, NZWindow.me);
                mClient.activate();
            }

        };
        thread.start();
        return 0;
    }

    private void conn0(WindowEvent windowevent)
    {
        try
        {
            endProgram();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void conn100(ActionEvent actionevent)
    {
        endProgram();
    }

    public void conn102(ActionEvent actionevent)
    {
        showProfile();
    }

    public void conn105(ActionEvent actionevent)
    {
        FeedbackForm feedbackform = new FeedbackForm(new Frame());
        feedbackform.setSubmitButton(false);
        feedbackform.setTextFocus();
        centerDialog(feedbackform);
        feedbackform.setVisible(true);
        if(FeedbackForm.getSubmitButton())
        {
            ZCast.displayDebug("comments submitted by " + nzUserid);
            ZCast.displayDebug(feedbackform.getComments());
            String s = feedbackform.getAttachFileName();
            Vector vector = new Vector(2);
            String s1 = nzUserid + "::" + feedbackform.getComments();
            vector.addElement(s1);
            if(s != null)
            {
                ZCast.displayDebug("attach file: " + s);
                try
                {
                    File file = new File(s);
                    long l = file.length();
                    if(l > 0x10000L)
                        l = 0x10000L;
                    FileInputStream fileinputstream = new FileInputStream(file);
                    byte abyte0[] = new byte[(int)l];
                    fileinputstream.read(abyte0);
                    fileinputstream.close();
                    vector.addElement(abyte0);
                }
                catch(Exception _ex) { }
            }
            Transaction transaction = new Transaction(transMessages);
            transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
            TransactionRequest transactionrequest = new TransactionRequest();
            transactionrequest.setClientVersion(ConfigParams.getVers());
            transactionrequest.setDataObject(vector);
            transactionrequest.setUserNumber(Initializer.m_userNumber);
            transactionrequest.setSessionId(Initializer.m_sessionId);
            transactionrequest.setUserId(nzUserid);
            TransactionResult transactionresult = transaction.execute(transactionrequest, "FDBK");
            ZCast.displayDebug("(FDBK) success flag set to: " + transactionresult.getSuccessFlag());
            if(transactionresult.getDataObject() instanceof TransactionResponse)
            {
                TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
                Initializer.compareTransResponse(transactionresponse, "FDBK");
                ZCast.displayDebug("(FDBK) return code = " + transactionresponse.getReturnCode());
            } else
            {
                ZCast.displayDebug("transaction FDBK failed to return a TransactionResponse");
            }
        }
    }

    private synchronized void conn12(FocusEvent focusevent)
    {
        try
        {
            if(onTopDelay > 0)
                wait(onTopDelay);
            ((Window)focusevent.getComponent()).toFront();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void conn2(MouseEvent mouseevent)
    {
        try
        {
            ZpComponent zpcomponent = uiShell.getZpComponentForPoint(mouseevent.getPoint());
            if(zpcomponent != null)
            {
                Long long1 = new Long(zpcomponent.getComponentId());
                Integer integer = zpcomponent.getEventId();
                if(integer != null && integer.intValue() == 0)
                {
                    zpcomponent.mouseClickCounter();
                    table.put(long1, zpcomponent);
                    ZpComponent zpcomponent1 = (ZpComponent)table.get(long1);
                    ZCast.displayDebug("Comp tracking: ID= " + long1.longValue() + " counter= " + zpcomponent1.getMouseClickedCount() + "\n");
                }
            }
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void conn50(ActionEvent actionevent)
    {
        getPrevButtonAd();
    }

    public void conn60(ActionEvent actionevent)
    {
        getNextButtonAd();
    }

    private void handleMousePressedOnAdDisplayer(MouseEvent mouseevent)
    {
        if((mouseevent.getModifiers() & 0x4) == 4)
        {
            ZCast.displayDebug("In handleMousePressedOnAdDisplayer, right mouse, show menu");
            Point point = adDisplayer.getLocation();
            showContextMenu(mouseevent.getX() + point.x, mouseevent.getY() + point.y);
            ZCast.displayDebug("before return in handleMousePressedOnAdDisplayer");
        } else
        {
            adDisplayer.adClicked(mouseevent.getWhen());
        }
    }

    public void conn91(ActionEvent actionevent)
    {
        showProfile();
    }

    private Point convertPointToScreen(Point point, Component component)
    {
        Rectangle rectangle = component.getBounds();
        int i = rectangle.x;
        int j = rectangle.y;
        return new Point(point.x + i, point.y + j);
    }

    private Panel createSearchPanel()
    {
        searchPanel = new Panel();
        searchPanel.setLayout(null);
        JCString jcstring = JCString.parse(this, "[IMG=images/entersearch.gif][ALIGN=TOP]");
        enterSearch = new JCLabel(jcstring);
        enterSearch.setAlignment(0);
        searchPanel.add(enterSearch);
        searchTextField = new TextField("", 15);
        searchTextField.setBackground(Color.white);
        searchTextField.setForeground(Color.black);
        searchTextField.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent keyevent)
            {
                if(keyevent.getKeyCode() == 10)
                    handleBrowseString(searchTextField.getText());
            }

        });
        searchPanel.add(searchTextField);
        searchButton = new NZButton("search");
        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                ZCast.displayDebug("\n\n****Search button EVENT!!!\n");
                handleBrowseString(searchTextField.getText());
            }

        });
        searchPanel.add(searchButton);
        jcstring = JCString.parse(this, "[IMG=images/powered_by_looksmart.gif][ALIGN=TOP]");
        lsLogo = new JCLabel(jcstring);
        lsLogo.setAlignment(0);
        searchPanel.add(lsLogo);
        setSearchPanelLocations();
        return searchPanel;
    }

    public void dockWindow(int i)
    {
        if(OSDetectNative.checkResolution() >= ZEROPORT_WIDTH)
        {
            currentOrientation = i;
            ShellUIWrapper shelluiwrapper = uiShell.getShellWrapper();
            ContainerLayout containerlayout = (ContainerLayout)shelluiwrapper.getShellLayout(new Long(uiShell.getCurrentLayoutID())).elementAt(0);
            String s = containerlayout.getLayoutDesc();
            if(s.equals("up") || s.equals("down") || s.equals("up-icon") || s.equals("down-icon"))
                if(aTickerPanel != null && aTickerPanel.isVisible())
                {
                    setShellState((int)uiShell.getLayoutIdByDesc("down-adv"));
                } else
                {
                    setShellState((int)uiShell.getLayoutIdByDesc("up-adv"));
                    resetTickerButtons();
                }
            if(IsZeroPortDocked())
            {
                ZCast.displayDebug("@@@@ ZeroPort is docked? Pushing window.");
                OSDetectNative.pushWindow(getSize().width, getSize().height, i);
            } else
            {
                ZCast.displayDebug("@@@@ ZeroPort is NOT docked. Docking window.");
                boolean flag = OSDetectNative.dock(getSize().width, getSize().height, i);
                if(flag)
                    setIsDocked(flag);
            }
            pmmPosition.setEnabled(false);
            setSearchPanelLocations();
        }
    }

    void enableOrientationItems()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        if(OSDetectNative.checkResolution() >= ZEROPORT_WIDTH && dimension.width >= ZEROPORT_WIDTH)
        {
            pmtiDockBottom.setEnabled(true);
            pmtiDockTop.setEnabled(true);
        } else
        {
            pmtiDockBottom.setEnabled(false);
            pmtiDockTop.setEnabled(false);
        }
        pmtiStandard.setEnabled(true);
        pmtiIcons.setEnabled(true);
        switch(currentOrientation)
        {
        case 0: // '\0'
            pmtiDockTop.setEnabled(false);
            break;

        case 1: // '\001'
            pmtiDockBottom.setEnabled(false);
            break;

        case -1: 
            pmtiStandard.setEnabled(false);
            break;

        case -2: 
            pmtiIcons.setEnabled(false);
            break;
        }
    }

    public void endPostProcessing()
    {
        dispose();
    }

    public void endPreProcessing()
    {
        saveProperties();
        if(nzLog != null)
        {
            Vector vector = null;
            String s = ServerParms.getParm("ComponentTracking", "false");
            if(s != null && s.equalsIgnoreCase("true"))
            {
                vector = new Vector();
                Vector vector1;
                for(Enumeration enumeration = table.keys(); enumeration.hasMoreElements(); ZCast.displayDebug("TransObj Listing:\n ID:" + vector1.elementAt(0) + " ActionType:" + vector1.elementAt(1) + " ClickCount:" + vector1.elementAt(2) + " OverCount:" + vector1.elementAt(3)))
                {
                    Long long1 = (Long)enumeration.nextElement();
                    ZpComponent zpcomponent = (ZpComponent)table.get(long1);
                    vector1 = new Vector(4);
                    vector1.addElement(new Long(zpcomponent.getComponentId()));
                    vector1.addElement(new Integer(zpcomponent.getActionType()));
                    vector1.addElement(new Integer(zpcomponent.getMouseClickedCount()));
                    vector1.addElement(new Integer(zpcomponent.getMouseOverCount()));
                    vector.addElement(vector1);
                }

            }
            nzLog.setComponentTrackingData(vector);
            if(!ZCast.m_demoMode)
                nzLog.sendSessionEndStats();
        }
    }

    private void endProgram()
    {
        ZeroOut.popup(new Frame());
    }

    public void focusGained(FocusEvent focusevent)
    {
    }

    public void focusLost(FocusEvent focusevent)
    {
        mouseExited(null);
        subMenu.setVisible(false);
    }

    public Component getAdBanner()
    {
        return uiShell.getComponentByDesc("ad banner");
    }

    public int getCurrentOrientation()
    {
        return currentOrientation;
    }

    public int getCurrentTopic()
    {
        return currentTopic;
    }

    public ImageGenerator getImageGenerator()
    {
        if(currentTopic >= 0)
            return aGenerator;
        else
            return null;
    }

    public static boolean getIsPopupShowing()
    {
        return isPopupShowing;
    }

    public ZpComponent getMailComponent()
    {
        return mailComponent;
    }

    public MonitorThread getMonitorThread()
    {
        return monThread;
    }

    public boolean getNewBrowserState()
    {
        if(pmviNewBrowser != null)
            return pmviNewBrowser.getState();
        else
            return true;
    }

    private void getNextButtonAd()
    {
        ZCast.displayDebug("============================NEXT button clicked =====================================");
        adDisplayer.setInterrupt(2);
    }

    public String getNzUserid()
    {
        return nzUserid;
    }

    public static OSDetectNative getOsDetect()
    {
        return osDetect;
    }

    public MenuItem getPmiBack()
    {
        return pmiBack;
    }

    public MenuItem getPmiNext()
    {
        return pmiNext;
    }

    private void getPrevButtonAd()
    {
        ZCast.displayDebug("============================PREV button clicked =====================================");
        adDisplayer.setInterrupt(1);
    }

    public Profile getProfile()
    {
        return prof;
    }

    public RASWinNative getRasNative()
    {
        return fw;
    }

    private int getTextFieldWidth()
    {
        int i = 0;
        Rectangle rectangle = OSDetectNative.getWorkspaceSize();
        ZCast.displayDebug("**** width = " + rectangle.width);
        i = rectangle.width - 800;
        if(i > 0)
        {
            i -= searchButton.getSize().width;
            i -= 15;
        } else
        {
            i = 0;
        }
        return i;
    }

    public NZUserLog getUserLog()
    {
        return nzLog;
    }

    private ZpComponent getSearchButtonComponent()
    {
        if(searchButtonComponent == null)
        {
            ZpContainer zpcontainer = uiShell.getContainerByDesc("virtual components");
            if(zpcontainer != null)
            {
                ZpComponent azpcomponent[] = zpcontainer.getZpComponents();
                for(int i = 0; i < azpcomponent.length; i++)
                {
                    if(!"search button".equals(azpcomponent[i].getDescription()))
                        continue;
                    searchButtonComponent = azpcomponent[i];
                    break;
                }

                if(searchButtonComponent != null)
                    ZCast.displayDebug("UI does not contain component \"search button\". Will not be able to log clicks to the search Button");
            } else
            {
                ZCast.displayDebug("UI does not contain container \"virutal components\". Will not be able to log clicks to the search Button");
            }
        }
        return searchButtonComponent;
    }

    private void handleBrowseString(String s)
    {
        if(s == null || s.equals(""))
        {
            ZCast.displayDebug("Browse string not specified. Ignoring.");
            return;
        }
        ZCast.displayDebug("browse string: " + s);
        if(s.startsWith("http://") || s.startsWith("www."))
        {
            Initializer.m_zwindow.showAdUrl(s);
        } else
        {
            BrowseHttpRequest browsehttprequest = new BrowseHttpRequest();
            browsehttprequest.gotoRealName(s);
        }
        if(searchButtonComponent != null)
        {
            Long long1 = new Long(searchButtonComponent.getComponentId());
            Integer integer = searchButtonComponent.getEventId();
            if(integer != null)
                if(integer.intValue() == 0)
                {
                    searchButtonComponent.mouseClickCounter();
                    table.put(long1, searchButtonComponent);
                    ZpComponent zpcomponent = (ZpComponent)table.get(long1);
                    ZCast.displayDebug("Comp tracking: ID= " + long1.longValue() + " counter= " + zpcomponent.getMouseClickedCount() + "\n");
                } else
                {
                    logEvent(long1.intValue(), integer.intValue());
                }
        }
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        addWindowListener(this);
        addMouseListener(this);
        addFocusListener(this);
    }

    private void initFrame1Stuff()
    {
        setCmdProg();
        if(prof == null)
            prof = new Profile();
        loadProperties();
        String s = System.getProperty("nz.showDrag");
        if(s != null)
            showDrag = true;
        ZCast.displayDebug("transfer mode set to " + ZCast.m_connectionType);
        nzLog = NZUserLog.getDefaultUserLog();
    }

    public void initialize()
        throws NZException
    {
        uiShell = new ZpShell();
        uiShell.fetchUI("shellui.obj");
        if(uiShell.getShellUi() == null)
            throw new NZException(300, resNZResource.getString("Unable_to_initialize_ZeroPort"));
        uiShell.fetchResourceCache("cache.obj");
        if(uiShell.getResourceCache() == null)
            throw new NZException(300, resNZResource.getString("Unable_to_initialize_ZeroPort"));
        showCompaq = ConfigParams.getVSource().startsWith("Q");
        setName("NZWindow");
        setLayout(null);
        initConnections();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBackground(Color.black);
        mt = new MediaTracker(this);
        uiShell.initShell();
        getSearchButtonComponent();
        imagesLoaded = true;
        initPopupMenu();
        initFrame1Stuff();
        setLocations();
        setSize(mySize);
        pmtiOnOff.setState(tickerState);
        if(prevButton != null)
            prevButton.setEnabled(false);
        if(nextButton != null)
            nextButton.setEnabled(false);
        if(!ZCast.m_demoMode)
        {
            mClient = new MailClient(nzUserid, nzPassword, this);
            if(Initializer.m_serviceThread != null)
                Initializer.m_serviceThread.register(mClient, 0, Initializer.m_mailInterval * 60, 0, 5, true);
        }
        Point point = new Point(prof.getX(), prof.getY());
        adjustToScreen(point);
        setLocation(point.x, point.y);
        if(showDrag)
            addMouseMotionListener(this);
        add(popupMenu1);
        adDisplayer.setVisible(true);
        adDisplayer.start();
        fw = getRasNative();
        zphw = new ZpHover(parentFrame);
        sponsorMenu = new ZpMenu(this);
        subMenu = new ZpSubMenu(this);
        setVisible(true);
        ShellUIWrapper shelluiwrapper = uiShell.getShellWrapper();
        if(shelluiwrapper != null)
        {
            ContainerLayout containerlayout = (ContainerLayout)shelluiwrapper.getShellLayout(new Long(uiShell.getCurrentLayoutID())).elementAt(0);
            String s = containerlayout.getLayoutDesc();
            if(s.equals("up-adv") || s.equals("down-adv"))
            {
                if(OSDetectNative.checkResolution() >= ZEROPORT_WIDTH)
                {
                    if(point.y == 0)
                    {
                        ZCast.displayDebug("NZWINDOW: Dock on top ");
                        dockWindow(0);
                    } else
                    {
                        ZCast.displayDebug("NZWINDOW: Dock on Bottom ");
                        dockWindow(1);
                    }
                } else
                {
                    if(OSDetectNative.checkResolution() >= ZEROPORT_WIDTH)
                    {
                        pmtiDockTop.setEnabled(false);
                        pmtiDockBottom.setEnabled(false);
                    } else
                    {
                        pmtiDockBottom.setEnabled(false);
                        pmtiDockTop.setEnabled(false);
                        pmtiStandard.setEnabled(false);
                    }
                    undockWindow(tickerState ? "down" : "up");
                }
            } else
            {
                ZCast.displayDebug("+++++++++Not Docking: " + containerlayout.getLayoutDesc());
                if(s.equals("down-icon") || s.equals("up-icon"))
                {
                    currentOrientation = -2;
                    pmtiIcons.setEnabled(false);
                    pmtiStandard.setEnabled(true);
                }
                setIsDocked(false);
            }
        }
        OSDetectNative.setNZwindow(this);
        ZCast.displayDebug("!!!!!!!!!!@@@ currentTickerSpeed= " + currentTickerSpeed);
        if(tickerState && currentTickerSpeed > 0)
        {
            ZpComponent zpcomponent = uiShell.getComponentByDesc("speed indicator");
            if(zpcomponent != null)
                zpcomponent.setState(currentTickerSpeed);
            else
                ZCast.displayDebug("!!!!!!!!!!@@@ ZpComponent is null");
            subMenu.repaint();
            aTickerPanel.speedUp();
        }
    }

    public void initializeTickerPanel()
    {
        ZCast.displayDebug("initializeTickerPanel Starts");
        String s = System.getProperty("nz.fake");
        if(s != null)
            TickerTransactions.nzFake = s.equals("true");
        aGenerator = new ImageGenerator();
        for(int i = 0; i < 3; i++)
            aGenerator.setWrapper(i, new TopicWrapper(5, 0x927c0L));

        TopicWrapper topicwrapper = new TopicWrapper(240, 0xf4240L);
        topicwrapper.setData((new TickerSwd()).getTokens());
        aGenerator.setWrapper(3, topicwrapper);
        aRetriever = new DataRetriever(aGenerator);
        aGenerator.setRetriever(aRetriever);
        aTickerPanel = new TickerPanel(aGenerator);
        aTickerPanel.setTickerOn();
        aTickerPanel.setLocation(tickerComponent.getLocation());
        aTickerPanel.setSize(tickerComponent.getSize());
        add(aTickerPanel);
        aTickerPanel.addMouseListener(this);
        ZCast.displayDebug("initializeTickerPanel Ends");
    }

    private void initPopupMenu()
    {
        popupMenu1 = new PopupMenu();
        pmiBack = new MenuItem(resNZResource.getString("Back"));
        popupMenu1.add(pmiBack);
        pmiNext = new MenuItem(resNZResource.getString("Next"));
        popupMenu1.add(pmiNext);
        pmmView = new Menu(resNZResource.getString("View"));
        pmtiStandard = new MenuItem("Standard");
        pmmView.add(pmtiStandard);
        pmtiDockBottom = new MenuItem("Dock Bottom");
        pmtiDockTop = new MenuItem("Dock Top");
        pmmView.add(pmtiDockTop);
        pmmView.add(pmtiDockBottom);
        pmtiIcons = new MenuItem("Icons");
        pmmView.add(pmtiIcons);
        popupMenu1.add(pmmView);
        pmmPosition = new Menu(resNZResource.getString("Position"));
        pmpiTopLeft = new MenuItem(resNZResource.getString("Top_left"));
        pmmPosition.add(pmpiTopLeft);
        pmpiTopRight = new MenuItem(resNZResource.getString("Top_right"));
        pmmPosition.add(pmpiTopRight);
        pmpiBottomLeft = new MenuItem(resNZResource.getString("Bottom_left"));
        pmmPosition.add(pmpiBottomLeft);
        pmpiBottomRight = new MenuItem(resNZResource.getString("Bottom_right"));
        pmmPosition.add(pmpiBottomRight);
        popupMenu1.add(pmmPosition);
        pmmBrowser = new Menu(resNZResource.getString("Browser"));
        pmviNewBrowser = new CheckboxMenuItem(resNZResource.getString("New_Browser_Window_on_Click"));
        pmviNewBrowser.setState(MemberRecs.getNewBrowser());
        pmmBrowser.add(pmviNewBrowser);
        popupMenu1.add(pmmBrowser);
        if(useBookmarks)
        {
            pmmPrint = new Menu(resNZResource.getString("Print"));
            pmpiAd = new MenuItem(resNZResource.getString("Print_Ad"));
            pmmPrint.add(pmpiAd);
            pmpiCoupon = new MenuItem(resNZResource.getString("Print_Coupon"));
            pmmPrint.add(pmpiCoupon);
            popupMenu1.add(pmmPrint);
        }
        if(useBookmarks)
        {
            pmmBookmarks = new Menu(resNZResource.getString("Bookmarks"));
            popupMenu1.add(pmmBookmarks);
        }
        pmiProfile = new MenuItem(resNZResource.getString("Profile"));
        popupMenu1.add(pmiProfile);
        popupMenu1.add(new MenuItem("-"));
        pmmTicker = new Menu(resNZResource.getString("Ticker"));
        pmtiOnOff = new CheckboxMenuItem(resNZResource.getString("On"));
        pmtiOnOff.setState(tickerState);
        pmmTicker.add(pmtiOnOff);
        pmtiCustomize = new MenuItem(resNZResource.getString("Customize"));
        pmmTicker.add(pmtiCustomize);
        popupMenu1.add(pmmTicker);
        pmmEmail = new Menu(resNZResource.getString("Email"));
        pmemCheck = new MenuItem(resNZResource.getString("Check_for_messages"));
        pmmEmail.add(pmemCheck);
        pmemLaunch = new MenuItem(resNZResource.getString("Show_e-mail"));
        pmmEmail.add(pmemLaunch);
        pmemConfig = new MenuItem(resNZResource.getString("Configure_e-mail_account"));
        pmmEmail.add(pmemConfig);
        pmemCOEAK = new MenuItem(resNZResource.getString("Change_of_e-mail_address_k"));
        pmmEmail.add(pmemCOEAK);
        popupMenu1.add(pmmEmail);
        popupMenu1.add(new MenuItem("-"));
        pmiNetzero = new MenuItem(resNZResource.getString("NetZero_Home"));
        popupMenu1.add(pmiNetzero);
        pmmSupport = new Menu(resNZResource.getString("Tech_Support"));
        pmisNet = new MenuItem(resNZResource.getString("NetZero_website"));
        pmmSupport.add(pmisNet);
        pmisFeed = new MenuItem(resNZResource.getString("Feedback"));
        pmmSupport.add(pmisFeed);
        popupMenu1.add(pmmSupport);
        popupMenu1.add(new MenuItem("-"));
        pmmHelp = new Menu(resNZResource.getString("Online_Help"));
        pmhiAbout = new MenuItem(resNZResource.getString("About"));
        pmmHelp.add(pmhiAbout);
        pmhiTutorial = new MenuItem(resNZResource.getString("Online_tutorial"));
        pmmHelp.add(pmhiTutorial);
        popupMenu1.add(pmmHelp);
        popupMenu1.add(new MenuItem("-"));
        pmiExit = new MenuItem(resNZResource.getString("Exit"));
        popupMenu1.add(pmiExit);
        pmiBack.setEnabled(false);
        pmiNext.setEnabled(false);
        int i = ZCast.m_connectionType;
        if(i == 2)
        {
            pmisNet.setEnabled(false);
            pmisFeed.setEnabled(false);
        }
        pmpiTopLeft.addActionListener(this);
        pmpiTopRight.addActionListener(this);
        pmpiBottomLeft.addActionListener(this);
        pmpiBottomRight.addActionListener(this);
        pmiExit.addActionListener(this);
        pmviNewBrowser.addItemListener(this);
        pmiProfile.addActionListener(this);
        pmiBack.addActionListener(this);
        pmiNext.addActionListener(this);
        pmtiOnOff.addItemListener(this);
        pmtiCustomize.addActionListener(this);
        pmemCheck.addActionListener(this);
        pmemLaunch.addActionListener(this);
        pmemConfig.addActionListener(this);
        pmemCOEAK.addActionListener(this);
        if(useBookmarks)
        {
            pmpiAd.addActionListener(this);
            pmpiCoupon.addActionListener(this);
        }
        pmiNetzero.addActionListener(this);
        pmhiAbout.addActionListener(this);
        pmhiTutorial.addActionListener(this);
        if(i == 0 || i == 1)
        {
            pmisNet.addActionListener(this);
            pmisFeed.addActionListener(this);
        }
        pmtiDockTop.addActionListener(this);
        pmtiDockBottom.addActionListener(this);
        pmtiStandard.addActionListener(this);
        pmtiIcons.addActionListener(this);
    }

    public boolean IsZeroPortDocked()
    {
        return isDocked;
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        hasPressed = false;
        isPopupShowing = false;
        if(itemevent.getSource() == pmtiOnOff)
        {
            if(tickerState)
            {
                uiShell.getComponentByAction(2).setState(0);
                ZCast.displayDebug("itemStateChanged 1 : ACTION_SHOWTICKER ");
                setTickerMode(false, (int)uiShell.getComponentByAction(3).getActionObjectId());
                setAllTickerButtonsToOff();
            } else
            {
                ZpComponent zpcomponent = uiShell.getComponentByAction(2);
                int i = 0;
                if(zpcomponent != null)
                {
                    zpcomponent.setState(1);
                    i = (int)uiShell.getComponentByAction(2).getActionObjectId();
                } else
                {
                    i = (int)uiShell.getLayoutIdByDesc("down-icon");
                }
                ZCast.displayDebug("itemStateChanged 2 : ACTION_SHOWTICKER ");
                setTickerMode(true, i);
            }
            return;
        }
        if(itemevent.getSource() == pmviNewBrowser)
        {
            ZCast.displayDebug(">>> Saving New Browser on Click:" + getNewBrowserState());
            MemberRecs.setNewBrowser(getNewBrowserState());
            MemberRecs.writeRecs();
        }
    }

    public void loadProperties()
    {
        try
        {
            Vector vector = (Vector)ClientUI.deserializeUi("pdata.obj");
            Point point = (Point)vector.elementAt(0);
            prof.setX(point.x);
            prof.setY(point.y);
            Long long1 = (Long)vector.elementAt(1);
            if(long1.intValue() > 0)
            {
                if(uiShell.getShellWrapper().getShellLayout(long1) == null || uiShell.getShellWrapper().getShellLayout(long1).size() == 0)
                {
                    ZCast.displayDebug(long1.intValue() + " not a valid layout, using default");
                    return;
                }
                defState = long1;
            }
            Integer integer = (Integer)vector.elementAt(2);
            tickerState = integer.intValue() == 1;
            currentTickerSponsorID = (Long)vector.elementAt(3);
            ZCast.displayDebug("currentTickerSponsorID in load props = " + currentTickerSponsorID);
            integer = (Integer)vector.elementAt(4);
            iconState = integer.intValue() == 1;
            currentTickerSpeed = ((Integer)vector.elementAt(5)).intValue();
            return;
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    public void logEvent(int i, int j)
    {
        if(eventLogging > 0)
        {
            Vector vector = new Vector(3);
            Vector vector1 = new Vector(1);
            vector.addElement(nzLog.getCreateDateTime());
            vector.addElement(new Integer(i));
            vector.addElement(new Integer(j));
            vector1.addElement(vector);
            TransactionRequest transactionrequest = new TransactionRequest(getNzUserid(), ConfigParams.getVers(), vector1);
            transactionrequest.setSessionId(Initializer.m_sessionId);
            transactionrequest.setUserNumber(Initializer.m_userNumber);
            Transaction transaction = new Transaction(false);
            transaction.setServerLocation(ServerParms.getParm("trip1", transaction.getServerLocation()));
            TransactionResult transactionresult = transaction.execute(transactionrequest, "EVNT");
            if(transactionresult != null)
            {
                ZCast.displayDebug("logEvent::(EVNT) success flag set to: " + transactionresult.getSuccessFlag());
                if(!(transactionresult.getDataObject() instanceof TransactionResponse))
                {
                    ZCast.displayDebug("logEvent::transaction EVNT failed to return TransactionResponse object");
                } else
                {
                    TransactionResponse transactionresponse = (TransactionResponse)transactionresult.getDataObject();
                    Initializer.compareTransResponse(transactionresponse, "EVNT");
                    ZCast.displayDebug("logEvent::(EVNT) return code = " + transactionresponse.getReturnCode());
                }
            } else
            {
                ZCast.displayDebug("NZwindow::logEvent::TransactionResult is null");
            }
        } else
        {
            ZCast.displayDebug("NZwindow::logEvent::eventLogging <= 0");
        }
    }

    public void logEvent(ZpComponent zpcomponent)
    {
        Integer integer = zpcomponent.getEventId();
        if(integer != null && integer.intValue() > 0)
            logEvent(zpcomponent.getIComponent().getComponentID().intValue(), integer.intValue());
        else
            ZCast.displayDebug("NZwindow:: logEvent :: eid is NULL  is <= 0 ");
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        zphw.interrupt();
        hoverComponent = null;
        resetComponent(null);
        clickComponent = null;
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
        isPopupShowing = false;
        if(sponsorMenu.isVisible())
            return;
        ZpComponent zpcomponent;
        if(mouseevent.getSource() == adDisplayer)
            zpcomponent = adComponent;
        else
            zpcomponent = uiShell.getZpComponentForPoint(mouseevent.getPoint());
        if(zpcomponent != null)
        {
            if(zpcomponent != hoverComponent)
            {
                zphw.interrupt();
                hoverComponent = zpcomponent;
                Point point = getLocationOnScreen();
                point.x += mouseevent.getPoint().x;
                point.y += mouseevent.getPoint().y;
                zphw.show(point, zpcomponent.getHoverText());
            }
        } else
        if(hoverComponent != null)
        {
            zphw.interrupt();
            hoverComponent = null;
        }
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        resetMonitorThread();
        if(skipNextPressed)
        {
            skipNextPressed = false;
            return;
        }
        if(hasPressed)
            return;
        hasPressed = true;
        Point point = mouseevent.getPoint();
        if(hoverComponent != null)
        {
            zphw.interrupt();
            hoverComponent = null;
        }
        sponsorMenu.setVisible(false);
        if(mouseevent.getSource() == adDisplayer && (mouseevent.getSource() instanceof Component) && ((Component)mouseevent.getSource()).contains(mouseevent.getPoint()))
        {
            ZCast.displayDebug("pressed adDisplayer");
            handleMousePressedOnAdDisplayer(mouseevent);
        } else
        if(mouseevent.getSource() == aTickerPanel && (mouseevent.getSource() instanceof Component) && ((Component)mouseevent.getSource()).contains(mouseevent.getPoint()))
        {
            ZCast.displayDebug("pressed aTickerPanel");
        } else
        {
            if(mouseevent.getSource() != this)
            {
                point.x = point.x + ((Component)mouseevent.getSource()).getLocation().x;
                point.y = point.y + ((Component)mouseevent.getSource()).getLocation().y;
            }
            clickComponent = null;
            ZpComponent zpcomponent = uiShell.getZpComponentForPoint(point);
            if(zpcomponent != null)
            {
                String s = zpcomponent.getDescription();
                ZCast.displayDebug(s);
                if(s.equals("grab_bar"))
                {
                    hasPressed = false;
                    if(IsZeroPortDocked())
                    {
                        skipNextPressed = true;
                        undockWindow(tickerState ? "down" : "up");
                        OSDetectNative.moveResizeZeroPortWithMouseAtPoint(getSize().width / 2, 5, getSize().width, getSize().height);
                    } else
                    {
                        OSDetectNative.moveZeroPortWithMouse();
                    }
                    return;
                }
                if(zpcomponent.getComponentType().equals("button") || zpcomponent.getComponentType().equals("hideable-button"))
                {
                    ZCast.displayDebug("on a button");
                    zpcomponent.setState(1);
                    clickComponent = zpcomponent;
                    repaint();
                } else
                if(zpcomponent.getComponentType().equals("radio-button"))
                {
                    if(zpcomponent.getState() != 1)
                        clickComponent = zpcomponent;
                } else
                if(zpcomponent.getComponentType().equals("image"))
                    clickComponent = zpcomponent;
                cyComponent = zpcomponent;
            } else
            {
                hasPressed = false;
            }
        }
    }

    public synchronized void mouseReleased(MouseEvent mouseevent)
    {
        hasPressed = false;
        Point point = mouseevent.getPoint();
        if(mouseevent.getSource() != this)
        {
            point.x = point.x + ((Component)mouseevent.getSource()).getLocation().x;
            point.y = point.y + ((Component)mouseevent.getSource()).getLocation().y;
        }
        ZCast.displayDebug(" Mouse released at " + mouseevent.getPoint() + ", really at " + point);
        ZpComponent zpcomponent = uiShell.getZpComponentForPoint(point);
        if(zpcomponent == null)
        {
            clickComponent = null;
            return;
        }
        ZCast.displayDebug("++++ Mouse released, at: " + point + ", zcp: " + zpcomponent);
        if(zpcomponent.getDescription().indexOf("ticker_emb") < 0)
            subMenu.setVisible(false);
        if(mouseevent.getSource() == aTickerPanel && (mouseevent.getSource() instanceof Component) && ((Component)mouseevent.getSource()).contains(mouseevent.getPoint()))
        {
            logEvent(tickerComponent);
            ZCast.displayDebug("++++ Mouse released, on ticker panel, log event and return.");
            return;
        }
        if(mouseevent.getSource() == adDisplayer && (mouseevent.getSource() instanceof Component) && ((Component)mouseevent.getSource()).contains(mouseevent.getPoint()))
            return;
        if(isPopupShowing || mouseevent.getSource().getClass().getName().equalsIgnoreCase("MenuItem"))
        {
            ZCast.displayDebug("Menu Item clicked.... not going to show any urls");
            return;
        }
        if(zpcomponent != null)
        {
            int i = zpcomponent.getActionType();
            if(resetComponent(zpcomponent))
            {
                clickComponent = null;
                return;
            }
            conn2(mouseevent);
            if(zpcomponent.getComponentType().equals("button") || zpcomponent.getComponentType().equals("hideable-button"))
            {
                zpcomponent.setState(0);
                repaint();
                logEvent(zpcomponent);
                if(i == 6)
                    endProgram();
            }
            if(zpcomponent.getComponentType().equals("radio-button"))
            {
                if(zpcomponent.getDescription().indexOf("tmode") >= 0 && (aTickerPanel == null || !aTickerPanel.isVisible()))
                {
                    ZCast.displayDebug("tmode button pressed with ticker panel off");
                    return;
                }
                ZCast.displayDebug("radio button pressed");
                if(zpcomponent.getState() != 1)
                {
                    zpcomponent.setState(1);
                    clickComponent = null;
                    repaint();
                }
                if(i == 3)
                {
                    setTickerMode(false, (int)zpcomponent.getActionObjectId());
                    if(zpcomponent.getDescription().equals("toff"))
                    {
                        zpcomponent.setState(1);
                        clickComponent = zpcomponent;
                        setAllTickerButtonsToOff();
                        repaint();
                    }
                    return;
                }
                if(i == 2)
                {
                    setTickerMode(true, (int)zpcomponent.getActionObjectId());
                    return;
                }
                if(i == 4 && aGenerator != null)
                {
                    tickerSponsor = zpcomponent;
                    currentTickerSponsorID = zpcomponent.getIComponent().getComponentID();
                    ZCast.displayDebug("currentTickerSponsorID in ACTION_CHANGETICKER = " + currentTickerSponsorID);
                    if(ZCast.m_demoMode)
                        aGenerator.setUrlBase("demo/");
                    else
                        aGenerator.setUrlBase(zpcomponent.getActionString());
                    ZCast.displayDebug("urlbase set to " + zpcomponent.getActionString());
                    Long long1 = zpcomponent.getIComponent().getActionObjID();
                    if(long1 != null)
                    {
                        ZpComponent zpcomponent3 = uiShell.findComponentByID(long1);
                        if(zpcomponent3 != null)
                            tickerComponent.getIComponent().setEventID(zpcomponent3.getIComponent().getEventID());
                    }
                    setTickerTopic(zpcomponent.getIComponent().getFeedID().intValue());
                }
                return;
            }
            ZCast.displayDebug("action " + zpcomponent.getActionType() + ", " + zpcomponent.getActionString());
            if(i == 5 || i == 19)
            {
                ZpContainer zpcontainer = uiShell.getContainerById(zpcomponent.getIComponent().getActionObjID());
                showSponsorMenu(zpcomponent, zpcontainer);
            }
            if(i == 1 || i == 19)
                if(zpcomponent.getDescription().equals("search"))
                {
                    showSearchDialog(zpcomponent);
                } else
                {
                    String s = zpcomponent.getActionString();
                    if(s != null)
                        showAdUrl(s);
                }
            if(i == 8 && prevButton != null && prevButton.isEnabled())
                getPrevButtonAd();
            if(i == 7 && nextButton != null && nextButton.isEnabled())
                getNextButtonAd();
            if(zpcomponent == tcButton)
                showTickerCustomization();
            if(i == 11)
                if(zpcomponent.getDescription().equals("browse"))
                    showBrowse();
                else
                    showSearchDialog(null);
            if(zpcomponent == mailComponent)
                showMail();
            if(i == 13)
            {
                if(zpcomponent.getDescription().indexOf("ticker_emb") >= 0)
                {
                    if(!tickerState)
                    {
                        uiShell.getComponentByAction(2).setState(1);
                        setTickerMode(true, (int)uiShell.getComponentByAction(2).getActionObjectId());
                    }
                    ZpContainer zpcontainer1 = uiShell.getContainerByDesc("my_ticker");
                    subMenu.show(zpcontainer1, aTickerPanel, 0, 0);
                } else
                if(zpcomponent.getDescription().equals("zeroport"))
                    showContextMenu(zpcomponent.getLocation().x, zpcomponent.getLocation().y + zpcomponent.getSize().height);
                else
                    showContextMenu(mouseevent.getPoint().x + 5, mouseevent.getPoint().y + 5);
            } else
            if(i == 15)
            {
                if(currentTickerSpeed < 4)
                {
                    currentTickerSpeed++;
                    ZpComponent zpcomponent1 = uiShell.getComponentByDesc("speed indicator");
                    zpcomponent1.setState(currentTickerSpeed);
                    subMenu.repaint();
                }
                aTickerPanel.speedUp();
            }
            if(i == 14)
            {
                if(currentTickerSpeed > 0)
                {
                    currentTickerSpeed--;
                    ZpComponent zpcomponent2 = uiShell.getComponentByDesc("speed indicator");
                    zpcomponent2.setState(currentTickerSpeed);
                    subMenu.repaint();
                }
                aTickerPanel.speedDown();
            }
            if(i == 17)
            {
                ZCast.displayDebug("action....dock top");
                dockWindow(0);
            } else
            if(i == 18)
            {
                ZCast.displayDebug("action....dock bottom");
                dockWindow(1);
            }
            if(cyComponent != null && aGenerator != null)
            {
                if(zpcomponent.getDescription().equals(cy1) && cyComponent.getDescription().equals(cy0))
                    aGenerator.setTopic(3);
                cyComponent = null;
            }
            return;
        } else
        {
            return;
        }
    }

    private void moveWindow(int i, int j)
    {
        setVisible(false);
        setLocation(i, j);
        prof.setX(i);
        prof.setY(j);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        if(!imagesLoaded)
            return;
        if(fromUpdate)
        {
            adDisplayer.paint(g);
            return;
        } else
        {
            uiShell.paint(g);
            super.paint(g);
            return;
        }
    }

    public boolean resetComponent(ZpComponent zpcomponent)
    {
        if(clickComponent != zpcomponent)
        {
            if(clickComponent != null)
            {
                clickComponent.setState(0);
                repaint();
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void resetMonitorThread()
    {
        monSwitch = true;
    }

    public void resetTickerButtons()
    {
        ZpComponent zpcomponent = uiShell.getComponentByDesc("ton");
        if(zpcomponent != null)
            zpcomponent.setState(0);
        for(int i = 1; i < 5; i++)
        {
            ZpComponent zpcomponent1 = uiShell.getComponentByDesc("tmode" + i);
            if(zpcomponent1 != null)
                zpcomponent1.setState(0);
        }

    }

    public void reshape(int i, int j, int k, int l)
    {
        super.reshape(i, j, k, l);
        if(tickerComponent != null)
            tickerComponent.setSize(k <= 800 ? k - 6 : 794, tickerComponent.getSize().height);
        if(aTickerPanel != null)
            aTickerPanel.setSize(tickerComponent.getSize());
    }

    public void saveProperties()
    {
        if(prof != null)
        {
            prof.setTitleState(showTitle);
            prof.setButtonState(showButtons);
            prof.saveProperties();
        }
        Vector vector = new Vector();
        if(!isVisible())
            vector.addElement(null);
        else
            vector.addElement(getLocationOnScreen());
        vector.addElement(new Long(uiState));
        vector.addElement(new Integer(tickerState ? 1 : 0));
        if(tickerSponsor != null)
            vector.addElement(tickerSponsor.getIComponent().getComponentID());
        else
            vector.addElement(null);
        vector.addElement(new Integer(iconState ? 1 : 0));
        vector.addElement(new Integer(currentTickerSpeed));
        ZCast.displayDebug("@@@ saveProperties");
        ZCast.displayDebug("@@@ currentTickerSpeed= " + currentTickerSpeed);
        ClientUI.serializeObject(vector, "pdata.obj");
        if(nzLog != null)
            nzLog.setPersistData(vector);
    }

    private void setAllTickerButtonsToOff()
    {
        for(int i = 1; i < 5; i++)
        {
            ZpComponent zpcomponent = uiShell.getComponentByDesc("tmode" + i);
            if(zpcomponent != null)
                zpcomponent.setState(0);
        }

    }

    public void setCmdProg()
    {
        cmdProg = System.getProperty("os.cmdprog");
        if(cmdProg == null)
            cmdProg = osDetect.getOsCommand();
    }

    public void setEventLogging(String s)
    {
        eventLogging = Integer.parseInt(s);
    }

    public void setExternalComponents()
    {
        uiState = (int)uiShell.getCurrentLayoutID();
        adComponent = uiShell.getComponentByDesc("ad banner");
        prevButton = uiShell.getComponentByDesc("back");
        nextButton = uiShell.getComponentByDesc("fwd");
        if(adDisplayer == null)
        {
            adDisplayer = AdDisplayer.getAdDisplayer(prevButton, nextButton, getPmiBack(), getPmiNext());
            adDisplayer.setCursor(Cursor.getPredefinedCursor(12));
            adDisplayer.addMouseListener(this);
            adDisplayer.addMouseMotionListener(this);
        }
        adDisplayer.setLocation(adComponent.getLocation());
        adDisplayer.setSize(adComponent.getSize());
        helpComponent = uiShell.getComponentByDesc("help");
        tcButton = uiShell.getComponentByDesc("cust");
        ZCast.displayDebug("setExternalComponents: tickerState is " + tickerState);
        if(tickerState)
        {
            tickerComponent = uiShell.getComponentByDesc("ticker");
            if(tickerComponent != null)
            {
                if(aTickerPanel == null)
                {
                    tickerComponent.setSize(getSize().width, tickerComponent.getSize().height);
                    initializeTickerPanel();
                }
                aTickerPanel.setLocation(tickerComponent.getLocation());
                if(currentTickerSponsorID == null)
                {
                    ZCast.displayDebug("currentTickerSponsorID is null");
                    tickerSponsor = uiShell.getComponentByDesc("tmode4");
                    currentTickerSponsorID = tickerSponsor.getIComponent().getComponentID();
                } else
                {
                    ZCast.displayDebug("currentTickerSponsorID  = " + currentTickerSponsorID);
                    tickerSponsor = uiShell.getComponentByID(currentTickerSponsorID);
                    if(tickerSponsor == null)
                    {
                        tickerSponsor = uiShell.getComponentByDesc("tmode4");
                        currentTickerSponsorID = tickerSponsor.getIComponent().getComponentID();
                    }
                }
                if(tickerSponsor == null)
                {
                    ZCast.displayDebug("tickerSponsor is null");
                } else
                {
                    if(ZCast.m_demoMode)
                        aGenerator.setUrlBase("demo/");
                    else
                        aGenerator.setUrlBase(tickerSponsor.getActionString());
                    tickerSponsor.setState(1);
                    setTickerTopic(tickerSponsor.getIComponent().getFeedID().intValue());
                    Long long1 = tickerSponsor.getIComponent().getActionObjID();
                    if(long1 != null)
                    {
                        ZpComponent zpcomponent1 = uiShell.findComponentByID(long1);
                        if(zpcomponent1 != null)
                            tickerComponent.getIComponent().setEventID(zpcomponent1.getIComponent().getEventID());
                    }
                    uiShell.getComponentByAction(2).setState(1);
                }
            }
        }
        mailComponent = uiShell.getComponentByDesc("message");
        ZpComponent zpcomponent = uiShell.getComponentByDesc("nz_icon");
        if(zpcomponent != null)
            hotSpot = zpcomponent.getBounds();
    }

    public void setHomePageUrl(String s)
    {
        homePageUrl = s;
    }

    public void setHomePageUrl(Vector vector)
    {
        homePageVector = vector;
        if(vector.size() > 0)
            launchUrl = (String)vector.elementAt(0);
        if(vector.size() > 1)
            homePageUrl = (String)vector.elementAt(1);
        if(vector.size() > 2)
            searchUrl = (String)vector.elementAt(2);
        if(vector.size() > 3)
            shopUrl = (String)vector.elementAt(3);
        if(vector.size() > 4)
            supportUrl = (String)vector.elementAt(4);
    }

    public void setIsDocked(boolean flag)
    {
        isDocked = flag;
    }

    protected void setLocations()
    {
        uiShell.setLocations(defState);
        mySize = uiShell.getShellSize();
        setExternalComponents();
        add(adDisplayer);
    }

    public void setMonitorThread(MonitorThread monitorthread)
    {
        monThread = monitorthread;
    }

    public void setNzPassword(String s)
    {
        nzPassword = s;
    }

    public void setNzUserid(String s)
    {
        nzUserid = s;
    }

    public void setOnTopDelay(int i)
    {
        onTopDelay = i;
    }

    public void setOsDetect(OSDetectNative osdetectnative)
    {
        osDetect = osdetectnative;
    }

    public void setProfile(Profile profile)
    {
        prof = profile;
    }

    public void setRasNative(RASWinNative raswinnative)
    {
        fw = raswinnative;
    }

    public void setSearchPanelLocations()
    {
        int i = getTextFieldWidth();
        if(i > 50)
        {
            searchPanel.setVisible(true);
            searchTextField.setBounds(5, 33, i, 25);
            enterSearch.setBounds(0, 0, 200, 30);
            searchButton.setLocation(i + 10, 33);
            lsLogo.setBounds(0, 55, 200, 25);
            searchPanel.setBounds(800, 0, Toolkit.getDefaultToolkit().getScreenSize().width - 800, 90);
        } else
        {
            searchPanel.setVisible(false);
        }
    }

    public void setShellState(int i)
    {
        setShellState(i, true);
    }

    public void setShellState(int i, boolean flag)
    {
        String s = null;
        uiState = i;
        if(tickerSponsor != null)
            s = tickerSponsor.getDescription();
        else
            ZCast.displayDebug("setShellState: tickerSponsor is null");
        if(i == 0)
            uiShell.setLocations(defState);
        else
            uiShell.setLocations(new Long(i));
        if(s != null)
        {
            ZpComponent zpcomponent = uiShell.getComponentByDesc(s);
            if(zpcomponent != null)
            {
                tickerSponsor = zpcomponent;
                currentTickerSponsorID = zpcomponent.getIComponent().getComponentID();
                ZCast.displayDebug("setShellState test:  currentTickerSponsorID = " + currentTickerSponsorID);
            }
        } else
        {
            ZCast.displayDebug("setShellState: tmode is null");
        }
        setExternalComponents();
        mySize = uiShell.getShellSize();
        ShellUIWrapper shelluiwrapper = uiShell.getShellWrapper();
        ContainerLayout containerlayout = (ContainerLayout)shelluiwrapper.getShellLayout(new Long(uiShell.getCurrentLayoutID())).elementAt(0);
        if(containerlayout.getLayoutDesc().indexOf("dock") != -1)
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            mySize.width = dimension.width;
        }
        setSize(mySize);
        if(flag)
        {
            Point point = getLocationOnScreen();
            adjustToScreen(point);
            setLocation(point);
        }
        repaint();
        checkMail();
    }

    public void setTickerMode(boolean flag, int i)
    {
        ZCast.displayDebug("ticker mode to " + flag + ", layout " + i);
        tickerState = flag;
        pmtiOnOff.setState(tickerState);
        if(i > 0)
            setShellState(i, IsZeroPortDocked() ^ true);
        aTickerPanel.setVisible(tickerState);
        if(IsZeroPortDocked())
            dockWindow(currentOrientation);
    }

    public void setTickerTopic(int i)
    {
        currentTopic = i;
        aGenerator.setTopic(i);
    }

    public void setupEmailAccount()
    {
        if(mailDlg != null && mailDlg.isVisible())
        {
            return;
        } else
        {
            Thread thread = new Thread() {

                public void run()
                {
                    mailDlg = new ConfigDlg2(new Frame(), nzUserid);
                    mailDlg.setModal(true);
                    ZCast.centerComponent(mailDlg);
                    mailDlg.setVisible(true);
                }

            };
            thread.start();
            return;
        }
    }

    private void showSearchDialog(ZpComponent zpcomponent)
    {
        try
        {
            if(SearchDialog.getIsVisible())
                return;
            ZpContainer zpcontainer = uiShell.getContainerByDesc("search panel");
            if(zpcontainer == null)
            {
                String s = null;
                if(zpcomponent != null)
                    s = zpcomponent.getActionString();
                if(s != null)
                    showAdUrl(s);
            } else
            {
                Point point = getLocationOnScreen();
                SearchDialog searchdialog = new SearchDialog(parentFrame, "NetZero Search", false, uiShell, this);
                searchdialog.setContainer(zpcontainer);
                searchdialog.pack();
                centerDialog(searchdialog);
                searchdialog.setVisible(true);
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug("Exception " + exception);
        }
    }

    private void showAbout()
    {
        Point point = getLocationOnScreen();
        AboutDialog aboutdialog = new AboutDialog(new Frame(), false);
        centerDialog(aboutdialog);
        aboutdialog.setVisible(true);
    }

    public void showAdUrl(final String urlName)
    {
        if(urlName.trim().equals(""))
        {
            ZCast.displayDebug("no url");
            return;
        } else
        {
            (new Thread() {

                public void run()
                {
                    OSDetectNative.showUrl(urlName);
                }

            }).start();
            return;
        }
    }

    public void showBrowse()
    {
        if(BrowseDialog.visible())
        {
            return;
        } else
        {
            BrowseDialog browsedialog = new BrowseDialog(new Frame(), homePageVector);
            ZCast.centerComponent(browsedialog);
            browsedialog.setVisible(true);
            return;
        }
    }

    public void showChangeOfEmailAddressKit()
    {
        AddressDlg.showDlg(nzUserid);
    }

    private void showContextMenu(final int x, int i)
    {
        isPopupShowing = true;
        menuThread = i. new Thread() {

            public void run()
            {
                ZCast.displayDebug("starting another thread for menu ");
                enableOrientationItems();
                synchronized(getLockForFloat())
                {
                    popupMenu1.show(NZWindow.me, x, y);
                }
            }

        };
        menuThread.start();
    }

    public void showMail()
    {
        if(ZCast.getHasEmailClient())
        {
            MailClient.startEmail(this, nzUserid);
            if(mailComponent.getState() == 1)
            {
                mailComponent.setState(0);
                repaint();
            }
        } else
        {
            String as[] = {
                "ok"
            };
            NZDialogBox.showMessageDialog(resNZResource.getString("No_Default_Email_Client"), resNZResource.getString("Unable_to_set_the_default_"), 3, as);
        }
    }

    public void showProfile()
    {
        if(ZCast.m_demoMode)
        {
            OSDetectNative.showUrl("file:demo/clubzero/index.htm");
            return;
        }
        try
        {
            String s = ServerParms.getParm("UpdateProfileURL", "https://gold.netzero.net/servlets/updateprofile?");
            if(s != null)
            {
                int i = s.indexOf("https://");
                if(i == -1)
                    i = s.indexOf("http://") + 7;
                else
                    i += 8;
                showAdUrl(s.substring(0, i) + "\"" + s.substring(i) + "&MemberID=" + getNzUserid() + "\"");
            } else
            {
                String as[] = new String[1];
                as[0] = "ok";
                String s1 = resNZResource.getString("Error");
                String s2 = resNZResource.getString("We_could_not_update_your_p") + resNZResource.getString("Please_restart_the_applica");
                NZDialogBox.showMessageDialog(s1, s2, 2, as);
            }
        }
        catch(Throwable throwable)
        {
            ZCast.displayDebug(throwable);
            ZCast.displayDebug(throwable);
        }
    }

    private void showSearch()
    {
        showAdUrl(searchUrl);
        requestFocus();
    }

    public void showSponsorMenu(ZpComponent zpcomponent, ZpContainer zpcontainer)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Point point = getLocationOnScreen();
        Dimension dimension1 = zpcontainer.getSize();
        Point point1 = new Point(point.x + zpcomponent.getLocation().x, point.y + zpcomponent.getLocation().y);
        if(point1.x + dimension1.width > dimension.width)
            point1.x = (point.x + zpcomponent.getLocation().x + zpcomponent.getSize().width) - dimension1.width;
        if(point1.y + dimension1.height > dimension.height)
            point1.y -= (point1.y + dimension1.height) - dimension.height;
        sponsorMenu.show(zpcontainer, point1.x, point1.y);
    }

    public void showTickerCustomization()
    {
        if(customizeActive)
            return;
        customizeActive = true;
        String s = System.getProperty("nz.fake");
        if(s != null)
            TickerTransactions.nzFake = true;
        Vector vector = TickerTransactions.fetchTickerInformation(new FetchRequestImpl());
        if(vector == null)
        {
            customizeActive = false;
            return;
        } else
        {
            CustomizationDialog customizationdialog = new CustomizationDialog(new Frame(), vector);
            customizationdialog.setModal(true);
            customizationdialog.setTitle(resNZResource.getString("ZeroPort_Ticker_Customizat"));
            ZCast.centerComponent(customizationdialog);
            customizationdialog.setVisible(true);
            customizeActive = false;
            return;
        }
    }

    public void slowDownTicker(ZpComponent zpcomponent)
    {
        if(currentTickerSpeed > 0)
        {
            currentTickerSpeed--;
            if(zpcomponent != null)
                zpcomponent.setState(currentTickerSpeed);
            subMenu.repaint();
        }
        aTickerPanel.speedDown();
    }

    public void speedUpTicker(ZpComponent zpcomponent)
    {
        if(currentTickerSpeed < 4)
        {
            currentTickerSpeed++;
            if(zpcomponent != null)
                zpcomponent.setState(currentTickerSpeed);
            subMenu.repaint();
        }
        aTickerPanel.speedUp();
    }

    public void terminateProgram(String s)
    {
        ZCast.m_emergencyExit = true;
        ZCast.terminateProgram(26, s);
    }

    public void undockWindow(String s)
    {
        if(s == "up" || s == "down")
            currentOrientation = -1;
        else
            currentOrientation = -2;
        String s1 = s;
        if(uiShell != null)
            setShellState((int)uiShell.getLayoutIdByDesc(s));
        else
            ZCast.displayDebug("@@@@ Undock wind: TEST B");
        if(osDetect != null)
            OSDetectNative.undock();
        else
            ZCast.displayDebug("@@@@ Undock wind: TEST C");
        setIsDocked(false);
        if(s == "up" || s == "up-icon")
            resetTickerButtons();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Point point = getLocation();
        int i = getSize().height;
        if(point.y > dimension.height / 2)
        {
            point.y = point.y - 1;
            setLocation(point);
        } else
        {
            point.y = point.y + 1;
            setLocation(point);
        }
        pmmPosition.setEnabled(true);
    }

    public void update(Graphics g)
    {
        Rectangle rectangle = g.getClipBounds();
        Rectangle rectangle1 = adDisplayer.getBounds();
        if(rectangle1.contains(rectangle.x, rectangle.y))
            fromUpdate = true;
        paint(g);
        fromUpdate = false;
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        if(windowevent.getSource() == this)
            conn0(windowevent);
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private boolean alwaysTrue;
    private boolean useBookmarks;
    private boolean transMessages;
    private Point dragPoint;
    private int frameWidth;
    private int titleHeight;
    private int onTopDelay;
    private int lastIndex;
    private int sizeMode;
    private Rectangle hotSpot;
    public Frame parentFrame;
    private int moveX;
    private int moveY;
    private Point dragPt;
    private Dimension mySize;
    private boolean rejectFocus;
    private boolean skipNextPressed;
    private boolean showDrag;
    private boolean showTitle;
    private boolean showButtons;
    private boolean suppressOnTop;
    private static String cmdProg;
    private static String cmdLine = "";
    private RASWinNative fw;
    private static OSDetectNative osDetect;
    private Image sysImage;
    private Image textImage;
    private Image titleHoverImage;
    private Image lookSmartStart;
    private Image lookSmartSearch;
    private NZUserLog nzLog;
    private String launchUrl;
    private String homePageUrl;
    private String shopUrl;
    private String searchUrl;
    private String compaqUrl;
    private String supportUrl;
    public boolean monSwitch;
    public int eventLogging;
    private int curEventCt;
    private String adNames[];
    private String adUrls[];
    private SequentialPlayList plist;
    private MonitorThread monThread;
    private Profile prof;
    private boolean showCompaq;
    private AdDisplayer adDisplayer;
    protected String nzUserid;
    protected String nzPassword;
    protected PopupMenu popupMenu1;
    protected Menu pmmView;
    protected Menu pmmBrowser;
    protected CheckboxMenuItem pmviNewBrowser;
    protected Menu pmmPrint;
    protected MenuItem pmpiAd;
    protected MenuItem pmpiCoupon;
    protected Menu pmmBookmarks;
    protected MenuItem pmiBack;
    protected MenuItem pmiNext;
    protected Menu pmmSupport;
    protected MenuItem pmisNet;
    protected MenuItem pmisFeed;
    protected MenuItem pmiProfile;
    protected MenuItem pmiNetzero;
    protected Menu pmmHelp;
    protected MenuItem pmhiAbout;
    protected MenuItem pmhiTutorial;
    protected MenuItem pmiExit;
    protected Menu pmmPosition;
    protected MenuItem pmpiTopLeft;
    protected MenuItem pmpiTopRight;
    protected MenuItem pmpiBottomLeft;
    protected MenuItem pmpiBottomRight;
    protected Menu pmmTicker;
    protected CheckboxMenuItem pmtiOnOff;
    protected MenuItem pmtiCustomize;
    protected Menu pmmEmail;
    protected MenuItem pmemCheck;
    protected MenuItem pmemLaunch;
    protected MenuItem pmemConfig;
    protected MenuItem pmemCOEAK;
    protected MenuItem pmtiDockTop;
    protected MenuItem pmtiDockBottom;
    protected MenuItem pmtiStandard;
    protected MenuItem pmtiIcons;
    private ZpShell uiShell;
    private Panel ivjContentsPane;
    private NzContainer currentContainer;
    private NzComponent currentComponent;
    private MediaTracker mt;
    private boolean knowWidth;
    private boolean knowHeight;
    private int zpWidth;
    private int zpHeight;
    private int zpx;
    private int zpy;
    private int nextx;
    private int nexty;
    private boolean setNext;
    private boolean useNext;
    private int nImages;
    private int maxImages;
    private Image zpImages[];
    private boolean sizeKnown;
    private boolean imagesLoaded;
    private Rectangle grabRect;
    private ZpComponent prevButton;
    private ZpComponent nextButton;
    private ZpComponent tcButton;
    private ZpComponent tickerComponent;
    private ZpComponent tickerSponsor;
    private ImageGenerator aGenerator;
    private DataRetriever aRetriever;
    private TickerPanel aTickerPanel;
    private int currentTopic;
    private ZpHover zphw;
    private ZpComponent hoverComponent;
    private ZpComponent clickComponent;
    private ZpMenu sponsorMenu;
    private ZpComponent mailComponent;
    private ZpComponent helpComponent;
    private ZpComponent cyComponent;
    private int iCy;
    private String cy0;
    private String cy1;
    private MailClient mClient;
    private int uiState;
    private Long defState;
    private boolean tickerState;
    private boolean iconState;
    private boolean customizeActive;
    private Long currentTickerSponsorID;
    private int tickerSpeed;
    private Vector homePageVector;
    private ZpComponent adComponent;
    private boolean fromUpdate;
    private Hashtable table;
    private ConfigDlg2 mailDlg;
    private boolean hasPressed;
    private ZpSubMenu subMenu;
    private int currentOrientation;
    private int currentTickerSpeed;
    private boolean isDocked;
    public static int ZEROPORT_WIDTH = 800;
    private TextField searchTextField;
    private NZButton searchButton;
    private Panel searchPanel;
    private JCLabel enterSearch;
    private JCLabel lsLogo;
    protected static boolean isPopupShowing = false;
    private static NZWindow me = null;
    private static Thread menuThread = null;
    private static Object lockForFloat = new Object();
    private ZpComponent searchButtonComponent;








}
