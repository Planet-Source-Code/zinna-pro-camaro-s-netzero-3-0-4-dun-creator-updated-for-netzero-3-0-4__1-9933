// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialGroups.java

package gui;

import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.TextComponent;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import nzcom.ZCast;

// Referenced classes of package gui:
//            PhoneRec, DialupGroup

public class DialGroups
    implements Serializable
{

    public DialGroups()
    {
    }

    public static void addGroup(String s, String s1)
    {
        ZCast.displayDebug("adding group named " + s);
        ZCast.displayDebug("with dialfrom of " + s1);
        if(groups == null)
        {
            ZCast.displayDebug("Adding the first group");
            groups = new Vector();
            DialupGroup dialupgroup = new DialupGroup(s, s1, null);
            groups.addElement(dialupgroup);
            CurrentGroup = getGroup(s);
            ZCast.displayDebug("number of groups == " + groups.size());
            ZCast.displayDebug("CurrentGroup is " + CurrentGroup);
            return;
        }
        if(!groupExists(s))
        {
            DialupGroup dialupgroup1 = new DialupGroup(s, s1, null);
            groups.addElement(dialupgroup1);
            CurrentGroup = getGroup(s);
            ZCast.displayDebug("Current group is " + CurrentGroup.getGroupName());
        } else
        {
            ZCast.displayDebug("Group " + CurrentGroup.getGroupName() + " already exists");
        }
    }

    public static boolean addingDup(String s)
    {
        if(EditGroup == null)
            return false;
        if(CurrentGroup == null)
            return false;
        return CurrentGroup == EditGroup && groupExists(s);
    }

    public static boolean addNumber(PhoneRec phonerec)
    {
        return CurrentGroup.addNumber(phonerec);
    }

    public static Vector buildCitySelectList(String as[])
    {
        ZCast.displayDebug("cities are:");
        ZCast.displayDebug(as);
        return CurrentGroup.buildCitySelectList(as);
    }

    public static Vector buildModifySettingsList(java.awt.List list)
    {
        return CurrentGroup.buildModifySettingsList(list);
    }

    public static void buildTestGroup()
    {
        groups = new Vector();
        addGroup("Home", "8054182000");
        CurrentGroup.setPrefixes("9", null, null, true);
        String s = "8051234567|Simi|CA|2.2|type|5|GTE|A|I|txt|3^8059172256|City2|CA|2.3|type|5|GTE|A|I|txt|3^8055470918|City4|CA|10.3|type|5|GTE|I|I|txt|3^8056010701|City1|CA|1.5|type|5|GTE|A|E|txt|3^8051234568|Tijuana|CA|3.6|type|5|GTE|A|I|txt|3";
        setServerNums(s);
    }

    public static void deleteCurrent()
    {
        if(groups.size() == 1)
        {
            groups = null;
            CurrentGroup = null;
            return;
        }
        int i = groups.indexOf(CurrentGroup);
        if(i > 0)
            i--;
        else
            i++;
        DialupGroup dialupgroup = CurrentGroup;
        CurrentGroup = (DialupGroup)groups.elementAt(i);
        groups.removeElement(dialupgroup);
    }

    public static void deleteGroup(String s)
    {
        if(groups == null || groups.size() <= 0)
            return;
        DialupGroup dialupgroup = null;
        dialupgroup = getGroup(s);
        if(dialupgroup != null)
            if(dialupgroup == CurrentGroup)
                deleteCurrent();
            else
                groups.removeElement(dialupgroup);
    }

    public static void deleteSelectList(java.awt.List list)
    {
        CurrentGroup.deleteSelectList(list);
    }

    public static void fillDialFromTextFields(TextField textfield, TextField textfield1)
    {
        String s = null;
        if(CurrentGroup != null)
            s = CurrentGroup.getDialFrom();
        else
        if(EditGroup != null)
            s = EditGroup.getDialFrom();
        ZCast.displayDebug("number is " + s);
        if(s == null || s.length() == 0)
        {
            textfield.setText("");
            textfield1.setText("");
            return;
        } else
        {
            String s1 = s.substring(0, 3);
            String s2 = s.substring(3, 6);
            textfield.setText(s1);
            textfield1.setText(s2);
            return;
        }
    }

    public static void fillGroupChoice(Choice choice)
    {
        String as[] = null;
        as = getGroupNames();
        if(as == null)
        {
            ZCast.displayDebug("it's null");
            return;
        }
        ZCast.displayDebug("num names = " + as.length);
        for(int i = 0; i < as.length; i++)
            choice.addItem(as[i]);

        if(CurrentGroup != null)
            choice.select(CurrentGroup.getGroupName());
    }

    public static void fillGroupList(java.awt.List list)
    {
        String as[] = null;
        as = getGroupNames();
        if(as == null)
        {
            ZCast.displayDebug("it's null");
            return;
        }
        ZCast.displayDebug("num names = " + as.length);
        for(int i = 0; i < as.length; i++)
            list.addItem(as[i]);

    }

    public static void fillPhoneList(java.awt.List list)
    {
        CurrentGroup.fillList(list);
    }

    public static void fillSortedPhoneList(java.awt.List list)
    {
        CurrentGroup.fillSortedList(list);
    }

    public static Vector getAllPhoneRecs()
    {
        return CurrentGroup.getAllPhoneRecs();
    }

    public static String getAreaCode()
    {
        return getEditDialFrom().substring(0, 3);
    }

    public static String getConnectDialFrom()
    {
        if(!ZCast.m_demoMode && ZCast.m_connectionType == 1)
            return "999999";
        else
            return CurrentGroup.getDialFrom();
    }

    public static String getCurrentGroupName()
    {
        if(CurrentGroup == null)
            return "";
        else
            return CurrentGroup.getGroupName();
    }

    public static boolean getDUN_noshow()
    {
        return DUN_noshow;
    }

    public static String getEditDialFrom()
    {
        if(CurrentGroup == null)
            return null;
        else
            return CurrentGroup.getDialFrom();
    }

    private static DialupGroup getGroup(String s)
    {
        if(groups == null)
            return null;
        DialupGroup dialupgroup = null;
        for(int i = 0; i < groups.size(); i++)
        {
            dialupgroup = (DialupGroup)groups.elementAt(i);
            if(s.equals(dialupgroup.getGroupName()))
                break;
        }

        return dialupgroup;
    }

    public static String[] getGroupNames()
    {
        if(groups == null || groups.size() <= 0)
            return null;
        String as[] = new String[groups.size()];
        for(int i = 0; i < groups.size(); i++)
        {
            DialupGroup dialupgroup = (DialupGroup)groups.elementAt(i);
            as[i] = dialupgroup.getGroupName();
        }

        return as;
    }

    public static boolean getMuteModem()
    {
        if(CurrentGroup == null)
            return true;
        else
            return CurrentGroup.getMuteModem();
    }

    public static boolean getNetworkChecked()
    {
        return NetworkChecked;
    }

    public static Vector getNumbersToDial()
    {
        return CurrentGroup.getNumbersToDial();
    }

    public static Vector getProblemNumbers()
    {
        return CurrentGroup.getProblemNumbers();
    }

    private static boolean groupExists(String s)
    {
        if(groups == null || groups.size() <= 0)
            return false;
        boolean flag = false;
        for(int i = 0; i < groups.size(); i++)
        {
            DialupGroup dialupgroup = (DialupGroup)groups.elementAt(i);
            if(!s.equalsIgnoreCase(dialupgroup.getGroupName()))
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public static void makeEditGroup()
    {
        EditGroup = new DialupGroup("", "", null);
        CurrentGroup = EditGroup;
    }

    private static void mergeEditGroup()
    {
        if(EditGroup == null)
            return;
        if(groups == null)
        {
            ZCast.displayDebug("Adding the first group");
            groups = new Vector();
        }
        groups.addElement(EditGroup);
        CurrentGroup = getGroup(EditGroup.getGroupName());
        EditGroup = null;
        ZCast.displayDebug("number of groups == " + groups.size());
        ZCast.displayDebug("CurrentGroup is " + CurrentGroup);
    }

    public static int numGroups()
    {
        if(CurrentGroup == null)
            return -1;
        if(groups == null)
            return 0;
        else
            return groups.size();
    }

    public static int numNumbers()
    {
        if(CurrentGroup == null)
            return 0;
        else
            return CurrentGroup.numNumbers();
    }

    public static void prepareToDial()
    {
        if(CurrentGroup != null)
            CurrentGroup.prepareToDial();
        else
            ZCast.displayDebug("Current group is null");
    }

    public static void printCurrent()
    {
        CurrentGroup.printGroup();
    }

    public static void readGroups()
    {
        File file = new File("AutoDialer.dat");
        ZCast.displayDebug("reading Groups File");
        if(file.exists())
        {
            ZCast.displayDebug("file exists, read it");
            try
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream("AutoDialer.dat"));
                ZCast.displayDebug("--------Read groups--------");
                groups = (Vector)objectinputstream.readObject();
                ZCast.displayDebug("----Done Reading groups----");
                ZCast.displayDebug("read current group name");
                if(groups.size() > 0)
                {
                    CurrentGroupName = (String)objectinputstream.readObject();
                    ZCast.displayDebug("and it is " + CurrentGroupName);
                    if(CurrentGroupName != null && CurrentGroupName.length() > 0)
                        setCurrentGroup(CurrentGroupName);
                    else
                        CurrentGroup = null;
                }
                try
                {
                    NetworkChecked = objectinputstream.readBoolean();
                }
                catch(IOException _ex)
                {
                    NetworkChecked = false;
                }
                try
                {
                    DUN_noshow = objectinputstream.readBoolean();
                }
                catch(IOException _ex)
                {
                    DUN_noshow = false;
                }
                objectinputstream.close();
            }
            catch(IOException ioexception)
            {
                ZCast.displayDebug("Exception reading Groups file. " + ioexception);
            }
            catch(IncompatibleClassChangeError incompatibleclasschangeerror)
            {
                ZCast.displayDebug("class change " + incompatibleclasschangeerror.toString());
                ZCast.displayDebug("Stack trace:");
                ZCast.displayDebug(incompatibleclasschangeerror);
            }
            catch(LinkageError linkageerror)
            {
                ZCast.displayDebug("linkage: " + linkageerror);
            }
            catch(ClassNotFoundException classnotfoundexception)
            {
                ZCast.displayDebug("Class exception. " + classnotfoundexception);
            }
            if(groups.size() == 0)
            {
                groups = null;
                CurrentGroup = null;
            }
        } else
        {
            ZCast.displayDebug("no groups file, init");
            groups = null;
            CurrentGroup = null;
        }
        if(CurrentGroup != null)
            printCurrent();
        else
            ZCast.displayDebug("current group is null");
    }

    public static void resetEditGroup()
    {
        if(EditGroup == null)
            return;
        if(groups == null || groups.size() <= 0)
        {
            return;
        } else
        {
            CurrentGroup = (DialupGroup)groups.elementAt(0);
            EditGroup = null;
            return;
        }
    }

    public static void selectChoiceAreaCode(Choice choice)
    {
        choice.select(0);
        choice.select(CurrentGroup.getDialFrom().substring(0, 3));
    }

    public static void selectCurrentGroupInList(java.awt.List list)
    {
        Object obj = null;
        boolean flag = false;
        String s = list.getItem(0);
        ZCast.displayDebug("FirstItem is " + s);
        if(!groupExists(s))
            flag = true;
        for(int i = flag; i < list.getItemCount(); i++)
            if(getCurrentGroupName().equals(list.getItem(i)))
            {
                list.select(i);
                return;
            }

    }

    public static void setAllPhoneRecs(Vector vector)
    {
        CurrentGroup.setAllPhoneRecs(vector);
    }

    public static void setCurrentGroup(String s)
    {
        if(groups == null || groups.size() <= 0)
            return;
        DialupGroup dialupgroup = null;
        dialupgroup = getGroup(s);
        if(dialupgroup != null)
        {
            CurrentGroup = dialupgroup;
            CurrentGroupName = s;
        }
    }

    public static void setCurrentName(String s)
    {
        ZCast.displayDebug("setting current name to " + s);
        if(CurrentGroup == null)
            return;
        if(s.length() == 0)
        {
            return;
        } else
        {
            CurrentGroup.setGroupName(s);
            return;
        }
    }

    public static void setDialFrom(String s)
    {
        ZCast.displayDebug("setting dial from to " + s);
        if(CurrentGroup == null)
        {
            return;
        } else
        {
            CurrentGroup.setDialFrom(s);
            return;
        }
    }

    public static void setDUN_noshow(boolean flag)
    {
        DUN_noshow = flag;
    }

    public static void setFinalPhoneList(java.awt.List list)
    {
        CurrentGroup.setFinalPhoneList(list);
    }

    public static void setIgnoreLDWarning(Vector vector)
    {
        CurrentGroup.setIgnoreLDWarning(vector);
    }

    public static void setLDAreaCodes(Vector vector)
    {
        vector = (Vector)CurrentGroup.getAllPhoneRecs().clone();
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            PhoneRec phonerec = (PhoneRec)enumeration.nextElement();
            if(!phonerec.getAreaCode().equals(getAreaCode()))
            {
                phonerec.setLDFlag(true);
                phonerec.setUseAreaCode(true);
            }
        }

    }

    public static void setMuteModem(boolean flag)
    {
        if(CurrentGroup != null)
            CurrentGroup.setMuteModem(flag);
    }

    public static void setNetworkChecked(boolean flag)
    {
        NetworkChecked = flag;
    }

    public static void setPrefixes(String s, String s1, String s2, boolean flag)
    {
        CurrentGroup.setPrefixes(s, s1, s2, flag);
    }

    public static void setPrefixGUI(Checkbox checkbox, TextField textfield, Checkbox checkbox1, TextField textfield1, Checkbox checkbox2, TextField textfield2, Checkbox checkbox3)
    {
        ZCast.displayDebug("Setting prefixes for CurrentGroup " + CurrentGroup.getGroupName());
        CurrentGroup.setPrefixGUI(checkbox, textfield, checkbox1, textfield1, checkbox2, textfield2, checkbox3);
    }

    public static void setServerNums(String s)
    {
        CurrentGroup.setServerNums(s);
    }

    public static void verifyAllPhoneList()
    {
        ZCast.displayDebug("@@@@@@@@@@@@@@@@@@@@@ VERIFY ALL NUMBERS AGAINST PHN.DAT @@@@@@@@@@@@@@@@@@@@@@@@");
        if(groups == null)
            return;
        for(int i = 0; i < groups.size(); i++)
        {
            DialupGroup dialupgroup = (DialupGroup)groups.elementAt(i);
            if(dialupgroup != null)
                dialupgroup.verifyPhoneNumbers();
        }

    }

    public static void writeGroups()
    {
        if(EditGroup != null && EditGroup.getGroupName().trim().length() > 0 && EditGroup.getDialFrom().length() > 0 && EditGroup.numNumbers() > 0)
            mergeEditGroup();
        if(groups == null || CurrentGroup == null)
            groups = new Vector();
        try
        {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream("AutoDialer.dat"));
            objectoutputstream.writeObject(groups);
            if(groups.size() > 0 && CurrentGroup != null)
            {
                CurrentGroupName = CurrentGroup.getGroupName();
                objectoutputstream.writeObject(CurrentGroupName);
            } else
            {
                CurrentGroupName = "none";
            }
            objectoutputstream.writeBoolean(NetworkChecked);
            objectoutputstream.writeBoolean(DUN_noshow);
            objectoutputstream.close();
        }
        catch(IOException ioexception)
        {
            ZCast.displayDebug("\n\n\texception writing Groups. " + ioexception);
        }
    }

    private static final String DIALGROUP_FILENAME = "AutoDialer.dat";
    public static final int MIN_GROUP_NAME_LEN = 3;
    public static final int MAX_GROUP_NAME_LEN = 30;
    private static Vector groups = new Vector();
    private static DialupGroup CurrentGroup = null;
    private static String CurrentGroupName = null;
    private static DialupGroup EditGroup = null;
    private static boolean NetworkChecked = false;
    private static boolean DUN_noshow = false;

}
