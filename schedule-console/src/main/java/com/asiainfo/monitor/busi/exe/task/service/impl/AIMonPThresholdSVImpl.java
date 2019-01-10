package com.asiainfo.monitor.busi.exe.task.service.impl;



import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThresholdBean;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPThresholdDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.ThresholdModel;

public class AIMonPThresholdSVImpl implements IAIMonPThresholdSV {

	/**
	 * 根据条件查询进程阀值配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] getThresholdByExpr(String expr, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		return thresholdDao.getThresholdByExpr(expr, startNum, endNum);
	}
	
	public int getThresholdCount(String expr) throws Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		return thresholdDao.getThresholdCount(expr);
	}
	
	/**
	 * 根据主键取得进程阀值配置信息
	 * 
	 * @param thresholdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue getBeanById(long thresholdId) throws RemoteException,Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		return thresholdDao.getBeanById(thresholdId);
	}
	
	/**
	 * 批量保存或修改进程阀值配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue[] values) throws RemoteException,Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		thresholdDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改进程阀值配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue value) throws RemoteException,Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		thresholdDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存进程阀值配置(向导模式)
	 * @param model
	 * @throws Exception
	 */
	public void savePThresholdByGuide(ThresholdModel model) throws RemoteException,Exception{
		try{
			
			IBOAIMonThresholdValue thresholdValue = new BOAIMonThresholdBean();
			long thresholdId=0;
			if ( model.getSelfId()==0){
				thresholdValue.setStsToNew();
				
			}else{				
				thresholdId=model.getSelfId();
				thresholdValue.setThresholdId(thresholdId);
				thresholdValue.setStsToOld();
			}
			
			thresholdValue.setThresholdName(model.getName());
			thresholdValue.setExpr1(model.getExpr1());
			thresholdValue.setExpr2(model.getExpr2());
			thresholdValue.setState("U");
			thresholdValue.setRemarks(model.getRemarks());
			
			IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
			thresholdDao.saveOrUpdate(thresholdValue);
			model.setSelfId(thresholdValue.getThresholdId());
			
			IAIMonPInfoSV pInfoSV =(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			IBOAIMonPInfoValue pInfoValue=pInfoSV.getMonPInfoValue(model.getParentId());
			if (pInfoValue!=null){
				pInfoValue.setThresholdId(thresholdValue.getThresholdId());
				pInfoSV.saveOrUpdate(pInfoValue);
			}else{
				// "没有定义进程监控的任务"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000183"));
			}
		}catch(Exception e){
			throw new Exception("Call AIMonPThresholdSVImpl's method savePThresholdByGuide has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 删除进程阀值配置(若有关联的任务则报内容为FAIL的异常)
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long thresholdId) throws RemoteException,Exception {
		IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
		//查询此阀值否有关联任务
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV) ServiceFactory
                .getService(IAIMonPInfoSV.class);
        IBOAIMonPInfoValue[] values = infoSv.getMonPInfoValueByParams(-1, -1,
                thresholdId,-1);
        if (values != null && values.length > 0)//有则不让删除,返回错误信息
        {
            throw new Exception("\u8be5\u6570\u636e\u4e0b\u6709\u4efb\u52a1\u5173\u8054\uff0c\u65e0\u6cd5\u5220\u9664");//跑出指定内容异常
        }
		thresholdDao.delete(thresholdId);
	}
	
	/**
     * 根据条件查询进程阀值配置
     * 
     * @param cond
     * @param param
     * @return
     * @throws Exception
     */
    public IBOAIMonThresholdValue[] getThresholdByExprAndName(String expr, String name,Integer startNum, Integer endNum) throws RemoteException,Exception {
        IAIMonPThresholdDAO thresholdDao = (IAIMonPThresholdDAO)ServiceFactory.getService(IAIMonPThresholdDAO.class);
        StringBuffer sb = new StringBuffer();
        Map param = new HashMap();
        sb.append("STATE = :state");
        param.put("state", "U");
        if (!StringUtils.isBlank(expr))
        {
            sb.append(" AND ");
            sb.append("EXPR1 like :expr");
            param.put("expr", "%"+expr+"%");
        }
        if (!StringUtils.isBlank(name))
        {
            sb.append(" AND ");
            sb.append("THRESHOLD_NAME like :name");
            param.put("name", "%"+name+"%");
        }
        return thresholdDao.query(sb.toString(), param, startNum, endNum);
    }
}
