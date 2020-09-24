// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Startup.java

package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import jclass.bwt.JCActionEvent;
import jclass.bwt.JCActionListener;
import jclass.bwt.JCComponent;
import jclass.bwt.JCContainer;
import jclass.bwt.JCGroupBox;
import jclass.bwt.JCItemEvent;
import jclass.bwt.JCItemListener;
import jclass.bwt.JCLabel;
import jclass.bwt.JCTextArea;
import jclass.bwt.JCTextComponent;
import jclass.bwt.JCTextEvent;
import jclass.bwt.JCTextField;
import jclass.bwt.JCTextListener;
import nzcom.BrowserDisplay;
import nzcom.ConfigParams;
import nzcom.Initializer;
import nzcom.OSDetectNative;
import nzcom.Profile;
import nzcom.RASWinNative;
import nzcom.SoftwareExit;
import nzcom.ZCast;
import pool.MarqueePool;
import pool.ZeroInPool;
import pool.ZeroOutPool;
import qprofile.MemberAnswer;
import qprofile.ProfileNavigator;
import qprofile.ProfileRapper;
import tcpBinary.TransactionResponse;
import transaction.client.TransactionThread;
import video.TviPlayer;
import video.VideoRenderer;

// Referenced classes of package gui:
//            AgCanvas, PhoneRec, ZeroIn, DialObject, 
//            PhoneNumberSetting, DialGroups, NZButton, NZDialogBox, 
//            MemberRecs, DeleteGroupDialog, NZFont, NZLabel, 
//            NZColor, AgComponent, NewUserDialog

public class Startup extends Dialog
    implements ActionListener, ItemListener, KeyListener, MouseListener, FocusListener, WindowListener, TextListener, JCItemListener, JCTextListener, JCActionListener
{
    class NodeType
    {

        int back;
        int next;

        NodeType(int i, int j)
        {
            back = 0;
            next = 0;
            back = i;
            next = j;
        }
    }


    public Startup(Frame frame, boolean flag, Profile profile)
    {
        super(frame, flag);
        modifyFlag = false;
        savedGroups = false;
        prof = null;
        pnav = null;
        firstTime = true;
        getProfileFromServer = true;
        ivjPanelFree = null;
        statusLabel = null;
        ivjCancelFreeButton = null;
        ivjExitFreeButton = null;
        backButton = null;
        nextButton = null;
        cancelButton = null;
        bottomBox = null;
        leftImage = null;
        topImage = null;
        currentNode = 0;
        path = null;
        preDialPanel = null;
        preDialGroupBox = null;
        preDialPhoneList = null;
        ignoreCheckbox = null;
        ivjContentsPane = null;
        ivjPanel1 = null;
        panelBottomAccount = null;
        groupBoxBottom = null;
        checkboxNewAccount = null;
        checkboxCurrentAccount = null;
        checkboxGroupAccount = null;
        textFieldID = null;
        panelSlide2A = null;
        panelBottomAccount2A = null;
        panelSlide7A = null;
        textFieldNickName7A = null;
        textFieldAreaCode7A = null;
        textFieldFirstThree7A = null;
        textFieldLastFour7A = null;
        panelSlide7B = null;
        groupsList7B = null;
        phoneList7B = null;
        textFieldNickName7B = null;
        textFieldAreaCode7B = null;
        textFieldFirstThree7B = null;
        showACError = false;
        refreshAC = true;
        refreshCities = true;
        curState = null;
        panelSlideEightA = null;
        panelBottomAccountEightA = null;
        groupBoxLeftEightA = null;
        groupBoxRightEightA = null;
        listCitiesEightA = null;
        listPhoneListEightA = null;
        buttonAddEightA = null;
        choiceGroupEightA = null;
        buttonDelEightA = null;
        buttonDelConnectA = null;
        buttonDelNumbersEightA = null;
        buttonSettingEightA = null;
        choiceAreaCodesEightA = null;
        panelSlideTenA = null;
        panelBottomAccountTenA = null;
        checkboxDialTenA = null;
        checkboxDisableCallTenA = null;
        checkboxCustomTenA = null;
        checkboxMuteModem = null;
        textFieldATenA = null;
        textFieldBTenA = null;
        textFieldCTenA = null;
        panelSlideElevenA = null;
        listPhoneListElevenA = null;
        panelSlideTwelveA = null;
        buttonFinishTwelveA = null;
        buttonBackSignupTwelveA = null;
        groupBoxBottomTwelveA = null;
        connectOptionsBox = null;
        panelConnectA = null;
        groupBoxBottomConnectA = null;
        buttonCancelConnectA = null;
        buttonModifyConnectA = null;
        buttonRemoveID = null;
        buttonConnectA = null;
        checkboxSavePasswordConnectA = null;
        textFieldPasswordConnectA = null;
        choiceDialingFromConnectA = null;
        choiceMemberID = null;
        networkCheckbox = null;
        panelSlideThreeA = null;
        checkboxAcceptSlideThreeA = null;
        textAreaSlide3A = null;
        panelSlideFourA = null;
        textFieldIdSlideFourA = null;
        panelSlideFiveA = null;
        textFieldPwdSlideFiveA = null;
        textFieldConfirmSlideFiveA = null;
        panelSlideSixA = null;
        textFieldAnswerSlideSixA = null;
        choiceQuestionSlideSixA = null;
        zrIn = null;
        m_parent = null;
        show8A = false;
        showCon = false;
        allowConnect = true;
        m_parent = frame;
        prof = profile;
        initialize();
        me = this;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getActionCommand().equals("11Anext"))
        {
            DialGroups.writeGroups();
            savedGroups = true;
            getNextButton().setActionCommand("next");
            processNext();
        }
        if(actionevent.getActionCommand().equals("TenABackTenA"))
        {
            save10A();
            getBackButton().setActionCommand("back");
            processBack();
        }
        if(actionevent.getActionCommand().equals("Process7B"))
        {
            ZCast.displayDebug("Next 7B!!");
            String s = groupsList7B.getSelectedItem();
            ZCast.displayDebug("selected item is " + s);
            if(s.equals(ADD_GROUP7B))
            {
                ZCast.displayDebug("adding group");
                if(validGroupName(getTextFieldNickName7B().getText().trim()))
                {
                    ZCast.displayDebug("valid name");
                    if(DialGroups.addingDup(getTextFieldNickName7B().getText().trim()))
                    {
                        String as1[] = {
                            resNZResource.getString("Ok")
                        };
                        NZDialogBox.showMessageDialog(resNZResource.getString("Duplicate_Phone_List"), resNZResource.getString("You_already_have_a_phone_l"), 0, as1);
                    } else
                    {
                        ZCast.displayDebug("\nCall add group");
                        DialGroups.setCurrentName(getTextFieldNickName7B().getText().trim());
                        DialGroups.setDialFrom(getTextFieldAreaCode7B().getText() + getTextFieldFirstThree7B().getText());
                        getNextButton().setActionCommand("next");
                        processNext();
                    }
                } else
                {
                    ZCast.displayDebug("invalid group name");
                }
            } else
            {
                getNextButton().setActionCommand("next");
                processNext();
            }
        }
        if(actionevent.getActionCommand().equals("Modify7A"))
        {
            ZCast.displayDebug("\n\tprocess Modify7A\n");
            ZCast.displayDebug("\n\tprocess Modify7A\n");
            if(DialGroups.addingDup(getTextFieldNickName7A().getText().trim()))
            {
                String as[] = {
                    resNZResource.getString("Ok")
                };
                NZDialogBox.showMessageDialog(resNZResource.getString("Duplicate_Phone_List"), resNZResource.getString("You_already_have_a_phone_l"), 0, as);
            } else
            {
                DialGroups.setDialFrom(getTextFieldAreaCode7A().getText() + getTextFieldFirstThree7A().getText());
                DialGroups.setCurrentName(getTextFieldNickName7A().getText().trim());
                getNextButton().setActionCommand("next");
                processNext();
            }
        }
        if(actionevent.getActionCommand().equals("AddGroup7A"))
        {
            ZCast.displayDebug("\n\tAddGroup 7A!!\n");
            ZCast.displayDebug("\n\tAddGroup 7A!!\n");
            if(validGroupName(getTextFieldNickName7A().getText()))
            {
                ZCast.displayDebug("\nCall add group");
                DialGroups.setCurrentName(getTextFieldNickName7A().getText().trim());
                DialGroups.setDialFrom(getTextFieldAreaCode7A().getText() + getTextFieldFirstThree7A().getText());
            } else
            {
                ZCast.displayDebug("invalid group name");
            }
            processNext();
        }
        if(actionevent.getActionCommand().equals("Back8A"))
        {
            ZCast.displayDebug("\n\tBack 8A modifyFlag == " + modifyFlag);
            getBackButton().setActionCommand("back");
            processBack();
        }
        if(actionevent.getActionCommand().equals("Modify7B"))
        {
            ZCast.displayDebug("process Modify7B");
            DialGroups.setDialFrom(getTextFieldAreaCode7B().getText() + getTextFieldFirstThree7B().getText());
            DialGroups.setCurrentName(getTextFieldNickName7B().getText());
            getNextButton().setActionCommand("next");
            processNext();
        }
        if(actionevent.getSource() == getButtonDelConnectA())
        {
            ZCast.displayDebug("delete selected group");
            String s1 = getChoiceDialingFromConnectA().getSelectedItem();
            if(!s1.equals(resNZResource.getString("A_new_location")) && DialGroups.numGroups() > 1)
            {
                if(getCheckboxSavePasswordConnectA().getState())
                {
                    String s5 = getTextFieldPasswordConnectA().getText().trim();
                    MemberRecs.setCurrentPassword(s5);
                }
                DeleteGroupDialog.showDialog();
                requestFocus();
                if(DeleteGroupDialog.delete())
                {
                    DialGroups.deleteCurrent();
                    DialGroups.writeGroups();
                    getChoiceDialingFromConnectA().remove(getChoiceDialingFromConnectA().getSelectedIndex());
                    refreshConnectABottom();
                }
            }
            refreshButtons8A();
        }
        if(actionevent.getActionCommand().equals("PasswordNext"))
            if(validPassConfirm())
            {
                getNextButton().setActionCommand("next");
                processNext();
            } else
            {
                ZCast.displayDebug("not good");
            }
        if(actionevent.getActionCommand() == "MemberIDNext")
        {
            String s2 = getTextFieldIdSlideFourA().getText().trim();
            if(isValidMemberID(s2))
            {
                getNextButton().setActionCommand("next");
                processNext();
            }
        }
        if(actionevent.getActionCommand().equals("Panel1Next"))
        {
            ZCast.displayDebug("panel 1 next button pressed");
            firstTime = false;
            if(getCheckboxNewAccount().getState())
            {
                if(DialGroups.numGroups() > 0)
                    setPathCreateUserPickGroup();
                else
                    setPathCreateUserAndGroup();
                currentNode = 0;
                getNextButton().setActionCommand("next");
                processNext();
            } else
            {
                String s3 = getTextFieldID().getText().trim();
                ZCast.displayDebug("IDString is " + s3);
                if(MemberRecs.addingDup(s3))
                {
                    String as2[] = {
                        resNZResource.getString("Ok")
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Duplicate_Member_ID"), resNZResource.getString("You_already_have_a_Member_"), 0, as2);
                } else
                {
                    ZCast.displayDebug("add the member rec!");
                    MemberRecs.addMemberRec(s3);
                    MemberRecs.writeRecs();
                    getNextButton().setActionCommand("next");
                    processNext();
                }
            }
        }
        if(actionevent.getActionCommand().startsWith("next"))
        {
            ZCast.displayDebug("next button pressed");
            processNext();
        }
        if(actionevent.getActionCommand().equals("back"))
        {
            ZCast.displayDebug("back button pressed");
            processBack();
        }
        if(actionevent.getActionCommand().equals("cancel"))
        {
            if(actionevent.getSource() == getButtonCancelConnectA() && DialGroups.numGroups() > 0)
                DialGroups.writeGroups();
            terminateProgram();
        }
        if(actionevent.getSource() == getListCitiesEightA())
        {
            processDoubleClick(getListCitiesEightA(), actionevent.getActionCommand());
            callAddSettings8A();
        }
        if(actionevent.getSource() == getListPhoneListEightA())
        {
            processDoubleClick(getListPhoneListEightA(), actionevent.getActionCommand());
            callModifySettings8A();
        }
        if(actionevent.getSource() == getButtonModifyConnectA())
        {
            if(DialGroups.getNetworkChecked() != networkCheckbox.getState())
            {
                DialGroups.setNetworkChecked(networkCheckbox.getState());
                DialGroups.writeGroups();
            }
            if(getCheckboxSavePasswordConnectA().getState())
            {
                String s4 = getTextFieldPasswordConnectA().getText().trim();
                MemberRecs.setCurrentPassword(s4);
            }
            showACError = false;
            setPathModifyGroup();
            processNext();
        }
        if(actionevent.getSource() == getButtonFinishTwelveA())
        {
            DialGroups.writeGroups();
            buildNewMemberRec();
            MemberRecs.writeRecs();
            processNext();
        }
        if(actionevent.getSource() == getButtonRemoveID())
            removeMemberID();
        if(actionevent.getSource() == getButtonConnectA())
            connectUser(false);
        if(actionevent.getSource() == getButtonSettingEightA())
            callModifySettings8A();
        if(actionevent.getSource() == getButtonAddEightA())
            addDialogEightA();
        if(actionevent.getSource() == getButtonDelNumbersEightA())
        {
            ZCast.displayDebug("\t\t----delete phone numbers\n");
            DialGroups.deleteSelectList(getListPhoneListEightA());
            refreshButtons8A();
        }
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
        if(jcactionevent.getSource() == getButtonSettingEightA())
            callModifySettings8A();
        if(jcactionevent.getSource() == getButtonAddEightA())
            addDialogEightA();
        if(jcactionevent.getSource() == getButtonDelNumbersEightA())
        {
            ZCast.displayDebug("\t\t----delete phone numbers\n");
            DialGroups.deleteSelectList(getListPhoneListEightA());
            refreshButtons8A();
        }
    }

    public void addDialogEightA()
    {
        callAddSettings8A();
    }

    private boolean allCharAndDigits(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isLetterOrDigit(s.charAt(i)))
                return false;

        return true;
    }

    private boolean allDigits(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                return false;

        return true;
    }

    private boolean allValidGroupChars(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isLetterOrDigit(s.charAt(i)) && s.charAt(i) != '_' && s.charAt(i) != '.')
                return false;

        return true;
    }

    private boolean allValidIDChars(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isLetterOrDigit(s.charAt(i)) && s.charAt(i) != '_' && s.charAt(i) != '.')
                return false;

        return true;
    }

    private void buildNewMemberRec()
    {
        String s = getTextFieldIdSlideFourA().getText().toLowerCase().trim();
        String s1 = getTextFieldConfirmSlideFiveA().getText().toLowerCase().trim();
        boolean flag = getCheckboxSavePasswordConnectA().getState();
        String s2 = getChoiceQuestionSlideSixA().getSelectedItem();
        String s3 = getTextFieldAnswerSlideSixA().getText();
        String s4 = DialGroups.getCurrentGroupName();
        ZCast.displayDebug("\n\nNew user is:");
        ZCast.displayDebug("id is " + s);
        ZCast.displayDebug("flag is " + flag);
        ZCast.displayDebug("pass is " + s1);
        ZCast.displayDebug("q is " + s2);
        ZCast.displayDebug("a is " + s3);
        ZCast.displayDebug("curgroup is " + s4);
        MemberRecs.addMemberRec(s, s1);
        MemberRecs.setSavePassword(flag);
        MemberRecs.setQA(s2, s3);
        MemberRecs.setCurrentGroupName(s4);
        MemberRecs.setIsNewUser(true);
    }

    private void callAddSettings8A()
    {
        Vector vector = DialGroups.buildCitySelectList((String[])getListCitiesEightA().getSelectedObjects());
        if(vector != null && vector.size() > 0)
        {
            String s = DialGroups.getAreaCode();
            showACError = true;
            PhoneNumberSetting.showDialog(vector);
            requestFocus();
            fillPhoneNumbersRightEightA();
            oldAreaCode = DialGroups.getAreaCode();
            oldFirst3 = DialGroups.getEditDialFrom().substring(3);
            DialGroups.selectChoiceAreaCode(getChoiceAreaCodesEightA());
            int i = getChoiceAreaCodesEightA().getSelectedIndex();
            if(i == 0)
            {
                ZCast.displayDebug("AREA CODE NOT IN LIST");
                getChoiceAreaCodesEightA().select(1);
                if(showACError && !s.equals(DialGroups.getAreaCode()))
                {
                    String as[] = {
                        resNZResource.getString("Ok")
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Area_Code_Warning"), resNZResource.getString("The_area_code_you_selected"), 0, as);
                    showACError = false;
                }
            }
            fillCitiesLeftEightA(getChoiceAreaCodesEightA().getSelectedItem(), ConfigParams.getAllNumbers());
            showACError = false;
        }
    }

    private void callModifySettings8A()
    {
        Vector vector = DialGroups.buildModifySettingsList(getListPhoneListEightA());
        if(vector != null && vector.size() > 0)
        {
            String s = DialGroups.getAreaCode();
            showACError = true;
            PhoneNumberSetting.showDialog();
            requestFocus();
            fillPhoneNumbersRightEightA();
            DialGroups.selectChoiceAreaCode(getChoiceAreaCodesEightA());
            oldAreaCode = DialGroups.getAreaCode();
            oldFirst3 = DialGroups.getEditDialFrom().substring(3);
            int i = getChoiceAreaCodesEightA().getSelectedIndex();
            if(i == 0)
            {
                ZCast.displayDebug("AREA CODE NOT IN LIST");
                getChoiceAreaCodesEightA().select(1);
                if(showACError && !s.equals(DialGroups.getAreaCode()))
                {
                    String as[] = {
                        resNZResource.getString("Ok")
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Area_Code_Warning"), resNZResource.getString("The_area_code_you_selected"), 0, as);
                    showACError = false;
                }
            }
            fillCitiesLeftEightA(getChoiceAreaCodesEightA().getSelectedItem(), ConfigParams.getAllNumbers());
            showACError = false;
        }
    }

    public void cancelNotification(String s)
    {
        ZCast.displayDebug(">>-->>> Do Cancelling..." + s);
        if(ZCast.m_regclasses != null)
        {
            for(int i = 0; i < ZCast.m_regclasses.size(); i++)
                ((SoftwareExit)ZCast.m_regclasses.elementAt(i)).endPreProcessing();

        }
        if(Initializer.m_ulogThread != null)
            Initializer.m_ulogThread.programIsTerminating();
        DialObject.doDisconnect();
        if(ZCast.m_regclasses != null)
        {
            for(int j = 0; j < ZCast.m_regclasses.size(); j++)
                ((SoftwareExit)ZCast.m_regclasses.elementAt(j)).endPostProcessing();

        }
        String as[] = {
            resNZResource.getString("Ok")
        };
        if(s == null)
            NZDialogBox.showMessageDialog(resNZResource.getString("Connection_Failure"), resNZResource.getString("Connection_Failure"), 3, as);
        else
            NZDialogBox.showMessageDialog(resNZResource.getString("Connection_Failure"), s, 3, as);
        if(zrIn != null)
        {
            zrIn.doCancelClicked();
            zrIn = null;
        } else
        {
            showConnectA();
            setVisible(true);
        }
        initObj = null;
    }

    private void checkAreaCode(String s, TextField textfield, TextField textfield1)
    {
        System.out.println("oldAreaCode is " + oldAreaCode);
        if(s == null)
        {
            System.out.println("newstr is null, so set text to oldAreaCode");
            textfield.setText(oldAreaCode);
            return;
        }
        if(!allDigits(s))
        {
            int i = textfield.getCaretPosition();
            if(i > 0)
                i--;
            System.out.println("not all digits,so last caret position == " + i);
            textfield.setText(oldAreaCode);
            textfield.setCaretPosition(i);
            return;
        }
        if(s.length() == 3)
        {
            System.out.println("length == 3, so go to next text field");
            textfield1.requestFocus();
            return;
        }
        if(s.length() > 3)
        {
            int j = textfield.getCaretPosition();
            if(j > 0)
                j--;
            System.out.println("length > 3, so last caret position == " + j);
            textfield.setText(oldAreaCode);
            textfield.setCaretPosition(j);
            return;
        }
        if(oldAreaCode.length() == 0)
        {
            System.out.println("set oldAreaCode to newstr of" + s);
            oldAreaCode = s;
        } else
        if(allDigits(s))
        {
            System.out.println("all digits so set oldAreaCode to newstr of" + s);
            oldAreaCode = s;
            showACError = true;
        } else
        {
            int k = textfield.getCaretPosition();
            if(k > 0)
                k--;
            textfield.setText(oldAreaCode);
            textfield.setCaretPosition(k);
        }
    }

    private boolean checkBadNumbers()
    {
        boolean flag = true;
        Vector vector = DialGroups.getProblemNumbers();
        if(vector != null)
        {
            String s = new String();
            for(int i = 0; i < vector.size(); i++)
            {
                if(s.length() > 0)
                    s = s + "\n";
                PhoneRec phonerec = (PhoneRec)vector.elementAt(i);
                ZCast.displayDebug("@@@@@@@ BAD NUMBER " + phonerec.toListString());
                s = s + phonerec.toBadNumberString();
            }

            if(vector.size() > 0)
            {
                String s1 = resNZResource.getString("Warning__Possible_Dialing_");
                String as[] = {
                    resNZResource.getString("ok")
                };
                String s2 = resNZResource.getString("According_to_information_y1") + DialGroups.getAreaCode() + resNZResource.getString(".__The_following_phone_num") + s + resNZResource.getString("_n_nThis_will_cause_proble");
                String s3 = resNZResource.getString("Do_you_want_us_to_change_t");
                int j = NZDialogBox.showMessageDialog(s1, s2, 3, as);
                Vector vector1 = DialGroups.getAllPhoneRecs();
                ZCast.displayDebug("sel is " + vector1);
                if(vector1 != null && vector1.size() > 0)
                {
                    flag = PhoneNumberSetting.showDialog();
                    requestFocus();
                }
            }
        } else
        {
            ZCast.displayDebug("@@@@@@@ THERE ARE NO BAD NUMBERS ");
        }
        return flag;
    }

    private void checkConf5A(String s, TextField textfield)
    {
        if(s == null)
        {
            textfield.setText(oldConf);
            return;
        }
        if(!allCharAndDigits(s))
        {
            int i = textfield.getCaretPosition();
            textfield.setText(oldConf);
            textfield.setCaretPosition(i);
            return;
        }
        if(s.length() > 12)
        {
            int j = textfield.getCaretPosition();
            textfield.setText(oldConf);
            textfield.setCaretPosition(j);
            return;
        }
        if(oldConf.length() == 0)
            oldConf = s;
        else
        if(allCharAndDigits(s))
        {
            oldConf = s;
        } else
        {
            int k = textfield.getCaretPosition();
            textfield.setText(oldConf);
            textfield.setCaretPosition(k);
        }
    }

    private void checkFirst3(String s, TextField textfield, TextField textfield1)
    {
        if(s == null)
        {
            textfield.setText(oldFirst3);
            return;
        }
        if(!allDigits(s))
        {
            int i = textfield.getCaretPosition();
            if(i > 0)
                i--;
            textfield.setText(oldFirst3);
            textfield.setCaretPosition(i);
            return;
        }
        if(s.length() == 3)
        {
            textfield1.requestFocus();
            return;
        }
        if(s.length() > 3)
        {
            int j = textfield.getCaretPosition();
            if(j > 0)
                j--;
            textfield.setText(oldFirst3);
            textfield.setCaretPosition(j);
            return;
        }
        if(oldFirst3.length() == 0)
            oldFirst3 = s;
        else
        if(allDigits(s))
        {
            oldFirst3 = s;
        } else
        {
            int k = textfield.getCaretPosition();
            if(k > 0)
                k--;
            textfield.setText(oldFirst3);
            textfield.setCaretPosition(k);
        }
    }

    private void checkID4A(String s, TextField textfield)
    {
        if(s == null)
        {
            textfield.setText(oldID);
            return;
        }
        if(!allValidIDChars(s))
        {
            int i = textfield.getCaretPosition();
            textfield.setText(oldID);
            textfield.setCaretPosition(i);
            return;
        }
        if(s.length() > 30)
        {
            int j = textfield.getCaretPosition();
            textfield.setText(oldID);
            textfield.setCaretPosition(j);
            return;
        }
        if(containsUpperCase(s))
        {
            int k = textfield.getCaretPosition();
            textfield.setText(s.toLowerCase());
            textfield.setCaretPosition(k);
        }
        if(oldID.length() == 0)
            oldID = s;
        else
        if(allValidIDChars(s))
        {
            oldID = s;
        } else
        {
            int l = textfield.getCaretPosition();
            textfield.setText(oldID);
            textfield.setCaretPosition(l);
        }
    }

    private void checkNewNumbers()
    {
        DialGroups.prepareToDial();
        Vector vector = DialGroups.getNumbersToDial();
        if(vector == null || vector.size() <= 0)
        {
            if(DialGroups.numNumbers() > 0)
            {
                String as[] = {
                    "Ok"
                };
                NZDialogBox.showMessageDialog(resNZResource.getString("Pick_a_Number"), resNZResource.getString("All_numbers_in_your_phone_"), 3, as);
            } else
            {
                String as1[] = {
                    resNZResource.getString("Ok")
                };
                NZDialogBox.showMessageDialog(resNZResource.getString("Pick_a_Number"), resNZResource.getString("All_numbers_in_your_phone_1"), 3, as1);
            }
            setPathConnectModifyGroup();
            processNext();
        }
    }

    private void checkNickname7A(String s)
    {
        if(s == null)
        {
            getTextFieldNickName7A().setText(oldNickname);
            return;
        }
        if(!allValidGroupChars(s))
        {
            int i = getTextFieldNickName7A().getCaretPosition();
            getTextFieldNickName7A().setText(oldNickname);
            getTextFieldNickName7A().setCaretPosition(i);
            return;
        }
        if(s.length() > 30)
        {
            int j = getTextFieldNickName7A().getCaretPosition();
            getTextFieldNickName7A().setText(oldNickname);
            getTextFieldNickName7A().setCaretPosition(j);
            return;
        }
        if(oldNickname.length() == 0)
            oldNickname = s;
        else
        if(allValidGroupChars(s))
        {
            oldNickname = s;
        } else
        {
            int k = getTextFieldNickName7A().getCaretPosition();
            getTextFieldNickName7A().setText(oldNickname);
            getTextFieldNickName7A().setCaretPosition(k);
        }
    }

    private void checkNickname7B(String s)
    {
        if(s == null)
        {
            getTextFieldNickName7B().setText(oldNickname);
            return;
        }
        if(!allValidGroupChars(s))
        {
            int i = getTextFieldNickName7B().getCaretPosition();
            getTextFieldNickName7B().setText(oldNickname);
            getTextFieldNickName7B().setCaretPosition(i);
            return;
        }
        if(s.length() > 30)
        {
            int j = getTextFieldNickName7B().getCaretPosition();
            getTextFieldNickName7B().setText(oldNickname);
            getTextFieldNickName7B().setCaretPosition(j);
            return;
        }
        if(oldNickname.length() == 0)
            oldNickname = s;
        else
        if(allValidGroupChars(s))
        {
            oldNickname = s;
        } else
        {
            int k = getTextFieldNickName7B().getCaretPosition();
            getTextFieldNickName7B().setText(oldNickname);
            getTextFieldNickName7B().setCaretPosition(k);
        }
    }

    private void checkPass5A(String s, TextField textfield)
    {
        if(s == null)
        {
            textfield.setText(oldPass);
            return;
        }
        if(!allCharAndDigits(s))
        {
            int i = textfield.getCaretPosition();
            textfield.setText(oldPass);
            textfield.setCaretPosition(i);
            return;
        }
        if(s.length() > 12)
        {
            int j = textfield.getCaretPosition();
            textfield.setText(oldPass);
            textfield.setCaretPosition(j);
            return;
        }
        if(oldPass.length() == 0)
            oldPass = s;
        else
        if(allCharAndDigits(s))
        {
            oldPass = s;
        } else
        {
            int k = textfield.getCaretPosition();
            textfield.setText(oldPass);
            textfield.setCaretPosition(k);
        }
    }

    private boolean checkPassword(boolean flag)
    {
        String s = null;
        if(flag)
            s = getTextFieldPwdSlideFiveA().getText();
        else
            s = getTextFieldPasswordConnectA().getText();
        return s != null && s.length() != 0;
    }

    public void cleanup()
    {
        if(leftImage != null)
            leftImage.cleanup();
        if(topImage != null)
            topImage.cleanup();
        if(backButton != null)
            backButton.cleanup();
        if(nextButton != null)
            nextButton.cleanup();
        if(cancelButton != null)
            cancelButton.cleanup();
        if(buttonAddEightA != null)
            buttonAddEightA.cleanup();
        if(buttonDelEightA != null)
            buttonDelEightA.cleanup();
        if(buttonDelConnectA != null)
            buttonDelConnectA.cleanup();
        if(buttonDelNumbersEightA != null)
            buttonDelNumbersEightA.cleanup();
        if(buttonSettingEightA != null)
            buttonSettingEightA.cleanup();
        if(buttonFinishTwelveA != null)
            buttonFinishTwelveA.cleanup();
        if(buttonBackSignupTwelveA != null)
            buttonBackSignupTwelveA.cleanup();
        if(buttonCancelConnectA != null)
            buttonCancelConnectA.cleanup();
        if(buttonModifyConnectA != null)
            buttonModifyConnectA.cleanup();
        if(buttonRemoveID != null)
            buttonRemoveID.cleanup();
        if(buttonConnectA != null)
            buttonConnectA.cleanup();
    }

    private void clear7B()
    {
        textFieldAreaCode7B.requestFocus();
        textFieldAreaCode7B.setText("");
        textFieldFirstThree7B.setText("");
        textFieldNickName7B.setText("");
        nextButton.setEnabled(false);
        textFieldAreaCode7B.setEditable(true);
        textFieldFirstThree7B.setEditable(true);
        textFieldNickName7B.setEditable(true);
        phoneList7B.removeAll();
        phoneList7B.addItem(resNZResource.getString("To_be_determined"));
    }

    private void clearAllPanels()
    {
        savedGroups = false;
        getCheckboxAcceptSlideThreeA().setState(false);
        getTextFieldIdSlideFourA().setText("");
        getTextFieldPwdSlideFiveA().setText("");
        getTextFieldConfirmSlideFiveA().setText("");
        getTextFieldAnswerSlideSixA().setText("");
        getTextFieldAreaCode7A().setText("");
        getTextFieldFirstThree7A().setText("");
        getTextFieldNickName7A().setText("");
        getTextFieldAreaCode7B().setText("");
        getTextFieldFirstThree7B().setText("");
        getTextFieldNickName7B().setText("");
    }

    private boolean connectedToNetwork()
    {
        boolean flag = false;
        setCursor(Cursor.getPredefinedCursor(3));
        if(ZCast.m_osDetectNative.testUrlAccess())
        {
            flag = true;
        } else
        {
            setCursor(Cursor.getPredefinedCursor(0));
            String as[] = {
                resNZResource.getString("Ok")
            };
            NZDialogBox.showMessageDialog(resNZResource.getString("Connect_Error"), resNZResource.getString("No_network_connection_dete"), 2, as);
        }
        setCursor(Cursor.getPredefinedCursor(0));
        return flag;
    }

    public void connectNotification()
    {
        if(zrIn != null)
            zrIn.connectNotification();
    }

    private void connectToNetzero()
    {
        try
        {
            showZeroInDialog();
            cancelStatus = false;
            try
            {
                Thread.sleep(0L);
            }
            catch(Exception _ex) { }
            if(MemberRecs.getIsNewUser())
            {
                ZCast.displayDebug("INIT-PROFILE-CHECK: FIRST TIME CREATION");
                ZCast.markConnectionTime();
                Thread thread = new Thread() {

                    public void run()
                    {
                        try
                        {
                            DialObject.doConnect(0, Startup.getInstance());
                            MemberRecs.setIsNewUser(false);
                            Initializer.doPARM();
                            initProfileNav();
                            MemberRecs.writeRecs();
                            DialObject.doDisconnect();
                            Startup.initObj = new Initializer(Startup.getInstance());
                            Startup.initObj.start();
                        }
                        catch(Exception exception1)
                        {
                            ZCast.displayDebug("\n\n** ** ** ");
                            ZCast.displayDebug(exception1);
                            ZCast.displayDebug("** ** **\n\n");
                            cancelNotification(Startup.resNZResource.getString("Unable_to_connect"));
                        }
                    }

                };
                thread.start();
            } else
            {
                initObj = new Initializer(getInstance());
                initObj.start();
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    private void connectUser(boolean flag)
    {
        ZCast.displayDebug("\n CONNECTUSER!!! allowConnect=" + allowConnect + "\n");
        if(allowConnect && checkPassword(flag))
        {
            allowConnect = false;
            final boolean isNewUser = flag;
            Thread thread = new Thread() {

                public void run()
                {
                    boolean flag1 = true;
                    ZCast.displayDebug("\n\nConnect button pressed, set and save\n");
                    ZCast.displayDebug("current member is " + MemberRecs.getCurrentMemberID());
                    String s = null;
                    if(isNewUser)
                        s = getTextFieldPwdSlideFiveA().getText().trim();
                    else
                        s = getTextFieldPasswordConnectA().getText().trim();
                    ZCast.displayDebug("set password to " + s);
                    MemberRecs.setCurrentPassword(s);
                    MemberRecs.writeRecs();
                    if(!ZCast.m_demoMode)
                    {
                        ZCast.displayDebug("\n\n\tNOT DEMO MODE\n\n");
                        if(networkCheckbox.getState())
                        {
                            if(connectedToNetwork())
                                ZCast.m_connectionType = 1;
                            else
                                return;
                        } else
                        {
                            ZCast.m_connectionType = 0;
                            ZCast.displayDebug("\n\n\tPREPARE FOR DIALING\n\n");
                            flag1 = prepareForDialing();
                        }
                    }
                    if(flag1)
                    {
                        DialGroups.writeGroups();
                        ZCast.displayDebug("\n\tconnectionType == " + ZCast.m_connectionType);
                        connectToNetzero();
                    }
                }

            };
            thread.start();
        }
    }

    private void connRadioToCurrentAccount()
    {
        try
        {
            getCheckboxCurrentAccount().setCheckboxGroup(getCheckboxGroupAccount());
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connRadioToNewAccount()
    {
        try
        {
            getCheckboxNewAccount().setCheckboxGroup(getCheckboxGroupAccount());
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private boolean containsUpperCase(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(Character.isUpperCase(s.charAt(i)))
                return true;

        return false;
    }

    private void fill7B()
    {
        String s = "";
        if(getGroupsList7B().getItemCount() != 0)
            s = getGroupsList7B().getSelectedItem();
        ZCast.displayDebug("oldGroupName is " + s);
        if(s.equals(ADD_GROUP7B))
        {
            ZCast.displayDebug("\nselect make new phone list\n");
            getNextButton().setActionCommand("Modify7B");
        } else
        {
            ZCast.displayDebug("Selected group in list");
            ZCast.displayDebug("Current Group is");
            DialGroups.fillDialFromTextFields(getTextFieldAreaCode7B(), getTextFieldFirstThree7B());
            getTextFieldAreaCode7B().requestFocus();
            String s1 = DialGroups.getCurrentGroupName();
            getTextFieldNickName7B().setText(s1);
            getGroupsList7B().removeAll();
            getGroupsList7B().addItem(ADD_GROUP7B);
            DialGroups.fillGroupList(getGroupsList7B());
            DialGroups.selectCurrentGroupInList(getGroupsList7B());
            DialGroups.setFinalPhoneList(getPhoneList7B());
            getTextFieldAreaCode7B().setEditable(false);
            getTextFieldFirstThree7B().setEditable(false);
            getTextFieldNickName7B().setEditable(false);
            getNextButton().setActionCommand("Process7B");
        }
    }

    protected void fillAreaCodeSlideEightA(String as[])
    {
        if(as != null)
        {
            String as1[] = new String[as.length];
            int i = 0;
            getChoiceAreaCodesEightA().removeAll();
            getChoiceAreaCodesEightA().addItem(" ");
            for(int j = 0; j < as.length; j++)
            {
                String s = as[j].substring(0, 3);
                boolean flag = false;
                int l = i;
                for(int i1 = 0; i1 < i; i1++)
                {
                    int j1 = as1[i1].compareTo(s);
                    if(j1 == 0)
                    {
                        flag = true;
                        i1 = i;
                    }
                    if(j1 > 0)
                    {
                        l = i1;
                        i1 = i;
                    }
                }

                if(!flag)
                {
                    for(int k1 = i; k1 > l; k1--)
                        as1[k1] = as1[k1 - 1];

                    as1[l] = s;
                    i++;
                }
            }

            for(int k = 0; k < i; k++)
                getChoiceAreaCodesEightA().addItem(as1[k]);

        }
    }

    protected void fillCitiesLeftEightA(String s, String as[])
    {
        getListCitiesEightA().removeAll();
        if(as != null)
        {
            String s1 = "";
            String as1[] = ConfigParams.getAllCities();
            for(int i = 0; i < as.length; i++)
                if(as[i].startsWith(s) && !as1[i].equals(s1))
                {
                    s1 = as1[i];
                    getListCitiesEightA().addItem(s1.substring(2) + ", " + s1.substring(0, 2));
                }

        }
    }

    protected void fillPhoneNumbersRightEightA()
    {
        DialGroups.fillSortedPhoneList(getListPhoneListEightA());
        for(int i = 0; i < getListCitiesEightA().getItemCount(); i++)
            getListCitiesEightA().deselect(i);

        refreshButtons8A();
    }

    public void focusGained(FocusEvent focusevent)
    {
    }

    public void focusLost(FocusEvent focusevent)
    {
    }

    private AgCanvas getAnimateConnectA()
    {
        if(topImage == null)
        {
            topImage = new AgCanvas();
            topImage.initialize(null, "images/anima/header_anima.gif");
            topImage.setLocation(60, 5);
        }
        return topImage;
    }

    private NZButton getBackButton()
    {
        if(backButton == null)
            try
            {
                backButton = new NZButton(resNZResource.getString("back"));
                backButton.setBounds(220, 8, 51, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return backButton;
    }

    private JCGroupBox getBottomBox()
    {
        if(bottomBox == null)
            try
            {
                bottomBox = new JCGroupBox();
                bottomBox.setName("bottomBox");
                bottomBox.setLayout(new FlowLayout(2));
                bottomBox.setShadowThickness(2);
                bottomBox.setBounds(6, 310, 455, 42);
                bottomBox.add(getBackButton(), getBackButton().getName());
                bottomBox.add(getNextButton(), getNextButton().getName());
                bottomBox.add(getCancelButton(), getCancelButton().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return bottomBox;
    }

    private NZButton getButtonAddEightA()
    {
        if(buttonAddEightA == null)
            try
            {
                buttonAddEightA = new NZButton("add_arrow");
                buttonAddEightA.setBounds(243, 160, 38, 38);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonAddEightA;
    }

    private NZButton getButtonBackSignupTwelveA()
    {
        if(buttonBackSignupTwelveA == null)
            try
            {
                buttonBackSignupTwelveA = new NZButton(resNZResource.getString("back"));
                buttonBackSignupTwelveA.setBounds(300, 8, 51, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonBackSignupTwelveA;
    }

    private NZButton getButtonCancelConnectA()
    {
        if(buttonCancelConnectA == null)
            try
            {
                buttonCancelConnectA = new NZButton(resNZResource.getString("cancel"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonCancelConnectA;
    }

    private NZButton getButtonConnectA()
    {
        if(buttonConnectA == null)
            try
            {
                buttonConnectA = new NZButton(resNZResource.getString("connect"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonConnectA;
    }

    private NZButton getButtonDelConnectA()
    {
        if(buttonDelConnectA == null)
            try
            {
                buttonDelConnectA = new NZButton(resNZResource.getString("delete"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonDelConnectA;
    }

    private NZButton getButtonDelNumbersEightA()
    {
        if(buttonDelNumbersEightA == null)
            try
            {
                buttonDelNumbersEightA = new NZButton(resNZResource.getString("delete"));
                buttonDelNumbersEightA.setLocation(385, 285);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonDelNumbersEightA;
    }

    private NZButton getButtonFinishTwelveA()
    {
        if(buttonFinishTwelveA == null)
            try
            {
                buttonFinishTwelveA = new NZButton(resNZResource.getString("finish"));
                buttonFinishTwelveA.setBounds(380, 8, 51, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonFinishTwelveA;
    }

    private NZButton getButtonModifyConnectA()
    {
        if(buttonModifyConnectA == null)
            try
            {
                buttonModifyConnectA = new NZButton(resNZResource.getString("modify"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonModifyConnectA;
    }

    private NZButton getButtonRemoveID()
    {
        if(buttonRemoveID == null)
            try
            {
                buttonRemoveID = new NZButton(resNZResource.getString("delete"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonRemoveID;
    }

    private NZButton getButtonSettingEightA()
    {
        if(buttonSettingEightA == null)
            try
            {
                buttonSettingEightA = new NZButton("settings");
                buttonSettingEightA.setLocation(322, 285);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return buttonSettingEightA;
    }

    private NZButton getCancelButton()
    {
        if(cancelButton == null)
            try
            {
                cancelButton = new NZButton(resNZResource.getString("cancel"));
                cancelButton.setBounds(380, 8, 51, 20);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cancelButton;
    }

    public boolean getCancelStatus()
    {
        return cancelStatus;
    }

    private Checkbox getCheckboxAcceptSlideThreeA()
    {
        if(checkboxAcceptSlideThreeA == null)
            try
            {
                checkboxAcceptSlideThreeA = new Checkbox();
                checkboxAcceptSlideThreeA.setName("CheckboxAcceptSlideThreeA");
                checkboxAcceptSlideThreeA.setBounds(120, 280, 120, 25);
                checkboxAcceptSlideThreeA.setLabel(resNZResource.getString("I_accept_the_terms"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxAcceptSlideThreeA;
    }

    private Checkbox getCheckboxCurrentAccount()
    {
        if(checkboxCurrentAccount == null)
            try
            {
                checkboxCurrentAccount = new Checkbox();
                checkboxCurrentAccount.setName("CheckboxCurrentAccount");
                checkboxCurrentAccount.setBounds(170, 133, 217, 25);
                checkboxCurrentAccount.setLabel(resNZResource.getString("I_already_have_a_NetZero_a"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxCurrentAccount;
    }

    private Checkbox getCheckboxCustomTenA()
    {
        if(checkboxCustomTenA == null)
            try
            {
                checkboxCustomTenA = new Checkbox();
                checkboxCustomTenA.setName("CheckboxCustomTenA");
                checkboxCustomTenA.setBounds(15, 70, 100, 20);
                checkboxCustomTenA.setLabel(resNZResource.getString("Custom_prefix"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxCustomTenA;
    }

    private Checkbox getCheckboxDialTenA()
    {
        if(checkboxDialTenA == null)
            try
            {
                checkboxDialTenA = new Checkbox();
                checkboxDialTenA.setName("CheckboxDialTenA ");
                checkboxDialTenA.setBounds(15, 10, 43, 20);
                checkboxDialTenA.setLabel(resNZResource.getString("Dial"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxDialTenA;
    }

    private Checkbox getCheckboxDisableCallTenA()
    {
        if(checkboxDisableCallTenA == null)
            try
            {
                checkboxDisableCallTenA = new Checkbox();
                checkboxDisableCallTenA.setName("CheckboxDisableCallTenA ");
                checkboxDisableCallTenA.setBounds(15, 40, 185, 20);
                checkboxDisableCallTenA.setLabel(resNZResource.getString("Disable_call_waiting_by_di"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxDisableCallTenA;
    }

    private CheckboxGroup getCheckboxGroupAccount()
    {
        if(checkboxGroupAccount == null)
            try
            {
                checkboxGroupAccount = new CheckboxGroup();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxGroupAccount;
    }

    private Checkbox getCheckboxMuteModem()
    {
        if(checkboxMuteModem == null)
            try
            {
                checkboxMuteModem = new Checkbox();
                checkboxMuteModem.setName("CheckboxCustomMuteModem");
                checkboxMuteModem.setBounds(15, 100, 200, 20);
                checkboxMuteModem.setLabel(resNZResource.getString("Mute_Modem_Speaker"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxMuteModem;
    }

    private Checkbox getCheckboxNewAccount()
    {
        if(checkboxNewAccount == null)
            try
            {
                checkboxNewAccount = new Checkbox();
                checkboxNewAccount.setName("CheckboxNewAccount");
                checkboxNewAccount.setBounds(170, 99, 230, 25);
                checkboxNewAccount.setLabel(resNZResource.getString("I_want_to_create_a_new_Net"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxNewAccount;
    }

    private Checkbox getCheckboxSavePasswordConnectA()
    {
        if(checkboxSavePasswordConnectA == null)
            try
            {
                checkboxSavePasswordConnectA = new Checkbox();
                checkboxSavePasswordConnectA.setName("Checkbox1");
                checkboxSavePasswordConnectA.setLabel(resNZResource.getString("Save_password"));
                checkboxSavePasswordConnectA.setState(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return checkboxSavePasswordConnectA;
    }

    private Choice getChoiceAreaCodesEightA()
    {
        if(choiceAreaCodesEightA == null)
            try
            {
                choiceAreaCodesEightA = new Choice();
                choiceAreaCodesEightA.setName("ChoiceAreaCodesEightA");
                choiceAreaCodesEightA.setBounds(243, 66, 52, 20);
                choiceAreaCodesEightA.setBackground(Color.white);
                choiceAreaCodesEightA.setForeground(Color.black);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return choiceAreaCodesEightA;
    }

    private Choice getChoiceDialingFromConnectA()
    {
        if(choiceDialingFromConnectA == null)
            try
            {
                choiceDialingFromConnectA = new Choice();
                choiceDialingFromConnectA.setName("Choice1");
                choiceDialingFromConnectA.setBackground(Color.white);
                choiceDialingFromConnectA.setForeground(Color.black);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return choiceDialingFromConnectA;
    }

    private Choice getChoiceGroupEightA()
    {
        if(choiceGroupEightA == null)
            try
            {
                choiceGroupEightA = new Choice();
                choiceGroupEightA.setName("Choice1");
                choiceGroupEightA.setBounds(210, 3, 60, 20);
                Font font = new Font("Arial", 1, 10);
                choiceGroupEightA.setFont(font);
                choiceGroupEightA.setBackground(Color.white);
                choiceGroupEightA.setForeground(Color.black);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return choiceGroupEightA;
    }

    private Choice getChoiceMemberID()
    {
        if(choiceMemberID == null)
            try
            {
                choiceMemberID = new Choice();
                choiceMemberID.setName("Choice52");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return choiceMemberID;
    }

    private Choice getChoiceQuestionSlideSixA()
    {
        if(choiceQuestionSlideSixA == null)
            try
            {
                choiceQuestionSlideSixA = new Choice();
                choiceQuestionSlideSixA.setName("Choice52");
                choiceQuestionSlideSixA.setBounds(190, 238, 270, 20);
                choiceQuestionSlideSixA.setBackground(Color.white);
                choiceQuestionSlideSixA.addItem(resNZResource.getString("What_is_my_mother's_maiden"));
                choiceQuestionSlideSixA.addItem(resNZResource.getString("What_is_my_dog's_name?"));
                choiceQuestionSlideSixA.addItem(resNZResource.getString("In_what_city_was_I_born?"));
                choiceQuestionSlideSixA.addItem(resNZResource.getString("What_are_the_last_four_dig"));
                choiceQuestionSlideSixA.addItem(resNZResource.getString("What_is_my_favorite_color?"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return choiceQuestionSlideSixA;
    }

    private JCGroupBox getConnectOptionsBox()
    {
        if(connectOptionsBox == null)
            try
            {
                connectOptionsBox = new JCGroupBox();
                connectOptionsBox.setName("JCGroupBox3");
                connectOptionsBox.setFont(new NZFont());
                connectOptionsBox.setBackground(SystemColor.control);
                connectOptionsBox.setTitle(resNZResource.getString("Connect_Options"));
                connectOptionsBox.setLayout(null);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return connectOptionsBox;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(new CardLayout());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private java.awt.List getGroupsList7B()
    {
        if(groupsList7B == null)
            try
            {
                groupsList7B = new java.awt.List();
                groupsList7B.setName("ListPhoneListEightA");
                groupsList7B.setBounds(130, 210, 120, 80);
                groupsList7B.setMultipleMode(false);
                NZFont nzfont = new NZFont();
                groupsList7B.setFont(nzfont);
                groupsList7B.setBackground(Color.white);
                groupsList7B.setForeground(Color.blue);
                groupsList7B.addMouseListener(new MouseAdapter() {

                    public void mouseReleased(MouseEvent mouseevent)
                    {
                        String s = groupsList7B.getSelectedItem();
                        if(s.equals(Startup.ADD_GROUP7B))
                        {
                            clear7B();
                            int i = currentNode;
                            setPathCreateUserNoPickedGroup();
                            currentNode = i;
                        } else
                        {
                            DialGroups.setCurrentGroup(s);
                            fill7B();
                            int j = currentNode;
                            setPathCreateUserPickGroup();
                            currentNode = j;
                        }
                    }

                });
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return groupsList7B;
    }

    private Checkbox getIgnoreCheckbox()
    {
        if(ignoreCheckbox == null)
        {
            ignoreCheckbox = new Checkbox();
            ignoreCheckbox.setBounds(120, 280, 250, 25);
            ignoreCheckbox.setLabel(resNZResource.getString("Don't_show_me_this_warning"));
        }
        return ignoreCheckbox;
    }

    private static Startup getInstance()
    {
        return me;
    }

    private JCGroupBox getJCGroupBoxBottomConnectA()
    {
        if(groupBoxBottomConnectA == null)
            try
            {
                groupBoxBottomConnectA = new JCGroupBox();
                groupBoxBottomConnectA.setName("groupBoxBottomConnectA");
                groupBoxBottomConnectA.setLayout(null);
                groupBoxBottomConnectA.setShadowThickness(2);
                groupBoxBottomConnectA.setBounds(5, 255, 338, 38);
                groupBoxBottomConnectA.setLayout(new FlowLayout(2));
                getJCGroupBoxBottomConnectA().add(getButtonConnectA());
                getJCGroupBoxBottomConnectA().add(getButtonCancelConnectA());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return groupBoxBottomConnectA;
    }

    private JCGroupBox getJCGroupBoxBottomTwelveA()
    {
        if(groupBoxBottomTwelveA == null)
            try
            {
                groupBoxBottomTwelveA = new JCGroupBox();
                groupBoxBottomTwelveA.setName("groupBoxBottomTwelveA");
                groupBoxBottomTwelveA.setLayout(new FlowLayout(2));
                groupBoxBottomTwelveA.setShadowThickness(2);
                groupBoxBottomTwelveA.setBounds(6, 310, 455, 42);
                getJCGroupBoxBottomTwelveA().add(getButtonBackSignupTwelveA(), getButtonBackSignupTwelveA().getName());
                getJCGroupBoxBottomTwelveA().add(getButtonFinishTwelveA(), getButtonFinishTwelveA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return groupBoxBottomTwelveA;
    }

    private JCGroupBox getJCGroupBoxLeftEightA()
    {
        if(groupBoxLeftEightA == null)
            try
            {
                groupBoxLeftEightA = new JCGroupBox();
                groupBoxLeftEightA.setName("groupBoxCities");
                groupBoxLeftEightA.setBounds(120, 90, 125, 195);
                groupBoxLeftEightA.setTitle(resNZResource.getString("Available_Cities"), 1);
                groupBoxLeftEightA.setFont(new Font("dialog", 0, 11));
                getJCGroupBoxLeftEightA().add(getListCitiesEightA(), getListCitiesEightA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return groupBoxLeftEightA;
    }

    private JCGroupBox getJCGroupBoxRightEightA()
    {
        if(groupBoxRightEightA == null)
            try
            {
                groupBoxRightEightA = new JCGroupBox();
                groupBoxRightEightA.setName("groupBox3");
                groupBoxRightEightA.setBounds(281, 90, 185, 195);
                groupBoxRightEightA.setTitle(resNZResource.getString("My_Phone_List"), 1);
                groupBoxRightEightA.setShadowThickness(2);
                groupBoxRightEightA.setFont(new Font("dialog", 0, 11));
                getJCGroupBoxRightEightA().add(getListPhoneListEightA(), getListPhoneListEightA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return groupBoxRightEightA;
    }

    private java.awt.List getListCitiesEightA()
    {
        if(listCitiesEightA == null)
            try
            {
                listCitiesEightA = new java.awt.List();
                listCitiesEightA.setName("ListCitiesEightA");
                listCitiesEightA.setBounds(140, 130, 155, 80);
                listCitiesEightA.setMultipleMode(true);
                listCitiesEightA.setBackground(Color.white);
                listCitiesEightA.setForeground(Color.black);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return listCitiesEightA;
    }

    private java.awt.List getListPhoneListEightA()
    {
        if(listPhoneListEightA == null)
            try
            {
                listPhoneListEightA = new java.awt.List();
                listPhoneListEightA.setName("ListPhoneListEightA");
                listPhoneListEightA.setBounds(325, 130, 155, 80);
                listPhoneListEightA.setMultipleMode(true);
                listPhoneListEightA.setBackground(Color.white);
                listPhoneListEightA.setForeground(Color.blue);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return listPhoneListEightA;
    }

    private java.awt.List getListPhoneListElevenA()
    {
        if(listPhoneListElevenA == null)
            try
            {
                listPhoneListElevenA = new java.awt.List();
                listPhoneListElevenA.setName("ListPhoneListElevenA");
                listPhoneListElevenA.setBounds(140, 180, 400, 40);
                listPhoneListElevenA.setBackground(Color.white);
                listPhoneListElevenA.setForeground(Color.blue);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return listPhoneListElevenA;
    }

    protected void getLoginInfo()
    {
        Object obj = null;
        boolean flag = false;
        String s1 = "";
        ZCast.displayDebug("inside getLoginInfo, numrecs == " + MemberRecs.numMemberRecs());
        if(MemberRecs.numMemberRecs() == 0 && prof != null)
        {
            ZCast.displayDebug("read old user info from disk");
            String s = prof.getProfUid();
            ZCast.displayDebug("oldprofile is '" + s + "'");
            ZCast.displayDebug("length is " + s.length());
            if(s != null && s.length() > 0)
            {
                ZCast.displayDebug("member id is " + s);
                boolean flag1 = prof.getSaveLoginState();
                if(flag1)
                    s1 = prof.getProfPwd();
                if(s1 == null)
                    s1 = "";
                MemberRecs.addMemberRec(s, s1);
                MemberRecs.setSavePassword(flag1);
                firstTime = false;
                MemberRecs.writeRecs();
            } else
            {
                ZCast.displayDebug("set first time to true");
                firstTime = true;
            }
        } else
        if(MemberRecs.numMemberRecs() > 0)
            firstTime = false;
        else
            firstTime = true;
    }

    private Checkbox getNetworkCheckbox()
    {
        if(networkCheckbox == null)
        {
            networkCheckbox = new Checkbox("Connect with DSL, cable, or company LAN");
            networkCheckbox.setSize(256, 22);
            networkCheckbox.setState(DialGroups.getNetworkChecked());
        }
        return networkCheckbox;
    }

    private NZButton getNextButton()
    {
        if(nextButton == null)
            try
            {
                nextButton = new NZButton(resNZResource.getString("next"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return nextButton;
    }

    private AgCanvas getNZImageBox()
    {
        if(leftImage == null)
        {
            leftImage = new AgCanvas();
            leftImage.initialize(null, "images/anima/left_anima.gif");
            leftImage.setLocation(5, 5);
        }
        return leftImage;
    }

    private Panel getPanel1()
    {
        setSize(467, 380);
        if(ivjPanel1 == null)
            try
            {
                ivjPanel1 = new Panel();
                ivjPanel1.setName("Panel1");
                ivjPanel1.setFont(new NZFont());
                ivjPanel1.setLayout(null);
                ivjPanel1.setBackground(SystemColor.control);
                ivjPanel1.setBounds(508, 89, 455, 400);
                Label label = new Label(resNZResource.getString("Member_ID_"));
                label.setBounds(188, 160, 72, 22);
                getPanel1().add(label);
                getPanel1().add(getTextFieldID(), getTextFieldID().getName());
                getPanel1().add(getCheckboxNewAccount(), getCheckboxNewAccount().getName());
                getPanel1().add(getCheckboxCurrentAccount(), getCheckboxCurrentAccount().getName());
                getPanel1().add(getBottomBox(), getBottomBox().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel1;
    }

    private Panel getPanelConnectA()
    {
        if(panelConnectA == null)
            try
            {
                panelConnectA = new Panel();
                panelConnectA.setName("PanelConnect");
                panelConnectA.setLayout(null);
                panelConnectA.setFont(new NZFont());
                panelConnectA.setBackground(SystemColor.control);
                panelConnectA.setBounds(80, 2556, 200, 400);
                AgCanvas agcanvas = getAnimateConnectA();
                getPanelConnectA().add(agcanvas);
                agcanvas.setLocation(5, 5);
                byte byte0 = 5;
                int i = 65;
                Label label = new Label(resNZResource.getString("Member_ID_"));
                label.setBounds(byte0, i + 3, 70, 15);
                panelConnectA.add(label);
                getPanelConnectA().add(getChoiceMemberID(), getChoiceMemberID().getName());
                getChoiceMemberID().setBounds(byte0 + 77, i, 190, 26);
                getPanelConnectA().add(getButtonRemoveID(), getButtonRemoveID().getName());
                getButtonRemoveID().setBounds(byte0 + 273, i + 3, 66, 23);
                i += 28;
                label = new Label(resNZResource.getString("Password_"));
                label.setBounds(byte0, i, 70, 15);
                panelConnectA.add(label);
                getPanelConnectA().add(getTextFieldPasswordConnectA(), getTextFieldPasswordConnectA().getName());
                getTextFieldPasswordConnectA().setBounds(byte0 + 75, i - 2, 194, 26);
                i += 27;
                getPanelConnectA().add(getCheckboxSavePasswordConnectA(), getCheckboxSavePasswordConnectA().getName());
                getCheckboxSavePasswordConnectA().setBounds(82, i, 110, 20);
                i += 50;
                getConnectOptionsBox().setBounds(byte0, i, 338, 85);
                i = 25;
                label = new Label(resNZResource.getString("I_am_dialing_from_"));
                label.setBounds(byte0, i, 100, 20);
                getConnectOptionsBox().add(label);
                getPanelConnectA().add(getChoiceDialingFromConnectA(), getChoiceDialingFromConnectA().getName());
                getConnectOptionsBox().add(getChoiceDialingFromConnectA());
                getChoiceDialingFromConnectA().setBounds(byte0 + 107, i, 155, 20);
                getPanelConnectA().add(getButtonModifyConnectA(), getButtonModifyConnectA().getName());
                getConnectOptionsBox().add(getButtonModifyConnectA());
                getButtonModifyConnectA().setLocation(272, i - 10);
                getPanelConnectA().add(getButtonDelConnectA(), getButtonDelConnectA().getName());
                getConnectOptionsBox().add(getButtonDelConnectA());
                getButtonDelConnectA().setLocation(272, i + 10);
                getNetworkCheckbox().setLocation(byte0, i + 30);
                getConnectOptionsBox().add(getNetworkCheckbox());
                getPanelConnectA().add(getJCGroupBoxBottomConnectA(), getJCGroupBoxBottomConnectA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        panelConnectA.add(getConnectOptionsBox());
        return panelConnectA;
    }

    private Panel getPanelSlide2A()
    {
        if(panelSlide2A == null)
            try
            {
                panelSlide2A = new Panel();
                panelSlide2A.setName("Panel1");
                panelSlide2A.setFont(new NZFont());
                panelSlide2A.setLayout(null);
                panelSlide2A.setBackground(SystemColor.control);
                panelSlide2A.setBounds(508, 89, 455, 400);
                NZLabel nzlabel = new NZLabel(resNZResource.getString("By_becoming_a_member_of_Ne1"));
                nzlabel.setBounds(120, 23, 450, 170);
                getPanelSlide2A().add(nzlabel);
                getPanelSlide2A().add(getBottomBox(), getBottomBox().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlide2A;
    }

    private Panel getPanelSlide7A()
    {
        setSize(467, 380);
        repaint();
        if(panelSlide7A == null)
            try
            {
                panelSlide7A = new Panel();
                panelSlide7A.setName("PanelSlide7A");
                panelSlide7A.setFont(new NZFont());
                panelSlide7A.setLayout(null);
                panelSlide7A.setBackground(SystemColor.control);
                panelSlide7A.setBounds(508, 89, 455, 400);
                getPanelSlide7A().add(getBottomBox(), getBottomBox().getName());
                getPanelSlide7A().add(getTextFieldAreaCode7A(), getTextFieldAreaCode7A().getName());
                getPanelSlide7A().add(getTextFieldFirstThree7A(), getTextFieldFirstThree7A().getName());
                getPanelSlide7A().add(getTextFieldNickName7A(), getTextFieldNickName7A().getName());
                makeLabels7A();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlide7A;
    }

    private Panel getPanelSlide7B()
    {
        if(panelSlide7B == null)
            try
            {
                panelSlide7B = new Panel();
                panelSlide7B.setName("PanelSlide7B");
                panelSlide7B.setFont(new NZFont());
                panelSlide7B.setLayout(null);
                panelSlide7B.setBackground(SystemColor.control);
                panelSlide7B.setBounds(508, 89, 455, 400);
                getPanelSlide7B().add(getBottomBox(), getBottomBox().getName());
                getPanelSlide7B().add(getTextFieldAreaCode7B(), getTextFieldAreaCode7B().getName());
                getPanelSlide7B().add(getTextFieldFirstThree7B(), getTextFieldFirstThree7B().getName());
                getPanelSlide7B().add(getTextFieldNickName7B(), getTextFieldNickName7B().getName());
                getPanelSlide7B().add(getGroupsList7B(), getGroupsList7B().getName());
                getPanelSlide7B().add(getPhoneList7B(), getPhoneList7B().getName());
                makeLabels7B();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlide7B;
    }

    private Panel getPanelSlide8A()
    {
        if(panelSlideEightA == null)
        {
            ZCast.displayDebug("\n\n########----###########----REMAKE 8A-----##\n\n");
            try
            {
                panelSlideEightA = new Panel();
                panelSlideEightA.setName("PanelSlideEightA ");
                panelSlideEightA.setFont(new NZFont());
                panelSlideEightA.setLayout(null);
                panelSlideEightA.setBackground(NZColor.BACKGROUND);
                panelSlideEightA.setBounds(508, 89, 455, 400);
                NZLabel nzlabel = new NZLabel(resNZResource.getString("Choose_area_codes_and_citi"));
                nzlabel.setBounds(117, 3, 400, 63);
                nzlabel.setAlignment(0);
                panelSlideEightA.add(nzlabel);
                nzlabel = new NZLabel(resNZResource.getString("Choose_area_code_"));
                nzlabel.setAlignment(0);
                nzlabel.setBounds(117, 66, 118, 20);
                panelSlideEightA.add(nzlabel);
                panelSlideEightA.add(getBottomBox(), getBottomBox().getName());
                panelSlideEightA.add(getJCGroupBoxLeftEightA(), getJCGroupBoxLeftEightA().getName());
                panelSlideEightA.add(getButtonAddEightA(), getButtonAddEightA().getName());
                panelSlideEightA.add(getJCGroupBoxRightEightA(), getJCGroupBoxRightEightA().getName());
                panelSlideEightA.add(getButtonSettingEightA(), getButtonSettingEightA().getName());
                panelSlideEightA.add(getButtonDelNumbersEightA(), getButtonDelNumbersEightA().getName());
                panelSlideEightA.add(getChoiceAreaCodesEightA(), getChoiceAreaCodesEightA().getName());
                fillAreaCodeSlideEightA(ConfigParams.getAllNumbers());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        }
        return panelSlideEightA;
    }

    private Panel getPanelSlideElevenA()
    {
        if(panelSlideElevenA == null)
            try
            {
                panelSlideElevenA = new Panel();
                panelSlideElevenA.setName("PanelSlideElevenA ");
                panelSlideElevenA.setFont(new NZFont());
                panelSlideElevenA.setLayout(null);
                panelSlideElevenA.setBackground(SystemColor.control);
                panelSlideElevenA.setBounds(508, 89, 455, 400);
                NZLabel nzlabel = new NZLabel(resNZResource.getString("Here_are_the_numbers_you_h1"));
                nzlabel.setBounds(120, 10, 400, 40);
                panelSlideElevenA.add(nzlabel);
                nzlabel = new NZLabel(resNZResource.getString("You_are_solely"));
                nzlabel.setBounds(120, 50, 400, 70);
                nzlabel.setFont(new Font("Arial", 1, 12));
                panelSlideElevenA.add(nzlabel);
                JCGroupBox jcgroupbox = new JCGroupBox();
                jcgroupbox.setName("JCGroupBox3");
                jcgroupbox.setBounds(125, 130, 280, 130);
                jcgroupbox.setTitle(resNZResource.getString("Key1"));
                jcgroupbox.setShadowThickness(2);
                jcgroupbox.add(getListPhoneListElevenA(), getListPhoneListElevenA().getName());
                panelSlideElevenA.add(jcgroupbox);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideElevenA;
    }

    private Panel getPanelSlideFiveA()
    {
        if(panelSlideFiveA == null)
            try
            {
                panelSlideFiveA = new Panel();
                panelSlideFiveA.setName("PanelSlideFiveA");
                panelSlideFiveA.setLayout(null);
                panelSlideFiveA.setBackground(SystemColor.control);
                panelSlideFiveA.setBounds(24, 1137, 455, 317);
                panelSlideFiveA.setFont(new NZFont());
                NZLabel nzlabel = new NZLabel(resNZResource.getString("Now_you_need_to_choose_a_p1"));
                nzlabel.setBounds(125, 5, 438, 220);
                panelSlideFiveA.add(nzlabel);
                Label label = new Label(resNZResource.getString("Password_"));
                label.setAlignment(0);
                label.setBounds(130, 223, 70, 30);
                panelSlideFiveA.add(label);
                label = new Label(resNZResource.getString("Confirm_"));
                label.setAlignment(0);
                label.setBounds(130, 263, 50, 28);
                panelSlideFiveA.add(label);
                panelSlideFiveA.add(getTextFieldPwdSlideFiveA(), getTextFieldPwdSlideFiveA().getName());
                panelSlideFiveA.add(getTextFieldConfirmSlideFiveA(), getTextFieldConfirmSlideFiveA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideFiveA;
    }

    private Panel getPanelSlideFourA()
    {
        if(panelSlideFourA == null)
            try
            {
                panelSlideFourA = new Panel();
                panelSlideFourA.setName("Panel9");
                panelSlideFourA.setLayout(null);
                panelSlideFourA.setBackground(SystemColor.control);
                panelSlideFourA.setBounds(28, 793, 455, 317);
                panelSlideFourA.setFont(new NZFont());
                NZLabel nzlabel = new NZLabel(resNZResource.getString("Now_let_'s_activate_your_N1"));
                nzlabel.setBounds(125, 5, 439, 235);
                getPanelSlideFourA().add(nzlabel);
                nzlabel = new NZLabel(resNZResource.getString("Member_ID_"));
                nzlabel.setBounds(125, 248, 74, 17);
                getPanelSlideFourA().add(nzlabel);
                getPanelSlideFourA().add(getTextFieldIdSlideFourA(), getTextFieldIdSlideFourA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideFourA;
    }

    private Panel getPanelSlideSixA()
    {
        if(panelSlideSixA == null)
            try
            {
                panelSlideSixA = new Panel();
                panelSlideSixA.setName("PanelSlideSixA");
                panelSlideSixA.setLayout(null);
                panelSlideSixA.setBackground(NZColor.BACKGROUND);
                panelSlideSixA.setFont(new NZFont());
                panelSlideSixA.setBounds(24, 1137, 455, 317);
                NZLabel nzlabel = new NZLabel(resNZResource.getString("If_you_forget_your_passwor1"));
                nzlabel.setBounds(120, 5, 438, 230);
                getPanelSlideSixA().add(nzlabel);
                nzlabel = new NZLabel(resNZResource.getString("Question_"));
                nzlabel.setBounds(120, 240, 65, 20);
                getPanelSlideSixA().add(nzlabel);
                nzlabel = new NZLabel(resNZResource.getString("Answer_"));
                nzlabel.setBounds(120, 270, 55, 20);
                getPanelSlideSixA().add(nzlabel);
                getPanelSlideSixA().add(getChoiceQuestionSlideSixA(), getChoiceQuestionSlideSixA().getName());
                getPanelSlideSixA().add(getTextFieldAnswerSlideSixA(), getTextFieldAnswerSlideSixA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideSixA;
    }

    private Panel getPanelSlideTenA()
    {
        if(panelSlideTenA == null)
            try
            {
                panelSlideTenA = new Panel();
                panelSlideTenA.setName("PanelSlideTenA");
                panelSlideTenA.setFont(new NZFont());
                panelSlideTenA.setLayout(null);
                panelSlideTenA.setBackground(NZColor.BACKGROUND);
                panelSlideTenA.setBounds(508, 89, 455, 400);
                NZLabel nzlabel = new NZLabel();
                nzlabel.setBounds(125, 23, 440, 20);
                nzlabel.setLabel(resNZResource.getString("Let's_select_your_dialing_"));
                getPanelSlideTenA().add(nzlabel);
                JCGroupBox jcgroupbox = new JCGroupBox();
                jcgroupbox.setName("JCGroupBox10A");
                jcgroupbox.setBounds(130, 50, 325, 130);
                jcgroupbox.setShadowThickness(2);
                jcgroupbox.setLayout(null);
                jcgroupbox.add(getCheckboxDialTenA(), getCheckboxDialTenA().getName());
                jcgroupbox.add(getCheckboxDisableCallTenA(), getCheckboxDisableCallTenA().getName());
                jcgroupbox.add(getTextFieldATenA(), getTextFieldATenA().getName());
                nzlabel = new NZLabel();
                nzlabel.setText(resNZResource.getString("for_an_outside_line"));
                nzlabel.setBounds(107, 10, 150, 21);
                jcgroupbox.add(nzlabel);
                jcgroupbox.add(getCheckboxCustomTenA(), getCheckboxCustomTenA().getName());
                jcgroupbox.add(getTextFieldBTenA(), getTextFieldBTenA().getName());
                jcgroupbox.add(getTextFieldCTenA(), getTextFieldCTenA().getName());
                jcgroupbox.add(getCheckboxMuteModem());
                getPanelSlideTenA().add(jcgroupbox);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideTenA;
    }

    private Panel getPanelSlideThreeA()
    {
        if(panelSlideThreeA == null)
            try
            {
                panelSlideThreeA = new Panel();
                panelSlideThreeA.setName("Panel4");
                panelSlideThreeA.setFont(new NZFont());
                panelSlideThreeA.setLayout(null);
                panelSlideThreeA.setBackground(SystemColor.control);
                panelSlideThreeA.setBounds(518, 532, 455, 317);
                getPanelSlideThreeA().add(getBottomBox(), getBottomBox().getName());
                NZLabel nzlabel = new NZLabel(resNZResource.getString("To_protect_our_members_and1"));
                nzlabel.setBounds(120, 3, 417, 78);
                getPanelSlideThreeA().add(nzlabel);
                getPanelSlideThreeA().add(getTextAreaSlide3A(), getTextAreaSlide3A().getName());
                getPanelSlideThreeA().add(getCheckboxAcceptSlideThreeA(), getCheckboxAcceptSlideThreeA().getName());
                loadZipFile("privacy.txt", getTextAreaSlide3A());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideThreeA;
    }

    private Panel getPanelSlideTwelveA()
    {
        if(panelSlideTwelveA == null)
            try
            {
                panelSlideTwelveA = new Panel();
                panelSlideTwelveA.setLayout(null);
                AgComponent agcomponent = new AgComponent();
                agcomponent.initialize(null, "images/congrats.gif");
                agcomponent.setLocation(114, 5);
                panelSlideTwelveA.add(agcomponent);
                getPanelSlideTwelveA().add(getJCGroupBoxBottomTwelveA(), getJCGroupBoxBottomTwelveA().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return panelSlideTwelveA;
    }

    private java.awt.List getPhoneList7B()
    {
        if(phoneList7B == null)
            try
            {
                phoneList7B = new java.awt.List();
                phoneList7B.setName("ListPhoneListEightA");
                phoneList7B.setBounds(270, 200, 180, 80);
                phoneList7B.setMultipleMode(false);
                NZFont nzfont = new NZFont();
                phoneList7B.setFont(nzfont);
                phoneList7B.setBackground(Color.white);
                phoneList7B.setForeground(Color.blue);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return phoneList7B;
    }

    private JCGroupBox getPreDialGroupBox()
    {
        if(preDialGroupBox == null)
            try
            {
                preDialGroupBox = new JCGroupBox();
                preDialGroupBox.setName("preDialGroupBox");
                preDialGroupBox.setBounds(130, 110, 280, 150);
                preDialGroupBox.setTitle("");
                preDialGroupBox.setShadowThickness(2);
                preDialGroupBox.add(getPreDialPhoneList(), getPreDialPhoneList().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return preDialGroupBox;
    }

    private Panel getPreDialPanel()
    {
        if(preDialPanel == null)
            try
            {
                preDialPanel = new Panel();
                preDialPanel.setName("PreDialPanel");
                preDialPanel.setFont(new NZFont());
                preDialPanel.setLayout(null);
                preDialPanel.setBackground(NZColor.BACKGROUND);
                preDialPanel.setBounds(508, 89, 455, 400);
                JCLabel jclabel = new JCLabel();
                jclabel.setLabel(resNZResource.getString("The_following_phone_number"));
                jclabel.setAlignment(0);
                jclabel.setBackground(NZColor.BACKGROUND);
                jclabel.setBounds(130, 23, 300, 50);
                preDialPanel.add(jclabel);
                preDialPanel.add(getPreDialGroupBox(), getPreDialGroupBox().getName());
                preDialPanel.add(getIgnoreCheckbox());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return preDialPanel;
    }

    private java.awt.List getPreDialPhoneList()
    {
        if(preDialPhoneList == null)
            try
            {
                preDialPhoneList = new java.awt.List();
                preDialPhoneList.setName("PreDialPhoneList");
                preDialPhoneList.setBounds(140, 100, 400, 40);
                preDialPhoneList.setBackground(Color.white);
                preDialPhoneList.setForeground(Color.blue);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return preDialPhoneList;
    }

    private Label getStatusLabel()
    {
        if(statusLabel == null)
            try
            {
                statusLabel = new Label();
                statusLabel.setForeground(Color.red);
                statusLabel.setName("statusLabel");
                statusLabel.setBounds(20, 320, 240, 28);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return statusLabel;
    }

    private JCTextArea getTextAreaSlide3A()
    {
        if(textAreaSlide3A == null)
            try
            {
                textAreaSlide3A = new JCTextArea("", 5, 30, 3);
                textAreaSlide3A.setName("TextArea3A");
                textAreaSlide3A.setBounds(120, 83, 340, 197);
                textAreaSlide3A.setEditable(false);
                textAreaSlide3A.setFont(new Font("Monospaced", 0, 12));
                textAreaSlide3A.setForeground(Color.black);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textAreaSlide3A;
    }

    private TextField getTextFieldAnswerSlideSixA()
    {
        if(textFieldAnswerSlideSixA == null)
            try
            {
                textFieldAnswerSlideSixA = new TextField();
                textFieldAnswerSlideSixA.setName("TextField61");
                textFieldAnswerSlideSixA.setBounds(190, 268, 170, 20);
                textFieldAnswerSlideSixA.addTextListener(this);
                textFieldAnswerSlideSixA.setBackground(Color.white);
                textFieldAnswerSlideSixA.requestFocus();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldAnswerSlideSixA;
    }

    private TextField getTextFieldAreaCode7A()
    {
        if(textFieldAreaCode7A == null)
            try
            {
                textFieldAreaCode7A = new TextField();
                textFieldAreaCode7A.setName("TextFieldAreaCode7A");
                textFieldAreaCode7A.setBounds(147, 100, 40, 20);
                textFieldAreaCode7A.setFont(new NZFont());
                textFieldAreaCode7A.requestFocus();
                textFieldAreaCode7A.setBackground(Color.white);
                textFieldAreaCode7A.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldAreaCode7A;
    }

    private TextField getTextFieldAreaCode7B()
    {
        if(textFieldAreaCode7B == null)
            try
            {
                textFieldAreaCode7B = new TextField();
                textFieldAreaCode7B.setName("TextFieldAreaCode7B");
                textFieldAreaCode7B.setBounds(147, 80, 40, 20);
                textFieldAreaCode7B.setFont(new NZFont());
                textFieldAreaCode7B.requestFocus();
                textFieldAreaCode7B.setBackground(Color.white);
                textFieldAreaCode7B.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldAreaCode7B;
    }

    private TextField getTextFieldATenA()
    {
        if(textFieldATenA == null)
            try
            {
                textFieldATenA = new TextField();
                textFieldATenA.setName("TextFieldA");
                textFieldATenA.setBounds(63, 10, 40, 20);
                textFieldATenA.setBackground(Color.white);
                textFieldATenA.setText("9");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldATenA;
    }

    private TextField getTextFieldBTenA()
    {
        if(textFieldBTenA == null)
            try
            {
                textFieldBTenA = new TextField();
                textFieldBTenA.setName("TextFieldA");
                textFieldBTenA.setBounds(202, 40, 80, 20);
                textFieldBTenA.setBackground(Color.white);
                textFieldBTenA.setText("*70");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldBTenA;
    }

    private TextField getTextFieldConfirmSlideFiveA()
    {
        if(textFieldConfirmSlideFiveA == null)
            try
            {
                textFieldConfirmSlideFiveA = new TextField();
                textFieldConfirmSlideFiveA.setName("TextField6");
                textFieldConfirmSlideFiveA.setEchoChar('*');
                textFieldConfirmSlideFiveA.addTextListener(this);
                textFieldConfirmSlideFiveA.setBounds(200, 268, 125, 20);
                textFieldConfirmSlideFiveA.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldConfirmSlideFiveA;
    }

    private TextField getTextFieldCTenA()
    {
        if(textFieldCTenA == null)
            try
            {
                textFieldCTenA = new TextField();
                textFieldCTenA.setName("TextFieldA");
                textFieldCTenA.setBounds(118, 70, 164, 20);
                textFieldCTenA.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldCTenA;
    }

    private TextField getTextFieldFirstThree7A()
    {
        if(textFieldFirstThree7A == null)
            try
            {
                textFieldFirstThree7A = new TextField();
                textFieldFirstThree7A.setName("TextFieldFirstThree7A");
                textFieldFirstThree7A.setFont(new NZFont());
                textFieldFirstThree7A.setBounds(205, 80, 40, 20);
                textFieldFirstThree7A.setBackground(Color.white);
                textFieldFirstThree7A.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldFirstThree7A;
    }

    private TextField getTextFieldFirstThree7B()
    {
        if(textFieldFirstThree7B == null)
            try
            {
                textFieldFirstThree7B = new TextField();
                textFieldFirstThree7B.setName("TextFieldFirstThree7B");
                textFieldFirstThree7B.setFont(new NZFont());
                textFieldFirstThree7B.setBounds(205, 80, 40, 20);
                textFieldFirstThree7B.setBackground(Color.white);
                textFieldFirstThree7B.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldFirstThree7B;
    }

    private TextField getTextFieldID()
    {
        if(textFieldID == null)
            try
            {
                textFieldID = new TextField();
                textFieldID.setName("TextFieldID");
                textFieldID.setBounds(260, 160, 120, 20);
                textFieldID.setBackground(Color.white);
                textFieldID.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldID;
    }

    private TextField getTextFieldIdSlideFourA()
    {
        if(textFieldIdSlideFourA == null)
            try
            {
                textFieldIdSlideFourA = new TextField();
                textFieldIdSlideFourA.setName("TextField4");
                textFieldIdSlideFourA.setBackground(Color.white);
                textFieldIdSlideFourA.setBounds(200, 248, 200, 20);
                textFieldIdSlideFourA.setForeground(Color.black);
                textFieldIdSlideFourA.addTextListener(this);
                textFieldIdSlideFourA.requestFocus();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldIdSlideFourA;
    }

    private TextField getTextFieldNickName7A()
    {
        if(textFieldNickName7A == null)
            try
            {
                textFieldNickName7A = new TextField();
                textFieldNickName7A.setName("TextFieldNickName7A");
                textFieldNickName7A.setBounds(140, 150, 200, 20);
                textFieldNickName7A.setFont(new NZFont());
                textFieldNickName7A.requestFocus();
                textFieldNickName7A.setBackground(Color.white);
                textFieldNickName7A.setText(DialGroups.getCurrentGroupName());
                textFieldNickName7A.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldNickName7A;
    }

    private TextField getTextFieldNickName7B()
    {
        if(textFieldNickName7B == null)
            try
            {
                textFieldNickName7B = new TextField();
                textFieldNickName7B.setName("TextFieldNickName7B");
                textFieldNickName7B.setBounds(140, 150, 200, 20);
                textFieldNickName7B.setFont(new NZFont());
                textFieldNickName7B.requestFocus();
                textFieldNickName7B.setBackground(Color.white);
                textFieldNickName7B.setText(DialGroups.getCurrentGroupName());
                textFieldNickName7B.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldNickName7B;
    }

    private JCTextField getTextFieldPasswordConnectA()
    {
        if(textFieldPasswordConnectA == null)
            try
            {
                textFieldPasswordConnectA = new JCTextField();
                textFieldPasswordConnectA.setName("PasswordConnectA");
                textFieldPasswordConnectA.setEchoChar('*');
                textFieldPasswordConnectA.setBackground(Color.white);
                textFieldPasswordConnectA.addTextListener(this);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldPasswordConnectA;
    }

    private TextField getTextFieldPwdSlideFiveA()
    {
        if(textFieldPwdSlideFiveA == null)
            try
            {
                textFieldPwdSlideFiveA = new TextField();
                textFieldPwdSlideFiveA.setName("TextField5");
                textFieldPwdSlideFiveA.setEchoChar('*');
                textFieldPwdSlideFiveA.setBounds(200, 228, 125, 20);
                textFieldPwdSlideFiveA.addTextListener(this);
                textFieldPwdSlideFiveA.setBackground(Color.white);
                textFieldPwdSlideFiveA.requestFocus();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textFieldPwdSlideFiveA;
    }

    public ProfileRapper getUpdateProfileRapper()
    {
        return pnav.getUpdateProfile();
    }

    private void handleException(Exception exception)
    {
        ZCast.displayDebug(exception);
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        addKeyListener(this);
        addWindowListener(this);
        addFocusListener(this);
        getBackButton().addActionListener(this);
        getNextButton().addActionListener(this);
        getCancelButton().addActionListener(this);
        connRadioToNewAccount();
        connRadioToCurrentAccount();
        getListCitiesEightA().addActionListener(this);
        getListPhoneListEightA().addActionListener(this);
        getButtonSettingEightA().addActionListener(this);
        getButtonBackSignupTwelveA().addActionListener(this);
        getButtonFinishTwelveA().addActionListener(this);
        getCheckboxNewAccount().setState(true);
        getTextFieldID().setEnabled(false);
        getCheckboxNewAccount().addItemListener(this);
        getCheckboxCurrentAccount().addItemListener(this);
        getButtonDelNumbersEightA().addActionListener(this);
        getChoiceAreaCodesEightA().addItemListener(this);
        getButtonAddEightA().addActionListener(this);
        getCheckboxAcceptSlideThreeA().addItemListener(this);
        getTextFieldIdSlideFourA().addKeyListener(this);
        getTextFieldIdSlideFourA().addMouseListener(this);
        getTextFieldPwdSlideFiveA().addKeyListener(this);
        getTextFieldAnswerSlideSixA().addKeyListener(this);
        getTextFieldAreaCode7A().addKeyListener(this);
        getTextFieldFirstThree7A().addKeyListener(this);
        getTextFieldAreaCode7B().addKeyListener(this);
        getTextFieldFirstThree7B().addKeyListener(this);
        getCheckboxMuteModem().addItemListener(this);
        getChoiceDialingFromConnectA().addItemListener(this);
        getButtonModifyConnectA().addActionListener(this);
        getTextFieldPasswordConnectA().addKeyListener(this);
        getButtonDelConnectA().addActionListener(this);
        getChoiceMemberID().addItemListener(this);
        getButtonCancelConnectA().addActionListener(this);
        getButtonRemoveID().addActionListener(this);
        getButtonConnectA().addActionListener(this);
        getButtonConnectA().addKeyListener(this);
        getCheckboxSavePasswordConnectA().addItemListener(this);
        getNetworkCheckbox().addItemListener(this);
        getIgnoreCheckbox().addItemListener(this);
    }

    private void initialize()
    {
        DialGroups.readGroups();
        MemberRecs.readRecs();
        ZCast.displayDebug("Number of recs == " + MemberRecs.numMemberRecs());
        setName("Startup");
        setResizable(false);
        setLayout(new BorderLayout());
        setBackground(SystemColor.control);
        setVisible(false);
        setFont(new NZFont());
        setSize(467, 380);
        setTitle(resNZResource.getString("Welcome_to_Netzero"));
        add(getContentsPane(), "Center");
        initConnections();
        setVerifyProfileMode(false);
        updatePhoneList();
        getLoginInfo();
        ZCast.displayDebug("\nfirst time == " + firstTime);
        if(firstTime)
            showPanel1();
        else
            showConnectA();
        ZCast.centerComponent(this);
        OSDetectNative.setTopMost(getTitle());
        Thread thread = new Thread() {

            public void run()
            {
                try
                {
                    ZeroInPool.getInstance();
                    ZeroOutPool.getInstance();
                    MarqueePool.getInstance();
                }
                catch(Exception exception)
                {
                    ZCast.displayDebug(exception);
                }
            }

        };
        thread.start();
    }

    public boolean initProfileNav()
    {
        boolean flag;
label0:
        {
            flag = false;
            if(pnav != null)
                break label0;
            pnav = new ProfileNavigator();
            pnav.resetModifiedAnswers();
            MemberAnswer memberanswer = pnav.createAnswer(211, 0, 'F', MemberRecs.getCurrentMemberID());
            pnav.addModifiedAnswer(memberanswer);
            pnav.addModifiedAnswer(pnav.createAnswer(212, 0, 'F', MemberRecs.getCurrentPassword()));
            pnav.addModifiedAnswer(pnav.createAnswer(213, 0, 'F', ConfigParams.getVSource()));
            pnav.addModifiedAnswer(pnav.createAnswer(113, 0, 'F', MemberRecs.getQuestionCode()));
            pnav.addModifiedAnswer(pnav.createAnswer(114, 0, 'F', MemberRecs.getAnswer()));
            do
            {
                if(pnav.createNewUserOnHost(MemberRecs.getCurrentMemberID(), MemberRecs.getCurrentPassword()))
                {
                    flag = true;
                    break label0;
                }
                if(pnav.getTranResp().getReturnCode() != 2)
                    break;
                NewUserDialog newuserdialog = new NewUserDialog(ZCast.topFrame, true);
                ZCast.centerComponent(newuserdialog);
                newuserdialog.setVisible(true);
                if(!newuserdialog.getCancelStatus())
                {
                    MemberRecs.setCurrentMemberID(newuserdialog.getUserid());
                    memberanswer.setAnswerText(MemberRecs.getCurrentMemberID());
                } else
                {
                    flag = false;
                    break label0;
                }
            } while(true);
            flag = false;
        }
        return flag;
    }

    private void initRas()
    {
        if(!ZCast.m_demoMode && ZCast.m_osDetectNative.testRas())
        {
            ZCast.displayDebug("\n\nSET TO RAS MODE\n\n");
            ZCast.m_connectionType = 0;
            ZCast.m_rasWinNative = new RASWinNative();
            String s = ConfigParams.getDialEntry(ZCast.m_authCode);
            ZCast.m_rasWinNative.setNativeDebug(ZCast.m_nzDebugMode.equals("off") ^ true);
            if(!ZCast.m_rasWinNative.doesEntryExist(s))
            {
                ZCast.displayDebug("n\nNO DIAL ENTRY EXISTS!  CREATE DUN ENTRY!\n\n");
                if(ZCast.m_rasWinNative.createDunEntry(s, "111", "2222") > 0)
                {
                    ZCast.displayDebug("NetZero not configured for dialup; contact NetZero support");
                    String as[] = {
                        resNZResource.getString("ok")
                    };
                    NZDialogBox.showMessageDialog(ZCast.m_resNZResource.getString("NetZero_not_configured"), ZCast.m_resNZResource.getString("Please_check_readme.txt_fi"), 2, as);
                    ZCast.terminateProgram(3, null);
                }
            }
            if(!ZCast.m_rasWinNative.setModemMute(s, DialGroups.getMuteModem()))
                ZCast.displayDebug("FAILED TO SET MODEM MUTE!\n\n");
        }
    }

    private boolean isValidMemberID(String s)
    {
        boolean flag = true;
        try
        {
            if(s.length() > 30)
                return false;
            if(s.length() < 3)
                return false;
            if(!Character.isLetter(s.charAt(0)))
            {
                String as[] = {
                    resNZResource.getString("Ok")
                };
                NZDialogBox.showMessageDialog(resNZResource.getString("Member_ID_Error"), resNZResource.getString("Your_Member_ID_must_begin_"), 2, as);
                return false;
            }
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
            return false;
        }
        return true;
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        if(itemevent.getSource() == getCheckboxMuteModem())
        {
            ZCast.displayDebug("set state to " + getCheckboxMuteModem().getState());
            if(!getCheckboxMuteModem().getState())
            {
                String as[] = {
                    resNZResource.getString("yes"), resNZResource.getString("no")
                };
                int i = NZDialogBox.showMessageDialog(resNZResource.getString("Mute_Modem_During_Dialing"), resNZResource.getString("Are_you_sure_you_want_to_h"), 1, as);
                if(i != 0)
                    getCheckboxMuteModem().setState(true);
                requestFocus();
            }
        }
        if(itemevent.getSource() == networkCheckbox)
        {
            ZCast.displayDebug("set state to " + networkCheckbox.getState());
            DialGroups.setNetworkChecked(networkCheckbox.getState());
            DialGroups.writeGroups();
        }
        if(itemevent.getSource() == getChoiceMemberID())
        {
            ZCast.displayDebug("test item hit");
            if(getChoiceMemberID().getSelectedItem() == ADD_ID_STR)
            {
                if(MemberRecs.numMemberRecs() > 0 && getCheckboxSavePasswordConnectA().getState())
                {
                    String s = getTextFieldPasswordConnectA().getText().trim();
                    MemberRecs.setCurrentPassword(s);
                }
                if(DialGroups.numGroups() > 0)
                {
                    System.out.println("1Set oldnickname to nothing");
                    oldNickname = "";
                } else
                {
                    System.out.println("1Set oldnickname to Home");
                    oldNickname = resNZResource.getString("Home");
                }
                setPathAddMemberID();
                processNext();
            } else
            {
                refreshConnectATop();
            }
        }
        if(itemevent.getSource() == getCheckboxSavePasswordConnectA())
        {
            ZCast.displayDebug("hit the checkbox");
            boolean flag = getCheckboxSavePasswordConnectA().getState();
            MemberRecs.setSavePassword(flag);
            MemberRecs.writeRecs();
        }
        if(itemevent.getSource() == getChoiceAreaCodesEightA())
            fillCitiesLeftEightA(getChoiceAreaCodesEightA().getSelectedItem(), ConfigParams.getAllNumbers());
        if(itemevent.getSource() == choiceDialingFromConnectA)
        {
            String s1 = choiceDialingFromConnectA.getSelectedItem();
            if(s1.equals(resNZResource.getString("A_new_location")))
            {
                if(getCheckboxSavePasswordConnectA().getState())
                {
                    String s2 = getTextFieldPasswordConnectA().getText().trim();
                    MemberRecs.setCurrentPassword(s2);
                }
                getTextFieldAreaCode7A().requestFocus();
                getTextFieldAreaCode7A().setText("");
                getTextFieldFirstThree7A().setText("");
                if(DialGroups.numGroups() > 0)
                {
                    System.out.println("Set oldnickname to nothing");
                    oldNickname = "";
                } else
                {
                    System.out.println("Set oldnickname to Home");
                    oldNickname = resNZResource.getString("Home");
                }
                setPathCreateGroup();
                processNext();
            } else
            {
                DialGroups.setCurrentGroup(s1);
            }
        }
        if(itemevent.getSource() == getCheckboxAcceptSlideThreeA())
        {
            getNextButton().setEnabled(true);
            if(!getCheckboxAcceptSlideThreeA().getState())
                getNextButton().setEnabled(false);
        }
        if(itemevent.getSource() == getCheckboxNewAccount())
        {
            getTextFieldID().setEnabled(false);
            getTextFieldID().setText("");
            getNextButton().setEnabled(true);
            if(DialGroups.numGroups() > 0)
            {
                System.out.println("Set oldnickname to nothing");
                oldNickname = "";
                setPathCreateUserPickGroup();
                currentNode = 1;
            } else
            {
                System.out.println("Set oldnickname to Home");
                oldNickname = resNZResource.getString("Home");
                setPathCreateUserAndGroup();
                currentNode = 1;
            }
        }
        if(itemevent.getSource() == getCheckboxCurrentAccount())
        {
            firstTime = false;
            getTextFieldID().setEnabled(true);
            getTextFieldID().requestFocus();
            getNextButton().setEnabled(false);
            setPathAddMemberID();
            nextButton.setName("AddMemberIDButton");
            currentNode = 1;
        }
        if(itemevent.getSource() == getIgnoreCheckbox())
        {
            MemberRecs.setIgnoreConfirm(getIgnoreCheckbox().getState());
            ZCast.displayDebug("Set to ignore!!!");
        }
    }

    public void itemStateChanged(JCItemEvent jcitemevent)
    {
    }

    public void keyPressed(KeyEvent keyevent)
    {
        System.out.println("e is " + keyevent);
        if(showCon)
        {
            int i = keyevent.getKeyCode();
            if(i == 10)
            {
                if(getButtonConnectA().isEnabled())
                {
                    ZCast.displayDebug("Connect user");
                    connectUser(false);
                }
                if(keyevent.getSource() == getTextFieldPasswordConnectA() && getButtonConnectA().isEnabled())
                {
                    ZCast.displayDebug("Connect user");
                    connectUser(false);
                }
            }
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void loadZipFile(String s, JCTextArea jctextarea)
    {
        try
        {
            StringBuffer stringbuffer = new StringBuffer(50000);
            ZipFile zipfile = new ZipFile("file.zip");
            ZipEntry zipentry = zipfile.getEntry(s);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(zipfile.getInputStream(zipentry)));
            String s1;
            while((s1 = bufferedreader.readLine()) != null) 
            {
                stringbuffer.append("\n");
                stringbuffer.append(s1);
            }
            bufferedreader.close();
            zipfile.close();
            jctextarea.setText(stringbuffer.toString());
            jctextarea.setCaretPosition(0);
        }
        catch(IOException _ex) { }
    }

    public static void main(String args[])
    {
        Profile profile = new Profile();
        String s = null;
        if(s != null && s.length() > 3)
        {
            profile.setSaveLoginState(true);
            profile.setProfUid(s);
            profile.setTestProfPwd("");
            profile.setFirstTimeFlag(false);
        }
        showDialog(profile);
    }

    private void makeLabels7A()
    {
        byte byte0 = 120;
        int i = 5;
        try
        {
            NZLabel nzlabel = new NZLabel(resNZResource.getString("Please_enter_the_area_code"));
            nzlabel.setBounds(byte0, i, 440, 63);
            panelSlide7A.add(nzlabel);
            i += 66;
            nzlabel = new NZLabel("(");
            nzlabel.setBounds(byte0, i, 18, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7A.add(nzlabel);
            getTextFieldAreaCode7A().setBounds(byte0 + 13, i + 2, 41, 20);
            getTextFieldFirstThree7A().setBounds(byte0 + 71, i + 2, 41, 20);
            nzlabel = new NZLabel("- xxxx");
            nzlabel.setBounds(byte0 + 112, i, 80, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7A.add(nzlabel);
            nzlabel = new NZLabel(")");
            nzlabel.setBounds(byte0 + 53, i, 18, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7A.add(nzlabel);
            i += 35;
            nzlabel = new NZLabel(resNZResource.getString("Nickname_for_this_phone_li"));
            nzlabel.setBounds(byte0, i, 440, 20);
            panelSlide7A.add(nzlabel);
            i += 20;
            textFieldNickName7A.setBounds(byte0 + 5, i, 200, 20);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void makeLabels7B()
    {
        byte byte0 = 120;
        int i = 5;
        try
        {
            NZLabel nzlabel = new NZLabel(resNZResource.getString("Please_enter_the_area_code"));
            nzlabel.setBounds(byte0, i, 440, 63);
            panelSlide7B.add(nzlabel);
            i += 66;
            nzlabel = new NZLabel("(");
            nzlabel.setBounds(byte0, i, 18, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7B.add(nzlabel);
            getTextFieldAreaCode7B().setBounds(byte0 + 13, i + 2, 41, 20);
            getTextFieldFirstThree7B().setBounds(byte0 + 71, i + 2, 41, 20);
            nzlabel = new NZLabel("- xxxx");
            nzlabel.setBounds(byte0 + 112, i, 80, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7B.add(nzlabel);
            nzlabel = new NZLabel(")");
            nzlabel.setBounds(byte0 + 53, i, 18, 40);
            nzlabel.setFont(new Font("Arial", 0, 15));
            panelSlide7B.add(nzlabel);
            i += 35;
            nzlabel = new NZLabel(resNZResource.getString("Nickname_for_this_phone_li"));
            nzlabel.setBounds(byte0, i, 440, 20);
            panelSlide7B.add(nzlabel);
            i += 20;
            textFieldNickName7B.setBounds(byte0 + 5, i, 200, 20);
            i += 30;
            nzlabel = new NZLabel(resNZResource.getString("Choose_an_existing_phone_n"));
            nzlabel.setBounds(byte0, i, 155, 50);
            panelSlide7B.add(nzlabel);
            nzlabel = new NZLabel(resNZResource.getString("Phone_numbers_in_phone_lis"));
            nzlabel.setBounds(byte0 + 152, i + 15, 200, 50);
            panelSlide7B.add(nzlabel);
            i += 34;
            byte byte1 = 110;
            groupsList7B.setBounds(byte0 + 5, i, 130, byte1);
            phoneList7B.setBounds(byte0 + 157, i, 180, byte1);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }

    protected void persistLoginInfo(boolean flag)
    {
        if(prof != null)
        {
            prof.setSaveLoginState(flag);
            if(flag)
            {
                prof.setProfUid(MemberRecs.getCurrentMemberID());
                prof.setProfPwd(MemberRecs.getCurrentPassword());
            } else
            {
                prof.setProfUid("");
                prof.setProfPwd("");
            }
            prof.saveProperties();
        }
    }

    private boolean prepareForDialing()
    {
        initRas();
        if(DialGroups.numGroups() > 0)
        {
            checkNewNumbers();
            return checkBadNumbers();
        }
        if(DialGroups.numGroups() > 0)
        {
            System.out.println("1Set oldnickname to nothing");
            oldNickname = "";
        } else
        {
            System.out.println("1Set oldnickname to Home");
            oldNickname = resNZResource.getString("Home");
        }
        setPathConnectCreateGroup();
        allowConnect = true;
        processNext();
        return false;
    }

    private void printPath()
    {
        ZCast.displayDebug("\nPrinting Path:");
        if(path == null)
        {
            ZCast.displayDebug("patha is null");
            return;
        }
        for(int i = 0; i < path.length; i++)
        {
            if(path[i] == null)
                break;
            ZCast.displayDebug("---node " + i + "---");
            ZCast.displayDebug("back = " + path[i].back);
            ZCast.displayDebug("next = " + path[i].next);
            ZCast.displayDebug("---------------");
        }

    }

    private void processBack()
    {
        NodeType nodetype = null;
        if(currentNode > 0)
            nodetype = path[--currentNode];
        else
            nodetype = path[0];
        getNextButton().setActionCommand("next");
        processNode(nodetype.back);
    }

    private void processDoubleClick(java.awt.List list, String s)
    {
        for(int i = 0; i < list.getItemCount(); i++)
        {
            list.deselect(i);
            String s1 = list.getItem(i);
            if(s.equals(list.getItem(i)))
                list.select(i);
        }

    }

    private void processNext()
    {
        NodeType nodetype = path[currentNode++];
        processNode(nodetype.next);
    }

    private void processNode(int i)
    {
        switch(i)
        {
        case 111: // 'o'
            setCursor(Cursor.getPredefinedCursor(3));
            showPanel1();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 1: // '\001'
            setCursor(Cursor.getPredefinedCursor(3));
            showConnectA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 2: // '\002'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlide2A();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 3: // '\003'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideThreeA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 4: // '\004'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideFourA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 5: // '\005'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideFiveA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 6: // '\006'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideSixA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 7: // '\007'
            show8A = false;
            setCursor(Cursor.getPredefinedCursor(3));
            showSlide7A();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 71: // 'G'
            show8A = false;
            setCursor(Cursor.getPredefinedCursor(3));
            showSlide7AModify();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 75: // 'K'
            show8A = false;
            setCursor(Cursor.getPredefinedCursor(3));
            showSlide7B();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 8: // '\b'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideEightA();
            show8A = true;
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 9: // '\t'
            setCursor(Cursor.getPredefinedCursor(3));
            break;

        case 10: // '\n'
            show8A = false;
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideTenA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 11: // '\013'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideElevenA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 12: // '\f'
            setCursor(Cursor.getPredefinedCursor(3));
            showSlideTwelveA();
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 13: // '\r'
            setCursor(Cursor.getPredefinedCursor(3));
            connectUser(false);
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        case 14: // '\016'
            setCursor(Cursor.getPredefinedCursor(3));
            ZCast.m_authenticationMode = 0;
            connectUser(true);
            setCursor(Cursor.getPredefinedCursor(0));
            break;

        default:
            ZCast.displayDebug("Error processing node");
            break;
        }
    }

    private void refresh1A()
    {
        if(getCheckboxNewAccount().getState())
        {
            getNextButton().setEnabled(true);
        } else
        {
            String s = getTextFieldID().getText().trim();
            if(s.length() > 0)
                getNextButton().setEnabled(true);
            else
                getNextButton().setEnabled(false);
        }
    }

    private void refresh4A()
    {
        String s = getTextFieldIdSlideFourA().getText().toLowerCase().trim();
        if(s.length() >= 3 && s.length() <= 30)
            getNextButton().setEnabled(true);
        else
            getNextButton().setEnabled(false);
    }

    private void refresh5A()
    {
        String s = getTextFieldPwdSlideFiveA().getText();
        String s1 = getTextFieldConfirmSlideFiveA().getText();
        if(s != null && s.length() >= 3 && s1 != null && s1.length() >= 3)
            getNextButton().setEnabled(true);
        else
            getNextButton().setEnabled(false);
    }

    private void refresh6A()
    {
        String s = getTextFieldAnswerSlideSixA().getText().trim();
        if(s.length() > 0)
            getNextButton().setEnabled(true);
    }

    private void refresh7A()
    {
        if(validFields7A())
            getNextButton().setEnabled(true);
        else
            getNextButton().setEnabled(false);
    }

    private void refresh7B()
    {
        if(validFields7B())
            getNextButton().setEnabled(true);
        else
            getNextButton().setEnabled(false);
    }

    private void refreshButtons8A()
    {
        if(getListPhoneListEightA().getItemCount() > 0)
        {
            getButtonSettingEightA().setEnabled(true);
            getButtonDelNumbersEightA().setEnabled(true);
            getNextButton().setEnabled(true);
        } else
        {
            getButtonSettingEightA().setEnabled(false);
            getButtonDelNumbersEightA().setEnabled(false);
            getNextButton().setEnabled(false);
        }
    }

    private void refreshChoiceMemberID()
    {
        getChoiceMemberID().removeAll();
        getChoiceMemberID().add(ADD_ID_STR);
        MemberRecs.fillMemberIDChoice(getChoiceMemberID());
        if(MemberRecs.numMemberRecs() > 0)
            getButtonRemoveID().setEnabled(true);
        else
            getButtonRemoveID().setEnabled(false);
    }

    private void refreshConnectABottom()
    {
        getChoiceDialingFromConnectA().removeAll();
        getChoiceDialingFromConnectA().addItem(resNZResource.getString("A_new_location"));
        DialGroups.fillGroupChoice(getChoiceDialingFromConnectA());
        if(DialGroups.numGroups() <= 1)
            getButtonDelConnectA().setEnabled(false);
        else
            getButtonDelConnectA().setEnabled(true);
        if(DialGroups.numGroups() > 0)
            getButtonModifyConnectA().setEnabled(true);
        else
            getButtonModifyConnectA().setEnabled(false);
    }

    private void refreshConnectATop()
    {
        String s = getChoiceMemberID().getSelectedItem();
        MemberRecs.setCurrentMember(s);
        String s1 = MemberRecs.getCurrentPassword();
        getCheckboxSavePasswordConnectA().setState(MemberRecs.getSavePassword());
        if(getCheckboxSavePasswordConnectA().getState())
            getTextFieldPasswordConnectA().setText(s1);
        else
            getTextFieldPasswordConnectA().setText("");
        refreshPassword();
    }

    private void refreshPassword()
    {
        String s = getTextFieldPasswordConnectA().getText();
        if(s.length() > 0)
            getButtonConnectA().setEnabled(true);
        else
            getButtonConnectA().setEnabled(false);
    }

    private void removeMemberID()
    {
        try
        {
            Object obj = null;
            boolean flag = false;
            if(MemberRecs.numMemberRecs() > 0)
            {
                String s = getChoiceMemberID().getSelectedItem();
                MemberRecs.deleteID(s);
                MemberRecs.writeRecs();
                refreshChoiceMemberID();
            }
            refreshConnectATop();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void save10A()
    {
        String s = null;
        String s1 = null;
        String s2 = null;
        if(getCheckboxDialTenA().getState())
            s = getTextFieldATenA().getText();
        if(getCheckboxDisableCallTenA().getState())
            s1 = getTextFieldBTenA().getText();
        if(getCheckboxCustomTenA().getState())
            s2 = getTextFieldCTenA().getText();
        DialGroups.setPrefixes(s, s1, s2, checkboxMuteModem.getState());
    }

    public void setCancelStatus(boolean flag)
    {
        cancelStatus = flag;
    }

    private void setPathAddMemberID()
    {
        path = new NodeType[2];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        nodetype = new NodeType(1, 111);
        path[i++] = nodetype;
        nodetype = new NodeType(111, 1);
        path[i++] = nodetype;
    }

    private void setPathConnect()
    {
        path = new NodeType[1];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        nodetype = new NodeType(1, 13);
        path[i++] = nodetype;
    }

    private void setPathConnectCreateGroup()
    {
        path = new NodeType[6];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        DialGroups.makeEditGroup();
        modifyFlag = false;
        nodetype = new NodeType(1, 7);
        path[i++] = nodetype;
        nodetype = new NodeType(7, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 13);
        path[i++] = nodetype;
    }

    private void setPathConnectModifyGroup()
    {
        path = new NodeType[5];
        int i = 0;
        modifyFlag = true;
        NodeType nodetype = null;
        currentNode = 0;
        nodetype = new NodeType(1, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 13);
        path[i++] = nodetype;
    }

    private void setPathCreateGroup()
    {
        path = new NodeType[6];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        DialGroups.makeEditGroup();
        modifyFlag = false;
        nodetype = new NodeType(1, 7);
        path[i++] = nodetype;
        nodetype = new NodeType(7, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 1);
        path[i++] = nodetype;
    }

    private void setPathCreateUserAndGroup()
    {
        path = new NodeType[11];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        DialGroups.makeEditGroup();
        modifyFlag = false;
        nodetype = new NodeType(1, 2);
        path[i++] = nodetype;
        nodetype = new NodeType(2, 3);
        path[i++] = nodetype;
        nodetype = new NodeType(3, 4);
        path[i++] = nodetype;
        nodetype = new NodeType(4, 5);
        path[i++] = nodetype;
        nodetype = new NodeType(5, 6);
        path[i++] = nodetype;
        nodetype = new NodeType(6, 7);
        path[i++] = nodetype;
        nodetype = new NodeType(7, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 12);
        path[i++] = nodetype;
        nodetype = new NodeType(12, 14);
        path[i++] = nodetype;
    }

    private void setPathCreateUserNoPickedGroup()
    {
        path = new NodeType[11];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        DialGroups.makeEditGroup();
        modifyFlag = false;
        nodetype = new NodeType(1, 2);
        path[i++] = nodetype;
        nodetype = new NodeType(2, 3);
        path[i++] = nodetype;
        nodetype = new NodeType(3, 4);
        path[i++] = nodetype;
        nodetype = new NodeType(4, 5);
        path[i++] = nodetype;
        nodetype = new NodeType(5, 6);
        path[i++] = nodetype;
        nodetype = new NodeType(6, 75);
        path[i++] = nodetype;
        nodetype = new NodeType(75, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 12);
        path[i++] = nodetype;
        nodetype = new NodeType(12, 14);
        path[i++] = nodetype;
    }

    private void setPathCreateUserPickGroup()
    {
        path = new NodeType[8];
        int i = 0;
        NodeType nodetype = null;
        currentNode = 0;
        nodetype = new NodeType(1, 2);
        path[i++] = nodetype;
        nodetype = new NodeType(2, 3);
        path[i++] = nodetype;
        nodetype = new NodeType(3, 4);
        path[i++] = nodetype;
        nodetype = new NodeType(4, 5);
        path[i++] = nodetype;
        nodetype = new NodeType(5, 6);
        path[i++] = nodetype;
        nodetype = new NodeType(6, 75);
        path[i++] = nodetype;
        nodetype = new NodeType(75, 12);
        path[i++] = nodetype;
        nodetype = new NodeType(12, 14);
        path[i++] = nodetype;
    }

    private void setPathModifyGroup()
    {
        path = new NodeType[5];
        int i = 0;
        modifyFlag = true;
        NodeType nodetype = null;
        currentNode = 0;
        nodetype = new NodeType(1, 71);
        path[i++] = nodetype;
        nodetype = new NodeType(71, 8);
        path[i++] = nodetype;
        nodetype = new NodeType(8, 10);
        path[i++] = nodetype;
        nodetype = new NodeType(10, 11);
        path[i++] = nodetype;
        nodetype = new NodeType(11, 1);
        path[i++] = nodetype;
    }

    public void setStatusLabel(String s)
    {
        zrIn.setStatusText(s);
    }

    public void setVerifyProfileMode(boolean flag)
    {
        getProfileFromServer = flag;
    }

    public void showConnectA()
    {
        DialGroups.resetEditGroup();
        getContentsPane().add("connectA", getPanelConnectA());
        refreshChoiceMemberID();
        refreshConnectATop();
        refreshConnectABottom();
        setSize(348, 313);
        showCon = true;
        setTitle(resNZResource.getString("NetZero_v") + ZCast.getZcastVersion() + resNZResource.getString("_Logon"));
        getNextButton().setActionCommand("next");
        clearAllPanels();
        modifyFlag = false;
        allowConnect = true;
        String s = getTextFieldPasswordConnectA().getText();
        if(s.length() >= 3 && MemberRecs.getCurrentMemberID().length() > 0)
            getButtonConnectA().requestFocus();
        else
            getTextFieldPasswordConnectA().requestFocus();
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "connectA");
    }

    public static final void showDialog(Profile profile)
    {
        try
        {
            Startup startup = new Startup(new Frame(), true, profile);
            ZCast.centerComponent(startup);
            startup.setVisible(true);
            System.exit(0);
        }
        catch(Throwable throwable)
        {
            ZCast.displayDebug("Exception occurred in main() of java.awt.Dialog");
            ZCast.displayDebug(throwable);
        }
    }

    public void showPanel1()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanel1().add(getNZImageBox());
        getPanel1().add(getBottomBox(), getBottomBox().getName());
        if(firstTime)
            getBackButton().setEnabled(false);
        else
            getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getNextButton().setActionCommand("Panel1Next");
        getTextFieldID().setText("");
        getCheckboxNewAccount().setState(true);
        getCheckboxCurrentAccount().setState(false);
        refresh1A();
        getContentsPane().add("startup", getPanel1());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "startup");
    }

    private void showPanel11A()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideElevenA().add(getBottomBox(), getBottomBox().getName());
        getNextButton().setActionCommand("11Anext");
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getPanelSlideElevenA().add(getNZImageBox());
        DialGroups.setFinalPhoneList(getListPhoneListElevenA());
        getContentsPane().add("SlideElevenA", getPanelSlideElevenA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideElevenA");
    }

    private void showPreDialPanel()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("Dialing_NetZero"));
        getPreDialPanel().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getPreDialPanel().add(getNZImageBox());
        DialGroups.setFinalPhoneList(getPreDialPhoneList());
        getContentsPane().add("PreDialPanel", getPreDialPanel());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "PreDialPanel");
    }

    public void showSlide2A()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlide2A().add(getNZImageBox());
        getPanelSlide2A().add(getBottomBox(), getBottomBox().getName());
        getNextButton().setActionCommand("next");
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getContentsPane().add("Slide2A", getPanelSlide2A());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "Slide2A");
    }

    public void showSlide7A()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        ZCast.displayDebug("showing slide seven a in 7A");
        getPanelSlide7A().add(getNZImageBox());
        getPanelSlide7A().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(false);
        if(modifyFlag)
        {
            getNextButton().setEnabled(true);
            setTitle(resNZResource.getString("Modify_Phone_List"));
            DialGroups.fillDialFromTextFields(getTextFieldAreaCode7A(), getTextFieldFirstThree7A());
            oldAreaCode = getTextFieldAreaCode7A().getText();
            oldFirst3 = getTextFieldFirstThree7A().getText();
        } else
        {
            setTitle(resNZResource.getString("Create_a_Phone_List"));
            getTextFieldAreaCode7A().setText(oldAreaCode);
            getTextFieldFirstThree7A().setText(oldFirst3);
        }
        System.out.println("oldNickname is " + oldNickname);
        getTextFieldNickName7A().setText(oldNickname);
        ZCast.displayDebug("set action to Modify7A");
        getNextButton().setActionCommand("Modify7A");
        if(DialGroups.numGroups() == 0 && DialGroups.getCurrentGroupName().length() == 0)
            getNextButton().setActionCommand("AddGroup7A");
        refresh7A();
        getContentsPane().add("SlideSevenA", getPanelSlide7A());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideSevenA");
        getTextFieldAreaCode7A().requestFocus();
    }

    private void showSlide7AModify()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("Modify_a_Phone_List"));
        ZCast.displayDebug("showing slide seven a");
        getPanelSlide7A().add(getNZImageBox());
        getPanelSlide7A().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getPanelSlide7A().add(getBottomBox(), getBottomBox().getName());
        DialGroups.fillDialFromTextFields(getTextFieldAreaCode7A(), getTextFieldFirstThree7A());
        getTextFieldAreaCode7A().requestFocus();
        String s = DialGroups.getCurrentGroupName();
        getTextFieldNickName7A().setText(s);
        getTextFieldAreaCode7A().requestFocus();
        getNextButton().setActionCommand("Modify7A");
        DialGroups.fillDialFromTextFields(getTextFieldAreaCode7A(), getTextFieldFirstThree7A());
        oldAreaCode = getTextFieldAreaCode7A().getText();
        oldFirst3 = getTextFieldFirstThree7A().getText();
        getContentsPane().add("SlideSevenA", getPanelSlide7A());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideSevenA");
        refresh7A();
        nextButton.setEnabled(true);
    }

    private void showSlide7B()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        ZCast.displayDebug("showing slide 7B");
        setTitle(resNZResource.getString("Create_or_Choose_a_Phone_L"));
        getPanelSlide7B().add(getNZImageBox());
        getPanelSlide7B().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(true);
        getNextButton().setActionCommand("Process7B");
        fill7B();
        if(oldAreaCode.length() > 0)
        {
            getTextFieldAreaCode7B().setText(oldAreaCode);
            getTextFieldFirstThree7B().setText(oldFirst3);
        } else
        {
            DialGroups.fillDialFromTextFields(getTextFieldAreaCode7B(), getTextFieldFirstThree7B());
        }
        getTextFieldAreaCode7B().requestFocus();
        refresh7B();
        setCursor(Cursor.getPredefinedCursor(0));
        repaint();
        getNZImageBox().repaint();
        getContentsPane().add("Slide7B", getPanelSlide7B());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "Slide7B");
    }

    public void showSlideEightA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("Modifying_Phone_List__") + DialGroups.getCurrentGroupName());
        getPanelSlide8A().add(getNZImageBox());
        getPanelSlide8A().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(false);
        getNextButton().setActionCommand("next");
        getBackButton().setActionCommand("Back8A");
        if(refreshAC)
        {
            ZCast.displayDebug("\n\n#########----##### REFRESHING AREA CODES ----####\n\n");
            DialGroups.selectChoiceAreaCode(getChoiceAreaCodesEightA());
            int i = getChoiceAreaCodesEightA().getSelectedIndex();
            if(i == 0)
            {
                ZCast.displayDebug("AREA CODE NOT IN LIST");
                getChoiceAreaCodesEightA().select(1);
                if(showACError)
                {
                    String as[] = {
                        resNZResource.getString("Ok")
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Area_Code_Warning"), resNZResource.getString("The_area_code_you_selected"), 0, as);
                    showACError = false;
                }
            }
        }
        getContentsPane().add("SlideEightA", getPanelSlide8A());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideEightA");
        if(refreshCities)
        {
            ZCast.displayDebug("\n\n#########----##### REFRESHING CITIES ----####\n\n");
            fillCitiesLeftEightA(getChoiceAreaCodesEightA().getSelectedItem(), ConfigParams.getAllNumbers());
        }
        fillPhoneNumbersRightEightA();
    }

    public void showSlideElevenA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        save10A();
        showPanel11A();
    }

    public void showSlideFiveA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getContentsPane().add("SlideFiveA", getPanelSlideFiveA());
        getPanelSlideFiveA().add(getNZImageBox());
        getPanelSlideFiveA().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(false);
        getNextButton().setActionCommand("PasswordNext");
        getTextFieldPwdSlideFiveA().requestFocus();
        refresh5A();
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideFiveA");
    }

    public void showSlideFourA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideFourA().add(getNZImageBox());
        getPanelSlideFourA().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(false);
        getNextButton().setActionCommand("MemberIDNext");
        refresh4A();
        getContentsPane().add("SlideFourA", getPanelSlideFourA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideFourA");
        getTextFieldIdSlideFourA().requestFocus();
    }

    public void showSlideSixA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideSixA().add(getNZImageBox());
        getPanelSlideSixA().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(false);
        refresh6A();
        getContentsPane().add("SlideSixA", getPanelSlideSixA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideSixA");
        getTextFieldAnswerSlideSixA().requestFocus();
    }

    public void showSlideTenA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideTenA().add(getNZImageBox());
        getPanelSlideTenA().add(getBottomBox(), getBottomBox().getName());
        DialGroups.setPrefixGUI(getCheckboxDialTenA(), getTextFieldATenA(), getCheckboxDisableCallTenA(), getTextFieldBTenA(), getCheckboxCustomTenA(), getTextFieldCTenA(), getCheckboxMuteModem());
        getNextButton().setActionCommand("next");
        getBackButton().setActionCommand("TenABackTenA");
        showACError = false;
        getContentsPane().add("SlideTenA", getPanelSlideTenA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideTenA");
    }

    public void showSlideThreeA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideThreeA().add(getNZImageBox());
        getPanelSlideThreeA().add(getBottomBox(), getBottomBox().getName());
        getBackButton().setEnabled(true);
        getNextButton().setEnabled(getCheckboxAcceptSlideThreeA().getState());
        getContentsPane().add("SlideThreeA", getPanelSlideThreeA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideThreeA");
    }

    public void showSlideTwelveA()
    {
        showCon = false;
        getContentsPane().setSize(467, 380);
        setSize(467, 380);
        setTitle(resNZResource.getString("NetZero_Signup"));
        getPanelSlideTwelveA().add(getNZImageBox());
        if(DialGroups.numGroups() < 2)
            getButtonDelConnectA().setEnabled(false);
        else
            getButtonDelConnectA().setEnabled(true);
        getContentsPane().add("SlideTwelveA", getPanelSlideTwelveA());
        ((CardLayout)getContentsPane().getLayout()).show(getContentsPane(), "SlideTwelveA");
    }

    public void showZeroInDialog()
    {
        showCon = false;
        TviPlayer.getInterface().init();
        BrowserDisplay.initBrowser();
        setSize(348, 313);
        zrIn = new ZeroIn(ZCast.topFrame, "NZTV", false, this);
        if(zrIn != null)
        {
            zrIn.setSize(348, 313);
            zrIn.setBackground(Color.black);
            ZCast.centerComponent(zrIn);
            setVisible(false);
            ZCast.displayDebug(">>>>>>>>>>>>>>>>>>>>>>>>>RIght before setVisible()");
            zrIn.setVisible(true);
            ZCast.displayDebug(">>>>>>>>>>>>>>>>>>>>>>>>>RIght after setVisible()");
            zrIn.start();
            try
            {
                Thread.sleep(0L);
            }
            catch(Exception _ex) { }
            Thread thread = new Thread() {

                public void run()
                {
                    OSDetectNative.updateProcessIcons();
                    OSDetectNative.osw32stopPaletteMessages("NZTV");
                    int i = OSDetectNative.getWindowHandle("NZTV");
                    OSDetectNative.removeSystemMenuItemByHandle(i, 4);
                    OSDetectNative.removeSystemMenuItemByHandle(i, 3);
                    OSDetectNative.removeSystemMenuItemByHandle(i, 2);
                    OSDetectNative.removeSystemMenuItemByHandle(i, 0);
                }

            };
            thread.start();
        }
    }

    public void terminateProgram()
    {
        if(MemberRecs.numMemberRecs() > 0)
        {
            boolean flag = getCheckboxSavePasswordConnectA().getState();
            if(flag)
                MemberRecs.setCurrentPassword(getTextFieldPasswordConnectA().getText());
            MemberRecs.writeRecs();
        }
        java.awt.Point point = getLocationOnScreen();
        String as[] = {
            "Yes", "No"
        };
        int i = NZDialogBox.showMessageDialog(resNZResource.getString("NetZero_Logoff"), resNZResource.getString("Are_you_sure_you_want_to_q"), 1, as);
        if(i == 0)
        {
            setVisible(false);
            setCancelStatus(true);
            ZCast.terminateProgram(5, null);
            dispose();
        }
        requestFocus();
    }

    public void textValueChangeBegin(JCTextEvent jctextevent)
    {
        if(firstTime)
            return;
        if(jctextevent.getSource() == getTextFieldPasswordConnectA())
            refreshPassword();
    }

    public void textValueChanged(TextEvent textevent)
    {
        if(firstTime)
            return;
        if(textevent.getSource() == getTextFieldID())
            refresh1A();
        if(textevent.getSource() == getTextFieldIdSlideFourA())
        {
            String s = getTextFieldIdSlideFourA().getText();
            checkID4A(s, getTextFieldIdSlideFourA());
            refresh4A();
        }
        if(textevent.getSource() == getTextFieldPwdSlideFiveA())
        {
            String s1 = getTextFieldPwdSlideFiveA().getText();
            checkPass5A(s1, getTextFieldPwdSlideFiveA());
            refresh5A();
        }
        if(textevent.getSource() == getTextFieldConfirmSlideFiveA())
        {
            String s2 = getTextFieldConfirmSlideFiveA().getText();
            checkConf5A(s2, getTextFieldConfirmSlideFiveA());
            refresh5A();
        }
        if(textevent.getSource() == getTextFieldAnswerSlideSixA())
        {
            String s3 = getTextFieldAnswerSlideSixA().getText();
            if(s3.length() > 0)
                getNextButton().setEnabled(true);
            else
                getNextButton().setEnabled(false);
        }
        if(textevent.getSource() == getTextFieldAreaCode7A())
        {
            String s4 = getTextFieldAreaCode7A().getText();
            System.out.println("newstr == " + s4);
            if(!s4.equals(oldAreaCode))
            {
                checkAreaCode(s4, getTextFieldAreaCode7A(), getTextFieldFirstThree7A());
                oldAreaCode = getTextFieldAreaCode7A().getText();
            }
            refresh7A();
        }
        if(textevent.getSource() == getTextFieldFirstThree7A())
        {
            String s5 = getTextFieldFirstThree7A().getText();
            System.out.println("newstr == " + s5);
            if(!s5.equals(oldFirst3))
            {
                checkFirst3(s5, getTextFieldFirstThree7A(), getTextFieldNickName7A());
                oldFirst3 = getTextFieldFirstThree7A().getText();
            }
            refresh7A();
        }
        if(textevent.getSource() == getTextFieldNickName7A())
        {
            String s6 = getTextFieldNickName7A().getText();
            checkNickname7A(s6);
            oldNickname = getTextFieldNickName7A().getText();
            refresh7A();
        }
        if(textevent.getSource() == getTextFieldAreaCode7B())
        {
            String s7 = getTextFieldAreaCode7B().getText();
            checkAreaCode(s7, getTextFieldAreaCode7B(), getTextFieldFirstThree7B());
            oldAreaCode = getTextFieldAreaCode7B().getText();
            refresh7B();
        }
        if(textevent.getSource() == getTextFieldFirstThree7B())
        {
            String s8 = getTextFieldFirstThree7B().getText();
            checkFirst3(s8, getTextFieldFirstThree7B(), getTextFieldNickName7B());
            oldFirst3 = getTextFieldFirstThree7B().getText();
            refresh7B();
        }
        if(textevent.getSource() == getTextFieldNickName7B())
        {
            String s9 = getTextFieldNickName7B().getText();
            checkNickname7B(s9);
            oldNickname = getTextFieldNickName7B().getText();
            refresh7B();
        }
    }

    public void textValueChangeEnd(JCTextEvent jctextevent)
    {
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void updatePhoneList()
    {
        File file = new File("phn.dat");
        ZCast.displayDebug("phn", "-->phoneList: check existence of phn.dat=" + file.toString());
        try
        {
            if(!file.exists())
            {
                ZipFile zipfile = new ZipFile("lib/zcast1_6.zip");
                ZipEntry zipentry = zipfile.getEntry("phn.dat");
                ZCast.displayDebug("phn", "-->phoneList: extracting internal phn.dat, ze=" + zipentry.toString());
                try
                {
                    InputStream inputstream = zipfile.getInputStream(zipentry);
                    FileOutputStream fileoutputstream = new FileOutputStream("defphn.dat");
                    byte abyte0[] = new byte[4096];
                    int i;
                    while((i = inputstream.read(abyte0, 0, 4096)) > 0) 
                        fileoutputstream.write(abyte0, 0, i);
                    inputstream.close();
                    fileoutputstream.close();
                }
                catch(Exception exception1)
                {
                    ZCast.displayDebug(exception1);
                }
                zipfile.close();
                file = new File("defphn.dat");
            }
            if(ConfigParams.initalize(file) && file.getName().equals("defphn.dat"))
            {
                boolean flag = file.delete();
                ZCast.displayDebug("phn", "-->phoneList: deleting internal phn.dat, rc=" + flag);
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
    }

    private boolean validAreaCode(String s)
    {
        int i = s.length();
        if(i != 3)
            return false;
        for(int j = 0; j < i; j++)
            if(!Character.isDigit(s.charAt(j)))
                return false;

        return true;
    }

    private boolean validFields7A()
    {
        if(!validAreaCode(getTextFieldAreaCode7A().getText()))
            return false;
        if(!validFirst3(getTextFieldFirstThree7A().getText()))
            return false;
        return validGroupName(getTextFieldNickName7A().getText());
    }

    private boolean validFields7B()
    {
        if(!validAreaCode(getTextFieldAreaCode7B().getText()))
            return false;
        if(!validFirst3(getTextFieldFirstThree7B().getText()))
            return false;
        return validGroupName(getTextFieldNickName7B().getText());
    }

    private boolean validFirst3(String s)
    {
        int i = s.length();
        if(i != 3)
            return false;
        for(int j = 0; j < i; j++)
            if(!Character.isDigit(s.charAt(j)))
                return false;

        return true;
    }

    private boolean validGroupName(String s)
    {
        int i = s.length();
        if(i < 3 || i > 30)
            return false;
        return Character.isUnicodeIdentifierStart(s.charAt(0));
    }

    private boolean validPassConfirm()
    {
        String s = getTextFieldPwdSlideFiveA().getText().trim();
        String s1 = getTextFieldConfirmSlideFiveA().getText().trim();
        if(s.equalsIgnoreCase("password"))
        {
            String as[] = {
                resNZResource.getString("Ok")
            };
            NZDialogBox.showMessageDialog(resNZResource.getString("Password_Error"), resNZResource.getString("You_may_not_use___password"), 2, as);
            return false;
        }
        if(s.equalsIgnoreCase(getTextFieldIdSlideFourA().getText().trim()))
        {
            String as1[] = {
                resNZResource.getString("Ok")
            };
            NZDialogBox.showMessageDialog(resNZResource.getString("Password_Error"), resNZResource.getString("Your_password_may_not_matc"), 2, as1);
            return false;
        }
        if(s.equalsIgnoreCase(s1))
        {
            return true;
        } else
        {
            String as2[] = {
                resNZResource.getString("Ok")
            };
            NZDialogBox.showMessageDialog(resNZResource.getString("Password_Error"), resNZResource.getString("Your_entries_for_password_"), 2, as2);
            return false;
        }
    }

    public void windowActivated(WindowEvent windowevent)
    {
        if(showCon)
        {
            String s = getTextFieldPasswordConnectA().getText();
            if(s.length() >= 3 && MemberRecs.getCurrentMemberID().length() > 0)
                getButtonConnectA().requestFocus();
            else
                getTextFieldPasswordConnectA().requestFocus();
        }
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        if(windowevent.getSource() == this)
            terminateProgram();
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

    public void zeroInCancelNotification()
    {
        ZCast.displayDebug("Startup.zeroInCancelNotification() STARTS");
        DialObject.doDisconnect();
        if(Initializer.m_zwindow != null)
            Initializer.m_zwindow = null;
        if(initObj != null && initObj.isAlive())
        {
            ZCast.displayDebug("Startup.zeroInCancelNotification() DESTROYING Initializer");
            startUpState = 2;
            initObj.interrupt();
            initObj = null;
        }
        showConnectA();
        setVisible(true);
        ZCast.displayDebug("Startup.zeroInCancelNotification() ENDS");
    }

    public void zeroInFinishNotification()
    {
        ZCast.displayDebug("Startup.zeroInFinishNotification() initObj=" + initObj);
        try
        {
            startUpState = 0;
            if(initObj != null)
                initObj.interrupt();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        TviPlayer.getInterface().cleanup();
        dispose();
    }

    private static ResourceBundle resNZResource;
    private static final String ADD_ID_STR;
    private static final String ADD_GROUP7B;
    private static final String INSTALL_ZIP = "lib/zcast1_6.zip";
    public static final int MIN_USERID_LENGTH = 3;
    public static final int MAX_USERID_LENGTH = 30;
    public static final int MIN_PASSWORD_LENGTH = 3;
    public static final int MAX_PASSWORD_LENGTH = 12;
    public static final int DONE = 0;
    public static final int NOT_DONE = 1;
    public static final int CANCEL = 2;
    private static final int PANEL1 = 111;
    private static final int CONNECTA = 1;
    private static final int TWOA = 2;
    private static final int THREEA = 3;
    private static final int FOURA = 4;
    private static final int FIVEA = 5;
    private static final int SIXA = 6;
    private static final int SEVENA = 7;
    private static final int SEVENAM = 71;
    private static final int SEVENB = 75;
    private static final int EIGHTA = 8;
    private static final int NINEA = 9;
    private static final int TENA = 10;
    private static final int ELEVENA = 11;
    private static final int TWELVEA = 12;
    private static final int CONNECT_NZ = 13;
    private static final int CONNECT_NZ_NEW_USER = 14;
    private boolean modifyFlag;
    private boolean savedGroups;
    private Profile prof;
    private ProfileNavigator pnav;
    private boolean cancelStatus;
    private boolean firstTime;
    private boolean getProfileFromServer;
    private Panel ivjPanelFree;
    private Label statusLabel;
    private Button ivjCancelFreeButton;
    private Button ivjExitFreeButton;
    private NZButton backButton;
    private NZButton nextButton;
    private NZButton cancelButton;
    private JCGroupBox bottomBox;
    private AgCanvas leftImage;
    private AgCanvas topImage;
    private int currentNode;
    private NodeType path[];
    private Panel preDialPanel;
    private JCGroupBox preDialGroupBox;
    private java.awt.List preDialPhoneList;
    private Checkbox ignoreCheckbox;
    private Panel ivjContentsPane;
    private Panel ivjPanel1;
    private Panel panelBottomAccount;
    private JCGroupBox groupBoxBottom;
    private Checkbox checkboxNewAccount;
    private Checkbox checkboxCurrentAccount;
    private CheckboxGroup checkboxGroupAccount;
    private TextField textFieldID;
    private Panel panelSlide2A;
    private Panel panelBottomAccount2A;
    private Panel panelSlide7A;
    private TextField textFieldNickName7A;
    private TextField textFieldAreaCode7A;
    private TextField textFieldFirstThree7A;
    private TextField textFieldLastFour7A;
    private static String oldAreaCode = "";
    private static String oldFirst3 = "";
    private static String oldLast4 = "";
    private static String oldNickname = "";
    private static String oldID = "";
    private static String oldPass = "";
    private static String oldConf = "";
    private Panel panelSlide7B;
    private java.awt.List groupsList7B;
    private java.awt.List phoneList7B;
    private TextField textFieldNickName7B;
    private TextField textFieldAreaCode7B;
    private TextField textFieldFirstThree7B;
    private boolean showACError;
    private boolean refreshAC;
    private boolean refreshCities;
    String curState;
    private Panel panelSlideEightA;
    private Panel panelBottomAccountEightA;
    private JCGroupBox groupBoxLeftEightA;
    private JCGroupBox groupBoxRightEightA;
    private java.awt.List listCitiesEightA;
    private java.awt.List listPhoneListEightA;
    private NZButton buttonAddEightA;
    private Choice choiceGroupEightA;
    private NZButton buttonDelEightA;
    private NZButton buttonDelConnectA;
    private NZButton buttonDelNumbersEightA;
    private NZButton buttonSettingEightA;
    private Choice choiceAreaCodesEightA;
    private Panel panelSlideTenA;
    private Panel panelBottomAccountTenA;
    private Checkbox checkboxDialTenA;
    private Checkbox checkboxDisableCallTenA;
    private Checkbox checkboxCustomTenA;
    private Checkbox checkboxMuteModem;
    private TextField textFieldATenA;
    private TextField textFieldBTenA;
    private TextField textFieldCTenA;
    private Panel panelSlideElevenA;
    private java.awt.List listPhoneListElevenA;
    private Panel panelSlideTwelveA;
    private NZButton buttonFinishTwelveA;
    private NZButton buttonBackSignupTwelveA;
    private JCGroupBox groupBoxBottomTwelveA;
    private JCGroupBox connectOptionsBox;
    private Panel panelConnectA;
    private JCGroupBox groupBoxBottomConnectA;
    private NZButton buttonCancelConnectA;
    private NZButton buttonModifyConnectA;
    private NZButton buttonRemoveID;
    private NZButton buttonConnectA;
    private Checkbox checkboxSavePasswordConnectA;
    private JCTextField textFieldPasswordConnectA;
    private Choice choiceDialingFromConnectA;
    private Choice choiceMemberID;
    private Checkbox networkCheckbox;
    private Panel panelSlideThreeA;
    private Checkbox checkboxAcceptSlideThreeA;
    private JCTextArea textAreaSlide3A;
    private Panel panelSlideFourA;
    private TextField textFieldIdSlideFourA;
    private Panel panelSlideFiveA;
    private TextField textFieldPwdSlideFiveA;
    private TextField textFieldConfirmSlideFiveA;
    private Panel panelSlideSixA;
    private TextField textFieldAnswerSlideSixA;
    private Choice choiceQuestionSlideSixA;
    private static Initializer initObj = null;
    private ZeroIn zrIn;
    public Frame m_parent;
    private boolean show8A;
    private boolean showCon;
    public static volatile int startUpState = 1;
    private static Startup me = null;
    private boolean allowConnect;

    static 
    {
        resNZResource = ResourceBundle.getBundle("gui.NZResource");
        ADD_ID_STR = resNZResource.getString("Add_New_Member_ID");
        ADD_GROUP7B = resNZResource.getString("Make_New_Phone_List");
    }


















}
