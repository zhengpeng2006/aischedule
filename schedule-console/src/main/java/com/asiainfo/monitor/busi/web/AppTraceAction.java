package com.asiainfo.monitor.busi.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIFileOperationSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowTraceSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 * 采集规则的定义
 * expr0:本地路径
 * expr1:本地备份路径
 * @author Guocx
 *
 */
public class AppTraceAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(AppTraceAction.class);
	
	
	/**
	 * 采集所有App应用的Trace文件
	 * @param appId
	 * @throws Exception
	 */
	public int collectTraceFile() throws Exception{		
		int result=0;
		try{
			
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.collectTraceFile();
		}catch(Exception e){
			log.error("Call AppTraceAction's Method collectTraceFile has Exception :" + e.getMessage());
			// "采集异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000010") + e.getMessage());
		}		
		return result;
	}
	
	/**
	 * 采集文件
	 * @param appId
	 * @throws Exception
	 */
	public int collectTraceFile(Object[] ids) throws Exception{
		if (ids==null || ids.length<1)
			return 0;
		int result=0;
		try{
			
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.collectTraceFile(ids);
		}catch(Exception e){
			log.error("Call AppTraceAction's Method collectTraceFile has Exception :" + e.getMessage());
			// "采集异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000010") + e.getMessage());
		}		
		return result;
	}
	
	/**
	 * 查询Trace文件
	 * @param appIds
	 * @param pathType
	 * @return
	 * @throws Exception
	 */
	public List queryTraceFile(String pathType) throws Exception {
		
		if ( StringUtils.isBlank(pathType))
			return null;
		//测试代码可见Main内
		List result=null;
		try{
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.queryTraceFile(pathType);
		}catch(Exception e){
			log.error("Call AppTraceAction's Method queryTraceFile has Exception :"+e.getMessage());
		}
		return result;
		
	}
	/**
	 * 显示Trace文件名,只能显示一个文件
	 * @param fileName
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String showTraceFile(String localPath,String localBackPath,String fileName,String path) throws Exception {
		String result="";
		try{
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.showTraceFile(localPath,localBackPath,fileName,path);
		}catch(Exception e){
			log.error("Call AppTraceAction's Method showTraceFile has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 将文件从数据目录移到备份目录
	 * @param fileBuffer:服务器ID+"|"+本地路径+"|"+本地备份路径+"|"+文件名
	 * @return
	 * @throws Exception
	 */
	public boolean moveTraceFile(Object[] fileBuffer) throws Exception {
		
		if (fileBuffer==null || fileBuffer.length<1)
			return true;
		boolean result=false;
		try{
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.moveTraceFile(fileBuffer);
			
		}catch(Exception e){
			log.error("Call AppTraceAction's Method moveTraceFile has Exception :"+e.getMessage());
			// 转移到备份目录失败
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000140")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 清除备份目录
	 * @param appId
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTraceFileForBack() throws Exception {
		boolean result=false;
		try{
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.deleteTraceFileForBack();
		}catch(Exception e){
			log.error("Call AppTraceAction's Method deleteTraceFileForBack has Exception :"+e.getMessage());
			// 删除备份目录失败
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000141")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 将Trace文件解析成树结构对象
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List getTraceTreeXml(String file) throws Exception{
		if (StringUtils.isBlank(file))
			return null;
		List result=null;
		try{
			IAPIFileOperationSV fileOptSV=(IAPIFileOperationSV)ServiceFactory.getService(IAPIFileOperationSV.class);
			result=fileOptSV.getTraceTreeXml(file);
		}catch(Exception e){
			log.error("Call AppTraceAction's Method getTraceTreeXml has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 根据应用标识读取AppTrace的状态、内容
	 * @param appServerIds
	 * @return
	 */
	public List getAppTraces(Object[] ids){
		List result = new ArrayList();
		try{
			if (ids==null || ids.length<1)
				return null;
			IAPIShowTraceSV showTraceSV=(IAPIShowTraceSV)ServiceFactory.getService(IAPIShowTraceSV.class);
			result=showTraceSV.getAppTraces(ids);

		}catch (Exception e) {
			log.error("Call AppTraceAction's Method getAppTraces has Exception :"+e.getMessage());			
		}
		return result;
	}
	
	/**
	 * 根据应用标识，读取WebTrace状态、信息
	 * @param appServerIds
	 * @return
	 */
	public List getWebTraces(Object[] ids){
		List result = new ArrayList();
		try{
			if (ids==null || ids.length<1)
				return null;
			IAPIShowTraceSV showTraceSV=(IAPIShowTraceSV)ServiceFactory.getService(IAPIShowTraceSV.class);
			result=showTraceSV.getWebTraces(ids);
		}catch (Exception e) {
			log.error("Call AppTraceAction's Method getWebTraces has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	public List getTraceTimes(){
		List ret = new ArrayList();
		try {
			IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] tmpRet = staticDataSV.queryByCodeType("TRACE_TIME");
			if (tmpRet!=null && tmpRet.length>0 && tmpRet[0]!=null){
				for(int i=0;i<tmpRet.length;i++){
					HashMap map = new HashMap();
					map.put(IBOAIMonStaticDataValue.S_CodeValue, tmpRet[i].getCodeValue());
					map.put(IBOAIMonStaticDataValue.S_CodeName, tmpRet[i].getCodeName());
					ret.add(map);
				}
			}
			
		} catch (Exception e) {
			// "获取初始时间失败！"
			ret.add(AIMonLocaleFactory.getResource("MOS0000290"));
		}
		return ret;
	}
	
	public static void main(String[] args){
		/*Map testMap=new HashMap();
		testMap.put("CHK","false");
		testMap.put("APP_ID","1");
		testMap.put("APP_NAME","CRM1");
		testMap.put("FILE_NAME","a1.trc");
		testMap.put("FILE_DATE","2010-06-18 11:12:46");
		testMap.put("FILE_SIZE","2k");
		testMap.put("LOCAL_PATH","/tmp/aitrc_tmp/data");
		testMap.put("LOCAL_BACK_PATH","/tmp/aitrc_tmp/bak");
		result.add(testMap);
		return result;*/
	}
}
