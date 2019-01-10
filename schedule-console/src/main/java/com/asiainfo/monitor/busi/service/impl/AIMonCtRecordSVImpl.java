package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCtRecordDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCtRecordSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCtRecordBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;

public class AIMonCtRecordSVImpl implements IAIMonCtRecordSV {

    /**
     * 根据标识读取控制记录
     * @param domainId
     */
    public IBOAIMonCtRecordValue getCtRecordById(String id) throws RemoteException, Exception {
        IAIMonCtRecordDAO dao = (IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
        return dao.getCtRecordById(id);
    }
    
    
    /**
     * 获取被控制对象还有效的控制记录
     * @return
     * @throws Exception
     */
    public IBOAIMonCtRecordValue[] getEffectCtRecordByObjId(int ct_Type,int obj_Type,String obj_Id) throws RemoteException,Exception{    	
    	IAIMonCtRecordDAO dao = (IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
    	return dao.getEffectCtRecordByObjId(ct_Type,obj_Type,obj_Id);
    }

    /**
     * 读取监控状态记录
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String getAllControlInfo() throws RemoteException,Exception{
    	IAIMonCtRecordDAO dao = (IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
    	int webTraceCount=dao.getEffectCtRecordCountByObjId(1,3);
    	int appTraceCount=dao.getEffectCtRecordCountByObjId(3,3);
    	int actionCount=dao.getEffectCtRecordCountByObjId(5,3);
    	int svCount=dao.getEffectCtRecordCountByObjId(7,3);
    	int sqlCount=dao.getEffectCtRecordCountByObjId(9,3);
    	StringBuilder result=new StringBuilder("<cts>");
    	result.append("<webtrace>"+webTraceCount+"</webtrace>");
    	result.append("<apptrace>"+appTraceCount+"</apptrace>");
    	result.append("<action>"+actionCount+"</action>");
    	result.append("<method>"+svCount+"</method>");
    	result.append("<sql>"+sqlCount+"</sql>");
    	result.append("</cts>");
    	return result.toString();
    }
    
    public List getCtRecordByCtType(String ctType) throws Exception {
    	List result = new ArrayList();
    	Map map = null;
    	IAIMonCtRecordDAO dao = (IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
    	IBOAIMonCtRecordValue[] values = dao.getCtRecordByCtType(ctType);
    	if (values != null || (values!=null && values.length > 0)) {
    		for (int i = 0; i <values.length; i++) {
    			map = new HashMap();
    			map.put("OBJ_NAME", values[i].getCtObjname());
    			map.put("CT_ACTOR", values[i].getCtActor());
    			result.add(map);
    		}
    	}

    	return result;
    }
    
    /**
     * 保存控制记录
     * @param ctType
     * @param objType
     * @param objId
     * @param objName
     * @param duration
     * @throws RemoteException
     * @throws Exception
     */
    public void saveOrUpdate(int ctType,int objType,int objId,String objName,Timestamp invalidDate,int duration,String actor) throws RemoteException,Exception{
    	IBOAIMonCtRecordValue value=new BOAIMonCtRecordBean();
		Timestamp ctime = new Timestamp(System.currentTimeMillis());
		value.setCtType(ctType);
		value.setCtObjtype(objType);//应用
		value.setCtObjid(objId);
		value.setCtObjname(objName);
		value.setCtActor(actor);
		value.setCtDate(ctime);
		value.setEffectDate(ctime);
		value.setInvalidDate(invalidDate);
		value.setInvalidDuti(duration);
		value.setCtFlag(1);
		IAIMonCtRecordDAO dao=(IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
		dao.saveOrUpdate(value);
    }
    
    /**
	 * 批量回写控制记录
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue[] values) throws RemoteException,Exception {
		IAIMonCtRecordDAO dao=(IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
		dao.saveOrUpdate(values);
	}

	/**
	 * 回写控制记录
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue value) throws RemoteException,Exception {
		IAIMonCtRecordDAO dao=(IAIMonCtRecordDAO)ServiceFactory.getService(IAIMonCtRecordDAO.class);
		dao.saveOrUpdate(value);
	}
	
}
