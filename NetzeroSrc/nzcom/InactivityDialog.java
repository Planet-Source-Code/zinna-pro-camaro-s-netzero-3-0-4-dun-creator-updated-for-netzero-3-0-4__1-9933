// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InactivityDialog.java

package nzcom;

import gui.NZButton;
import gui.NZLabel;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;
import jclass.bwt.JCLabel;

// Referenced classes of package nzcom:
//            OSDetectNative, MonitorThread, ServerParms

public class InactivityDialog extends Dialog
    implements ActionListener, WindowListener
{

    public InactivityDialog(Frame frame)
    {
        super(frame);
        ivjButton1 = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        ivjLabel3 = null;
        ivjLabel4 = null;
        ivjLabel5 = null;
        remainingSeconds = Integer.parseInt(ServerParms.getParm("ResumeDialogDisplaySeconds", "60"));
        resume = false;
        setModal(false);
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
            resume = true;
            setVisible(false);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void countDown(int i)
    {
        remainingSeconds = i;
        resume = false;
        setVisible(true);
        while(remainingSeconds > 0 && !resume) 
        {
            setRemainingSeconds(remainingSeconds);
            try
            {
                wait(1000L);
                remainingSeconds--;
            }
            catch(InterruptedException _ex) { }
        }
        setVisible(false);
    }

    private NZButton getButton1()
    {
        if(ivjButton1 == null)
            try
            {
                ivjButton1 = new NZButton("resume");
                int i = getSize().width / 2 - ivjButton1.getSize().width / 2;
                byte byte0 = 125;
                ivjButton1.setLocation(i, byte0);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton1;
    }

    private Label getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new Label();
                ivjLabel1.setName("Label1");
                ivjLabel1.setFont(new Font("dialog", 1, 14));
                ivjLabel1.setAlignment(1);
                ivjLabel1.setText(resNZResource.getString("WARNING!"));
                ivjLabel1.setBounds(140, 24, 125, 30);
                ivjLabel1.setForeground(Color.red);
                int i = getSize().width / 2 - ivjLabel1.getSize().width / 2;
                byte byte0 = 22;
                ivjLabel1.setLocation(i, byte0);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    private NZLabel getLabel2()
    {
        if(ivjLabel2 == null)
            try
            {
                int i = MonitorThread.getMaxWaitMin();
                ivjLabel2 = new NZLabel("Your connection is about to be terminated.  To remain\nconnected, click on the RESUME button below.");
                ivjLabel2.setBounds(5, 48, 450, 50);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel2;
    }

    private Label getLabel3()
    {
        if(ivjLabel3 == null)
            try
            {
                ivjLabel3 = new Label();
                ivjLabel3.setName("Label3");
                ivjLabel3.setText(resNZResource.getString("Please_push_the_RESUME_but"));
                ivjLabel3.setBounds(27, 71, 334, 28);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel3;
    }

    private Label getLabel4()
    {
        if(ivjLabel4 == null)
            try
            {
                ivjLabel4 = new Label();
                ivjLabel4.setName("Label4");
                ivjLabel4.setFont(new Font("dialog", 1, 14));
                ivjLabel4.setAlignment(1);
                ivjLabel4.setText("");
                ivjLabel4.setBounds(5, 101, 26, 21);
                ivjLabel4.setForeground(Color.red);
                ivjLabel4.setText(String.valueOf(remainingSeconds));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel4;
    }

    private NZLabel getLabel5()
    {
        if(ivjLabel5 == null)
            try
            {
                ivjLabel5 = new NZLabel();
                ivjLabel5.setText(resNZResource.getString("Seconds_remaining_before_t"));
                ivjLabel5.setBounds(25, 102, 230, 24);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel5;
    }

    public int getRemainingSeconds()
    {
        return remainingSeconds;
    }

    public boolean getResumeStatus()
    {
        return resume;
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
        setName("InactivityDialog");
        setName("InactivityDialog");
        setTitle("NetZero Tip");
        setFont(new Font("dialog", 1, 10));
        setLayout(null);
        setSize(312, 150);
        setResizable(false);
        add(getLabel1(), getLabel1().getName());
        add(getLabel2(), getLabel2().getName());
        add(getLabel4(), getLabel4().getName());
        add(getLabel5(), getLabel5().getName());
        add(getButton1(), getButton1().getName());
        initConnections();
    }

    public static void main(String args[])
    {
        try
        {
            InactivityDialog inactivitydialog = new InactivityDialog(new Frame());
            try
            {
                Class class1 = Class.forName("uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    inactivitydialog
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog: " + throwable.toString());
        }
    }

    public void setRemainingSeconds(int i)
    {
        getLabel4().setText(String.valueOf(i));
    }

    public void setResumeStatus(boolean flag)
    {
        resume = flag;
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
    private NZButton ivjButton1;
    private Label ivjLabel1;
    private NZLabel ivjLabel2;
    private Label ivjLabel3;
    private Label ivjLabel4;
    private NZLabel ivjLabel5;
    private int remainingSeconds;
    private boolean resume;
    static Class class$java$awt$Window; /* synthetic field */

}
