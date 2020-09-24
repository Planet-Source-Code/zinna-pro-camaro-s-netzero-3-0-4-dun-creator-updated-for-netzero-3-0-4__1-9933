// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelatedQuestion.java

package qprofile;

import java.io.Serializable;

// Referenced classes of package qprofile:
//            ProfileQuestion

public class RelatedQuestion
    implements Serializable
{

    public RelatedQuestion()
    {
    }

    public RelatedQuestion(Integer integer, Character character)
    {
        questionCode = integer;
        relatedCode = character;
    }

    public ProfileQuestion getQuestion()
    {
        return question;
    }

    public Integer getQuestionCode()
    {
        return questionCode;
    }

    public Character getRelatedCode()
    {
        return relatedCode;
    }

    public void setQuestion(ProfileQuestion profilequestion)
    {
        question = profilequestion;
    }

    public void setQuestionCode(Integer integer)
    {
        questionCode = integer;
    }

    public void setRelatedCode(Character character)
    {
        relatedCode = character;
    }

    protected Integer questionCode;
    protected Character relatedCode;
    protected ProfileQuestion question;
}
