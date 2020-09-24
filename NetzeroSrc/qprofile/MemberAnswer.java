// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberAnswer.java

package qprofile;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package qprofile:
//            ProfileQuestion

public class MemberAnswer
    implements Serializable
{

    public MemberAnswer()
    {
        answerCodes = new Vector();
    }

    public void addAnswer(Integer integer)
    {
        answerCodes.addElement(integer);
    }

    public Vector getAnswers()
    {
        return answerCodes;
    }

    public Enumeration getAnswersEnumeration()
    {
        return answerCodes.elements();
    }

    public String getAnswerText()
    {
        return answerText;
    }

    public ProfileQuestion getQuestion()
    {
        return question;
    }

    public Integer getQuestionCode()
    {
        return questionCode;
    }

    public void removeAllAnswers()
    {
        answerCodes.removeAllElements();
    }

    public void setAnswers(Vector vector)
    {
        answerCodes = vector;
    }

    public void setAnswerText(String s)
    {
        answerText = s;
    }

    public void setQuestion(ProfileQuestion profilequestion)
    {
        question = profilequestion;
    }

    public void setQuestionCode(Integer integer)
    {
        questionCode = integer;
    }

    protected Integer questionCode;
    protected ProfileQuestion question;
    protected Vector answerCodes;
    protected String answerText;
}
