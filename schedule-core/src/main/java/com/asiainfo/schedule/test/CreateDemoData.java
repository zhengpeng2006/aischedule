package com.asiainfo.schedule.test;

import java.sql.Timestamp;

import junit.framework.TestCase;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.core.po.CfgTfBean;
import com.asiainfo.schedule.core.po.CfgTfDtlBean;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

public class CreateDemoData extends TestCase {

	private static ISchedulerSV sv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);

	public static void main(String[] args) throws Exception {
		CreateDemoData c = new CreateDemoData();
		
//		DataManagerFactory.getDataManager().setStaticData("loginPassword", "12D1753443E190AAF0B1AB5D31394096");
//		DataManagerFactory.getDataManager().setTaskRunSts("demo_single_01", RunSts.stop.name());
//		 c.createTaskGroup();
//		 c.createTask();
//		 c.createComplexTask();
//		
//		 c.createTfTask();
//		 c.createTfDtl("demo_tf_01");
//		 c.createTfMapping("demo_tf_01", "dtl$0000000000");
////
////		 c.createReloadTask();
//		DataManagerFactory.getDataManager().setTaskRunSts("demo_tf_01", "start");
//		sv.assignServer2TaskItem("demo_tf_01", "571", "local_test_server");
//		sv.stopTask("demo_complex_01", "test");
//		sv.resumServer("local_test_server", "test");
//		ZKDataManagerFactory zk =	((ZKDataManagerFactory)DataManagerFactory.getDataManager());
//		zk.setStaticData("LOG_OUTPUT_FLAG", "Y");
//		zk.deleteTree("/ai-schedule-center-QQ/task/demo_tf_01");
		sv.startTaskNow("demo_tf_01",null, "test");
//		sv.enableAutoFault();
//		ServerBean serverHeart = DataManagerFactory.getDataManager().getServerRegistry(serverId, ServerType.processor);
		
		
	}

	public void createTask2() throws Exception {
		TaskBean task = new TaskBean();
		task.setTaskCode("demo_single_03");
		task.setTaskName("简单任务示例3");
		task.setTaskGroupCode("DemoGroup");
		task.setTaskType("single");
		task.setSplitRegion(true);
		task.setItems("0,1".split(","));// 按手机的mod(billid,2)=0||1
		task.setStartTime("0/1 -1 -1 -1 -1 -1");
		// task.setAssignType("M");
		task.setProcessClass("com.asiainfo.schedule.test.DemoSingleTask");
		task.setVersion(1);
		task.setFaultProcessMethod("A");
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setState("U");
		task.setThreadNum(1);
		sv.createTask(task);
	}

	public void createTask() throws Exception {
		TaskBean task = new TaskBean();
		task.setTaskCode("demo_single_01");
		task.setTaskName("简单任务示例");
		task.setTaskGroupCode("DemoGroup");
		task.setTaskType("single");
		task.setSplitRegion(false);
		task.setItems("0,1,2".split(","));
		task.setStartTime("0/3 -1 -1 -1 -1 -1");
		task.setEndTime("2/3 -1 -1 -1 -1 -1");
		// task.setAssignType("M");
		task.setProcessClass("com.asiainfo.schedule.test.DemoSingleTask");
		task.setVersion(1);
		task.setFaultProcessMethod("A");
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setState("U");
		task.setThreadNum(1);
		sv.createTask(task);
		sv.taskEffect("demo_single_01");
		sv.assignServer2TaskItem("demo_single_01", "0", "local_test_server");
	}

	public void createComplexTask() throws Exception {
		sv.deleteTask("demo_complex_01");
		TaskBean task = new TaskBean();
		task.setTaskCode("demo_complex_01");
		task.setTaskName("复杂任务示例");
		task.setTaskGroupCode("DemoGroup");
		task.setTaskType("complex");
		task.setSplitRegion(true);
		task.setStartTime("1/3 -1 -1 -1 -1 -1");
//		task.setEndTime("2/3 -1 -1 -1 -1 -1");
		task.setAssignType("M");
		task.setProcessClass("com.asiainfo.schedule.test.DemoComplexTask");
		task.setVersion(1);
		task.setFaultProcessMethod("A");
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setState("U");
		task.setThreadNum(2);
		task.setIdleSleepTime(60000);
		task.setScanIntervalTime(2000);
		task.setExecuteNum(10);
		task.setScanNum(100);
		sv.createTask(task);
		sv.taskEffect("demo_complex_01");
		sv.assignServer2TaskItem("demo_complex_01", "571", "local_test_server");

	}

	public void createTaskGroup() throws Exception {
		TaskGroupBean group = new TaskGroupBean();
		group.setGroupCode("DemoGroup");
		group.setGroupName("测试示例组");
		group.setCreateTime(DateUtils.formatYYYYMMddHHmmss(DateUtils.getCurrentDate()));
		sv.createTaskGroup(group);
	}

	public void createTfTask() throws Exception {

		TaskBean task = new TaskBean();
		task.setTaskCode("demo_tf_01");
		task.setTaskName("TF任务示例");
		task.setTaskGroupCode("demoGroup");
		task.setTaskType("tf");
		task.setSplitRegion(true);
		task.setStartTime("run$now");
		task.setAssignType("M");
		task.setProcessClass("com.asiainfo.schedule.test.DemoTfTask");
		task.setVersion(1);
		task.setFaultProcessMethod("M");
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setState("U");
		task.setThreadNum(2);
		task.setIdleSleepTime(60000);
		task.setScanIntervalTime(3000);
		task.setExecuteNum(2);
		task.setScanNum(100);
		sv.createTask(task);
		sv.assignServer2TaskItem("demo_tf_01", "570", "local_test_server");
		sv.taskEffect("demo_tf_01");
		// 创建tf信息
		CfgTfBean bean = new CfgTfBean();
		bean.setCfgTfCode(task.getTaskCode());
		bean.setErrorSql("insert_err_delete_src");
		bean.setPkColumns("user_id,number");
		bean.setQuerySql("select t.* from {TableName} t where mod(USER_ID,{MOD_MODE})={MOD_VALUE} ");
		bean.setSrcDbAcctCode("base");
		bean.setSrcTableName("ABG_MON_HOST_LOG_{YYYYMMM|CREATE_DATE|2}");
		bean.setCountSql("select count(1) as CNT from {TableName} t where mod(USER_ID,{MOD_MODE})={MOD_VALUE} ");
		sv.createOrUpdCfgTf(bean);
	}

	// 创建tf详情
	public void createTfDtl(String tfCode) throws Exception {
		CfgTfDtlBean bean = new CfgTfDtlBean();
		bean.setCfgTfCode(tfCode);
		bean.setDbAcctCode("base");
		bean.setTableName("DEMO_TF_DEST");
		bean.setTfType("DEST");
		DataManagerFactory.getDataManager().createCfgTfDtl(new CfgTfDtlBean[] { bean });
		bean = new CfgTfDtlBean();
		bean.setCfgTfCode(tfCode);
		bean.setDbAcctCode("so{CENTER}");
		bean.setTableName("DEMO_TF_HIS");
		bean.setTfType("HIS");
		sv.createCfgTfDtl(new CfgTfDtlBean[] { bean });
	}

	// 创建tf映射
	public void createTfMapping(String tfCode, String dtlId) throws Exception {
		CfgTfMapping[] mapping = new CfgTfMapping[2];
		mapping[0] = new CfgTfMapping();
		mapping[0].setCfgTfDtlId(dtlId);
		mapping[0].setSrcColumnName("USER_ID");
		mapping[0].setTfColumnName("OBJECT_ID");
		mapping[1] = new CfgTfMapping();
		mapping[1].setCfgTfDtlId(dtlId);
		mapping[1].setSrcColumnName("USER_NAME");
		mapping[1].setTfColumnName("OBJECT_NAME");
		sv.createCfgTfMapping(tfCode, mapping);
	}

	// 创建reload任务
	public void createReloadTask() throws Exception {
		TaskBean task = new TaskBean();
		task.setTaskCode("demo_reload_01");
		task.setTaskName("reload任务示例");
		task.setTaskGroupCode("DemoGroup");
		task.setTaskType("reload");
		task.setSplitRegion(false);
		task.setStartTime("0/5 -1 -1 -1 -1 -1");
		task.setAssignType("M");
		task.setVersion(1);
		task.setFaultProcessMethod("A");
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setState("U");
		task.setThreadNum(1);
		sv.createTask(task);
		sv.taskEffect("demo_reload_01");
		sv.assignServer2TaskItem("demo_reload_01", "0", "local_test_server");
		// 创建tf信息
		CfgTfBean bean = new CfgTfBean();
		bean.setCfgTfCode(task.getTaskCode());
		bean.setSrcDbAcctCode("so2");
		bean.setSrcTableName("DEMO_RELOAD_SRC");
		sv.createOrUpdCfgTf(bean);

		CfgTfDtlBean dtlbean = new CfgTfDtlBean();
		dtlbean.setCfgTfCode("demo_reload_01");
		dtlbean.setDbAcctCode("base");
		dtlbean.setTableName("DEMO_RELOAD_DEST");
		dtlbean.setTfType("DEST");
		sv.createCfgTfDtl(new CfgTfDtlBean[] { dtlbean });
	}

}
