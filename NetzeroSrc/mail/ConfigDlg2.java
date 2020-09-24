// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigDlg2.java

package mail;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.EventObject;
import java.util.ResourceBundle;
import jclass.bwt.JCContainer;
import jclass.bwt.JCGroupBox;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package mail:
//            Dll4Mail, Messenger, EmailCfg, MessNEudora

public class ConfigDlg2 extends Dialog
    implements ActionListener
{

    public ConfigDlg2(Frame frame)
    {
        super(frame);
        cfg = null;
        login = null;
        backButton = null;
        cancelButton = null;
        ivjButton2 = null;
        ivjChoice1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjChoice2 = null;
        ivjLabel2 = null;
        ivjLabel3 = null;
        ivjPanel3 = null;
        ivjTextArea1 = null;
        NewButton = null;
        ivjLabel4 = null;
        ivjLabel5 = null;
        ivjLabel6 = null;
        windowIconFile = "images\\icon.gif";
        iconFile = "images/anima/left_anima.gif";
        changeIcon(frame);
        initialize();
    }

    public ConfigDlg2(Frame frame, String s)
    {
        super(frame);
        cfg = null;
        login = null;
        backButton = null;
        cancelButton = null;
        ivjButton2 = null;
        ivjChoice1 = null;
        ivjContentsPane = null;
        ivjLabel1 = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjChoice2 = null;
        ivjLabel2 = null;
        ivjLabel3 = null;
        ivjPanel3 = null;
        ivjTextArea1 = null;
        NewButton = null;
        ivjLabel4 = null;
        ivjLabel5 = null;
        ivjLabel6 = null;
        windowIconFile = "images\\icon.gif";
        iconFile = "images/anima/left_anima.gif";
        login = s;
        changeIcon(frame);
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getCancelButton())
            cancelAction();
        if(actionevent.getSource() == getNextButton())
        {
            if(getPanel2().isVisible())
                nextButtonAction();
            else
            if(getPanel3().isVisible())
                profileNextButtonAction();
            getBackButton().setEnabled(true);
        }
        if(actionevent.getSource() == getNewButton())
            newButtonAction();
        if(actionevent.getSource() == getBackButton())
        {
            getBackButton().setEnabled(false);
            getBackButton().transferFocus();
            getPanel3().setVisible(false);
            ivjPanel3.removeAll();
            ivjPanel3 = null;
            ivjTextArea1 = null;
            ivjLabel2 = null;
            ivjLabel5 = null;
            getPanel2().setVisible(true);
        }
    }

    private void cancelAction()
    {
        terminate();
    }

    private void changeIcon(Frame frame)
    {
        try
        {
            File file = new File(windowIconFile);
            if(file.exists())
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(windowIconFile));
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    private NZButton getBackButton()
    {
        if(backButton == null)
            try
            {
                backButton = new NZButton("back");
                backButton.setLocation(110, 30);
                backButton.setEnabled(false);
                backButton.setVisible(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return backButton;
    }

    private JCGroupBox getBottomBox()
    {
        JCGroupBox jcgroupbox = new JCGroupBox();
        jcgroupbox.setName("BottomBox");
        jcgroupbox.setLayout(new FlowLayout(2));
        jcgroupbox.setShadowThickness(2);
        jcgroupbox.setBounds(10, 330, 452, 42);
        jcgroupbox.add(getBackButton());
        jcgroupbox.add(getNextButton());
        jcgroupbox.add(getCancelButton());
        return jcgroupbox;
    }

    private Panel getButtonPanel()
    {
        if(ivjPanel1 == null)
            try
            {
                ivjPanel1 = new Panel();
                ivjPanel1.setName("Panel1");
                ivjPanel1.setLayout(null);
                ivjPanel1.setBounds(0, 0, 20, 80);
                ivjPanel1.add(getCancelButton());
                ivjPanel1.add(getNextButton());
                ivjPanel1.add(getBackButton());
                ivjPanel1.setVisible(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel1;
    }

    private NZButton getCancelButton()
    {
        if(cancelButton == null)
            try
            {
                cancelButton = new NZButton("cancel");
                cancelButton.setLocation(270, 30);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cancelButton;
    }

    private Choice getChoice1()
    {
        if(ivjChoice1 == null)
            try
            {
                ivjChoice1 = new Choice();
                ivjChoice1.setName("Choice1");
                ivjChoice1.setBounds(47, 120, 196, 131);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjChoice1;
    }

    private Choice getChoice2()
    {
        if(ivjChoice2 == null)
            try
            {
                ivjChoice2 = new Choice();
                ivjChoice2.setName("Choice2");
                ivjChoice2.setBounds(105, 220, 160, 100);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjChoice2;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(new BorderLayout());
                ivjContentsPane.setBounds(120, 0, 330, 330);
                ivjContentsPane.setVisible(true);
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
                ivjLabel1.setFont(new Font("dialog", 0, 12));
                ivjLabel1.setText(resNZResource.getString("Please_confirm_the_e-mail_"));
                ivjLabel1.setBounds(47, 60, 241, 23);
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
                ivjLabel2.setText(resNZResource.getString("Select_a_profile_to_config"));
                ivjLabel2.setBounds(105, 184, 200, 20);
                if(ivjLabel5 == null)
                    try
                    {
                        ivjLabel5 = new Label();
                        ivjLabel5.setName("Label5");
                        ivjLabel5.setText(resNZResource.getString("select_NEW_to_create_a_new"));
                        ivjLabel5.setBounds(105, 199, 200, 20);
                    }
                    catch(Throwable throwable)
                    {
                        handleException(throwable);
                    }
            }
            catch(Throwable throwable1)
            {
                handleException(throwable1);
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
                ivjLabel3.setText(resNZResource.getString("or_a_personality_(Eudora_P"));
                ivjLabel3.setBounds(10, 253, 161, 23);
                ivjLabel3.setVisible(false);
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
                ivjLabel4.setFont(new Font("dialog", 1, 12));
                ivjLabel4.setText(resNZResource.getString("Note_"));
                ivjLabel4.setBounds(30, 22, 45, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel4;
    }

    private Label getLabel6()
    {
        if(ivjLabel6 == null)
            try
            {
                ivjLabel6 = new Label();
                ivjLabel6.setName("Label6");
                ivjLabel6.setFont(new Font("dialog", 0, 12));
                ivjLabel6.setText(resNZResource.getString("use_with_your_FREE_e-mail_"));
                ivjLabel6.setBounds(47, 80, 241, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel6;
    }

    private NZButton getNewButton()
    {
        if(NewButton == null)
            try
            {
                NewButton = new NZButton("new");
                NewButton.setBounds(270, 220, 50, 30);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return NewButton;
    }

    private NZButton getNextButton()
    {
        if(ivjButton2 == null)
            try
            {
                ivjButton2 = new NZButton("next");
                ivjButton2.setLocation(170, 30);
                ivjButton2.setEnabled(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjButton2;
    }

    private Panel getPanel2()
    {
        if(ivjPanel2 == null)
            try
            {
                ivjPanel2 = new Panel();
                ivjPanel2.setName("Panel2");
                ivjPanel2.setLayout(null);
                ivjPanel2.setBackground(Color.lightGray);
                ivjPanel2.setBounds(0, 0, 330, 230);
                ivjPanel2.add(getLabel1(), getLabel1().getName());
                ivjPanel2.add(getLabel6(), getLabel6().getName());
                ivjPanel2.add(getChoice1(), getChoice1().getName());
                ivjPanel2.setVisible(true);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel2;
    }

    private Panel getPanel3()
    {
        if(ivjPanel3 == null)
            try
            {
                ivjPanel3 = new Panel();
                ivjPanel3.setName("Panel3");
                ivjPanel3.setLayout(null);
                ivjPanel3.setBackground(Color.lightGray);
                ivjPanel3.setBounds(0, 0, 330, 250);
                ivjPanel3.add(getLabel2(), getLabel2().getName());
                ivjPanel3.add(getTextArea1(), getTextArea1().getName());
                ivjPanel3.add(getChoice2(), getChoice2().getName());
                ivjPanel3.add(ivjLabel5, ivjLabel5.getName());
                ivjPanel3.add(getLabel3(), getLabel3().getName());
                ivjPanel3.add(getLabel4(), getLabel4().getName());
                ivjPanel3.add(getNewButton());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel3;
    }

    private TextArea getTextArea1()
    {
        if(ivjTextArea1 == null)
            try
            {
                ivjTextArea1 = new TextArea(null, 10, 10, 1);
                ivjTextArea1.setName("TextArea1");
                if(configName == null)
                {
                    ivjTextArea1.setText(resNZResource.getString("Messenger__Netscape_Commun"));
                } else
                {
                    if(configName.trim().equals("mail.Messenger"))
                        ivjTextArea1.setText(resNZResource.getString("Messenger__Netscape_Commun1"));
                    if(configName.trim().equals("mail.Eudora"))
                    {
                        ivjTextArea1.setText(resNZResource.getString("Eudora__Eudora_Pro_4.x_(on"));
                        ivjLabel2.setText(resNZResource.getString("Select_a_personality_to_co"));
                        ivjLabel5.setText(resNZResource.getString("'NEW'_to_create_a_new_pers"));
                    }
                }
                ivjTextArea1.setBounds(30, 45, 290, 138);
                ivjTextArea1.setEditable(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextArea1;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
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
        getCancelButton().addActionListener(this);
        getNextButton().addActionListener(this);
        getNewButton().addActionListener(this);
        getBackButton().addActionListener(this);
    }

    private void initialize()
    {
        setName("ConfigDlg2");
        setTitle(resNZResource.getString("NetZero_EZ_Mail_Setup"));
        setSize(467, 380);
        setLayout(null);
        setResizable(false);
        add(setGif());
        add(getBottomBox());
        add(getContentsPane());
        getContentsPane().add(getPanel2(), "Center");
        getPanel2().setVisible(true);
        initConnections();
        showMailPanel();
        ZCast.centerComponent(this);
    }

    public static void main(String args[])
    {
        if(args.length < 1)
        {
            return;
        } else
        {
            testMode = true;
            ConfigDlg2 configdlg2 = new ConfigDlg2(new Frame(), args[0]);
            configdlg2.setModal(true);
            configdlg2.setVisible(true);
            System.exit(0);
            return;
        }
    }

    private void newButtonAction()
    {
        Object obj = null;
        int i = 0;
        String as[] = new String[2];
        as[0] = "back";
        as[1] = "continue";
        if(configName.equals("mail.Messenger"))
        {
            String s = resNZResource.getString("This_will_create_a_new_Net") + login + resNZResource.getString("'_for_use_with_NetZero_inc");
            String s2 = resNZResource.getString("Creating_New_Profile");
            i = NZDialogBox.showMessageDialog(s2, s, 0, as);
        }
        if(configName.equals("mail.Eudora"))
        {
            String s1 = resNZResource.getString("This_will_create_a_new_Eud") + login + resNZResource.getString("'_for_use_with_NetZero_inc1");
            String s3 = resNZResource.getString("Creating_New_Personality");
            i = NZDialogBox.showMessageDialog(s3, s1, 0, as);
        }
        ((MessNEudora)cfg).setNewProfile(true);
        if(i == 1)
        {
            ZCast.displayDebug("index==" + i);
            setupAccount();
        }
    }

    private void nextButtonAction()
    {
        String as[][] = {
            {
                resNZResource.getString("Netscape_Messenger"), "mail.Messenger"
            }, {
                resNZResource.getString("Outlook_Express"), "mail.OutlookX"
            }, {
                resNZResource.getString("Eudora"), "mail.Eudora"
            }, {
                resNZResource.getString("Microsoft_Outlook"), "mail.Outlook"
            }
        };
        String s = getChoice1().getSelectedItem();
        int i;
        for(i = 0; i < as.length; i++)
            if(as[i][0].equals(s))
                break;

        if(i >= as.length)
        {
            String as1[] = new String[1];
            as1[0] = "ok";
            String s1 = resNZResource.getString("Configuration_failed");
            String s2 = resNZResource.getString("Unable_to_configure_") + s + ".";
            NZDialogBox.showMessageDialog(s1, s2, 2, as1);
        } else
        {
            try
            {
                Class class1 = Class.forName(as[i][1]);
                Class aclass[] = {
                    class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String
                };
                Constructor constructor = class1.getConstructor(aclass);
                if(login == null)
                    throw new Exception(resNZResource.getString("login_name_is_null"));
                Object aobj[] = {
                    login
                };
                cfg = (EmailCfg)constructor.newInstance(aobj);
                String s3 = class1.getName().trim();
                configName = s3;
                configName = s3;
                if(s3.equals("mail.Messenger") && !((Messenger)cfg).isVersionAbleToConfigure())
                {
                    String as2[] = new String[1];
                    as2[0] = "ok";
                    String s4 = resNZResource.getString("Configuration_failed");
                    String s6 = resNZResource.getString("Unable_to_configure_the_cu");
                    NZDialogBox.showMessageDialog(s4, s6, 2, as2);
                    terminate();
                }
                if(s3.equals("mail.Messenger") || s3.equals("mail.Eudora"))
                    showProfilePanel();
                else
                if(cfg.isAccountAlreadyDefined())
                {
                    String as3[] = new String[1];
                    as3[0] = "ok";
                    String s5 = resNZResource.getString("Configuration_failed");
                    String s7 = resNZResource.getString("Account_already_exists._Op");
                    NZDialogBox.showMessageDialog(s5, s7, 2, as3);
                    cfg.sendEMCFxAction();
                    terminate();
                } else
                {
                    setupAccount();
                }
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
                showFailure(exception);
                terminate();
            }
        }
    }

    private void profileNextButtonAction()
    {
        Object obj = null;
        int i = 0;
        String as[] = new String[2];
        as[0] = "back";
        as[1] = "continue";
        if(configName.equals("mail.Messenger"))
        {
            String s = resNZResource.getString("This_will_configure_your_N") + getChoice2().getSelectedItem() + resNZResource.getString("'_for_NetZero_e-mail_servi");
            String s2 = resNZResource.getString("Changing_Existing_Profile");
            i = NZDialogBox.showMessageDialog(s2, s, 3, as);
        }
        if(configName.equals("mail.Eudora"))
        {
            String s1 = resNZResource.getString("This_will_configure_your_E") + getChoice2().getSelectedItem() + resNZResource.getString("'_for_NetZero_e-mail_servi1");
            String s3 = resNZResource.getString("Changing_Existing_Personal");
            i = NZDialogBox.showMessageDialog(s3, s1, 3, as);
        }
        ((MessNEudora)cfg).setProfileName(getChoice2().getSelectedItem());
        ZCast.displayDebug("index==" + i);
        if(i == 1)
            setupAccount();
    }

    private AgComponent setGif()
    {
        AgComponent agcomponent = new AgComponent();
        agcomponent.initialize(null, "images/anima/left_anima.gif");
        agcomponent.setLocation(10, 28);
        return agcomponent;
    }

    private void setupAccount()
    {
        try
        {
            cfg.setupAccount();
            if(!testMode)
                cfg.sendEMCFxAction();
            String as[] = new String[1];
            as[0] = "ok";
            String s = resNZResource.getString("Congratulations!");
            if(configName.equals("mail.Messenger"))
            {
                String s1 = resNZResource.getString("Your_Netscape_email_has_be");
                NZDialogBox.showMessageDialog(s, s1, 0, as);
            } else
            if(configName.equals("mail.Eudora"))
            {
                String s2 = resNZResource.getString("Your_Eudora_email_has_been");
                NZDialogBox.showMessageDialog(s, s2, 0, as);
            } else
            if(configName.equals("mail.OutlookX"))
            {
                String s3 = resNZResource.getString("Your_Outlook_Express_email");
                NZDialogBox.showMessageDialog(s, s3, 0, as);
            } else
            if(configName.equals("mail.Outlook"))
            {
                String s4 = resNZResource.getString("Your_Outlook_email_has_bee");
                NZDialogBox.showMessageDialog(s, s4, 0, as);
            } else
            {
                String s5 = resNZResource.getString("Your_email_has_bee");
                NZDialogBox.showMessageDialog(s, s5, 0, as);
            }
        }
        catch(Exception exception1)
        {
            showFailure(exception1);
        }
        finally
        {
            terminate();
        }
    }

    private void showFailure(Exception exception)
    {
        String as[] = new String[1];
        as[0] = "ok";
        String s = resNZResource.getString("Unable_to_configure_the_em");
        String s1 = exception.toString();
        NZDialogBox.showMessageDialog(s, s1, 2, as);
    }

    protected void showMailPanel()
    {
        String as[] = Dll4Mail.getRegSubkeys("HKEY_LOCAL_MACHINE", "SOFTWARE\\Clients\\Mail");
        for(int i = 0; i < as.length; i++)
            ZCast.displayDebug("apps is " + as[i]);

        if(as.length > 0)
        {
            for(int j = 0; j < as.length; j++)
                if(as[j].trim().indexOf("Outlook") >= 0 || as[j].trim().indexOf("Messenger") >= 0 || as[j].trim().indexOf("Eudora") >= 0)
                    getChoice1().addItem(as[j]);

            String s1 = OSDetectNative.getRegValue("SOFTWARE\\Clients\\Mail", "", false);
            if(s1 != null && (s1.trim().indexOf("Outlook") >= 0 || s1.trim().indexOf("Messenger") >= 0 || s1.trim().indexOf("Eudora") >= 0))
                getChoice1().select(s1);
            getNextButton().setEnabled(true);
        } else
        {
            String s = resNZResource.getString("No_Default_Email_Client");
            String s2 = resNZResource.getString("Unable_to_set_the_default_");
            String as1[] = new String[1];
            as1[0] = "ok";
            NZDialogBox.showMessageDialog(s, s2, 2, as1);
        }
    }

    protected void showProfilePanel()
    {
        if(ivjPanel3 == null)
        {
            getContentsPane().add(getPanel3(), "Center");
            getPanel2().setVisible(false);
            getPanel3().setVisible(true);
        }
        try
        {
            String as[] = ((MessNEudora)cfg).getProfiles();
            getChoice2().removeAll();
            if(as.length > 0)
            {
                for(int i = 0; i < as.length; i++)
                    getChoice2().addItem(as[i]);

            }
            boolean flag = ((MessNEudora)cfg).canCreateNewProf();
            if(flag)
            {
                for(int j = 0; j < as.length; j++)
                {
                    if(!as[j].equalsIgnoreCase(login))
                        continue;
                    flag = false;
                    break;
                }

            }
            if(flag)
                getNewButton().setEnabled(true);
            else
                getNewButton().setEnabled(false);
        }
        catch(Exception _ex) { }
        getContentsPane().add(getPanel3(), "Center");
        getPanel2().setVisible(false);
        getPanel3().setVisible(true);
        repaint();
        getNewButton().setEnabled(true);
        NewButton.setVisible(true);
    }

    private void terminate()
    {
        dispose();
    }

    public void update(Graphics g)
    {
        paint(g);
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
    private EmailCfg cfg;
    private String login;
    private NZButton backButton;
    private NZButton cancelButton;
    private NZButton ivjButton2;
    private Choice ivjChoice1;
    private Panel ivjContentsPane;
    private Label ivjLabel1;
    private Panel ivjPanel1;
    private Panel ivjPanel2;
    private Choice ivjChoice2;
    private Label ivjLabel2;
    private Label ivjLabel3;
    private Panel ivjPanel3;
    private TextArea ivjTextArea1;
    private NZButton NewButton;
    private Label ivjLabel4;
    private Label ivjLabel5;
    private Label ivjLabel6;
    private String windowIconFile;
    private String iconFile;
    public static String configName = null;
    private static boolean testMode = false;
    static Class class$java$lang$String; /* synthetic field */


}
