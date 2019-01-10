package com.asiainfo.socket.util;

import java.io.InputStream;

/**
 * IO流读取辅助类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class IOStreamUtil
{

    private static int maxContentLength = 10;

    /**
     * 读取输入流
     * read  inputStream
     * @param ins
     * @return
     * @throws Exception
     */
    public static byte[] read(InputStream ins) throws Exception
    {
        //获取报文长度
        byte[] lenBytes = new byte[maxContentLength];
        ins.read(lenBytes);

        int contentLength = Integer.parseInt(new String(lenBytes,"UTF-8"));
        byte[] totalBytes = new byte[contentLength];

        //消息体长度
        byte[] contentBytes = new byte[contentLength];

        int i = 0;
        while(i < contentLength) {
            int tmpCnt = ins.read(contentBytes);
            System.arraycopy(contentBytes, 0, totalBytes, i, tmpCnt);
            i += tmpCnt;
        }
        return totalBytes;
    }

}
