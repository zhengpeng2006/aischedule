package com.asiainfo.common.abo.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOABGMonWTriggerBean;
import com.asiainfo.common.abo.bo.BOABGMonWTriggerEngine;
import com.asiainfo.common.abo.dao.interfaces.IABGMonWTriggerDAO;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.logger.utils.LoggerUtils;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public class ABGMonWTriggerDAOImpl implements IABGMonWTriggerDAO
{

    private static transient Log log = LogFactory.getLog(ABGMonWTriggerDAOImpl.class);

    @Override
	public long getBeanId() throws Exception
    {
        return BOABGMonWTriggerEngine.getNewId().longValue();
    }

    @Override
	public BOABGMonWTriggerBean getBeanById(long id) throws Exception
    {
        return BOABGMonWTriggerEngine.getBean(id);
    }

    @Override
	public void save(BOABGMonWTriggerBean value) throws Exception
    {
        if(value.isNew() && value.getTriggerId() <= 0)
            value.setTriggerId(getBeanId());
        BOABGMonWTriggerEngine.save(value);
    }

    /** 
    * 根据条件查询告警记录信息
    * @param condition
    * @param parameter
    * @return
    * @throws Exception
    */
    @Override
	public IBOABGMonWTriggerValue[] query(String condition, Map parameter) throws Exception
    {
        return BOABGMonWTriggerEngine.getBeans(condition, parameter);
    }

    /** 
     * 根据告警标识查找告警记录
     * @param trigger_Id
     * @return
     * @throws Exception
     */
    @Override
	public IBOABGMonWTriggerValue getWTriggerBean(String trigger_Id) throws Exception
    {
        return BOABGMonWTriggerEngine.getBean(Long.parseLong(trigger_Id));
    }

    /** 
     * 根据条件查询告警记录
     * @param condition
     * @param startIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    @Override
	public IBOABGMonWTriggerValue[] getMonWTriggerByCondition(String condition, Map parameter, int startIndex, int endIndex) throws Exception
    {
        return BOABGMonWTriggerEngine.getBeans(null, condition, parameter, startIndex, endIndex, true);
    }

    /** 
     * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
     * @param infoId
     * @param second
     * @param level
     * @return
     * @throws Exception
     */
    @Override
	public IBOABGMonWTriggerValue[] getMonWTriggerBefore(long infoId, int second, String level) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(IBOAIMonWTriggerValue.S_InfoId).append("=").append(" :infoId ");
        sb.append(" and ");
        sb.append(IBOAIMonWTriggerValue.S_CreateDate).append("<=(sysdate- :second /24/60) ");
        Map parameter = new HashMap();
        parameter.put("infoId", infoId);
        parameter.put("second", second);
        return BOABGMonWTriggerEngine.getBeans(sb.toString(), parameter);
    }

    /** 
     * 分页信息
     * @param realSQL String
     * @param startIndex long
     * @param endIndex long
     * @return String
     */
    private String getPagingSQL(String realSQL, long startIndex, long endIndex)
    {
        StringBuilder pagingSelect = new StringBuilder();
        pagingSelect.append("select TRIGGER_ID,RECORD_ID,IP,INFO_ID,INFO_NAME,PHONENUM,CONTENT,WARN_LEVEL,");
        pagingSelect.append("CREATE_DATE,DONE_DATE,STATE,REMARKS,rownum_ from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(realSQL);
        pagingSelect.append(" ) row_ where rownum <= " + endIndex + " ) where rownum_ > " + startIndex);
        return pagingSelect.toString();
    }

    /** 
     * 批量保存或修改警告记�?
     * @param values
     * @throws Exception
     */
    @Override
	public void saveOrUpdate(IBOABGMonWTriggerValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew() && values[i].getTriggerId() <= 0) {
                    values[i].setTriggerId(BOABGMonWTriggerEngine.getNewId().longValue());
                }
                StringBuilder msg = new StringBuilder("");
                String splitChar = LoggerUtils.getColumnSeperator();
                // 任务编码
                // msg.append(values[i].getTriggerId()).append(splitChar);
                msg.append(values[i].getRecordId()).append(splitChar);
                msg.append(values[i].getIp()).append(splitChar);
                msg.append(values[i].getInfoId()).append(splitChar);
                msg.append(values[i].getInfoName()).append(splitChar);
                msg.append(values[i].getLayer()).append(splitChar);
                msg.append(values[i].getPhonenum()==null?"":values[i].getPhonenum()).append(splitChar);
                msg.append(values[i].getContent()).append(splitChar);
                msg.append(values[i].getWarnLevel()).append(splitChar);
                msg.append(values[i].getExpiryDate()==null?"1970-01-01":DateUtil.date2String(values[i].getExpiryDate(), DateUtil.DATETIME_FORMAT_1)).append(splitChar);
                msg.append(values[i].getCreateDate()).append(splitChar);
                msg.append(DateUtil.date2String(values[i].getCreateTime(), DateUtil.DATETIME_FORMAT_1)).append(splitChar);
                msg.append(values[i].getDoneDate()==null?"1970-01-01":DateUtil.date2String(values[i].getDoneDate(), DateUtil.DATETIME_FORMAT_1)).append(splitChar);

                msg.append(values[i].getState()).append(splitChar);
                msg.append(values[i].getRemarks());
				if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
					LoggerUtils.addLog("AI.ZJ.ABG_MON_W_TRIGGER", msg.toString());
				} else {
					LoggerUtils.addLog("MYSQL.AI.ZJ.ABG_MON_W_TRIGGER", msg.toString());
				}
            }

        }
        else {
            throw new Exception();
        }
        //   BOABGMonWTriggerEngine.saveBatch(values);
    }

    /** 
     * 保存或修改警告记�?
     * @param value
     * @throws Exception
     */
    @Override
	public void saveOrUpdate(IBOABGMonWTriggerValue value) throws Exception
    {
        if(value.isNew()) {
            value.setTriggerId(BOABGMonWTriggerEngine.getNewId().longValue());
        }
        BOABGMonWTriggerEngine.save(value);
    }

    /** 
     * 删除警告记录
     * @param value
     * @throws Exception
     */
    @Override
	public void deleteWTrigger(IBOABGMonWTriggerValue value) throws Exception
    {
        value.delete();
        BOABGMonWTriggerEngine.save(value);
    }

    /** 
     * 批量删除警告记录
     * @param value
     * @throws Exception
     */
    @Override
	public void deleteWTrigger(IBOABGMonWTriggerValue[] values) throws Exception
    {
        for(int i = 0; i < values.length; i++) {
            values[i].delete();
        }
        BOABGMonWTriggerEngine.saveBatch(values);
    }

    @Override
	public int count(String condition, Map params) throws Exception
    {
        if(log.isDebugEnabled()) {
            log.debug("condition={" + condition + "}");
        }
        return BOABGMonWTriggerEngine.getBeansCount(condition, params);
    }

    /**
     * 通过ip，信息名称查询告警信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOABGMonWTriggerValue[] getTriggerValuesByIpInfoName(String ip, String infoName, String hostName,String beginDate,String endDate, int start, int end) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        //当ip不为空，主机名称为空的时候，用ip写到查询条件里
        if(StringUtils.isNotBlank(ip) && StringUtils.isBlank(hostName)) {
            condition.append(IBOABGMonWTriggerValue.S_Ip).append(" like :ip");
            parameter.put("ip", "%" + ip + "%");
        }

        //当ip为空，主机名称不为空的时候，通过主机名称查询到主机IP，并写到查询条件里
        if(StringUtils.isBlank(ip) && StringUtils.isNotBlank(hostName)) {
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] values = hostSv.getPhysicHostByName(hostName);
            if(values.length > 0) {
                condition.append(IBOABGMonWTriggerValue.S_Ip).append(" in (");
                for(int i = 0; i < values.length; i++) {
                    if(i == values.length - 1) {
                        condition.append("'" + values[i].getHostIp() + "'");
                    }
                    else {
                        condition.append("'" + values[i].getHostIp() + "',");
                    }
                }
                condition.append(")");
            }
        }
        //当主机ip和主机名称都不为空的时候，添加查询条件
        if(StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(hostName)) {
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] values = hostSv.getPhysicHostByName(hostName);
            if(values.length > 0) {
                condition.append("'" + ip + "'").append(" in (");
                for(int i = 0; i < values.length; i++) {
                    IBOAIMonPhysicHostValue value = values[i];
                    String hostIp = value.getHostIp();
                    if(i == values.length - 1) {
                        condition.append("'" + hostIp + "'");
                    }
                    else {
                        condition.append("'" + hostIp + "',");
                    }
                }
                condition.append(")");
                condition.append(" and " + IBOABGMonWTriggerValue.S_Ip).append("='" + ip + "'");
            }
        }

        if(StringUtils.isNotBlank(infoName)) {
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_InfoName).append(" like :infoName");
            parameter.put("infoName", "%" + infoName + "%");
        }
        if(StringUtils.isNotBlank(beginDate)&&StringUtils.isBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryBeginDate = temp.parse(beginDate);//将字符串转换成日期类型
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" > :beginDate ");
            parameter.put("beginDate", new Timestamp(qryBeginDate.getTime()));
        }
        if(StringUtils.isNotBlank(beginDate)&&StringUtils.isNotBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryBeginDate = temp.parse(beginDate);//将字符串转换成日期类型
            Date qryEndDate = temp.parse(endDate);
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" between :beginDate and :endDate ");
            parameter.put("beginDate", new Timestamp(qryBeginDate.getTime()));
            parameter.put("endDate", new Timestamp(qryEndDate.getTime()));
        }
        if(StringUtils.isBlank(beginDate)&&StringUtils.isNotBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryEndDate = temp.parse(endDate);
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" < :endDate ");
            parameter.put("endDate", new Timestamp(qryEndDate.getTime()));
        }
        condition.append(" order by " + IBOABGMonWTriggerValue.S_CreateTime + " desc");
        return BOABGMonWTriggerEngine.getBeans(null, condition.toString(), parameter, start, end, false);
    }

    /**
     * 查询结果行数
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public int getCntByCond(String ip, String infoName, String hostName,String beginDate,String endDate) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        //当ip不为空，主机名称为空的时候，用ip写到查询条件里
        if(StringUtils.isNotBlank(ip) && StringUtils.isBlank(hostName)) {
            condition.append(IBOABGMonWTriggerValue.S_Ip).append(" like :ip");
            parameter.put("ip", "%" + ip + "%");
        }

        //当ip为空，主机名称不为空的时候，通过主机名称查询到主机IP，并写到查询条件里
        if(StringUtils.isBlank(ip) && StringUtils.isNotBlank(hostName)) {
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] values = hostSv.getPhysicHostByName(hostName);
            if(values.length > 0) {
                condition.append(IBOABGMonWTriggerValue.S_Ip).append(" in (");
                for(int i = 0; i < values.length; i++) {
                    if(i == values.length - 1) {
                        condition.append("'" + values[i].getHostIp() + "'");
                    }
                    else {
                        condition.append("'" + values[i].getHostIp() + "',");
                    }
                }
                condition.append(")");
            }
        }
        //当主机ip和主机名称都不为空的时候，添加查询条件
        if(StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(hostName)) {
            IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] values = hostSv.getPhysicHostByName(hostName);
            if(values.length > 0) {
                condition.append("'" + ip + "'").append(" in (");
                for(int i = 0; i < values.length; i++) {
                    IBOAIMonPhysicHostValue value = values[i];
                    String hostIp = value.getHostIp();
                    if(i == values.length - 1) {
                        condition.append("'" + hostIp + "'");
                    }
                    else {
                        condition.append("'" + hostIp + "',");
                    }
                }
                condition.append(")");
                condition.append(" and " + IBOABGMonWTriggerValue.S_Ip).append("='" + ip + "'");
            }
        }

        if(StringUtils.isNotBlank(infoName)) {
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_InfoName).append(" like :infoName");
            parameter.put("infoName", "%" + infoName + "%");
        }
        if(StringUtils.isNotBlank(beginDate)&&StringUtils.isBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryBeginDate = temp.parse(beginDate);//将字符串转换成日期类型
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" > :beginDate ");
            parameter.put("beginDate", new Timestamp(qryBeginDate.getTime()));
        }
        if(StringUtils.isNotBlank(beginDate)&&StringUtils.isNotBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryBeginDate = temp.parse(beginDate);//将字符串转换成日期类型
            Date qryEndDate = temp.parse(endDate);
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" between :beginDate and :endDate ");
            parameter.put("beginDate", new Timestamp(qryBeginDate.getTime()));
            parameter.put("endDate", new Timestamp(qryEndDate.getTime()));
        }
        if(StringUtils.isBlank(beginDate)&&StringUtils.isNotBlank(endDate)) {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date qryEndDate = temp.parse(endDate);
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOABGMonWTriggerValue.S_CreateTime).append(" < :endDate ");
            parameter.put("endDate", new Timestamp(qryEndDate.getTime()));
        }
        return BOABGMonWTriggerEngine.getBeansCount(condition.toString(), parameter);
    }

}
