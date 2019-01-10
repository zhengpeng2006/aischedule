package com.asiainfo.schedule.core.client.tf;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.center.CenterInfo;
import com.ai.appframe2.complex.center.interfaces.ICenter;
import com.ai.appframe2.complex.datasource.DataSourceTemplate;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal;
import com.asiainfo.schedule.core.client.interfaces.ISleepTimeController;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.template.defaults.table.DefaultTable;
import com.asiainfo.schedule.core.client.tf.template.defaults.table.DefaultTableQuery;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * tf 任务抽象类，提供业务侧继承实现
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月25日
 * @modify
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractTfTaskDeal implements IComplexTaskDeal<HashMap>, ISleepTimeController{
	private static final transient Logger logger = LoggerFactory.getLogger(AbstractTfTaskDeal.class);
	private TaskDealParam param;
	private CfgTf objCfgTf = null;
	private DefaultTable srcTableConfig = null;
	private DefaultTable[] hisTableConfig = null;
	private DefaultTable[] destTableConfig = null;

	@Override
	public boolean isFinishNoData() {
		return false;
	}

	@Override
	public long getTaskDataTotal() throws Exception {
		if(StringUtils.isNotBlank(srcTableConfig.getCountSql())){
			return srcTableConfig.count();
		}
		return -1;
	}

	@Override
	public void init(TaskDealParam _param) throws Exception {
		this.param = _param;
		this.objCfgTf = DataManagerFactory.getDataManager().getCfgTfByCode(param.getTaskCode());
		if (objCfgTf == null || CommonUtils.isBlank(objCfgTf.getSrcDbAcctCode()) || CommonUtils.isBlank(objCfgTf.getSrcTableName())) {
			throw new Exception("任务tf的相关配置关键属性不能为空！" + param.getTaskCode());
		}
		String tabName = this.objCfgTf.getSrcTableName();
		if (!CommonUtils.isBlank(tabName) && tabName.indexOf("{REGION_CODE}") > -1) {
			if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
				throw new Exception("任务未按地市分片，源表按地市分表无法执行！任务编码：" + param.getTaskCode() + ",源表名：" + tabName);
			}
			this.objCfgTf.setSrcTableName(tabName.replace("{REGION_CODE}", param.getRegionCode()));
		}
		// 处理数据源配置,按中心分
		String srcAcctCode = this.objCfgTf.getSrcDbAcctCode();
		if (!CommonUtils.isBlank(srcAcctCode) && srcAcctCode.indexOf(DataSourceTemplate.CENTER_FLAG) > -1) {
			if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
				throw new Exception("任务配置的数据源带有分中心标识，但未按地市拆分！任务编码：" + param.getTaskCode() + ",数据源：" + srcAcctCode);
			}

			ICenter centerClass = DataManagerFactory.getDataManager().getScheduleConfig().getCenterClass();
			if (centerClass == null) {
				throw new Exception("当前任务[" + param.getTaskCode() + "]数据源设置了分中心，必须配置中心的实现类！");
			}
			CenterInfo centerInfo = centerClass.getCenterByValue(param.getRegionCode());
			if (centerInfo == null) {
				throw new Exception("根据分片地市编码找不到中心！" + param.getRegionCode());
			}
			// 设置实际数据源
			this.objCfgTf.setSrcDbAcctCode(srcAcctCode.replace(DataSourceTemplate.CENTER_FLAG, centerInfo.getCenter()));
			// 处理历史、目的表

			CfgTfDtl[] dtls = this.objCfgTf.getObjCfgTfDtl();
			if (dtls != null && dtls.length > 0) {
				for (CfgTfDtl dtl : dtls) {
					String dbAcctCode = dtl.getDbAcctCode();
					if (!CommonUtils.isBlank(dbAcctCode) && dbAcctCode.indexOf(DataSourceTemplate.CENTER_FLAG) > -1) {
						dtl.setDbAcctCode(dbAcctCode.replace(DataSourceTemplate.CENTER_FLAG, centerInfo.getCenter()));
					}
				}
			}
		}
		// 处理数据源配置,按地市分
		srcAcctCode = this.objCfgTf.getSrcTableName();
		if (!CommonUtils.isBlank(srcAcctCode) && srcAcctCode.indexOf("{REGION_CODE}") > -1) {
			if (!param.isSplitRegion() || CommonUtils.isBlank(param.getRegionCode())) {
				throw new Exception("任务未按地市分片，源表数据源配置有误：" + param.getTaskCode() + "," + srcAcctCode);
			}
			// 设置实际数据源
			this.objCfgTf.setSrcDbAcctCode(srcAcctCode.replace("{REGION_CODE}", param.getRegionCode()));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("源表名：" + objCfgTf.getSrcTableName());
			logger.debug("源数据源名：" + objCfgTf.getSrcDbAcctCode());
			CfgTfDtl[] dtls = this.objCfgTf.getObjCfgTfDtl();
			if (dtls != null && dtls.length > 0) {
				for (CfgTfDtl dtl : dtls) {
					logger.debug(dtl.getTfType() + " 数据源:" + dtl.getDbAcctCode());
				}
			}
		}
		// 读取表信息
		DefaultTableQuery objTableQuery = new DefaultTableQuery(param, this.objCfgTf);
		srcTableConfig = objTableQuery.getSrcTable();
		hisTableConfig = objTableQuery.getHisTable();
		destTableConfig = objTableQuery.getDestTable();
	}

	@Override
	public List<HashMap> selectTasks() throws Exception {
		return srcTableConfig.query();
	}

	@Override
	public void execute(List<HashMap> tasks) throws Exception {
		if (tasks == null || tasks.size() == 0) {
			return;
		}
		HashMap[] taskArray = tasks.toArray(new HashMap[0]);
		try {
			ServiceManager.getSession().startTransaction();
			processTransform(taskArray);
			ServiceManager.getSession().commitTransaction();
		} catch (Throwable ex) {
			logger.error("转移数据出现异常", ex);

			ServiceManager.getSession().rollbackTransaction();
			try {
				ServiceManager.getSession().startTransaction();
				this.srcTableConfig.processError(taskArray, ex);
				ServiceManager.getSession().commitTransaction();
			} catch (Throwable ex2) {
				ServiceManager.getSession().rollbackTransaction();
				logger.error("记录错误异常", ex2);
			}

			try {
				ServiceManager.getSession().startTransaction();
				this.onError(taskArray, ex);
				ServiceManager.getSession().commitTransaction();
			} catch (Throwable ex2) {
				ServiceManager.getSession().rollbackTransaction();
				logger.error("调用ITfCallback接口方法出现异常", ex2);
			}
			throw new Exception(ex);
		}

	}

	private void processTransform(HashMap[] tasks) throws Throwable {

		// 开始业务调用
		long bizStart = System.currentTimeMillis();
		try {
			this.transform(tasks);
		} catch (Throwable ex) {
			logger.error("调用业务实现错误", ex);
			throw ex;
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("业务调用耗时:" + (System.currentTimeMillis() - bizStart));
			}
		}
		// 结束业务调用

		// 处理源表
		if (this.isSelfSrcFinish()) {
			this.srcFinish(tasks);
		} else {
			srcTableConfig.finish(tasks);
		}

		// 处理历史表
		if (this.isSelfHisFinish()) {
			this.hisFinish(tasks);
		} else {
			for (int i = 0; i < hisTableConfig.length; i++) {
				hisTableConfig[i].finish(tasks);
			}
		}

		// 处理目的表
		if (this.isSelfDestFinish()) {
			this.destFinish(tasks);
		} else {
			for (int i = 0; i < destTableConfig.length; i++) {
				destTableConfig[i].finish(tasks);
			}
		}

	}

	protected void srcFinish(HashMap[] tasks) {
	}

	protected void hisFinish(HashMap[] tasks) {

	}

	protected void destFinish(HashMap[] tasks) {

	}

	/**
	 * 兼容原高级模式，默认返回false
	 */
	protected boolean isSelfDestFinish() {
		return false;
	}

	/**
	 * 兼容原高级模式，默认返回false
	 */
	protected boolean isSelfHisFinish() {
		return false;
	}

	/**
	 * 兼容原高级模式，默认返回false
	 */
	protected boolean isSelfSrcFinish() {
		return false;
	}

	/**
	 * 业务实现的数据转移接口
	 * 
	 * @param tasks
	 * @throws Exception
	 */
	protected abstract void transform(HashMap[] tasks) throws Exception;

	/**
	 * 业务实现的数据转移出现异常时执行方法
	 * 
	 * @param tasks
	 * @throws Exception
	 */
	protected void onError(HashMap[] tasks, Throwable t) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("transform data error!data size:" + tasks.length);
		}
	}
	
	public TaskDealParam getTaskDealParam(){
		return this.param;
	}

	@Override
	public Comparator<HashMap> getComparator() {

		return new Comparator<HashMap>() {
			@Override
			public int compare(HashMap o1, HashMap o2) {
				return -1;
			}
		};
	}

	public int getSleepTime(long executeTaskCount, long taskDealTime){

		return new Long(taskDealTime).intValue();
	}

}
