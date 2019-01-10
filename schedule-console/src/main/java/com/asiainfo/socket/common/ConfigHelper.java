package com.asiainfo.socket.common;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.digester.Digester;

import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.asiainfo.socket.config.Handle;
import com.asiainfo.socket.config.MsgHandle;

/**
 * socket 消息通信配置解析帮助类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class ConfigHelper
{
    private static MsgHandle config = null;
    //是否需要缓存
    //    private static Map<String, String> hanleMap;

    static {
        try {
            config = ConfigHelper.createConfig();
        }
        catch(Exception ex) {
        }
    }

    private static MsgHandle createConfig() throws Exception
    {
        String defaultsFileName = "socket/MsgHandleConfig.xml";
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultsFileName);
        if(input == null) {
            //"无法找到" + defaultsFileName + "配置文件"
            String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.complex.xml.no_config_file", new String[]{defaultsFileName});
            throw new Exception(msg);
        }

        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("msghandle/handles", MsgHandle.class);
        // 指明匹配模式和要创建的类   
        digester.addObjectCreate("msghandle/handles/handle", Handle.class);
        // 设置对象属性,与xml文件对应,不设置则是默认  
        digester.addBeanPropertySetter("msghandle/handles/handle/msgtype", "msgtype");
        digester.addBeanPropertySetter("msghandle/handles/handle/msghandler", "msghandler");

        // 当移动到下一个标签中时的动作  
        digester.addSetNext("msghandle/handles/handle", "addHandle");

        MsgHandle msgHandle = null;
        msgHandle = (MsgHandle) digester.parse(input);

        return msgHandle;
    }

    /**
     * 根据类型，获取对应的处理类
     * @param type
     * @return
     * @throws Exception
     */
    public static String getHandle(String type) throws Exception
    {
        String handleCls = "";
        List<Handle> handleList = config.getHandleList();
        for(Handle handle : handleList) {
            if(type.equals(handle.getMsgtype())) {
                handleCls = handle.getMsghandler();
                break;
            }
        }

        return handleCls;
    }

}
