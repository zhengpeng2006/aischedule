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
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;

public class BOAIMonParamValuesEngine {

  public static BOAIMonParamValuesBean[] getBeans(DataContainerInterface dc) throws
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

    public static BOAIMonParamValuesBean getBean(long _VId) throws Exception{
            /**new create*/
    String condition = "V_ID = :S_V_ID";
      Map map = new HashMap();
      map.put("S_V_ID",new Long(_VId));
;
      BOAIMonParamValuesBean[] beans = getBeans(condition,map);
      if(beans!=null && beans.length==1)
	      return beans[0];
      else if(beans!=null && beans.length>1){
	//[错误]根据主键查询出现一条以上记录
	      throw new Exception("[ERROR]More datas than one queryed by PK");
      }else{
	      BOAIMonParamValuesBean bean = new BOAIMonParamValuesBean();
	      	      bean.setVId(_VId);
	      	      return bean;
      }
 }

  public static  BOAIMonParamValuesBean[] getBeans(Criteria sql) throws Exception{
   return getBeans(sql,-1,-1,false);
  }
 public static  BOAIMonParamValuesBean[] getBeans(Criteria sql,int startNum,int endNum,boolean isShowFK) throws Exception{
    String[] cols = null;
    String condition = "";
    Map param = null;
    if(sql != null){
      cols = (String[])sql.getSelectColumns().toArray(new String[0]);
      condition = sql.toString();
      param = sql.getParameters();
    }
    return (BOAIMonParamValuesBean[])getBeans(cols,condition,param,startNum,endNum,isShowFK);
  }




  public static  BOAIMonParamValuesBean[] getBeans(String condition,Map parameter) throws Exception{
	return getBeans(null,condition,parameter,-1,-1,false);
  }

  public static  BOAIMonParamValuesBean[] getBeans(String[] cols,String condition,Map parameter,
	   int startNum,int endNum,boolean isShowFK) throws Exception{
	Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return (BOAIMonParamValuesBean[])ServiceManager.getDataStore().retrieve(conn,BOAIMonParamValuesBean.class,BOAIMonParamValuesBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,null);
	}catch(Exception e){
		throw e;
	}finally{
	   if (conn != null)
	      conn.close();
	}
  }

   public static  BOAIMonParamValuesBean[] getBeans(String[] cols,String condition,Map parameter,
	  int startNum,int endNum,boolean isShowFK,String[] extendBOAttrs) throws Exception{
	  Connection conn = null;
	  try {
		  conn = ServiceManager.getSession().getConnection();
		  return (BOAIMonParamValuesBean[])ServiceManager.getDataStore().retrieve(conn,BOAIMonParamValuesBean.class,BOAIMonParamValuesBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,extendBOAttrs);
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
	      return ServiceManager.getDataStore().retrieveCount(conn,BOAIMonParamValuesBean.getObjectTypeStatic(),condition,parameter,null);
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
		return ServiceManager.getDataStore().retrieveCount(conn,BOAIMonParamValuesBean.getObjectTypeStatic(),condition,parameter,extendBOAttrs);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

  public static void save( BOAIMonParamValuesBean aBean) throws Exception
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

   public static  void save( BOAIMonParamValuesBean[] aBeans) throws Exception{
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

   public static  void saveBatch( BOAIMonParamValuesBean[] aBeans) throws Exception{
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


  public static  BOAIMonParamValuesBean[] getBeansFromQueryBO(String soureBO,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOAIMonParamValuesBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOAIMonParamValuesBean.class,BOAIMonParamValuesBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
    	  if(resultset!=null)
    		  resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

     public static  BOAIMonParamValuesBean[] getBeansFromSql(String sql,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BOAIMonParamValuesBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BOAIMonParamValuesBean.class,BOAIMonParamValuesBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
    	  if(resultset!=null)
    		  resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

   public static java.math.BigDecimal getNewId() throws Exception{
       return ServiceManager.getIdGenerator().getNewId(BOAIMonParamValuesBean.getObjectTypeStatic());
   }


   public static java.sql.Timestamp getSysDate() throws Exception{
       return ServiceManager.getIdGenerator().getSysDate(BOAIMonParamValuesBean.getObjectTypeStatic());
   }


   public static BOAIMonParamValuesBean wrap(DataContainerInterface source,Map colMatch,boolean canModify){
     try{
       return (BOAIMonParamValuesBean)DataContainerFactory.wrap(source,BOAIMonParamValuesBean.class,colMatch,canModify);
     }catch(Exception e){
       if(e.getCause()!=null)
	 throw new RuntimeException(e.getCause());
       else
	 throw new RuntimeException(e);
     }
   }
   public static BOAIMonParamValuesBean copy(DataContainerInterface source,Map colMatch,boolean canModify){
     try {
       BOAIMonParamValuesBean result = new BOAIMonParamValuesBean();
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

   public static BOAIMonParamValuesBean transfer(IBOAIMonParamValuesValue value) {
	   if(value==null)
		   return null;
	try {
		if(value instanceof BOAIMonParamValuesBean){
			return (BOAIMonParamValuesBean)value;
		}
		BOAIMonParamValuesBean newBean = new BOAIMonParamValuesBean();

		DataContainerFactory.transfer(value ,newBean);
		return newBean;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }

   public static BOAIMonParamValuesBean[] transfer(IBOAIMonParamValuesValue[] value) {
	   if(value==null || value.length==0)
		   return null;

	try {
		if(value instanceof BOAIMonParamValuesBean[]){
			return (BOAIMonParamValuesBean[])value;
		}
		BOAIMonParamValuesBean[] newBeans = new BOAIMonParamValuesBean[value.length];
		   for(int i=0;i<newBeans.length;i++){
			   newBeans[i] = new BOAIMonParamValuesBean();
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
  public static void save(IBOAIMonParamValuesValue aValue) throws Exception
  {
     save(transfer(aValue));
  }

   public static  void save( IBOAIMonParamValuesValue[] aValues) throws Exception{
	  save(transfer(aValues));
   }

   public static  void saveBatch( IBOAIMonParamValuesValue[] aValues) throws Exception{
     saveBatch(transfer(aValues));
   }
}
