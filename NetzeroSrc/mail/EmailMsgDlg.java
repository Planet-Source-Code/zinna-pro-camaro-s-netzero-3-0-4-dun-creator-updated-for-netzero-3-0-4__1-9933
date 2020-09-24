// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailMsgDlg.java

package mail;

import gui.NZButton;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import nzcom.OSDetectNative;
import nzcom.ZCast;

public class EmailMsgDlg extends Dialog
    implements ActionListener, ItemListener, WindowListener
{

    public EmailMsgDlg(Frame frame)
    {
        super(frame);
        ivjContentsPane = null;
        ivjSubjectLabel = null;
        ivjSubjectText = null;
        ivjChoiceBox = null;
        ivjEmailList = null;
        ivjMsgText = null;
        ivjTopLabel1 = null;
        ivjTopLabel2 = null;
        RealName = "RealName";
        UserName = "UserName";
        CurChoice = 0;
        ivjFromLabel = null;
        ivjFromText = null;
        ivjToLabel = null;
        ivjSendButton = null;
        ivjBackButton = null;
        initialize();
    }

    public EmailMsgDlg(Frame frame, String s, String s1, String s2)
    {
        super(frame);
        ivjContentsPane = null;
        ivjSubjectLabel = null;
        ivjSubjectText = null;
        ivjChoiceBox = null;
        ivjEmailList = null;
        ivjMsgText = null;
        ivjTopLabel1 = null;
        ivjTopLabel2 = null;
        RealName = "RealName";
        UserName = "UserName";
        CurChoice = 0;
        ivjFromLabel = null;
        ivjFromText = null;
        ivjToLabel = null;
        ivjSendButton = null;
        ivjBackButton = null;
        RealName = s;
        UserName = s1;
        EmailString = s2;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getBackButton())
            connEtoM1(actionevent);
        if(actionevent.getSource() == getSendButton())
            connEtoM2(actionevent);
    }

    public static void centerComponent(Component component)
    {
    }

    public void choiceBox_ItemStateChanged(ItemEvent itemevent)
    {
        refresh(ivjChoiceBox.getSelectedIndex());
    }

    private void connEtoC1(WindowEvent windowevent)
    {
        try
        {
            dismiss = true;
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoC2(ItemEvent itemevent)
    {
        try
        {
            choiceBox_ItemStateChanged(itemevent);
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
            dismiss = true;
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
            dismiss = false;
            Msg = ivjMsgText.getText();
            EmailString = ivjEmailList.getText();
            Subject = ivjSubjectText.getText();
            dispose();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private String convertString(String s)
    {
        String s1 = s.replace('^', '\n');
        return s1;
    }

    private NZButton getBackButton()
    {
        if(ivjBackButton == null)
            try
            {
                ivjBackButton = new NZButton("back");
                ivjBackButton.setLocation(250, 334);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjBackButton;
    }

    private String getChoice(int i)
    {
        String s = Choices[i];
        int j = s.indexOf("|");
        s = s.substring(0, j);
        return s;
    }

    private Choice getChoiceBox()
    {
        if(ivjChoiceBox == null)
            try
            {
                ivjChoiceBox = new Choice();
                ivjChoiceBox.setName("ChoiceBox");
                ivjChoiceBox.setBackground(SystemColor.activeCaptionText);
                ivjChoiceBox.setBounds(278, 14, 112, 27);
                for(int i = 0; i < Choices.length; i++)
                    ivjChoiceBox.add(getChoice(i));

            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjChoiceBox;
    }

    private String[] getChoices()
    {
        int i = Choices.length;
        String as[] = new String[i];
        for(int j = 0; j < i; j++)
            as[j] = getChoice(j);

        return Choices;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                getContentsPane().add(getBackButton(), getBackButton().getName());
                getContentsPane().add(getSendButton(), getSendButton().getName());
                getContentsPane().add(getToLabel(), getToLabel().getName());
                getContentsPane().add(getMsgText(), getMsgText().getName());
                getContentsPane().add(getSubjectLabel(), getSubjectLabel().getName());
                getContentsPane().add(getSubjectText(), getSubjectText().getName());
                getContentsPane().add(getEmailList(), getEmailList().getName());
                getContentsPane().add(getTopLabel2(), getTopLabel2().getName());
                getContentsPane().add(getChoiceBox(), getChoiceBox().getName());
                getContentsPane().add(getTopLabel1(), getTopLabel1().getName());
                getContentsPane().add(getFromLabel(), getFromLabel().getName());
                getContentsPane().add(getFromText(), getFromText().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    public static boolean getDismiss()
    {
        return dismiss;
    }

    private TextArea getEmailList()
    {
        if(ivjEmailList == null)
            try
            {
                ivjEmailList = new TextArea();
                ivjEmailList.setName("EmailList");
                ivjEmailList.setBackground(SystemColor.activeCaptionText);
                ivjEmailList.setBounds(43, 48, 347, 40);
                ivjEmailList.setEditable(true);
                ivjEmailList = new TextArea(EmailString, 1, 1000, 2);
                ivjEmailList.setName("EmailList");
                ivjEmailList.setBackground(SystemColor.activeCaptionText);
                ivjEmailList.setBounds(49, 52, 338, 43);
                ivjEmailList.setEditable(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjEmailList;
    }

    public static String getEmailString()
    {
        return EmailString;
    }

    private Label getFromLabel()
    {
        if(ivjFromLabel == null)
            try
            {
                ivjFromLabel = new Label();
                ivjFromLabel.setName("FromLabel");
                ivjFromLabel.setText(resNZResource.getString("From_"));
                ivjFromLabel.setBounds(16, 100, 36, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjFromLabel;
    }

    private TextField getFromText()
    {
        if(ivjFromText == null)
            try
            {
                ivjFromText = new TextField();
                ivjFromText.setName("FromText");
                ivjFromText.setBackground(SystemColor.inactiveCaptionText);
                ivjFromText.setBounds(52, 100, 180, 20);
                ivjFromText.setEditable(false);
                ivjFromText.setText(UserName + "@netzero.net");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjFromText;
    }

    public static String getMsg()
    {
        return Msg;
    }

    private TextArea getMsgText()
    {
        if(ivjMsgText == null)
            try
            {
                ivjMsgText = new TextArea();
                ivjMsgText.setName("MsgText");
                ivjMsgText.setBackground(SystemColor.activeCaptionText);
                ivjMsgText.setBounds(16, 151, 378, 175);
                ivjMsgText = new TextArea("", 0, 0, 1);
                ivjMsgText.setBackground(SystemColor.activeCaptionText);
                ivjMsgText.setBounds(16, 151, 378, 175);
                ivjMsgText.setText(Msg);
                ivjMsgText.setEditable(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjMsgText;
    }

    public String getRealName()
    {
        return RealName;
    }

    private NZButton getSendButton()
    {
        if(ivjSendButton == null)
            try
            {
                ivjSendButton = new NZButton("send");
                ivjSendButton.setLocation(97, 334);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjSendButton;
    }

    public static String getSubject()
    {
        return Subject;
    }

    private Label getSubjectLabel()
    {
        if(ivjSubjectLabel == null)
            try
            {
                ivjSubjectLabel = new Label();
                ivjSubjectLabel.setName("SubjectLabel");
                ivjSubjectLabel.setText(resNZResource.getString("Subject_"));
                ivjSubjectLabel.setBounds(16, 125, 48, 18);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjSubjectLabel;
    }

    private TextField getSubjectText()
    {
        if(ivjSubjectText == null)
            try
            {
                ivjSubjectText = new TextField();
                ivjSubjectText.setName("SubjectText");
                ivjSubjectText.setBackground(SystemColor.activeCaptionText);
                ivjSubjectText.setBounds(63, 125, 327, 18);
                ivjSubjectText.setEditable(true);
                ivjSubjectText.setText(Subject);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjSubjectText;
    }

    private Label getToLabel()
    {
        if(ivjToLabel == null)
            try
            {
                ivjToLabel = new Label();
                ivjToLabel.setName("ToLabel");
                ivjToLabel.setText(resNZResource.getString("To_"));
                ivjToLabel.setBounds(16, 60, 19, 18);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjToLabel;
    }

    private Label getTopLabel1()
    {
        if(ivjTopLabel1 == null)
            try
            {
                ivjTopLabel1 = new Label();
                ivjTopLabel1.setName("TopLabel1");
                ivjTopLabel1.setText(resNZResource.getString("Choose_how_to_word_your_em"));
                ivjTopLabel1.setBounds(16, 8, 264, 16);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTopLabel1;
    }

    private Label getTopLabel2()
    {
        if(ivjTopLabel2 == null)
            try
            {
                ivjTopLabel2 = new Label();
                ivjTopLabel2.setName("TopLabel2");
                ivjTopLabel2.setText(resNZResource.getString("others_of_your_NetZero_ema"));
                ivjTopLabel2.setBounds(16, 22, 215, 16);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTopLabel2;
    }

    public String getUserName()
    {
        return UserName;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getBackButton().addActionListener(this);
        getChoiceBox().addItemListener(this);
        getSendButton().addActionListener(this);
    }

    private void initialize()
    {
        CurChoice = 0;
        updateEmailTemplates();
        setSubjectMsg();
        setName("EmailMsgDlg");
        setLayout(new BorderLayout());
        setSize(403, 385);
        setTitle(resNZResource.getString("Send_Email_Notification"));
        add(getContentsPane(), "Center");
        initConnections();
        setModal(true);
        ZCast.centerComponent(this);
        dismiss = false;
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        if(itemevent.getSource() == getChoiceBox())
            connEtoC2(itemevent);
    }

    public static void main(String args[])
    {
        try
        {
            showDlg("RealName", "LoginName", "test@email");
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    private void refresh(int i)
    {
        CurChoice = i;
        setSubjectMsg();
        ivjSubjectText.setText(Subject);
        ivjMsgText.setText(Msg);
    }

    public void setEmailString(String s)
    {
        EmailString = s;
    }

    private void setMsg(String s)
    {
        int i = s.indexOf("**");
        int j = s.indexOf("#");
        String s1 = s.substring(0, i);
        String s2 = s.substring(i + 2, j);
        String s3 = s1;
        s3 = s3.concat(UserName);
        s3 = s3.concat(s2);
        s3 = s3.concat(RealName);
        Msg = s3;
    }

    public void setRealName(String s)
    {
        RealName = s;
    }

    private void setSubjectMsg()
    {
        int i = CurChoice;
        String s = Choices[i];
        int j = s.indexOf("|") + 1;
        s = Choices[i].substring(j + 1);
        int k = s.indexOf("|") + j + 1;
        Subject = Choices[i].substring(j, k);
        setMsg(Choices[i].substring(k + 1));
    }

    public void setUserName(String s)
    {
        UserName = s;
    }

    public static void showDlg(String s, String s1, String s2)
    {
        EmailMsgDlg emailmsgdlg = new EmailMsgDlg(new Frame(), s, s1, s2);
        emailmsgdlg.setVisible(true);
        emailmsgdlg.dispose();
        emailmsgdlg = null;
    }

    public void updateEmailTemplates()
    {
        Vector vector = new Vector();
        File file = new File("emltmplt.dat");
        try
        {
            if(file.exists())
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                do
                {
                    String s = bufferedreader.readLine();
                    if(s == null)
                        break;
                    vector.addElement(s);
                } while(true);
                bufferedreader.close();
                Choices = new String[vector.size()];
                int i = 0;
                for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
                {
                    String s1 = (String)enumeration.nextElement();
                    String s2 = convertString(s1);
                    Choices[i++] = s2;
                }

            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
            return;
        }
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

    private static ResourceBundle resNZResource;
    private Panel ivjContentsPane;
    private Label ivjSubjectLabel;
    private TextField ivjSubjectText;
    private Choice ivjChoiceBox;
    private TextArea ivjEmailList;
    private TextArea ivjMsgText;
    private Label ivjTopLabel1;
    private Label ivjTopLabel2;
    private String RealName;
    private String UserName;
    private static boolean dismiss = false;
    private static String Choices[];
    private static String EmailString = null;
    private static String Subject = null;
    private static String Msg = null;
    private int CurChoice;
    private Label ivjFromLabel;
    private TextField ivjFromText;
    private Label ivjToLabel;
    private NZButton ivjSendButton;
    private static final String TEMPLATE_FILE = "emltmplt.dat";
    private NZButton ivjBackButton;

    static 
    {
        resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
        Choices = (new String[] {
            resNZResource.getString("To_the_Point|My_email_addr"), resNZResource.getString("Formal|Change_of_e-mail_ad"), resNZResource.getString("Friendly|I've_Moved!|Just_")
        });
    }
}
