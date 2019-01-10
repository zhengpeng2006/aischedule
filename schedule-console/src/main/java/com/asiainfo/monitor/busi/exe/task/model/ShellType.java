package com.asiainfo.monitor.busi.exe.task.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.common.ExecShellUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.model.interfaces.IShellType;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * shell 脚本类型，执行shell脚本
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class ShellType extends DefaultCmdType implements IShellType, Serializable
{

    private transient static Log log = LogFactory.getLog(ShellType.class);

    public ShellType()
    {
    }

    public String getType()
    {
        return CmdType.SHELL;
    }

    /**
     * 执行命令
     * 
     * @param task
     * @return
     * @throws
     */
    public void doTask(ITaskRtn taskRtn) throws Exception
    {
        try {
            String innerParam = "";
            
            // 获取容器参数
            List paramList = ((BaseContainer) this.getContainer()).getParameter();
            if(paramList != null && paramList.size() > 0) {
                StringBuilder sb = new StringBuilder("");

                for(int count = 0, k = 0; count < paramList.size(); count++) {
                    KeyName itemInnerPara = (KeyName) paramList.get(count);

                    // 如果内参是主机ID,则不视为内参
                    if(itemInnerPara.getName().equals(TypeConst.HOST + TypeConst._ID)) {
                        continue;
                    }
                    if(k > 0) {
                        sb.append(" ");
                    }
                    sb.append(itemInnerPara.getKey());
                    k++;
                }
                innerParam = sb.toString();
            }
            // 主机信息
            // 转换shell脚本，主要是换行符之类的处理
            String shell = this.parseShellExpr(this.getContainer().getExpr());
            String rtn = null;
            String charEncoding = CharEncoding.ISO_8859_1;

            //获取传递参数的编码方式
            IAIMonStaticDataSV staticDataSV = (IAIMonStaticDataSV) ServiceFactory.getService(IAIMonStaticDataSV.class);
            IBOAIMonStaticDataValue[] datas = staticDataSV.queryByCodeType("SSH_CHARENCODING");
            if(datas != null && datas.length > 0) {
                charEncoding = datas[0].getCodeValue();
            }
            //入参
            String inParamStr = new String(innerParam.getBytes(), charEncoding);
            String shellFile = ExecShellUtil.getShellFile(shell);
            rtn = ExecShellUtil.execShell(shellFile, inParamStr);
            rtn = this.process(rtn);
            taskRtn.setMsg(rtn);
        }
        catch(Exception e) {
            // 执行类型为Command的任务["+this.getContainer().getName()+"]时发生异常
            log.error(AIMonLocaleFactory.getResource("MOS0000318", this.getContainer().getName()) + ":" + e.getMessage());
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000318", this.getContainer().getName()) + ":" + e.getMessage());
        }
        return;
    }

    /**
     * 转换shell脚本
     * 
     * @param shell
     * @return
     */
    public String parseShellExpr(String shell)
    {
        String expr = shell;
        if(shell.indexOf("\r") != -1) {
            expr = shell.replace("\r", "\n");
        }
        return expr;
    }
}
