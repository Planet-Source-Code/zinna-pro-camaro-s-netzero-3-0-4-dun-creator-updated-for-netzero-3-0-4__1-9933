// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileRapper.java

package qprofile;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package qprofile:
//            ProfileQuestion, MemberAnswer, MemberOptin, ProfileOptin

public class ProfileRapper
    implements Serializable
{

    public ProfileRapper()
    {
    }

    public void addMemberAnswer(MemberAnswer memberanswer)
    {
        if(memberAnswers == null)
            memberAnswers = new Vector(50);
        memberAnswers.addElement(memberanswer);
    }

    public void addMemberOptin(Integer integer)
    {
        if(memberOptins == null)
            memberOptins = new Vector(8);
        memberOptins.addElement(integer);
    }

    public void addMemberOptin(MemberOptin memberoptin)
    {
        if(memberOptins == null)
            memberOptins = new Vector(8);
        memberOptins.addElement(memberoptin);
    }

    public void addOptin(ProfileOptin profileoptin)
    {
        if(optins == null)
            optins = new Vector(50);
        optins.addElement(profileoptin);
    }

    public void addQuestion(ProfileQuestion profilequestion)
    {
        if(questions == null)
            questions = new Vector(50);
        questions.addElement(profilequestion);
    }

    public Vector getMemberAnswers()
    {
        return memberAnswers;
    }

    public Enumeration getMemberAnswersEnumeration()
    {
        return memberAnswers.elements();
    }

    public Vector getMemberOptins()
    {
        return memberOptins;
    }

    public Enumeration getMemberOptinsEnumeration()
    {
        return memberOptins.elements();
    }

    public Vector getOptins()
    {
        return optins;
    }

    public Enumeration getOptinsEnumeration()
    {
        return optins.elements();
    }

    public ProfileQuestion getQuestionByCode(Integer integer)
    {
        for(int i = 0; i < questions.size(); i++)
        {
            ProfileQuestion profilequestion = (ProfileQuestion)questions.elementAt(i);
            if(profilequestion.getCode().equals(integer))
                return profilequestion;
        }

        return null;
    }

    public ProfileQuestion getQuestionByDesc(String s)
    {
        for(int i = 0; i < questions.size(); i++)
        {
            ProfileQuestion profilequestion = (ProfileQuestion)questions.elementAt(i);
            if(profilequestion.getDesc().equals(s))
                return profilequestion;
        }

        return null;
    }

    public Vector getQuestions()
    {
        return questions;
    }

    public Enumeration getQuestionsEnumeration()
    {
        return questions.elements();
    }

    public String getUserid()
    {
        return userid;
    }

    public String getVersion()
    {
        return version;
    }

    public void removeAllMemberAnswers()
    {
        if(memberAnswers != null)
            memberAnswers.removeAllElements();
    }

    public void removeAllMemberOptins()
    {
        if(memberOptins != null)
            memberOptins.removeAllElements();
    }

    public void removeAllOptins()
    {
        if(optins != null)
            optins.removeAllElements();
    }

    public void removeAllQuestions()
    {
        if(questions != null)
            questions.removeAllElements();
    }

    public void setMemberAnswers(Vector vector)
    {
        memberAnswers = vector;
    }

    public void setMemberOptins(Vector vector)
    {
        memberOptins = vector;
    }

    public void setOptins(Vector vector)
    {
        optins = vector;
    }

    public void setQuestions(Vector vector)
    {
        questions = vector;
    }

    public void setUserid(String s)
    {
        userid = s;
    }

    public void setVersion(String s)
    {
        version = s;
    }

    protected String userid;
    protected String version;
    protected Vector questions;
    protected Vector memberAnswers;
    protected Vector optins;
    protected Vector memberOptins;
}
