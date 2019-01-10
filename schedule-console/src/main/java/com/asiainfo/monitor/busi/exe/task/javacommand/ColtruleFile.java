package com.asiainfo.monitor.busi.exe.task.javacommand;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.SSHUtil2;
/**
 * 后台定时任务,采集Trace文件
 * @author Guocx
 *
 */
public class ColtruleFile implements IJavaCommand {

	private transient static Log log = LogFactory.getLog(ColtruleFile.class);

	/**
	 * 后台定时定时任务采集文件
	 * com.asiainfo.appframe.ext.monitor.exe.javacommand.ColtruleFile;COLTIP:192.168.1.1|COLTPORT:8090|COLTUSER:aiweb|COLTPASSWORD:密码
	 * in:参数in由于JavaCommandType对象将表中的参数配置追加到后面，所有可能有8
	 */
	public String execute(KeyName[] parameter) throws Exception {
		String rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000304") + "|COLTRULE:0";
	    
	    
	    String ip="",port="",userName="",password="";
	    String shellFile="",localPath="",remotePath="";
	    
	    for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals("COLTIP")){
				ip=parameter[i].getKey();
			}else if (parameter[i].getName().equals("COLTPORT")){
				port=parameter[i].getKey();
			}else if (parameter[i].getName().equals("COLTUSER")){
				userName=parameter[i].getKey();
			}else if (parameter[i].getName().equals("COLTPASSWORD")){
				password=parameter[i].getKey();
			}else if (parameter[i].getName().equals("SHELLFILE")){
				shellFile=parameter[i].getKey();
			}else if (parameter[i].getName().equals("LOCALPATH")){
				localPath=parameter[i].getKey();
			}else if (parameter[i].getName().equals("REMOTEPATH")){
				remotePath=parameter[i].getKey();
			}
		}
	    
	    try {
	    	if (StringUtils.isBlank(shellFile) || StringUtils.isBlank(localPath) || StringUtils.isBlank(remotePath)){
	    		if (log.isDebugEnabled())
	    			log.debug(AIMonLocaleFactory.getResource("MOS0000305") + ",shellfile:"+shellFile+",localPath:"+localPath+",remotePath:"+remotePath);
	    		rtn="NAME:" + AIMonLocaleFactory.getResource("MOS0000304") + "|COLTRULE:-1";
	    	}else{
	    		int c = SSHUtil2.collectFile(ip,Integer.parseInt(port),userName,password,shellFile,localPath,remotePath,"trc", null);
		    	rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000304") + "|COLTRULE:"+c;
	    	}
	    }
	    catch (Throwable ex) {
	      rtn = "NAME:" + AIMonLocaleFactory.getResource("MOS0000304") + "|COLTRULE:0";
	      // 连接:"+ip+":"+port+"采集文件失败
	      log.error(AIMonLocaleFactory.getResource("MOS0000165", ip, port),ex);
	    }
	    finally{
	      ip=null;
	      port=null;
	      userName=null;
	      password=null;
	    }
	    return rtn;
	}

}
