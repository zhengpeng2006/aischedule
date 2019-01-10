package com.asiainfo.monitor.busi.exe.task.job;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiProcessSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.service.interfaces.IABGMonHostLogSV;

public class ThreadAcqHostKpi extends Thread
{
    private static transient Log log = LogFactory.getLog(ThreadAcqHostKpi.class);

    private String hostId;

    public ThreadAcqHostKpi(String hostId)
    {
        this.hostId = hostId;
    }

    public void run()
    {
        try {
            IBusiProcessSV sv = (IBusiProcessSV) ServiceFactory.getService(IBusiProcessSV.class);
            JSONArray jsonKpiArr = sv.acqHostKpiInfo(hostId);
            if(jsonKpiArr.size() > 0) {
                JSONObject jsonObj = jsonKpiArr.getJSONObject(0);
                String kpiCpu = (String) jsonObj.get("KPI_CPU");
                String kpiMem = (String) jsonObj.get("KPI_MEM");
                String kpiFile = (String) jsonObj.get("KPI_FILE");

                BOABGMonHostLogBean bean = new BOABGMonHostLogBean();
                bean.setStsToNew();

                //设置主机标识
                // bean.setHostId(Long.parseLong(hostId));
                bean.setKpiCpu(kpiCpu);
                bean.setKpiMem(kpiMem);
                bean.setKpiFs(kpiFile);
                bean.setSystemDomain(CommonConst.SYSTEM_DOMAIN);
                //子系统编码
                bean.setSubsystemDomain(CommonConst.SUB_SYSTEM_DOMAIN);
                bean.setAppServerName(CommonConst.APP_SERVER_NAME);
                bean.setMonFlag(CommonConst.MON_FLAG_N);
                IABGMonHostLogSV logSv = (IABGMonHostLogSV) ServiceFactory.getService(IABGMonHostLogSV.class);
                logSv.save(bean);
            }
        }
        catch(Exception e) {
            log.error("acquire host's information exception ..");
        }
    }

}
