package com.asiainfo.monitor.interapi.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class HttpClientUtil {

	public HttpClientUtil() {
		super();
	}

	/**
	 * 
	 * @param url:http://10.96.20.132:21000/version.txt
	 * @param timeout:秒
	 * @return
	 * @throws Exception
	 */
	public static String curl(String url, int timeout) throws Exception {
		String rtn = null;

		HttpClient client = null;
		GetMethod method = null;
		try {
			client = new HttpClient();
			client.setConnectionTimeout(timeout * 1000);

			method = new GetMethod(url);
			method.setRequestHeader("Connection", "close");
			method.getParams().setParameter("http.socket.timeout",new Integer(timeout * 1000));
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				rtn = new String(method.getResponseBody(), "gb2312");
			} else {
				// 不能正确获得
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000278"));
			}
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (client != null) {
				client.getHttpConnectionManager().closeIdleConnections(0);
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			}
		}

		return rtn;
	}
}
