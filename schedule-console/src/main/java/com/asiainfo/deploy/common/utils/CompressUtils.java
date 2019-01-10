package com.asiainfo.deploy.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.exception.ErrorCode;

/**
 * 文件压缩相关
 * @author 孙德东(24204)
 */
public class CompressUtils {

	private static transient Log LOG = LogFactory.getLog(CompressUtils.class);

	/**
	 * 向安装包中添加版本信息
	 * @param pkgPath
	 * @param versionId
	 * @return
	 * @throws Exception 
	 */
	public static void appendVersionToPkg(List<String> pkgPath, long versionId, String dst) throws Exception {
		if (pkgPath == null || pkgPath.isEmpty()) {
			LOG.error("no file to compress when install.");
			throw new Exception("" + ErrorCode.Publish.APPEND_VERSION_TO_PKG_ERROR);
		}
		
		FileOutputStream out = null;
		CompressorOutputStream gzippedOut = null;
		TarArchiveOutputStream tarOut = null;

		try {
			out = new FileOutputStream(dst);
			gzippedOut = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, out);
			
			tarOut = new TarArchiveOutputStream(new BufferedOutputStream(gzippedOut));
			
			// 把安装包添加到压缩文件中
			for (String s : pkgPath) {
				File pkgFile = new File(s);
				InputStream in = new FileInputStream(s);
				tarOut.putArchiveEntry(new TarArchiveEntry(pkgFile,pkgFile.getName()));
				IOUtils.copy(in, tarOut);
				tarOut.closeArchiveEntry();
				// 关闭流，否则文件一直被占用
				in.close();
			}

			// 把版本信息添加到压缩文件中
			TarArchiveEntry versionEntry = new TarArchiveEntry(DeployConstants.Common.VERSION_FILE_NAME);
			byte[] buffer = String.valueOf(versionId).getBytes("UTF-8");
			versionEntry.setSize(buffer.length);
			tarOut.putArchiveEntry(versionEntry);
			tarOut.write(buffer);
			tarOut.closeArchiveEntry();

			//刷新流
			tarOut.flush();
			gzippedOut.flush();
			out.flush();
		} catch (Exception e) {
			LOG.error("append version to install package error.", e);
			throw new Exception("" + ErrorCode.Publish.APPEND_VERSION_TO_PKG_ERROR);
		} finally {
			// 关闭流
			try {
				if (tarOut != null)
					tarOut.close();
				if (gzippedOut != null)
					gzippedOut.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				LOG.error("close stream error:" + e.getLocalizedMessage());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("H:/netty-5.0.0.Alpha1.tar.bz2");
		CompressUtils.appendVersionToPkg(list, 101L, "H:/2.tar.gz");
	}

}
