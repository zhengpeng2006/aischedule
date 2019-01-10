package com.asiainfo.monitor.busi.exe.ds;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBAcctSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBUrlSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: AI(NanJing)</p>
 *
 * @author Yang Hua
 * @version 1.0
 */
public final class MonTableDataSourceUtil {

  private static final HashMap CACHE = new HashMap();

  private transient static Log log=LogFactory.getLog(MonTableDataSourceUtil.class);
  
  private MonTableDataSourceUtil() {
  }


  /**
   *
   * @param dbUrl String
   * @param dbAcctCode String
   * @return Connection
   * @throws Exception
   */
  public static Connection getConnection(String dbUrl, String dbAcctCode) throws Exception {
    Connection conn = null;
    String key = getKey(dbUrl, dbAcctCode);
    if (!CACHE.containsKey(key)) {
      synchronized (CACHE) {
	if (!CACHE.containsKey(key)) {
	  IAIMonDBAcctSV objMonDBAcctSV = (IAIMonDBAcctSV) ServiceFactory.getService(IAIMonDBAcctSV.class);
	  IAIMonDBUrlSV objMonDBUrlSV = (IAIMonDBUrlSV) ServiceFactory.getService(IAIMonDBUrlSV.class);
	  IBOAIMonDBURLValue objMonDbUrl = objMonDBUrlSV.getBeanByName(dbUrl);
	  IBOAIMonDBAcctValue objMonDbAcct = objMonDBAcctSV.getBeanByCode(dbAcctCode);

	  Properties prop = new Properties();
	  prop.setProperty("initialSize", String.valueOf(objMonDbAcct.getConnMin()));
	  prop.setProperty("maxActive", String.valueOf(objMonDbAcct.getConnMax()));
	  prop.setProperty("maxIdle", String.valueOf(objMonDbAcct.getConnMin()));
	  prop.setProperty("maxWait", "2000");
	  prop.setProperty("removeAbandoned", "true");
	  prop.setProperty("removeAbandonedTimeout", "180");
	  prop.setProperty("logAbandoned", "true");
	  prop.setProperty("validationQuery", "select 1 from dual");
	  prop.setProperty("testWhileIdle", "true");
	  prop.setProperty("testOnBorrow", "false");
	  prop.setProperty("testOnReturn", "false");
	  prop.setProperty("timeBetweenEvictionRunsMillis", "10000");
	  prop.setProperty("numTestsPerEvictionRun", "2");
	  prop.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
	  prop.setProperty("url", "jdbc:oracle:thin:@" + objMonDbUrl.getUrl());
	  prop.setProperty("username", objMonDbAcct.getUsername());
	  prop.setProperty("password", objMonDbAcct.getPassword());
	  CACHE.put(key, createDataSource(prop));
	}
      }
    }

    DataSource ds = (DataSource) CACHE.get(key);
    try{
    	conn = ds.getConnection();
    	conn.setAutoCommit(false);
    }catch(SQLNestedException ex){
    	// "数据源连接异常"
    	log.error(AIMonLocaleFactory.getResource("MOS0000148"));
    }
    
    return conn;
  }


  /**
   *
   * @param prop Properties
   * @return DataSource
   * @throws Exception
   */
  private static DataSource createDataSource(Properties prop) throws Exception {
    return BasicDataSourceFactory.createDataSource(prop);
  }

  /**
   *
   * @param dbUrl String
   * @param dbAcctCode String
   * @return String
   */
  private static String getKey(String dbUrl,String dbAcctCode){
    return dbUrl+"|"+dbAcctCode;
  }
  
  public static void main(String[] args){
	  try{
		  Properties prop = new Properties();
		  prop.setProperty("initialSize", "1");
		  prop.setProperty("maxActive", "10");
		  prop.setProperty("maxIdle", "5");
		  prop.setProperty("maxWait", "2000");
		  prop.setProperty("removeAbandoned", "true");
		  prop.setProperty("removeAbandonedTimeout", "180");
		  prop.setProperty("logAbandoned", "true");
		  prop.setProperty("validationQuery", "select 1 from dual");
		  prop.setProperty("testWhileIdle", "true");
		  prop.setProperty("testOnBorrow", "false");
		  prop.setProperty("testOnReturn", "false");
		  prop.setProperty("timeBetweenEvictionRunsMillis", "10000");
		  prop.setProperty("numTestsPerEvictionRun", "2");
		  prop.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
		  StringBuilder sb=new StringBuilder("");
		  sb.append("(DESCRIPTION_LIST =");
		  sb.append("(LOAD_BALANCE = off)");
		  sb.append("(FAILOVER = on)");
		  sb.append("(DESCRIPTION =");
		  sb.append("(ADDRESS_LIST =");
		  sb.append("(LOAD_BALANCE=OFF)");
		  sb.append("(FAILOVER=ON)");
		  sb.append("(ADDRESS = (PROTOCOL = TCP)(HOST = 10.11.16.168)(PORT = 1521))");
		  sb.append(")");
		  sb.append("(CONNECT_DATA =");
		  sb.append("(SERVICE_NAME = TESTDB)");
//		  sb.append("(INSTANCE_NAME = HNKT2)");
		  sb.append("(FAILOVER_MODE=(TYPE=session)(METHOD=basic)(RETRIES=4)(DELAY=1))");
		  sb.append(")");
		  sb.append(")");
		  sb.append(")");
		  prop.setProperty("url", "jdbc:oracle:thin:@" + sb.toString());
		  prop.setProperty("username", "orcl");
		  prop.setProperty("password", "orcl");
		  
		  DataSource ds=createDataSource(prop);
		  Connection conn = ds.getConnection();
		  conn.setAutoCommit(false);
	  }catch(Exception e){
		  
	  }
  }
}
