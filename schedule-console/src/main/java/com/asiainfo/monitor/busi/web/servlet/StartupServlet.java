package com.asiainfo.monitor.busi.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ai.appframe2.complex.Startup;

public class StartupServlet extends HttpServlet {

	public StartupServlet() {
		super();
	}

	public void init() throws ServletException {
		Startup.startup();
	}
}
