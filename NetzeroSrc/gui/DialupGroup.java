// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DialupGroup.java

package gui;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Frame;
import java.awt.TextComponent;
import java.awt.TextField;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import nzcom.ConfigParams;
import nzcom.ZCast;

// Referenced classes of package gui:
//            PhoneNumberSetting, NewPhoneNumbers, DialObject, DialGroups, 
//            PhoneRec

public class DialupGroup
    implements Serializable
{

    public DialupGroup(String s, String s1, Vector vector)
    {
        GroupName = null;
        DialFrom = null;
        PhoneNumbers = new Vector();
        ServerNumbers = new Vector();
        RepromptCount = 0;
        OutPrefix = null;
        CallWaitingPrefix = null;
        CustomPrefix = null;
        MuteModem = 1;
        GroupName = s;
        DialFrom = s1;
        PhoneNumbers = vector;
        OutPrefix = null;
        CallWaitingPrefix = null;
    }

    private void addByRank(PhoneRec phonerec)
    {
        int i = getInsertIndex(phonerec.getRank());
        if(i >= 0)
            PhoneNumbers.insertElementAt(phonerec, i);
        else
            PhoneNumbers.addElement(phonerec);
    }

    public boolean addNumber(PhoneRec phonerec)
    {
        if(PhoneNumbers == null)
            PhoneNumbers = new Vector();
        int i = getServerIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
        if(i >= 0 && i < ServerNumbers.size())
        {
            PhoneRec phonerec1 = (PhoneRec)ServerNumbers.elementAt(i);
            int k = getPhoneIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
            if(k >= 0 && k < PhoneNumbers.size())
            {
                deleteNumber(k);
                if(phonerec1.active())
                {
                    phonerec1.setLDFlag(phonerec.getLDFlag());
                    phonerec1.setUseAreaCode(phonerec.getUseAreaCode());
                    addByRank(phonerec1);
                } else
                {
                    return false;
                }
            } else
            if(phonerec1.active())
            {
                phonerec.setRank(phonerec1.getRank());
                phonerec.setTempInactive(phonerec1.TempInactive());
                addByRank(phonerec);
            }
        } else
        {
            int j = getPhoneIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
            if(j >= 0 && j < PhoneNumbers.size())
            {
                deleteNumber(j);
                PhoneNumbers.insertElementAt(phonerec, j);
            } else
            {
                addByRank(phonerec);
            }
        }
        return true;
    }

    private void addToServerList(PhoneRec phonerec)
    {
        int i = getServerIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
        if(i >= 0)
        {
            PhoneRec phonerec1 = (PhoneRec)ServerNumbers.elementAt(i);
            phonerec.setRejected(phonerec1.getRejected());
            deleteServerNumber(i);
            if(i < ServerNumbers.size())
                ServerNumbers.insertElementAt(phonerec, i);
            else
                ServerNumbers.addElement(phonerec);
        } else
        {
            ServerNumbers.addElement(phonerec);
        }
    }

    public Vector buildCitySelectList(String as[])
    {
        boolean flag = false;
        Vector vector = new Vector();
        for(int i = 0; i < as.length; i++)
        {
            String as1[] = ConfigParams.getNumAndRankByCity(as[i]);
            for(int j = 0; j < as1.length; j++)
            {
                PhoneRec phonerec = new PhoneRec(as[i], as1[j]);
                String s = DialGroups.getAreaCode();
                if(!s.equals(phonerec.getAreaCode()))
                {
                    phonerec.setUseAreaCode(true);
                    phonerec.setLDFlag(true);
                }
                vector.addElement(phonerec);
            }

        }

        return vector;
    }

    public Vector buildDeleteList(java.awt.List list)
    {
        Vector vector = new Vector();
        int ai[] = list.getSelectedIndexes();
        for(int i = 0; i < ai.length; i++)
        {
            String s = list.getItem(ai[i]);
            PhoneRec phonerec = (PhoneRec)getPhoneRecByListString(s).clone();
            vector.addElement(phonerec);
        }

        return vector;
    }

    private String buildFullString(PhoneRec phonerec)
    {
        String s = new String();
        if(OutPrefix != null)
            s = s.concat(OutPrefix + ",");
        if(CallWaitingPrefix != null)
            s = s.concat(CallWaitingPrefix + ",");
        if(CustomPrefix != null)
            s = s.concat(CustomPrefix);
        s = s.concat(phonerec.getDialString());
        return s;
    }

    public Vector buildModifySettingsList(java.awt.List list)
    {
        Vector vector = new Vector();
        for(int i = 0; i < list.getItemCount(); i++)
        {
            String s = list.getItem(i);
            PhoneRec phonerec = (PhoneRec)getPhoneRecByListString(s).clone();
            vector.addElement(phonerec);
        }

        return vector;
    }

    public static String checkExistanceInPHN(String s)
    {
        int i = -1;
        String s1 = s;
        String as[] = ConfigParams.getAllNumbers();
        boolean flag = false;
        for(i = 0; i < as.length; i++)
        {
            if(!s1.equals(as[i]))
                continue;
            flag = true;
            break;
        }

        if(flag)
            return ConfigParams.getNumberRank(i);
        else
            return "-9999.9999";
    }

    private void deleteNumber(int i)
    {
        PhoneNumbers.removeElementAt(i);
    }

    public void deleteNumber(String s, String s1)
    {
        int i = getPhoneIndex(s, s1);
        if(i >= 0)
            deleteNumber(i);
    }

    public void deleteSelectList(java.awt.List list)
    {
        Vector vector = buildDeleteList(list);
        for(int i = 0; i < vector.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)vector.elementAt(i);
            deleteNumber(phonerec.getAreaCode(), phonerec.getPhoneNumber());
            list.remove(phonerec.toListString());
        }

    }

    private void deleteServerNumber(int i)
    {
        ServerNumbers.removeElementAt(i);
    }

    public void deleteServerNumber(String s, String s1)
    {
        int i = getServerIndex(s, s1);
        if(i >= 0)
            deleteServerNumber(i);
    }

    public void fillList(java.awt.List list)
    {
        list.removeAll();
        if(PhoneNumbers == null)
            return;
        if(PhoneNumbers.size() == 0)
            return;
        String as[] = getPhoneNumbers();
        for(int i = 0; i < as.length; i++)
            list.addItem(as[i]);

    }

    public void fillSortedList(java.awt.List list)
    {
        list.removeAll();
        if(PhoneNumbers == null)
            return;
        if(PhoneNumbers.size() == 0)
            return;
        String as[] = getSortedPhoneNumbers();
        for(int i = 0; i < as.length; i++)
            list.addItem(as[i]);

    }

    public Vector getAllPhoneRecs()
    {
        if(PhoneNumbers == null)
            return null;
        if(PhoneNumbers.size() == 0)
            return null;
        String as[] = getSortedPhoneNumbers();
        Vector vector = new Vector();
        for(int i = 0; i < as.length; i++)
        {
            PhoneRec phonerec = (PhoneRec)getPhoneRecByListString(as[i]).clone();
            vector.addElement(phonerec);
        }

        return vector;
    }

    public String getCallWaitingPrefix()
    {
        return CallWaitingPrefix;
    }

    public String getCustomPrefix()
    {
        return CustomPrefix;
    }

    public String getDialFrom()
    {
        return DialFrom;
    }

    public String getGroupName()
    {
        return GroupName;
    }

    private int getInsertIndex(String s)
    {
        int i = -1;
        if(PhoneNumbers.size() == 0)
            return 0;
        for(int j = 0; j < PhoneNumbers.size(); j++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(j);
            float f = (new Float(s)).floatValue();
            float f1 = (new Float(phonerec.getRank())).floatValue();
            if(f <= f1)
                continue;
            i = j;
            break;
        }

        return i;
    }

    public boolean getMuteModem()
    {
        if(MuteModem == 0)
            return true;
        return MuteModem == 1;
    }

    public PhoneRec getNumber(String s, String s1, String s2)
    {
        int i = getPhoneIndex(s1, s2);
        if(i == -1)
        {
            return null;
        } else
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            return phonerec;
        }
    }

    public Vector getNumbersToDial()
    {
        Vector vector = new Vector();
        Object obj = null;
        for(int i = 0; i < PhoneNumbers.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            if(!phonerec.TempInactive())
            {
                String s = buildFullString(phonerec);
                DialObject dialobject = new DialObject(phonerec.getAreaCode() + "-" + phonerec.getFirst3() + "-" + phonerec.getLast4() + " " + phonerec.getStateString() + " " + phonerec.getCityString() + " ", s, phonerec.getRetryCount(), phonerec.getRank());
                vector.addElement(dialobject);
            }
        }

        return vector;
    }

    public String getOutPrefix()
    {
        return OutPrefix;
    }

    private int getPhoneIndex(String s, String s1)
    {
        int i = -1;
        for(int j = 0; j < PhoneNumbers.size(); j++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(j);
            if(!s.equals(phonerec.getAreaCode()) || !s1.equals(phonerec.getPhoneNumber()))
                continue;
            i = j;
            break;
        }

        return i;
    }

    private String[] getPhoneNumbers()
    {
        if(PhoneNumbers == null || PhoneNumbers.size() <= 0)
            return null;
        String as[] = new String[PhoneNumbers.size()];
        for(int i = 0; i < PhoneNumbers.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            as[i] = phonerec.toListString();
        }

        return as;
    }

    private PhoneRec getPhoneRecByListString(String s)
    {
        for(int i = 0; i < PhoneNumbers.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            if(phonerec == null)
                return null;
            if(s.equals(phonerec.toListString()))
                return phonerec;
        }

        return null;
    }

    public Vector getProblemNumbers()
    {
        Vector vector = new Vector();
        String s = DialFrom.substring(0, 3);
        for(int i = 0; i < numNumbers(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            if(phonerec.numberIsBad(s))
                vector.addElement(phonerec);
        }

        return vector;
    }

    private int getRepromptCount()
    {
        return RepromptCount;
    }

    private int getServerIndex(String s, String s1)
    {
        int i = -1;
        for(int j = 0; j < ServerNumbers.size(); j++)
        {
            PhoneRec phonerec = (PhoneRec)ServerNumbers.elementAt(j);
            if(!s.equals(phonerec.getAreaCode()) || !s1.equals(phonerec.getPhoneNumber()))
                continue;
            i = j;
            break;
        }

        return i;
    }

    private String[] getSortedPhoneNumbers()
    {
        if(PhoneNumbers == null || PhoneNumbers.size() <= 0)
            return null;
        String as[] = new String[PhoneNumbers.size()];
        Vector vector = new Vector();
        for(int i = 0; i < PhoneNumbers.size(); i++)
        {
            PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
            insertByCity(vector, phonerec.toListString());
        }

        for(int j = 0; j < vector.size(); j++)
        {
            String s = (String)vector.elementAt(j);
            as[j] = s;
        }

        return as;
    }

    private void insertByCity(Vector vector, String s)
    {
        String s1 = parseCity(s);
        int i = -1;
        for(int j = 0; j < vector.size(); j++)
        {
            String s2 = (String)vector.elementAt(j);
            if(s1.compareTo(parseCity(s2)) > 0)
                continue;
            i = j;
            break;
        }

        if(i >= 0)
        {
            if(vector.size() == 0)
                vector.addElement(s);
            else
                vector.insertElementAt(s, i);
        } else
        {
            vector.addElement(s);
        }
    }

    public void mergeServerNumbers()
    {
        Vector vector = new Vector();
        for(int i = 0; i < ServerNumbers.size(); i++)
        {
            PhoneRec phonerec = null;
            phonerec = (PhoneRec)ServerNumbers.elementAt(i);
            int k = getPhoneIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
            if(k >= 0 && k < PhoneNumbers.size())
            {
                PhoneRec phonerec2 = (PhoneRec)PhoneNumbers.elementAt(k);
                deleteNumber(k);
                if(phonerec.active())
                {
                    vector.addElement(phonerec);
                    PhoneRec phonerec3 = null;
                    phonerec3 = (PhoneRec)ServerNumbers.elementAt(i);
                    if(phonerec3 != null)
                    {
                        phonerec3.setRejected(false);
                        phonerec3.setLDFlag(phonerec2.getLDFlag());
                        phonerec3.setUseAreaCode(phonerec2.getUseAreaCode());
                        addByRank(phonerec3);
                    }
                }
            }
        }

        if(vector != null)
        {
            for(int j = 0; j < vector.size(); j++)
            {
                PhoneRec phonerec1 = (PhoneRec)vector.elementAt(j);
                if(phonerec1 != null)
                    deleteServerNumber(phonerec1.getAreaCode(), phonerec1.getPhoneNumber());
            }

        }
    }

    public void modifyNumber(PhoneRec phonerec)
    {
        int i = getPhoneIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
        PhoneNumbers.setElementAt(phonerec, i);
    }

    private boolean numberAlreadyInList(PhoneRec phonerec)
    {
        boolean flag = false;
        int i = getPhoneIndex(phonerec.getAreaCode(), phonerec.getPhoneNumber());
        if(i >= 0 && i < PhoneNumbers.size())
            flag = true;
        return flag;
    }

    public int numNumbers()
    {
        if(PhoneNumbers == null)
            return 0;
        else
            return PhoneNumbers.size();
    }

    private String parseCity(String s)
    {
        int i = s.indexOf(",");
        String s1 = s.substring(i + 2);
        return s1;
    }

    public void prepareToDial()
    {
        if(ServerNumbers != null)
        {
            mergeServerNumbers();
            Vector vector = new Vector(1);
            for(int i = 0; i < ServerNumbers.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)ServerNumbers.elementAt(i);
                if(phonerec != null && phonerec.active() && !phonerec.TempInactive() && !phonerec.getRejected())
                    vector.addElement(phonerec);
            }

            if(vector != null && vector.size() > 0)
            {
                NewPhoneNumbers newphonenumbers = new NewPhoneNumbers(new Frame(), true, vector);
                ZCast.centerComponent(newphonenumbers);
                newphonenumbers.setVisible(true);
                Vector vector1 = NewPhoneNumbers.getVectorToShow();
                if(vector1 != null && vector1.size() > 0)
                {
                    Vector vector2 = new Vector();
                    for(int j = 0; j < vector1.size(); j++)
                        vector2.addElement(vector1.elementAt(j));

                    if(vector2 != null)
                        PhoneNumberSetting.showDialog(vector2);
                    for(int k = 0; k < vector2.size(); k++)
                    {
                        PhoneRec phonerec1 = (PhoneRec)vector2.elementAt(k);
                        if(phonerec1 != null)
                            deleteServerNumber(phonerec1.getAreaCode(), phonerec1.getPhoneNumber());
                    }

                }
                rejectAllServerNumbers();
            }
        }
        DialGroups.writeGroups();
        ZCast.displayDebug("************ Phone numbers to dial and Server Numbers which is saved");
        printGroup();
        ZCast.displayDebug("************ end ***************");
    }

    public void printGroup()
    {
        ZCast.displayDebug("--------------------------------");
        ZCast.displayDebug("GroupName is " + GroupName);
        ZCast.displayDebug("Dial From is " + DialFrom);
        Object obj = null;
        if(PhoneNumbers == null)
        {
            ZCast.displayDebug("PhoneNumbers is null");
        } else
        {
            for(int i = 0; i < PhoneNumbers.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
                ZCast.displayDebug("  ~~~~~~~~~~~~~~~~ PhoneNumber =  " + phonerec.toListString() + "  area code= " + phonerec.getAreaCode());
                ZCast.displayDebug("  rank = " + phonerec.getRank() + "  active =  " + phonerec.active());
                ZCast.displayDebug("  TempInactive  =  " + phonerec.TempInactive() + "  Rejected  = " + phonerec.getRejected());
                ZCast.displayDebug("  Redial Count  =  " + phonerec.getRetryCount());
            }

        }
        ZCast.displayDebug(" ********************************************************");
        if(ServerNumbers == null)
        {
            ZCast.displayDebug("ServerNumbers is null");
        } else
        {
            for(int j = 0; j < ServerNumbers.size(); j++)
            {
                PhoneRec phonerec1 = (PhoneRec)ServerNumbers.elementAt(j);
                ZCast.displayDebug("  ~~~~~~~~~~~~~~~~ Server Number =  " + phonerec1.toListString() + "  area code= " + phonerec1.getAreaCode());
                ZCast.displayDebug("  rank = " + phonerec1.getRank() + "  active =  " + phonerec1.active());
                ZCast.displayDebug("  TempInactive  =  " + phonerec1.TempInactive() + "  Rejected  = " + phonerec1.getRejected());
                ZCast.displayDebug("  Redial Count  =  " + phonerec1.getRetryCount());
            }

        }
        ZCast.displayDebug("RepromptCount is " + RepromptCount);
        ZCast.displayDebug("OutPrefix is " + OutPrefix);
        ZCast.displayDebug("CallWaitingPrefix is " + CallWaitingPrefix);
        ZCast.displayDebug("CustomPrefix is " + CustomPrefix);
        ZCast.displayDebug("--------------------------------");
    }

    private void rejectAllServerNumbers()
    {
        if(ServerNumbers != null)
        {
            for(int i = 0; i < ServerNumbers.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)ServerNumbers.elementAt(i);
                if(!phonerec.TempInactive())
                    phonerec.setRejected(true);
            }

        }
    }

    public void setAllPhoneRecs(Vector vector)
    {
        PhoneNumbers = vector;
    }

    public void setCallWaitingPrefix(String s)
    {
        CallWaitingPrefix = s;
    }

    public void setCustomPrefix(String s)
    {
        CustomPrefix = s;
    }

    public void setDialFrom(String s)
    {
        DialFrom = s;
    }

    public void setFinalPhoneList(java.awt.List list)
    {
        list.removeAll();
        String as[] = getSortedPhoneNumbers();
        for(int i = 0; i < as.length; i++)
        {
            String s = new String();
            if(OutPrefix != null)
                s = s.concat(OutPrefix + ",");
            if(CallWaitingPrefix != null)
                s = s.concat(CallWaitingPrefix + ",");
            if(CustomPrefix != null)
                s = s.concat(CustomPrefix);
            s = s.concat(as[i]);
            list.addItem(s);
        }

    }

    public void setGroupName(String s)
    {
        GroupName = s;
    }

    public void setIgnoreLDWarning(Vector vector)
    {
        PhoneRec phonerec1;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); phonerec1.setIgnoreLDWarning(true))
        {
            PhoneRec phonerec = (PhoneRec)enumeration.nextElement();
            phonerec1 = getNumber(phonerec.getCityString(), phonerec.getAreaCode(), phonerec.getPhoneNumber());
        }

    }

    public void setLDAreaCodes(Vector vector)
    {
        PhoneRec phonerec;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); phonerec.setUseAreaCode(true))
            phonerec = (PhoneRec)enumeration.nextElement();

    }

    public void setMuteModem(boolean flag)
    {
        if(flag)
            MuteModem = 1;
        else
            MuteModem = 2;
    }

    public void setOutPrefix(String s)
    {
        OutPrefix = s;
    }

    public void setPrefixes(String s, String s1, String s2, boolean flag)
    {
        OutPrefix = s;
        CallWaitingPrefix = s1;
        CustomPrefix = s2;
        setMuteModem(flag);
    }

    public void setPrefixGUI(Checkbox checkbox, TextField textfield, Checkbox checkbox1, TextField textfield1, Checkbox checkbox2, TextField textfield2, Checkbox checkbox3)
    {
        if(OutPrefix != null)
        {
            checkbox.setState(true);
            textfield.setText(OutPrefix);
        } else
        {
            checkbox.setState(false);
            textfield.setText("9");
        }
        if(CallWaitingPrefix != null)
        {
            checkbox1.setState(true);
            textfield1.setText(CallWaitingPrefix);
        } else
        {
            checkbox1.setState(false);
            textfield1.setText("*70");
        }
        if(CustomPrefix != null)
        {
            checkbox2.setState(true);
            textfield2.setText(CustomPrefix);
        } else
        {
            checkbox2.setState(false);
            textfield2.setText("");
        }
        if(MuteModem == 1)
            checkbox3.setState(true);
        else
            checkbox3.setState(false);
    }

    private void setRepromptCount(int i)
    {
        RepromptCount = i;
    }

    public void setServerNums(String s)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        if(s != null)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, "^");
            k = stringtokenizer.countTokens();
            if(k > 0)
            {
                for(int i1 = 0; i1 < k; i1++)
                {
                    String s7 = stringtokenizer.nextToken();
                    StringTokenizer stringtokenizer1 = new StringTokenizer(s7, "|");
                    String s1 = stringtokenizer1.nextToken().trim();
                    String s2 = stringtokenizer1.nextToken().trim();
                    String s3 = stringtokenizer1.nextToken().trim();
                    String s6 = stringtokenizer1.nextToken().trim();
                    stringtokenizer1.nextToken();
                    String s8 = stringtokenizer1.nextToken();
                    Long long1 = new Long(s8.trim());
                    int l = long1.intValue();
                    stringtokenizer1.nextToken();
                    String s4 = stringtokenizer1.nextToken().trim();
                    String s5 = stringtokenizer1.nextToken().trim();
                    stringtokenizer1.nextToken();
                    s8 = stringtokenizer1.nextToken();
                    long1 = new Long(s8.trim());
                    RepromptCount = long1.intValue();
                    if(RepromptCount < 0)
                        RepromptCount = 0;
                    PhoneRec phonerec = new PhoneRec(s2, s1.substring(0, 3), s1.substring(3, 6), s1.substring(6), s6);
                    if(s4.equals("A"))
                    {
                        i++;
                        phonerec.setInactive(false);
                    } else
                    {
                        phonerec.setInactive(true);
                    }
                    if(s5.equals("I"))
                    {
                        j++;
                        phonerec.setTempInactive(false);
                    } else
                    {
                        phonerec.setTempInactive(true);
                    }
                    phonerec.setStateString(s3);
                    phonerec.setRetryCount(l);
                    phonerec.setRejected(false);
                    addToServerList(phonerec);
                }

            }
        }
        ZCast.displayDebug("################ DETAILS ABOUT DATABASE NUMBERS ##################");
        ZCast.displayDebug("################            START               ##################\n");
        ZCast.displayDebug("################ Total    Numbers from server = " + k);
        ZCast.displayDebug("################ ACTIVE   Numbers from server = " + i);
        ZCast.displayDebug("################ INCLUDED Numbers from server = " + j);
        ZCast.displayDebug("################            END                 ##################\n");
    }

    public void verifyPhoneNumbers()
    {
        if(PhoneNumbers != null && PhoneNumbers.size() > 0)
        {
            for(int i = 0; i < PhoneNumbers.size(); i++)
            {
                PhoneRec phonerec = (PhoneRec)PhoneNumbers.elementAt(i);
                String s = phonerec.getAreaCode() + phonerec.getFirst3() + "-" + phonerec.getLast4();
                String s1 = checkExistanceInPHN(s);
                float f = (new Float(s1)).floatValue();
                float f1 = (new Float("-9999.9999")).floatValue();
                float f2 = (new Float(phonerec.getRank())).floatValue();
                if(f == f1)
                    deleteNumber(i);
                else
                if(f != f2)
                {
                    deleteNumber(i);
                    phonerec.setRank(s1);
                    addByRank(phonerec);
                }
            }

        }
    }

    static final long serialVersionUID = 0x6563c4e307f9ed16L;
    static final int INIT_MODEM = 0;
    static final int MUTE_MODEM = 1;
    static final int HEAR_MODEM = 2;
    public static final String DEF_OUT_PREFIX = "9";
    public static final String DEF_CALL_WAITING_PREFIX = "*70";
    private String GroupName;
    private String DialFrom;
    private Vector PhoneNumbers;
    private Vector ServerNumbers;
    private int RepromptCount;
    private String OutPrefix;
    private String CallWaitingPrefix;
    private String CustomPrefix;
    private int MuteModem;
}
