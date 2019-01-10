package com.asiainfo.monitor.common.combin;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;

/**
 * 主机组合对象，包含，主机连接方式，主机用户，主备机关系
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class CmbHostBean
{

    /** 主机基础属性 **/
    private long hostId = 0;
    private String hostCode = null;
    private String hostName = null;
    private String hostIp = null;

    /** 主机支持的连接方式 **/
    private IBOAIMonConModeValue[] conModeArr;

    /** 主机用户 **/
    //private IBOAIMonHostUserValue[] hostUserArr;

    /**
     * 根据连接类型获取连接方式端口
     * @param conType
     * @return
     */
    public String getConPort(String conType)
    {
        String conPort = "";
        for(IBOAIMonConModeValue conMode : conModeArr) {
            if(conType.endsWith(conMode.getConType())) {
                conPort = String.valueOf(conMode.getConPort());
                break;
            }
        }

        return conPort;
    }

    /**
     * 获取监控用户信息
     * @return
     */
    /*  public Map<String, String> getMonUserInfo()
      {
          Map<String, String> userInfoMap = null;
          if(null != hostUserArr && hostUserArr.length > 0) {
              for(IBOAIMonHostUserValue hostUser : hostUserArr) {
                  if(hostUser.getUserType().equals(CommonConst.USER_TYPE_MONITOR)) {
                      userInfoMap = new HashMap<String, String>();
                      userInfoMap.put(CommonConst.USER_NAME, hostUser.getUserName());
                      userInfoMap.put(CommonConst.USER_PASSWD, hostUser.getUserPwd());
                      return userInfoMap;
                  }
              }
          }

          return null;
      }
    */
    /**
     * 获取发布用户信息
     * @return
     */
    /*  public Map<String, String> getPubUserInfo()
      {
          Map<String,String> userInfoMap = null;
          if(null != hostUserArr && hostUserArr.length > 0) {
              for(IBOAIMonHostUserValue hostUser : hostUserArr) {
                  if(hostUser.getUserType().equals(CommonConst.USER_TYPE_PUBLISH)) {
                      userInfoMap = new HashMap<String, String>();
                      userInfoMap.put(CommonConst.USER_NAME, hostUser.getUserName());
                      userInfoMap.put(CommonConst.USER_PASSWD, hostUser.getUserPwd());
                      return userInfoMap;
                  }
              }
          }
          return null;
      }*/



    public long getHostId()
    {
        return hostId;
    }

    public void setHostId(long hostId)
    {
        this.hostId = hostId;
    }

    public String getHostCode()
    {
        return hostCode;
    }

    public void setHostCode(String hostCode)
    {
        this.hostCode = hostCode;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public String getHostIp()
    {
        return hostIp;
    }

    public IBOAIMonConModeValue[] getConModeArr()
    {
        return conModeArr;
    }

    /* public IBOAIMonHostUserValue[] getHostUserArr()
     {
         return hostUserArr;
     }*/

    public void setHostIp(String hostIp)
    {
        this.hostIp = hostIp;
    }

    public void setConModeArr(IBOAIMonConModeValue[] conModeArr)
    {
        this.conModeArr = conModeArr;
    }

    /*   public void setHostUserArr(IBOAIMonHostUserValue[] hostUserArr)
       {
           this.hostUserArr = hostUserArr;
       }*/


}
