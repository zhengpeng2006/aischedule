/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asiainfo.socket.request;

import net.sf.json.JSONObject;

/**
 * 请求表示类
 */
public class Request {
    private Object attachment;
    private String ip;
    private String port;
    private JSONObject reqJson;

    public Request(String ip, String port, JSONObject reqJson, Object attachment) {
        this.ip = ip;
        this.port = port;
        this.reqJson = reqJson;
        this.attachment = attachment;
    }

    /**
     * @return the attachment
     */
    public Object getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the reqJson
     */
    public JSONObject getReqJson() {
        return reqJson;
    }

    /**
     * @param reqJson the reqJson to set
     */
    public void setReqJson(JSONObject reqJson) {
        this.reqJson = reqJson;
    }

	@Override
	public String toString() {
		return "Request [attachment=" + attachment + ", ip=" + ip + ", port=" + port + ", reqJson=" + reqJson + "]";
	}
}
