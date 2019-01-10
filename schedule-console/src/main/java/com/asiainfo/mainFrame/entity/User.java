package com.asiainfo.mainFrame.entity;

public class User {
    private String opID;
    private Integer orgID;
    private int viewID;
    private String opName;
    private String loginName;
    private String opPasswd;
    private int admFlag;
    private int opStatus;
    private int securityID;
    private String passwdChangeDate;
    private String ccInput;
    private String reltTele;
    private String mobile;//手机号
    private String email;
    private int entrance;
    private int loginModeCfg;
    private String createDate;
    private String doneDate;
    private String validDate;
    private String expireDate;
    private Integer deptID;
    private Integer keyID;
    private String errMsg;
    private String chgPasswdFlag;//密码修改提醒
    private String returnCode;
    
    private String regionCode ;//用户所属地市
    private int operType;//人员类型     1-地市稽核人员 2-地市稽核管理员  4-省级稽核人员  8-省级稽核管理员 16-系统维护人员
    private int groupID ;
    
    
    private String sessionID;
    private long time = System.currentTimeMillis();
    private String loginTime ;  
    
    public String getOpID() {
        return opID;
    }
    public void setOpID(String opID) {
        this.opID = opID;
    }
    public Integer getOrgID() {
        return orgID;
    }
    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }
    public int getViewID() {
        return viewID;
    }
    public void setViewID(int viewID) {
        this.viewID = viewID;
    }
    public String getOpName() {
        return opName;
    }
    public void setOpName(String opName) {
        this.opName = opName;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getOpPasswd() {
        return opPasswd;
    }
    public void setOpPasswd(String opPasswd) {
        this.opPasswd = opPasswd;
    }
    public int getAdmFlag() {
        return admFlag;
    }
    public void setAdmFlag(int admFlag) {
        this.admFlag = admFlag;
    }
    public int getOpStatus() {
        return opStatus;
    }
    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }
    public int getSecurityID() {
        return securityID;
    }
    public void setSecurityID(int securityID) {
        this.securityID = securityID;
    }
    public String getPasswdChangeDate() {
        return passwdChangeDate;
    }
    public void setPasswdChangeDate(String passwdChangeDate) {
        this.passwdChangeDate = passwdChangeDate;
    }
    public String getCcInput() {
        return ccInput;
    }
    public void setCcInput(String ccInput) {
        this.ccInput = ccInput;
    }
    public String getReltTele() {
        return reltTele;
    }
    public void setReltTele(String reltTele) {
        this.reltTele = reltTele;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getEntrance() {
        return entrance;
    }
    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }
    public int getLoginModeCfg() {
        return loginModeCfg;
    }
    public void setLoginModeCfg(int loginModeCfg) {
        this.loginModeCfg = loginModeCfg;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getDoneDate() {
        return doneDate;
    }
    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }
    public String getValidDate() {
        return validDate;
    }
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
    public String getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    public Integer getDeptID() {
        return deptID;
    }
    public void setDeptID(Integer deptID) {
        this.deptID = deptID;
    }
    public Integer getKeyID() {
        return keyID;
    }
    public void setKeyID(Integer keyID) {
        this.keyID = keyID;
    }
    public String getSessionID() {
        return sessionID;
    }
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    public long getTime() {
        return time;
    }
    
    public void freshTime(){
        this.time = System.currentTimeMillis();
    }
    public String getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
    
    public String getRegionCode() {
        return regionCode;
    }
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    public void load(Operator oper){
        this.regionCode = oper.getRegionCode();
        this.mobile = oper.getBillID();
        this.email = oper.getEmail();
        this.groupID = oper.getGroupID();
        this.operType = oper.getOperType();
    }
    public int getOperType() {
        return operType;
    }
    public void setOperType(int operType) {
        this.operType = operType;
    }
    public int getGroupID() {
        return groupID;
    }
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public String getChgPasswdFlag() {
        return chgPasswdFlag;
    }
    public void setChgPasswdFlag(String chgPasswdFlag) {
        this.chgPasswdFlag = chgPasswdFlag;
    }
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
        
}
