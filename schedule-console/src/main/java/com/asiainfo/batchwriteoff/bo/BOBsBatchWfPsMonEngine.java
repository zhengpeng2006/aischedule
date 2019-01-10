package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsMonValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class BOBsBatchWfPsMonEngine {

	public static BOBsBatchWfPsMonBean[] getBeans(DataContainerInterface dc) throws Exception {
		Map ps = dc.getProperties();
		StringBuffer buffer = new StringBuffer();
		Map pList = new HashMap();
		for (java.util.Iterator cc = ps.entrySet().iterator(); cc.hasNext();) {
			Map.Entry e = (Map.Entry) cc.next();
			if (buffer.length() > 0)
				buffer.append(" and ");
			buffer.append(e.getKey().toString() + " = :p_" + e.getKey().toString());
			pList.put("p_" + e.getKey().toString(), e.getValue());
		}
		Connection conn = ServiceManager.getSession().getConnection();
		try {
			return getBeans(buffer.toString(), pList);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static BOBsBatchWfPsMonBean getBean(String _RegionId, String _WfProcessId, int _BillingCycleId, int _BillDay)
			throws Exception {
		/**new create*/
		String condition = "REGION_ID = :S_REGION_ID and WF_PROCESS_ID = :S_WF_PROCESS_ID and BILLING_CYCLE_ID = :S_BILLING_CYCLE_ID and BILL_DAY = :S_BILL_DAY";
		Map map = new HashMap();
		map.put("S_REGION_ID", _RegionId);
		map.put("S_WF_PROCESS_ID", _WfProcessId);
		map.put("S_BILLING_CYCLE_ID", new Integer(_BillingCycleId));
		map.put("S_BILL_DAY", new Integer(_BillDay));
		;
		BOBsBatchWfPsMonBean[] beans = getBeans(condition, map);
		if (beans != null && beans.length == 1)
			return beans[0];
		else if (beans != null && beans.length > 1) {
			// [错误]根据主键查询出现一条以上记录
			throw new Exception("[ERROR]More datas than one queryed by PK");
		} else {
			BOBsBatchWfPsMonBean bean = new BOBsBatchWfPsMonBean();
			bean.setRegionId(_RegionId);
			bean.setWfProcessId(_WfProcessId);
			bean.setBillingCycleId(_BillingCycleId);
			bean.setBillDay(_BillDay);
			return bean;
		}
	}

	public static BOBsBatchWfPsMonBean getBean(int _BillingCycleId, int _BillDay, String _RegionId, String _WfProcessId)
			throws Exception {
		/**new create*/
		String condition = "BILLING_CYCLE_ID = :S_BILLING_CYCLE_ID and BILL_DAY = :S_BILL_DAY and REGION_ID = :S_REGION_ID and WF_PROCESS_ID = :S_WF_PROCESS_ID";
		Map map = new HashMap();
		map.put("S_BILLING_CYCLE_ID", new Integer(_BillingCycleId));
		map.put("S_BILL_DAY", new Integer(_BillDay));
		map.put("S_REGION_ID", _RegionId);
		map.put("S_WF_PROCESS_ID", _WfProcessId);
		;
		BOBsBatchWfPsMonBean[] beans = getBeans(condition, map);
		if (beans != null && beans.length == 1)
			return beans[0];
		else if (beans != null && beans.length > 1) {
			// [错误]根据主键查询出现一条以上记录
			throw new Exception("[ERROR]More datas than one queryed by PK");
		} else {
			BOBsBatchWfPsMonBean bean = new BOBsBatchWfPsMonBean();
			bean.setBillingCycleId(_BillingCycleId);
			bean.setBillDay(_BillDay);
			bean.setRegionId(_RegionId);
			bean.setWfProcessId(_WfProcessId);
			return bean;
		}
	}

	public static BOBsBatchWfPsMonBean[] getBeans(Criteria sql) throws Exception {
		return getBeans(sql, -1, -1, false);
	}

	public static BOBsBatchWfPsMonBean[] getBeans(Criteria sql, int startNum, int endNum, boolean isShowFK)
			throws Exception {
		String[] cols = null;
		String condition = "";
		Map param = null;
		if (sql != null) {
			cols = (String[]) sql.getSelectColumns().toArray(new String[0]);
			condition = sql.toString();
			param = sql.getParameters();
		}
		return (BOBsBatchWfPsMonBean[]) getBeans(cols, condition, param, startNum, endNum, isShowFK);
	}

	public static BOBsBatchWfPsMonBean[] getBeans(String condition, Map parameter) throws Exception {
		return getBeans(null, condition, parameter, -1, -1, false);
	}

	public static BOBsBatchWfPsMonBean[] getBeans(String[] cols, String condition, Map parameter, int startNum,
			int endNum, boolean isShowFK) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			return (BOBsBatchWfPsMonBean[]) ServiceManager.getDataStore().retrieve(conn, BOBsBatchWfPsMonBean.class,
					BOBsBatchWfPsMonBean.getObjectTypeStatic(), cols, condition, parameter, startNum, endNum, isShowFK,
					false, null);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static BOBsBatchWfPsMonBean[] getBeans(String[] cols, String condition, Map parameter, int startNum,
			int endNum, boolean isShowFK, String[] extendBOAttrs) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			return (BOBsBatchWfPsMonBean[]) ServiceManager.getDataStore().retrieve(conn, BOBsBatchWfPsMonBean.class,
					BOBsBatchWfPsMonBean.getObjectTypeStatic(), cols, condition, parameter, startNum, endNum, isShowFK,
					false, extendBOAttrs);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static int getBeansCount(String condition, Map parameter) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			return ServiceManager.getDataStore().retrieveCount(conn, BOBsBatchWfPsMonBean.getObjectTypeStatic(),
					condition, parameter, null);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static int getBeansCount(String condition, Map parameter, String[] extendBOAttrs) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			return ServiceManager.getDataStore().retrieveCount(conn, BOBsBatchWfPsMonBean.getObjectTypeStatic(),
					condition, parameter, extendBOAttrs);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static void save(BOBsBatchWfPsMonBean aBean) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			ServiceManager.getDataStore().save(conn, aBean);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static void save(BOBsBatchWfPsMonBean[] aBeans) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			ServiceManager.getDataStore().save(conn, aBeans);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static void saveBatch(BOBsBatchWfPsMonBean[] aBeans) throws Exception {
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			ServiceManager.getDataStore().saveBatch(conn, aBeans);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static BOBsBatchWfPsMonBean[] getBeansFromQueryBO(String soureBO, Map parameter) throws Exception {
		Connection conn = null;
		ResultSet resultset = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
			resultset = ServiceManager.getDataStore().retrieve(conn, sql, parameter);
			return (BOBsBatchWfPsMonBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(
					BOBsBatchWfPsMonBean.class, BOBsBatchWfPsMonBean.getObjectTypeStatic(), resultset, null, true);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static BOBsBatchWfPsMonBean[] getBeansFromSql(String sql, Map parameter) throws Exception {
		Connection conn = null;
		ResultSet resultset = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			resultset = ServiceManager.getDataStore().retrieve(conn, sql, parameter);
			return (BOBsBatchWfPsMonBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(
					BOBsBatchWfPsMonBean.class, BOBsBatchWfPsMonBean.getObjectTypeStatic(), resultset, null, true);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static java.math.BigDecimal getNewId() throws Exception {
		return ServiceManager.getIdGenerator().getNewId(BOBsBatchWfPsMonBean.getObjectTypeStatic());
	}

	public static java.sql.Timestamp getSysDate() throws Exception {
		return ServiceManager.getIdGenerator().getSysDate(BOBsBatchWfPsMonBean.getObjectTypeStatic());
	}

	public static BOBsBatchWfPsMonBean wrap(DataContainerInterface source, Map colMatch, boolean canModify) {
		try {
			return (BOBsBatchWfPsMonBean) DataContainerFactory.wrap(source, BOBsBatchWfPsMonBean.class, colMatch,
					canModify);
		} catch (Exception e) {
			if (e.getCause() != null)
				throw new RuntimeException(e.getCause());
			else
				throw new RuntimeException(e);
		}
	}

	public static BOBsBatchWfPsMonBean copy(DataContainerInterface source, Map colMatch, boolean canModify) {
		try {
			BOBsBatchWfPsMonBean result = new BOBsBatchWfPsMonBean();
			DataContainerFactory.copy(source, result, colMatch);
			return result;
		} catch (AIException ex) {
			if (ex.getCause() != null)
				throw new RuntimeException(ex.getCause());
			else
				throw new RuntimeException(ex);
		}
	}

	public static BOBsBatchWfPsMonBean transfer(IBOBsBatchWfPsMonValue value) {
		if (value == null)
			return null;
		try {
			if (value instanceof BOBsBatchWfPsMonBean) {
				return (BOBsBatchWfPsMonBean) value;
			}
			BOBsBatchWfPsMonBean newBean = new BOBsBatchWfPsMonBean();

			DataContainerFactory.transfer(value, newBean);
			return newBean;
		} catch (Exception ex) {
			if (ex.getCause() != null)
				throw new RuntimeException(ex.getCause());
			else
				throw new RuntimeException(ex);
		}
	}

	public static BOBsBatchWfPsMonBean[] transfer(IBOBsBatchWfPsMonValue[] value) {
		if (value == null || value.length == 0)
			return null;

		try {
			if (value instanceof BOBsBatchWfPsMonBean[]) {
				return (BOBsBatchWfPsMonBean[]) value;
			}
			BOBsBatchWfPsMonBean[] newBeans = new BOBsBatchWfPsMonBean[value.length];
			for (int i = 0; i < newBeans.length; i++) {
				newBeans[i] = new BOBsBatchWfPsMonBean();
				DataContainerFactory.transfer(value[i], newBeans[i]);
			}
			return newBeans;
		} catch (Exception ex) {
			if (ex.getCause() != null)
				throw new RuntimeException(ex.getCause());
			else
				throw new RuntimeException(ex);
		}
	}

	public static void save(IBOBsBatchWfPsMonValue aValue) throws Exception {
		save(transfer(aValue));
	}

	public static void save(IBOBsBatchWfPsMonValue[] aValues) throws Exception {
		save(transfer(aValues));
	}

	public static void saveBatch(IBOBsBatchWfPsMonValue[] aValues) throws Exception {
		saveBatch(transfer(aValues));
	}
}
