package com.asiainfo.monitor.tools.license;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.license.signature.base.KeyGenerater;
import com.asiainfo.monitor.tools.license.signature.base.Signaturer;

/**
 * 1、加载原始license定义文件
 * 2、生成数字签名
 * 3、生成加密文件
 * 
 * 将公有密匙替换LicenseManager.java内的publicKey
 * 
 * @author guocx
 * 该类不打包
 */
public class LicenseBuilder {
	
	private static final transient Log log=LogFactory.getLog(LicenseBuilder.class);
	
	public static String generateLicense() throws Exception{
		//重新加载license文件
		try {
			InputStream instream =Thread.currentThread().getContextClassLoader().getResourceAsStream("appmonitor_licenses.txt");
			if (instream==null){
				log.error("The monitor license file could not be found .");
				throw new Exception(" ");
			}
			//转换成对象
			License license=LicenseBetwixtReader.xml2Java(instream);
			LicenseWrapper.getInstance().setLicense(license);
			//生成公有、私有密匙
			KeyGenerater keyGen=new KeyGenerater();
			keyGen.generaterRSA();
			System.out.println("公有密匙:"+StringTransform.newStringUtf8(keyGen.getPubKey()));
			System.out.println("私有密匙:"+StringTransform.newStringUtf8(keyGen.getPriKey()));
			//Base64加密指纹
			String licensesCode=Base64.encodeBase64String(LicenseWrapper.getInstance().getFingerprint());
			//生成数字签名
			byte[] signaturer=Signaturer.sign(keyGen.getPriKey(),licensesCode);
			license.setSignature(StringTransform.newStringUtf8(signaturer));
			
			String licenseXml=LicenseBetwixtWriter.java2XML(license);
			System.out.println("最终的license :");
			System.out.println(licenseXml);
			
			return Base64.encodeBase64String(LicenseEncode.createLicenseCode(licenseXml.getBytes()));
		} catch (Exception e) {
			throw new Exception("发生异常:"+e.getMessage());
		}
		// 将字符串转成licenses
		
	}
	
	public static void main(String[] args) throws Exception{
		String licensesCode=LicenseBuilder.generateLicense();
		System.out.println(licensesCode);
	}
}
