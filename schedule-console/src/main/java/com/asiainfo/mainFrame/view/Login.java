package com.asiainfo.mainFrame.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import sun.misc.BASE64Decoder;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.complex.util.RuntimeServerUtil;
import com.ai.appframe2.privilege.LoginException;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.privilege.UserManagerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.MD5;
import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.ai.appframe2.web.BaseServer;
import com.ai.comframe.utils.TimeUtil;
import com.ailk.appengine.web.view.AppPage;
import com.asiainfo.appframe.ext.exeframe.ws.client.WsClient;
import com.asiainfo.mainFrame.util.Constants;
import com.asiainfo.mainFrame.util.MenuUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonLoginSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonLoginBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.util.ScheduleConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class Login extends AppPage
{
    public static final String WBS_USER_ATTR = "USERINFO_ATTR";
    
    // 同一个会话中，sessionId是一样的
    private static final Logger LOGGER = Logger.getLogger(Login.class);
    
    // 最大登录失败次数
    private static int maxFailLoginCount = 5000;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void userLogin(IRequestCycle cycle)
        throws Exception
    {
        String name = getContext().getParameter("name");
        String pwd = getContext().getParameter("pwd");
        UserInfoInterface userInfo = null;
        
        JSONObject jsonObj = new JSONObject();
        IAIMonLoginSV loginSV = (IAIMonLoginSV)ServiceFactory.getService(IAIMonLoginSV.class);
        IBOAIMonLoginValue loginValue = null;
        DataManagerFactory zkdm = DataManagerFactory.getDataManager();
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(pwd))
        {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(pwd);
            pwd = new String(b);
            name = name.trim();
            pwd = pwd.trim();
            MD5 md5 = new MD5();
            String pwdMD5 = md5.getMD5ofStr(pwd);
            
            Date nowDate = new Date();
            // String ip = cycle.getRequestContext().getRequest().getRemoteAddr();
            String ip = getIpAddr();
            loginValue = loginSV.qryLoginInfoByIpAndName(ip, name);
            long count = 0;
            // 当loginValue是空的时候，往表里插入一条新数据，只有ip和loginName
            if (loginValue == null)
            {
                BOAIMonLoginBean bean = new BOAIMonLoginBean();
                bean.setIp(ip);
                bean.setLoginName(name);
                bean.setFailCount(0);
                loginSV.saveLoginInfo(bean);
            }
            else
            {
                count = loginValue.getFailCount();
                if (count >= maxFailLoginCount)
                {
                    Date lastDate = loginValue.getLastFailTime();
                    long diff = nowDate.getTime() - lastDate.getTime();
                    long minutes = diff / (1000 * 60);
                    if (minutes < 30)
                    {
                        // 上次登录失败时间与现在时间对比，时间差少于30分钟，不让登录
                        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, "登录失败次数过多，上次登录失败时间为" + lastDate
                            + ",在此时间之后30分钟才能够重新登录");
                        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                        this.setAjax(jsonObj);
                        return;
                    }
                    else
                    {
                        if (loginValue != null)
                        {
                            count = 0;
                            loginValue.setFailCount(count);
                            loginSV.saveLoginInfo(loginValue);
                        }
                    }
                }
            }
            
            String userPwd = zkdm.getStaticData(name);
            
            if (StringUtils.isNotBlank(userPwd))
            {
                if (log.isInfoEnabled())
                {
                    log.info("预置工号登陆：" + name);
                }
                if (userPwd.equals(pwdMD5))
                {
                    userInfo = ServiceManager.getNewBlankUserInfo();
                    userInfo.setCode(name);
                    userInfo.setID(-110);
                    userInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
                    userInfo.setName("调度管理");
                    userInfo.setIP(getIpAddr(cycle.getRequestContext().getRequest()));
                    userInfo.setDomainId(ScheduleConstants.DOMAIN_ID);
                    userInfo.setOrgId(1);
                    userInfo.setSessionID(cycle.getRequestContext().getRequest().getSession().getId());
                }
                else
                {
                    loginValue = loginSV.qryLoginInfoByIpAndName(ip, name);
                    count++;
                    loginValue.setFailCount(count);
                    loginValue.setLastFailTime(TimeUtil.getSysTime());
                    loginSV.saveLoginInfo(loginValue);
                    LOGGER.error("login failed,password is wrong");
                    jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                    jsonObj.put(ResultConstants.ResultKey.RESULT_MSG,
                        "\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef");
                }
            }
            else
            {// 不是本地验证才去远程验证 调用远程接口 未完成
                try
                {
                    String loginXml = createLoginXml(name, pwd);
                    if (LOGGER.isDebugEnabled())
                    {
                        LOGGER.debug("remote login start");
                    }
                    String result = MenuUtil.doWebService("WSForAuth", "do_login", loginXml);
                    userInfo = xml2User(result, cycle);
                    if (LOGGER.isDebugEnabled())
                    {
                        LOGGER.debug("remote login finish");
                    }
                }
                catch (Throwable e)
                {
                    loginValue = loginSV.qryLoginInfoByIpAndName(ip, name);
                    count++;
                    loginValue.setFailCount(count);
                    loginValue.setLastFailTime(new Timestamp(System.currentTimeMillis()));
                    loginSV.saveLoginInfo(loginValue);
                    LOGGER.error("login failed", e);
                    jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
                }
            }
        }
        
        if (userInfo != null)
        {
            if (loginValue != null)
            {
                loginSV.deleteLoginInfo(loginValue);
            }
            UserManagerFactory.getUserManager().setLogined(userInfo);
            // 在session中放置一个标志序列号
            cycle.getRequestContext().getRequest().getSession().setAttribute(WBS_USER_ATTR, userInfo.getSerialID());
            
            // 设置前台cookie.增加add by zr at 2005-2-20
            try
            {
                Cookie c = new Cookie(WBS_USER_ATTR, userInfo.getSerialID());
                c.setMaxAge(-1);
                cycle.getRequestContext().getResponse().addCookie(c);
            }
            catch (Exception e)
            {
                // 登录时设置cookie出现错误
                LOGGER.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.BaseServer.set_cookie_error"), e);
            }
            
            if (!StringUtils.isBlank(RuntimeServerUtil.getServerName()))
            {
                try
                {
                    Cookie c = new Cookie("_BelongedSrvId", RuntimeServerUtil.getServerName());
                    c.setMaxAge(-1);
                    cycle.getRequestContext().getResponse().addCookie(c);
                }
                catch (Exception e)
                {
                    // 登录时设置cookie[_BelongedSrvId]出现错误
                    LOGGER.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.BaseServer.set_cookie_error"),
                        e);
                }
            }
            // user.setDomainId(channelId);
            // 将当前用户的信息发送到后台
            SessionManager.setUser(userInfo);
        }
        
        this.setAjax(jsonObj);
    }
    
    /**
     * 注销
     * 
     * @throws Exception
     */
    public void userLogout(IRequestCycle cycle)
        throws Exception
    {
        HttpServletRequest request = getContext().getRequest();
        HttpServletResponse response = getContext().getResponse();
        processLogout(request, response);
    }
    
    /**
     * 处理用户注销操作
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        UserInfoInterface user = BaseServer.getCurUser(request);
        if (user != null)
        {
            try
            {
                UserManagerFactory.getUserManager().loginOut(user);
                clearSession(request, response);
            }
            catch (LoginException ex)
            {
                LOGGER.error("logout user:" + user.getSerialID() + " error.");
            }
        }
        
        // set httpsession invalidate
        request.getSession().invalidate();
    }
    
    /**
     * 清除SESSION
     */
    private void clearSession(HttpServletRequest nRequest, HttpServletResponse nResponse)
        throws Exception
    {
        // 删除session中的用户信息
        nRequest.getSession().removeAttribute(BaseServer.WBS_USER_ATTR);
    }
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        
        initExtend(cycle);
    }
    
    /**
     * 获取客户端ip
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = null;
        Enumeration enu = request.getHeaderNames();
        
        for (; enu.hasMoreElements();)
        {
            String name = (String)enu.nextElement();
            if (name.equalsIgnoreCase("X-Forwarded-For"))
            {
                ip = request.getHeader(name);
            }
            else if (name.equalsIgnoreCase("Proxy-Client-IP"))
            {
                ip = request.getHeader(name);
            }
            else if (name.equalsIgnoreCase("WL-Proxy-Client-IP"))
            {
                ip = request.getHeader(name);
            }
            
            if (ip != null && ip.length() != 0)
            {
                break;
            }
        }
        
        if (ip == null || ip.length() == 0)
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 远程登录
     * 
     * @param xml
     * @return
     */
    private static String doLogin(String loginXml)
        throws Exception
    {
        WsClient client = new WsClient("WSForAuth");
        Object result = client.invoke("do_login", new Object[] {loginXml});
        return result == null ? null : (String)result;
    }
    
    /**
     * 创建登录报文
     * 
     * @param name
     * @param password
     * @return
     */
    private static String createLoginXml(String name, String password)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='utf-8'?><record>")
            .append("<loginName>")
            .append(name)
            .append("</loginName>")
            .append("<password>")
            .append(password)
            .append("</password>")
            .append("<entrance>3</entrance>")
            .append("<ip></ip>")
            .append("<mac></mac>")
            .append("<loginChannel></loginChannel>")
            .append("</record>");
        return sb.toString();
    }
    
    /**
     * 解析登录返回报文
     * 
     * @param xml
     * @param cycle
     * @return
     * @throws Exception
     */
    private static UserInfoInterface xml2User(String xml, IRequestCycle cycle)
        throws Exception
    {
        UserInfoInterface user = null;
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
                    user = parseLoginReturn(doc, cycle);
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
            throw e;
        }
        return user;
    }
    
    /**
     * 解析登录返回报文
     * 
     * @param doc
     * @param cycle
     * @return
     * @throws Exception
     */
    private static UserInfoInterface parseLoginReturn(Document doc, IRequestCycle cycle)
        throws Exception
    {
        UserInfoInterface userInfo = ServiceManager.getNewBlankUserInfo();
        userInfo.setCode(MenuUtil.getValue(doc, "/record/loginName"));
        userInfo.setID(Long.parseLong(MenuUtil.getValue(doc, "/record/opId")));
        userInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
        userInfo.setName(MenuUtil.getValue(doc, "/record/opName"));
        userInfo.setDomainId(ScheduleConstants.DOMAIN_ID);
        userInfo.setOrgId((Long.parseLong(MenuUtil.getValue(doc, "/record/orgId"))));
        userInfo.setIP(getIpAddr(cycle.getRequestContext().getRequest()));
        userInfo.setSessionID(cycle.getRequestContext().getRequest().getSession().getId());
        return userInfo;
    }
    
    private String getIpAddr()
    {
        String ipAddress = null;
        // ipAddress = this.getRequest().getRemoteAddr();
        ipAddress = this.getRequest().getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = this.getRequest().getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = this.getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
        {
            ipAddress = this.getRequest().getRemoteAddr();
            if (ipAddress.equals("127.0.0.1"))
            {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try
                {
                    inet = InetAddress.getLocalHost();
                }
                catch (UnknownHostException e)
                {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
            
        }
        
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15)
        { // "***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0)
            {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
    
    public static void main(String[] args)
    {
        MD5 md5 = new MD5();
        String pwd = "admin";
        String pwdMD5 = md5.getMD5ofStr(pwd);
        System.out.println(pwdMD5);
    }
    
}
