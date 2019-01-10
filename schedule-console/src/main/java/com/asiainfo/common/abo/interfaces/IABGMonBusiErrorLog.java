package com.asiainfo.common.abo.interfaces;

import com.ailk.appengine.complex.abo.interfaces.IABOInterface;

public interface IABGMonBusiErrorLog extends IABOInterface,
		IABGMonBusiErrorLogBase {

	public void save() throws Exception;

}
