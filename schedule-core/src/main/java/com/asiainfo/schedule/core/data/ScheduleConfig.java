package com.asiainfo.schedule.core.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.appframe2.complex.center.interfaces.ICenter;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.schedule.core.utils.Constants.TaskAssignType;

/**
 * 调度服务器配置信息
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月11日
 * @modify
 */
public class ScheduleConfig {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
	// 默认配置文件 classpath中获取
	private static final String defaultConfigName = "schedule.properties";
	private String dsType;
	private String zkRootPath;
	private String zkConnectString;
	private String zkUserName;
	private String zkPassword;
	private int zkSessionTimeOut;
	private int heartbeatInterval;
	private int deadInterval;
	private int scheduleThreadPoolSize;
	private int scheduleCheckThreadPoolSize;
	private String scheduleImplementClass;
	private String[] regionCodes;
	private ICenter center;
	private String tfSrcQuerySize;
	private String tfHisInsertAppend;

	private int faultProcessCount;
	private String logOutputFlag;
	private String threadThreshold;
	private String taskMaxAlarmTimes;
	private int itemJobAlarmTime;
	private String taskAlarmFlag;
	private Map<String,String> hostFsKpiPathMap;

	private String taskAssignType = TaskAssignType.Manual.name();
	@SuppressWarnings("serial")
	public static class ConfigException extends Exception {
		public ConfigException(String msg) {
			super(msg);
		}

		public ConfigException(String msg, Exception e) {
			super(msg, e);
		}
	}

	public void parse() throws ConfigException {
		this.parse(defaultConfigName);
	}

	public void parse(String path) throws ConfigException {

		logger.info("Reading configuration from: " + path);

		try {
			InputStream in = null;
			File configFile = new File(path);
			if (configFile.exists()) {
				in = new FileInputStream(configFile);
			} else {
				// from classpath
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			}
			if (in == null) {
				throw new IllegalArgumentException(path + " file is missing");
			}
			Properties cfg = new Properties();
			try {
				cfg.load(in);
			} finally {
				in.close();
			}
			parseProperties(cfg);
		} catch (IOException e) {
			throw new ConfigException("Error processing " + path, e);
		} catch (IllegalArgumentException e) {
			throw new ConfigException("Error processing " + path, e);
		}
	}

	public void parseProperties(Properties zkProp) throws IOException, ConfigException {
		for (Entry<Object, Object> entry : zkProp.entrySet()) {
			String key = entry.getKey().toString().trim();
			String value = entry.getValue().toString().trim();
			if (key.equals("dsType")) {
				this.dsType = value;
			} else if (key.equals("zookeeper.rootPath")) {
				this.zkRootPath = value;
			} else if (key.equals("zookeeper.connectString")) {
				this.zkConnectString = value;
			} else if (key.equals("zookeeper.userName")) {
				this.zkUserName = value;
			} else if (key.equals("zookeeper.password")) {
				this.zkPassword = value;
			} else if (key.equals("zookeeper.sessionTimeOut")) {
				this.zkSessionTimeOut = Integer.parseInt(value);
			} else if (key.equals("heartbeatInterval")) {
				this.heartbeatInterval = Integer.parseInt(value) * 1000;
			} else if (key.equals("deadInterval")) {
				this.deadInterval = Integer.parseInt(value) * 1000;
			} else if (key.equals("schedule.implement.class")) {
				this.scheduleImplementClass = value;
			} else if (key.equals("schedule.threadPoolSize")) {
				this.scheduleThreadPoolSize = Integer.parseInt(value);
			} else if (key.equals("schedule.checkThreadPoolSize")) {
				this.scheduleCheckThreadPoolSize = Integer.parseInt(value);
			} else if (key.equals("region")) {
				this.regionCodes = value.split(",");
			} else if (key.endsWith("centerClass")) {
				try {
					Object obj = Class.forName(value).newInstance();
					this.center = (ICenter) obj;
				} catch (Exception e) {
					throw new ConfigException("centerClass中心实现类配置错误！ " + value);
				}
			} else if (key.equals("tf.src.query.fetchSize=")) {
				this.tfSrcQuerySize = value;
			} else if (key.equals("tf.his.insert.append")) {
				this.tfHisInsertAppend = value;
			} else if (key.equals("schedule.fault.process.count")) {
				this.faultProcessCount = Integer.parseInt(value);
			} else if (key.equals("schedule.task.assign.type")) {
				this.taskAssignType = value;
			} else if (key.equals("complexTask.logOutputFlag")) {
				this.logOutputFlag = value;
			} else if (key.equals("processor.thread.threshold")) {
				this.threadThreshold = value;
			} else if (key.equals("processor.task.max.alarmWaitTimes")) {
				this.taskMaxAlarmTimes = value;
			} else if (key.equals("task.itemJob.alarmTime")) {
				this.itemJobAlarmTime = Integer.parseInt(value) * 1000;
			} else if (key.equals("task.alarmFlag")) {
				this.taskAlarmFlag = value;
			}else if (key.equals("host.Fs.kpi.path")) {
				this.hostFsKpiPathMap = getFsKpiPath(value);
			}
		}

		if (this.getDeadInterval() < this.getHeartbeatInterval() * 5) {
			throw new ConfigException("数据配置存在问题，死亡的时间间隔，至少要大于心跳线程的5倍。当前配置数据：deadInterval = " + this.getDeadInterval()
					+ ",heartbeatInterval = " + this.getHeartbeatInterval());
		}

		if (this.scheduleThreadPoolSize <= 0) {
			logger.warn("scheduleThreadPoolSize 小于0，默认设置为1。");
			this.scheduleThreadPoolSize = 1;
		}
		if (this.scheduleCheckThreadPoolSize <= 0) {
			logger.warn("scheduleCheckThreadPoolSize 小于0，默认设置为1。");
			this.scheduleCheckThreadPoolSize = 1;
		}
	}
	
	private static Map<String, String> getFsKpiPath(String value) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(value)) {
			return map;
		}
		try {
			String[] IpPaths = value.split(",");
			for (String ipPath : IpPaths) {
				String[] paths = ipPath.split("\\|");
				if (paths.length == 2) {
					map.put(paths[0], paths[1]);
				}
			}
		} catch (Exception e) {
			Log.error("getFsKpiPath err, e = " + e);
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(getFsKpiPath("10.5.7.70|/ve,10.5.7.77|/111"));
	}

	public String getDsType() {
		return dsType;
	}

	public String getZkRootPath() {
		return zkRootPath;
	}

	public String getZkConnectString() {
		return zkConnectString;
	}

	public String getZkUserName() {
		return zkUserName;
	}

	public String getZkPassword() {
		return zkPassword;
	}

	public int getZkSessionTimeOut() {
		return zkSessionTimeOut;
	}

	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public int getDeadInterval() {
		return deadInterval;
	}

	public int getScheduleThreadPoolSize() {
		return scheduleThreadPoolSize;
	}

	public int getScheduleCheckThreadPoolSize() {
		return scheduleCheckThreadPoolSize;
	}

	public String getScheduleImplementClass() {
		return scheduleImplementClass;
	}

	public String[] getRegionCodes() {
		return regionCodes;
	}

	public ICenter getCenterClass() {
		return center;
	}

	public String getTfSrcQuerySize() {
		return tfSrcQuerySize;
	}

	public String getTfHisInsertAppend() {
		return tfHisInsertAppend;
	}

	public int getFaultProcessCount() {
		return faultProcessCount;
	}

	public void setFaultProcessCount(int faultProcessCount) {
		this.faultProcessCount = faultProcessCount;
	}

	public String getTaskAssignType() {
		return taskAssignType;
	}

	public void setTaskAssignType(String taskAssignType) {
		this.taskAssignType = taskAssignType;
	}

	public String getLogOutputFlag() {
		return logOutputFlag;
	}

	public String getThreadThreshold() {
		return threadThreshold;
	}

	public String getTaskMaxAlarmTimes() {
		return taskMaxAlarmTimes;
	}

	public int getItemJobAlarmTime() {
		return itemJobAlarmTime;
	}

	public String getTaskAlarmFlag() {
		return taskAlarmFlag;
	}

	public Map<String, String> getHostFsKpiPathMap() {
		return hostFsKpiPathMap;
	}

	
}
