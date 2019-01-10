package com.asiainfo.schedule.view;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.common.abo.ivalues.IBOSchedulerOperationsValue;
import com.asiainfo.common.service.interfaces.IABGMonWTriggerSV;
import com.asiainfo.common.service.interfaces.ISchedulerOperationsSV;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.ExcelWriter;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;


/**
 * 现在为所有导出方法的共用servlet
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public  class TaskExportServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(TaskExportServlet.class);
	
	//未配置分组：为找不到对应分组任务配置的默认分组
	private static final String NO_GROUP_BELONGS = "\u672a\u914d\u7f6e\u5206\u7ec4"; 
	
	/** 页面静态数据map */
	private static Map<String, Map<String, String>> staticDataMap;
	
	public static void main(String[] args) throws Exception
    {
	    exportHost(null);
    }
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//获取操作类型，有新的导出可以自行添加进判断分支
			String opertion = request.getParameter("opertion");
			try {
				if (StringUtils.isBlank(opertion)){
					LOGGER.error("export failed, opertion is null");
				}else if ("taskExport".equalsIgnoreCase(opertion.trim())){
					export(response);
				}else if("hostExport".equalsIgnoreCase(opertion.trim())) {
                    exportHost(response);
                }else if("operationExport".equalsIgnoreCase(opertion.trim())){
                	exportOperation(request, response);
                }else if("triggerInfoExport".equalsIgnoreCase(opertion.trim())){
                	exportTrigger(request, response);
                }
			}catch (Exception e) {
				LOGGER.error("service failed",e);
			}
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
	    			this.service(request,response);
			  }
			 
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
			    this.service(request,response);
			}
	
	/**
	 * 导出告警信息
	 * @param response
	 * @throws Exception
	 */
	 private static void exportTrigger(HttpServletRequest request, HttpServletResponse response) throws Exception
	 {
	        //创建workbook
	        HSSFWorkbook wb = ExcelWriter.createWorkBook();
	        
	        OutputStream out = response.getOutputStream();
	        try{
	        	JSONArray jsonArrayOper = exportTriggerInfo(request);
	        	if(jsonArrayOper.size() == 0) {
	        		response.reset();
	        		String str = "<html><head><link href=\""+request.getContextPath()+"/ecl/webframe-ecl-base.css\""+" rel=\""+"stylesheet\""
	    					+"type=\""+"text/css\""+" media=\""+"screen\""+" /></head>"
	    					+ "<body><div class=\""+"c_title\""+"><div class=\""+"text\""+">操作结果</div></div>"
	    					+ "<div class=\""+"c_box\""+" style=\""+"font:14px/1.5 Arial;height:95%;overflow-x:true;overflow-y:true;OVERFLOW:scroll;\""+">&nbsp;查询数据为空！</div>"
	    					+ "</body></html>"; 
	    			response.setContentType("text/html;charset=UTF-8");
	    			response.getWriter().write(str); 
	            }else if(jsonArrayOper.size() > 65530){
	            	response.reset();
	        		String str = "<html><head><link href=\""+request.getContextPath()+"/ecl/webframe-ecl-base.css\""+" rel=\""+"stylesheet\""
	    					+"type=\""+"text/css\""+" media=\""+"screen\""+" /></head>"
	    					+ "<body><div class=\""+"c_title\""+"><div class=\""+"text\""+">操作结果</div></div>"
	    					+ "<div class=\""+"c_box\""+" style=\""+"font:14px/1.5 Arial;height:95%;overflow-x:true;overflow-y:true;OVERFLOW:scroll;\""+">&nbsp;超过Excel导出限制，请缩小范围！</div>"
	    					+ "</body></html>"; 
	    			response.setContentType("text/html;charset=UTF-8");
	    			response.getWriter().write(str); 
	            }else{
	            	//导出操作日志信息
	            	wb = writeTriggerInfos(wb, jsonArrayOper);
	            	
	            	//获取输出流
	            	String disposition = "attachment;filename=" + URLEncoder.encode("triggerInfoDatas.xls", "UTF-8");
	            	response.setHeader("Content-disposition", disposition);
	            	response.setContentType("application/vnd.ms-excel");
	            	//OutputStream out = new FileOutputStream("E:\\1.xls");
	            	//输出生成文件
	            	out = ExcelWriter.write2stream(wb, out);
	            	out.flush();
	            }
	        	
	        }catch(Exception e) {
	            LOGGER.error("exprot to excel failed", e);
	            throw e;
	        }finally {
                out.close();
            }
	 }
	 
	 /**
	 * 需要导出的告警信息
	 * @return
	 * @throws Exception
	 */
	 public static JSONArray exportTriggerInfo(HttpServletRequest request) throws Exception
	 {
		 request.setCharacterEncoding("utf-8");
		 
		 String startTime = request.getParameter("beginDate");
		 String endTime = request.getParameter("endDate");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Timestamp start = null;
		 Timestamp end = null;
		 if (StringUtils.isNotBlank(startTime)){
		  	start =  new Timestamp(sdf.parse(startTime).getTime());
		 }
		 if (StringUtils.isNotBlank(endTime)){
		  	end =  new Timestamp(sdf.parse(endTime).getTime());
		 }
		 if (start != null && end != null){
		  	if (start.after(end)){
		  		throw new Exception("开始时间要小于结束时间");
		  	}
		 }
		 
		 String ip = "";
		 String infoName = "";
		 String hostName = "";
		 String beginDate = "";
		 String endDate = "";
		 if(StringUtils.isNotBlank(request.getParameter("ip"))){
			 ip = request.getParameter("ip");
		 }
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("infoName")))){
			 infoName = strEncode(request.getParameter("infoName"));
		 }
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("hostName")))){
			 hostName = strEncode(request.getParameter("hostName"));
		 }
		 if(StringUtils.isNotBlank(request.getParameter("beginDate"))){
			 beginDate = request.getParameter("beginDate");
		 }
		 if(StringUtils.isNotBlank(request.getParameter("endDate"))){
			 endDate = request.getParameter("endDate");
		 }
		 
		 IABGMonWTriggerSV sv = (IABGMonWTriggerSV) ServiceFactory.getService(IABGMonWTriggerSV.class);
	     IBOABGMonWTriggerValue[] values = sv.getTriggerValuesByIpInfoName(ip, infoName, hostName, beginDate, endDate, -1, -1);
		 IDataBus bus = null;
		 if (values == null)
			bus = DataBusHelper.getEmptyArrayDataBus();
		 else
			bus = DataBusHelper.getDataBusByBeans(values, IBOSchedulerOperationsValue.class);
			JSONArray array = bus.getDataArray();
		 return array;
	 }
	 
	 
	 /**
	  * 告警信息导出
	  * @param wb
	  * @param jsonArrayOper
	  * @return
	  * @throws Exception
	  */
	 private static HSSFWorkbook writeTriggerInfos(HSSFWorkbook wb, JSONArray jsonArrayOper) throws Exception
	 {
	   HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u544a\u8b66\u4fe1\u606f");//告警信息
	     //创建表头
	     sheet = createTriggerInfoHeader(sheet);
	     for(int i=0;i<jsonArrayOper.size();i++) {
	         JSONObject object= (JSONObject)jsonArrayOper.get(i);
	         HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
	         ExcelWriter.writeCell(row, object.get("triggerId"), -1);
	         ExcelWriter.writeCell(row, object.get("ip"), -1);
	         ExcelWriter.writeCell(row, object.get("infoName"), -1);
	         ExcelWriter.writeCell(row, object.get("content"), -1);
	         ExcelWriter.writeCell(row, object.get("warnLevel"), -1);
	         ExcelWriter.writeCell(row, object.get("createDate"), -1);
	         ExcelWriter.writeCell(row, object.get("remarks"), -1);
	     }
	     return wb;
	 } 
	 
	 //导出告警信息的表头
	 private static HSSFSheet createTriggerInfoHeader(HSSFSheet sheet) throws Exception
	 {
	     HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
	     ExcelWriter.writeCell(row, "\u89e6\u53d1\u6807\u8bc6",-1);//触发标识
	     ExcelWriter.writeCell(row, "IP", -1);//IP
	     ExcelWriter.writeCell(row, "\u4fe1\u606f\u540d\u79f0", -1);//信息名称
	     ExcelWriter.writeCell(row, "\u5185\u5bb9", -1);//内容
	     ExcelWriter.writeCell(row, "\u544a\u8b66\u7ea7\u522b", -1);//告警级别
	     ExcelWriter.writeCell(row, "\u521b\u5efa\u65e5\u671f", -1);//创建日期
	     ExcelWriter.writeCell(row, "\u5907\u6ce8", -1);//备注
	     return sheet;
	 }
	
	/**
	 * 导出操作日志信息
	 * @param response
	 * @throws Exception
	 */
	 private static void exportOperation(HttpServletRequest request, HttpServletResponse response) throws Exception
	 {
	        //创建workbook
	        HSSFWorkbook wb = ExcelWriter.createWorkBook();
	        
	        OutputStream out = response.getOutputStream();
	        try{
	        	JSONArray jsonArrayOper = exportOperInfo(request);
	        	if(jsonArrayOper.size() == 0) {
	        		response.reset();
	        		String str = "<html><head><link href=\""+request.getContextPath()+"/ecl/webframe-ecl-base.css\""+" rel=\""+"stylesheet\""
	    					+"type=\""+"text/css\""+" media=\""+"screen\""+" /></head>"
	    					+ "<body><div class=\""+"c_title\""+"><div class=\""+"text\""+">操作结果</div></div>"
	    					+ "<div class=\""+"c_box\""+" style=\""+"font:14px/1.5 Arial;height:95%;overflow-x:true;overflow-y:true;OVERFLOW:scroll;\""+">&nbsp;查询数据为空！</div>"
	    					+ "</body></html>"; 
	    			response.setContentType("text/html;charset=UTF-8");
	    			response.getWriter().write(str); 
	            }else{
	            	//导出操作日志信息
	            	wb = writeOperInfos(wb, jsonArrayOper);
	            	
	            	//获取输出流
	            	String disposition = "attachment;filename=" + URLEncoder.encode("operationLogDatas.xls", "UTF-8");
	            	response.setHeader("Content-disposition", disposition);
	            	response.setContentType("application/vnd.ms-excel");
	            	//OutputStream out = new FileOutputStream("E:\\1.xls");
	            	//输出生成文件
	            	out = ExcelWriter.write2stream(wb, out);
	            	out.flush();
	            }
	        	
	        }catch(Exception e) {
	            LOGGER.error("exprot to excel failed", e);
	            throw e;
	        }finally {
                out.close();
            }
	 }
	 
	 /**
	 * 需要导出的操作日志信息
	 * @return
	 * @throws Exception
	 */
	 public static JSONArray exportOperInfo(HttpServletRequest request) throws Exception
	 {
		 ISchedulerOperationsSV sv = (ISchedulerOperationsSV)ServiceFactory.getService(ISchedulerOperationsSV.class);
		 request.setCharacterEncoding("utf-8");
		 
		 String startTime = request.getParameter("startTime");
		 String endTime = request.getParameter("endTime");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Timestamp start = null;
		 Timestamp end = null;
		 if (StringUtils.isNotBlank(startTime)){
		  	start =  new Timestamp(sdf.parse(startTime).getTime());
		 }
		 if (StringUtils.isNotBlank(endTime)){
		  	end =  new Timestamp(sdf.parse(endTime).getTime());
		 }
		 if (start != null && end != null){
		  	if (start.after(end)){
		  		throw new Exception("开始时间要小于结束时间");
		  	}
		 }
		 Map<String,Object> condition = new HashMap<String,Object>();
		 //拼接表单数据
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("module")))){
			 condition.put(IBOSchedulerOperationsValue.S_OperationModel, strEncode(request.getParameter("module")));
		 }
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("operType")))){
			 condition.put(IBOSchedulerOperationsValue.S_OperationType, strEncode(request.getParameter("operType")));
		 }
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("operObj")))){
			 condition.put(IBOSchedulerOperationsValue.S_OperationObjectType, strEncode(request.getParameter("operObj")));
		 }
		 if(StringUtils.isNotBlank(request.getParameter("content"))){
			 condition.put(IBOSchedulerOperationsValue.S_OperationObjectContent, "%"+request.getParameter("content")+"%");
		 }
		 if(StringUtils.isNotBlank(request.getParameter("operationClientIp"))){
			 condition.put(IBOSchedulerOperationsValue.S_OperationClientIp, "%"+request.getParameter("operationClientIp")+"%");
		 }
		 if(StringUtils.isNotBlank(strEncode(request.getParameter("operator")))){
			 condition.put(IBOSchedulerOperationsValue.S_Operator, strEncode(request.getParameter("operator")));
		 }
		 if (start != null)
		 {
			 condition.put(CommonConstants.OPERATION_QRY_START, start);
		 }
		 if (end != null)
		 {
			 condition.put(CommonConstants.OPERATION_QRY_END, end);
		 }		 
		 
		 IBOSchedulerOperationsValue[] values = sv.getBeanByCondition(condition, -1, -1);
		 IDataBus bus = null;
		 if (values == null)
			bus = DataBusHelper.getEmptyArrayDataBus();
		 else
			bus = DataBusHelper.getDataBusByBeans(values, IBOSchedulerOperationsValue.class);
			JSONArray array = bus.getDataArray();
		 return array;
	 }
	 
	 public static String strEncode(String str) throws Exception{
		 return new String(str.getBytes("ISO-8859-1"),"UTF-8");
	 }
	 
	 /**
	  * 操作日志信息导出
	  * @param wb
	  * @param jsonArrayOper
	  * @return
	  * @throws Exception
	  */
	 private static HSSFWorkbook writeOperInfos(HSSFWorkbook wb, JSONArray jsonArrayOper) throws Exception
	 {
	   HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u64cd\u4f5c\u65e5\u5fd7\u4fe1\u606f");//操作日志信息
	     //创建表头
	     sheet = createOperInfoHeader(sheet);
	     for(int i=0;i<jsonArrayOper.size();i++) {
	         JSONObject object= (JSONObject)jsonArrayOper.get(i);
	         HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
	         ExcelWriter.writeCell(row, object.get("operationModel"), -1);
	         ExcelWriter.writeCell(row, object.get("operationType"), -1);
	         ExcelWriter.writeCell(row, object.get("operationObjectType"), -1);
	         ExcelWriter.writeCell(row, object.get("operationObjectContent"), -1);
	         ExcelWriter.writeCell(row, object.get("createDate"), -1);
	         ExcelWriter.writeCell(row, object.get("operator"), -1);
	         ExcelWriter.writeCell(row, object.get("operationClientIp"), -1);
	         ExcelWriter.writeCell(row, object.get("remarks"), -1);
	     }
	     return wb;
	 } 
	 
	  //导出操作日志信息的表头
	 private static HSSFSheet createOperInfoHeader(HSSFSheet sheet) throws Exception
	 {
	     HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
	     ExcelWriter.writeCell(row, "\u6a21\u5757",-1);//模块
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u7c7b\u578b", -1);//操作类型
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u5bf9\u8c61\u7c7b\u578b", -1);//操作对象类型
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u5bf9\u8c61\u6807\u8bc6", -1);//操作对象标识
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u65f6\u95f4", -1);//操作时间
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u5458", -1);//操作员
	     ExcelWriter.writeCell(row, "\u64cd\u4f5c\u5ba2\u6237\u7aef"+"IP", -1);//操作客户端IP
	     ExcelWriter.writeCell(row, "\u5907\u6ce8", -1);//备注
	     return sheet;
	 }
	 
	/**
	 * 导出主机信息
	 * @param response
	 * @throws Exception
	 */
    private static void exportHost(HttpServletResponse response) throws Exception
    {
        ISchedulerSV sv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
        //创建workbook
        HSSFWorkbook wb = ExcelWriter.createWorkBook();
        try {
            JSONArray jsonArrayHost = CommonSvUtil.exportHostInfo();
            HashMap<String, String> map=CommonSvUtil.exportMasterSlaveInfo();
            if(jsonArrayHost.size() == 0) {
                return;
            }
            //导出主机信息
            wb = writeHostInfos(wb, jsonArrayHost);

            //导出主备机关系
            wb = writeMasterSlaveInfos(wb,map);

            //获取输出流
            String disposition = "attachment;filename=" + URLEncoder.encode("hostDatas.xls", "UTF-8");
            response.setHeader("Content-disposition", disposition);
            response.setContentType("application/vnd.ms-excel");
            OutputStream out = response.getOutputStream();
            //OutputStream out = new FileOutputStream("E:\\1.xls");
            try {
                //输出生成文件
                out = ExcelWriter.write2stream(wb, out);
                out.flush();
            }
            catch(Exception e) {
                LOGGER.error("write to stream failed", e);
                throw e;
            }
            finally {
                out.close();
            }
        }
        catch(Exception e) {
            LOGGER.error("exprot to excel failed", e);
            throw e;
        }
    }
    
    /**
     * 导出主备机信息
     * @param wb
     * @param map
     * @return
     * @throws Exception
     */
	private static HSSFWorkbook writeMasterSlaveInfos(HSSFWorkbook wb, HashMap<String, String> map) throws Exception
    {
	    HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u4e3b\u5907\u673a\u4fe1\u606f");
        //创建表头
        sheet = createMasterSlaveInfoHeader(sheet);
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()) {
            String masterInfo=(String) iterator.next();
            String slaveInfo=(String)map.get(masterInfo);
            sheet=createMasterSlaveInfos(sheet,masterInfo,slaveInfo);
        }
        return wb;
    }
	/**
	 * 主备机信息的字段名
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
    private static HSSFSheet createMasterSlaveInfoHeader(HSSFSheet sheet) throws Exception
    {
        HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
        ExcelWriter.writeCell(row,"\u4e3b\u673a\u540d\u79f0",-1);//主机名称（主机ip）
        ExcelWriter.writeCell(row,"\u4e3b\u673aIP",-1);//主机ip
        ExcelWriter.writeCell(row,"\u5907\u673a\u540d\u79f0",-1);//备机名称
        ExcelWriter.writeCell(row,"\u5907\u673aIP",-1);//备机ip
        return sheet;
    }
    /**
     * 写入主备机信息
     * @param sheet
     * @param masterInfo
     * @param slaveInfo
     * @return
     * @throws Exception
     */
    private static HSSFSheet createMasterSlaveInfos(HSSFSheet sheet, String masterInfo, String slaveInfo) throws Exception
    {
        HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
        String[] mInfo=masterInfo.split("#");
        String[] sInfo=slaveInfo.split("#");
        if(mInfo!=null&&mInfo.length==2&&sInfo!=null&&sInfo.length==2) {
            ExcelWriter.writeCell(row,mInfo[0],-1);//主机名称
            ExcelWriter.writeCell(row, mInfo[1], -1);//主机IP
            ExcelWriter.writeCell(row,sInfo[0],-1);//备机名称
            ExcelWriter.writeCell(row, sInfo[1], -1);
        }
        return sheet;
    }
    
    /**
     * 分组、主机、应用信息导出
     * @param wb
     * @param jsonArrayHost
     * @return
     * @throws Exception
     */
    private static HSSFWorkbook writeHostInfos(HSSFWorkbook wb, JSONArray jsonArrayHost) throws Exception
    {
	    HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u5206\u7ec4\u3001\u4e3b\u673a\u3001\u5e94\u7528\u4fe1\u606f");//分组、主机、应用信息
        //创建表头
        sheet = createHostInfoHeader(sheet);
        for(int i=0;i<jsonArrayHost.size();i++) {
            JSONArray jsonArrayGroup=(JSONArray) jsonArrayHost.get(i);
            sheet=createHostInfos(sheet,jsonArrayGroup);
        }
        return wb;
    }
	/**
	 * 写入表格分组、主机、应用信息
	 * @param dtlSheet
	 * @param jsonArrayGroup
	 * @return
	 * @throws Exception
	 */
	private static HSSFSheet createHostInfos(HSSFSheet dtlSheet, JSONArray jsonArrayGroup) throws Exception
    {   
	    for(int i=0;i<jsonArrayGroup.size();i++) {
	        HSSFRow row = null;;
	        if(i==0) {
	            if (jsonArrayGroup.size() == 1) {
	                row = ExcelWriter.createNewRow(dtlSheet, -1);
	                JSONObject object=(JSONObject) jsonArrayGroup.get(i);
	                ExcelWriter.writeCell(row, object.get("group"), -1);
	            }
	        }else {
	            row = ExcelWriter.createNewRow(dtlSheet, -1);
	            JSONObject object=(JSONObject) jsonArrayGroup.get(0);
                ExcelWriter.writeCell(row, object.get("group"), -1);
	            JSONArray  hostArr=(JSONArray) jsonArrayGroup.get(i);
	            //判断主机是否为空
	            if(hostArr!=null&&hostArr.size()>0) {
	                for(int j=0;j<hostArr.size();j++) {
	                    if(j==0) {
	                        JSONObject hostObj=(JSONObject) hostArr.get(j);
	                        ExcelWriter.writeCell(row, hostObj.get("hostName"), -1);
	                        ExcelWriter.writeCell(row, hostObj.get("hostIp"), -1);
	                    }else {
	                        JSONArray  serverArr=(JSONArray) hostArr.get(j);
	                        if(serverArr!=null&&serverArr.size()>0) {
	                            for(int m=0;m<serverArr.size();m++) {
	                                JSONObject serObj=(JSONObject) serverArr.get(m);
	                                ExcelWriter.writeCell(row, serObj.get("serverCode"), -1);
	                            }
	                        }
	                    }
	                }
	            }
	            
	        }
	    }
        return dtlSheet;
    }

    //导出主机信息的表头
    private static HSSFSheet createHostInfoHeader(HSSFSheet sheet) throws Exception
    {
        HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
        ExcelWriter.writeCell(row,"\u5206\u7ec4\u540d\u79f0",-1);//分组名称
        ExcelWriter.writeCell(row, "\u4e3b\u673a\u540d\u79f0", -1);//主机名称
        ExcelWriter.writeCell(row, "\u4e3b\u673aIP", -1);//主机IP
        ExcelWriter.writeCell(row, "\u5e94\u7528\u7f16\u7801", -1);//应用编码
        return sheet;
    }

    /**
	 * 导出所有任务数据
	 * @param cycle
	 * @throws Exception
	 */
	private void export(HttpServletResponse response) throws Exception{
		ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
		
		//创建workbook
		HSSFWorkbook wb = ExcelWriter.createWorkBook();
		try{
			TaskGroupBean[] groups = sv.getAllTaskGroup();
			TaskBean[] tasks = sv.getAllValidTasks();
			//整合任务基本信息结构
			Map<String,List<TaskBean>> taskDatas = createTaskBaseDatas(groups, tasks);
			//没有数据就不导出
			if (taskDatas.isEmpty()){
				return;
			}
			
				//生成基础任务信息页
				wb = writeTaskBaseInfos(wb,taskDatas);
			
				//生成任务参数页
				wb = writeTaskParams(wb,sv,taskDatas);
				
				//生成分片对应应用配置页
				wb = writeSplitConfigs(wb, sv, tasks);
				
				//生成TF/RELOAD配置页
				wb = writeTFConfigs(wb, sv, taskDatas);
				
				
			
			
			//获取输出流
			String disposition = "attachment;filename=" + URLEncoder.encode("taskDatas.xls", "UTF-8");
	        response.setHeader("Content-disposition", disposition);   
	        response.setContentType("application/vnd.ms-excel");  
			OutputStream out = response.getOutputStream();
//			OutputStream out = new FileOutputStream("D:\\1.xls");
			try {
				//输出生成文件
				out = ExcelWriter.write2stream(wb, out);
				out.flush();
			} catch (Exception e) {
				LOGGER.error("write to stream failed",e);
				throw e;
			}finally{
				out.close();
			}
		}catch(Exception e){
			LOGGER.error("exprot to excel failed",e);
			throw e;
		}
	}
	
	

	/**
	 * 创建基础信息页
	 * @param wb
	 * @param sv
	 * @param taskDatas
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook writeTaskBaseInfos(HSSFWorkbook wb,Map<String,List<TaskBean>> taskDatas) throws Exception{
		HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u57fa\u672c\u4efb\u52a1\u914d\u7f6e\u4fe1\u606f");
		//创建表头
		sheet = createTaskBaseInfoHeader(sheet);
		for (Map.Entry<String, List<TaskBean>> entry : taskDatas.entrySet()) {
			//写任务内容
			for (TaskBean task : entry.getValue()) {
				sheet = createTaskBaseInfoRow(sheet,task,entry.getKey());
			}
		}
		return wb;
	}
	
	/**
	 * 分片对应关系导出页
	 * @param wb
	 * @param sv
	 * @param taskDatas
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook writeSplitConfigs(HSSFWorkbook wb, ISchedulerSV sv,
			TaskBean[] tasks) throws Exception{
		HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u5206\u7247\u5bf9\u5e94\u5e94\u7528\u914d\u7f6e");
		//创建表头
		sheet = createSplitConfigHeader(sheet);
		for (TaskBean task : tasks) {
			String[] items = sv.getTaskItemsByTaskCode(task.getTaskCode());
			if (items != null){
				for (String item : items) {
					String serverCode = sv.getServerCode(task.getTaskCode(), item);
					sheet = createSplitConfigRow(sheet,task.getTaskCode(),item,serverCode);
				}
			}
		}
		return wb;
	}

	/**
	 * 创建任务参数页
	 * @param wb
	 * @param sv
	 * @param taskDatas
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook writeTaskParams(HSSFWorkbook wb,ISchedulerSV sv,Map<String,List<TaskBean>> taskDatas) throws Exception{
		HSSFSheet sheet = ExcelWriter.createSheet(wb, "\u4efb\u52a1\u53c2\u6570\u914d\u7f6e");
		Map<TaskBean, Map<String, String>> tempMap = new HashMap<TaskBean, Map<String, String>>();//储存一个分组的结果
		sheet = createTaskParamsHeader(sheet);
		//按分组创建信息
		for (Map.Entry<String, List<TaskBean>> entry : taskDatas.entrySet()) {
			tempMap.clear();//清除上次记录的结果
			for (TaskBean task : entry.getValue()) {
				Map<String, String> map = sv.getTaskParam(task.getTaskCode());
				//若有参数则放入表中
				if (map!=null && !map.isEmpty()){
					tempMap.put(task,map);
				}
			}
			//分组下没有记录则进行下次循环
			if (tempMap.isEmpty()){
				continue;
			}
			//写任务内容
			for (Map.Entry<TaskBean,  Map<String, String>> ety : tempMap.entrySet()) {
				sheet = createTaskParamsRow(sheet,ety.getValue(),ety.getKey());
			}
		}
		return wb;
	}
	
	/**
	 * 创建tf配置页
	 * 根据数据可能关联创建tf配置详情页 映射关系页
	 * @param wb
	 * @param sv
	 * @return
	 */
	private HSSFWorkbook writeTFConfigs(HSSFWorkbook wb,ISchedulerSV sv,Map<String,List<TaskBean>> taskDatas) throws Exception{
		HSSFSheet sheet = ExcelWriter.createSheet(wb, "tf\u6216\u8005reload\u53c2\u6570\u914d\u7f6e");
		//创建表头
		Map<String, List<TaskBean>> tempMap = new HashMap<String,List<TaskBean>>();//储存过滤后的结果
		List<TaskBean> list = null;
		sheet = createTfConfigsHeader(sheet);
		//将所有数据按是否有tf reload类型任务过滤
		for (Map.Entry<String, List<TaskBean>> entry : taskDatas.entrySet()) {
			for (TaskBean task : entry.getValue()) {
				if ("tf".equalsIgnoreCase(task.getTaskType()) || "reload".equalsIgnoreCase(task.getTaskType())){
					if (tempMap.containsKey(entry.getKey())){
						list = tempMap.get(entry.getKey());
						list.add(task);
						tempMap.put(entry.getKey(), list);
					}else{
						list = new ArrayList<TaskBean>();
						list.add(task);
						tempMap.put(entry.getKey(), list);
					}
				}
			}
		}
		
		//记录是否有详细配置
		List<CfgTfDtl> details = new ArrayList<CfgTfDtl>();
		//过滤后还有结果则开始写内容
		if (!tempMap.isEmpty()){
			for (Map.Entry<String, List<TaskBean>> entry : tempMap.entrySet()) {
				for (TaskBean task : entry.getValue()) {
					CfgTf tf = sv.getCfgTf(task.getTaskCode());
					if (tf != null){
						sheet = createTFConfigsRow(sheet, tf, task);
						if (tf.getObjCfgTfDtl() != null && tf.getObjCfgTfDtl().length > 0){
							details.addAll(Arrays.asList(tf.getObjCfgTfDtl()));
						}
					}
				}
			}
		}
		
		//记录是否有映射关系
		Map<String,List<CfgTfMapping>> mappings = new HashMap<String, List<CfgTfMapping>>();
		//有详细配置则生成详细配置页
		if (!details.isEmpty()){
			HSSFSheet dtlSheet = ExcelWriter.createSheet(wb, "tf\u6216\u8005reload\u8be6\u7ec6\u914d\u7f6e");
			dtlSheet = createDetailHeader(dtlSheet);
			List<CfgTfMapping> tempList = null;
			for (CfgTfDtl dtl : details) {
				dtlSheet = createDetailRow(dtlSheet,dtl);
				if (dtl.getObjCfgTfMapping() != null && dtl.getObjCfgTfMapping() .length > 0){
					if (mappings.containsKey(dtl.getCfgTfCode())){
						tempList = mappings.get(dtl.getCfgTfCode());
						tempList.addAll(Arrays.asList(dtl.getObjCfgTfMapping()));
						mappings.put(dtl.getCfgTfCode(), tempList);
					}else{
						tempList = new ArrayList<CfgTfMapping>();
						tempList.addAll(Arrays.asList(dtl.getObjCfgTfMapping()));
						mappings.put(dtl.getCfgTfCode(), tempList);
					}
				}
			}
		}
		
		//若有映射关系则写映射关系页
		if (!mappings.isEmpty()){
			HSSFSheet dtlSheet = ExcelWriter.createSheet(wb, "\u6620\u5c04\u5173\u7cfb\u914d\u7f6e");
			dtlSheet = createMappingHeader(dtlSheet);
			for (Map.Entry<String, List<CfgTfMapping>> entry : mappings.entrySet()) {
				for (CfgTfMapping mapping:entry.getValue()) {
					dtlSheet = createMappingRow(dtlSheet,mapping,entry.getKey());
				}
			}
		}
		
		return wb;
	}
	

	/**
	 * 整合分组和任务 形成基本信息体
	 * @param groups
	 * @param tasks
	 * @return Map<String,List<TaskBean>> key是分组名 value是分组所属所有任务
	 * @throws Exception
	 */
	private Map<String,List<TaskBean>> createTaskBaseDatas(TaskGroupBean[] groups,TaskBean[] tasks) throws Exception{
		Map<String,List<TaskBean>> taskDatas = new HashMap<String, List<TaskBean>>();
		//没有任务 则方法终止
		if (tasks == null || tasks.length == 0){
			return taskDatas;
		}
		
		//整合成key为编码 value为名称的map
		Map<String,String> groupMap = new HashMap<String, String>();
		if(groups != null){
			for (TaskGroupBean group : groups) {
				groupMap.put(group.getGroupCode(), group.getGroupName());
			}
		}
		
		List<TaskBean> list = null;
		for (TaskBean task : tasks) {
			//匹配到分组的放入分组下 未匹配到的放入未配置分组下
			if (groupMap.containsKey(task.getTaskGroupCode())){
				//
				if (taskDatas.containsKey(groupMap.get(task.getTaskGroupCode()))){
					list = taskDatas.get(groupMap.get(task.getTaskGroupCode()));
					list.add(task);
					taskDatas.put(groupMap.get(task.getTaskGroupCode()), list);
				}else{
					list = new ArrayList<TaskBean>();
					list.add(task);
					taskDatas.put(groupMap.get(task.getTaskGroupCode()), list);
				}
			}else{
				if (taskDatas.containsKey(NO_GROUP_BELONGS)){
					list = taskDatas.get(NO_GROUP_BELONGS);
					list.add(task);
					taskDatas.put(NO_GROUP_BELONGS, list);
				}else{
					list = new ArrayList<TaskBean>();
					list.add(task);
					taskDatas.put(NO_GROUP_BELONGS, list);
				}
			}
			
		}
		return taskDatas;
	}
	
	/**
	 * 创建基础信息表表头
	 * @param sheet
	 * @return
	 */
	private HSSFSheet createTaskBaseInfoHeader(HSSFSheet sheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u5206\u7ec4",-1);//任务分组
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u540d\u79f0",-1);//任务名称
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u63cf\u8ff0",-1);//任务描述
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7c7b\u578b",-1);//任务类型
		ExcelWriter.writeCell(row,"\u7248\u672c",-1);//版本
		ExcelWriter.writeCell(row,"\u4f9d\u8d56\u4efb\u52a1\u7f16\u7801\u5217\u8868",-1);//依赖任务编码列表
		ExcelWriter.writeCell(row,"\u521b\u5efa\u65f6\u95f4",-1);//创建时间
		ExcelWriter.writeCell(row,"\u5f00\u59cb\u65f6\u95f4",-1);//开始时间
		ExcelWriter.writeCell(row,"\u7ed3\u675f\u65f6\u95f4",-1);//结束时间
		ExcelWriter.writeCell(row,"\u626b\u63cf\u95f4\u9694\u65f6\u95f4",-1);//扫描间隔时间
		ExcelWriter.writeCell(row,"\u6bcf\u6b21\u626b\u63cf\u6570",-1);//每次扫描数
		ExcelWriter.writeCell(row,"\u6bcf\u6b21\u6267\u884c\u6570",-1);//每次执行数
		ExcelWriter.writeCell(row,"\u7ebf\u7a0b\u6570",-1);//线程数
		ExcelWriter.writeCell(row,"\u662f\u5426\u6309\u5730\u5e02\u5206\u7247",-1);//是否按地市分片
		ExcelWriter.writeCell(row,"\u62c6\u5206\u7ec6\u9879",-1);//拆分细项
		ExcelWriter.writeCell(row,"\u4e1a\u52a1\u6267\u884c\u7c7b",-1);//业务执行类
		ExcelWriter.writeCell(row,"\u6545\u969c\u5904\u7406\u65b9\u5f0f",-1);//故障处理方式
		ExcelWriter.writeCell(row,"\u4f18\u5148\u7ea7",-1);//优先级
		ExcelWriter.writeCell(row,"\u662f\u5426\u8bb0\u5f55\u65e5\u5fd7",-1);//是否记录日志
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u72b6\u6001",-1);//任务状态
		return sheet;
	}
	
	/**
	 * 创建任务基础信息每行数据
	 * @param sheet
	 * @return
	 */
	private HSSFSheet createTaskBaseInfoRow(HSSFSheet sheet,TaskBean task,String groupName) throws Exception{
		if (task != null){
			HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
			ExcelWriter.writeCell(row, groupName, -1);
			ExcelWriter.writeCell(row, task.getTaskCode(), -1);
			ExcelWriter.writeCell(row, task.getTaskName(), -1);
			ExcelWriter.writeCell(row, task.getTaskDesc(), -1);
			ExcelWriter.writeCell(row, task.getTaskType(), -1);
			ExcelWriter.writeCell(row, task.getVersion(), -1);
			ExcelWriter.writeCell(row, task.getDepends(), -1);
			ExcelWriter.writeCell(row, DateUtils.formatYYYYMMddHHmmss(task.getCreateTime()), -1);
			ExcelWriter.writeCell(row, task.getStartTime(), -1);
			ExcelWriter.writeCell(row, task.getEndTime(), -1);
			ExcelWriter.writeCell(row, task.getScanIntervalTime(), -1);
			ExcelWriter.writeCell(row, task.getScanNum(), -1);
			ExcelWriter.writeCell(row, task.getExecuteNum(), -1);
			ExcelWriter.writeCell(row, task.getThreadNum(), -1);
			ExcelWriter.writeCell(row, getStaticDataMap().get("splitRegion").get(String.valueOf(task.isSplitRegion())), -1);
			ExcelWriter.writeCell(row, StringUtils.join(task.getItems(), ","), -1);
			ExcelWriter.writeCell(row, task.getProcessClass(), -1);
			ExcelWriter.writeCell(row, getStaticDataMap().get("faultProcessMethod").get(task.getFaultProcessMethod()), -1);
			ExcelWriter.writeCell(row, task.getPriority(), -1);
			ExcelWriter.writeCell(row, getStaticDataMap().get("splitRegion").get(String.valueOf(task.isLog())), -1);
			ExcelWriter.writeCell(row, getStaticDataMap().get("taskState").get(task.getState()), -1);
		}
		return sheet;
	}
	
	
	/**
	 * 创建参数信息表表头
	 * @param sheet
	 * @return
	 */
	private HSSFSheet createTaskParamsHeader(HSSFSheet sheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u540d\u79f0",-1);//任务名称
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row, "\u4efb\u52a1\u53c2\u6570...", -1);//任务参数...
		return sheet;
	}
	
	/**
	 * 创建参数信息内容
	 * @param sheet
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet createTaskParamsRow(HSSFSheet sheet,Map<String, String> map,TaskBean task) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row, task.getTaskName(), -1);
		ExcelWriter.writeCell(row, task.getTaskCode(), -1);
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.delete(0, sb.length());//清除上次记录内容
			sb.append(entry.getKey()).append("=").append(entry.getValue());
			ExcelWriter.writeCell(row, sb.toString(), -1);
		}
		return sheet;
	}
	
	
	/**
	 * 创建tf配置表头
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet createTfConfigsHeader(HSSFSheet sheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row, "\u6e90\u8868\u6570\u636e\u6e90", -1);//源表数据源
		ExcelWriter.writeCell(row, "\u6e90\u8868\u540d", -1);//源表名
		ExcelWriter.writeCell(row, "\u4e3b\u952e", -1);//主键
		ExcelWriter.writeCell(row, "\u67e5\u8be2sql", -1);//查询sql
		ExcelWriter.writeCell(row, "\u6267\u884csql", -1);//执行sql
		ExcelWriter.writeCell(row, "\u5b8c\u6210sql", -1);//完成sql
		ExcelWriter.writeCell(row,"\u9519\u8befsql", -1);//错误sql
		ExcelWriter.writeCell(row, "\u5907\u6ce8", -1);//备注
		return sheet;
	}
	
	/**
	 * 创建TF配置内容
	 * @param sheet
	 * @param key
	 * @return
	 */
	private HSSFSheet createTFConfigsRow(HSSFSheet sheet, CfgTf tf,TaskBean task) throws Exception{
		if (tf != null){
			HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
			ExcelWriter.writeCell(row, tf.getCfgTfCode(), -1);
			ExcelWriter.writeCell(row, tf.getSrcDbAcctCode(), -1);
			ExcelWriter.writeCell(row, tf.getSrcTableName(), -1);
			ExcelWriter.writeCell(row, tf.getPkColumns(), -1);
			ExcelWriter.writeCell(row, tf.getQuerySql(), -1);
			ExcelWriter.writeCell(row, tf.getProcessingSql(), -1);
			ExcelWriter.writeCell(row, tf.getFinishSql(), -1);
			ExcelWriter.writeCell(row, tf.getErrorSql(), -1);
			ExcelWriter.writeCell(row, tf.getRemarks(), -1);
		}
		return sheet;
	}

	/**
	 * 创建tf详细配置表头
	 * @param dtlSheet
	 * @return
	 */
	private HSSFSheet createDetailHeader(HSSFSheet dtlSheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(dtlSheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row, "\u8be6\u7ec6\u914d\u7f6e\u6807\u5fd7", -1);//详细配置标志
		ExcelWriter.writeCell(row, "\u6570\u636e\u6e90", -1);//数据源
		ExcelWriter.writeCell(row,"\u8868\u540d", -1);//表名
		ExcelWriter.writeCell(row, "tf\u7c7b\u578b", -1);//tf类型
		ExcelWriter.writeCell(row, "\u5b8c\u6210sql", -1);//完成sql
		ExcelWriter.writeCell(row, "\u5907\u6ce8", -1);//备注
		return dtlSheet;
	}
	
	/**
	 * 创建TF详细内容
	 * @param sheet
	 * @param key
	 * @return
	 */
	private HSSFSheet createDetailRow(HSSFSheet sheet, CfgTfDtl dtl) throws Exception{
		if (dtl != null){
			HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
			ExcelWriter.writeCell(row, dtl.getCfgTfCode(), -1);
			ExcelWriter.writeCell(row, dtl.getCfgTfDtlId(), -1);
			ExcelWriter.writeCell(row, dtl.getDbAcctCode(), -1);
			ExcelWriter.writeCell(row, dtl.getTableName(), -1);
			ExcelWriter.writeCell(row, dtl.getTfType(), -1);
			ExcelWriter.writeCell(row, dtl.getFinishSql(), -1);
			ExcelWriter.writeCell(row, dtl.getRemarks(), -1);
		}
		return sheet;
	}

	/**
	 * 创建映射表头
	 * @param dtlSheet
	 * @return
	 */
	private HSSFSheet createMappingHeader(HSSFSheet dtlSheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(dtlSheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row, "\u8be6\u7ec6\u914d\u7f6e\u6807\u5fd7", -1);//详细配置标志
		ExcelWriter.writeCell(row, "\u6620\u5c04\u6807\u5fd7", -1);//映射标志
		ExcelWriter.writeCell(row, "\u6e90\u5b57\u6bb5\u540d", -1);//源字段名
		ExcelWriter.writeCell(row, "\u76ee\u6807\u8868\u5b57\u6bb5", -1);//目标表字段
		ExcelWriter.writeCell(row, "\u5907\u6ce8", -1);//备注
		return dtlSheet;
	}
	
	/**
	 * 创建映射内容
	 * @param dtlSheet
	 * @param mapping
	 * @param taskCode
	 * @return
	 */
	private HSSFSheet createMappingRow(HSSFSheet dtlSheet,
			CfgTfMapping mapping, String taskCode) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(dtlSheet, -1);
		if (mapping != null){
			ExcelWriter.writeCell(row, taskCode, -1);
			ExcelWriter.writeCell(row, mapping.getCfgTfDtlId(), -1);
			ExcelWriter.writeCell(row, mapping.getMappingId(), -1);
			ExcelWriter.writeCell(row, mapping.getSrcColumnName(), -1);
			ExcelWriter.writeCell(row, mapping.getTfColumnName(), -1);
			ExcelWriter.writeCell(row, mapping.getRemarks(), -1);
		}
		return dtlSheet;
	}
	

	/**
	 * 创建分片表头
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet createSplitConfigHeader(HSSFSheet sheet) throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row,"\u4efb\u52a1\u7f16\u7801",-1);//任务编码
		ExcelWriter.writeCell(row, "\u62c6\u5206\u9879", -1);//拆分项
		ExcelWriter.writeCell(row, "\u5bf9\u5e94\u5e94\u7528\u7f16\u7801", -1);//对应应用编码
		return sheet;
	}
	
	/**
	 * 导出分片对应应用内容
	 * @param sheet
	 * @param taskCode
	 * @param item
	 * @param serverCode
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet createSplitConfigRow(HSSFSheet sheet,String taskCode,String item,String serverCode) 
			throws Exception{
		HSSFRow row = ExcelWriter.createNewRow(sheet, -1);
		ExcelWriter.writeCell(row, taskCode, -1);
		ExcelWriter.writeCell(row, item, -1);
		ExcelWriter.writeCell(row, serverCode == null?"":serverCode, -1);
		return sheet;
	}


	
	/**
	 * 获得静态数据map
	 * 
	 * @return
	 */
	private Map<String, Map<String, String>> getStaticDataMap() {
		if (null == staticDataMap) {
			staticDataMap = new HashMap<String, Map<String, String>>();
			// 加载故障处理方式map
			Map<String, String> faultProcessMethodMap = new HashMap<String, String>();
			faultProcessMethodMap.put("M", "\u4eba\u5de5\u5904\u7406");
			faultProcessMethodMap.put("A", "\u81ea\u52a8\u5904\u7406");
			staticDataMap.put("faultProcessMethod", faultProcessMethodMap);
			// 加载任务状态map
			Map<String, String> taskStateMap = new HashMap<String, String>();
			taskStateMap.put("U", "\u53ef\u7528");
			taskStateMap.put("E", "\u672a\u914d\u7f6e\u5b8c\u6210");
			staticDataMap.put("taskState", taskStateMap);
			// 是否地区分片map
			Map<String, String> splitRegionMap = new HashMap<String, String>();
			splitRegionMap.put("false", "\u5426");
			splitRegionMap.put("true", "\u662f");
			staticDataMap.put("splitRegion", splitRegionMap);
		}
		return staticDataMap;
	}

}
