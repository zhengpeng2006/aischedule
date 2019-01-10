package com.asiainfo.deploy.common.ivalues;
import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;
public interface IBODeployStrategyValue extends DataStructInterface{

  public final static  String S_FtpProtocol = "FTP_PROTOCOL";
  public final static  String S_ClientHomePath = "CLIENT_HOME_PATH";
  public final static  String S_OperatorId = "OPERATOR_ID";
  public final static  String S_InstallRule = "INSTALL_RULE";
  public final static  String S_ModifyTime = "MODIFY_TIME";
  public final static  String S_CreateTime = "CREATE_TIME";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ServerPackagePath = "SERVER_PACKAGE_PATH";
  public final static  String S_FtpHostIp = "FTP_HOST_IP";
  public final static  String S_TemplateId = "TEMPLATE_ID";
  public final static  String S_HistoryPackageNum = "HISTORY_PACKAGE_NUM";
  public final static  String S_DeployStrategyId = "DEPLOY_STRATEGY_ID";
  public final static  String S_DeployStrategyName = "DEPLOY_STRATEGY_NAME";
  public final static  String S_ClientBinPath = "CLIENT_BIN_PATH";
  public final static  String S_ClientSbinPath = "CLIENT_SBIN_PATH";
  public final static  String S_ClientLogPath = "CLIENT_LOG_PATH";
  public final static  String S_FtpPackagePath = "FTP_PACKAGE_PATH";
  public final static  String S_FtpUserName = "FTP_USER_NAME";
  public final static  String S_FtpPassword = "FTP_PASSWORD";
  public final static  String S_FtpHostPort = "FTP_HOST_PORT";

	public final static String S_StopTemplateId = "STOP_TEMPLATE_ID";

public String getFtpProtocol();

public String getClientHomePath();

public long getOperatorId();

public String getInstallRule();

public Timestamp getModifyTime();

public Timestamp getCreateTime();

public String getRemarks();

public String getServerPackagePath();

public String getFtpHostIp();

public long getTemplateId();

public long getStopTemplateId();

public int getHistoryPackageNum();

public long getDeployStrategyId();

public String getDeployStrategyName();

public String getClientBinPath();

public String getClientSbinPath();

public String getClientLogPath();

public String getFtpPackagePath();

public String getFtpUserName();

public String getFtpPassword();

public int getFtpHostPort();


public  void setFtpProtocol(String value);

public  void setClientHomePath(String value);

public  void setOperatorId(long value);

public  void setInstallRule(String value);

public  void setModifyTime(Timestamp value);

public  void setCreateTime(Timestamp value);

public  void setRemarks(String value);

public  void setServerPackagePath(String value);

public  void setFtpHostIp(String value);

public  void setTemplateId(long value);

public  void setStopTemplateId(long value);

public  void setHistoryPackageNum(int value);

public  void setDeployStrategyId(long value);

public  void setDeployStrategyName(String value);

public  void setClientBinPath(String value);

public  void setClientSbinPath(String value);

public  void setClientLogPath(String value);

public  void setFtpPackagePath(String value);

public  void setFtpUserName(String value);

public  void setFtpPassword(String value);

public  void setFtpHostPort(int value);
}
