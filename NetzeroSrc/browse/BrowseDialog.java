// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BrowseDialog.java

package browse;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import jclass.bwt.*;
import jclass.util.JCString;
import nzcom.*;

// Referenced classes of package browse:
//            BrowseHttpRequest

public class BrowseDialog extends Dialog
    implements WindowListener, ActionListener
{
    class KeyEventHandler extends KeyAdapter
    {

        public void keyPressed(KeyEvent keyevent)
        {
            if(keyevent.getKeyCode() == 10)
            {
                keyevent.consume();
                String s = getTextField1().getText().trim();
                if(s.length() > 0)
                    handleBrowseString(s);
            }
        }

        KeyEventHandler()
        {
        }
    }


    public BrowseDialog(Frame frame)
    {
        super(frame);
        urls = null;
        ivjJCLabel2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        GoButton = null;
        ivjTextField1 = null;
        ivjContentsPane = null;
        ag = null;
        initialize();
    }

    public BrowseDialog(Frame frame, String s)
    {
        super(frame, s);
        urls = null;
        ivjJCLabel2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        GoButton = null;
        ivjTextField1 = null;
        ivjContentsPane = null;
        ag = null;
        initialize();
    }

    public BrowseDialog(Frame frame, String s, boolean flag)
    {
        super(frame, s, flag);
        urls = null;
        ivjJCLabel2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        GoButton = null;
        ivjTextField1 = null;
        ivjContentsPane = null;
        ag = null;
        initialize();
    }

    public BrowseDialog(Frame frame, Vector vector)
    {
        super(frame);
        urls = null;
        ivjJCLabel2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        GoButton = null;
        ivjTextField1 = null;
        ivjContentsPane = null;
        ag = null;
        setUrls(vector);
        initialize();
    }

    public BrowseDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        urls = null;
        ivjJCLabel2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        GoButton = null;
        ivjTextField1 = null;
        ivjContentsPane = null;
        ag = null;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = getTextField1().getText().trim();
        if(s.length() > 0)
            handleBrowseString(s);
    }

    private void connEtoC1(WindowEvent windowevent)
    {
        try
        {
            visible = false;
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private AgComponent getAg()
    {
        ag = new AgComponent();
        ag.initialize(null, "images/powered_by.gif");
        ag.setLocation(202, 52);
        return ag;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                ivjContentsPane.setBackground(NZColor.BACKGROUND);
                getContentsPane().add(getJCGroupBox1(), getJCGroupBox1().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private NZButton getGoButton()
    {
        if(GoButton == null)
            try
            {
                GoButton = new NZButton("Go!");
                GoButton.setLocation(298, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return GoButton;
    }

    private JCGroupBox getJCGroupBox1()
    {
        if(ivjJCGroupBox1 == null)
            try
            {
                ivjJCGroupBox1 = new JCGroupBox();
                ivjJCGroupBox1.setName("JCGroupBox1");
                ivjJCGroupBox1.setLayout(null);
                ivjJCGroupBox1.setShadowThickness(2);
                ivjJCGroupBox1.setBounds(8, 8, 355, 108);
                getJCGroupBox1().add(getTextField1(), getTextField1().getName());
                getJCGroupBox1().add(getGoButton(), getGoButton().getName());
                ivjJCGroupBox1.setInsets(new Insets(0, 0, 0, 0));
                JCString jcstring = JCString.parse(this, "[IMG=images/enter_keyword.gif][ALIGN=TOP]");
                ivjJCGroupBox1.setTitle(jcstring);
                ivjJCGroupBox1.setFont(new Font("Arial", 1, 13));
                NZLabel nzlabel = new NZLabel("Type in a name, brand, or\ncompany name to go directly\nto the site, or to a list of web sites\nthat match your query.");
                getJCGroupBox1().add(nzlabel);
                nzlabel.setFont(new Font("Arial", 0, 10));
                nzlabel.setLocation(7, 45);
                nzlabel.setSize(180, 55);
                ivjJCGroupBox1.add(getAg());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox1;
    }

    private TextField getTextField1()
    {
        if(ivjTextField1 == null)
            try
            {
                ivjTextField1 = new TextField();
                ivjTextField1.setName("TextField1");
                ivjTextField1.setBackground(NZColor.TEXT_BACKGROUND);
                ivjTextField1.setLocation(10, 20);
                ivjTextField1.setSize(280, 23);
                ivjTextField1.setForeground(NZColor.TEXT);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField1;
    }

    private void handleBrowseString(String s)
    {
        final String browse = s;
        Thread thread = new Thread() {

            public void run()
            {
                ZCast.displayDebug("browse string: " + browse);
                if(browse.startsWith("http://") || browse.startsWith("www."))
                {
                    Initializer.m_zwindow.showAdUrl(browse);
                } else
                {
                    if(request == null)
                        request = new BrowseHttpRequest(urls);
                    request.gotoRealName(browse);
                }
            }

        };
        thread.start();
        visible = false;
        dispose();
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        getGoButton().addActionListener(this);
        getTextField1().addKeyListener(new KeyEventHandler());
        addWindowListener(this);
    }

    private void initialize()
    {
        visible = true;
        setName("BrowseDialog");
        setResizable(false);
        setLayout(new BorderLayout());
        setBackground(NZColor.BACKGROUND);
        setSize(370, 143);
        setTitle(resNZResource.getString("NetZero_Browse"));
        add(getContentsPane(), "Center");
        initConnections();
        ZCast.displayDebug("number = " + Initializer.m_userNumber);
        ZCast.displayDebug("version = " + ZCast.getZcastVersion());
    }

    public static void main(String args[])
    {
        if(visible())
        {
            return;
        } else
        {
            BrowseDialog browsedialog = new BrowseDialog(new Frame());
            ZCast.centerComponent(browsedialog);
            browsedialog.setVisible(true);
            return;
        }
    }

    public void setUrls(Vector vector)
    {
        urls = vector;
    }

    public static boolean visible()
    {
        return visible;
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
            connEtoC1(windowevent);
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
        OSDetectNative.updateProcessIcons();
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private BrowseHttpRequest request;
    private static boolean visible = false;
    private Vector urls;
    private JCLabel ivjJCLabel2;
    private JCGroupBox ivjJCGroupBox1;
    private JCLabel ivjJCLabel1;
    private NZButton GoButton;
    private TextField ivjTextField1;
    private Panel ivjContentsPane;
    private AgComponent ag;






}
