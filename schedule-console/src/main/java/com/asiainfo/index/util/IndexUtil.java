package com.asiainfo.index.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ai.appframe2.common.DataType;
import com.ai.common.bo.BOBsStaticDataBean;
import com.asiainfo.index.advpay.bo.BOUpgMonitorDataBean;
import com.asiainfo.index.base.bo.BOBsMonitorBean;

public class IndexUtil {
	
	private static final Logger LOGGER = Logger.getLogger(IndexUtil.class);
	
	public static Map<String, List<String[]>> getMapFromData(BOBsStaticDataBean[] datas) {
		 Map<String, List<String[]>> result= new HashMap<String, List<String[]>>();
		 if (datas != null || datas.length > 0){
			 for (BOBsStaticDataBean data : datas) {
				 if (result.containsKey(data.getCodeDesc())){
					 List<String[]> list = result.get(data.getCodeDesc());
					 String[] strs = new String[2];
					 strs[0] = data.getCodeValue();
					 strs[1] = data.getCodeTypeAlias();
					 list.add(strs);
					 result.put(data.getCodeDesc(), list);
				 }else{
					 List<String[]> list = new ArrayList<String[]>();
					 String[] strs = new String[2];
					 strs[0] = data.getCodeValue();
					 strs[1] = data.getCodeTypeAlias();
					 list.add(strs);
					 result.put(data.getCodeDesc(), list);
				 }
			}
		 }
		return result;
	}

	public static Map<Integer, String> getMapFromMonitor(
			BOBsMonitorBean[] monitors) {
		Map<Integer, String> result = new HashMap<Integer, String>();
		if (monitors != null && monitors.length > 0){
			for (BOBsMonitorBean monitor : monitors) {
				result.put(monitor.getMonitorId(), monitor.getMonitorName());
			}
		}
		return result;
	}
	
	/**
	 * 根据取值list拼接select 到from这段
	 * @param list
	 * @return
	 */
	public static String getPreSqlFromList(List<String> list){
		StringBuffer sql = new StringBuffer();
		if (list != null){
			sql.append("SELECT MAX(SEQ_ID) SEQ_ID,");
			if (list.size() == 1){
				sql.append("MAX(").append(list.get(0)).append(") ").append(list.get(0)).append(" ");
			}
			else{
				for (int i = 0; i < list.size(); i++) {
					if (i == 0){//按约定 第一个为分钟维度
						sql.append("MAX(").append(list.get(i)).append(") ").append(list.get(i)).append(",");
					}else if (i == (list.size() - 1)){//最后一个不加逗号 改为空格和后面区分
						sql.append("SUM(").append(list.get(i)).append(") ").append(list.get(i)).append(" ");
					}else{
						sql.append("SUM(").append(list.get(i)).append(") ").append(list.get(i)).append(",");
					}
				}
			}
		}
		return sql.toString();
	}
	
	/**
	 * 根据维度形成过滤条件
	 * @param conditions
	 * @return
	 */
	public static String getConditionSqlFromList(Map<String, Object> conditions){
		StringBuffer sql = new StringBuffer();
		if (conditions != null){
			Set<Entry<String, Object>> set = conditions.entrySet();
			for (Entry<String, Object> entry : set) {
				sql.append(" AND ").append(entry.getKey()).append(" = :").append(entry.getKey());
			}
		}
		return sql.toString();
	}
	
	/**
	 * 从指标数据中读出对应属性名的数据
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static String getProperty(BOUpgMonitorDataBean bean,String propertyName) throws Exception{
		String result = "";
		if (propertyName != null){
			propertyName = propertyName.toUpperCase();
			result = DataType.getAsString(bean.get(propertyName));
		}
		return result;
	}
	
	/**
	 * 将原始数据转换为X轴的时分信息
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static  String getXFromData(String data) throws Exception{
		SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyyMMddHHmm");
		SimpleDateFormat showFormat = new SimpleDateFormat("HH:mm");
		String result = null;
		try {
			result = showFormat.format(dataFormat.parse(data));
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception("日期数据不符合格式要求(yyyyMMddHHmm)，为"+data);
		}
		return result;
	}

    /**
     * 将原始数据转换为X轴的月日时分信息
     * @param data
     * @return
     * @throws Exception
     */
	public static  String getHisXFromData(String data) throws Exception{
        SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyyMMddHHmm");
        SimpleDateFormat showFormat = new SimpleDateFormat("MM-dd HH:mm");
        String result = null;
        try {
            result = showFormat.format(dataFormat.parse(data));
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception("日期数据不符合格式要求(yyyyMMddHHmm)，为"+data);
        }
        return result;
    }
	
}
