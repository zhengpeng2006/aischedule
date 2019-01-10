package com.asiainfo.deploy.filetransfer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.asiainfo.deploy.exception.ExecuteResult;

/**
 * 文件上传下载接口
 * 
 * @author 孙德东(24204)
 */
public interface Operator {
	/**
	 * 上传文件
	 * 
	 * @param node
	 *            节点信息
	 * @param src
	 * @param dst
	 */
	ExecuteResult put(Map<String, String> node, String src, String dst);
	ExecuteResult put(Map<String, String> node, InputStream src, String dst);
	/**
	 * 下载文件
	 * 
	 * @param node
	 * @param src
	 * @param dst
	 * @return
	 */
	ExecuteResult get(Map<String, String> node, String src, String dst);

	/**
	 * 批量上传文件
	 * 
	 * @param node
	 *            节点信息
	 * @param src
	 * @param dst
	 */
	ExecuteResult batchPut(Map<String, String> node, List<InputStream> src, List<String> dst);

	/**
	 * 批量下载文件
	 * @param node
	 * @param src
	 * @param dst
	 * @return
	 */
	ExecuteResult batchGet(Map<String, String> node, List<String> src, List<String> dst);
}
