package com.asiainfo.monitor.exeframe.config.ivalues;
import com.ai.appframe2.common.DataStructInterface;
public interface IBOAIMonParamDefineValue extends DataStructInterface{

  public final static  String S_State = "STATE";
  public final static  String S_SortId = "SORT_ID";
  public final static  String S_RegisteId = "REGISTE_ID";
  public final static  String S_DataType = "DATA_TYPE";
  public final static  String S_Remarks = "REMARKS";
  public final static  String S_ParamDesc = "PARAM_DESC";
  public final static  String S_IsMust = "IS_MUST";
  public final static  String S_RegisteType = "REGISTE_TYPE";
  public final static  String S_ParamId = "PARAM_ID";
  public final static  String S_ParamCode = "PARAM_CODE";


    public String getState();

    public int getSortId();

    public long getRegisteId();

    public String getDataType();

    public String getRemarks();

    public String getParamDesc();

    public String getIsMust();

    public int getRegisteType();

    public long getParamId();

    public String getParamCode();

    public void setState(String value);

    public void setSortId(int value);

    public void setRegisteId(long value);

    public void setDataType(String value);

    public void setRemarks(String value);

    public void setParamDesc(String value);

    public void setIsMust(String value);

    public void setRegisteType(int value);

    public void setParamId(long value);

    public void setParamCode(String value);
}
