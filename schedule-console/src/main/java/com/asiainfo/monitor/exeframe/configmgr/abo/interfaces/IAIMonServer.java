package com.asiainfo.monitor.exeframe.configmgr.abo.interfaces;

import com.ailk.appengine.complex.abo.interfaces.IABOInterface;

public interface IAIMonServer extends IABOInterface, IAIMonServerBase
{

    public void save() throws Exception;

}
