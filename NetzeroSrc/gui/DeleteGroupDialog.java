// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeleteGroupDialog.java

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButton, DialGroups, NZLabel, NZFont

public class DeleteGroupDialog extends Dialog
    implements ActionListener, WindowListener
{

    public DeleteGroupDialog(Frame frame)
    {
        super(frame);
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjList1 = null;
        ivjNoButton = null;
        ivjYesButton = null;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getNoButton())
            connEtoC2();
        if(actionevent.getSource() == getYesButton())
            connEtoC3();
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

    private void connEtoC2()
    {
        try
        {
            noButton_ActionEvents();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoC3()
    {
        try
        {
            yesButton_ActionEvents();
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
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public static boolean delete()
    {
        return delete;
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
                getContentsPane().add(getYesButton(), getYesButton().getName());
                getContentsPane().add(getNoButton(), getNoButton().getName());
                getContentsPane().add(getList1(), getList1().getName());
                ivjContentsPane.setFont(new NZFont());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private NZLabel getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new NZLabel(resNZResource.getString("Are_you_sure_you_want_to_d") + DialGroups.getCurrentGroupName() + resNZResource.getString("'_with_the_following_phone"));
                ivjLabel1.setName("Label1");
                ivjLabel1.setBounds(7, 6, 314, 35);
                ivjLabel1 = new NZLabel(resNZResource.getString("Are_you_sure_you_want_to_d") + DialGroups.getCurrentGroupName() + resNZResource.getString("'_with_the_following_phone"));
                ivjLabel1.setName("Label1");
                ivjLabel1.setBounds(7, 6, 314, 35);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    private List getList1()
    {
        if(ivjList1 == null)
            try
            {
                ivjList1 = new List();
                ivjList1.setName("List1");
                ivjList1.setBounds(9, 45, 253, 138);
                DialGroups.setFinalPhoneList(ivjList1);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjList1;
    }

    private NZButton getNoButton()
    {
        if(ivjNoButton == null)
            try
            {
                ivjNoButton = new NZButton("no");
                ivjNoButton.setLocation(162, 195);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjNoButton;
    }

    private NZButton getYesButton()
    {
        if(ivjYesButton == null)
            try
            {
                ivjYesButton = new NZButton("yes");
                ivjYesButton.setLocation(53, 195);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjYesButton;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getNoButton().addActionListener(this);
        getYesButton().addActionListener(this);
    }

    private void initialize()
    {
        setName("DeleteGroupDialog");
        setLayout(new BorderLayout());
        setSize(274, 250);
        add(getContentsPane(), "Center");
        initConnections();
        ZCast.centerComponent(this);
    }

    public static void main(String args[])
    {
        try
        {
            DialGroups.readGroups();
            DialGroups.buildTestGroup();
            DeleteGroupDialog deletegroupdialog = new DeleteGroupDialog(new Frame());
            deletegroupdialog.setModal(true);
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    deletegroupdialog
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
            deletegroupdialog.setVisible(true);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    public void noButton_ActionEvents()
    {
        delete = false;
        dispose();
    }

    public static void showDialog()
    {
        DeleteGroupDialog deletegroupdialog = new DeleteGroupDialog(new Frame());
        delete = false;
        deletegroupdialog.setTitle(resNZResource.getString("Delete_Phone_List_'") + DialGroups.getCurrentGroupName() + "'");
        deletegroupdialog.setModal(true);
        deletegroupdialog.setVisible(true);
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
    }

    public void yesButton_ActionEvents()
    {
        delete = true;
        dispose();
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

    private static java.util.ResourceBundle resNZResource = java.util.ResourceBundle.getBundle("gui.NZResource");
    private Panel ivjContentsPane;
    private NZLabel ivjLabel1;
    private List ivjList1;
    private NZButton ivjNoButton;
    private NZButton ivjYesButton;
    private static boolean delete = false;
    static Class class$java$awt$Window; /* synthetic field */

}
