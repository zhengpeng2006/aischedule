package com.asiainfo.monitor.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonVerifySV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonVerifyValue;

/**
 * 防止存储型XSS攻击校验
 * @author szh
 *
 */
public class XssUtil {

	private static String DEFAULT_ILLEGAL_CHAR_CHECK = "document.cookie|href|script|//|select/|insert/|update/|delete/|truncate/|exec/|drop/";
	private static String DEFAULT_SHELL_ILLEGAL_CHAR_CHECK="document.cookie|href|script";
	private static Pattern PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
	private static Pattern PATTERNSHELL = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
	private static transient Log log = LogFactory.getLog(XssUtil.class);

	public static void main(String[] args) throws Exception {
		/*TaskBean bean = new TaskBean();
		bean.setTaskName("<script>alert(111)</script>aa");
		System.out.println(checkBeforeSave(bean, bean.getClass()));*/
		System.out.println(checkString("<"));
	}

	public static void main1(String[] args) {
		Map<String, String> result = new HashMap<String, String>();
		for (String key : result.keySet()) {
			System.out.println(key);
		}
	}

	public static boolean checkString(String str) throws Exception {
		IAIMonVerifySV verifySv = (IAIMonVerifySV) ServiceFactory.getService(IAIMonVerifySV.class);
		IBOAIMonVerifyValue value = verifySv.qryVerifyInfoByType("common");
		String illegal_char_check_xss = "";
		if (value != null) {
			illegal_char_check_xss = value.getRule();
		}
		if (!(StringUtils.isBlank(illegal_char_check_xss))) {
			try {
				PATTERN = Pattern.compile(illegal_char_check_xss);
			} catch (Exception ex) {
				PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
				log.debug("获取pattern失败，采用默认的pattern");
			}
		} else {
			PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
		}
		if ((!(StringUtils.isBlank(str))) && (PATTERN.matcher(str).find())) {
			StringBuilder sb = new StringBuilder();
			sb.append(new StringBuilder().append("输入的参数中含有不规则字符！").toString());
			log.debug(sb.toString());
			return false;
		}
		return true;
	}

	/**
	 * 保存前检查非法字符,返回true代表校验通过，返回false代表校验不通过
	 * @param o
	 * @param c
	 * @throws Exception
	 */
	public static boolean checkBeforeSave11(Object o, Class<?> c) throws Exception {
		IAIMonVerifySV verifySv = (IAIMonVerifySV) ServiceFactory.getService(IAIMonVerifySV.class);
		IBOAIMonVerifyValue value = verifySv.qryVerifyInfoByType("common");
		String illegal_char_check_xss = "";
		if (value != null) {
			illegal_char_check_xss = value.getRule();
		}
		if (!(StringUtils.isBlank(illegal_char_check_xss))) {
			try {
				PATTERN = Pattern.compile(illegal_char_check_xss);
			} catch (Exception ex) {
				PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
				log.debug("获取pattern失败，采用默认的pattern");
			}
		} else {
			PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
		}
		Map<String, String> map = getStringProperties(o, c);
		for (String key : map.keySet()) {
			if ((!(StringUtils.isBlank(map.get(key)))) && (PATTERN.matcher(map.get(key)).find())) {
				StringBuilder sb = new StringBuilder();
				sb.append(new StringBuilder().append("输入的参数中含有不规则字符！").toString());
				log.debug(sb.toString());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 保存启停模板
	 * @param o
	 * @param c
	 * @return
	 * @throws Exception  
	 * @Description:
	 */
	public static boolean checkBeforeSave(Object o, Class<?> c,String shellField) throws Exception {
		//从表里读取配置信息
		IAIMonVerifySV verifySv = (IAIMonVerifySV) ServiceFactory.getService(IAIMonVerifySV.class);
		IBOAIMonVerifyValue[] values = verifySv.qryAllVerifyInfos();
		String illegal_char_check_common_xss = "";
		String illegal_char_check_shell_xss="";
		if (values != null&&values.length>0) {
			for(IBOAIMonVerifyValue value:values){
				if("common".equals(value.getVerifyType())){
					illegal_char_check_common_xss = value.getRule();
				}else if("shell".equals(value.getVerifyType())){
					illegal_char_check_shell_xss = value.getRule();
				}
			}
		}
		//通用校验规则
		if (!(StringUtils.isBlank(illegal_char_check_common_xss))) {
			try {
				PATTERN = Pattern.compile(illegal_char_check_common_xss);
			} catch (Exception ex) {
				PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
				log.debug("获取pattern失败，采用默认的pattern");
			}
		} else {
			PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
		}
		//脚本校验规则
		if (!(StringUtils.isBlank(illegal_char_check_shell_xss))) {
			try {
				PATTERNSHELL = Pattern.compile(illegal_char_check_shell_xss);
			} catch (Exception ex) {
				PATTERNSHELL = Pattern.compile(DEFAULT_SHELL_ILLEGAL_CHAR_CHECK);
				log.debug("获取pattern失败，采用默认的pattern");
			}
		} else {
			PATTERNSHELL = Pattern.compile(DEFAULT_SHELL_ILLEGAL_CHAR_CHECK);
		}
		//当shellField不为空的时候，校验脚本；当为空的时候不校验脚本
		if(StringUtils.isNotBlank(shellField)){
			Map<String, String> map = getStringProperties(o, c);
			for (String key : map.keySet()) {
				//待修改
				if(!shellField.contains(key)){
					if ((!(StringUtils.isBlank(map.get(key)))) && (PATTERN.matcher(map.get(key)).find())) {
						StringBuilder sb = new StringBuilder();
						sb.append(new StringBuilder().append("输入的参数中含有不规则字符！").toString());
						log.debug(sb.toString());
						return false;
					}
				}else{
					if ((!(StringUtils.isBlank(map.get(key)))) && (PATTERNSHELL.matcher(map.get(key)).find())) {
						StringBuilder sb = new StringBuilder();
						sb.append(new StringBuilder().append("输入的参数中含有不规则字符！").toString());
						log.debug(sb.toString());
						return false;
					}
				}
			}
		}else{
			Map<String, String> map = getStringProperties(o, c);
			for (String key : map.keySet()) {
				if ((!(StringUtils.isBlank(map.get(key)))) && (PATTERN.matcher(map.get(key)).find())) {
					StringBuilder sb = new StringBuilder();
					sb.append(new StringBuilder().append("输入的参数中含有不规则字符！").toString());
					log.debug(sb.toString());
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 获取字段名和字段值放入map中
	 * @param o
	 * @param c
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> getStringProperties(Object o, Class<?> c) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		String fieldName = null;
		for (Field field : fields) {
			fieldName = field.getName();
			String type = field.getGenericType().toString();
			if (type.equals("class java.lang.String")) {
				if (!fieldName.contains("m_bo")) {
					Method m = o.getClass().getMethod(getMethodName(fieldName));
					String value = "";
					if (m.invoke(o) instanceof String) {
						value = (String) m.invoke(o);
						result.put(getFiledName(fieldName), value);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取得到字段值的方法
	 * @param name
	 * @return
	 */
	private static String getMethodName(String name) {
		if (null != name) {
			if (name.length() == 1) {
				name = name.toUpperCase();
			} else {
				if (name.contains("_")) {
					name = name.substring(2);
				} else {
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
				}
			}
			name = "get" + name;
		}
		return name;
	}
	
	private static String getFiledName(String name){
		if (name.length() == 1) {
			name = name.toUpperCase();
		} else {
			if (name.contains("_")) {
				name = name.substring(2);
			} else {
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
			}
		}
		return name;
	}
}
