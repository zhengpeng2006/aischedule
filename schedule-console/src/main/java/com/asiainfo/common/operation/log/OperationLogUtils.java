package com.asiainfo.common.operation.log;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.dialect.DialectFactory;
import com.ai.appframe2.common.SessionManager;
import com.asiainfo.logger.utils.LoggerUtils;

/**
 * 记录操作日志
 * 
 * @author 孙德东(24204)
 */
public class OperationLogUtils 
{
	
	private static transient Log LOG = LogFactory.getLog(OperationLogUtils.class);
	private static final String OPERATION_LOG_CODE = "AI.ZJ.AI_SCHEDULER_OPERATIONS";
	private OperationLogUtils(){}
	/**
	 * 前台界面操作调用该接口记录日志
	 * @param model
	 * @param type
	 * @param objectType
	 * @param objectContent
	 * @param remarks
	 */
	public static void addFrontEndLog(String model, String type, String objectType, String objectContent, String remarks) {
		String oprerator = SessionManager.getUser().getCode();
		String clientIP = SessionManager.getUser().getIP();
		addOperationLog("前端", model, type, objectType, objectContent, oprerator, clientIP, remarks);
	}

	/**
	 * 后端任务（非前台界面操作）调用该接口记录日志
	 * 参数涵义请参考@see addOperationLog
	 * @param model
	 * @param type
	 * @param objectType
	 * @param objectContent
	 * @param remarks
	 */
	public static void addBackEndLog(String model, String type, String objectType, String objectContent, String operator, String clientIP, String remarks) {
		addOperationLog("后端", model, type, objectType, objectContent, operator, clientIP, remarks);
	}
	
	/**
	 * 记录操作日志
	 * 参数涵义请参考@see addOperationLog
	 * @param source
	 * 操作来源：前台、后台
	 * @param model
	 * 操作模块：调度、监控、部署、启停、配置
	 * @param type
	 * 操作类型
                            配置模块分三种操作类型：新增、删除、修改
                            其他模块直接记录操作名称（比如主机初始化，发布，回滚，同步至当前版本，任务挂起）
	 * @param objectType
	 * 操作对象类型
                            部署： 直接记录为“节点”
                            启动/停止： 直接记录为“应用”
                            挂起：挂起的对象目前有应用和任务
                            新增/修改/删除：记录所操作的对象，如主机、节点、用户、部署策略
	 * @param objectContent
	 * 操作对象标识
                            部署：记录部署的节点列表（生成版本的地方单独记录一条配置日志）
                            启动/停止：记录批量/单个进程的进程编码
                            挂起：记录任务/应用编码
                            新增/修改/删除：所操作对象的id:code(name)
     * @param operator
     * 操作员
	 * @param remarks
	 * 前面的字段无法完整描述的操作，可以将剩余内容记录在此
	 */
	private static void addOperationLog(String source, String model, String type, String objectType, String objectContent, String operator, String clientIP, String remarks) {
		String split = LoggerUtils.getColumnSeperator();
		source = (source == null ? "" : source);
		model = (model == null ? "" : model);
		type = (type == null ? "" : type);
		objectType = (objectType == null ? "" : objectType);
		objectContent = (objectContent == null ? "" : objectContent);
		operator = (operator == null ? "" : operator);
		clientIP = (clientIP == null ? "" : clientIP);
		remarks = (remarks == null ? "" : remarks);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(source).append(split)
		  .append(model).append(split)
		  .append(type).append(split)
		  .append(objectType).append(split)
		  .append(objectContent).append(split)
		  .append(clientIP).append(split)
		  .append(LoggerUtils.formatCreateDate(new Date())).append(split)
		  .append(operator).append(split)
		  .append(remarks);
		if (LOG.isDebugEnabled()) {
			LOG.debug("记录操作日志：" + sb.toString());
		}
		if (DialectFactory.getDialect().getDatabaseType().equalsIgnoreCase(DialectFactory.ORACLE)) {
			LoggerUtils.addLog(OPERATION_LOG_CODE, sb.toString());
		} else {
			LoggerUtils.addLog("MYSQL.AI.ZJ.AI_SCHEDULER_OPERATIONS", sb.toString());
		}
	}
}
