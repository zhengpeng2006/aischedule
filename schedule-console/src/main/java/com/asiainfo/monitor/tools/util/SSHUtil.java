package com.asiainfo.monitor.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.common.SSHBean;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

/**
 * SSH脚本执行工具类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public final class SSHUtil
{
    private SSHUtil()
    {
    }

    public static String ssh4Shell(String ip, int port, String userName, String password, String fileName, String inParamStr, String shell)
            throws Exception
    {
        SSHBean sshBean = new SSHBean();
        sshBean.setIp(ip);
        sshBean.setSshPort(port);
        sshBean.setUsername(userName);
        sshBean.setPassword(password);
        sshBean.setParameters(inParamStr);
        sshBean.setShell(shell);
        sshBean.setTmpFileName(fileName);

        return SSHUtil.ssh4Shell(sshBean);
    }

    /**
     * 
     */
    public static String ssh4Shell(SSHBean sshBean) throws Exception
    {
        String ip = sshBean.getIp();
        int sshPort = sshBean.getSshPort();
        String username = sshBean.getUsername();
        String password = sshBean.getPassword();
        String tmpFileName = sshBean.getTmpFileName();
        String shell = sshBean.getShell();
        String parameters = sshBean.getParameters();
        int timeout = sshBean.getTimeout();

        String rtn = null;
        Connection conn = null;
        Session sess = null;
        String tmpFilePath = "$HOME/tmp/";
        Session tmpSess = null;
        try {
            conn = getConnection(ip, sshPort, username, password, timeout);

            // 上传文件
            String fileName = tmpFileName + ".sh";
            //SSHUtil.checkTmpDir(ip, sshPort, username, password, timeout);

            tmpSess = conn.openSession();
            tmpSess.execCommand("  mkdir $HOME/tmp");

            upload(conn, ip, sshPort, username, password, shell.getBytes(), tmpFilePath, fileName);
            sess = conn.openSession();

            if(StringUtils.isBlank(parameters)) {
                sess.execCommand(" chmod +x " + tmpFilePath + fileName + " && " + tmpFilePath + fileName + "  && rm -rf " + tmpFilePath + fileName);
            }
            else {
                sess.execCommand(" chmod +x " + tmpFilePath + fileName + " && " + tmpFilePath + fileName + " " + parameters + "  && rm -rf "
                        + tmpFilePath + fileName);
            }
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            StringBuilder sb = new StringBuilder("");
            while(true) {
                String line = br.readLine();
                if(line == null || line.indexOf("扫描任务执行") >= 0) {
                    break;
                }
                else {
                    sb.append(line).append(" ");
                }
            }
            rtn = sb.toString();

        }
        catch(Exception ex) {
            throw ex;
        }
        finally {
            if(sess != null) {
                sess.close();
            }
            if(tmpSess != null) {
                tmpSess.close();
            }

            if(conn != null) {
                conn.close();
            }
            sess = null;
            conn = null;
        }

        return rtn;
    }

    /**
     * @param ip
     * @param sshPort
     * @param username
     * @param password
     * @param timeout
     * @return
     * @throws Exception
     */
    private static boolean checkTmpDir(String ip, int sshPort, String username, String password, int timeout) throws Exception
    {
        Connection connection = null;
        Session tmpSession = null;

        try {
            connection = SSHUtil.getConnection(ip, sshPort, username, password, timeout);

            tmpSession = connection.openSession();

            //如果tmp目录不存在，则创建tmp目录
            tmpSession.execCommand(" mkdir $HOME/tmp ");
        }

        catch(Exception ex) {

        }
        finally {
            if(tmpSession != null) {
                tmpSession.close();
            }
            if(connection != null) {
                connection.close();
            }
            tmpSession = null;
            connection = null;
        }

        tmpSession = null;
        connection = null;
        return true;
    }

    public static void main(String[] args) throws Exception
    {
        SSHUtil.checkTmpDir("20.26.12.158", 22, "monexe", "monexe-123", 1000);
        System.out.println("1234124");
    }

    private static Connection getConnection(String ip, int sshPort, String username, String password, int timeout) throws IOException
    {
        Connection conn;
        if(sshPort <= 0) {
            conn = new Connection(ip);
        }
        else {
            conn = new Connection(ip, sshPort);
        }

        conn.connect(null, timeout, timeout);

        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if(isAuthenticated == false) {
            // "认证失败"
            throw new IOException(AIMonLocaleFactory.getResource("MOS0000271") + "ip:" + ip + ",username:" + username);
        }
        return conn;
    }

    /**
     *
     * @param ip String
     * @param sshPort int
     * @param username String
     * @param password String
     * @param shellName String[]
     * @param parameters String[]
     * @param shell String[]
     * @return String[]
     * @throws <any>
     */
    public static String[] ssh4Shell(String ip, int sshPort, String username, String password, String[] shellName, String[] shell) throws Exception
    {
        return ssh4Shell(ip, sshPort, username, password, shellName, null, shell);
    }

    /**
     *
     * @param ip String
     * @param sshPort int
     * @param username String
     * @param password String
     * @param shellName String[]
     * @param parameters String[]
     * @param shell String[]
     * @return String[]
     * @throws Exception
     */
    public static String[] ssh4Shell(String ip, int sshPort, String username, String password, String[] shellName, String[] parameters, String[] shell)
            throws Exception
    {
        String[] rtn = new String[shellName.length];
        Connection conn = null;

        try {
            if(sshPort <= 0)
                conn = new Connection(ip);
            else
                conn = new Connection(ip, sshPort);

            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);

            if(isAuthenticated == false) {
                // "认证失败"
                throw new IOException(AIMonLocaleFactory.getResource("MOS0000271") + ",ip:" + ip + ",username:" + username);
            }

            //上传文件
            String[] fileName = new String[shellName.length];

            byte[][] b = new byte[shellName.length][];
            for(int i = 0; i < shellName.length; i++) {
                fileName[i] = shellName[i] + ".sh";
                b[i] = shell[i].getBytes();
            }
            upload(conn, ip, sshPort, username, password, b, "/tmp/", fileName);

            for(int i = 0; i < shellName.length; i++) {
                Session sess = conn.openSession();
                try {
                    if(parameters == null || StringUtils.isBlank(parameters[i])) {
                        sess.execCommand(" chmod +x /tmp/" + fileName[i] + " && /tmp/" + fileName[i] + " && rm -rf /tmp/" + fileName[i]);
                    }
                    else {
                        sess.execCommand(" chmod +x /tmp/" + fileName[i] + " && /tmp/" + fileName[i] + " " + parameters[i] + "  && rm -rf /tmp/"
                                + fileName[i]);
                    }
                    InputStream stdout = new StreamGobbler(sess.getStdout());
                    BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
                    while(true) {
                        String line = br.readLine();
                        if(line == null) {
                            break;
                        }
                        else {
                            rtn[i] = line;
                        }
                    }

                }
                catch(Exception ex) {
                    rtn[i] = null;
                    throw ex;
                }
                finally {
                    if(sess != null) {
                        sess.close();
                    }
                }
            }
        }
        catch(Exception ex) {
            throw ex;
        }
        finally {
            if(conn != null) {
                conn.close();
            }
        }

        return rtn;
    }

    public static String ssh4Command(String ip, int sshPort, String username, String password, String command) throws Exception
    {

        String rtn = null;
        Connection conn = null;
        Session sess = null;
        try {
            conn = new Connection(ip, sshPort);

            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);

            if(isAuthenticated == false) {
                // "认证失败"
                throw new IOException(AIMonLocaleFactory.getResource("MOS0000271"));
            }

            sess = conn.openSession();

            sess.execCommand(command);

            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            while(true) {
                String line = br.readLine();
                if(line == null) {
                    break;
                }
                else {
                    rtn = line;
                }
            }

        }
        catch(Exception ex) {
            throw ex;
        }
        finally {
            if(sess != null) {
                sess.close();
            }
            if(conn != null) {
                conn.close();
            }
        }

        return rtn;
    }

    /**
     * 
     */
    public static int collectFile(String ip, int sshPort, String username, String password, File shell, File toDir) throws Exception
    {
        int rtn = 0;
        Connection conn = null;
        Session sess = null;
        try {
            conn = new Connection(ip, sshPort);

            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);

            if(isAuthenticated == false) {
                // "认证失败"
                throw new IOException(AIMonLocaleFactory.getResource("MOS0000271"));
            }

            // 上传文件
            String fileName = shell.getName();
            byte[] bb = FileUtils.readFileToByteArray(shell);

            upload(conn, ip, sshPort, username, password, bb, "/tmp/", fileName);
            sess = conn.openSession();

            sess.execCommand(" chmod +x /tmp/" + fileName + " && /tmp/" + fileName + " && rm -rf /tmp/" + fileName);

            List list = new ArrayList();
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            while(true) {
                String line = br.readLine();
                if(line == null) {
                    break;
                }
                else {
                    list.add(line);
                }
            }

            rtn = list.size();
            download(conn, ip, sshPort, username, password, (String[]) list.toArray(new String[list.size()]), toDir.getPath());

        }
        catch(Exception ex) {
            throw ex;
        }
        finally {
            if(sess != null) {
                sess.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
        return rtn;
    }

    public static void upload(Connection conn, String ip, int sshPort, String username, String password, byte[] bytes, String destPath,
            String fileName) throws Exception
    {
        try {
            SCPClient scp = conn.createSCPClient();
            scp.put(bytes, fileName, destPath);
        }
        catch(Exception ex) {
            throw ex;
        }
    }

    public static void upload(Connection conn, String ip, int sshPort, String username, String password, byte[][] bytes, String destPath,
            String[] fileName) throws Exception
    {
        try {
            SCPClient scp = conn.createSCPClient();
            for(int i = 0; i < fileName.length; i++) {
                scp.put(bytes[i], fileName[i], destPath);
            }
        }
        catch(Exception ex) {
            throw ex;
        }
    }

    public static void download(Connection conn, String ip, int sshPort, String username, String password, String[] remoteFileName, String localPath)
            throws Exception
    {
        try {
            SCPClient scp = conn.createSCPClient();
            scp.get(remoteFileName, localPath);
        }
        catch(Exception ex) {
            throw ex;
        }
    }
}