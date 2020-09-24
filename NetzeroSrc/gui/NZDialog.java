// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZDialog.java

package gui;

import java.awt.*;
import java.awt.event.*;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButtonPanel

public abstract class NZDialog extends Dialog
    implements ActionListener, WindowListener
{

    public NZDialog(Frame frame)
    {
        super(frame);
        ivjContentsPane = null;
        MainPanel = null;
        ButtonPanel = null;
        initialize();
    }

    public NZDialog(Frame frame, String as[])
    {
        super(frame);
        ivjContentsPane = null;
        MainPanel = null;
        ButtonPanel = null;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        ZCast.displayDebug("in the original base, e is " + actionevent);
    }

    protected NZButtonPanel getButtonPanel()
    {
        return ButtonPanel;
    }

    protected Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
    }

    private void initialize()
    {
        setName("NZDialog");
        setLayout(new BorderLayout());
        setSize(475, 380);
        add(getContentsPane());
        ButtonPanel = new NZButtonPanel();
        getContentsPane().add(ButtonPanel);
        MainPanel = makeMainPanel();
        getContentsPane().add(MainPanel);
        initConnections();
    }

    protected abstract Panel makeMainPanel();

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        dispose();
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

    protected Panel ivjContentsPane;
    protected Panel MainPanel;
    protected NZButtonPanel ButtonPanel;
}
