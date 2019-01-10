package com.asiainfo.schedule.view;

import com.ailk.appengine.web.view.AppPage;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import java.util.Map;

/**
 * Created by Genesis on 16/6/17.
 */
public class ProcessorBackupCfg extends AppPage {

    private static final Logger LOGGER = Logger.getLogger(ProcessorBackupCfg.class);

    /** 页面操作类型：修改 */
    private static final String OPERATION_UPDATE = "update";

    /** 页面操作类型：新增 */
    private static final String OPERATION_ADD = "add";

    /** 页面静态数据map */
    private static Map<String, Map<String, String>> staticDataMap;


    /**
     * 设置备用进程
     * @param request
     */
    public void setBackupProcessor(IRequestCycle request){

    }

    /**
     * 查询应用调度实例列表
     * @param request
     */
    public void queryProcessorList(IRequestCycle request){

    }

}
