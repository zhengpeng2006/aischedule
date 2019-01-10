package com.asiainfo.deploy.version;

import java.io.File;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.common.bo.BODeployVersionBean;
import com.asiainfo.deploy.common.constants.Category.VersionState;
import com.asiainfo.deploy.dao.interfaces.IVersionDAO;

/**
 * 版本管理
 * 
 * @author 孙德东(24204)
 */
public class VersionManager
{
    private static IVersionDAO versionDao = (IVersionDAO)ServiceFactory.getService(IVersionDAO.class);
    
    private VersionManager()
    {
    }
    
    /**
     * 全量安装或更新时的版本处理
     * 
     * @throws Exception
     */
    public static void installVersionHandler(long versionId, String path, long strategyId, int maxNum)
        throws Exception
    {
        // 如果超出要保留的版本数目，将最久的版本设置为无效版本
        changeUnnecessaryHisVersionState(strategyId, maxNum);
        // 当前版本设置为有效的历史版本
        changeCurrentVersion2ValidHistory(strategyId);
        // 新版本设置为当前版本
        change2CurrentVersion(versionId, path);
    }
    
    /**
     * 回滚的版本处理
     * 
     * @throws Exception
     */
    public static void rollbackVersionHandler(long versionId, long strategyId)
        throws Exception
    {
        // 将回滚之前的版本设置为无效版本
        BODeployVersionBean[] validHistories = versionDao.getValidHistoryVersionsAsc(strategyId);
        for (BODeployVersionBean his : validHistories)
        {
            if (his.getVersionId() > versionId)
            {
                toInvalidHistoryVersion(his);
                versionDao.saveVersion(his);
            }
        }
        
        // 将回滚前的当前版本置为失效 2016.9.29 处理：回滚之后在发布存在多个当前版本问题
        BODeployVersionBean cur = versionDao.currentVersion(strategyId);
        if (cur != null)
        {
            cur.setState(VersionState.INVALID_HISTORY.value());
            versionDao.saveVersion(cur);
        }
        
        // 回滚版本设置为当前版本
        changeVersionState(versionId, VersionState.CURRENT);
    }
    
    /**
     * 主键获取版本
     * 
     * @param versionId
     * @return
     * @throws Exception
     */
    public static BODeployVersionBean getVersionById(long versionId)
        throws Exception
    {
        return versionDao.getVersionById(versionId);
    }
    
    /**
     * 将版本设置为当前版本
     * 
     * @param versionId
     * @param path
     * @throws Exception
     */
    private static void change2CurrentVersion(long versionId, String path)
        throws Exception
    {
        BODeployVersionBean newVersion = versionDao.getVersionById(versionId);
        newVersion.setState(VersionState.CURRENT.value());
        newVersion.setPackagePath(path);
        
        versionDao.saveVersion(newVersion);
    }
    
    /**
     * 当前版本
     * 
     * @throws Exception
     */
    private static BODeployVersionBean currentVersion(long strategyId)
        throws Exception
    {
        return versionDao.currentVersion(strategyId);
    }
    
    /**
     * 将当前版本修改为历史版本
     * 
     * @param versionId
     * @param state
     * @param path
     * @throws Exception
     */
    private static void changeCurrentVersion2ValidHistory(long strategyId)
        throws Exception
    {
        BODeployVersionBean cur = currentVersion(strategyId);
        // 第一个版本发布的时候，是没有当前版本的。
        if (cur == null)
            return;
        cur.setState(VersionState.VALID_HISTORY.value());
        versionDao.saveVersion(cur);
    }
    
    /**
     * 改变版本的状态
     * 
     * @param versionId
     * @param state
     * @throws Exception
     */
    private static void changeVersionState(long versionId, VersionState state)
        throws Exception
    {
        BODeployVersionBean version = versionDao.getVersionById(versionId);
        if (version == null)
            return;
        
        version.setState(state.value());
        versionDao.saveVersion(version);
    }
    
    /**
     * 移除多余的历史版本
     * 
     * @param strategyId
     * @param maxNum
     * @throws Exception
     */
    private static void changeUnnecessaryHisVersionState(long strategyId, int maxNum)
        throws Exception
    {
        BODeployVersionBean[] validHistories = versionDao.getValidHistoryVersionsAsc(strategyId);
        // 没有可用的历史版本
        if (validHistories == null || validHistories.length <= 0)
            return;
        if (validHistories.length >= maxNum)
        {
            // 版本设置为无效
            toInvalidHistoryVersion(validHistories[0]);
            versionDao.saveVersion(validHistories[0]);
        }
    }
    
    /**
     * 版本设置为无效
     * 
     * @param hisVersion
     */
    private static void toInvalidHistoryVersion(BODeployVersionBean hisVersion)
    {
        // 移除版本号最小的那个版本（状态设置为无效）
        hisVersion.setState(VersionState.INVALID_HISTORY.value());
        // 删除保留的版本文件
        String pkgPath = hisVersion.getPackagePath();
        if (StringUtils.isNotEmpty(pkgPath))
        {
            File f = new File(pkgPath);
            f.delete();
        }
        hisVersion.setPackagePath(StringUtils.EMPTY);
    }
}
