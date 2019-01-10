package com.asiainfo.batchwriteoff.util;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.datasource.DataSourceFactory;
import com.ai.appframe2.complex.xml.XMLHelper;
import com.ai.appframe2.complex.xml.cfg.defaults.Pool;
import com.ai.common.util.DistrictUtil;
import com.ai.common.util.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate Mar 7, 2012 4:41:34 PM </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public class ConnUtil {
	private final static transient Log log = LogFactory.getLog(ConnUtil.class);
	
	private static final String DATA_SOURCE_PARTY = "party";
	private static final String DATA_SOURCE_SO = "so";
	private static final String DATA_SOURCE_AMS = "ams";
	private static final String DATA_SOURCE_ZG="zg";
	private static final String DATA_SOURCE_ZC = "zc";
	private static final String DATA_SOURCE_JF = "jf";
	

	private static final Map poolMap = new HashMap();

	static {
		try {
			Pool[] pools = XMLHelper.getInstance().getDefaults().getDatasource().getPools();
			if(pools!=null && pools.length>0){
				for (int i = 0; i < pools.length; i++) {
					poolMap.put(pools[i].getName(), "");
				}
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	
	/**
	 * 获得base库只读连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 4:46:02 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 4:46:02 PM       yanrg                 v1.0.0
	 */
	public static Connection getReadOnlyBaseConn() throws Exception {
		return ServiceManager.getSession().getConnection(DataSourceFactory.getDataSource().getPrimaryDataSource());
	}

	/**
	 * 获得base可写连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 4:47:40 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 4:47:40 PM       yanrg                 v1.0.0
	 */
	public static Connection getWriteAbleBaseConn() throws Exception {
		return ServiceManager.getSession().getNewConnection(DataSourceFactory.getDataSource().getPrimaryDataSource());
	}
	
	
	/**
	 * 获得party只读连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 5:08:02 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 5:08:02 PM       yanrg                 v1.0.0
	 */
	public static Connection getReadOnlyPartyConn() throws Exception {
		return ServiceManager.getSession().getConnection(getRealDataSource(DATA_SOURCE_PARTY,""));
	}
	
	/**
	 * 获得party可写连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 4:47:40 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 4:47:40 PM       yanrg                 v1.0.0
	 */
	public static Connection getWriteAblePartyConn() throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_PARTY,""));
	}
	
	/**
	 * 获得so只读连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 5:08:02 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 5:08:02 PM       yanrg                 v1.0.0
	 */
	public static Connection getReadOnlySoConn(String regionId) throws Exception {
		return ServiceManager.getSession().getConnection(getRealDataSource(DATA_SOURCE_SO,regionId));
	}
	
	/**
	 * 获得so可写连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 4:47:40 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 4:47:40 PM       yanrg                 v1.0.0
	 */
	public static Connection getWriteAbleSoConn(String regionId) throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_SO,regionId));
	}
	
	/**
	 * 获得AMS只读连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 5:08:02 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 5:08:02 PM       yanrg                 v1.0.0
	 */
	public static Connection getReadOnlyAmsConn(String regionId) throws Exception {
		return ServiceManager.getSession().getConnection(getRealDataSource(DATA_SOURCE_AMS,regionId));
	}
	
	/**
	 * 获得AMS可写连接
		* @Function: ConnUtil.java
		* @Description: 
		*
		* @return
		* @throws Exception
		*
		* @version: v1.0.0
		* @author: yanrg
		* @date: Mar 7, 2012 4:47:40 PM
		*
		* Modification History:
		* Date         		    Author          	 Version            
		*-------------------------------------------------------*
		* Mar 7, 2012 4:47:40 PM       yanrg                 v1.0.0
	 */
	public static Connection getWriteAbleAmsConn(String regionId) throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_AMS,regionId));
	}
	
	public static Connection getTransactionAbleWriteAbleAmsConn(String regionId) throws Exception {
		return ServiceManager.getSession().getConnection(getRealDataSource(DATA_SOURCE_AMS,regionId));
	}
	
	private static String getRealDataSource(String dataSourceName,String regionId)throws Exception{
		String realDataSource = null;
		if(poolMap.containsKey(dataSourceName)){
			realDataSource = dataSourceName;
		}else {
			if(StringUtils.isNotBlank(regionId)){
				String centerNumber = DistrictUtil.getCenterByRegionId(regionId);
				StringBuilder sb = new StringBuilder().append(dataSourceName).append(centerNumber);
				if(poolMap.containsKey(sb.toString())){
					realDataSource = sb.toString();
				}else{
					//根据数据源名称-[{0}]-和地市标识-[{1}]-没有获得真实数据源！
					ExceptionUtil.throwBusinessException("CMS0000152", dataSourceName, regionId);
				}
					
			}else {
				//地市为空！
				ExceptionUtil.throwBusinessException("CMS0000151");
			}
			
		}
		return realDataSource;
	}
    
	
	/** 
	 * 删除临时表数据  
	 * @Function getDeleteTmpTableSql
	 * @Description 
	 *
	 * @param tempTable
	 * @return
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-1 上午09:44:09
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-1     yandong2           v1.0.0               修改原因<br>
	 */
	public static String getDeleteTmpTableSql(String tempTable,String field,int modNum,int modVal) {
		StringBuilder sql=new StringBuilder();
		if (modNum<1||modVal<0|| StringUtils.isBlank(field)) {
			sql.append(" truncate table ").append(tempTable);
		}else{
			if (modNum==1&&modVal==0) {
				sql.append(" truncate table ").append(tempTable);
			}else{
				sql.append(" delete from ").append(tempTable);
				sql.append(" where mod(").append(field.toUpperCase())
					.append(",").append(modNum).append(")=").append(modVal);
			}
		}
		
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return sql.toString();
	}
	
	/**   
	 * @Function getDropTmpTable
	 * @Description 
	 *
	 * @param tempTable
	 * @return
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-8-31 下午04:00:45
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-8-31     yandong2           v1.0.0               修改原因<br>
	 */
	public static String getDropTmpTable(String tempTable)throws Exception {
		StringBuilder sql=new StringBuilder();
		sql.append(" drop table ").append(tempTable).append(" CASCADE CONSTRAINTS ");
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return sql.toString();
	}
	
	
	/**
	 * 判断某个数据库下是否存在表
	 * @param conn	数据源连接
	 * @param tableName	表名称
	 * @return
	 * @throws Exception
	 */
	public static boolean isExistTable(Connection conn,String tableName) throws Exception{
		boolean result=false;
		if (conn==null){
			return result;
		}
		DatabaseMetaData meta = (DatabaseMetaData)conn.getMetaData();		
		ResultSet rs = meta.getTables(null, null, tableName, null);
		if(rs.next()){
			result=true;
		}
		if (rs != null){ 
			rs.close();
			rs = null;
		}
		return result;
	}
	
	/**
	 * 获取可读的ZG数据库连接
	 * @Function getReadOnlyZgConn
	 * @Description 
	 *
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-28 下午09:05:05
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-28     yandong2           v1.0.0               修改原因<br>
	 */
	public static Connection getReadOnlyZgConn() throws Exception {
		return ServiceManager.getSession().getConnection(getRealDataSource(DATA_SOURCE_ZG,""));
	}
	
	/**
	 * 获取可写的ZG库数据源连接
	 * @Function getWriteAbleZgConn
	 * @Description 
	 *
	 * @return
	 * @throws Exception
	 *
	 * @version v1.0.0
	 * @author yandong2
	 * @date 2012-9-28 下午09:06:34
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2012-9-28     yandong2           v1.0.0               修改原因<br>
	 */
	public static Connection getWriteAbleZgConn() throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_ZG,""));
	}
	
	/**
	 * @Function: 			getWriteAbleZcConn
	 * @Description: 		获取可写的ZC库数据源连接		
	 *
	 * @return
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: zhaowei7
	 * @date: 2012-11-13
	 *
	 * Modification History:
	 * Date         		    Author          	 Version            
	 *-------------------------------------------------------*
	 * 2012-11-13       		zhaowei7             v1.0.0
	 */
	public static Connection getWriteAbleZcConn() throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_ZC,""));
	}
	
	/**
	 * @Function: 			getWriteAbleJfConn
	 * @Description:		获取计费连接 		
	 *
	 * @return
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: zhaowei7
	 * @date: 2012-11-26
	 *
	 * Modification History:
	 * Date         		    Author          	 Version            
	 *-------------------------------------------------------*
	 * 2012-11-26       		zhaowei7             v1.0.0
	 */
	public static Connection getWriteAbleJfConn() throws Exception {
		return ServiceManager.getSession().getNewConnection(getRealDataSource(DATA_SOURCE_JF,""));
	}
	
	/**
	 * @Function: ConnUtil.java
	 * @Description: 
	 *
	 * @param args
	 *
	 * @version: v1.0.0
	 * @author: yanrg
	 * @date: Mar 7, 2012 4:41:34 PM
	 *
	 * Modification History:
	 * Date         		    Author          	 Version            
	 *-------------------------------------------------------*
	 * Mar 7, 2012 4:41:34 PM       yanrg                 v1.0.0
	 */
	public static void main(String[] args)throws Exception {
		/*Connection conn = getWriteAblePartyConn();
		System.out.println(conn.getAutoCommit());*/
	}

}
