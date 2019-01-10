package com.asiainfo.monitor.exeframe.loginfomgr.abo.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.logger.common.LoggerDateUtils;
import com.asiainfo.logger.utils.LoggerUtils;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogBean;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.bo.BOABGMonHostLogEngine;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.dao.interfaces.IABGMonHostLogDAO;
import com.asiainfo.monitor.exeframe.loginfomgr.abo.ivalues.IBOABGMonHostLogValue;

public class ABGMonHostLogDAOImpl implements IABGMonHostLogDAO
{
    private transient static Log log = LogFactory.getLog(ABGMonHostLogDAOImpl.class);
    @Override
	public long getBeanId() throws Exception
    {
        return BOABGMonHostLogEngine.getNewId().longValue();
    }

    @Override
	public BOABGMonHostLogBean getBeanById(long id) throws Exception
    {
        return BOABGMonHostLogEngine.getBean(id);
    }

    /**
     * 往日志表里插入数据
     * 
     * @param request
     * @throws Exception
     */
    @Override
	public void save(BOABGMonHostLogBean value) throws Exception
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(value.isNew() && value.getAcqLogId() <= 0) {
            String bd = df.format(nowDate);
            value.setAcqLogId(getBeanId());
            value.setCreateDate(bd);
            value.setState(CommonConst.STATE_U);
            value.setAcqDate(nowDate);
        }
        //字段：ACQ_LOG_ID,HOST_ID,KPI_CPU,KPI_MEM,KPI_FS,EXT_KPI_1,EXT_KPI_2,EXT_KPI_3,EXT_KPI_4,
        //SYSTEM_DOMAIN,SUBSYSTEM_DOMAIN,APP_SERVER_NAME,CREATE_DATE,MON_FLAG,ACQ_DATE,STATE
        StringBuilder msg = new StringBuilder("");
        String splitChar = LoggerUtils.getColumnSeperator();
        // 任务编码
        // msg.append(value.getAcqLogId()).append(splitChar);
        msg.append(value.getHostIp()).append(splitChar);
        msg.append(value.getKpiCpu()).append(splitChar);
        msg.append(value.getKpiMem()).append(splitChar);
        msg.append(value.getKpiFs()).append(splitChar);
        msg.append(value.getExtKpi1()).append(splitChar);
        msg.append(value.getExtKpi2()).append(splitChar);
        msg.append(value.getExtKpi3()).append(splitChar);
        msg.append(value.getExtKpi4()).append(splitChar);

        msg.append(value.getCreateDate()).append(splitChar);
        msg.append(value.getMonFlag()).append(splitChar);
        msg.append(DateUtil.date2String(value.getAcqDate(), DateUtil.DATETIME_FORMAT_1)).append(splitChar);
        msg.append(value.getState());
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			LoggerUtils.addLog("AI.ZJ.ABG_MON_HOST_LOG", msg.toString());
		} else {
			LoggerUtils.addLog("MYSQL.AI.ZJ.ABG_MON_HOST_LOG", msg.toString());
		}
    }

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

    /**
     * 通过开始时间，结束时间和主机Id查询日志
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOABGMonHostLogValue[] qryHisHostKpiInfo(Date beginDate, Date endDate, String hostId, String monFlag) throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//分表字段格式
        StringBuffer strBuff = new StringBuffer();
        StringBuffer pre = new StringBuffer();
        pre.append("select acq_log_id,host_ip,kpi_cpu,kpi_mem,kpi_fs,ext_kpi_1,ext_kpi_2,ext_kpi_3,ext_kpi_4,system_domain,subsystem_domain,app_server_name,create_date,mon_flag,acq_date,state from ");
        //conditon是查询条件
        StringBuffer condition = new StringBuffer();
        Map parameter = new HashMap();
        long hostIdLong = Long.parseLong(hostId);
        condition.append("state='U'");
        if(StringUtils.isNotBlank(hostId)) {
            //根据主机id查询到主机ip
            IBOAIMonPhysicHostValue hostValue = hostSv.getPhysicHostById(hostId);
            String hostIp = hostValue.getHostIp();
            if(StringUtils.isNotBlank(hostIp)) {
                condition.append(" and ");
                condition.append(IBOABGMonHostLogValue.S_HostIp).append("= :hostIp");
                parameter.put("hostIp", hostIp);
            }
        }
        if(StringUtils.isNotBlank(monFlag)) {
            condition.append(" and ").append(IBOABGMonHostLogValue.S_MonFlag).append("= :monFlag");
            parameter.put("monFlag", monFlag);
        }
        Date[] tables = LoggerDateUtils.dividedByMonth(beginDate, endDate);
        if(tables.length == 1) {
            StringBuffer sql = new StringBuffer();
            sql.append("abg_mon_host_log_").append(sdf.format(tables[0])).append(" where ");
            sql.append(IBOABGMonHostLogValue.S_AcqDate).append(" between :beginDate and :endDate and ");
            parameter.put("beginDate", new Timestamp(beginDate.getTime()));
            parameter.put("endDate", new Timestamp(endDate.getTime()));
            parameter.put(IBOABGMonHostLogValue.S_AcqDate, new Timestamp(beginDate.getTime()));

            strBuff.append(pre).append(sql).append(condition).toString();
        }
        else {
            for(int i = 0; i < tables.length; i++) {
                if(i == 0) {
                    StringBuffer sql = new StringBuffer();
                    sql.append("abg_mon_host_log_").append(sdf.format(tables[i])).append(" where ");
                    sql.append(IBOABGMonHostLogValue.S_AcqDate + " >= :beginDate");
                    sql.append(" and ");
                    parameter.put("beginDate", new Timestamp(beginDate.getTime()));
                    // 查询当前时间所在的表
                    parameter.put(IBOABGMonHostLogValue.S_AcqDate, new Timestamp(beginDate.getTime()));
                    strBuff.append(pre).append(sql).append(condition).append(" UNION ALL ");
                }
                else if(i == tables.length - 1) {
                    // 最后一张表，查询<endTime
                    StringBuffer sql = new StringBuffer();
                    sql.append("abg_mon_host_log_").append(sdf.format(tables[i])).append(" where ");
                    sql.append(IBOABGMonHostLogValue.S_AcqDate + " <= :endDate");
                    sql.append(" and ");
                    parameter.put("endDate", new Timestamp(endDate.getTime()));
                    // 查询当前时间所在的表
                    parameter.put(IBOABGMonHostLogValue.S_AcqDate, new Timestamp(endDate.getTime()));
                    strBuff.append(pre).append(sql).append(condition);
                }
                else { // 中间表，查询表中全部
                    StringBuffer sql = new StringBuffer();
                    sql.append("abg_mon_host_log_").append(sdf.format(tables[i])).append(" where ");
                    parameter.put(IBOABGMonHostLogValue.S_AcqDate, new Timestamp(tables[i].getTime()));
                    strBuff.append(pre).append(sql).append(condition).append(" UNION ALL ");
                }
            }
        }
        strBuff.append(" order by ").append(IBOABGMonHostLogValue.S_AcqDate).toString();

        log.debug("SQL STRING :" + strBuff.toString());
        IBOABGMonHostLogValue[] values = BOABGMonHostLogEngine.getBeansFromSql(strBuff.toString(), parameter);
        if(StringUtils.isNotBlank(monFlag)) {
            for(IBOABGMonHostLogValue value : values) {
                value.setMonFlag("Y");
                BOABGMonHostLogEngine.save(value);
            }
        }
        return values;
    }

}
