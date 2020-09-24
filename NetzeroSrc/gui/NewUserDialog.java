// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewUserDialog.java

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;
import jclass.bwt.JCLabel;
import nzcom.OSDetectNative;

public class NewUserDialog extends Dialog
    implements ActionListener, WindowListener
{

    public NewUserDialog(Frame frame)
    {
        super(frame);
        ivjButton1 = null;
        ivjButton2 = null;
        ivjContentsPane = null;
        ivjJCLabel1 = null;
        ivjLabel1 = null;
        ivjTextField1 = null;
        cancelStatus = false;
        initialize();
    }

    public NewUserDialog(Frame frame, String s)
    {
        super(frame, s);
        ivjButton1 = null;
        ivjButton2 = null;
        ivjContentsPane = null;
        ivjJCLabel1 = null;
        ivjLabel1 = null;
        ivjTextField1 = null;
        cancelStatus = false;
    }

    public NewUserDialog(Frame frame, String s, boolean flag)
    {
        super(frame, s, flag);
        ivjButton1 = null;
        ivjButton2 = null;
        ivjContentsPane = null;
        ivjJCLabel1 = null;
        ivjLabel1 = null;
        ivjTextField1 = null;
        cancelStatus = false;
    }

    public NewUserDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        ivjButton1 = null;
        ivjButton2 = null;
        ivjContentsPane = null;
        ivjJCLabel1 = null;
        ivjLabel1 = null;
        ivjTextField1 = null;
        cancelStatus = false;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getButton2())
            connEtoM1(actionevent);
        if(actionevent.getSource() == getButton1())
            connEtoM2(actionevent);
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
            cancelStatus = true;
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoM2(ActionEvent actionevent)
    {
        try
        {
            cancelStatus = false;
            userid = getTextField1().getText();
            dispose();
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
                ivjButton1.setBounds(26, 116, 56, 23);
                ivjButton1.setLabel(resNZResource.getString("Submit"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton1;
    }

    private Button getButton2()
    {
        if(ivjButton2 == null)
            try
            {
                ivjButton2 = new Button();
                ivjButton2.setName("Button2");
                ivjButton2.setBounds(288, 117, 56, 23);
                ivjButton2.setLabel(resNZResource.getString("Cancel"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton2;
    }

    public boolean getCancelStatus()
    {
        return cancelStatus;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                ivjContentsPane.setBackground(Color.white);
                ivjContentsPane.add(getJCLabel1(), getJCLabel1().getName());
                ivjContentsPane.add(getLabel1(), getLabel1().getName());
                ivjContentsPane.add(getTextField1(), getTextField1().getName());
                ivjContentsPane.add(getButton1(), getButton1().getName());
                ivjContentsPane.add(getButton2(), getButton2().getName());
                ivjContentsPane.setBackground(SystemColor.control);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private JCLabel getJCLabel1()
    {
        if(ivjJCLabel1 == null)
            try
            {
                ivjJCLabel1 = new JCLabel();
                ivjJCLabel1.setName("JCLabel1");
                ivjJCLabel1.setAlignment(0);
                ivjJCLabel1.setBounds(17, 9, 334, 55);
                ivjJCLabel1.setLabel(resNZResource.getString("The_member_ID_you_selected"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCLabel1;
    }

    private Label getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new Label();
                ivjLabel1.setName("Label1");
                ivjLabel1.setFont(new Font("dialog", 1, 12));
                ivjLabel1.setText(resNZResource.getString("Enter_alternate_member_ID"));
                ivjLabel1.setBounds(19, 77, 156, 26);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    private TextField getTextField1()
    {
        if(ivjTextField1 == null)
            try
            {
                ivjTextField1 = new TextField();
                ivjTextField1.setName("TextField1");
                ivjTextField1.setBackground(Color.white);
                ivjTextField1.setBounds(178, 76, 171, 27);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField1;
    }

    public String getUserid()
    {
        return userid;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getButton2().addActionListener(this);
        getButton1().addActionListener(this);
    }

    private void initialize()
    {
        setName("NewUserDialog");
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(385, 172);
        setModal(true);
        setTitle(resNZResource.getString("NetZero_member_ID_"));
        add(getContentsPane(), "Center");
        initConnections();
    }

    public static void main(String args[])
    {
        try
        {
            NewUserDialog newuserdialog = new NewUserDialog(new Frame());
            newuserdialog.setModal(true);
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    newuserdialog
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
            newuserdialog.setVisible(true);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    public void setCancelStatus(boolean flag)
    {
        cancelStatus = flag;
    }

    public void setUserid(String s)
    {
        userid = s;
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

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private Button ivjButton1;
    private Button ivjButton2;
    private Panel ivjContentsPane;
    private JCLabel ivjJCLabel1;
    private Label ivjLabel1;
    private TextField ivjTextField1;
    public boolean cancelStatus;
    public String userid;
    static Class class$java$awt$Window; /* synthetic field */

}
