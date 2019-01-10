package com.asiainfo.monitor.tools.license;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

/**
 * 
 * @author guocx
 * 该类不打包
 */
public class LicenseBetwixtWriter {

	/**
	 * 将License对象转成Xml对象
	 * @param license
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws IntrospectionException
	 */
	public static String java2XML(License license) throws IOException,SAXException, IntrospectionException {
		String reslutXml;
		// 创建一个输出流，将用来输出Java转换的XML文件
		StringWriter outputWriter = new StringWriter();
		// 输出XML的文件头
		outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		// 创建一个BeanWriter实例，并将BeanWriter的输出重定向到指定的输出流
		BeanWriter beanWriter = new BeanWriter(outputWriter);
		// 配置BeanWriter对象
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanWriter.getBindingConfiguration().setMapIDs(false); // 不自动生成ID
		beanWriter.setWriteEmptyElements(true); // 输出空元素
		beanWriter.enablePrettyPrint(); // 格式化输出
		// 将对象转换为XML
		beanWriter.write(license);
		// 获取转换后的结果
		reslutXml = outputWriter.toString();
		// 关闭输出流
		outputWriter.close();
		return reslutXml;
	}

	public static void main(String[] args) throws Exception{
		License license=new License();
		license.setLicenseID("90011");
		license.setCompany("Asiainfo Linkage Technologies (China) Inc");
		license.setCreationDate("2011/05/12");
		license.setEntries("");
		license.setExpiresDate("2011/05/30");
		license.setLicenseType("Commercial");
		license.setName("Monitor");
		license.setNumClusterMembers("1");
		license.setNumCopies("1");
		license.setProduct("Asiainfo Linkage Monitor Professional");
		license.setUrl("http://Asiainfo");
		license.setVersion("1.0");
		license.setSignature("302c02145dd415ac6bdc313e24d50db500a78bf7951ca45d021447d9dd31ba8b8d2ba32144d86bffee5a685d9532");
		String xml=LicenseBetwixtWriter.java2XML(license);
		System.out.println(xml);
	}
}
