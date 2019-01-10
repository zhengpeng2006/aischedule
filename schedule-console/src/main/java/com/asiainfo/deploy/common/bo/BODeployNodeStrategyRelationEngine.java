package com.asiainfo.deploy.common.bo;

import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.ResultSet;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.util.criteria.Criteria;
import com.ai.appframe2.bo.DataContainerFactory;

import com.asiainfo.deploy.common.ivalues.*;

public class BODeployNodeStrategyRelationEngine {

  public static BODeployNodeStrategyRelationBean[] getBeans(DataContainerInterface dc) throws
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

    public static BODeployNodeStrategyRelationBean getBean(long _NodeId) throws Exception{
            /**new create*/
    String condition = "NODE_ID = :S_NODE_ID";
      Map map = new HashMap();
      map.put("S_NODE_ID",new Long(_NodeId));
;
      BODeployNodeStrategyRelationBean[] beans = getBeans(condition,map);
      if(beans!=null && beans.length==1)
	      return beans[0];
      else if(beans!=null && beans.length>1){
	//[错误]根据主键查询出现一条以上记录
	      throw new Exception("[ERROR]More datas than one queryed by PK");
      }else{
	      BODeployNodeStrategyRelationBean bean = new BODeployNodeStrategyRelationBean();
	      	      bean.setNodeId(_NodeId);
	      	      return bean;
      }
 }

  public static  BODeployNodeStrategyRelationBean[] getBeans(Criteria sql) throws Exception{
   return getBeans(sql,-1,-1,false);
  }
 public static  BODeployNodeStrategyRelationBean[] getBeans(Criteria sql,int startNum,int endNum,boolean isShowFK) throws Exception{
    String[] cols = null;
    String condition = "";
    Map param = null;
    if(sql != null){
      cols = (String[])sql.getSelectColumns().toArray(new String[0]);
      condition = sql.toString();
      param = sql.getParameters();
    }
    return (BODeployNodeStrategyRelationBean[])getBeans(cols,condition,param,startNum,endNum,isShowFK);
  }




  public static  BODeployNodeStrategyRelationBean[] getBeans(String condition,Map parameter) throws Exception{
	return getBeans(null,condition,parameter,-1,-1,false);
  }

  public static  BODeployNodeStrategyRelationBean[] getBeans(String[] cols,String condition,Map parameter,
	   int startNum,int endNum,boolean isShowFK) throws Exception{
	Connection conn = null;
	try {
		conn = ServiceManager.getSession().getConnection();
		return (BODeployNodeStrategyRelationBean[])ServiceManager.getDataStore().retrieve(conn,BODeployNodeStrategyRelationBean.class,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,null);
	}catch(Exception e){
		throw e;
	}finally{
	   if (conn != null)
	      conn.close();
	}
  }

   public static  BODeployNodeStrategyRelationBean[] getBeans(String[] cols,String condition,Map parameter,
	  int startNum,int endNum,boolean isShowFK,String[] extendBOAttrs) throws Exception{
	  Connection conn = null;
	  try {
		  conn = ServiceManager.getSession().getConnection();
		  return (BODeployNodeStrategyRelationBean[])ServiceManager.getDataStore().retrieve(conn,BODeployNodeStrategyRelationBean.class,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),cols,condition,parameter,startNum,endNum,isShowFK,false,extendBOAttrs);
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
	      return ServiceManager.getDataStore().retrieveCount(conn,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),condition,parameter,null);
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
		return ServiceManager.getDataStore().retrieveCount(conn,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),condition,parameter,extendBOAttrs);
	}catch(Exception e){
		throw e;
	}finally{
	  if (conn != null)
	      conn.close();
	}
   }

  public static void save( BODeployNodeStrategyRelationBean aBean) throws Exception
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

   public static  void save( BODeployNodeStrategyRelationBean[] aBeans) throws Exception{
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

   public static  void saveBatch( BODeployNodeStrategyRelationBean[] aBeans) throws Exception{
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


  public static  BODeployNodeStrategyRelationBean[] getBeansFromQueryBO(String soureBO,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  String sql = ServiceManager.getObjectTypeFactory().getInstance(soureBO).getMapingEnty();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BODeployNodeStrategyRelationBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BODeployNodeStrategyRelationBean.class,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
      if(resultset!=null)resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

     public static  BODeployNodeStrategyRelationBean[] getBeansFromSql(String sql,Map parameter) throws Exception{
      Connection conn = null;
      ResultSet resultset = null;
      try {
	  conn = ServiceManager.getSession().getConnection();
	  resultset =ServiceManager.getDataStore().retrieve(conn,sql,parameter);
	  return (BODeployNodeStrategyRelationBean[])ServiceManager.getDataStore().crateDtaContainerFromResultSet(BODeployNodeStrategyRelationBean.class,BODeployNodeStrategyRelationBean.getObjectTypeStatic(),resultset,null,true);
      }catch(Exception e){
	  throw e;
      }finally{
      if(resultset!=null)resultset.close();
	  if (conn != null)
	      conn.close();
      }
   }

   public static java.math.BigDecimal getNewId() throws Exception{
       return ServiceManager.getIdGenerator().getNewId(BODeployNodeStrategyRelationBean.getObjectTypeStatic());
   }


   public static java.sql.Timestamp getSysDate() throws Exception{
       return ServiceManager.getIdGenerator().getSysDate(BODeployNodeStrategyRelationBean.getObjectTypeStatic());
   }


   public static BODeployNodeStrategyRelationBean wrap(DataContainerInterface source,Map colMatch,boolean canModify){
     try{
       return (BODeployNodeStrategyRelationBean)DataContainerFactory.wrap(source,BODeployNodeStrategyRelationBean.class,colMatch,canModify);
     }catch(Exception e){
       if(e.getCause()!=null)
	 throw new RuntimeException(e.getCause());
       else
	 throw new RuntimeException(e);
     }
   }
   public static BODeployNodeStrategyRelationBean copy(DataContainerInterface source,Map colMatch,boolean canModify){
     try {
       BODeployNodeStrategyRelationBean result = new BODeployNodeStrategyRelationBean();
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

   public static BODeployNodeStrategyRelationBean transfer(IBODeployNodeStrategyRelationValue value) {
	   if(value==null)
		   return null;
	try {
		if(value instanceof BODeployNodeStrategyRelationBean){
			return (BODeployNodeStrategyRelationBean)value;
		}
		BODeployNodeStrategyRelationBean newBean = new BODeployNodeStrategyRelationBean();

		DataContainerFactory.transfer(value ,newBean);
		return newBean;
	}catch (Exception ex) {
		if(ex.getCause()!=null)
			throw new RuntimeException(ex.getCause());
		else
			throw new RuntimeException(ex);
	}
   }

   public static BODeployNodeStrategyRelationBean[] transfer(IBODeployNodeStrategyRelationValue[] value) {
	   if(value==null || value.length==0)
		   return null;

	try {
		if(value instanceof BODeployNodeStrategyRelationBean[]){
			return (BODeployNodeStrategyRelationBean[])value;
		}
		BODeployNodeStrategyRelationBean[] newBeans = new BODeployNodeStrategyRelationBean[value.length];
		   for(int i=0;i<newBeans.length;i++){
			   newBeans[i] = new BODeployNodeStrategyRelationBean();
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
  public static void save(IBODeployNodeStrategyRelationValue aValue) throws Exception
  {
     save(transfer(aValue));
  }

   public static  void save( IBODeployNodeStrategyRelationValue[] aValues) throws Exception{
	  save(transfer(aValues));
   }

   public static  void saveBatch( IBODeployNodeStrategyRelationValue[] aValues) throws Exception{
     saveBatch(transfer(aValues));
   }
}
