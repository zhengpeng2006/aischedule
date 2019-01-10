package com.asiainfo.monitor.busi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.impl.CollectTraceAsynOperate;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerRouteSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIFileOperationSV;
import com.asiainfo.monitor.busi.web.AppTraceAction;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;
import com.asiainfo.monitor.interapi.api.trace.ITrace;
import com.asiainfo.monitor.interapi.api.trace.TracePoJoApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class APIFileOperationSVImpl implements IAPIFileOperationSV {

	private static transient Log log=LogFactory.getLog(APIFileOperationSVImpl.class);
	
	/**
	 * 采集所有App主机的trace文件
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public int collectTraceFile() throws RemoteException,Exception{
		int result=0;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			List list=serverSV.getServerByTempType(TypeConst.APP);
			if (list!=null && list.size()>0){
				String[] ids=new String[list.size()];
				for (int i=0;i<list.size();i++){
					ids[i]=((IServer)list.get(i)).getApp_Id();
				}
				if (ids!=null && ids.length>0){
					result=this.collectTraceFile(ids);
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000010")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 采集Trace文件
	 * @param appId
	 * @throws Exception
	 */
	public int collectTraceFile(Object[] appIds) throws RemoteException,Exception{		
		int result=0;
		try{
			if (appIds==null || appIds.length==0)
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000350"));
			
			//trace文件生成在app服务器主机上，如果是WEB的应用标识，则根据web路由找到具体的APP标识
			List idlist=new ArrayList();
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			IAIMonServerRouteSV routeSV=(IAIMonServerRouteSV)ServiceFactory.getService(IAIMonServerRouteSV.class);
			for (int i=0;i<appIds.length;i++){
				
				ServerConfig appServer=serverSV.getServerByServerId(String.valueOf(appIds[i]));
				if (appServer.getTemp_Type().equals(TypeConst.WEB)){
					IBOAIMonServerRouteValue[] routeAppValues=routeSV.getMonServerRouteBySServId(appServer.getApp_Id());
					if (routeAppValues!=null && routeAppValues.length>0){
						for (int j=0;j<routeAppValues.length;j++){
							if (!idlist.contains(String.valueOf(routeAppValues[j].getDServId()))){
								idlist.add(String.valueOf(routeAppValues[j].getDServId()));
							}
						}
					}
				}else{
					if (!idlist.contains(String.valueOf(appIds[i]))){
						idlist.add(String.valueOf(appIds[i]));
					}
				}
			}
			
			AsynOperation operate=new CollectTraceAsynOperate();
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			List srList=operate.asynOperation(threadCount,-1,appIds,-1);
			if (srList!=null && srList.size()>0){
				for (int i=0;i<srList.size();i++){
					SimpleResult sr=(SimpleResult)srList.get(i);
					if (sr!=null && sr.isSucc()){
						if (sr.getValue()!=null)
							result=result+Integer.parseInt(String.valueOf(sr.getValue()));
					}
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceAction's Method collectTraceFile has Exception :"+e.getMessage());
			// "采集异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000010")+e.getMessage());
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
	public List queryTraceFile(String pathType) throws RemoteException,Exception {
		List result=new ArrayList();
		if ( StringUtils.isBlank(pathType))
			return null;
		//测试代码可见Main内		
		try{
			String dir="",localPath="/tmp/aitrc_tmp/data",localBackPath="/tmp/aitrc_tmp/bak";
			
			String[] paths=this.getTrcPathSet();
			if (paths!=null && paths.length>1){
				localPath=paths[0];
				localBackPath=paths[1];
			}
			
			if(pathType.equalsIgnoreCase("TRC_DATA")){// "/tmp/aitrc_tmp/data"
	    		dir =localPath;
	    	}else if(pathType.equalsIgnoreCase("TRC_BAK")){// "/tmp/aitrc_tmp/bak"
	    		dir =localBackPath;
	    	}
			File f = new File(dir);
	    	File[] tmp = f.listFiles();
	    	Date date;
	    	Map fileMap;
	    	for (int fileCount = 0; fileCount < tmp.length; fileCount++) {
	    		if(tmp[fileCount].isFile()){
	    			fileMap = new HashMap();
	    			fileMap.put("CHK","false");
	    			fileMap.put("FILE_NAME",tmp[fileCount].getName());
	    			date = new Date();
	    			date.setTime(tmp[fileCount].lastModified());
	    			fileMap.put("FILE_DATE",date);
	    			fileMap.put("FILE_SIZE",tmp[fileCount].length() + "B");
	    			fileMap.put("LOCAL_PATH",localPath);
	    			fileMap.put("LOCAL_BACK_PATH",localBackPath);
	    			result.add(fileMap);
	    		}
	    	}
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
	public String showTraceFile(String localPath,String localBackPath,String fileName,String path) throws RemoteException,Exception {
		String tfile="";
		try{
			
			if(!StringUtils.isBlank(fileName) && !StringUtils.isBlank(path)){
		    	String dir = null;
		    	if(path.equalsIgnoreCase("TRC_DATA")){//"/tmp/aitrc_tmp/data"
		    		dir = localPath;
		    	}else if(path.equalsIgnoreCase("TRC_BAK")){//"/tmp/aitrc_tmp/bak"
		    		dir = localBackPath;
		    	}
		    	
		    	File file = new File(dir + "/" + fileName);
		    	String clsAsResource = AppTraceAction.class.getName().replace('.', '/').concat(".class");
		    	String p1 = AppTraceAction.class.getClassLoader().getResource(clsAsResource).getPath();
		    	String p2 = StringUtils.substringBefore(p1,"WEB-INF")+"/mon/trace/tmp";
		    	File f = new File(p2);
		    	if (f!=null){
		    		File[] tmp = f.listFiles();
			    	if (tmp!=null){
			    		for (int i = 0; i < tmp.length; i++) {
				    		tmp[i].delete();
				    	}
			    	}
		    	}
		    	
		    	tfile=p2+"/"+file.getName();
		    	FileUtils.writeByteArrayToFile(new File(tfile),FileUtils.readFileToByteArray(file));	
		    }
			
		}catch(Exception e){
			log.error("Call AppTraceAction's Method showTraceFile has Exception :"+e.getMessage());
		}
		return tfile;
	}
	
	/**
	 * 将文件从数据目录移到备份目录
	 * @param fileBuffer:服务器ID+"|"+本地路径+"|"+本地备份路径+"|"+文件名
	 * @return
	 * @throws Exception
	 */
	public boolean moveTraceFile(Object[] fileBuffer) throws RemoteException,Exception {
		
		if (fileBuffer==null || fileBuffer.length<1)
			return true;
		boolean result=false;
		try{
			
			for (int i=0;i<fileBuffer.length;i++){
				String[] fileItem=StringUtils.split(fileBuffer[i].toString(),"|");
				if(!StringUtils.isBlank(fileItem[2])){
					String data="/tmp/aitrc_tmp/data";
					String bak="/tmp/aitrc_tmp/bak";
					String[] paths=this.getTrcPathSet();
					if (paths!=null && paths.length>1){
						data=paths[0];
						bak=paths[1];
					}
					
					if (StringUtils.isNotBlank(fileItem[0])){
						data =fileItem[0];//"/tmp/aitrc_tmp/data"						
					}
					if (StringUtils.isNotBlank(fileItem[1])){
						bak=fileItem[1];
					}
					File file = new File(data + "/" + fileItem[2]);
					File dir = new File(bak);
					file.renameTo(new File(dir, file.getName()));
			    }			
			}
			result=true;
			
		}catch(Exception e){
			log.error("Call AppTraceAction's Method moveTraceFile has Exception :"+e.getMessage());
			// "转移到备份目录失败"
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
	public boolean deleteTraceFileForBack() throws RemoteException,Exception {
		boolean result=false;
		try{
			boolean isAll = true;
			String bak="/tmp/aitrc_tmp/bak";
			String[] paths=this.getTrcPathSet();
			if (paths!=null && paths.length>1){
				bak=paths[1];
			}
			File f = new File(bak);
			File[] tmp = f.listFiles();
			for (int j = 0; j < tmp.length; j++) {
				boolean item =tmp[j].delete();
				isAll=isAll && item;
			}
			if(isAll){
				result=true;
			}else{
				result=false;
			}			
		}catch(Exception e){
			log.error("Call AppTraceAction's Method moveTraceFile has Exception :"+e.getMessage());
			// "删除备份目录失败"
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
	public List getTraceTreeXml(String file) throws RemoteException,Exception{
		List result=null;
		try{

			InputStream in=new FileInputStream(new File(file));
			InputStreamReader strInStream = new InputStreamReader(in, "GBK"); 
			if (in==null)
				return result;
			ITrace trace=TracePoJoApi.parseTracePoJo(strInStream);
			if (trace==null)
				return result;
			result=new ArrayList(1);
			trace.buildTree(result);
		}catch(Exception e){
			log.error("Call AppTraceAction's Method getTraceTreeXml has Exception :"+e.getMessage());
		}
		return result;
	}

	/**
	 * 读取采集文件的本地路径配置
	 * @return
	 * @throws Exception
	 */
	private String[] getTrcPathSet() throws Exception{
		String[] result=new String[]{"/tmp/aitrc_tmp/data","/tmp/aitrc_tmp/bak"};
		IAIMonColgRuleSV colgRuleSV=(IAIMonColgRuleSV)ServiceFactory.getService(IAIMonColgRuleSV.class);
		IBOAIMonColgRuleValue ruleValue=colgRuleSV.getDefaultColgRuleByType("CTRACE");
		if (ruleValue!=null){			
			if (StringUtils.isNotBlank(ruleValue.getExpr1())){
				String localPath=null,localBackPath=null;
				String lastChar=StringUtils.substring(ruleValue.getExpr1(),ruleValue.getExpr1().length()-1);
				if (lastChar.equals("/")){
					localPath=StringUtils.substring(ruleValue.getExpr1(),0,ruleValue.getExpr1().length()-1);
				}else{
					localPath=ruleValue.getExpr1();
				}
				localBackPath=StringUtils.substring(localPath,0,localPath.lastIndexOf("/")+1)+"bak";
				if (StringUtils.isNotBlank(localPath))
					result[0]=localPath;
				if (StringUtils.isNotBlank(localBackPath))
					result[1]=localBackPath;
			}
		}
		return result;
	}
}
