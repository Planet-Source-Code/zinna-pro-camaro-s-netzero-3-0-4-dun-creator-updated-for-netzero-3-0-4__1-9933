// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileNavigator.java

package qprofile;

import java.util.Enumeration;
import java.util.Vector;
import nzcom.*;
import tcpBinary.*;

// Referenced classes of package qprofile:
//            MemberOptin, ProfileRapper, MemberAnswer, QuestionFormatter

public class ProfileNavigator
{

    public ProfileNavigator()
    {
        profileDirty = false;
        optinsDirty = false;
        modifiedAnswers = new Vector();
        testMode = false;
        storeMode = 0;
    }

    public ProfileNavigator(ProfileRapper profilerapper)
    {
        profileDirty = false;
        optinsDirty = false;
        modifiedAnswers = new Vector();
        testMode = false;
        storeMode = 0;
        rapper = profilerapper;
    }

    public void addModifiedAnswer(MemberAnswer memberanswer)
    {
        if(!modifiedAnswers.contains(memberanswer))
            modifiedAnswers.addElement(memberanswer);
        profileDirty = true;
    }

    public MemberAnswer createAnswer(int i, int j, char c, String s)
    {
        MemberAnswer memberanswer = new MemberAnswer();
        memberanswer.setAnswers(new Vector(1));
        memberanswer.setQuestionCode(new Integer(i));
        memberanswer.addAnswer(new Integer(j));
        if(c == 'F')
            memberanswer.setAnswerText(s);
        return memberanswer;
    }

    public boolean createNewUserOnHost(String s, String s1)
    {
        updateRapper = new ProfileRapper();
        updateRapper.setUserid(s);
        updateRapper.setVersion("0");
        updateRapper.setQuestions(null);
        updateRapper.setOptins(null);
        updateRapper.setMemberAnswers(modifiedAnswers);
        tranReq = new TransactionRequest();
        tranReq.setUserNumber(null);
        tranReq.setSessionId(null);
        tranReq.setDataObject(updateRapper);
        tranReq.setTransactionCode(new Integer(1));
        tranReq.setUserId(s);
        tranReq.setPassword(s1);
        tranReq.setClientVersion(ConfigParams.getVers());
        if(testMode)
        {
            for(Enumeration enumeration = modifiedAnswers.elements(); enumeration.hasMoreElements();)
            {
                MemberAnswer memberanswer = (MemberAnswer)enumeration.nextElement();
                ZCast.displayDebug("question " + memberanswer.getQuestionCode().intValue() + " transmitted");
                Integer integer = memberanswer.getQuestionCode();
            }

        }
        Transaction transaction = new Transaction(false);
        try
        {
            if(testMode)
                transaction.setServerLocation(server);
            else
                transaction.setServerLocation(ServerParms.getParm("pfip1", transaction.getServerLocation()));
            TransactionResult transactionresult = transaction.execute(tranReq, "UPRF");
            ZCast.displayDebug("(UPRF) success flag set to: " + transactionresult.getSuccessFlag());
            ZCast.displayDebug("profile create returned object: " + transactionresult.getDataObject().getClass().getName());
            profileDirty = false;
            if(!(transactionresult.getDataObject() instanceof TransactionResponse))
            {
                ZCast.displayDebug("transaction UPRF failed to return TransactionResponse object");
                return false;
            }
            tranResp = (TransactionResponse)transactionresult.getDataObject();
            Initializer.compareTransResponse(tranResp, "UPRF");
            int i = tranResp.getReturnCode();
            ZCast.displayDebug("(UPRF) return code = " + i);
            if(i == 0)
            {
                Initializer.m_userNumber = tranResp.getUserNumber();
                return true;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
        }
        return false;
    }

    public QuestionFormatter fetchHwQuestion()
    {
        return fetchQuestion(hwIndex);
    }

    public QuestionFormatter fetchNextQuestion()
    {
        if(currentIndex < maxIndex - 1)
        {
            currentIndex++;
            if(currentIndex > hwIndex)
                hwIndex = currentIndex;
            return qFormatter[currentIndex];
        } else
        {
            return null;
        }
    }

    public QuestionFormatter fetchPrevQuestion()
    {
        if(currentIndex > 0)
        {
            currentIndex--;
            return qFormatter[currentIndex];
        } else
        {
            return null;
        }
    }

    public QuestionFormatter fetchQuestion(int i)
    {
        if(i > -1 && i < maxIndex)
        {
            currentIndex = i;
            return qFormatter[i];
        } else
        {
            return null;
        }
    }

    public int getCurrentIndex()
    {
        return currentIndex;
    }

    public int getMaxQuestions()
    {
        return maxIndex;
    }

    public boolean getProfileDirty()
    {
        return profileDirty;
    }

    public ProfileRapper getRapper()
    {
        return rapper;
    }

    public boolean getTestMode()
    {
        return false;
    }

    public TransactionRequest getTranReq()
    {
        return tranReq;
    }

    public TransactionResponse getTranResp()
    {
        return tranResp;
    }

    public ProfileRapper getUpdateProfile()
    {
        return updateRapper;
    }

    public boolean isOptinsDirty()
    {
        return optinsDirty;
    }

    public void resetFetch()
    {
        currentIndex = -1;
        hwIndex = -1;
        if(rapper != null)
            maxIndex = rapper.getQuestions().size();
        else
            maxIndex = 0;
    }

    public void resetModifiedAnswers()
    {
        modifiedAnswers = new Vector();
    }

    public void setOptinsDirty(boolean flag)
    {
        optinsDirty = flag;
    }

    public void setProfile3From2(String s, String s1)
    {
        MemberAnswer memberanswer = createAnswer(211, 0, 'F', s);
        rapper.addMemberAnswer(memberanswer);
        addModifiedAnswer(memberanswer);
        memberanswer = createAnswer(211, 0, 'F', s);
        rapper.addMemberAnswer(memberanswer);
        addModifiedAnswer(memberanswer);
        memberanswer = createAnswer(212, 0, 'F', s1);
        rapper.addMemberAnswer(memberanswer);
        addModifiedAnswer(memberanswer);
        memberanswer = createAnswer(213, 0, 'F', "Z");
        rapper.addMemberAnswer(memberanswer);
        addModifiedAnswer(memberanswer);
        memberanswer = createAnswer(101, 1, ' ', null);
        rapper.addMemberAnswer(memberanswer);
        addModifiedAnswer(memberanswer);
    }

    public void setProfileDirty(boolean flag)
    {
        profileDirty = flag;
    }

    public void setRapper(ProfileRapper profilerapper)
    {
        rapper = profilerapper;
    }

    public void setServer(String s)
    {
        server = s;
    }

    public void setTestMode(boolean flag)
    {
        testMode = flag;
    }

    public boolean storeUpdatesToHost()
    {
        if(profileDirty || optinsDirty)
        {
            ZCast.displayDebug("Profile update needed");
        } else
        {
            ZCast.displayDebug("Profile not modified, update ignored");
            return true;
        }
        updateRapper = new ProfileRapper();
        updateRapper.setUserid(rapper.getUserid());
        updateRapper.setVersion(rapper.getVersion());
        updateRapper.setQuestions(null);
        updateRapper.setOptins(null);
        updateRapper.setMemberAnswers(modifiedAnswers);
        if(optinsDirty)
        {
            updateRapper.removeAllMemberOptins();
            for(Enumeration enumeration = rapper.getMemberOptinsEnumeration(); enumeration.hasMoreElements(); updateRapper.addMemberOptin((MemberOptin)enumeration.nextElement()));
        }
        tranReq.setUserNumber(Initializer.m_userNumber);
        tranReq.setSessionId(Initializer.m_sessionId);
        tranReq.setClientVersion(ConfigParams.getVers());
        tranReq.setDataObject(updateRapper);
        tranReq.setTransactionCode(new Integer(storeMode));
        ZCast.displayDebug("storing in mode " + storeMode);
        if(testMode)
        {
            for(Enumeration enumeration1 = modifiedAnswers.elements(); enumeration1.hasMoreElements();)
            {
                MemberAnswer memberanswer = (MemberAnswer)enumeration1.nextElement();
                ZCast.displayDebug("question " + memberanswer.getQuestionCode().intValue() + " modified");
                Integer integer = memberanswer.getQuestionCode();
            }

        }
        Transaction transaction = new Transaction(false);
        try
        {
            if(testMode)
                transaction.setServerLocation(server);
            else
                transaction.setServerLocation(ServerParms.getParm("pfip1", transaction.getServerLocation()));
            TransactionResult transactionresult = transaction.execute(tranReq, "UPRF");
            ZCast.displayDebug("(UPRF) success flag set to: " + transactionresult.getSuccessFlag());
            ZCast.displayDebug("profile update returned object: " + transactionresult.getDataObject().getClass().getName());
            profileDirty = false;
            if(!(transactionresult.getDataObject() instanceof TransactionResponse))
            {
                ZCast.displayDebug("transaction UPRF failed to return TransactionResponse object");
                return false;
            } else
            {
                tranResp = (TransactionResponse)transactionresult.getDataObject();
                Initializer.compareTransResponse(tranResp, "UPRF");
                ZCast.displayDebug("(UPRF) return code = " + tranResp.getReturnCode());
                return tranResp.getReturnCode() == 0;
            }
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception.toString());
        }
        return false;
    }

    protected ProfileRapper rapper;
    protected ProfileRapper updateRapper;
    protected TransactionRequest tranReq;
    protected TransactionResponse tranResp;
    protected boolean profileDirty;
    protected boolean optinsDirty;
    protected Vector modifiedAnswers;
    protected QuestionFormatter qFormatter[];
    private int currentIndex;
    private int maxIndex;
    private int hwIndex;
    private boolean testMode;
    private String server;
    private int storeMode;
}
