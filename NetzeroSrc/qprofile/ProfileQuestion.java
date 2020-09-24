// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileQuestion.java

package qprofile;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package qprofile:
//            ProfileAnswer, RelatedQuestion

public class ProfileQuestion
    implements Serializable
{

    public ProfileQuestion()
    {
    }

    public void addAnswer(ProfileAnswer profileanswer)
    {
        if(answers == null)
            answers = new Vector(16);
        answers.addElement(profileanswer);
    }

    public void addRelatedQuestion(RelatedQuestion relatedquestion)
    {
        if(relatedQuestions == null)
            relatedQuestions = new Vector();
        relatedQuestions.addElement(relatedquestion);
    }

    public ProfileAnswer getAnswerByCode(Integer integer)
    {
        for(int i = 0; i < answers.size(); i++)
        {
            ProfileAnswer profileanswer = (ProfileAnswer)answers.elementAt(i);
            if(profileanswer.getCode().equals(integer))
                return profileanswer;
        }

        return null;
    }

    public Character getAnswerFreeformCode()
    {
        return answerFreeformCode;
    }

    public Vector getAnswers()
    {
        return answers;
    }

    public Enumeration getAnswersEnumeration()
    {
        return answers.elements();
    }

    public Integer getCode()
    {
        return code;
    }

    public Character getDataType()
    {
        return dataType;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getDisplayId()
    {
        return displayId;
    }

    public Integer getLength()
    {
        return length;
    }

    public Boolean getMultipleAnswers()
    {
        return multipleAnswers;
    }

    public Boolean getProfileFlag()
    {
        return profileFlag;
    }

    public Character getQuestionType()
    {
        return questionType;
    }

    public Vector getRelatedQuestions()
    {
        return relatedQuestions;
    }

    public Boolean getRelatedQuestionsDefined()
    {
        return relatedQuestionsDefined;
    }

    public Enumeration getRelatedQuestionsEnumeration()
    {
        return relatedQuestions.elements();
    }

    public Boolean getRequired()
    {
        return required;
    }

    public Character getStatus()
    {
        return status;
    }

    public Boolean getTargetFlag()
    {
        return targetFlag;
    }

    public String getText()
    {
        return text;
    }

    public void removeAllAnswers()
    {
        if(answers != null)
            answers.removeAllElements();
    }

    public void removeAllRelatedQuestions()
    {
        if(relatedQuestions != null)
            relatedQuestions.removeAllElements();
    }

    public void setAnswerFreeformCode(Character character)
    {
        answerFreeformCode = character;
    }

    public void setAnswers(Vector vector)
    {
        answers = vector;
    }

    public void setCode(Integer integer)
    {
        code = integer;
    }

    public void setDataType(Character character)
    {
        dataType = character;
    }

    public void setDesc(String s)
    {
        desc = s;
    }

    public void setDisplayId(String s)
    {
        displayId = s;
    }

    public void setLength(Integer integer)
    {
        length = integer;
    }

    public void setMultipleAnswers(Boolean boolean1)
    {
        multipleAnswers = boolean1;
    }

    public void setProfileFlag(Boolean boolean1)
    {
        profileFlag = boolean1;
    }

    public void setQuestionType(Character character)
    {
        questionType = character;
    }

    public void setRelatedQuestions(Vector vector)
    {
        relatedQuestions = vector;
    }

    public void setRelatedQuestionsDefined(Boolean boolean1)
    {
        relatedQuestionsDefined = boolean1;
    }

    public void setRequired(Boolean boolean1)
    {
        required = boolean1;
    }

    public void setStatus(Character character)
    {
        status = character;
    }

    public void setTargetFlag(Boolean boolean1)
    {
        targetFlag = boolean1;
    }

    public void setText(String s)
    {
        text = s;
    }

    protected Integer code;
    protected Character status;
    protected Boolean required;
    protected Boolean profileFlag;
    protected Boolean targetFlag;
    protected String displayId;
    protected String desc;
    protected String text;
    protected Boolean multipleAnswers;
    protected Integer length;
    protected Character dataType;
    protected Boolean relatedQuestionsDefined;
    protected Character questionType;
    protected Vector answers;
    protected Vector relatedQuestions;
    protected Character answerFreeformCode;
}
