package com.asiainfo.mainFrame.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ailk.common.data.impl.TreeBean;
import com.asiainfo.appframe.ext.exeframe.ws.client.WsClient;
import com.asiainfo.schedule.util.ScheduleConstants;

/**
 * 获取菜单的工具类 支持xml配置文件方式和webservice远程获取方式（暂未实现）
 * 
 * @author lj
 *
 */
public class MenuUtil
{
    
    private static final Logger LOGGER = Logger.getLogger(MenuUtil.class);
    
    /** 顶级菜单默认父ID：当菜单没有父ID时赋予 */
    private static final String TOP_ID = "-1";
    
    /**
     * 获得菜单树
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public static TreeBean[] getTreeItems(UserInfoInterface user, HttpServletRequest req)
        throws Exception
    {
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        
        // 若用户名为本地启动用户，则本地启动
        if (user.getID() == -110)
        {
            // 根节点初始化
            createRootByMenuXml(null, treeList, req);
            treeList = getLocalMenu(treeList, req);
        }
        else
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("get remote menu start");
            }
            treeList = getRemoteMenu(treeList, user, req);
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("get remote menu end,menu size is " + treeList.size());
            }
        }
        return treeList.toArray(new TreeBean[0]);
    }
    
    /**
     * 调用webservice接口
     * 
     * @param wsCode
     * @param Method
     * @param xml
     * @return
     */
    public static String doWebService(String wsCode, String Method, String xml)
        throws Exception
    {
        WsClient client = new WsClient(wsCode);
        Object result = client.invoke(Method, new Object[] {xml});
        return result == null ? null : (String)result;
    }
    
    /**
     * 获得XML中指定子元素的值
     * 
     * @param doc
     * @param path
     * @return
     * @throws Exception
     */
    public static String getValue(Document doc, String path)
        throws Exception
    {
        Node node = doc.selectSingleNode(path);
        if (node == null)
        {
            throw new Exception("parse xml failed, node is null,node path is " + path);
        }
        return node.getStringValue();
    }
    
    /**
     * 获取本地菜单
     * 
     * @param treeList
     * @return
     * @throws Exception
     */
    private static List<TreeBean> getLocalMenu(List<TreeBean> treeList, HttpServletRequest req)
        throws Exception
    {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("menu.xml");
        SAXReader reader = new SAXReader();
        Document doc = reader.read(in);
        List<Node> nodeList = doc.selectNodes("/record/moduleList/module");
        Iterator nodeIt = nodeList.iterator();
        while (nodeIt.hasNext())
        {
            TreeBean bean = new TreeBean();
            Node module = (Node)nodeIt.next();
            String level = module.selectSingleNode("ModuleLevel").getStringValue();
            int curentLevel = Integer.parseInt(level);
            String parentLevel = curentLevel < 3 ? "" : "_" + (curentLevel - 1);
            // id加上level为ID 以防出现1的二级11 和一级11ID冲突
            bean.setId(module.selectSingleNode("moduleId").getStringValue() + "_" + level);
            bean.setLabel(module.selectSingleNode("ModuleName").getStringValue());
            bean.setParentId(module.selectSingleNode("ParentModuleId").getStringValue() + parentLevel);
            // String url = module.selectSingleNode("ModuleUrl").getStringValue();
            // bean.setHref(StringUtils.isNotBlank(url)?getWholeUrl(url, req):null);
            bean.setLevel(module.selectSingleNode("ModuleLevel").getStringValue());
            treeList.add(bean);
        }
        return treeList;
    }
    
    /**
     * 获取远程菜单
     * 
     * @param treeList
     * @param user
     * @return
     * @throws Exception
     */
    private static List<TreeBean> getRemoteMenu(List<TreeBean> treeList, UserInfoInterface user, HttpServletRequest req)
        throws Exception
    {
        String reqXml = createMenuReqXml(user.getCode());
        String result = MenuUtil.doWebService("WSForAuth", "get_operModuleList", reqXml);
        treeList = xml2tree(result, treeList, req);
        return treeList;
    }
    
    private static String getWholeUrl(String menu, HttpServletRequest req)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        // 如果本身是全地址的则直接返回
        if (menu.indexOf("http:") >= 0)
        {
            return menu;
        }
        
        sb.append("http://")
            .append(InetAddress.getLocalHost().getHostAddress())
            .append(":")
            .append(req.getLocalPort())
            .append(req.getContextPath())
            .append("/?")
            .append(menu);
        return sb.toString();
    }
    
    private static String createMenuReqXml(String loginName)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='utf-8'?><record>")
            .append("<loginName>")
            .append(loginName)
            .append("</loginName>")
            .append("<funcType>")
            .append(ScheduleConstants.FUNC_TYPE)
            .append("</funcType>")
            .append("</record>");
        return sb.toString();
    }
    
    /**
     * XML解析为菜单树
     * 
     * @param xml
     * @param treeList
     * @return
     */
    private static List<TreeBean> xml2tree(String xml, List<TreeBean> treeList, HttpServletRequest req)
    {
        try
        {
            if (StringUtils.isNotBlank(xml))
            {
                SAXReader reader = new SAXReader();
                InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                Document doc = reader.read(in);
                List<Node> nodeList = doc.selectNodes("/record/returnCode");
                if (nodeList == null || nodeList.size() != 1)
                {
                    throw new Exception("parse login return xml failed, returnCode is null or more than one");
                }
                String returnCode = nodeList.get(0).getStringValue();
                if (Constants.RETURN_CODE_FAIL.equals(returnCode))
                {
                    throw new Exception(MenuUtil.getValue(doc, "/record/errMsg"));
                }
                else if (Constants.RETURN_CODE_SUCCESS.equals(returnCode))
                {
                    // 先生成根节点
                    // treeList = createRootByMenuXml(doc, treeList, req);
                    // 再生成其他节点
                    treeList = parseMenuXml(doc, treeList, req);
                }
                else
                {
                    throw new Exception("parse login return xml failed, returnCode is wrong:" + returnCode);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("login failed", e);
        }
        return treeList;
    }
    
    /**
     * 根据报文生成根节点
     * 
     * @param doc
     * @param treeList
     * @param levelMap
     * @return
     * @throws Exception
     */
    private static List<TreeBean> createRootByMenuXml(Document doc, List<TreeBean> treeList, HttpServletRequest req)
        throws Exception
    {
        List<Node> nodeList = null;
        if (doc != null)
        {
            nodeList = doc.selectNodes("/record/moduleList/module");
        }
        if (nodeList != null)
        {
            for (Node module : nodeList)
            {
                if (TOP_ID.equals(module.selectSingleNode("ParentModuleId").getStringValue()))
                {
                    TreeBean bean = new TreeBean();
                    bean.setCode("root");
                    bean.setId("-1");
                    bean.setLabel(module.selectSingleNode("ModuleName").getStringValue());
                    bean.setParentId("0");
                    String url = module.selectSingleNode("ModuleUrl").getStringValue();
                    bean.setHref(StringUtils.isNotBlank(url) ? getWholeUrl(url, req) : null);
                    bean.setValue("root");
                    treeList.add(bean);
                }
                break;
            }
        }
        
        if (treeList.size() == 0)
        {
            TreeBean rootNode = new TreeBean();
            rootNode.setCode("root");
            rootNode.setId("-1");
            rootNode.setLabel("\u8c03\u5ea6\u7ba1\u63a7\u5e73\u53f0");
            rootNode.setLevel("1");
            rootNode.setParentId("0");
            rootNode.setValue("root");
            treeList.add(rootNode);
        }
        return treeList;
    }
    
    /**
     * 根据报文生成树
     * 
     * @param doc
     * @param treeList
     * @param levelMap
     * @return
     * @throws Exception
     */
    private static List<TreeBean> parseMenuXml(Document doc, List<TreeBean> treeList, HttpServletRequest req)
        throws Exception
    {
        List<Node> nodeList = doc.selectNodes("/record/moduleList/module");
        if (nodeList != null)
        {
            for (Node module : nodeList)
            {
                TreeBean bean = new TreeBean();
                // 排除掉顶级节点
                if (TOP_ID.equals(module.selectSingleNode("ParentModuleId").getStringValue()))
                {
                    String moduleId = module.selectSingleNode("moduleId").getStringValue();
                    bean.setId(module.selectSingleNode("moduleId").getStringValue());
                    bean.setLabel(module.selectSingleNode("ModuleName").getStringValue());
                    bean.setParentId("0");
                    String url = module.selectSingleNode("ModuleUrl").getStringValue();
                    bean.setHref(StringUtils.isNotBlank(url) ? getWholeUrl(url, req) : null);
                    bean.setCode("root");
                    bean.setValue("root");
                    treeList.add(bean);
                }
                else
                {
                    String moduleId = module.selectSingleNode("moduleId").getStringValue();
                    bean.setId(module.selectSingleNode("moduleId").getStringValue());
                    bean.setLabel(module.selectSingleNode("ModuleName").getStringValue());
                    bean.setParentId(module.selectSingleNode("ParentModuleId").getStringValue());
                    String url = module.selectSingleNode("ModuleUrl").getStringValue();
                    bean.setHref(StringUtils.isNotBlank(url) ? getWholeUrl(url, req) : null);
                    treeList.add(bean);
                }
            }
        }
        return treeList;
    }
    
}
