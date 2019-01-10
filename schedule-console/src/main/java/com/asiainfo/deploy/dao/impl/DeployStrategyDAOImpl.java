package com.asiainfo.deploy.dao.impl;
import java.sql.Timestamp;
import java.util.Map;

import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyEngine;
import com.asiainfo.deploy.common.bo.BODeployStrategyHistoryBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyHistoryEngine;
import com.asiainfo.deploy.dao.interfaces.IDeployStrategyDAO;

/**
 * 部署策略数据库操作实现类
 * 
 * @author 孙德东(24204)
 */
public class DeployStrategyDAOImpl implements IDeployStrategyDAO {

	@Override
	public BODeployStrategyBean[] getAllDeployStrategy() throws Exception {
		return BODeployStrategyEngine.getBeans(null, null);
	}

	@Override
	public BODeployStrategyBean getStrategyById(long strategyId) throws Exception {
		return BODeployStrategyEngine.getBean(strategyId);
	}

	@Override
	public BODeployStrategyBean[] getStrategyByCondition(Map<String, Object> condition) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (condition != null && !condition.isEmpty()) {
			for (Map.Entry<String, Object> entry : condition.entrySet()) {
				if (sb.length() > 0) {
					sb.append("AND ");
				}
				if (BODeployStrategyBean.S_DeployStrategyName.equals(entry.getKey())) {
					sb.append(entry.getKey()).append(" like :").append(entry.getKey()).append("");
				} else {
					sb.append(entry.getKey()).append(" = :").append(entry.getKey());
				}
			}
		}
		return BODeployStrategyEngine.getBeans(sb.toString(), condition);
	}

	@Override
	public void deleteStrategy(long strategyId) throws Exception {
		BODeployStrategyBean bean = getStrategyById(strategyId);
		// 删除前先保存历史表
		saveHis(bean);
		bean.delete();
		BODeployStrategyEngine.save(bean);
	}

	@Override
	public void saveStrategy(BODeployStrategyBean strategy) throws Exception {
		if (strategy.isNew()) {
			strategy.setDeployStrategyId(BODeployStrategyEngine.getNewId().longValue());
			strategy.setCreateTime(new Timestamp(System.currentTimeMillis()));
		} else if (strategy.isModified()) {
			strategy.setModifyTime(new Timestamp(System.currentTimeMillis()));
			// 修改前先保存历史表
			saveHis(strategy);
		}
		BODeployStrategyEngine.save(strategy);
	}

	/**
	 * 将元数据转移到历史表
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private void saveHis(BODeployStrategyBean bean) throws Exception {
		BODeployStrategyHistoryBean his = bean2his(bean);
		his.setDeployStrategyHistoryId(BODeployStrategyHistoryEngine.getNewId().longValue());
		his.setHistoryCreateTime(new Timestamp(System.currentTimeMillis()));
		BODeployStrategyHistoryEngine.save(his);
	}

	private static BODeployStrategyHistoryBean bean2his(BODeployStrategyBean bean) throws Exception {
		BODeployStrategyHistoryBean his = new BODeployStrategyHistoryBean();
		his.setClientBinPath(bean.getClientBinPath());
		his.setClientSbinPath(bean.getClientSbinPath());
		his.setClientHomePath(bean.getClientHomePath());
		his.setClientLogPath(bean.getClientLogPath());
		his.setCreateTime(bean.getCreateTime());
		his.setDeployStrategyId(bean.getDeployStrategyId());
		his.setDeployStrategyName(bean.getDeployStrategyName());
		his.setFtpHostIp(bean.getFtpHostIp());
		his.setFtpHostPort(bean.getFtpHostPort());
		his.setFtpPackagePath(bean.getFtpPackagePath());
		his.setFtpProtocol(bean.getFtpProtocol());
		his.setHistoryPackageNum(bean.getHistoryPackageNum());
		his.setModifyTime(bean.getModifyTime());
		// 设置操作员编号
		his.setOperatorId(bean.getOperatorId());
		his.setFtpPassword(bean.getFtpPassword());
		his.setRemarks(bean.getRemarks());
		his.setServerPackagePath(bean.getServerPackagePath());
		his.setTemplateId(bean.getTemplateId());
		his.setFtpUserName(bean.getFtpUserName());
		his.setInstallRule(bean.getInstallRule());
		his.setStopTemplateId(bean.getStopTemplateId());
		return his;
	}

}
