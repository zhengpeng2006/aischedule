package com.asiainfo.deploy.common.constants;

import java.util.regex.Pattern;

/**
 * 变量匹配
 * 
 * @author 孙德东(24204)
 */
public interface PatternConstants {
	final Pattern variablePattern = Pattern.compile("@@(\\w+)");
}
