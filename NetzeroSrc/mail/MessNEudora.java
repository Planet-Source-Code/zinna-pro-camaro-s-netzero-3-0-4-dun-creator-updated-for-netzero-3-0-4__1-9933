// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessNEudora.java

package mail;


// Referenced classes of package mail:
//            EmailCfg, EmailConfigException

public abstract class MessNEudora extends EmailCfg
{

    public MessNEudora(String s)
        throws EmailConfigException
    {
        super(s);
        fileDir = null;
        fileName = null;
        profileName = null;
        newProfile = false;
    }

    public abstract boolean canCreateNewProf();

    protected abstract void createNewProfile()
        throws EmailConfigException;

    protected String getFileDir()
        throws EmailConfigException
    {
        if(fileDir == null)
            initFileDir();
        return fileDir;
    }

    protected String getFileName()
        throws EmailConfigException
    {
        if(fileName == null)
            initFileName();
        return fileName;
    }

    protected String getProfileName()
    {
        return profileName;
    }

    public abstract String[] getProfiles();

    protected abstract void initFileDir()
        throws EmailConfigException;

    protected abstract void initFileName()
        throws EmailConfigException;

    protected boolean isNewProfile()
    {
        return newProfile;
    }

    public void setNewProfile(boolean flag)
    {
        newProfile = flag;
    }

    public void setProfileName(String s)
    {
        profileName = s;
    }

    public void setupAccount()
        throws EmailConfigException
    {
        if(isNewProfile())
            createNewProfile();
        else
            updateExistingAccount();
    }

    protected abstract void updateExistingAccount()
        throws EmailConfigException;

    protected String fileDir;
    protected String fileName;
    private String profileName;
    private boolean newProfile;
}
