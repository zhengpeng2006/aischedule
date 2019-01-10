package com.asiainfo.scheduler.sso;


public class OperInfo
{
  private String opId;
  private String opname;
  private String oplogname;
  private String oporgid;
  private String opdeptid;
  private String opentid;
  private String opadmflag;
  private String regioncode;
  private String firstorgtype;
  private String secorgtype;
  private String thirdorgtype;
  private String tokenId;
  private long createTime = 0L;

  public long getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(long createTime)
  {
    this.createTime = createTime;
  }

  public String getOpId() {
    return this.opId;
  }

  public String getOpname() {
    return this.opname;
  }

  public String getOplogname() {
    return this.oplogname;
  }

  public String getOporgid() {
    return this.oporgid;
  }

  public String getOpdeptid() {
    return this.opdeptid;
  }

  public String getOpentid() {
    return this.opentid;
  }

  public String getOpadmflag()
  {
    return this.opadmflag;
  }

  public String getRegioncode() {
    return this.regioncode;
  }

  public String getFirstorgtype() {
    return this.firstorgtype;
  }

  public String getSecorgtype() {
    return this.secorgtype;
  }

  public String getThirdorgtype() {
    return this.thirdorgtype;
  }

  public void setOpId(String opId) {
    this.opId = opId;
  }

  public void setOpname(String opname) {
    this.opname = opname;
  }

  public void setOplogname(String oplogname) {
    this.oplogname = oplogname;
  }

  public void setOporgid(String oporgid) {
    this.oporgid = oporgid;
  }

  public void setOpdeptid(String opdeptid) {
    this.opdeptid = opdeptid;
  }

  public void setOpentid(String opentid) {
    this.opentid = opentid;
  }

  public void setOpadmflag(String opadmflag) {
    this.opadmflag = opadmflag;
  }

  public void setRegioncode(String regioncode) {
    this.regioncode = regioncode;
  }

  public void setFirstorgtype(String firstorgtype) {
    this.firstorgtype = firstorgtype;
  }

  public void setSecorgtype(String secorgtype) {
    this.secorgtype = secorgtype;
  }

  public void setThirdorgtype(String thirdorgtype) {
    this.thirdorgtype = thirdorgtype;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();
    strBuf.append("oplogname=").append(this.oplogname);
    strBuf.append("\t opname=").append(this.opname);
    strBuf.append("\t opId=").append(this.opId);
    strBuf.append("\t oporgid=").append(this.oporgid);
    strBuf.append("\t opdeptid=").append(this.opdeptid);
    strBuf.append("\t opadmflag=").append(this.opadmflag);
    strBuf.append("\t regioncode=").append(this.regioncode);
    strBuf.append("\t firstorgtype=").append(this.firstorgtype);
    strBuf.append("\t secorgtype=").append(this.secorgtype);
    strBuf.append("\t thirdorgtype=").append(this.thirdorgtype);
    return strBuf.toString();
  }

  public String getTokenId()
  {
    return this.tokenId;
  }

  public void setTokenId(String tokenId)
  {
    this.tokenId = tokenId;
  }
}