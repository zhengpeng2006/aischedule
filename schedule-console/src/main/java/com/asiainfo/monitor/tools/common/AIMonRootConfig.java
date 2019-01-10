package com.asiainfo.monitor.tools.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import com.ai.appframe2.common.Util;
import com.ai.appframe2.util.XmlUtil;

public class AIMonRootConfig {

	private static transient Log log = LogFactory.getLog(AIMonRootConfig.class);

	private static AIMonRootConfig instance = null;

	public static final String S_ROOT_CONFIG_FILE = "AIRootConfig.xml";

	private Map attrMap = new HashMap();

	public AIMonRootConfig() {
		try {
			InputStream in = Util.getResourceAsStream(AIMonRootConfig.class,S_ROOT_CONFIG_FILE);
			Element e = XmlUtil.parseXml(in);
			initial(e);
		} catch (Throwable ex) {
			log.error("ERROR:RootConfig-InitialError Initialization configuration file "+ S_ROOT_CONFIG_FILE + " failed:", ex);
			throw new RuntimeException(ex);
		}
	}

	public static AIMonRootConfig getInstance() {
		if (instance == null) {
			synchronized (AIMonRootConfig.class) {
				if (instance == null) {
					instance = new AIMonRootConfig();
				}
			}
		}
		return instance;
	}

	public void initial(Element e) {
		List list = e.elements("item");
		for (int i = 0; i < list.size(); i++) {
			ConfigItem item = new ConfigItem((Element) list.get(i));
			this.attrMap.put(item.name, item);
		}
	}
	
	public String getValueByName(String name) {
		ConfigItem item = (ConfigItem)this.attrMap.get(name);
	    if(item == null){
	      return "";
	    }
	    return item.value;
	}
	
	class ConfigItem{
		String name;
		String value;
		String desc;
		
		public ConfigItem(String name){
			this.name = name;
		}
	  
		public ConfigItem(Element e){
			name = e.attributeValue("name");
			value = e.attributeValue("value");
			desc = e.attributeValue("desc");
		}
	   
		public Element createElement() {
			Element e = XmlUtil.createElement("item", "");
			e.addAttribute("name",name);
			e.addAttribute("value",value);
			e.addAttribute("desc",desc);
			return e;
	    }

		public String toString() {
			return XmlUtil.formatElement(createElement());
		}
	}
}
