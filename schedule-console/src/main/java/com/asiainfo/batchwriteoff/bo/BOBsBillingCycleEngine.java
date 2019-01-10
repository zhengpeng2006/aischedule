package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class BOBsBillingCycleEngine {

  public static BOBsBillingCycleBean[] getBeans(DataContainerInterface dc) throws
      Exception {
    Map ps = dc.getProperties();
    StringBuffer buffer = new StringBuffer();
    Map pList = new HashMap();
    for (java.util.Iterator cc = ps.entrySet().iterator(); cc.hasNext(); ) {
      Map.Entry e = (Map.Entry) cc.next();
      if(buffer.length() >0)
	 buffer.append(" and ");
      buffer.append(e.getKey().toString() + " = :p_" + e.getKey().toString());
      pList.put("p_" + e.getKey().toString(),e.getValue());
    }
    Connection conn = ServiceManager.getSession().getConnection();
    try {
      return getBeans(buffer.toString(), pList);
    }finally{
      if (conn != null)
	conn.close();
    }
  }

    public static BOBsBillingCycleBean getBean(String _RegionId,long _BillingCycleId,int _BillingCycleTypeId) throws Exception{
            /**new create*/
    String condition = "REGION_ID = :S_REGION_ID and BILLING_CYCLE_ID = :S_BILLING_CYCLE_ID and BILLING_CYCLE_TYPE_ID = :S_BILLING_CYCLE_TYPE_ID";
      Map map = new HashMap();
      map.put("S_REGION_ID",_RegionId);
      map.put("S_BILLING_CYCLE_ID",new Long(_BillingCycleId));
      map.put("S_BILLING_CYCLE_TYPE_ID",new Integer(_BillingCycleTypeId));
;
      BOBsBillingCycleBean[] beans = getBeans(condition,map);
      if(beans!=null && beans.length==1)
	      return beans[0];
      else if(beans!=null && beans.length>1){
	//[错误]根据主键查询出现一条以上记录
	      throw new Exception("[ERROR]More datas than one queryed by PK");
      }else{
	      BOBsBillingCycleBean bean = new BOBsBillingCycleBean();
	      	      bean.setRegionId(_RegionId);
	      	      bean.setBillingCycleId(_BillingCycleId);
	      	      bean.setBillingCycleTypeId(_BillingCycleTypeId);
	      	      return bean;
      }
 }

  public static  BOBsBillingCycleBean[] getBeans(Criteria sql) throws Exception{
   return getBeans(sql,-1,-1,false);
  }
 public static  BOBsBillingCycleBean[] getBeans(Criteria sql,int startNum,int endNum,boolean isShowFK) throws Exception{
    String[] cols = null;
    String condition = "";
    Map param = null;
    if(sql != null){
      cols = (String[])sql.getSelectColumns().toArray(new String[0]);
      condition = sql.toString();
      param = sql.getParameters();
    }
    return (BOBsBillingCycleBean[])getBeans(cols,condition,param,startNum,endNum,isShowFK);
  }




  public static  BOBsBillingCycleBean[] getBeans(String condition,Map parameter) throws Exception{
	return getBeans(null,condition,parameter,-1,-1,false);
  }

  public static  BOBsBillingCycleBean[] getBeans(String[] cols,String condition,Map parameter,
	   int startNum,int endNum,boolean isShowFK) throws Exception{
	Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return (BOBsBillingCycleBean[]) ServiceManager.getDataStore().retrieve(conn,BOBsBillingCycleBean.class,BOBsBillingCycleBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,null);
	}catch(Exception e){
		throw e;
	}finally{
	   if (conn != null)
	      conn.close();
	}
  }

   public static  BOBsBillingCycleBean[] getBeans(String[] cols,String condition,Map parameter,
	  int startNum,int endNum,boolean isShowFK,String[] extendBOAttrs) throws Exception{
	  Connection conn = null;
	  try {
		  conn = ServiceManager.getSession().getConnection();
		  return (BOBsBillingCycleBean[]) ServiceManager.getDataStore().retrieve(conn,BOBsBillingCycleBean.class,BOBsBillingCycleBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,extendBOAttrs);
	  }catch(Exception e){
		  throw e;
	  }finally{
		if (conn != null)
		  conn.close();
	  }
   }


   public static int getBeansCount(String condition,Map parameter) throws Exception{
     Connection conn = null;
      try {
	      conn = ServiceManager.getSession().getConnection();
	      return ServiceManager.getDataStore().retrieveCount(conn,BOBsBillingCycleBean.getObjectTypeStatic(),condition,parameter,null);
      }catch(Exception e){
	      throw e;
      }finally{
	  if (conn != null)
	      conn.close();
      }
   }

   public static int getBeansCount(String condition,Map parameter,String[] extendBOAttrs) throws Exception{
	      Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return ServiceManager.getDataStore().retrieveCount(conn,BOBsBillingCycleBean.getObjectTypeStatic(),condition,parameter,extendBOAttrs);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

  public static void save( BOBsBillingCycleBean aBean) throws Exception
  {
	  Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		   ServiceManager.getDataStore().save(conn,aBean);
	   }catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
		  conn.close();
	}
  }

   public static  void save( BOBsBillingCycleBean[] aBeans) throws Exception{
	     Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		ServiceManager.getDataStore().save(conn,aBeans);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

   public static  void saveBatch( BOBsBillingCycleBean[] aBeans) throws Exception{
	     Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		ServiceManager.getDataStore().saveBatch(conn,aBeans);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }


  public static  BOBsBillingCycleBean[] getBeansFromQueryBO(String soureBO,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
	  resultset = ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOBsBillingCycleBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOBsBillingCycleBean.class,BOBsBillingCycleBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
	  if (conn != null)
	      conn.close();
      }
   }

     public static  BOBsBillingCycleBean[] getBeansFromSql(String sql,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  resultset = ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOBsBillingCycleBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOBsBillingCycleBean.class,BOBsBillingCycleBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
	  if (conn != null)
	      conn.close();
      }
   }

   public static java.math.BigDecimal getNewId() throws Exception{
       return ServiceManager.getIdGenerator().getNewId(BOBsBillingCycleBean.getObjectTypeStatic());
   }


   public static Timestamp getSysDate() throws Exception{
       return ServiceManager.getIdGenerator().getSysDate(BOBsBillingCycleBean.getObjectTypeStatic());
   }


   public static BOBsBillingCycleBean wrap(DataContainerInterface source,Map colMatch,boolean canModify){
     try{
       return (BOBsBillingCycleBean) DataContainerFactory.wrap(source, BOBsBillingCycleBean.class, colMatch, canModify);
     }catch(Exception e){
       if(e.getCause()!=null)
	 throw new RuntimeException(e.getCause());
       else
	 throw new RuntimeException(e);
     }
   }
   public static BOBsBillingCycleBean copy(DataContainerInterface source,Map colMatch,boolean canModify){
     try {
       BOBsBillingCycleBean result = new BOBsBillingCycleBean();
       DataContainerFactory.copy(source, result, colMatch);
       return result;
     }
     catch (AIException ex) {
       if(ex.getCause()!=null)
	 throw new RuntimeException(ex.getCause());
       else
	 throw new RuntimeException(ex);
     }
    }

   public static BOBsBillingCycleBean transfer(IBOBsBillingCycleValue value) {
	   if(value==null)
		   return null;
	try {
		if(value instanceof BOBsBillingCycleBean){
			return (BOBsBillingCycleBean)value;
		}
		BOBsBillingCycleBean newBean = new BOBsBillingCycleBean();

		DataContainerFactory.transfer(value, newBean);
		return newBean;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }

   public static BOBsBillingCycleBean[] transfer(IBOBsBillingCycleValue[] value) {
	   if(value==null || value.length==0)
		   return null;

	try {
		if(value instanceof BOBsBillingCycleBean[]){
			return (BOBsBillingCycleBean[])value;
		}
		BOBsBillingCycleBean[] newBeans = new BOBsBillingCycleBean[value.length];
		   for(int i=0;i<newBeans.length;i++){
			   newBeans[i] = new BOBsBillingCycleBean();
			DataContainerFactory.transfer(value[i], newBeans[i]);
		}
		return newBeans;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }
  public static void save(IBOBsBillingCycleValue aValue) throws Exception
  {
     save(transfer(aValue));
  }

   public static  void save( IBOBsBillingCycleValue[] aValues) throws Exception{
	  save(transfer(aValues));
   }

   public static  void saveBatch( IBOBsBillingCycleValue[] aValues) throws Exception{
     saveBatch(transfer(aValues));
   }
}
