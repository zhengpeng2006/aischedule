package com.asiainfo.monitor.busi.common.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: AI(NanJing)</p>
 *
 * @author Yang Hua
 * @version 1.0
 */
public class MemcachedStatUtil {
  public static final byte[] BYTE_CRLF = new byte[] {13, 10};
  public static final String SERVER_STATUS_END = "END";

  private MemcachedStatUtil() {
  }

  /**
   *
   * @param host String
   * @param port int
   * @return HashMap
   * @throws Exception
   */
  public static HashMap getStat(String host,int port) throws Exception {


    HashMap rtn = new HashMap();

    Socket socket = null;
    try {
      socket = new Socket(host,port);
      BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
      BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
      out.write("stats".getBytes());
      out.write(BYTE_CRLF);

      out.flush();

      rtn.put("host",socket.getInetAddress().getHostAddress()+":"+socket.getPort());
      String cmd = readLine(in);
      while(!SERVER_STATUS_END.equals(cmd)){
	String[] yh = StringUtils.split(cmd," ");
	rtn.put(yh[1],yh[2]);
	cmd = readLine(in);
      }
    }
    catch (Exception ex) {
      throw ex;
    }
    finally {
      if (socket != null) {
	socket.close();
	socket = null;//help gc
      }
    }

    return rtn;
  }

  /**
   * 读一行数据
   * @param in InputStream
   * @throws IOException
   * @return String
   */
  private static String readLine(InputStream in) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    boolean eol = false;
    byte[] b = new byte[1];
    while (in.read(b, 0, 1) != -1) {
      if (b[0] == 13) {
	eol = true;
      }
      else if (eol && b[0] == 10) {
	break;
      }
      else {
	eol = false;
      }

      bos.write(b, 0, 1);
    }

    if (bos.size() == 0) {
      return null;
    }
    return bos.toString().trim();
  }

  public static void main(String[] args) throws Exception {
    System.out.println(MemcachedStatUtil.getStat("10.96.19.157",37777));
  }
}
