package com.asiainfo.monitor.tools.license.signature.base;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import com.asiainfo.monitor.tools.license.Base64;

public class KeyGenerater {

	private byte[] priKey;
	private byte[] pubKey;

	/**
	 * 生成私匙和公匙
	 */
	public void generaterRSA() {
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed("Asiainfo Linkage Monitor".getBytes()); // 初始化随机产生器
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			pubKey = Base64.encodeBase64(pubkey.getEncoded());
			priKey = Base64.encodeBase64(prikey.getEncoded());
			System.out.println("pubKey = " + new String(pubKey));
			System.out.println("priKey = " + new String(priKey));
		} catch (Exception e) {
			System.out.println("生成密钥对失败");
			e.printStackTrace();
		}
	}

	/**
	 * 暂时不对外提供,没有配套的生成签名方法
	 */
	private void generaterDSA(){
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator.getInstance("DSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed("Asiainfo Linkage Monitor".getBytes()); // 初始化随机产生器
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			pubKey = Base64.encodeBase64(pubkey.getEncoded());
			priKey = Base64.encodeBase64(prikey.getEncoded());
			System.out.println("pubKey = " + new String(pubKey));
			System.out.println("priKey = " + new String(priKey));
		} catch (Exception e) {
			System.out.println("生成密钥对失败");
			e.printStackTrace();
		}
	}
	
	public byte[] getPriKey() {
		return priKey;
	}

	public byte[] getPubKey() {
		return pubKey;
	}
	
	public static void main(String[] args) {
		KeyGenerater keyGener=new KeyGenerater();
		keyGener.generaterDSA();
	} 
}
