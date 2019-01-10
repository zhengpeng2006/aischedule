package com.asiainfo.socket.util;

import org.apache.commons.lang.StringUtils;

/**
 * 解包/压包的常量工具类
 * 
 * @author 孙德东(24204)
 */
public class PackageUtils {
	
	/**
	 * 二进制流解析成String
	 * @param packageData
	 * @return
	 * @throws Exception
	 */
	public static String dataPackageParse(byte[] packageData) throws Exception {
		String msg = null;
		if (packageData != null && packageData.length > 4) {
			// 报文长度验证
			int length = ConvertUtil.byteArrayToInt(packageData, 4, ConvertUtil.ALIGNMENT_HIGHT);
			if (length != packageData.length - 4) {
				throw new Exception("报文长度验证失败！");
			}
			// 报文格式解析
			byte[] data = new byte[length];
			System.arraycopy(packageData, 4, data, 0, length);
			msg = new String(data,"UTF-8");
		}

		return msg;
	}

	
	/**
	 * String转化成可以直接发送的二进制流，并添加消息长度
	 * @param ret
	 * @return
	 * @throws Exception
	 */
	public static byte[] dataPackageCompose(String ret) throws Exception {
		byte[] packageData = null;

		if (!StringUtils.isEmpty(ret)) {

			byte[] data = ret.getBytes("UTF-8");
			byte[] head = ConvertUtil.intToByteArray(data.length, 4, ConvertUtil.ALIGNMENT_HIGHT);
			packageData = new byte[data.length + 4];
			System.arraycopy(head, 0, packageData, 0, 4);
			System.arraycopy(data, 0, packageData, 4, data.length);

		}

		return packageData;
	}

}
