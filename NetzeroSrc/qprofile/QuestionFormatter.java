// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QuestionFormatter.java

package qprofile;

import java.awt.Component;
import java.util.Vector;

// Referenced classes of package qprofile:
//            MemberAnswer, ProfileQuestion

public interface QuestionFormatter
{

    public abstract void formatQuestion(Component component);

    public abstract MemberAnswer getMemberAnswer();

    public abstract ProfileQuestion getQuestion();

    public abstract boolean isAnswerChanged();

    public abstract boolean isAnswerRequired();

    public abstract void setMemberAnswer(MemberAnswer memberanswer);

    public abstract void setQuestion(ProfileQuestion profilequestion);

    public abstract void setSelectedAnswers();

    public abstract void updateAnswer(Vector vector);

    public abstract boolean verifyAnswer();
}
