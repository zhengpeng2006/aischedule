package com.asiainfo.mainFrame.entity;

public class Operator {
    private Long operID;
    private String operName;
    private String regionCode;
    private String billID;       //账单ID
    private String email;
    private String notes;
    private String createTime;
    private int orgID;           //组织ID
    private int operType;        //人员类型
    private String countyCode;  //县市代码
    private int groupID;         //组号
    private int delFlag;
    private String deleteTime;
    
    
    public Long getOperID() {
        return operID;
    }
    public void setOperID(Long operID) {
        this.operID = operID;
    }
    public String getOperName() {
        return operName;
    }
    public void setOperName(String operName) {
        this.operName = operName;
    }
    public String getRegionCode() {
        return regionCode;
    }
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    public String getBillID() {
        return billID;
    }
    public void setBillID(String billID) {
        this.billID = billID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public int getOrgID() {
        return orgID;
    }
    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }
    public int getOperType() {
        return operType;
    }
    public void setOperType(int operType) {
        this.operType = operType;
    }
    public String getCountyCode() {
        return countyCode;
    }
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }
    public int getGroupID() {
        return groupID;
    }
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    public int getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
    public String getDeleteTime() {
        return deleteTime;
    }
    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }
    
    

}
