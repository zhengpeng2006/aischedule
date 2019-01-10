package com.asiainfo.monitor.tools.license.signature.base;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import com.asiainfo.monitor.tools.license.Base64;


public class Signaturer {

	/**
	 * 利用私匙将文本内容生成数字签名
	 * 该类不进行打包
	 * @param priKeyText
	 * @param plainText
	 * @return
	 */
	public static byte[] sign(byte[] priKeyText, String plainText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			// 用私钥对信息生成数字签名
			java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			byte[] signed = Base64.encodeBase64(signet.sign());
			return signed;
		} catch (Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}
		return null;
	}

}
