package com.asiainfo.deploy.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asiainfo.deploy.common.utils.StringUtils;

/**
 * 常用方法
 * @author 孙德东(24204)
 */
public class DeployUtils {
	private static final Pattern fileNamePattern = Pattern.compile("[/\\\\]([^/\\\\]+)$");
	
	private DeployUtils(){}
	
	/**
	 * 统一文件分隔符
	 * @return
	 */
	public static String unifyPathDelimiter(String path) {
		if (StringUtils.isEmpty(path)) return StringUtils.EMPTY;
		
		return StringUtils.replace(path, "\\", "/");
	}
	
	/**
	 * 在目录后面添加/，方便拼接绝对路径
	 * @return
	 */
	public static String addFileSeperatorInEnd(String path) {
		String tmp = unifyPathDelimiter(path);
		return StringUtils.endsWith(tmp, "/") ? tmp : tmp + "/";
	}
	
	/**
	 * 在目录后面添加/，方便拼接绝对路径
	 * @return
	 */
	public static String removeFileSeperatorInEnd(String path) {
		String tmp = unifyPathDelimiter(path);
		return StringUtils.endsWith(tmp, "/") ? StringUtils.substring(tmp, 0, tmp.length() - 1) : tmp;
	}
	
	/**
	 * 构造绝对路径（如果dir不是绝对路径，则加上home);并且以/结尾
	 * 
	 * @param home
	 * @param dir
	 * @return
	 */
	public static String constructAbsoluteDirWithSlash(String home, String dir) {
		return StringUtils.startsWith(dir, "/") ? addFileSeperatorInEnd(dir) : addFileSeperatorInEnd(home) + addFileSeperatorInEnd(dir);
	}
	
	/**
	 * 构造绝对路径（如果dir不是绝对路径，则加上home);并且不以/结尾
	 * 
	 * @param home
	 * @param dir
	 * @return
	 */
	public static String constructAbsoluteDirWithoutSlash(String home, String dir) {
		return StringUtils.startsWith(dir, "/") ? removeFileSeperatorInEnd(dir) : addFileSeperatorInEnd(home) + removeFileSeperatorInEnd(dir);
	}
	
	/**
	 * 根据绝对路径获取文件名
	 * @param absolutePath
	 * @return
	 */
	public static String getFileNameFromAbsolutePath(String absolutePath) {
		if (StringUtils.isEmpty(absolutePath)) return StringUtils.EMPTY;
		
		Matcher matcher = fileNamePattern.matcher(absolutePath);
		if (matcher.find()) {
			return  matcher.group(1);
		}
		
		return absolutePath;
	}
	
	/**
	 * \home\1.txt  --> \home\prefix_1.txt
	 * @param absolutePath
	 * @param prefix
	 * @return
	 */
	public static String appendPrefix(String absolutePath, String prefix) {
		if (StringUtils.isEmpty(absolutePath)) return StringUtils.EMPTY;
		
		Matcher matcher = fileNamePattern.matcher(absolutePath);
		if (matcher.find()) {
			String fileName = matcher.group(1);
			int start = matcher.start();
			String dir = StringUtils.substring(absolutePath, 0, start + 1);
			
			return dir + prefix + fileName;
		}
		
		return prefix + absolutePath;
	}
	
	public static void main(String[] args) {
		//System.out.println(getFileNameFromAbsolutePath("345.txt"));
		System.out.println(appendPrefix("H:/abc/345.txt","a_"));
		System.out.println(unifyPathDelimiter("hello/a\\b/c\\d"));
		
		String s = "abc";
		System.out.println(StringUtils.substring(s, 0, s.length() - 1));
	}
}
