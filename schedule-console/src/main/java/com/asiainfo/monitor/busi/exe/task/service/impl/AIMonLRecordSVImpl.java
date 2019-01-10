package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonLRecordDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonLRecordValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonLRecordSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPImgDataResolveSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupAtomicSV;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TimeUtil;
import com.asiainfo.monitor.tools.util.TypeConst;
import com.asiainfo.monitor.tools.util.transform.DataGridTransformImpl;
import com.asiainfo.monitor.tools.util.transform.IChartTransform;
import com.asiainfo.monitor.tools.util.transform.ITransformData;
import com.asiainfo.monitor.tools.util.transform.TransformData;

public class AIMonLRecordSVImpl implements IAIMonLRecordSV {
	
	private static transient Log log = LogFactory.getLog(AIMonLRecordSVImpl.class);
	
	public IBOAIMonLRecordValue[] queryRecord(String infoId) throws RemoteException,Exception{
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonLRecordValue.S_InfoId).append("= :infoId");
		HashMap parameters=new HashMap();
		parameters.put("infoId",infoId);
		IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
		return recordDao.query(sb.toString(),parameters);
	}
	
	
	/**
	 * 获得监控图形数据
	 * @param infoId long[]
	 * @param transformClass String
	 * @param startDate Date
	 * @param endDate Date
	 * @return HashMap
	 * @throws Exception
	 */
	public List getMonLRecordImage(Object[] infoIds,String groupId,String viewTpyeId,Date startDate, Date endDate) throws RemoteException,Exception {
		List result=null;
		try{
			IAIMonPInfoGroupAtomicSV groupSV=(IAIMonPInfoGroupAtomicSV)ServiceFactory.getService(IAIMonPInfoGroupAtomicSV.class);
			IBOAIMonPInfoGroupValue groupValue=groupSV.getMonPInfoGroupById(Long.parseLong(groupId));
			if (groupValue==null){
				// "没有定义组["+groupId+"]"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000178", groupId));
			}
			if (StringUtils.isBlank(groupValue.getLayer())){
				// "没有为组["+groupValue.getGroupName()+"]定义数据存储层"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000179", groupValue.getGroupName()));
			}
			List resolveList=new ArrayList();
			IAIMonPInfoSV infoSV=(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			if (infoIds.length>1){			
				for (int i=0;i<infoIds.length;i++){
					IBOAIMonPInfoValue infoValue=infoSV.getMonPInfoValue(Long.parseLong(String.valueOf(infoIds[i])));
					if (infoValue!=null && !resolveList.contains(infoValue.getResolveId()+"")){
						resolveList.add(infoValue.getResolveId()+"");
					}
				}
			}else{
				IBOAIMonPInfoValue infoValue=infoSV.getMonPInfoValue(Long.parseLong(String.valueOf(infoIds[0])));
				if (infoValue!=null && !resolveList.contains(infoValue.getResolveId()+"")){
					resolveList.add(infoValue.getResolveId()+"");
				}
			}
			if (resolveList.size()>1){
				resolveList=null;
				// "你选择的任务图形解析设置不同，不支持不同解析函数"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000180"));
			}
			
			IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV)ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
			IBOAIMonPImgDataResolveValue resolveValue= resolveSV.getMonPImgDataResolveById(Long.parseLong(String.valueOf(resolveList.get(0))));
			
		    String clazz = resolveValue.getTransformClass();
		    if(StringUtils.isBlank(clazz)){
		    	// 请配置数据转换项
		    	log.error(AIMonLocaleFactory.getResource("MOS0000181") + "!resolveId="+resolveValue.getResolveId());
		    	throw new Exception(AIMonLocaleFactory.getResource("MOS0000181") + "["+resolveValue.getName()+"]");
		    }
		    IChartTransform objITransform = (IChartTransform)Class.forName(clazz).newInstance();
		    objITransform.setShowNamePos(resolveValue.getShowNamePos());
		    objITransform.setShowValuePos(resolveValue.getShowValuePos());
			IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
			IBOAIMonLRecordValue[] recordValues=recordDao.getMonLRecordImage(infoIds,groupValue.getLayer(),viewTpyeId,startDate,endDate);
			if (recordValues==null || recordValues.length<1){
				return null;
			}
			ITransformData[] datas=new TransformData[recordValues.length];
			for (int i=0;i<recordValues.length;i++){
				datas[i]=new TransformData();
				datas[i].setCode(recordValues[i].getInfoCode());
				datas[i].setName(recordValues[i].getInfoName());
				datas[i].setValue(recordValues[i].getInfoValue());
				datas[i].setTime( TimeUtil.format(recordValues[i].getCreateDate()));
			}
			result=objITransform.createChartData(datas);
			resolveList=null;
			objITransform=null;
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		
		return result;
	}

	/**
	 * 获得监控图形数据
	 * @param infoId
	 * @param layer String
	 * @param durHour
	 * @return List
	 * @throws Exception
	 */
	public List getMonLRecordImage(String infoId,String layer,int durHour) throws RemoteException,Exception {
		List result=null;
		try{
			
			IAIMonPInfoSV infoSV=(IAIMonPInfoSV)ServiceFactory.getService(IAIMonPInfoSV.class);
			IBOAIMonPInfoValue infoValue=infoSV.getMonPInfoValue(Long.parseLong(String.valueOf(infoId)));
			if (infoValue==null){
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000247"));
			}
			
			IAIMonPImgDataResolveSV resolveSV = (IAIMonPImgDataResolveSV)ServiceFactory.getService(IAIMonPImgDataResolveSV.class);
			IBOAIMonPImgDataResolveValue resolveValue= resolveSV.getMonPImgDataResolveById(infoValue.getResolveId());
			if (resolveValue==null)
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000181") + "info:["+infoValue.getInfoId()+"]");
		    String clazz = resolveValue.getTransformClass();
		    if(StringUtils.isBlank(clazz)){
		    	// 请配置数据转换项
		    	log.error(AIMonLocaleFactory.getResource("MOS0000181") + "!resolveId="+resolveValue.getResolveId());
		    	throw new Exception(AIMonLocaleFactory.getResource("MOS0000181") + "["+resolveValue.getName()+"]");
		    }
		    IChartTransform objITransform = (IChartTransform)Class.forName(clazz).newInstance();
		    objITransform.setShowNamePos(resolveValue.getShowNamePos());
		    objITransform.setShowValuePos(resolveValue.getShowValuePos());
			IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
			IBOAIMonLRecordValue[] recordValues=recordDao.getMonLRecord(infoValue.getInfoId(), layer, durHour);
			if (recordValues==null || recordValues.length<1){
				return null;
			}
			ITransformData[] datas=new TransformData[recordValues.length];
			for (int i=0;i<recordValues.length;i++){
				datas[i]=new TransformData();
				datas[i].setCode(recordValues[i].getInfoCode());
				datas[i].setName(recordValues[i].getInfoName());
				datas[i].setValue(recordValues[i].getInfoValue());
				datas[i].setTime( TimeUtil.format(recordValues[i].getCreateDate()));
			}
			result=objITransform.createChartData(datas);
			objITransform=null;
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 获得监控表格数据
	 * @param layer
	 * @param infocode
	 * @param startDate Date
	 * @param endDate Date
	 * @return List
	 * @throws Exception
	 */
	public List getMonLRecordGrid() throws RemoteException,Exception {
		List result=null;
		try{
			IAIMonStaticDataSV dataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] dataValues=dataSV.queryByCodeType("REGION_MAP");
			if (dataValues==null || dataValues.length<1)
				return result;
			result = new ArrayList();
			List dataArray=new ArrayList();
			IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
			for (int i=0;i<dataValues.length;i++){
				String layerInfoCode=dataValues[i].getCodeTypeAlias();
				if (StringUtils.isNotBlank(layerInfoCode) && layerInfoCode.indexOf(TypeConst._SPLIT_CHAR)>=0){
					String[] varStr=StringUtils.split(layerInfoCode,TypeConst._SPLIT_CHAR);
					String layer=varStr[0];
					String infoCode=varStr[1];
					IBOAIMonLRecordValue[] recordValues = recordDao.getMonLRecordByCode(layer, infoCode);
					if (recordValues!=null && recordValues.length > 0){						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						ITransformData[] datas=new TransformData[recordValues.length];
						for (int j=0;j<recordValues.length;j++){
							if (StringUtils.isNotBlank(recordValues[j].getInfoValue())) {
								ITransformData data=new TransformData();
								data.setValue(recordValues[j].getInfoValue());
								data.setTime(df.format(recordValues[j].getCreateDate()));
								dataArray.add(data);
							}
						}
					}
				}
			}
			if (dataArray.size()>0){
				ITransformData[] datas=(ITransformData[])dataArray.toArray(new ITransformData[0]);
				if (datas!=null){
					DataGridTransformImpl gridTrans = new DataGridTransformImpl();
					result.add(gridTrans.getGridValue(datas));
					result.add(gridTrans.getStatisValue(datas));
				}
			}
			
			
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @param infoId
	 * @param layer
	 * @param type:D:天,W:周[本月内周],M:月
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonLRecordValue[] getMonLRecord(long infoId,String layer,String type) throws RemoteException,Exception{
		IBOAIMonLRecordValue[] result=null;
		try{
			IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
			String beginDate="",endDate="";
			if ("D".equalsIgnoreCase(type)){
				String curDate=TimeUtil.format1(new Date(System.currentTimeMillis()));
				beginDate=curDate;
				endDate=curDate;
			}else if ("W".equalsIgnoreCase(type)){
				String monday=TimeUtil.format1(TimeUtil.getMondayOfThisWeek().getTime());
				String mondayMonth=TimeUtil.getMM(TimeUtil.getMondayOfThisWeek().getTime());
				String sunday=TimeUtil.format1(TimeUtil.getSundayOfThisWeek().getTime());
				String sundayMonth=TimeUtil.getMM(TimeUtil.getSundayOfThisWeek().getTime());
				String curMonth=TimeUtil.getMM(new Date(System.currentTimeMillis()));
				beginDate=monday;
				endDate=sunday;				
				if (!curMonth.equals(mondayMonth)){
					beginDate=TimeUtil.format1(TimeUtil.getFristDateOfMonth().getTime());
				}
				if (!curMonth.equals(sundayMonth)){
					endDate=TimeUtil.format1(TimeUtil.getLastDateOfMonth().getTime());
				}
				
				
			}else if ("M".equalsIgnoreCase(type)){
				beginDate=TimeUtil.format1(TimeUtil.getFristDateOfMonth().getTime());
				endDate=TimeUtil.format1(TimeUtil.getLastDateOfMonth().getTime());
			}
			beginDate=beginDate+" 00:00:00";
			endDate=endDate+" 23:59:59";
			result=recordDao.getMonLRecord(infoId,layer,beginDate,endDate);
			
		}catch(Exception e){
			log.error("Call AIMonLRecordSVImpl's method getMonLRecord has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取BatchId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public long getBatchId() throws RemoteException,Exception {
		IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
		return recordDao.getBatchId();
	}
	
	/**
	 * 获取sysdate
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Timestamp getSystemTime() throws RemoteException,Exception {
		return ServiceManager.getOpDateTime();
	}
	
	/**
	 * 批量保存或修改监控结果
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonLRecordValue[] values) throws RemoteException,Exception {
		IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
		recordDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改监控结果
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonLRecordValue value) throws RemoteException,Exception {
		IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
		recordDao.saveOrUpdate(value);
	}
	
	/**
	   * 根据任务标识、层、读取最新监控结果
	   * @param infoId
	   * @param layer
	   * @return
	   * @throws Exception
	   */
	  public List getLastMonLRecordByIds(Object[] infoIds,String layer) throws RemoteException,Exception {
		  List result=new ArrayList();
		  IAIMonLRecordDAO recordDao = (IAIMonLRecordDAO)ServiceFactory.getService(IAIMonLRecordDAO.class);
		  IBOAIMonLRecordValue value = null;
		  Map map = null;
		  for (int i=0;i<infoIds.length;i++){
			  value = recordDao.getLastMonLRecordById(Long.parseLong(String.valueOf(infoIds[i])), layer);
			  if (value!=null) {
				  map = new HashMap();
				  map.put("id", value.getInfoId());
				  map.put("value", value.getInfoValue());
				  map.put("name", value.getInfoName());
				  result.add(map);
			  }
		  }
		  return result;
	  }
}
