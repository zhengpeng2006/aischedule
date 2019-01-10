package com.asiainfo.deploy.common.bo;

import java.sql.Timestamp;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.deploy.common.ivalues.IBODeployStrategyHistoryValue;

public class BODeployStrategyHistoryBean extends DataContainer implements DataContainerInterface, IBODeployStrategyHistoryValue {

	private static String m_boName = "com.asiainfo.deploy.common.bo.BODeployStrategyHistory";

	public final static String S_FtpPassword = "FTP_PASSWORD";
	public final static String S_FtpHostPort = "FTP_HOST_PORT";
	public final static String S_FtpUserName = "FTP_USER_NAME";
	public final static String S_FtpPackagePath = "FTP_PACKAGE_PATH";
	public final static String S_ClientSbinPath = "CLIENT_SBIN_PATH";
	public final static String S_ClientBinPath = "CLIENT_BIN_PATH";
	public final static String S_DeployStrategyName = "DEPLOY_STRATEGY_NAME";
	public final static String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
	public final static String S_HistoryCreateTime = "HISTORY_CREATE_TIME";
	public final static String S_HistoryPackageNum = "HISTORY_PACKAGE_NUM";
	public final static String S_TemplateId = "TEMPLATE_ID";
	public final static String S_FtpHostIp = "FTP_HOST_IP";
	public final static String S_ServerPackagePath = "SERVER_PACKAGE_PATH";
	public final static String S_Remarks = "REMARKS";
	public final static String S_CreateTime = "CREATE_TIME";
	public final static String S_ModifyTime = "MODIFY_TIME";
	public final static String S_InstallRule = "INSTALL_RULE";
	public final static String S_OperatorId = "OPERATOR_ID";
	public final static String S_DeployStrategyHistoryId = "DEPLOY_STRATEGY_HISTORY_ID";
	public final static String S_ClientHomePath = "CLIENT_HOME_PATH";
	public final static String S_FtpProtocol = "FTP_PROTOCOL";
	public final static String S_ClientLogPath = "CLIENT_LOG_PATH";

	public final static String S_StopTemplateId = "STOP_TEMPLATE_ID";

	public static ObjectType S_TYPE = null;
	static {
		try {
			S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public BODeployStrategyHistoryBean() throws AIException {
		super(S_TYPE);
	}

	public static ObjectType getObjectTypeStatic() throws AIException {
		return S_TYPE;
	}

	@Override
	public void setObjectType(ObjectType value) throws AIException {
		throw new AIException("Cannot reset ObjectType");
	}

	public void initFtpPassword(String value) {
		this.initProperty(S_FtpPassword, value);
	}
	@Override
	public void setFtpPassword(String value) {
		this.set(S_FtpPassword, value);
	}
	public void setFtpPasswordNull() {
		this.set(S_FtpPassword, null);
	}

	@Override
	public String getFtpPassword() {
		return DataType.getAsString(this.get(S_FtpPassword));

	}
	public String getFtpPasswordInitialValue() {
		return DataType.getAsString(this.getOldObj(S_FtpPassword));
	}

	public void initFtpHostPort(int value) {
		this.initProperty(S_FtpHostPort, new Integer(value));
	}
	@Override
	public void setFtpHostPort(int value) {
		this.set(S_FtpHostPort, new Integer(value));
	}
	public void setFtpHostPortNull() {
		this.set(S_FtpHostPort, null);
	}

	@Override
	public int getFtpHostPort() {
		return DataType.getAsInt(this.get(S_FtpHostPort));

	}
	public int getFtpHostPortInitialValue() {
		return DataType.getAsInt(this.getOldObj(S_FtpHostPort));
	}

	public void initFtpUserName(String value) {
		this.initProperty(S_FtpUserName, value);
	}
	@Override
	public void setFtpUserName(String value) {
		this.set(S_FtpUserName, value);
	}
	public void setFtpUserNameNull() {
		this.set(S_FtpUserName, null);
	}

	@Override
	public String getFtpUserName() {
		return DataType.getAsString(this.get(S_FtpUserName));

	}
	public String getFtpUserNameInitialValue() {
		return DataType.getAsString(this.getOldObj(S_FtpUserName));
	}

	public void initFtpPackagePath(String value) {
		this.initProperty(S_FtpPackagePath, value);
	}
	@Override
	public void setFtpPackagePath(String value) {
		this.set(S_FtpPackagePath, value);
	}
	public void setFtpPackagePathNull() {
		this.set(S_FtpPackagePath, null);
	}

	@Override
	public String getFtpPackagePath() {
		return DataType.getAsString(this.get(S_FtpPackagePath));

	}
	public String getFtpPackagePathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_FtpPackagePath));
	}

	public void initClientSbinPath(String value) {
		this.initProperty(S_ClientSbinPath, value);
	}
	@Override
	public void setClientSbinPath(String value) {
		this.set(S_ClientSbinPath, value);
	}
	public void setClientSbinPathNull() {
		this.set(S_ClientSbinPath, null);
	}

	@Override
	public String getClientSbinPath() {
		return DataType.getAsString(this.get(S_ClientSbinPath));

	}
	public String getClientSbinPathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_ClientSbinPath));
	}

	public void initClientBinPath(String value) {
		this.initProperty(S_ClientBinPath, value);
	}
	@Override
	public void setClientBinPath(String value) {
		this.set(S_ClientBinPath, value);
	}
	public void setClientBinPathNull() {
		this.set(S_ClientBinPath, null);
	}

	@Override
	public String getClientBinPath() {
		return DataType.getAsString(this.get(S_ClientBinPath));

	}
	public String getClientBinPathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_ClientBinPath));
	}

	public void initDeployStrategyName(String value) {
		this.initProperty(S_DeployStrategyName, value);
	}
	@Override
	public void setDeployStrategyName(String value) {
		this.set(S_DeployStrategyName, value);
	}
	public void setDeployStrategyNameNull() {
		this.set(S_DeployStrategyName, null);
	}

	@Override
	public String getDeployStrategyName() {
		return DataType.getAsString(this.get(S_DeployStrategyName));

	}
	public String getDeployStrategyNameInitialValue() {
		return DataType.getAsString(this.getOldObj(S_DeployStrategyName));
	}

	public void initDeployStrategyId(long value) {
		this.initProperty(S_DeployStrategyId, new Long(value));
	}
	@Override
	public void setDeployStrategyId(long value) {
		this.set(S_DeployStrategyId, new Long(value));
	}
	public void setDeployStrategyIdNull() {
		this.set(S_DeployStrategyId, null);
	}

	@Override
	public long getDeployStrategyId() {
		return DataType.getAsLong(this.get(S_DeployStrategyId));

	}
	public long getDeployStrategyIdInitialValue() {
		return DataType.getAsLong(this.getOldObj(S_DeployStrategyId));
	}

	public void initHistoryCreateTime(Timestamp value) {
		this.initProperty(S_HistoryCreateTime, value);
	}
	@Override
	public void setHistoryCreateTime(Timestamp value) {
		this.set(S_HistoryCreateTime, value);
	}
	public void setHistoryCreateTimeNull() {
		this.set(S_HistoryCreateTime, null);
	}

	@Override
	public Timestamp getHistoryCreateTime() {
		return DataType.getAsDateTime(this.get(S_HistoryCreateTime));

	}
	public Timestamp getHistoryCreateTimeInitialValue() {
		return DataType.getAsDateTime(this.getOldObj(S_HistoryCreateTime));
	}

	public void initHistoryPackageNum(int value) {
		this.initProperty(S_HistoryPackageNum, new Integer(value));
	}
	@Override
	public void setHistoryPackageNum(int value) {
		this.set(S_HistoryPackageNum, new Integer(value));
	}
	public void setHistoryPackageNumNull() {
		this.set(S_HistoryPackageNum, null);
	}

	@Override
	public int getHistoryPackageNum() {
		return DataType.getAsInt(this.get(S_HistoryPackageNum));

	}
	public int getHistoryPackageNumInitialValue() {
		return DataType.getAsInt(this.getOldObj(S_HistoryPackageNum));
	}

	public void initTemplateId(long value) {
		this.initProperty(S_TemplateId, new Long(value));
	}
	@Override
	public void setTemplateId(long value) {
		this.set(S_TemplateId, new Long(value));
	}
	public void setTemplateIdNull() {
		this.set(S_TemplateId, null);
	}

	@Override
	public long getTemplateId() {
		return DataType.getAsLong(this.get(S_TemplateId));

	}
	public long getTemplateIdInitialValue() {
		return DataType.getAsLong(this.getOldObj(S_TemplateId));
	}

	public void initFtpHostIp(String value) {
		this.initProperty(S_FtpHostIp, value);
	}
	@Override
	public void setFtpHostIp(String value) {
		this.set(S_FtpHostIp, value);
	}
	public void setFtpHostIpNull() {
		this.set(S_FtpHostIp, null);
	}

	@Override
	public String getFtpHostIp() {
		return DataType.getAsString(this.get(S_FtpHostIp));

	}
	public String getFtpHostIpInitialValue() {
		return DataType.getAsString(this.getOldObj(S_FtpHostIp));
	}

	public void initServerPackagePath(String value) {
		this.initProperty(S_ServerPackagePath, value);
	}
	@Override
	public void setServerPackagePath(String value) {
		this.set(S_ServerPackagePath, value);
	}
	public void setServerPackagePathNull() {
		this.set(S_ServerPackagePath, null);
	}

	@Override
	public String getServerPackagePath() {
		return DataType.getAsString(this.get(S_ServerPackagePath));

	}
	public String getServerPackagePathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_ServerPackagePath));
	}

	public void initRemarks(String value) {
		this.initProperty(S_Remarks, value);
	}
	@Override
	public void setRemarks(String value) {
		this.set(S_Remarks, value);
	}
	public void setRemarksNull() {
		this.set(S_Remarks, null);
	}

	@Override
	public String getRemarks() {
		return DataType.getAsString(this.get(S_Remarks));

	}
	public String getRemarksInitialValue() {
		return DataType.getAsString(this.getOldObj(S_Remarks));
	}

	public void initCreateTime(Timestamp value) {
		this.initProperty(S_CreateTime, value);
	}
	@Override
	public void setCreateTime(Timestamp value) {
		this.set(S_CreateTime, value);
	}
	public void setCreateTimeNull() {
		this.set(S_CreateTime, null);
	}

	@Override
	public Timestamp getCreateTime() {
		return DataType.getAsDateTime(this.get(S_CreateTime));

	}
	public Timestamp getCreateTimeInitialValue() {
		return DataType.getAsDateTime(this.getOldObj(S_CreateTime));
	}

	public void initModifyTime(Timestamp value) {
		this.initProperty(S_ModifyTime, value);
	}
	@Override
	public void setModifyTime(Timestamp value) {
		this.set(S_ModifyTime, value);
	}
	public void setModifyTimeNull() {
		this.set(S_ModifyTime, null);
	}

	@Override
	public Timestamp getModifyTime() {
		return DataType.getAsDateTime(this.get(S_ModifyTime));

	}
	public Timestamp getModifyTimeInitialValue() {
		return DataType.getAsDateTime(this.getOldObj(S_ModifyTime));
	}

	public void initInstallRule(String value) {
		this.initProperty(S_InstallRule, value);
	}
	@Override
	public void setInstallRule(String value) {
		this.set(S_InstallRule, value);
	}
	public void setInstallRuleNull() {
		this.set(S_InstallRule, null);
	}

	@Override
	public String getInstallRule() {
		return DataType.getAsString(this.get(S_InstallRule));

	}
	public String getInstallRuleInitialValue() {
		return DataType.getAsString(this.getOldObj(S_InstallRule));
	}

	public void initOperatorId(long value) {
		this.initProperty(S_OperatorId, new Long(value));
	}
	@Override
	public void setOperatorId(long value) {
		this.set(S_OperatorId, new Long(value));
	}
	public void setOperatorIdNull() {
		this.set(S_OperatorId, null);
	}

	@Override
	public long getOperatorId() {
		return DataType.getAsLong(this.get(S_OperatorId));

	}
	public long getOperatorIdInitialValue() {
		return DataType.getAsLong(this.getOldObj(S_OperatorId));
	}

	public void initDeployStrategyHistoryId(long value) {
		this.initProperty(S_DeployStrategyHistoryId, new Long(value));
	}
	@Override
	public void setDeployStrategyHistoryId(long value) {
		this.set(S_DeployStrategyHistoryId, new Long(value));
	}
	public void setDeployStrategyHistoryIdNull() {
		this.set(S_DeployStrategyHistoryId, null);
	}

	@Override
	public long getDeployStrategyHistoryId() {
		return DataType.getAsLong(this.get(S_DeployStrategyHistoryId));

	}
	public long getDeployStrategyHistoryIdInitialValue() {
		return DataType.getAsLong(this.getOldObj(S_DeployStrategyHistoryId));
	}

	public void initClientHomePath(String value) {
		this.initProperty(S_ClientHomePath, value);
	}
	@Override
	public void setClientHomePath(String value) {
		this.set(S_ClientHomePath, value);
	}
	public void setClientHomePathNull() {
		this.set(S_ClientHomePath, null);
	}

	@Override
	public String getClientHomePath() {
		return DataType.getAsString(this.get(S_ClientHomePath));

	}
	public String getClientHomePathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_ClientHomePath));
	}

	public void initFtpProtocol(String value) {
		this.initProperty(S_FtpProtocol, value);
	}
	@Override
	public void setFtpProtocol(String value) {
		this.set(S_FtpProtocol, value);
	}
	public void setFtpProtocolNull() {
		this.set(S_FtpProtocol, null);
	}

	@Override
	public String getFtpProtocol() {
		return DataType.getAsString(this.get(S_FtpProtocol));

	}
	public String getFtpProtocolInitialValue() {
		return DataType.getAsString(this.getOldObj(S_FtpProtocol));
	}

	public void initClientLogPath(String value) {
		this.initProperty(S_ClientLogPath, value);
	}
	@Override
	public void setClientLogPath(String value) {
		this.set(S_ClientLogPath, value);
	}
	public void setClientLogPathNull() {
		this.set(S_ClientLogPath, null);
	}

	@Override
	public String getClientLogPath() {
		return DataType.getAsString(this.get(S_ClientLogPath));

	}
	public String getClientLogPathInitialValue() {
		return DataType.getAsString(this.getOldObj(S_ClientLogPath));
	}

	public void initStopTemplateId(long value) {
		this.initProperty(S_StopTemplateId, new Long(value));
	}
	public void setStopTemplateId(long value) {
		this.set(S_StopTemplateId, new Long(value));
	}
	public void setStopTemplateIdNull() {
		this.set(S_StopTemplateId, null);
	}

	public long getStopTemplateId() {
		return DataType.getAsLong(this.get(S_StopTemplateId));

	}
	public long getStopTemplateIdInitialValue() {
		return DataType.getAsLong(this.getOldObj(S_StopTemplateId));
	}

}
