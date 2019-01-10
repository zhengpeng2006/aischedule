package com.asiainfo.monitor.interapi.api.collect;

public class AppSSHConfig {
  private String hostname;
  private String username;
  private String password;
  private String state;
  private long sshport;
  private String ip;
  public AppSSHConfig() {
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setSshport(long sshport) {
    this.sshport = sshport;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getHostname() {
    return hostname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getState() {
    return state;
  }

  public long getSshport() {
    return sshport;
  }

  public String getIp() {
    return ip;
  }
}
