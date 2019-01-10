package com.asiainfo.monitor.tools.license;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LicenseManager {

	private static final transient Log log=LogFactory.getLog(LicenseManager.class);

	public static void validateLicense(String product, String version) throws Exception {
		loadLicenses();
		if (!isValidProduct(product))
			throw new Exception("the monitor license is not the product["+product+"]");
		return;
	}

	/**
	 * 验证license的数字签名
	 * 如果有效日期等属性被人为的改变,则数字签名将无效
	 * @param license
	 * @return
	 * @throws Exception
	 */
	private static boolean validate() throws Exception {
		//已加密的公有密匙
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCRg/hnTl1wZhg0eRnixE/Y8nw1a8fhrTfGSpDbfCt8QmGJLgYo20FP4lBv2+WEbcP+x9vLmLqisZoRozI1YVcRVYri4vpTu+UNPx7vcIYzvgCcb4E4PuUHuolxJ0SQi4BaWodT6ASTHvtJABdwdC+Kg2Me/fAkfUssy8s6eFnwQIDAQAB";
		//解密公有密匙
		byte[] pub = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pub);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
		//解密数字签名
		byte[] signed=Base64.decodeBase64(LicenseWrapper.getInstance().getSignature());
		Signature signatureChecker = Signature.getInstance("MD5withRSA");
		signatureChecker.initVerify(pubKey);		
		signatureChecker.update(Base64.encodeBase64String(LicenseWrapper.getInstance().getFingerprint()).getBytes());
		return signatureChecker.verify(signed);
	}
	
	/**
	 * 加载licensens文件
	 */
	private static synchronized void loadLicenses() throws Exception{
		
		try {
			InputStream instream =Thread.currentThread().getContextClassLoader().getResourceAsStream("appmonitor.licenses");
			if (instream==null){
				throw new Exception("The monitor license file could not be found .");
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(instream));
			StringBuffer text = new StringBuffer();
			char[] buf = new char[1024];
			int len = 0;
			while ((len = in.read(buf)) >= 0) {
				for (int j = 0; j < len; ++j) {
					char ch = buf[j];
					if ((!(Character.isLetter(ch)))
							&& (!(Character.isDigit(ch))) && (ch != '+')
							&& (ch != '/') && (ch != '=')) {
						continue;
					}
					text.append(ch);
				}
			}
			in.close();
			//解密成xml串
			String xml=LicenseDecode.transformStr(text.toString());
			if (log.isDebugEnabled()) {
				log.debug(xml);
			}
			LicenseWrapper.getInstance().setLicense(LicenseBetwixtReader.xml2Java(xml));
			
			//提供licenseId
			if (LicenseWrapper.getInstance().getLicenseID() <= 1L) {
				throw new LicenseException("The license is out of date and is no longer "+ "valid. Please use a new license file.");
			}
			//检查是否是试用版,试用版存在功能限制以及有效时间限制
			if (LicenseWrapper.getInstance().getLicenseType().isEvaluation()){
				//是否已过期
				if (LicenseWrapper.getInstance().getExpiresDate() != null) {
					long now = System.currentTimeMillis();
					if (LicenseWrapper.getInstance().getExpiresDate().getTime() < now) {
						throw new LicenseException("The license  is expired.");
					}
				}
			}
			
			//验证数字签名是否有效
			if (!(validate())) {
				throw new LicenseException("The license is not valid.");
			}
		} catch (Exception e) {
			log.error(e);
			throw new LicenseException("There was an error reading the license:" + e.getMessage());			
		}		
	}

	/**
	 * 检查是否是有效产品
	 * @param product
	 * @return
	 */
	private static boolean isValidProduct(String product) {
		product = product.intern();
		if ( LicenseWrapper.getInstance().getProduct()==null)
			return false;
		String licenseProduct = LicenseWrapper.getInstance().getProduct().intern();
		if (licenseProduct.indexOf("Monitor") >= 0) {
			if ("Monitor Basic".equals(product)) {
				return ((licenseProduct.equals("Asiainfo Linkage Professional")) || (licenseProduct.equals("Asiainfo Linkage Enterprise")));
			}

			if ("Monitor Professional".equals(product)) {
				return ((licenseProduct.equals("Asiainfo Linkage Monitor Professional")) || (licenseProduct.equals("Asiainfo Linkage Enterprise") ));
			}

			if ("Monitor Enterprise".equals(product)) {
				return (licenseProduct.equals("Asiainfo Linkage Monitor Enterprise"));
			}

			return false;
		}

		return false;
	}
	
	public static void main(String[] args) throws Exception{
		LicenseManager licenseManager=new LicenseManager();
	}
}
