package com.asiainfo.monitor.busi.panel.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.panel.transform.IPanelTransform;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.pool.impl.SimpleObjectPoolImpl;

public class RegionViewWrapperStrategy implements IViewWrapperStrategy {

	private static transient Log log = LogFactory.getLog(RegionViewWrapperStrategy.class);

	public String wrapperHeader(List taskList, IPanel panel) throws Exception {
		return "";
	}

	public String wrapperBody(TaskRtnModel rtnModel, IPanel panel)
			throws Exception {
		if (rtnModel == null || rtnModel.getRtns() == null || rtnModel.getRtns().size() < 1)
			return null;
		StringBuilder sb = new StringBuilder("<chart id=\""+panel.getPanel_Id()+"\" name=\""+panel.getPanel_Name()+"\">");
		try {
			if (StringUtils.isNotBlank(rtnModel.getTransform())) {
				IPanelTransform transform = (IPanelTransform) SimpleObjectPoolImpl.getInstance().getObject(rtnModel.getTransform());
				try {
					if (transform != null)
						sb.append(transform.transform(rtnModel));
					else {
						if (log.isDebugEnabled()) {
							log.debug(AIMonLocaleFactory.getResource("MOS0000217")+ "[" + rtnModel.getTransform() + "]");
						}
					}
				} finally {
					SimpleObjectPoolImpl.getInstance().releaseObject(rtnModel.getTransform(), transform);
				}
			}
		} catch (Exception e) {
			// 面板运行期异常:
			log.error(AIMonLocaleFactory.getResource("MOS0000218"), e);
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000218")+ e.getMessage());
		} finally {
			sb.append("</chart>");
		}
		if (log.isDebugEnabled()) {
			// "面板执行期结果:"
			log.debug(AIMonLocaleFactory.getResource("MOS0000219")+ sb.toString());
		}
		return sb.toString();
	}

	public String wrapperTail(List taskList, IPanel panel) throws Exception {
		return "";
	}

}
