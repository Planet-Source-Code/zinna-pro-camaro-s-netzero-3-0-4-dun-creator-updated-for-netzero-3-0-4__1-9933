// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddressDlg.java

package mail;

import gui.NZButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import nzcom.OSDetectNative;
import nzcom.ZCast;
import sun.net.smtp.SmtpClient;

// Referenced classes of package mail:
//            EmailMsgDlg

public class AddressDlg extends Dialog
{
    class NameTextListener
        implements TextListener
    {

        public void textValueChanged(TextEvent textevent)
        {
            if(getNameText().getText().trim().length() > 0 && ivjEmailList.getItemCount() > 0)
                getSendButton().setEnabled(true);
            else
                getSendButton().setEnabled(false);
        }

        NameTextListener()
        {
        }
    }

    class AddressTextListener
        implements TextListener
    {

        public void textValueChanged(TextEvent textevent)
        {
            if(getEmailText().getText().trim().length() > 0)
            {
                if(!getAddButton().isEnabled())
                    getAddButton().setEnabled(true);
            } else
            {
                getAddButton().setEnabled(false);
            }
        }

        AddressTextListener()
        {
        }
    }

    class AddListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            getEmailList().add(getEmailText().getText().trim());
            getAddButton().setEnabled(false);
            getEmailText().setText(null);
            if(!getSendButton().isEnabled() && getNameText().getText().trim().length() > 0)
                getSendButton().setEnabled(true);
        }

        AddListener()
        {
        }
    }

    class RemoveListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            String as[] = getEmailList().getSelectedItems();
            for(int i = 0; i < as.length; i++)
                getEmailList().remove(as[i]);

            getRemoveButton().setEnabled(false);
            cSel -= as.length;
            if(getNameText().getText().length() > 0 && getEmailList().getItemCount() <= 0)
                getSendButton().setEnabled(false);
        }

        RemoveListener()
        {
        }
    }

    class MyItemListener
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            if(itemevent.getStateChange() == 1)
                cSel++;
            else
                cSel--;
            ZCast.displayDebug("cSel = " + cSel);
            if(cSel > 0 && !getRemoveButton().isEnabled())
                getRemoveButton().setEnabled(true);
            if(cSel <= 0 && getRemoveButton().isEnabled())
                getRemoveButton().setEnabled(false);
        }

        MyItemListener()
        {
        }
    }

    class SendListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            ZCast.displayDebug("login is " + login);
            ZCast.displayDebug("getText is " + getNameText().getText());
            ZCast.displayDebug("EmailString is " + getEmailString(getEmailList().getItems()));
            EmailMsgDlg.showDlg(getNameText().getText(), login, getEmailString(getEmailList().getItems()));
            if(EmailMsgDlg.getDismiss())
            {
                requestFocus();
            } else
            {
                Thread thread = new Thread() {

                    public void run()
                    {
                        sendMail();
                    }

                };
                thread.start();
                terminate();
            }
        }


        SendListener()
        {
        }
    }

    class CancelListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            terminate();
        }

        CancelListener()
        {
        }
    }


    public AddressDlg(Frame frame)
    {
        super(frame);
        login = null;
        cSel = 0;
        ivjContentsPane = null;
        ivjAddButton = null;
        ivjEmailLabel = null;
        ivjEmailList = null;
        ivjEmailText = null;
        ivjNameText = null;
        ivjRealNameLabel = null;
        ivjRemoveButton = null;
        ivjSendButton = null;
        ivjListLabel = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjIntroLabel1 = null;
        ivjIntroLabel2 = null;
        ivjIntroLabel3 = null;
        ivjIntroLabel4 = null;
        ivjIntroLabel5 = null;
        ivjCancelButton = null;
        initialize();
    }

    public AddressDlg(Frame frame, String s)
    {
        super(frame);
        login = null;
        cSel = 0;
        ivjContentsPane = null;
        ivjAddButton = null;
        ivjEmailLabel = null;
        ivjEmailList = null;
        ivjEmailText = null;
        ivjNameText = null;
        ivjRealNameLabel = null;
        ivjRemoveButton = null;
        ivjSendButton = null;
        ivjListLabel = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjIntroLabel1 = null;
        ivjIntroLabel2 = null;
        ivjIntroLabel3 = null;
        ivjIntroLabel4 = null;
        ivjIntroLabel5 = null;
        ivjCancelButton = null;
        login = s;
        initialize();
    }

    private NZButton getAddButton()
    {
        if(ivjAddButton == null)
            try
            {
                ivjAddButton = new NZButton("add");
                ivjAddButton.setLocation(274, 71);
                ivjAddButton.setEnabled(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjAddButton;
    }

    private NZButton getCancelButton()
    {
        if(ivjCancelButton == null)
            try
            {
                ivjCancelButton = new NZButton("cancel");
                ivjCancelButton.setLocation(218, 292);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCancelButton;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                getContentsPane().add(getPanel1(), getPanel1().getName());
                getContentsPane().add(getPanel2(), getPanel2().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private Label getEmailLabel()
    {
        if(ivjEmailLabel == null)
            try
            {
                ivjEmailLabel = new Label();
                ivjEmailLabel.setName("EmailLabel");
                ivjEmailLabel.setText(resNZResource.getString("Enter_each_email_address_b"));
                ivjEmailLabel.setBounds(8, 48, 191, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjEmailLabel;
    }

    private java.awt.List getEmailList()
    {
        if(ivjEmailList == null)
            try
            {
                ivjEmailList = new java.awt.List();
                ivjEmailList.setName("EmailList");
                ivjEmailList.setBackground(SystemColor.activeCaptionText);
                ivjEmailList.setBounds(11, 136, 258, 144);
                ivjEmailList.setMultipleMode(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjEmailList;
    }

    private String getEmailString(String as[])
    {
        String s = new String();
        for(int i = 0; i < as.length; i++)
        {
            s = s.concat(as[i]);
            s = s.concat("; ");
        }

        return s;
    }

    private TextField getEmailText()
    {
        if(ivjEmailText == null)
            try
            {
                ivjEmailText = new TextField();
                ivjEmailText.setName("EmailText");
                ivjEmailText.setSelectionEnd(0);
                ivjEmailText.setBackground(SystemColor.activeCaptionText);
                ivjEmailText.setBounds(11, 71, 258, 23);
                ivjEmailText.setSelectionStart(1);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjEmailText;
    }

    private Label getIntroLabel1()
    {
        if(ivjIntroLabel1 == null)
            try
            {
                ivjIntroLabel1 = new Label();
                ivjIntroLabel1.setName("IntroLabel1");
                ivjIntroLabel1.setAlignment(0);
                ivjIntroLabel1.setText(resNZResource.getString("NetZero_has_made_it_easy_f"));
                ivjIntroLabel1.setBounds(5, 2, 336, 14);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIntroLabel1;
    }

    private Label getIntroLabel2()
    {
        if(ivjIntroLabel2 == null)
            try
            {
                ivjIntroLabel2 = new Label();
                ivjIntroLabel2.setName("IntroLabel2");
                ivjIntroLabel2.setAlignment(0);
                ivjIntroLabel2.setText(resNZResource.getString("your_new_email_address!__E"));
                ivjIntroLabel2.setBounds(5, 18, 336, 14);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIntroLabel2;
    }

    private Label getIntroLabel3()
    {
        if(ivjIntroLabel3 == null)
            try
            {
                ivjIntroLabel3 = new Label();
                ivjIntroLabel3.setName("IntroLabel3");
                ivjIntroLabel3.setAlignment(0);
                ivjIntroLabel3.setText(resNZResource.getString("friends_know_you_by).___Fo"));
                ivjIntroLabel3.setBounds(5, 34, 336, 14);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIntroLabel3;
    }

    private Label getIntroLabel4()
    {
        if(ivjIntroLabel4 == null)
            try
            {
                ivjIntroLabel4 = new Label();
                ivjIntroLabel4.setName("IntroLabel4");
                ivjIntroLabel4.setAlignment(0);
                ivjIntroLabel4.setText(resNZResource.getString("in_his_or_her_email_addres"));
                ivjIntroLabel4.setBounds(5, 50, 336, 14);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIntroLabel4;
    }

    private Label getIntroLabel5()
    {
        if(ivjIntroLabel5 == null)
            try
            {
                ivjIntroLabel5 = new Label();
                ivjIntroLabel5.setName("IntroLabel5");
                ivjIntroLabel5.setAlignment(0);
                ivjIntroLabel5.setText(resNZResource.getString("to_add_it_to_the_list._Whe"));
                ivjIntroLabel5.setBounds(5, 66, 336, 14);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIntroLabel5;
    }

    private Label getListLabel()
    {
        if(ivjListLabel == null)
            try
            {
                ivjListLabel = new Label();
                ivjListLabel.setName("ListLabel");
                ivjListLabel.setText(resNZResource.getString("Notify_the_following_peopl"));
                ivjListLabel.setBounds(8, 109, 288, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjListLabel;
    }

    private TextField getNameText()
    {
        if(ivjNameText == null)
            try
            {
                ivjNameText = new TextField();
                ivjNameText.setName("NameText");
                ivjNameText.setBackground(SystemColor.activeCaptionText);
                ivjNameText.setBounds(134, 14, 135, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjNameText;
    }

    private Panel getPanel1()
    {
        if(ivjPanel1 == null)
            try
            {
                ivjPanel1 = new Panel();
                ivjPanel1.setName("Panel1");
                ivjPanel1.setLayout(null);
                ivjPanel1.setBounds(4, 95, 341, 321);
                getPanel1().add(getNameText(), getNameText().getName());
                getPanel1().add(getEmailText(), getEmailText().getName());
                getPanel1().add(getAddButton(), getAddButton().getName());
                getPanel1().add(getEmailList(), getEmailList().getName());
                getPanel1().add(getRemoveButton(), getRemoveButton().getName());
                getPanel1().add(getListLabel(), getListLabel().getName());
                getPanel1().add(getEmailLabel(), getEmailLabel().getName());
                getPanel1().add(getRealNameLabel(), getRealNameLabel().getName());
                getPanel1().add(getSendButton(), getSendButton().getName());
                getPanel1().add(getCancelButton(), getCancelButton().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel1;
    }

    private Panel getPanel2()
    {
        if(ivjPanel2 == null)
            try
            {
                ivjPanel2 = new Panel();
                ivjPanel2.setName("Panel2");
                ivjPanel2.setLayout(null);
                ivjPanel2.setBounds(4, 9, 341, 85);
                getPanel2().add(getIntroLabel1(), getIntroLabel1().getName());
                getPanel2().add(getIntroLabel2(), getIntroLabel2().getName());
                getPanel2().add(getIntroLabel3(), getIntroLabel3().getName());
                getPanel2().add(getIntroLabel4(), getIntroLabel4().getName());
                getPanel2().add(getIntroLabel5(), getIntroLabel5().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel2;
    }

    private Label getRealNameLabel()
    {
        if(ivjRealNameLabel == null)
            try
            {
                ivjRealNameLabel = new Label();
                ivjRealNameLabel.setName("RealNameLabel");
                ivjRealNameLabel.setText(resNZResource.getString("Enter_your_real_name_"));
                ivjRealNameLabel.setBounds(8, 13, 126, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjRealNameLabel;
    }

    private NZButton getRemoveButton()
    {
        if(ivjRemoveButton == null)
            try
            {
                ivjRemoveButton = new NZButton("remove");
                ivjRemoveButton.setLocation(274, 136);
                ivjRemoveButton.setEnabled(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjRemoveButton;
    }

    private NZButton getSendButton()
    {
        if(ivjSendButton == null)
            try
            {
                ivjSendButton = new NZButton("send");
                ivjSendButton.setLocation(81, 292);
                ivjSendButton.setEnabled(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjSendButton;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        ivjEmailText.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent keyevent)
            {
                if(keyevent.getKeyCode() == 10)
                {
                    getEmailList().add(getEmailText().getText().trim());
                    getAddButton().setEnabled(false);
                    getEmailText().setText(null);
                    if(!getSendButton().isEnabled() && getNameText().getText().trim().length() > 0)
                        getSendButton().setEnabled(true);
                }
            }

        });
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                terminate();
            }

            public void windowOpened(WindowEvent windowevent)
            {
                OSDetectNative.updateProcessIcons();
            }

        });
        getCancelButton().addActionListener(new CancelListener());
        getAddButton().addActionListener(new AddListener());
        getRemoveButton().addActionListener(new RemoveListener());
        getSendButton().addActionListener(new SendListener());
        getNameText().addTextListener(new NameTextListener());
        getEmailText().addTextListener(new AddressTextListener());
        getEmailList().addItemListener(new MyItemListener());
    }

    private void initialize()
    {
        setName("AddressDlg");
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(349, 443);
        setTitle(resNZResource.getString("Notify_Others_of_Your_NetZ"));
        add(getContentsPane(), "Center");
        ZCast.centerComponent(this);
        initConnections();
        getNameText().requestFocus();
        setModal(true);
    }

    public static void main(String args[])
    {
        if(args.length < 1)
        {
            ZCast.displayDebug("missing parameter.");
            return;
        } else
        {
            showDlg(args[0]);
            System.exit(0);
            return;
        }
    }

    private String[] parseAddresses(String s)
    {
        String as[] = null;
        if(s != null && s.length() > 1)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
            int i = stringtokenizer.countTokens();
            as = new String[i];
            int j = 0;
            while(stringtokenizer.hasMoreTokens()) 
                as[j++] = stringtokenizer.nextToken().trim();
        }
        return as;
    }

    private void sendMail()
    {
        try
        {
            String s = EmailMsgDlg.getEmailString();
            String as[] = parseAddresses(s);
            SmtpClient smtpclient = new SmtpClient("smtp.netzero.net");
            smtpclient.from(login + "@netzero.net");
            for(int i = 0; i < as.length; i++)
                smtpclient.to(as[i]);

            PrintStream printstream = smtpclient.startMessage();
            String s1 = EmailMsgDlg.getSubject();
            String s2 = EmailMsgDlg.getMsg();
            printstream.print("Subject: " + s1 + "\n");
            printstream.print("Date: " + new Date() + "\n");
            printstream.print("From: " + login + "@netzero.net" + "\n");
            printstream.println("");
            printstream.print(s2);
            printstream.flush();
            smtpclient.closeServer();
        }
        catch(Exception _ex) { }
    }

    public static void showDlg(String s)
    {
        AddressDlg addressdlg = new AddressDlg(new Frame(), s);
        addressdlg.setVisible(true);
        addressdlg.dispose();
        addressdlg = null;
    }

    private void terminate()
    {
        dispose();
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private String login;
    private int cSel;
    private Panel ivjContentsPane;
    private NZButton ivjAddButton;
    private Label ivjEmailLabel;
    private java.awt.List ivjEmailList;
    private TextField ivjEmailText;
    private TextField ivjNameText;
    private Label ivjRealNameLabel;
    private NZButton ivjRemoveButton;
    private NZButton ivjSendButton;
    private Label ivjListLabel;
    private Panel ivjPanel1;
    private Panel ivjPanel2;
    private Label ivjIntroLabel1;
    private Label ivjIntroLabel2;
    private Label ivjIntroLabel3;
    private Label ivjIntroLabel4;
    private Label ivjIntroLabel5;
    private NZButton ivjCancelButton;














}
