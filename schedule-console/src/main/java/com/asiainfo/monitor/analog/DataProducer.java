package com.asiainfo.monitor.analog;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;

/**
 * 数据创建者
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class DataProducer
{

    private int seq = 0;
    /**
     * 批量插入数据
     * @throws Exception
     */
    public void batchBusiLogInsert(RequestChannel logChannel) throws Exception
    {
        //共5个批次
        List<String> serialList = new ArrayList<String>();
        serialList.add("1000001");
        //        serialList.add("1000000002");
        //        serialList.add("1000000003");
        //        serialList.add("1000000004");
        //        serialList.add("1000000005");

        //每个批次总量
        List<String> totalCntList = new ArrayList<String>();
        totalCntList.add("100000");
        //        totalCntList.add("1000000");
        //        totalCntList.add("1000000");
        //        totalCntList.add("1000000");
        //        totalCntList.add("1000000");

        long handleCnt = 0;

        for(String serialNo : serialList) {
            System.out.println("begin on batch serial no:" + serialNo);
            long serial = Long.parseLong(serialNo);
            handleCnt = 0;
            String taskId = String.valueOf("5700001");
            for(String totalCnt : totalCntList) {
                long tlCnt = Long.parseLong(totalCnt);
                System.out.println("handing total count :" + totalCnt);
                long logBeginTime = System.currentTimeMillis();
                long errLogBeginTime = System.currentTimeMillis();
                for(int i = 0; i < tlCnt; i++) {
                    if(handleCnt >= tlCnt) {
                        break;
                    }
                    //100条一个批次
                    if(i % 100 != 0 && i != 0) {
                        Thread.currentThread().sleep(50l);
                        continue;
                    }
                    else if(i != 0) {
                        int perHandleCnt = new Random().nextInt(300) + 1200;
                        handleCnt += perHandleCnt;

                        System.out.println("handling 100 count.");
                        long useTime = System.currentTimeMillis() - logBeginTime;

                        logBeginTime = System.currentTimeMillis();
                        BusiLogBean logBean = new BusiLogBean();
                        logBean.setTaskId(taskId);
                        logBean.setTaskSplitId(logBean.getTaskId());
                        logBean.setSerialNo(Long.parseLong(serialNo));
                        logBean.setTotalCnt(Long.parseLong(totalCnt));
                        logBean.setPerHandleCnt(perHandleCnt);
                        logBean.setHandleCnt(handleCnt);
                        logBean.setServerCode("MON-EXE");
                        logBean.setRegionCode("570");
                        logBean.setConsumeTime(useTime);
                        logBean.setAcqDate(DateUtil.getNowDate());
                        logBean.setSeq(++seq);

                        //修饰日志bean
                        logChannel.putRequest(logBean);
                    }
                    //每500条记录一次错单日志
                    if(i % 20 == 0) {

                        System.out.println("handling 500 count. write errlog ...");
                        long useTime = System.currentTimeMillis() - errLogBeginTime;

                        errLogBeginTime = System.currentTimeMillis();
                        BusiErrLogBean logBean = new BusiErrLogBean();
                        logBean.setTaskId(taskId);
                        logBean.setTaskSplitId(logBean.getTaskId());
                        logBean.setSerialNo(Long.parseLong(serialNo));
                        logBean.setServerCode("MON-EXE");
                        logBean.setErrCnt(new Random().nextInt(20) + 10);
                        //logBean.set("570");
                        logBean.setConsumeTime(useTime);
                        logBean.setAcqDate(DateUtil.getNowDate());
                        logBean.setSeq(++seq);

                        //修饰日志bean
                        logChannel.putErrRequest(logBean);

                    }

                }
            }

        }

    }

    /**
     * 业务日志生产者模拟使用
     * @return
     * @throws Exception
     */
    public synchronized BOABGMonBusiLogBean busiLogProduce(BusiLogBean logBean) throws Exception
    {
        Date nowDate = DateUtil.getNowDate();
        DateUtil.date2String(nowDate);

        BOABGMonBusiLogBean logValBean = new BOABGMonBusiLogBean();
        //logValBean.setThroughputId(BOAIMonBusiLogEngine.getNewId().longValue());
        //logValBean.setThroughputId(new Random().nextInt(1000) * 1000);
       
        logValBean.setSerialNo(logBean.getSerialNo());
        logValBean.setTaskId(logBean.getTaskId());
        logValBean.setTaskSplitId(logBean.getTaskSplitId());
        logValBean.setServerCode(logBean.getServerCode());
        logValBean.setTotalCnt(logBean.getTotalCnt());
        logValBean.setPerHandleCnt(logBean.getPerHandleCnt());
        logValBean.setHandleCnt(logBean.getHandleCnt());
        logValBean.setRegionCode(logBean.getRegionCode());
        logValBean.setConsumeTime(logBean.getConsumeTime());
        logValBean.setSeq(logBean.getSeq());
        logValBean.setSystemDomain("CRM");
        logValBean.setSubsystemDomain("CRM-X86");
        logValBean.setAppServerName("CRM-X86-Monitor");
        logValBean.setCreateDate(new Timestamp(nowDate.getTime()));
        logValBean.setMonFlag("Y");

        return logValBean;
    }

    /**
     * 业务日志生产者模拟使用
     * @return
     * @throws Exception
     */
    public synchronized BOABGMonBusiErrorLogBean errLogProduce(BusiErrLogBean logBean) throws Exception
    {
        Date nowDate = DateUtil.getNowDate();
        DateUtil.date2String(nowDate);

        BOABGMonBusiErrorLogBean logValBean = new BOABGMonBusiErrorLogBean();
        //logValBean.setThroughputId(BOAIMonBusiLogEngine.getNewId().longValue());
        //logValBean.setThroughputId(new Random().nextInt(1000) * 1000);

        logValBean.setSerialNo(logBean.getSerialNo());
        logValBean.setTaskId(logBean.getTaskId());
        logValBean.setTaskSplitId(logBean.getTaskSplitId());
        logValBean.setServerCode(logBean.getServerCode());
        //        logValBean.setRegionCode(logBean.getRegionCode());
        logValBean.setErrCnt(logBean.getErrCnt());
        logValBean.setSystemDomain("CRM");
        logValBean.setSubsystemDomain("CRM-X86");
        logValBean.setAppServerName("CRM-X86-Monitor");
        logValBean.setCreateDate(DateUtil.date2String(DateUtil.getNowDate()));
        logValBean.setAcqDate(new Timestamp(nowDate.getTime()));
        logValBean.setMonFlag("Y");

        return logValBean;
    }

}
