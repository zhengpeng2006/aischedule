package com.asiainfo.monitor.analyse.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;
import com.asiainfo.monitor.analyse.IDataAnalyse;

/**
 * 采集数据的分析
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class DataAnalyseImpl implements IDataAnalyse
{
    private static transient Log log = (Log) LogFactory.getLog(DataAnalyseImpl.class);

    private static final Pattern FIRST_PATTERN = Pattern.compile("%%");
    private static final Pattern SECOND_PATTERN = Pattern.compile("\\^\\^\\^");
    /**
     * Map<String, List<String>>
     */
    @Override
    public Object procInfoAnalyse(String analyseType, String processInfo) throws Exception
    {
        //分析应用进程信息
        if(analyseType.equals(AcquireConst.ANALYSE_TYPE_PROCESS)) {
            return this.analyseProcessInfo(processInfo);
        }
        //分析主机KPI信息
        else if(analyseType.equals(AcquireConst.ANALYSE_TYPE_HOST)) {
            return processInfo;
        }
        //单个进程数据信息
        else if(analyseType.equals(AcquireConst.ANALYSE_TYPE_BUSI_PROCESS)) {
            return processInfo;
        }

        return null;
    }

    /**
     * 分析应用进程Kpi信息
     * 
     * 信息格式为：psInfo<user#Pid#cmdInfo^^^...>%%topInfo<pid#res#cpu#mem*....>
     * @param processInfo
     * @return
     */
    private Object analyseProcessInfo(String processInfo)
    {
        Map<String, List<String>> processInfoMap = new HashMap<String, List<String>>();

        //分析shell cmd [PS -ef] 数据信息
        Map<String, List<String>> psInfoMap = new HashMap<String, List<String>>();
        //String[] proArr = processInfo.split("%%");
        String[] proArr = FIRST_PATTERN.split(processInfo);
        String psInfo = proArr[0].trim();
        String topInfo = proArr[1].trim();
        //String[] singleProcInfoArr = psInfo.split("\\^\\^\\^");
        String[] singleProcInfoArr = SECOND_PATTERN.split(psInfo);
        String pid = "";
        for(int i = 1; i < singleProcInfoArr.length; i++) {
            if(StringUtils.isBlank(singleProcInfoArr[i]) || "#".equals(singleProcInfoArr[i])) {
                continue;
            }
            String[] realProcInfoarr = singleProcInfoArr[i].split("#");
            try {
                List<String> listPs = new ArrayList<String>();
                pid = realProcInfoarr[1];
                listPs.add(realProcInfoarr[0]);
                listPs.add(realProcInfoarr[1]);
                listPs.add(realProcInfoarr[2]);

                psInfoMap.put(pid, listPs);
            }
            catch(Exception ex) {
                //log.info("ignore ps information. which process id is :" + pid);
            }
        }

        //分析shell cmd [TOP -bn 1 -d 1 ] 数据信息
        Map<String, List<String>> topInfoMap = new HashMap<String, List<String>>();
        String[] singleTopInfoArr = topInfo.split("\\*");
        for(int j = 7; j < singleTopInfoArr.length; j++) {
            if(StringUtils.isBlank(singleTopInfoArr[j]) || "#".equals(singleTopInfoArr[j])) {
                continue;
            }
            try {
                String[] realTopInfoarr = singleTopInfoArr[j].split("#");
                pid = realTopInfoarr[0];
                List<String> listTop = new ArrayList<String>();
                listTop.add(realTopInfoarr[2]);
                listTop.add(realTopInfoarr[3]);
                listTop.add(realTopInfoarr[4]);

                topInfoMap.put(pid, listTop);
            }
            catch(Exception ex) {

            }
        }

        //信息归并，根据PID进行归并：归并后格式为：User PID  RES CPU MEM CmdInfo
        Iterator iterator = topInfoMap.keySet().iterator();
        String pidKey = null;
        List<String> psValList = null;
        List<String> topValList = null;
        while(iterator.hasNext()) {
            pidKey = (String) iterator.next();

            psValList = psInfoMap.get(pidKey);
            topValList = topInfoMap.get(pidKey);
            try {
                for(int i = 0; i < topValList.size(); i++) {
                    psValList.add(topValList.get(i));
                }
                processInfoMap.put(pidKey, psValList);
            }
            catch(NullPointerException ex) {
                //log.info("ps process id is " + pidKey + ",which can't find cpu mem information in top Info.");
            }
        }

        return processInfoMap;
    }

    @Override
    public BusiLogBean[] busiLogAnalyse(IBOABGMonBusiLogValue[] busiLogArr) throws Exception
    {
        List<BusiLogBean> logList = new ArrayList<BusiLogBean>();
        for(IBOABGMonBusiLogValue busiLog : busiLogArr) {
            BusiLogBean logBean = new BusiLogBean();
            //对象赋值
            logBean.setTotalCnt(busiLog.getTotalCnt());
            logBean.setConsumeTime(busiLog.getConsumeTime());
            logBean.setHandleCnt(busiLog.getHandleCnt());
            logBean.setPerHandleCnt(busiLog.getPerHandleCnt());
            logBean.setRegionCode(busiLog.getRegionCode());
            logBean.setSeq(busiLog.getSeq());
            logBean.setServerCode(busiLog.getServerCode());
            logBean.setTaskId(busiLog.getTaskId());
            logBean.setTaskSplitId(busiLog.getTaskSplitId());
            logBean.setAcqDate(new Date(busiLog.getCreateDate().getTime()));
            logList.add(logBean);
        }

        return logList.toArray(new BusiLogBean[0]);
    }

    @Override
    public BusiErrLogBean[] busiErrLogAnalyse(IBOABGMonBusiErrorLogValue[] busiErrLogArr) throws Exception
    {
        List<BusiErrLogBean> logList = new ArrayList<BusiErrLogBean>();
        for(IBOABGMonBusiErrorLogValue busiLog : busiErrLogArr) {
            BusiErrLogBean logBean = new BusiErrLogBean();
            //对象赋值
            //            logBean.setConsumeTime(busiLog.getConsumeTime());
            //            logBean.setRegionCode(busiLog.getRegionCode());
            //            logBean.setSeq(busiLog.getSeq());
            logBean.setAcqDate(new Date(busiLog.getAcqDate().getTime()));
            logBean.setServerCode(busiLog.getServerCode());
            logBean.setErrCnt(busiLog.getErrCnt());
            logBean.setTaskId(busiLog.getTaskId());
            logBean.setTaskSplitId(busiLog.getTaskSplitId());
            logList.add(logBean);
        }

        return logList.toArray(new BusiErrLogBean[0]);
    }

}
