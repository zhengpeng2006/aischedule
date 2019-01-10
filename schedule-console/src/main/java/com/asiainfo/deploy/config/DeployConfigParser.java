package com.asiainfo.deploy.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.deploy.common.utils.DeployFileUtils;

/**
 * 解析sftpserver.properties
 * 
 * @author 孙德东(24204)
 */
public class DeployConfigParser {
	private static final Logger LOG = LoggerFactory.getLogger(DeployConfigParser.class);
	private static final String CONFIG_PATH = "sftpserver.properties";
	
	private DeployConfigParser(){}
	
	private static Properties prop = null;
	private static Object monitor = new Object();
	private static boolean loaded = false;
	
	public static Properties getConfig() {
		if (!loaded) {
			synchronized(monitor) {
				if (!loaded) {
					parser();
					loaded = true;
				}
			}
		}
		
		return prop;
	}
	
	private static void parser() {
		InputStream in = DeployFileUtils.loadFile(CONFIG_PATH);
		if (in == null) {
			LOG.error("没有配置sftpserver.properties，使用非分布式模式。");
			return;
		}
		
		prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			//重新设置为null
			prop = null;
			LOG.error("加载sftpserver.properties异常。", e);
			return;
		}
	}
}
