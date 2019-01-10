package com.asiainfo.common.abo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.common.abo.bo.BOABGMonLRecordBean;
import com.asiainfo.common.abo.bo.BOABGMonLRecordEngine;
import com.asiainfo.common.abo.dao.interfaces.IABGMonLRecordDAO;
import com.asiainfo.common.abo.ivalues.IBOABGMonLRecordValue;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonLRecordEngine;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TimeUtil;

public class ABGMonLRecordDAOImpl implements IABGMonLRecordDAO
{
    private transient static Log log = LogFactory.getLog(ABGMonLRecordDAOImpl.class);

    public long getBeanId() throws Exception
    {
        return BOABGMonLRecordEngine.getNewId().longValue();
    }

    public BOABGMonLRecordBean getBeanById(long id) throws Exception
    {
        return BOABGMonLRecordEngine.getBean(id);
    }

    public void save(BOABGMonLRecordBean value) throws Exception
    {
        if(value.isNew() && value.getRecordId() <= 0)
            value.setRecordId(getBeanId());
        BOABGMonLRecordEngine.save(value);
    }

    /** 
    * 查询
    * @param condition
    * @param parameter
    * @return
    * @throws Exception
    */
    public IBOABGMonLRecordValue[] query(String condition, Map parameter) throws Exception
    {
        return BOABGMonLRecordEngine.getBeans(condition, parameter);
    }

    /** 
     * 根据任务标识、开始日期，结束日期读取监控结果
     * @param infoId
     * @param layer
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public IBOABGMonLRecordValue[] getMonLRecord(long infoId, String layer, String beginDate, String endDate) throws Exception
    {
        IBOABGMonLRecordValue[] result = null;
        try {
            StringBuilder sb = new StringBuilder("");
            sb.append(IBOABGMonLRecordValue.S_InfoId + "= :infoId ");
            sb.append(" and ");
            sb.append(IBOABGMonLRecordValue.S_CreateDate + " >= to_date( :beginDate ,'yyyy-mm-dd hh24:mi:ss')");
            sb.append(" and ");
            sb.append(IBOABGMonLRecordValue.S_CreateDate + " <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')");
            sb.append(" and ");
            sb.append(IBOABGMonLRecordValue.S_Layer + "= :layer ");
            sb.append(" ORDER BY " + IBOABGMonLRecordValue.S_CreateDate);
            HashMap parameter = new HashMap();
            parameter.put("infoId", infoId);
            parameter.put("beginDate", beginDate);
            parameter.put("endDate", endDate);
            parameter.put("layer", layer);
            result = this.query(sb.toString(), parameter);
        }
        catch(Exception e) {
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000149") + e.getMessage());
        }
        return result;
    }

    /** 
     * 根据任务标识、层、读取最新监控结�?
     * @param infoId
     * @param layer
     * @return
     * @throws Exception
     */
    public IBOABGMonLRecordValue getLastMonLRecordById(long infoId, String layer) throws Exception
    {
        IBOABGMonLRecordValue[] result = null;
        try {
            int yyyymm = TimeUtil.getYYYYMM(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ai_mon_l_record_");
            sb.append(layer).append("_").append(yyyymm).append(" where ");
            sb.append(IBOABGMonLRecordValue.S_InfoId + "= :infoId ");
            sb.append(" order by " + IBOABGMonLRecordValue.S_CreateDate);
            HashMap parameter = new HashMap();
            parameter.put("infoId", infoId);
            result = BOABGMonLRecordEngine.getBeansFromSql(sb.toString(), parameter);
        }
        catch(Exception e) {
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000149") + e.getMessage());
        }
        if(result != null && result.length > 0) {
            return result[0];
        }
        return null;
    }

    /** 
     * 根据任务标识、层、时间间隔t（单位小时）获取昨天和今天从当前时间�?��推t小时内的两条数据 不支持跨�?
     * @param infoId
     * @param layer
     * @param durHour
     * @return
     * @throws Exception
     */
    public IBOABGMonLRecordValue[] getMonLRecord(long infoId, String layer, int durHour) throws Exception
    {
        IBOABGMonLRecordValue[] result = null;
        try {
            int yyyymm = TimeUtil.getYYYYMM(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ai_mon_l_record_");
            sb.append(layer).append("_").append(yyyymm).append(" where ");
            sb.append(IBOABGMonLRecordValue.S_InfoId + "= :infoId ");
            sb.append(" and ");
            if(durHour == 24) {
                sb.append("(");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " >= trunc(sysdate) ");
                sb.append(" or ");
                sb.append("(");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " >= trunc(sysdate) - 1");
                sb.append(" and ");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " <= trunc(sysdate) -1/86400 ");
                sb.append("))");
            }
            else {
                sb.append("((");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " >= (sysdate-:durHour /24)");
                sb.append(" and ");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " <= sysdate ");
                sb.append(")");
                sb.append(" or ");
                sb.append("(");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " >= (sysdate-:durHour /24)-1");
                sb.append(" and ");
                sb.append(IBOABGMonLRecordValue.S_CreateDate + " <= sysdate-1 ");
                sb.append("))");
            }
            sb.append(" and ");
            sb.append(IBOABGMonLRecordValue.S_Layer + "= :layer ");
            sb.append(" ORDER BY " + IBOABGMonLRecordValue.S_CreateDate);
            HashMap parameter = new HashMap();
            parameter.put("infoId", infoId);
            if(durHour != 24)
                parameter.put("durHour", durHour);
            parameter.put("layer", layer);
            result = BOABGMonLRecordEngine.getBeansFromSql(sb.toString(), parameter);
        }
        catch(Exception e) {
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000149") + e.getMessage());
        }
        return result;
    }

    /** 
     * 获得监控图形数据
     * @param infoId long[]
     * @param transformClass String
     * @param startDate Date
     * @param endDate Date
     * @return HashMap
     * @throws Exception
     */
    public IBOABGMonLRecordValue[] getMonLRecordImage(Object[] infoIds, String layer, String viewTpyeId, Date startDate, Date endDate)
            throws Exception
    {
        int[] yyyymm = TimeUtil.createYYYYMM(startDate, endDate);
        List list = new ArrayList();
        for(int i = 0; i < infoIds.length; i++) {
            list.add(String.valueOf(infoIds[i]));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select info_name,info_value,create_date from (");
        for(int i = 0; i < yyyymm.length; i++) {
            if(i != 0) {
                sb.append(" union all ");
            }
            sb.append(" select RECORD_ID,INFO_ID,HOST_NAME,IP,MON_TYPE,INFO_NAME,GROUP_ID,IS_TRIGGER_WARN,INFO_VALUE,CREATE_DATE,DONE_DATE,WARN_LEVEL,INFO_CODE ");
            sb.append("from ai_mon_l_record_");
            if(StringUtils.isNotBlank(layer))
                sb.append(layer);
            sb.append("_").append(yyyymm[i]).append(" where info_id in ( " + StringUtils.join(list.iterator(), ","));
            sb.append(") and create_date >= to_date( :beginDate ,'yyyy-mm-dd hh24:mi:ss')");
            sb.append(" and create_date <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')");
            if(StringUtils.isNotBlank(viewTpyeId)) {
                if(viewTpyeId.equals("1")) {
                    sb.append(" and warn_level!=0");
                }
                else {
                    sb.append(" and warn_level=0");
                }
            }
        }
        sb.append(" )  order by create_date asc ");
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap parameter = new HashMap();
        parameter.put("beginDate", sdformat.format(startDate));
        parameter.put("endDate", sdformat.format(endDate));
        if(log.isDebugEnabled()) {
            log.debug("getMonLRecord SQL:" + sb.toString());
        }
        IBOABGMonLRecordValue[] values = BOABGMonLRecordEngine.getBeansFromSql(sb.toString(), parameter);
        if(values == null || values.length < 1)
            return null;
        else
            return values;
    }

    /** 
     * 获得监控表格数据
     * @param layer
     * @param infocode
     * @param startDate Date
     * @param endDate Date
     * @return IBOABGMonLRecordValue
     * @throws Exception
     */
    public IBOABGMonLRecordValue[] getMonLRecordByCode(String layer, String infocode) throws Exception
    {
        int yyyymm = TimeUtil.getYYYYMM(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("select info_name,info_value,create_date from ai_mon_l_record_");
        sb.append(layer).append("_").append(yyyymm).append(" where ").append(IBOABGMonLRecordValue.S_InfoCode + "= :infoCode ");
        sb.append(" and batch_id = (select max(batch_id) from ai_mon_l_record_");
        sb.append(layer).append("_").append(yyyymm).append(" where ").append(IBOABGMonLRecordValue.S_InfoCode + "= :infoCode ").append(")");
        HashMap parameter = new HashMap();
        parameter.put("infoCode", infocode);
        if(log.isDebugEnabled()) {
            log.debug("getMonLRecord SQL:" + sb.toString());
        }
        return BOABGMonLRecordEngine.getBeansFromSql(sb.toString(), parameter);
    }

    /** 
     * 批量保存或修改监控结�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonLRecordValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew() && values[i].getRecordId() <= 0) {
                    values[i].setRecordId(BOABGMonLRecordEngine.getNewId().longValue());
                }
            }
        }
        else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOABGMonLRecordEngine.saveBatch(values);
    }

    /** 
     * 获取Batch_Id
     * @return
     * @throws Exception
     */
    public long getBatchId() throws Exception
    {
        long result = ServiceManager.getIdGenerator().getNewId("BATCH_ID").longValue();
        return result;
    }

    /** 
     * 保存或修改监控结�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOABGMonLRecordValue value) throws Exception
    {
        if(value.isNew()) {
            value.setRecordId(BOAIMonLRecordEngine.getNewId().longValue());
        }
        BOABGMonLRecordEngine.save(value);
    }



}
