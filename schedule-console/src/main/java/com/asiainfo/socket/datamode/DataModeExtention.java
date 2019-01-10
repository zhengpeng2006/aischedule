package com.asiainfo.socket.datamode;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.exeframe.socket.handler.datamode.IBinTypeDataMode;
import com.asiainfo.socket.util.ConvertUtil;

/**
 * 扩展的QuickServer DataMode
 * 
 * @author 孙德东(24204)
 */
public class DataModeExtention implements IBinTypeDataMode {
	private transient static Log LOG = LogFactory.getLog(DataModeExtention.class);
	public static int maxPackageLength = 1024 * 20;

	public DataModeExtention() {
	}

	/**
	 * 
	 * @param objInputStream
	 *            InputStream
	 * @throws IOException
	 * @return byte[]
	 */
	public byte[] read(InputStream objInputStream) throws IOException {
		byte[] buffer = new byte[maxPackageLength];

		// 读取4字节的报文长度
		int offset = 0;
		while (offset < 4) {
			int size = objInputStream.read(buffer, offset, 4 - offset);
			if (size == -1) {
				throw new IOException("读数据失败，read返回-1");
			}
			offset += size;
		}
		// 计算报文长度
		int length = 0;
		try {
			length = ConvertUtil.byteArrayToInt(buffer, 4, ConvertUtil.ALIGNMENT_HIGHT);
		} catch (Exception e) {
			throw new IOException("转换报文长度异常！");
		}
		if (length > maxPackageLength) {
			throw new IOException("获取的报文长度超限！");
		}
		// 读取报文正文
		while (offset < 4 + length) {
			int size = objInputStream.read(buffer, offset, 4 + length - offset);
			if (size == -1) {
				throw new IOException("读数据失败，read返回-1");
			}
			offset += size;
		}
		byte[] data = new byte[4 + length];
		System.arraycopy(buffer, 0, data, 0, data.length);
		LOG.info("接收到完整报文，报文内容：[" + new String(data) + "]");

		return data;
	}
}
