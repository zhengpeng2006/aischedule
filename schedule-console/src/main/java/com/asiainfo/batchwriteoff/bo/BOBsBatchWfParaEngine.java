package com.asiainfo.batchwriteoff.bo;

import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfParaValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class BOBsBatchWfParaEngine {

  public static BOBsBatchWfParaBean[] getBeans(DataContainerInterface dc) throws
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

    public static BOBsBatchWfParaBean getBean(String _RegionId,String _ParaCode) throws Exception{
            /**new create*/
    String condition = "REGION_ID = :S_REGION_ID and PARA_CODE = :S_PARA_CODE";
      Map map = new HashMap();
      map.put("S_REGION_ID",_RegionId);
      map.put("S_PARA_CODE",_ParaCode);
;
      BOBsBatchWfParaBean[] beans = getBeans(condition,map);
      if(beans!=null && beans.length==1)
	      return beans[0];
      else if(beans!=null && beans.length>1){
	//[错误]根据主键查询出现一条以上记录
	      throw new Exception("[ERROR]More datas than one queryed by PK");
      }else{
	      BOBsBatchWfParaBean bean = new BOBsBatchWfParaBean();
	      	      bean.setRegionId(_RegionId);
	      	      bean.setParaCode(_ParaCode);
	      	      return bean;
      }
 }

  public static  BOBsBatchWfParaBean[] getBeans(Criteria sql) throws Exception{
   return getBeans(sql,-1,-1,false);
  }
 public static  BOBsBatchWfParaBean[] getBeans(Criteria sql,int startNum,int endNum,boolean isShowFK) throws Exception{
    String[] cols = null;
    String condition = "";
    Map param = null;
    if(sql != null){
      cols = (String[])sql.getSelectColumns().toArray(new String[0]);
      condition = sql.toString();
      param = sql.getParameters();
    }
    return (BOBsBatchWfParaBean[])getBeans(cols,condition,param,startNum,endNum,isShowFK);
  }




  public static  BOBsBatchWfParaBean[] getBeans(String condition,Map parameter) throws Exception{
	return getBeans(null,condition,parameter,-1,-1,false);
  }

  public static  BOBsBatchWfParaBean[] getBeans(String[] cols,String condition,Map parameter,
	   int startNum,int endNum,boolean isShowFK) throws Exception{
	Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return (BOBsBatchWfParaBean[]) ServiceManager.getDataStore().retrieve(conn,BOBsBatchWfParaBean.class,BOBsBatchWfParaBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,null);
	}catch(Exception e){
		throw e;
	}finally{
	   if (conn != null)
	      conn.close();
	}
  }

   public static  BOBsBatchWfParaBean[] getBeans(String[] cols,String condition,Map parameter,
	  int startNum,int endNum,boolean isShowFK,String[] extendBOAttrs) throws Exception{
	  Connection conn = null;
	  try {
		  conn = ServiceManager.getSession().getConnection();
		  return (BOBsBatchWfParaBean[]) ServiceManager.getDataStore().retrieve(conn,BOBsBatchWfParaBean.class,BOBsBatchWfParaBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,extendBOAttrs);
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
	      return ServiceManager.getDataStore().retrieveCount(conn,BOBsBatchWfParaBean.getObjectTypeStatic(),condition,parameter,null);
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
		return ServiceManager.getDataStore().retrieveCount(conn,BOBsBatchWfParaBean.getObjectTypeStatic(),condition,parameter,extendBOAttrs);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

  public static void save( BOBsBatchWfParaBean aBean) throws Exception
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

   public static  void save( BOBsBatchWfParaBean[] aBeans) throws Exception{
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

   public static  void saveBatch( BOBsBatchWfParaBean[] aBeans) throws Exception{
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


  public static  BOBsBatchWfParaBean[] getBeansFromQueryBO(String soureBO,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
	  resultset = ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOBsBatchWfParaBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOBsBatchWfParaBean.class,BOBsBatchWfParaBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
	  if (conn != null)
	      conn.close();
      }
   }

     public static  BOBsBatchWfParaBean[] getBeansFromSql(String sql,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  resultset = ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOBsBatchWfParaBean[]) ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOBsBatchWfParaBean.class,BOBsBatchWfParaBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
	  if (conn != null)
	      conn.close();
      }
   }

   public static java.math.BigDecimal getNewId() throws Exception{
       return ServiceManager.getIdGenerator().getNewId(BOBsBatchWfParaBean.getObjectTypeStatic());
   }


   public static Timestamp getSysDate() throws Exception{
       return ServiceManager.getIdGenerator().getSysDate(BOBsBatchWfParaBean.getObjectTypeStatic());
   }


   public static BOBsBatchWfParaBean wrap(DataContainerInterface source,Map colMatch,boolean canModify){
     try{
       return (BOBsBatchWfParaBean) DataContainerFactory.wrap(source, BOBsBatchWfParaBean.class, colMatch, canModify);
     }catch(Exception e){
       if(e.getCause()!=null)
	 throw new RuntimeException(e.getCause());
       else
	 throw new RuntimeException(e);
     }
   }
   public static BOBsBatchWfParaBean copy(DataContainerInterface source,Map colMatch,boolean canModify){
     try {
       BOBsBatchWfParaBean result = new BOBsBatchWfParaBean();
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

   public static BOBsBatchWfParaBean transfer(IBOBsBatchWfParaValue value) {
	   if(value==null)
		   return null;
	try {
		if(value instanceof BOBsBatchWfParaBean){
			return (BOBsBatchWfParaBean)value;
		}
		BOBsBatchWfParaBean newBean = new BOBsBatchWfParaBean();

		DataContainerFactory.transfer(value, newBean);
		return newBean;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }

   public static BOBsBatchWfParaBean[] transfer(IBOBsBatchWfParaValue[] value) {
	   if(value==null || value.length==0)
		   return null;

	try {
		if(value instanceof BOBsBatchWfParaBean[]){
			return (BOBsBatchWfParaBean[])value;
		}
		BOBsBatchWfParaBean[] newBeans = new BOBsBatchWfParaBean[value.length];
		   for(int i=0;i<newBeans.length;i++){
			   newBeans[i] = new BOBsBatchWfParaBean();
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
  public static void save(IBOBsBatchWfParaValue aValue) throws Exception
  {
     save(transfer(aValue));
  }

   public static  void save( IBOBsBatchWfParaValue[] aValues) throws Exception{
	  save(transfer(aValues));
   }

   public static  void saveBatch( IBOBsBatchWfParaValue[] aValues) throws Exception{
     saveBatch(transfer(aValues));
   }
}
