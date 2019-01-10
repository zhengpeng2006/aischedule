package com.asiainfo.monitor.tools.license;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class License {

	private static final transient Log log=LogFactory.getLog(License.class);
	
	//标识
	private String licenseID;
	//产品
	private String product;
	//版本
	private String version;
	//类型
	private String licenseType;
	//名称
	private String name;
	//公司
	private String company;
	
	private String numCopies;
	
	private String numClusterMembers;
	
	private String url;
	
	//实体
	private String entries;
	
	//失效时间
	private String expiresDate;
	//生成时间
	private String creationDate;
	//license签名
	private String signature;	

	public License(){		
	}

	public String getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(String licenseID) {
		this.licenseID = licenseID;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumCopies() {
		return numCopies;
	}

	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}

	public String getNumClusterMembers() {
		return numClusterMembers;
	}

	public void setNumClusterMembers(String numClusterMembers) {
		this.numClusterMembers = numClusterMembers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getExpiresDate() {		
		return this.expiresDate;
	}

	public void setExpiresDate(String eDate) {
		this.expiresDate=eDate;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(String cDate) {
		this.creationDate=cDate;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(String entries) {
		this.entries = entries;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof License)) {
			return false;
		}
		return ((this == o) || (this.licenseID.equals(((License) o).getLicenseID())));
	}

}
