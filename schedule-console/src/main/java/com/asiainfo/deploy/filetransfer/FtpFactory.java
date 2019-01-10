package com.asiainfo.deploy.filetransfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.asiainfo.deploy.common.constants.Category.FtpType;

/**
 * ftp操作者工厂
 * @author 孙德东(24204)
 */
public class FtpFactory {

	private FtpFactory() {
	}

	public static Operator getFileOperator(FtpType fType) {
		switch (fType) {
		case FTP:
			return new FtpOperator();
		case SFTP:
		default:
			return new SftpOperator();
		}
	}

	/**
	 * 测试并发读取同一个文件流
	 */
	public static void main(String[] args) {
		final String src = "H:/netty-5.0.0.Alpha1.tar.bz2";
		
		for (int i = 0; i < 16; i++) {
		    final String dest = src + i;
		    
			new Thread(){
				public void run()
				{
					try {
						InputStream in = new BufferedInputStream(new FileInputStream(new File(src)));
						OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
						byte[] buffer = new byte[1024];
						
						System.out.println(Thread.currentThread().toString() + "begin.");
						long begin = System.currentTimeMillis();
						
						int readed = -1;
						while((readed = in.read(buffer)) != -1) {
							out.write(buffer,0,readed);
						}
						
						System.out.println(Thread.currentThread().toString() + "cost " + (System.currentTimeMillis() - begin));
						
						in.close();
						out.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
