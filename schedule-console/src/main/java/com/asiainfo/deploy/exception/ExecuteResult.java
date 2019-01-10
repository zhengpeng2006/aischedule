package com.asiainfo.deploy.exception;

/**
 * 执行结果
 * @author 孙德东(24204)
 */
public class ExecuteResult 
{
	private boolean success;
	private Object message;
	private Long errorCode; //0表示成功
	
	private ExecuteResult(){}
	
	public static ExecuteResult successResult(Object message)
	{
		 ExecuteResult result = new ExecuteResult();
		 result.success = true;
		 result.message = message;
		 result.errorCode = 0L;
		 return result;
	}

	public static ExecuteResult errorResult(long errorCode, Object message)
	{
		 ExecuteResult result = new ExecuteResult();
		 result.success = false;
		 result.errorCode = errorCode;
		 result.message = message;
		 return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getMessage() {
		return message;
	}

	public long getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[isSuccess=")
		  .append(success)
		  .append(",errorCode=")
		  .append(errorCode)
		  .append(",message=\r\n")
		  .append(message)
		  .append("]");
		return sb.toString();
	}
	
	
}
