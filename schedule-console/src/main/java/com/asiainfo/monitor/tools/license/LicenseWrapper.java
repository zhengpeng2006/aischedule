package com.asiainfo.monitor.tools.license;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * license 包装
 * @author guocx
 *
 */
public class LicenseWrapper {

	private static final transient Log log=LogFactory.getLog(LicenseWrapper.class);
	
	private License license;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	private List entries=null;
	
	private static LicenseWrapper _instance=new LicenseWrapper();	
	
	private LicenseWrapper(){
	}
	
	public void setLicense(License license) {
		this.license = license;
		this.init();
	}
	
	public static LicenseWrapper getInstance(){
		return _instance;
	}
	
	private boolean hasLicense(){
		return this.license!=null && StringUtils.isNotBlank(this.license.getLicenseID());
	}
	
	public long getLicenseID() {
		if (hasLicense() && StringUtils.isNotBlank(this.license.getLicenseID()))
			return Long.parseLong(this.license.getLicenseID());
		else
			return 0l;
	}

	public String getProduct() {
		if (!hasLicense())
			return null;
		return this.license.getProduct();
	}

	public String getVersion() {
		if (!hasLicense())
			return null;
		return this.license.getVersion();
	}

	public LicenseType getLicenseType() {
		if (!hasLicense())
			return null;
		return LicenseType.fromString(this.license.getLicenseType());
	}

	public String getName() {
		if (!hasLicense())
			return null;
		return this.license.getName();
	}

	public String getCompany() {
		if (!hasLicense())
			return null;
		return this.license.getCompany();
	}

	public int getNumCopies() {
		if (!hasLicense())
			return 0;
		return Integer.parseInt(this.license.getNumCopies());
	}

	public int getNumClusterMembers() {
		if (!hasLicense())
			return 0;
		return Integer.parseInt(this.license.getNumClusterMembers());
	}

	public String getUrl() {
		if (!hasLicense())
			return null;
		return this.license.getUrl();
	}

	public Date getExpiresDate() {
		if (!hasLicense())
			return null;
		if (this.license.getExpiresDate() == null) {
			return null;
		}
		try{
			return formatter.parse(this.license.getExpiresDate());
		}catch(ParseException e){
			log.error("格式化化失效日期发生异常:"+e.getMessage());
		}
		return null;
	}

	public Date getCreationDate() {
		if (!hasLicense())
			return null;
		if (this.license.getCreationDate() == null) {
			return null;
		}
		try{
			return formatter.parse(this.license.getCreationDate());
		}catch(ParseException e){
			log.error("格式化化创建日期发生异常:"+e.getMessage());
		}
		return null;
	}
	
	public String getSignature() {
		if (!hasLicense())
			return null;
		return this.license.getSignature();
	}
	
	private void init(){
		if (StringUtils.isBlank(this.license.getEntries()))
			return;
		if (entries==null)
			entries=new ArrayList();
		entries.clear();
		StringTokenizer stk=new StringTokenizer(this.license.getEntries(),",");
		while (stk.hasMoreElements()){
			entries.add(stk.nextElement());
		}
	}
	
	/**
	 * 检查是否存在实体
	 * @param entity
	 * @return
	 */
	public boolean contain(String entity){
		if (this.entries==null)
			return false;
		if (this.entries.contains(entity))
			return true;
		else
			return false;
	}
	
	/**
	 * 获得指纹
	 * @return
	 */
	public byte[] getFingerprint() {
		StringBuffer buf = new StringBuffer(200);
		buf.append(this.license.getLicenseID());
		buf.append(this.license.getProduct());
		if (this.license.getEntries()!=null){
			buf.append(this.license.getEntries());
		}
		buf.append(this.license.getVersion());
		buf.append(this.license.getLicenseType());
		buf.append(this.license.getNumCopies());
		if (this.license.getExpiresDate() != null) {
			buf.append(this.license.getExpiresDate());
		}
		if (this.license.getName() != null) {
			buf.append(this.license.getName());
		}
		if (this.license.getCompany() != null) {
			buf.append(this.license.getCompany());
		}

		if (this.getVersion().compareTo("2.0") >= 0) {
			buf.append(this.getNumClusterMembers());
			if (this.license.getUrl() != null)
				buf.append(this.license.getUrl());
		}
		try {
			return buf.toString().getBytes("utf-8");
		} catch (UnsupportedEncodingException ue) {
			log.error("encoding exception"+ue.getMessage());
		}
		return buf.toString().getBytes();
	}
	
	/**
	 * license类型
	 * @author guocx
	 *
	 */
	public static final class LicenseType {
		
		private final String name;
		
		//非商业版(一般用于开发)
		public static final LicenseType NON_COMMERCIAL = new LicenseType("Non-Commercial");

		//商业版(无功能限制)
		public static final LicenseType COMMERCIAL = new LicenseType("Commercial");

		//试用版、估价版(功能限制)
		public static final LicenseType EVALUATION = new LicenseType("Evaluation");

		private LicenseType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
		
		public boolean isCommercial(){
			return this.name.equals("Commercial")?true:false;
		}
		
		public boolean isNonCommercial(){
			return this.name.equals("Non-Commercial")?true:false;
		}

		public boolean isEvaluation(){
			return this.name.equals("Evaluation")?true:false;
		}
		
		public static LicenseType fromString(String type) {
			if (NON_COMMERCIAL.toString().equals(type)) {
				return NON_COMMERCIAL;
			}
			if (COMMERCIAL.toString().equals(type)) {
				return COMMERCIAL;
			}
			if (EVALUATION.toString().equals(type)) {
				return EVALUATION;
			}
			return EVALUATION;
		}
	}
	
	public static void main(String[] args){
		StringTokenizer stk=new StringTokenizer("a,fh,sds",",");
		while (stk.hasMoreElements()){
			System.out.println(stk.nextElement());
		}
	}
}
