package com.asiainfo.monitor.tools.license;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * @author guocx
 * 该类不打包
 */
public class LicenseEncode {

	private static byte SALT_SIZE = 8;
	
	/**
	 * 创建licenses
	 * @param licenseData
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] createLicenseCode(byte[] licenseData) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        byte[] salt = new byte[SALT_SIZE];
        Random saltGenerator = new Random(System.currentTimeMillis());
        saltGenerator.nextBytes(salt);
        DESKeySpec key = new DESKeySpec(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(key));

        byte[] encodedData = cipher.doFinal(licenseData);

        byte[] licenseCode = new byte[SALT_SIZE + encodedData.length];

        System.arraycopy(salt, 0, licenseCode, 0, SALT_SIZE);
        System.arraycopy(encodedData, 0, licenseCode, SALT_SIZE, encodedData.length);
        return licenseCode;
    }
}
