package com.asiainfo.monitor.busi.config;

import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.asiainfo.monitor.busi.cache.impl.PriEntity;

public class PriEntityTreeConfig {
	
	// 选中标志
	private boolean checked;

	// 下级
	private List childs;

	private PriEntity priEntity;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List getChilds() {
		return childs;
	}

	public void setChilds(List childs) {
		this.childs = childs;
	}

	public PriEntity getPriEntity() {
		return priEntity;
	}

	public void setPriEntity(PriEntity priEntity) {
		this.priEntity = priEntity;
	}

	public Element toXml() {

		Element self = DocumentHelper.createElement("node");
		if (this.getPriEntity() != null) {
			self.addAttribute("id", "" + this.getPriEntity().getId());
			self.addAttribute("label", this.getPriEntity().getName());
			self.addAttribute("parentId", "" + this.getPriEntity().getParentId());
			self.addAttribute("checked", "" + (this.isChecked() ? "1" : "0"));

			if (childs != null && childs.size() > 0) {
				for (int i = 0; i < childs.size(); i++)
					self.add(((PriEntityTreeConfig) childs.get(i)).toXml());
			}
		}
		return self;
	}

}
