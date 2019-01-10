package com.asiainfo.index.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.index.advpay.dao.interfaces.IUpgMonitorDataDAO;
import com.asiainfo.index.advpay.ivalues.IBOUpgMonitorDataValue;
import com.asiainfo.index.base.dao.interfaces.IBsIndexDefDAO;
import com.asiainfo.index.base.dao.interfaces.IBsMonitorCfgDAO;
import com.asiainfo.index.base.dao.interfaces.IBsMonitorDAO;
import com.asiainfo.index.base.dao.interfaces.IBsStaticDataDAO;
import com.asiainfo.index.base.ivalues.IBOBsMonitorCfgValue;
import com.asiainfo.index.service.interfaces.IBsIndexSV;

public class BsIndexSVImpl implements IBsIndexSV{
	private static final Logger LOGGER = Logger.getLogger(BsIndexSVImpl.class);

	@Override
	public Map<String, List<String[]>> getAllEnmuValue() throws Exception {
		IBsStaticDataDAO dao = (IBsStaticDataDAO)ServiceFactory.getService(IBsStaticDataDAO.class);
		return dao.getAllEnmuValue();
	}

	@Override
	public Map<Integer, String> getAllMonitors() throws Exception {
		IBsMonitorDAO dao = (IBsMonitorDAO)ServiceFactory.getService(IBsMonitorDAO.class);
		return dao.getAllMonitors();
	}

	@Override
	public IBOBsMonitorCfgValue[] getConditions(int monitorId,
			String indexKind) throws Exception {
	    IBsMonitorCfgDAO bsMonitorCfgDAO=(IBsMonitorCfgDAO)ServiceFactory.getService(IBsMonitorCfgDAO.class);
	    return bsMonitorCfgDAO.getConditions(monitorId,indexKind);
	}

	@Override
	public Map<Integer, String> getAllIndexName() throws Exception {
		IBsIndexDefDAO bsIndexDefDAO=(IBsIndexDefDAO)ServiceFactory.getService(IBsIndexDefDAO.class);
		return bsIndexDefDAO.getAllIndexName();
	}

	@Override
	public IBOUpgMonitorDataValue[] getData(Map<String, Object> conditions,List<String> list,
			String startTime, String endTime,long seqId) throws Exception{
		IBOUpgMonitorDataValue[] datas= null;
		if (startTime == null || endTime == null){//没有传时间默认是实时查询
			if (seqId > 0){//没有传上次序列号无法查询
				datas = getCurData(conditions,list, seqId);
			}else{
				LOGGER.error("没有上批次序列号，无法执行查询");
			}
		}else{
			datas = getHisData(conditions,list, startTime, endTime);
		}
		return datas;
	}
	
	private IBOUpgMonitorDataValue[] getHisData(Map<String, Object> conditions,List<String> list,
			String startTime, String endTime) throws Exception{
		IUpgMonitorDataDAO dao = (IUpgMonitorDataDAO)ServiceFactory.getService(IUpgMonitorDataDAO.class);
		return dao.getHisData(conditions, list, startTime, endTime);
	}
	
	private IBOUpgMonitorDataValue[] getCurData(Map<String, Object> conditions,List<String> list,long seqId) throws Exception{
		IUpgMonitorDataDAO dao = (IUpgMonitorDataDAO)ServiceFactory.getService(IUpgMonitorDataDAO.class);
		return dao.getCurData(conditions, list, seqId);
	}
	
		public static void main(String[] args) {
			List<String> list = new ArrayList<String>();
			list.add("DIM_VALUE4");
			list.add("SCA_VALUE1");
			list.add("SCA_VALUE2");
			list.add("SCA_VALUE3");
			list.add("SCA_VALUE4");
			IBsIndexSV sv = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
			try {
				IBOUpgMonitorDataValue[] datas = sv.getData(null, list, "2015-03-01 11:11:11","2015-05-20 11:11:11",-1);
				for (int i = 0; i < datas.length; i++) {
					System.out.println(datas[i]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
}
