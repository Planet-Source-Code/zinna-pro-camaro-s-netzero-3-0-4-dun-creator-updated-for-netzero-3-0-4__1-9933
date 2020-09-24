// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FeedbackForm.java

package nzcom;

import gui.NZButton;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;
import jclass.bwt.JCContainer;
import jclass.bwt.JCGroupBox;

// Referenced classes of package nzcom:
//            OSDetectNative

class FeedbackForm extends Dialog
    implements ActionListener, ItemListener, WindowListener
{

    public FeedbackForm(Frame frame)
    {
        super(frame);
        fileName = null;
        ivjButton1 = null;
        ivjButton2 = null;
        ivjCheckbox1 = null;
        ivjContentsPane = null;
        ivjFileDialog1 = null;
        ivjJCGroupBox1 = null;
        ivjJCGroupBox2 = null;
        ivjLabel1 = null;
        ivjLabel2 = null;
        ivjLabel3 = null;
        ivjLabel4 = null;
        ivjPanel1 = null;
        ivjTextArea1 = null;
        ivjTextField1 = null;
        ivjTextField2 = null;
        ivjTextField3 = null;
        ivjTextField4 = null;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getButton2())
            conn1(actionevent);
        if(actionevent.getSource() == getButton1())
            conn3(actionevent);
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
            submitButton = false;
            setVisible(false);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void conn2(ItemEvent itemevent)
    {
        try
        {
            if(itemevent.getStateChange() == 2)
            {
                fileName = null;
                getCheckbox1().setLabel(resNZResource.getString("(Attach_File)"));
                return;
            }
            if(itemevent.getStateChange() != 1)
                return;
            getFileDialog1().setVisible(true);
            if(getFileDialog1().getFile() != null)
            {
                fileName = getFileDialog1().getDirectory() + getFileDialog1().getFile();
                getCheckbox1().setLabel(resNZResource.getString("Attach_") + fileName);
            } else
            {
                fileName = null;
                getCheckbox1().setLabel(resNZResource.getString("(Attach_File)"));
                getCheckbox1().setState(false);
            }
            getFileDialog1().dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void conn3(ActionEvent actionevent)
    {
        try
        {
            submitButton = true;
            setVisible(false);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public String getAttachFileName()
    {
        return fileName;
    }

    private NZButton getButton1()
    {
        if(ivjButton1 == null)
            try
            {
                ivjButton1 = new NZButton("submit");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton1;
    }

    private NZButton getButton2()
    {
        if(ivjButton2 == null)
            try
            {
                ivjButton2 = new NZButton("cancel");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton2;
    }

    private JCGroupBox getButtonBox()
    {
        JCGroupBox jcgroupbox = null;
        try
        {
            jcgroupbox = new JCGroupBox();
            jcgroupbox.setName("JCGroupBox1");
            jcgroupbox.setLayout(new FlowLayout(2));
            jcgroupbox.setBounds(10, 275, 335, 35);
            jcgroupbox.add(getButton1(), getButton1().getName());
            jcgroupbox.add(getButton2(), getButton2().getName());
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
        return jcgroupbox;
    }

    private Checkbox getCheckbox1()
    {
        if(ivjCheckbox1 == null)
            try
            {
                ivjCheckbox1 = new Checkbox();
                ivjCheckbox1.setName("Checkbox1");
                ivjCheckbox1.setBounds(5, 108, 320, 20);
                ivjCheckbox1.setLabel(resNZResource.getString("(Attach_File)"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCheckbox1;
    }

    public String getComments()
    {
        return getTextArea1().getText();
    }

    private Panel getContentsPane()
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

    private FileDialog getFileDialog1()
    {
        if(ivjFileDialog1 == null)
            try
            {
                ivjFileDialog1 = new FileDialog(new Frame());
                ivjFileDialog1.setName("FileDialog1");
                ivjFileDialog1.setTitle(resNZResource.getString("Attach_File_to_Feedback_Fo"));
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                ivjFileDialog1.setLocation((dimension.width - ivjFileDialog1.getSize().width) / 2, (dimension.height - ivjFileDialog1.getSize().height) / 2);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjFileDialog1;
    }

    private JCGroupBox getJCGroupBox1()
    {
        if(ivjJCGroupBox1 == null)
            try
            {
                ivjJCGroupBox1 = new JCGroupBox();
                ivjJCGroupBox1.setName("JCGroupBox1");
                ivjJCGroupBox1.setLayout(new BorderLayout());
                ivjJCGroupBox1.setBounds(10, 25, 335, 112);
                ivjJCGroupBox1.setTitle(resNZResource.getString("User_Info"));
                getJCGroupBox1().add("Center", getPanel1());
                getPanel1().setVisible(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox1;
    }

    private JCGroupBox getJCGroupBox2()
    {
        if(ivjJCGroupBox2 == null)
            try
            {
                ivjJCGroupBox2 = new JCGroupBox();
                ivjJCGroupBox2.setName("JCGroupBox2");
                ivjJCGroupBox2.setLayout(null);
                ivjJCGroupBox2.setBounds(10, 138, 335, 135);
                ivjJCGroupBox2.setTitle(resNZResource.getString("Feedback_Comments"));
                ivjJCGroupBox2.add(getTextArea1());
                ivjJCGroupBox2.add(getCheckbox1(), getCheckbox1().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox2;
    }

    private Label getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new Label();
                ivjLabel1.setName("Label1");
                ivjLabel1.setText(resNZResource.getString("Userid"));
                ivjLabel1.setBounds(195, 2, 44, 24);
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
                ivjLabel2.setText(resNZResource.getString("Name"));
                ivjLabel2.setBounds(8, 2, 39, 27);
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
                ivjLabel3.setText(resNZResource.getString("Address"));
                ivjLabel3.setBounds(8, 30, 45, 30);
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
                ivjLabel4.setText(resNZResource.getString("Phone"));
                ivjLabel4.setBounds(8, 58, 47, 29);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel4;
    }

    private Panel getPanel1()
    {
        if(ivjPanel1 == null)
            try
            {
                ivjPanel1 = new Panel();
                ivjPanel1.setName("Panel1");
                ivjPanel1.setLayout(null);
                ivjPanel1.add(getLabel2(), getLabel2().getName());
                ivjPanel1.add(getTextField2(), getTextField2().getName());
                ivjPanel1.add(getLabel1(), getLabel1().getName());
                ivjPanel1.add(getTextField1(), getTextField1().getName());
                ivjPanel1.add(getLabel3(), getLabel3().getName());
                ivjPanel1.add(getTextField3(), getTextField3().getName());
                ivjPanel1.add(getLabel4(), getLabel4().getName());
                ivjPanel1.add(getTextField4(), getTextField4().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel1;
    }

    public static boolean getSubmitButton()
    {
        return submitButton;
    }

    private TextArea getTextArea1()
    {
        if(ivjTextArea1 == null)
            try
            {
                ivjTextArea1 = new TextArea();
                ivjTextArea1.setName("TextArea1");
                ivjTextArea1.setBounds(5, 15, 320, 90);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextArea1;
    }

    private TextField getTextField1()
    {
        if(ivjTextField1 == null)
            try
            {
                ivjTextField1 = new TextField();
                ivjTextField1.setName("TextField1");
                ivjTextField1.setBounds(238, 3, 85, 25);
                ivjTextField1.setEditable(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField1;
    }

    private TextField getTextField2()
    {
        if(ivjTextField2 == null)
            try
            {
                ivjTextField2 = new TextField();
                ivjTextField2.setName("TextField2");
                ivjTextField2.setBounds(60, 3, 120, 25);
                ivjTextField2.setEditable(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField2;
    }

    private TextField getTextField3()
    {
        if(ivjTextField3 == null)
            try
            {
                ivjTextField3 = new TextField();
                ivjTextField3.setName("TextField3");
                ivjTextField3.setBounds(60, 32, 263, 25);
                ivjTextField3.setEditable(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField3;
    }

    private TextField getTextField4()
    {
        if(ivjTextField4 == null)
            try
            {
                ivjTextField4 = new TextField();
                ivjTextField4.setName("TextField4");
                ivjTextField4.setBounds(60, 61, 120, 25);
                ivjTextField4.setEditable(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField4;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getButton2().addActionListener(this);
        getCheckbox1().addItemListener(this);
        getButton1().addActionListener(this);
    }

    private void initialize()
    {
        setName("FeedbackForm");
        setTitle(resNZResource.getString("NetZero_zCast_Feedback_For"));
        setLayout(null);
        setSize(350, 315);
        setSize(348, 313);
        setResizable(false);
        setModal(true);
        add(getJCGroupBox1(), getJCGroupBox1().getName());
        add(getJCGroupBox2(), getJCGroupBox2().getName());
        add(getButtonBox());
        initConnections();
        setSubmitButton(false);
        setTextFocus();
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        if(itemevent.getSource() == getCheckbox1())
            conn2(itemevent);
    }

    public static void main(String args[])
    {
        try
        {
            FeedbackForm feedbackform = new FeedbackForm(new Frame());
            feedbackform.setModal(true);
            try
            {
                Class class1 = Class.forName("uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    class$java$awt$Window == null ? (class$java$awt$Window = class$("java.awt.Window")) : class$java$awt$Window
                };
                Object aobj[] = {
                    feedbackform
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable _ex) { }
            feedbackform.setVisible(true);
        }
        catch(Throwable _ex)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
        }
    }

    public void setSubmitButton(boolean flag)
    {
        submitButton = flag;
    }

    public void setTextFocus()
    {
        getTextArea1().requestFocus();
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
    public String fileName;
    private NZButton ivjButton1;
    private NZButton ivjButton2;
    private Checkbox ivjCheckbox1;
    private Panel ivjContentsPane;
    private FileDialog ivjFileDialog1;
    private JCGroupBox ivjJCGroupBox1;
    private JCGroupBox ivjJCGroupBox2;
    private Label ivjLabel1;
    private Label ivjLabel2;
    private Label ivjLabel3;
    private Label ivjLabel4;
    private Panel ivjPanel1;
    private TextArea ivjTextArea1;
    private TextField ivjTextField1;
    private TextField ivjTextField2;
    private TextField ivjTextField3;
    private TextField ivjTextField4;
    private static boolean submitButton = false;
    static Class class$java$awt$Window; /* synthetic field */

}
