// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContainerPosition.java

package ui;


public class ContainerPosition
{

    public ContainerPosition(Long long1, Long long2, Integer integer)
    {
        setContainerID(long1);
        setAlignment(long2);
        setCoordinate(integer);
    }

    public Long getAlignment()
    {
        return relativeToID;
    }

    public Long getContainerID()
    {
        return containerID;
    }

    public Integer getCoordinate()
    {
        return coordinate;
    }

    public void setAlignment(Long long1)
    {
        relativeToID = long1;
    }

    public void setContainerID(Long long1)
    {
        containerID = long1;
    }

    public void setCoordinate(Integer integer)
    {
        coordinate = integer;
    }

    private Long containerID;
    private Long relativeToID;
    private Integer coordinate;
}
