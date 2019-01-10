package com.asiainfo.emend_yh.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.emend_yh.bo.BOAmSchedPsMonEngine;
import com.asiainfo.emend_yh.bo.BOAmTaskRelyEngine;
import com.asiainfo.emend_yh.dao.interfaces.IEmendYhCommonDAO;
import com.asiainfo.emend_yh.ivalues.IBOAmSchedPsMonValue;
import com.asiainfo.emend_yh.ivalues.IBOAmTaskRelyValue;
import com.asiainfo.emend_yh.service.interfaces.IEmendYhCommonSV;
import com.mysql.jdbc.Connection;

public class EmendYhCommonDAOImpl implements IEmendYhCommonDAO {

	@Override
	public IBOAmTaskRelyValue[] qryAllRely() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" 1=1 ");
		IBOAmTaskRelyValue[] retValues = BOAmTaskRelyEngine.getBeans(sb.toString(), null);
		return retValues;
	}

	@Override
	public IBOAmTaskRelyValue[] qryRely(String taskGroup) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" 1=1 ");
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotBlank(taskGroup)) {
			sb.append(" AND ").append(IBOAmTaskRelyValue.S_TaskGroup).append(" =:taskGroup ");
			map.put("taskGroup", taskGroup);
		}
		IBOAmTaskRelyValue[] retValues = BOAmTaskRelyEngine.getBeans(sb.toString(), map);
		return retValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IBOAmSchedPsMonValue[] qryPsMonByCondition(String taskCode, String regionId, long bsBillingCycle) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" 1=1 ");
		Map map = new HashMap();
		if (StringUtils.isNotBlank(taskCode)) {
			sb.append(" AND ").append(IBOAmSchedPsMonValue.S_TaskCode).append(" =:taskCode ");
			map.put("taskCode", taskCode);
		}
		if (StringUtils.isNotBlank(String.valueOf(regionId))) {
			sb.append(" AND ").append(IBOAmSchedPsMonValue.S_RegionId).append(" =:regionId ");
			map.put("regionId", regionId);
		}
		if (StringUtils.isNotBlank(String.valueOf(bsBillingCycle))) {
			sb.append(" AND ").append(IBOAmSchedPsMonValue.S_BsBillingCycle).append(" =:bsBillingCycle ");
			map.put("bsBillingCycle", bsBillingCycle);
		}
		IBOAmSchedPsMonValue[] psValues = BOAmSchedPsMonEngine.getBeans(sb.toString(), map);
		return psValues;
	}

	@Override
	public List<String> qryGroupInfo() throws Exception {
		StringBuilder sql = new StringBuilder(100);
		List<String> list=new ArrayList<String>();
		Map<String, String> parameter = new HashMap<String, String>();
		sql.append("select distinct(task_group) taskGroup from am_task_rely");

		java.sql.Connection conn = null;
		ResultSet resultset = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			resultset = ServiceManager.getDataStore().retrieve(conn, sql.toString(), parameter);
			while (resultset.next()) {
				  list.add(resultset.getString("taskGroup"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (resultset != null) {
                resultset.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
		return list;
	}
	public static void main(String[] args) throws Exception {
		IEmendYhCommonSV sv=(IEmendYhCommonSV)ServiceFactory.getService(IEmendYhCommonSV.class);
		List<String> list=sv.qryGroupInfo();
		System.out.println(list);
	}

}
