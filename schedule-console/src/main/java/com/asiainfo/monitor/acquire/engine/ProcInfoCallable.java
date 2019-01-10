package com.asiainfo.monitor.acquire.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.dto.AcqParamBean;
import com.asiainfo.socket.service.ClientHelper;

public class ProcInfoCallable implements Callable<Map<String, String>>
{
    /** 任务执行所需要的参数bean **/
    private AcqParamBean paramBean = null;

    public ProcInfoCallable(AcqParamBean paramBean)
    {
        this.paramBean = paramBean;
    }

    @Override
    public Map<String, String> call() throws Exception
    {
        Map<String, String> stateMap = new HashMap<String, String>();
        //        SSHBean sshBean = new SSHBean();
        //        sshBean.setIp(paramBean.getIp());
        //        sshBean.setSshPort(Integer.parseInt(paramBean.getPort()));
        //        sshBean.setUsername(paramBean.getUsername());
        //        sshBean.setPassword(K.k(paramBean.getPassword()));
        //        sshBean.setTmpFileName(paramBean.getTmpShellFileName());
        //        sshBean.setShell(paramBean.getShell());
        //        sshBean.setParameters(paramBean.getParamStr());
        //        sshBean.setTimeout(paramBean.getTimeout());

        //执行ssh脚本
        //String procState = SSHUtil.ssh4Shell(sshBean);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("TYPE_ID", AcquireConst.EXE_TYPE_SHELL);
        paramMap.put("USER_NAME", paramBean.getUsername());
        paramMap.put("PARAM", paramBean.getParamStr());
        paramMap.put("SHELL", paramBean.getShell());
        paramMap.put("IP", paramBean.getIp());

        //数据采集获取数据状态
        String procState = ClientHelper.execShell(paramMap);

        stateMap.put(paramBean.getServerCode(), procState.trim().equals("1") ? AcquireConst.FLAG_T : AcquireConst.FLAG_F);
        return stateMap;

    }

}
