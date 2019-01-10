package com.asiainfo.monitor.service.impl;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.external.AppParamUtils;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonNodeUserSV;
import com.asiainfo.monitor.service.interfaces.IHostBackSV;

public class HostBackSVImpl implements IHostBackSV
{
    /**
     * 根据主机标识备份主机
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public boolean hostBack(String hostId) throws Exception
    {
        //根据主机标识查询备机标识
        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV) ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        List<String> list = msrSv.getSlaveHostId(hostId);

        String slaveHostId = null;
        if(list.size() > 0) {
            slaveHostId = list.get(0);
        }
        else {
            return false;
        }
        //根据主机标识查询该主机下的所有节点
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] result = nodeSv.qryNodeInfo(hostId);

        //根据备机标识查询该主机下的所有节点
        IBOAIMonNodeValue[] slaveResult = nodeSv.qryNodeInfo(slaveHostId);

        //当主备机下的节点都不为空时，依次比较各节点的部署策略,一个节点编码对应一个部署策略
        if(result.length > 0 && slaveResult.length > 0) {
            for(int i = 0; i < result.length; i++) {
                for(int j = 0; j < slaveResult.length; j++) {
                    //获取主机部署策略
                    BODeployStrategyBean hostBean = DeployStrategyUtils.getDeployStrategyByNodeId(result[i].getNodeId());
                    //获取备机部署策略
                    BODeployStrategyBean slaveHostBean = DeployStrategyUtils.getDeployStrategyByNodeId(slaveResult[j].getNodeId());
                    if(null != hostBean && null != slaveHostBean && (hostBean.getDeployStrategyId() == (slaveHostBean.getDeployStrategyId()))) {
                        //在节点部署策略相同的情况下，若节点编码相同，则将应用添加到该节点下
                        if(result[i].getNodeCode().equals(slaveResult[j].getNodeCode())) {
                            //节点标识唯一
                            String nodeId = Long.toString(result[i].getNodeId());
                            String slaveNodeId = Long.toString(slaveResult[j].getNodeId());
                            //根据节点标识查询应用信息
                            IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                            IBOAIMonServerValue[] serverResult = serverSv.qryServerInfo(nodeId);

                            for(int m = 0; m < serverResult.length; m++) {
                                IBOAIMonServerValue sValue = serverResult[m];
                                Long serverIdOld = sValue.getServerId();
                                String serverCodeNew = serverResult[m].getServerCode() + "_bak";
                                sValue.setStsToNew();
                                sValue.setServerCode(serverCodeNew);
                                sValue.setNodeId(Long.parseLong(slaveNodeId));

                                boolean code = serverSv.isExistServerByCode(sValue.getServerCode());
                                if(code) {
                                    return false;
                                }
                                long serverIdNew = serverSv.saveOrUpdate(sValue);
                                //前面是老的，后面是新的
                                AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                            }
                            break;
                        }
                    }
                    if(j == slaveResult.length - 1) {
                        long nodeIdOld = result[i].getNodeId();
                        result[i].setStsToNew();
                        result[i].setHostId(Long.parseLong(slaveHostId));
                        long nodeId = nodeSv.saveOrUpdate(result[i]);
                        BODeployStrategyBean bean = DeployStrategyUtils.getDeployStrategyByNodeId(nodeIdOld);
                        long deployStrategyId = bean.getDeployStrategyId();
                        DeployStrategyUtils.addNodeDeployStrategy(nodeId, deployStrategyId);
                        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                        IBOAIMonServerValue[] serverResult = serverSv.qryServerInfo(Long.toString(nodeIdOld));

                        //查询到节点用户，并新增用户
                        IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV) ServiceFactory.getService(IAIMonNodeUserSV.class);
                        IBOAIMonNodeUserValue[] values = nuSv.qryNodeUserByNodeId(Long.toString(nodeIdOld));

                        if(values.length > 0) {
                            IBOAIMonNodeUserValue value = values[0];
                            value.setStsToNew();
                            value.setNodeUserId(0);
                            value.setNodeId(nodeId);
                            nuSv.save(value);
                        }
                        for(int m = 0; m < serverResult.length; m++) {
                            IBOAIMonServerValue sValue = serverResult[m];
                            Long serverIdOld = sValue.getServerId();
                            String serverCodeNew = serverResult[m].getServerCode() + "_bak";
                            sValue.setStsToNew();
                            sValue.setServerCode(serverCodeNew);
                            sValue.setNodeId(nodeId);
                            boolean code = serverSv.isExistServerByCode(sValue.getServerCode());
                            if(code) {
                                return false;
                            }
                            long serverIdNew = serverSv.saveOrUpdate(sValue);
                            //前面是老的，后面是新的
                            AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                        }
                    }
                }
            }
            return true;
        }
        //当主机节点不为空，而备机节点为空的情况下，复制节点，并复制应用
        if(result.length > 0 && slaveResult.length <= 0) {
            for(int i = 0; i < result.length; i++) {
                    //获取主机部署策略
                long nodeIdOld = result[i].getNodeId();
                result[i].setStsToNew();
                result[i].setHostId(Long.parseLong(slaveHostId));
                long nodeId = nodeSv.saveOrUpdate(result[i]);
                BODeployStrategyBean bean = DeployStrategyUtils.getDeployStrategyByNodeId(nodeIdOld);
                long deployStrategyId = bean.getDeployStrategyId();
                DeployStrategyUtils.addNodeDeployStrategy(nodeId, deployStrategyId);

                //查询到节点用户，并新增用户
                IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV) ServiceFactory.getService(IAIMonNodeUserSV.class);
                IBOAIMonNodeUserValue[] values = nuSv.qryNodeUserByNodeId(Long.toString(nodeIdOld));

                if(values.length > 0) {
                    IBOAIMonNodeUserValue value = values[0];
                    value.setStsToNew();
                    value.setNodeUserId(0);
                    value.setNodeId(nodeId);
                    nuSv.save(value);
                }

                IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
                IBOAIMonServerValue[] serverResult = serverSv.qryServerInfo(Long.toString(nodeIdOld));

                for(int m = 0; m < serverResult.length; m++) {
                    IBOAIMonServerValue sValue = serverResult[m];
                    Long serverIdOld = sValue.getServerId();
                    String serverCodeNew = serverResult[m].getServerCode() + "_bak";
                    sValue.setStsToNew();
                    sValue.setServerCode(serverCodeNew);
                    sValue.setNodeId(nodeId);
                    boolean code = serverSv.isExistServerByCode(sValue.getServerCode());
                    if(code) {
                        return false;
                    }
                    long serverIdNew = serverSv.saveOrUpdate(sValue);
                    //前面是老的，后面是新的
                    AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception
    {
        IHostBackSV hostBackSv = (IHostBackSV) ServiceFactory.getService(IHostBackSV.class);
        hostBackSv.hostServerBack("MasterServer");
    }
    /**
     * 根据应用编码备份应用
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public boolean hostServerBack(String serverCode) throws Exception
    {
        //根据应用编码查询节点
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        IBOAIMonNodeValue nodeValue = commonSv.qryNodeInfoByServerCode(serverCode);
        if(null == nodeValue) {
            return false;
        }
        long nodeIdOld = nodeValue.getNodeId();

        //根据应用编码查询主机标识，再通过主机标识查出备机标识
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        String hostId = Long.toString(nodeValue.getHostId());

        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV) ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        List<String> list = msrSv.getSlaveHostId(hostId);

        String slaveHostId = null;
        if(list.size() > 0) {
            slaveHostId = list.get(0);
        }
        else {
            return false;
        }

        //根据节点标识查询节点策略
        BODeployStrategyBean hostBean = DeployStrategyUtils.getDeployStrategyByNodeId(nodeValue.getNodeId());

        //获取备机所有节点
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] slaveResult = null;

        slaveResult = nodeSv.qryNodeInfo(slaveHostId);

        //通过应用编码查询应用信息
        IBOAIMonServerValue serverResult = serverSv.qryServerByServerCode(serverCode);
        //遍历备机所有节点，主备机节点策略比对
        if(null != serverResult) {
            if(slaveResult.length == 0) {
                //复制节点，然后复制应用
                nodeValue.setStsToNew();
                nodeValue.setHostId(Long.parseLong(slaveHostId));
                long nodeId = nodeSv.saveOrUpdate(nodeValue);
                BODeployStrategyBean bean = DeployStrategyUtils.getDeployStrategyByNodeId(nodeIdOld);
                long deployStrategyId = bean.getDeployStrategyId();
                DeployStrategyUtils.addNodeDeployStrategy(nodeId, deployStrategyId);

                //查询到节点用户，并新增用户
                IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV) ServiceFactory.getService(IAIMonNodeUserSV.class);
                IBOAIMonNodeUserValue[] values = nuSv.qryNodeUserByNodeId(Long.toString(nodeIdOld));

                if(values.length > 0) {
                    IBOAIMonNodeUserValue value = values[0];
                    value.setStsToNew();
                    value.setNodeUserId(0);
                    value.setNodeId(nodeId);
                    nuSv.save(value);
                }

                Long serverIdOld = serverResult.getServerId();
                String serverCodeNew = serverResult.getServerCode() + "_bak";
                serverResult.setStsToNew();
                serverResult.setServerCode(serverCodeNew);
                serverResult.setNodeId(nodeId);

                long serverIdNew = serverSv.saveOrUpdate(serverResult);
                //前面应用标识是老的，后面是新的
                AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
            }
            for(int i = 0; i < slaveResult.length; i++) {
                long slaveNodeId = slaveResult[i].getNodeId();
                BODeployStrategyBean hostSlaveBean = DeployStrategyUtils.getDeployStrategyByNodeId(slaveNodeId);
                if(null != hostBean && null != hostSlaveBean && (hostBean.getDeployStrategyId() == (hostSlaveBean.getDeployStrategyId()))) {
                    //当节点编码相同时，备份
                    if(nodeValue.getNodeCode().equals(slaveResult[i].getNodeCode())) {
                        Long serverIdOld = serverResult.getServerId();
                        String serverCodeNew = serverResult.getServerCode() + "_bak";
                        serverResult.setStsToNew();
                        serverResult.setServerCode(serverCodeNew);
                        serverResult.setNodeId(slaveNodeId);
                        boolean code = serverSv.isExistServerByCode(serverResult.getServerCode());
                        if(code) {
                            return false;
                        }
                        long serverIdNew = serverSv.saveOrUpdate(serverResult);
                        //前面应用标识是老的，后面是新的
                        AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                        break;
                    }
                }
                if(i == slaveResult.length - 1) {
                    nodeValue.setStsToNew();
                    nodeValue.setHostId(Long.parseLong(slaveHostId));
                    long nodeId = nodeSv.saveOrUpdate(nodeValue);
                    BODeployStrategyBean bean = DeployStrategyUtils.getDeployStrategyByNodeId(nodeIdOld);
                    long deployStrategyId = bean.getDeployStrategyId();
                    DeployStrategyUtils.addNodeDeployStrategy(nodeId, deployStrategyId);

                    //查询到节点用户，并新增用户
                    IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV) ServiceFactory.getService(IAIMonNodeUserSV.class);
                    IBOAIMonNodeUserValue[] values = nuSv.qryNodeUserByNodeId(Long.toString(nodeIdOld));

                    if(values.length > 0) {
                        IBOAIMonNodeUserValue value = values[0];
                        value.setNodeUserId(0);
                        value.setStsToNew();
                        value.setNodeId(nodeId);
                        nuSv.save(value);
                    }

                    Long serverIdOld = serverResult.getServerId();
                    String serverCodeNew = serverResult.getServerCode() + "_bak";
                    serverResult.setStsToNew();
                    serverResult.setServerCode(serverCodeNew);
                    serverResult.setNodeId(nodeId);
                    boolean code = serverSv.isExistServerByCode(serverResult.getServerCode());
                    if(code) {
                        return false;
                    }
                    long serverIdNew = serverSv.saveOrUpdate(serverResult);
                    //前面应用标识是老的，后面是新的
                    AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                }
            }
            return true;
        }
        return false;
    }
}
