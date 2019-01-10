package com.asiainfo.monitor.exeframe.config.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOCfgTaskParamDefineValue;

public class BOCfgTaskParamDefineEngine {

  public static BOCfgTaskParamDefineBean[] getBeans(DataContainerInterface dc) throws
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

    public static BOCfgTaskParamDefineBean getBean(long _Id) throws Exception{
            /**new create*/
    String condition = "ID = :S_ID";
      Map map = new HashMap();
      map.put("S_ID",new Long(_Id));
;
      BOCfgTaskParamDefineBean[] beans = getBeans(condition,map);
      if(beans!=null && beans.length==1)
	      return beans[0];
      else if(beans!=null && beans.length>1){
	      throw new Exception("[ERROR]More datas than one queryed by PK");
      }else{
	      BOCfgTaskParamDefineBean bean = new BOCfgTaskParamDefineBean();
	      	      bean.setId(_Id);
	      	      return bean;
      }
 }

  public static  BOCfgTaskParamDefineBean[] getBeans(Criteria sql) throws Exception{
   return getBeans(sql,-1,-1,false);
  }
 public static  BOCfgTaskParamDefineBean[] getBeans(Criteria sql,int startNum,int endNum,boolean isShowFK) throws Exception{
    String[] cols = null;
    String condition = "";
    Map param = null;
    if(sql != null){
      cols = (String[])sql.getSelectColumns().toArray(new String[0]);
      condition = sql.toString();
      param = sql.getParameters();
    }
    return (BOCfgTaskParamDefineBean[])getBeans(cols,condition,param,startNum,endNum,isShowFK);
  }




  public static  BOCfgTaskParamDefineBean[] getBeans(String condition,Map parameter) throws Exception{
	return getBeans(null,condition,parameter,-1,-1,false);
  }

  public static  BOCfgTaskParamDefineBean[] getBeans(String[] cols,String condition,Map parameter,
	   int startNum,int endNum,boolean isShowFK) throws Exception{
	Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return (BOCfgTaskParamDefineBean[])ServiceManager.getDataStore().retrieve(conn,BOCfgTaskParamDefineBean.class,BOCfgTaskParamDefineBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,null);
	}catch(Exception e){
		throw e;
	}finally{
	   if (conn != null)
	      conn.close();
	}
  }

   public static  BOCfgTaskParamDefineBean[] getBeans(String[] cols,String condition,Map parameter,
	  int startNum,int endNum,boolean isShowFK,String[] extendBOAttrs) throws Exception{
	  Connection conn = null;
	  try {
		  conn = ServiceManager.getSession().getConnection();
		  return (BOCfgTaskParamDefineBean[])ServiceManager.getDataStore().retrieve(conn,BOCfgTaskParamDefineBean.class,BOCfgTaskParamDefineBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,extendBOAttrs);
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
	      return ServiceManager.getDataStore().retrieveCount(conn,BOCfgTaskParamDefineBean.getObjectTypeStatic(),condition,parameter,null);
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
		return ServiceManager.getDataStore().retrieveCount(conn,BOCfgTaskParamDefineBean.getObjectTypeStatic(),condition,parameter,extendBOAttrs);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

  public static void save( BOCfgTaskParamDefineBean aBean) throws Exception
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

   public static  void save( BOCfgTaskParamDefineBean[] aBeans) throws Exception{
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

   public static  void saveBatch( BOCfgTaskParamDefineBean[] aBeans) throws Exception{
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


  public static  BOCfgTaskParamDefineBean[] getBeansFromQueryBO(String soureBO,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOCfgTaskParamDefineBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOCfgTaskParamDefineBean.class,BOCfgTaskParamDefineBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
      if(resultset != null)
	      resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

     public static  BOCfgTaskParamDefineBean[] getBeansFromSql(String sql,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOCfgTaskParamDefineBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOCfgTaskParamDefineBean.class,BOCfgTaskParamDefineBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
      if(resultset != null)
	      resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

   public static java.math.BigDecimal getNewId() throws Exception{
       return ServiceManager.getIdGenerator().getNewId(BOCfgTaskParamDefineBean.getObjectTypeStatic());
   }


   public static java.sql.Timestamp getSysDate() throws Exception{
       return ServiceManager.getIdGenerator().getSysDate(BOCfgTaskParamDefineBean.getObjectTypeStatic());
   }


   public static BOCfgTaskParamDefineBean wrap(DataContainerInterface source,Map colMatch,boolean canModify){
     try{
       return (BOCfgTaskParamDefineBean)DataContainerFactory.wrap(source,BOCfgTaskParamDefineBean.class,colMatch,canModify);
     }catch(Exception e){
       if(e.getCause()!=null)
	 throw new RuntimeException(e.getCause());
       else
	 throw new RuntimeException(e);
     }
   }
   public static BOCfgTaskParamDefineBean copy(DataContainerInterface source,Map colMatch,boolean canModify){
     try {
       BOCfgTaskParamDefineBean result = new BOCfgTaskParamDefineBean();
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

   public static BOCfgTaskParamDefineBean transfer(IBOCfgTaskParamDefineValue value) {
	   if(value==null)
		   return null;
	try {
		if(value instanceof BOCfgTaskParamDefineBean){
			return (BOCfgTaskParamDefineBean)value;
		}
		BOCfgTaskParamDefineBean newBean = new BOCfgTaskParamDefineBean();

		DataContainerFactory.transfer(value ,newBean);
		return newBean;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }

   public static BOCfgTaskParamDefineBean[] transfer(IBOCfgTaskParamDefineValue[] value) {
	   if(value==null || value.length==0)
		   return null;

	try {
		if(value instanceof BOCfgTaskParamDefineBean[]){
			return (BOCfgTaskParamDefineBean[])value;
		}
		BOCfgTaskParamDefineBean[] newBeans = new BOCfgTaskParamDefineBean[value.length];
		   for(int i=0;i<newBeans.length;i++){
			   newBeans[i] = new BOCfgTaskParamDefineBean();
			DataContainerFactory.transfer(value[i] ,newBeans[i]);
		}
		return newBeans;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }
  public static void save(IBOCfgTaskParamDefineValue aValue) throws Exception
  {
     save(transfer(aValue));
  }

   public static  void save( IBOCfgTaskParamDefineValue[] aValues) throws Exception{
	  save(transfer(aValues));
   }

   public static  void saveBatch( IBOCfgTaskParamDefineValue[] aValues) throws Exception{
     saveBatch(transfer(aValues));
   }
}
