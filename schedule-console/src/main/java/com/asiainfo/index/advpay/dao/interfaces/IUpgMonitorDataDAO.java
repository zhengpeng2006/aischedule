package com.asiainfo.index.advpay.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.asiainfo.index.advpay.bo.BOUpgMonitorDataBean;
import com.asiainfo.index.advpay.ivalues.IBOUpgMonitorDataValue;

public interface IUpgMonitorDataDAO {
	
	BOUpgMonitorDataBean getBeanById(int id) throws Exception;
	
	
	/**
	 * 获取历史数据
	 * @param conditions 维度过滤条件
	 * @param list           需要查值的字段名（第一个为分钟维度的字段名）
	 * @param startTime 开始时间（查询历史时有值）
	 * @param endTime   结束时间（查询历史时有值)
	 * @return
	 * @throws Exception
	 */
	IBOUpgMonitorDataValue[] getHisData(Map<String, Object> conditions, List<String> list,
                                        String startTime, String endTime) throws Exception;
	
	/**
	 * 获取实时数据
	 * @param conditions 维度过滤条件
	 * @param list           需要查值的字段名（第一个为分钟维度的字段名）
	 * @param seqId      上批次的最大序列号（实时查询时大于0，历史查询时小于0）
	 * @return
	 * @throws Exception
	 */
	IBOUpgMonitorDataValue[] getCurData(Map<String, Object> conditions, List<String> list, long seqId) throws Exception;
}
