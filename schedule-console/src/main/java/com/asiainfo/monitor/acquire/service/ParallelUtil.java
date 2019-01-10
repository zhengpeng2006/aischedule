package com.asiainfo.monitor.acquire.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.asiainfo.monitor.tools.util.SSHUtil;

public class ParallelUtil {
	
	public static String[] sort(String[] str){
		HashMap map = new HashMap();
	    for (int i = 0; i < str.length; i++) {
	      map.put(new Integer(i), str[i]);
	    }

	    HashMap tmp = new HashMap();
	    for (int i = 0; i < str.length; i++) {
	      char[] ch = str[i].toCharArray();
	      StringBuffer sb = new StringBuffer();
	      for (int j = 0; j < ch.length; j++) {
	        sb.append(ch[j]);
	      }
	    }

	    return null;
	  }

	  public static String[] computeMidServer(int threadcCount, int timeout, MidServerTask[] objMidServerTask)throws Exception{
		  List rtn = new ArrayList();
		  ExecutorService exec = Executors.newFixedThreadPool(threadcCount);
		  try {
			  CompletionService serv = new ExecutorCompletionService(exec);

			  for (int i = 0; i < objMidServerTask.length; i++) {
				  serv.submit(new MidServerServerCallable(objMidServerTask[i]));
			  }

			  for (int index = 0; index < objMidServerTask.length; index++) {
				  Future task = serv.poll(timeout + 5, TimeUnit.SECONDS);
				  if (task == null) {
					  rtn.add(objMidServerTask[index].serverName + "执行超时");
				  }else {
					  String item = (String)task.get();
					  rtn.add(item);
				  }
			  }
	    }catch (Exception ex) {
	      throw ex;
	    }finally{
	      if (exec != null) {
	        exec.shutdownNow();
	      }
	    }
	    
	    return (String[])(String[])rtn.toArray(new String[0]);
	  }
	  
	  
	  public static String[] getBusiInfoFromService(int threadcCount, int timeout, MidServerTask[] objMidServerTask)throws Exception{
		  List rtn = new ArrayList();
		  ExecutorService exec = Executors.newFixedThreadPool(threadcCount);
		  try {
			  CompletionService serv = new ExecutorCompletionService(exec);

			  for (int i = 0; i < objMidServerTask.length; i++) {
				  serv.submit(new BusiKPICallable(objMidServerTask[i]));
			  }

			  for (int index = 0; index < objMidServerTask.length; index++) {
				  Future task = serv.poll(timeout + 5, TimeUnit.SECONDS);
				  if (task == null) {
					  rtn.add(objMidServerTask[index].serverName + "执行超时");
				  }else {
					  String item = (String)task.get();
					  rtn.add(item);
				  }
			  }
	    }catch (Exception ex) {
	      throw ex;
	    }finally{
	      if (exec != null) {
	        exec.shutdownNow();
	      }
	    }
	    
	    return (String[])(String[])rtn.toArray(new String[0]);
	  }
	  
	  
	  public static class MidServerTask{
		  public String ip;
		  public int port;
		  public String username;
		  public String password;
		  public String serverName;
		  public String path;
		  public String shell;
		  public String params;
	  }

	  public static class MidServerServerCallable implements Callable{
	    private MidServerTask objMidServerTask;

	    public MidServerServerCallable(MidServerTask objMidServerTask){
	      this.objMidServerTask = objMidServerTask;
	    }

	    public Object call() throws Exception {
	      String rtn = "";
	      try {
	        String tmp = SSHUtil.ssh4Shell(this.objMidServerTask.ip, this.objMidServerTask.port, this.objMidServerTask.username, this.objMidServerTask.password, this.objMidServerTask.serverName, this.objMidServerTask.path, this.objMidServerTask.shell);

	        rtn = this.objMidServerTask.serverName + "执行成功";
	      }catch (Exception ex) {
	        rtn = this.objMidServerTask.serverName + "执行失败:" + ex.getMessage();
	      }
	      return rtn;
	    }
	  }
	  
	  public static class BusiKPICallable implements Callable{
		  
		    private MidServerTask objMidServerTask;

		    public BusiKPICallable(MidServerTask objMidServerTask){
		      this.objMidServerTask = objMidServerTask;
		    }

		    public Object call() throws Exception {
		      String rtn = "";
		      try {
		    	  rtn = SSHUtil.ssh4Shell(this.objMidServerTask.ip, this.objMidServerTask.port, this.objMidServerTask.username, this.objMidServerTask.password, this.objMidServerTask.serverName, this.objMidServerTask.params, this.objMidServerTask.shell);
		      }catch (Exception ex) {
		    	  rtn = this.objMidServerTask.serverName + "执行失败:" + ex.getMessage();
		      }
		      
		      return rtn;
		    }
		  }
}
