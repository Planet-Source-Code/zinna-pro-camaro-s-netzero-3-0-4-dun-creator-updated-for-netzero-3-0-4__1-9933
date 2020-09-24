// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageDialog.java

package nzcom;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.ResourceBundle;
import jclass.bwt.*;
import jclass.util.JCString;
import tcpBinary.UserMessage;

// Referenced classes of package nzcom:
//            OSDetectNative, MsgTimingThread, ZCast, MsgProcObj

public class MessageDialog extends Dialog
    implements WindowListener, JCActionListener
{

    public MessageDialog(Frame frame, String s, UserMessage usermessage)
    {
        super(frame, s);
        ivjContentsPane = null;
        ivjTextArea1 = null;
        ivjJCButton1 = null;
        ivjJCGroupBox1 = null;
        timer = null;
        umsg = null;
        setUmsg(usermessage);
        setMessageText((String)usermessage.getMsg());
        initialize();
    }

    public MessageDialog(Frame frame, UserMessage usermessage)
    {
        super(frame, resNZResource.getString("NetZero_Message_Dialog"));
        ivjContentsPane = null;
        ivjTextArea1 = null;
        ivjJCButton1 = null;
        ivjJCGroupBox1 = null;
        timer = null;
        umsg = null;
        setUmsg(usermessage);
        setMessageText((String)usermessage.getMsg());
        initialize();
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
        if(jcactionevent.getSource() == getJCButton1())
            connEtoM1(jcactionevent);
    }

    private void connEtoC1(WindowEvent windowevent)
    {
        try
        {
            disposeSave();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoM1(JCActionEvent jcactionevent)
    {
        try
        {
            disposeSave();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void disposeNoSave()
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

    private void disposeSave()
    {
        try
        {
            MsgProcObj.saveMsg(umsg);
            dispose();
            timer.stop();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                ivjContentsPane.setBackground(new Color(64, 64, 64));
                getContentsPane().add(getTextArea1(), getTextArea1().getName());
                getContentsPane().add(getJCButton1(), getJCButton1().getName());
                getContentsPane().add(getJCGroupBox1(), getJCGroupBox1().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private JCButton getJCButton1()
    {
        if(ivjJCButton1 == null)
            try
            {
                ivjJCButton1 = new JCButton();
                ivjJCButton1.setName("JCButton1");
                ivjJCButton1.setBackground(Color.lightGray);
                ivjJCButton1.setBounds(141, 164, 68, 20);
                ivjJCButton1.setForeground(Color.lightGray);
                JCString jcstring = JCString.parse(this, "[IMG=images/dismiss.gif");
                ivjJCButton1.setLabel(jcstring);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton1;
    }

    private JCGroupBox getJCGroupBox1()
    {
        if(ivjJCGroupBox1 == null)
            try
            {
                ivjJCGroupBox1 = new JCGroupBox();
                ivjJCGroupBox1.setName("JCGroupBox1");
                ivjJCGroupBox1.setShadowThickness(3);
                ivjJCGroupBox1.setBounds(20, 3, 309, 154);
                ivjJCGroupBox1.setForeground(Color.lightGray);
                JCString jcstring = JCString.parse(this, "[IMG=images/message_logo.gif]");
                ivjJCGroupBox1.setTitle(jcstring);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox1;
    }

    public String getMessageText()
    {
        return MessageText;
    }

    private TextArea getTextArea1()
    {
        if(ivjTextArea1 == null)
            try
            {
                ivjTextArea1 = new TextArea();
                ivjTextArea1.setName("TextArea1");
                ivjTextArea1.setBackground(Color.black);
                ivjTextArea1.setBounds(31, 48, 284, 96);
                ivjTextArea1.setEditable(false);
                ivjTextArea1.setForeground(Color.green);
                ivjTextArea1 = new TextArea(MessageText, 5, 30, 1);
                ivjTextArea1.setName("TextArea1");
                ivjTextArea1.setBackground(Color.black);
                ivjTextArea1.setBounds(31, 48, 284, 96);
                ivjTextArea1.setEditable(false);
                ivjTextArea1.setForeground(Color.green);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextArea1;
    }

    private UserMessage getUmsg()
    {
        return umsg;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        ivjTextArea1.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent keyevent)
            {
                if(keyevent.getKeyCode() == 10)
                    disposeSave();
            }

        });
        addWindowListener(this);
        getJCButton1().addActionListener(this);
    }

    private void initialize()
    {
        setName("RobMessageDialog");
        setLayout(new BorderLayout());
        setBackground(new Color(64, 64, 64));
        setModal(true);
        setSize(351, 220);
        setResizable(false);
        add(getContentsPane(), "Center");
        initConnections();
        ZCast.centerComponent(this);
        timer = new MsgTimingThread(this, umsg.getDisplayTime());
        timer.start();
        setVisible(true);
    }

    public static void main(String args[])
    {
        try
        {
            String s = resNZResource.getString("This_is_sample_text.__It_i");
            UserMessage usermessage = new UserMessage(1, s, 4);
            MessageDialog messagedialog = new MessageDialog(new Frame(), usermessage);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
        ZCast.displayDebug("There, it is done.");
    }

    public void setMessageText(String s)
    {
        MessageText = s;
    }

    private void setUmsg(UserMessage usermessage)
    {
        umsg = usermessage;
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
    private Panel ivjContentsPane;
    private TextArea ivjTextArea1;
    private JCButton ivjJCButton1;
    private JCGroupBox ivjJCGroupBox1;
    private String MessageText;
    private MsgTimingThread timer;
    private UserMessage umsg;


}
