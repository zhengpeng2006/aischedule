package com.asiainfo.monitor.acquire.dto;

/**
 * 
 * <p>Title: 业务处理日志处理量信息 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiLogBean extends BusiLog
{
    private long totalCnt; //总业务量
    private long perHandleCnt; //每批次处理业务量
    private long handleCnt; //已处理业务量
    private String regionCode; //区域编码
    private long consumeTime; //耗时

    public long getTotalCnt()
    {
        return totalCnt;
    }

    public void setTotalCnt(long totalCnt)
    {
        this.totalCnt = totalCnt;
    }

    public long getPerHandleCnt()
    {
        return perHandleCnt;
    }

    public void setPerHandleCnt(long perHandleCnt)
    {
        this.perHandleCnt = perHandleCnt;
    }

    public long getHandleCnt()
    {
        return handleCnt;
    }

    public void setHandleCnt(long handleCnt)
    {
        this.handleCnt = handleCnt;
    }

    public String getRegionCode()
    {
        return regionCode;
    }

    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    public long getConsumeTime()
    {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime)
    {
        this.consumeTime = consumeTime;
    }

}
