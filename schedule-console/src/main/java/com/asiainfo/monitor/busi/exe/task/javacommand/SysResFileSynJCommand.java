package com.asiainfo.monitor.busi.exe.task.javacommand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.util.XmlUtil;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.tools.common.ITreeOperate;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.TreeNodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.DirectoryHelper;
import com.asiainfo.monitor.tools.util.SSHUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

public class SysResFileSynJCommand implements IJavaCommand {

	/**
	 * 
	 */
	public String execute(KeyName[] parameter) throws Exception {
		String result = "NAME:"+ AIMonLocaleFactory.getResource("MOS0000357")+"SUC:FAIL";
		if (parameter==null)
			return result;
		
		String hostId="";//WEB应用的标识
		String scanPath="";//扫描目录
		String writePath="";//写文件目录
		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.HOST+TypeConst._ID)){
				hostId=parameter[i].getKey();
			}else if (parameter[i].getName().equals("SCAN_PATH")){
				scanPath=parameter[i].getKey();
			}else
				writePath=parameter[i].getKey();
		}
		if (StringUtils.isNotBlank(hostId)){
            //			IAIMonPhysicHostAtomicSV hostSV=(IAIMonPhysicHostAtomicSV)ServiceFactory.getService(IAIMonPhysicHostAtomicSV.class);
            //			IBOAIMonPhysicHostValue hostBean=hostSV.getPhysicHostById(hostId);
            CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfo(hostId);
            String ip = hostBean.getHostIp();
            // Map<String, String> userInfoMap = hostBean.getMonUserInfo();
            //根据主机标识查询监控用户信息
            Map<String, String> userInfoMap = new HashMap<String, String>();
            userInfoMap = CommonSvUtil.qryMonitorNodeUserInfoByHostId(hostId);
            int sshPort = Integer.parseInt(hostBean.getConPort(CommonConst.CON_TYPE_SSH));
            String username = userInfoMap.get(CommonConst.USER_NAME);
            String password = K.k(userInfoMap.get(CommonConst.USER_PASSWD));
			String shellName="sys_res.sh";
			StringBuffer sb=new StringBuffer("#!/bin/sh");
			sb.append("respath=$1");
			sb.append("\n");			
			sb.append("lcount=0");
			sb.append("\n");
			sb.append("function scanclass()");
			sb.append("\n");
			sb.append("{");
			sb.append("\n");
			sb.append("splitchar=\",\"");
			sb.append("\n");
			sb.append("local curpath=$1");
			sb.append("\n");
			sb.append("local wholepath=\"\"");
			sb.append("\n");
			sb.append("cd $curpath");
			sb.append("\n");
			sb.append("itempath=`pwd`");
			sb.append("\n");			
			sb.append("totlen=`expr length $itempath`");
			sb.append("\n");
			sb.append("sublocation=`expr $totlen - 3`");
			sb.append("\n");
			sb.append("suffix=`expr substr $itempath $sublocation 4`");
			sb.append("\n");
			sb.append("if [ $suffix != \"/com\" ]; then");
			sb.append("\n");
			sb.append("len_dir=`expr length $curpath`");
			sb.append("\n");
			sb.append("suffix_dir=`expr substr $curpath $len_dir 1`");
			sb.append("\n");
			sb.append("if [ $suffix_dir = \",\" ]; then");
			sb.append("\n");
			sb.append("wholepath=$itempath");
			sb.append("\n");
			sb.append("else");
			sb.append("\n");
			sb.append("if [ $lcount -eq 0 ]; then");
			sb.append("\n");			
			sb.append("lcount=`expr $lcount + 1`");
			sb.append("\n");
			sb.append("wholepath=$itempath");
			sb.append("\n");
			sb.append("else");
			sb.append("\n");
			sb.append("wholepath=$splitchar$itempath");
			sb.append("\n");
			sb.append("fi");
			sb.append("\n");
			sb.append("fi");
			sb.append("\n");
			sb.append("for i in `ls |grep -v \"/\"`");
			sb.append("\n");
			sb.append("do");
			sb.append("\n");
			sb.append("if [ -f $i ]; then");
			sb.append("\n");
			sb.append("wholepath=$wholepath:$i");
			sb.append("\n");
			sb.append("fi");
			sb.append("\n");
			sb.append("done");
			sb.append("\n");
			sb.append("for j in `ls -l|grep \"^d\"|awk '{print$8}'`");
			sb.append("\n");
			sb.append("do");
			sb.append("\n");
			sb.append("cd $j");
			sb.append("\n");
			sb.append("local pathj=`pwd`");
			sb.append("\n");
			sb.append("if [ -d $pathj ]; then");
			sb.append("\n");
			sb.append("local ipath=`scanclass $pathj`");
			sb.append("\n");
			sb.append("wholepath=$wholepath$ipath");
			sb.append("\n");
			sb.append("fi");
			sb.append("\n");
			sb.append("cd ..");
			sb.append("\n");
			sb.append("done");
			sb.append("\n");
			sb.append("fi");
			sb.append("\n");
			sb.append("echo $wholepath");
			sb.append("\n");
			sb.append("}");
			sb.append("\n");
			sb.append("rtn=`scanclass $respath`");
			sb.append("\n");
			sb.append("echo $rtn");
			String res=SSHUtil.ssh4Shell(ip, sshPort, username, password, shellName,scanPath,sb.toString());
			if (!StringUtils.isBlank(res)){
				INodeInfo root=DirectoryHelper.buildDirTreeNode(res);
				WrapperTreeOperate operate=new WrapperTreeOperate();
				((TreeNodeInfo)root).operate(operate);
				String xml=operate.getTreeXml().toString();
				org.dom4j.Element element=XmlUtil.parseXmlOfString(xml);
				XmlUtil.writerXml(writePath, element);
				result="NAME:"+ AIMonLocaleFactory.getResource("MOS0000357")+"SUC:SUCC";
			}
		}
	    return result;
	}


	public class WrapperTreeOperate implements ITreeOperate{
		
		private StringBuffer treeXml=new StringBuffer("");
		
		private AtomicInteger count=new AtomicInteger(0);
		public WrapperTreeOperate(){
			
		}
		
		public StringBuffer getTreeXml() {
			return treeXml;
		}

		public void setTreeXml(StringBuffer treeXml) {
			this.treeXml = treeXml;
		}

		public void operate(INodeInfo node) throws Exception{			
			if (node!=null && node.hasChild()){
				Map child=(Map)node.getChild();
				String rootDir=StringUtils.substring(node.getId(),node.getId().indexOf("/classes"));
				//根结点
				if (node.getType().equals(DirectoryHelper.ROOT)){
					treeXml.append("<node label=\"").append(rootDir).append("\" id=\"").append(count.addAndGet(1)).append("\" >\n");
				}
				for (Iterator it=child.keySet().iterator();it.hasNext();){
					String key=String.valueOf(it.next());
					INodeInfo item=(INodeInfo)child.get(key);
					if (item.hasChild()){
						String shortDir=StringUtils.substring(item.getId(),item.getId().indexOf("/classes"));
						String parentDir=StringUtils.substring(item.getParent().getId(),item.getParent().getId().indexOf("/classes"));
						treeXml.append("<node label=\"").append(shortDir).append("\" id=\"").append(count.addAndGet(1)).append("\">\n");
						operate(item);
					}else{
						String shortDir=StringUtils.substring(item.getParent().getId(),item.getParent().getId().indexOf("/classes"));
						treeXml.append("<node label=\"").append(item.getId()).append("\" id=\"").append(count.addAndGet(1)).append("\"/>\n");
					}
				}
				treeXml.append("</node>");
			}
		}
	}
}
