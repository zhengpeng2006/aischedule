package com.asiainfo.monitor.tools.model.interfaces;

import com.asiainfo.monitor.tools.model.ContainerEvent;

public interface IContainerListener {

	public void containerEvent(ContainerEvent event) throws Exception;
}
