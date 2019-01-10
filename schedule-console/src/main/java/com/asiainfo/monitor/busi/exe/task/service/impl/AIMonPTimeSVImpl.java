package com.asiainfo.monitor.busi.exe.task.service.impl;



import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTimeBean;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPTimeDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.TimeModel;

public class AIMonPTimeSVImpl implements IAIMonPTimeSV {

	/**
	 * 根据条件查询监控时间段配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] getTimeInfoByExpr(String expr, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		return timeDao.getTimeInfoByExpr(expr, startNum, endNum);
	}
	
	public int getTimeInfoCount(String expr) throws Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		return timeDao.getTimeInfoCount(expr);
	}
	
	/**
	 * 根据主键取得监控时间段配置信息
	 * 
	 * @param timeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue getBeanById(long timeId) throws RemoteException,Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		return timeDao.getBeanById(timeId);
	}

	/**
	 * 批量保存或修改监控时间段配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue[] values) throws RemoteException,Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		timeDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改监控时间段配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue value) throws RemoteException,Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		timeDao.saveOrUpdate(value);
	}
	
	/**
	 * 保存监控时间段配置(向导模式)
	 * @param model
	 * @throws Exception
	 */
	public void savePTimeByGuide(TimeModel model) throws RemoteException,Exception{
		try{
			
			IBOAIMonPTimeValue timeValue = new BOAIMonPTimeBean();
			long timeId=0;
			if ( model.getSelfId()==0){
				timeValue.setStsToNew();
			}else{				
				timeId=model.getSelfId();
				timeValue.setTimeId(timeId);
				timeValue.setStsToOld();
			}
			
			
			timeValue.setTType(model.getType());
			timeValue.setExpr(model.getExpr());
			timeValue.setState("U");
			timeValue.setRemarks(model.getName());
			IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
			timeDao.saveOrUpdate(timeValue);
			model.setSelfId(timeValue.getTimeId());
			
			IAIMonPInfoSV pInfoSV =(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			IBOAIMonPInfoValue pInfoValue=pInfoSV.getMonPInfoValue(model.getParentId());
			if (pInfoValue!=null){
				pInfoValue.setTimeId(timeValue.getTimeId());
				pInfoValue.setState("U");
				pInfoSV.saveOrUpdate(pInfoValue);
			}else{
				// "没有定义进程监控的任务"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000183"));
			}
		}catch(Exception e){
			throw new Exception("Call AIMonPTimeSVImpl's method savePTimeByGuide has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 删除监控时间段配置(若有任务关联此监控时间，则报内容为FAIL的异常)
	 * 
	 * @param tableId
	 * @throws Exception 
	 */
	public void delete(long timeId) throws RemoteException,Exception {
		IAIMonPTimeDAO timeDao = (IAIMonPTimeDAO)ServiceFactory.getService(IAIMonPTimeDAO.class);
		//查询是否有关联任务
        IAIMonPInfoSV infoSv = (IAIMonPInfoSV) ServiceFactory
                .getService(IAIMonPInfoSV.class);
        IBOAIMonPInfoValue[] values = infoSv.getMonPInfoValueByParams(-1, timeId,
                -1,-1);
        if (values != null && values.length > 0)//有则不让删除,返回错误信息
        {
            throw new Exception("\u8be5\u6570\u636e\u4e0b\u6709\u4efb\u52a1\u5173\u8054\uff0c\u65e0\u6cd5\u5220\u9664");//跑出指定内容异常
        }
		timeDao.delete(timeId);
	}
}
