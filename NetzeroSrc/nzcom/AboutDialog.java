// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AboutDialog.java

package nzcom;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

// Referenced classes of package nzcom:
//            OSDetectNative, ZCast

class AboutDialog extends Dialog
    implements ActionListener, WindowListener
{

    public AboutDialog(Frame frame)
    {
        super(frame);
        ivjButton1 = null;
        ivjContentsPane = null;
        ag = null;
        ButtonPanel = null;
        initialize();
    }

    public AboutDialog(Frame frame, String s)
    {
        super(frame, s);
        ivjButton1 = null;
        ivjContentsPane = null;
        ag = null;
        ButtonPanel = null;
    }

    public AboutDialog(Frame frame, String s, boolean flag)
    {
        super(frame, s, flag);
        ivjButton1 = null;
        ivjContentsPane = null;
        ag = null;
        ButtonPanel = null;
    }

    public AboutDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        ivjButton1 = null;
        ivjContentsPane = null;
        ag = null;
        ButtonPanel = null;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getButton1())
            conn1(actionevent);
    }

    private void conn0(WindowEvent windowevent)
    {
        try
        {
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void conn1(ActionEvent actionevent)
    {
        try
        {
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
        ag.initialize(null, "images/about_nz.gif");
        ag.setLocation(7, 27);
        return ag;
    }

    private NZButton getButton1()
    {
        if(ivjButton1 == null)
            try
            {
                ivjButton1 = new NZButton("ok");
                ivjButton1.setLocation(454, 236);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton1;
    }

    private NZButtonPanel getButtonPanel()
    {
        NZButton anzbutton[] = new NZButton[1];
        anzbutton[0] = getButton1();
        ButtonPanel = new NZButtonPanel(anzbutton, 1);
        ButtonPanel.setLocation(5, 260);
        return ButtonPanel;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setFont(new Font("dialog", 1, 12));
                ivjContentsPane.setLayout(null);
                ivjContentsPane.add(getAg());
                ivjContentsPane.setBounds(0, 0, 532, 333);
                ivjContentsPane.add(getButtonPanel());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        addWindowListener(this);
        getButton1().addActionListener(this);
    }

    private void initialize()
    {
        setName("AboutDialog");
        setLayout(null);
        setModal(false);
        setTitle("NetZero About Dialog");
        add(getContentsPane(), getContentsPane().getName());
        int i = ag.getImage().getWidth(this);
        int j = ag.getImage().getHeight(this);
        setSize(i + 14, j + 75);
        ButtonPanel.setLocation(7, j + 32);
        ButtonPanel.setWidth(i + 4);
        initConnections();
        setResizable(false);
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
        OSDetectNative.updateProcessIcons();
    }

    private NZButton ivjButton1;
    private Panel ivjContentsPane;
    private AgComponent ag;
    private NZButtonPanel ButtonPanel;
}
