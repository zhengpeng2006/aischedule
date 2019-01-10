package com.asiainfo.monitor.tools.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

public class LicenseDecode {

	private static final byte SALT_SIZE = 8;
	
	public static final byte[] license = new byte[]{
		65,115,105,97,105,110,102,111,32,77,111,110,105,116,111,114,32,69,110,116,101,114,112,114,105,115,101
        };
	
	private static byte[] getLicenseData(byte[] licenseCode) throws InvalidKeyException, NoSuchAlgorithmException,InvalidKeySpecException, NoSuchPaddingException,IllegalBlockSizeException, BadPaddingException {

		byte[] salt = new byte[SALT_SIZE];
		System.arraycopy(licenseCode, 0, salt, 0, SALT_SIZE);
		byte[] encodedData = new byte[licenseCode.length - SALT_SIZE];

		System.arraycopy(licenseCode, SALT_SIZE, encodedData, 0,encodedData.length);

		DESKeySpec key = new DESKeySpec(salt);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(key));

		return cipher.doFinal(encodedData);
	}
		
	private static boolean checkLicense(String licenseCode) {
        byte[] licenseCodeBytes = Base64.decodeBase64(licenseCode);
        byte[] licenseData = new byte[0];
        try {
            licenseData = getLicenseData(licenseCodeBytes);
        } catch (Exception e) {
            return false;
        }
        return Arrays.equals(license, licenseData);
    }
	
	/**
	 * 返回license
	 * @param licenseCode
	 * @return
	 */
	public static String transformStr(String licenseCode){
		String result=null;
        try {
        	byte[] licenseCodeBytes = Base64.decodeBase64(licenseCode);
            byte[] licenseData = new byte[0];
            licenseData = getLicenseData(licenseCodeBytes);
            char[] cs=Base64.getChars(licenseData);
            result=new String(cs);
        } catch (Exception e) {
            return null;
        }
        return result;
	}
	
	/**
	 * 获取license文件信息
	 * @return
	 * @throws Exception
	 */
	public static String getLicenses() throws Exception{
		InputStream licneses =Thread.currentThread().getContextClassLoader().getResourceAsStream("monitor.licenses");
    	if (licneses==null)
    		throw new Exception("Not found License info");
    	BufferedReader br = new BufferedReader(new InputStreamReader(licneses));
        String tmp = null;
        StringBuilder sb = new StringBuilder();
        try{
        	while(true){
            	tmp = br.readLine();
            	if ( tmp!=null ){
            		if (!StringUtils.isBlank(sb.toString()))
            			sb.append("\n");
            		sb.append(tmp);
            		
            	}else{
            		break;
            	}
            }
        	return sb.toString(); 
        }catch (IOException e){
        	return "";
        }finally{
            try {
            	if (br!=null)
            		br.close();
            } catch (IOException e) {
            	
            }
        }
	}
	
	/**
	 * 检测license
	 * @return
	 */
	public static boolean checkDefaultLicense() throws Exception{
		return checkLicense(getLicenses()); 
    }
	
	/**
	 * 检测license
	 * @param licenseCode
	 * @return
	 * @throws Exception
	 */
	public static boolean checkCustLicense(String licenseCode) throws Exception{
		return checkLicense(licenseCode);
	}
}
