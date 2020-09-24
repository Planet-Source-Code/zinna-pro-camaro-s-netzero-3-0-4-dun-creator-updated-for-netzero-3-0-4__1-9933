// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FetchRequestImpl.java

package ticker;


// Referenced classes of package ticker:
//            FetchRequest

public class FetchRequestImpl
    implements FetchRequest
{

    public FetchRequestImpl()
    {
        requestStatus = null;
        itemId = null;
        wanted = null;
        requestStatus = new Integer(0);
    }

    public Integer getItemId()
    {
        return itemId;
    }

    public int getStatus()
    {
        return requestStatus.intValue();
    }

    public String getWanted()
    {
        return wanted;
    }

    public void setItemId(Integer integer)
    {
        itemId = integer;
    }

    public void setStatus(int i)
    {
        requestStatus = new Integer(i);
    }

    public void setWanted(String s)
    {
        wanted = s;
    }

    private Integer requestStatus;
    private Integer itemId;
    private String wanted;
}
