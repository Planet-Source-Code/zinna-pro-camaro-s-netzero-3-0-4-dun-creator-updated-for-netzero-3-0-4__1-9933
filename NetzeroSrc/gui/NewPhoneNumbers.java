// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewPhoneNumbers.java

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.Vector;
import jclass.bwt.JCGroupBox;
import jclass.bwt.JCLabel;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZButton, NZFont, PhoneRec

public class NewPhoneNumbers extends Dialog
    implements WindowListener, ActionListener
{

    public NewPhoneNumbers(Frame frame, String s, Vector vector)
    {
        super(frame, s);
        ivjContentsPane = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        ivjJCLabel2 = null;
        ivjList1 = null;
        JCGroupBoxNumbers = null;
        pRecVector = new Vector();
        for(int i = 0; i < vector.size(); i++)
            pRecVector.addElement(vector.elementAt(i));

        initialize();
    }

    public NewPhoneNumbers(Frame frame, String s, boolean flag, Vector vector)
    {
        super(frame, s, flag);
        ivjContentsPane = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        ivjJCLabel2 = null;
        ivjList1 = null;
        JCGroupBoxNumbers = null;
        pRecVector = new Vector();
        for(int i = 0; i < vector.size(); i++)
            pRecVector.addElement(vector.elementAt(i));

        initialize();
    }

    public NewPhoneNumbers(Frame frame, Vector vector)
    {
        super(frame);
        ivjContentsPane = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        ivjJCLabel2 = null;
        ivjList1 = null;
        JCGroupBoxNumbers = null;
        pRecVector = new Vector();
        if(vector != null)
        {
            for(int i = 0; i < vector.size(); i++)
                pRecVector.addElement(vector.elementAt(i));

        }
        initialize();
    }

    public NewPhoneNumbers(Frame frame, boolean flag, Vector vector)
    {
        super(frame, flag);
        ivjContentsPane = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        ivjJCGroupBox1 = null;
        ivjJCLabel1 = null;
        ivjJCLabel2 = null;
        ivjList1 = null;
        JCGroupBoxNumbers = null;
        pRecVector = new Vector();
        for(int i = 0; i < vector.size(); i++)
            pRecVector.addElement(vector.elementAt(i));

        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getJCButton1())
            clickNext();
        if(actionevent.getSource() == getJCButton2())
            clickSkip();
    }

    private void clickNext()
    {
        try
        {
            Vector vector = new Vector();
            boolean flag = false;
            Object aobj[] = getList1().getSelectedObjects();
            if(aobj != null && (pRecVector != null) & (pRecVector.size() > 0))
            {
                for(int i = 0; i < pRecVector.size(); i++)
                {
                    boolean flag1 = false;
                    PhoneRec phonerec = (PhoneRec)pRecVector.elementAt(i);
                    String s = "(" + phonerec.getAreaCode() + ")" + phonerec.getFirst3() + "-" + phonerec.getLast4() + ", " + phonerec.getCityString();
                    for(int k = 0; (k < aobj.length) & (!flag1); k++)
                    {
                        String s1 = (String)aobj[k];
                        if(s.equals(s1))
                        {
                            flag1 = true;
                            vector.addElement(phonerec);
                        }
                    }

                }

            }
            pRecVector = new Vector();
            if((vector != null) & (vector.size() > 0))
            {
                for(int j = 0; j < vector.size(); j++)
                    pRecVector.addElement(vector.elementAt(j));

            }
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void clickSkip()
    {
        try
        {
            pRecVector = null;
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
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

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                ivjContentsPane.setFont(new NZFont());
                getContentsPane().add(getJCLabel1(), getJCLabel1().getName());
                getContentsPane().add(getJCGroupBox1(), getJCGroupBox1().getName());
                getContentsPane().add(getList1(), getList1().getName());
                getContentsPane().add(getJCGroupBoxNumbers(), getJCGroupBoxNumbers().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private NZButton getJCButton1()
    {
        if(ivjJCButton1 == null)
            try
            {
                ivjJCButton1 = new NZButton("next");
                ivjJCButton1.setLocation(264, 6);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton1;
    }

    private NZButton getJCButton2()
    {
        if(ivjJCButton2 == null)
            try
            {
                ivjJCButton2 = new NZButton("skip");
                ivjJCButton2.setLocation(351, 6);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton2;
    }

    private JCGroupBox getJCGroupBox1()
    {
        if(ivjJCGroupBox1 == null)
            try
            {
                ivjJCGroupBox1 = new JCGroupBox();
                ivjJCGroupBox1.setName("JCGroupBox1");
                ivjJCGroupBox1.setLayout(null);
                ivjJCGroupBox1.setBounds(3, 230, 421, 41);
                getJCGroupBox1().add(getJCButton1(), getJCButton1().getName());
                getJCGroupBox1().add(getJCButton2(), getJCButton2().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox1;
    }

    private JCGroupBox getJCGroupBoxNumbers()
    {
        if(JCGroupBoxNumbers == null)
            try
            {
                JCGroupBoxNumbers = new JCGroupBox();
                JCGroupBoxNumbers.setName("JCGroupBoxNumbers");
                JCGroupBoxNumbers.setBounds(5, 90, 280, 130);
                JCGroupBoxNumbers.setTitle(resNZResource.getString("New_Numbers"));
                JCGroupBoxNumbers.setShadowThickness(2);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return JCGroupBoxNumbers;
    }

    private JCLabel getJCLabel1()
    {
        if(ivjJCLabel1 == null)
            try
            {
                ivjJCLabel1 = new JCLabel();
                ivjJCLabel1.setName("JCLabel1");
                ivjJCLabel1.setAlignment(0);
                ivjJCLabel1.setBounds(5, 5, 420, 78);
                ivjJCLabel1.setLabel(resNZResource.getString("According_to_information_y"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCLabel1;
    }

    private JCLabel getJCLabel2()
    {
        if(ivjJCLabel2 == null)
            try
            {
                ivjJCLabel2 = new JCLabel();
                ivjJCLabel2.setName("JCLabel2");
                ivjJCLabel2.setFont(new Font("dialog", 0, 14));
                ivjJCLabel2.setBounds(19, 120, 122, 26);
                ivjJCLabel2.setLabel(resNZResource.getString("New_Numbers_"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCLabel2;
    }

    private java.awt.List getList1()
    {
        if(ivjList1 == null)
            try
            {
                ivjList1 = new java.awt.List();
                ivjList1.setName("JCList1");
                ivjList1.setBackground(SystemColor.window);
                ivjList1.setBounds(11, 105, 264, 103);
                ivjList1.setMultipleMode(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjList1;
    }

    public static Vector getVectorToShow()
    {
        return pRecVector;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getJCButton1().addActionListener(this);
        getJCButton2().addActionListener(this);
    }

    private void initialize()
    {
        setName("NewPhoneNumbers");
        setLayout(new BorderLayout());
        setSize(426, 300);
        setTitle(resNZResource.getString("New_Phone_Numbers"));
        setModal(true);
        setResizable(false);
        ZCast.displayDebug("getContentsPane");
        add(getContentsPane(), "Center");
        ZCast.displayDebug("done");
        ZCast.displayDebug("check pRecVector");
        if(pRecVector != null && pRecVector.size() > 0)
        {
            ZCast.displayDebug("is not null and > 0");
            for(int i = 0; i < pRecVector.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)pRecVector.elementAt(i);
                String s = "(" + phonerec.getAreaCode() + ")" + phonerec.getFirst3() + "-" + phonerec.getLast4() + ", " + phonerec.getCityString();
                getList1().addItem(s);
            }

        }
        ZCast.displayDebug("initConnections");
        initConnections();
        for(int j = 0; j < getList1().getItemCount(); j++)
            getList1().select(j);

        setVisible(false);
    }

    public static void main(String args[])
    {
        try
        {
            NewPhoneNumbers newphonenumbers = new NewPhoneNumbers(new Frame(), null);
            newphonenumbers.setModal(true);
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    newphonenumbers
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
            newphonenumbers.setVisible(true);
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    public void newMethod()
    {
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
            clickSkip();
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

    private Panel ivjContentsPane;
    private static ResourceBundle resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private NZButton ivjJCButton1;
    private NZButton ivjJCButton2;
    private JCGroupBox ivjJCGroupBox1;
    private JCLabel ivjJCLabel1;
    private JCLabel ivjJCLabel2;
    private java.awt.List ivjList1;
    private static Vector pRecVector = new Vector();
    private JCGroupBox JCGroupBoxNumbers;
    static Class class$java$awt$Window; /* synthetic field */

}
