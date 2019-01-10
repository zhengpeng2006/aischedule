package com.asiainfo.monitor.acquire.dto;

/**
 * 
 * <p>Title: 业务处理日志错单量 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BusiErrLogBean extends BusiLog
{
    private long errCnt; //错单量
    private long consumeTime; //耗时

    public long getErrCnt()
    {
        return errCnt;
    }

    public void setErrCnt(long errCnt)
    {
        this.errCnt = errCnt;
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
