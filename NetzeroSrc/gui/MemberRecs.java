// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberRecs.java

package gui;

import java.awt.Choice;
import java.io.*;
import java.util.Vector;
import jclass.bwt.JCComboBox;
import nzcom.ZCast;

// Referenced classes of package gui:
//            MemberRec

public class MemberRecs
    implements Serializable
{

    public MemberRecs()
    {
    }

    public static boolean addingDup(String s)
    {
        if(CurrentMember == null)
            return false;
        return memberExists(s);
    }

    public static void addMemberRec(String s)
    {
        ZCast.displayDebug("adding id " + s);
        if(memrecs == null)
            memrecs = new Vector();
        MemberRec memberrec = new MemberRec(s, "");
        ZCast.displayDebug("add Member rec to vector:");
        memberrec.printRec();
        addNewRec(memberrec);
    }

    public static void addMemberRec(String s, String s1)
    {
        ZCast.displayDebug("adding id " + s);
        ZCast.displayDebug("pass is " + s1);
        if(memrecs == null)
            memrecs = new Vector();
        String s2 = s1;
        if(s1.length() > 0)
            s2 = mangle(s1);
        ZCast.displayDebug("mangled pass is " + s2);
        MemberRec memberrec = new MemberRec(s, s2);
        ZCast.displayDebug("add Member rec to vector:");
        addNewRec(memberrec);
    }

    private static void addNewRec(MemberRec memberrec)
    {
        if(memrecs == null)
        {
            memrecs = new Vector();
            memrecs.addElement(memberrec);
            CurrentMember = (MemberRec)memrecs.elementAt(0);
            CurrentMemberString = memberrec.getID();
        } else
        {
            int i = getMemberIndex(memberrec.getID());
            if(i > 0)
            {
                CurrentMember = (MemberRec)memrecs.elementAt(i);
                CurrentMemberString = memberrec.getID();
            } else
            {
                memrecs.addElement(memberrec);
                int j = memrecs.indexOf(memberrec);
                ZCast.displayDebug("indexof is " + j);
                CurrentMember = (MemberRec)memrecs.elementAt(j);
                CurrentMemberString = memberrec.getID();
            }
        }
    }

    public static void deleteID(String s)
    {
        for(int i = 0; i < memrecs.size(); i++)
        {
            MemberRec memberrec = (MemberRec)memrecs.elementAt(i);
            if(!s.equals(memberrec.getID()))
                continue;
            memrecs.removeElementAt(i);
            if(memrecs.size() <= 0)
            {
                CurrentMember = null;
                CurrentMemberString = "";
            } else
            {
                if(i == memrecs.size())
                    CurrentMember = (MemberRec)memrecs.elementAt(i - 1);
                else
                    CurrentMember = (MemberRec)memrecs.elementAt(i);
                CurrentMemberString = CurrentMember.getID();
            }
            break;
        }

    }

    public static void fillComboBox(JCComboBox jccombobox)
    {
        for(int i = 0; i < memrecs.size(); i++)
        {
            MemberRec memberrec = (MemberRec)memrecs.elementAt(i);
            jccombobox.add(memberrec.getID());
        }

        if(CurrentMember != null)
        {
            ZCast.displayDebug("select " + CurrentMember.getID());
            jccombobox.select(CurrentMember.getID());
        }
    }

    public static void fillMemberIDChoice(Choice choice)
    {
        String as[] = null;
        as = getMemberIDs();
        if(as == null)
        {
            ZCast.displayDebug("IDs is null");
            return;
        }
        ZCast.displayDebug("num IDs = " + as.length);
        for(int i = 0; i < as.length; i++)
            choice.addItem(as[i]);

        if(CurrentMember != null)
            choice.select(CurrentMember.getID());
        if(numMemberRecs() == 0)
            choice.select(0);
    }

    public static String getAnswer()
    {
        if(CurrentMember == null)
            return "";
        else
            return CurrentMember.getAnswer();
    }

    public static String getCurrentMemberID()
    {
        if(CurrentMember == null)
            return "";
        else
            return CurrentMember.getID();
    }

    public static String getCurrentPassword()
    {
        if(CurrentMember == null)
            return "";
        else
            return unmangle(CurrentMember.getPassword());
    }

    public static String getCurrentLanguage()
    {
        return "EN";
    }

    public static boolean getIgnoreConfirm()
    {
        return CurrentMember.getIgnoreConfirm();
    }

    public static final boolean getIsNewUser()
    {
        return CurrentMember.getIsNewUser();
    }

    public static String[] getMemberIDs()
    {
        if(memrecs == null || memrecs.size() <= 0)
            return null;
        String as[] = new String[memrecs.size()];
        for(int i = 0; i < memrecs.size(); i++)
        {
            MemberRec memberrec = (MemberRec)memrecs.elementAt(i);
            as[i] = memberrec.getID();
        }

        return as;
    }

    private static int getMemberIndex(String s)
    {
        int i = -1;
        for(int j = 0; j < memrecs.size(); j++)
        {
            MemberRec memberrec = (MemberRec)memrecs.elementAt(j);
            if(!s.equals(memberrec.getID()))
                continue;
            i = j;
            break;
        }

        return i;
    }

    private static MemberRec getMemberRec(String s)
    {
        if(memrecs == null)
            return null;
        MemberRec memberrec = null;
        for(int i = 0; i < memrecs.size(); i++)
        {
            memberrec = (MemberRec)memrecs.elementAt(i);
            if(s.equals(memberrec.getID()))
                break;
        }

        return memberrec;
    }

    public static String getQuestion()
    {
        if(CurrentMember == null)
            return "";
        else
            return CurrentMember.getQuestion();
    }

    public static String getQuestionCode()
    {
        if(CurrentMember == null)
            return "";
        try
        {
            return CurrentMember.getQuestionCode();
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return "-1";
    }

    public static boolean getSavePassword()
    {
        if(CurrentMember == null)
            return false;
        else
            return CurrentMember.savePassword();
    }

    public static String mangle(String s)
    {
        int k = s.length();
        char ac[] = new char[2 * k];
        for(int l = 0; l < k; l++)
        {
            char c = s.charAt(l);
            int i = c / 16;
            int j = c % 16;
            ac[l] = "1aMQf7gT94LWe6yC".charAt(j);
            ac[l + k] = "1aMQf7gT94LWe6yC".charAt(i);
        }

        String s1 = new String(ac);
        return s1;
    }

    private static boolean memberExists(String s)
    {
        if(memrecs == null || memrecs.size() <= 0)
            return false;
        boolean flag = false;
        for(int i = 0; i < memrecs.size(); i++)
        {
            MemberRec memberrec = (MemberRec)memrecs.elementAt(i);
            if(!s.equalsIgnoreCase(memberrec.getID()))
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public static int numMemberRecs()
    {
        if(memrecs == null)
            return 0;
        else
            return memrecs.size();
    }

    public static void printMemberRecs()
    {
        ZCast.displayDebug("\n------Printing Member records------");
        if(memrecs == null)
        {
            ZCast.displayDebug("memrecs is null");
        } else
        {
            for(int i = 0; i < memrecs.size(); i++)
            {
                MemberRec memberrec = (MemberRec)memrecs.elementAt(i);
                ZCast.displayDebug("-----");
                ZCast.displayDebug("record " + i + ":");
                memberrec.printRec();
                ZCast.displayDebug("-----");
            }

        }
        if(CurrentMember == null)
        {
            ZCast.displayDebug("Current Member is null");
        } else
        {
            ZCast.displayDebug("Current Member is not null");
            ZCast.displayDebug("and getID() is " + CurrentMember.getID());
        }
        ZCast.displayDebug("CurrentMemberString is " + CurrentMemberString);
        ZCast.displayDebug("----Done Printing Member records----\n");
    }

    public static void readRecs()
    {
        File file = new File("id.dat");
        ZCast.displayDebug("reading ID File");
        if(file.exists())
        {
            ZCast.displayDebug("file exists, read it");
            try
            {
                ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream("id.dat"));
                ZCast.displayDebug("--------Read IDs--------");
                memrecs = (Vector)objectinputstream.readObject();
                ZCast.displayDebug("----Done Reading IDs----");
                ZCast.displayDebug("read Current Member ID");
                CurrentMemberString = (String)objectinputstream.readObject();
                ZCast.displayDebug("and it is " + CurrentMemberString);
                if(CurrentMemberString != null && CurrentMemberString.length() > 0)
                {
                    setCurrentMember(CurrentMemberString);
                    ZCast.displayDebug("set currentMember");
                } else
                if(memrecs == null || memrecs.size() == 0)
                {
                    CurrentMember = null;
                    ZCast.displayDebug("Current Member is null");
                } else
                {
                    CurrentMember = (MemberRec)memrecs.elementAt(0);
                }
                objectinputstream.close();
            }
            catch(IOException ioexception)
            {
                ZCast.displayDebug("Exception reading ID file. " + ioexception);
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
        } else
        {
            ZCast.displayDebug("no ID file, init");
            memrecs = null;
            CurrentMember = null;
        }
        if(CurrentMember == null)
            ZCast.displayDebug("current Member is null");
        ZCast.displayDebug("Read the groups in.  They are:");
        printMemberRecs();
    }

    public static void setCurrentGroupName(String s)
    {
        CurrentMember.setGroupName(s);
    }

    public static void setCurrentMember(String s)
    {
        if(memrecs == null || memrecs.size() <= 0)
            return;
        MemberRec memberrec = null;
        memberrec = getMemberRec(s);
        if(memberrec != null)
        {
            CurrentMember = memberrec;
            CurrentMemberString = s;
        }
    }

    public static void setCurrentMemberID(String s)
    {
        CurrentMember.setID(s);
        CurrentMemberString = CurrentMember.getID();
        ZCast.displayDebug("Set current member id to " + CurrentMember.getID());
        ZCast.displayDebug("Current member id string set to " + CurrentMemberString);
    }

    public static void setCurrentPassword(String s)
    {
        CurrentMember.setPassword(mangle(s));
    }

    public static void setIgnoreConfirm(boolean flag)
    {
        CurrentMember.setIgnoreConfirm(flag);
        writeRecs();
    }

    public static void setIsNewUser(boolean flag)
    {
        CurrentMember.setIsNewUser(flag);
    }

    public static void setQA(String s, String s1)
    {
        CurrentMember.setQuestion(s);
        CurrentMember.setAnswer(s1);
    }

    public static void setSavePassword(boolean flag)
    {
        CurrentMember.setSavePassword(flag);
    }

    public static String unmangle(String s)
    {
        int k = s.length() / 2;
        char ac[] = new char[k];
        for(int l = 0; l < k; l++)
        {
            int i = "1aMQf7gT94LWe6yC".indexOf(s.charAt(l));
            int j = "1aMQf7gT94LWe6yC".indexOf(s.charAt(l + k));
            ac[l] = (char)(j * 16 + i);
        }

        String s1 = new String(ac);
        return s1;
    }

    public static void setNewBrowser(boolean flag)
    {
        CurrentMember.setNewBrowser(flag);
    }

    public static boolean getNewBrowser()
    {
        if(CurrentMember == null)
            return true;
        else
            return CurrentMember.getNewBrowser();
    }

    public static void writeRecs()
    {
        try
        {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream("id.dat"));
            if(memrecs == null || CurrentMemberString == null)
            {
                objectoutputstream.close();
                ZCast.displayDebug("everything is null, no write");
                return;
            }
            objectoutputstream.writeObject(memrecs);
            objectoutputstream.writeObject(CurrentMemberString);
            objectoutputstream.close();
        }
        catch(IOException ioexception)
        {
            ZCast.displayDebug("exception writing Groups. " + ioexception);
        }
    }

    static final String ID_FILENAME = "id.dat";
    private static final String m1 = "1aMQf7gT94LWe6yC";
    private static Vector memrecs = null;
    private static MemberRec CurrentMember = null;
    private static String CurrentMemberString = null;

}
