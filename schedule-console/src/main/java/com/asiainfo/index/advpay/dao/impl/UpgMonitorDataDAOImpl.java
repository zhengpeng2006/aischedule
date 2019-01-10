package com.asiainfo.index.advpay.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;




import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.index.advpay.bo.BOUpgMonitorDataBean;
import com.asiainfo.index.advpay.bo.BOUpgMonitorDataEngine;
import com.asiainfo.index.advpay.dao.interfaces.IUpgMonitorDataDAO;
import com.asiainfo.index.advpay.ivalues.IBOUpgMonitorDataValue;
import com.asiainfo.index.util.IndexConstants;
import com.asiainfo.index.util.IndexUtil;
import com.asiainfo.logger.common.LoggerDateUtils;

public class UpgMonitorDataDAOImpl implements IUpgMonitorDataDAO{
	
	/* 数据表名 **/
	private static final String TABLE_NAME_PRE = "UPG_MONITOR_DATA_";
	
	private static final Logger LOGGER = Logger.getLogger(UpgMonitorDataDAOImpl.class);
	
	@Override
	public BOUpgMonitorDataBean getBeanById(int id) throws Exception {
		StringBuilder querySb = new StringBuilder();
		Map map = new HashMap();
		querySb.append(IBOUpgMonitorDataValue.S_SeqId).append("= :seqId ");
		map.put("seqId", new Long(id));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		map.put(IBOUpgMonitorDataValue.S_CreateDate, new Date());
		return BOUpgMonitorDataEngine.getBeans(querySb.toString(), map)[0];
	}

	@Override
	public IBOUpgMonitorDataValue[] getHisData(Map<String, Object> conditions,
			List<String> list, String startTime, String endTime)
			throws Exception {
		//若未传入 则新建一个
		if (conditions == null){
			conditions = new HashMap<String, Object>();
		}
		//账期格式
		SimpleDateFormat BillingCycleSdf = new SimpleDateFormat("yyyyMM");

		//根据list获取sql前半段
		String pre = IndexUtil.getPreSqlFromList(list);
		
		//根据startTime,endTime确定查询那些分表
		SimpleDateFormat sdf = new SimpleDateFormat(IndexConstants.DATE_DATE_FORMAT); 
		Date start = sdf.parse(startTime);
		Date end = sdf.parse(endTime);
		Date[] billingCycleDates = LoggerDateUtils.dividedByMonth(start,end);
		
		//根据分表和conditions拼接最终sql(记得加上开始结束时间的限制条件,字段为create_date)
		StringBuffer sqlStr = new StringBuffer();
		 if(null == billingCycleDates || billingCycleDates.length == 0) {
	            LOGGER.error("分表解析失败");
	            return null;
	      }
		 else if(billingCycleDates.length == 1)//若果在同一张表内 则开始时间结束时间都在此拼接
         {
			 //获取维度过滤sql
			 String conditionSql = IndexUtil.getConditionSqlFromList(conditions);
			 sqlStr.append(pre).append("FROM ")
			 		.append(TABLE_NAME_PRE).append(BillingCycleSdf.format(billingCycleDates[0]))//拼接到分表名
			 		.append(" WHERE 1=1 ")
			 		.append(conditionSql)//拼接到维度过滤
			 		.append(" AND CREATE_DATE  >= :startTime")
			 		.append(" AND CREATE_DATE  <= :endTime")
			 		.append(" GROUP BY ").append(list.get(0))//按分钟分组统计
			 		.append(" ORDER BY ").append(list.get(0));//按分钟排序
			 conditions.put("startTime", new Timestamp(start.getTime()));
			 conditions.put("endTime", new Timestamp(end.getTime()));
         }
	     else {//多表关联查询 
	    	 	//获取维度过滤sql
	    	 	String conditionSql = IndexUtil.getConditionSqlFromList(conditions);
	            for(int i = 0; i < billingCycleDates.length; i++) {
	            	sqlStr.append(pre).append("FROM ")
            		.append(TABLE_NAME_PRE).append(BillingCycleSdf.format(billingCycleDates[i]))//拼接表名
            		.append(" WHERE 1=1")//拼接通用过滤字段
	            	.append(conditionSql);
	            	if (i == 0){//开始时间在第一张表
	            		sqlStr.append(" AND CREATE_DATE  >= :startTime")//大雨开始时间
				 		.append(" GROUP BY ").append(list.get(0))//按分钟分组统计
				 		.append(" UNION ALL ");
	            		conditions.put("startTime", new Timestamp(start.getTime()));
	            	}else if(i == (billingCycleDates.length - 1)){//结束时间在最后一张表拼接
	            		sqlStr.append(" AND CREATE_DATE  <= :endTime")
	            		.append(" GROUP BY ").append(list.get(0))//按分钟分组统计
				 		.append(" ORDER BY ").append(list.get(0));//按分钟排序
	            		conditions.put("endTime", new Timestamp(end.getTime()));
	                }else{//中间表就没有新加的过滤条件
	                	sqlStr.append(" UNION ALL ");
	                }
	            }
	     }
		return BOUpgMonitorDataEngine.getBeansFromSql(sqlStr.toString(), conditions);
	}

	@Override
	public IBOUpgMonitorDataValue[] getCurData(Map<String, Object> conditions,
			List<String> list, long seqId) throws Exception {
		//根据list获取sql前半段
		String pre = IndexUtil.getPreSqlFromList(list);
		
		//获取当前账期用来拼分表名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String billingCycleDate = sdf.format(new Date());
		
		//根据分表和conditions,seqId拼接过滤条件(大于传入seqId以保证所取数据是未读取过的)
		StringBuffer sql = new StringBuffer();
		sql.append(pre).append("FROM ")
		.append(TABLE_NAME_PRE).append(billingCycleDate)//拼接到分表名
		.append(" WHERE 1=1")
		.append(IndexUtil.getConditionSqlFromList(conditions))//拼接维度过滤条件
		.append(" AND SEQ_ID > ").append(seqId)//大于上次最大序列以保证取得数据为新数据
		.append(" GROUP BY ").append(list.get(0))//按分钟分组统计
		.append(" ORDER BY ").append(list.get(0));//按分钟排序
		return BOUpgMonitorDataEngine.getBeansFromSql(sql.toString(), conditions);
	}

}
