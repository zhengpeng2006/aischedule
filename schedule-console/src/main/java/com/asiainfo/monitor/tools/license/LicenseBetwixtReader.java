package com.asiainfo.monitor.tools.license;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.betwixt.io.BeanReader;
import org.xml.sax.SAXException;

/**
 * 将License的xml串映射成对象
 * 
 * @author guocx
 * 
 */
public class LicenseBetwixtReader {

	/**
	 * 将xml转成对象
	 * 
	 * @param xml 
	 * @return
	 * @throws IntrospectionException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static License xml2Java(String xml) throws IntrospectionException,IOException, SAXException {
		// 创建一个读取xml文件的流
		StringReader xmlReader = new StringReader(xml);
		// 创建一个BeanReader实例，相当于转换器
		BeanReader beanReader = new BeanReader();

		// 配置BeanReader实例
		beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanReader.getBindingConfiguration().setMapIDs(false); // 不自动生成ID
		// 注册要转换对象的类，并指定根节点名称
		beanReader.registerBeanClass("License", License.class);
		// 将XML解析Java Object
		License License = (License) beanReader.parse(xmlReader);
		return License;
	}

	/**
	 * 将xml流转成对象
	 * @param xml
	 * @return
	 * @throws IntrospectionException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static License xml2Java(InputStream xml) throws IntrospectionException,IOException, SAXException {		
		// 创建一个BeanReader实例，相当于转换器
		BeanReader beanReader = new BeanReader();
		// 配置BeanReader实例
		beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanReader.getBindingConfiguration().setMapIDs(false); // 不自动生成ID
		// 注册要转换对象的类，并指定根节点名称
		beanReader.registerBeanClass("License", License.class);
		// 将XML解析Java Object
		License License = (License) beanReader.parse(xml);
		return License;
	}
}
