// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TopicsInformation.java

package ticker;

import java.util.Vector;

// Referenced classes of package ticker:
//            TopicItem

public interface TopicsInformation
{

    public abstract void addElement(TopicItem topicitem);

    public abstract String[] getColumnLabels();

    public abstract TopicItem getHeader();

    public abstract int getSearchStatus();

    public abstract Vector getSelection();

    public abstract int getTopicId();

    public abstract Integer getUpperLimit();

    public abstract void setSelection2Null();
}
