// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberRec.java

package gui;

import java.io.Serializable;
import nzcom.ZCast;

public class MemberRec
    implements Serializable
{

    public MemberRec(String s, String s1)
    {
        id = null;
        password = null;
        SavePassword = false;
        GroupName = null;
        question = null;
        answer = null;
        isNewUser = false;
        ignoreConfirm = true;
        newBrowser = true;
        id = s;
        password = s1;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setNewBrowser(boolean flag)
    {
        newBrowser = flag;
    }

    public boolean getNewBrowser()
    {
        return newBrowser;
    }

    public String getGroupName()
    {
        return GroupName;
    }

    public String getID()
    {
        return id;
    }

    public boolean getIgnoreConfirm()
    {
        return ignoreConfirm;
    }

    public boolean getIsNewUser()
    {
        return isNewUser;
    }

    public String getPassword()
    {
        return password;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getQuestionCode()
        throws Exception
    {
        String s = getQuestion();
        if(s == null)
            throw new Exception("Password question has not yet been set");
        if(s.indexOf("maiden") > 0)
            return "1";
        if(s.indexOf("dog") > 0)
            return "2";
        if(s.indexOf("city") > 0)
            return "3";
        if(s.indexOf("social security") > 0)
            return "4";
        if(s.indexOf("color") > 0)
            return "5";
        else
            throw new Exception("Password question is not in the code list");
    }

    public void printRec()
    {
        ZCast.displayDebug("id == " + id);
        ZCast.displayDebug("password == " + password);
        ZCast.displayDebug("SavePassword == " + SavePassword);
        ZCast.displayDebug("GroupName == " + GroupName);
        ZCast.displayDebug("New User == " + isNewUser);
    }

    public boolean savePassword()
    {
        return SavePassword;
    }

    public void setAnswer(String s)
    {
        answer = s;
    }

    public void setGroupName(String s)
    {
        GroupName = s;
    }

    public void setID(String s)
    {
        id = s;
    }

    public void setIgnoreConfirm(boolean flag)
    {
        ignoreConfirm = flag;
    }

    public void setIsNewUser(boolean flag)
    {
        isNewUser = flag;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setQuestion(String s)
    {
        question = s;
    }

    public void setSavePassword(boolean flag)
    {
        SavePassword = flag;
    }

    static final long serialVersionUID = 0x93d370e901fac778L;
    private String id;
    private String password;
    private boolean SavePassword;
    private String GroupName;
    private String question;
    private String answer;
    private boolean isNewUser;
    private boolean ignoreConfirm;
    private boolean newBrowser;
}
