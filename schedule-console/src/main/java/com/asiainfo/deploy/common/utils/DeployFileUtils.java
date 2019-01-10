package com.asiainfo.deploy.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.axis.utils.StringUtils;

/**
 * 文件操作常量类
 * 
 * @author 孙德东(24204)
 */
public class DeployFileUtils {
	
	private DeployFileUtils(){}
	
	public static String readFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(loadFile(filePath)));
		
		StringBuffer sb = new StringBuffer();
		String s = null;
		while((s=reader.readLine()) != null) {
			sb.append(s + "\n");
		}
		
		reader.close();
		return sb.toString();
	}
	
	/**
	 * 将字符串转化成某种格式的流
	 * @param s
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream toInputStream(String s) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(s)) return null;
		
		//使用默认的字符编码
		/*if (StringUtils.isEmpty(charSet)) {
			new ByteArrayInputStream(s.getBytes());
		}*/
		return new ByteArrayInputStream(s.getBytes());
	}
	
	public static InputStream loadFile(String filePath) {
		//先使用当前类的类加载器查找资源
		InputStream in = DeployFileUtils.class.getClassLoader().getResourceAsStream(filePath);
		if (in == null) {
			//查找不到资源时，使用线程上下文的类加载器查找资源
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filePath);
		}
		return in;
	}


}
