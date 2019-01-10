package com.asiainfo.schedule.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

public class ImportItemsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServletContext sc;
	private String savePath;

	private static final Logger LOGGER = Logger
			.getLogger(TaskExportServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			imports(request, response);
		} catch (Exception e) {
			LOGGER.error("service failed", e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.service(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.service(request, response);
	}

	private void imports(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String fileName = "";
		String result = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			List<FileItem> items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				fileName = item.getName();
				InputStream in = item.getInputStream();
				result = batchAssign(in);
			}
			String str = "<html><head><link href=\""+request.getContextPath()+"/ecl/webframe-ecl-base.css\""+" rel=\""+"stylesheet\""
					+"type=\""+"text/css\""+" media=\""+"screen\""+" /></head>"
					+ "<body><div class=\""+"c_title\""+"><div class=\""+"text\""+">操作结果</div></div>"
					+ "<div class=\""+"c_box\""+" style=\""+"font:14px/1.5 Arial;height:95%;overflow-x:true;overflow-y:true;OVERFLOW:scroll;\""+">"+result+"</div>"
					+ "</body></html>"; 
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(str); 
			
		} catch (Exception e) {
			result = e.getMessage();
			LOGGER.error("文件导入失败", e);
		} finally {
			// 记录操作日志
			OperationLogUtils.addFrontEndLog(
					CommonConstants.OPERATE_MODULE_CONFIG,
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_TASK, fileName, "批量导入分片配置");
		}
	}

	public String batchAssign(InputStream in) throws Exception {
		Properties p = new Properties();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		p.load(br);
		
		List<String> sus = new ArrayList<String>();
		List<String> fail = new ArrayList<String>();
		IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory
				.getService(IAIMonServerSV.class);
		ISchedulerSV ssv = (ISchedulerSV) ServiceFactory
				.getService(ISchedulerSV.class);
		Map<String,List<String>> errorMap = new HashMap<String,List<String>>();
		for (Entry<Object, Object> e : p.entrySet()) {
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			try {
				if (key.indexOf("@@") < 0) {
					throw new Exception("\u7f16\u7801\u914d\u7f6e\u683c\u5f0f\u6709\u8bef");
				}
				String[] tmp = key.split("@@");
				if (tmp.length != 2) {
					throw new Exception("\u7f16\u7801\u914d\u7f6e\u683c\u5f0f\u6709\u8bef");
				}
				String taskCode = tmp[0];
				String taskItemCode = tmp[1];
				String serverId = value.trim();

				TaskBean task = ssv.getTaskInfoByTaskCode(taskCode);
				if (task == null) {
					throw new Exception("\u4efb\u52a1\u4e0d\u5b58\u5728");
				}

				String[] aa = ssv.getTaskItemsByTaskCode(taskCode);
				boolean isFound = false;
				if (aa != null && aa.length > 0) {
					for (String a : aa) {
						if (a.equals(taskItemCode)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					throw new Exception("\u4efb\u52a1\u62c6\u5206\u9879\u4e0d\u5b58\u5728");
				}

				boolean code = serverSv.isExistServerByCode(serverId);
				if (!code) {
					throw new Exception("\u914d\u7f6e\u7684\u670d\u52a1\u7f16\u7801\u4e0d\u5b58\u5728");
				}
				ssv.assignServer2TaskItem(taskCode, taskItemCode, serverId);

				sus.add(key);
			} catch (Throwable t) {
				System.err.println(key + ",\u5bfc\u5165\u5931\u8d25！");
				fail.add(key);
				if(errorMap.containsKey(t.getMessage())){
					errorMap.get(t.getMessage()).add(key);
				}else{
					List<String> list = new ArrayList<String>();
					list.add(key);
					errorMap.put(t.getMessage(), list);
				}
			} 
		}
		StringBuilder result = new StringBuilder();
		result.append("\u6279\u91cf\u5bfc\u5165\u7684\u603b\u6761\u6570=").append(p.size()).append("<br/>");
		result.append("\u66f4\u65b0\u6210\u529f\u6570：").append(sus.size()).append("<br/>");
		result.append("\u66f4\u65b0\u5931\u8d25\u6570：").append(fail.size()).append("<br/>");
		if(!errorMap.isEmpty()){
			result.append("\u5931\u8d25\u8be6\u7ec6\u539f\u56e0\u5982\u4e0b：").append("<br/>");
			for(Entry<String, List<String>> e : errorMap.entrySet()){
				result.append(e.getKey()).append("：");
				for(int i=0;i<e.getValue().size();i++){
					result.append(e.getValue().get(i));
					if(i==e.getValue().size()-1){
						result.append("<br/>");
					}else if(i>0 && (i+1)%4==0){
						result.append("；").append("<br/>");
					}else
						result.append("；");
					}
				}
			}
		return result.toString();
	}
}
