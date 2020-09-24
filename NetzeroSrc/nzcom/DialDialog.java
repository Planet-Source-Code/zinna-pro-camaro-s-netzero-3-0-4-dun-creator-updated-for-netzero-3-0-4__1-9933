// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialDialog.java

package nzcom;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;

// Referenced classes of package nzcom:
//            OSDetectNative, ZCast, RASWinNative

public class DialDialog extends Dialog
    implements ActionListener, WindowListener
{

    public DialDialog(Frame frame)
    {
        super(frame);
        ivjButton1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        wn = null;
        connStatus = 0;
        initialize();
    }

    public DialDialog(Frame frame, String s)
    {
        super(frame, s);
        ivjButton1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        wn = null;
        connStatus = 0;
        dialName = s;
        initialize();
    }

    public DialDialog(Frame frame, String s, boolean flag)
    {
        super(frame, s, flag);
        ivjButton1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        wn = null;
        connStatus = 0;
    }

    public DialDialog(Frame frame, RASWinNative raswinnative, String s)
    {
        super(frame, resNZResource.getString("Connecting_to_") + s);
        ivjButton1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        wn = null;
        connStatus = 0;
        wn = raswinnative;
        dialName = s;
        initialize();
    }

    public DialDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        ivjButton1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        wn = null;
        connStatus = 0;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getButton1())
            connEtoM1(actionevent);
        ZCast.m_emergencyExit = true;
        ZCast.terminateProgram(5, null);
    }

    private void connEtoC1(WindowEvent windowevent)
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

    private void connEtoM1(ActionEvent actionevent)
    {
        try
        {
            setCancelStatus(true);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private Button getButton1()
    {
        if(ivjButton1 == null)
            try
            {
                ivjButton1 = new Button();
                ivjButton1.setName("Button1");
                ivjButton1.setBounds(213, 30, 51, 20);
                ivjButton1.setLabel(resNZResource.getString("Cancel"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton1;
    }

    public boolean getCancelStatus()
    {
        return cancelStatus;
    }

    public int getConnStatus()
    {
        return connStatus;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                getContentsPane().add(getLabel1(), getLabel1().getName());
                getContentsPane().add(getLabel2(), getLabel2().getName());
                getContentsPane().add(getButton1(), getButton1().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private Label getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new Label();
                ivjLabel1.setName("Label1");
                ivjLabel1.setText("");
                ivjLabel1.setBounds(21, 10, 258, 15);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    private Label getLabel2()
    {
        if(ivjLabel2 == null)
            try
            {
                ivjLabel2 = new Label();
                ivjLabel2.setName("Label2");
                ivjLabel2.setText("");
                ivjLabel2.setBounds(21, 27, 190, 15);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel2;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getButton1().addActionListener(this);
    }

    private void initialize()
    {
        setName("DialDialog");
        setResizable(false);
        setFont(new Font("dialog", 1, 10));
        setLayout(new BorderLayout());
        setSize(297, 77);
        add(getContentsPane(), "Center");
        initConnections();
        ZCast.centerComponent(this);
    }

    public static void main(String args[])
    {
        try
        {
            DialDialog dialdialog = new DialDialog(new Frame());
            dialdialog.setModal(true);
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    dialdialog
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
            dialdialog.setVisible(true);
            dialdialog.getLabel1().setText("just testing");
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    public void setButton1Status(boolean flag)
    {
        getButton1().setEnabled(flag);
        getButton1().setVisible(flag);
    }

    public void setCancelStatus(boolean flag)
    {
        cancelStatus = flag;
    }

    public void setLabel1Text(String s)
    {
        getLabel1().setText(s);
        getLabel2().setText("");
    }

    public void setLabel2Text(String s)
    {
        getLabel2().setText(s);
    }

    public void setLabelText(String s)
    {
        getLabel1().setText(s);
        getLabel2().setText("");
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

    static Class class$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private boolean cancelStatus;
    private Button ivjButton1;
    private Panel ivjContentsPane;
    private Label ivjLabel1;
    private Label ivjLabel2;
    private RASWinNative wn;
    private int connStatus;
    private String dialName;
    static Class class$java$awt$Window; /* synthetic field */

}
