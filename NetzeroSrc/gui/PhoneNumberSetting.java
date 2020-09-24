// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhoneNumberSetting.java

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.Vector;
import jclass.base.TransientComponent;
import jclass.bwt.JCActionEvent;
import jclass.bwt.JCActionListener;
import jclass.bwt.JCCheckbox;
import jclass.bwt.JCCheckboxGroup;
import jclass.bwt.JCContainer;
import jclass.bwt.JCGroupBox;
import jclass.bwt.JCItemEvent;
import jclass.bwt.JCItemListener;
import jclass.bwt.JCLabel;
import nzcom.OSDetectNative;
import nzcom.ZCast;

// Referenced classes of package gui:
//            NZLabel, PhoneRec, DialGroups, NZDialogBox, 
//            NZButton, NZFont

public class PhoneNumberSetting extends Dialog
    implements WindowListener, JCActionListener, ActionListener, TextListener, ItemListener, JCItemListener
{

    public PhoneNumberSetting(Frame frame, Vector vector)
    {
        super(frame);
        ivjContentsPane = null;
        TopLabel = null;
        ApplyButton = null;
        SelectAllButton = null;
        ivjJCButton3 = null;
        cb_1ac = null;
        cb_ac = null;
        cb_neither = null;
        ivjJCCheckboxGroup1 = null;
        cb_box = null;
        ivjJCGroupBox2 = null;
        PhoneList = null;
        CurrentIndex = 0;
        list = null;
        DialFromPanel = null;
        OkButton = null;
        AreaCodeText = null;
        PrefixText = null;
        OldAreaCode = "";
        OldPrefix = "";
        SavedDialFrom = null;
        PhoneList = vector;
        initialize();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == getApplyButton())
        {
            ZCast.displayDebug("Apply");
            connBtoApply();
        }
        if(actionevent.getSource() == getSelectAllButton())
        {
            ZCast.displayDebug("Select all");
            connBtoSelectAll();
        }
        if(actionevent.getSource() == getJCButton3())
        {
            ZCast.displayDebug("cancel");
            connBtoCancel();
        }
        if(actionevent.getSource() == getOkButton())
        {
            ZCast.displayDebug("ok");
            doOk();
        }
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
    }

    private boolean allDigits(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                return false;

        return true;
    }

    private void applyToRec(PhoneRec phonerec)
    {
        int i = getCB_Neither().getState();
        int j = getCB_1AC().getState();
        int k = getCB_AC().getState();
        if(j == 1)
        {
            ZCast.displayDebug("set 1A");
            phonerec.setLDFlag(true);
            phonerec.setUseAreaCode(true);
        }
        if(k == 1)
        {
            ZCast.displayDebug("set A");
            phonerec.setLDFlag(false);
            phonerec.setUseAreaCode(true);
        }
        if(i == 1)
        {
            ZCast.displayDebug("set N");
            phonerec.setLDFlag(false);
            phonerec.setUseAreaCode(false);
        }
    }

    private void checkAreaCode(String s, TextField textfield, TextField textfield1)
    {
        System.out.println("OldAreaCode is " + OldAreaCode);
        if(s == null)
        {
            System.out.println("newstr is null, so set text to OldAreaCode");
            textfield.setText(OldAreaCode);
            return;
        }
        if(!allDigits(s))
        {
            int i = textfield.getCaretPosition();
            if(i > 0)
                i--;
            System.out.println("not all digits,so last caret position == " + i);
            textfield.setText(OldAreaCode);
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
            textfield.setText(OldAreaCode);
            textfield.setCaretPosition(j);
            return;
        }
        if(OldAreaCode.length() == 0)
        {
            System.out.println("set OldAreaCode to newstr of" + s);
            OldAreaCode = s;
        } else
        if(allDigits(s))
        {
            System.out.println("all digits so set OldAreaCode to newstr of" + s);
            OldAreaCode = s;
        } else
        {
            int k = textfield.getCaretPosition();
            if(k > 0)
                k--;
            textfield.setText(OldAreaCode);
            textfield.setCaretPosition(k);
        }
    }

    private void checkPrefix(String s, TextField textfield, TextField textfield1)
    {
        if(s == null)
        {
            textfield.setText(OldPrefix);
            return;
        }
        if(!allDigits(s))
        {
            int i = textfield.getCaretPosition();
            if(i > 0)
                i--;
            textfield.setText(OldPrefix);
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
            textfield.setText(OldPrefix);
            textfield.setCaretPosition(j);
            return;
        }
        if(OldPrefix.length() == 0)
            OldPrefix = s;
        else
        if(allDigits(s))
        {
            OldPrefix = s;
        } else
        {
            int k = textfield.getCaretPosition();
            if(k > 0)
                k--;
            textfield.setText(OldPrefix);
            textfield.setCaretPosition(k);
        }
    }

    private void connBtoApply()
    {
        try
        {
            for(int i = 0; i < list.getItemCount(); i++)
                if(list.isIndexSelected(i))
                {
                    ZCast.displayDebug("item " + i + " is selected");
                    PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
                    applyToRec(phonerec);
                    PhoneList.setElementAt(phonerec, i);
                }

            fillList();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connBtoCancel()
    {
        try
        {
            save = false;
            DialGroups.setDialFrom(SavedDialFrom);
            finalGoodbye();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connBtoSelectAll()
    {
        try
        {
            for(int i = 0; i < list.getItemCount(); i++)
                list.select(i);

            resetCheckboxes();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
        refreshCB();
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

    private void doOk()
    {
        try
        {
            for(int i = 0; i < list.getItemCount(); i++)
            {
                ZCast.displayDebug("do ok item " + i + " is selected");
                PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
                if(!DialGroups.addNumber(phonerec))
                {
                    String as[] = {
                        "Ok"
                    };
                    NZDialogBox.showMessageDialog(resNZResource.getString("Inactive_Number"), resNZResource.getString("The_number_") + phonerec.getPhoneNumber() + resNZResource.getString("_is_no_longer_valid_for_lo"), 3, as);
                }
            }

        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
        DialGroups.setDialFrom(getAreaCodeText().getText() + getPrefixText().getText());
        save = true;
        finalGoodbye();
    }

    private void fillDialFrom()
    {
        OldAreaCode = DialGroups.getAreaCode();
        getAreaCodeText().setText(OldAreaCode);
        OldPrefix = DialGroups.getEditDialFrom().substring(3, 6);
        getPrefixText().setText(OldPrefix);
    }

    private void fillList()
    {
        if((PhoneList != null) & (PhoneList.size() > 0))
        {
            int ai[] = list.getSelectedIndexes();
            list.removeAll();
            ZCast.displayDebug("add the phone numbers to list");
            for(int i = 0; i < PhoneList.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
                String s = phonerec.toListString();
                getList().addItem(s);
            }

            for(int j = 0; j < ai.length; j++)
                list.select(ai[j]);

        }
    }

    private void finalGoodbye()
    {
        dispose();
    }

    private NZButton getApplyButton()
    {
        if(ApplyButton == null)
            try
            {
                ApplyButton = new NZButton("apply");
                ApplyButton.setLocation(44, 8);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ApplyButton;
    }

    private TextField getAreaCodeText()
    {
        if(AreaCodeText == null)
            try
            {
                AreaCodeText = new TextField();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return AreaCodeText;
    }

    private JCCheckbox getCB_1AC()
    {
        if(cb_1ac == null)
            try
            {
                cb_1ac = new JCCheckbox();
                cb_1ac.setIndicator(2);
                cb_1ac.setName("JCCheckbox2");
                cb_1ac.setLabel(resNZResource.getString("Dial_1+_area_code_+_number"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cb_1ac;
    }

    private JCCheckbox getCB_AC()
    {
        if(cb_ac == null)
            try
            {
                cb_ac = new JCCheckbox();
                cb_ac.setIndicator(2);
                cb_ac.setName("JCCheckbox3");
                cb_ac.setLabel(resNZResource.getString("Dial_area_code_+_number"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cb_ac;
    }

    private JCCheckbox getCB_Neither()
    {
        if(cb_neither == null)
            try
            {
                cb_neither = new JCCheckbox();
                cb_neither.setIndicator(2);
                cb_neither.setName("JCCheckbox1");
                cb_neither.setLabel(resNZResource.getString("Neither"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cb_neither;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                setTopLabel();
                TopLabel.setBounds(3, 2, 475, 65);
                ivjContentsPane.add(TopLabel);
                ivjContentsPane.add(getJCCheckboxGroup1());
                ivjContentsPane.add(getDialFromPanel());
                ivjContentsPane.add(getJCGroupBox2(), getJCGroupBox2().getName());
                ivjContentsPane.add(getSelectAllButton());
                ivjContentsPane.add(getList());
                ivjContentsPane.setFont(new NZFont());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private Panel getDialFromPanel()
    {
        if(DialFromPanel == null)
            try
            {
                DialFromPanel = new Panel();
                DialFromPanel.setBounds(250, 183, 202, 50);
                DialFromPanel.setLayout(null);
                Label label = new Label("Phone number I am dialing from:");
                label.setBounds(0, 2, 300, 15);
                DialFromPanel.add(label);
                label = new Label("(");
                label.setFont(new Font("Arial", 0, 15));
                label.setBounds(1, 22, 5, 20);
                DialFromPanel.add(label);
                DialFromPanel.add(getAreaCodeText());
                getAreaCodeText().setBounds(8, 22, 40, 20);
                label = new Label(")");
                label.setFont(new Font("Arial", 0, 15));
                label.setBounds(50, 22, 5, 20);
                DialFromPanel.add(label);
                DialFromPanel.add(getPrefixText());
                getPrefixText().setBounds(57, 22, 40, 20);
                label = new Label("- xxxx");
                label.setBounds(102, 20, 40, 20);
                label.setFont(new Font("Arial", 0, 15));
                DialFromPanel.add(label);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return DialFromPanel;
    }

    private NZButton getJCButton3()
    {
        if(ivjJCButton3 == null)
            try
            {
                ivjJCButton3 = new NZButton("cancel");
                ivjJCButton3.setLocation(279, 8);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton3;
    }

    private JCGroupBox getJCCheckboxGroup1()
    {
        if(cb_box == null)
            try
            {
                cb_box = new JCGroupBox();
                cb_box.setLayout(new GridLayout(3, 1));
                cb_box.setName("JCCheckboxGroup1");
                cb_box.add(getCB_1AC());
                cb_box.add(getCB_AC());
                cb_box.add(getCB_Neither());
                cb_box.setBounds(250, 70, 205, 80);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return cb_box;
    }

    private JCGroupBox getJCGroupBox2()
    {
        if(ivjJCGroupBox2 == null)
            try
            {
                ivjJCGroupBox2 = new JCGroupBox();
                ivjJCGroupBox2.setName("JCGroupBox2");
                ivjJCGroupBox2.setLayout(new FlowLayout(2));
                ivjJCGroupBox2.setBounds(6, 255, 448, 38);
                getJCGroupBox2().add(getOkButton());
                getJCGroupBox2().add(getJCButton3(), getJCButton3().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCGroupBox2;
    }

    private java.awt.List getList()
    {
        if(list == null)
            try
            {
                list = new java.awt.List(10, true);
                list.setBounds(6, 70, 236, 155);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return list;
    }

    private NZButton getOkButton()
    {
        if(OkButton == null)
            try
            {
                OkButton = new NZButton("save&exit");
                OkButton.setLocation(34, 8);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return OkButton;
    }

    private TextField getPrefixText()
    {
        if(PrefixText == null)
            try
            {
                PrefixText = new TextField();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return PrefixText;
    }

    private NZButton getSelectAllButton()
    {
        if(SelectAllButton == null)
            try
            {
                SelectAllButton = new NZButton("select_all");
                SelectAllButton.setLocation(5, 230);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return SelectAllButton;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addWindowListener(this);
        getApplyButton().addActionListener(this);
        getSelectAllButton().addActionListener(this);
        getJCButton3().addActionListener(this);
        getCB_Neither().addItemListener(this);
        getCB_1AC().addItemListener(this);
        getCB_AC().addItemListener(this);
        getOkButton().addActionListener(this);
        getAreaCodeText().addTextListener(this);
        getPrefixText().addTextListener(this);
        getList().addItemListener(this);
    }

    private void initialize()
    {
        setName("PhoneNumberSetting");
        setLayout(new BorderLayout());
        setTitle("Phone Settings");
        setSize(463, 316);
        setModal(true);
        setResizable(false);
        SavedDialFrom = DialGroups.getEditDialFrom();
        add(getContentsPane(), "Center");
        initConnections();
        ZCast.centerComponent(this);
        boolean flag = false;
        ZCast.displayDebug("\n\n\n\n\nPassed in");
        for(int i = 0; i < PhoneList.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
            ZCast.displayDebug(phonerec.toListString());
            if(!phonerec.getAreaCode().equals(DialGroups.getAreaCode()))
            {
                ZCast.displayDebug("area code " + DialGroups.getAreaCode() + " doesn't match rec " + phonerec);
                if(!phonerec.getUseAreaCode())
                {
                    ZCast.displayDebug("so set it to dial 1 and area code");
                    phonerec.setLDFlag(true);
                    phonerec.setUseAreaCode(true);
                    PhoneList.setElementAt(phonerec, i);
                }
            }
        }

        ZCast.displayDebug("Done-------------\n\n\n\n");
        fillList();
        cb_1ac.setState(0);
        cb_ac.setState(0);
        cb_neither.setState(0);
        DialGroups.fillDialFromTextFields(getAreaCodeText(), getPrefixText());
        OldAreaCode = getAreaCodeText().getText();
        OldPrefix = getPrefixText().getText();
        initList();
        resetCheckboxes();
        cb_1ac.setState(0);
        cb_ac.setState(0);
        cb_neither.setState(0);
    }

    private void initList()
    {
        if(PhoneList == null || PhoneList.size() <= 0)
            return;
        int ai[] = list.getSelectedIndexes();
        boolean flag = false;
        String s = DialGroups.getAreaCode();
        for(int i = 0; i < PhoneList.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
            if(!phonerec.getAreaCode().equals(s) && !phonerec.getUseAreaCode())
            {
                phonerec.setLDFlag(true);
                phonerec.setUseAreaCode(true);
                flag = true;
            }
        }

        if(flag)
        {
            fillList();
            for(int j = 0; j < ai.length; j++)
                list.select(ai[j]);

        }
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        resetCheckboxes();
        refreshCB();
    }

    public void itemStateChanged(JCItemEvent jcitemevent)
    {
        ZCast.displayDebug("JCItemEvent change is" + jcitemevent);
        boolean flag = false;
        if(jcitemevent.getSource() == getCB_1AC())
        {
            int i = getCB_1AC().getState();
            getCB_Neither().setState(0);
            getCB_1AC().setState(1);
            getCB_AC().setState(0);
            refreshList(getCB_1AC());
        }
        if(jcitemevent.getSource() == getCB_AC())
        {
            int j = getCB_AC().getState();
            getCB_Neither().setState(0);
            getCB_1AC().setState(0);
            getCB_AC().setState(1);
            refreshList(getCB_AC());
        }
        if(jcitemevent.getSource() == getCB_Neither())
        {
            int k = getCB_Neither().getState();
            getCB_Neither().setState(1);
            getCB_1AC().setState(0);
            getCB_AC().setState(0);
            refreshList(getCB_Neither());
        }
    }

    public static void main(String args[])
    {
        Vector vector = new Vector();
        PhoneRec phonerec = new PhoneRec("T.O., CA", "805418-2000|1.1");
        phonerec.setLDFlag(true);
        phonerec.setUseAreaCode(true);
        vector.addElement(phonerec);
        phonerec = new PhoneRec("TestTown, CA", "757499-4012|2.2");
        phonerec.setUseAreaCode(true);
        vector.addElement(phonerec);
        phonerec = new PhoneRec("NeitherTown, CA", "757499-4000|2.2");
        vector.addElement(phonerec);
        DialGroups.readGroups();
        DialGroups.addGroup("Home, CA", "805418-2000");
        try
        {
            showDialog();
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Dialog");
            throwable.printStackTrace(System.out);
        }
    }

    private void refreshCB()
    {
        if(PhoneList == null || PhoneList.size() <= 0)
            return;
        boolean flag = false;
        int ai[] = getList().getSelectedIndexes();
        for(int i = 0; i < ai.length; i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(ai[i]);
            if(phonerec.getAreaCode().equals(DialGroups.getAreaCode()))
                continue;
            flag = true;
            break;
        }

        if(flag)
            getCB_Neither().disable();
        else
            getCB_Neither().enable();
    }

    private void refreshList(JCCheckbox jccheckbox)
    {
        if(jccheckbox == getCB_1AC())
            System.out.println("1 area code");
        if(jccheckbox == getCB_AC())
            System.out.println("area code");
        if(jccheckbox == getCB_Neither())
            System.out.println("neither");
        try
        {
            for(int i = 0; i < list.getItemCount(); i++)
                if(list.isIndexSelected(i))
                {
                    ZCast.displayDebug("item " + i + " is selected");
                    PhoneRec phonerec = (PhoneRec)PhoneList.elementAt(i);
                    applyToRec(phonerec);
                    PhoneList.setElementAt(phonerec, i);
                }

            fillList();
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void resetCheckboxes()
    {
        getCB_Neither().setState(0);
        getCB_1AC().setState(0);
        getCB_AC().setState(0);
    }

    private void setTopLabel()
    {
        String s = "You can change individual numbers in the list by highlighting them and clicking\n\"APPLY\".  You can change all numbers in the list by selecting \"SELECT ALL\" then\n\"APPLY\".  Select \"OK\" when finished to accept the changes.  NOTE: The \"Neither\"\ncheckbox is disabled when modifying numbers outside of your area code (" + DialGroups.getAreaCode() + ").\nIf this area code is incorrect, you can change it below.";
        s = "Change settings by selecting numbers in the list, then choosing a settings type.\nSelect \"SAVE & EXIT\" when done or \"CANCEL\" to cancel your changes.  NOTE:\nThe \"Neither\" checkbox is disabled when you select numbers outside of your area\ncode (" + DialGroups.getAreaCode() + ").  If this area code is incorrect, you can change it below.";
        if(TopLabel == null)
            TopLabel = new NZLabel(s);
        else
            TopLabel.setText(s);
    }

    public static boolean showDialog()
    {
        ZCast.displayDebug("show the settings window");
        Vector vector = DialGroups.getAllPhoneRecs();
        PhoneNumberSetting phonenumbersetting = new PhoneNumberSetting(new Frame(), vector);
        phonenumbersetting.setVisible(true);
        phonenumbersetting.setModal(true);
        phonenumbersetting.dispose();
        phonenumbersetting = null;
        return save;
    }

    public static boolean showDialog(Vector vector)
    {
        ZCast.displayDebug("show the settings window");
        PhoneNumberSetting phonenumbersetting = new PhoneNumberSetting(new Frame(), vector);
        phonenumbersetting.setVisible(true);
        phonenumbersetting.setModal(true);
        phonenumbersetting.dispose();
        phonenumbersetting = null;
        return save;
    }

    public void textValueChanged(TextEvent textevent)
    {
        ZCast.displayDebug("e is " + textevent);
        if(textevent.getSource() == getAreaCodeText())
        {
            String s = getAreaCodeText().getText();
            if(!s.equals(OldAreaCode))
                checkAreaCode(s, getAreaCodeText(), getPrefixText());
            if(getAreaCodeText().getText().length() == 3)
            {
                DialGroups.setDialFrom(getAreaCodeText().getText() + getPrefixText().getText());
                ZCast.displayDebug("refresh");
                setTopLabel();
                resetCheckboxes();
                initList();
            }
        }
        if(textevent.getSource() == getPrefixText())
        {
            String s1 = getPrefixText().getText();
            if(!s1.equals(OldPrefix))
                checkPrefix(s1, getPrefixText(), getAreaCodeText());
            DialGroups.setDialFrom(getAreaCodeText().getText() + getPrefixText().getText());
            ZCast.displayDebug("refresh");
            resetCheckboxes();
            initList();
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
        {
            save = false;
            connEtoC1(windowevent);
        }
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

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("gui.NZResource");
    private Panel ivjContentsPane;
    private NZLabel TopLabel;
    private NZButton ApplyButton;
    private NZButton SelectAllButton;
    private NZButton ivjJCButton3;
    private JCCheckbox cb_1ac;
    private JCCheckbox cb_ac;
    private JCCheckbox cb_neither;
    private JCCheckboxGroup ivjJCCheckboxGroup1;
    private JCGroupBox cb_box;
    private JCGroupBox ivjJCGroupBox2;
    private Vector PhoneList;
    private int CurrentIndex;
    private java.awt.List list;
    private Panel DialFromPanel;
    private NZButton OkButton;
    private TextField AreaCodeText;
    private TextField PrefixText;
    private static boolean save = false;
    private String OldAreaCode;
    private String OldPrefix;
    private String SavedDialFrom;

}
