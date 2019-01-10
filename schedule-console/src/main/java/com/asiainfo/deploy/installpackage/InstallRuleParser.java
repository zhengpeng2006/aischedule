package com.asiainfo.deploy.installpackage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.deploy.common.constants.Category.BooleanType;
import com.asiainfo.deploy.common.constants.Category.CompressType;
import com.asiainfo.deploy.exception.ErrorCode;

/**
 * 解析InstallRule
 * 
 * @author 孙德东(24204)
 */
public class InstallRuleParser {
	private static final String GROUP_SEPERATOR = ";";
	private static final String INNER_SEPERATOR = ":";
	//INSTALL_RULE:文件名:存放路径:是否需要解压:压缩格式; 因此每个规则有4个段
	private static final int SECTION_PER_RULE = 4;
	private InstallRuleParser() {
	}

	public static String groupSeperator() {
		return GROUP_SEPERATOR;
	}
	
	public static String innerSeperator() {
		return INNER_SEPERATOR;
	}
	
	/**
	 * 解析压缩格式
	 * @param installRule
	 * @return
	 * @throws Exception 
	 */
	public static List<InstallRule> parser(String installRule) throws Exception {
		if (StringUtils.isEmpty(installRule)) {
			//throw new Exception("" + ErrorCode.Common.PARSE_INSTALL_RULE_ERROR);
			return new ArrayList<InstallRule>(0);
		}
		
		List<InstallRule> ruleList = new ArrayList<InstallRule>();
		String[] groups = StringUtils.split(installRule, GROUP_SEPERATOR);
		for (String group : groups) {
			String[] rules = StringUtils.split(group, INNER_SEPERATOR);
			if (rules == null || rules.length != SECTION_PER_RULE) {
				throw new Exception("" + ErrorCode.Common.PARSE_INSTALL_RULE_ERROR);
			}
			
			InstallRule ruleObj = new InstallRule();
			ruleObj.src = StringUtils.trim(rules[0]); //要获取的安装文件在ftp服务器上的的绝对路径
			ruleObj.dst = StringUtils.trim(rules[1]); //安装文件在待安装主机上的绝对路径
			
			BooleanType boolType = BooleanType.getBooleanType(StringUtils.trim(rules[2]));
			if (boolType == BooleanType.Unknown) {
				throw new Exception("" + ErrorCode.Common.PARSE_INSTALL_RULE_ERROR);
			}
			ruleObj.needUnzip = boolType.value();
			ruleObj.compressType = CompressType.getCompressType(StringUtils.trim(rules[3]));
			
			ruleList.add(ruleObj);
		}
		
		return ruleList;
	}
	
	/**
	 * INSTALL_RULE:文件名:存放路径:是否需要解压(Y/N):压缩格式(jar);
	 * 当不需要压缩式，压缩格式填：plain
	 */
	
	public static class InstallRule {
		public String src;
		public String dst;
		public boolean needUnzip;
		public CompressType compressType; 
	}
}
