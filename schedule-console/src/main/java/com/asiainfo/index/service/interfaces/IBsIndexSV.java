package com.asiainfo.index.service.interfaces;

import java.util.List;
import java.util.Map;

import com.asiainfo.index.advpay.ivalues.IBOUpgMonitorDataValue;
import com.asiainfo.index.base.ivalues.IBOBsMonitorCfgValue;

public interface IBsIndexSV {
		/**
		 * 取所有指标的枚举值
		 * @return Map<String,String[]>  Key为index_code;value中是为长度为2的数组
		 * 	第一个是指标枚举值，第二个是显示名
		 * @throws Exception
		 */
		Map<String,List<String[]>> getAllEnmuValue() throws Exception;
		
		
		/**
		 * 获取所有需要监控的指标
		 * @return
		 * @throws Exception
		 */
		Map<Integer,String> getAllMonitors() throws Exception;
		
		
		/**
		 * @param monitorId 监控ID
		 * @param indexKind 指标类型（维度，标量）
		 * @return
		 * @throws Exception
		 */
	    IBOBsMonitorCfgValue[] getConditions(int monitorId, String indexKind) throws Exception;
		
		/**
		 * 获取所有指标维度的对应显示名
		 * @return  Map<Integer,String> key为指标编号，value为显示名
		 * @throws Exception
		 */
		Map<Integer,String> getAllIndexName() throws Exception;
		
		/**
		 * 获取数据
		 * @param conditions 维度过滤条件
		 * @param list           需要查值的字段名（第一个为分钟维度的字段名）
		 * @param startTime 开始时间（查询历史时有值）
		 * @param endTime   结束时间（查询历史时有值)
		 * @param seqId      上批次的最大序列号（实时查询时大于0，历史查询时小于0）
		 * @return
		 * @throws Exception
		 */
		IBOUpgMonitorDataValue[] getData(Map<String, Object> conditions, List<String> list, String startTime, String endTime, long seqId) throws Exception;
}
