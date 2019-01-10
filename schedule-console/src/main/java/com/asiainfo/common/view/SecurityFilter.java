package com.asiainfo.common.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.AIConfigManager;
import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.impl.SecAllAccessCacheImpl;
import com.ai.appframe2.complex.mbean.standard.trace.WebTraceMonitor;
import com.ai.appframe2.complex.secframe.ICenterUserInfo;
import com.ai.appframe2.complex.secframe.access.SecAccessFactory;
import com.ai.appframe2.complex.trace.impl.WebTrace;
import com.ai.appframe2.complex.util.EscapeURLDecoder;
import com.ai.appframe2.complex.util.JVMID;
import com.ai.appframe2.complex.util.RuntimeServerUtil;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.ai.appframe2.web.BaseServer;
import com.ai.appframe2.web.log.WebLogFactory;
import com.ailk.appengine.complex.secframe.access.IEnhancedSecAccess;
import com.ailk.appengine.complex.service.security.SecurityInvokeHelper;
import com.ailk.appengine.web.exception.WebSecurityException;

public class SecurityFilter implements Filter
{
    private static transient Log log = LogFactory.getLog(SecurityFilter.class);
    
    protected FilterConfig filterConfig;
    
    private static boolean IS_SESSION_CHECK = false;
    
    private static boolean IS_URL_CHECK = false;
    
    private static Map UNCHECK_URL = null;
    
    public static Boolean IS_INIT_NEW_URL_FUNCTION_MAP = Boolean.FALSE;
    
    private static String DEFAULT_ILLEGAL_CHAR_CHECK =
        "document.cookie|href|script|select |select/|select\\(|select\\*|insert |insert/|insert\\(|insert\\*|update |update/|update\\(|update\\*|delete |delete/|delete\\(|delete\\*|truncate |truncate/|truncate\\(|truncate\\*|exec |exec/|exec\\(|exec\\*|drop |drop/|drop\\(|drop\\*";
    
    private static Pattern PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
    
    private static String URL_CHECK_LEVEL = "MENU";
    
    @Override
    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        this.filterConfig = filterConfig;
        try
        {
            String str = AIConfigManager.getConfigItem("IS_LOGIN_CHECK_FLAG");
            if ((str != null) && (str.equalsIgnoreCase("Y")))
            {
                IS_SESSION_CHECK = true;
            }
            
            str = AIConfigManager.getConfigItem("IS_URL_CHECK_FLAG");
            if ((str != null) && ("Y".equalsIgnoreCase(str)))
            {
                IS_URL_CHECK = true;
            }
            UNCHECK_URL = AIConfigManager.getConfigItemsByKind("UNCHECK_URL");
            
            String illegal_char_check = AIConfigManager.getConfigItem("ILLEGAL_CHAR_CHECK");
            if (!(StringUtils.isBlank(illegal_char_check)))
            {
                try
                {
                    PATTERN = Pattern.compile(illegal_char_check);
                    log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.use_error",
                        new String[] {illegal_char_check}));
                }
                catch (Exception ex)
                {
                    PATTERN = Pattern.compile(DEFAULT_ILLEGAL_CHAR_CHECK);
                    log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.use_error_default",
                        new String[] {illegal_char_check, DEFAULT_ILLEGAL_CHAR_CHECK}),
                        ex);
                }
            }
            else
            {
                log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.use_default",
                    new String[] {DEFAULT_ILLEGAL_CHAR_CHECK}));
            }
            String urlCheckLevel = AIConfigManager.getConfigItem("URL_CHECK_LEVEL");
            if (!(StringUtils.isBlank(urlCheckLevel)))
            {
                URL_CHECK_LEVEL = urlCheckLevel.toUpperCase();
            }
            
        }
        catch (Exception ex)
        {
            log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.fail_get_aiconfig"),
                ex);
        }
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        SessionManager.setContextName(req.getContextPath());
        SessionManager.setRequest(req);
        
        SessionManager.setUser(null);
        
        SessionManager.setLocale(null);
        
        UserInfoInterface curUser = null;
        
        HttpSession session = req.getSession(false);
        String serialID = null;
        if (session != null)
        {
            serialID = (String)req.getSession().getAttribute("USERINFO_ATTR");
        }
        
        if (!(StringUtils.isBlank(serialID)))
            try
            {
                curUser = BaseServer.getCurUser(req);
                SessionManager.setUser(curUser);
                SessionManager.setLocale(req.getLocale());
            }
            catch (Exception ex)
            {
                log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.obtain_info_error",
                    new String[] {serialID}),
                    ex);
                
                throw new ServletException(ex);
            }
        try
        {
            SecurityInvokeHelper.removeCurUIEntityId();
            String url = StringUtils.substringAfter(req.getRequestURI().toString(), req.getContextPath());
            String query = req.getQueryString();
            if (log.isDebugEnabled())
            {
                log.debug(new StringBuilder().append("req.getRequestURI()=")
                    .append(req.getRequestURI())
                    .append(",url=")
                    .append(url)
                    .append(",contextPath=")
                    .append(req.getContextPath())
                    .append(",servlet=")
                    .append(req.getServletPath())
                    .append(",query=")
                    .append(query)
                    .toString());
            }
            if (!(StringUtils.isBlank(query)))
            {
                url = new StringBuilder().append(url).append("?").append(query).toString();
            }
            
            if ((!(StringUtils.isBlank(query)))
                && (PATTERN.matcher(EscapeURLDecoder.decode(query.toLowerCase())).find()))
            {
                StringBuilder sb = new StringBuilder();
                
                String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.char_error");
                sb.append(new StringBuilder().append(msg).append("</p>").toString());
                log.error(sb.toString());
                resp.setContentType("text/html; charset=GBK");
                PrintWriter writer = resp.getWriter();
                writer.print(sb.toString());
                return;
            }
            
            int j = judge(req, curUser, url);
            String ipMsg = null;
            String numberMsg = null;
            String orgMsg = null;
            String timeMsg = null;
            String undoLinkMsg = null;
            String operationMsg = null;
            String urlMsg = null;
            String permitLimitMsg = null;
            if ((j == -1) || (j == -2))
            {
                ipMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.user_ip");
                
                numberMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.user_number");
                
                orgMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.user_org");
                
                timeMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.login_time");
                
                undoLinkMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.undo_url");
                
                operationMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.record_action");
                
                urlMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.action_url");
                
                permitLimitMsg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.no_permission");
            }
            
            if (j > 0)
            {
                resp.addHeader("P3P", "CP=CAO PSA OUR");
                boolean isCreateTrace = false;
                
                if ((WebTraceMonitor.isEnableGlobalTrace()) && (curUser != null) && (curUser.getCode() != null)
                    && (curUser.getCode().equals(WebTraceMonitor._getCode())) && (url != null)
                    && (url.indexOf("/misc") == -1))
                {
                    if (WebTraceMonitor._getUrl() != null)
                    {
                        if (WebTraceMonitor._getUrl().indexOf(url) != -1)
                            isCreateTrace = true;
                        else
                            isCreateTrace = false;
                    }
                    else
                    {
                        isCreateTrace = true;
                    }
                    
                    if ((isCreateTrace) && (WebTraceMonitor._getClientIp() != null) && (curUser.getIP() != null))
                    {
                        if (WebTraceMonitor._getClientIp().indexOf(curUser.getIP()) != -1)
                            isCreateTrace = true;
                        else
                            isCreateTrace = false;
                    }
                    else
                    {
                        isCreateTrace = true;
                    }
                    
                    if (isCreateTrace)
                    {
                        WebTrace objWebTrace = new WebTrace();
                        objWebTrace.setCreateTime(System.currentTimeMillis());
                        objWebTrace.setUrl(url);
                        objWebTrace.setServerIp(RuntimeServerUtil.getServerIP());
                        objWebTrace.setServerName(RuntimeServerUtil.getServerName());
                        if (curUser.getIP() != null)
                        {
                            objWebTrace.setClientIp(curUser.getIP());
                        }
                        if (curUser.getCode() != null)
                        {
                            objWebTrace.setCode(curUser.getCode());
                        }
                        if (curUser instanceof ICenterUserInfo)
                        {
                            ((ICenterUserInfo)curUser).setTrace(true);
                            ((ICenterUserInfo)curUser).setWebTrace(objWebTrace);
                        }
                        
                    }
                    
                }
                
                long start = 0L;
                String logId = null;
                boolean isEnable = WebLogFactory.isEnable();
                if (isEnable)
                {
                    start = System.currentTimeMillis();
                    
                    logId =
                        new StringBuilder().append(RuntimeServerUtil.getServerName())
                            .append("^")
                            .append(JVMID.getLocalShortJVMID())
                            .append("^")
                            .append(WebLogFactory.getLogId())
                            .toString();
                    
                    if (curUser != null)
                    {
                        curUser.set("LOG_ID_KEY", logId);
                    }
                }
                
                try
                {
                    chain.doFilter(request, response);
                }
                finally
                {
                    if (isCreateTrace)
                    {
                        if ((curUser != null) && (curUser instanceof ICenterUserInfo))
                        {
                            ((ICenterUserInfo)curUser).setTrace(false);
                            ((ICenterUserInfo)curUser).setWebTrace(null);
                        }
                        
                        if (WebTraceMonitor.isTimeOut())
                        {
                            WebTraceMonitor.disableGlobalTrace();
                        }
                        
                    }
                    
                    if (isEnable)
                    {
                        WebLogFactory.logWebInfo(logId, request, response, start, url);
                    }
                    
                }
                
            }
            else if (j == 0)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(new StringBuilder().append("j=0,url:").append(url).toString());
                }
                String urlSource = req.getParameter("url_source");
                if ("XMLHTTP".equalsIgnoreCase(urlSource))
                {
                    resp.getWriter().write("<LOGIN_OUT>LOGINOUT</LOGIN_OUT>");
                }
                else if ((url.indexOf("?service=ajax") > -1) && (url.indexOf("page=Logout") == -1))
                {
                    resp.setStatus(206);
                    // pageui检测页面超时使用，不可变动
                    resp.getWriter()
                        .write("{\"context\": {\"x_resultinfo\": \"ok\",\"x_resultcode\": \"0\"},\"data\": {\"retcode\": \"4\",\"resultMsg\": \"Not login\"}}");
                }
                else if (url.indexOf("?service=page") > -1)
                {
                    StringBuffer sb = new StringBuffer();
                    StringBuffer pre = null;
                    // 如果不是IPv4或者localhost，则为域名访问，截出域名
                    String httpUrl = BaseServer.getLogoutHTML();
                    if (StringUtils.isBlank(httpUrl))
                    {
                        throw new Exception("login page has not bean configed in AIConfig.xml");
                    }
                    
                    if (!httpUrl.contains("http:"))
                    {
                        httpUrl = req.getRequestURL() + BaseServer.getLogoutHTML();
                    }
                    sb.append("<html><head><script>top.location.href='")
                        .append(httpUrl)
                        .append("'</script></head><body>session invalid,please wait until the page go to login page")
                        .append("</body></html>");
                    if (log.isDebugEnabled())
                    {
                        log.debug(sb.toString());
                    }
                    resp.getWriter().write(sb.toString());
                }
                else
                {
                    req.getRequestDispatcher(BaseServer.getLogoutHTML()).forward(request, response);
                }
                
            }
            else if ((j == -1) || (j == -2))
            {
                StringBuilder sb = new StringBuilder();
                sb.append(new StringBuilder().append("<p>")
                    .append(ipMsg)
                    .append("IP:<font color=\"#FF0000\"><b>")
                    .append(curUser.getIP())
                    .append("</b></font></p>")
                    .toString());
                sb.append(new StringBuilder().append("<p>")
                    .append(numberMsg)
                    .append(":<font color=\"#FF0000\"><b>")
                    .append(curUser.getCode())
                    .append("</b></font></p>")
                    .toString());
                sb.append(new StringBuilder().append("<p>")
                    .append(orgMsg)
                    .append(":<font color=\"#FF0000\"><b>")
                    .append(curUser.getOrgName())
                    .append("</b></font></p>")
                    .toString());
                sb.append(new StringBuilder().append("<p>")
                    .append(timeMsg)
                    .append(":<font color=\"#FF0000\"><b>")
                    .append(new Date())
                    .append("</b></font></p>")
                    .toString());
                sb.append(new StringBuilder().append("<p>")
                    .append(undoLinkMsg)
                    .append(":<font color=\"#FF0000\"><b>")
                    .append(url)
                    .append("</b></font></p>")
                    .toString());
                if (j == -1)
                    sb.append(new StringBuilder().append("<p><font color=\"#FF0000\"><b>")
                        .append(operationMsg)
                        .append("!</b></font></p>")
                        .toString());
                else
                {
                    sb.append(new StringBuilder().append("<p><font color=\"#FF0000\"><b>")
                        .append(permitLimitMsg)
                        .append("!</b></font></p>")
                        .toString());
                }
                log.error(sb.toString());
                resp.setContentType("text/html; charset=GBK");
                PrintWriter writer = resp.getWriter();
                writer.print(sb.toString());
            }
            else
            {
                String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.wrong_value");
                throw new Exception(msg);
            }
        }
        catch (Exception e)
        {
            throw new ServletException(e);
        }
    }
    
    @Override
    public void destroy()
    {
        this.filterConfig = null;
        UNCHECK_URL = null;
    }
    
    public int judge(HttpServletRequest request, UserInfoInterface userInfo, String url)
        throws Exception
    {
        if (!(IS_SESSION_CHECK))
        {
            return 1;
        }
        
        if ((StringUtils.isBlank(url)) || (url.equalsIgnoreCase("/")) || (url.equals(request.getServletPath())))
            return 1;
        Iterator iter;
        if (UNCHECK_URL != null)
        {
            Set key = UNCHECK_URL.keySet();
            for (iter = key.iterator(); iter.hasNext();)
            {
                String item = (String)iter.next();
                String itemValue = (String)UNCHECK_URL.get(item);
                
                if ((itemValue.equalsIgnoreCase("N")) || (StringUtils.isBlank(itemValue)))
                {
                    if (url.indexOf(item) != -1)
                    {
                        if (log.isDebugEnabled())
                            log.debug(new StringBuilder().append("Request Url not check,").append(url).toString());
                        return 1;
                    }
                    
                }
                else if ((userInfo == null) && (url.equals(item)))
                {
                    if (log.isDebugEnabled())
                        log.debug(new StringBuilder().append("Request Url not check,").append(url).toString());
                    return 1;
                }
            }
            
        }
        
        if ((userInfo == null) || (StringUtils.isBlank(userInfo.getCode())))
        {
            log.error("没有用户登录信息");
            return 0;
        }
        
        if ((userInfo != null) && (userInfo.get("IS_LOGOUTED") != null))
        {
            Boolean b = (Boolean)userInfo.get("IS_LOGOUTED");
            if ((b != null) && (b.booleanValue()))
            {
                request.getSession(false).invalidate();
                return 0;
            }
        }
        
        if (IS_URL_CHECK)
        {
            int rtn = 0;
            try
            {
                boolean isPass = entityControl(request, userInfo, url);
                if (isPass)
                    rtn = 1;
                else
                    rtn = -1;
            }
            catch (Throwable ex)
            {
                if (ex instanceof WebSecurityException)
                {
                    rtn = -1;
                    log.error(ex.getMessage(), ex);
                }
                else
                {
                    log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.web.filter.LoginFilter.check_error"),
                        ex);
                    rtn = -2;
                }
            }
            return rtn;
        }
        
        return 1;
    }
    
    private boolean entityControl(HttpServletRequest request, UserInfoInterface userInfo, String url)
        throws Exception
    {
        if ((userInfo == null) || (StringUtils.isBlank(userInfo.getCode())))
        {
            log.error("实体检查时,没有用户登录信息");
            return false;
        }
        String menuIdStr = request.getParameter("m");
        String curPage = request.getParameter("p");
        if (log.isDebugEnabled())
        {
            log.debug(new StringBuilder().append("Current Request UI:")
                .append(url)
                .append(",m=")
                .append(menuIdStr)
                .append(",p=")
                .append(curPage)
                .toString());
        }
        HashMap entityData = ((IEnhancedSecAccess)SecAccessFactory.getSecAccess()).getAEUIEntityByUrl(curPage);
        if (!(checkCarryInfo(entityData, menuIdStr, curPage, url)))
        {
            return false;
        }
        long menuId = Long.parseLong((StringUtils.isBlank(menuIdStr)) ? "-1" : menuIdStr);
        long uiEntityId = Long.parseLong(String.valueOf((entityData.containsKey("ID")) ? entityData.get("ID") : "-1"));
        long userId = userInfo.getID();
        long channelId = userInfo.getDomainId();
        
        HashMap userFunctionMap = (HashMap)request.getSession().getAttribute("SESSION_ACCESS_URL");
        if (userFunctionMap == null)
        {
            userFunctionMap =
                ((IEnhancedSecAccess)SecAccessFactory.getSecAccess()).getStaffAccessByStaffIdChannelId(userId,
                    channelId);
            request.getSession().setAttribute("SESSION_ACCESS_URL", userFunctionMap);
        }
        
        long hostId = 0L;
        if (!(URL_CHECK_LEVEL.equals("MENU")))
        {
            hostId = ((IEnhancedSecAccess)SecAccessFactory.getSecAccess()).getEntityHostByMenu(menuId, uiEntityId);
        }
        if (!(checkMenuPermission(userFunctionMap, menuIdStr, url)))
        {
            return false;
        }
        
        if (URL_CHECK_LEVEL.equals("MENU"))
        {
            return true;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("URL访问检查级别为红色");
        }
        
        if (hostId == 0L)
        {
            if (log.isDebugEnabled())
                log.debug(new StringBuilder().append("当前用户具有访问菜单[").append(menuId).append("]的权限").toString());
            if (URL_CHECK_LEVEL.equals("ALL"))
            {
                SecurityInvokeHelper.setCurUIEntityId(new StringBuilder().append(uiEntityId).append("").toString());
            }
            return true;
        }
        if (hostId < 0L)
        {
            log.error(new StringBuilder().append("没有从属关系，不能访问！{menuId:")
                .append(menuId)
                .append(", uiEntityId:")
                .append(uiEntityId)
                .append("}")
                .toString());
            return false;
        }
        
        if (!(checkBuiltinPagePermission(menuId, userId, hostId, uiEntityId, channelId)))
        {
            return false;
        }
        if (URL_CHECK_LEVEL.equals("ALL"))
        {
            SecurityInvokeHelper.setCurUIEntityId(new StringBuilder().append(uiEntityId).append("").toString());
        }
        return true;
    }
    
    private boolean checkCarryInfo(HashMap entityData, String menuIdStr, String curPage, String url)
        throws Exception
    {
        String[] urls = StringUtils.split(url, "?");
        if (urls[0].endsWith(".html"))
        {
            return true;
        }
        if ((url.indexOf("?service=page/") == -1) && (url.indexOf("?service=ajax&") == -1))
        {
            log.error(new StringBuilder().append("请求URL不正确:").append(url).toString());
            throw new Exception(AppframeLocaleFactory.getResource("i18n.appengine_resource",
                "com.ailk.appengine.web.filter.SecurityFilter.request_url_er"));
        }
        if ((StringUtils.isBlank(menuIdStr)) || (StringUtils.isBlank(curPage)))
        {
            log.error(new StringBuilder().append("请求中没有主实体信息m[")
                .append(menuIdStr)
                .append("],p[")
                .append(curPage)
                .append("]，不能访问！")
                .toString());
            return false;
        }
        if ((entityData.isEmpty()) || (!(entityData.containsKey("ID"))) || (!(entityData.containsKey("FULLNAME"))))
        {
            log.error(new StringBuilder().append("UI实体表中不存在该实体定义，不能访问！{uiEntityName:")
                .append(curPage)
                .append("}")
                .toString());
            return false;
        }
        String eUrl = String.valueOf(entityData.get("FULLNAME"));
        
        String requestPage = null;
        if (url.indexOf("?service=page/") != -1)
        {
            requestPage = StringUtils.substringBetween(url, "?service=page/", "&");
        }
        else
        {
            requestPage = StringUtils.substringBetween(url, "?service=ajax&page=", "&");
            if (!(curPage.equals(requestPage)))
            {
                log.error(new StringBuilder().append("访问资源地址中的Page与P不一致，不能访问！{url:")
                    .append(url)
                    .append(",P[")
                    .append(curPage)
                    .append("],Page:")
                    .append(requestPage)
                    .append("}")
                    .toString());
                return false;
            }
        }
        
        if ((StringUtils.isBlank(requestPage)) || (!(requestPage.equalsIgnoreCase(eUrl))))
        {
            log.error(new StringBuilder().append("访问资源地址与实体信息不一致，不能访问！{url:")
                .append(url)
                .append(",requestPage:")
                .append(requestPage)
                .append(", entityUrl:")
                .append(eUrl)
                .append("}")
                .toString());
            return false;
        }
        
        return true;
    }
    
    private boolean checkMenuPermission(HashMap userFunctionMap, String menuIdStr, String url)
        throws Exception
    {
        String requestUrl = url;
        if (url.indexOf("?service=page/") != -1)
        {
            if (url.startsWith("/"))
                requestUrl = StringUtils.substring(StringUtils.substringBefore(url, "&MENU_ID="), 1);
            else
                requestUrl = StringUtils.substringBefore(url, "&MENU_ID=");
        }
        else if (url.indexOf("?service=ajax&") != -1)
        {
            return true;
        }
        
        if (StringUtils.isNotBlank(menuIdStr))
        {
            Map secaccMap = (Map)CacheFactory.get(SecAllAccessCacheImpl.class, "SECACCESS_IDKEY");
            if ((secaccMap != null) && (secaccMap.containsKey(Long.valueOf(menuIdStr))))
            {
                String dbUrl = (String)secaccMap.get(Long.valueOf(menuIdStr));
                
                return (userFunctionMap.containsKey(dbUrl));
            }
            
            return false;
        }
        if (userFunctionMap.containsKey(requestUrl))
        {
            return true;
        }
        if (log.isDebugEnabled())
        {
            log.debug(new StringBuilder().append("url [").append(url).append("],该用户没有权限").toString());
        }
        return false;
    }
    
    private boolean checkBuiltinPagePermission(long menuId, long operatorId, long parentId, long currentId,
        long channelId)
        throws Exception
    {
        boolean hasSec =
            ((IEnhancedSecAccess)SecAccessFactory.getSecAccess()).checkSecEntityPermission(operatorId,
                parentId,
                currentId,
                channelId);
        if (log.isDebugEnabled())
        {
            if (hasSec)
                log.debug(new StringBuilder().append("当前用户访问能够访问该实体{menuid,hostId,enityid}[")
                    .append(menuId)
                    .append(",")
                    .append(parentId)
                    .append(",")
                    .append(currentId)
                    .append("]")
                    .toString());
            else
                log.debug(new StringBuilder().append("当前用户访问!!!不!!!能够访问该实体{menuid,enityid}[")
                    .append(menuId)
                    .append(",")
                    .append(parentId)
                    .append(",")
                    .append(currentId)
                    .append("]")
                    .toString());
        }
        return hasSec;
    }
}